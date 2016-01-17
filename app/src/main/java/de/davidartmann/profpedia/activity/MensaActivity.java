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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.mensa.MensaViewPagerAdapter;
import de.davidartmann.profpedia.fragment.mensa.contract.IProgressBar;
import de.davidartmann.profpedia.transition.DepthPageTransformer;
import de.davidartmann.profpedia.utils.MensaAssetHelper;

/**
 * Activity for the Mensa view.
 * Created by david on 11.01.16.
 */
public class MensaActivity extends AppCompatActivity implements IProgressBar {

    private MensaViewPagerAdapter mensaViewPagerAdapter;
    private TabLayout tabLayout;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private ImageView imageViewCollapsingToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensa);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        initViewPagerAndTabs();
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_mensa_app_bar_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(sharedPreferences.getString(
                    getString(R.string.pref_key_mensa_location_id),
                    getResources().getStringArray(R.array.mensa_names)[0]));
        }
        progressBar = (ProgressBar) findViewById(R.id.activity_mensa_progressbar);
        imageViewCollapsingToolbar = (ImageView) findViewById(R.id.activity_mensa_collapseimageview);
        setImageOfMensaByPref(sharedPreferences, imageViewCollapsingToolbar);
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
        viewPager.setOffscreenPageLimit(6); //because of this, we do not get load every time from mensa API when we swipe back from two tabs distance
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
                showMensaPicker();
                return true;
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
                        //Log.d("Clicked on Item", arrayAdapter.getItem(which));
                        sharedPreferences.edit().putString(getString(R.string.pref_key_mensa_location_id),
                                arrayAdapter.getItem(which)).apply();
                        setImageOfMensaByPref(sharedPreferences, imageViewCollapsingToolbar);
                        //TODO: foodlistadapter informieren über neuen inhalt + titel
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
        String[] displayedValues = getResources().getStringArray(R.array.mensa_days_numeric);
        numberPicker.setDisplayedValues(displayedValues);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(displayedValues.length);
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
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

    /**
     * Convinient method to get position of the actual mensa in the string array
     * (R.array.mensa_names) and return this position to display this info in the dialog.
     * @return position of mensa.
     */
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

    /**
     * Method to set the image of the prefered mensa from the sharedprefs into the given imageview.
     * @param sharedPreferences given sharedpreferences
     * @param imageView given imageview
     */
    private void setImageOfMensaByPref(SharedPreferences sharedPreferences, ImageView imageView) {
        JSONObject preferedMensaJsonObject =
                MensaAssetHelper.getPreferedMensaJSONObject(this, sharedPreferences);
        try {
            Picasso.with(this).load(preferedMensaJsonObject.getString("imgurl")).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showProgressBar(final boolean b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }
}
