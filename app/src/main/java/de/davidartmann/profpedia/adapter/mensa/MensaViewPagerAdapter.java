package de.davidartmann.profpedia.adapter.mensa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.async.LoadMensaFromNetwork;
import de.davidartmann.profpedia.fragment.mensa.MensaFridayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaMondayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaSaturdayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaSundayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaThursdayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaTuesdayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaWednesdayFragment;
import de.davidartmann.profpedia.model.MensaMeal;

/**
 * Adapter class for the viewpager of the mensa view.
 * Created by david on 11.01.16.
 */
public class MensaViewPagerAdapter extends FragmentStatePagerAdapter
        implements LoadMensaFromNetwork.IGetMensaDataFromNetwork {

    private static final String TAG = MensaViewPagerAdapter.class.getSimpleName();

    private List<String> titles;
    private Context context;
    private SharedPreferences sharedPreferences;

    public MensaViewPagerAdapter(FragmentManager fm, List<String> titles, Context context) {
        super(fm);
        this.titles = titles;
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.getString(context.getString(R.string.pref_key_mensa_location_id),
                context.getString(R.string.default_mensa_location_id));
        new LoadMensaFromNetwork(this).execute(context.getString(R.string.load_mensa_base_url)
                +getPrefMensaId()/*context.getString(R.string.load_mensa_id_wuerzburg_hubland)*/);
    }

    //TODO: create algorithm, which depends on weekday to display the current day first and following ones after this
    // e.g. we have thursday, so we want to display thursday first and the friday etc.
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                //Montag
                fragment = new MensaMondayFragment();
                break;
            case 1:
                //Dienstag
                fragment = new MensaTuesdayFragment();
                break;
            case 2:
                //Mittwoch
                fragment = new MensaWednesdayFragment();
                break;
            case 3:
                //Donnerstag
                fragment = new MensaThursdayFragment();
                break;
            case 4:
                //Freitag
                fragment = new MensaFridayFragment();
                break;
            case 5:
                //Samstag
                fragment = new MensaSaturdayFragment();
                break;
            case 6:
                //Sonntag
                fragment = new MensaSundayFragment();
                break;
            default:
                Log.e(TAG, "getItem default path");
                fragment = new MensaMondayFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Integer.valueOf(sharedPreferences
                .getString(context.getString(R.string.pref_key_number_of_mensa_tabs),
                        context.getString(R.string.default_number_of_mensa_tabs)));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public void fetchMensaData(List<MensaMeal> mensaMeals) {
        Log.d(TAG, "fetchMensaData called!!!");

    }

    public String getPrefMensaId() {
        String s = sharedPreferences.getString(context.getString(R.string.pref_key_mensa_location_id),
                context.getString(R.string.default_mensa_location_id));

        return "";
    }
}
