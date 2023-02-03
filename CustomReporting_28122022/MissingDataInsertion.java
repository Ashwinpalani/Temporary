//javac -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. MissingDataInsertion.java
//java -classpath E:\CustomReporting\CustomReporting\mssql-jdbc-8.4.1.jre8.jar;E:\CustomReporting\CustomReporting\postgresql-42.2.18.jar;. MissingDataInsertion

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

public class MissingDataInsertion {

	static String START_TIME = "2021-10-01 00:00:00.000";
	static String END_TIME = "2021-10-02 00:00:00.000";
	
	public static void cdr_contacts_insert(Connection postgres, Connection mssql, String table, String dateColumn, String whereClause) throws Exception {
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : *********  Mismatching data insertion started for : "+table+"  *********");	
		
		String sql_dateRange = dateColumn + " >= CONVERT(DATETIME, '"+START_TIME+"', 121) and " + dateColumn + " < CONVERT(DATETIME, '"+END_TIME+"', 121)";
		
		String postgres_daterange = dateColumn + " >= TO_TIMESTAMP('"+START_TIME+"', 'YYYY-MM-DD HH24:MI:SS.MS') AND " + dateColumn + " < TO_TIMESTAMP('"+END_TIME+"', 'YYYY-MM-DD HH24:MI:SS.MS')";
		
		if(whereClause!=null && whereClause!="")
		{
			sql_dateRange=sql_dateRange+" and "+whereClause;
			postgres_daterange=postgres_daterange+" and "+whereClause;
		}
		
		String postgres_query = "select ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, ENGAGEMENT_ID, CONTACT_ID, CUSTOMER_ID, TO_CHAR(ORIGINATED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS ORIGINATED_TIMESTAMP, TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS END_TIMESTAMP, RING_TIME_DURATION, HANDLING_TIME_DURATION, HOLD_DURATION, ACTIVE_TIME_DURATION, ABANDONED_INDICATOR, INITIAL_DISPOSITION, FINAL_DISPOSITION, CONTACT_DURATION, CALLING_PARTY, IS_EXTERNAL, INCOMPLETE, SEGMENT_TYPE, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, TRANSFERRED_COUNT, CONSULT_COUNT, CONFERENCE_COUNT, DESTINATION_PARTY, PERSONAL_CONTACT_INDICATOR, WAIT_TIME_INDICATOR, WAIT_TIME, TREATMENT_INDICATOR, COVERAGE_INDICATOR, ROUTING_DURATION, DEFERRED_INDICATOR, AGENT_DISCONNECT_FIRST, LAST_ROUTING_SERVICE_SK, EMAIL_FORWARD_INDICATOR, TO_CHAR(CUSTOMER_CONTACT_END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS CUSTOMER_CONTACT_END_TIMESTAMP, AUTO_REDACTED, CONVERSATION_THREAD_ID, MESSAGE_SOURCE_ID, TO_CHAR(LAST_CHANGED, 'YYYY-MM-DD HH24:MI:SS.MS') AS LAST_CHANGED,reply_indicator from fact." + table + " where " + postgres_daterange ;
		
		String mssql_query = "select engagement_id from " + table + " where " +  sql_dateRange + " ORDER BY convert(date, " + dateColumn + ", 101) desc";
		
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
		
		if(engagementidsstring != "")
		{
			if(engagementidsstring.substring(engagementidsstring.length()-1, engagementidsstring.length()).equals(","))
			{
				engagementidsstring = engagementidsstring.substring(0, engagementidsstring.length()-1);
			}
			postgres_query = postgres_query +" and engagement_id not in ( " + engagementidsstring + " )";
		}
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : postgres_query : " + postgres_query);
		
		Statement postgres_stmt = postgres.createStatement();
		ResultSet postgres_rs = postgres_stmt.executeQuery(postgres_query);
		
		String insert_stmt = "INSERT INTO "+table+" (ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, ENGAGEMENT_ID, CONTACT_ID, CUSTOMER_ID, ORIGINATED_TIMESTAMP, END_TIMESTAMP, RING_TIME_DURATION, HANDLING_TIME_DURATION, HOLD_DURATION, ACTIVE_TIME_DURATION, ABANDONED_INDICATOR, INITIAL_DISPOSITION, FINAL_DISPOSITION, CONTACT_DURATION, CALLING_PARTY, IS_EXTERNAL, INCOMPLETE, SEGMENT_TYPE, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, TRANSFERRED_COUNT, CONSULT_COUNT, CONFERENCE_COUNT, DESTINATION_PARTY, PERSONAL_CONTACT_INDICATOR, WAIT_TIME_INDICATOR, WAIT_TIME, TREATMENT_INDICATOR, COVERAGE_INDICATOR, ROUTING_DURATION, DEFERRED_INDICATOR, AGENT_DISCONNECT_FIRST, LAST_ROUTING_SERVICE_SK, EMAIL_FORWARD_INDICATOR, CUSTOMER_CONTACT_END_TIMESTAMP, AUTO_REDACTED, CONVERSATION_THREAD_ID, MESSAGE_SOURCE_ID, LAST_CHANGED,reply_indicator) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : cdr_contacts_insert : insert Query : " + insert_stmt);
		PreparedStatement mssqlstatement = mssql.prepareStatement(insert_stmt);
		Statement delete_stmt=mssql.createStatement();
		
