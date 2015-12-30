package de.davidartmann.profpedia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import de.davidartmann.profpedia.adapter.viewholder.LecturerDetailViewholder;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Adapter class for the {@link de.davidartmann.profpedia.activity.ProfDetailActivity}'s {@link RecyclerView}.
 * Created by david on 30.12.15.
 */
public class LecturerDetailRecyclerviewAdapter extends RecyclerView.Adapter<LecturerDetailViewholder> {

    private int layout;
    private Lecturer lecturer;
    private Context context;

    public LecturerDetailRecyclerviewAdapter(int layout, Lecturer lecturer, Context context) {
        this.layout = layout;
        this.lecturer = lecturer;
        this.context = context;
    }

    @Override
    public LecturerDetailViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LecturerDetailViewholder(LayoutInflater.from(context).inflate(layout, parent, false), context);
    }

    @Override
    public void onBindViewHolder(LecturerDetailViewholder holder, int position) {
        holder.assignData(lecturer, position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
