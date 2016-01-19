package de.davidartmann.profpedia.adapter.mensa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.activity.contract.OnFoodClickListener;
import de.davidartmann.profpedia.adapter.mensa.viewholder.MensaListViewHolder;
import de.davidartmann.profpedia.async.LoadMensaFromNetwork;
import de.davidartmann.profpedia.fragment.mensa.contract.IProgressBar;
import de.davidartmann.profpedia.model.MensaMeal;
import de.davidartmann.profpedia.utils.DateHelper;
import de.davidartmann.profpedia.utils.MensaAssetHelper;

/**
 * Adapter for the mensa food list view.
 * Created by david on 17.01.16.
 */
public class MensaFoodListAdapter extends RecyclerView.Adapter<MensaListViewHolder>
        implements LoadMensaFromNetwork.IGetMensaDataFromNetwork,
            SharedPreferences.OnSharedPreferenceChangeListener{

    private Context context;
    /**
     * Data of the adapter.
     */
    private List<MensaMeal> mensaMeals;
    /**
     * Each fragment will tell the adapter for which day it stands.
     */
    private int actualDayOfFragment;
    /**
     * The ProgressBar interface for telling activity about when to show and hide it.
     */
    private IProgressBar iProgressBar;
    /**
     * The given layout for the appropriate fragment.
     */
    private int layout;
    /**
     * The interface for the click event of a food card in the viewholder.
     */
    private OnFoodClickListener onFoodClickListener;

    public MensaFoodListAdapter(int layout, Context context, int actualDayOfFragment,
                                IProgressBar iProgressBar,
                                OnFoodClickListener onFoodClickListener) {
        this.layout = layout;
        this.context = context;
        mensaMeals = new ArrayList<>();
        this.actualDayOfFragment = actualDayOfFragment;
        this.iProgressBar = iProgressBar;
        this.onFoodClickListener = onFoodClickListener;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        //iProgressBar.showProgressBar(true);
        new LoadMensaFromNetwork(this, iProgressBar).execute(context.getString(R.string.load_mensa_base_url)
                + getIdOfActualMensaInPreferences(sharedPreferences));
    }

    @Override
    public MensaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new MensaListViewHolder(view, context, onFoodClickListener);
    }

    @Override
    public void onBindViewHolder(MensaListViewHolder holder, int position) {
        holder.assignData(mensaMeals.get(position));
    }

    @Override
    public int getItemCount() {
        return mensaMeals.size();
    }

    @Override
    public void fetchMensaData(List<MensaMeal> mensaMeals) {
        List<MensaMeal> filteredMeals = new ArrayList<>();
        Long date = DateHelper.getSecondsByGivenWeekDay(actualDayOfFragment);
        for (MensaMeal mensaMeal : mensaMeals) {
            if (Long.valueOf(mensaMeal.getDate()).equals(date)) {
                filteredMeals.add(mensaMeal);
            }
        }
        this.mensaMeals = filteredMeals;
        notifyDataSetChanged();
        //iProgressBar.showProgressBar(false);
    }

    /**
     * Convenient method to get the name of the actual mensa from the shared prefs.
     * Then check this name.
     * @return id of the actual mensa from string resources.
     */
    public String getIdOfActualMensaInPreferences(SharedPreferences sharedPreferences) {
        String currentMensaName = sharedPreferences.getString(
                context.getString(R.string.pref_key_mensa_location_id),
                context.getResources().getStringArray(R.array.mensa_names)[0]);
        JSONArray jsonArray = MensaAssetHelper.loadMensaDataToJSONArray(context);
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("name").equals(currentMensaName)) {
                        return jsonObject.getString("id");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(context.getString(R.string.pref_key_mensa_location_id))) {
            new LoadMensaFromNetwork(this, iProgressBar).execute(context.getString(R.string.load_mensa_base_url)
                    +getIdOfActualMensaInPreferences(sharedPreferences));
        }
    }
}
