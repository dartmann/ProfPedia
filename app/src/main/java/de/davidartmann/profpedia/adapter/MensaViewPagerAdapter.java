package de.davidartmann.profpedia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

import de.davidartmann.profpedia.fragment.MensaFridayFragment;
import de.davidartmann.profpedia.fragment.MensaMondayFragment;
import de.davidartmann.profpedia.fragment.MensaSaturdayFragment;
import de.davidartmann.profpedia.fragment.MensaSundayFragment;
import de.davidartmann.profpedia.fragment.MensaThursdayFragment;
import de.davidartmann.profpedia.fragment.MensaTuesdayFragment;
import de.davidartmann.profpedia.fragment.MensaWednesdayFragment;

/**
 * Adapter class for the viewpager of the mensa view.
 * Created by david on 11.01.16.
 */
public class MensaViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = MensaViewPagerAdapter.class.getSimpleName();

    private List<String> titles;

    public MensaViewPagerAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.titles = titles;
    }

    //TODO: add the Fragments
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
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
