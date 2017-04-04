package Framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
///singleton
	
	private static DataBaseManager instance = null;
	private Connection connection=null;
	private Statement statement = null;
	
	public static synchronized DataBaseManager get(){
		if(instance == null)
			instance = new DataBaseManager();
		return instance;
	}
	
	private DataBaseManager(){
		
		try {
			Class.forName("org.sqlite.JDBC");
			String dbLocation = SettingsManager.getSetting(SettingsNames.DATABASE_LOCATION_VAR_NAME);
			String dbName = SettingsManager.getSetting(SettingsNames.DATABASE_NAME_VAR_NAME);
			connection = DriverManager.getConnection( "jdbc:sqlite:" + dbLocation + "/" + dbName );
	    	} catch ( Exception e ) {
	    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    		System.exit(0);
	    	}
			try {
				statement = this.getConnection().createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	System.out.println("Opened database successfully");
		}
	public Connection getConnection(){
		return this.connection;
	}
	
	public Statement getStatement() {
		synchronized (statement) {
			return statement;
		}
	}
	

}
