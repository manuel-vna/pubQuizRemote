package com.example.pubquizremote;


import static com.example.pubquizremote.ImageRoundFragment.answersPlayer;
import com.example.pubquizremote.databinding.FragmentRoundBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public abstract class RoundFragment {

    private FragmentRoundBinding binding;
    String current_round;
    AnswersPlayer answerPlayer;
    static FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
    static FirebaseAuth auth = FirebaseAuth.getInstance();
    static String[] questionNos = {"1", "2", "3", "4", "5", "6"};
    String uid = auth.getCurrentUser().getUid();





    void player_answers_to_db(){

        DatabaseReference ref_player_answers = database.getReference("players").child(uid).child(current_round);

        answerPlayer = ImageRoundFragment.answersPlayer;

        ref_player_answers.setValue(answersPlayer);



    }


}