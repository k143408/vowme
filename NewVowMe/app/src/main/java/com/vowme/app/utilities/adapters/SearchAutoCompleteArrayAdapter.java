package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vowme.app.models.Enum.AutoCompleteCategorie;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.SearchItem;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.helpers.JSONHelper;
import com.vowme.app.utilities.helpers.TypefacesHelper;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchAutoCompleteArrayAdapter extends ArrayAdapter<SearchItem> implements Filterable {
    List<SearchItem> mItemValues = new ArrayList();
    private String apiViktorClientSecret;
    private String apiViktorGetClientSecret;
    private String apiViktorURL;
    private String autoComplete;
    private JSONArray causes;
    private JSONArray interests;
    private boolean isDefaultValues = true;
    private LayoutInflater mInflater = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    private List<String> recents;

    public SearchAutoCompleteArrayAdapter(Context context, String apiViktorURL, String apiViktorClientSecret, String apiViktorGetClientSecret) {
        super(context, -1);
        this.apiViktorURL = apiViktorURL;
        this.apiViktorClientSecret = apiViktorClientSecret;
        this.apiViktorGetClientSecret = apiViktorGetClientSecret;
        this.recents = new ArrayList();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View tv = null;
        SearchItem item = (SearchItem) getItem(position);
        if (item.isCategory()) {
            tv = (TextView) this.mInflater.inflate(R.layout.search_autocomplete_subtitle, parent, false);
        } else if (this.isDefaultValues) {
            RelativeLayout tv2 = (RelativeLayout) this.mInflater.inflate(R.layout.search_autocomplete_default, parent, false);
        } else {
            TextView tv3 = (TextView) this.mInflater.inflate(R.layout.search_autocomplete_suggestion, parent, false);
        }
        if (item.isCategory()) {
            if (this.isDefaultValues) {
                ((TextView) tv).setText("Top " + item.getName().substring(0, 1).toUpperCase() + item.getName().toLowerCase().substring(1));
            } else {
                ((TextView) tv).setText(item.getName().substring(0, 1).toUpperCase() + item.getName().toLowerCase().substring(1));
            }
        } else if (this.isDefaultValues) {
            ((TextView) tv.findViewById(R.id.search_autocomplete_title)).setText(!TextUtils.isEmpty(item.getName()) ? item.getName() : "");
            TextView icon = (TextView) tv.findViewById(R.id.search_autocomplete_icon);
            icon.setTypeface(TypefacesHelper.get(getContext(), "fonts/material_icon_font.ttf"));
            if (item.isFromRecents()) {
                icon.setText(getContext().getResources().getString(R.string.ic_access_time));
            } else {
                icon.setText(getContext().getResources().getString(R.string.ic_local_offer));
            }
        } else {
            ((TextView) tv).setText(item.getName());
        }
        return tv;
    }

    public boolean isEnabled(int position) {
        return !((SearchItem) getItem(position)).isCategory();
    }

    public Filter getFilter() {
        return new C09011();
    }

    public void setRecents(List<String> recents) {
        this.recents = recents;
    }

    public void setInterests(JSONArray interests) {
        this.interests = interests;
    }

    public void setCauses(JSONArray causes) {
        this.causes = causes;
    }

    private void addOrganisations(String results) throws JSONException {
        JSONArray organisations = JSONHelper.getJSONArrayFromString(results);
        int i = 0;
        if (organisations.length() > 0) {
            this.mItemValues.add(new SearchItem(AutoCompleteCategorie.ORGANISATIONS, AutoCompleteCategorie.ORGANISATIONS.name(), true));
        }
        while (i < organisations.length()) {
            JSONObject orga = organisations.getJSONObject(i);
            this.mItemValues.add(new SearchItem(AutoCompleteCategorie.ORGANISATIONS, orga.getString("Keyword"), orga.getInt("Id"), false));
            i++;
        }
    }

    private void addTopValues(JSONArray values, AutoCompleteCategorie categorie) throws JSONException {
        List<JSONObject> topCount = getTopCount(values);
        if (topCount.size() > 0) {
            this.mItemValues.add(new SearchItem(categorie, categorie.name(), true));
        }
        for (int i = 0; i < topCount.size(); i++) {
            JSONObject obj = (JSONObject) topCount.get(i);
            this.mItemValues.add(new SearchItem(categorie, obj.getString("Name"), obj.getInt("Id"), false));
        }
    }

    private List<JSONObject> getTopCount(JSONArray list) throws JSONException {
        List<JSONObject> result = new ArrayList();
        for (int i = 1; i < list.length(); i++) {
            result.add(list.getJSONObject(i));
        }
        Collections.sort(result, new JSONTopCountComparator());
        return result.subList(0, 5);
    }

    private void addFilteredValues(JSONArray values, AutoCompleteCategorie categorie, CharSequence constraint) throws JSONException {
        List<JSONObject> topCount = getFilteredJSON(values, constraint);
        if (topCount.size() > 0) {
            this.mItemValues.add(new SearchItem(categorie, categorie.name(), true));
        }
        for (int i = 0; i < topCount.size(); i++) {
            JSONObject obj = (JSONObject) topCount.get(i);
            this.mItemValues.add(new SearchItem(categorie, obj.getString("Name"), obj.getInt("Id"), false));
        }
    }

    private List<JSONObject> getFilteredJSON(JSONArray list, CharSequence constraint) throws JSONException {
        List<JSONObject> result = new ArrayList();
        for (int i = 1; i < list.length(); i++) {
            JSONObject obj = list.getJSONObject(i);
            if (obj.getString("Name").toLowerCase().contains(constraint.toString().toLowerCase())) {
                result.add(obj);
            }
        }
        return result;
    }

    class C09011 extends Filter {
        C09011() {
        }

        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            SearchAutoCompleteArrayAdapter.this.mItemValues.clear();
            SearchAutoCompleteArrayAdapter.this.autoComplete = (String) constraint;
            if (constraint == null || constraint.length() == 0) {
                SearchAutoCompleteArrayAdapter.this.isDefaultValues = true;
                for (String recent : SearchAutoCompleteArrayAdapter.this.recents) {
                    if (recent.contains("searchAutoCompleteItem")) {
                        try {
                            JSONObject item = new JSONObject(new JSONObject(recent).getString("searchAutoCompleteItem"));
                            SearchAutoCompleteArrayAdapter.this.mItemValues.add(new SearchItem(AutoCompleteCategorie.valueOf(item.getString("categoryType")), item.getString("name"), item.getInt("id"), false, true));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        SearchAutoCompleteArrayAdapter.this.mItemValues.add(new SearchItem(AutoCompleteCategorie.KEYWORD, recent, 0, false, true));
                    }
                }
                try {
                    SearchAutoCompleteArrayAdapter.this.addTopValues(SearchAutoCompleteArrayAdapter.this.causes, AutoCompleteCategorie.CAUSES);
                    SearchAutoCompleteArrayAdapter.this.addTopValues(SearchAutoCompleteArrayAdapter.this.interests, AutoCompleteCategorie.INTERESTS);
                } catch (JSONException e2) {
                }
            } else {
                SearchAutoCompleteArrayAdapter.this.isDefaultValues = false;
                try {
                    SearchAutoCompleteArrayAdapter.this.addFilteredValues(SearchAutoCompleteArrayAdapter.this.causes, AutoCompleteCategorie.CAUSES, constraint);
                    SearchAutoCompleteArrayAdapter.this.addFilteredValues(SearchAutoCompleteArrayAdapter.this.interests, AutoCompleteCategorie.INTERESTS, constraint);
                    String result = (String) new GetOrganisationByKeyWord(((String) constraint).replace(" ", "")).execute(new Void[0]).get();
                    if (!TextUtils.isEmpty(result)) {
                        SearchAutoCompleteArrayAdapter.this.addOrganisations(result);
                    }
                } catch (InterruptedException e3) {
                } catch (ExecutionException e4) {
                } catch (JSONException e5) {
                }
            }
            filterResults.values = new ArrayList(SearchAutoCompleteArrayAdapter.this.mItemValues);
            filterResults.count = SearchAutoCompleteArrayAdapter.this.mItemValues.size();
            return filterResults;
        }

        public void publishResults(CharSequence contraint, FilterResults results) {
            SearchAutoCompleteArrayAdapter.this.clear();
            for (SearchItem item : (List<SearchItem>) results.values) {
                SearchAutoCompleteArrayAdapter.this.add(item);
            }
            if (results.count > 0) {
                SearchAutoCompleteArrayAdapter.this.notifyDataSetChanged();
            } else {
                SearchAutoCompleteArrayAdapter.this.notifyDataSetInvalidated();
            }
        }

        public CharSequence convertResultToString(Object resultValue) {
            return resultValue == null ? "" : ((SearchItem) resultValue).getName();
        }
    }

    private class GetOrganisationByKeyWord extends ApiWCFRequest {
        public GetOrganisationByKeyWord(CharSequence keyword) {
            super(HttpRequestType.GET, SearchAutoCompleteArrayAdapter.this.apiViktorURL, "OpportunityOrganisationKeywords/" + SearchAutoCompleteArrayAdapter.this.apiViktorClientSecret + "/" + SearchAutoCompleteArrayAdapter.this.apiViktorGetClientSecret + "?Keywords=" + keyword);
        }
    }

    class JSONTopCountComparator implements Comparator<JSONObject> {
        JSONTopCountComparator() {
        }

        public int compare(JSONObject a, JSONObject b) {
            int valA = 0;
            int valB = 0;
            try {
                valA = a.getInt("Count");
                valB = b.getInt("Count");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (valA > valB) {
                return -1;
            }
            if (valA < valB) {
                return 1;
            }
            return 0;
        }
    }
}
