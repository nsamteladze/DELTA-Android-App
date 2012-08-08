package com.usf.research;

import java.util.ArrayList;

/**
 * This class manages all strings processing.
 * This generally includes retrieving of data from strings
 * in different formats.
 * @author Nikolai Samteladze
 *
 */
public class StringProcessor 
{
	/**
	 * Gets applications names from files names.
	 * @param rawFilesNames Files names to retrieve information from
	 * @return String array of applications' names
	 */
	public static String[] GetAppsNames(String[] rawFilesNames)
	{
		// ---=== Data ===---
		
		// Array with applications names
		String[] 	appsNames 		= new String[rawFilesNames.length];
		// Help string array to split file name
		String[] 	tempSplitString = null;	
		
		for (int i = 0; i < rawFilesNames.length; i++) 
		{
			// Split the file name in parts
			tempSplitString = rawFilesNames[i].split("_");
			
			appsNames[i] = "";
			// Last part is apps version. Everything else - name
			for (int j = 0; j < (tempSplitString.length - 1); j++)
			{
				if (j > 0)
				{
					appsNames[i] += " ";
				}
				appsNames[i] += tempSplitString[j];				
			}
		}
		
		return (appsNames);	
	}
	
	public static ArrayList<String> GetAppVersions(String[] patchesNames, String appName)
	{
		// ---=== Data ===---
		ArrayList<String>	availableVesions; 			// Versions that we can patch to
		String[] 			tempSplitString 	= null;	// Help array to split strings		
		
		availableVesions = new ArrayList<String>();
		availableVesions.add("None");
		
		// ---=== Filter Patches for the Applications ===---
		
		// 
		for (int i = 0; i < patchesNames.length; i++)
		{
			tempSplitString = patchesNames[i].split("_");
			
			String tempAppName = "";
			// Last part is apps version. Everything else - name
			for (int j = 0; j < (tempSplitString.length - 1); j++)
			{
				if (j > 0)
				{
					tempAppName += " ";
				}
				tempAppName += tempSplitString[j];				
			}
			
			if (tempAppName.equals(appName))
			{
				String rawVersion = tempSplitString[tempSplitString.length - 1];
				tempSplitString = rawVersion.split(".diffpatch");											
				availableVesions.add(tempSplitString[0]);
			}
		}		
		
		return (availableVesions);	
	}
}
