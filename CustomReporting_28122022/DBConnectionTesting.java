//javac -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. DBConnectionTesting.java
//java -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. DBConnectionTesting


import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;

public class DBConnectionTesting{  


	public static void main(String args[]) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//Here you set to your timezone
		sdf.setTimeZone(TimeZone.getDefault());
		
		System.out.println("\n\n ********* STARTED@ ********* " + sdf.format(calendar.getTime()));

		try{
			FileInputStream fis = new FileInputStream("E:/CustomReporting/CustomReporting/config.properties");

			Properties prop = new Properties();
			prop.load(fis);
			
			String db_name = "db.postgresql";
			
			String driver = prop.getProperty(db_name + ".driver");
			String url = prop.getProperty(db_name + ".connection.url");
			String user = prop.getProperty(db_name + ".user");
			String password = prop.getProperty(db_name + ".password");
			password = EncryptionAndDecryption.decrypt(password, "etisalatreporting");
			System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection created successfully for the DB: " + db_name);

			db_name = "db.mssql";			
			driver = prop.getProperty(db_name + ".driver");
			url = prop.getProperty(db_name + ".connection.url");
			user = prop.getProperty(db_name + ".user");
			password = prop.getProperty(db_name + ".password");
			password = EncryptionAndDecryption.decrypt(password, "etisalatreporting");
			//System.out.println("Driver:" + EncryptionAndDecryption.encrypt("P@ssw0rd", "etisalatreporting"));			
			
			System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection mssqlcon=DriverManager.getConnection(url, user, password);			
			System.out.println("Connection created successfully for the DB: " + db_name);
			
			//con.close();
			mssqlcon.close();
			System.out.println("\n\n ********* END@ ********* " + sdf.format(calendar.getTime()));
		}catch(Exception e){ System.out.println(e);
			System.out.println("Exception occurred while connecting to DB. " + e);
            e.printStackTrace();			
		}
		calendar.setTime(new java.util.Date());
		System.out.println("********* END@ *********  " + sdf.format(calendar.getTime()));
	}
}