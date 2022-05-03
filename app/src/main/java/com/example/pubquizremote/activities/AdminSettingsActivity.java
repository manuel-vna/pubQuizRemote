package com.example.pubquizremote.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pubquizremote.models.AdminSettingsViewModel;
import com.example.pubquizremote.databinding.ActivityAdminSettingsBinding;



public class AdminSettingsActivity extends AppCompatActivity {

    public ActivityAdminSettingsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("uid");
        //Log.w("Debug_A", "AdminSettingsActivity -> uid: "+uid);

        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdminSettingsViewModel adminSettingsViewModel = new AdminSettingsViewModel(getApplication());


        binding.buttonBackfromAdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
                startActivity(intent);
            }
        });


        binding.buttonInitialiseDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminSettingsViewModel.initialise_db();

            }
        });


        binding.buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String round = "round1";
                String question_no = "2";

                adminSettingsViewModel.add_question_to_round(binding.editTextQuestion.getText().toString(),question_no,
                        round,binding.editTextCorrectAnswer.getText().toString());

            }
        });


        binding.buttonCalculateRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminSettingsViewModel.evaluate_results_of_round();

            }
        });

    }


}