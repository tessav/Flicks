package com.example.tessav.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tessav.flicks.activities.MovieDetailActivity;
import com.example.tessav.flicks.activities.QuickPlayActivity;
import com.example.tessav.flicks.adapters.MovieArrayAdapter;
import com.example.tessav.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        setupListViewListener();
    }

    private void setupListViewListener() {
        // edit task or show more details
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item,
                                            int pos, long id) {
                        Intent openActivity;
                        if (movies.get(pos).getMovieType() == Movie.MovieTypes.POPULAR) {
                            openActivity = new Intent(MovieActivity.this, QuickPlayActivity.class);
                            openActivity.putExtra("movieId", movies.get(pos).getMovieId());
                        } else {
                            openActivity = new Intent(MovieActivity.this, MovieDetailActivity.class);
                            openActivity.putExtra("movieTitle", movies.get(pos).getOriginalTitle());
                            openActivity.putExtra("movieOverview", movies.get(pos).getOverview());
                            openActivity.putExtra("movieVoteAvg", movies.get(pos).getVoteAvg());
                            openActivity.putExtra("moviePopularity", movies.get(pos).getPopularity());
                            openActivity.putExtra("movieId", movies.get(pos).getMovieId());
                        }
                        startActivity(openActivity);
                    }
                }
        );
    }
}
