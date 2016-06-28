package net.albertogarrido.moviecovers.data;

import android.content.Context;

import net.albertogarrido.moviecovers.data.entities.MovieCover;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class DBRepository implements CoversRepository {

    public DBRepository(Context context) {

    }

    @Override
    public void getFavouriteCovers(LoadCoversCallback callback) {

    }

    @Override
    @Deprecated
    public void getRecommendedCovers(int page, LoadCoversCallback callback) {
        // does nothing for now, this use case would be useful for cached movie covers
    }

    @Override
    public void getCover(int coverId, LoadCoversCallback callback) {

    }

    @Override
    public void saveCover(MovieCover cover, SaveCoverCallback callback) {

    }
}
