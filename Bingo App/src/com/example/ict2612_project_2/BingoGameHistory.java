package com.example.ict2612_project_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/*
 * This class is responsible for displaying all the game instances
 * It has the option of displaying game instances in either ascending or descending order. It is also responsible for saving a game instance out to file
 * Layout is kept as clean as possible. Core functionality can be found in BingoGamePlay class
 */

public class BingoGameHistory extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bingo_game_history);
		
		//Lines 27-30 declares all the widgets in the layout file
		Button mainMenu = (Button)findViewById(R.id.history_main_menu);
		Button updateButton = (Button)findViewById(R.id.history_update);
		final RadioGroup updateOptions = (RadioGroup)findViewById(R.id.history_update_options);
		final TextView bingoNumbers = (TextView)findViewById(R.id.history_game_history);
		
		//Line 33 creates an instance of the class BingoGamePlay. This class contains reusable or really long functions. Code is organized this way to make it more readable
		final BingoGamePlay allGames = new BingoGamePlay(BingoGameHistory.this);
		
		//In line 37 the ReturnGameArray method returns the List of all game instances.
		//In line 37 the ReturnGameHistory method takes two parameters. The first parameter consists of a List containing the game instances. The second parameter sorts the array in either ascending or descending order (not needed in this case)
		bingoNumbers.setText(allGames.ReturnGameHistory(allGames.ReturnGameArray(), ""));
		
		//Lines 40-49 displays the all the game instances, with the option of sorting in either ascending or descending order
		updateButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				//Lines 45-47 checks which radio button is selected. The games list is then sorted (ascending or descending) according to the user input
				int selectedRadioInt = updateOptions.getCheckedRadioButtonId();
				RadioButton selectedRadioButton = (RadioButton)findViewById(selectedRadioInt);
				bingoNumbers.setText(allGames.ReturnGameHistory(allGames.ReturnGameArray(), String.valueOf(selectedRadioButton.getText())));
			}
		});
		
		//Lines 52-58 returns the user back to the main menu
		mainMenu.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				startActivity(new Intent(BingoGameHistory.this, MainActivity.class));
			}
		});
	}
}
