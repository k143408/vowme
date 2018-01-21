package com.vowme.vol.app.activities.timesheet;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.TimesheetItem;
import com.vowme.app.models.api.ViraHoursModel;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.activities.SaveMenuFormValidationActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.customWidgets.CustomSpinnerTextView;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.app.utilities.helpers.TextViewHelper;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LogHoursActivity extends SaveMenuFormValidationActivity {
    private static TextView dateOfLog;
    private static TimesheetItem model;
    private static TextView timeOfLog;
    private boolean isEditMode = true;
    private List<Lookup> opportunitiesToLog;
    private CustomSpinnerTextView opportunity;

    private static String getDuration() {
        return TextViewHelper.getHourTimeToString(model.hours) + ":" + TextViewHelper.getMinuteTimeToString(model.minutes);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.opportunitiesToLog == null) {
            this.opportunitiesToLog = new ArrayList();
        }
    }

    protected void createModel() {
        this.isEditMode = getIntent().getBooleanExtra("android.intent.extra.TEXT", true);
        model = new TimesheetItem();
        if (!TextUtils.isEmpty(this.modelAsString)) {
            try {
                if (this.isEditMode) {
                    model = new TimesheetItem(new JSONObject(this.modelAsString));
                    return;
                }
                this.opportunitiesToLog = new ArrayList();
                JSONArray opportunities = new JSONArray(this.modelAsString);
                for (int i = 0; i < opportunities.length(); i++) {
                    this.opportunitiesToLog.add(new Lookup(opportunities.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected void initFields() {
        TextInputLayout oppLayout = (TextInputLayout) findViewById(R.id.opportunity_include);
        this.opportunity = (CustomSpinnerTextView) oppLayout.findViewById(R.id.spinner_txt);
        oppLayout.setHint("Opportunity");
        this.opportunity.setText(model.name);
        if (this.isEditMode) {
            this.opportunity.setEnabled(false);
            this.opportunity.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.dis_hint_light));
            this.opportunity.setHintTextColor(ContextCompat.getColor(getBaseContext(), R.color.dis_hint_light));
        } else {
            this.opportunity.setUpSpinnerTextView(17367043, this.opportunitiesToLog);
        }
        dateOfLog = (TextView) findViewById(R.id.date_txt);
        dateOfLog.setInputType(0);
        dateOfLog.setOnClickListener(new DatePickerListener());
        dateOfLog.setOnFocusChangeListener(new DatePickerOnFocusChange());
        dateOfLog.setText(model.dateAsString);
        timeOfLog = (TextView) findViewById(R.id.time_txt);
        timeOfLog.setInputType(0);
        timeOfLog.setOnClickListener(new TimePickerFragmentOnClickListener());
        timeOfLog.setOnFocusChangeListener(new TimePickerFragmentOnFocusChange());
        timeOfLog.setText(getDuration());
    }

    protected void OnSaveOptionsItemSelected() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateString = dateFormat.format(model.date);
        try {
            model.date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!this.isEditMode) {
            String opportunityName = this.opportunity.getText().toString();
            model.positionId = ArrayListHelper.getIdFromName(opportunityName, this.opportunitiesToLog);
        }
        new putVolunteerFewThings(model.toJsonObject()).execute(new Void[0]);
    }

    protected int getLayoutResID() {
        return R.layout.activity_log_hours;
    }

    public static class DatePickerFragment extends DialogFragment implements OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (!(LogHoursActivity.dateOfLog == null || LogHoursActivity.dateOfLog.getText() == null || TextUtils.isEmpty(LogHoursActivity.dateOfLog.getText()))) {
                try {
                    c.setTime(dateFormat.parse(LogHoursActivity.dateOfLog.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DATE);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            c.set(year - 120, month - 1, day, 0, 0);
            try {
                dialog.getDatePicker().setMinDate(dateFormat.parse(dateFormat.format(c.getTime())).getTime());
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, 0, 0);
            LogHoursActivity.dateOfLog.setText(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
            LogHoursActivity.model.date = c.getTime();
        }
    }

    public static class TimePickerFragment extends DialogFragment implements OnTimeSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new TimePickerDialog(getActivity(), this, LogHoursActivity.model.hours.intValue(), LogHoursActivity.model.minutes.intValue(), true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            LogHoursActivity.model.hours = Integer.valueOf(hourOfDay);
            LogHoursActivity.model.minutes = Integer.valueOf(minute);
            LogHoursActivity.timeOfLog.setText(LogHoursActivity.getDuration());
        }
    }

    class DatePickerListener implements OnClickListener {
        DatePickerListener() {
        }

        public void onClick(View v) {
            new DatePickerFragment().show(LogHoursActivity.this.getSupportFragmentManager(), "datePicker");
        }
    }

    class DatePickerOnFocusChange implements OnFocusChangeListener {
        DatePickerOnFocusChange() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                new DatePickerFragment().show(LogHoursActivity.this.getSupportFragmentManager(), "datePicker");
            }
        }
    }

    class TimePickerFragmentOnClickListener implements OnClickListener {
        TimePickerFragmentOnClickListener() {
        }

        public void onClick(View v) {
            new TimePickerFragment().show(LogHoursActivity.this.getSupportFragmentManager(), "timePicker");
        }
    }

    class TimePickerFragmentOnFocusChange implements OnFocusChangeListener {
        TimePickerFragmentOnFocusChange() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                new TimePickerFragment().show(LogHoursActivity.this.getSupportFragmentManager(), "timePicker");
            }
        }
    }

    public class putVolunteerFewThings extends ApiRestFullRequest {
        public putVolunteerFewThings(JSONObject param) {
            super(HttpRequestType.POST, LogHoursActivity.this.getString(R.string.apiVolunteerURL1), "api/opportunity/log/" + LogHoursActivity.this.getUserAccessToken() + "/" + Integer.toString(LogHoursActivity.model.positionId), param, LogHoursActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            LogHoursActivity.this.setResult(-1, new Intent());
            LogHoursActivity.this.finish();
        }
    }
}
