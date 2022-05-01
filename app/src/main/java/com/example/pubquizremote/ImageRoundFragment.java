package com.example.pubquizremote;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pubquizremote.databinding.FragmentImageRoundBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ImageRoundFragment extends Fragment implements RoundFragment {

    private FragmentImageRoundBinding binding;
    private SharedRoundsViewModel sharedRoundsViewModel;
    public String round = "round1";
    String[] questionNos = {"1", "2", "3", "4", "5", "6"};
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String uid = auth.getCurrentUser().getUid();
    public List<QuestionData> questionDataList3;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sharedRoundsViewModel = new ViewModelProvider(this).get(SharedRoundsViewModel.class);
        sharedRoundsViewModel.get_images(round);

        binding = FragmentImageRoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        questionDataList3 = sharedRoundsViewModel.questionDataList2;
        //Log.w("Debug_A", "questionDataList A1"+questionDataList3);
        send_images_to_ui_fragment(questionDataList3);


    }



    public void send_images_to_ui_fragment(List<QuestionData> questionDataList){

        Log.w("Debug_A", "questionDataList A2"+questionDataList);

        for (QuestionData question_block : questionDataList) {

            //Log.w("Debug_A", "questionNo: "+question_block.questionNo);
            //Log.w("Debug_A", "correctAnswer: "+question_block.correctAnswer);

            switch (question_block.questionNo) {
                case "1":
                    Picasso.get().load(question_block.picture).into(binding.pic1);
                    //answer_options.set(3,question_block.correctAnswer);
                    break;
                case "2":
                    Picasso.get().load(question_block.picture).into(binding.pic2);
                    //answer_options.set(5,question_block.correctAnswer);
                    break;
                case "3":
                    Picasso.get().load(question_block.picture).into(binding.pic3);
                    //answer_options.set(1,question_block.correctAnswer);
                    break;
                case "4":
                    Picasso.get().load(question_block.picture).into(binding.pic4);
                    //answer_options.set(6,question_block.correctAnswer);
                    break;
                case "5":
                    Picasso.get().load(question_block.picture).into(binding.pic5);
                    //answer_options.set(2,question_block.correctAnswer);
                    break;
                case "6":
                    Picasso.get().load(question_block.picture).into(binding.pic6);
                    //answer_options.set(4,question_block.correctAnswer);
                    break;
            }
        }
    }


}





/*
        answersPlayer = new AnswersPlayer(
            binding.spinnerAnswerOptions1.getSelectedItem().toString(),
            binding.spinnerAnswerOptions2.getSelectedItem().toString(),
            binding.spinnerAnswerOptions3.getSelectedItem().toString(),
            binding.spinnerAnswerOptions4.getSelectedItem().toString(),
            binding.spinnerAnswerOptions5.getSelectedItem().toString(),
            binding.spinnerAnswerOptions6.getSelectedItem().toString()
        );
         */