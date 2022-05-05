package com.example.pubquizremote.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pubquizremote.R;
import com.example.pubquizremote.activities.LoggedInActivity;
import com.example.pubquizremote.models.SharedRoundsViewModel;

public class AbcdRoundFragment extends Fragment {

    private SharedRoundsViewModel mViewModel;
    private String current_round;


    public static AbcdRoundFragment newInstance() {
        return new AbcdRoundFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String current_round = bundle.getString("message");
        Log.i("Debug_A", "ABCD Fragment - current_round: "+current_round);


        return inflater.inflate(R.layout.fragment_abcd_round, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}