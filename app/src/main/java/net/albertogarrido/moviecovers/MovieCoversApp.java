package net.albertogarrido.moviecovers;

import android.app.Application;

/**
 * Created by AlbertoGarrido on 27/6/16.
 */
public class MovieCoversApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        configurePicasso();
    }

    private void configurePicasso() {
        // info and more: http://stackoverflow.com/questions/37098999/caching-images-and-strings-using-retrofit-okhttp-picasso
    }
}