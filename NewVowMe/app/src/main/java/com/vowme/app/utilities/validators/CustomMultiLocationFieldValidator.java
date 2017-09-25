package com.vowme.app.utilities.validators;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.StyleSpan;

import com.vowme.app.utilities.customWidgets.CustomMultiAutoCompleteTextView;

public class CustomMultiLocationFieldValidator implements TextWatcher {
    private boolean isLoggedSear;
    private CustomMultiAutoCompleteTextView locationField;

    public CustomMultiLocationFieldValidator(CustomMultiAutoCompleteTextView locationField, boolean isLoggedSear) {
        this.locationField = locationField;
        this.isLoggedSear = isLoggedSear;
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
        if (s instanceof SpannableStringBuilder) {
            StyleSpan[] spaces = (StyleSpan[]) ((SpannableStringBuilder) s).getSpans(0, s.length(), StyleSpan.class);
            if (!this.isLoggedSear && spaces.length > 1) {
                this.locationField.setError("You can specify only one location.");
                isValidToSubmit(false, this.locationField.getId());
            } else if (spaces.length == 0) {
                this.locationField.setError("You must specify one location.");
                isValidToSubmit(false, this.locationField.getId());
            } else {
                this.locationField.setError(null);
                isValidToSubmit(true, this.locationField.getId());
            }
        }
    }

    public void isValidToSubmit(boolean isValid, int id) {
    }
}
