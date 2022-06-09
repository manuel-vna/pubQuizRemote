package com.example.pubquizremote.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pubquizremote.R;
import com.example.pubquizremote.fragments.AbcdRoundFragment;
import com.example.pubquizremote.fragments.HomeFragment;
import com.example.pubquizremote.fragments.ImageRoundFragment;
import com.example.pubquizremote.models.SharedRoundsViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pubquizremote.databinding.NavLoggedInDrawerLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LoggedInActivity extends AppCompatActivity { //implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavLoggedInDrawerLayoutBinding binding;
    private TextView navigationDrawerPoints;
    FirebaseAuth auth;
    String uid;
    private SharedRoundsViewModel sharedRoundsViewModel;

    public String navigationDrawerPointsString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = NavLoggedInDrawerLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
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

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_infopage, R.id.nav_round1, R.id.nav_round2)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        setNavigationDrawerHeader(navigationView,uid);

    }



    private void setNavigationDrawerHeader(NavigationView navigationView,String uid) {

        View headerView = navigationView.getHeaderView(0);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            TextView navigationDrawerPoints = (TextView) headerView.findViewById(R.id.NavigationDrawerPlayerName);
            navigationDrawerPoints.setText(signInAccount.getDisplayName());
        }

        SharedRoundsViewModel sharedRoundsViewModel = new SharedRoundsViewModel();
        sharedRoundsViewModel.GetDataForNavigationDrawerHeader(headerView);

        final Observer<String> resultObserver2 = new Observer<String>(){
            @Override
            public void onChanged(@Nullable final String result){
                navigationDrawerPointsString = result;
                TextView navigationDrawerPoints = (TextView) headerView.findViewById(R.id.NavigationDrawerPoints);
                navigationDrawerPoints.setText("Aktuelle Punktzahl: " +  navigationDrawerPointsString);
            }
        };
        sharedRoundsViewModel.getNavigationDrawerPoints().observe(this,resultObserver2);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.three_dots_settings, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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
                List<String> uid_admin_list = new ArrayList<String>();
                uid_admin_list.addAll(Arrays.asList("RNo7Y78aQxfRMw7Gx3sqJe6htaw2")); //"RqjdhEXaqhfF3ECQtOrEvV4henp2"
                String uid = auth.getCurrentUser().getUid();
                //if (uid.equals("RqjdhEXaqhfF3ECQtOrEvV4henp2")) {
                if (uid_admin_list.contains(uid)){
                    Intent intentAdmin = new Intent(getApplicationContext(), AdminSettingsActivity.class);
                    intentAdmin.putExtra("uid", uid);
                    startActivity(intentAdmin);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "ADMIN user permissions required", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View toastView = toast.getView();
                    toastView.setBackgroundResource(R.drawable.toast_drawable_background_color);
                    toast.show();
                }
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intentLogout = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}