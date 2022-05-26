package com.example.pubquizremote.activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pubquizremote.R;
import com.example.pubquizremote.models.AdminSettingsViewModel;
import com.example.pubquizremote.databinding.ActivityAdminSettingsBinding;
import java.util.Arrays;
import java.util.List;


public class AdminSettingsActivity extends AppCompatActivity {

    public ActivityAdminSettingsBinding binding;
    AdminSettingsViewModel adminSettingsViewModel = new AdminSettingsViewModel();
    private String current_round;
    List<String> round_options = Arrays.asList("round1", "round2");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        String uid = bundle.getString("uid");

        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.buttonInitialiseDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation_dialogbox();
            }
        });



        binding.buttonCalculateRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.SpinnnerCalculateRound != null) {
                    adminSettingsViewModel.get_from_firebase_round_results_startpoint(
                            binding.SpinnnerCalculateRound.getSelectedItem().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.NoRoundNumberErrorMessage,
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        binding.buttonBackfromAdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoggedInActivity.class);
                startActivity(intent);
            }
        });


        set_spinner_calculate_round(round_options);

    }


    private void confirmation_dialogbox() {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        //builder.setMessage("Do you want to exit?");
        builder.setMessage("Do you really want to overwrite the database with an initial data setup? All current data will be lost.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adminSettingsViewModel.get_from_AdminSettingsCalulation_initial_structure();
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"Canceled",Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Confirm Overwriting DB");
        alert.show();
    }


    private void set_spinner_calculate_round(List<String> round_options) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, round_options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.SpinnnerCalculateRound.setAdapter(adapter);
    }


}