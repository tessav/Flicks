package com.example.tessav.flicks.activities;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tessav.flicks.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by tessavoon on 9/18/17.
 */

public class MovieDetailActivity extends YouTubeBaseActivity {
    @BindView(R.id.tvDetailTitle)
    TextView tvTitle;
    @BindView(R.id.tvDetailOverview)
    TextView tvOverview;
    @BindView(R.id.detailRatingBar)
    RatingBar ratingBar;
    @BindView(R.id.tvPopularity)
    TextView tvPopularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);
        ButterKnife.bind(this);
        int movieId = getIntent().getIntExtra("movieId", 0);
        String movieTitle = getIntent().getStringExtra("movieTitle");
        String movieOverview = getIntent().getStringExtra("movieOverview");
        int movieVoteAvg = getIntent().getIntExtra("movieVoteAvg", 0);
        Double moviePopularity = getIntent().getDoubleExtra("moviePopularity", 0);
        getTrailerFromId(movieId);
        tvTitle.setText(movieTitle);
        tvOverview.setText(movieOverview);
        tvPopularity.setText(moviePopularity.toString());
        float movieVoteAvgScaled = (float) movieVoteAvg / 2;
        ratingBar.setStepSize((float) 0.01);
        ratingBar.setRating(movieVoteAvgScaled);

    }

    private void getTrailerFromId(Integer movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONObject trailerJson = null;
                try {
                    trailerJson = (JSONObject) response.getJSONArray("results").get(0);
                    playYoutubeTrailer(trailerJson.getString("key"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void playYoutubeTrailer(final String key) {
        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.detailPlayer);

        youTubePlayerView.initialize("AIzaSyAMs8XSrJApzix81_CkaqABWgbOUMNrVzc",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(key);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                    }
                });
    }
}
