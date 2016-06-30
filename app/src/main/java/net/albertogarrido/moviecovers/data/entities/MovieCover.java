package net.albertogarrido.moviecovers.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class MovieCover implements Parcelable{

    private long id;
    private boolean adult; //TODO add NSFW filter in the app
    @SerializedName("poster_path")
    private String movieCover;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    private String title;
    private String overview;
    private boolean imageLoaded = false;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getMovieCover() {
        return movieCover;
    }

    public void setMovieCover(String movieCover) {
        this.movieCover = movieCover;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isImageLoaded() {
        return imageLoaded;
    }

    public void setImageLoaded(boolean isReady) {
        this.imageLoaded = isReady;
    }

    /**
     * PARCELABLE STUFF
     */
    @Override
    public int describeContents() {
        // ignore for now
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(movieCover);
        dest.writeFloat(voteAverage);
        dest.writeString(releaseDate);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeByte((byte) (imageLoaded ? 1 : 0));
    }

    public static final Parcelable.Creator<MovieCover> CREATOR = new Parcelable.Creator<MovieCover>() {
        public MovieCover createFromParcel(Parcel pc) {
            return new MovieCover(pc);
        }

        public MovieCover[] newArray(int size) {
            return new MovieCover[size];
        }
    };

    public MovieCover(Parcel in) {
        id = in.readLong();
        adult = in.readByte() != 0;
        movieCover = in.readString();
        voteAverage = in.readFloat();
        releaseDate = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        title = in.readString();
        overview = in.readString();
        imageLoaded = in.readByte() != 0;
    }
    
}
