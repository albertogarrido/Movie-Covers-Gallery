package net.albertogarrido.moviecovers.listcovers.tabs.recommended;

import android.util.Log;

import net.albertogarrido.moviecovers.data.CoversRepository;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.listcovers.CoversListContract;

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
        repository.getRecommendedCovers(page, this);
    }

    @Override
    public void incrementPage() {
        this.page++;
    }

    @Override
    public void onCoversLoaded(List<MovieCover> movieCovers) {
        view.populateCovers(movieCovers);
//        for(MovieCover movieCover : movieCovers){
//            Log.d("RECOMMENDED", movieCover.getOriginalTitle() + " - " + movieCover.getTitle());
//        }
    }

    @Override
    public void onCoversFailed(Exception e) {
        Log.e("RECOMMENDED", e.getMessage());

    }
}
