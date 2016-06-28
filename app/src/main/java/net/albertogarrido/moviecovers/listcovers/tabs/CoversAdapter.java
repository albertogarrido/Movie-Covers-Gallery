package net.albertogarrido.moviecovers.listcovers.tabs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.entities.MovieCover;
import net.albertogarrido.moviecovers.util.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class CoversAdapter extends RecyclerView.Adapter<CoversAdapter.MovieCoverViewHolder> {

    private List<MovieCover> coversList;
    private Context context;

    public CoversAdapter(List<MovieCover> categoriesList, Context context) {
        this.coversList = categoriesList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return coversList.size();
    }

    @Override
    public void onBindViewHolder(MovieCoverViewHolder holder, int i) {
        MovieCover cover = coversList.get(i);
        String coverFullPath = Config.IMAGE_BASE_URL + Config.POSTER_SIZE_W500 + cover.getMovieCover();

        Log.d("ADAPTER" , cover.getTitle());

        Picasso.with(context)
                .load(coverFullPath)

                .fit().centerCrop()
                .into(holder.cover);
//        holder.title.setText(cover.getOriginalTitle());
    }

    @Override
    public MovieCoverViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.cover_item, viewGroup,false
        );
        return new MovieCoverViewHolder(itemView);
    }

    public void addToList(List<MovieCover> covers) {
        if(covers.size() == 0){
            coversList = new ArrayList<>();
        } else {
            coversList.addAll(covers);
        }
    }

    public static class MovieCoverViewHolder
            extends RecyclerView.ViewHolder {

        @Bind(R.id.cover) ImageView cover;
//        @Bind(R.id.title) TextView title;

        public MovieCoverViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}