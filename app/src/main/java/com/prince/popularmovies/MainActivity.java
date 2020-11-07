package com.prince.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
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
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MyOwnAdapter.ListItemClickListener {
    RecyclerView movieGridRecyclerView;
    MyOwnAdapter myOwnAdapter;
    String animalName[];
    String animalDescription[];
    String animalImage[] = {"https://homepages.cae.wisc.edu/~ece533/images/airplane.png","https://homepages.cae.wisc.edu/~ece533/images/arctichare.png","https://homepages.cae.wisc.edu/~ece533/images/baboon.png","https://homepages.cae.wisc.edu/~ece533/images/airplane.png","https://homepages.cae.wisc.edu/~ece533/images/arctichare.png","https://homepages.cae.wisc.edu/~ece533/images/baboon.png","https://homepages.cae.wisc.edu/~ece533/images/airplane.png","https://homepages.cae.wisc.edu/~ece533/images/arctichare.png","https://homepages.cae.wisc.edu/~ece533/images/baboon.png"};
    Parameters paramitersToInflateMoviesList;
    TextView textView;
    String gSearchResult;
    private final String stringUrlToFetchData = "http://api.themoviedb.org/3/movie/popular?api_key=41ec4d909d71953ad8ffa75eb3157315";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  animalName[0] = "Cat";
       // animalName[1] = "Dog";
       // animalName[2] = "mouse";

       // animalDescription[0] = "Cats are addorable";
       // animalDescription[1] = "Dog are loyal";
       // animalDescription[2] = "mouse are mischevious";

        //animalImage[0] = R.drawable.dog;
       // animalImage[1] = R.drawable.cat;
       // animalImage[2] = R.drawable.mouse;
        textView = findViewById(R.id.tv);
        paramitersToInflateMoviesList = new Parameters();
        animalName = getResources().getStringArray(R.array.petname);
        animalDescription = getResources().getStringArray(R.array.desc);
        paramitersToInflateMoviesList.setAnimalDescriptions(animalDescription);
        paramitersToInflateMoviesList.setAnimalImage(animalImage);
        paramitersToInflateMoviesList.setAnimalName(animalName);

        movieGridRecyclerView = findViewById(R.id.movie_grid_recycler_view);
        myOwnAdapter = new MyOwnAdapter(this,paramitersToInflateMoviesList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        movieGridRecyclerView.setLayoutManager(layoutManager);
        movieGridRecyclerView.setHasFixedSize(true);
        movieGridRecyclerView.setAdapter(myOwnAdapter);
        new FetchDataFronInterner().execute(stringUrlToFetchData);

    }

    @Override
    public void onListItemClick(int listItemIndex) {
        Intent intentToStartDetailActivity = new Intent(MainActivity.this,DetailActivity.class);
        intentToStartDetailActivity.putExtra("DETAIL_STRING",animalDescription[listItemIndex]);
        startActivity(intentToStartDetailActivity);
        Toast.makeText(this, "index:"+listItemIndex, Toast.LENGTH_SHORT).show();
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
            textView.setText("text"+s);
        }
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

    private List<String> getGridDataForMainActivity(String searchResult){
        try {
            JSONObject movieData = new JSONObject(searchResult);
            JSONArray movieNames = movieData.getJSONArray("result");
            int length = movieNames.length();
            List<String> listMovie = new List<String>();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
