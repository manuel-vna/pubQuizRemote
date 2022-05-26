package com.example.pubquizremote.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pubquizremote.models.AdminSettingsViewModel;
import com.example.pubquizremote.databinding.ActivityAdminSettingsBinding;
import com.example.pubquizremote.utils.AdminSettingsCalculations;



public class AdminSettingsActivity extends AppCompatActivity {

    public ActivityAdminSettingsBinding binding;
    //AdminSettingsViewModel adminSettingsViewModel = new AdminSettingsViewModel(getApplication());
    AdminSettingsViewModel adminSettingsViewModel = new AdminSettingsViewModel();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("uid");

        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

/*
        binding.buttonInitialiseDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminSettingsCalculations.set_initial_db_structure();
            }
        });

 */


        binding.buttonCalculateRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adminSettingsViewModel.evaluate_results_of_round();
            }
        });


        binding.buttonBackfromAdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
                startActivity(intent);
            }
        });

    }

}