
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
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.*" %>
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
  	LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
  	LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
  	TransferData tTransferData = new TransferData();
        String tContNo = request.getParameter("ContNo");
	
    String tNeedPrintFlag = request.getParameter("NeedPrintFlag"); //2008-11-27 ln add
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	String tPrtNo = request.getParameter("PrtNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	
	String tCustomerNo = request.getParameter("InsuredNo");
	String tOperatetype = request.getParameter("operate");
	String tProposalcontno = request.getParameter("proposalcontno");
	String tSerialno = request.getParameter("serialno");
	String tSpecTemp = request.getParameter("SpecTemp");
	
	loggerDebug("UWManuSpecChk","PrtNo:"+tPrtNo);
	loggerDebug("UWManuSpecChk","ContNo:"+tContNo);
	loggerDebug("UWManuSpecChk","remark:"+tRemark);
	loggerDebug("UWManuSpecChk","InsuredNo:"+tCustomerNo);
	loggerDebug("UWManuSpecChk","Operate:"+tOperatetype);
	loggerDebug("UWManuSpecChk","Proposalno:"+tProposalcontno);
	loggerDebug("UWManuSpecChk","Serialno:"+tSerialno);
	
	if (tContNo == "" || (tRemark == "" ) )
	{
		Content = "��¼�������ر�Լ����Ϣ��������ע��Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{     
	      if(tContNo != null && tPrtNo != null && tMissionID != null && tSubMissionID != null)
	      {
		   //׼����Լ��Ϣ
		   	tLCCSpecSchema.setContNo(tContNo); 
		   	tLCCSpecSchema.setCustomerNo(tCustomerNo);
		   	tLCCSpecSchema.setProposalContNo(tProposalcontno);
		   	
		   	tLCCSpecSchema.setSpecContent(tRemark);
		   	tLCCSpecSchema.setSpecType("ch");
		   	tLCCSpecSchema.setSpecCode(tSpecTemp);
		   	tLCCSpecSchema.setNeedPrint(tNeedPrintFlag);
		   //׼����Լԭ��
		tLCIndUWMasterSchema.setSpecReason(tSpecReason);
		//tongmeng 2007-12-17 add
		//��¼��Լԭ��
		tLCCSpecSchema.setSpecReason(tSpecReason);	
                
		   } // End of if
		  else
		  {
			Content = "��������ʧ��!";
			flag = false;
		  }
	}
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tLCCSpecSchema);
		tVData.add( tLCIndUWMasterSchema );
		TransferData mTransferData = new TransferData();
		//tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("ProposalContNo",tProposalcontno);
		tTransferData.setNameAndValue("Serialno",tSerialno);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
		
		tVData.add(tTransferData);
		tVData.add( tG );
		
		// ���ݴ���
		//UWSpecUI tUWSpecUI   = new UWSpecUI();
		String busiName="cbcheckUWSpecUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData,"",busiName))
		  {     
		        
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+"��ʾ���쳣��ֹ!";
}
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

