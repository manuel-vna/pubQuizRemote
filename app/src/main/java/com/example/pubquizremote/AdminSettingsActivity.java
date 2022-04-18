package com.example.pubquizremote;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pubquizremote.databinding.AdminSettingsActivityBinding;



public class AdminSettingsActivity extends AppCompatActivity {

    public AdminSettingsActivityBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("uid");
        Log.w("Debug_A", "AdminSettingsActivity -> uid: "+uid);

        binding = AdminSettingsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.buttonBackfromAdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoggedInHomeActivity.class);
                startActivity(intent);
            }
        });


        binding.buttonInitialiseDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdminSettingsViewModel.initialise_db();

            }
        });


        binding.buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String round = "round1";
                String question_no = "2";

                AdminSettingsViewModel.add_question_to_round(binding.editTextQuestion.getText().toString(),question_no,
                        round,binding.editTextCorrectAnswer.getText().toString());

            }
        });


        binding.buttonCalculateRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdminSettingsViewModel.evaluate_results_of_round();

            }
        });

    }


}