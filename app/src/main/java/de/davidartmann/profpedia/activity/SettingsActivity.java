package de.davidartmann.profpedia.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.davidartmann.profpedia.fragment.SettingsFragment;

/**
 * Activity for the SharedPreferences, which then get handled inside the {@link SettingsFragment}.
 * Created by david on 16.01.16.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            SettingsFragment settingsFragment = new SettingsFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, settingsFragment,
                            SettingsFragment.class.getSimpleName())
                    .commit();
        }
    }
}
