package com.example.pubquizremote;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AdminSettingsViewModel extends AndroidViewModel{



    static FirebaseDatabase database = RoundFragment.database;
    static FirebaseAuth auth = FirebaseAuth.getInstance();

    // database references
    static DatabaseReference ref_db_first_level = database.getReference();
    static DatabaseReference ref_db_global_game_data = database.getReference("global_game_data");


    public AdminSettingsViewModel(@NonNull Application application) {
        super(application);
    }


    public static void initialise_db(){

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

    public static void add_question_to_round(String questionText, String questionNo,
                                             String roundNo, String correctAnswer){

        QuestionData questionData = new QuestionData(questionText,questionNo,roundNo,correctAnswer,"","");

        ref_db_global_game_data.child(roundNo).child(questionNo).setValue(questionData);

    }


    public static void evaluate_results_of_round(){

        Map<String, String> correct_answers_of_round  = new HashMap<String, String>();

        final String[] questions = {"1", "2"}; //"3", "4", "5", "6"};

        for (String s : questions) {

            DatabaseReference ref_correct_answers = database.getReference("global_game_data").child("round1").child(s);
            ref_correct_answers.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String correct_answer = dataSnapshot.child("correctAnswer").getValue(String.class);
                    correct_answers_of_round.put(s, correct_answer);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // we are showing that error message in toast
                    Log.w("Debug_A", "error: loading image", databaseError.toException());
                }

            });
        }

        Log.w("Debug_A", "CorrectAnswers Hashmap"+ String.valueOf(correct_answers_of_round));

    }


    /*
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.submit(new Runnable() {
        public void run() {

                    }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
     */


}
