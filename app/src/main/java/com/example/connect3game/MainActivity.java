package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0: yellow, 1: red
    int activePlayer = 0;
    // 2: empty
    int[] gameState = { 2, 2, 2, 2, 2, 2, 2, 2, 2};
    boolean gameActive = true;

    // hor, vertical, and diag
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {3, 4, 6}
    };

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        // Putting tag on a view for keeping track of the "Game State"
        // Log.i("Tag ", counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // To start the game
        if (gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activePlayer;

            // Set it top of the Screen
            counter.setTranslationY(-1500);
            // Set image(color) to the "view"
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            // Loop through all of the winning Positions
            // To check if positions are the same color
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2
                ) {
                    // Some has won!
                    gameActive = false;

                    String winner = "";
                    if (activePlayer == 1) {
                        winner = "Yellow";
                    } else {
                        winner = "Red";
                    }
                    Toast.makeText(this, winner + " is the WINNER!!!", Toast.LENGTH_SHORT).show();

                    // Display play again button and winner info
                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                    winnerTextView.setText(winner + " has won!!");
                    // Show on Screen
                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);

                }
            }
        }
    }

    public void playAgain(View view) {
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        // Invisible on Screen
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        // Loop through all view objects to make it invisible
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            // Remove the image source
            counter.setImageDrawable(null);

        }
        // Set game states back to initial
        activePlayer = 0;
        gameActive = true;
        // CANNOT update an array this way:
        // gameState = { 2, 2, 2, 2, 2, 2, 2, 2, 2};
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}