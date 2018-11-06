package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

/**
 * The Game Launcher Screen
 */
public class GameLauncherActivity extends AppCompatActivity implements View.OnClickListener {
    Button Scoreboards, TilesGame;
    String user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelauncheractivity);
        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        myToolbar.setLogo(R.drawable.ic_launcher_foreground);
        setSupportActionBar(myToolbar);
        TilesGame = findViewById(R.id.TilesGame);
        Scoreboards = findViewById(R.id.ScoreboardButton);
        TilesGame.setOnClickListener(this);
        Scoreboards.setOnClickListener(this);
        user = SharedPreferenceManager.getSharedValue(this, "sharedUser", "thisUser");
        createFiles(user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signoff:
                startActivity(new Intent(this, LoginActivity.class));
                SharedPreferenceManager.deleteUser(this, "sharedUser", "thisUser");

                return true;
        }
        return true;

    }


    public void createFiles(String userFile) {
        File userScoreFile = new File(this.getFilesDir(), userFile + "Score.txt");
        File slidingFile = new File(this.getFilesDir(), "SlidingTiles.txt");
        try {
            userScoreFile.createNewFile();
            slidingFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TilesGame:
                startActivity(new Intent(this,
                        SlidingTilesStartingActivity.class));
                break;
            case R.id.ScoreboardButton:
                startActivity(new Intent(this,
                        ScoreboardMenuActivity.class));
                break;
        }
    }
}