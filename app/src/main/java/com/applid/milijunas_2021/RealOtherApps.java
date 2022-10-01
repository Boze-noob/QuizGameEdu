package com.applid.milijunas_2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.applid.milijunas_2021.models.OtherAppsModel;

import java.util.ArrayList;
import java.util.Locale;

public class RealOtherApps extends AppCompatActivity {

    private RecyclerView recyclerView;

    ArrayList<OtherAppsModel> mainModels;
    OtherAppsAdapter otherAppsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_real_other_apps);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.other_apps_recycler_view);

        Integer[] appsLogo = {R.drawable.drawing_app_icon, R.drawable.music_box_app_icon,
        R.drawable.tic_tac_toe_app_icon, R.drawable.run_pro_app_icon};

        String[] appsName = {"Easy Drawing", "MusicBox", "Tic Tac Toe", "Run PRO"};

        mainModels = new ArrayList<>();
        for(int i = 0; i < appsLogo.length; i++)
        {
            OtherAppsModel otherAppsModel = new OtherAppsModel(appsLogo[i], appsName[i]);
            mainModels.add(otherAppsModel);

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                RealOtherApps.this, LinearLayoutManager.HORIZONTAL,false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        otherAppsAdapter = new OtherAppsAdapter(RealOtherApps.this,mainModels);
        recyclerView.setAdapter(otherAppsAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences("Language", Context.MODE_PRIVATE);
        int i = prefs.getInt("LangValue", 0);
        if(i == 1)
        {
            changeLanguageRealOtherApps();
        }
    }

    public void changeLanguageRealOtherApps()
    {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }
}