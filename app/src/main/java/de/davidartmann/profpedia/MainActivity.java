package de.davidartmann.profpedia;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.davidartmann.profpedia.adapter.MainRecyclerviewAdapter;
import de.davidartmann.profpedia.model.Lecturer;
import de.davidartmann.profpedia.utils.LecturerData;

public class MainActivity extends AppCompatActivity /*implements MainRecyclerviewAdapter.OnItemClickListener*/ {

    LecturerData lecturerData;
    List<Lecturer> lecturers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lecturerData = new LecturerData(this);
        lecturers = lecturerData.getLecturers();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sglm);
        MainRecyclerviewAdapter mra = new MainRecyclerviewAdapter(R.layout.recyclerview_main_cardview,
                lecturers, this);
        recyclerView.setAdapter(mra);
    }
}
