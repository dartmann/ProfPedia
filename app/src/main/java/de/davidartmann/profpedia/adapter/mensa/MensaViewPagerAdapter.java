package de.davidartmann.profpedia.adapter.mensa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.fragment.mensa.MensaFridayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaMondayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaThursdayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaTuesdayFragment;
import de.davidartmann.profpedia.fragment.mensa.MensaWednesdayFragment;

/**
 * Adapter class for the viewpager of the mensa view.
 * Created by david on 11.01.16.
 */
public class MensaViewPagerAdapter extends FragmentStatePagerAdapter {

    //private static final String TAG = MensaViewPagerAdapter.class.getSimpleName();

    private List<String> titles;
    private Context context;
    private SharedPreferences sharedPreferences;

    public MensaViewPagerAdapter(FragmentManager fm, List<String> titles, Context context) {
        super(fm);
        this.titles = titles;
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Fragment getItem(int position) {
        return getFragmentByWeekday(position);
    }

    /**
     * Convinient method to return the Fragments for the ViewPagerAdapter
     * in dependecy of the actual weekday. The weekdays start with sunday = 1 and go on
     * till saturday = 7. <br>
     * E.g. we have sunday, so the first fragment in the viewpager should be the
     * {@link MensaMondayFragment}.
     * @param positionOfFragmentInTabs position of fragment in viewpager tablayout.
     * @return needed Fragment.
     */
    private Fragment getFragmentByWeekday(int positionOfFragmentInTabs) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        List<Fragment> weekFragments = new ArrayList<>();
        weekFragments.add(new MensaMondayFragment());
        weekFragments.add(new MensaTuesdayFragment());
        weekFragments.add(new MensaWednesdayFragment());
        weekFragments.add(new MensaThursdayFragment());
        weekFragments.add(new MensaFridayFragment());
        return weekFragments.get(
                getReturnPositionByHelper(positionOfFragmentInTabs, dayOfWeek, weekFragments.size()));
    }

    @Override
    public int getCount() {
        return Integer.valueOf(sharedPreferences
                .getString(context.getString(R.string.pref_key_number_of_mensa_tabs),
                        context.getString(R.string.default_number_of_mensa_tabs)));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return titles.get(getReturnPositionByHelper(position, dayOfWeek, titles.size()));
    }

    private int getReturnPositionByHelper(int position, int dayOfWeek, int size) {
        int posToReturn;
        if (dayOfWeek != Calendar.SUNDAY) {
            posToReturn = ((position + dayOfWeek) - 2) % size;
        } else {
            posToReturn = ((position + dayOfWeek) - 1) % size;
        }
        return posToReturn;
    }
}
