package com.example.pubquizremote.models;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.pubquizremote.dataobjects.AnswersPlayerData;
import com.example.pubquizremote.dataobjects.QuestionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class AdminSettingsViewModel extends AndroidViewModel{

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
    FirebaseAuth auth = FirebaseAuth.getInstance();

    // database references
    DatabaseReference ref_db_first_level = database.getReference();
    DatabaseReference ref_db_global_game_data = database.getReference("global_game_data");

    //Arrays, Lists and Maps
    List<String> answerCorrectList = new ArrayList<String>();
    List<String> row = new ArrayList<String>();
    List<AnswersPlayerData> answers_players_objects = new ArrayList<AnswersPlayerData>();

    //round variables
    int round_score = 0;
    String pre_update_score;
    int new_score;

    //snapshots
    DataSnapshot user_block;
    DataSnapshot round;


    public AdminSettingsViewModel(@NonNull Application application) {
        super(application);
    }



    public void initialise_db(){

        Map<String, Object> third_level_rounds_radio_group_false  = new HashMap<String, Object>() {{
            put("automated_evaluation", "false");

        }};
        Map<String, Object> third_level_rounds_radio_group_true  = new HashMap<String, Object>() {{
            put("automated_evaluation", "true");

        }};
        Map<String, Object> second_level_rounds  = new HashMap<String, Object>() {{
            put("round1", third_level_rounds_radio_group_true);
            put("round2", third_level_rounds_radio_group_true);
            put("round3", third_level_rounds_radio_group_false);
            put("round4", third_level_rounds_radio_group_false);
            put("round5", third_level_rounds_radio_group_false);
            put("round6", third_level_rounds_radio_group_true);
        }};

        Map<String, Object> first_level  = new HashMap<String, Object>() {{
            put("player_data", "");
            put("global_game_data", second_level_rounds);
        }};

        //Log.w("Debug_A", String.valueOf(first_level));
        ref_db_first_level.setValue(first_level);

    }

    public void add_question_to_round(String questionText, String questionNo,
                                             String roundNo, String answerCorrect){

        QuestionData questionData = new QuestionData(questionText,questionNo,roundNo,answerCorrect,"");
        ref_db_global_game_data.child(roundNo).child(questionNo).setValue(questionData);
    }


    public void evaluate_results_of_round() {
        readData(new FirebaseDBCallback() {
            @Override
            public void onCallback(List<String> list1,List<AnswersPlayerData> list2) {
                answerCorrectList = list1;
                answers_players_objects = list2;

                calculate_points(answerCorrectList,answers_players_objects);

                answerCorrectList.clear();
                answers_players_objects.clear();
            }
        });
    }



    private void readData(FirebaseDBCallback firebaseDBCallback) {

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


                            for (DataSnapshot a : level1.child("round1").getChildren()){
                                if (a.child("answerCorrect").exists()) {
                                    answerCorrectList.add(a.child("answerCorrect").getValue().toString());
                                }
                            }
                        }
                        if (level1.getKey().toString().equals("player_data")) {
                            for (DataSnapshot user_block : level1.getChildren()) {
                                row.clear();
                                for (DataSnapshot round : user_block.child("round1").getChildren()) {
                                    row.add(round.getValue().toString());
                                }
                                answers_players_objects.add(new AnswersPlayerData(
                                        user_block.getKey(),user_block.child("name").getValue().toString(),user_block.child("points").getValue().toString(),
                                        row.get(0),row.get(1),row.get(2),row.get(3),row.get(4),row.get(5)));
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


    private void calculate_points(List<String> answerCorrectList,List<AnswersPlayerData> answers_players_objects) {

        for (AnswersPlayerData user_block : answers_players_objects) {
            if (user_block.playerAnswer0.equals(answerCorrectList.get(0))){
                round_score += 1;
            }
            if (user_block.playerAnswer1.equals(answerCorrectList.get(1))){
                round_score += 1;
            }
            if (user_block.playerAnswer2.equals(answerCorrectList.get(2))){
                round_score += 1;
            }
            if (user_block.playerAnswer3.equals(answerCorrectList.get(3))){
                round_score += 1;
            }
            if (user_block.playerAnswer4.equals(answerCorrectList.get(4))){
                round_score += 1;
            }
            if (user_block.playerAnswer5.equals(answerCorrectList.get(5))){
                round_score += 1;
            }
            pre_update_score = user_block.points;
            new_score = Integer.parseInt(pre_update_score) + round_score;
            database.getReference().child("player_data").child(user_block.uid).child("points").setValue(String.valueOf(new_score));
            Log.i("Debug_A", "Round Score: "+user_block.uid+" | " +String.valueOf(round_score)+ " | "+String.valueOf(new_score));
            round_score = 0;
        }
    }

}

