<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PRnewUWManuHealthChk.jsp
//�����ܣ��б��˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");	  
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }

  
  	// Ͷ�����б�
	LCRReportSchema tLCRReportSchema = new LCRReportSchema();
	LCRReportSet tLCRReportSet = new LCRReportSet();
	
	LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
	
	String tGrpContNo = request.getParameter("GrpContNo");
	String tContNo = request.getParameter("ContNo");
	String tProposalContNo = request.getParameter("GrpContNo");
	String tInsureNo = request.getParameter("CustomerNo");
	String tRReportReason  = request.getParameter("RReportReason");
	loggerDebug("GrpUWManuRReportChk","InsureNo="+tInsureNo);
	
	
	String tSerialNo[] = request.getParameterValues("InvestigateGridNo");
	String thealthcode[] = request.getParameterValues("InvestigateGrid1");
	String thealthname[] = request.getParameterValues("InvestigateGrid2");
	
	loggerDebug("GrpUWManuRReportChk","GrpContNo:"+tGrpContNo);
	loggerDebug("GrpUWManuRReportChk","ContNo:"+tContNo);
	loggerDebug("GrpUWManuRReportChk","ProposalContNo:"+tProposalContNo);
	
	
	
	boolean flag = true;
	int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}
	loggerDebug("GrpUWManuRReportChk","count:"+ChkCount);
	if (ChkCount == 0 )
	{
		Content = "����������Ϣ¼�벻����!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpUWManuRReportChk","111");
	}
	else
	{
		loggerDebug("GrpUWManuRReportChk","222");
	    //�������һ
		    tLCRReportSchema.setGrpContNo(tGrpContNo);
		    tLCRReportSchema.setContNo(tContNo);
		    tLCRReportSchema.setProposalContNo(tGrpContNo);	    		
	    	tLCRReportSchema.setCustomerNo(tInsureNo);
	    	tLCRReportSchema.setRReportReason(tRReportReason);
	    	tLCRReportSet.add(tLCRReportSchema);

	    	loggerDebug("GrpUWManuRReportChk","chkcount="+ChkCount);
	    	if (ChkCount > 0)
	    	{
	    		for (int i = 0; i < ChkCount; i++)
			{
				if (!thealthcode[i].equals(""))
				{
		  			LCRReportItemSchema tLCRReportItemSchema = new LCRReportItemSchema();
		  			tLCRReportItemSchema.setGrpContNo(tGrpContNo);
		  			tLCRReportItemSchema.setContNo(tContNo);
		  			tLCRReportItemSchema.setProposalContNo(tGrpContNo);
					tLCRReportItemSchema.setRReportItemCode( thealthcode[i]);
					tLCRReportItemSchema.setRReportItemName( thealthname[i]);
					   	    
			    	tLCRReportItemSet.add( tLCRReportItemSchema );
			        loggerDebug("GrpUWManuRReportChk","i:"+i);
	    		    loggerDebug("GrpUWManuRReportChk","healthcode:"+thealthcode[i]);	    	
			   		flag = true;
				}
			}
			    
		}
		else
		{
			Content = "��������ʧ��!";
			flag = false;
		}
	}
	
	loggerDebug("GrpUWManuRReportChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData();
	   
       
	   tVData.add(tGlobalInput);
	   tVData.add(tLCRReportSet);
	   tVData.add(tLCRReportItemSet);
	  
	        UWRReportUI tUWRReportUI = new UWRReportUI();
		if (tUWRReportUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWRReportUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tUWRReportUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWRReportUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �˹��˱��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �˹��˱�ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
		
	} 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
