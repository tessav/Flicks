package com.example.tessav.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tessavoon on 9/14/17.
 */

public class Movie {

    public enum MovieTypes {
        POPULAR, NORMAL
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public MovieTypes getMovieType() {
        return movieType;
    }

    public Integer getMovieId() {
        return id;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Integer getVoteAvg() {
        return voteAvg;
    }


    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    Integer voteAvg;
    Integer id;
    Double popularity;
    MovieTypes movieType;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.voteAvg = jsonObject.getInt("vote_average");
        this.popularity = jsonObject.getDouble("popularity");
        this.movieType = (this.voteAvg > 5) ? MovieTypes.POPULAR : MovieTypes.NORMAL;
        this.id = jsonObject.getInt("id");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
