<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PRnewUWManuHealthChk.jsp
//�����ܣ��б��˹��ڵ��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
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
	
	LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	
	LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
	
	String tContNo = request.getParameter("ContNo");
	String tProposalContNo = request.getParameter("ContNo");
	
	
	
	
	String tSerialNo[] = request.getParameterValues("InvestigateGridNo");
	String thealthcode[] = request.getParameterValues("InvestigateGrid1");
	String thealthname[] = request.getParameterValues("InvestigateGrid2");
	
	
	loggerDebug("GrpUWRReportChk","ContNo:"+tContNo);
	loggerDebug("GrpUWRReportChk","ProposalContNo:"+tProposalContNo);
	
	
	
	boolean flag = true;
	int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}
	loggerDebug("GrpUWRReportChk","count:"+ChkCount);
	if (ChkCount == 0 )
	{
		Content = "����������Ϣ¼�벻����!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpUWRReportChk","111");
	}
	else
	{
		loggerDebug("GrpUWRReportChk","222");
	    //�������һ
		    tLCGrpContSchema.setGrpContNo(tContNo);
		    tLCGrpContSchema.setProposalGrpContNo(tContNo);	    		
	    	
	    	
	    	
	    	tLCGrpContSet.add(tLCGrpContSchema);

	    	loggerDebug("GrpUWRReportChk","chkcount="+ChkCount);
	    	if (ChkCount > 0)
	    	{
	    		for (int i = 0; i < ChkCount; i++)
			{
				if (!thealthcode[i].equals(""))
				{
		  			LCRReportItemSchema tLCRReportItemSchema = new LCRReportItemSchema();
		  			tLCRReportItemSchema.setContNo(tContNo);
		  			tLCRReportItemSchema.setProposalContNo(tContNo);
					tLCRReportItemSchema.setRReportItemCode( thealthcode[i]);
					tLCRReportItemSchema.setRReportItemName( thealthname[i]);
					   	    
			    	tLCRReportItemSet.add( tLCRReportItemSchema );
			        loggerDebug("GrpUWRReportChk","i:"+i);
	    		    loggerDebug("GrpUWRReportChk","healthcode:"+thealthcode[i]);	    	
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
	
	loggerDebug("GrpUWRReportChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData();
	   
       
	   tVData.add(tGlobalInput);
	   tVData.add(tLCGrpContSet);
	   tVData.add(tLCRReportItemSet);
	  
	        GrpUWRReportUI tGrpUWRReportUI = new GrpUWRReportUI();
		if (tGrpUWRReportUI.submitData(tVData,"INSERT") == false)
		{
			int n = tGrpUWRReportUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tGrpUWRReportUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tGrpUWRReportUI.mErrors;
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
