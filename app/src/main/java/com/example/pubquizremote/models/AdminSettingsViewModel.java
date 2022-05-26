package com.example.pubquizremote.models;


import androidx.lifecycle.ViewModel;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.pubquizremote.dataobjects.AnswersPlayerData;
import com.example.pubquizremote.utils.AdminSettingsCalculations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AdminSettingsViewModel extends ViewModel {

    //database
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String uid;

    // database references
    DatabaseReference ref_db_first_level = database.getReference();
    DatabaseReference ref_db_global_game_data = database.getReference("global_game_data");

    //Arrays, Lists and Maps
    List<String> answerCorrectList = new ArrayList<String>();
    List<String> row = new ArrayList<String>();
    List<AnswersPlayerData> answers_players_objects = new ArrayList<AnswersPlayerData>();

    //snapshots
    DataSnapshot user_block;
    DataSnapshot round;

    //instantiation
    AdminSettingsCalculations adminSettingsCalculations = new AdminSettingsCalculations();

    //data fields
    int new_score;
    Map<String, Object> first_db_level;



    public void get_from_AdminSettingsCalulation_initial_structure(){
        adminSettingsCalculations.define_initial_db_structure(new AdminSettingsViewModel());
    }
    public void write_to_firebase_initial_structure(Map<String, Object> first_db_level){
        //ref_db_first_level.setValue(first_db_level);
        Log.i("Debug_A", String.valueOf(first_db_level));
    }


    public void get_from_firebase_round_results_startpoint(String current_round) {
        get_from_firebase_round_results_reading(current_round,new FirebaseDBCallback() {
            @Override
            public void onCallback(List<String> list1,List<AnswersPlayerData> list2) {
                answerCorrectList = list1;
                answers_players_objects = list2;

                adminSettingsCalculations.calculate_points(new AdminSettingsViewModel(),answerCorrectList,answers_players_objects);

                answerCorrectList.clear();
                answers_players_objects.clear();
            }
        });
    }


    private void get_from_firebase_round_results_reading(String current_round,FirebaseDBCallback firebaseDBCallback) {

        DatabaseReference ref_round = database.getReference();
        ref_round.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Debug_A", "Error getting data", task.getException());
                }
                else {
                    for (DataSnapshot level1 : task.getResult().getChildren()) {

                        if (level1.getKey().toString().equals("global_game_data")){


                            for (DataSnapshot a : level1.child(current_round).getChildren()){
                                if (a.child("answerCorrect").exists()) {
                                    answerCorrectList.add(a.child("answerCorrect").getValue().toString());
                                }
                            }
                        }
                        if (level1.getKey().toString().equals("player_data")) {
                            for (DataSnapshot user_block : level1.getChildren()) {
                                row.clear();
                                for (DataSnapshot round : user_block.child(current_round).getChildren()) {
                                    row.add(round.getValue().toString());
                                }
                                if (row != null && user_block != null) {
                                    answers_players_objects.add(new AnswersPlayerData(
                                            user_block.getKey(), user_block.child("name").getValue().toString(), user_block.child("points").getValue().toString(),
                                            row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5)));
                                }
                            }
                        }
                    }
                    firebaseDBCallback.onCallback(answerCorrectList,answers_players_objects);
                }
            }
        });
    }
    private interface FirebaseDBCallback {
        void onCallback(List<String> list1,List<AnswersPlayerData> list2);
    }


    public void write_to_firebase_points(String uid,int new_score){
        database.getReference().child("player_data").child(uid).child("points").setValue(String.valueOf(new_score));
    }


}

