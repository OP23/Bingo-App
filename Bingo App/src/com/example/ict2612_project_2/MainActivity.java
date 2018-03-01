package com.example.ict2612_project_2;

import android.os.Bundle;

/*
 * This class is the Main Menu. Users can navigate around from this class
 * Layout is kept as clean as possible. Core functionality can be found in BingoGamePlay class
 */

import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            
            //Line 26-30 contains values which is displayed on the Main Menu screen. These values represent the different screens which the user will be taken to
            String[] menuOptions = new String[3];
            
            menuOptions[0] = "Play Bingo";
            menuOptions[1] = "View Game History";
            menuOptions[2] = "Clear History";

            //Line 33 displays a simple list layout along with the values from lines 20-24
            setListAdapter(new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, menuOptions));
    }
    
    //Lines 37-60 takes the user to different screens depending on what the user clicks on
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	switch(position)
        {
        	case 0:
        	{
        		//Line 44 takes the user to the screen which generates the random Bingo numbers
        		startActivity(new Intent(MainActivity.this, BingoNumberGenerator.class));
        		break;
        	}
        	case 1:
        	{
        		//Line 50 takes the user to the screen which shows all the game instances
        		startActivity(new Intent(MainActivity.this, BingoGameHistory.class));
        		break;
        	}
        	default:
        	{
        		//Line 56 takes the user to the screen which deletes a game instance
        		startActivity(new Intent(MainActivity.this, BingoClearHistory.class));
        		break;
        	}
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
