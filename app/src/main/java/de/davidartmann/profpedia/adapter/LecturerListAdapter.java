package de.davidartmann.profpedia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.davidartmann.profpedia.adapter.viewholder.MainViewHolder;
import de.davidartmann.profpedia.async.LoadLecturerFromNetwork;
import de.davidartmann.profpedia.fragment.LecturerListFragment;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Adapter for the {@link LecturerListFragment}'s {@link RecyclerView}.
 * Created by david on 25.12.15.
 */
public class LecturerListAdapter extends RecyclerView.Adapter<MainViewHolder>
        implements LoadLecturerFromNetwork.IGetLecturerDataFromNetwork {

    private int layout;
    private List<Lecturer> lecturers;
    private List<Lecturer> filteredLecturers;
    private Context context;
    private LecturerListFragment.IOnLecturerClicked iOnLecturerClicked;
    private int screenOrientation;
    private LecturerListFragment.IProgressBar iProgressBar;
    private int numberOfBackendData;
    private String nextUrl;


    public LecturerListAdapter(int layout,
                               Context context,
                               LecturerListFragment.IOnLecturerClicked iOnLecturerClicked,
                               int screenOrientation,
                               LecturerListFragment.IProgressBar iProgressBar) {
        numberOfBackendData = 0;
        nextUrl = "";
        this.layout = layout;
        lecturers = new ArrayList<>();
        filteredLecturers = new ArrayList<>();
        this.context = context;
        this.iOnLecturerClicked = iOnLecturerClicked;
        this.iProgressBar = iProgressBar;
        this.screenOrientation = screenOrientation;
        //TODO: maybe network check and let info appear?
        new LoadLecturerFromNetwork(this, context)
                .execute("http://193.175.31.146:8080/fiwincoming/api/lecturers?size=10");
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MainViewHolder(view, context, iOnLecturerClicked, screenOrientation);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if (position >= getItemCount()-1) {
            if (!nextUrl.equals("")) {
                loadMoreData();
            }
        }
        holder.assignData(filteredLecturers.get(position));
    }

    private void loadMoreData() {
        new Thread() {
            @Override
            public void run() {
                iProgressBar.showProgressBarForLecturerList(true);
                /**
                 * just for showcase, because with wifi it loads so fast
                 * we do not see the progressbar
                 *
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 */
                new LoadLecturerFromNetwork(LecturerListAdapter.this, context).execute(nextUrl);
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return filteredLecturers.size();
    }

    public void filter(String query) {
        if (!query.isEmpty()) {
            for (Lecturer l : lecturers) {
                //get position of lecturer in filtered list
                int pos = filteredLecturers.indexOf(l);
                //check if lastname is not starting with query
                if (!l.getLastName().trim().toLowerCase().startsWith(query.trim().toLowerCase())) {
                    //if lecturer is in filtered list
                    if (pos != -1) {
                        //lecturer does not fit -> remove from filtered list
                        filteredLecturers.remove(pos);
                        notifyItemRemoved(pos);
                    }
                } else {
                    //lecturer is not in filtered list, but he should be
                    if (pos == -1) {
                        //add to filtered list, sort,
                        // and search for index to tell ui where to display insertion
                        filteredLecturers.add(l);
                        Collections.sort(filteredLecturers);
                        notifyItemInserted(filteredLecturers.indexOf(l));
                    }
                }
            }
        } else {
            filteredLecturers = new ArrayList<>(this.lecturers);
            notifyDataSetChanged();
        }
    }

    @Override
    public void fetchLecturers(List<Lecturer> lecturers, int numberOfBackendData, String nextUrl) {
        this.lecturers.addAll(lecturers);
        filteredLecturers = new ArrayList<>(this.lecturers); //(List<Lecturer>) ((ArrayList<Lecturer>) this.lecturers).clone();
        this.nextUrl = nextUrl;
        this.numberOfBackendData = numberOfBackendData;
        iProgressBar.showProgressBarForLecturerList(false);
        /**
         * Because we are in the callback method of the IGetLecturerDataFromNetwork interface,
         * which is called inside the async task, we do not need the handler here.
         */
        notifyDataSetChanged();
        //informAdapter();
    }

    /*
    private void informAdapter() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
    */
}
