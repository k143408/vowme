package com.vowme.vol.app.activities.start;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vowme.app.models.Enum.LookupType;
import com.vowme.app.utilities.adapters.DefaultDataRecyclerViewAdapter;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;

import java.util.ArrayList;
import java.util.List;

public class StartInterestsFragment extends Fragment {
    private DefaultDataRecyclerViewAdapter adapter;
    private List<Integer> checked;
    private List<?> items;
    private RecyclerView recyclerView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_start_interests, container, false);
        this.recyclerView = (RecyclerView) result.findViewById(R.id.interests_list);
        this.checked = new ArrayList();
        this.items = DefaultDataHelper.getInterests();
        this.adapter = new DefaultDataRecyclerViewAdapter(this.items, null, this.checked, true, LookupType.INTERESTS, getContext());
        this.adapter.notifyDataSetChanged();
        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.recyclerView.setAdapter(this.adapter);
        return result;
    }

    public List<Integer> getChecked() {
        return this.checked;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onDetach() {
        super.onDetach();
    }
}
