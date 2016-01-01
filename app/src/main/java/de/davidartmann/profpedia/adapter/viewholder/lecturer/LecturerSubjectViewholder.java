package de.davidartmann.profpedia.adapter.viewholder.lecturer;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.viewholder.LecturerViewholder;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Viewholder for the subject data of a lecturer.
 * Created by david on 01.01.16.
 */
public class LecturerSubjectViewholder extends LecturerViewholder implements View.OnClickListener {

    private static final String TAG = LecturerSubjectViewholder.class.getSimpleName();

    private TextView textViewHeaderDescription;
    private TextView textViewSubjects;
    private Context context;
    private Lecturer lecturer;

    public LecturerSubjectViewholder(View itemView, Context context) {
        super(itemView, context);
        itemView.setOnClickListener(this);
        this.context = context;
        textViewHeaderDescription = (TextView) itemView.findViewById(
                        R.id.recyclerview_lecturer_subject_cardview_textview_headerdescription);
        textViewSubjects = (TextView) itemView.findViewById(
                        R.id.recyclerview_lecturer_subject_cardview_textview_subjects);
    }

    public void assignData(Lecturer lecturer) {
        this.lecturer = lecturer;
        textViewHeaderDescription.setText(R.string.unterricht);
        textViewSubjects.setText("blafoo\nfoo\nbar\nsubject no.42");
    }

    @Override
    public void onClick(View v) {
        //TODO: here we could implement listener on the subjects or other lesson relevant stuff
    }
}
