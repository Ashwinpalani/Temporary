package com.etisalat.excel.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import com.etisalat.excel.utill.ExcelVO;

public interface ExportExcelService {

	List<ExcelVO> export() throws IOException, ParseException, SQLException, Exception;
	
	List<ExcelVO> exportpdf();

	

}