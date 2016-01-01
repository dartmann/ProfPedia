package de.davidartmann.profpedia.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.LecturerDetailRecyclerviewAdapter;
import de.davidartmann.profpedia.model.Lecturer;
import de.davidartmann.profpedia.utils.LecturerData;

public class ProfDetailActivity extends AppCompatActivity {

    /*
    private static final String TAG = ProfDetailActivity.class.getSimpleName();
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_detail);
        ImageView imageViewHeader = (ImageView) findViewById(R.id.activity_prof_detail_imageview_header);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_prof_detail_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        }
        Intent intent = getIntent();
        Lecturer lecturer = LecturerData.getInstance(this).getLecturer(intent.getIntExtra("id", 0));
        toolbar.setTitle(String.format("%s"+" "+"%s"+" "+"%s",
                lecturer.getTitle(),
                lecturer.getFirstName(),
                lecturer.getLastName()));
        Picasso.with(this).load(lecturer.getUrlProfileImage()).into(imageViewHeader);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_prof_detail_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LecturerDetailRecyclerviewAdapter(lecturer, this));
    }
}
