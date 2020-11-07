package com.prince.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    TextView detailTv;
    String messageFromParentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intentThatStartedActivity =getIntent();
        detailTv = findViewById(R.id.animalDetailtv);
        if(intentThatStartedActivity!=null) {

            if(intentThatStartedActivity.hasExtra("DETAIL_STRING")) {
                messageFromParentActivity = intentThatStartedActivity.getStringExtra("DETAIL_STRING");
                detailTv.setText(messageFromParentActivity);
            }
        }

    }
}
