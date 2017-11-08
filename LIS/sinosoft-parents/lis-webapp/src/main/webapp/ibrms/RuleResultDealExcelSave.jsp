<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：PDRateCashValueSave.jsp
//程序功能：费率表和现金价值定制
//创建日期：2009-3-17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.ibrms.*"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
  
<%
 //接收信息，并作校验处理。
 //输入参数
 

 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "close";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getValue("GI");
 

 String ExcelName = "";
 String tID = request.getParameter("TempalteID");
 
 ExportExcel.Format format = new ExportExcel.Format();
 ArrayList listCell = new ArrayList();
 ArrayList listLB = new ArrayList();
 ArrayList listColWidth = new ArrayList();
 format.mListCell=listCell;
 format.mListBL=listLB;
 format.mListColWidth=listColWidth;
 ExportExcel.Cell tCell=null;
 ExportExcel.ListBlock tLB=null;
 
 tLB = new ExportExcel.ListBlock("001");
 /*
 String queryColNames = "select column_name,table_name from user_tab_columns where lower(table_name) "
                      + " = ( "
	                  + " select lower(tablename) from lrtemplatet where id='"+tID+"') "
	                  + " order by column_id ";   					 

 ExeSQL exec = new ExeSQL();
 SSRS ssrs = exec.execSQL(queryColNames);
 */
 String[][] tDTColumns = RuleInfoPrepare.getDtColumn(tID);
 String headerLine[] = new String[tDTColumns.length];
 if(tDTColumns!=null)
 {
 	for(int i = 0; i < tDTColumns.length; i++)
 	{
	 	if(i==0)
	 	{
			 ExcelName = tDTColumns[0][1];
	 	}
	 	//listColWidth.add("500");
	 	headerLine[i] = tDTColumns[i][0];
 	}
 }
 tLB.colName = headerLine; 
 tLB.row1 = 0;
 tLB.col1 = 0;
 tLB.InitData();
 listLB.add(tLB);
 try{
 	  response.reset();
     response.setContentType("application/octet-stream");
     
     //设置导出的xls文件名默认值
     String HeaderParam = "\""+"attachment;filename="+ExcelName+".xls"+"\"";
     response.setHeader("Content-Disposition",HeaderParam);
     OutputStream outOS = response.getOutputStream();
     BufferedOutputStream bos = new BufferedOutputStream(outOS);
     ExportExcel excel = new ExportExcel();

     excel.write(format, bos,ExcelName);
     bos.flush();
     bos.close();
	out.clear();   
	out = pageContext.pushBody();   
 }
 catch(Exception e){
     loggerDebug("RuleResultDealExcelSave","导出Excel失败！");
 }


 //添加各种预处理
%>                      
<%=Content%>
<html>
<script language="javascript">
 //parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

