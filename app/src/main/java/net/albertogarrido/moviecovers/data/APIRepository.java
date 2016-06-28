package net.albertogarrido.moviecovers.data;

import android.content.Context;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.entities.ApiResponse;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.util.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class APIRepository implements CoversRepository {

    private Context context;

    public APIRepository(Context context) {
        this.context = context;
    }

    @Override
    @Deprecated
    public void getFavouriteCovers(LoadCoversCallback callback) {
        // does nothing for now, this use case would be useful if users could store and retrieve favourites from "our" servers
    }

    @Override
    public void getRecommendedCovers(int page, final LoadCoversCallback callback) {
        final TheMovieDBAPI reviewsApiService = TheMovieDBAPI.retrofit.create(TheMovieDBAPI.class);

        Call<ApiResponse> reviews = reviewsApiService.getTopRatedMovies(
                page,
                Config.API_KEY
        );

        reviews.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() == null) {
                    callback.onCoversFailed(new Exception(context.getResources().getString(R.string.server_error)));
                } else if (response.body().getMovieCovers() == null || response.body().getMovieCovers().size() <= 0) {
                    callback.onCoversFailed(new Exception(context.getResources().getString(R.string.no_results)));
                } else {
                    callback.onCoversLoaded(response.body().getMovieCovers());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onCoversFailed(new Exception(t.getMessage()));
            }

        });
    }

    @Override
    public void getCover(int coverId, LoadCoversCallback callback) {

    }

    @Override
    @Deprecated
    public void saveCover(MovieCover cover, SaveCoverCallback callback) {
        // does nothing for now, this use case would be useful if users could store favourites in "our" servers
    }
}
