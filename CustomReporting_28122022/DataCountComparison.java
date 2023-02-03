//javac -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. DataCountComparison.java
//java -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. DataCountComparison >> DataCountComparison_Log.log


import java.sql.*;  
import java.util.*;
import java.text.*;
import java.io.*;

public class DataCountComparison
{
	public static void compareDataCount(Connection postgres, Connection mssql, String table, String dateColumn, String whereClause) throws Exception {
		String sql_dateRange = dateColumn + " >= CONVERT(DATETIME, '2021-10-01 00:00:00.000', 121) and " + dateColumn + " < CONVERT(DATETIME, '2021-11-01 00:00:00.000', 121) and ROUTING_SERVICE_SK != -1";

		String postgres_daterange = dateColumn + " >= TO_TIMESTAMP('2021-10-01 00:00:00.000', 'YYYY-MM-DD HH24:MI:SS.MS') AND " + dateColumn + " < TO_TIMESTAMP('2021-11-01 00:00:00.000', 'YYYY-MM-DD HH24:MI:SS.MS') and ROUTING_SERVICE_SK != -1";
		
		String postgres_query = "select DATE(" + dateColumn + "), COUNT(*) from fact." + table + " where " + postgres_daterange + " GROUP BY DATE(" + dateColumn + ") ORDER BY DATE(" + dateColumn + ") DESC ";
		
		String mssql_query = "select convert(date, " + dateColumn + ", 101) as dateString, count(*) as count from " + table + " where " +  sql_dateRange + " group by convert(date, " + dateColumn + ", 101) ORDER BY convert(date, " + dateColumn + ", 101) desc";
		
		System.out.println("\n postgres_query: " + postgres_query);
		System.out.println("\n mssql_query: " + mssql_query);

		Statement postgres_stmt = postgres.createStatement();
		ResultSet postgres_rs = postgres_stmt.executeQuery(postgres_query);
		List<String> postgressDateList = new ArrayList<>();
		Map<String, String> postgresMap = new HashMap<>();
		while(postgres_rs.next()) {
			String date = postgres_rs.getString(1);
			String recordCount = postgres_rs.getString(2);
			postgressDateList.add(date);
			postgresMap.put(date, recordCount);
		}

		Statement mssql_stmt = mssql.createStatement();
		ResultSet mssql_rs = mssql_stmt.executeQuery(mssql_query);
		Map<String, String> mssqlMap = new HashMap<>();
		while(mssql_rs.next()) {
			String date = mssql_rs.getString(1);
			String recordCount = mssql_rs.getString(2);
			mssqlMap.put(date, recordCount);
		}
		System.out.println(postgressDateList.size());
		postgressDateList.forEach(date -> {
			String postgres_count = postgresMap.get(date);
			String mssql_count = mssqlMap.get(date);
			//System.out.println(date + ",  ========  " + postgres_count + " - " + mssql_count);
			if(postgres_count != null && postgres_count.equals(mssql_count)) {
				System.out.println(date + ", 	" + postgres_count + " - " + mssql_count);
			}
			else
			{
				System.out.println(date + ", 	" + postgres_count + " - " + mssql_count + " - Count difference occured.");				
			}
		});


/*		for (Map.Entry<String,String> postgres_entry : postgresMap.entrySet()) {
			mssqlMap.get(postgres_entry.getKey());
            System.out.println("Key = " + postgres_entry.getKey() + ", Value = " + postgres_entry.getValue());
		}
*/
						 
	}
	public static void main(String args[]) {

		try{
			
			FileInputStream fis = new FileInputStream("E:/CustomReporting/CustomReporting/config.properties");

			Properties prop = new Properties();
			prop.load(fis);
			
			//String db_name = "db.mssql";
			String db_name = "db.postgresql";
			
			String driver = prop.getProperty(db_name + ".driver");
			String url = prop.getProperty(db_name + ".connection.url");
			String user = prop.getProperty(db_name + ".user");
			String password = prop.getProperty(db_name + ".password");
			password = EncryptionAndDecryption.decrypt(password, "etisalatreporting");
			//System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection created successfully for the DB: " + db_name);

			db_name = "db.mssql";			
			driver = prop.getProperty(db_name + ".driver");
			url = prop.getProperty(db_name + ".connection.url");
			user = prop.getProperty(db_name + ".user");
			password = prop.getProperty(db_name + ".password");
			password = EncryptionAndDecryption.decrypt(password, "etisalatreporting");
			//System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection mssqlcon=DriverManager.getConnection(url, user, password);			
			//System.out.println("Connection created successfully for the DB: " + db_name + ", START_TIME: " + START_TIME + ", END_TIME: " + END_TIME);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			//Here you set to your timezone
			sdf.setTimeZone(TimeZone.getDefault());
			System.out.println("********* STARTED@ ********* " + sdf.format(calendar.getTime()));

			compareDataCount(con, mssqlcon, "CDR_CONTACTS", "ORIGINATED_TIMESTAMP ", "");
			compareDataCount(con, mssqlcon, "CDR_SEGMENTS", "ORIGINATED_TIMESTAMP ", "");
			
			calendar.setTime(new java.util.Date());
			System.out.println("********* END@ *********  " + sdf.format(calendar.getTime()));
			
			//compareDataCount(con, mssqlcon, "CDR_SEGMENTS", "ORIGINATED_TIMESTAMP", "");
			
			con.close();
			mssqlcon.close();

		}catch(Exception e){ System.out.println(e);
			System.out.println("Exception occurred while connecting to DB. " + e);
            e.printStackTrace();
		}
	}
}