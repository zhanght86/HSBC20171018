<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDIssueQuerySave.jsp
//�����ܣ������¼��
//�������ڣ�2009-4-3
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>    
<%
 //������Ϣ������У�鴦��
 //�������
 

 //PDIssueQueryBL pDIssueQueryBL = new PDIssueQueryBL();
 
 CErrors tError = null;

 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();

 GlobalInput tG = new GlobalInput(); 

 tG=(GlobalInput)session.getAttribute("GI");

 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");

 transferData.setNameAndValue("SerialNo",request.getParameter("SerialNo"));

 transferData.setNameAndValue("ReplyCont",request.getParameter("ReplyContDesc"));

 try
 {
  // ׼���������� VData
  VData tVData = new VData();


  tVData.add(tG);

  tVData.add(transferData);

String busiName="PDIssueQueryBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();

    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
    Content = " "+"����ɹ�!"+" ";
  	FlagStr = "Succ";
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();

  FlagStr = "Fail";
 }
  //pDIssueQueryBL.submitData(tVData,operator);
 

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

