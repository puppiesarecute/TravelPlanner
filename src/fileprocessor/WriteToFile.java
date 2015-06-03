package fileprocessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class WriteToFile
{
    private final static String USER_PASS_PATH = "userpass.txt";
    private final static String LOG_PATH = "log.txt";

    public static void log(String toBeLogged)
    {
	try
	{
	    LocalDateTime timePoint = LocalDateTime.now();
	    FileWriter writeToLog = new FileWriter(LOG_PATH, true);
	    writeToLog.write("Log time: " + timePoint + " -- " + toBeLogged + "\n");
	    writeToLog.close();

	}
	catch (IOException e)
	{
	    System.out.println("File: " + LOG_PATH + " Couldn't be found");
	    e.printStackTrace();
	}

    }
}
