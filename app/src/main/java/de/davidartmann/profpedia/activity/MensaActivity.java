package de.davidartmann.profpedia.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.MensaViewPagerAdapter;
import de.davidartmann.profpedia.transition.DepthPageTransformer;

/**
 * Activity for the Mensa view.
 * Created by david on 11.01.16.
 */
public class MensaActivity extends AppCompatActivity {

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
            getSupportActionBar().setTitle(R.string.mensa);
        }
    }

    /**
     * Convenient method for initializing the viewpager
     */
    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_mensa_viewpager);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        List<String> titles = new ArrayList<>();
        Collections.addAll(titles, getResources().getStringArray(R.array.activity_mensa_tabtitles));
        MensaViewPagerAdapter mensaViewPagerAdapter =
                new MensaViewPagerAdapter(getSupportFragmentManager(), titles);
        viewPager.setAdapter(mensaViewPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_mensa_app_bar_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
