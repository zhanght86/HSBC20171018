<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ManuHealthQChk.jsp
//�����ܣ��˹��˱�������ϲ�ѯ
//�������ڣ�2005-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
 
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		
	    String tGrpContNo = request.getParameter("GrpContNo");
	    String tMissionID = request.getParameter("MissionID");
	    String tSubMissionID = request.getParameter("SubMissionID");
	    String tRemark = request.getParameter("ChangeIdea");
		tLCGrpContSchema.setRemark(tRemark);
		tLCGrpContSchema.setGrpContNo(tGrpContNo);
		tLCGrpContSet.add(tLCGrpContSchema);
		
		
		 TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("GrpContNo",tGrpContNo);	  
	   tTransferData.setNameAndValue("MissionID",tMissionID );
	   tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
	  
		// ׼���������� VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tLCGrpContSet);
		tVData.add(tTransferData);
		 GrpTbWorkFlowUI tGrpTbWorkFlowUI   = new GrpTbWorkFlowUI();
		if (tGrpTbWorkFlowUI.submitData(tVData,"0000002301") == false)
	
					{						
						Content = " �б��ƻ����ԭ��¼��ʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = " �б��ƻ�����ɹ���";
						FlagStr = "Succ";
					}
	
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
