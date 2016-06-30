package net.albertogarrido.moviecovers.detailcover;

import android.graphics.Bitmap;

import net.albertogarrido.moviecovers.MvpContract;
import net.albertogarrido.moviecovers.data.entities.MovieCover;

/**
 * Created by AlbertoGarrido on 6/30/16.
 */
public interface CoverDetailContract extends MvpContract{
    interface View extends MvpContract.View {
        void populateCoverImage(Bitmap bitmap);

        void populateMovieDetails(MovieCover cover);
    }

    interface UserActionsListener extends MvpContract.Listener {
        void parseData();
    }
}
