package de.davidartmann.profpedia.adapter.lecturer.viewholder.lecturer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.lecturer.viewholder.LecturerViewholder;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Viewholder for the contact data of the lecturer.
 * Created by david on 01.01.16.
 */
public class LecturerContactViewholder extends LecturerViewholder
        implements View.OnClickListener {

    private static final String TAG = LecturerContactViewholder.class.getSimpleName();

    private TextView textViewHeaderDescription;
    private ImageView imageViewEmail, imageViewPhone;
    private TextView textViewEmail, textViewPhone;
    private Context context;
    private Lecturer lecturer;

    public LecturerContactViewholder(View itemView, Context context) {
        super(itemView, context);
        this.context = context;
        itemView.setOnClickListener(this);
        textViewHeaderDescription = (TextView) itemView.
                findViewById(
                        R.id.recyclerview_lecturer_contact_cardview_textview_headerdescription);
        imageViewEmail = (ImageView) itemView
                .findViewById(R.id.recyclerview_lecturer_contact_cardview_imageview_email);
        textViewEmail = (TextView) itemView
                .findViewById(R.id.recyclerview_lecturer_contact_cardview_textview_email);
        imageViewPhone = (ImageView) itemView
                .findViewById(R.id.recyclerview_lecturer_contact_cardview_imageview_phone);
        textViewPhone = (TextView) itemView
                .findViewById(R.id.recyclerview_lecturer_contact_cardview_textview_phone);
        imageViewPhone.setOnClickListener(this);
        textViewPhone.setOnClickListener(this);
        imageViewEmail.setOnClickListener(this);
        textViewEmail.setOnClickListener(this);
    }

    public void assignData(Lecturer lecturer) {
        this.lecturer = lecturer;
        textViewHeaderDescription.setText(R.string.kontakt);
        imageViewEmail.setImageDrawable(context.getDrawable(R.drawable.email));
        textViewEmail.setText(lecturer.getEmail());
        imageViewPhone.setImageDrawable(context.getDrawable(R.drawable.call));
        textViewPhone.setText(lecturer.getPhone());
    }

    @Override
    public void onClick(View v) {
        if (lecturer != null) {
            if (context != null) {
                Intent intent = null;
                int id = v.getId();
                if (id == R.id.recyclerview_lecturer_contact_cardview_imageview_email
                        || id == R.id.recyclerview_lecturer_contact_cardview_textview_email) {
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, lecturer.getEmail());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    context.startActivity(Intent.createChooser(intent, "Email versenden mit"));
                } else if (id == R.id.recyclerview_lecturer_contact_cardview_imageview_phone
                        || id == R.id.recyclerview_lecturer_contact_cardview_textview_phone) {
                    intent = new Intent(Intent.ACTION_CALL,
                            Uri.parse("tel:" + lecturer.getPhone().trim()));
                    if (ActivityCompat
                            .checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    context.startActivity(Intent.createChooser(intent, "Anrufen mit"));
                }
            } else {
                Log.w(TAG, "context is null");
            }
        } else {
            Log.w(TAG, "lecturer is null");
        }
    }
}
