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
  <%@page import="java.util.Hashtable"%>
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
  loggerDebug("LLUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//������Ϣ
  	// �����б�
	LLUWPremMasterSet tLLUWPremMasterSet = new LLUWPremMasterSet();
	LCPolSet tLCPolSet = new LCPolSet();
    TransferData tTransferData = new TransferData();
    VData tInputData = new VData();
	Hashtable PolHashtable = new Hashtable();
	String tContNo = request.getParameter("ContNo");
	String tAddReason = request.getParameter("AddReason");
	String tUpReporFlag = request.getParameter("UpReporFlag");
	String tClmNo = request.getParameter("ClmNo");
	String tBatNo = request.getParameter("BatNo");
	//
	String[] tPolNo = request.getParameterValues("PolAddGrid12");
	 loggerDebug("LLUWManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@111111111");
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
	
	String taddForm[] = request.getParameterValues("PolAddGrid15");
	
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
			String tCurrPolNo = tPolNo[i];
			
			//String tCurrDuty = tdutycode[i];
			if (!tdutycode[i].equals("")&&!tpayplantype[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
			{
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema.setPolNo(tCurrPolNo);
				tLCPolSchema.setContNo(tContNo);
				if(i==0)
				{
					PolHashtable.put(tCurrPolNo,tCurrPolNo);
					tLCPolSet.add(tLCPolSchema);
				}
				else if(!PolHashtable.containsKey(tCurrPolNo))
				{
					PolHashtable.put(tCurrPolNo,tCurrPolNo);
					tLCPolSet.add(tLCPolSchema);
				}
					
				
				
				LLUWPremMasterSchema tLLUWPremMasterSchema = new LLUWPremMasterSchema();
				tLLUWPremMasterSchema.setPolNo(tPolNo[i]);
				tLLUWPremMasterSchema.setDutyCode( tdutycode[i]);
				tLLUWPremMasterSchema.setPayStartDate( tstartdate[i]);
				tLLUWPremMasterSchema.setClmNo(tClmNo);
				tLLUWPremMasterSchema.setBatNo(tBatNo);
				tLLUWPremMasterSchema.setPayPlanType( tpayplantype[i]);
				tLLUWPremMasterSchema.setPayEndDate( tenddate[i]);
				tLLUWPremMasterSchema.setAddFeeDirect(tAddFeeDirect[i]); //�ӷѷ�ʽ
				//tLCPremSchema.setSecInsuAddPoint(tSecondScore[i]);
				tLLUWPremMasterSchema.setPrem(trate[i]);
				tLLUWPremMasterSchema.setSuppRiskScore( tsuppriskscore[i]);
				tLLUWPremMasterSchema.setAddForm(taddForm[i]);
			    tLLUWPremMasterSet.add( tLLUWPremMasterSchema);
    		  	flag = true;
    		  	    
    		  	loggerDebug("LLUWManuChangeRiskAddChk","i:"+i);
    		  	loggerDebug("LLUWManuChangeRiskAddChk","���α���"+i+":  "+tdutycode[i]);
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
		tTransferData.setNameAndValue("ClmNo",tClmNo);
		tTransferData.setNameAndValue("BatNo",tBatNo);
		tInputData.add(tG);
		tInputData.add(tAddReason);
		tInputData.add(tUpReporFlag); //�����ж��Ƿ���Ҫ�ٱ���־
		tInputData.add(tLCPolSet);
		tInputData.add(tLLUWPremMasterSet);
		tInputData.add(tTransferData);
	}
	
	loggerDebug("LLUWManuChangeRiskAddChk","flag:"+flag);
try
{
  	if (flag == true)
  	{		
		// ���ݴ���
		/*LLUWManuAddChkUI tLLUWManuAddChkUI = new LLUWManuAddChkUI();
		if (!tLLUWManuAddChkUI.submitData(tInputData,""))
		{
			int n = tLLUWManuAddChkUI.mErrors.getErrorCount();
			Content = " �˹��˱��ӷ�ʧ�ܣ�ԭ����: " + tLLUWManuAddChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tLLUWManuAddChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �˹��˱��ӷѳɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}*/
		String busiName="LLUWManuAddChkUI";
		  String mDescType="�˹��˱��ӷ�";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tInputData,"",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = mDescType+"ʧ��";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"�ɹ�! ";
			      	FlagStr = "Succ";  
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
