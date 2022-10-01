package com.applid.milijunas_2021;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


import java.util.Locale;
import java.util.Random;


public class ShowActivity extends AppCompatActivity {
    ShowHelper showHelper;
    Button a, b, c, d;
    Button withdraw;
    ImageButton halfHalf;
    ImageButton phone;
    ImageButton public_p;
    TextView source;
    Button[] moneyButtons = new Button[12];
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-2281074336539826/5025628605", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("TAG", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("TAG", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show);
        getSupportActionBar().hide();
        findViews();

        try {
            showHelper = new ShowHelper(this, a, b, c, d, source);
            showHelper.setMoneyButton(moneyButtons);
            showHelper.setHalfHalf(halfHalf);
            showHelper.setPhoneImg(phone);
            showHelper.setPublicImg(public_p);
            showHelper.setWithdrawButtonu(withdraw);
            showHelper.setNumOfQuestions(12);
            showHelper.play();

        } catch (Exception e) {

        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);

    }

    void findViews() {
        a = (Button) findViewById(R.id.a);
        b = (Button) findViewById(R.id.b);
        c = (Button) findViewById(R.id.c);
        d = (Button) findViewById(R.id.d);

        public_p = (ImageButton) findViewById(R.id.public_icon);
        phone = (ImageButton) findViewById(R.id.phone_icon);
        halfHalf = (ImageButton) findViewById(R.id.half_half_icon);
        source = (TextView) findViewById(R.id.sourc_container);

        moneyButtons[0] = (Button) findViewById(R.id.money1);
        moneyButtons[1] = (Button) findViewById(R.id.money2);
        moneyButtons[2] = (Button) findViewById(R.id.money3);
        moneyButtons[3] = (Button) findViewById(R.id.money4);
        moneyButtons[4] = (Button) findViewById(R.id.money5);
        moneyButtons[5] = (Button) findViewById(R.id.money6);
        moneyButtons[6] = (Button) findViewById(R.id.money7);
        moneyButtons[7] = (Button) findViewById(R.id.money8);
        moneyButtons[8] = (Button) findViewById(R.id.money9);
        moneyButtons[9] = (Button) findViewById(R.id.money10);
        moneyButtons[10] = (Button) findViewById(R.id.money11);
        moneyButtons[11] = (Button) findViewById(R.id.money12);


        withdraw = (Button) findViewById(R.id.withdraw);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // finish();


    }

    @Override
    protected void onPause() {
        if (ShowHelper.mediaPlayer != null)
            ShowHelper.mediaPlayer.pause();
        super.onPause();

    }

    @Override
    protected void onStop() {
        if (ShowHelper.mediaPlayer != null)
            ShowHelper.mediaPlayer.pause();
        super.onStop();
        try {

           finish();

        } catch (Exception e) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ShowHelper.mediaPlayer != null) {
            ShowHelper.mediaPlayer.stop();
            ShowHelper.mediaPlayer.reset();
            ShowHelper.mediaPlayer.release();
            ShowHelper.mediaPlayer = null;
        }


        try {
            Random r = new Random();
            int num = r.nextInt(10 - 1) + 1;
            if(num < 6)
            {
                showAds();
            }

        } catch (Exception e) {
          Log.d("ad error", "Couldn't show error!");
        }
        finish();
    }

    public void showAds()
    {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(ShowActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences("Language", Context.MODE_PRIVATE);
        int i = prefs.getInt("LangValue", 0);
        if(i == 1)
        {
            changeLanguageShow();
        }
    }

    public void changeLanguageShow()
    {
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

}

