package com.vowme.app.utilities.validators;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

public class FloatingTextsCompareValidator extends FloatingTextValidator {
    private EditText compare;

    public FloatingTextsCompareValidator(TextInputLayout floatingText, EditText compare, String requiredMessage) {
        super(floatingText);
        this.compare = compare;
        this.requiredMessage = requiredMessage;
        this.errorMessage = requiredMessage;
    }

    public FloatingTextsCompareValidator(TextInputLayout floatingText, EditText compare, String requiredMessage, String errorMessage) {
        super(floatingText);
        this.compare = compare;
        this.requiredMessage = requiredMessage;
        this.errorMessage = errorMessage;
    }

    public boolean validate(String text) {
        if (text.length() == 0) {
            this.displayMessage = this.requiredMessage;
            return false;
        } else if (text.matches(this.compare.getText().toString())) {
            return true;
        } else {
            this.displayMessage = this.errorMessage;
            return false;
        }
    }

    public void isValidToSubmit(boolean isValid, int id) {
    }
}
