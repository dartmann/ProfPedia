package de.davidartmann.profpedia.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.fragment.EndlessListFragment;

/**
 * Created by david on 09.01.16.
 */
public class EndlessListViewholder extends RecyclerView.ViewHolder {

    private TextView textView;

    public EndlessListViewholder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(android.R.id.text1);
    }

    public void assignData(String s) {
        textView.setText(s);
    }
}
