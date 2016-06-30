package net.albertogarrido.moviecovers.listcovers.tabs.recommended;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.RepositoryInjector;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.detailcover.CoverDetailActivity;
import net.albertogarrido.moviecovers.listcovers.CoversListActivity;
import net.albertogarrido.moviecovers.listcovers.CoversListContract;
import net.albertogarrido.moviecovers.listcovers.tabs.TabFragment;
import net.albertogarrido.moviecovers.util.Config;
import net.albertogarrido.moviecovers.util.CoversAdapter;
import net.albertogarrido.moviecovers.util.EndlessScrollListener;
import net.albertogarrido.moviecovers.util.RecyclerItemListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AlbertoGarrido on 27/6/16.
 */
public class RecommendedFragment
        extends TabFragment
        implements CoversListContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        RecyclerItemListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CoversListContract.UserActionsListener presenter;
    private CoversAdapter mAdapter;


    @Bind(R.id.covers_list) RecyclerView mRecyclerView;
    @Bind(R.id.static_loading_indicator_layout) LinearLayout mStaticLoadingIndicatorLayout;
    @Bind(R.id.error_indicator_layout) LinearLayout mErrorIndicatorLayout;
    @Bind(R.id.covers_list_layout) LinearLayout mCoversListLayout;

    @Bind(R.id.static_loading_indicator) TextView mStaticLoadingIndicator;
    @Bind(R.id.error_indicator) TextView mErrorIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_recommended, container, false);

        ButterKnife.bind(this, mSwipeRefreshLayout);

        setupSwipeRefreshLayout(mSwipeRefreshLayout, this);

        return mSwipeRefreshLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureRecyclerView();
        presenter = new RecommendedCoversPresenter(this, RepositoryInjector.getAPIRepository(getContext()));
        presenter.loadMovieCovers(true);
    }

    @Override
    public void startLoadingIndicator() {
        if (!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(true);
        }
        mStaticLoadingIndicatorLayout.setVisibility(View.VISIBLE);
        mErrorIndicatorLayout.setVisibility(View.GONE);
        mCoversListLayout.setVisibility(View.GONE);
    }

    @Override
    public void stopLoadingIndicator() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mStaticLoadingIndicatorLayout.setVisibility(View.VISIBLE);
        mErrorIndicatorLayout.setVisibility(View.GONE);
        mCoversListLayout.setVisibility(View.GONE);
    }

    @Override
    public void displayNetworkError(String message) {
        mStaticLoadingIndicatorLayout.setVisibility(View.GONE);
        mErrorIndicatorLayout.setVisibility(View.VISIBLE);
        mErrorIndicator.setText(message);
        mCoversListLayout.setVisibility(View.GONE);
    }

    @Override
    public void displayNoDataMessage(String message) {
        mStaticLoadingIndicatorLayout.setVisibility(View.GONE);
        mErrorIndicatorLayout.setVisibility(View.VISIBLE);
        mErrorIndicator.setText(message);
        mCoversListLayout.setVisibility(View.GONE);
    }

    @Override
    public void populateCovers(List<MovieCover> covers) {

        mStaticLoadingIndicatorLayout.setVisibility(View.GONE);
        mErrorIndicatorLayout.setVisibility(View.GONE);
        mCoversListLayout.setVisibility(View.VISIBLE);

        if (mAdapter == null) {
            mAdapter = new CoversAdapter(covers, getContext());
            mAdapter.setType(Config.ADAPTER_TYPE_GRID);
            mAdapter.addRecyclerItemListener(this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.addToList(covers);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Refreshes SwipeRefreshLayout: reset adapter and perform new query
     */
    @Override
    public void onRefresh() {
        mAdapter = null;
        populateCovers(new ArrayList<MovieCover>());
        presenter.loadMovieCovers(true);
    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                presenter.incrementPage();
                presenter.loadMovieCovers(false);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // Do nothing for now
            }
        });
    }

    /**
     * Start activity with shared element animation
     * @param view
     * @param adapterPosition
     */
    @Override
    public void onItemTouch(View view, int adapterPosition) {

        Intent coverDetailIntent = new Intent(getContext(), CoverDetailActivity.class);

        MovieCover cover = mAdapter.getItemsList().get(adapterPosition);

        ImageView mCoverImage = (ImageView) view.findViewById(R.id.grid_cover);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mCoverImage.getDrawable();

        coverDetailIntent.putExtra(CoverDetailActivity.EXTRA_COVER, cover);

        coverDetailIntent.putExtra(CoverDetailActivity.EXTRA_COVER_POSITION, adapterPosition);


        if (cover.isImageLoaded() || bitmapDrawable != null) {

            String name = CoverDetailActivity.SHARED_ELEMENT;

            //necessary step to prevent TransactionTooLargeException when passing huge bitmaps as extra
            CoversListActivity.sCoverCollection.put(0, bitmapDrawable.getBitmap());

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, name);

            getActivity().startActivity(coverDetailIntent, options.toBundle());

        } else {
            Snackbar.make(
                    view,
                    getContext().getResources().getString(R.string.msg_image_not_loaded),
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }
}