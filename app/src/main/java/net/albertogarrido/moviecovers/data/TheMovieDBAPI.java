package net.albertogarrido.moviecovers.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.albertogarrido.moviecovers.data.entities.ApiResponse;
import net.albertogarrido.moviecovers.util.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public interface TheMovieDBAPI {

    @GET(Config.TOP_RATED_MOVIES)
    Call<ApiResponse> getTopRatedMovies (
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.ENDPOINT_API_URL + Config.VERSION)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

}
