//javac -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. EtisalatDataDump.java
//java -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. EtisalatDataDump


import java.sql.*;  
import java.util.*;
import java.text.*;
import java.io.*;
import java.util.Date;

public class EtisalatDataDump{  

	static String START_TIME = "2021-01-01 00:00:00.000";
	static String END_TIME = "2021-03-13 07:30:00.000";
	
		public static void cleanupTables(Connection con, String tableName) throws Exception{

		Statement stmt=con.createStatement();
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : Cleanup.... : "+tableName);
		if(tableName != null && tableName != "")
		{
			stmt.executeUpdate("DELETE FROM " + tableName);
			System.out.println(new Timestamp(new Date().getTime()) + " : Cleanup completed.... : "+tableName);
		}else{
			System.out.println(new Timestamp(new Date().getTime()) + " : Table Name is not specified...");
			System.out.println(new Timestamp(new Date().getTime()) + " : Cleanup not completed.... : "+tableName);
			//stmt.executeUpdate("DELETE FROM DIM_ROUTING_SERVICES");
			//stmt.executeUpdate("DELETE FROM DIM_AGENTS");
		}
		

	}

	public static void getReportIntervals(Connection con, String report_name) throws Exception{
		Statement stmt = con.createStatement();
		String sql = "SELECT CONVERT(VARCHAR(50), CONVERT(DATETIME, PARAM_VALUE, 121), 121) AS START_TIME, CONVERT(VARCHAR(50), (DATEADD(MINUTE, CAST((SELECT PARAM_VALUE FROM CONFIG_PARAMS WHERE PARAM_NAME = 'INTERVAL_IN_MINUTES') AS INT), CONVERT(DATETIME, PARAM_VALUE, 121))), 121) AS END_TIME FROM CONFIG_PARAMS WHERE PARAM_NAME = 'DATA_DUMP_START_TIME'";		
		
		System.out.println(new Timestamp(new Date().getTime()) + " : getReportIntervals: " + sql);
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			START_TIME = rs.getString(1);
			END_TIME = rs.getString(2);
		}
		
