package de.davidartmann.profpedia.fragment.mensa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.async.LoadMensaFromNetwork;
import de.davidartmann.profpedia.model.MensaMeal;

/**
 * Fragment for the Monday listview of the menu.
 * Created by david on 11.01.16.
 */
public class MensaMondayFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mensa_monday, container, false);
        TextView textView = (TextView) view.findViewById(R.id.mensafraisn_textview);
        textView.setText("");
        return view;
    }
}
