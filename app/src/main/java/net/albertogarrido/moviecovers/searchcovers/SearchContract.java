package net.albertogarrido.moviecovers.searchcovers;

import net.albertogarrido.moviecovers.MvpContract;
import net.albertogarrido.moviecovers.data.entities.MovieCover;

import java.util.List;

/**
 * Created by AlbertoGarrido on 6/29/16.
 */
public interface SearchContract {
    interface View extends MvpContract.View {//
        void populateCovers(List<MovieCover> covers);
    }

    interface UserActionsListener extends MvpContract.Listener {
        void searchMovies(String query);
        void incrementPage();
    }
}
