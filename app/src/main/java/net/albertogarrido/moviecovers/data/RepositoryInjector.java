package net.albertogarrido.moviecovers.data;

import android.content.Context;

import net.albertogarrido.moviecovers.util.Utils;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class RepositoryInjector {

    private RepositoryInjector() {
    }

    /**
     * Very simple repository switcher:
     * Returns the API repository if internet connection or the DB repository if no internet connection
     */
    public static CoversRepository getRepository(Context context) {
        if (Utils.isConnectionActive(context)) {
            return getAPIRepository(context);
        } else {
            return getDBRepository(context);
        }
    }

    public static CoversRepository getDBRepository(Context context) {
        return new DBRepository(context);
    }

    public static CoversRepository getAPIRepository(Context context) {
        return new APIRepository(context);
    }
}
