<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuNormChk.jsp
//�����ܣ��˹��˱����ս���¼�뱣��
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
  //CErrors tErrors = null;
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

	String tUWFlag = request.getParameter("UWState");		
	String tUWIdea = request.getParameter("UWIdea");
	String tvalidate = request.getParameter("UWDelay");
	String tPolNo = "";

	tPolNo = request.getParameter("PolNo");

	loggerDebug("UWManuNormGChk","UWIDEA:"+tUWIdea);
	loggerDebug("UWManuNormGChk","PolNo:"+tPolNo);
	
	boolean flag = false;
	
	if (!tPolNo.equals(""))
	{
	
 	    LCPolSchema tLCPolSchema = new LCPolSchema();
 	    LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
 		  tLCPolSchema.setGrpContNo(request.getParameter("tempgrpcontno"));
 		  tLCPolSchema.setRiskCode(request.getParameter("tempriskcode"));
	    tLCPolSchema.setPolNo( tPolNo);
	    tLCPolSchema.setUWFlag(tUWFlag);
	    tLCPolSchema.setRemark(tUWIdea);
	    tLCUWMasterSchema.setPostponeDay(tvalidate);
	    tLCUWMasterSchema.setUWIdea(tUWIdea);
	    
	    tLCPolSet.add( tLCPolSchema );
	    tLCUWMasterSet.add( tLCUWMasterSchema );
	    
	    flag = true;
	}
	else
	{
	    FlagStr = "Fail";
	    Content = "���봫��ʧ��!";
	}
	
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCPolSet );
		tVData.add( tLCUWMasterSet);
		tVData.add( tG );
		
		// ���ݴ���

		UWManuNormGChkUI tUWManuNormGChkUI   = new UWManuNormGChkUI();
		if (tUWManuNormGChkUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWManuNormGChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �˹��˱�ʧ�ܣ�ԭ����: " + tUWManuNormGChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    
		    tError = tUWManuNormGChkUI.mErrors;
		    if (!tError.needDealError())
		    {                     
		    	Content = " �˹��˱������ɹ�!";
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
