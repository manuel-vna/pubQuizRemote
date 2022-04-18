package com.example.pubquizremote;

import android.util.Log;

public class Player {

    private String name;
    private String points;
    private AnswersPlayer round1;
    private AnswersPlayer round2;
    private AnswersPlayer round3;
    private AnswersPlayer round4;
    private AnswersPlayer round5;
    private AnswersPlayer round6;

    public Player(){
        Log.i("Debug_A", "Class Player default Constructor");
    }

    public Player(String name,String points) {
        this(name,points,null,null,null,null,null,null);

    }


    public Player(String name, String points, AnswersPlayer round1, AnswersPlayer round2, AnswersPlayer round3, AnswersPlayer round4, AnswersPlayer round5, AnswersPlayer round6) {
        this.name = name;
        this.points = points;
        this.round1 = round1;
        this.round2 = round2;
        this.round3 = round3;
        this.round4 = round4;
        this.round5 = round5;
        this.round6 = round6;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public AnswersPlayer getRound1() {
        return round1;
    }

    public void setRound1(AnswersPlayer round1) {
        this.round1 = round1;
    }

    public AnswersPlayer getRound2() {
        return round2;
    }

    public void setRound2(AnswersPlayer round2) {
        this.round2 = round2;
    }

    public AnswersPlayer getRound3() {
        return round3;
    }

    public void setRound3(AnswersPlayer round3) {
        this.round3 = round3;
    }

    public AnswersPlayer getRound4() {
        return round4;
    }

    public void setRound4(AnswersPlayer round4) {
        this.round4 = round4;
    }

    public AnswersPlayer getRound5() {
        return round5;
    }

    public void setRound5(AnswersPlayer round5) {
        this.round5 = round5;
    }

    public AnswersPlayer getRound6() {
        return round6;
    }

    public void setRound6(AnswersPlayer round6) {
        this.round6 = round6;
    }
    


}
