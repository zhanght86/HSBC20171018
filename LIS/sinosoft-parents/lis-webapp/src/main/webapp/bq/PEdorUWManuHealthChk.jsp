<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PEdorUWManuHealthChk.jsp
//�����ܣ���ȫ�˹��˱��������¼��
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
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
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
	LPPENoticeSchema tLPPENoticeSchema = new LPPENoticeSchema();
	LPPENoticeItemSet tLPPENoticeItemSet = new LPPENoticeItemSet();
	
	String tContNo = request.getParameter("ContNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tInsureNo = request.getParameter("InsureNo");
	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("EDate");
	String tNote = request.getParameter("Note");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	
	String tSerialNo[] = request.getParameterValues("HealthGridNo");
	String thealthcode[] = request.getParameterValues("HealthGrid1");
	String thealthname[] = request.getParameterValues("HealthGrid2");
	String tIfCheck[] = request.getParameterValues("HealthGrid3");
	//String tChk[] = request.getParameterValues("InpHealthGridChk");
	
	System.out.println("contno:"+tContNo);
	System.out.println("hospital:"+tHospital);
	System.out.println("note:"+tNote);
	System.out.println("ifempty:"+tIfEmpty);
	System.out.println("insureno:"+tInsureNo);
	System.out.println("EDATE:"+tEDate);
	
	boolean flag = true;
	int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}
	System.out.println("count:"+ChkCount);
	if (ChkCount == 0 || tHospital.equals("") || tIfEmpty.equals(""))
	{
		Content = "����¼�벻����!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
	    //�������һ
		    tLPPENoticeSchema.setEdorNo(tEdorNo);	    
		    tLPPENoticeSchema.setContNo(tContNo);	    		
	    	tLPPENoticeSchema.setPEAddress(tHospital);
	    	tLPPENoticeSchema.setPEDate(tEDate);
	    	tLPPENoticeSchema.setPEBeforeCond(tIfEmpty);
	    	tLPPENoticeSchema.setRemark(tNote);
	    	tLPPENoticeSchema.setCustomerNo(tInsureNo);

	    	System.out.println("chkcount="+ChkCount);
	    	if (ChkCount > 0)
	    	{
	    		for (int i = 0; i < ChkCount; i++)
			{
				if (!thealthcode[i].equals("")&&!tIfCheck[i].equals(""))
				{
		  			LPPENoticeItemSchema tLPPENoticeItemSchema = new LPPENoticeItemSchema();
		  			tLPPENoticeItemSchema.setEdorNo(tEdorNo);
		  			tLPPENoticeItemSchema.setContNo(tContNo);
					tLPPENoticeItemSchema.setPEItemCode( thealthcode[i]);
					tLPPENoticeItemSchema.setPEItemName( thealthname[i]);
					tLPPENoticeItemSchema.setFreePE( tIfCheck[i]);						   		    	    
			    	tLPPENoticeItemSet.add( tLPPENoticeItemSchema );
			        System.out.println("i:"+i);
	    		    System.out.println("healthcode:"+thealthcode[i]);	    	
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
	
	System.out.println("flag:"+flag);
  	if (flag == true)
  	{
		// ׼���������� VData
	   VData tVData = new VData();
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("ContNo",tContNo);
	   tTransferData.setNameAndValue("EdorNo",tEdorNo) ;
	   tTransferData.setNameAndValue("InsuredNo",tInsureNo );
	   tTransferData.setNameAndValue("MissionID",tMissionID );
	   tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
       tTransferData.setNameAndValue("LPPENoticeSchema",tLPPENoticeSchema);
       tTransferData.setNameAndValue("LPPENoticeItemSet",tLPPENoticeItemSet);
       
	   tVData.add(tGlobalInput);
	   tVData.add(tTransferData);
		// ���ݴ���
		PEdorManuUWWorkFlowUI tPEdorManuUWWorkFlowUI   = new PEdorManuUWWorkFlowUI();
		if (tPEdorManuUWWorkFlowUI.submitData(tVData,"0000000001") == false)
		{
			int n = tPEdorManuUWWorkFlowUI.mErrors.getErrorCount();
			Content = " ��ȫ�������¼��ʧ�ܣ�ԭ����:";
			Content = Content + tPEdorManuUWWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tPEdorManuUWWorkFlowUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " ��ȫ�������¼��ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " ��ȫ�������¼��ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
