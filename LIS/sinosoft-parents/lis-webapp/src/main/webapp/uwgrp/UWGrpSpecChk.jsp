<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWGrpSpecChk.jsp
//�����ܣ������˹��˱���Լ¼��
//�������ڣ�2002-06-19 11:10:36
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
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�
	LCPolSet tLCPolSet = new LCPolSet();
	LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
	LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
	LCSpecSet tLCSpecSet = new LCSpecSet();
	LCPremSet tLCPremSet = new LCPremSet();

	String tProposalNo = request.getParameter("ProposalNoHide");
	String tUWFlag = request.getParameter("Flag");
	String tUWIdea = request.getParameter("UWIdea");
	String tspec = request.getParameter("Remark");
	String treason = request.getParameter("Reason");
	String tSpecReason = request.getParameter("SpecReason");
	
	String tdutycode[] = request.getParameterValues("SpecGrid1");
	String tpayplantype[] = request.getParameterValues("SpecGrid2");
	String tstartdate[] = request.getParameterValues("SpecGrid3");
	String tenddate[] = request.getParameterValues("SpecGrid4");
	String trate[] = request.getParameterValues("SpecGrid5");
	//String tpayplancode = request.getParameterValues("SpecGrid6");
	
	
	loggerDebug("UWGrpSpecChk","polno:"+tProposalNo);
	loggerDebug("UWGrpSpecChk","remark:"+tspec);
	loggerDebug("UWGrpSpecChk","flag:"+tUWFlag);
	
	boolean flag = true;
	int feeCount = tdutycode.length;
	if (feeCount == 0 && tspec.equals(""))
	{
		Content = "��¼��б�����!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("UWGrpSpecChk","111");
	}
	else
	{
		loggerDebug("UWGrpSpecChk","222");
		if (!tProposalNo.equals("")&& !tUWFlag.equals(""))
		{
			LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
 			LCSpecSchema tLCSpecSchema = new LCSpecSchema();	
			
			
			tLCSpecSchema.setPolNo(tProposalNo);
	    	tLCSpecSchema.setSpecContent(tspec);
	    		
	    	tLCGUWMasterSchema.setGrpPolNo(tProposalNo);
	    	tLCGUWMasterSchema.setPassFlag(tUWFlag);
	    	tLCGUWMasterSchema.setSpecReason(tSpecReason);
	    
	    	tLCSpecSet.add(tLCSpecSchema);
	    	tLCGUWMasterSet.add(tLCGUWMasterSchema);
	   	}
		else
		{
			Content = "��������ʧ��!";
			flag = false;
		}
	}
	
	loggerDebug("UWGrpSpecChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCSpecSet);
		tVData.add( tLCGUWMasterSet);
		tVData.add( tG );
		
		// ���ݴ���
		UWGSpecChkUI tUWGSpecChkUI   = new UWGSpecChkUI();
		if (tUWGSpecChkUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWGSpecChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �����˱�ʧ�ܣ�ԭ����: " + tUWGSpecChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWGSpecChkUI.mErrors;
		    //tErrors = tUWGSpecChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " ��Լ¼��ɹ�! ";
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
	Content = Content.trim()+".��ʾ���쳣��ֹ!";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
