
import java.sql.*;  
import java.util.*;
import java.text.*;
import java.io.*;

public class ReportGenerator{  

	public static void prepareReport(final Connection con, final String proc_name, final String inputParams) throws Exception {
		String START_TIME 	= "";
		String END_TIME 	= "";
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String inParams = "PARAM_NAME=" + proc_name + ",START_TIME=" + START_TIME + ",END_TIME_TIME=" + END_TIME + inputParams;
		
		//Here you set to your timezone
		sdf.setTimeZone(TimeZone.getDefault());
		
		System.out.println("\n ********* STARTED@ ********* " + sdf.format(calendar.getTime()));
		System.out.println("Proc_name: " + proc_name + ", param_name: " + inParams);

		String sql = "{call " + proc_name + "(?,?)}";
		CallableStatement cstmt = con.prepareCall(sql);
		//Setting the value for the TN parameter
		cstmt.setString(1, inParams);

		//Registering the type of the OUT parameters
		cstmt.registerOutParameter(2, Types.VARCHAR);

		//Executing the CallableStatement
		cstmt.executeUpdate();

		//Retrieving the values for product name, customer name and, price
		String status = cstmt.getString(2);
		System.out.println("Status: " + status);
		calendar.setTime(new java.util.Date());
		System.out.println("********* END@ *********  " + sdf.format(calendar.getTime()));
	}
	
	public static void main(String args[]) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//Here you set to your timezone
		sdf.setTimeZone(TimeZone.getDefault());
		
		System.out.println("********* STARTED@ ********* " + sdf.format(calendar.getTime()));

		try{
			FileInputStream fis = new FileInputStream("E:/CustomReporting/CustomReporting/config.properties");

			Properties prop = new Properties();
			prop.load(fis);
			
			String db_name = "db.mssql";
			
			String driver = prop.getProperty(db_name + ".driver");
			String url = prop.getProperty(db_name + ".connection.url");
			String user = prop.getProperty(db_name + ".user");
			String password = prop.getProperty(db_name + ".password");
			
			System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			
				try{
					
				} catch (Exception e) {
					System.out.println("Exception occured while preparing Report for the procedure => " + e);
					e.printStackTrace();
					//throw new Exception();
				}
			
			con.close();
			}catch(Exception e){ System.out.println(e);
			System.out.println("Exception occurred while connecting to DB. " + e);
            e.printStackTrace();			
		}
		calendar.setTime(new java.util.Date());
		System.out.println("********* END@ *********  " + sdf.format(calendar.getTime()));
	}
}