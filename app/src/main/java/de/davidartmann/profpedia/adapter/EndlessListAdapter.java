package de.davidartmann.profpedia.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import de.davidartmann.profpedia.R;
import de.davidartmann.profpedia.adapter.viewholder.EndlessListViewholder;
import de.davidartmann.profpedia.fragment.EndlessListFragment;

/**
 * Created by david on 09.01.16.
 */
public class EndlessListAdapter extends RecyclerView.Adapter<EndlessListViewholder> {

    private List<String> items;
    private int layout;
    private Handler handler;
    private EndlessListFragment.OnShowProgressBar onShowProgressBar;

    public EndlessListAdapter(List<String> items, int layout,
                              EndlessListFragment.OnShowProgressBar onShowProgressBar) {
        this.items = items;
        this.layout = layout;
        this.handler = new Handler();
        this.onShowProgressBar = onShowProgressBar;
    }

    @Override
    public EndlessListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new EndlessListViewholder(view);
    }

    @Override
    public void onBindViewHolder(EndlessListViewholder holder, int position) {
        if (position >= getItemCount()-1) {
            loadMoreData(holder, position+1, 20);
        }
        holder.assignData(items.get(position));
    }

    private void loadMoreData(final EndlessListViewholder viewholder, final int startIndex, final int numberOfNewData) {
        new Thread() {
            @Override
            public void run() {
                onShowProgressBar.showProgressBar(true);
                waitForSomeTime();
                for (int i = startIndex; i<(startIndex+numberOfNewData); i++) {
                    items.add("Entry "+i);
                }
                onShowProgressBar.showProgressBar(false);
                informAdapter();
            }
        }.start();
    }

    private void informAdapter() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    private void waitForSomeTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
