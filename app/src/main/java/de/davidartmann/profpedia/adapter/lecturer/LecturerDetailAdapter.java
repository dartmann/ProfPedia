package de.davidartmann.profpedia.adapter.lecturer;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.activity.LecturerDetailActivity;
import de.davidartmann.profpedia.adapter.lecturer.viewholder.lecturer.LecturerAdressViewholder;
import de.davidartmann.profpedia.adapter.lecturer.viewholder.lecturer.LecturerContactViewholder;
import de.davidartmann.profpedia.adapter.lecturer.viewholder.lecturer.LecturerSubjectViewholder;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Adapter class for the {@link LecturerDetailActivity}'s {@link RecyclerView}.
 * Created by david on 30.12.15.
 */
public class LecturerDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = LecturerDetailAdapter.class.getSimpleName();

    private Lecturer lecturer;
    private Context context;
    private FloatingActionButton fabCall;
    private FloatingActionButton fabEmail;
    private FloatingActionButton fabLike;

    public LecturerDetailAdapter(Lecturer lecturer, Context context,
                                 FloatingActionButton fabCall,
                                 FloatingActionButton fabEmail,
                                 FloatingActionButton fabLike) {
        this.lecturer = lecturer;
        this.context = context;
        this.fabCall = fabCall;
        this.fabEmail = fabEmail;
        this.fabLike = fabLike;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new LecturerAdressViewholder(LayoutInflater.from(context)
                        .inflate(R.layout.cardview_lecturer_address,
                                parent, false), context);
            case 1:
                return new LecturerContactViewholder(LayoutInflater.from(context)
                        .inflate(R.layout.cardview_lecturer_contact,
                                parent, false), context, fabCall, fabEmail, fabLike);
            case 2:
                return new LecturerSubjectViewholder(LayoutInflater.from(context)
                        .inflate(R.layout.cardview_lecturer_subject,
                                parent, false), context);
            default:
                Log.w(TAG, "default path in onCreateViewHolder");
                //TODO: ask what's the improved way here?!
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                ((LecturerAdressViewholder) holder).assignData(lecturer);
                break;
            case 1:
                ((LecturerContactViewholder) holder).assignData(lecturer);
                break;
            case 2:
                ((LecturerSubjectViewholder) holder).assignData(lecturer);
                break;
            default:
                Log.w(TAG, "default path in onBindViewHolder with position: "+position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    /**
     * Because we have different viewtypes, we return different positions.
     *
     * @param position the position of the card
     * @return integer value identifying the type of the view needed
     * to represent the item at position. Type codes need not be contiguous.
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