		System.out.println(new Timestamp(new Date().getTime()) + " : START_TIME:" + START_TIME + ", END_TIME:" + END_TIME);
	}
	
	public static void setReportIntervals(Connection con, String report_name) throws Exception{
		Statement stmt = con.createStatement();
		
		String sql = "UPDATE CONFIG_PARAMS SET PARAM_VALUE = '" + END_TIME + "' WHERE PARAM_NAME = 'DATA_DUMP_START_TIME'";
		
		System.out.println(new Timestamp(new Date().getTime()) + " : setReportIntervals: " + sql);
		stmt.executeUpdate(sql);

	}
	
 	public static void loadQUARTER_HOURS(Connection con, Connection mssqlcon) throws Exception {
 
           Statement stmt=con.createStatement();
			String sql = "SELECT TIMEZONE,CAST(YEAR_SK AS VARCHAR),CAST(QUARTER_NUMBER AS VARCHAR),CAST(MONTH_NUMBER AS VARCHAR),CAST(DAYS_IN_MONTH AS VARCHAR),CAST(WEEKDAYS_IN_MONTH AS VARCHAR),CAST(DAY_DT AS VARCHAR),CAST(DAY_OF_MONTH AS VARCHAR),CAST(DAY_OF_WEEK_MONDAY AS VARCHAR),CAST(DAY_OF_WEEK_SUNDAY AS VARCHAR),CAST(DAY_OF_YEAR AS VARCHAR),CAST(HOUR_DT AS VARCHAR),CAST(HALF_HOUR_DT AS VARCHAR),CAST(QUARTER_HOUR_DT AS VARCHAR),CAST(LOCAL_QUARTER_HOUR_SK AS VARCHAR),CAST(UTC_QUARTER_HOUR_SK AS VARCHAR)FROM FACT.VW_QUARTER_HOURS where hour_dt >= TO_TIMESTAMP('2021-01-01 00:00:00.000','YYYY-MM-DD HH24:MI:SS.MS') and hour_dt < TO_TIMESTAMP('2021-02-01 00:00:00.000','YYYY-MM-DD HH24:MI:SS.MS')";
            
            System.out.println(new Timestamp(new Date().getTime()) + " : \n loadQUARTER_HOURS: Select Quesry: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

            String insert_stmt = "INSERT INTO [QUARTER_HOURS]([TIMEZONE],[YEAR_SK],[QUARTER_NUMBER],[MONTH_NUMBER],[DAYS_IN_MONTH],[WEEKDAYS_IN_MONTH],[DAY_DT],[DAY_OF_MONTH],[DAY_OF_WEEK_MONDAY], [DAY_OF_WEEK_SUNDAY],[DAY_OF_YEAR],[HOUR_DT],[HALF_HOUR_DT],[QUARTER_HOUR_DT],[LOCAL_QUARTER_HOUR_SK],[UTC_QUARTER_HOUR_SK]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
			System.out.println(new Timestamp(new Date().getTime()) + " : \n loadQUARTER_HOURS: insert Quesry: " + insert_stmt);
  
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
		
	public static void loadDIM_ROUTING_SERVICES(Connection con, Connection mssqlcon) throws Exception {

		Statement stmt=con.createStatement();
	
		String sql = "SELECT ROUTING_SERVICE_SK, CHANNEL_ID, PROVIDER_ID, ROUTING_SERVICE_ID, ROUTING_SERVICE_NAME, ROUTING_SERVICE_CATEGORY_NAME, TO_CHAR(ROW_ACTIVE_DATE, 'YYYY-MM-DD HH:MI:SS.MS') AS ROW_ACTIVE_DATE, TO_CHAR(ROW_INACTIVE_DATE, 'YYYY-MM-DD HH:MI:SS.MS') AS ROW_INACTIVE_DATE, ACTIVE_RECORD, TO_CHAR(LAST_CHANGED, 'YYYY-MM-DD HH:MI:SS.MS') AS LAST_CHANGED FROM FACT.DIM_ROUTING_SERVICES";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : loadDIM_ROUTING_SERVICES : Select Quesry : " + sql);

		ResultSet rs = stmt.executeQuery(sql);
		
		String insert_stmt = "INSERT INTO DIM_ROUTING_SERVICES (ROUTING_SERVICE_SK, CHANNEL_ID, PROVIDER_ID, ROUTING_SERVICE_ID, ROUTING_SERVICE_NAME, ROUTING_SERVICE_CATEGORY_NAME, ROW_ACTIVE_DATE, ROW_INACTIVE_DATE, ACTIVE_RECORD, LAST_CHANGED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : loadDIM_ROUTING_SERVICES : insert Quesry : " + insert_stmt);
		PreparedStatement mssqlstatement = mssqlcon.prepareStatement(insert_stmt);
		
		while(rs.next()) {

			mssqlstatement.setInt(1, rs.getInt("ROUTING_SERVICE_SK"));
			mssqlstatement.setString(2, rs.getString("CHANNEL_ID"));
			mssqlstatement.setString(3, rs.getString("PROVIDER_ID"));
			mssqlstatement.setString(4, rs.getString("ROUTING_SERVICE_ID"));
			mssqlstatement.setString(5, rs.getString("ROUTING_SERVICE_NAME"));
			mssqlstatement.setString(6, rs.getString("ROUTING_SERVICE_CATEGORY_NAME"));
			mssqlstatement.setString(7, rs.getString("ROW_ACTIVE_DATE"));
			mssqlstatement.setString(8, rs.getString("ROW_INACTIVE_DATE"));
			mssqlstatement.setString(9, rs.getString("ACTIVE_RECORD"));
			mssqlstatement.setString(10, rs.getString("LAST_CHANGED"));
			// insert the data
			mssqlstatement.executeUpdate();
		}
	}


	public static void loadDIM_AGENTS(Connection con, Connection mssqlcon) throws Exception {
		Statement stmt=con.createStatement();

		String sql =" SELECT AGENT_SK, SUPERVISOR_SK, AGENT_ID, AGENT_FIRST_NAME, AGENT_LAST_NAME, AGENT_DISPLAY_NAME, AGENT_USER_HANDLE, AGENT_TYPE, TO_CHAR(ROW_ACTIVE_DATE, 'YYYY-MM-DD HH24:MI:SS.MS') as ROW_ACTIVE_DATE,TO_CHAR(ROW_INACTIVE_DATE, 'YYYY-MM-DD HH24:MI:SS.MS') AS ROW_INACTIVE_DATE, ACTIVE_RECORD, TO_CHAR(LAST_CHANGED, 'YYYY-MM-DD HH24:MI:SS.MS') AS LAST_CHANGED FROM FACT.DIM_AGENTS";

		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : loadDIM_AGENTS : Select Query : " + sql);

		ResultSet rs = stmt.executeQuery(sql);
		
		String insert_stmt = "INSERT INTO DIM_AGENTS (AGENT_SK, SUPERVISOR_SK, AGENT_ID, AGENT_FIRST_NAME, AGENT_LAST_NAME, AGENT_DISPLAY_NAME, AGENT_USER_HANDLE, AGENT_TYPE, ROW_ACTIVE_DATE, ROW_INACTIVE_DATE, ACTIVE_RECORD, LAST_CHANGED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : loadDIM_AGENTS : insert Query : " + insert_stmt);
		
		PreparedStatement mssqlstatement = mssqlcon.prepareStatement(insert_stmt);
		
		while(rs.next()) {

			mssqlstatement.setString(1, rs.getString("AGENT_SK"));
			mssqlstatement.setString(2, rs.getString("SUPERVISOR_SK"));
			mssqlstatement.setString(3, rs.getString("AGENT_ID"));
			mssqlstatement.setString(4, rs.getString("AGENT_FIRST_NAME"));
			mssqlstatement.setString(5, rs.getString("AGENT_LAST_NAME"));
			mssqlstatement.setString(6, rs.getString("AGENT_DISPLAY_NAME"));
			mssqlstatement.setString(7, rs.getString("AGENT_ID"));
			mssqlstatement.setString(8, rs.getString("AGENT_TYPE"));
			mssqlstatement.setString(9, rs.getString("ROW_ACTIVE_DATE"));
			mssqlstatement.setString(10, rs.getString("ROW_INACTIVE_DATE"));
			mssqlstatement.setString(11, rs.getString("ACTIVE_RECORD"));
			mssqlstatement.setString(12, rs.getString("LAST_CHANGED"));
			// insert the data
			mssqlstatement.executeUpdate();
		}
	}
		
		
	public static void loadCDR_CONTACTS(Connection con, Connection mssqlcon) throws Exception{
		
		Statement stmt=con.createStatement();
		
		String sql = "SELECT ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, ENGAGEMENT_ID, CONTACT_ID, CUSTOMER_ID, TO_CHAR(ORIGINATED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS ORIGINATED_TIMESTAMP, TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS END_TIMESTAMP, RING_TIME_DURATION, HANDLING_TIME_DURATION, HOLD_DURATION, ACTIVE_TIME_DURATION, ABANDONED_INDICATOR, INITIAL_DISPOSITION, FINAL_DISPOSITION, CONTACT_DURATION, CALLING_PARTY, IS_EXTERNAL, INCOMPLETE, SEGMENT_TYPE, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, TRANSFERRED_COUNT, CONSULT_COUNT, CONFERENCE_COUNT, DESTINATION_PARTY, PERSONAL_CONTACT_INDICATOR, WAIT_TIME_INDICATOR, WAIT_TIME, TREATMENT_INDICATOR, COVERAGE_INDICATOR, ROUTING_DURATION, DEFERRED_INDICATOR, AGENT_DISCONNECT_FIRST, LAST_ROUTING_SERVICE_SK, EMAIL_FORWARD_INDICATOR, TO_CHAR(CUSTOMER_CONTACT_END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS CUSTOMER_CONTACT_END_TIMESTAMP, AUTO_REDACTED, CONVERSATION_THREAD_ID, MESSAGE_SOURCE_ID, TO_CHAR(LAST_CHANGED, 'YYYY-MM-DD HH24:MI:SS.MS') AS LAST_CHANGED,reply_indicator from FACT.cdr_contacts WHERE ORIGINATED_TIMESTAMP >= TO_TIMESTAMP('" + START_TIME+ "', 'YYYY-MM-DD HH24:MI:SS.MS') AND ORIGINATED_TIMESTAMP < TO_TIMESTAMP('" + END_TIME + "', 'YYYY-MM-DD HH24:MI:SS.MS') ORDER BY ORIGINATED_TIMESTAMP";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : loadCDR_CONTACTS : Select Quesry : " + sql);
		
		ResultSet rs = stmt.executeQuery(sql);
		
		String insert_stmt = "INSERT INTO CDR_CONTACTS (ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, ENGAGEMENT_ID, CONTACT_ID, CUSTOMER_ID, ORIGINATED_TIMESTAMP, END_TIMESTAMP, RING_TIME_DURATION, HANDLING_TIME_DURATION, HOLD_DURATION, ACTIVE_TIME_DURATION, ABANDONED_INDICATOR, INITIAL_DISPOSITION, FINAL_DISPOSITION, CONTACT_DURATION, CALLING_PARTY, IS_EXTERNAL, INCOMPLETE, SEGMENT_TYPE, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, TRANSFERRED_COUNT, CONSULT_COUNT, CONFERENCE_COUNT, DESTINATION_PARTY, PERSONAL_CONTACT_INDICATOR, WAIT_TIME_INDICATOR, WAIT_TIME, TREATMENT_INDICATOR, COVERAGE_INDICATOR, ROUTING_DURATION, DEFERRED_INDICATOR, AGENT_DISCONNECT_FIRST, LAST_ROUTING_SERVICE_SK, EMAIL_FORWARD_INDICATOR, CUSTOMER_CONTACT_END_TIMESTAMP, AUTO_REDACTED, CONVERSATION_THREAD_ID, MESSAGE_SOURCE_ID, LAST_CHANGED,reply_indicator) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : loadCDR_CONTACTS : insert Quesry : " + insert_stmt);
		PreparedStatement mssqlstatement = mssqlcon.prepareStatement(insert_stmt);
		String dat = "";
		while(rs.next()) {
			//System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			try { 
			dat = rs.getString("ENGAGEMENT_ID") + "    " + rs.getString("CONTACT_ID");
			mssqlstatement.setLong(1,rs.getLong("ROUTING_POINT_SK"));
			mssqlstatement.setLong(2,rs.getLong("ROUTING_SERVICE_SK"));
			mssqlstatement.setLong(3,rs.getLong("ENGAGEMENT_SK"));
			mssqlstatement.setLong(4,rs.getLong("CONTACT_SK"));
			mssqlstatement.setString(5,rs.getString("ENGAGEMENT_ID"));
			mssqlstatement.setString(6,rs.getString("CONTACT_ID"));
			mssqlstatement.setString(7,rs.getString("CUSTOMER_ID"));
			mssqlstatement.setString(8,rs.getString("ORIGINATED_TIMESTAMP"));
			//mssqlstatement.setString(9,rs.getString("END_TIMESTAMP"));
			if(rs.getLong("ROUTING_SERVICE_SK") == -1){
					mssqlstatement.setString(9, null);
					}
					else{
						mssqlstatement.setString(9, rs.getString("END_TIMESTAMP"));
					}
			mssqlstatement.setLong(10,rs.getLong("RING_TIME_DURATION"));
			mssqlstatement.setLong(11,rs.getLong("HANDLING_TIME_DURATION"));
			mssqlstatement.setLong(12,rs.getLong("HOLD_DURATION"));
			mssqlstatement.setLong(13,rs.getLong("ACTIVE_TIME_DURATION"));
			mssqlstatement.setString(14,rs.getString("ABANDONED_INDICATOR"));
			mssqlstatement.setString(15,rs.getString("INITIAL_DISPOSITION"));
			mssqlstatement.setString(16,rs.getString("FINAL_DISPOSITION"));
			mssqlstatement.setLong(17,rs.getLong("CONTACT_DURATION"));
			mssqlstatement.setString(18,rs.getString("CALLING_PARTY"));
			mssqlstatement.setString(19,rs.getString("IS_EXTERNAL"));
			mssqlstatement.setString(20,rs.getString("INCOMPLETE"));
			mssqlstatement.setString(21,rs.getString("SEGMENT_TYPE"));
			mssqlstatement.setString(22,rs.getString("TRANSFERRED_INDICATOR"));
			mssqlstatement.setString(23,rs.getString("CONSULT_INDICATOR"));
			mssqlstatement.setString(24,rs.getString("CONFERENCE_INDICATOR"));
			mssqlstatement.setInt(25,rs.getInt("TRANSFERRED_COUNT"));
			mssqlstatement.setInt(26,rs.getInt("CONSULT_COUNT"));
			mssqlstatement.setInt(27,rs.getInt("CONFERENCE_COUNT"));
			mssqlstatement.setString(28,rs.getString("DESTINATION_PARTY"));
			mssqlstatement.setString(29,rs.getString("PERSONAL_CONTACT_INDICATOR"));
			mssqlstatement.setString(30,rs.getString("WAIT_TIME_INDICATOR"));
			mssqlstatement.setLong(31,rs.getLong("WAIT_TIME"));
			mssqlstatement.setString(32,rs.getString("TREATMENT_INDICATOR"));
			mssqlstatement.setString(33,rs.getString("COVERAGE_INDICATOR"));
			mssqlstatement.setLong(34,rs.getLong("ROUTING_DURATION"));
			mssqlstatement.setString(35,rs.getString("DEFERRED_INDICATOR"));
			mssqlstatement.setString(36,rs.getString("AGENT_DISCONNECT_FIRST"));
			mssqlstatement.setLong(37,rs.getLong("LAST_ROUTING_SERVICE_SK"));
			mssqlstatement.setString(38,rs.getString("EMAIL_FORWARD_INDICATOR"));
			mssqlstatement.setString(39,rs.getString("CUSTOMER_CONTACT_END_TIMESTAMP"));
			mssqlstatement.setString(40,rs.getString("AUTO_REDACTED"));
			mssqlstatement.setString(41,rs.getString("CONVERSATION_THREAD_ID"));
			mssqlstatement.setString(42,rs.getString("MESSAGE_SOURCE_ID"));
			mssqlstatement.setString(43,rs.getString("LAST_CHANGED"));
			mssqlstatement.setString(44,rs.getString("reply_indicator"));
			// insert the data
			mssqlstatement.executeUpdate();
			}catch(Exception e) {
				System.out.println(dat + ", exception occured: " + e);
				throw new Exception(e);
			}
		}		
	}

	public static void loadCDR_SEGMENTS(Connection con, Connection mssqlcon) throws Exception {
		
		Statement stmt=con.createStatement();
		
		String sql = "SELECT SEGMENT_SK, ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, AGENT_SK, AGENT_CHANNEL_ADDRESS, ENGAGEMENT_ID, CONTACT_ID, SEGMENT_ID, TO_CHAR(ORIGINATED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS ORIGINATED_TIMESTAMP, TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS END_TIMESTAMP, RING_TIME_DURATION, ACTIVE_TIME_DURATION, HANDLING_TIME_DURATION, CONSULT_TIME_DURATION, AFTER_CALL_WORK_DURATION, ABANDONED_INDICATOR, RONA_INDICATOR, SEGMENT_DURATION, CALLING_PARTY, IS_EXTERNAL, INITIAL_DISPOSITION, FINAL_DISPOSITION, INCOMPLETE, SEGMENT_TYPE, TO_CHAR(ANSWER_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS ANSWER_TIMESTAMP, TO_CHAR(CUSTOMER_CONTACT_END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS CUSTOMER_CONTACT_END_TIMESTAMP, ALERT_TIME_DURATION, DISCONNECT_FROM_HOLD_INDICATOR, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, HOLD_INDICATOR, HOLD_DURATION, BARGED_OUT_INDICATOR, COACHED_DURATION, COACHING_DURATION, OBSERVED_DURATION, OBSERVING_DURATION, BARGED_IN_DURATION, BARGED_OUT_DURATION, TO_CHAR(COACH_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS COACH_TIMESTAMP, TO_CHAR(BARGED_OUT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS BARGED_OUT_TIMESTAMP, HOLD_COUNT, TO_CHAR(OBSERVE_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS OBSERVE_TIMESTAMP, COACH_INDICATOR, COACHING_INDICATOR, BARGED_IN_INDICATOR, OBSERVED_INDICATOR, OBSERVING_INDICATOR, PERSONAL_CONTACT_INDICATOR, PERSONAL_OUTBOUND_INDICATOR, PERSONAL_INBOUND_INDICATOR, ROUTED_CONTACT_INDICATOR, DEFERRED_INDICATOR, DEFERRED_REASON_CODE_SK, RESERVED_INDICATOR, TO_CHAR(TRANSFER_INITIATED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS TRANSFER_INITIATED_TIMESTAMP, TO_CHAR(TRANSFER_ACCEPTED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS TRANSFER_ACCEPTED_TIMESTAMP, AUTO_REDACTED, TO_CHAR(LAST_CHANGED, 'YYYY-MM-DD HH24:MI:SS.MS') AS LAST_CHANGED FROM FACT.CDR_SEGMENTS WHERE ORIGINATED_TIMESTAMP >= TO_TIMESTAMP('" + START_TIME+ "', 'YYYY-MM-DD HH24:MI:SS.MS') AND ORIGINATED_TIMESTAMP < TO_TIMESTAMP('" + END_TIME + "', 'YYYY-MM-DD HH24:MI:SS.MS') ORDER BY ORIGINATED_TIMESTAMP";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : loadCDR_SEGMENTS : Select Quesry : " + sql);


		ResultSet rs = stmt.executeQuery(sql);
		
		String insert_stmt = "INSERT INTO CDR_SEGMENTS (SEGMENT_SK, ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, AGENT_SK, AGENT_CHANNEL_ADDRESS, ENGAGEMENT_ID, CONTACT_ID, SEGMENT_ID, ORIGINATED_TIMESTAMP, END_TIMESTAMP, RING_TIME_DURATION, ACTIVE_TIME_DURATION, HANDLING_TIME_DURATION, CONSULT_TIME_DURATION, AFTER_CALL_WORK_DURATION, ABANDONED_INDICATOR, RONA_INDICATOR, SEGMENT_DURATION, CALLING_PARTY, IS_EXTERNAL, INITIAL_DISPOSITION, FINAL_DISPOSITION, INCOMPLETE, SEGMENT_TYPE, ANSWER_TIMESTAMP, CUSTOMER_CONTACT_END_TIMESTAMP, ALERT_TIME_DURATION, DISCONNECT_FROM_HOLD_INDICATOR, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, HOLD_INDICATOR, HOLD_DURATION, BARGED_OUT_INDICATOR, COACHED_DURATION, COACHING_DURATION, OBSERVED_DURATION, OBSERVING_DURATION, BARGED_IN_DURATION, BARGED_OUT_DURATION, COACH_TIMESTAMP, BARGED_OUT_TIMESTAMP, HOLD_COUNT, OBSERVE_TIMESTAMP, COACH_INDICATOR, COACHING_INDICATOR, BARGED_IN_INDICATOR, OBSERVED_INDICATOR, OBSERVING_INDICATOR, PERSONAL_CONTACT_INDICATOR, PERSONAL_OUTBOUND_INDICATOR, PERSONAL_INBOUND_INDICATOR, ROUTED_CONTACT_INDICATOR, DEFERRED_INDICATOR, DEFERRED_REASON_CODE_SK, RESERVED_INDICATOR, TRANSFER_INITIATED_TIMESTAMP, TRANSFER_ACCEPTED_TIMESTAMP, AUTO_REDACTED, LAST_CHANGED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : loadCDR_SEGMENTS : insert Quesry : " + insert_stmt);
		PreparedStatement mssqlstatement = mssqlcon.prepareStatement(insert_stmt);
		
		while(rs.next()) {

			mssqlstatement.setLong(1, rs.getLong("SEGMENT_SK"));
			mssqlstatement.setLong(2, rs.getLong("ROUTING_POINT_SK"));
			mssqlstatement.setLong(3, rs.getLong("ROUTING_SERVICE_SK"));
			mssqlstatement.setLong(4, rs.getLong("ENGAGEMENT_SK"));
			mssqlstatement.setLong(5, rs.getLong("CONTACT_SK"));
			mssqlstatement.setLong(6, rs.getLong("AGENT_SK"));
			mssqlstatement.setString(7, rs.getString("AGENT_CHANNEL_ADDRESS"));
			mssqlstatement.setString(8, rs.getString("ENGAGEMENT_ID"));
			mssqlstatement.setString(9, rs.getString("CONTACT_ID"));
			mssqlstatement.setString(10, rs.getString("SEGMENT_ID"));
			mssqlstatement.setString(11, rs.getString("ORIGINATED_TIMESTAMP"));
			mssqlstatement.setString(12, rs.getString("END_TIMESTAMP"));
			mssqlstatement.setLong(13, rs.getLong("RING_TIME_DURATION"));
			mssqlstatement.setLong(14, rs.getLong("ACTIVE_TIME_DURATION"));
			mssqlstatement.setLong(15, rs.getLong("HANDLING_TIME_DURATION"));
			mssqlstatement.setLong(16, rs.getLong("CONSULT_TIME_DURATION"));
			mssqlstatement.setLong(17, rs.getLong("AFTER_CALL_WORK_DURATION"));
			mssqlstatement.setString(18, rs.getString("ABANDONED_INDICATOR"));
			mssqlstatement.setString(19, rs.getString("RONA_INDICATOR"));
			mssqlstatement.setLong(20, rs.getLong("SEGMENT_DURATION"));
			mssqlstatement.setString(21, rs.getString("CALLING_PARTY"));
			mssqlstatement.setString(22, rs.getString("IS_EXTERNAL"));
			mssqlstatement.setString(23, rs.getString("INITIAL_DISPOSITION"));
			mssqlstatement.setString(24, rs.getString("FINAL_DISPOSITION"));
			mssqlstatement.setString(25, rs.getString("INCOMPLETE"));
			mssqlstatement.setString(26, rs.getString("SEGMENT_TYPE"));	
			mssqlstatement.setString(27, rs.getString("ANSWER_TIMESTAMP"));
			mssqlstatement.setString(28, rs.getString("CUSTOMER_CONTACT_END_TIMESTAMP"));
			mssqlstatement.setLong(29, rs.getLong("ALERT_TIME_DURATION"));
			mssqlstatement.setString(30, rs.getString("DISCONNECT_FROM_HOLD_INDICATOR"));
			mssqlstatement.setString(31, rs.getString("TRANSFERRED_INDICATOR"));
			mssqlstatement.setString(32, rs.getString("CONSULT_INDICATOR"));
			mssqlstatement.setString(33, rs.getString("CONFERENCE_INDICATOR"));
			mssqlstatement.setString(34, rs.getString("HOLD_INDICATOR"));
			mssqlstatement.setLong(35, rs.getLong("HOLD_DURATION"));
			mssqlstatement.setString(36, rs.getString("BARGED_OUT_INDICATOR"));
			mssqlstatement.setLong(37, rs.getLong("COACHED_DURATION"));
			mssqlstatement.setLong(38, rs.getLong("COACHING_DURATION"));
			mssqlstatement.setLong(39, rs.getLong("OBSERVED_DURATION"));
			mssqlstatement.setLong(40, rs.getLong("OBSERVING_DURATION"));
			mssqlstatement.setLong(41, rs.getLong("BARGED_IN_DURATION"));
			mssqlstatement.setLong(42, rs.getLong("BARGED_OUT_DURATION"));
			mssqlstatement.setString(43, rs.getString("COACH_TIMESTAMP"));
			mssqlstatement.setString(44, rs.getString("BARGED_OUT_TIMESTAMP"));
			mssqlstatement.setLong(45, rs.getLong("HOLD_COUNT"));
			mssqlstatement.setString(46, rs.getString("OBSERVE_TIMESTAMP"));
			mssqlstatement.setString(47, rs.getString("COACH_INDICATOR"));
			mssqlstatement.setString(48, rs.getString("COACHING_INDICATOR"));
			mssqlstatement.setString(49, rs.getString("BARGED_IN_INDICATOR"));
			mssqlstatement.setString(50, rs.getString("OBSERVED_INDICATOR"));
			mssqlstatement.setString(51, rs.getString("OBSERVING_INDICATOR"));
			mssqlstatement.setString(52, rs.getString("PERSONAL_CONTACT_INDICATOR"));
			mssqlstatement.setString(53, rs.getString("PERSONAL_OUTBOUND_INDICATOR"));
			mssqlstatement.setString(54, rs.getString("PERSONAL_INBOUND_INDICATOR"));
			mssqlstatement.setString(55, rs.getString("ROUTED_CONTACT_INDICATOR"));
			mssqlstatement.setString(56, rs.getString("DEFERRED_INDICATOR"));
			mssqlstatement.setLong(57, rs.getLong("DEFERRED_REASON_CODE_SK"));
			mssqlstatement.setString(58, rs.getString("RESERVED_INDICATOR"));
			mssqlstatement.setString(59, rs.getString("TRANSFER_INITIATED_TIMESTAMP"));
			mssqlstatement.setString(60, rs.getString("TRANSFER_ACCEPTED_TIMESTAMP"));
			mssqlstatement.setString(61, rs.getString("AUTO_REDACTED"));
			mssqlstatement.setString(62, rs.getString("LAST_CHANGED"));

			// insert the data
			mssqlstatement.executeUpdate();
		}
	}
	
	public static void cdr_contacts_update(Connection postgres, Connection mssql) throws Exception {
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : *********  Updating CDR_CONTACTS FOR ROUTING_SERVICE_SK = -1 Records *********");		
		
		String mssql_query = "select engagement_id from CDR_CONTACTS where (ROUTING_SERVICE_SK = '-1' or end_timestamp >= '"+START_TIME+"' and end_timestamp < '"+END_TIME+"') ORDER BY convert(date, originated_timestamp, 121) desc";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : mssql_query : " + mssql_query);

		Statement mssql_stmt = mssql.createStatement();
		ResultSet mssql_rs = mssql_stmt.executeQuery(mssql_query);
		
		Set<String> mssql_engagementids = new LinkedHashSet<String>();
		while(mssql_rs.next()) {
			String engagement_id = mssql_rs.getString(1);
			mssql_engagementids.add(engagement_id);
		}
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : mssql_engagementids : "+mssql_engagementids.size());
		String engagementidsstring = "";
		for(String engagement_id:mssql_engagementids)
		{
			if(engagement_id !=null && engagement_id !="")
			{
				engagementidsstring = engagementidsstring +"'"+engagement_id+"',";	
			}
		}
		
		String postgres_query = "select ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, ENGAGEMENT_ID, CONTACT_ID, CUSTOMER_ID, TO_CHAR(ORIGINATED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS ORIGINATED_TIMESTAMP, TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS END_TIMESTAMP, RING_TIME_DURATION, HANDLING_TIME_DURATION, HOLD_DURATION, ACTIVE_TIME_DURATION, ABANDONED_INDICATOR, INITIAL_DISPOSITION, FINAL_DISPOSITION, CONTACT_DURATION, CALLING_PARTY, IS_EXTERNAL, INCOMPLETE, SEGMENT_TYPE, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, TRANSFERRED_COUNT, CONSULT_COUNT, CONFERENCE_COUNT, DESTINATION_PARTY, PERSONAL_CONTACT_INDICATOR, WAIT_TIME_INDICATOR, WAIT_TIME, TREATMENT_INDICATOR, COVERAGE_INDICATOR, ROUTING_DURATION, DEFERRED_INDICATOR, AGENT_DISCONNECT_FIRST, LAST_ROUTING_SERVICE_SK, EMAIL_FORWARD_INDICATOR, TO_CHAR(CUSTOMER_CONTACT_END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS CUSTOMER_CONTACT_END_TIMESTAMP, AUTO_REDACTED, CONVERSATION_THREAD_ID, MESSAGE_SOURCE_ID, TO_CHAR(LAST_CHANGED, 'YYYY-MM-DD HH24:MI:SS.MS') AS LAST_CHANGED,reply_indicator from fact.CDR_CONTACTS ";
		
		if(engagementidsstring != "")
		{
			if(engagementidsstring.substring(engagementidsstring.length()-1, engagementidsstring.length()).equals(","))
			{
				engagementidsstring = engagementidsstring.substring(0, engagementidsstring.length()-1);
			}
			postgres_query = postgres_query +" where engagement_id in ( " + engagementidsstring + " )";
		}
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : postgres_query : " + postgres_query);
		
		Statement postgres_stmt = postgres.createStatement();
		ResultSet postgres_rs = postgres_stmt.executeQuery(postgres_query);
		
		String insert_stmt = "INSERT INTO CDR_CONTACTS (ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, ENGAGEMENT_ID, CONTACT_ID, CUSTOMER_ID, ORIGINATED_TIMESTAMP, END_TIMESTAMP, RING_TIME_DURATION, HANDLING_TIME_DURATION, HOLD_DURATION, ACTIVE_TIME_DURATION, ABANDONED_INDICATOR, INITIAL_DISPOSITION, FINAL_DISPOSITION, CONTACT_DURATION, CALLING_PARTY, IS_EXTERNAL, INCOMPLETE, SEGMENT_TYPE, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, TRANSFERRED_COUNT, CONSULT_COUNT, CONFERENCE_COUNT, DESTINATION_PARTY, PERSONAL_CONTACT_INDICATOR, WAIT_TIME_INDICATOR, WAIT_TIME, TREATMENT_INDICATOR, COVERAGE_INDICATOR, ROUTING_DURATION, DEFERRED_INDICATOR, AGENT_DISCONNECT_FIRST, LAST_ROUTING_SERVICE_SK, EMAIL_FORWARD_INDICATOR, CUSTOMER_CONTACT_END_TIMESTAMP, AUTO_REDACTED, CONVERSATION_THREAD_ID, MESSAGE_SOURCE_ID, LAST_CHANGED,reply_indicator) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : cdr_contacts_update : insert Query : " + insert_stmt);
		PreparedStatement mssqlstatement = mssql.prepareStatement(insert_stmt);
		Statement delete_stmt=mssql.createStatement();
		
		while(postgres_rs.next()) {
			try { 
			System.out.println("\n"+new Timestamp(new Date().getTime()) + " : Existing CDR_CONTACTS delete_stmt : " + "DELETE FROM CDR_CONTACTS WHERE ENGAGEMENT_ID = '"+postgres_rs.getString("ENGAGEMENT_ID")+"'");
			delete_stmt.executeUpdate("DELETE FROM CDR_CONTACTS WHERE ENGAGEMENT_ID = '"+postgres_rs.getString("ENGAGEMENT_ID")+"'");
					
			mssqlstatement.setLong(1,postgres_rs.getLong("ROUTING_POINT_SK"));
			mssqlstatement.setLong(2,postgres_rs.getLong("ROUTING_SERVICE_SK"));
			mssqlstatement.setLong(3,postgres_rs.getLong("ENGAGEMENT_SK"));
			mssqlstatement.setLong(4,postgres_rs.getLong("CONTACT_SK"));
			mssqlstatement.setString(5,postgres_rs.getString("ENGAGEMENT_ID"));
			mssqlstatement.setString(6,postgres_rs.getString("CONTACT_ID"));
			mssqlstatement.setString(7,postgres_rs.getString("CUSTOMER_ID"));
			mssqlstatement.setString(8,postgres_rs.getString("ORIGINATED_TIMESTAMP"));
			//mssqlstatement.setString(9,postgres_rs.getString("END_TIMESTAMP"));
			if(postgres_rs.getLong("ROUTING_SERVICE_SK") == -1){
					mssqlstatement.setString(9, null);
					}
					else{
						mssqlstatement.setString(9, postgres_rs.getString("END_TIMESTAMP"));
					}
			mssqlstatement.setLong(10,postgres_rs.getLong("RING_TIME_DURATION"));
			mssqlstatement.setLong(11,postgres_rs.getLong("HANDLING_TIME_DURATION"));
			mssqlstatement.setLong(12,postgres_rs.getLong("HOLD_DURATION"));
			mssqlstatement.setLong(13,postgres_rs.getLong("ACTIVE_TIME_DURATION"));
			mssqlstatement.setString(14,postgres_rs.getString("ABANDONED_INDICATOR"));
			mssqlstatement.setString(15,postgres_rs.getString("INITIAL_DISPOSITION"));
			mssqlstatement.setString(16,postgres_rs.getString("FINAL_DISPOSITION"));
			mssqlstatement.setLong(17,postgres_rs.getLong("CONTACT_DURATION"));
			mssqlstatement.setString(18,postgres_rs.getString("CALLING_PARTY"));
			mssqlstatement.setString(19,postgres_rs.getString("IS_EXTERNAL"));
			mssqlstatement.setString(20,postgres_rs.getString("INCOMPLETE"));
			mssqlstatement.setString(21,postgres_rs.getString("SEGMENT_TYPE"));
			mssqlstatement.setString(22,postgres_rs.getString("TRANSFERRED_INDICATOR"));
			mssqlstatement.setString(23,postgres_rs.getString("CONSULT_INDICATOR"));
			mssqlstatement.setString(24,postgres_rs.getString("CONFERENCE_INDICATOR"));
			mssqlstatement.setInt(25,postgres_rs.getInt("TRANSFERRED_COUNT"));
			mssqlstatement.setInt(26,postgres_rs.getInt("CONSULT_COUNT"));
			mssqlstatement.setInt(27,postgres_rs.getInt("CONFERENCE_COUNT"));
			mssqlstatement.setString(28,postgres_rs.getString("DESTINATION_PARTY"));
			mssqlstatement.setString(29,postgres_rs.getString("PERSONAL_CONTACT_INDICATOR"));
			mssqlstatement.setString(30,postgres_rs.getString("WAIT_TIME_INDICATOR"));
			mssqlstatement.setLong(31,postgres_rs.getLong("WAIT_TIME"));
			mssqlstatement.setString(32,postgres_rs.getString("TREATMENT_INDICATOR"));
			mssqlstatement.setString(33,postgres_rs.getString("COVERAGE_INDICATOR"));
			mssqlstatement.setLong(34,postgres_rs.getLong("ROUTING_DURATION"));
			mssqlstatement.setString(35,postgres_rs.getString("DEFERRED_INDICATOR"));
			mssqlstatement.setString(36,postgres_rs.getString("AGENT_DISCONNECT_FIRST"));
			mssqlstatement.setLong(37,postgres_rs.getLong("LAST_ROUTING_SERVICE_SK"));
			mssqlstatement.setString(38,postgres_rs.getString("EMAIL_FORWARD_INDICATOR"));
			mssqlstatement.setString(39,postgres_rs.getString("CUSTOMER_CONTACT_END_TIMESTAMP"));
			mssqlstatement.setString(40,postgres_rs.getString("AUTO_REDACTED"));
			mssqlstatement.setString(41,postgres_rs.getString("CONVERSATION_THREAD_ID"));
			mssqlstatement.setString(42,postgres_rs.getString("MESSAGE_SOURCE_ID"));
			mssqlstatement.setString(43,postgres_rs.getString("LAST_CHANGED"));
			mssqlstatement.setString(44,postgres_rs.getString("reply_indicator"));
			// insert the data
			mssqlstatement.executeUpdate();
			
			System.out.println("\n"+new Timestamp(new Date().getTime()) + " : Latest CDR_CONTACTS Data Inserted : "+postgres_rs.getString("ENGAGEMENT_ID"));
			
			ReportGenerator.prepareReport(mssql, "CHAT_DETAIL_REPORT_UPDATE_PROC", ",SOURCE=Manual,WORKREQUEST_ID="+postgres_rs.getString("ENGAGEMENT_ID"));
			ReportGenerator.prepareReport(mssql, "EMAIL_DETAIL_REPORT_UPDATE_PROC", ",SOURCE=Manual,WORKREQUEST_ID="+postgres_rs.getString("ENGAGEMENT_ID"));
			ReportGenerator.prepareReport(mssql, "CHAT_DETAIL_REPORT_PROC_TEST", ",SOURCE=Manual,WORKREQUEST_ID="+postgres_rs.getString("ENGAGEMENT_ID"));
			ReportGenerator.prepareReport(mssql, "EMAIL_DETAIL_REPORT_PROC_NEW_test", ",SOURCE=Manual,WORKREQUEST_ID="+postgres_rs.getString("ENGAGEMENT_ID"));
			ReportGenerator.prepareReport(mssql, "EMAIL_DETAIL_REPORT_MULTIPLE_RECORDS_PRC", ",SOURCE=Manual,WORKREQUEST_ID="+postgres_rs.getString("ENGAGEMENT_ID"));
			
			
			}catch(Exception e) {
				System.out.println("exception occured: " + e);
				throw new Exception(e);
			}
		}
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : ********* Updating CDR_CONTACTS FOR ROUTING_SERVICE_SK = -1 Records *********");
	}
	



		public static void main(String args[]) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new java.util.Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
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

			
			//cleanupTables(mssqlcon,null);
			
			//loadDIM_ROUTING_SERVICES(con, mssqlcon);
			//loadDIM_AGENTS(con, mssqlcon);
			//loadCDR_CONTACTS(con, mssqlcon);
			//loadCDR_SEGMENTS(con, mssqlcon);
			//loadQUARTER_HOURS(con, mssqlcon);
			
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