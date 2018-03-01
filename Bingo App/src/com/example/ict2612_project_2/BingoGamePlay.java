package com.example.ict2612_project_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class BingoGamePlay extends Activity
{
	/*
	 * This class contains functions/methods which consists of a lot of code or can be reused. This makes code layout more readable
	 */
	
	//Line 26 contains the list of randomly generated Bingo numbers
	public static List<Integer> BingoNumbers = new ArrayList<Integer>();
	private Context context;
	
	//Line 30 specifies the name of the file where the Bingo numbers will be saved
    private static final String FILENAME = "mystuff.txt";
    
    //Constructor
	public BingoGamePlay(Context currentContext) 
	{
		super();
		this.context = currentContext;
	}
	
	//Lines 40-55 contains functionality which randomly generates Bingo numbers. The method ensures that same number cannot be generated twice in one game instance. The generated number is stored in a list
	public void GenerateRandomNumber()
	{
		Random generator = new Random();
		int randomNumber;
		
		randomNumber = generator.nextInt(99);
		
		if(!BingoNumbers.contains(randomNumber))
		{
			BingoNumbers.add(randomNumber);
		}
		else
		{
			Toast.makeText(context, "Number Already Exists. Please Have Another Go", Toast.LENGTH_LONG).show();
		}
	}
	
	//Lines 58-68 contains functionality which formats the generated Bingo numbers in a string format and returns it
	public String ReturnNumberList()
	{
		String showNumbers = "";
		
		for(int i = 0; i < BingoNumbers.size(); i++)
		{
			showNumbers += String.valueOf(BingoNumbers.get(i)) +  ", ";
		}
		
		return showNumbers;
	}
	
	//Lines 71-81 contains functionality which returns the last number generated
	public String GetLastNumber()
	{
		if(!BingoNumbers.isEmpty())
		{
			return String.valueOf(BingoNumbers.get(BingoNumbers.size() - 1));
		}
		else
		{
			return "";
		}
	}

	//Lines 84-128 contains functionality which writes data to the text file. These values are then saved to the text file
    public static void WriteToFile(String data, Context ctx) throws FileNotFoundException
    {
    	OutputStreamWriter osw = new OutputStreamWriter(ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE));
    	BufferedWriter bw = new BufferedWriter(osw);
    	
    	try
    	{
			bw.write(data);
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			Toast.makeText(ctx, "Could Not Write To File", Toast.LENGTH_LONG).show();
		}
    	
    	try
    	{
			bw.newLine();
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			Toast.makeText(ctx, "Could Not Write To File", Toast.LENGTH_LONG).show();
		}
    	
    	try
    	{
			bw.close();
		}
    	catch (IOException e)
		{
			e.printStackTrace();
			Toast.makeText(ctx, "Could Not Write To File", Toast.LENGTH_LONG).show();
		}
    	
    	try
    	{
			osw.close();
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			Toast.makeText(ctx, "Could Not Write To File", Toast.LENGTH_LONG).show();
		}
    }

    //Lines 131-170 contains functionality which returns all stored data in the text file
    public static String ReadFromFile(Context ctx) throws FileNotFoundException
    {
    	String ret = "";

    	FileInputStream is = ctx.openFileInput(FILENAME);
    	
    	InputStreamReader isr = new InputStreamReader(is);
    	BufferedReader br = new BufferedReader(isr);
    	
    	String inString = "";
    	
    	StringBuilder sb = new StringBuilder();
    	
    	try
    	{
			while((inString = br.readLine()) != null)
			{
				sb.append(inString);
			}
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
			Toast.makeText(ctx, "File Could Not Be Found", Toast.LENGTH_LONG).show();
		}
    	
    	try
    	{
			is.close();
		}
    	catch (IOException e)
		{
			e.printStackTrace();
			Toast.makeText(ctx, "File Could Not Be Found", Toast.LENGTH_LONG).show();
		}
    	
    	ret = sb.toString();
    	
		return ret;
    }
    
    //Lines 173-219 contains functionality which searches for the Bingo numbers only and stores it in a List, ignoring any other characters in the text file. Each set of values is then stored in the same List
    public ArrayList<ArrayList<Integer>> ReturnGameArray()
    {
    	String inText1 = "";
		List<String> inText2 = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> sortAlgParent = new ArrayList<ArrayList<Integer>>();
		int hashOccurances = 0;
		int commaOccurances = 0;
		int startChar = 0;
		
		try
        {
			inText1 = ReadFromFile(context);
		}
        catch (FileNotFoundException e)
		{
			e.printStackTrace();
			Toast.makeText(context, "File Could Not Be Found", Toast.LENGTH_LONG).show();
		}
		
		hashOccurances = inText1.length() - inText1.replace("#", "").length();
		
        for(int h = 0; h < hashOccurances; h++)
        {
        	inText2.add(inText1.substring(startChar, inText1.indexOf("#")));
        	inText1 = inText1.replace((inText2.get(h) + "#"), "");
        }
        
        startChar = 0;
        
        for(int j = 0; j < inText2.size(); j++)
        {
    		ArrayList<Integer> sortAlgChild = new ArrayList<Integer>();
            commaOccurances = inText2.get(j).length() - inText2.get(j).replace(",", "").length();
    		
	        for(int i = 0; i < commaOccurances; i++)
	        {
	        	sortAlgChild.add(Integer.valueOf(inText2.get(j).substring(startChar, inText2.get(j).indexOf(",", startChar))));
	        	startChar = inText2.get(j).indexOf(",", startChar) + 1;
	        }
	        
	        startChar = 0;
	        
	        sortAlgParent.add(sortAlgChild);
        }
        
        return sortAlgParent;
    }
    
    //Lines 222-258 contains functionality which converts List from ReturnGameArray to a string which is ready for display. Determines whether or not to return it in descending or ascending order (depending on user input)
    public String ReturnGameHistory(ArrayList<ArrayList<Integer>> sortAlgParent, String order)
    {
    	String games = "";
    	
        for(int i = 0; i < sortAlgParent.size(); i++)
        {
        	if(order.equals("Ascending"))
        	{
        		Collections.sort(sortAlgParent.get(i));
        	}
        	else if(order.equals("Descending"))
        	{
        		Collections.sort(sortAlgParent.get(i), Collections.reverseOrder());
        	}
        	
        	for(int j = 0; j < sortAlgParent.get(i).size(); j++)
        	{
        		if(j == 0)
        		{
        			games += "Game Play " + (i+1) + ": ";
        		}
        		
        		if(j != (sortAlgParent.get(i).size()-1))
        		{
        			games += String.valueOf(sortAlgParent.get(i).get(j)) + ", ";
        		}
        		else
        		{
        			games += String.valueOf(sortAlgParent.get(i).get(j));
        		}
        	}
        	
        	games += '\n';
        }
        
        return games;
    }
    
    
    //Lines 262-314 contains functionality which deletes and returns the new game instance(s). This method works in conjunction with ReturnGameArray method to get the Array List
    public String ClearReturnGameHistory(ArrayList<ArrayList<Integer>> sortAlgParent, int gamePosition)
    {
    	String games = "";
    	String textToSave = "";
    	
    	if(gamePosition <= sortAlgParent.size() && gamePosition > 0)
    	{
    		sortAlgParent.remove(gamePosition-1);
    		
	        for(int i = 0; i < sortAlgParent.size(); i++)
	        {
	        	for(int j = 0; j < sortAlgParent.get(i).size(); j++)
	        	{
	        		textToSave += String.valueOf(sortAlgParent.get(i).get(j)) + ",";
	        		
	        		if(j == 0)
	        		{
	        			games += "Game Play " + (i+1) + ": ";
	        		}
	        		
	        		if(j != (sortAlgParent.get(i).size()-1))
	        		{
	        			games += String.valueOf(sortAlgParent.get(i).get(j)) + ", ";
	        		}
	        		else
	        		{
	        			games += String.valueOf(sortAlgParent.get(i).get(j));
	        		}
	        	}
	        	
	        	textToSave += "#";
	        	
	        	games += '\n';
	        }
        	
        	try
        	{
				WriteToFile(textToSave, context);
			}
        	catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
        	
			Toast.makeText(context, "Game Play " + gamePosition + " Has Been Deleted", Toast.LENGTH_LONG).show();
	    }
    	else
    	{
			Toast.makeText(context, "Invalid Entry", Toast.LENGTH_LONG).show();
    	}
        
        return games;
    }
}
