package com.example.pubquizremote;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;



public class AdminSettingsViewModel extends AndroidViewModel{

    FirebaseDatabase database = RoundFragment.database;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    // database references
    DatabaseReference ref_db_first_level = database.getReference();
    DatabaseReference ref_db_global_game_data = database.getReference("global_game_data");

    //Arrays and Maps
    Map<String, String> correct_answers_of_round = new HashMap<String, String>();

    //int count variables
    int round_score;
    int round_count;
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
            put("is_type_radio_group", "false");

        }};
        Map<String, Object> third_level_rounds_radio_group_true  = new HashMap<String, Object>() {{
            put("is_type_radio_group", "true");

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

        Log.w("Debug_A", String.valueOf(first_level));
        ref_db_first_level.setValue(first_level);

    }

    public void add_question_to_round(String questionText, String questionNo,
                                             String roundNo, String correctAnswer){

        QuestionData questionData = new QuestionData(questionText,questionNo,roundNo,correctAnswer,"","");
        ref_db_global_game_data.child(roundNo).child(questionNo).setValue(questionData);
    }


    public void evaluate_results_of_round() {
        readData(new FirebaseDBCallback() {
            @Override
            public void onCallback(Map<String, String> map) {
                correct_answers_of_round = map;
                //Log.i("Debug_A", "CorrectAnswers Hashmap 2: " + correct_answers_of_round);
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

                            Log.i("Debug_A", "RadioGroupRound? "+level1.child("round1/is_type_radio_group").getValue().toString());

                            for (DataSnapshot a : level1.child("round1").getChildren()){
                                //Log.i("Debug_A", "B: "+String.valueOf(a));
                                if (a.child("correctAnswer").exists()) {
                                    correct_answers_of_round.put(a.getKey(), a.child("correctAnswer").getValue().toString());
                                }
                            }
                        }

                        if (level1.getKey().toString().equals("player_data")){

                            for (DataSnapshot user_block : level1.getChildren()){
                                round_score = 0;
                                round_count = 1;
                                for (DataSnapshot round: user_block.child("round1").getChildren()){
                                    Log.i("Debug_A", "Values: "+round.getValue().toString()+" | "+correct_answers_of_round.get(String.valueOf(round_count)));
                                    if (round.getValue().toString().equals(correct_answers_of_round.get(String.valueOf(round_count)))){
                                        round_score += 1;
                                    }
                                    round_count += 1;
                                }
                                pre_update_score = user_block.child("points").getValue().toString();
                                new_score = Integer.parseInt(pre_update_score)+round_score;
                                user_block.getRef().child("points").setValue(String.valueOf(new_score));
                            }
                        }
                    }
                }
                firebaseDBCallback.onCallback(correct_answers_of_round);
            }
        });
    }
    private interface FirebaseDBCallback {
        void onCallback(Map<String, String> map);
    }

}


/*

    ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
public void run() {

}
        });
*/