package com.example.pubquizremote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {

    TextView textView2;
    Button buttonLogout;
    Button buttonAddAnswerTest;
    EditText editTextAnswerTest;
    FirebaseAuth auth;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        auth = FirebaseAuth.getInstance();

        textView2 = findViewById(R.id.textView2);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonAddAnswerTest = findViewById(R.id.buttonAddAnswerTest);
        editTextAnswerTest = findViewById(R.id.editTextAnswerTest);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null){
            textView2.setText(signInAccount.getEmail());
        }

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


        buttonAddAnswerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("Debug_A", "Hello");

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
                String uid = auth.getCurrentUser().getUid();
                DatabaseReference ref_uid = database.getReference(uid);
                ref_uid.child("players").child(uid).setValue("answer1", editTextAnswerTest.getText().toString());

            }
        });


    }


}