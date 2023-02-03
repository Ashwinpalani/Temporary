//javac -classpath D:\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;D:\CustomReporting\postgresql-42.2.18.jar;. QuarterHoursDataDump.java
//java -classpath D:\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;D:\CustomReporting\postgresql-42.2.18.jar;. QuarterHoursDataDump

import java.sql.*;  
import java.util.*;
import java.text.*;
import java.io.*;

public class QuarterHoursDataDump{  

	static String START_TIME = "2021-01-01 00:00:00.000";
	static String END_TIME = "2021-03-13 07:30:00.000";
	
		public static void cleanupTables(Connection con, String tableName) throws Exception{

		Statement stmt=con.createStatement();
		System.out.println("Cleanup....");
		if(tableName != null && tableName != "")
		{
			stmt.executeUpdate("DELETE FROM " + tableName);
		}else{
			//stmt.executeUpdate("DELETE FROM DIM_ROUTING_SERVICES");
			//stmt.executeUpdate("DELETE FROM DIM_AGENTS");
		}
		
		System.out.println("Cleanup completed....");

	}

	public static void getReportIntervals(Connection con, String report_name) throws Exception{
		Statement stmt = con.createStatement();
		String sql = "SELECT CONVERT(VARCHAR(50), CONVERT(DATETIME, PARAM_VALUE, 121), 121) AS START_TIME, CONVERT(VARCHAR(50), (DATEADD(MINUTE, CAST((SELECT PARAM_VALUE FROM CONFIG_PARAMS WHERE PARAM_NAME = 'INTERVAL_IN_MINUTES') AS INT), CONVERT(DATETIME, PARAM_VALUE, 121))), 121) AS END_TIME FROM CONFIG_PARAMS WHERE PARAM_NAME = 'DATA_DUMP_START_TIME'";		
		
		System.out.println("getReportIntervals: " + sql);
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			START_TIME = rs.getString(1);
			END_TIME = rs.getString(2);
		}
		
