<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ����ͺ˱�Ҫ��֪ͨ��
//�����ܣ���������Լ/��ȫ �ŵ��˱�Ҫ��֪ͨ
//�������ڣ�2005-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
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
	   	    
	    String tEdorNo=request.getParameter("EdorNo"); //��ȫ����ţ��˲�����Ϊ��ʱΪ��ȫҵ���ڷ������������Լ�ֿ�����
	    String tEdorType=request.getParameter("EdorType"); //��ȫ����
	    
		tLCGrpContSchema.setRemark(tRemark);
		tLCGrpContSchema.setGrpContNo(tGrpContNo);
		tLCGrpContSet.add(tLCGrpContSchema);
		
		
		 TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
	   tTransferData.setNameAndValue("MissionID",tMissionID );
	   tTransferData.setNameAndValue("SubMissionID",tSubMissionID );	   
	   tTransferData.setNameAndValue("EdorNo",tEdorNo); //�����ţ�add by liuxiaosong 2006-11-15
	   tTransferData.setNameAndValue("EdorType",tEdorType); //��������
	  
	  
		// ׼���������� VData
		VData tVData = new VData();
		FlagStr="";

		tVData.add(tG);
		tVData.add(tLCGrpContSet);
		tVData.add(tTransferData);
		GrpTbWorkFlowUI tGrpTbWorkFlowUI   = new GrpTbWorkFlowUI();
		if (tGrpTbWorkFlowUI.submitData(tVData,"0000002301") == false)
	
		{						
			Content = "�����ҷ���ʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "�����ҷ��ųɹ���";
			FlagStr = "Succ";
		}
	
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
