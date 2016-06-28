package net.albertogarrido.moviecovers.data;

import net.albertogarrido.moviecovers.data.entities.MovieCover;

import java.util.List;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public interface CoversRepository {

    interface LoadCoversCallback {
        void onCoversLoaded(List<MovieCover> movieCovers);

        void onCoversFailed(Exception e);
    }

    interface SaveCoverCallback {
        void onSaveCoverFailed(Exception error);

        void onSaveCoverSuccess(String message);
    }

    void getFavouriteCovers(LoadCoversCallback callback);

    void getRecommendedCovers(int page, LoadCoversCallback callback);

    void getCover(int coverId, LoadCoversCallback callback);

    void saveCover(MovieCover cover, SaveCoverCallback callback);
}
