package de.davidartmann.profpedia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.davidartmann.profpedia.adapter.viewholder.MainViewHolder;
import de.davidartmann.profpedia.async.LoadLecturerFromNetwork;
import de.davidartmann.profpedia.fragment.LecturerListFragment;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Adapter for the {@link LecturerListFragment}'s {@link RecyclerView}.
 * Created by david on 25.12.15.
 */
public class MainRecyclerviewAdapter extends RecyclerView.Adapter<MainViewHolder>
        implements LoadLecturerFromNetwork.IGetLecturerDataFromNetwork {

    private int layout;
    private List<Lecturer> lecturers;
    private Context context;
    private LecturerListFragment.OnLecturerClicked onLecturerClicked;
    private int screenOrientation;

    public MainRecyclerviewAdapter(int layout, List<Lecturer> lecturers, Context context,
                                   LecturerListFragment.OnLecturerClicked onLecturerClicked,
                                   int screenOrientation) {
        this.layout = layout;
        //this.lecturers = lecturers;
        this.context = context;
        this.onLecturerClicked = onLecturerClicked;
        this.screenOrientation = screenOrientation;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MainViewHolder(view, context, onLecturerClicked, screenOrientation);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.assignData(lecturers.get(position));
    }

    @Override
    public int getItemCount() {
        return lecturers.size();
    }

    //TODO: fix the search to work!!!
    public void filter(String query) {
        int posInCurrentList = 0;
        for(int posInOrigList = 0; posInOrigList < lecturers.size(); posInOrigList++) {
            Lecturer lecturer = lecturers.get(posInOrigList);
            posInCurrentList = findPosOfLecturerInCurrentList(lecturer, posInCurrentList);
            if (lecturer.getLastName().toLowerCase().startsWith(query.toLowerCase())) {
                if (posInCurrentList >= lecturers.size()
                        || lecturers.get(posInCurrentList).getId() == lecturer.getId()) {
                    lecturers.add(posInCurrentList, lecturer);
                    notifyItemInserted(posInCurrentList);
                }
            } else {
                if (posInCurrentList < lecturers.size()
                        && lecturers.get(posInCurrentList).getId() == lecturer.getId()) {
                    lecturers.remove(posInCurrentList);
                    notifyItemRemoved(posInCurrentList);
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

    public void setNewData(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
        notifyDataSetChanged(); //not the best way
    }

    @Override
    public void getLecturers(List<Lecturer> lecturers) {

    }
}
