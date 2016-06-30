package net.albertogarrido.moviecovers.listcovers;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.listcovers.tabs.ViewPagerAdapter;
import net.albertogarrido.moviecovers.listcovers.tabs.favourites.FavouritesFragment;
import net.albertogarrido.moviecovers.listcovers.tabs.recommended.RecommendedFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoversListActivity extends SearchableActivity {


    public static SparseArray<Bitmap> sCoverCollection = new SparseArray<>(1);

    @Bind(R.id.view_pager) ViewPager mViewPager;
    @Bind(R.id.tab_layout) TabLayout mTabLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covers_list);

        ButterKnife.bind(this);

        configureToolbar();

        setupViewPager(mViewPager);
        configureTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarTitle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                openSearchScreen(this);
                break;
        }
        return true;
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void setToolbarTitle() {
        mToolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void configureTabs() {
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);

        RecommendedFragment recommendedFragment = new RecommendedFragment();
        adapter.addTitle(getResources().getString(R.string.fragment_title_recommended));
        adapter.addFragment(recommendedFragment);

        FavouritesFragment favouritesFragment = new FavouritesFragment();
        adapter.addTitle(getResources().getString(R.string.fragment_title_favourites));
        adapter.addFragment(favouritesFragment);

        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);
        viewPager.setAdapter(adapter);
    }
}
