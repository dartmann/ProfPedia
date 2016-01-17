package de.davidartmann.profpedia.adapter.mensa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.davidartmann.profpedia.R;
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
        implements LoadMensaFromNetwork.IGetMensaDataFromNetwork {

    private int layout;
    private Context context;
    private List<MensaMeal> mensaMeals;
    private SharedPreferences sharedPreferences;
    private int actualDayOfFragment;
    private IProgressBar iProgressBar;

    public MensaFoodListAdapter(int layout, Context context, int actualDayOfFragment,
                                IProgressBar iProgressBar) {
        this.layout = layout;
        this.context = context;
        mensaMeals = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        iProgressBar.showProgressBar(true);
        new LoadMensaFromNetwork(this).execute(context.getString(R.string.load_mensa_base_url)
                + getIdOfActualMensaInPreferences());
        this.actualDayOfFragment = actualDayOfFragment;
        this.iProgressBar = iProgressBar;
    }

    @Override
    public MensaListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new MensaListViewHolder(view, context);
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
                //Log.d("fetchMensaData", "hit date in api response");
                filteredMeals.add(mensaMeal);
            }
        }
        this.mensaMeals = filteredMeals;
        notifyDataSetChanged();
        iProgressBar.showProgressBar(false);
    }

    /**
     * Convenient method to get the name of the actual mensa from the shared prefs.
     * Then check this name.
     * @return id of the actual mensa from string resources.
     */
    public String getIdOfActualMensaInPreferences() {
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
}
