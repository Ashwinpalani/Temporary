package com.etisalat.excel.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etisalat.excel.utill.ExcelVO;

@Configuration
public class ExcelExportRepositary {

	static ResultSetMetaData rsmd;
	static int colCount;

	@Autowired
	private Environment env;

	private static Logger logger = LogManager.getLogger(ExcelExportRepositary.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final class ExcelMapper implements RowMapper<ExcelVO> {
		
		public ExcelVO mapRow(ResultSet rs, int rowNum) {
			ExcelVO dtl = new ExcelVO();
			try {
				dtl.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
				dtl.setSrcTrxId(rs.getString("SRC_TRX_ID"));
				dtl.setTrxDateTime(rs.getString("TRX_DATE_TIME"));
				dtl.setTrxContent(rs.getString("TRX_CONTENT"));
				dtl.setTrxServiceDesc(rs.getString("TRX_SERVICE_DESC"));
				dtl.setDestAccountNumber(rs.getString("DEST_ACCOUNT_NUMBER"));
				dtl.setAgent(rs.getString("AGENT"));
				rsmd = rs.getMetaData();
				colCount = rsmd.getColumnCount();

			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("SQLException " + e);
			}
			return dtl;
		}
	}

	public List<ExcelVO> export() throws Exception {

		logger.info("Started");

		String excelFilePath = env.getProperty("excelFilePath");

		String sql = env.getProperty("queries");

		List<ExcelVO> result = null;
		try {
			result = jdbcTemplate.query(sql, new ExcelMapper());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception " + e);
			throw new Exception(e.getLocalizedMessage());
		}

		int size = result.size();
		logger.info("Number of records present is " + size);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Reviews");
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		((XSSFFont) font).setBold(true);
		style.setFont(font);

		writeHeaderLine(sheet, style);

		writeDataLines(result, workbook, sheet);

		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(excelFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("FileNotFoundException " + e);
		}

		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("IOException " + e);
		}

		logger.info("Excel file generated");

		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("IOException   " + e);
		}

		logger.info("Ended");

		return result;
	}

	private static void writeHeaderLine(XSSFSheet sheet, CellStyle style) throws SQLException {

		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < colCount; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(style);
			try {
				headerCell.setCellValue((rsmd.getColumnName(i + 1)).equalsIgnoreCase("TRX_CONTENT") ? "Message"
						: rsmd.getColumnName(i + 1));
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("SQLException   " + e);
			}
		}
	}

	private static void writeDataLines(List<ExcelVO> result, XSSFWorkbook workbook, XSSFSheet sheet) {

		int rowCount = 1;

		String json = null;
		boolean isJsonString = false;

		for (ExcelVO vo : result) {
			json = vo.getTrxContent();
			Row row = sheet.createRow(rowCount++);
			
			Cell cell;
			cell = row.createCell(0);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cell.setCellStyle(cellStyle);

			String accountNumber = "" + vo.getAccountNumber();
			cell.setCellValue(accountNumber);

			cell = row.createCell(1);
			String srcTrxId = "" + vo.getSrcTrxId();
			cell.setCellValue(srcTrxId);

			cell = row.createCell(2);
			String trxDateTime = "" + vo.getTrxDateTime();
			cell.setCellValue(trxDateTime);

			cell = row.createCell(4);
			String trxServiceDesc = "" + vo.getTrxServiceDesc();
			cell.setCellValue(trxServiceDesc);

			cell = row.createCell(5);
			String destAccountNumber = "" + vo.getDestAccountNumber();
			cell.setCellValue(destAccountNumber);

			cell = row.createCell(6);
			String agent = "" + vo.getAgent();
			cell.setCellValue(agent);

			cell = row.createCell(3);
			cell.setCellStyle(cellStyle);

			isJsonString = isJSONValid(json);

			if (isJsonString) {
				StringBuilder sb = new StringBuilder("");
				JSONArray jsonarray = new JSONArray(json);
				for (int i = 0; i < jsonarray.length(); i++) {

					JSONObject jsonobject = jsonarray.getJSONObject(i);
					String sender = jsonobject.getString("sender");
					String message = jsonobject.getString("message");
					String date = Long.toString(jsonobject.getLong("date"));
					DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					long milliSeconds = Long.parseLong(date);
					formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
					String parsedDate = formatter.format(milliSeconds);

					sb.append("[ " + parsedDate + " ] " + sender + " - " + message + "\n");
				}
				cell.setCellValue(sb + "");
			} else {
				logger.info("Invalid JSON format for the respective ( " + vo.getSrcTrxId()
						+ " ) work request ID (SRC_TRX_ID)");
				cell.setCellValue("Invalid JSON");
			}
		}

		for (int i = 0; i <= 10; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	public static boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

}