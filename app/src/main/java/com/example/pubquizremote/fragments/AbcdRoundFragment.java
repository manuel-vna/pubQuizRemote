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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubquizremote.dataobjects.AnswersPlayerData;
import com.example.pubquizremote.dataobjects.QuestionData;
import com.example.pubquizremote.R;
import com.example.pubquizremote.databinding.FragmentAbcdRoundBinding;
import com.example.pubquizremote.models.SharedRoundsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
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
    private int radioButtonID;
    private RadioButton selectedRadioButton;
    private int selectedRadioButtonId;
    private int selectedRadioButtonIdXml;
    int radioButtonToSetID;
    private String textOfSelectedRadioButton;
    AnswersPlayerData answersPlayer;
    SharedPreferences sharedPrefAbcdRound;
    String answerOption;



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

        /*
        Bundle bundle = getArguments();
        String current_round = bundle.getString("message");
        */
        String current_round =  getString(R.string.abcd_round);


        sharedRoundsViewModel = new ViewModelProvider(this).get(SharedRoundsViewModel.class);
        sharedRoundsViewModel.getDataQuestionsAndAnswers(current_round);

        AnswersPlayerData answersPlayer = new AnswersPlayerData();

        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelectedPosition = tabLayout.getSelectedTabPosition();
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

                if (selectedRadioButtonId == R.id.answerOptionA){
                    selectedRadioButtonIdXml = R.id.answerOptionA;
                }
                else if (selectedRadioButtonId == R.id.answerOptionB){
                    selectedRadioButtonIdXml = R.id.answerOptionB;
                }
                else if (selectedRadioButtonId == R.id.answerOptionC){
                    selectedRadioButtonIdXml = R.id.answerOptionC;
                }
                else {
                    selectedRadioButtonIdXml = -1;
                }

                SharedPreferences sharedPrefAbcdRound = getActivity().getSharedPreferences(getString(R.string.abcdRound), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefAbcdRound.edit();
                editor.putInt(String.valueOf(tabSelectedPosition),selectedRadioButtonIdXml);
                editor.apply();

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

                SharedPreferences sharedPrefAbcdRound = getActivity().getSharedPreferences(getString(R.string.abcdRound), Context.MODE_PRIVATE);

                int toast_count = 0;
                //iterate over the six questions
                for (int i = 0; i <= 5; i++) {

                    radioButtonToSetID = sharedPrefAbcdRound.getInt(String.valueOf(i),1);

                    if (radioButtonToSetID == R.id.answerOptionA){
                        answerOption = questionDataListResult.get(i).answerOptionA;
                    }
                    else if (radioButtonToSetID == R.id.answerOptionB){
                        answerOption = questionDataListResult.get(i).answerOptionB;
                    }
                    else if (radioButtonToSetID == R.id.answerOptionC){
                        answerOption = questionDataListResult.get(i).answerOptionC;
                    }
                    else {
                        answerOption = "no_selection";
                        if (toast_count < 1) {
                            Toast.makeText(getContext(), R.string.messagetoUser_answers_incomplete, Toast.LENGTH_LONG).show();
                        }
                        toast_count += 1;
                    }

                    switch (i) {
                        case 0:
                            answersPlayer.setPlayerAnswer0(answerOption);
                            break;
                        case 1:
                            answersPlayer.setPlayerAnswer1(answerOption);
                            break;
                        case 2:
                            answersPlayer.setPlayerAnswer2(answerOption);
                            break;
                        case 3:
                            answersPlayer.setPlayerAnswer3(answerOption);
                            break;
                        case 4:
                            answersPlayer.setPlayerAnswer4(answerOption);
                            break;
                        case 5:
                            answersPlayer.setPlayerAnswer5(answerOption);
                            break;
                    }
                }
                sharedRoundsViewModel.safe_player_answers_to_db(current_round,answersPlayer);
            }
        });


    }


    public void set_views(int tabSelectedPosition, List<QuestionData> questionDataListResult, AnswersPlayerData answersPlayer){

        SharedPreferences sharedPrefAbcdRound = getActivity().getSharedPreferences(getString(R.string.abcdRound), Context.MODE_PRIVATE);
        int defaultValue = -1;
        int radioButtonToSet = sharedPrefAbcdRound.getInt(String.valueOf(tabSelectedPosition),defaultValue);

        binding.question.setText(questionDataListResult.get(tabSelectedPosition).question);
        binding.answerOptionA.setText(questionDataListResult.get(tabSelectedPosition).answerOptionA);
        binding.answerOptionB.setText(questionDataListResult.get(tabSelectedPosition).answerOptionB);
        binding.answerOptionC.setText(questionDataListResult.get(tabSelectedPosition).answerOptionC);

        binding.answerOptions1.check(radioButtonToSet);

    }


}