package com.navneet.kotlinlistview.config

import android.content.Context
import android.os.Environment
import android.util.Log

import java.util.logging.FileHandler

public object Logger {
    var logger: FileHandler? = null
    var context: Context? = null
    private val TAG = "Logger"
    private val filename = "errorlog"
    var isExternalStorageAvailable = true
    var isExternalStorageWriteable = true
    var state = Environment.getExternalStorageState()

   public fun debugE(className: String, message: String) {

        Log.e(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", "$className : $message")
        addRecordToLog(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + className + " : " + message)


    }

    public fun debugE(message: String) {
        Log.e(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", message)
        addRecordToLog(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + " : " + message)
    }

    public  fun debugE(message: String, execption: Boolean) {
        val fullClassName = Thread.currentThread().stackTrace[2].className
        val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
        val methodName = Thread.currentThread().stackTrace[2].methodName
        val lineNumber = Thread.currentThread().stackTrace[2].lineNumber
        val errormessage = "$className.$methodName():-->$lineNumber--->$message"
        Log.e(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", errormessage)
        addRecordToLog(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + errormessage)
    }

    public fun debugV(message: String) {
        Log.v(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", message)
        addRecordToLog(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + message)
    }

    public fun debugV(className: String, message: String) {
        Log.v(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  ", "$className:$message")
        addRecordToLog(MFAdvisoryConfig.APP_VERSION + " " + System.currentTimeMillis() + " :  " + className + ":" + message)
    }

    public  fun addRecordToLog(message: String) {

        /*  if(true)
        {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                isExternalStorageAvailable = isExternalStorageWriteable = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // We can only read the media
                isExternalStorageAvailable = true;
                isExternalStorageWriteable = false;
            } else {
                // Something else is wrong. It may be one of many other states, but all we need
                //  to know is we can neither read nor write
                isExternalStorageAvailable = isExternalStorageWriteable = false;
            }

            File dir = new File("/sdcard/Files/Project_Name");
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                if (!dir.exists()) {
                    Log.d("Dir created ", "Dir created ");
                    dir.mkdirs();
                }

                File logFile = new File("/sdcard/Files/Project_Name/" + filename + ".txt");

                if (!logFile.exists()) {
                    try {
                        Log.d("File created ", "File created ");
                        logFile.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Logger.debugE(e.toString());
                    }
                }
                try {
                    //BufferedWriter for performance, true to set append to file flag
                    BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));

                    buf.write(message + "\r\n");
                    //buf.append(message);
                    buf.newLine();
                    buf.flush();
                    buf.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Logger.debugE(e.toString());
                }
            }
            else
            {

            }
        }*/


    }
}