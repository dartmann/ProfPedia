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
import android.view.Menu;
import android.view.MenuItem;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.fragment.LecturerListFragment;
import de.davidartmann.profpedia.model.Lecturer;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LecturerListFragment.OnLecturerClicked {

    private static final String TAG = NavigationDrawerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_navigation_drawer_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
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
        /*
        Bundle bundle = new Bundle();
        bundle.putInt("id", position);
        Fragment fragment = new LecturerDetailFragment_OLD();
        fragment.setArguments(bundle);
        replaceFragment(fragment, true);
        */
        Intent intent = new Intent(this, LecturerDetailActivity.class);
        //intent.putExtra("id", position);
        intent.putExtra("lecturer", lecturer);
        startActivity(intent);
    }
}
