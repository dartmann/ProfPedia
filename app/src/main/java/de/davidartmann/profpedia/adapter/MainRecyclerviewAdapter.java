package de.davidartmann.profpedia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.davidartmann.profpedia.adapter.viewholder.MainViewHolder;
import de.davidartmann.profpedia.fragment.LecturerListFragment;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Adapter for the {@link LecturerListFragment}'s {@link RecyclerView}.
 * Created by david on 25.12.15.
 */
public class MainRecyclerviewAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private int layout;
    private List<Lecturer> lecturers;
    private Context context;
    private LecturerListFragment.OnLecturerClicked onLecturerClicked;
    private int screenOrientation;

    public MainRecyclerviewAdapter(int layout, List<Lecturer> lecturers, Context context,
                                   LecturerListFragment.OnLecturerClicked onLecturerClicked,
                                   int screenOrientation) {
        this.layout = layout;
        this.lecturers = lecturers;
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

    public void setNewData(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
        notifyDataSetChanged();
    }
}
