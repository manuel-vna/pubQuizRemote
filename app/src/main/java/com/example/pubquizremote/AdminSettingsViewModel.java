package com.example.pubquizremote;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    Map<String, String> correct_answers_of_round = new HashMap<String, String>();
    List<String> correct_answers = new ArrayList<String>();
    //List<List<String>> csv_input_list_parent = new ArrayList<>();
    List<Map<String, List<String>>> csv_input_parent = new ArrayList<>();
    Map<String, List<String>> row_map = new HashMap<String, List<String>>();
    List<String> header = Arrays.asList(new String[]{"1", "2","3","4","5","6"});
    List<String> row = new ArrayList<String>();
    List<AnswersPlayer> row_parent = new ArrayList<AnswersPlayer>();


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
                row_parent = list2;

                if (is_type_radio_group.equals("true")) {
                    calculate_points(correct_answers,row_parent);
                    //Log.i("Debug_A", "Row Parent: " + row_parent);
                }
                else {
                    //Log.i("Debug_A", "Output 2: " + radioString + " | " + correct_answers + " | " + row_parent.get(0).uid);
                    write_points_to_csv(correct_answers,row_parent);
                }
            }
        });
    }

    private void write_points_to_csv(List<String> correct_answers,List<AnswersPlayer> row_parent) {

        String pathname = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        try {
            File root = new File(pathname);
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "round1.csv");
            FileWriter writer = new FileWriter(gpxfile);

            for (AnswersPlayer user_block : row_parent) {

                Log.i("Debug_A", "Uid: " + user_block.uid);
                writer.append(user_block.uid);
                        /*
                        + ";"
                        + user_block.name + ";"
                        + user_block.playerAnswer1 + ";"
                        + user_block.playerAnswer2 + ";"
                        + user_block.playerAnswer3 + ";"
                        + user_block.playerAnswer4 + ";"
                        + user_block.playerAnswer5 + ";"
                        + user_block.playerAnswer6 + "\n");

                         */
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Debug_A", String.valueOf(e));
        }
    }

    private void calculate_points(List<String> correct_answers,List<AnswersPlayer> row_parent) {

        for (AnswersPlayer user_block : row_parent) {
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
            //Log.i("Debug_A", "Round Score: "+user_block.uid+" | " +String.valueOf(round_score)+ " | "+String.valueOf(new_score));
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
                                row_parent.add(new AnswersPlayer(user_block.getKey(),user_block.child("name").getValue().toString(),user_block.child("points").getValue().toString(),row.get(0),row.get(1),row.get(2),row.get(3),row.get(4),row.get(5)));
                            }
                        }
                    }
                    firebaseDBCallback.onCallback(is_type_radio_group,correct_answers,row_parent);
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