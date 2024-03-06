package com.vam.whitecoats.tools;

import android.app.Activity;
import android.os.Build;
import android.util.Log;

public class ExceptionHandler  {
	private final Activity myContext;
	private final String LINE_SEPARATOR = "\n";

	public ExceptionHandler(Activity context) {
		myContext = context;
	}
	
	@SuppressWarnings("deprecation")
	public void uncaughtException(String exception) {
		//StringWriter stackTrace = new StringWriter();
		//exception.printStackTrace(new PrintWriter(stackTrace));
		StringBuilder errorReport = new StringBuilder();
		errorReport.append("************ CAUSE OF ERROR ************\n\n");
		//errorReport.append("Class Name: "+name.toString()+LINE_SEPARATOR);
	//	errorReport.append("Line: "+lineNumber+LINE_SEPARATOR);
		errorReport.append(exception.toString());

		errorReport.append("\n************ DEVICE INFORMATION ***********\n");
		errorReport.append("Brand: ");
		errorReport.append(Build.BRAND);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Device: ");
		errorReport.append(Build.DEVICE);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Model: ");
		errorReport.append(Build.MODEL);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Id: ");
		errorReport.append(Build.ID);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Product: ");
		errorReport.append(Build.PRODUCT);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("\n************ FIRMWARE ************\n");
		errorReport.append("SDK: ");
		errorReport.append(Build.VERSION.SDK);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Release: ");
		errorReport.append(Build.VERSION.RELEASE);
		errorReport.append(LINE_SEPARATOR);
		errorReport.append("Incremental: ");
		errorReport.append(Build.VERSION.INCREMENTAL);
		errorReport.append(LINE_SEPARATOR);

	/*	Intent intent = new Intent(myContext, AnotherActivity.class);
		intent.putExtra("error", errorReport.toString());
		myContext.startActivity(intent);*/
		Log.d("ExceptionHandler===", errorReport.toString());

		//android.os.Process.killProcess(android.os.Process.myPid());
		//System.exit(10);
	}
 

	 
}
