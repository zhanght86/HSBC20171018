<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWGSpecChk.jsp
//�����ܣ������˹��˱��ӷ�¼��
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
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  GlobalInput tG = new GlobalInput();
  
  tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null)
   {
	out.println("session has expired");
	return;
   }
 
  	//������Ϣ
  	// Ͷ�����б�
	LCPolSet tLCPolSet = new LCPolSet();
	LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
	LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
	LCSpecSet tLCSpecSet = new LCSpecSet();
	LCPremSet tLCPremSet = new LCPremSet();

	String tProposalNo = request.getParameter("ProposalNoHide");
	String tUWFlag = request.getParameter("Flag");
	String tUWIdea = request.getParameter("UWIdea");
	String tspec = request.getParameter("Remark");
	String treason = request.getParameter("Reason");
	String tSpecReason = request.getParameter("SpecReason");
	
	String tdutycode[] = request.getParameterValues("SpecGrid1");
	String tpayplantype[] = request.getParameterValues("SpecGrid2");
	String tstartdate[] = request.getParameterValues("SpecGrid3");
	String tenddate[] = request.getParameterValues("SpecGrid4");
	String trate[] = request.getParameterValues("SpecGrid5");
	//String tpayplancode = request.getParameterValues("SpecGrid6");
	
	
	loggerDebug("UWGSpecChk","polno:"+tProposalNo);
	loggerDebug("UWGSpecChk","remark:"+tspec);
	loggerDebug("UWGSpecChk","flag:"+tUWFlag);
	
	boolean flag = true;
	int feeCount = tdutycode.length;
	if (feeCount == 0 && tspec.equals(""))
	{
		Content = "��¼��б�����!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("UWGSpecChk","111");
	}
	else
	{
		loggerDebug("UWGSpecChk","222");
		if (!tProposalNo.equals("")&& !tUWFlag.equals(""))
		{

 			LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
	
	    	tLCGUWMasterSchema.setGrpPolNo(tProposalNo);
	    	tLCGUWMasterSchema.setPassFlag(tUWFlag);
	    	if(tUWFlag.equals("1") == true)
	    	{	    		
	    		tLCGUWMasterSchema.setSpecReason(tSpecReason);
	    	}
	    	if(tUWFlag.equals("2") == true)
	    	{
	    		tLCGUWMasterSchema.setSpecReason(treason);
	    	}
	    
	    	tLCGUWMasterSet.add(tLCGUWMasterSchema);
	    		
	    	loggerDebug("UWGSpecChk","feecount="+feeCount);
	    	if (feeCount > 0)
	    	{
	    	  for (int i = 0; i < feeCount; i++)
			  {
				if (!tdutycode[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
					{
			  			LCPremSchema tLCPremSchema = new LCPremSchema();
	
						tLCPremSchema.setDutyCode( tdutycode[i]);
						tLCPremSchema.setPayStartDate( tstartdate[i]);
						tLCPremSchema.setPayPlanType( tpayplantype[i]);
						tLCPremSchema.setPayEndDate( tenddate[i]);
				    	tLCPremSchema.setPrem( trate[i]);
	    			    
	    			 	loggerDebug("UWGSpecChk","i:"+i);
	    			    loggerDebug("UWGSpecChk","dutycode:"+tdutycode[i]);
	    			    
				    	tLCPremSet.add( tLCPremSchema );
				    
				   		flag = true;
					}
				}
				    
			}
		}
		else
		{
			Content = "��������ʧ��!";
			flag = false;
		}
	}
	
	loggerDebug("UWGSpecChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();		
		tVData.add( tLCGUWMasterSet);
		tVData.add( tLCPremSet);		
		tVData.add( tG );
		
		// ���ݴ���
		UWGSpecChkUI tUWGSpecChkUI   = new UWGSpecChkUI();
		if (tUWGSpecChkUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWGSpecChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �����˱�ʧ�ܣ�ԭ����: " + tUWGSpecChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWGSpecChkUI.mErrors;
		    //tErrors = tUWGSpecChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �ӷ�¼��ɹ�! ";
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
