package com.jojones.jones_tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Leaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        String[] lb = MainActivity.leaderboard;
        //int picId = getResources().getIdentifier("1", "layout", getApplicationContext().getPackageName());

    }
}
