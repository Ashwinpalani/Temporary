
//javac -classpath D:\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;D:\CustomReporting\postgresql-42.2.18.jar;. TableCreater.java
//java -classpath D:\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;D:\CustomReporting\postgresql-42.2.18.jar;. TableCreater


import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class TableCreater{  

	static String START_TIME = "2021-02-08 00:00:00.000";
	static String END_TIME = "2021-02-09 00:00:00.000";
	
	
		public static void pogresSQLMetadataAndSQLQuery(Connection postgreSQLConnection, Connection mssqlcon, String tableName) {
try{
			String query1 = "CREATE TABLE "+tableName+" (";
			StringBuilder str = new StringBuilder(query1);
			HashMap<String, String> tableColumnDatatype = new HashMap<String, String>();
		try {
			ResultSet rsColumns = postgreSQLConnection.getMetaData().getColumns(null, null, tableName, null);
			while (rsColumns.next()) {
			String metaDatacolumnName = rsColumns.getString("COLUMN_NAME");
			String columnType = rsColumns.getString("TYPE_NAME");
			int columnSize = rsColumns.getInt("COLUMN_SIZE");
			columnType = columnType.equals("timestamptz") ? "datetime" : columnType;
			columnType = columnType.equals("timestamp") ? "datetime" : columnType;
			String columnSizeStr = columnType.equals("datetime") ? "" : "(" + columnSize + ")";
			tableColumnDatatype.put("[" + metaDatacolumnName + "]", "[" + columnType + "]" + columnSizeStr);
			}
		} catch (Exception e) {
		}
		for (Map.Entry<String, String> entry : tableColumnDatatype.entrySet()) {
			str.append(entry.getKey() + entry.getValue() + ",");
		}
		str.append(");");
			System.out.println(tableColumnDatatype);
			str.deleteCharAt(str.length() - 3);
			String query = str.toString();
			String sqlQuery = query.replaceAll("serial(.....)", "int]").replaceAll("int8(.....)", "int]").replaceAll("int4(.....)", "int]").replaceAll("int2(....)", "int]").replaceAll("bool(....)", "bit]").replace("_bpchar", "char");
		
		System.out.println(sqlQuery);
		PreparedStatement ps=mssqlcon.prepareStatement(sqlQuery);
		ps.executeUpdate();
			//return sqlQuery;
		}
catch(Exception ea)
{
ea.printStackTrace();
}	
		}

	public static void main(String args[]) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//Here you set to your timezone
		sdf.setTimeZone(TimeZone.getDefault());
		
		System.out.println("\n\n ********* STARTED@ ********* " + sdf.format(calendar.getTime()));

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
			System.out.println("Connection created successfully for the DB: " + db_name);
			
			//pogresSQLMetadataAndSQLQuery(con,mssqlcon,"dim_routing_services");
			//pogresSQLMetadataAndSQLQuery(con,mssqlcon,"dim_agents");

			//System.out.println("Procedure execution end \n");
			
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