package com.vowme.vol.app.activities.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.AutoCompleteCategorie;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.SearchItem;
import com.vowme.app.utilities.adapters.SearchAutoCompleteArrayAdapter;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.fragments.BaseFragment;
import com.vowme.app.utilities.helpers.JSONHelper;
import com.vowme.vol.app.R;

import java.util.concurrent.ExecutionException;

public class SearchFragment extends BaseFragment {
    protected SearchView searchView;
    private boolean isClearIconVisible;
    private SearchAutoCompleteArrayAdapter mAdapter;
    private ImageView searchClear;
    private ImageView searchIcon;
    private AutoCompleteTextView searchText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.searchView = (SearchView) ((RelativeLayout) MenuItemCompat.getActionView(menu.findItem(R.id.action_search))).findViewById(R.id.search_edit_frame);
        setupSearchView(this.searchView);
        this.searchText = (AutoCompleteTextView) this.searchView.findViewById(R.id.search_src_text);
        setUpSearchText();
        this.searchClear = (ImageView) this.searchView.findViewById(R.id.search_close_btn);
        setUpSearchClear();
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.setBackgroundColor(-1);
        searchView.clearFocus();
        this.searchIcon = (ImageView) searchView.findViewById(R.id.search_mag_icon);
        this.searchIcon.setVisibility(8);
        this.searchIcon.setImageDrawable(null);
    }

    private void setUpSearchText() {
        Exception e;
        this.searchText.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_light));
        this.searchText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.text_light));
        this.searchText.setHint("Search");
        this.searchText.setThreshold(0);
        this.mAdapter = new SearchAutoCompleteArrayAdapter(getContext(), getString(R.string.apiViktorURL), getResources().getString(R.string.apiViktorClientSecret), getResources().getString(R.string.apiViktorGetClientSecret));
        try {
            this.mAdapter.setCauses(JSONHelper.getJSONArrayFromString((String) new GetOrganisationServiceFocuses().execute(new Void[0]).get()));
            this.mAdapter.setInterests(JSONHelper.getJSONArrayFromString((String) new GetInterests().execute(new Void[0]).get()));
        } catch (InterruptedException e2) {
            e = e2;
            e.printStackTrace();
            this.searchText.setAdapter(this.mAdapter);
            this.searchText.addTextChangedListener(new C08361());
            this.searchText.setOnItemClickListener(new C08372());
            this.searchText.setOnEditorActionListener(new C08383());
            this.searchText.setOnFocusChangeListener(new C08404());
        } catch (ExecutionException e3) {
            e = e3;
            e.printStackTrace();
            this.searchText.setAdapter(this.mAdapter);
            this.searchText.addTextChangedListener(new C08361());
            this.searchText.setOnItemClickListener(new C08372());
            this.searchText.setOnEditorActionListener(new C08383());
            this.searchText.setOnFocusChangeListener(new C08404());
        }
        this.searchText.setAdapter(this.mAdapter);
        this.searchText.addTextChangedListener(new C08361());
        this.searchText.setOnItemClickListener(new C08372());
        this.searchText.setOnEditorActionListener(new C08383());
        this.searchText.setOnFocusChangeListener(new C08404());
    }

    private void setUpSearchClear() {
        this.searchClear.setImageResource(R.drawable.ic_bookmark_24dp);
        this.searchClear.setVisibility(0);
        this.isClearIconVisible = false;
        this.searchClear.setOnClickListener(new C08415());
    }

    class C08361 implements TextWatcher {
        C08361() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                SearchFragment.this.searchClear.setVisibility(0);
                SearchFragment.this.searchClear.setImageResource(R.drawable.ic_clear_24dp);
                SearchFragment.this.isClearIconVisible = true;
                return;
            }
            if (SearchFragment.this.searchText.hasFocus()) {
                SearchFragment.this.isClearIconVisible = false;
            }
            SearchFragment.this.setUpSearchClear();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C08372 implements OnItemClickListener {
        C08372() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SearchItem item = (SearchItem) parent.getItemAtPosition(position);
            if (!item.isCategory()) {
                SearchFragment.this.searchText.getText().clear();
                SearchFragment.this.searchText.append(item.getName());
                SearchFragment.this.searchText.setTag(R.string.EXTRA_TYPE_KEYWORD, Integer.valueOf(item.getCategoryType().getValue()));
                SearchFragment.this.searchText.setTag(R.string.EXTRA_ID_KEYWORD, Integer.valueOf(item.getId()));
            }
        }
    }

    class C08383 implements OnEditorActionListener {
        C08383() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3 || v.getEditableText().length() <= 0) {
                return false;
            }
            Intent searchResult = new Intent(SearchFragment.this.getActivity(), SearchResultActivity.class);
            searchResult.putExtra(SearchFragment.this.getResources().getString(R.string.EXTRA_IS_RADIUS_SEARCH), false);
            searchResult.putExtra(SearchFragment.this.getResources().getString(R.string.EXTRA_KEYWORD), v.getEditableText().toString());
            searchResult.putExtra(SearchFragment.this.getResources().getString(R.string.EXTRA_TYPE_KEYWORD), (Integer) v.getTag(R.string.EXTRA_TYPE_KEYWORD));
            searchResult.putExtra(SearchFragment.this.getResources().getString(R.string.EXTRA_ID_KEYWORD), (Integer) v.getTag(R.string.EXTRA_ID_KEYWORD));
            SearchFragment.this.searchText.setTag(R.string.EXTRA_TYPE_KEYWORD, Integer.valueOf(AutoCompleteCategorie.KEYWORD.getValue()));
            SearchFragment.this.searchText.setTag(R.string.EXTRA_ID_KEYWORD, Integer.valueOf(0));
            SearchFragment.this.startActivityForResult(searchResult, ActivityCode.RESULTFROMSEARCH.getValue());
            ((InputMethodManager) SearchFragment.this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
    }

    class C08404 implements OnFocusChangeListener {

        C08404() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                SearchFragment.this.searchIcon.setImageResource(R.drawable.ic_arrow_back_24dp);
                SearchFragment.this.searchIcon.setOnClickListener(new C08391());
                SearchFragment.this.mAdapter.setRecents(SearchFragment.this.getBaseActivity().getRecentsSearchKeyword());
                return;
            }
            SearchFragment.this.searchIcon.setVisibility(8);
            SearchFragment.this.searchIcon.setImageDrawable(null);
        }

        class C08391 implements OnClickListener {
            C08391() {
            }

            public void onClick(View v) {
                SearchFragment.this.searchText.getText().clear();
                ((InputMethodManager) SearchFragment.this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
                SearchFragment.this.searchText.clearFocus();
                SearchFragment.this.setUpSearchClear();
            }
        }
    }

    class C08415 implements OnClickListener {
        C08415() {
        }

        public void onClick(View v) {
            if (SearchFragment.this.isClearIconVisible) {
                SearchFragment.this.searchText.getText().clear();
                return;
            }
            SearchFragment.this.startActivity(new Intent(SearchFragment.this.getBaseActivity(), SavedSearchActivity.class));
        }
    }

    private class GetInterests extends ApiWCFRequest {
        public GetInterests() {
            super(HttpRequestType.GET, SearchFragment.this.getString(R.string.apiViktorURL), "Interests/" + SearchFragment.this.getResources().getString(R.string.apiViktorClientSecret) + "/" + SearchFragment.this.getResources().getString(R.string.apiViktorGetClientSecret));
        }
    }

    private class GetOrganisationServiceFocuses extends ApiWCFRequest {
        public GetOrganisationServiceFocuses() {
            super(HttpRequestType.GET, SearchFragment.this.getString(R.string.apiViktorURL), "OrganisationServiceFocuses/" + SearchFragment.this.getResources().getString(R.string.apiViktorClientSecret) + "/" + SearchFragment.this.getResources().getString(R.string.apiViktorGetClientSecret));
        }
    }
}
