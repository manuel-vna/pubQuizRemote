package com.example.pubquizremote;

public class QuestionData {

    public String question;
    public String questionNo;
    public String round;
    public String correctAnswer;
    public String picture;
    public String label;


    public QuestionData(String question,String questionNo,String round,String correctAnswer,String picture){
        this(question,questionNo,round,correctAnswer,picture,null);
    }

    public QuestionData(String question,String questionNo,String round,String correctAnswer,String picture,String label){

        this.question = question;
        this.questionNo = questionNo;
        this.round = round;
        this.correctAnswer = correctAnswer;
        this.picture = picture;
        this.label = label;

    }
}
