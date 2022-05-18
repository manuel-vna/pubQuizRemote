package com.example.pubquizremote.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionData implements Parcelable {

    public String question;
    public String questionNo;
    public String round;
    public String answerCorrect;
    public String answerOptionA;
    public String answerOptionB;
    public String answerOptionC;
    public String picture;
    public String label;


    public QuestionData(String question,String questionNo,String round,String answerCorrect, String picture){
        this(question,questionNo,round,answerCorrect,null,null,null,picture,null);
    }

    public QuestionData(String question,String questionNo,String round,String answerCorrect,String answerOptionA,String answerOptionB,
                        String answerOptionC, String picture){
        this(question,questionNo,round,answerCorrect,answerOptionA,answerOptionB,answerOptionC,picture,null);
    }

    public QuestionData(String question,String questionNo,String round,String answerCorrect,String answerOptionA,String answerOptionB,
                        String answerOptionC,String picture,String label){

        this.question = question;
        this.questionNo = questionNo;
        this.round = round;
        this.answerCorrect = answerCorrect;
        this.answerOptionA = answerOptionA;
        this.answerOptionB = answerOptionB;
        this.answerOptionC = answerOptionC;
        this.picture = picture;
        this.label = label;

    }

    protected QuestionData(Parcel in) {
        question = in.readString();
        questionNo = in.readString();
        round = in.readString();
        answerCorrect = in.readString();
        picture = in.readString();
        label = in.readString();
    }

    public static final Creator<QuestionData> CREATOR = new Creator<QuestionData>() {
        @Override
        public QuestionData createFromParcel(Parcel in) {
            return new QuestionData(in);
        }

        @Override
        public QuestionData[] newArray(int size) {
            return new QuestionData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeString(questionNo);
        parcel.writeString(round);
        parcel.writeString(answerCorrect);
        parcel.writeString(picture);
        parcel.writeString(label);
    }
}
