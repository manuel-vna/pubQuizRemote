package com.example.pubquizremote.dataobjects;

import android.util.Log;

import com.example.pubquizremote.dataobjects.AnswersPlayerData;

public class PlayerData {

    public String uid;
    public String name;
    public String points;
    private AnswersPlayerData round1;
    private AnswersPlayerData round2;
    private AnswersPlayerData round3;
    private AnswersPlayerData round4;
    private AnswersPlayerData round5;
    private AnswersPlayerData round6;

    public PlayerData(){
        Log.i("Debug_A", "Class Player default Constructor");
    }

    public PlayerData(String name, String points) {
        this(name,points,null,null,null,null,null,null,null);
    }


    public PlayerData(String name, String points, String uid, AnswersPlayerData round1, AnswersPlayerData round2, AnswersPlayerData round3, AnswersPlayerData round4, AnswersPlayerData round5, AnswersPlayerData round6) {
        this.uid = uid;
        this.name = name;
        this.points = points;
        this.round1 = round1;
        this.round2 = round2;
        this.round3 = round3;
        this.round4 = round4;
        this.round5 = round5;
        this.round6 = round6;
    }


    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

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

    public AnswersPlayerData getRound1() {
        return round1;
    }

    public void setRound1(AnswersPlayerData round1) {
        this.round1 = round1;
    }

    public AnswersPlayerData getRound2() {
        return round2;
    }

    public void setRound2(AnswersPlayerData round2) {
        this.round2 = round2;
    }

    public AnswersPlayerData getRound3() {
        return round3;
    }

    public void setRound3(AnswersPlayerData round3) {
        this.round3 = round3;
    }

    public AnswersPlayerData getRound4() {
        return round4;
    }

    public void setRound4(AnswersPlayerData round4) {
        this.round4 = round4;
    }

    public AnswersPlayerData getRound5() {
        return round5;
    }

    public void setRound5(AnswersPlayerData round5) {
        this.round5 = round5;
    }

    public AnswersPlayerData getRound6() {
        return round6;
    }

    public void setRound6(AnswersPlayerData round6) {
        this.round6 = round6;
    }


}
