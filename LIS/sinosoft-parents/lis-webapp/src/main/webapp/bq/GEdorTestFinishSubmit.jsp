<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%
  String busiName="EdorWorkFlowUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //������Ϣ������У�鴦��
  LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();

  //�������
  String FlagStr = "";
  String Content = "";
  GlobalInput tGI = new GlobalInput(); 
  tGI=(GlobalInput)session.getValue("GI");  
  String Operator  = tGI.Operator ;  //�����½����Ա�˺�
  String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom�ڴ��е�½��վ����
  CErrors tError = null;
  String delReason = ""; //ɾ��ԭ��
  String reasonCode = ""; //ԭ����


  TransferData tTransferData = new TransferData();
  VData tVData = new VData();

	//������ȫ����
	tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));     
	tLPEdorAppSchema.setEdorState(request.getParameter("EdorState"));
	
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");		
	
	tTransferData.setNameAndValue("MissionID",tMissionID);
	tTransferData.setNameAndValue("SubMissionID",tSubMissionID);		
	tTransferData.setNameAndValue("DelReason", delReason);
	tTransferData.setNameAndValue("ReasonCode", reasonCode);
	
	//EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
	
	try
	{
    	// ׼���������� VData
     	tVData.addElement(tGI);
     	tVData.addElement(tLPEdorAppSchema);
		tVData.addElement(tTransferData);

		tBusinessDelegate.submitData(tVData, "0000008098",busiName);	

    }
	catch(Exception ex)
	{
	      Content = "ɾ��ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError())
		{                          
			Content ="ɾ���ɹ���";
			FlagStr = "Succ";
		}
		else                                                                           
		{
			Content = "ɾ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}  

%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

