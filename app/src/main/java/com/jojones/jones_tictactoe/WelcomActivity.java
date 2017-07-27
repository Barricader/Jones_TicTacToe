package com.jojones.jones_tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class WelcomActivity extends AppCompatActivity {
    public static String pOneName = "";
    public static int theme = 0;
    public static int playerPiece = 0;

    private int infoState = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        SharedPreferences sharedPreferences =
                this.getSharedPreferences("com.example.tictac",
                        Context.MODE_PRIVATE);
        String pInput = sharedPreferences.getString("id", "");
        if(pInput.equals("replay"))
        {
            sharedPreferences.edit().clear().commit();
            goToGame();
        }
    }

    @SuppressLint("SetTextI18n")
    public void btnPressed(View view) {
        if (view.getId() == R.id.btnOk) {
            boolean success = false;

            TextView question = (TextView) findViewById(R.id.txtQuestion);
            EditText text = (EditText) findViewById(R.id.etAnswer);

            switch (infoState) {
                case 0:
                    // Check if name is not empty
                    if (!text.getText().toString().trim().equals("")) {
                        pOneName = text.getText().toString();
                        success = true;

                        question.setText("Game Piece");
                        text.setText("");
                        text.setVisibility(View.INVISIBLE);

                        findViewById(R.id.imgO).setVisibility(View.VISIBLE);
                        findViewById(R.id.imgX).setVisibility(View.VISIBLE);
                    }
                    break;
//                case 1:
//                    // TODO: choose AI level
//
////                    if (playerPiece != 0) {
////                        text.setVisibility(View.VISIBLE);
////                        success = true;
////                    }
//
//                    success = true;
//                    break;
                case 1:
                    // Check if name is not empty
                    if (playerPiece != 0) {
                        success = true;

                        question.setText("Theme");

                        findViewById(R.id.imgO).setVisibility(View.GONE);
                        findViewById(R.id.imgX).setVisibility(View.GONE);
                        findViewById(R.id.imgBorder).setVisibility(View.GONE);
                        (findViewById(R.id.imgTheme01)).setVisibility(View.VISIBLE);
                        (findViewById(R.id.imgTheme02)).setVisibility(View.VISIBLE);
                        (findViewById(R.id.imgTheme03)).setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    if (theme > 0) {
                        success = true;

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
            case R.id.imgO:
                playerPiece = 2;
                border.setVisibility(View.VISIBLE);
                border.setX(findViewById(R.id.imgO).getX() - 3);
                break;
            case R.id.imgX:
                playerPiece = 1;
                border.setVisibility(View.VISIBLE);
                border.setX(findViewById(R.id.imgX).getX() - 3);
                break;
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
