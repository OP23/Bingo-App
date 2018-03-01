package com.example.ict2612_project_2;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This class is responsible for deleting either specific game instances, or all of the game instances.
 * When a single game instance has been deleted, it will show a new updated list
 * Layout is kept as clean as possible. Core functionality can be found in BingoGamePlay class
 */

public class BingoClearHistory extends Activity
{
	String inText = "";
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bingo_clear_history);
		
		//Lines 34-39 declares all the widgets in the layout file
		Button clearButton = (Button)findViewById(R.id.clear_clear_button);
		Button mainMenu = (Button)findViewById(R.id.clear_mainMenu);
		final Spinner clearBingoGames = (Spinner)findViewById(R.id.clear_clear_options);
		final TextView allGamePlays = (TextView)findViewById(R.id.clear_updated_list);
		final TextView clearGamePlaysHeading = (TextView)findViewById(R.id.clear_remove_gameplay_heading);
		final EditText listItem = (EditText)findViewById(R.id.clear_list_item);
		
		//Lines 42-44 declares a list which stores values based on whether the user wants to delete a specific game or all the bingo games. These values are displayed in the Spinner
		final String[] clearOptions = new String[2];
		clearOptions[0] = "Clear All Entries";
		clearOptions[1] = "Clear A Specific Entry";
		
		//Line 47 creates an instance of the class BingoGamePlay. This class contains reusable or really long functions. Code is organized this way to make it more readable
		final BingoGamePlay gamePlayAccess = new BingoGamePlay(this);
		
		//Lines 50-52 sets the type of layout for the Spinner. It uses the clearOptions array to display the list of options. Games get deleted based on various Spinner values
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clearOptions);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		clearBingoGames.setAdapter(dataAdapter);
		
		//Line 57 displays all the games
		//In Line 57 each element of the ReturnGameArray() method contains a game instance (An ArrayList within an ArrayList).
		//In Line 57 the method ReturnGameHistory converts all the games played to a String. The first parameter accepts the List of all the games played. The second parameter determines whether the returned values need to be in ascending or descending order (not needed in this case) 
    	allGamePlays.setText(gamePlayAccess.ReturnGameHistory(gamePlayAccess.ReturnGameArray(), ""));
		
    	//Lines 60-85 Checks which item has been SELECTED from the Spinner
		clearBingoGames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> av, View v, int i, long l)
			{
				//Line 65 returns the value of the Spinner selected
				String selectedItem = clearBingoGames.getSelectedItem().toString();
				
				//Lines 68-77 displays an EditText which will be visible ONLY if the user chooses to delete a single SPECIFIC game instance
				if(selectedItem.equals(clearOptions[1]))
				{
					listItem.setVisibility(View.VISIBLE);
					clearGamePlaysHeading.setVisibility(View.VISIBLE);
				}
				else
				{
					listItem.setVisibility(View.INVISIBLE);
					clearGamePlaysHeading.setVisibility(View.INVISIBLE);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> av)
			{
				
			}
		});
		
		//Lines 88-133 clears the game instance(s) based on user input from the Spinner widget
		clearButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//Line 93 gets the values of the currently selected Spinner item
				String selectedItem = clearBingoGames.getSelectedItem().toString();
				
				//96-111 executes only if 'Clear All Entries' option is selected (See line 41 and 49). Basically, this is deleting every game instance
				if(selectedItem.equals(clearOptions[0]))
				{
	        		try
	        		{
	        			//Lines 101-103 deletes everything that is saved to file and confirms it with a pop up.
						BingoGamePlay.WriteToFile("", BingoClearHistory.this);
						allGamePlays.setText(gamePlayAccess.ReturnGameHistory(gamePlayAccess.ReturnGameArray(), ""));
						Toast.makeText(BingoClearHistory.this, "Contents Have Been Deleted", Toast.LENGTH_LONG).show();
					}
	        		catch (FileNotFoundException e)
	        		{
						e.printStackTrace();
	        			//Displays an error message if the contents of the file could not be deleted
						Toast.makeText(BingoClearHistory.this, "File Could Not Be Written To", Toast.LENGTH_LONG).show();
					}
				}
				//Lines 113-132 deletes SPECIFIC instances of the game only
				else
				{
					//Line 117 deletes a specific game instance
					//In line 117 the first parameter of the ClearReturnGameHistory contains the List of all the current games. The second parameter specifies which game instance to delete
					gamePlayAccess.ClearReturnGameHistory(gamePlayAccess.ReturnGameArray(), Integer.valueOf(listItem.getText().toString()));
					
					try
			        {
						//Line 122 gets the new List after deleting the specific instance
						inText = BingoGamePlay.ReadFromFile(BingoClearHistory.this);
						//Line 125 displays the new List
						//In line 125, the first parameter of ReturnGameHistory() method accepts the array of all the game instances. The second parameter accepts the order, either ascending or descending , which it needs to be sorted (not required in this case)
			    		allGamePlays.setText(gamePlayAccess.ReturnGameHistory(gamePlayAccess.ReturnGameArray(), ""));
					}
			        catch (FileNotFoundException e)
					{
						e.printStackTrace();
						Toast.makeText(BingoClearHistory.this, "File Could Not Be Found", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		
		//Lines 137-143 sets up a button to return back to the main menu
		mainMenu.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				startActivity(new Intent(BingoClearHistory.this, MainActivity.class));
			}
		});
	}
}
