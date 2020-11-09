package com.prince.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MyOwnAdapter.ListItemClickListener {
    RecyclerView movieGridRecyclerView;
    MyOwnAdapter myOwnAdapter;
    TextView tvErrorMessage;
    Parameters paramitersToInflateMoviesList;
    public String gSearchResult;

    private final String stringBaseAddressTopRated = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
    private final String stringBaseAddressPopular = "http://api.themoviedb.org/3/movie/popular?api_key=";
    private final String api = "41ec4d909d71953ad8ffa75eb3157315";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paramitersToInflateMoviesList = new Parameters();

        movieGridRecyclerView = findViewById(R.id.movie_grid_recycler_view);
        tvErrorMessage = findViewById(R.id.errorMessage);


        new FetchDataFronInterner().execute(stringBaseAddressPopular+api);

    }

    void inflateMainActivity(Parameters paramitersToInflateMoviesList){
        myOwnAdapter = new MyOwnAdapter(this,paramitersToInflateMoviesList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        movieGridRecyclerView.setLayoutManager(layoutManager);
        movieGridRecyclerView.setHasFixedSize(true);
        movieGridRecyclerView.setAdapter(myOwnAdapter);
    }

    @Override
    public void onListItemClick(int listItemIndex) {
        Intent intentToStartDetailActivity = new Intent(MainActivity.this,DetailActivity.class);
        intentToStartDetailActivity.putExtra("MOVIE_INDEX",listItemIndex);
        intentToStartDetailActivity.putExtra("SEARCH_RESULT",gSearchResult);
        startActivity(intentToStartDetailActivity);

    }

    public class FetchDataFronInterner extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            URL urlToFetchData = getUrlFromString(stringUrl);
            String searchResult = null;
            try {
                searchResult = getResponseFromHttpUrl(urlToFetchData);
                gSearchResult = searchResult;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return searchResult;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                displayRecyclerView();
                Parameters parameters = getGridDataForMainActivity(s);
                inflateMainActivity(parameters);
            }else{
                displatErrorMessage();
            }
        }
    }
    void displatErrorMessage(){
        movieGridRecyclerView.setVisibility(View.INVISIBLE);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }
    void displayRecyclerView(){
        movieGridRecyclerView.setVisibility(View.VISIBLE);
        tvErrorMessage.setVisibility(View.INVISIBLE);
    }

    URL getUrlFromString(String stringUrl){
        try {
            URL urlToFetchData = new URL(stringUrl);
            return urlToFetchData;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private Parameters getGridDataForMainActivity(String searchResult){
        Parameters parameters = new Parameters();
        List<String> movieName = new ArrayList<>();
        List<Double> movieRating = new ArrayList<>();
        List<String> movieImage = new ArrayList<>();
        try {
            JSONObject movieData = new JSONObject(searchResult);
            JSONArray movieDetails = movieData.getJSONArray("results");
            int length = movieDetails.length();
           for(int i=0; i<length;i++){
               JSONObject tempObject = new JSONObject(movieDetails.getString(i));
               movieName.add(tempObject.getString("title"));
               movieRating.add(tempObject.getDouble("vote_average"));
               movieImage.add(tempObject.getString("poster_path"));
           }
           parameters.setMovieImage(movieImage);
           parameters.setMovieName(movieName);
           parameters.setMovieRating(movieRating);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parameters;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.listByTop_rating){
            new FetchDataFronInterner().execute(stringBaseAddressTopRated+api);
        }
        else if(itemId == R.id.listByPopularity){
            new FetchDataFronInterner().execute(stringBaseAddressPopular+api);
        }

        return true;
    }
}
