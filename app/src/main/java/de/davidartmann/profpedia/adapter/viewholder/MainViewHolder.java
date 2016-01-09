package de.davidartmann.profpedia.adapter.viewholder;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.fragment.LecturerListFragment;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Viewholder class for the main activity.
 * Created by david on 25.12.15.
 */
public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView;
    private Context context;
    private LecturerListFragment.IOnLecturerClicked IOnLecturerClicked;
    private int screenOrientation;
    private Lecturer lecturer;

    public MainViewHolder(View itemView, Context context,
                          LecturerListFragment.IOnLecturerClicked IOnLecturerClicked,
                          int screenOrientation) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.context = context;
        imageView = (ImageView) itemView.findViewById(R.id.recyclerview_main_cardview_imageview);
        textView = (TextView) itemView.findViewById(R.id.recyclerview_main_cardview_textview);
        this.IOnLecturerClicked = IOnLecturerClicked;
        this.screenOrientation = screenOrientation;
    }

    public void assignData(final Lecturer lecturer) {
        this.lecturer = lecturer;
        Picasso.with(context).load(lecturer.getUrlProfileImage()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    imageView.setImageBitmap(
                            Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight()-80));
                }
            }

            @Override
            public void onError() {
                Log.e("PICASSO ERROR", "Problem with callback while loading image");
            }
        });
        if (lecturer.getTitle().isEmpty() || lecturer.getTitle().equals(" ")) {
            textView.setText(String.format("%s", lecturer.getLastName().trim()));
        } else {
            textView.setText(String.format("%s" + " " + "%s", lecturer.getTitle(), lecturer.getLastName()));
        }
    }

    @Override
    public void onClick(View v) {
        //IOnLecturerClicked.onLecturerClick(getAdapterPosition());
        IOnLecturerClicked.onLecturerClick(lecturer);
    }
}
