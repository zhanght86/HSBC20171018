<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWContManuNormChk.jsp
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
	LCContSet tLCContSet = new LCContSet();
	LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();

	String tUWFlag = request.getParameter("ContUWState");		
	String tUWIdea = request.getParameter("ContUWIdea");
	String tvalidate = request.getParameter("ContUWDelay");
	String tContNo = "";

	tContNo = request.getParameter("ContNo");

	loggerDebug("UWContManuNormGChk","UWState:"+tUWFlag);
	loggerDebug("UWContManuNormGChk","UWIDEA:"+tUWIdea);
	loggerDebug("UWContManuNormGChk","ContNo:"+tContNo);
	
	boolean flag = false;
	
	if (!tContNo.equals(""))
	{
	
 	    LCContSchema tLCContSchema = new LCContSchema();
 	    LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
 		
	    tLCContSchema.setContNo( tContNo);
	    tLCContSchema.setUWFlag(tUWFlag);
	    tLCContSchema.setRemark(tUWIdea);
	    tLCCUWMasterSchema.setPostponeDay(tvalidate);
	    tLCCUWMasterSchema.setUWIdea(tUWIdea);
	    
	    tLCContSet.add( tLCContSchema );
	    tLCCUWMasterSet.add( tLCCUWMasterSchema );
	    
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
		tVData.add( tLCContSet );
		tVData.add( tLCCUWMasterSet);
		tVData.add( tG );
		
		// ���ݴ���

		UWContManuNormGChkUI tUWContManuNormGChkUI = new UWContManuNormGChkUI();
		if (tUWContManuNormGChkUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWContManuNormGChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �˹��˱�ʧ�ܣ�ԭ����: " + tUWContManuNormGChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    
		    tError = tUWContManuNormGChkUI.mErrors;
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
