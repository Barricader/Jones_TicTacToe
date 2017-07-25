package com.jojones.jones_tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Board b;
    ImageView[] imgs = new ImageView[Board.NUM_SQUARES];
    Bitmap activeX;
    Bitmap activeO;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch (WelcomActivity.theme) {
            case 1:
                activeX = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.x_01);
                activeO = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.o_01);
                break;
            case 2:
                activeX = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.x_02);
                activeO = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.o_02);
                break;
            case 3:
                activeX = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.x_03);
                activeO = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.o_03);
                break;
            default:
                activeX = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.x_01);
                activeO = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.o_01);
        }

        ((TextView) findViewById(R.id.txtNameTurn)).setText(WelcomActivity.pOneName + "'s\nTurn");

        b = new Board();
        imgs[0] = (ImageView) findViewById(R.id.square0);
        imgs[1] = (ImageView) findViewById(R.id.square1);
        imgs[2] = (ImageView) findViewById(R.id.square2);
        imgs[3] = (ImageView) findViewById(R.id.square3);
        imgs[4] = (ImageView) findViewById(R.id.square4);
        imgs[5] = (ImageView) findViewById(R.id.square5);
        imgs[6] = (ImageView) findViewById(R.id.square6);
        imgs[7] = (ImageView) findViewById(R.id.square7);
        imgs[8] = (ImageView) findViewById(R.id.square8);
    }

    public void playAgain(View view) {
        Intent myIntent = new Intent(this, WelcomActivity.class);
        startActivity(myIntent);
    }

    @SuppressLint("SetTextI18n")
    public void squarePressed(View view) {
        int squareID = -1;

        switch (view.getId()) {
            case R.id.square0:
                squareID = 0;
                break;
            case R.id.square1:
                squareID = 1;
                break;
            case R.id.square2:
                squareID = 2;
                break;
            case R.id.square3:
                squareID = 3;
                break;
            case R.id.square4:
                squareID = 4;
                break;
            case R.id.square5:
                squareID = 5;
                break;
            case R.id.square6:
                squareID = 6;
                break;
            case R.id.square7:
                squareID = 7;
                break;
            case R.id.square8:
                squareID = 8;
                break;
            default:

        }

        if (b.squares[squareID] == Board.State.SQUARE_EMPTY && b.winner == 0) {
            b.updateSquare(squareID, b.turn % 2 == 0 ? Board.State.SQUARE_O : Board.State.SQUARE_X);
            imgs[squareID].setImageBitmap(b.turn % 2 == 1 ? activeO : activeX);

            // TODO: show player's game piece

            ((TextView) findViewById(R.id.txtNameTurn)).setText((b.turn % 2 == 0 ? WelcomActivity.pTwoName : WelcomActivity.pOneName) + "'s\nTurn");
        }

        if (b.winner > 0) {
            // Player win's or a tie
            switch (b.winner) {
                case 1:
                    ((TextView) findViewById(R.id.txtNameTurn)).setText(WelcomActivity.pOneName + "\nWINS!");
                    break;
                case 2:
                    ((TextView) findViewById(R.id.txtNameTurn)).setText(WelcomActivity.pTwoName + "\nWINS!");
                    break;
                case 3:
                    ((TextView) findViewById(R.id.txtNameTurn)).setText("It is a tie!");
                    break;
                default:

            }

            findViewById(R.id.btnRetry).setVisibility(View.VISIBLE);
        }
    }
}
