package com.vowme.vol.app.activities.profile;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.VolunteerContactModel;
import com.vowme.app.models.api.VolunteerFewThingsModel;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.activities.ProfileFormActivity;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.customWidgets.CustomSpinnerTextView;
import com.vowme.app.utilities.helpers.ArrayListHelper;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PersonalInfoActivity extends ProfileFormActivity {
    private static TextView dateOfbirth;
    private static VolunteerFewThingsModel model;
    private final List<String> yesNo = new C08211();
    private CustomSpinnerTextView attended;
    private CustomSpinnerTextView before;
    private VolunteerContactModel contactModel;
    private TextView contactName;
    private TextView contactPhone;
    private TextView dietary;
    private TextView driver;
    private TextView email;
    private TextView medical;
    private TextView phone;
    private TextView postcode;
    private TextView street;
    private CustomSpinnerTextView suburb;
    private List<Lookup> suburbs = new ArrayList();
    private CustomSpinnerTextView tShirt;
    private CustomSpinnerTextView transport;
    private CustomSpinnerTextView typeCar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initFields() {
        this.email = (TextView) findViewById(R.id.email_txt);
        this.street = (TextView) findViewById(R.id.street_txt);
        this.phone = (TextView) findViewById(R.id.phone_txt);
        this.postcode = (TextView) findViewById(R.id.postcode_txt);
        this.contactName = (TextView) findViewById(R.id.name_txt);
        this.contactPhone = (TextView) findViewById(R.id.phone_contact_txt);
        //TextInputLayout suburbtLayout = (TextInputLayout) findViewById(R.id.suburb_include);
        //this.suburb = (CustomSpinnerTextView) suburbtLayout.findViewById(R.id.spinner_txt);
        //suburbtLayout.setHint("Suburb*");
        dateOfbirth = (TextView) findViewById(R.id.birth_txt);
        dateOfbirth.setInputType(0);
        dateOfbirth.setOnClickListener(new C08222());
        dateOfbirth.setOnFocusChangeListener(new C08233());
        this.driver = (TextView) findViewById(R.id.driver_txt);
        this.dietary = (TextView) findViewById(R.id.dietary_txt);
        this.medical = (TextView) findViewById(R.id.medical_txt);
        this.transport.setUpSpinnerTextView(17367043, DefaultDataHelper.getTransports());
        this.typeCar.setUpSpinnerTextView(17367043, DefaultDataHelper.getTypeOfCars());
        this.tShirt.setUpSpinnerTextView(17367043, DefaultDataHelper.gettShirtSize());
        this.before.setUpSpinnerTextView(17367043, this.yesNo);
        this.attended.setUpSpinnerTextView(17367043, this.yesNo);
        this.suburb.setUpSpinnerTextView(17367043, this.suburbs);
        TextInputLayout floatingstreetText = (TextInputLayout) findViewById(R.id.input_layout_street);
        if (!(floatingstreetText == null || floatingstreetText.getEditText() == null)) {
            floatingstreetText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingstreetText, 1, 150, "Please enter a street.", "The street can have lengh up to 150 characters."));
        }
        TextInputLayout floatingPhoneText = (TextInputLayout) findViewById(R.id.input_layout_phone);
        if (!(floatingPhoneText == null || floatingPhoneText.getEditText() == null)) {
            floatingPhoneText.getEditText().addTextChangedListener(getFloatingTextRegexValidator(floatingPhoneText, getResources().getString(R.string.phone_regex), "Please enter a phone number.", "Please enter a valid phone number."));
        }
        TextInputLayout floatingPostcodeText = (TextInputLayout) findViewById(R.id.input_layout_postcode);
        if (!(floatingPostcodeText == null || floatingPostcodeText.getEditText() == null)) {
            floatingPostcodeText.getEditText().addTextChangedListener(getFloatingTextRegexValidator(floatingPostcodeText, getResources().getString(R.string.postcode_regex), "Please enter a postcode.", "The postcode must be a valid number."));
            floatingPostcodeText.getEditText().addTextChangedListener(new C08244());
        }
      /*  TextInputLayout floatingSuburbText = (TextInputLayout) findViewById(R.id.suburb_include);
        if (!(floatingSuburbText == null || floatingSuburbText.getEditText() == null)) {
            floatingSuburbText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingSuburbText, 1, "Please select your suburb."));
        }
      */
        TextInputLayout floatingContactText = (TextInputLayout) findViewById(R.id.input_layout_name);
        if (!(floatingContactText == null || floatingContactText.getEditText() == null)) {
            floatingContactText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingContactText, 0, 100, null, "Your emergency contact name can have length up to 100 characters."));
        }
        TextInputLayout floatingContactPhoneText = (TextInputLayout) findViewById(R.id.input_layout_phone_contact);
        if (!(floatingContactPhoneText == null || floatingContactPhoneText.getEditText() == null)) {
            floatingContactPhoneText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingContactText, 0, 50, null, "Your emergency contact phone number can have length up to 50 characters."));
        }
        TextInputLayout floatingBirthText = (TextInputLayout) findViewById(R.id.input_layout_birth);
        if (!(floatingBirthText == null || floatingBirthText.getEditText() == null)) {
            floatingBirthText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingBirthText, 1, "Please enter your date of Birth."));
        }
        TextInputLayout floatingDriverText = (TextInputLayout) findViewById(R.id.input_layout_driver);
        if (!(floatingDriverText == null || floatingDriverText.getEditText() == null)) {
            floatingDriverText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingDriverText, 0, 20, null, "Your licence number can have length up to 20 characters."));
        }
        TextInputLayout floatingDietaryText = (TextInputLayout) findViewById(R.id.input_layout_dietary);
        if (!(floatingDietaryText == null || floatingDietaryText.getEditText() == null)) {
            floatingDietaryText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingDietaryText, 0, Callback.DEFAULT_DRAG_ANIMATION_DURATION, null, "Your dietary requirements can have length up to 200 characters.\""));
        }
        TextInputLayout floatingMedicalText = (TextInputLayout) findViewById(R.id.input_layout_medical);
        if (!(floatingMedicalText == null || floatingMedicalText.getEditText() == null)) {
            floatingMedicalText.getEditText().addTextChangedListener(getFloatingTextLengthValidator(floatingMedicalText, 0, Callback.DEFAULT_DRAG_ANIMATION_DURATION, null, "Your medical history can have length up to 200 characters."));
        }
        new GetVolunteerFewThings().execute(new Void[0]);
    }

    protected void OnSaveOptionsItemSelected() {
        this.contactModel.emergencyName = this.contactName.getText().toString();
        this.contactModel.emergencyPhone = this.contactPhone.getText().toString();
        if (this.contactModel.phone != null) {
            this.contactModel.phone.number = this.phone.getText().toString();
        }
        if (this.contactModel.address != null) {
            this.contactModel.address.street = this.street.getText().toString();
            this.contactModel.address.postcode = Integer.valueOf(Integer.parseInt(this.postcode.getText().toString()));
            this.contactModel.address.suburbId = ArrayListHelper.getIdFromName(this.suburb.getText().toString(), this.suburbs);
        }
        new putVolunteerContact(this.contactModel.toJsonObject().toString()).execute(new Void[0]);
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_personal_info;
    }

    public static class DatePickerFragment extends DialogFragment implements OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            if (!(PersonalInfoActivity.dateOfbirth == null || PersonalInfoActivity.dateOfbirth.getText() == null || TextUtils.isEmpty(PersonalInfoActivity.dateOfbirth.getText()))) {
                try {
                    c.setTime(dateFormat.parse(PersonalInfoActivity.dateOfbirth.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            c.setTime(new Date());

            c.set(year - 18, month - 1, day, 0, 0);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            c.set(year - 120, month - 1, day, 0, 0);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day, 0, 0);
            PersonalInfoActivity.dateOfbirth.setText(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
        }
    }

    class C08211 extends ArrayList<String> {
        C08211() {
            add("Yes");
            add("No");
        }
    }

    class C08222 implements OnClickListener {
        C08222() {
        }

        public void onClick(View v) {
            new DatePickerFragment().show(PersonalInfoActivity.this.getSupportFragmentManager(), "datePicker");
        }
    }

    class C08233 implements OnFocusChangeListener {
        C08233() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                new DatePickerFragment().show(PersonalInfoActivity.this.getSupportFragmentManager(), "datePicker");
            }
        }
    }

    class C08244 implements TextWatcher {
        C08244() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            new GetSuburb(Integer.valueOf(Integer.parseInt(s.toString()))).execute(new Void[0]);
            PersonalInfoActivity.this.suburb.getText().clear();
        }
    }

    private class GetSuburb extends ApiRestFullRequest {
        public GetSuburb(Integer postcode) {
            super(HttpRequestType.GET, PersonalInfoActivity.this.getString(R.string.apiVolunteerURL), "api/location/" + postcode.toString() + "/suburbs", PersonalInfoActivity.this.getApliAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() != 0) {
                try {
                    JSONArray datas = new JSONArray(result);
                    PersonalInfoActivity.this.suburbs.clear();
                    for (int i = 0; i < datas.length(); i++) {
                        PersonalInfoActivity.this.suburbs.add(new Lookup(datas.getJSONObject(i)));
                    }
                    ((ArrayAdapter) PersonalInfoActivity.this.suburb.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class GetVolunteerContact extends ApiRestFullRequest {
        public GetVolunteerContact() {
            super(HttpRequestType.GET, PersonalInfoActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/contact", PersonalInfoActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() != 0) {
                try {
                    PersonalInfoActivity.this.contactModel = new VolunteerContactModel(new JSONObject(result));
                    PersonalInfoActivity.this.email.setText(PersonalInfoActivity.this.contactModel.email);
                    PersonalInfoActivity.this.contactName.setText(PersonalInfoActivity.this.contactModel.emergencyName);
                    PersonalInfoActivity.this.contactPhone.setText(PersonalInfoActivity.this.contactModel.emergencyPhone);
                    if (PersonalInfoActivity.this.contactModel.phone != null) {
                        PersonalInfoActivity.this.phone.setText(PersonalInfoActivity.this.contactModel.phone.number);
                    }
                    if (PersonalInfoActivity.this.contactModel.address != null) {
                        PersonalInfoActivity.this.street.setText(PersonalInfoActivity.this.contactModel.address.street);
                        PersonalInfoActivity.this.postcode.setText(PersonalInfoActivity.this.contactModel.address.postcode.toString());
                        PersonalInfoActivity.this.suburb.setText(PersonalInfoActivity.this.contactModel.address.suburbName);
                        new GetSuburb(PersonalInfoActivity.this.contactModel.address.postcode).execute(new Void[0]);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class GetVolunteerFewThings extends ApiRestFullRequest {
        public GetVolunteerFewThings() {
            super(HttpRequestType.GET, PersonalInfoActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/fewthings", PersonalInfoActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            if (result.length() != 0) {
                try {
                    PersonalInfoActivity.model = new VolunteerFewThingsModel(new JSONObject(result));
                    PersonalInfoActivity.this.driver.setText(PersonalInfoActivity.model.licenceNumber);
                    PersonalInfoActivity.this.transport.setText(PersonalInfoActivity.model.transport);
                    PersonalInfoActivity.this.typeCar.setText(PersonalInfoActivity.model.typeOfCar);
                    PersonalInfoActivity.this.tShirt.setText(PersonalInfoActivity.model.tShirtSize);
                    PersonalInfoActivity.this.before.setText(PersonalInfoActivity.model.hasVolunteered ? "Yes" : "No");
                    PersonalInfoActivity.this.attended.setText(PersonalInfoActivity.model.hasAttended ? "Yes" : "No");
                    PersonalInfoActivity.this.dietary.setText(PersonalInfoActivity.model.dietaryRequirements);
                    PersonalInfoActivity.this.medical.setText(PersonalInfoActivity.model.medicalHistorical);
                    try {
                        PersonalInfoActivity.dateOfbirth.setText(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(PersonalInfoActivity.model.birthDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    new GetVolunteerContact().execute(new Void[0]);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class putVolunteerContact extends ApiRestFullRequest {
        public putVolunteerContact(String param) {
            super(HttpRequestType.PUT, PersonalInfoActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/contact", param, PersonalInfoActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            boolean z;
            boolean z2 = true;
            PersonalInfoActivity.model.licenceNumber = PersonalInfoActivity.this.driver.getText().toString();
            PersonalInfoActivity.model.transport = PersonalInfoActivity.this.transport.getText().toString();
            PersonalInfoActivity.model.typeOfCar = TextUtils.isEmpty(PersonalInfoActivity.this.typeCar.getText().toString()) ? null : PersonalInfoActivity.this.typeCar.getText().toString();
            PersonalInfoActivity.model.tShirtSize = PersonalInfoActivity.this.tShirt.getText().toString();
            VolunteerFewThingsModel access$200 = PersonalInfoActivity.model;
            if (PersonalInfoActivity.this.before.getText().toString().equals("Yes")) {
                z = true;
            } else {
                z = false;
            }
            access$200.hasVolunteered = z;
            VolunteerFewThingsModel access$2002 = PersonalInfoActivity.model;
            if (!PersonalInfoActivity.this.attended.getText().toString().equals("Yes")) {
                z2 = false;
            }
            access$2002.hasAttended = z2;
            PersonalInfoActivity.model.dietaryRequirements = PersonalInfoActivity.this.dietary.getText().toString();
            PersonalInfoActivity.model.medicalHistorical = PersonalInfoActivity.this.medical.getText().toString();
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(PersonalInfoActivity.dateOfbirth.getText().toString());
                PersonalInfoActivity.model.birthDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            new putVolunteerFewThings(PersonalInfoActivity.model.toJsonObject().toString()).execute(new Void[0]);
        }
    }

    public class putVolunteerFewThings extends ApiRestFullRequest {
        public putVolunteerFewThings(String param) {
            super(HttpRequestType.PUT, PersonalInfoActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/fewthings", param, PersonalInfoActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            PersonalInfoActivity.this.finish();
        }
    }
}
