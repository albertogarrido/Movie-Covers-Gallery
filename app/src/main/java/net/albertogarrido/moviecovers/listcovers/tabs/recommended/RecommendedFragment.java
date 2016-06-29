package net.albertogarrido.moviecovers.listcovers.tabs.recommended;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.RepositoryInjector;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.listcovers.CoversListContract;
import net.albertogarrido.moviecovers.listcovers.tabs.CoversAdapter;
import net.albertogarrido.moviecovers.listcovers.tabs.TabFragment;
import net.albertogarrido.moviecovers.util.Config;
import net.albertogarrido.moviecovers.util.EndlessScrollListener;
import net.albertogarrido.moviecovers.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AlbertoGarrido on 27/6/16.
 */
public class RecommendedFragment
        extends TabFragment
        implements CoversListContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CoversListContract.UserActionsListener presenter;
    private CoversAdapter adapter;

    @Bind(R.id.covers_list) RecyclerView mRecyclerView;
//    @Bind(R.id.static_loading_indicator) TextView staticLoadingIndicator;
//    @Bind(R.id.error_indicator) TextView errorIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_recommended, container, false);

        ButterKnife.bind(this, mSwipeRefreshLayout);

        setupSwipeRefreshLayout(mSwipeRefreshLayout, this);

        return mSwipeRefreshLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Utils.isConnectionActive(getActivity())) {
            configureRecyclerView();
            startLoadingIndicator();
            presenter = new RecommendedCoversPresenter(this, RepositoryInjector.getAPIRepository(getContext()));
            presenter.loadMovieCovers();
        } else {
            stopLoadingIndicator();
            displayNetworkError(getResources().getString(R.string.network_error));
        }
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

        mRecyclerView.setVisibility(View.VISIBLE);
    //        staticLoadingIndicator.setVisibility(View.GONE);
    //        errorIndicator.setVisibility(View.GONE);

        if (adapter == null) {
            adapter = new CoversAdapter(covers, getContext());
            adapter.setType(Config.ADAPTER_TYPE_GRID);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.addToList(covers);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
//        mRecyclerView.addItemDecoration(itemDecoration);
//        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.incrementPage();
                presenter.loadMovieCovers();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Do nothing for now
            }
        });
    }
}