package net.albertogarrido.moviecovers.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.albertogarrido.moviecovers.R;
import net.albertogarrido.moviecovers.data.entities.MovieCover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class CoversAdapter extends RecyclerView.Adapter<CoversAdapter.MovieCoverViewHolder> {

    private List<MovieCover> mCoversList;
    private Context mContext;
    private String mType;
    private RecyclerItemListener mListener;

    public CoversAdapter(List<MovieCover> categoriesList, Context context) {
        this.mCoversList = categoriesList;
        this.mContext = context;
        this.mType = Config.ADAPTER_TYPE_GRID;
    }


    @Override
    public int getItemCount() {
        return mCoversList.size();
    }

    @Override
    public void onBindViewHolder(MovieCoverViewHolder holder, int position) {
        MovieCover cover = mCoversList.get(position);
        String coverFullPath;

        Callback imageLoadedCallback = prepareImageLoadedCallback(position);

        switch (mType) {
            case Config.ADAPTER_TYPE_GRID:
                coverFullPath = Config.IMAGE_BASE_URL + Config.POSTER_SIZE_W780 + cover.getMovieCover();
                Picasso.with(mContext)
                        .load(coverFullPath)
                        .into(holder.cover, imageLoadedCallback);
                break;
            case Config.ADAPTER_TYPE_LIST:
                if (cover.getMovieCover() != null) {
                    coverFullPath = Config.IMAGE_BASE_URL + Config.POSTER_SIZE_W342 + cover.getMovieCover();
                    Picasso.with(mContext)
                            .load(coverFullPath)
                            .into(holder.cover, imageLoadedCallback);
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

    public void addRecyclerItemListener(RecyclerItemListener listener) {
        this.mListener = listener;
    }

    @Override
    public MovieCoverViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView;
        if (Objects.equals(mType, Config.ADAPTER_TYPE_LIST)) {
            itemView = LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.cover_list_item, viewGroup, false
            );
        } else {
            itemView = LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.cover_grid_item, viewGroup, false
            );
        }
        return new MovieCoverViewHolder(itemView, mType, mListener);
    }

    public void addToList(List<MovieCover> covers) {
        if (covers.size() == 0) {
            mCoversList = new ArrayList<>();
        } else {
            mCoversList.addAll(covers);
        }
    }

    public void setType(String type) {
        this.mType = type;
    }


    private Callback prepareImageLoadedCallback(final int adapterPosition) {
        return new Callback() {
            @Override
            public void onSuccess() {
                mCoversList.get(adapterPosition).setImageLoaded(true);
            }

            @Override
            public void onError() {
                mCoversList.get(adapterPosition).setImageLoaded(false);
            }
        };
    }

    public List<MovieCover> getItemsList() {
        return mCoversList;
    }

    public static class MovieCoverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RecyclerItemListener mListener;

        protected ImageView cover;
        protected TextView title;
        protected TextView originalTitle;
        protected TextView overview;
        protected TextView releaseDate;
        protected CardView listCard;


        public MovieCoverViewHolder(@NonNull View view, String type, @NonNull RecyclerItemListener listener) {
            super(view);
            mListener = listener;
            if (Objects.equals(type, Config.ADAPTER_TYPE_LIST)) {
                cover = (ImageView) view.findViewById(R.id.search_cover);
                title = (TextView) view.findViewById(R.id.movie_title);
                originalTitle = (TextView) view.findViewById(R.id.movie_title_original);
                overview = (TextView) view.findViewById(R.id.movie_overview);
                releaseDate = (TextView) view.findViewById(R.id.movie_release_date);
                listCard = (CardView) view.findViewById(R.id.card_view);
                listCard.setOnClickListener(this);
            } else {
                cover = (ImageView) view.findViewById(R.id.grid_cover);
                cover.setOnClickListener(this);

            }
        }

        @Override
        public void onClick(View viewCaller) {
            mListener.onItemTouch(viewCaller, getAdapterPosition());
        }
    }
}