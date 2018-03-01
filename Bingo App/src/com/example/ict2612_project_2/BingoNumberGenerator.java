package com.example.ict2612_project_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This class is responsible for randomly creating a Bingo number. The same number is not allowed to be called twice in a single game instance
 * This class does not save the current game instance
 * Layout is kept as clean as possible. Core functionality can be found in BingoGamePlay class
 */

public class BingoNumberGenerator extends Activity
{
	//Line 21 has a variable which checks if the game has already started
	boolean didGameStart = false;
	//Line 23 has a variable which checks if the game has ended
	boolean didGameEnd = false;
	//Line 25 displays all the bingo numbers of the current game instance in a string format
	String displayCurrentBingoList = "";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bingo_number_generator);
		
		//Lines 33-38 declares all the widgets in the layout file
		Button generateNumber = (Button)findViewById(R.id.generator_new_number);
		final TextView listOfCalledNumbers = (TextView)findViewById(R.id.generator_bingo_numbers);
		final TextView storedNumbers = (TextView)findViewById(R.id.generator_generated_numbers);
		Button callBingo = (Button)findViewById(R.id.generator_bingo);
		Button exitGame = (Button)findViewById(R.id.generator_main_menu);
		final Button continueButton = (Button)findViewById(R.id.generator_continue);
		
		//Line 41 creates an instance of the BingoGamePlay class
		final BingoGamePlay randomGenerator = new BingoGamePlay(BingoNumberGenerator.this);
		
		//Line 44-66 ensures that a random number will be generated when the Bingo button is clicked. It will also prevent numbers from being generated when the game has ended
		generateNumber.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		//If the game did not end, lines 49-59 generates a new number
        		if(!didGameEnd)
        		{
        			//Line 52 generates a new Bingo number
        			randomGenerator.GenerateRandomNumber();
        			
        			//Line 55 displays the last number called
        			listOfCalledNumbers.setText(randomGenerator.GetLastNumber());
	        		
        			//Line 58 indicates that the game has started
	        		didGameStart = true;
        		}
        		//Lines 61-64 prevents numbers from being generated when the game ends
        		else
        		{
        			Toast.makeText(BingoNumberGenerator.this, "Your Game Has Ended", Toast.LENGTH_LONG).show();
        		}
        	}
        });
		
		//Lines 69-92 ends the game when the Bingo button is pressed
		callBingo.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//Lines 74-85 executes when the game has already started
				if(didGameStart)
				{
					//Line 77 holds all the Bingo numbers of the current game instance
					displayCurrentBingoList = randomGenerator.ReturnNumberList().substring(0, randomGenerator.ReturnNumberList().length() - 2);
					//Line 79 displays the variable on line 77
					storedNumbers.setText(displayCurrentBingoList);
					//Line 81 leads to the Save Page layout
					continueButton.setVisibility(View.VISIBLE);
					//Line 83-84 indicates that the game has ended
					didGameEnd = true;
					Toast.makeText(BingoNumberGenerator.this, "The Game Has Ended", Toast.LENGTH_LONG).show();
				}
				//Line 87-90 executes when the user still clicks the Bingo button before the game has started
				else
				{
					Toast.makeText(BingoNumberGenerator.this, "Your Game Has Not Started Yet", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		//Lines 95-119 takes the user back to the Main Menu only if the game has not yet started, or if the game has ended
		exitGame.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//Lines 100-112 executes if the game has not yet started or if the game has ended
				if(!didGameStart || didGameEnd)
				{
					//Lines 103-107 will execute if the List of the current game instance has Bingo numbers and is not empty
					if(!BingoGamePlay.BingoNumbers.isEmpty())
					{
						//Line 106 notifies the user that the game has not been saved
						Toast.makeText(BingoNumberGenerator.this, "Game Has Not Been Saved", Toast.LENGTH_LONG).show();
					}
					//Line 109 clears the Bingo List, so that the next time a game is started there will be a fresh new list
					BingoGamePlay.BingoNumbers.clear();
					//Line 111 takes the user to the Main Menu
					startActivity(new Intent(BingoNumberGenerator.this, MainActivity.class));
				}
				//Line 114-117 executes when the game is still in session
				else
				{
					Toast.makeText(BingoNumberGenerator.this, "Your Game Is Still In Session", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		//Line 122-128 takes the user to the Save layout
		continueButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				startActivity(new Intent(BingoNumberGenerator.this, BingoSaveGame.class));
			}
		});
	}
}
