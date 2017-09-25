package com.vowme.vol.app.activities.start;

import android.content.Context;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.utilities.customWidgets.CustomMultiAutoCompleteTextView;
import com.vowme.app.utilities.fragments.BaseFragment;
import com.vowme.app.utilities.validators.CustomMultiLocationFieldValidator;
import com.vowme.vol.app.R;

import java.util.ArrayList;

public class StartLocationFragment extends BaseFragment {
    private MenuItem menuItemDone;
    private CustomMultiAutoCompleteTextView searchText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_start_location, container, false);
        this.searchText = (CustomMultiAutoCompleteTextView) result.findViewById(R.id.search_edit_frame_location);
        this.searchText.setUpSearchlocationText(getBaseActivity(), getString(R.string.apiViktorURL), getResources().getString(R.string.apiViktorClientSecret), getResources().getString(R.string.apiViktorGetClientSecret), getString(R.string.apiVolunteerURL), getBaseActivity().getApliAccessToken(), new ArrayList(), false);
        this.searchText.addTextChangedListener(getCustomMultiLocationTextView(this.searchText, false));
        return result;
    }

    protected TextWatcher getCustomMultiLocationTextView(CustomMultiAutoCompleteTextView locationField, boolean isLoggedSear) {
        return new CustomMultiLocationFieldValidator(locationField, isLoggedSear) {
            public void isValidToSubmit(boolean isValid, int id) {
                StartLocationFragment.this.menuItemDone.setEnabled(isValid);
            }
        };
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onDetach() {
        super.onDetach();
    }

    public void setUpMenuItemDone(MenuItem menuItemDone) {
        this.menuItemDone = menuItemDone;
        if (this.searchText.getText().length() > 0) {
            menuItemDone.setEnabled(true);
        } else {
            menuItemDone.setEnabled(false);
        }
    }

    public String getLocation() {
        return this.searchText.getText().toString().trim();
    }
}
