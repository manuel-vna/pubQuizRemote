package com.example.pubquizremote.models;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.pubquizremote.AnswersPlayer;
import com.example.pubquizremote.QuestionData;
import com.example.pubquizremote.fragments.AbcdRoundFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SharedRoundsViewModel extends ViewModel {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String round_type;
    private String picture;
    private String question;
    private String answerCorrect;
    private String answerOptionA;
    private String answerOptionB;
    private String answerOptionC;
    private String questionNo;
    public List<QuestionData> questionDataList = new ArrayList<QuestionData>();
    public MutableLiveData<List<QuestionData>> questionDataList2 = new MutableLiveData<>();
    Integer count;


    public MutableLiveData<List<QuestionData>> getResult(){
        return questionDataList2;
    }


    public void get_data(String round) {
        read_data(round, new LoadDataCallback() {
            @Override
            public void onCallback(List<QuestionData> qdList,String round) {
                questionDataList = qdList;
                questionDataList2.setValue(questionDataList);
            }
        });
    }

    public void read_data(String round, LoadDataCallback loadDataCallback){

        count = 0;
        DatabaseReference ref_round = database.getReference("global_game_data").child(round);
        ref_round.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Debug_A", "Error getting data", task.getException());
                }
                else {
                    round_type = task.getResult().child("round_type").getValue().toString();
                    Log.i("Debug_A", "SharedRoundsViewModel - Automated Evaluation: "+round_type);

                    for (DataSnapshot question_block : task.getResult().getChildren()) {
                        count += 1;
                        if( count <= 6) {

                            if(round_type.equals("abcd_round")) {
                                question = question_block.child("question").getValue().toString();
                                questionNo = question_block.child("questionNo").getValue().toString();
                                picture = question_block.child("picture").getValue().toString();
                                answerCorrect = question_block.child("answerCorrect").getValue().toString();
                                answerOptionA = question_block.child("answerOptionA").getValue().toString();
                                answerOptionB = question_block.child("answerOptionB").getValue().toString();
                                answerOptionC = question_block.child("answerOptionC").getValue().toString();

                                questionDataList.add(new QuestionData(question, questionNo, "round1", answerCorrect,
                                        answerOptionA, answerOptionB, answerOptionC, picture));
                            }

                            else if(round_type.equals("image_round")){
                                question = question_block.child("question").getValue().toString();
                                questionNo = question_block.child("questionNo").getValue().toString();
                                picture = question_block.child("picture").getValue().toString();
                                answerCorrect = question_block.child("answerCorrect").getValue().toString();

                                questionDataList.add(new QuestionData(question, questionNo, "round1", answerCorrect, picture));
                            }
                        }
                    }
                    loadDataCallback.onCallback(questionDataList,round);
                }
            }
        });

    }

    private interface LoadDataCallback {
        void onCallback(List<QuestionData> qdList,String round);
    }


    public void safe_player_answers_to_db(String current_round, AnswersPlayer answersPlayer) {

        if (answersPlayer.getPlayerAnswer1() == null || answersPlayer.getPlayerAnswer2() == null ||
                answersPlayer.getPlayerAnswer3() == null || answersPlayer.getPlayerAnswer4() == null ||
                answersPlayer.getPlayerAnswer5() == null || answersPlayer.getPlayerAnswer6() == null
        ){
            Log.w("Debug_A", "Bitte alle 6 Fragen ausfüllen, um speichern zu können!");
            //Toast.makeText(getContext(), "Bitte alle 6 Fragen ausfüllen, um speichern zu können!", Toast.LENGTH_LONG).show();
        }
        else {
            //auth = FirebaseAuth.getInstance();
            //FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
            String uid = auth.getCurrentUser().getUid();

            DatabaseReference ref_player_answers = database.getReference("player_data").child(uid).child(current_round);
            ref_player_answers.setValue(answersPlayer);
        }
    }

}