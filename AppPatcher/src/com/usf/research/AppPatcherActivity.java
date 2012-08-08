package com.usf.research;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


public class AppPatcherActivity extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	// ---=== Set Up ===---
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        System.loadLibrary("native");
        
        // ---=== Variables ===---
        String[] 	filesNames 		= null; // Names of the applications available
        int			retCode;				// Method result code
           
        // TEMP CODE
        // Will be replaced in version 1.1
        // Apps names will be obtained through PackageManager rather 
        // than from the folder on SD card
        
        // ---=== Get Data From SD Card ===---
        // Check folders structure and read files names
        try 
        {
        	// If the proper folders structure does not exist
            if (!HasFolderStructure())
            {
            	// Output message that folders structure will be created
            	Toast.makeText(	getApplicationContext(), 
            					"Creating the folders hierarchy", 
            					Toast.LENGTH_LONG).show();
            	
            	// Create a proper folders structure
            	retCode = CreateFolderStructure();
            	// If folders creation failed
            	if (retCode != 0)
            	{
            		/* TODO:
            		 * Handle the error properly rather than just exit
            		 */
            		
            		// Show message
            		Toast.makeText(	getApplicationContext(), 
            					 	"Folders creation failed",
            					 	Toast.LENGTH_LONG).show();
            		
            		// End program
            		return;
            	}
            }
            
            // Get files names through FileManager class
        	filesNames = FileManager.GetFilesNames(
        			new File(	Environment.getExternalStorageDirectory() + "/" + 
        						R.string.app_main_folder + "/" + 
        						R.string.app_app_folder));            
        } 
        // Catch any IO exceptions
        catch (Exception e) 
        {
            Log.e("AppPatcherActivity", e.getMessage());
        }
        
        // TEMP CODE - END
                             
        String[]	 appsNames	= null;	// Array with actual names of available applications        
        
        // Get apps names from files names using StringProcessor
        appsNames = StringProcessor.GetAppsNames(filesNames);
        
        // ---=== Set Data in Spinners ===---        
        
        // Get spinner from the layout
        Spinner spinnerSelectApp = (Spinner) findViewById(R.id.spinner_SelectApp);
        ArrayAdapter<String> adapterApps = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, appsNames);      
        adapterApps.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectApp.setAdapter(adapterApps);
        spinnerSelectApp.setOnItemSelectedListener(new AppsOnItemSelectedListener());
        
        Spinner spinnerSelectVersion = (Spinner) findViewById(R.id.spinner_SelectVesrion);
         ArrayAdapter<CharSequence> adapterVersion = ArrayAdapter.createFromResource(
        		this, R.array.available_versions, android.R.layout.simple_spinner_item);
        adapterVersion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectVersion.setAdapter(adapterVersion);
        spinnerSelectVersion.setOnItemSelectedListener(new VersionOnItemSelectedListner()); 
        
        Button buttonPatch = (Button) findViewById(R.id.buttonPatch);
        buttonPatch.setOnClickListener(new PatchOnClickListener()); 
    }
    
    public native int patch(String argv1, String argv2, String argv3);
    
    /**
     * Test is there a proper folders structure on SD card
     * @return TRUE if there is a proper folders structure;
     * 		   FALSE if not
     */
    private boolean HasFolderStructure()
    {
    	/* TODO
    	 * Get the installed from the PackageManager rather than store them on SD card.
    	 * No reason to check appFolder (app_app_folder) as it will not exist.
    	 */
    	
       	// Define required folders
    	File 	sdCard		= Environment.getExternalStorageDirectory();				// Path to SD card
    	File 	rootFolder 	= new File(sdCard + "/" + R.string.app_main_folder);		// Path to the main app folder
    	File	patchFolder	= new File(rootFolder + "/" + R.string.app_patch_forder);	// Path to the folder for patches	
    	
    	// TEMP CODE
    	// Will be deleted in version 1.1
    	
    	File 	appFolder	= new File(rootFolder + "/" + R.string.app_app_folder);		// Path to the folder with the installed apps
    	
    	// TEMP CODE - END
    	
    	// Test that all the required folders exist and are folders.
    	// Return FALSE if some folder does not exist
    	
    	// Test root folder
    	if (!rootFolder.exists() || !rootFolder.isDirectory())
    	{
    		return (false);
    	}
    	
    	// Test patches folder
    	if (!patchFolder.exists() || !patchFolder.isDirectory())
    	{
    		return (false);
    	}
    	
    	// TEMP CODE
    	
    	if (!appFolder.exists() || !appFolder.isDirectory())
    	{
    		return (false);
    	}
    	
    	// TEMP CODE - END
    	
    	// Return TRUE if all the required folders exist
		return (true);    	
    }
    
    /**
     * Create a proper folder structure for the app on SD card
     * @return 	0 	- if all the missing folders were created;
     * 		 	-1 	- error: there is a file with the same name as the required folder;
     * 			-2  - error: folder creation failed; 
     */
    private int CreateFolderStructure()
    {
    	/* TODO:
    	 * Get the installed apps through PackageManager rather than store them on SD card.
    	 * No reason to check or create appFolder (app_app_folder) as it will not exist.
    	 */
    	
    	// Define all the required folders
    	File	sdCard		=	Environment.getExternalStorageDirectory();				// SD card root folder
    	File	rootFolder	=	new File(sdCard + "/" + R.string.app_main_folder);		// App's main folder 
    	File 	patchFolder	= 	new File(rootFolder + "/" + R.string.app_patch_forder);	// App's patches folder
    	
    	// TEMP CODE
    	// Will be deleted in version 1.1
    	
    	File	appFolder	= 	new File(rootFolder + "/" + R.string.app_app_folder);	// Folder with the installed apps
    	
    	// TEMP CODE - END
    	
    	boolean	retCode;	// Method return code. Help variable.
    	
    	// Attempt to create the main folder
    	// If the folder does not exist yet
    	if (!rootFolder.exists())
    	{
    		// Create folder
    		retCode = rootFolder.mkdir();
    		// If folder creation failed
    		if (!retCode)
    		{
    			return (-2);
    		}
    	}
    	else
    	{
    		// File with the specified name is not a directory
    		if (!rootFolder.isDirectory())
    		{
    			// There exists a file with the same name as the 
    			// main folder. Return an error (-1)
    			return (-1);
    		}
    		// Main folder already exists
    		else
    		{
    			// Everything is OK. Do nothing
    		}
    	}
    	
    	//
    	if (!patchFolder.exists())
    	{
    		// Create folder
    		retCode = patchFolder.mkdir();
    		// If folder creation failed
    		if (!retCode)
    		{
    			// Return an error (-2)
    			return (-2);
    		}
    	}
    	else
    	{
    		// File with the specified name is not a directory
    		if (!patchFolder.isDirectory())
    		{
    			// There exists another file with the same name
    			// as the main folder. Return an error (-1)
    			return (-1);
    		}
    		else
    		{
    			// Everything is OK. Do nothing
    		}
    	}
    	
    	// TEMP CODE
    	// Will be deleted in version 1.1
    	
    	// If the appFolder does not exist 
    	if (!appFolder.exists())
    	{
    		// Create folder. Store return code
    		retCode = appFolder.mkdir();
    		// If folder creation failed
    		if (!retCode)
    		{
    			// Return an error (-2)
    			return (-2);
    		}
    	}
    	// If such file exists
    	else
    	{
    		// If the existing file is not a folder
    		if (!appFolder.isDirectory())
    		{
    			// There is a file with the same name as the
    			// installed apps folder being created.
    			// Return an error (-1);
    			return (-1);
    		}
    		else
    		{
    			// Everything is OK. Do nothing.
    		}
    	}
    	
    	// TEMP CODE - END
    		
    	// All the required folders have been successfully created
    	return (0);
    }
    
    public class PatchOnClickListener implements OnClickListener
    {
		public void onClick(View v) 
		{
			long start;
			long end;

			start = android.os.SystemClock.uptimeMillis();
	
			patch("/sdcard/AppPatcher/Installed/Shazam_v3.7.2.apk",
				  "/sdcard/AppPatcher/New/Shazam_v3.7.3.apk",
				  "/sdcard/AppPatcher/Patch/Shazam_v3.7.3.diffpatch");
			
			end = android.os.SystemClock.uptimeMillis();

			Toast.makeText(getApplicationContext(), 
					String.valueOf(end - start), Toast.LENGTH_LONG).show();
		}    
    }
    
    public class AppsOnItemSelectedListener implements OnItemSelectedListener 
    {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
        {
        	// If some application is chosen
        	if (!parent.getItemAtPosition(pos).toString().equals("None"))
        	{
        		// Print notification
	        	Toast.makeText(parent.getContext(), "Application: " + 
	        			parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	        	
	        	// Fill spinner_SelectVersion with available patches
	        	Object item = parent.getItemAtPosition(pos);
	            String[] patchesNames = null;	// Names of the patches available
	            patchesNames = FileManager.GetFilesNames
	            		(new File(Environment.getExternalStorageDirectory() + "/AppPatcher/Patch"));
	            ArrayList<String> availableVersions = StringProcessor.GetAppVersions
	            		(patchesNames, item.toString()); 
	            
	            Spinner spinnerSelectVersion = (Spinner) findViewById(R.id.spinner_SelectVesrion);
	            ArrayAdapter<String> adapterVersion = new ArrayAdapter<String>
	            	(view.getContext(), android.R.layout.simple_spinner_item, availableVersions);
	           adapterVersion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	           spinnerSelectVersion.setAdapter(adapterVersion);
	           //spinnerSelectVersion.setOnItemSelectedListener(new VersionOnItemSelectedListner());
        	}
        	else
        	{
        		Spinner spinnerSelectVersion = (Spinner) findViewById(R.id.spinner_SelectVesrion);
                ArrayAdapter<CharSequence> adapterVersion = ArrayAdapter.createFromResource(
                		view.getContext(), R.array.available_versions, android.R.layout.simple_spinner_item);
                adapterVersion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSelectVersion.setAdapter(adapterVersion);
        	}
        }

        public void onNothingSelected(AdapterView<?> parent) 
        {
          // Do nothing.
        }
    }
    
    public class VersionOnItemSelectedListner implements OnItemSelectedListener
    {

		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
		{
			if (!parent.getItemAtPosition(pos).toString().equals("None"))
			{
				Toast.makeText(parent.getContext(), "Version: " + 
						parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) 
		{
			// Do nothing			
		}
    	
    }
}