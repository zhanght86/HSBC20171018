<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuHealthChk.jsp
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
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  loggerDebug("UWManuHealthChk","start..................................");

  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }

  loggerDebug("UWManuHealthChk","step1..................................");
  	// Ͷ�����б�
	LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
	LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();

	String tContNo = request.getParameter("ContNo");
	String tInsureNo = request.getParameter("InsureNo");
	String tHospital = request.getParameter("Hospital");
	String tPEReason = request.getParameter("PEReason");
	String tPrtNo =  request.getParameter("PrtNo");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("EDate");
	String tNote = request.getParameter("Note");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tFlag = request.getParameter("Flag");     
        loggerDebug("UWManuHealthChk","step2..................................");

	//String tSerialNo[] = request.getParameterValues("HealthGridNo");
	//String thealthcode[] = request.getParameterValues("HealthGrid1");
	//String thealthname[] = request.getParameterValues("HealthGrid2");
        String  tbodyCheck = request.getParameter("bodyCheck");
	String[] tBodyCheckItems = request.getParameterValues(tbodyCheck);
        String[] thealthcode = new String[20];
        String[] thealthname = new String[20];
        loggerDebug("UWManuHealthChk","size:"+tBodyCheckItems.length);
        loggerDebug("UWManuHealthChk","step3..................................");
        if(tBodyCheckItems.length != 0)
        {
        	for(int i = 0;i<tBodyCheckItems.length;i++)
                {
                	thealthcode[i] = tBodyCheckItems[i].substring(0,3);
                        thealthname[i] = tBodyCheckItems[i].substring(3);
                }
        }



	loggerDebug("UWManuHealthChk","ContNo:"+tContNo);
	loggerDebug("UWManuHealthChk","hospital:"+tHospital);
	loggerDebug("UWManuHealthChk","note:"+tNote);
	loggerDebug("UWManuHealthChk","ifempty:"+tIfEmpty);
	loggerDebug("UWManuHealthChk","insureno:"+tInsureNo);
	loggerDebug("UWManuHealthChk","EDATE:"+tEDate);
	loggerDebug("UWManuHealthChk","Flag:"+tFlag);
	boolean flag = true;
	int ChkCount = tBodyCheckItems.length;
        /*
        if(tSerialNo != null)
	{
		ChkCount = tSerialNo.length;
	}
        //*/
	loggerDebug("UWManuHealthChk","count:"+ChkCount);
	if (ChkCount == 0 || tHospital.equals("") || tIfEmpty.equals(""))
	{
		Content = "���������Ϣ¼�벻����!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("UWManuHealthChk","111");
	}
	else
	{
		loggerDebug("UWManuHealthChk","222");
	    //�������һ
                tLCPENoticeSchema.setContNo(tContNo);
	    	tLCPENoticeSchema.setPEAddress(tHospital);
	    	tLCPENoticeSchema.setPEDate(tEDate);
	    	tLCPENoticeSchema.setPEBeforeCond(tIfEmpty);
	    	tLCPENoticeSchema.setRemark(tNote);
	    	tLCPENoticeSchema.setCustomerNo(tInsureNo);
	    	tLCPENoticeSchema.setPEReason(tPEReason);

	    	loggerDebug("UWManuHealthChk","chkcount="+ChkCount);
	    	if (ChkCount > 0)
	    	{
	    		for (int i = 0; i < ChkCount; i++)
			{
				if (!thealthcode[i].equals(""))
				{
		  			LCPENoticeItemSchema tLCPENoticeItemSchema = new LCPENoticeItemSchema();
		  			tLCPENoticeItemSchema.setContNo(tContNo);
					  tLCPENoticeItemSchema.setPEItemCode( thealthcode[i]);
					  tLCPENoticeItemSchema.setPEItemName( thealthname[i]);
					  tLCPENoticeItemSchema.setFreePE("N");
			    	tLCPENoticeItemSet.add( tLCPENoticeItemSchema );
			      loggerDebug("UWManuHealthChk","i:"+i);
	    		  loggerDebug("UWManuHealthChk","healthcode:"+thealthcode[i]);
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

	loggerDebug("UWManuHealthChk","flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData();
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("ContNo",tContNo);
	   tTransferData.setNameAndValue("PrtNo",tPrtNo);
	   tTransferData.setNameAndValue("CustomerNo",tInsureNo );
	   tTransferData.setNameAndValue("MissionID",tMissionID );
	   tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
       tTransferData.setNameAndValue("LCPENoticeSchema",tLCPENoticeSchema);
       tTransferData.setNameAndValue("LCPENoticeItemSet",tLCPENoticeItemSet);
       tTransferData.setNameAndValue("Flag",tFlag);

	   tVData.add(tGlobalInput);
	   tVData.add(tTransferData);
		// ���ݴ���
		
		String busiName="tbTbWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   
		//TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
		if (tBusinessDelegate.submitData(tVData,"0000001101",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			Content = " �б��������¼��ʧ�ܣ�ԭ����:";
			Content = Content + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {
		    	Content = " �б��������¼��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else
		    {
		    	Content = " �б��������¼��ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
