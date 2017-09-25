package com.vowme.app.utilities.validators;

import android.support.design.widget.TextInputLayout;

public class FloatingTextLengthValidator extends FloatingTextValidator {
    private int maxLength;
    private int minLength;

    public FloatingTextLengthValidator(TextInputLayout floatingText, int minLength, String requiredMessage) {
        super(floatingText);
        this.minLength = minLength;
        this.maxLength = 0;
        this.requiredMessage = requiredMessage;
        this.errorMessage = requiredMessage;
    }

    public FloatingTextLengthValidator(TextInputLayout floatingText, int minLength, int maxLength, String requiredMessage, String errorMessage) {
        super(floatingText);
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.requiredMessage = requiredMessage;
        this.errorMessage = errorMessage;
    }

    public boolean validate(String text) {
        int length = text.length();
        if (this.minLength == 1 && length == 0) {
            this.displayMessage = this.requiredMessage;
            return false;
        } else if (this.maxLength == 0) {
            return true;
        } else {
            if (this.minLength <= length && this.maxLength >= length) {
                return true;
            }
            this.displayMessage = this.errorMessage;
            return false;
        }
    }

    public void isValidToSubmit(boolean isValid, int id) {
    }
}
