package net.albertogarrido.moviecovers.detailcover;

import android.os.Bundle;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.listcovers.SearchableActivity;

public class CoverDetailActivity extends SearchableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_detail);
    }
}
