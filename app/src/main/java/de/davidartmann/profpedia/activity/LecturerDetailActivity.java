package de.davidartmann.profpedia.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.lecturer.LecturerDetailAdapter;
import de.davidartmann.profpedia.model.Lecturer;

public class LecturerDetailActivity extends AppCompatActivity {

    /*
    private static final String TAG = LecturerDetailActivity.class.getSimpleName();
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_detail);
        final ImageView imageViewHeader =
                (ImageView) findViewById(R.id.activity_lecturer_detail_collapseimageview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_lecturer_detail_toolbar);
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(
                        R.id.activity_lecturer_detail_collapsing_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }
        Intent intent = getIntent();
        //Lecturer lecturer = LecturerData.getInstance(this).getLecturer(intent.getIntExtra("id", 0));
        Lecturer lecturer = (Lecturer) intent.getSerializableExtra("lecturer");
        toolbar.setTitle(String.format("%s"+" "+"%s"+" "+"%s",
                lecturer.getTitle(),
                lecturer.getFirstName(),
                lecturer.getLastName()));
        Picasso.with(this).load(lecturer.getUrlProfileImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (getScreenOrientation() == Configuration.ORIENTATION_PORTRAIT) {
                    imageViewHeader.setImageBitmap(
                            Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight()-180));
                } else {
                    imageViewHeader.setImageBitmap(
                            Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                    bitmap.getHeight()-270));
                }
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Log.d("getMutedColor", palette.getMutedColor(
                                ContextCompat.getColor(
                                        LecturerDetailActivity.this,
                                        R.color.colorPrimary))+"");
                        collapsingToolbarLayout.setContentScrimColor(
                                palette.getVibrantColor(
                                        ContextCompat.getColor(
                                                LecturerDetailActivity.this,
                                                R.color.colorPrimary)));
                        Log.d("getDarkMutedColor", palette.getDarkMutedColor(
                                ContextCompat.getColor(
                                        LecturerDetailActivity.this,
                                        R.color.colorPrimaryDark))+"");
                        collapsingToolbarLayout.setStatusBarScrimColor(
                                palette.getDarkVibrantColor(
                                        ContextCompat.getColor(
                                                LecturerDetailActivity.this,
                                                R.color.colorPrimaryDark)));
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
        RecyclerView recyclerView =
                (RecyclerView) findViewById(R.id.activity_lecturer_detail_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LecturerDetailAdapter(lecturer, this));
    }

    private int getScreenOrientation() {
        return getResources().getConfiguration().orientation;
    }
}
