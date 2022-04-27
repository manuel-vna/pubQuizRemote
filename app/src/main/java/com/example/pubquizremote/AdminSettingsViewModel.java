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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class AdminSettingsViewModel extends AndroidViewModel{

    FirebaseDatabase database = RoundFragment.database;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    // database references
    DatabaseReference ref_db_first_level = database.getReference();
    DatabaseReference ref_db_global_game_data = database.getReference("global_game_data");

    //Arrays, Lists and Maps
    List<String> correct_answers = new ArrayList<String>();
    List<String> header = Arrays.asList(new String[]{"1", "2","3","4","5","6"});
    List<String> row = new ArrayList<String>();
    List<AnswersPlayer> answers_players_objects = new ArrayList<AnswersPlayer>();


    //round variables
    int round_score = 0;
    String pre_update_score;
    int new_score;
    String is_type_radio_group;

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

        //Log.w("Debug_A", String.valueOf(first_level));
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
            public void onCallback(String radioString,List<String> list1,List<AnswersPlayer> list2) {
                is_type_radio_group = radioString;
                correct_answers = list1;
                answers_players_objects = list2;

                if (is_type_radio_group.equals("true")) {

                    calculate_points(correct_answers,answers_players_objects);
                    //Log.i("Debug_A", "Row Parent: " + answers_players_objects);
                }
                else {

                    write_answers_for_review(correct_answers,answers_players_objects);
                    //Log.i("Debug_A", "Output 2: " + radioString + " | " + correct_answers + " | " + answers_players_objects.get(0).uid);
                }
                correct_answers.clear();
                answers_players_objects.clear();
            }
        });
    }

    private void write_answers_for_review(List<String> correct_answers,List<AnswersPlayer> answers_players_objects) {

        Log.i("Debug_A", "write_answers_for_review: "+String.valueOf(correct_answers));
        Log.i("Debug_A", "write_answers_for_review: "+String.valueOf(answers_players_objects));

        //TBD: Saving csv-file to Google Drive API
    }

    private void calculate_points(List<String> correct_answers,List<AnswersPlayer> answers_players_objects) {

        for (AnswersPlayer user_block : answers_players_objects) {
            if (user_block.playerAnswer1.equals(correct_answers.get(0))){
                round_score += 1;
            }
            if (user_block.playerAnswer2.equals(correct_answers.get(1))){
                round_score += 1;
                    }
            if (user_block.playerAnswer3.equals(correct_answers.get(2))){
                round_score += 1;
            }
            if (user_block.playerAnswer4.equals(correct_answers.get(3))){
                round_score += 1;
            }
            if (user_block.playerAnswer5.equals(correct_answers.get(4))){
                round_score += 1;
            }
            if (user_block.playerAnswer6.equals(correct_answers.get(5))){
                round_score += 1;
            }
            pre_update_score = user_block.points;
            new_score = Integer.parseInt(pre_update_score) + round_score;
            database.getReference().child("player_data").child(user_block.uid).child("points").setValue(String.valueOf(new_score));
            Log.i("Debug_A", "Round Score: "+user_block.uid+" | " +String.valueOf(round_score)+ " | "+String.valueOf(new_score));
            round_score = 0;
        }

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

                            is_type_radio_group = level1.child("round1/is_type_radio_group").getValue().toString();
                            for (DataSnapshot a : level1.child("round1").getChildren()){
                                if (a.child("correctAnswer").exists()) {
                                    correct_answers.add(a.child("correctAnswer").getValue().toString());
                                }
                            }
                        }
                        if (level1.getKey().toString().equals("player_data")) {
                            for (DataSnapshot user_block : level1.getChildren()) {
                                row.clear();
                                for (DataSnapshot round : user_block.child("round1").getChildren()) {
                                    row.add(round.getValue().toString());
                                }
                                answers_players_objects.add(new AnswersPlayer(user_block.getKey(),user_block.child("name").getValue().toString(),user_block.child("points").getValue().toString(),row.get(0),row.get(1),row.get(2),row.get(3),row.get(4),row.get(5)));
                            }
                        }
                    }
                    firebaseDBCallback.onCallback(is_type_radio_group,correct_answers,answers_players_objects);
                }
            }
        });
    }
    private interface FirebaseDBCallback {
        void onCallback(String radioString,List<String> list1,List<AnswersPlayer> list2);
    }
}


/*

    ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
public void run() {

}
        });
*/