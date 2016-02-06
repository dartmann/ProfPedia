package de.davidartmann.profpedia.adapter.lecturer;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.lecturer.viewholder.LecturerListViewHolder;
import de.davidartmann.profpedia.async.LoadLecturerFromNetwork;
import de.davidartmann.profpedia.fragment.lecturer.LecturerListFragment;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Adapter for the {@link LecturerListFragment}'s {@link RecyclerView}.
 * Created by david on 25.12.15.
 */
public class LecturerListAdapter extends RecyclerView.Adapter<LecturerListViewHolder>
        implements LoadLecturerFromNetwork.IGetLecturerDataFromNetwork {

    private static final String TAG = LecturerListAdapter.class.getSimpleName();

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
        //iProgressBar.showProgressBarForLecturerList(true);
        new LoadLecturerFromNetwork(this, iProgressBar)
                .execute(context.getString(R.string.load_lecturers_base_url));
    }

    @Override
    public LecturerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new LecturerListViewHolder(view, context, iOnLecturerClicked, screenOrientation);
    }

    @Override
    public void onBindViewHolder(LecturerListViewHolder holder, int position) {
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
                //iProgressBar.showProgressBarForLecturerList(true);
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
                //TODO: this is already an extra userthread, so no wrapping needed
                new LoadLecturerFromNetwork(LecturerListAdapter.this, iProgressBar).execute(nextUrl);
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return filteredLecturers.size();
    }

    /**
     * The search implementation for the actionbar.
     * @param query the incoming search string.
     */
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
        if (lecturers != null && numberOfBackendData != 0 /*&& !nextUrl.equals("")*/) { //nextUrl has this value, when we fetched the last url from the backend
            this.lecturers.addAll(lecturers);
            filteredLecturers = new ArrayList<>(this.lecturers); //(List<Lecturer>) ((ArrayList<Lecturer>) this.lecturers).clone();
            this.nextUrl = nextUrl;
            this.numberOfBackendData = numberOfBackendData;
            //iProgressBar.showProgressBarForLecturerList(false);
            /**
             * Because we are in the callback method of the IGetLecturerDataFromNetwork interface,
             * which is called inside the async task, we do not need the handler here.
             */
            notifyDataSetChanged();
        } else {
            Log.w(TAG, "response empty");
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setMessage("Lecturer konnten leider nicht geladen werden.\nNochmal versuchen?")
                    .setTitle("Hinweis")
                    .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new LoadLecturerFromNetwork(LecturerListAdapter.this, iProgressBar)
                                    .execute(context.getString(R.string.load_lecturers_base_url));
                        }
                    })
                    .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    }
}
