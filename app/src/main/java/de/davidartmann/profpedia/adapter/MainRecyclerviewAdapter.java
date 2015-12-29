package de.davidartmann.profpedia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.davidartmann.profpedia.adapter.viewholder.MainViewHolder;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Created by david on 25.12.15.
 */
public class MainRecyclerviewAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private int layout;
    private List<Lecturer> lecturers;
    private Context context;

    public MainRecyclerviewAdapter(int layout, List<Lecturer> lecturers, Context context) {
        this.layout = layout;
        this.lecturers = lecturers;
        this.context = context;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MainViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.assignData(lecturers.get(position));
    }

    @Override
    public int getItemCount() {
        return lecturers.size();
    }
}
