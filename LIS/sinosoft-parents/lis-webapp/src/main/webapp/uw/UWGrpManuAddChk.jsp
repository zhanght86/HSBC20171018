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
    
    LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
    
    		
    
	String tPolNo = request.getParameter("PolNo"); //��ȫ��Ŀ����Եı���
	String tPolNo2 = request.getParameter("PolNo2"); //��ȫ�ӷ�����Եı���
	String tContNo = request.getParameter("ContNo");
	String tGrpContNo = request.getParameter("GrpContNo");
	String tAddReason = request.getParameter("AddReason");
	
	String tdutycode[] = request.getParameterValues("SpecGrid1");
	String tpayplantype[] = request.getParameterValues("SpecGrid2");
	String tstartdate[] = request.getParameterValues("SpecGrid3");
	String tenddate[] = request.getParameterValues("SpecGrid4");
	String trate[] = request.getParameterValues("SpecGrid5");
	String tsuppriskscore[] = request.getParameterValues("SpecGrid6");
	
	String tdutycodepol[] = request.getParameterValues("PolAddGrid1");
	String tGrpPolNopol[] = request.getParameterValues("PolAddGrid2");

	
	loggerDebug("UWGrpManuAddChk","polno:"+tPolNo);
	loggerDebug("UWGrpManuAddChk","AddReason:"+tAddReason);
	loggerDebug("UWGrpManuAddChk","ContNo:"+tContNo);
	loggerDebug("UWGrpManuAddChk","GrpContNo:"+tGrpContNo);
	
	
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
					if (!tdutycode[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
					{
			  			LCPremSchema tLCPremSchema = new LCPremSchema();
			  			tLCPremSchema.setPolNo(tPolNo2);
			  			tLCPremSchema.setDutyCode( tdutycode[i]);
			  			tLCPremSchema.setPayStartDate( tstartdate[i]);
						tLCPremSchema.setPayPlanType("03");
						tLCPremSchema.setPayEndDate( tenddate[i]);
				    	tLCPremSchema.setPrem( trate[i]);
				    	//tLCPremSchema.setSuppRiskScore( tsuppriskscore[i]);
	    			    tLCPremSet.add( tLCPremSchema );

	    			    flag = true;
	    			    
	    			 	loggerDebug("UWGrpManuAddChk","i:"+i);
	    			    loggerDebug("UWGrpManuAddChk","���α���"+i+":  "+tdutycode[i]);
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
		
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
 			 //tLCGrpPolSchema.setGrpContNo(tContNo); 
 			   	loggerDebug("UWGrpManuAddChk","@@@@@@@@@@@@@@@@@@@");
 			   	
			tInputData.add(tG);
			tInputData.add(tLCPolSchema);
			tInputData.add(tAddReason);
			tInputData.add(tLCPremSet);
			tInputData.add(tLCGrpPolSchema);
			
/*			tTransferData.setNameAndValue("PolNo",tPolNo);
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
	
	loggerDebug("UWGrpManuAddChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
//		VData tVData = new VData();
//		tVData.add( tG );
//		tVData.add( tTransferData);				
		// ���ݴ���
		UWGrpManuAddChkUI tUWGrpManuAddChkUI = new UWGrpManuAddChkUI();
		if (!tUWGrpManuAddChkUI.submitData(tInputData,""))
		{
			int n = tUWGrpManuAddChkUI.mErrors.getErrorCount();
			Content = " �˹��˱��ӷ�ʧ�ܣ�ԭ����: " + tUWGrpManuAddChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWGrpManuAddChkUI.mErrors;
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
