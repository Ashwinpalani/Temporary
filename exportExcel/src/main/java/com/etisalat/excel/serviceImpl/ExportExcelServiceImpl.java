package com.etisalat.excel.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etisalat.excel.dao.ExcelExportRepositary;
import com.etisalat.excel.service.ExportExcelService;
import com.etisalat.excel.utill.ExcelVO;


@Service
@Transactional
public class ExportExcelServiceImpl implements ExportExcelService{

	@Autowired
	ExcelExportRepositary excelExportRepositary;
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	@Override
	public List<ExcelVO> export() throws Exception {
		
		List<ExcelVO> listExcelDetailsVO = excelExportRepositary.export();
		
		return listExcelDetailsVO;
	}


	@Override
	public List<ExcelVO> exportpdf() {

		String sql = "SELECT * FROM EXCEL_EXPORT_TABLE WHERE ACCOUNT_NUMBER=5.51954956E8";
		System.out.println(sql);

		List<ExcelVO> result = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ExcelVO.class));
		
		return result;
	}



}
