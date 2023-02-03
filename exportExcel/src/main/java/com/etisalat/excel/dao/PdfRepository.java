package com.etisalat.excel.dao;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.etisalat.excel.utill.ExcelVO;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Configuration
public class PdfRepository {

	static ResultSetMetaData rsmd;
	static int colCount;
	final static String USER_PASSWORD = "user";
	final static String OWNER_PASSWORD = "owner";
	List<ExcelVO> list;

	public PdfRepository(List<ExcelVO> list) {
		this.list = list;
	}

	@Autowired
	private Environment env;

	private static Logger logger = LogManager.getLogger(ExcelExportRepositary.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final class PdfMapper implements RowMapper<ExcelVO> {

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

	public List<ExcelVO> export(HttpServletResponse response) throws Exception {

		logger.info("Started"); 

		Document document = new Document(PageSize.A2);
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
		writer.setEncryption(USER_PASSWORD.getBytes(), OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING,
				PdfWriter.ENCRYPTION_AES_128);
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLACK);

		Paragraph p = new Paragraph("List of Users", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);
		System.out.println("**********" + colCount);
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 30, 30, 30, 100, 30, 30, 30 });
		table.setSpacingAfter(20);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();
		writer.close();
		return list;

	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.CYAN);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.BLACK);

		cell.setPhrase(new Phrase("ACCOUNT_NUMBER", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("SRC_TRX_ID", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("TRX_DATE_TIME", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("TRX_CONTENT", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("TRX_SERVICE_DESC", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("DEST_ACCOUNT_NUMBER", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("AGENT", font));
		table.addCell(cell);

	}

	private void writeTableData(PdfPTable table) {
		String json = null;
		boolean isJsonString = false;
		
		for (ExcelVO pdfdata : list) {

			table.addCell(pdfdata.getAccountNumber());
			table.addCell(pdfdata.getSrcTrxId());
			table.addCell(pdfdata.getTrxDateTime());
			
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
				table.addCell(sb + "");
			} else {
				logger.info("Invalid JSON format for the respective ( " + pdfdata.getSrcTrxId()
						+ " ) work request ID (SRC_TRX_ID)");
				table.addCell("Invalid JSON");
			}
			
			table.addCell(pdfdata.getTrxContent());
			table.addCell(pdfdata.getTrxServiceDesc());
			table.addCell(pdfdata.getDestAccountNumber());
			table.addCell(pdfdata.getAgent());
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
