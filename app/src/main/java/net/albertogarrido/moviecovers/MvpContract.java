package net.albertogarrido.moviecovers;

import android.content.Context;

/**
 * Created by AlbertoGarrido on 27/6/16.
 */
public interface MvpContract {

    interface View {
        Context getContext();
    }

    interface Listener {
    }
}