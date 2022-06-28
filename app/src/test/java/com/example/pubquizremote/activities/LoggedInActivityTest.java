package com.example.pubquizremote.activities;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import android.view.MenuItem;

import com.example.pubquizremote.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class LoggedInActivityTest {


    @Before
    public void setUp(){
        AdminSettingsActivity adminSettingsActivity = Mockito.mock(AdminSettingsActivity.class);
    }


    @Test
    private void onOptionsItemSelected(String admin_settings) {

        String role = "player";
        LoggedInActivity loggedInActivity = Mockito.mock(LoggedInActivity.class);

    }


}