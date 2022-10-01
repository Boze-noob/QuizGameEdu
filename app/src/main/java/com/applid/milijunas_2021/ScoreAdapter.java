package com.applid.milijunas_2021;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;


public class ScoreAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    ArrayList<LeaderboardHelper> scores;


    public ScoreAdapter(Activity activity, ArrayList<LeaderboardHelper> scores) {
        this.activity = activity;

        this.scores = scores;

        inflater = LayoutInflater.from(activity.getApplicationContext());

    }



    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int i) {
        return scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= inflater.inflate(R.layout.score_row,null);

        TextView moneyTxt = (TextView)v.findViewById(R.id.money_record_row);
        TextView scoreTxt = (TextView)v.findViewById(R.id.score_record_row);

        moneyTxt.setText(""+scores.get(i).getMoney()+" "+activity.getString(R.string.currency));
        scoreTxt.setText(""+scores.get(i).getScore()+" "+activity.getString(R.string.point));

        return v;
    }


}
