package de.davidartmann.profpedia.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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
    private Handler handler;
    private int numberOfBackendData;
    private String nextUrl;


    public LecturerListAdapter(int layout/*, List<Lecturer> lecturers*/, Context context,
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
        handler = new Handler();
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
                loadMoreData(holder);
            }
        }
        holder.assignData(lecturers.get(position));
    }

    private void loadMoreData(MainViewHolder holder) {
        new Thread() {
            @Override
            public void run() {
                iProgressBar.showProgressBarForLecturerList(true);
                //TODO: just for showcase, remove if not neeeded any more
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new LoadLecturerFromNetwork(LecturerListAdapter.this, context).execute(nextUrl);
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return lecturers.size();
    }

    //TODO: fix the search to work!!!
    public void filter(String query) {
        if (!query.equals("")) {
            List<Lecturer> tempLecturers = lecturers;
            int currentPos = 0;
            for(int posInOrigList = 0; posInOrigList < tempLecturers.size(); posInOrigList++) {
                Lecturer lecturer = lecturers.get(posInOrigList);
                currentPos = findPosOfLecturerInCurrentList(lecturer, currentPos);
                if (lecturer.getLastName().toLowerCase().startsWith(query.toLowerCase())) {
                    if (currentPos >= lecturers.size()
                            || lecturers.get(currentPos).getId() == lecturer.getId()) {
                        lecturers.add(currentPos, lecturer);
                        notifyItemInserted(currentPos);
                    }
                } else {
                    if (currentPos < lecturers.size()
                            && lecturers.get(currentPos).getId() == lecturer.getId()) {
                        lecturers.remove(currentPos);
                        notifyItemRemoved(currentPos);
                    }
                }
            }
        }
    }

    private int findPosOfLecturerInCurrentList(Lecturer lecturer, int posInCurrentList) {
        for (int i = posInCurrentList; i<lecturers.size(); i++) {
            if (lecturer.getId() == lecturers.get(i).getId()) {
                return i;
            }
        }
        return 0;
    }

    /*
    public void setNewData(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
        notifyDataSetChanged(); //not the best way
    }
    */

    @Override
    public void fetchLecturers(List<Lecturer> lecturers, int numberOfBackendData, String nextUrl) {
        this.lecturers.addAll(lecturers);
        filteredLecturers = lecturers;
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
