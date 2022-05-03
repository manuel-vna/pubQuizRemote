package com.example.pubquizremote.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.pubquizremote.QuestionData;
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
    private String picture;
    private String question;
    private String correctAnswer;
    private String questionNo;
    public List<QuestionData> questionDataList = new ArrayList<QuestionData>();
    public List<QuestionData> questionDataList2 = new ArrayList<QuestionData>();
    Integer count;


    public void get_images(String round) {
        read_images(round, new LoadImagesCallback() {
            @Override
            public void onCallback(List<QuestionData> qdList,String round) {
                questionDataList2 = qdList;

            }
        });
    }

    public void read_images(String round, LoadImagesCallback loadImagesCallback){

        count = 0;
        DatabaseReference ref_round = database.getReference("global_game_data").child(round);
        ref_round.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Debug_A", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot question_block : task.getResult().getChildren()) {
                        count += 1;
                        if( count <= 6) {
                            question = question_block.child("question").getValue().toString();
                            questionNo = question_block.child("questionNo").getValue().toString();
                            correctAnswer = question_block.child("correctAnswer").getValue().toString();
                            picture = question_block.child("picture").getValue().toString();

                            questionDataList.add(new QuestionData(question, questionNo, "round1", correctAnswer, picture));
                        }
                    }
                    loadImagesCallback.onCallback(questionDataList,round);
                }
            }
        });

    }

    private interface LoadImagesCallback {
        void onCallback(List<QuestionData> qdList,String round);
    }

}