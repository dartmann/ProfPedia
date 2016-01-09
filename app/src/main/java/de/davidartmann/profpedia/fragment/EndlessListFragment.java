package de.davidartmann.profpedia.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.EndlessListAdapter;

/**
 * Fragment for the endless list.
 * Created by david on 09.01.16.
 */
public class EndlessListFragment extends Fragment {

    private OnShowProgressBar onShowProgressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.onShowProgressBar = (OnShowProgressBar) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lecturer_endlesslist, container, false);
        RecyclerView recyclerView =
                (RecyclerView) view.findViewById(R.id.activity_lecturer_endlesslist_recyclerview);
        ProgressBar progressBar =
                (ProgressBar) view.findViewById(R.id.app_bar_navigation_drawer_progressbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new EndlessListAdapter(createDummyData(),
                android.R.layout.simple_list_item_1,
                onShowProgressBar));
        return view;
    }

    private List<String> createDummyData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            data.add("Entry "+i);
        }
        return data;
    }

    public interface OnShowProgressBar {
        void showProgressBar(boolean b);
    }
}
