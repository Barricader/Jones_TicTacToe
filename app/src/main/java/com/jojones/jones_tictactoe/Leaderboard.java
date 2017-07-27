package com.jojones.jones_tictactoe;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import org.json.JSONException;
import org.json.JSONObject;

public class Leaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Intent intent = getIntent();
        String[] lb = intent.getStringArrayExtra("leaderboardarray");
        JSONObject reader = new JSONObject();
        String name;
        int score;
        int compscore;
        int tVId;
        TextView tv;

        for (int i = 0; i < lb.length; i++){
            if (lb[i] == null){
                lb[i] = "\"name\" : \"-\" , \"score\" : 0 , \"computerscore\" : 0";
            }
            try {
                reader = new JSONObject(lb[i]);
                name = reader.getString("name");
                score = reader.getInt("score");
                compscore = reader.getInt("computerscore");
                Log.e("HEYO", Integer.toString(i+1) + " " + name);
                tVId = getResources().getIdentifier("z" + Integer.toString(i + 1), "id", getApplicationContext().getPackageName());
                tv = (TextView) findViewById(tVId);
                tv.setText(name);

                tVId = getResources().getIdentifier("z" + Integer.toString(i + 1) + "w", "id", getApplicationContext().getPackageName());
                tv = (TextView) findViewById(tVId);
                tv.setText(Integer.toString(score));

                tVId = getResources().getIdentifier("z" + Integer.toString(i + 1) + "l", "id", getApplicationContext().getPackageName());
                tv = (TextView) findViewById(tVId);
                tv.setText(Integer.toString(compscore));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
