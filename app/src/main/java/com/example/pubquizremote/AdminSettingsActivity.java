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



        binding.buttonBackFromAdminSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoggedInHomeActivity.class);
                startActivity(intent);
            }
        });


    }


}