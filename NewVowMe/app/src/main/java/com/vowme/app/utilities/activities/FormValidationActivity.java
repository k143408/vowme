package com.vowme.app.utilities.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.view.menu.MenuItemImpl;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.vowme.app.utilities.customWidgets.CustomMultiAutoCompleteTextView;
import com.vowme.app.utilities.validators.CustomMultiLocationFieldValidator;
import com.vowme.app.utilities.validators.FloatingTextLengthValidator;
import com.vowme.app.utilities.validators.FloatingTextRangeYearValidator;
import com.vowme.app.utilities.validators.FloatingTextRegexValidator;
import com.vowme.app.utilities.validators.FloatingTextsCompareValidator;

import java.util.HashMap;

public abstract class FormValidationActivity extends BaseActivity {
    protected Object submitButton;
    protected ValidationListFieldsHelper validationListHelper;

    protected abstract void initFieldsSubmitButton();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.validationListHelper = new ValidationListFieldsHelper();
    }

    protected FloatingTextLengthValidator getFloatingTextLengthValidator(TextInputLayout floatingText, int minLenght, String requiredMessage) {
        this.validationListHelper.addValue(Integer.valueOf(floatingText.getId()), Boolean.valueOf(false));
        return new FloatingTextLengthValidator(floatingText, minLenght, requiredMessage) {
            public void isValidToSubmit(boolean isValid, int id) {
                FormValidationActivity.this.updateIsValidToSubmitForm(isValid, id);
            }
        };
    }

    protected FloatingTextLengthValidator getFloatingTextLengthValidator(TextInputLayout floatingText, int minLenght, int maxLength, String requiredMessage, String errorMessage) {
        this.validationListHelper.addValue(Integer.valueOf(floatingText.getId()), Boolean.valueOf(false));
        return new FloatingTextLengthValidator(floatingText, minLenght, maxLength, requiredMessage, errorMessage) {
            public void isValidToSubmit(boolean isValid, int id) {
                FormValidationActivity.this.updateIsValidToSubmitForm(isValid, id);
            }
        };
    }

    protected FloatingTextRangeYearValidator getFloatingTextRangeYearValidator(TextInputLayout floatingText, int minYear, int maxYear, String requiredMessage, String errorMessage) {
        this.validationListHelper.addValue(Integer.valueOf(floatingText.getId()), Boolean.valueOf(false));
        return new FloatingTextRangeYearValidator(floatingText, minYear, maxYear, requiredMessage, errorMessage) {
            public void isValidToSubmit(boolean isValid, int id) {
                FormValidationActivity.this.updateIsValidToSubmitForm(isValid, id);
            }
        };
    }

    protected TextWatcher getCustomMultiLocationTextView(CustomMultiAutoCompleteTextView locationField, boolean isLoggedSear) {
        this.validationListHelper.addValue(Integer.valueOf(locationField.getId()), Boolean.valueOf(false));
        return new CustomMultiLocationFieldValidator(locationField, isLoggedSear) {
            public void isValidToSubmit(boolean isValid, int id) {
                FormValidationActivity.this.updateIsValidToSubmitForm(isValid, id);
            }
        };
    }

    protected FloatingTextRegexValidator getFloatingTextRegexValidator(TextInputLayout floatingText, String regex, String requiredMessage, String errorMessage) {
        this.validationListHelper.addValue(Integer.valueOf(floatingText.getId()), Boolean.valueOf(false));
        return new FloatingTextRegexValidator(floatingText, regex, requiredMessage, errorMessage) {
            public void isValidToSubmit(boolean isValid, int id) {
                FormValidationActivity.this.updateIsValidToSubmitForm(isValid, id);
            }
        };
    }

    protected FloatingTextsCompareValidator getFloatingTextsCompareValidator(TextInputLayout floatingText, EditText compare, String requiredMessage, String errorMessage) {
        this.validationListHelper.addValue(Integer.valueOf(floatingText.getId()), Boolean.valueOf(false));
        return new FloatingTextsCompareValidator(floatingText, compare, requiredMessage, errorMessage) {
            public void isValidToSubmit(boolean isValid, int id) {
                FormValidationActivity.this.updateIsValidToSubmitForm(isValid, id);
            }
        };
    }

    protected void disableSubmitButton() {
        if (this.submitButton instanceof View) {
            ((View) this.submitButton).setEnabled(false);
            ((View) this.submitButton).setClickable(false);
        } else if (this.submitButton instanceof MenuItem) {
            ((MenuItemImpl) this.submitButton).setEnabled(false);
        }
    }

    protected void enableSubmitButton() {
        if (this.submitButton instanceof View) {
            ((View) this.submitButton).setEnabled(true);
            ((View) this.submitButton).setClickable(true);
        } else if (this.submitButton instanceof MenuItem) {
            ((MenuItemImpl) this.submitButton).setEnabled(true);
        }
    }

    private void updateIsValidToSubmitForm(boolean isValid, int id) {
        this.validationListHelper.addValue(Integer.valueOf(id), Boolean.valueOf(isValid));
        isValidToSubmitForm();
    }

    public void isValidToSubmitForm() {
        if (this.validationListHelper.isAllValuesValid()) {
            enableSubmitButton();
        } else {
            disableSubmitButton();
        }
    }

    protected class ValidationListFieldsHelper {
        private HashMap<Integer, Boolean> listToValidate = new HashMap();

        public void addValue(Integer key, Boolean value) {
            this.listToValidate.put(key, value);
        }

        public void removeValue(Integer key) {
            this.listToValidate.remove(key);
        }

        public boolean isAllValuesValid() {
            if (this.listToValidate.values().contains(Boolean.valueOf(false))) {
                return false;
            }
            return true;
        }
    }
}
