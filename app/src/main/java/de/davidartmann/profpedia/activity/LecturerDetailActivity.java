package de.davidartmann.profpedia.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.lecturer.LecturerDetailAdapter;
import de.davidartmann.profpedia.adapter.lecturer.viewholder.lecturer.LecturerContactViewholder;
import de.davidartmann.profpedia.model.Lecturer;

public class LecturerDetailActivity extends AppCompatActivity {

    /*
    private static final String TAG = LecturerDetailActivity.class.getSimpleName();
    */
    private boolean fabLikeClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_detail);
        fabLikeClicked = false;
        final ImageView imageViewHeader =
                (ImageView) findViewById(R.id.activity_lecturer_detail_collapseimageview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_lecturer_detail_toolbar);
        AppBarLayout appBarLayout =
                (AppBarLayout) findViewById(R.id.activity_lecturer_detail_appbar);
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(
                        R.id.activity_lecturer_detail_collapsing_toolbar);
        final FloatingActionButton fabLike =
                (FloatingActionButton) findViewById(R.id.activity_lecturer_detail_fabLike);
        final FloatingActionButton fabEmail =
                (FloatingActionButton) findViewById(R.id.activity_lecturer_detail_fabEmail);
        final FloatingActionButton fabCall =
                (FloatingActionButton) findViewById(R.id.activity_lecturer_detail_fabCall);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }
        Intent intent = getIntent();
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
        recyclerView.setAdapter(new LecturerDetailAdapter(lecturer, this, fabCall, fabEmail, fabLike));
        fabLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fabLikeClicked) {
                    fabLikeClicked = true;
                    fabEmail.setVisibility(View.VISIBLE); //why is fabCall also visible, although it has not been set here?
                    fabEmail.animate()
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .scaleX(0.75f).scaleY(0.75f)
                            .translationX(-175.0f)
                            .rotation(-360.0f)
                            .setDuration(400);
                    fabCall.animate()
                            .setInterpolator(new BounceInterpolator())
                            .scaleX(0.875f).scaleY(0.875f);
                    fabLike.animate()
                            .setInterpolator(new BounceInterpolator())
                            .setDuration(500)
                            .scaleX(0.875f).scaleY(0.875f)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    fabCall.animate()
                                            .setInterpolator(new AccelerateDecelerateInterpolator())
                                            .scaleX(0.75f).scaleY(0.75f)
                                            .translationX(-350.0f)
                                            .rotation(-360.0f)
                                            .setDuration(400);
                                    fabLike.animate()
                                            .setInterpolator(new BounceInterpolator())
                                            .setDuration(500)
                                            .scaleX(0.75f).scaleY(0.75f);
                                }
                            });
                } else {
                    fabLikeClicked = false;
                    fabCall.animate()
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .translationXBy(350.0f)
                            .rotation(360.0f)
                            .setDuration(400)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    fabLike.animate()
                                            .setInterpolator(new BounceInterpolator())
                                            .setDuration(400)
                                            .scaleX(0.875f).scaleY(0.875f)
                                            .withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            fabEmail.animate()
                                                    .setInterpolator(new AccelerateDecelerateInterpolator())
                                                    .translationXBy(175.0f)
                                                    .rotation(360.0f)
                                                    .setDuration(400)
                                                    .withEndAction(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            fabLike.animate()
                                                                    .setInterpolator(new BounceInterpolator())
                                                                    .setDuration(400)
                                                                    .scaleX(1.0f).scaleY(1.0f)
                                                                    .withEndAction(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    fabCall.animate().scaleX(1.0f).scaleY(1.0f);
                                                                    fabEmail.animate().scaleX(1.0f).scaleY(1.0f);
                                                                }
                                                            });
                                                        }
                                                    });
                                        }
                                    });
                                }
                            });
                }
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (fabLikeClicked) {
                    fabCall.setScaleX(0.75f);
                    fabCall.setScaleY(0.75f);
                    fabEmail.setScaleX(0.75f);
                    fabEmail.setScaleY(0.75f);
                    fabLike.setScaleX(0.75f);
                    fabLike.setScaleY(0.75f);
                }
                /*
                if (!(appBarLayout.getHeight() / 2 < -verticalOffset)) {
                    //fab.setVisibility(View.VISIBLE);
                    if (fabLikeClicked) {
                        fabCall.setScaleX(0.75f);
                        fabCall.setScaleY(0.75f);
                        fabEmail.setScaleX(0.75f);
                        fabEmail.setScaleY(0.75f);
                        fabLike.setScaleX(0.75f);
                        fabLike.setScaleY(0.75f);
                    } else {
                        Log.d("FABLIKE", "not clicked");
                    }
                } else {
                    Log.d("SIZE", "not in area");
                }
                */
            }
        });
    }

    private int getScreenOrientation() {
        return getResources().getConfiguration().orientation;
    }
}
