package com.jojones.jones_tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class WelcomActivity extends AppCompatActivity {
    public static String pOneName = "";
    public static String pTwoName = "";
    public static int theme = 0;

    private SharedPreferences sharedPreferences;
    private int infoState = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        sharedPreferences = this.getSharedPreferences("com.jojones.jones_tictactoe", Context.MODE_PRIVATE);

        String p1 = sharedPreferences.getString("p1", "");
        String p2 = sharedPreferences.getString("p2", "");
        int theme = sharedPreferences.getInt("theme", -1);

        // Check if pref already saved
        if (p1.equals("") || p2.equals("") || theme <= 0) {
            infoState = 1;
            ((TextView) findViewById(R.id.txtQuestion)).setText("Player 1's Name");
            ((EditText) findViewById(R.id.etAnswer)).setText("PlayerOne");
            findViewById(R.id.tbYesNo).setVisibility(View.INVISIBLE);
            findViewById(R.id.etAnswer).setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    public void btnPressed(View view) {
        if (view.getId() == R.id.btnOk) {
            boolean success = false;

            TextView question = (TextView) findViewById(R.id.txtQuestion);
            EditText text = (EditText) findViewById(R.id.etAnswer);
            ToggleButton btnSaved = (ToggleButton) findViewById(R.id.tbYesNo);

            switch (infoState) {
                case 0:
                    success = true;

                    if (btnSaved.isChecked()) {
                        pOneName = sharedPreferences.getString("p1", "");
                        pTwoName = sharedPreferences.getString("p2", "");
                        theme = sharedPreferences.getInt("theme", -1);

                        goToGame();
                    }
                    else {
                        question.setText("Player 1's Name");
                        btnSaved.setVisibility(View.INVISIBLE);
                        text.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    // Check if name is not empty
                    // TODO: choose AI level
                    if (!text.getText().toString().trim().equals("")) {
                        pOneName = text.getText().toString();
                        success = true;

                        question.setText("Player 2's Name");
                        text.setText("");
                    }
                    break;
                case 2:
                    // Check if name is not empty
                    if (!text.getText().toString().trim().equals("")) {
                        pTwoName = text.getText().toString();
                        success = true;

                        question.setText("Theme");
                        text.setVisibility(View.GONE);
                        (findViewById(R.id.imgTheme01)).setVisibility(View.VISIBLE);
                        (findViewById(R.id.imgTheme02)).setVisibility(View.VISIBLE);
                        (findViewById(R.id.imgTheme03)).setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    if (theme > 0) {
                        success = true;

                        savePreferences();

                        goToGame();
                    }
                    break;
                default:

            }

            if (success) {
                infoState++;
            }
        }
    }

    public void imgPressed(View view) {
        ImageView border = (ImageView) findViewById(R.id.imgBorder);

        switch (view.getId()) {
            case R.id.imgTheme01:
                theme = 1;
                border.setVisibility(View.VISIBLE);
                border.setX(findViewById(R.id.imgTheme01).getX() - 3);
                break;
            case R.id.imgTheme02:
                theme = 2;
                border.setVisibility(View.VISIBLE);
                border.setX(findViewById(R.id.imgTheme02).getX() - 3);
                break;
            case R.id.imgTheme03:
                theme = 3;
                border.setVisibility(View.VISIBLE);
                border.setX(findViewById(R.id.imgTheme03).getX() - 3);
                break;
            default:
                theme = -1;
                border.setVisibility(View.INVISIBLE);
        }
    }

    public void savePreferences() {
        sharedPreferences.edit().putString("p1", pOneName).apply();
        sharedPreferences.edit().putString("p2", pTwoName).apply();
        sharedPreferences.edit().putInt("theme", theme).apply();
    }

    public void goToGame() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}