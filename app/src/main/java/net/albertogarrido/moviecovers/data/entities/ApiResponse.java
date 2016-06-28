package net.albertogarrido.moviecovers.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AlbertoGarrido on 28/6/16.
 */
public class ApiResponse {

    @SerializedName("page")
    private int page; // starts in 1

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<MovieCover> movieCovers;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieCover> getMovieCovers() {
        return movieCovers;
    }

    public void setMovieCovers(List<MovieCover> movieCovers) {
        this.movieCovers = movieCovers;
    }
}
