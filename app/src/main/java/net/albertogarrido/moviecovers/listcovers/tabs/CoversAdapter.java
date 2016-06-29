package net.albertogarrido.moviecovers.listcovers.tabs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import java.util.Objects;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class CoversAdapter extends RecyclerView.Adapter<CoversAdapter.MovieCoverViewHolder> {

    private List<MovieCover> mCoversList;
    private Context mContext;
    private String type;

    public CoversAdapter(List<MovieCover> categoriesList, Context context) {
        this.mCoversList = categoriesList;
        this.mContext = context;
        this.type = Config.ADAPTER_TYPE_GRID;
    }

    @Override
    public int getItemCount() {
        return mCoversList.size();
    }

    @Override
    public void onBindViewHolder(MovieCoverViewHolder holder, int i) {
        MovieCover cover = mCoversList.get(i);
        String coverFullPath;
        switch (type) {
            case Config.ADAPTER_TYPE_GRID:
                coverFullPath = Config.IMAGE_BASE_URL + Config.POSTER_SIZE_ORIGINAL + cover.getMovieCover();
                Picasso.with(mContext)
                        .load(coverFullPath)
                        .into(holder.cover);
                break;
            case Config.ADAPTER_TYPE_LIST:
                if(cover.getMovieCover() != null) {
                    coverFullPath = Config.IMAGE_BASE_URL + Config.POSTER_SIZE_W342 + cover.getMovieCover();
                    Picasso.with(mContext)
                            .load(coverFullPath)
                            .into(holder.cover);
                } else {
                    Picasso.with(mContext)
                            .load(R.drawable.placeholder_list)
                            .fit()
                            .centerCrop()
                            .into(holder.cover);
                }
                holder.title.setText(cover.getTitle());
                holder.originalTitle.setText(cover.getOriginalTitle());
                holder.overview.setText(cover.getOverview());
                holder.releaseDate.setText(cover.getReleaseDate());
                break;
        }
    }

    @Override
    public MovieCoverViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView;
        if (Objects.equals(type, Config.ADAPTER_TYPE_LIST)) {
            itemView = LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.cover_list_item, viewGroup, false
            );
        } else {
            itemView = LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.cover_grid_item, viewGroup, false
            );
        }
        return new MovieCoverViewHolder(itemView, type, new MovieCoverViewHolder.ICategoryHolderClicks() {
            @Override
            public void onCoverClick(View caller, int adapterPosition) {
                //TODO
            }
        });
    }

    public void addToList(List<MovieCover> covers) {
        if (covers.size() == 0) {
            mCoversList = new ArrayList<>();
        } else {
            mCoversList.addAll(covers);
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class MovieCoverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ICategoryHolderClicks mListener;

        protected ImageView cover;
        protected TextView title;
        protected TextView originalTitle;
        protected TextView overview;
        protected TextView releaseDate;

        public interface ICategoryHolderClicks {
            void onCoverClick(View caller, int adapterPosition);
        }

        public MovieCoverViewHolder(@NonNull View view, String type, @NonNull ICategoryHolderClicks listener) {
            super(view);
            mListener = listener;
            cover = (ImageView) view.findViewById(R.id.cover);
            if (Objects.equals(type, Config.ADAPTER_TYPE_LIST)) {
                title = (TextView) view.findViewById(R.id.movie_title);
                originalTitle = (TextView) view.findViewById(R.id.movie_title_original);
                overview = (TextView) view.findViewById(R.id.movie_overview);
                releaseDate = (TextView) view.findViewById(R.id.movie_release_date);
            }
        }

        @Override
        public void onClick(View viewCaller) {
            mListener.onCoverClick(viewCaller, getAdapterPosition());
        }
    }
}