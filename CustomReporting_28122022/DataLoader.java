//javac -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. DataLoader.java
//java -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. DataLoader


import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.util.Date;

public class DataLoader{  

	static String START_TIME = "2021-02-08 00:00:00.000";
	static String END_TIME = "2021-02-09 00:00:00.000";
	
	public static void getReportIntervals(Connection con, String reportName, String paramName) throws Exception{
		Statement stmt = con.createStatement();
		String sql = "SELECT CONVERT(VARCHAR(50), CONVERT(DATETIME, PARAM_VALUE, 121), 121) AS START_TIME, CONVERT(VARCHAR(50), (DATEADD(MINUTE, CAST((SELECT PARAM_VALUE FROM CONFIG_PARAMS WHERE PARAM_NAME = 'INTERVAL_IN_MINUTES') AS INT), CONVERT(DATETIME, PARAM_VALUE, 121))), 121) AS END_TIME FROM CONFIG_PARAMS WHERE PARAM_NAME = 'DATA_DUMP_START_TIME'";		
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : getReportIntervals Query : " + sql);
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			START_TIME 	= rs.getString(1);
			END_TIME 	= rs.getString(2);			
		}
		rs.close();
		
		System.out.println("START_TIME:" + START_TIME + ", END_TIME:" + END_TIME);
	}
	
	public static void setReportIntervals(Connection con, String report_name) throws Exception{
		Statement stmt = con.createStatement();
		
		String sql = "UPDATE CONFIG_PARAMS SET PARAM_VALUE = '" + END_TIME + "' WHERE PARAM_NAME = 'DATA_DUMP_START_TIME'";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : setReportIntervals Query : " + sql);
		stmt.executeUpdate(sql);

	}

	public static void main(String args[]) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//Here you set to your timezone
		sdf.setTimeZone(TimeZone.getDefault());
		
		System.out.println("********* STARTED@ ********* " + sdf.format(calendar.getTime()));

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
			//System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection created successfully for the DB: " + db_name);

			db_name = "db.mssql";			
			
			driver = prop.getProperty(db_name + ".driver");
			url = prop.getProperty(db_name + ".connection.url");
			user = prop.getProperty(db_name + ".user");
			password = prop.getProperty(db_name + ".password");
			//System.out.println("Driver:" + EncryptionAndDecryption.encrypt("P@ssw0rd", "etisalatreporting"));			
			password = EncryptionAndDecryption.decrypt(password, "etisalatreporting");
			//System.out.println("Driver:" + driver + ", url:" + url + ", user:" + user + ", password:" + password);			
			Class.forName(driver);
			Connection mssqlcon=DriverManager.getConnection(url, user, password);			
			System.out.println("Connection created successfully for the DB: " + db_name);
			
			//for (int i = 0 ; i < 0 ; i++)
			{
			getReportIntervals(mssqlcon, "All", "");
			
			EtisalatDataDump.START_TIME 	= START_TIME;
			EtisalatDataDump.END_TIME 		= END_TIME;
				
			Map<String,Boolean> status = new HashMap < String, Boolean > ();
			try{
				EtisalatDataDump.cleanupTables(mssqlcon, "DIM_ROUTING_SERVICES");
				EtisalatDataDump.loadDIM_ROUTING_SERVICES(con, mssqlcon);
				//status.put("DIM_ROUTING_SERVICES", true);
				
			} catch (Exception e) {
				System.out.println("Exception occured while Data Dump : DIM_ROUTING_SERVICES. " + e);
				e.printStackTrace();
				//throw new Exception();
			}
			
			try{
				EtisalatDataDump.cleanupTables(mssqlcon, "DIM_AGENTS");
				EtisalatDataDump.loadDIM_AGENTS(con, mssqlcon);
				//status.put("DIM_AGENTS", true);
				
			} catch (Exception e) {
				System.out.println("Exception occured while Data Dump : DIM_AGENTS. " + e);
				e.printStackTrace();
				//throw new Exception();
			}
			
				
			try{
				EtisalatDataDump.loadCDR_CONTACTS(con, mssqlcon);
				//status.put("CDR_CONTACTS", true);
				
			} catch (Exception e) {
				System.out.println("Exception occured while Data Dump : CDR_CONTACTS. " + e);
				e.printStackTrace();
				//throw new Exception();
			}
			
			
			try{
				EtisalatDataDump.loadCDR_SEGMENTS(con, mssqlcon);
				//status.put("CDR_SEGMENTS", true);
				
			} catch (Exception e) {
				System.out.println("Exception occured while Data Dump : CDR_SEGMENTS. " + e);
				e.printStackTrace();
				//throw new Exception();
			}

			try{
				//EtisalatDataDump.loadQUARTER_HOURS(con, mssqlcon);
				//status.put("QUARTER_HOURS", true);
			} catch (Exception e) {
				System.out.println("Exception occured while Data Dump : QUARTER_HOURS. " + e);
				e.printStackTrace();
				//throw new Exception();
			}
			
			
			setReportIntervals(mssqlcon, "DATA_DUMP_START_TIME");
				
			System.out.println("\n"+new Timestamp(new Date().getTime()) + " : Data load completed successfully for the period : " + START_TIME + " - " + END_TIME);
			System.out.println("\n"+new Timestamp(new Date().getTime()) + " : Procedure execution starts");			
			
				try{
					ReportGenerator.prepareReport(mssqlcon, "CHAT_DETAIL_REPORT_PROC",",SOURCE=SCHEDULER");
				} catch (Exception e) {
					System.out.println("Exception occured while preparing Report for the CHAT_DETAIL_REPORT_PROC. " + e);
					e.printStackTrace();
					//throw new Exception();
				}
			
			
				try{
					ReportGenerator.prepareReport(mssqlcon, "EMAIL_DETAIL_REPORT_PROC",",SOURCE=SCHEDULER");
				} catch (Exception e) {
					System.out.println("Exception occured while preparing Report for the EMAIL_DETAIL_REPORT_PROC. " + e);
					e.printStackTrace();
					//throw new Exception();
				}
			
			
				try{
					ReportGenerator.prepareReport(mssqlcon, "EMAIL_DETAIL_REPORT_PROC_NEW_test",",SOURCE=SCHEDULER");
				} catch (Exception e) {
					System.out.println("Exception occured while preparing Report for the EMAIL_DETAIL_REPORT_PROC_NEW_test. " + e);
					e.printStackTrace();
					//throw new Exception();
				}
			
			
				try{
					ReportGenerator.prepareReport(mssqlcon, "CHAT_DETAIL_REPORT_PROC_TEST",",SOURCE=SCHEDULER");
				} catch (Exception e) {
					System.out.println("Exception occured while preparing Report for the CHAT_DETAIL_REPORT_PROC_TEST. " + e);
					e.printStackTrace();
					//throw new Exception();
				}
				
				try{
					ReportGenerator.prepareReport(mssqlcon, "EMAIL_DETAIL_REPORT_MULTIPLE_RECORDS_PRC",",SOURCE=SCHEDULER");
				} catch (Exception e) {
					System.out.println("Exception occured while preparing Report for the EMAIL_DETAIL_REPORT_MULTIPLE_RECORDS_PRC. " + e);
					e.printStackTrace();
					//throw new Exception();
				}
			
			System.out.println("\n"+new Timestamp(new Date().getTime()) + " : Procedure execution end");
			
			try{
				MissingDataInsertion.getReportIntervals(mssqlcon, "All", "");
			} catch (Exception e) {
				System.out.println("Exception occured while getReportIntervals : " + e);
				e.printStackTrace();
				//throw new Exception();
			}
			
			try{
				MissingDataInsertion.cdr_segments_insert(con, mssqlcon, "CDR_SEGMENTS", "ORIGINATED_TIMESTAMP", "");
				//MissingDataInsertion.cdr_segments_insert(con, mssqlcon, "CDR_SEGMENTS", "ORIGINATED_TIMESTAMP", "ROUTING_SERVICE_SK != -1");
			} catch (Exception e) {
				System.out.println("Exception occured while MissingDataInsertion.cdr_segments_insert : cdr_segments_insert. " + e);
				e.printStackTrace();
				//throw new Exception();
			}
			
			
			try{
				MissingDataInsertion.cdr_contacts_insert(con, mssqlcon, "CDR_CONTACTS", "ORIGINATED_TIMESTAMP", "");
				//MissingDataInsertion.cdr_contacts_insert(con, mssqlcon, "CDR_CONTACTS", "ORIGINATED_TIMESTAMP", "ROUTING_SERVICE_SK != -1");
			} catch (Exception e) {
				System.out.println("Exception occured while MissingDataInsertion.cdr_contacts_insert : cdr_contacts_insert. " + e);
				e.printStackTrace();
				//throw new Exception();
			}
			
			try{
				EtisalatDataDump.cdr_contacts_update(con, mssqlcon);
			} catch (Exception e) {
				System.out.println("Exception occured while EtisalatDataDump.cdr_contacts_update : cdr_contacts_update. " + e);
				e.printStackTrace();
				//throw new Exception();
			}
			
			}
			con.close();
			mssqlcon.close();
		}catch(Exception e){ System.out.println(e);
			System.out.println("Exception occurred while connecting to DB. " + e);
            e.printStackTrace();			
		}
		calendar.setTime(new java.util.Date());
		System.out.println("\n ********* END@ *********  " + sdf.format(calendar.getTime()));
	}
}