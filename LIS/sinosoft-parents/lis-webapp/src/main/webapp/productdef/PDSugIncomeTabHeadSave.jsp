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
  
<%
 //������Ϣ������У�鴦��
 //�������

 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 String tRiskCode=null;
 
 TransferData transferData = new TransferData();
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 tRiskCode=request.getParameter("RiskCode");
 
 String tID =  request.getParameter("ID");
 String tPRIFITCODE =  request.getParameter("PRIFITCODE");
 String tNAME =  request.getParameter("NAME");
 String tPARENTID =  request.getParameter("PARENTID");
 String tSHOWORDER =  request.getParameter("SHOWORDER");
 
 transferData.setNameAndValue("ID",tID);
 transferData.setNameAndValue("PRIFITCODE",tPRIFITCODE);
 transferData.setNameAndValue("NAME",tNAME);
 transferData.setNameAndValue("PARENTID",tPARENTID);
 transferData.setNameAndValue("SHOWORDER",tSHOWORDER);
 transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));


 try
 {
  // ׼���������� VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDSugIncomeTabHeadBL";
  
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
  

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


