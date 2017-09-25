package com.vowme.app.utilities.validators;

import android.support.design.widget.TextInputLayout;

public class FloatingTextRegexValidator extends FloatingTextValidator {
    private String regex;

    public FloatingTextRegexValidator(TextInputLayout floatingText, String regex, String requiredMessage) {
        super(floatingText);
        this.regex = regex;
        this.requiredMessage = requiredMessage;
        this.errorMessage = requiredMessage;
    }

    public FloatingTextRegexValidator(TextInputLayout floatingText, String regex, String requiredMessage, String errorMessage) {
        super(floatingText);
        this.regex = regex;
        this.requiredMessage = requiredMessage;
        this.errorMessage = errorMessage;
    }

    public boolean validate(String text) {
        int length = text.length();
        if (length == 0 && this.requiredMessage != null) {
            this.displayMessage = this.requiredMessage;
            return false;
        } else if (length == 0 && this.requiredMessage == null) {
            return true;
        } else {
            if (text.matches(this.regex)) {
                return true;
            }
            this.displayMessage = this.errorMessage;
            return false;
        }
    }

    public void isValidToSubmit(boolean isValid, int id) {
    }
}
