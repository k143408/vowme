package com.vowme.app.utilities.adapters;

import android.content.Context;
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
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.api.ApiWCFRequest;
import com.vowme.app.utilities.helpers.JSONHelper;
import com.vowme.app.utilities.helpers.TypefacesHelper;
import com.vowme.vol.app.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LocationAutoCompleteArrayAdapter extends ArrayAdapter<SearchItem> implements Filterable {
    List<SearchItem> mItemValues = new ArrayList();
    private String apiViktorClientSecret;
    private String apiViktorGetClientSecret;
    private String apiViktorURL;
    private String apiVolunteerURL;
    private String apliAccessToken;
    private boolean isLoggedinSearh;
    private LayoutInflater mInflater = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    private List<String> recents = new ArrayList();
    private List<String> volunteerLocation;

    public LocationAutoCompleteArrayAdapter(Context context, String apiViktorURL, String apiViktorClientSecret, String apiViktorGetClientSecret, String apiVolunteerURL, String apliAccessToken, List<String> volunteerLocation, boolean isLoggedinSearh) {
        super(context, -1);
        this.isLoggedinSearh = isLoggedinSearh;
        this.apiViktorURL = apiViktorURL;
        this.apiViktorClientSecret = apiViktorClientSecret;
        this.apiViktorGetClientSecret = apiViktorGetClientSecret;
        this.apiVolunteerURL = apiVolunteerURL;
        this.apliAccessToken = apliAccessToken;
        this.volunteerLocation = volunteerLocation;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout tv = (RelativeLayout) this.mInflater.inflate(R.layout.search_autocomplete_default, parent, false);
        TextView itemText = (TextView) tv.findViewById(R.id.search_autocomplete_title);
        TextView icon = (TextView) tv.findViewById(R.id.search_autocomplete_icon);
        icon.setTypeface(TypefacesHelper.get(getContext(), "fonts/material_icon_font.ttf"));
        SearchItem item = (SearchItem) getItem(position);
        itemText.setText(item.getName());
        if (item.isFromRecents()) {
            icon.setText(getContext().getResources().getString(R.string.ic_access_time));
        } else {
            icon.setText(getContext().getResources().getString(R.string.ic_search));
        }
        return tv;
    }

    public Filter getFilter() {
        return new C08941();
    }

    private void addLocationsUnAuthorized(String results) throws JSONException {
        JSONArray locations = JSONHelper.getJSONArrayFromString(results);
        for (int i = 0; i < locations.length(); i++) {
            this.mItemValues.add(new SearchItem(AutoCompleteCategorie.KEYWORD, locations.getJSONObject(i).getString("name"), false));
        }
    }

    private void addLocationsAuthorized(String results) throws JSONException {
        JSONArray locations = JSONHelper.getJSONArrayFromString(results);
        for (int i = 0; i < locations.length(); i++) {
            this.mItemValues.add(new SearchItem(AutoCompleteCategorie.KEYWORD, locations.getString(i), false));
        }
    }

    public void setRecents(List<String> recents) {
        this.recents = recents;
    }

    public void setVolunteerLocation(List<String> volunteerLocation) {
        this.volunteerLocation = volunteerLocation;
    }

    class C08941 extends Filter {
        C08941() {
        }

        protected FilterResults performFiltering(CharSequence constraint) {
            Exception e;
            FilterResults filterResults = new FilterResults();
            LocationAutoCompleteArrayAdapter.this.mItemValues.clear();
            if (!(constraint == null || constraint.length() == 0)) {
                try {
                    if (LocationAutoCompleteArrayAdapter.this.isLoggedinSearh) {
                        LocationAutoCompleteArrayAdapter.this.addLocationsAuthorized((String) new GetLocationsAuthorized(constraint).execute(new Void[0]).get());
                    } else {
                        LocationAutoCompleteArrayAdapter.this.addLocationsUnAuthorized((String) new GetLocationsUnAuthorized(constraint).execute(new Void[0]).get());
                    }
                } catch (InterruptedException e2) {
                    e = e2;
                    e.printStackTrace();
                    for (String recent : LocationAutoCompleteArrayAdapter.this.recents) {
                        LocationAutoCompleteArrayAdapter.this.mItemValues.add(new SearchItem(AutoCompleteCategorie.KEYWORD, recent, 0, false, true));
                    }
                    filterResults.values = new ArrayList(LocationAutoCompleteArrayAdapter.this.mItemValues);
                    filterResults.count = LocationAutoCompleteArrayAdapter.this.mItemValues.size();
                    return filterResults;
                } catch (ExecutionException e3) {
                    e = e3;
                    e.printStackTrace();
                    while (recents.iterator().hasNext()) {
                        String recent = (String) recents.iterator().next();
                        LocationAutoCompleteArrayAdapter.this.mItemValues.add(new SearchItem(AutoCompleteCategorie.KEYWORD, recent, 0, false, true));
                    }
                    filterResults.values = new ArrayList(LocationAutoCompleteArrayAdapter.this.mItemValues);
                    filterResults.count = LocationAutoCompleteArrayAdapter.this.mItemValues.size();
                    return filterResults;
                } catch (JSONException e4) {
                    e = e4;
                    e.printStackTrace();
                    while (recents.iterator().hasNext()) {
                        String recent = (String) recents.iterator().next();
                        LocationAutoCompleteArrayAdapter.this.mItemValues.add(new SearchItem(AutoCompleteCategorie.KEYWORD, recent, 0, false, true));
                    }
                    filterResults.values = new ArrayList(LocationAutoCompleteArrayAdapter.this.mItemValues);
                    filterResults.count = LocationAutoCompleteArrayAdapter.this.mItemValues.size();
                    return filterResults;
                }
            }
            while (recents.iterator().hasNext()) {
                String recent = (String) recents.iterator().next();
                LocationAutoCompleteArrayAdapter.this.mItemValues.add(new SearchItem(AutoCompleteCategorie.KEYWORD, recent, 0, false, true));
            }
            filterResults.values = new ArrayList(LocationAutoCompleteArrayAdapter.this.mItemValues);
            filterResults.count = LocationAutoCompleteArrayAdapter.this.mItemValues.size();
            return filterResults;
        }

        public void publishResults(CharSequence contraint, FilterResults results) {
            LocationAutoCompleteArrayAdapter.this.clear();
            for (SearchItem item : (List<SearchItem>) results.values) {
                if (!LocationAutoCompleteArrayAdapter.this.volunteerLocation.contains(item.getName())) {
                    LocationAutoCompleteArrayAdapter.this.add(item);
                }
            }
            if (results.count > 0) {
                LocationAutoCompleteArrayAdapter.this.notifyDataSetChanged();
            } else {
                LocationAutoCompleteArrayAdapter.this.notifyDataSetInvalidated();
            }
        }

        public CharSequence convertResultToString(Object resultValue) {
            return resultValue == null ? "" : ((SearchItem) resultValue).getName();
        }
    }

    private class GetLocationsAuthorized extends ApiRestFullRequest {
        public GetLocationsAuthorized(CharSequence name) {
            super(HttpRequestType.GET, LocationAutoCompleteArrayAdapter.this.apiVolunteerURL, "api/location/" + name + "/postcodes", LocationAutoCompleteArrayAdapter.this.apliAccessToken);
        }
    }

    private class GetLocationsUnAuthorized extends ApiWCFRequest {
        public GetLocationsUnAuthorized(CharSequence name) {
            super(HttpRequestType.GET, LocationAutoCompleteArrayAdapter.this.apiViktorURL, "api/lookup/location" +"?name=" + name);
        }
    }
}
