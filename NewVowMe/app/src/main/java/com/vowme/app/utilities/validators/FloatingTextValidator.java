package com.vowme.app.utilities.validators;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

public abstract class FloatingTextValidator implements TextWatcher {
    protected String displayMessage;
    protected String errorMessage;
    protected String requiredMessage;
    private TextInputLayout floatingText;

    public FloatingTextValidator(TextInputLayout floatingText) {
        this.floatingText = floatingText;
    }

    public abstract void isValidToSubmit(boolean z, int i);

    public abstract boolean validate(String str);

    public void afterTextChanged(Editable s) {
        boolean isValid = validate(s.toString());
        if (isValid) {
            this.floatingText.setError(null);
            this.floatingText.setErrorEnabled(false);
        } else {
            this.floatingText.setError(this.displayMessage);
            this.floatingText.setErrorEnabled(true);
        }
        isValidToSubmit(isValid, this.floatingText.getId());
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
