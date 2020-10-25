package com.socialcrap.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	@SuppressWarnings({ "resource", "static-access" })
	public static <T> ByteArrayInputStream getExcel(String fileName, List<T> list) {

		ByteArrayOutputStream outputStream = null;
		Workbook workbook = null;
		workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		List<String> fieldNameList = getEntityFields(list.get(0).getClass());
		int rowCount = 0;
		int columnCount = 0;
		Row row = sheet.createRow(rowCount++);
		for (String fieldName : fieldNameList) {
			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.WHITE.getIndex());
			style.setFont(font);
			style.setAlignment(style.getAlignment().DISTRIBUTED);
			style.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			style.setFillPattern(FillPatternType.BRICKS);
			Cell cell = row.createCell(columnCount++);
			cell.setCellStyle(style);
			cell.setCellValue(fieldName.toUpperCase());
		}
		Class<?> cls = list.get(0).getClass();
		try {
			for (T t : list) {
				row = sheet.createRow(rowCount++);
				columnCount = 0;
				for (String fieldName : fieldNameList) {
					Field field = null;
					Object value = null;
					field = cls.getDeclaredField(fieldName);
					field.setAccessible(true);
					value = field.get(t);
					Cell cell = row.createCell(columnCount);
					CellStyle style = workbook.createCellStyle();
					style.setAlignment(style.getAlignment().CENTER);
					cell.setCellStyle(style);
					if (value != null) {
						if (value instanceof Long) {
							cell.setCellValue((Long) value);
						}
						if (value instanceof String) {
							cell.setCellValue((String) value);
						}

					}
					sheet.autoSizeColumn(columnCount);
					columnCount++;
				}
			}
			outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public static List<String> getEntityFields(Class<?> cls) {
		Field[] fields = cls.getDeclaredFields();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			list.add(i, fields[i].getName());
		}
		return list;
	}

}
