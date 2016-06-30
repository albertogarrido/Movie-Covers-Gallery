package net.albertogarrido.moviecovers.searchcovers;

import android.util.Log;

import net.albertogarrido.moviecovers.data.CoversRepository;
import net.albertogarrido.moviecovers.data.entities.MovieCover;

import java.util.List;

/**
 * Created by AlbertoGarrido on 6/29/16.
 */
public class SearchPresenter implements SearchContract.UserActionsListener, CoversRepository.LoadCoversCallback{

    private static final String TAG = SearchPresenter.class.getSimpleName();
    private SearchContract.View view;
    private CoversRepository repository;
    private int page;

    public SearchPresenter(SearchContract.View view, CoversRepository repository) {
        this.view = view;
        this.repository = repository;
        this.page = 1;
    }

    @Override
    public void searchMovies(String query) {
        repository.searchMovies(page, query, this);
    }

    @Override
    public void incrementPage() {
        page++;
    }

    @Override
    public void onCoversLoaded(List<MovieCover> movieCovers) {
        view.populateCovers(movieCovers);
    }

    @Override
    public void onCoversFailed(Exception e) {
        // TODO add feedback to the view
        Log.d(TAG, "Failed search: " + e.getMessage());
    }
}