package net.albertogarrido.moviecovers.detailcover;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.listcovers.SearchableActivity;

import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoverDetailActivity extends SearchableActivity implements CoverDetailContract.View {

    public static final String EXTRA_COVER = "cover_id";
    public static final String EXTRA_COVER_POSITION = "cover_position";
    public static final String SHARED_ELEMENT = "cover_shared_element";

    private CoverDetailContract.UserActionsListener mPresenter;
    private MovieCover mCover;

    @Bind(R.id.detail_cover) ImageView mDetailCover;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.app_bar_layout) AppBarLayout mAppBarLayout;

    @Bind(R.id.title) TextView mTitle;
    @Bind(R.id.original_title_layout) LinearLayout mOriginalTitleLayout;
    @Bind(R.id.original_title) TextView mOriginalTitle;
    @Bind(R.id.release_date) TextView mReleaseDate;
    @Bind(R.id.overview) TextView mOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_detail);

        ButterKnife.bind(this);

        configureToolbar();
        configureCollapsingListener();

        mPresenter = new CoverDetailPresenter(this);
        mPresenter.parseData();

        showScrollHint();
    }

    private void showScrollHint() {
        Snackbar.make(mAppBarLayout, getResources().getString(R.string.hint_scroll), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finishAfterTransition();
                break;
            case R.id.action_search:
                openSearchScreen(this);
                break;
        }
        return true;
    }

    @Override
    public void populateCoverImage(Bitmap bitmap) {
        mDetailCover.setImageBitmap(bitmap);
    }

    @Override
    public void populateMovieDetails(MovieCover cover) {
        this.mCover = cover;

        if (Objects.equals(mCover.getTitle(), mCover.getOriginalTitle())) {
            mOriginalTitleLayout.setVisibility(View.GONE);
        } else {
            mOriginalTitle.setText(mCover.getOriginalTitle());

        }
        mTitle.setText(mCover.getTitle());
        mReleaseDate.setText(mCover.getReleaseDate());
        mOverview.setText(mCover.getOverview());
    }

    @Override
    public Context getContext() {
        return this;
    }


    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    private void configureCollapsingListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (verticalOffset <= 0 && (verticalOffset * -1) < scrollRange) {
                    setToolbarTitle("");
                } else if ((verticalOffset * -1) == scrollRange) {
                    setToolbarTitle(mCover.getTitle());
                }
            }
        });
    }
}
