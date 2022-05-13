package com.example.pubquizremote.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.pubquizremote.AnswersPlayer;
import com.example.pubquizremote.QuestionData;
import com.example.pubquizremote.databinding.FragmentAbcdRoundBinding;
import com.example.pubquizremote.models.SharedRoundsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class AbcdRoundFragment extends Fragment {

    private FragmentAbcdRoundBinding binding;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private String uid;
    private SharedRoundsViewModel mViewModel;
    private String current_round;
    private SharedRoundsViewModel sharedRoundsViewModel;
    public List<QuestionData> result;
    public List<QuestionData> questionDataListResult;
    private TableLayout tabLayout;
    private int tabSelectedPosition;
    private int tabPreviousSelectedPosition;
    private int radioButtonID;
    private RadioButton selectedRadioButton;
    private int selectedRadioButtonId;
    int radioButtonToSet;
    private String textOfSelectedRadioButton;
    AnswersPlayer answersPlayer;



    public static AbcdRoundFragment newInstance() {
        return new AbcdRoundFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAbcdRoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String current_round = bundle.getString("message");


        sharedRoundsViewModel = new ViewModelProvider(this).get(SharedRoundsViewModel.class);
        sharedRoundsViewModel.get_data(current_round);

        AnswersPlayer answersPlayer = new AnswersPlayer();

        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelectedPosition = tabLayout.getSelectedTabPosition();
                Log.i("Debug_A", "ABCD Fragment - Selected Tab position: "+tabSelectedPosition);
                set_views(tabSelectedPosition,questionDataListResult,answersPlayer);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.answerOptions1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int selectedRadioButtonId) {

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(String.valueOf(tabSelectedPosition),selectedRadioButtonId);
                editor.apply();

                safe_users_radio_choice(answersPlayer);
            }

        });


        final Observer<List<QuestionData>> resultObserver = new Observer<List<QuestionData>>(){
            @Override
            public void onChanged(@Nullable final List<QuestionData> result){
                questionDataListResult = result;
                Log.i("Debug_A", "ABCD Fragment - questionData: "+questionDataListResult);
                tabSelectedPosition = 0;
                set_views(tabSelectedPosition,questionDataListResult,answersPlayer);
            }
        };
        sharedRoundsViewModel.getResult().observe(getViewLifecycleOwner(),resultObserver);


        binding.UserSaveAnswersAbcdRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedRoundsViewModel.safe_player_answers_to_db(current_round,answersPlayer);
            }
        });

    }

    public void safe_users_radio_choice(AnswersPlayer answersPlayer) {

        selectedRadioButton = binding.getRoot().findViewById(binding.answerOptions1.getCheckedRadioButtonId());
        //ternary operator
        textOfSelectedRadioButton = selectedRadioButton != null
                ? (String) selectedRadioButton.getText() : "no selection";

        // write radioButton selection of previous tab to the answerPlayer instance object if something is selected
        if (!"no selection".equals(textOfSelectedRadioButton)) {

            switch (tabPreviousSelectedPosition) {
                case 0:
                    answersPlayer.setPlayerAnswer1(textOfSelectedRadioButton);
                    break;
                case 1:
                    answersPlayer.setPlayerAnswer2(textOfSelectedRadioButton);
                    break;
                case 2:
                    answersPlayer.setPlayerAnswer3(textOfSelectedRadioButton);
                    break;
                case 3:
                    answersPlayer.setPlayerAnswer4(textOfSelectedRadioButton);
                    break;
                case 4:
                    answersPlayer.setPlayerAnswer5(textOfSelectedRadioButton);
                    break;
                case 5:
                    answersPlayer.setPlayerAnswer6(textOfSelectedRadioButton);
                    break;
            }
        }
    }



    public void set_views(int tabSelectedPosition,List<QuestionData> questionDataListResult,AnswersPlayer answersPlayer){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int defaultValue = -1;
        int radioButtonToSet = sharedPref.getInt(String.valueOf(tabSelectedPosition),defaultValue);

        binding.answerOptions1.clearCheck();

        binding.question.setText(questionDataListResult.get(tabSelectedPosition).question);
        binding.answerOptionA.setText(questionDataListResult.get(tabSelectedPosition).answerOptionA);
        binding.answerOptionB.setText(questionDataListResult.get(tabSelectedPosition).answerOptionB);
        binding.answerOptionC.setText(questionDataListResult.get(tabSelectedPosition).answerOptionC);

        tabPreviousSelectedPosition = tabSelectedPosition;
    }



}