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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
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
  	// �����б�
	LCPremSet tLCPremSet = new LCPremSet();
//    TransferData tTransferData = new TransferData();
    VData tInputData = new VData();
	String tPolNo = request.getParameter("PolNo"); //��ȫ��Ŀ����Եı���
	String tPolNo2 = request.getParameter("PolNo2"); //��ȫ�ӷ�����Եı���
	String tContNo = request.getParameter("ContNo");
	String tAddReason = request.getParameter("AddReason");
	
	String tdutycode[] = request.getParameterValues("SpecGrid1");
	String tpayplantype[] = request.getParameterValues("SpecGrid2");
	String tstartdate[] = request.getParameterValues("SpecGrid3");
	String tenddate[] = request.getParameterValues("SpecGrid4");

	String tsuppriskscore[] = request.getParameterValues("SpecGrid5");
	String tSecondScore[] = request.getParameterValues("SpecGrid6");
	String AddObj[] = request.getParameterValues("SpecGrid7");
	String trate[] = request.getParameterValues("SpecGrid8");
	
	loggerDebug("UWManuAddChk","polno:"+tPolNo);
	loggerDebug("UWManuAddChk","AddReason:"+tAddReason);
	loggerDebug("UWManuAddChk","ContNo:"+tContNo);
	
	
	int feeCount = tdutycode.length;
	if (feeCount == 0 )
	{
		Content = "��¼��ӷ���Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		if (!tPolNo.equals(""))
		{
	    	//׼����Լ�ӷ���Ϣ
	    	if (feeCount > 0)
	    	 {
	    	  for (int i = 0; i < feeCount; i++)
				  {
					  if (!tdutycode[i].equals("")&&!tpayplantype[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
					  {
			  	  	LCPremSchema tLCPremSchema = new LCPremSchema();
			  	  	tLCPremSchema.setPolNo(tPolNo2);
			  	  	tLCPremSchema.setDutyCode( tdutycode[i]);
			  	  	tLCPremSchema.setPayStartDate( tstartdate[i]);
					  	tLCPremSchema.setPayPlanType( tpayplantype[i]);
					  	tLCPremSchema.setPayEndDate( tenddate[i]);
					  	tLCPremSchema.setAddFeeDirect(AddObj[i]);
					  	tLCPremSchema.setSecInsuAddPoint(tSecondScore[i]);
				      tLCPremSchema.setPrem( trate[i]);
				      tLCPremSchema.setSuppRiskScore( tsuppriskscore[i]);
	    		  	tLCPremSet.add( tLCPremSchema );
	    		  	flag = true;
	    		  	    
	    		  	loggerDebug("UWManuAddChk","i:"+i);
	    		  	loggerDebug("UWManuAddChk","���α���"+i+":  "+tdutycode[i]);
	    		  } // End of if
	    		  else
	    		  {
	    		  	Content = "�ӷ���Ϣδ��д����,��ȷ��!";
	    		  	flag = false;	
	    		  	FlagStr = "Fail";   
	    		  	break; 			 
	    		  }
				  } // End of for				    
			  } // End of if
			
		    //׼������������Ϣ
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema.setContNo(tContNo);
			tLCPolSchema.setPolNo(tPolNo);

			tInputData.add(tG);
			tInputData.add(tLCPolSchema);
			tInputData.add(tAddReason);
			tInputData.add(tLCPremSet);
/*		tTransferData.setNameAndValue("PolNo",tPolNo);
			tTransferData.setNameAndValue("PolNo2",tPolNo2);
			tTransferData.setNameAndValue("PrtNo",tContNo) ;
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("AddReason",tAddReason);
			tTransferData.setNameAndValue("LCPremSet",tLCPremSet);*/	
			
		} // End of if
		else
		{
			Content = "��������ʧ��!";
			flag = false;
		}
	}
	
	loggerDebug("UWManuAddChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
//		VData tVData = new VData();
//		tVData.add( tG );
//		tVData.add( tTransferData);				
		// ���ݴ���
		//UWManuAddChkUI tUWManuAddChkUI = new UWManuAddChkUI();
		String busiName="cbcheckUWManuAddChkUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tInputData,"",busiName))
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			Content = " �˹��˱��ӷ�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
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
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
