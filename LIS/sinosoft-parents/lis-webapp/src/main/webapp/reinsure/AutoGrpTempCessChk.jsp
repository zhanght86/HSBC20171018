<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ApplyRecallChk.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.reinsure.*"%>
  <%@page import="com.sinosoft.lis.reinsure.calRule.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	  //�������
	  CErrors tError = null;
	  String FlagStr = "Fail";
		String Content="";
		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getAttribute("GI");
		
		String tOpeFlag 	= request.getParameter("OpeFlag"); 
		String tGrpContNo	= request.getParameter("OpeData"); 
		
		System.out.println("OpeFlag: "	+tOpeFlag);
		System.out.println("GrpContNo: "+tGrpContNo);
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OpeFlag",tOpeFlag);
		tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
		
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG);
		System.out.println(" ��ʼ�ŵ��ٷֺ˱�������������������������"); 
		// ���ݴ��� 
		String result= "" ;
		GrpTempAutoUW tGrpTempAutoUW = new GrpTempAutoUW(); 
		if (tGrpTempAutoUW.submitData(tVData,"") == false){ 
			Content = " "+"�Ժ�ʧ�ܣ�ԭ����:"+" " + tGrpTempAutoUW.mErrors.getError(0).errorMessage; 
			FlagStr = "Fail";
		}
	  else{
	  	result=tGrpTempAutoUW.getResult();
		  Content = " "+"�Ժ˳ɹ�!"+" ";
		  FlagStr = "Succ";
		}
%> 
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>");
</script>
</html>

