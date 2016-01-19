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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.activity.contract.OnFoodClickListener;
import de.davidartmann.profpedia.adapter.mensa.MensaViewPagerAdapter;
import de.davidartmann.profpedia.fragment.mensa.contract.IProgressBar;
import de.davidartmann.profpedia.model.MensaMeal;
import de.davidartmann.profpedia.transition.DepthPageTransformer;
import de.davidartmann.profpedia.utils.MensaAssetHelper;

/**
 * Activity for the Mensa view.
 * Created by david on 11.01.16.
 */
public class MensaActivity extends AppCompatActivity implements IProgressBar, OnFoodClickListener {

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
        imageViewCollapsingToolbar =
                (ImageView) findViewById(R.id.activity_mensa_collapseimageview);
        setImageOfMensaByPref(sharedPreferences);
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
        viewPager.setOffscreenPageLimit(6);
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
     * Helper method to show a listviewdialog for choosing a mensa location.
     */
    private void showMensaPicker() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.waehle_mensa));
        //builder.setIcon(R.drawable.location_marker);
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
                        setImageOfMensaByPref(sharedPreferences);
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(arrayAdapter.getItem(which));
                        }
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
                        mensaViewPagerAdapter.notifyDataSetChanged(); //DOES NOT REFRESH THE VIEW!
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
     */
    private void setImageOfMensaByPref(SharedPreferences sharedPreferences) {
        /*
        JSONObject preferedMensaJsonObject =
                MensaAssetHelper.getPreferedMensaJSONObject(this, sharedPreferences);
        try {
            Picasso.with(this).load(preferedMensaJsonObject.getString("imgurl")).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
        MensaAssetHelper.setImageByPreferedMensa(this, sharedPreferences,
                imageViewCollapsingToolbar);
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

    @Override
    public void onClick(MensaMeal mensaMeal) {
        showDetailMealDialog(mensaMeal);
    }

    private void showDetailMealDialog(MensaMeal mensaMeal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(mensaMeal.getName());
        builder.setMessage(getCleanMessage(mensaMeal));
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Helper method for the dialog to show by clicking a list item of the mensa meals.
     * @param mensaMeal the given data object behind the clicked card.
     * @return the whole string in the message of the dialog.
     */
    private String getCleanMessage(MensaMeal mensaMeal) {
        String ret = getString(R.string.essensart);
        String foodtype = getFoodtypeBySwitch(mensaMeal.getFoodtype());
        ret += foodtype+"\n\n";
        String additives = mensaMeal.getAdditivenumbers();
        String[] additivesArr = getResources().getStringArray(R.array.additives);
        String additivesDetail = getString(R.string.Zusatzstoffe)+"\n";
        for (String s : additivesArr) {
            if (additives.contains(s)) {
                additivesDetail += getString(R.string.additive_dot) + " " + getAdditiveDetail(s) + "\n";
            }
        }
        return ret + additivesDetail;
    }

    private String getAdditiveDetail(String strAdd) {
        switch (strAdd) {
            case "A1":
                strAdd = getString(R.string.A1);
                break;
            case "A2":
                strAdd = getString(R.string.A2);
                break;
            case "A3":
                strAdd = getString(R.string.A3);
                break;
            case "A4":
                strAdd = getString(R.string.A4);
                break;
            case "A5":
                strAdd = getString(R.string.A5);
                break;
            case "A6":
                strAdd = getString(R.string.A6);
                break;
            case "A7":
                strAdd = getString(R.string.A7);
                break;
            case "A8":
                strAdd = getString(R.string.A8);
                break;
            case "A9":
                strAdd = getString(R.string.A9);
                break;
            case "A10":
                strAdd = getString(R.string.A10);
                break;
            case "A11":
                strAdd = getString(R.string.A11);
                break;
            case "A12":
                strAdd = getString(R.string.A12);
                break;
            case "A13":
                //TODO: not found in api response
                //strAdd = getString(R.string.A13);
                break;
            case "A14":
                strAdd = getString(R.string.A14);
                break;
            case "A15":
                strAdd = getString(R.string.A15);
                break;
            case "A16":
                strAdd = getString(R.string.A16);
                break;
            case "A17":
                strAdd = getString(R.string.A17);
                break;
            case "A18":
                //TODO: not found in api response
                //strAdd = getString(R.string.A18);
                break;
            case "A19":
                strAdd = getString(R.string.A19);
                break;
            case "F":
                strAdd = getString(R.string.F);
                break;
            case "K":
                strAdd = getString(R.string.K);
                break;
            case "G":
                strAdd = getString(R.string.G);
                break;
            case "R":
                strAdd = getString(R.string.R);
                break;
            case "S":
                strAdd = getString(R.string.S);
                break;
            case "Z1":
                strAdd = getString(R.string.Z1);
                break;
            case "Z2":
                strAdd = getString(R.string.Z2);
                break;
            case "Z3":
                strAdd = getString(R.string.Z3);
                break;
            case "Z4":
                strAdd = getString(R.string.Z4);
                break;
            case "Z5":
                strAdd = getString(R.string.Z5);
                break;
            //TODO: Z6-8 not found in api reponse
            case "Z9":
                strAdd = getString(R.string.Z9);
                break;
            case "Z10":
                strAdd = getString(R.string.Z10);
                break;
        }
        return strAdd;
    }

    private String getFoodtypeBySwitch(String foodtype) {
        switch (foodtype) {
            case "S":
                foodtype = getString(R.string.pig);
                break;
            case "G":
                foodtype = getString(R.string.chicken);
                break;
            case "R":
                foodtype = getString(R.string.cow);
                break;
            case "K":
                foodtype = getString(R.string.calf);
                break;
            case "F":
                foodtype = getString(R.string.fish);
                break;
            case "FL":
                foodtype = getString(R.string.meatless);
                break;
            case "V":
                foodtype = getString(R.string.vegan);
                break;
            case "A":
                foodtype = getString(R.string.alkohol);
                break;
            case "VS":
                foodtype = getString(R.string.ham);
                break;
            case "L":
                foodtype = getString(R.string.lamb);
                break;
            case "W":
                foodtype = getString(R.string.wild);
                break;
            // alkohol
            case "S/A":
                foodtype = getString(R.string.pig)+"/"+getString(R.string.alkohol);
                break;
            case "G/A":
                foodtype = getString(R.string.chicken)+"/"+getString(R.string.alkohol);
                break;
            case "R/A":
                foodtype = getString(R.string.cow)+"/"+getString(R.string.alkohol);
                break;
            case "K/A":
                foodtype = getString(R.string.lamb)+"/"+getString(R.string.alkohol);
                break;
            case "F/A":
                foodtype = getString(R.string.fish)+"/"+getString(R.string.alkohol);
                break;
            case "FL/A":
                foodtype = getString(R.string.meatless)+"/"+getString(R.string.alkohol);
                break;
            case "V/A":
                foodtype = getString(R.string.vegan)+"/"+getString(R.string.alkohol);
                break;
            case "VS/A":
                foodtype = getString(R.string.ham)+"/"+getString(R.string.alkohol);
                break;
            case "L/A":
                foodtype = getString(R.string.lamb)+"/"+getString(R.string.alkohol);
                break;
            case "W/A":
                foodtype = getString(R.string.wild)+"/"+getString(R.string.alkohol);
                break;
            // mixed food
            case "F/G":
                foodtype = getString(R.string.fish)+"/"+getString(R.string.chicken);
                break;
            case "R/S":
                foodtype = getString(R.string.cow)+"/"+getString(R.string.pig);
                break;
            case "S/R":
                foodtype = getString(R.string.pig)+"/"+getString(R.string.cow);
                break;
            case "S/VS":
                foodtype = getString(R.string.pig)+"/"+getString(R.string.ham);
                break;
            case "VS/S":
                foodtype = getString(R.string.ham)+"/"+getString(R.string.pig);
                break;
            default:
                // there could be meals without a foodtype or unkown
                foodtype = getString(R.string.N_A);
        }
        return foodtype;
    }
}
