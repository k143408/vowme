package com.vowme.app.utilities.customWidgets;

import android.content.Context;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.vowme.app.models.Enum.SharedPreferencesKeys;
import com.vowme.app.models.SearchItem;
import com.vowme.app.utilities.adapters.LocationAutoCompleteArrayAdapter;
import com.vowme.app.utilities.helpers.sharedPreferences.UserSearchSharedLocationFieldHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomMultiAutoCompleteTextView extends AppCompatMultiAutoCompleteTextView {
    private List<String> mItems;

    public CustomMultiAutoCompleteTextView(Context context) {
        super(context);
        init();
    }

    public CustomMultiAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void onSelectionChanged(int selStart, int selEnd) {
        setSelection(length());
    }

    public boolean onTextContextMenuItem(int id) {
        return false;
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= -1073741825;
        return conn;
    }

    private void init() {
        this.mItems = new ArrayList();
        setThreshold(3);
        setLongClickable(false);
        setTextIsSelectable(false);
        setTokenizer(new CustomTokenizer());
        setImeOptions(6);
        setOnEditorActionListener(new C09031());
        setOnItemClickListener(new C09042());
        setOnFocusChangeListener(new C09053());
        setOnClickListener(new C09064());
        addTextChangedListener(new C09075());
        setCustomSelectionActionModeCallback(new C09086());
    }

    public void setUpSearchlocationText(Context context, String apiViktorURL, String apiViktorClientSecret, String apiViktorGetClientSecret, String apiVolunteerURL, String volunteerAccessToken, List<String> volunteerLocation, boolean isLoggedinSearh) {
        setAdapter(new LocationAutoCompleteArrayAdapter(context, apiViktorURL, apiViktorClientSecret, apiViktorGetClientSecret, apiVolunteerURL, volunteerAccessToken, volunteerLocation, isLoggedinSearh));
        if (!isLoggedinSearh) {
            setInputType(655361);
        }
    }

    public void addToItems(String item) {
        this.mItems.add(item);
        ((LocationAutoCompleteArrayAdapter) getAdapter()).setVolunteerLocation(this.mItems);
    }

    public void clearItems() {
        this.mItems.clear();
    }

    private void removeItems(int location) {
        this.mItems.remove(location);
        ((LocationAutoCompleteArrayAdapter) getAdapter()).setVolunteerLocation(this.mItems);
    }

    public String itemsJoined() {
        return TextUtils.join(",", this.mItems);
    }

    public List<String> itemsList() {
        return new ArrayList(this.mItems);
    }

    class C09031 implements OnEditorActionListener {
        C09031() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            ((InputMethodManager) CustomMultiAutoCompleteTextView.this.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
    }

    class C09042 implements OnItemClickListener {
        C09042() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SearchItem item = (SearchItem) parent.getItemAtPosition(position);
            CustomMultiAutoCompleteTextView.this.addToItems(item.getName());
            Context context = CustomMultiAutoCompleteTextView.this.getContext();
            String value = SharedPreferencesKeys.USER_SEARCH_LOCATION_FIELD.getValue();
            CustomMultiAutoCompleteTextView.this.getContext();
            UserSearchSharedLocationFieldHelper.addToRecents(context.getSharedPreferences(value, 0), item.getName());
        }
    }

    class C09053 implements OnFocusChangeListener {
        C09053() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ((CustomMultiAutoCompleteTextView) v).getFilter().filter("");
            }
        }
    }

    class C09064 implements OnClickListener {
        C09064() {
        }

        public void onClick(View v) {
            CustomMultiAutoCompleteTextView.this.setSelection(CustomMultiAutoCompleteTextView.this.getText().length());
        }
    }

    class C09075 implements TextWatcher {
        C09075() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if ((s instanceof SpannableStringBuilder) && after < count) {
                char lastCharacter = s.toString().charAt(s.length() - 1);
                SpannableStringBuilder textSpan = (SpannableStringBuilder) s;
                Object[] spans = textSpan.getSpans(0, textSpan.length(), Object.class);
                StyleSpan[] spaces = (StyleSpan[]) textSpan.getSpans(0, s.length(), StyleSpan.class);
                if (lastCharacter != ' ' || spaces.length <= 0 || s.length() != textSpan.getSpanEnd(spaces[spaces.length - 1])) {
                    return;
                }
                if (spaces.length > 1) {
                    StyleSpan spanToDeleteUntil = spaces[spaces.length - 2];
                    int indexSpanToDeleteUntil = textSpan.getSpanEnd(spanToDeleteUntil);
                    int i = spans.length - 1;
                    StyleSpan span = (StyleSpan) spans[i];
                    while (i >= 0 && span != spanToDeleteUntil) {
                        textSpan.removeSpan(span);
                        i--;
                        span = (StyleSpan) spans[i];
                    }
                    CustomMultiAutoCompleteTextView.this.setText(textSpan.subSequence(0, indexSpanToDeleteUntil));
                    CustomMultiAutoCompleteTextView.this.removeItems(CustomMultiAutoCompleteTextView.this.mItems.size() - 1);
                } else if (spaces.length == 1) {
                    CustomMultiAutoCompleteTextView.this.getText().clearSpans();
                    CustomMultiAutoCompleteTextView.this.setText("");
                    CustomMultiAutoCompleteTextView.this.removeItems(CustomMultiAutoCompleteTextView.this.mItems.size() - 1);
                }
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C09086 implements Callback {
        C09086() {
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }
    }
}
