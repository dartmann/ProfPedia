package de.davidartmann.profpedia.fragment.mensa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.activity.contract.OnFoodClickListener;
import de.davidartmann.profpedia.adapter.mensa.MensaFoodListAdapter;
import de.davidartmann.profpedia.fragment.mensa.contract.IProgressBar;

/**
 * Fragment for the wednesday.
 * Created by david on 11.01.16.
 */
public class MensaWednesdayFragment extends Fragment {

    private IProgressBar iProgressBar;
    private OnFoodClickListener onFoodClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iProgressBar = (IProgressBar) context;
        onFoodClickListener = (OnFoodClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mensa_wednesday, container, false);
        RecyclerView recyclerView =
                (RecyclerView) view.findViewById(R.id.fragment_mensa_wednesday_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MensaFoodListAdapter mensaFoodListAdapter =
                new MensaFoodListAdapter(R.layout.cardview_mensa_meal, getContext(),
                        Calendar.WEDNESDAY, iProgressBar, onFoodClickListener);
        recyclerView.setAdapter(mensaFoodListAdapter);
        return view;
    }
}
