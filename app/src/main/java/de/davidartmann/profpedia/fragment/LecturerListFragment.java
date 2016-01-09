package de.davidartmann.profpedia.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.MainRecyclerviewAdapter;
import de.davidartmann.profpedia.async.LoadLecturerFromNetwork;
import de.davidartmann.profpedia.model.Lecturer;
import de.davidartmann.profpedia.utils.LecturerData;

/**
 * Fragment for the list of lecturers.
 * Created by david on 01.01.16.
 */
public class LecturerListFragment extends Fragment {

    private List<Lecturer> lecturers;
    private MainRecyclerviewAdapter mra;
    private OnLecturerClicked onLecturerClicked;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onLecturerClicked = (OnLecturerClicked) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lecturer_list, container, false);
        setHasOptionsMenu(true);
        LecturerData lecturerData = new LecturerData(view.getContext());
        lecturers = lecturerData.getLecturers();
        RecyclerView recyclerView =
                (RecyclerView) view.findViewById(R.id.fragment_lecturer_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager sglm;
        if (getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT) {
            sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        recyclerView.setLayoutManager(sglm);
        mra = new MainRecyclerviewAdapter(R.layout.cardview_lecturer_list,
                lecturers, view.getContext(), onLecturerClicked, getScreenOrientation());
        recyclerView.setAdapter(mra);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        Log.d("OPTIONS", "greift!");
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mra.filter(newText);
                //fetchLecturerByLastname(newText);
                return true;
            }
        });
    }

    /*
    private void fetchLecturerByLastname(String query) {
        List<Lecturer> filteredLecturers = new ArrayList<>();
        for (Lecturer l : lecturers) {
            if (l.getLastName().toLowerCase().startsWith(query.toLowerCase())) {
                filteredLecturers.add(l);
            }
        }
        mra.setNewData(filteredLecturers);
    }
    */

    /**
     * Interface for the clickhandling contract between activity and (fragment->adapter->viewholder)
     */
    public interface OnLecturerClicked {
        //void onLecturerClick(int position);
        void onLecturerClick(Lecturer lecturer);
    }

    private int getScreenOrientation() {
        return getResources().getConfiguration().orientation;
    }
}
