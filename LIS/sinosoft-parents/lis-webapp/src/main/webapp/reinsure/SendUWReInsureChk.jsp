<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�SendUWReInsureChk.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.service.*"%>
<%
	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tG = new GlobalInput();  
	tG=(GlobalInput)session.getAttribute("GI");
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	String tOverFlag	=request.getParameter("OverFlag"); 
	String tOpeData = request.getParameter("OpeData");
	String tFilePath	= request.getParameter("FilePath"); 
	String tFileName	= request.getParameter("FileName");
	String tEdorNo		= request.getParameter("EdorNo");
	String tEdorType	= request.getParameter("EdorType");
	String tCaseNo		= request.getParameter("CaseNo");
	String tOpeFlag		= request.getParameter("OpeFlag");
	String tRemark 		= request.getParameter("Remark");
	String tSerialNo  = request.getParameter("SerialNo");
	String tUWConclusionInfo = request.getParameter("UWConclusionInfo");
	
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("OpeData" 	,tOpeData);
	tTransferData.setNameAndValue("FilePath"	,tFilePath);
	tTransferData.setNameAndValue("FileName"	,tFileName);
	tTransferData.setNameAndValue("EdorNo"  	,tEdorNo	);
	tTransferData.setNameAndValue("EdorType"	,tEdorType);
	tTransferData.setNameAndValue("CaseNo"  	,tCaseNo	);
	tTransferData.setNameAndValue("OpeFlag" 	,tOpeFlag	);
	tTransferData.setNameAndValue("Remark"		,tRemark);
	tTransferData.setNameAndValue("SerialNo"	,tSerialNo);
	tTransferData.setNameAndValue("UWConclusionInfo",tUWConclusionInfo);
	
	System.out.println("OverFlag: "+tOverFlag);
	System.out.println("OpeData 	"+tOpeData);
	System.out.println("OpeFlag	"+tOpeFlag);
	System.out.println("FilePath"+tFilePath);
	System.out.println("FileName"+tFileName);
	System.out.println("EdorNo	"+tEdorNo);
	System.out.println("EdorType"+tEdorType); 
	System.out.println("Caseno	"+tCaseNo); 
	System.out.println("Remark 	"+tRemark); 
	System.out.println("SerialNo  "+tSerialNo); 
	System.out.println("UWConclusionInfo: "+tUWConclusionInfo); 
	
	// ׼���������� VData
	VData tVData = new VData();
	tVData.add(tTransferData);
	tVData.add(tG );
	// ���ݴ���
	//SendUWReInsureUI tSendUWReInsureUI   = new SendUWReInsureUI();
	String SerialNo = "";
	if (!tBusinessDelegate.submitData( tVData, tOverFlag,"SendUWReInsureUI" )){
		Content = " "+"����ʧ�ܣ�ԭ����:"+" " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
  else{
	    Content = " "+"����ɹ�!"+" ";
	    FlagStr = "Succ";

	    TransferData sTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
	    SerialNo = (String)sTransferData.getValueByName("SerialNo");
	}
%>                      
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=SerialNo%>");
</script>
</html>
