<%@include file="../i18n/language.jsp"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDSugIncomeDataAlgSave.jsp
//�����ܣ������㷨����
//�������ڣ�2011-10-24
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.sinosoft.service.*" %>  
<%@page import="org.codehaus.xfire.addressing.RandomGUID" %>  
<%@page import="java.io.File" %>  
  
<%
 //������Ϣ������У�鴦��
 //�������

 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 
 TransferData transferData = new TransferData();
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 
 
 String tID =  request.getParameter("ID");
 String tCONTENT =  request.getParameter("CONTENT");
 String tTITTLE =  request.getParameter("TITTLE");
 String tBAK1 =  request.getParameter("BAK1");
 String tRELATION =  request.getParameter("RELATION");
 String tFILENAME =  request.getParameter("FILENAME");
 String   filepathsug = getServletContext().getRealPath( "/")+ "productdef\\sugconfig"; 
 String   filepath = getServletContext().getRealPath( "/")+ "productdef\\sugconfig\\"; 
 File filesug = new File(filepath);
 File file = new File(filepath+tFILENAME);
 if(!filesug.exists()){
 	filesug.mkdir();
 }
 if(operator.equals("save")){
	 if (!file.exists()) {
		 file.mkdir();
		 transferData.setNameAndValue("ID",tID);
	 	 transferData.setNameAndValue("CONTENT",tCONTENT);
	 	 transferData.setNameAndValue("TITTLE",tTITTLE);
	 	 transferData.setNameAndValue("BAK1",tBAK1);
	 	 transferData.setNameAndValue("TYPE","doc");
	 	 transferData.setNameAndValue("RELATION",tRELATION);
	 	 transferData.setNameAndValue("FILENAME",tFILENAME);
	 	 transferData.setNameAndValue("tableName",request.getParameter("tableName"));


	 	 try
	 	 {
	 	  // ׼���������� VData
	 	  VData tVData = new VData();

	 	  tVData.add(tG);
	 	  tVData.add(transferData);
	 	  String busiName="PDSugTypeManageBL";
	 	  
	 	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 	  //String tDiscountCode = "";
	 	  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	 		  VData rVData = tBusinessDelegate.getResult();
	 	    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + (String)rVData.get(0);
	 	  	FlagStr = "Fail";
	 	  }
	 	  else {
	 		  VData rVData = tBusinessDelegate.getResult();
	 	      Content = " "+"����ɹ�!"+" ";
	 	  	  FlagStr = "Succ";

	 	  }
	 	 
	 	 }
	 	 catch(Exception ex)
	 	 {
	 	  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
	 	  FlagStr = "Fail";
	 	 }
	 }else{
		 Content = " "+"�ļ����Ѿ�����!"+" ";
 	  	 FlagStr = "Fail";
	 }
 }else{
	 if(operator.equals("update")){
		 if (!file.exists()) {
			 file.mkdir();
		 }
	 }
	 transferData.setNameAndValue("ID",tID);
 	 transferData.setNameAndValue("CONTENT",tCONTENT);
 	 transferData.setNameAndValue("TITTLE",tTITTLE);
 	 transferData.setNameAndValue("BAK1",tBAK1);
 	 transferData.setNameAndValue("TYPE","doc");
 	 transferData.setNameAndValue("RELATION",tRELATION);
 	 transferData.setNameAndValue("FILENAME",tFILENAME);
 	 transferData.setNameAndValue("tableName",request.getParameter("tableName"));


 	 try
 	 {
 	  // ׼���������� VData
 	  VData tVData = new VData();

 	  tVData.add(tG);
 	  tVData.add(transferData);
 	  String busiName="PDSugTypeManageBL";
 	  
 	  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
 	  //String tDiscountCode = "";
 	  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
 		  VData rVData = tBusinessDelegate.getResult();
 	    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + (String)rVData.get(0);
 	  	FlagStr = "Fail";
 	  }
 	  else {
 		  VData rVData = tBusinessDelegate.getResult();
 	      Content = " "+"����ɹ�!"+" ";
 	  	  FlagStr = "Succ";

 	  }
 	 
 	 }
 	 catch(Exception ex)
 	 {
 	  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
 	  FlagStr = "Fail";
 	 }
 }

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
	 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


