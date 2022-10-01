package com.applid.milijunas_2021;


public class LeaderboardHelper {
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LeaderboardHelper(long id, String money, int time, int score) {

        this.id = id;
        this.money = money;
        this.score = score;
        this.time = time;
    }

    String money;
    int score;
    int time=0;

    void timeHelper(String money,int time){

        int num = 0;

        switch (money) {
            case "0":
                num = 0;
                break;
            case "500":
                num = 10000;
                break;
            case "1.000":
                num = 20000;
                break;
            case "2.000":
                num = 40000;
                break;
            case "4.000":
                num = 80000;
                break;
            case "8.000":
                num = 160000;
                break;
            case "16.000":
                num = 320000;
                break;
            case "32.000":
                num = 640000;
                break;
            case "64.000":
                num = 1280000;
                break;
            case "125.000":
                num = 2600000;
                break;
            case "250.000":
                num = 5200000;
                break;
            case "500.000":
                num = 14000000;
                break;
            case "1.000.000":
                num = 28000000;
                break;
        }


            int numHelper = num / time;
            score = numHelper;


    }

    public LeaderboardHelper(String money, int time) {
        this.money = money;
        this.time = time;
        timeHelper(money, time);
    }

    public int getTime() {
        return time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String para) {
        this.money = para;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
