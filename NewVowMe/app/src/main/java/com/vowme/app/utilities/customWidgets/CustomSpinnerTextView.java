package com.vowme.app.utilities.customWidgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;

import com.vowme.app.models.lookUp.Lookup;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerTextView extends AutoCompleteTextView {

    public CustomSpinnerTextView(Context context) {
        super(context);
    }

    public CustomSpinnerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinnerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUpSpinnerTextView(int resource, List<?> mItemValues) {
        setAdapter(new CustomSpinnerpinnerArrayAdapter(getContext(), resource, mItemValues));
        setInputType(0);
        setOnFocusChangeListener(new ChangeListener());
        setOnClickListener(new ClickListener());
        setOnItemClickListener(new ItemClickListener());
    }

    public boolean onCheckIsTextEditor() {
        return false;
    }

    public boolean enoughToFilter() {
        return true;
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= -1073741825;
        return conn;
    }

    class ChangeListener implements OnFocusChangeListener {
        ChangeListener() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ((CustomSpinnerTextView) v).getFilter().filter("");
                ((CustomSpinnerTextView) v).showDropDown();
            }
            ((InputMethodManager) CustomSpinnerTextView.this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    class ClickListener implements OnClickListener {
        ClickListener() {
        }

        public void onClick(View v) {
            ((InputMethodManager) CustomSpinnerTextView.this.getContext().getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
            ((CustomSpinnerTextView) v).getFilter().filter("");
            ((CustomSpinnerTextView) v).showDropDown();
        }
    }

    class ItemClickListener implements OnItemClickListener {
        ItemClickListener() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String item = (String) parent.getItemAtPosition(position);
            CustomSpinnerTextView.this.getText().clear();
            CustomSpinnerTextView.this.append(item);
        }
    }

    private class CustomSpinnerpinnerArrayAdapter extends ArrayAdapter<String> implements Filterable {
        private List<?> mItemValues;

        public CustomSpinnerpinnerArrayAdapter(Context context, int resource, List<?> mItemValues) {
            super(context, resource);
            this.mItemValues = mItemValues;
        }

        public Filter getFilter() {
            return new C09121();
        }

        class C09121 extends Filter {
            C09121() {
            }

            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = new ArrayList(CustomSpinnerpinnerArrayAdapter.this.mItemValues);
                filterResults.count = CustomSpinnerpinnerArrayAdapter.this.mItemValues.size();
                return filterResults;
            }

            public void publishResults(CharSequence contraint, FilterResults results) {
                CustomSpinnerpinnerArrayAdapter.this.clear();
                for (Object item : (List) results.values) {
                    if (item instanceof String) {
                        CustomSpinnerpinnerArrayAdapter.this.add((String) item);
                    } else {
                        CustomSpinnerpinnerArrayAdapter.this.add(((Lookup) item).getName());
                    }
                }
                if (results.count > 0) {
                    CustomSpinnerpinnerArrayAdapter.this.notifyDataSetChanged();
                } else {
                    CustomSpinnerpinnerArrayAdapter.this.notifyDataSetInvalidated();
                }
            }

            public CharSequence convertResultToString(Object resultValue) {
                return resultValue == null ? "" : (String) resultValue;
            }
        }
    }
}
