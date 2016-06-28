package net.albertogarrido.moviecovers.listcovers.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.listcovers.CoversListContract;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public abstract class TabFragment extends Fragment implements CoversListContract.View {

    protected void setupSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setOnRefreshListener(listener);
        swipeRefreshLayout.setDistanceToTriggerSync(180);// in dips
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary_700));
    }

    protected void startRefreshing(final SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    protected void stopRefreshing(SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
