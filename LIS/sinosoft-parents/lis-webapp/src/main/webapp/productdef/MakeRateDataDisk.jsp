<%@include file="../i18n/language.jsp"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFFont"%><%@page import="com.sinosoft.productdef.*"%><%@page import="com.sinosoft.ibrms.*"%><%@page import="java.util.*" %><%@page import="org.apache.poi.hssf.usermodel.HSSFCellStyle"%><%@page import="com.sinosoft.lis.vschema.LCPolSet"%><%@page import="com.sinosoft.lis.db.LCPolDB"%><%@page import="org.apache.poi.hssf.usermodel.HSSFCell"%><%@page import="org.apache.poi.hssf.usermodel.HSSFRow"%><%@page import="org.apache.poi.hssf.usermodel.HSSFSheet"%><%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%><%@ page language="java" pageEncoding="UTF-8"%><%response.reset();
System.out.println("+rateTableName");			
String rateTableName = request.getParameter("tableName");
			System.out.println(""+rateTableName);
			try {
				response.reset();;
				response.setContentType("application/msexcel");
				HSSFWorkbook wb = new HSSFWorkbook();
				String ExcelName = "RuleData";
				HSSFSheet sheet = wb.createSheet("Sheet1");
				sheet.setDefaultColumnWidth((short)12);
				//以下以写表头
				//表头为第一行
				HSSFRow row = sheet.createRow((short) 0);
				HSSFFont font = wb.createFont();
				font.setColor(HSSFFont.COLOR_RED);
				HSSFCellStyle cellstyle = wb.createCellStyle();
				cellstyle.setFont(font);
				//处理表头
				String[][] tDTColumns = RateInfoPrepare.getDtColumn(rateTableName);
				if(tDTColumns!=null)
				{
					HSSFCell[] HSSFCellS = new HSSFCell[tDTColumns.length];
					 if(tDTColumns!=null)
					 {
					 	for(int i = 0; i < tDTColumns.length; i++)
					 	{
						 	if(i==0)
						 	{
								 ExcelName = tDTColumns[0][1];
						 	}
						 	//listColWidth.add("500");
						 	HSSFCellS[i] = row.createCell((short)i);
						 	HSSFCellS[i].setCellValue(tDTColumns[i][0]);
					 	}
					 	//获取数据
					 	
					 	ArrayList tDataList = RateInfoPrepare.getRuleDataArray(ExcelName);
					 	for(int i=0;i<tDataList.size();i++)
					 	{
					 		String[][] temp = (String[][])tDataList.get(i);
					 		//String tData = temp[i][1];
					 		row = sheet.createRow((short) i+1);
					 		 for(int j=0;j<HSSFCellS.length;j++)
							 {
					 			HSSFCellS[j] = row.createCell((short) j);
					 			HSSFCellS[j].setEncoding(HSSFCell.ENCODING_UTF_16);
					 			HSSFCellS[j].setCellValue(temp[j][1]);
					 			//System.out.println("temp["+j+"][1]:"+HSSFCellS[j].getStringCellValue());
							 }
					 		
					 	}
					 	
					 }
				}
				response.setHeader("Content-disposition","attachment;filename="+ExcelName+".csv"); //定义文件名	
				wb.write(response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
				out.clear();
				out = pageContext.pushBody();

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}%>
