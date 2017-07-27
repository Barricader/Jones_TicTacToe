package com.jojones.jones_tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

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

        b = new Board(this);
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

    public void newGame(View view) {
        Intent myIntent = new Intent(this, WelcomActivity.class);
        startActivity(myIntent);
    }

    public void replay(View view) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.tictac", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("id", "replay").apply();
        Intent myIntent = new Intent(this, WelcomActivity.class);
        startActivity(myIntent);
    }

    public void updateImg(int img, Board.State s) {
        imgs[img].setImageBitmap(s == Board.State.SQUARE_O ? activeO : activeX);

    }
    public void changeTurn(){
        alternatePlayer();
    }
    public static int turnCounter = 0;
    public void alternatePlayer(){

        if (turnCounter % 2 ==0){
            updateplayerStats(activeX,WelcomActivity.pOneName );

        }
            turnCounter++;
    }
    public void updateplayerStats(Bitmap avatar, String playerName){
        ImageView playerPiece = (ImageView) findViewById(R.id.piecePreview);
        TextView playerTurn = (TextView) findViewById(R.id.txtNameTurn);
        playerTurn.setText(playerName);
        playerPiece.setImageBitmap(avatar);

    }

    public void squarePressed(View view) {
        int squareID = -1;

            changeTurn();


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
            b.updateSquare(squareID, b.playerPiece);
//            imgs[squareID].setImageBitmap(b.turn % 2 == 1 ? activeO : activeX);

            // TODO: show player's game piece

//            ((TextView) findViewById(R.id.txtNameTurn)).setText((b.turn % 2 == 0 ? WelcomActivity.pTwoName : WelcomActivity.pOneName) + "'s\nTurn");
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

            //Currently super broken

            ExecuteAsyncMethod();
            findViewById(R.id.btnRetry).setVisibility(View.VISIBLE);
            findViewById(R.id.btnReplay).setVisibility(View.VISIBLE);
        }

    }

    public void ExecuteAsyncMethod(){
        new OpenMongoConnectionTask().execute();
    }
}

class OpenMongoConnectionTask extends AsyncTask<Void, Void, Void> {
    protected Void doInBackground(Void... voids) {
        MongoClient mongoClient = null;
        MongoCredential credential = MongoCredential.createScramSha1Credential("root", "admin", "Mv21Uf5WYkj4".toCharArray());
        try {
            ServerAddress adr = new ServerAddress("13.59.196.169", 27017);
            mongoClient = new MongoClient(adr,Arrays.asList(credential));
            DB db = mongoClient.getDB("tictactoe");
            DBCollection coll = db.getCollection("scores");
            BasicDBObject doc = new BasicDBObject("name", "MongoDB").append("playername", "score")
                    .append("count", 1)
                    .append("info", new BasicDBObject("x", 203).append("y", 102));
            coll.insert(doc);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
