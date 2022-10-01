package com.applid.milijunas_2021;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class ScoreListHelper extends AppCompatActivity {

    ListView scorelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score_list_helper);
        getSupportActionBar().hide();
        setAdapter();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    void setAdapter() {

        scorelist = (ListView) findViewById(R.id.score_list);

        LeaderboardDatabase leaderboardDatabase = new LeaderboardDatabase(this);
        ScoreAdapter adapter = new ScoreAdapter(this, leaderboardDatabase.getAllScoresHighest());
        scorelist.setAdapter(adapter);

    }

    public void clear(View view) {
        LeaderboardDatabase leaderboardDatabase = new LeaderboardDatabase(this);
        leaderboardDatabase.deleteAllScores();
        ScoreAdapter scoreAdapter = new ScoreAdapter(this, leaderboardDatabase.getAllScoresHighest());
        scorelist.setAdapter(scoreAdapter);
    }

    public void exit(View view)
    {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void changeLanguageScoreHelper()
    {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = this.getSharedPreferences("Language", Context.MODE_PRIVATE);
        int i = prefs.getInt("LangValue", 0);
        if(i == 1)
        {
            changeLanguageScoreHelper();

        }
    }
}
