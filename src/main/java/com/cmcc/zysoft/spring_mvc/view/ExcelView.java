package com.cmcc.zysoft.spring_mvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cmcc.zysoft.spring_mvc.model.User;

public class ExcelView extends AbstractNameExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    HSSFSheet sheet;
        HSSFCell cell;
        sheet = workbook.createSheet("Spring");
        sheet.setDefaultColumnWidth(12);
        cell = getCell(sheet, 0, 0);
        User user = (User) model.get("user");
        setText(cell, user.getUsername());
	}

}
