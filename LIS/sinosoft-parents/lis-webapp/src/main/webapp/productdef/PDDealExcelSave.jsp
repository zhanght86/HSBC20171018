<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

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
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 
 String MullineName = "";
 if(operator.equals("downloadRateTable"))
 {
	 MullineName = "Mulline10Grid3";
 }

 String ExcelName = request.getParameter("newTableName");
 
 
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
 
 String queryColNames = "select a.factorcode from Pd_Scheratecalfactor_Lib a right outer join User_Tab_Cols b"
		+" on upper(a.Factorcode) = b.Column_Name where b.Table_Name = upper('"
		+ ExcelName
		+ "') order by b.Column_Id ";   					 

 ExeSQL exec = new ExeSQL();
 SSRS ssrs = exec.execSQL(queryColNames);
 
 String headerLine[] = new String[ssrs.getMaxRow()];
 
 for(int i = 0; i < ssrs.MaxRow; i++)
 {
	 //listColWidth.add("500");
	 headerLine[i] = ssrs.GetText(i+1,1); 
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
     excel.write(format, bos);
     bos.flush();
     bos.close();
 }
 catch(Exception e){
     System.out.println("导出Excel失败！");
 }
 

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

