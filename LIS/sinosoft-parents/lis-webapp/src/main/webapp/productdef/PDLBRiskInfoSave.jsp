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
  tTransferData.setNameAndValue("RiskCode", request.getParameter("RiskCode"));
  tTransferData.setNameAndValue("StartDate", request.getParameter("StartDate"));
  tTransferData.setNameAndValue("EndDate", request.getParameter("EndDate"));
  tTransferData.setNameAndValue("batch", request.getParameter("batch"));
  
  System.out.println(request.getParameter("batch"));
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
   
  //����PDLBRiskInfoBL���� ������һ��ListTable ListTable�з��ľ�����Ҫչ����ҳ���ϵ���Ϣ
  PDLBRiskInfoBL tPDLBRiskInfoBL = new PDLBRiskInfoBL();
  
  ListTable tTableList =  tPDLBRiskInfoBL.submitData(tVData, "ModifyQuery");
  
  //��ֵ
  int tTableListSize = tTableList.size();
  
  //ɾ��ԭ������
  %>
  <script type="text/javascript">
 	parent.fraInterface.Mulline9Grid.clearData();
   </script>
 <%
  for(int i = 0; i < tTableListSize; i ++){
	  String[] stringArray = tTableList.get(i);
	  
	  //���һ��
	  %>
	  <script type="text/javascript">
	 	parent.fraInterface.Mulline9Grid.addOne();
	   </script>
	 <%
	 
	  int stringArrayLength = stringArray.length;
	  
	  for(int j = 0; j < stringArrayLength; j ++){
		  //���һ������
		  %>
		  <script type="text/javascript">
		 	parent.fraInterface.Mulline9Grid.setRowColData("<%=i%>", "<%=j + 1%>", "<%=stringArray[j]%>");
		   </script>
		 <%
	  }
  }
 
 FlagStr = "True";
 Content = ""+"��ѯ�޸���Ϣ�ɹ���"+"";
 %>
 
 
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 