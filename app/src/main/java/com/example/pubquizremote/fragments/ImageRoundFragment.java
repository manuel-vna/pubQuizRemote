package com.example.pubquizremote.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pubquizremote.AnswersPlayer;
import com.example.pubquizremote.QuestionData;
import com.example.pubquizremote.models.SharedRoundsViewModel;
import com.example.pubquizremote.databinding.FragmentImageRoundBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ImageRoundFragment extends Fragment {

    private FragmentImageRoundBinding binding;
    private SharedRoundsViewModel sharedRoundsViewModel;
    private String current_round;
    String[] questionNos = {"1", "2", "3", "4", "5", "6"};
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String uid = auth.getCurrentUser().getUid();
    public List<QuestionData> questionDataListResult;
    List<String> answer_options = Arrays.asList("0", "1", "2", "3", "4", "5");
    AnswersPlayer answersPlayer;
    String spinnerAnswerOptions1;
    String spinnerAnswerOptions2;
    String spinnerAnswerOptions3;
    String spinnerAnswerOptions4;
    String spinnerAnswerOptions5;
    String spinnerAnswerOptions6;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentImageRoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String current_round = bundle.getString("message");
        Log.i("Debug_A", "ABCD Fragment - current_round: "+current_round);

        sharedRoundsViewModel = new ViewModelProvider(this).get(SharedRoundsViewModel.class);
        sharedRoundsViewModel.get_data("round2");

        final Observer<List<QuestionData>> resultObserver = new Observer<List<QuestionData>>(){
            @Override
            public void onChanged(@Nullable final List<QuestionData> result){
                questionDataListResult = result;
                Log.i("Debug_A", "ABCD Fragment - questionData: "+questionDataListResult);
                set_images_in_ui_views(questionDataListResult);
            }
        };

        sharedRoundsViewModel.getResult().observe(getViewLifecycleOwner(),resultObserver);

        binding.UserSaveAnswersImageRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                safe_player_answers_to_db(current_round);
                Log.w("Debug_A", "Button pressed");
            }
        });
    }



    public void set_images_in_ui_views(List<QuestionData> questionDataList){

        Log.w("Debug_A", "questionDataList A2"+questionDataList);

        try {
            for (QuestionData question_block : questionDataList) {

                switch (question_block.questionNo) {
                    case "1":
                        Picasso.get().load(question_block.picture).into(binding.pic1);
                        answer_options.set(0, question_block.answerCorrect);
                        binding.textView1.setText(question_block.question);
                        break;
                    case "2":
                        Picasso.get().load(question_block.picture).into(binding.pic2);
                        answer_options.set(4, question_block.answerCorrect);
                        binding.textView2.setText(question_block.question);
                        break;
                    case "3":
                        Picasso.get().load(question_block.picture).into(binding.pic3);
                        answer_options.set(1, question_block.answerCorrect);
                        binding.textView3.setText(question_block.question);
                        break;
                    case "4":
                        Picasso.get().load(question_block.picture).into(binding.pic4);
                        answer_options.set(5, question_block.answerCorrect);
                        binding.textView4.setText(question_block.question);
                        break;
                    case "5":
                        Picasso.get().load(question_block.picture).into(binding.pic5);
                        answer_options.set(3, question_block.answerCorrect);
                        binding.textView5.setText(question_block.question);
                        break;
                    case "6":
                        Picasso.get().load(question_block.picture).into(binding.pic6);
                        answer_options.set(2, question_block.answerCorrect);
                        binding.textView6.setText(question_block.question);
                        break;
                }
            }
        }
        catch(Exception e){
            Log.e("Debug_A", "Error: "+e);
            Toast.makeText(getContext(),"Data could not be loaded!", Toast.LENGTH_LONG).show();
        }
        set_player_answer_options(answer_options);
    }

    private void set_player_answer_options(List answer_options) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, answer_options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAnswerOptions1.setAdapter(adapter);
        binding.spinnerAnswerOptions2.setAdapter(adapter);
        binding.spinnerAnswerOptions3.setAdapter(adapter);
        binding.spinnerAnswerOptions4.setAdapter(adapter);
        binding.spinnerAnswerOptions5.setAdapter(adapter);
        binding.spinnerAnswerOptions6.setAdapter(adapter);
    }

    private void safe_player_answers_to_db(String current_round){

        // if user doesn't give input: save an empty string to the database
        String spinnerAnswerOptions1 = binding.spinnerAnswerOptions1.getSelectedItem() != null
                ? binding.spinnerAnswerOptions1.getSelectedItem().toString()
                : "";
        String spinnerAnswerOptions2 = binding.spinnerAnswerOptions2.getSelectedItem() != null
                ? binding.spinnerAnswerOptions2.getSelectedItem().toString()
                : "";
        String spinnerAnswerOptions3 = binding.spinnerAnswerOptions3.getSelectedItem() != null
                ? binding.spinnerAnswerOptions3.getSelectedItem().toString()
                : "";
        String spinnerAnswerOptions4 = binding.spinnerAnswerOptions4.getSelectedItem() != null
                ? binding.spinnerAnswerOptions4.getSelectedItem().toString()
                : "";
        String spinnerAnswerOptions5 = binding.spinnerAnswerOptions5.getSelectedItem() != null
                ? binding.spinnerAnswerOptions5.getSelectedItem().toString()
                : "";
        String spinnerAnswerOptions6 = binding.spinnerAnswerOptions6.getSelectedItem() != null
                ? binding.spinnerAnswerOptions6.getSelectedItem().toString()
                : "";

        answersPlayer = new AnswersPlayer(
                spinnerAnswerOptions1,
                spinnerAnswerOptions2,
                spinnerAnswerOptions3,
                spinnerAnswerOptions4,
                spinnerAnswerOptions5,
                spinnerAnswerOptions6
            );

        String uid = auth.getCurrentUser().getUid();
        DatabaseReference ref_player_answers = database.getReference("player_data").child(uid).child(current_round);
        ref_player_answers.setValue(answersPlayer);
    }

}
