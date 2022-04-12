package com.example.pubquizremote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pubquizremote.databinding.ActivityLoggedInHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoggedInHomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityLoggedInHomeBinding binding;
    private TextView navigationDrawerPoints;

    FirebaseAuth auth;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoggedInHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pub-quiz-remote-default-rtdb.europe-west1.firebasedatabase.app");
        String uid = auth.getCurrentUser().getUid();


        setSupportActionBar(binding.appBarLoggedInHome.toolbar);
        binding.appBarLoggedInHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_round_one, R.id.nav_round_two)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_logged_in_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        setFieldsHeaderView(navigationView,database,uid);

    }

    private void setFieldsHeaderView(NavigationView navigationView,FirebaseDatabase database,String uid) {

        // set player NAME in NavigationView header
        View headerView = navigationView.getHeaderView(0);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            TextView navigationDrawerPoints = (TextView) headerView.findViewById(R.id.NavigationDrawerPlayerName);
            navigationDrawerPoints.setText(signInAccount.getDisplayName());
        }

        // set player POINTS in NavigationView header
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String points = dataSnapshot.getValue(String.class);

                TextView navigationDrawerPoints = (TextView) headerView.findViewById(R.id.NavigationDrawerPoints);
                navigationDrawerPoints.setText("Aktuelle Punktzahl: "+points);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Debug_A", "loadingPoints:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference ref_uid = database.getReference("players").child(uid).child("points");
        ref_uid.addValueEventListener(postListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged_in_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_logged_in_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(getApplicationContext(),"settings chosen", Toast.LENGTH_LONG).show();
                return true;
            case R.id.adminSettings:
                String uid = auth.getCurrentUser().getUid();
                if (uid.equals("RqjdhEXaqhfF3ECQtOrEvV4henp2")) {
                    Intent intentAdmin = new Intent(getApplicationContext(), AdminSettingsActivity.class);
                    intentAdmin.putExtra("uid", uid);
                    startActivity(intentAdmin);
                }
                else {
                    Toast.makeText(getApplicationContext(),"You are not registered as an admin user", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intentLogout = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}