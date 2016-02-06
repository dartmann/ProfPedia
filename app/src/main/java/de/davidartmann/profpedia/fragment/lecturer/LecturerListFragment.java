package de.davidartmann.profpedia.fragment.lecturer;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.lecturer.LecturerListAdapter;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Fragment for the list of lecturers.
 * Created by david on 01.01.16.
 */
public class LecturerListFragment extends Fragment {

    //private List<Lecturer> lecturers;
    private LecturerListAdapter mra;
    private IOnLecturerClicked iOnLecturerClicked;
    private IProgressBar iProgressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iOnLecturerClicked = (IOnLecturerClicked) context;
        iProgressBar = (IProgressBar) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lecturer_list, container, false);
        setHasOptionsMenu(true);
        //LecturerData lecturerData = new LecturerData(view.getContext());
        //lecturers = lecturerData.getLecturers();
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
        /**
         * did not work -> check this one: http://stackoverflow.com/questions/27816217/how-to-save-recyclerview-scroll-position-with-recyclerview-state-or-no
         *
        Log.d("POSITION RECEIVED", getActivity().getIntent()
                .getIntExtra("position", RecyclerView.SCROLLBAR_POSITION_DEFAULT)+"");
        sglm.scrollToPosition(getActivity().getIntent()
                .getIntExtra("position", RecyclerView.SCROLLBAR_POSITION_DEFAULT));
                */
        mra = new LecturerListAdapter(R.layout.cardview_lecturer_list,
                /*lecturers,*/ view.getContext(),
                iOnLecturerClicked,
                getScreenOrientation(),
                iProgressBar);
        recyclerView.setAdapter(mra);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
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
                return true;
            }
        });
    }

    /**
     * Interface for the clickhandling contract between activity and (fragment->adapter->viewholder)
     */
    public interface IOnLecturerClicked {
        void onLecturerClick(Lecturer lecturer, ImageView imageView, int adapterPosition);
    }

    /**
     * Interface for the {@link android.widget.ProgressBar} handling.
     */
    public interface IProgressBar {
        void showProgressBarForLecturerList(boolean b);
    }

    /**
     * Method to get the screenorientation.
     * @return either {@link Configuration#ORIENTATION_PORTRAIT}
     * or {@link Configuration#ORIENTATION_LANDSCAPE}.
     */
    private int getScreenOrientation() {
        return getResources().getConfiguration().orientation;
    }
}
