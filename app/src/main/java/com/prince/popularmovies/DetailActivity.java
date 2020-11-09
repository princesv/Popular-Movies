package com.prince.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    TextView tvMovieTitle;
    TextView tvMovieOverview;
    TextView tvMovieRating;
    TextView tvMovieTotalVotes;
    TextView tvMovieOriginalLangudge;
    TextView tvOriginalTitle;
    TextView tvMovieIsAdult;
    TextView tvDateOfRelease;
    ImageView ivBackdropImage;
    ImageView ivposterImage;
    int movieIndex;
    String searchResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intentThatStartedActivity =getIntent();
        tvMovieTitle = findViewById(R.id.movieTitle);
        tvMovieOverview =findViewById(R.id.overview);
        tvMovieRating = findViewById(R.id.movieRating);
        tvDateOfRelease = findViewById(R.id.dateOfRelease);
        tvMovieTotalVotes = findViewById(R.id.voteCount);
        tvOriginalTitle = findViewById(R.id.originalTitle);
        tvMovieOriginalLangudge= findViewById(R.id.originalLanguage);
        tvMovieIsAdult = findViewById(R.id.isAdult);
        ivBackdropImage = findViewById(R.id.backdropImage);
        ivposterImage = findViewById(R.id.posterImage);

        if(intentThatStartedActivity!=null) {

            if(intentThatStartedActivity.hasExtra("MOVIE_INDEX") && intentThatStartedActivity.hasExtra("SEARCH_RESULT")) {
                movieIndex = intentThatStartedActivity.getIntExtra("MOVIE_INDEX",-1);
                searchResult = intentThatStartedActivity.getStringExtra("SEARCH_RESULT");
                JSONObject resultJSON = new JSONObject();
                JSONArray moviesData =new JSONArray();
                JSONObject movieData = new JSONObject();
                if(movieIndex != -1) {

                    try {
                        resultJSON = new JSONObject(searchResult);
                        moviesData = resultJSON.getJSONArray("results");
                        movieData = moviesData.getJSONObject(movieIndex);
                        tvMovieIsAdult.setText(""+movieData.getBoolean("adult"));
                        tvMovieTitle.setText(movieData.getString("title"));
                        tvMovieOriginalLangudge.setText(movieData.getString("original_language"));
                        tvOriginalTitle.setText(movieData.getString("original_title"));
                        tvMovieOverview.setText(movieData.getString("overview"));
                        tvMovieTotalVotes.setText(""+movieData.getInt("vote_count"));
                        tvDateOfRelease.setText(""+movieData.getString("release_date"));
                        tvMovieRating.setText(""+movieData.getDouble("vote_average"));
                        Picasso.get().load("http://image.tmdb.org/t/p/w185//"+movieData.getString("poster_path")).into(ivposterImage);
                        Picasso.get().load("http://image.tmdb.org/t/p/w185//"+movieData.getString("backdrop_path")).into(ivBackdropImage);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }
        }

    }
}
