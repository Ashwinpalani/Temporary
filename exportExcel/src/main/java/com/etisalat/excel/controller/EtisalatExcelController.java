package com.etisalat.excel.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etisalat.excel.dao.PdfRepository;
import com.etisalat.excel.service.ExportExcelService;
import com.etisalat.excel.utill.ExcelVO;
import com.etisalat.excel.utill.Response;


import javax.servlet.http.HttpServletResponse;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class EtisalatExcelController {

	@Autowired
	ExportExcelService exportExcelService;

	@GetMapping("/data")
	@Scheduled(cron = "${exportExcel.cronPattern}")
	public Response<ExcelVO> export(){
		try {
			return new Response<ExcelVO>(200, "DetailsFound").setResponse(exportExcelService.export());
		} catch (IOException e) {
			e.printStackTrace();
			return new Response<ExcelVO>(400, e.getLocalizedMessage()).setResponse(new ArrayList<ExcelVO>());
		} catch (ParseException e) {
			e.printStackTrace();
			return new Response<ExcelVO>(400, e.getLocalizedMessage()).setResponse(new ArrayList<ExcelVO>());
		} catch (SQLException e) {
			e.printStackTrace();
			return new Response<ExcelVO>(400, e.getLocalizedMessage()).setResponse(new ArrayList<ExcelVO>());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<ExcelVO>(400, e.getLocalizedMessage()).setResponse(new ArrayList<ExcelVO>());
		}
		
		
	}
	@GetMapping("/pdf")
    public void exportToPDF(HttpServletResponse response) throws Exception {
	 
        response.setContentType("application/pdf");
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=EtisalatData.pdf";
        response.setHeader(headerKey, headerValue);
        
        List<ExcelVO> list = exportExcelService.exportpdf();
        
        PdfRepository generate = new PdfRepository(list);
        generate.export(response);
        
        
}
	
	
}
