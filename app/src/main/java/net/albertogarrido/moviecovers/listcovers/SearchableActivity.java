package net.albertogarrido.moviecovers.listcovers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import net.albertogarrido.moviecovers.searchcovers.SearchActivity;

/**
 * Created by AlbertoGarrido on 27/6/16.
 */
public abstract class SearchableActivity extends AppCompatActivity {
    protected void openSearchScreen(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
