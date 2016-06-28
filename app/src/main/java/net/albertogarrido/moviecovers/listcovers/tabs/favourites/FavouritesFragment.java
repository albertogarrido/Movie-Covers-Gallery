package net.albertogarrido.moviecovers.listcovers.tabs.favourites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.listcovers.CoversListContract;
import net.albertogarrido.moviecovers.listcovers.tabs.TabFragment;

import java.util.List;

/**
 * Created by AlbertoGarrido on 27/6/16.
 */
public  class  FavouritesFragment
        extends TabFragment
        implements  CoversListContract.View,
                    SwipeRefreshLayout.OnRefreshListener  {

    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_favourites, container, false);

        setupSwipeRefreshLayout(mSwipeRefreshLayout, this);

        return mSwipeRefreshLayout;
    }

    @Override
    public void startLoadingIndicator() {

    }

    @Override
    public void stopLoadingIndicator() {

    }

    @Override
    public void displayNetworkError(String message) {

    }

    @Override
    public void displayNoDataMessage(String message) {

    }

    @Override
    public void populateCovers(List<MovieCover> covers) {

    }

    @Override
    public void onRefresh() {

    }
}
