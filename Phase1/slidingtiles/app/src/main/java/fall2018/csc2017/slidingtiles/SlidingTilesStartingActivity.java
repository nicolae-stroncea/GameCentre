package fall2018.csc2017.slidingtiles;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * The initial activity for the sliding puzzle tile game.
 * Code for picking image inspired from http://androidbitmaps.blogspot.com/2015/04/loading-images-in-android-part-iii-pick.html
 */
public class SlidingTilesStartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static String SAVE_FILENAME;

    /**
     * The board manager.
     */
    private BoardManager boardManager;
    private String tilePicture;

    private Context context;
    private static final int PICK_IMAGE_REQ_CODE = 50;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addPictureButtonListener();

        SharedPreferences currentUsername = getApplicationContext().getSharedPreferences("sharedUser", MODE_PRIVATE);
        String user = currentUsername.getString("thisUser", "User");
        SAVE_FILENAME = user + "save_file.ser";

    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = SaveAndLoad.loadFromFile(context, SAVE_FILENAME);
                if (boardManager == null) {
                    Toast.makeText(getApplicationContext(), "No previously saved game.", Toast.LENGTH_SHORT).show();
                } else {
                    makeToastLoadedText();
                    loadGame();
                }

            }
        });
    }

    /**
     * Activate the choose picture
     */
    private void addPictureButtonListener() {
        Button choosePicture = findViewById(R.id.ChooseTilePicture);
        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(gallery, PICK_IMAGE_REQ_CODE);
            }
        });
    }

    /**
     * Get the picture
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If the result is our code, that means we are getting the result from the gallery activity
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQ_CODE) {
            // data returns the Uri(address) of the selected picture
            tilePicture = data.getData().toString();
            boardManager = SaveAndLoad.loadFromFile(context, SAVE_FILENAME);
            if (boardManager != null) {
                Board board = boardManager.getBoard();
                board.setPicturePath(tilePicture);
                SaveAndLoad.saveToFile(context, SAVE_FILENAME, boardManager);
            }
        }
    }


    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }


    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        boardManager = SaveAndLoad.loadFromFile(this, SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void loadGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);


    }

    private void newGame() {
        Intent tmp = new Intent(this, ChooseDimensionsActivity.class);
        if (tilePicture != null) {
            tmp.putExtra("TileImage", tilePicture);
        }

        startActivity(tmp);


    }
}