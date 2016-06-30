package net.albertogarrido.moviecovers.listcovers.tabs.recommended;

import android.util.Log;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.CoversRepository;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.listcovers.CoversListContract;
import net.albertogarrido.moviecovers.util.Utils;

import java.util.List;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class RecommendedCoversPresenter implements CoversListContract.UserActionsListener, CoversRepository.LoadCoversCallback {

    private CoversListContract.View view;
    private CoversRepository repository;
    private int page;

    public RecommendedCoversPresenter(CoversListContract.View view, CoversRepository repository) {
        this.view = view;
        this.repository = repository;
        this.page = 1;
    }

    @Override
    public void loadMovieCovers() {
        if (Utils.isConnectionActive(view.getContext())) {
            repository.getRecommendedCovers(page, this);
            view.startLoadingIndicator();
        } else {
            view.stopLoadingIndicator();
            view.displayNetworkError(view.getContext().getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void incrementPage() {
        this.page++;
    }

    @Override
    public void onCoversLoaded(List<MovieCover> movieCovers) {
        view.stopLoadingIndicator();
        view.populateCovers(movieCovers);
    }

    @Override
    public void onCoversFailed(Exception e) {
        view.displayNoDataMessage(e.getMessage());
    }
}
