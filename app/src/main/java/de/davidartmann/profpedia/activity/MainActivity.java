package de.davidartmann.profpedia.activity;

import android.os.SystemClock;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.MainRecyclerviewAdapter;
import de.davidartmann.profpedia.model.Lecturer;
import de.davidartmann.profpedia.utils.LecturerData;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Lecturer> lecturers;
    private MainRecyclerviewAdapter mra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        LecturerData lecturerData = new LecturerData(this);
        lecturers = lecturerData.getLecturers();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        mra = new MainRecyclerviewAdapter(R.layout.recyclerview_main_cardview,
                lecturers, this);
        recyclerView.setAdapter(mra);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit at "+SystemClock.currentThreadTimeMillis());
                return false;
                /*
                fetchLecturerByLastname(query);
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                toolbar.setTitle(query);
                return true;
                */
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange at "+SystemClock.currentThreadTimeMillis());
                fetchLecturerByLastname(newText);
                return true;
            }
        });
        return true;
    }

    private void fetchLecturerByLastname(String query) {
        List<Lecturer> filteredLecturers = new ArrayList<>();
        for (Lecturer l : lecturers) {
            if (l.getLastName().toLowerCase().contains(query.toLowerCase())) {
                filteredLecturers.add(l);
            }
        }
        mra.setNewData(filteredLecturers);
    }
}
