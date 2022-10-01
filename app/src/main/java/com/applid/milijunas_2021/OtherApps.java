package com.applid.milijunas_2021;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;

public class OtherApps extends AppCompatActivity {

    private TextView songLinkTxt, downloadLinkTxt, promotorLinkTxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_other_apps);
        getSupportActionBar().hide();

        songLinkTxt = findViewById(R.id.songLink);
        downloadLinkTxt = findViewById(R.id.downloadLink);
        promotorLinkTxt = findViewById(R.id.promotorLink);

        songLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=o3n1uV48_2Q"));
                startActivity(youtubeIntent);

            }
        });

        downloadLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent downloadIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bit.ly/-dramatic-orchestral"));
                startActivity(downloadIntent);

            }
        });

        promotorLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent promotorIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/o3n1uV48_2Q"));
                startActivity(promotorIntent);

            }
        });




        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences("Language", Context.MODE_PRIVATE);
        int i = prefs.getInt("LangValue", 0);
        if(i == 1)
        {
            changeLanguageOtherApps();
        }
    }

    public void changeLanguageOtherApps()
    {

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }



}