		System.out.println("START_TIME:" + START_TIME + ", END_TIME:" + END_TIME);
	}
	
	public static void setReportIntervals(Connection con, String report_name) throws Exception{
		Statement stmt = con.createStatement();
		
		String sql = "UPDATE CONFIG_PARAMS SET PARAM_VALUE = '" + END_TIME + "' WHERE PARAM_NAME = 'DATA_DUMP_START_TIME'";
		
		System.out.println("setReportIntervals: " + sql);
		stmt.executeUpdate(sql);

	}
	
 	public static void loadQUARTER_HOURS(Connection con, Connection mssqlcon) throws Exception {
 
           Statement stmt=con.createStatement();
			String sql = "SELECT TIMEZONE,CAST(YEAR_SK AS VARCHAR),CAST(QUARTER_NUMBER AS VARCHAR),CAST(MONTH_NUMBER AS VARCHAR),CAST(DAYS_IN_MONTH AS VARCHAR),CAST(WEEKDAYS_IN_MONTH AS VARCHAR),CAST(DAY_DT AS VARCHAR),CAST(DAY_OF_MONTH AS VARCHAR),CAST(DAY_OF_WEEK_MONDAY AS VARCHAR),CAST(DAY_OF_WEEK_SUNDAY AS VARCHAR),CAST(DAY_OF_YEAR AS VARCHAR),CAST(HOUR_DT AS VARCHAR),CAST(HALF_HOUR_DT AS VARCHAR),CAST(QUARTER_HOUR_DT AS VARCHAR),CAST(LOCAL_QUARTER_HOUR_SK AS VARCHAR),CAST(UTC_QUARTER_HOUR_SK AS VARCHAR)FROM FACT.VW_QUARTER_HOURS where hour_dt >= TO_TIMESTAMP('2021-04-01 00:00:00.000','YYYY-MM-DD HH24:MI:SS.MS') and hour_dt < TO_TIMESTAMP('2021-06-01 00:00:00.000','YYYY-MM-DD HH24:MI:SS.MS')";
            
            System.out.println("\n\n loadQUARTER_HOURS: Select Quesry: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

            String insert_stmt = "INSERT INTO [QUARTER_HOURS]([TIMEZONE],[YEAR_SK],[QUARTER_NUMBER],[MONTH_NUMBER],[DAYS_IN_MONTH],[WEEKDAYS_IN_MONTH],[DAY_DT],[DAY_OF_MONTH],[DAY_OF_WEEK_MONDAY], [DAY_OF_WEEK_SUNDAY],[DAY_OF_YEAR],[HOUR_DT],[HALF_HOUR_DT],[QUARTER_HOUR_DT],[LOCAL_QUARTER_HOUR_SK],[UTC_QUARTER_HOUR_SK]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
			System.out.println("\n\n loadQUARTER_HOURS: insert Quesry: " + insert_stmt);
  
			PreparedStatement mssqlstatement = mssqlcon.prepareStatement(insert_stmt);
            
			while(rs.next()) {
			mssqlstatement.setString(1, rs.getString("TIMEZONE"));
			mssqlstatement.setLong(2, Long.parseLong(rs.getString("YEAR_SK")));
            mssqlstatement.setLong(3, Long.parseLong(rs.getString("QUARTER_NUMBER")));
            mssqlstatement.setLong(4, Long.parseLong(rs.getString("MONTH_NUMBER")));
			mssqlstatement.setLong(5, Long.parseLong(rs.getString("DAYS_IN_MONTH")));
			mssqlstatement.setLong(6, Long.parseLong(rs.getString("WEEKDAYS_IN_MONTH")));
            mssqlstatement.setString(7, rs.getString("DAY_DT"));
            mssqlstatement.setLong(8, Long.parseLong(rs.getString("DAY_OF_MONTH")));
			mssqlstatement.setLong(9, Long.parseLong(rs.getString("DAY_OF_WEEK_MONDAY")));
			mssqlstatement.setLong(10, Long.parseLong(rs.getString("DAY_OF_WEEK_SUNDAY")));
			mssqlstatement.setLong(11, Long.parseLong(rs.getString("DAY_OF_YEAR")));
            mssqlstatement.setString(12, rs.getString("HOUR_DT"));
			mssqlstatement.setString(13, rs.getString("HALF_HOUR_DT"));
            mssqlstatement.setString(14, rs.getString("QUARTER_HOUR_DT"));
            mssqlstatement.setString(15, rs.getString("LOCAL_QUARTER_HOUR_SK"));
            mssqlstatement.setString(16, rs.getString("UTC_QUARTER_HOUR_SK"));
   
             // insert the data
			mssqlstatement.executeUpdate();
   
         }
	}
		
	


		public static void main(String args[]) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//Here you set to your timezone
		sdf.setTimeZone(TimeZone.getDefault());
		
		System.out.println("********* STARTED@ ********* " + sdf.format(calendar.getTime()));

		try{
			FileInputStream fis = new FileInputStream("D:/CustomReporting/config.properties");

			Properties prop = new Properties();
			prop.load(fis);
			
			//String db_name = "db.mssql";
			String db_name = "db.postgresql";
			
			String driver = prop.getProperty(db_name + ".driver");
			String url = prop.getProperty(db_name + ".connection.url");
			String user = prop.getProperty(db_name + ".user");
			String password = prop.getProperty(db_name + ".password");
			
			System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
		
			db_name = "db.mssql";			
			driver = prop.getProperty(db_name + ".driver");
			url = prop.getProperty(db_name + ".connection.url");
			user = prop.getProperty(db_name + ".user");
			password = prop.getProperty(db_name + ".password");

			System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection mssqlcon=DriverManager.getConnection(url, user, password);			
			
			//getReportIntervals(mssqlcon, "DATA_DUMP_START_TIME");
			
			System.out.println("Start: " + START_TIME + " - " + END_TIME);
			
			//cleanupTables(mssqlcon,'QUARTER_HOURS');
			
			loadQUARTER_HOURS(con, mssqlcon);
			
			//setReportIntervals(mssqlcon,"");

			con.close();
			mssqlcon.close();
			
		}catch(Exception e){ System.out.println(e);
			System.out.println("Exception occurred while connecting to DB. " + e);
            e.printStackTrace();			
		}
		calendar.setTime(new java.util.Date());
		System.out.println("********* END@ *********  " + sdf.format(calendar.getTime()));
	}
}