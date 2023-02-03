//javac -classpath D:\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;D:\CustomReporting\postgresql-42.2.18.jar;. Mismatchdata.java
//java -classpath D:\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;D:\CustomReporting\postgresql-42.2.18.jar;.Mismatchdata

import java.sql.*;  
import java.util.*;
import java.text.*;
import java.io.*;

public class Mismatchdata
{
	public static void compareDataCount(Connection postgres, Connection mssql, String table, String dateColumn, String whereClause) throws Exception {
		System.out.println("*********  Count comparision started for : "+table+"  *********");	
		String sql_dateRange = dateColumn + " >= CONVERT(DATETIME, '2020-09-18 00:00:00.000', 121) and " + dateColumn + " < CONVERT(DATETIME, '2021-04-25 00:00:00.000', 121)";
		
		String postgres_daterange = dateColumn + " >= TO_TIMESTAMP('2020-09-18 00:00:00.000', 'YYYY-MM-DD HH24:MI:SS.MS') AND " + dateColumn + " < TO_TIMESTAMP('2021-04-25 00:00:00.000', 'YYYY-MM-DD HH24:MI:SS.MS')";
		
		if(whereClause!=null && whereClause!="")
		{
			sql_dateRange=sql_dateRange+" and "+whereClause;
			postgres_daterange=postgres_daterange+" and "+whereClause;
		}
		String postgres_query = "select DATE(" + dateColumn + "), COUNT(*) from fact." + table + " where " + postgres_daterange + " GROUP BY DATE(" + dateColumn + ") ORDER BY DATE(" + dateColumn + ") DESC ";
		
		String mssql_query = "select convert(date, " + dateColumn + ", 101) as dateString, count(*) as count from " + table + " where " +  sql_dateRange + " group by convert(date, " + dateColumn + ", 101) ORDER BY convert(date, " + dateColumn + ", 101) desc";
		
		
		postgres_query = "select engagement_id from fact." + table + " where " + postgres_daterange + " ORDER BY DATE(" + dateColumn + ") DESC ";
		
		mssql_query = "select engagement_id from " + table + " where " +  sql_dateRange + " ORDER BY convert(date, " + dateColumn + ", 101) desc";
		
		
		System.out.println("\n\n postgres_query: " + postgres_query);
		System.out.println("\n mssql_query: " + mssql_query);

		Statement mssql_stmt = mssql.createStatement();
		ResultSet mssql_rs = mssql_stmt.executeQuery(mssql_query);
		Set<String> mssql_engagementids =
		while(mssql_rs.next()) {
			String engagement_id = mssql_rs.getString(1);
			mssqlMap.put(date, recordCount);
		}

		postgressDateList.forEach(date -> {
			String postgres_count = postgresMap.get(date);
			String mssql_count = mssqlMap.get(date);
			if(postgres_count != null && postgres_count.equals(mssql_count)) {
				System.out.println(date + ", 	" + postgres_count + " - " + mssql_count);
			}
			else
			{
				System.out.println(date + ", 	" + postgres_count + " - " + mssql_count + " - Count difference occured.");				
			}
		});
		System.out.println("*********  Count comparision completed for : "+table+"  *********");				

/*		for (Map.Entry<String,String> postgres_entry : postgresMap.entrySet()) {
			mssqlMap.get(postgres_entry.getKey());
            System.out.println("Key = " + postgres_entry.getKey() + ", Value = " + postgres_entry.getValue());
		}
*/
						 
	}
	public static void main(String args[]) {

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
			
			//System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection created successfully for the DB: " + db_name);

			db_name = "db.mssql";			
			driver = prop.getProperty(db_name + ".driver");
			url = prop.getProperty(db_name + ".connection.url");
			user = prop.getProperty(db_name + ".user");
			password = prop.getProperty(db_name + ".password");

			//System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection mssqlcon=DriverManager.getConnection(url, user, password);			
			//System.out.println("Connection created successfully for the DB: " + db_name + ", START_TIME: " + START_TIME + ", END_TIME: " + END_TIME);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			//Here you set to your timezone
			sdf.setTimeZone(TimeZone.getDefault());
			System.out.println("********* STARTED@ ********* " + sdf.format(calendar.getTime()));

			calendar.setTime(new java.util.Date());
			System.out.println("********* END@ *********  " + sdf.format(calendar.getTime()));
			
			compareDataCount(con, mssqlcon, "CDR_CONTACTS", "ORIGINATED_TIMESTAMP", "ROUTING_SERVICE_SK != -1");
			compareDataCount(con, mssqlcon, "CDR_SEGMENTS", "ORIGINATED_TIMESTAMP", "ROUTING_SERVICE_SK != -1");
			
			//compareDataCount(con, mssqlcon, "AGENT_LOGON", "LOGIN_TIMESTAMP", "");
			//compareDataCount(con, mssqlcon, "AGENT_BY_NOT_READY_REASON_CODE_INTERVAL", "INTERVAL_START_TIMESTAMP", "time_in_nr_reasoncode > 0");
			
			//compareDataCount(con, mssqlcon, "AGENT_INTERVAL", "INTERVAL_START_TIMESTAMP", "logon_duration >0");
			//compareDataCount(con, mssqlcon, "ROUTING_SERVICE_INTERVAL", "INTERVAL_START_TIMESTAMP", "");
			//compareDataCount(con, mssqlcon, "ROUTING_SERVICE_BY_AGENT_INTERVAL", "INTERVAL_START_TIMESTAMP", "");
			
			
			//compareDataCount(con, mssqlcon, "DIM_ROUTING_SERVICES", "ORIGINATED_TIMESTAMP", "ROUTING_SERVICE_SK != -1");
			//compareDataCount(con, mssqlcon, "DIM_AGENTS", "ORIGINATED_TIMESTAMP", "ROUTING_SERVICE_SK != -1");
			
			con.close();
			mssqlcon.close();

		}catch(Exception e){ System.out.println(e);
			System.out.println("Exception occurred while connecting to DB. " + e);
            e.printStackTrace();
		}
	}
}