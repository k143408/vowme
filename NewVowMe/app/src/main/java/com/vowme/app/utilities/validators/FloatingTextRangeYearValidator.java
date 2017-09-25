package com.vowme.app.utilities.validators;

import android.support.design.widget.TextInputLayout;

public class FloatingTextRangeYearValidator extends FloatingTextValidator {
    private int maxYear;
    private int minYear;

    public FloatingTextRangeYearValidator(TextInputLayout floatingText, int minYear, String requiredMessage) {
        super(floatingText);
        this.minYear = minYear;
        this.maxYear = 0;
        this.requiredMessage = requiredMessage;
        this.errorMessage = requiredMessage;
    }

    public FloatingTextRangeYearValidator(TextInputLayout floatingText, int minYear, int maxYear, String requiredMessage, String errorMessage) {
        super(floatingText);
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.requiredMessage = requiredMessage;
        this.errorMessage = errorMessage;
    }

    public boolean validate(String text) {
        if (text.length() == 0) {
            this.displayMessage = this.requiredMessage;
            return false;
        }
        Integer year = Integer.valueOf(Integer.parseInt(text));
        if (year == null) {
            this.displayMessage = this.requiredMessage;
            return false;
        } else if (this.minYear <= year.intValue() && this.maxYear >= year.intValue()) {
            return true;
        } else {
            this.displayMessage = this.errorMessage;
            return false;
        }
    }

    public void isValidToSubmit(boolean isValid, int id) {
    }
}
