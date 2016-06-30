package net.albertogarrido.moviecovers.listcovers;

import net.albertogarrido.moviecovers.MvpContract;
import net.albertogarrido.moviecovers.data.entities.MovieCover;

import java.util.List;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public interface CoversListContract {
    interface View extends MvpContract.View {
        void startLoadingIndicator();
        void stopLoadingIndicator();
        void displayNetworkError(String message);
        void displayNoDataMessage(String message);

        void populateCovers(List<MovieCover> covers);
    }

    interface UserActionsListener extends MvpContract.Listener {
        void loadMovieCovers(boolean showLoadingIndicator);
        void incrementPage();
    }
}
