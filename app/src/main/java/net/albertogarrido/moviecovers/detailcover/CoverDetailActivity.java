package net.albertogarrido.moviecovers.detailcover;

import android.os.Bundle;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.searchcovers.SearchActivity;

public class CoverDetailActivity extends SearchActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_detail);
    }
}
