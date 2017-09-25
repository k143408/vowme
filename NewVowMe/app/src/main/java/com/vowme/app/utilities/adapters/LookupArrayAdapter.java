package com.vowme.app.utilities.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vowme.app.models.lookUp.Lookup;

import java.util.List;

public class LookupArrayAdapter extends ArrayAdapter<Lookup> {
    private List<Lookup> mValues;

    public LookupArrayAdapter(Context context, int resource, List<Lookup> mItemValues) {
        super(context, resource);
        this.mValues = mItemValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View result = super.getView(position, convertView, parent);
        ((TextView) result.findViewById(16908308)).setText(getItem(position).getName());
        return result;
    }

    public int getCount() {
        return this.mValues.size();
    }

    public Lookup getItem(int position) {
        return (Lookup) this.mValues.get(position);
    }
}
