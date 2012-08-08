package com.usf.research;

import java.io.File;
import android.util.Log;

/* DESCRIPTION:
 * File manager does all the manipulations 
 * with files. Every other object deals with
 * the files though the FileManager's public 
 * methods. 
 */
public class FileManager
{
	/** 
	 * Get files names from the specified folder	
	 * @param folderPath Folder to get files names from
	 * @return String array with files names
	 */
	public static String[] GetFilesNames(File folder)
	{
		// ---=== Data ===---
		
		String[] 	filesNames 	= null;	// Array with files names
		int			numFiles	= 0;	// Number of files in the folder
		
		// ---=== Get Files Names ===---
		
		// Catch unexpected exceptions
		try 
		{
			// Count number of files (not folders) in the folder
        	for (File file : folder.listFiles()) 
        	{
        	    if (file.isFile())
        	    {
        	    	numFiles++;
        	    }
        	}
        	
        	// Allocate memory for the array
        	filesNames = new String[numFiles + 1];
        	
        	// Fill the array with files names
        	filesNames[0] = "None_v0.0";
        	int i = 1;
        	for (File file : folder.listFiles()) 
        	{
        	    if (file.isFile())
        	    {
        	    	// Put the file's name in the array
        	        filesNames[i] = file.getName();
        	        i++;
        	    }
        	}
		} 
		// Catch any exceptions and print them to log
		catch (Exception e) 
		{			
			Log.e("AppPatcherActivity", e.getMessage());
		}
		
		// Return array with files names
		return (filesNames);		
	}
}
