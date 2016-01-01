package de.davidartmann.profpedia.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.activity.LecturerDetailActivity;
import de.davidartmann.profpedia.model.Lecturer;

/**
 * Viewholder class for the main activity.
 * Created by david on 25.12.15.
 */
public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView;
    private Context context;
    //private Palette.Swatch swatch;

    public MainViewHolder(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.context = context;
        imageView = (ImageView) itemView.findViewById(R.id.recyclerview_main_cardview_imageview);
        textView = (TextView) itemView.findViewById(R.id.recyclerview_main_cardview_textview);
    }

    public void assignData(final Lecturer lecturer) {
        Picasso.with(context).load(lecturer.getUrlProfileImage()).into(imageView);
        /*
        Picasso.with(context).load(lecturer.getUrlProfileImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        swatch = palette.getLightVibrantSwatch();
                        if (swatch != null) {
                            Log.i(TAG, String.valueOf(swatch.getRgb()));
                            textView.setBackgroundColor(swatch.getRgb());
                        } else {
                            Log.d(TAG, "Palette swatch was null");
                        }
                    }
                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        */
        if (lecturer.getTitle().isEmpty() || lecturer.getTitle().equals(" ")) {
            textView.setText(String.format("%s", lecturer.getLastName().trim()));
        } else {
            textView.setText(String.format("%s" + " " + "%s", lecturer.getTitle(), lecturer.getLastName()));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, LecturerDetailActivity.class);
        intent.putExtra("id", getAdapterPosition());
        context.startActivity(intent);
        //Log.d("TAG", "onClick " + getAdapterPosition() + " " + textView.getText());
    }
}
