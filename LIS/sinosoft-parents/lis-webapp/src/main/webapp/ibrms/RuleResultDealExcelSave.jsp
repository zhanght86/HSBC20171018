<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�PDRateCashValueSave.jsp
//�����ܣ����ʱ���ֽ��ֵ����
//�������ڣ�2009-3-17
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
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
 //������Ϣ������У�鴦��
 //�������
 

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
     
     //���õ�����xls�ļ���Ĭ��ֵ
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
     loggerDebug("RuleResultDealExcelSave","����Excelʧ�ܣ�");
 }


 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script language="javascript">
 //parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

