package net.albertogarrido.moviecovers.detailcover;

import android.os.Bundle;

import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.listcovers.CoversListActivity;

/**
 * Created by AlbertoGarrido on 6/30/16.
 */
public class CoverDetailPresenter implements CoverDetailContract.UserActionsListener {

    private final CoverDetailContract.View view;

    public CoverDetailPresenter(CoverDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void parseData() {
        Bundle extras = ((CoverDetailActivity) view).getIntent().getExtras();
        MovieCover cover = extras.getParcelable(CoverDetailActivity.EXTRA_COVER);
        view.populateCoverImage(CoversListActivity.sCoverCollection.get(0));
        view.populateMovieDetails(cover);
    }
}