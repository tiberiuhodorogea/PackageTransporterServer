package Framework.Log;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import Framework.SettingsNames;

import Framework.SettingsManager;

//singleton
public class TextLogger implements ILogger{
	//settings
	private static final String LOG_LINES_DATE_SEPARATOR = "/";
	private static final String FILE_NAME_DATE_SEPARATOR = ".";
	//end settings
	
	
	private static TextLogger instance = null;
	private WriteFile writeFile;
	private ConcurrentLinkedQueue<String> logMessagesQueue;
	private static String today = null;
	
	public static TextLogger getInstance(){
		
		if( null == instance ){
			String logsPath = SettingsManager.getSetting(SettingsNames.LOGS_PATH_VAR_NAME);
			String logFileName = getLogFileNameForToday();
			instance = new TextLogger(new WriteFile(logsPath,logFileName, true));		
		}
		if ( false == today.equals(getFormattedDate(FILE_NAME_DATE_SEPARATOR) )){
			instance.writeFile.setFileName(getLogFileNameForToday());
		}
		
		return instance;
	}
	private TextLogger(WriteFile writeFile){
		if( null == writeFile ){
			throw new UnsupportedOperationException("wirteFile parameter passed as null in TextLogger ctor...");
		}
		
		today = getFormattedDate(FILE_NAME_DATE_SEPARATOR);
		this.writeFile = writeFile;
		this.logMessagesQueue = new ConcurrentLinkedQueue<String>();
		new Thread(new AsyncLogWriter(writeFile, logMessagesQueue)).start();

	}
	
	
	@Override
	public void log(LogLevel level, String message) {
		String logLine = "| " + level;
		if( LogLevel.INFO == level )
			logLine   += " ";
		logLine		  += " | ";
		logLine 	  += getFormattedDate(LOG_LINES_DATE_SEPARATOR) + " ";
		logLine       += getFormattedTime() + " | ";
		logLine       += message + " |";
		
		logMessagesQueue.add(logLine);
	}
	
	private static String getFormattedDate(String separator){
		String ret = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy"+ separator + "MM" + separator +"dd");
		Date date = new Date();
		ret = dateFormat.format(date);
		return ret;
	}
	
	private static String getFormattedTime(){
		String ret = null;
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		ret = dateFormat.format(date);
		return ret;
	}
	
	
	private static String getLogFileNameForToday(){
		String ret = null;
		String logsFileNameSufix = SettingsManager.getSetting(SettingsNames.LOGS_FILE_SUFIX_VAR_NAME);
		ret  = getFormattedDate(FILE_NAME_DATE_SEPARATOR);
		ret += logsFileNameSufix;
		return ret;
	}
}
