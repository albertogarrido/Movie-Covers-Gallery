package net.albertogarrido.moviecovers.searchcovers;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.RepositoryInjector;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.detailcover.CoverDetailActivity;
import net.albertogarrido.moviecovers.listcovers.CoversListActivity;
import net.albertogarrido.moviecovers.util.Config;
import net.albertogarrido.moviecovers.util.CoversAdapter;
import net.albertogarrido.moviecovers.util.EndlessScrollListener;
import net.albertogarrido.moviecovers.util.RecyclerItemListener;
import net.albertogarrido.moviecovers.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class SearchActivity extends AppCompatActivity implements SearchContract.View, RecyclerItemListener {

    @Bind(R.id.search_field) EditText mSearchField;
    @Bind(R.id.delete_search_term) ImageView mDeleteSearchText;
    @Bind(R.id.search_recycler) RecyclerView mSearchRecycler;

    private SearchContract.UserActionsListener mPresenter;
    private CoversAdapter mAdapter;
    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    /**
     * Closes the current activity
     *
     * @param v
     */
    @OnClick(R.id.back_arrow)
    public void dismissSearch(View v) {
        finish();
    }

    /**
     * Clean the search field
     *
     * @param v
     */
    @OnClick(R.id.delete_search_term)
    public void cleanSearchText(View v) {
        mSearchField.setText("");
        Utils.openKeyboard(getContext(), mSearchField);
    }

    /**
     * Listener for the search button in the keyboard
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @OnEditorAction(R.id.search_field)
    public boolean searchCovers(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String inputQuery = v.getText() != null ? v.getText().toString() : "";
            if (!"".equals(inputQuery)) {
                Utils.closeKeyboard(getContext(), mSearchField);
            }
            return true;
        }
        return false;
    }

    /**
     * Listener before typing, enables delete text action
     *
     * @param charSequence
     */
    @OnTextChanged(value = R.id.search_field, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
    public void beforeTextChangedShowDeleteButton(CharSequence charSequence) {
        mDeleteSearchText.setVisibility(View.VISIBLE);
    }

    /**
     * Listener after typing, executes live search. If field empty disables text delete action
     *
     * @param inputText
     */
    @OnTextChanged(value = R.id.search_field, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChanged(CharSequence inputText) {

        if (inputText.length() == 0) {
            mDeleteSearchText.setVisibility(View.GONE);
        } else if (inputText.length() > 2) {
            if (Utils.isConnectionActive(getContext())) {
                configureRecyclerView();
                // TODO add feedback to the view
//                startLoadingIndicator();
                mPresenter = new SearchPresenter(this, RepositoryInjector.getAPIRepository(getContext()));
                mQuery = inputText.toString();
                mPresenter.searchMovies(mQuery);
            } else {
                // TODO add feedback to the view
//                stopLoadingIndicator();
//                displayNetworkError(getResources().getString(R.string.network_error));
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void configureRecyclerView() {
        mSearchRecycler.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchRecycler.setLayoutManager(mLayoutManager);

        mSearchRecycler.addOnScrollListener(new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.incrementPage();
                mPresenter.searchMovies(mQuery);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_SETTLING && newState != RecyclerView.SCROLL_STATE_IDLE) {
                    Utils.closeKeyboard(getContext(), recyclerView);
                }
            }
        });
    }

    @Override
    public void populateCovers(List<MovieCover> covers) {
        mSearchRecycler.setVisibility(View.VISIBLE);
        mAdapter = new CoversAdapter(covers, getContext());
        mAdapter.addRecyclerItemListener(this);
        mAdapter.setType(Config.ADAPTER_TYPE_LIST);
        mSearchRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemTouch(View view, int adapterPosition) {


        Intent coverDetailIntent = new Intent(getContext(), CoverDetailActivity.class);

        MovieCover cover = mAdapter.getItemsList().get(adapterPosition);

        ImageView mCoverImage = (ImageView) view.findViewById(R.id.search_cover);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mCoverImage.getDrawable();

        coverDetailIntent.putExtra(CoverDetailActivity.EXTRA_COVER, cover);

        coverDetailIntent.putExtra(CoverDetailActivity.EXTRA_COVER_POSITION, adapterPosition);


        if (cover.isImageLoaded() || bitmapDrawable != null) {

            String name = CoverDetailActivity.SHARED_ELEMENT;

            //necessary step to prevent TransactionTooLargeException when passing huge bitmaps as extra
            CoversListActivity.sCoverCollection.put(0, bitmapDrawable.getBitmap());

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, mCoverImage, name);

            startActivity(coverDetailIntent, options.toBundle());

        } else {
            Snackbar.make(
                    view,
                    getContext().getResources().getString(R.string.msg_image_not_loaded),
                    Snackbar.LENGTH_SHORT
            ).show();
        }
    }

}