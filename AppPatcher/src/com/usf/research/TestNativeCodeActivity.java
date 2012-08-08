// This code is used to access Native C code
// that patches the application. Is used on
// Patch button click. Example. 

package com.usf.research;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TestNativeCodeActivity extends Activity 
{       
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TextView  tv = new TextView(this);
        int       x  = 3;
        int       y  = 42;

        // here, we dynamically load the library at runtime
        // before calling the native method.
        //
        System.loadLibrary("native");

        int  z = add(x, y);

        tv.setText( "The sum of " + x + " and " + y + " is " + z );
        setContentView(tv);
    }

    public native int add(int  x, int  y);
}