		while(postgres_rs.next()) {
			try { 
			System.out.println("\n"+new Timestamp(new Date().getTime()) + " : MissingDataInsertion CDR_CONTACTS delete_stmt : " + "DELETE FROM "+table+" WHERE ENGAGEMENT_ID = '"+postgres_rs.getString("ENGAGEMENT_ID")+"'");
			delete_stmt.executeUpdate("DELETE FROM "+table+" WHERE ENGAGEMENT_ID = '"+postgres_rs.getString("ENGAGEMENT_ID")+"'");
					
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
			
			System.out.println("\n"+new Timestamp(new Date().getTime()) + " : Missing CDR_CONTACTS Data Inserted : "+postgres_rs.getString("ENGAGEMENT_ID"));
			
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
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : ********* Mismatching data insertion completed for : "+table+"  *********");
	}
	
	public static void cdr_segments_insert(Connection postgres, Connection mssql, String table, String dateColumn, String whereClause) throws Exception
	{
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : *********  Mismatching data insertion started for : "+table+"  *********");	
		
		String sql_dateRange = dateColumn + " >= CONVERT(DATETIME, '"+START_TIME+"', 121) and " + dateColumn + " < CONVERT(DATETIME, '"+END_TIME+"', 121)";
		String postgres_daterange = dateColumn + " >= TO_TIMESTAMP('"+START_TIME+"', 'YYYY-MM-DD HH24:MI:SS.MS') AND " + dateColumn + " < TO_TIMESTAMP('"+END_TIME+"', 'YYYY-MM-DD HH24:MI:SS.MS')";
		
		if(whereClause!=null && whereClause!="")
		{
			//sql_dateRange=sql_dateRange+" and "+whereClause +"and answer_timestamp is not null";
			sql_dateRange=sql_dateRange+" and "+whereClause;
			postgres_daterange=postgres_daterange+" and "+whereClause;
		}
		
		String postgres_query = "select SEGMENT_SK, ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, AGENT_SK, AGENT_CHANNEL_ADDRESS, ENGAGEMENT_ID, CONTACT_ID, SEGMENT_ID, TO_CHAR(ORIGINATED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS ORIGINATED_TIMESTAMP, TO_CHAR(END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS END_TIMESTAMP, RING_TIME_DURATION, ACTIVE_TIME_DURATION, HANDLING_TIME_DURATION, CONSULT_TIME_DURATION, AFTER_CALL_WORK_DURATION, ABANDONED_INDICATOR, RONA_INDICATOR, SEGMENT_DURATION, CALLING_PARTY, IS_EXTERNAL, INITIAL_DISPOSITION, FINAL_DISPOSITION, INCOMPLETE, SEGMENT_TYPE, TO_CHAR(ANSWER_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS ANSWER_TIMESTAMP, TO_CHAR(CUSTOMER_CONTACT_END_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS CUSTOMER_CONTACT_END_TIMESTAMP, ALERT_TIME_DURATION, DISCONNECT_FROM_HOLD_INDICATOR, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, HOLD_INDICATOR, HOLD_DURATION, BARGED_OUT_INDICATOR, COACHED_DURATION, COACHING_DURATION, OBSERVED_DURATION, OBSERVING_DURATION, BARGED_IN_DURATION, BARGED_OUT_DURATION, TO_CHAR(COACH_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS COACH_TIMESTAMP, TO_CHAR(BARGED_OUT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS BARGED_OUT_TIMESTAMP, HOLD_COUNT, TO_CHAR(OBSERVE_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS OBSERVE_TIMESTAMP, COACH_INDICATOR, COACHING_INDICATOR, BARGED_IN_INDICATOR, OBSERVED_INDICATOR, OBSERVING_INDICATOR, PERSONAL_CONTACT_INDICATOR, PERSONAL_OUTBOUND_INDICATOR, PERSONAL_INBOUND_INDICATOR, ROUTED_CONTACT_INDICATOR, DEFERRED_INDICATOR, DEFERRED_REASON_CODE_SK, RESERVED_INDICATOR, TO_CHAR(TRANSFER_INITIATED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS TRANSFER_INITIATED_TIMESTAMP, TO_CHAR(TRANSFER_ACCEPTED_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.MS') AS TRANSFER_ACCEPTED_TIMESTAMP, AUTO_REDACTED, TO_CHAR(LAST_CHANGED, 'YYYY-MM-DD HH24:MI:SS.MS') AS LAST_CHANGED FROM fact." + table + " where " + postgres_daterange ;
		
		String mssql_query = "select SEGMENT_ID from " + table + " where " +  sql_dateRange + " ORDER BY convert(datetime, " + dateColumn + ", 121) desc";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : mssql_query: " + mssql_query);

		Statement mssql_stmt = mssql.createStatement();
		ResultSet mssql_rs = mssql_stmt.executeQuery(mssql_query);
		
		Set<String> mssql_segmentids = new LinkedHashSet<String>();
		while(mssql_rs.next()) {
			String SEGMENT_ID = mssql_rs.getString(1);
			mssql_segmentids.add(SEGMENT_ID);
		}
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : mssql_segmentids : "+mssql_segmentids.size());
		String SEGMENT_IDsstring = "";
		for(String SEGMENT_ID:mssql_segmentids)
		{
			if(SEGMENT_ID !=null && SEGMENT_ID !="")
			{
				SEGMENT_IDsstring = SEGMENT_IDsstring +"'"+SEGMENT_ID+"',";	
			}
		}
		
		if(SEGMENT_IDsstring != "")
		{
			if(SEGMENT_IDsstring.substring(SEGMENT_IDsstring.length()-1, SEGMENT_IDsstring.length()).equals(","))
			{
				SEGMENT_IDsstring = SEGMENT_IDsstring.substring(0, SEGMENT_IDsstring.length()-1);
			}
			
			postgres_query = postgres_query +" and SEGMENT_ID not in ( " + SEGMENT_IDsstring + " )";
		}
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : postgres_query : " + postgres_query);
		
		Statement postgres_stmt = postgres.createStatement();
		ResultSet postgres_rs = postgres_stmt.executeQuery(postgres_query);
		
		String insert_stmt = "INSERT INTO "+table+" (SEGMENT_SK, ROUTING_POINT_SK, ROUTING_SERVICE_SK, ENGAGEMENT_SK, CONTACT_SK, AGENT_SK, AGENT_CHANNEL_ADDRESS, ENGAGEMENT_ID, CONTACT_ID, SEGMENT_ID, ORIGINATED_TIMESTAMP, END_TIMESTAMP, RING_TIME_DURATION, ACTIVE_TIME_DURATION, HANDLING_TIME_DURATION, CONSULT_TIME_DURATION, AFTER_CALL_WORK_DURATION, ABANDONED_INDICATOR, RONA_INDICATOR, SEGMENT_DURATION, CALLING_PARTY, IS_EXTERNAL, INITIAL_DISPOSITION, FINAL_DISPOSITION, INCOMPLETE, SEGMENT_TYPE, ANSWER_TIMESTAMP, CUSTOMER_CONTACT_END_TIMESTAMP, ALERT_TIME_DURATION, DISCONNECT_FROM_HOLD_INDICATOR, TRANSFERRED_INDICATOR, CONSULT_INDICATOR, CONFERENCE_INDICATOR, HOLD_INDICATOR, HOLD_DURATION, BARGED_OUT_INDICATOR, COACHED_DURATION, COACHING_DURATION, OBSERVED_DURATION, OBSERVING_DURATION, BARGED_IN_DURATION, BARGED_OUT_DURATION, COACH_TIMESTAMP, BARGED_OUT_TIMESTAMP, HOLD_COUNT, OBSERVE_TIMESTAMP, COACH_INDICATOR, COACHING_INDICATOR, BARGED_IN_INDICATOR, OBSERVED_INDICATOR, OBSERVING_INDICATOR, PERSONAL_CONTACT_INDICATOR, PERSONAL_OUTBOUND_INDICATOR, PERSONAL_INBOUND_INDICATOR, ROUTED_CONTACT_INDICATOR, DEFERRED_INDICATOR, DEFERRED_REASON_CODE_SK, RESERVED_INDICATOR, TRANSFER_INITIATED_TIMESTAMP, TRANSFER_ACCEPTED_TIMESTAMP, AUTO_REDACTED, LAST_CHANGED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : cdr_segments_insert : insert Query : " + insert_stmt);
		PreparedStatement mssqlstatement = mssql.prepareStatement(insert_stmt);
		Statement delete_stmt=mssql.createStatement();
		
		while(postgres_rs.next()) {
			try { 
					System.out.println("\n"+new Timestamp(new Date().getTime()) + " : MissingDataInsertion CDR_SEGMENTS delete_stmt : " + "DELETE FROM "+table+" WHERE SEGMENT_ID = '"+postgres_rs.getString("SEGMENT_ID")+"'");
					delete_stmt.executeUpdate("DELETE FROM "+table+" WHERE SEGMENT_ID = '"+postgres_rs.getString("SEGMENT_ID")+"'");
					
					mssqlstatement.setLong(1, postgres_rs.getLong("SEGMENT_SK"));
					mssqlstatement.setLong(2, postgres_rs.getLong("ROUTING_POINT_SK"));
					mssqlstatement.setLong(3, postgres_rs.getLong("ROUTING_SERVICE_SK"));
					mssqlstatement.setLong(4, postgres_rs.getLong("ENGAGEMENT_SK"));
					mssqlstatement.setLong(5, postgres_rs.getLong("CONTACT_SK"));
					mssqlstatement.setLong(6, postgres_rs.getLong("AGENT_SK"));
					mssqlstatement.setString(7, postgres_rs.getString("AGENT_CHANNEL_ADDRESS"));
					mssqlstatement.setString(8, postgres_rs.getString("ENGAGEMENT_ID"));
					mssqlstatement.setString(9, postgres_rs.getString("CONTACT_ID"));
					mssqlstatement.setString(10, postgres_rs.getString("SEGMENT_ID"));
					mssqlstatement.setString(11, postgres_rs.getString("ORIGINATED_TIMESTAMP"));
					mssqlstatement.setString(12, postgres_rs.getString("END_TIMESTAMP"));
					//if(postgres_rs.getLong("ROUTING_SERVICE_SK").equals("-1")){
					//mssqlstatement.setString(12, null);
					//}
					//else{
					//	mssqlstatement.setString(12, postgres_rs.getString("END_TIMESTAMP"));
					//}
					mssqlstatement.setLong(13, postgres_rs.getLong("RING_TIME_DURATION"));
					mssqlstatement.setLong(14, postgres_rs.getLong("ACTIVE_TIME_DURATION"));
					mssqlstatement.setLong(15, postgres_rs.getLong("HANDLING_TIME_DURATION"));
					mssqlstatement.setLong(16, postgres_rs.getLong("CONSULT_TIME_DURATION"));
					mssqlstatement.setLong(17, postgres_rs.getLong("AFTER_CALL_WORK_DURATION"));
					mssqlstatement.setString(18, postgres_rs.getString("ABANDONED_INDICATOR"));
					mssqlstatement.setString(19, postgres_rs.getString("RONA_INDICATOR"));
					mssqlstatement.setLong(20, postgres_rs.getLong("SEGMENT_DURATION"));
					mssqlstatement.setString(21, postgres_rs.getString("CALLING_PARTY"));
					mssqlstatement.setString(22, postgres_rs.getString("IS_EXTERNAL"));
					mssqlstatement.setString(23, postgres_rs.getString("INITIAL_DISPOSITION"));
					mssqlstatement.setString(24, postgres_rs.getString("FINAL_DISPOSITION"));
					mssqlstatement.setString(25, postgres_rs.getString("INCOMPLETE"));
					mssqlstatement.setString(26, postgres_rs.getString("SEGMENT_TYPE"));	
					mssqlstatement.setString(27, postgres_rs.getString("ANSWER_TIMESTAMP"));
					mssqlstatement.setString(28, postgres_rs.getString("CUSTOMER_CONTACT_END_TIMESTAMP"));
					mssqlstatement.setLong(29, postgres_rs.getLong("ALERT_TIME_DURATION"));
					mssqlstatement.setString(30, postgres_rs.getString("DISCONNECT_FROM_HOLD_INDICATOR"));
					mssqlstatement.setString(31, postgres_rs.getString("TRANSFERRED_INDICATOR"));
					mssqlstatement.setString(32, postgres_rs.getString("CONSULT_INDICATOR"));
					mssqlstatement.setString(33, postgres_rs.getString("CONFERENCE_INDICATOR"));
					mssqlstatement.setString(34, postgres_rs.getString("HOLD_INDICATOR"));
					mssqlstatement.setLong(35, postgres_rs.getLong("HOLD_DURATION"));
					mssqlstatement.setString(36, postgres_rs.getString("BARGED_OUT_INDICATOR"));
					mssqlstatement.setLong(37, postgres_rs.getLong("COACHED_DURATION"));
					mssqlstatement.setLong(38, postgres_rs.getLong("COACHING_DURATION"));
					mssqlstatement.setLong(39, postgres_rs.getLong("OBSERVED_DURATION"));
					mssqlstatement.setLong(40, postgres_rs.getLong("OBSERVING_DURATION"));
					mssqlstatement.setLong(41, postgres_rs.getLong("BARGED_IN_DURATION"));
					mssqlstatement.setLong(42, postgres_rs.getLong("BARGED_OUT_DURATION"));
					mssqlstatement.setString(43, postgres_rs.getString("COACH_TIMESTAMP"));
					mssqlstatement.setString(44, postgres_rs.getString("BARGED_OUT_TIMESTAMP"));
					mssqlstatement.setLong(45, postgres_rs.getLong("HOLD_COUNT"));
					mssqlstatement.setString(46, postgres_rs.getString("OBSERVE_TIMESTAMP"));
					mssqlstatement.setString(47, postgres_rs.getString("COACH_INDICATOR"));
					mssqlstatement.setString(48, postgres_rs.getString("COACHING_INDICATOR"));
					mssqlstatement.setString(49, postgres_rs.getString("BARGED_IN_INDICATOR"));
					mssqlstatement.setString(50, postgres_rs.getString("OBSERVED_INDICATOR"));
					mssqlstatement.setString(51, postgres_rs.getString("OBSERVING_INDICATOR"));
					mssqlstatement.setString(52, postgres_rs.getString("PERSONAL_CONTACT_INDICATOR"));
					mssqlstatement.setString(53, postgres_rs.getString("PERSONAL_OUTBOUND_INDICATOR"));
					mssqlstatement.setString(54, postgres_rs.getString("PERSONAL_INBOUND_INDICATOR"));
					mssqlstatement.setString(55, postgres_rs.getString("ROUTED_CONTACT_INDICATOR"));
					mssqlstatement.setString(56, postgres_rs.getString("DEFERRED_INDICATOR"));
					mssqlstatement.setLong(57, postgres_rs.getLong("DEFERRED_REASON_CODE_SK"));
					mssqlstatement.setString(58, postgres_rs.getString("RESERVED_INDICATOR"));
					mssqlstatement.setString(59, postgres_rs.getString("TRANSFER_INITIATED_TIMESTAMP"));
					mssqlstatement.setString(60, postgres_rs.getString("TRANSFER_ACCEPTED_TIMESTAMP"));
					mssqlstatement.setString(61, postgres_rs.getString("AUTO_REDACTED"));
					mssqlstatement.setString(62, postgres_rs.getString("LAST_CHANGED"));
			// insert the data
			 mssqlstatement.executeUpdate();
			System.out.println("\n"+new Timestamp(new Date().getTime()) + " : Missing CDR_SEGMENTS Data Inserted : "+postgres_rs.getString("SEGMENT_ID"));
			
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
		
		System.out.println("*********  Mismatching data insertion completed for : "+table+"  *********");				
	}
	
		public static void getReportIntervals(Connection con, String reportName, String paramName) throws Exception{
		
		String MISSING_DATA_INTERVAL_IN_MINUTES = "60";
		Statement stmt = con.createStatement();
		
		String sql = "SELECT CONVERT(VARCHAR(50), CONVERT(DATETIME, PARAM_VALUE, 121), 121) AS END_TIME FROM CONFIG_PARAMS WHERE PARAM_NAME in ('DATA_DUMP_START_TIME')";
		
		System.out.println("\n"+ new Timestamp(new Date().getTime()) + " : MissingDataInsertion => getReportIntervals END_TIME Query: " + sql);
		
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()) {
			END_TIME 	= rs.getString(1);
		}
		rs.close();
		
		sql = "SELECT PARAM_VALUE FROM CONFIG_PARAMS WHERE PARAM_NAME in ('MISSING_DATA_INTERVAL_IN_MINUTES')";
		
		System.out.println("\n"+ new Timestamp(new Date().getTime()) + " : MissingDataInsertion => getReportIntervals START_TIME Query: " + sql);
		
		rs = stmt.executeQuery(sql);
		while(rs.next()) {
			MISSING_DATA_INTERVAL_IN_MINUTES 	= rs.getString(1);
		}
		rs.close();
		
		int INTERVAL_IN_MINUTES = Integer.parseInt(MISSING_DATA_INTERVAL_IN_MINUTES);
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		calendar.setTime(sdf.parse(END_TIME));
		calendar.add(Calendar.MINUTE, -INTERVAL_IN_MINUTES);
		calendar.set(Calendar.SECOND, 00);
		
		START_TIME = sdf.format((calendar.getTime()));
		
		System.out.println("\n"+new Timestamp(new Date().getTime()) + " : START_TIME :" + START_TIME + ", END_TIME :" + END_TIME);
	}
	
	public static void setReportIntervals(Connection con, String report_name) throws Exception{
		Statement stmt = con.createStatement();
		
		String sql = "UPDATE CONFIG_PARAMS SET PARAM_VALUE = '" + END_TIME + "' WHERE PARAM_NAME = 'MISSED_DATA_DUMP_START_TIME'";
		
		System.out.println("setReportIntervals: " + sql);
		stmt.executeUpdate(sql);

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
			System.out.println("Connection created successfully for the DB: " + db_name + ", START_TIME: " + START_TIME + ", END_TIME: " + END_TIME);
			
			getReportIntervals(mssqlcon, "All", "");
			
			//Calendar calendar = Calendar.getInstance();
			//calendar.setTime(new java.util.Date());
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			//Here you set to your timezone
			//sdf.setTimeZone(TimeZone.getDefault());
			//System.out.println("********* STARTED@ ********* " + sdf.format(calendar.getTime()));

			//cdr_segments_insert(con, mssqlcon, "CDR_SEGMENTS", "ORIGINATED_TIMESTAMP", "ROUTING_SERVICE_SK != -1");

			//cdr_contacts_insert(con, mssqlcon, "CDR_CONTACTS", "ORIGINATED_TIMESTAMP", "ROUTING_SERVICE_SK != -1");
			
			//setReportIntervals(mssqlcon, "MISSED_DATA_DUMP_START_TIME");
			
			//calendar.setTime(new java.util.Date());
			//System.out.println("********* END@ *********  " + sdf.format(calendar.getTime()));
			
			
			con.close();
			mssqlcon.close();

		}catch(Exception e){ System.out.println(e);
			System.out.println("Exception occurred while connecting to DB. " + e);
            e.printStackTrace();
		}
	}

}
