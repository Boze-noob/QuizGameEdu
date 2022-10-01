package com.applid.milijunas_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

   ImageView logo;
   Switch music;
   SharedPreferences sharedPreferences;
   private MediaPlayer mp;
   private Integer inputSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        music = (Switch) findViewById(R.id.music_on_off);
        getSupportActionBar().hide();
        mp = MediaPlayer.create(this,R.raw.main_music_full);

        RotateNow();
        sharedPreferences = getSharedPreferences("ses", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit = sharedPreferences.edit();

        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    edit.putString("ses", "ok");
                    setAndStartMusic();
                    edit.apply();
                }
                else if (!b) {

                    edit.putString("ses", "no");
                    mp.stop();
                    edit.commit();
                }
            }

        });

        if (sharedPreferences.getString("ses", "").equals("ok")) {

            setAndStartMusic();
            music.setChecked(true);

        }

        else music.setChecked(false);

        SharedPreferences prefs = getSharedPreferences("subToTopic", MODE_PRIVATE);
        if(!prefs.getBoolean("subValue", false)){
            subscribeToTopic();

        }

    }

    public void openStudio(View view) {

        mp.stop();
        startActivity(new Intent(MainActivity.this, ShowActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getString("ses", "").equals("ok")) {
            setAndStartMusic();


        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        if(mp.isPlaying())
        {
            mp.stop();
        }

    }

    @Override
    public void onBackPressed()
    {
        mp.stop();
        mp.reset();
        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(mp.isPlaying())
        {
            mp.stop();
        }
    }


    public void RotateNow() {

        logo = (ImageView) findViewById(R.id.main_logo);
        RotateAnimation rotate = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        logo.startAnimation(rotate);

    }


    public void openScore(View view) {

        mp.stop();
        startActivity(new Intent(MainActivity.this, ScoreListHelper.class));

    }

    public void openOtherApps(View view) {

        mp.stop();
        startActivity(new Intent(MainActivity.this, OtherApps.class));
    }

    public void openCreate(View view)
    {
        mp.stop();
        startActivity(new Intent(MainActivity.this, CreateActivity.class));
    }

    public void openOtherAppsActivity(View view)
    {
        mp.stop();
        startActivity(new Intent(MainActivity.this, RealOtherApps.class));
    }

    public void quit(View view) {

        mp.stop();
        mp.reset();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }

    private void setAndStartMusic(){
        mp.stop();
        mp.reset();
        mp = MediaPlayer.create(this,R.raw.main_music_full);
        mp.start();
        mp.setLooping(true);
    }

    public void openTranslateDialog(View view)
    {
        SharedPreferences prefs = this.getSharedPreferences("Language", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        inputSelection = prefs.getInt("LangValue", 0);

        final CharSequence[] items = { " Croatian ", " English " };

        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select language");

        builder.setSingleChoiceItems(items,inputSelection,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        inputSelection = item;
                        editor.putInt("LangValue", item);
                        editor.apply();
                        dialog.dismiss();
                        translateMethod(item);
                        recreate();
                    }
                });
        builder.create();
        builder.show();

    }

    private void translateMethod(Integer integer)
    {


        String lang;

        if(integer == 0) {
            lang = "hr";
        }
        else {
            lang = "en";
        }

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

    }

    public void loadLanguage()
    {
        SharedPreferences prefs = this.getSharedPreferences("Language", Context.MODE_PRIVATE);
        translateMethod(prefs.getInt("LangValue", 0));
    }

    private void subscribeToTopic()
    {
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (!task.isSuccessful()) {
                          Log.d("TopicSub", "Nije se dobro subalooooooooooooooooooooooooo");
                        }
                        else{
                            SharedPreferences.Editor editor = getSharedPreferences("subToTopic", MODE_PRIVATE).edit();
                            editor.putBoolean("subValue", true);
                            editor.apply();

                        }

                    }
                });

    }


}
