<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2009-09-10
//������  ��sunyu
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*" %>

<%
  String FlagStr = "";
  String Content = "";
      	 
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("RiskCode", request.getParameter("riskcode"));
  tTransferData.setNameAndValue("batch", request.getParameter("batch"));
  
  System.out.println("��Ʒ���ִ��룺" + request.getParameter("riskcode") + " "+"���κţ�"+"" + request.getParameter("batch"));
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
   
  //����PDLBRiskInfoBL���� ������һ��ListTable ListTable�з��ľ�����Ҫչ����ҳ���ϵ���Ϣ
  PDProductCallBackBL tPDProductCallBackBL= new PDProductCallBackBL();
  
  if(!tPDProductCallBackBL.submitData(tVData, "callback")){
	  FlagStr = "Fail";
	  Content = ""+"����ʧ�ܣ�ԭ��Ϊ��"+"" + tPDProductCallBackBL.mErrors.getFirstError() + "!S";
  }else{
	  FlagStr = "True";
	  Content = ""+"���˳ɹ���"+"";
  }
%>  
 
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 