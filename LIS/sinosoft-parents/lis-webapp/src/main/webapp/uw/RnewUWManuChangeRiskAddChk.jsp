<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpecChk.jsp
//�����ܣ��˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.util.Hashtable"%>
  
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
  loggerDebug("RnewUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//������Ϣ
  	// �����б�
	LCPremSet tLCPremSet = new LCPremSet();
	LCPolSet tLCPolSet = new LCPolSet();
    TransferData tTransferData = new TransferData();
    VData tInputData = new VData();
	Hashtable PolHashtable = new Hashtable();
	String tContNo = request.getParameter("ContNo");
	String tAddReason = request.getParameter("AddReason");
	String tUpReporFlag = request.getParameter("UpReporFlag");
	//���ֱ���
	String[] tRiskCode = request.getParameterValues("PolAddGrid1");
	//���ֺ�
	String[] tPolNo = request.getParameterValues("PolAddGrid12");
	//���α���
	String tdutycode[] = request.getParameterValues("PolAddGrid3");
	//�ӷ�����
	String tpayplantype[] = request.getParameterValues("PolAddGrid4");
	//�ɷ�����
	String tstartdate[] = request.getParameterValues("PolAddGrid9");
	//�ɷ�ֹ��
	String tenddate[] = request.getParameterValues("PolAddGrid10");
	//�ӷѷ�ʽ
	String tAddFeeDirect[] = request.getParameterValues("PolAddGrid5");
	//�ӷ�����
	String tsuppriskscore[] = request.getParameterValues("PolAddGrid6");
	//
	//String tSecondScore[] = request.getParameterValues("SpecGrid6");
	//String AddObj[] = request.getParameterValues("SpecGrid7");
	//�ӷѽ��
	String trate[] = request.getParameterValues("PolAddGrid7");
	
	int feeCount = tPolNo.length;
	if (feeCount == 0 )
	{
		Content = "��¼��ӷ���Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		//ѭ������ÿ�����ֵļӷ�����
		for(int i=0;i<tPolNo.length;i++)
		{
			String tCurrRiskCode = tRiskCode[i];
			String tCurrPolNo = tPolNo[i];
			loggerDebug("RnewUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@riskcode="+tRiskCode[i]);
			loggerDebug("RnewUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@polno="+tPolNo[i]);
			loggerDebug("RnewUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@dutycode="+tdutycode[i]);
			loggerDebug("RnewUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@payplantype="+tpayplantype[i]);
			loggerDebug("RnewUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@startdate="+tstartdate[i]);
			loggerDebug("RnewUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@enddate="+tenddate[i]);
			loggerDebug("RnewUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@trate="+trate[i]);
			//String tCurrDuty = tdutycode[i];
			if (!tdutycode[i].equals("")&&!tpayplantype[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
			{
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSchema tLCPolSchema = new LCPolSchema();
				
				tLCPolDB.setRiskCode(tCurrRiskCode);
				tLCPolDB.setContNo(tContNo);
				tLCPolDB.setAppFlag("9");
				if(tLCPolDB.query().size()==0)
				{
					Content = "��ѯ���ֺţ�"+tCurrPolNo+"��Ͷ������Ϣʧ�ܣ�";
	    		  	flag = false;	
	    		  	FlagStr = "Fail";   
	    		  	break; 			
				}
				tLCPolSchema=tLCPolDB.query().get(1);
				if(i==0)
				{
					PolHashtable.put(tCurrPolNo,tCurrPolNo);
					tLCPolSet.add(tLCPolSchema);
				}
				else if(!PolHashtable.containsKey(tCurrPolNo))
				{
					tLCPolSet.add(tLCPolSchema);
				}
					
				
				loggerDebug("RnewUWManuChangeRiskAddChk","222222222222");
				LCPremSchema tLCPremSchema = new LCPremSchema();
		  	  	tLCPremSchema.setPolNo(tLCPolSchema.getPolNo());
		  	  	tLCPremSchema.setDutyCode( tdutycode[i]);
		  	  	tLCPremSchema.setPayStartDate( tstartdate[i]);
				tLCPremSchema.setPayPlanType( tpayplantype[i]);
				tLCPremSchema.setPayEndDate( tenddate[i]);
				tLCPremSchema.setAddFeeDirect(tAddFeeDirect[i]); //�ӷѷ�ʽ
				//tLCPremSchema.setSecInsuAddPoint(tSecondScore[i]);
			    tLCPremSchema.setPrem(trate[i]);
			    tLCPremSchema.setSuppRiskScore( tsuppriskscore[i]);
    		  	tLCPremSet.add( tLCPremSchema);
    		  	flag = true;
    		  	    
    		  	loggerDebug("RnewUWManuChangeRiskAddChk","i:"+i);
    		  	loggerDebug("RnewUWManuChangeRiskAddChk","���α���"+i+":  "+tdutycode[i]);
    		  } // End of if
    		  else
    		  {
    		  	Content = "�ӷ���Ϣδ��д����,��ȷ��!";
    		  	flag = false;	
    		  	FlagStr = "Fail";   
    		  	break; 			 
    		  }
			}
		
		tTransferData.setNameAndValue("ContNo",tContNo);
		tInputData.add(tG);
		tInputData.add(tAddReason);
		tInputData.add(tUpReporFlag); //�����ж��Ƿ���Ҫ�ٱ���־
		tInputData.add(tLCPolSet);
		tInputData.add(tLCPremSet);
		tInputData.add(tTransferData);
	}
	
	loggerDebug("RnewUWManuChangeRiskAddChk","flag:"+flag);
try
{
  	if (flag == true)
  	{		
		// ���ݴ���
		RnewUWManuAddChkUI tRnewUWManuAddChkUI = new RnewUWManuAddChkUI();
		if (!tRnewUWManuAddChkUI.submitData(tInputData,""))
		{
			int n = tRnewUWManuAddChkUI.mErrors.getErrorCount();
			Content = " �˹��˱��ӷ�ʧ�ܣ�ԭ����: " + tRnewUWManuAddChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tRnewUWManuAddChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �˹��˱��ӷѳɹ�! ";
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
