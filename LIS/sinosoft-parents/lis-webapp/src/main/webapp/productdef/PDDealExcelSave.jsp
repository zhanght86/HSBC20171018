<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

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
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
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
     
     //���õ�����xls�ļ���Ĭ��ֵ
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
     System.out.println("����Excelʧ�ܣ�");
 }
 

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

