<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

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
  
  
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	LCGCUWMasterSet tLCGCUWMasterSet = new LCGCUWMasterSet();

	String tUWFlag = "9";		
	String tUWIdea = "����ͨ��";
	String tGrpContNo = request.getParameter("GrpContNo");

	loggerDebug("UWBatchManuNormGChk","UWState:"+tUWFlag);
	loggerDebug("UWBatchManuNormGChk","UWIDEA:"+tUWIdea);
	loggerDebug("UWBatchManuNormGChk","GrpContNo:"+tGrpContNo);
	
	boolean flag = false;
	
	if (!tGrpContNo.equals(""))
	{
	
 	    LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
 	    LCGCUWMasterSchema tLCGCUWMasterSchema = new LCGCUWMasterSchema();
 		
	    tLCGrpContSchema.setGrpContNo( tGrpContNo);
	    tLCGrpContSchema.setUWFlag(tUWFlag);
	    tLCGrpContSchema.setRemark(tUWIdea);
	    tLCGrpContSchema.setSpecFlag("5"); //˵��������ͨ��
	    tLCGCUWMasterSchema.setUWIdea(tUWIdea);
	    
	    tLCGrpContSet.add( tLCGrpContSchema );
	    tLCGCUWMasterSet.add( tLCGCUWMasterSchema );
	    
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
		tVData.add( tLCGrpContSet );
		tVData.add( tLCGCUWMasterSet);
		tVData.add( tG );
		
		// ���ݴ���

		UWBatchManuNormGChkUI tUWBatchManuNormGChkUI = new UWBatchManuNormGChkUI();
		if (tUWBatchManuNormGChkUI.submitData(tVData,"") == false)
		{
			int n = tUWBatchManuNormGChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �˹��˱�ʧ�ܣ�ԭ����: " + tUWBatchManuNormGChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    
		    tError = tUWBatchManuNormGChkUI.mErrors;
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
