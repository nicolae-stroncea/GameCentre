package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.valueOf;

public class UserScoreboardActivity extends AppCompatActivity {
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences currentUsername = getApplicationContext().getSharedPreferences("sharedUser", MODE_PRIVATE);
        user = currentUsername.getString("thisUser", "User");
        setContentView(R.layout.activity_user_scoreboard);
        TextView gameName = findViewById(R.id.GameNameLabel);
        TextView gameScore = findViewById(R.id.ScoreLabel);
        gameName.setText("Sliding Tiles");
        gameScore.setText(getScorePerGame("SlidingTiles.txt", user));

    }

    public void goToScoreboardMenu (View view){
        Intent intent = new Intent (this, ScoreboardActivity.class);
        startActivity(intent);
    }

    public String getScorePerGame(String fileName, String targetUser){
        String score = "0";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int index = line.indexOf(",");
                String user = line.substring(0, index-1);
                if (user.equals(targetUser)){
                    score = line.substring(index + 1, line.length()-1);
                }
            } bufferedReader.close();
        } catch (FileNotFoundException e) {
            Log.e("Exception", "Unable to open file: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }



}
