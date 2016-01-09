package de.davidartmann.profpedia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.fragment.EndlessListFragment;
import de.davidartmann.profpedia.fragment.LecturerListFragment;
import de.davidartmann.profpedia.model.Lecturer;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LecturerListFragment.OnLecturerClicked, EndlessListFragment.OnShowProgressBar {

    private static final String TAG = NavigationDrawerActivity.class.getSimpleName();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_navigation_drawer_toolbar);
        progressBar = (ProgressBar) findViewById(R.id.app_bar_navigation_drawer_progressbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        //TODO: drawer find one time and reuse
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            replaceFragment(new LecturerListFragment(), false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //if there is sth. to save, do it here
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Log.d(TAG, "settings clicked");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lecturer) {
            replaceFragment(new LecturerListFragment(), false);
        }
        if (id == R.id.nav_endlesslist) {
            replaceFragment(new EndlessListFragment(), false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment, boolean isAllowedToBackStack) {
        //http://www.vogella.com/tutorials/AndroidFragments/article.html
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                //.add(fragment, null);
                .replace(R.id.app_bar_navigation_drawer_framelayout, fragment);
        if (isAllowedToBackStack) {
            ft.addToBackStack(null);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onLecturerClick(Lecturer lecturer) {
        Intent intent = new Intent(this, LecturerDetailActivity.class);
        //intent.putExtra("id", position);
        intent.putExtra("lecturer", lecturer);
        startActivity(intent);
    }

    @Override
    public void showProgressBar(final boolean b) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
