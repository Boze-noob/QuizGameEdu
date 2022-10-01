package com.applid.milijunas_2021;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class LeaderboardDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "millionaire_db";
    private static final String TABLE_NAME = "tablee";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_TIME = "time";
    private static final int DATABASE_VERSION = 1;
    public LeaderboardDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                new StringBuilder().append("CREATE TABLE ")
                        .append(TABLE_NAME).append(" ( ")
                        .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                        .append(COLUMN_MONEY).append(" TEXT, ")
                        .append(COLUMN_TIME).append(" INTEGER, ")
                        .append(COLUMN_SCORE).append(" INTEGER ); ").toString();
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addScore(LeaderboardHelper leaderboardHelper) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONEY, leaderboardHelper.getMoney());
        values.put(COLUMN_SCORE, leaderboardHelper.getScore());
        values.put(COLUMN_TIME, leaderboardHelper.getTime());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public void deleteAllScores() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();

    }


    public ArrayList<LeaderboardHelper> getAllScores() {

        ArrayList<LeaderboardHelper> list = new ArrayList<>();
        String sql_command = "SELECT * FROM " + TABLE_NAME+ " ORDER BY "+COLUMN_TIME+" DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_command,null);

        while(cursor.moveToNext()){

            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String money = cursor.getString(cursor.getColumnIndex(COLUMN_MONEY));
            int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));

            LeaderboardHelper newScore = new LeaderboardHelper(id, money, Integer.parseInt(time), score);
            list.add(newScore);
        }
        cursor.close();
        db.close();
        return list;


    }

    public ArrayList<LeaderboardHelper> getAllScoresHighest() {

        ArrayList<LeaderboardHelper> list = new ArrayList<>();
        String sql_command = "SELECT * FROM " + TABLE_NAME+ " ORDER BY "+COLUMN_SCORE+" DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_command,null);

        while(cursor.moveToNext()){

            Long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String money = cursor.getString(cursor.getColumnIndex(COLUMN_MONEY));
            int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));

            LeaderboardHelper newscores = new LeaderboardHelper(id, money, Integer.parseInt(time), score);
            list.add(newscores);
        }
        cursor.close();
        db.close();
        return list;


    }


}



