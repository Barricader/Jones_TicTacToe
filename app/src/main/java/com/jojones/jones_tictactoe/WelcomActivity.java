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

    private int infoState = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
    }

    @SuppressLint("SetTextI18n")
    public void btnPressed(View view) {
        if (view.getId() == R.id.btnOk) {
            boolean success = false;

            TextView question = (TextView) findViewById(R.id.txtQuestion);
            EditText text = (EditText) findViewById(R.id.etAnswer);
            //ToggleButton btnSaved = (ToggleButton) findViewById(R.id.tbYesNo);

            switch (infoState) {
                case 0:
                    // Check if name is not empty
                    // TODO: choose AI level
                    if (!text.getText().toString().trim().equals("")) {
                        pOneName = text.getText().toString();
                        success = true;

//                        question.setText("Player 2's Name");
//                        text.setText("");
                        question.setText("Theme");
                        text.setVisibility(View.GONE);
                        (findViewById(R.id.imgTheme01)).setVisibility(View.VISIBLE);
                        (findViewById(R.id.imgTheme02)).setVisibility(View.VISIBLE);
                        (findViewById(R.id.imgTheme03)).setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    // Check if name is not empty
//                    if (!text.getText().toString().trim().equals("")) {
//                        pTwoName = text.getText().toString();
                        success = true;
                    if (theme > 0) {
                        success = true;

                        goToGame();
                    }

//                    }
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

    public void goToGame() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}