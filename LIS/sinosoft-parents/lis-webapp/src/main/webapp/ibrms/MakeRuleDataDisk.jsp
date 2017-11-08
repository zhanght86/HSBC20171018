<%@page import="org.apache.poi.hssf.usermodel.HSSFFont"%><%@include file="../common/jsp/Log4jUI.jsp"%><%@page import="com.sinosoft.ibrms.*"%><%@page import="java.util.*" %><%@page import="org.apache.poi.hssf.usermodel.HSSFCellStyle"%><%@page import="com.sinosoft.lis.vschema.LCPolSet"%><%@page import="com.sinosoft.lis.db.LCPolDB"%><%@page import="org.apache.poi.hssf.usermodel.HSSFCell"%><%@page import="org.apache.poi.hssf.usermodel.HSSFRow"%><%@page import="org.apache.poi.hssf.usermodel.HSSFSheet"%><%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%><%@ page language="java" pageEncoding="GBK"%><%response.reset();
			String templateID  = request.getParameter("templateID");
			
	//		response.setHeader("Content-disposition",
	//				"inline;filename="+templateID+".xls"); //定义文件名

			try {
				HSSFWorkbook wb = new HSSFWorkbook();
				String ExcelName = "";
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
				String[][] tDTColumns = RuleInfoPrepare.getDtColumn(templateID);
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
						 //	HSSFCellS[i] = tDTColumns[i][0];
						 	HSSFCellS[i] = row.createCell((short)i);
						 	HSSFCellS[i].setCellValue(tDTColumns[i][0]);
					 	}
					 	response.reset();
					 	response.setContentType("application/msexcel");
					 	response.setHeader("Content-disposition",
								"inline;filename="+ExcelName+".xls"); //定义文件名	
					 	//获取数据
					 	
					 	ArrayList tDataList = RuleInfoPrepare.getRuleDataArray(templateID,ExcelName);
					 	for(int i=0;i<tDataList.size();i++)
					 	{
					 		String[][] temp = (String[][])tDataList.get(i);
					 		//String tData = temp[i][1];
					 		row = sheet.createRow((short) i+1);
					 		 for(int j=0;j<HSSFCellS.length;j++)
							 {
									//tempStr[j][0] = tDTColums[j][0];
									//tempStr[j][1] = tSSRS.GetText(i, j+1);
					 			HSSFCellS[j] = row.createCell((short) j);
//					 			loggerDebug("MakeRuleDataDisk","temp["+j+"][1]:"+temp[j][1]);
					 			
					 			HSSFCellS[j].setCellValue(temp[j][1]);
									
							 }
					 		
					 	}
					 	
					 }
				}

				wb.write(response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
				out.clear();
				out = pageContext.pushBody();

			} catch (Exception ex) {
					ex.printStackTrace();
			}%>
