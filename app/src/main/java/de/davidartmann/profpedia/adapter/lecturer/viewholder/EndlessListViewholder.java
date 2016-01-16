package de.davidartmann.profpedia.adapter.lecturer.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
