package com.example.pubquizremote.fragments;

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
import android.widget.TableLayout;

import com.example.pubquizremote.QuestionData;
import com.example.pubquizremote.R;
import com.example.pubquizremote.activities.LoggedInActivity;
import com.example.pubquizremote.databinding.FragmentAbcdRoundBinding;
import com.example.pubquizremote.databinding.FragmentImageRoundBinding;
import com.example.pubquizremote.models.SharedRoundsViewModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AbcdRoundFragment extends Fragment {

    private FragmentAbcdRoundBinding binding;
    private SharedRoundsViewModel mViewModel;
    private String current_round;
    private SharedRoundsViewModel sharedRoundsViewModel;
    public List<QuestionData> result;
    public List<QuestionData> questionDataListResult;
    TableLayout tabLayout;
    Integer tabSelectedPosition;
    Integer tabPreviousSelectedPosition;


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

    public void set_views(int tabSelectedPosition,List<QuestionData> questionDataListResult){

        binding.question.setText(questionDataListResult.get(tabSelectedPosition).question);
        binding.answerOptionA.setText(questionDataListResult.get(tabSelectedPosition).answerOptionA);
        binding.answerOptionB.setText(questionDataListResult.get(tabSelectedPosition).answerOptionB);
        binding.answerOptionC.setText(questionDataListResult.get(tabSelectedPosition).answerOptionC);

        tabPreviousSelectedPosition = tabSelectedPosition;

        switch (tabPreviousSelectedPosition) {
            case 1:
                binding.answerOptions1.setVisibility(View.GONE);
            case 2:
                binding.answerOptions2.setVisibility(View.GONE);
                break;
            case 3:
                binding.answerOptions3.setVisibility(View.GONE);
                break;
            case 4:
                binding.answerOptions4.setVisibility(View.GONE);
                break;
            case 5:
                binding.answerOptions5.setVisibility(View.GONE);
                break;
            case 6:
                binding.answerOptions6.setVisibility(View.GONE);
                break;
        }
        switch (tabSelectedPosition) {
            case 1:
                binding.answerOptions1.setVisibility(View.VISIBLE);
            case 2:
                binding.answerOptions2.setVisibility(View.VISIBLE);
                break;
            case 3:
                binding.answerOptions3.setVisibility(View.VISIBLE);
                break;
            case 4:
                binding.answerOptions4.setVisibility(View.VISIBLE);
                break;
            case 5:
                binding.answerOptions5.setVisibility(View.VISIBLE);
                break;
            case 6:
                binding.answerOptions6.setVisibility(View.VISIBLE);
                break;
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String current_round = bundle.getString("message");
        Log.i("Debug_A", "ABCD Fragment - current_round: "+current_round);

        sharedRoundsViewModel = new ViewModelProvider(this).get(SharedRoundsViewModel.class);
        sharedRoundsViewModel.get_data(current_round);

        TabLayout tabLayout = binding.tabLayout;

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelectedPosition = tabLayout.getSelectedTabPosition();
                Log.i("Debug_A", "ABCD Fragment - Selected Tab position: "+tabSelectedPosition);
                set_views(tabSelectedPosition,questionDataListResult);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });




        final Observer<List<QuestionData>> resultObserver = new Observer<List<QuestionData>>(){
            @Override
            public void onChanged(@Nullable final List<QuestionData> result){
                questionDataListResult = result;
                Log.i("Debug_A", "ABCD Fragment - questionData: "+questionDataListResult);
                tabSelectedPosition = 0;
                set_views(tabSelectedPosition,questionDataListResult);

            }

        };

        sharedRoundsViewModel.getResult().observe(getViewLifecycleOwner(),resultObserver);

        binding.UserSaveAnswersAbcdRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                safe_player_answers_to_db(current_round);
            }
        });

    }

    private void safe_player_answers_to_db(String current_round) {


    }



}