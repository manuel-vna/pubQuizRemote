package com.example.pubquizremote.models;

import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.pubquizremote.dataobjects.AnswersPlayerData;
import com.example.pubquizremote.dataobjects.PlayerData;
import com.example.pubquizremote.dataobjects.QuestionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
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
    public MutableLiveData<List<QuestionData>> questionDataListLiveData = new MutableLiveData<>();
    public MutableLiveData<String> navigationDrawerPoints = new MutableLiveData<>();
    Integer count;
    String uid = auth.getCurrentUser().getUid();
    public List<PlayerData> pointsDataList = new ArrayList<PlayerData>();
    public MutableLiveData<List<PlayerData>> pointsDataListLiveData = new MutableLiveData<>();
    private String playerName;
    private String playerPoints;


    public MutableLiveData<List<QuestionData>> getResult(){
        return questionDataListLiveData;
    }
    public MutableLiveData<String> getNavigationDrawerPoints(){ return navigationDrawerPoints; }
    public MutableLiveData<List<PlayerData>> getPointsDataListLiveData(){ return pointsDataListLiveData; }



    public void getDataQuestionsAndAnswers(String round) {
        readDataQuestionAndAnswers(round, new QuestionsDataCallback() {
            @Override
            public void onCallback(List<QuestionData> qdList,String round) {
                questionDataListLiveData.setValue(qdList);
            }
        });
    }
    public void readDataQuestionAndAnswers(String round, QuestionsDataCallback questionsDataCallback){

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
                    questionsDataCallback.onCallback(questionDataList,round);
                }
            }
        });
    }

    private interface QuestionsDataCallback {
        void onCallback(List<QuestionData> qdList,String round);
    }


    public void safe_player_answers_to_db(String current_round, AnswersPlayerData answersPlayer) {
        DatabaseReference ref_player_answers = database.getReference("player_data").child(uid).child(current_round);
        ref_player_answers.setValue(answersPlayer);
    }

    
    public void getDataForNavigationDrawerHeader(View headerView) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                navigationDrawerPoints.setValue(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Debug_A", "loadingPoints:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference ref_uid = database.getReference("player_data").child(uid).child("points");
        ref_uid.addValueEventListener(postListener);
    }



    public void getDataForPlayerScoreTable() {
        readDataPlayerPoints(new PlayerPointsCallback() {
            @Override
            public void onCallback(List<PlayerData> pdList) {
                pointsDataListLiveData.setValue(pdList);
            }
        });
    }
        public void readDataPlayerPoints(PlayerPointsCallback playerPointsCallback){
            pointsDataList.clear();
            DatabaseReference ref_player = database.getReference("player_data");
            ref_player.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("Debug_A", "Error getting data", task.getException());
                    }
                    else {
                        for (DataSnapshot player_block : task.getResult().getChildren()) {
                            playerName = player_block.child("name").getValue().toString();
                            playerPoints = player_block.child("points").getValue().toString();
                            pointsDataList.add(new PlayerData(playerName,playerPoints));
                        }
                        playerPointsCallback.onCallback(pointsDataList);
                    }
                }
            });
        }
        private interface PlayerPointsCallback {
            void onCallback(List<PlayerData> pdList);
        }


}