package com.example.ict2612_project_2;

import java.io.FileNotFoundException;
import java.util.Collections;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This class saves the current game instance. The user also has the option of saving in ascending order
 * Layout is kept as clean as possible. Core functionality can be found in BingoGamePlay class
 */

public class BingoSaveGame extends Activity
{
	//The variable line 23 holds all the bingo games from the text file in an unformatted form
    String inText = "";
    //Line 25 indicates whether the bingo game has already been saved
    boolean alreadySaved = false;
    
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bingo_save_current);
		
		//Lines 33-36 declares all the widgets in the layout file
		final TextView bingoNumbers = (TextView)findViewById(R.id.save_current_game_list);
		Button saveButton = (Button)findViewById(R.id.save_save_to_file);
		Button mainMenu = (Button)findViewById(R.id.save_menu);
		final CheckBox saveInAscOrder = (CheckBox)findViewById(R.id.save_ascending_order_checkbox);
		
		//Line 39-48 reads the data from the text file and stores it in the inText variable. If there is any error it will be handled
		try
        {
			//Line 42 reads the data from the text file and stores it in the inText variable
			inText = BingoGamePlay.ReadFromFile(BingoSaveGame.this);
		}
        catch (FileNotFoundException e)
		{
			e.printStackTrace();
			Toast.makeText(BingoSaveGame.this, "File Could Not Be Found", Toast.LENGTH_LONG).show();
		}
		
		//Lines 51-99 saves the bingo game by writing it out to a text file
		saveButton.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		//Line 56 stores the current Bingo game with the old bingo games. This prevents data from being overwritten
        		String textToSave = "";
        		
        		//Lines 59-63 executes if the user has the Ascending check box checked
        		if(saveInAscOrder.isChecked())
        		{
        			//Line 62 orders the current bingo game from largest to smallest
        			Collections.sort(BingoGamePlay.BingoNumbers);
        		}
        		
        		//Lines 66-69 converts all the Bingo numbers of the current game to a single string. Each Bingo value is separated by a comma
        		for(int i = 0; i < BingoGamePlay.BingoNumbers.size(); i++)
        		{
        			textToSave += String.valueOf(BingoGamePlay.BingoNumbers.get(i)) + ",";
        		}
        		
        		//Lines 72-89 executes only if the game has not yet been saved. Each Bingo game is written out to file and separated by a # character
        		if(!alreadySaved)
        		{
        			//The statement on lines 75 and 84 handles any errors while saving the file
	        		try
	        		{
	        			//Line 78 saves each Bingo game out to file. Each bingo game is separated by a comma
						BingoGamePlay.WriteToFile(inText + textToSave + "#", BingoSaveGame.this);
						//Line 80 prevents the same game from being saved again
						alreadySaved = true;
						//Line 82 confirms that the game has been saved
	        			Toast.makeText(BingoSaveGame.this, "Game Has Been Saved", Toast.LENGTH_LONG).show();
					}
	        		catch (FileNotFoundException e)
	        		{
						e.printStackTrace();
						Toast.makeText(BingoSaveGame.this, "File Could Not Be Written To", Toast.LENGTH_LONG).show();
					}
        		}
        		//Lines 91-94 executes if the game has already been saved
        		else
        		{
        			Toast.makeText(BingoSaveGame.this, "Game Has Already Been Saved", Toast.LENGTH_LONG).show();
        		}
        		
        		//Line 97 shows all the values of the newly saved Bingo game
        		bingoNumbers.setText(textToSave.substring(0, textToSave.length() - 1));
        	}
        });
		
		//Line 102-117 takes the user back to the Main Menu layout
		mainMenu.setOnClickListener(new OnClickListener()
        {
			public void onClick(View v)
			{
				//Lines 107-111 executes if the user goes back to the Main Menu without saving
				if(!alreadySaved && !BingoGamePlay.BingoNumbers.isEmpty())
				{
					//Line 110 contains a message notifying the user that he/she did not save the game
					Toast.makeText(BingoSaveGame.this, "Game Has Not Been Saved", Toast.LENGTH_LONG).show();
				}
				//Line 113 clears the Bingo list so that the user has a fresh list to write to for the next game instance. This prevents old data from being written to a new game
				BingoGamePlay.BingoNumbers.clear();
				//Line 115 takes the user back to the Main Menu
				startActivity(new Intent(BingoSaveGame.this, MainActivity.class));
			}
        });
	}
}
