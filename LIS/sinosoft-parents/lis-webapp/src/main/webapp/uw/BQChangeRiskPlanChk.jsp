
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpecChk.jsp
//�����ܣ��˹��˱���Լ�б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//modify by zhangxing
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  boolean flag = true;
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) {
		out.println("session has expired");
		return;
   } 
    //������Ϣ
  	LPCSpecSchema tLPCSpecSchema = new LPCSpecSchema();
  	LPIndUWMasterSchema tLPIndUWMasterSchema = new LPIndUWMasterSchema();
  	TransferData tTransferData = new TransferData();
  	String tContNo = request.getParameter("ContNo");
	
    String tNeedPrintFlag = request.getParameter("NeedPrintFlag"); //2008-11-27 ln add
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	
	//String tPolNo = request.getParameter("PolNo");
	String tOperatetype = request.getParameter("operate");
	String tProposalContNo = request.getParameter("proposalContNo");
	String tSerialno = request.getParameter("serialno");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tSpecType = request.getParameter("HealthSpecTemp");
	String tSpecCode = request.getParameter("SpecTemp");
	String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	loggerDebug("BQChangeRiskPlanChk","ContNo:"+tContNo);
	loggerDebug("BQChangeRiskPlanChk","remark:"+tRemark);
	//loggerDebug("BQChangeRiskPlanChk","PolNo:"+tPolNo);
	loggerDebug("BQChangeRiskPlanChk","Operate:"+tOperatetype);
	loggerDebug("BQChangeRiskPlanChk","Proposalno:"+tProposalContNo);
	loggerDebug("BQChangeRiskPlanChk","Serialno:"+tSerialno);
	loggerDebug("BQChangeRiskPlanChk","InsuredNo:"+tInsuredNo);
	loggerDebug("BQChangeRiskPlanChk","SpecType:"+tSpecType);
	loggerDebug("BQChangeRiskPlanChk","SpecCode:"+tSpecCode);
	if (tContNo == "" || (tRemark == "" ) ){
		Content = "��¼�������ر�Լ����Ϣ��������ע��Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else{     
	    if(tContNo != null &&  tMissionID != null && tSubMissionID != null){
		   //׼����Լ��Ϣ
		   	tLPCSpecSchema.setContNo(tContNo); 
		   	tLPCSpecSchema.setCustomerNo(tInsuredNo);
		   	tLPCSpecSchema.setProposalContNo(tProposalContNo);
		   	tLPCSpecSchema.setSpecContent(tRemark);
		   	tLPCSpecSchema.setSpecType(tSpecType);
		   	tLPCSpecSchema.setSpecCode(tSpecCode);
		   	tLPCSpecSchema.setNeedPrint(tNeedPrintFlag);
		   	tLPCSpecSchema.setPrtFlag("1");
			//tongmeng 2007-12-17 add
			//��¼��Լԭ��
			tLPCSpecSchema.setSpecReason(tSpecReason);	
			            
		}else{
			Content = "��������ʧ��!";
			flag = false;
		}
	}
try{
  	if (flag == true){
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tLPCSpecSchema);
		tVData.add( tLPIndUWMasterSchema );
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("ProposalContNo",tProposalContNo);
		tTransferData.setNameAndValue("Serialno",tSerialno);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("CustomerNo",tInsuredNo);
		tTransferData.setNameAndValue("EdorAcceptNo",tEdorAcceptNo);
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tTransferData.setNameAndValue("EdorType",tEdorType);
		
		tVData.add(tTransferData);
		tVData.add( tG );
		
		// ���ݴ���
		BQSpecUI tBQSpecUI   = new BQSpecUI();
		if (!tBQSpecUI.submitData(tVData,"")){     
		        
			int n = tBQSpecUI.mErrors.getErrorCount();
			Content = " �˱���Լʧ�ܣ�ԭ����: " + tBQSpecUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail"){
		    tError = tBQSpecUI.mErrors;
		    if (!tError.needDealError()){                          
		    	Content = " �����˱���Լ�ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else{
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e){
	e.printStackTrace();
	Content = Content.trim()+"��ʾ���쳣��ֹ!";
}
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

