package net.albertogarrido.moviecovers.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by AlbertoGarrido on 28/6/16.
 * Note: this is a class I've been using on my projects for a while, it's a modified code of a work found in a github
 * gist.
 * Provides endless scroll to a recycler view when reaching the bottom.
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    public static String TAG = EndlessScrollListener.class.getSimpleName();

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private LinearLayoutManager layoutManager;

    public EndlessScrollListener(LinearLayoutManager layoutManager) {
//        if(Objects.equals(type, Config.ENDLESS_LAYOUT_TYPE_GRID)){
            this.layoutManager = layoutManager;
//        } else {
//            this.layoutManager = (LinearLayoutManager) layoutManager;
//        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            current_page++;
            onLoadMore(current_page);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
