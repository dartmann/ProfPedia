package de.davidartmann.profpedia.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.mensa.MensaViewPagerAdapter;
import de.davidartmann.profpedia.transition.DepthPageTransformer;

/**
 * Activity for the Mensa view.
 * Created by david on 11.01.16.
 */
public class MensaActivity extends AppCompatActivity {

    private MensaViewPagerAdapter mensaViewPagerAdapter;
    private TabLayout tabLayout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensa);
        initViewPagerAndTabs();
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_mensa_app_bar_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setTitle(""/*R.string.mensa*/);
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    /**
     * Convenient method for initializing the viewpager
     */
    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_mensa_viewpager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        List<String> titles = new ArrayList<>();
        Collections.addAll(titles, getResources().getStringArray(R.array.activity_mensa_tabtitles));
        mensaViewPagerAdapter =
                new MensaViewPagerAdapter(getSupportFragmentManager(), titles, this);
        viewPager.setAdapter(mensaViewPagerAdapter);
        viewPager.setOffscreenPageLimit(6); //experimental
        tabLayout = (TabLayout) findViewById(R.id.activity_mensa_app_bar_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mensa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mensa_days:
                showNumberPicker();
                //startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_mensa_mensachooser:
                //TODO: add picker of mensa location
                showMensaPicker();
            default:
                return false;
        }
    }

    /**
     * Helper method to show a listviewdialog for choosing a mensa.
     */
    private void showMensaPicker() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.waehle_mensa));
        builder.setIcon(R.drawable.location_marker);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice);
        arrayAdapter.addAll(getResources().getStringArray(R.array.mensa_names));
        builder.setSingleChoiceItems(arrayAdapter,
                getSavedMensaPosition(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Clicked on Item", arrayAdapter.getItem(which));
                        sharedPreferences.edit().putString(getString(R.string.pref_key_mensa_location_id),
                                arrayAdapter.getItem(which)).apply();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Helper method to show a numberpicker for the number of days shown with the TabLayout Tabs.
     */
    private void showNumberPicker() {
        final NumberPicker numberPicker = new NumberPicker(this);
        //numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDisplayedValues(getResources().getStringArray(R.array.mensa_days_numeric));
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(7);
        numberPicker.setValue(Integer.valueOf(
                sharedPreferences.getString(getString(R.string.pref_key_number_of_mensa_tabs),
                        getString(R.string.default_number_of_mensa_tabs))));
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(numberPicker)
                .setTitle("Anzahl anzuzeigender Tage")
                .setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("DIALOGVALUE", numberPicker.getValue()+"");
                        sharedPreferences.edit().putString(
                                getString(R.string.pref_key_number_of_mensa_tabs),
                                String.valueOf(numberPicker.getValue())).apply();
                        mensaViewPagerAdapter.notifyDataSetChanged(); //DOES NOT REFRESH THE VIEW!!!
                        tabLayout.setTabsFromPagerAdapter(mensaViewPagerAdapter); // ...this does
                        /*
                        mensaViewPagerAdapter =
                                new MensaViewPagerAdapter(getSupportFragmentManager(), titles,
                                        MensaActivity.this);
                        viewPager.setAdapter(mensaViewPagerAdapter);
                        */
                    }
                })
                .create();
        alertDialog.show();
    }

    private int getSavedMensaPosition() {
        String[] mensaStrings = getResources().getStringArray(R.array.mensa_names).clone();
        String inPrefs = sharedPreferences.getString(getString(R.string.pref_key_mensa_location_id),
                getString(R.string.default_mensa_location_id));
        for (int i = 0; i < mensaStrings.length; i++) {
            if (mensaStrings[i].equals(inPrefs)) {
                return i;
            }
        }
        return 0;
    }
}
