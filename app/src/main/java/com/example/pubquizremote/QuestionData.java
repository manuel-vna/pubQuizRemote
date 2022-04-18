package com.example.pubquizremote;

public class QuestionData {

    public String question;
    public String questionNo;
    public String round;
    public String correctAnswer;
    public String label;
    public String picture;

    public QuestionData(String question,String questionNo,String round,String correctAnswer){
        this(question,questionNo,round,correctAnswer,null,null);
    }

    public QuestionData(String question,String questionNo,String round,String correctAnswer,String label,String picture){

        this.question = question;
        this.questionNo = questionNo;
        this.round = round;
        this.correctAnswer = correctAnswer;
        this.label = label;
        this.picture = picture;

    }


}
