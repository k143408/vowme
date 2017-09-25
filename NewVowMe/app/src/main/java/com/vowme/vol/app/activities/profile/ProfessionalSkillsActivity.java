package com.vowme.vol.app.activities.profile;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vowme.app.models.Enum.ActivityCode;
import com.vowme.app.models.Enum.HttpRequestType;
import com.vowme.app.models.api.VolunteerSkillsModel;
import com.vowme.app.models.lookUp.Lookup;
import com.vowme.app.utilities.activities.ProfileFormActivity;
import com.vowme.app.utilities.adapters.LookupArrayAdapter;
import com.vowme.app.utilities.api.ApiRestFullRequest;
import com.vowme.app.utilities.customWidgets.DividerItemDecoration;
import com.vowme.app.utilities.helpers.DefaultDataHelper;
import com.vowme.vol.app.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfessionalSkillsActivity extends ProfileFormActivity {
    private Builder dialog;
    private LookupArrayAdapter proAdapter;
    private RecyclerView proSkillListView;
    private List<Lookup> proSkills;
    private VolProSkillArrayAdapter volProAdapter;
    private List<Lookup> volProSkills;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.proSkills = new ArrayList();
        this.volProSkills = new ArrayList();
        List<Integer> proSkillIds = new ArrayList();
        for (VolunteerSkillsModel subSkill : this.volModel.skills) {
            if (!proSkillIds.contains(subSkill.classificationId)) {
                proSkillIds.add(subSkill.classificationId);
            }
        }
        for (Lookup lookup : DefaultDataHelper.getProSkills()) {
            if (proSkillIds.contains(Integer.valueOf(lookup.getId()))) {
                this.volProSkills.add(lookup);
            } else {
                this.proSkills.add(lookup);
            }
        }
        this.proSkillListView = (RecyclerView) findViewById(R.id.pro_skill_list);
        this.proSkillListView.addItemDecoration(new DividerItemDecoration(this, 1));
        this.proSkillListView.setLayoutManager(new LinearLayoutManager(this));
        this.volProAdapter = new VolProSkillArrayAdapter(this.volProSkills);
        this.proSkillListView.setAdapter(this.volProAdapter);
        new ItemTouchHelper(new ProSkillTouchHelper(this.volProAdapter)).attachToRecyclerView(this.proSkillListView);
        this.volProAdapter.notifyDataSetChanged();
    }

    protected void initFields() {
    }

    public void addSkill(View view) {
        this.proAdapter = new LookupArrayAdapter(this, 17367043, this.proSkills);
        this.proAdapter.notifyDataSetChanged();
        this.dialog = new Builder(this).setCancelable(true).setNegativeButton((CharSequence) "Cancel", new C08262()).setAdapter(this.proAdapter, new C08251());
        this.dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            String profileDetails = data.getStringExtra(getResources().getString(R.string.EXTRA_MODEL_DETAILS));
            if (!TextUtils.isEmpty(profileDetails)) {
                this.modelAsString = profileDetails;
                createModel();
            }
        }
    }

    protected void OnSaveOptionsItemSelected() {
        HashMap<String, String> result = new HashMap();
        String key = "";
        String value = "";
        for (VolunteerSkillsModel model : this.volModel.skills) {
            key = key + model.subClassificationId.toString() + ",";
            value = value + model.experienceId.toString() + ",";
        }
        result.put(key.substring(0, key.length() - 1), value.substring(0, value.length() - 1));
        new putVolunteerSkills(new JSONObject(result)).execute(new Void[0]);
    }

    @LayoutRes
    protected int getLayoutResID() {
        return R.layout.activity_professional_skills;
    }

    class C08251 implements OnClickListener {
        C08251() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Lookup item = ProfessionalSkillsActivity.this.proAdapter.getItem(which);
            ProfessionalSkillsActivity.this.volProAdapter.mValues.add(item);
            ProfessionalSkillsActivity.this.proSkills.remove(item);
            ProfessionalSkillsActivity.this.proAdapter.notifyDataSetChanged();
            ProfessionalSkillsActivity.this.volProAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }

    class C08262 implements OnClickListener {
        C08262() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    private class ProSkillTouchHelper extends SimpleCallback {
        private VolProSkillArrayAdapter mAdapter;

        public ProSkillTouchHelper(VolProSkillArrayAdapter mAdapter) {
            super(0, 8);
            this.mAdapter = mAdapter;
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            return false;
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            if (viewHolder instanceof VolProSkillArrayAdapter.RemoveableViewHolder) {
                return Callback.makeMovementFlags(0, 48);
            }
            return 0;
        }

        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            Callback.getDefaultUIUtil().clearView(((VolProSkillArrayAdapter.RemoveableViewHolder) viewHolder).getSwipableView());
        }

        public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
            if (viewHolder != null) {
                Callback.getDefaultUIUtil().onSelected(((VolProSkillArrayAdapter.RemoveableViewHolder) viewHolder).getSwipableView());
            }
        }

        public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Callback.getDefaultUIUtil().onDraw(c, recyclerView, ((VolProSkillArrayAdapter.RemoveableViewHolder) viewHolder).getSwipableView(), dX, dY, actionState, isCurrentlyActive);
        }

        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            Callback.getDefaultUIUtil().onDrawOver(c, recyclerView, ((VolProSkillArrayAdapter.RemoveableViewHolder) viewHolder).getSwipableView(), dX, dY, actionState, isCurrentlyActive);
        }

        public void onSwiped(ViewHolder viewHolder, int direction) {
            if (ProfessionalSkillsActivity.this.volProAdapter.mValues.size() != 0 && (viewHolder instanceof VolProSkillArrayAdapter.RemoveableViewHolder)) {
                ProfessionalSkillsActivity.this.volProAdapter.mValues.remove(((VolProSkillArrayAdapter.RemoveableViewHolder) viewHolder).mItem);
                ProfessionalSkillsActivity.this.volProAdapter.notifyDataSetChanged();
            }
        }
    }

    private class VolProSkillArrayAdapter extends Adapter<ViewHolder> {
        private List<Lookup> mValues;

        public VolProSkillArrayAdapter(List<Lookup> mItemValues) {
            this.mValues = mItemValues;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RemoveableViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_removeable, parent, false));
        }

        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (holder instanceof RemoveableViewHolder) {
                RemoveableViewHolder vh = (RemoveableViewHolder) holder;
                vh.mItem = (Lookup) this.mValues.get(position);
                vh.itemText.setText(vh.mItem.getName());
                vh.layout.setClickable(true);
                vh.layout.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(ProfessionalSkillsActivity.this, ProfessionalSkillsEditActivity.class);
                        if (intent != null) {
                            intent.putExtra(ProfessionalSkillsActivity.this.getResources().getString(R.string.EXTRA_MODEL_DETAILS), ProfessionalSkillsActivity.this.volModel.toJsonObject().toString());
                            intent.putExtra(ProfessionalSkillsActivity.this.getResources().getString(R.string.EXTRA_PRO_SKILL_TITLE), ((RemoveableViewHolder) holder).mItem.getName());
                            intent.putExtra(ProfessionalSkillsActivity.this.getResources().getString(R.string.EXTRA_PRO_SKILL_ID), ((RemoveableViewHolder) holder).mItem.getId());
                            ProfessionalSkillsActivity.this.startActivityForResult(intent, ActivityCode.PROFILEEDIT.getValue());
                        }
                    }
                });
            }
        }

        public int getItemCount() {
            return this.mValues.size();
        }

        public class RemoveableViewHolder extends ViewHolder {
            public final TextView itemText;
            public final FrameLayout layout;
            public final FrameLayout mRemoveableView;
            public Lookup mItem;

            public RemoveableViewHolder(View view) {
                super(view);
                this.layout = (FrameLayout) view.findViewById(R.id.item_removeable_lyt);
                this.itemText = (TextView) view.findViewById(R.id.removeable_name);
                this.mRemoveableView = (FrameLayout) view.findViewById(R.id.removeable_layout);
            }

            public FrameLayout getSwipableView() {
                return this.mRemoveableView;
            }

            public String toString() {
                return super.toString() + " '" + "'";
            }
        }
    }

    public class putVolunteerSkills extends ApiRestFullRequest {
        public putVolunteerSkills(JSONObject param) {
            super(HttpRequestType.PUT, ProfessionalSkillsActivity.this.getString(R.string.apiVolunteerURL), "api/volunteer/skill", param, ProfessionalSkillsActivity.this.getUserAccessToken());
        }

        protected void onPostExecuteBody(String result) {
            ProfessionalSkillsActivity.this.finishActivity();
        }
    }
}
