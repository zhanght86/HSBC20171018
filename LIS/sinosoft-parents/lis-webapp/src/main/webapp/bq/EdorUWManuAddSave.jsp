<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorUWManuSpecSave.jsp
//�����ܣ��˹��˱��ӷ�
//�������ڣ�2005-07-16 11:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
    //�������
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
    boolean flag = true;
	GlobalInput tG = new GlobalInput();  
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) 
  	{
		out.println("session has expired");
		return;
	}
  
	//������Ϣ
  	// �����б�
	LPPremSet tLPPremSet = new LPPremSet();
    VData tInputData = new VData();
	String tPolNo = request.getParameter("PolNo"); //��ȫ��Ŀ����Եı���
	String tPolNo2 = request.getParameter("PolNo2"); //��ȫ�ӷ�����Եı���
	String tContNo = request.getParameter("ContNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	String tAddReason = request.getParameter("AddReason");
	String sNoAddPrem = request.getParameter("NoAddPrem");
	int feeCount = 0;
	if (sNoAddPrem != null && sNoAddPrem.equals("Y"))  //û�мӷ�
	{
		feeCount = 0;
	}
	else
	{
		String tdutycode[] = request.getParameterValues("SpecGrid1");
		String tpayplantype[] = request.getParameterValues("SpecGrid2");
		String tstartdate[] = request.getParameterValues("SpecGrid9");
		String tenddate[] = request.getParameterValues("SpecGrid11");
		String tpaytodate[] = request.getParameterValues("SpecGrid10");
		
		String tsuppriskscore[] = request.getParameterValues("SpecGrid4");
		String tSecondScore[] = request.getParameterValues("SpecGrid5");
		String AddObj[] = request.getParameterValues("SpecGrid6");
		String trate[] = request.getParameterValues("SpecGrid7");
		feeCount = tdutycode.length;
	    	//׼����Լ�ӷ���Ϣ
	    	if (feeCount > 0)
	    	{
	        	for (int i = 0; i < feeCount; i++)
				{
				    if (!tdutycode[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals(""))
					{
	    		  	  	LPPremSchema tLPPremSchema = new LPPremSchema();
			  	  	    tLPPremSchema.setPolNo(tPolNo2);
			  	  	    tLPPremSchema.setEdorNo(tEdorNo);
    			  	  	tLPPremSchema.setEdorType(tEdorType);
	    		  	  	tLPPremSchema.setDutyCode( tdutycode[i]);
		    	  	  	tLPPremSchema.setPayStartDate( tstartdate[i]);
		    	  	  	tLPPremSchema.setPaytoDate(tpaytodate[i]);
					  	tLPPremSchema.setPayPlanType( tpayplantype[i]);
					  	tLPPremSchema.setPayEndDate( tenddate[i]);
					  	
					  	tLPPremSchema.setAddFeeDirect(AddObj[i]);
					  	tLPPremSchema.setSecInsuAddPoint(tSecondScore[i]);
			            tLPPremSchema.setPrem( trate[i]);
				        tLPPremSchema.setSuppRiskScore( tsuppriskscore[i]);
    	    		  	tLPPremSet.add( tLPPremSchema );
	        		  	flag = true;
	    		  	    
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
	}

		if (!tPolNo.equals(""))
		{
		    //׼������������Ϣ
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLPPolSchema.setContNo(tContNo);
			tLPPolSchema.setEdorType(tEdorType);
			tLPPolSchema.setEdorNo(tEdorNo);
			tLPPolSchema.setPolNo(tPolNo);

			tInputData.add(tG);
			tInputData.add(tLPPolSchema);
			tInputData.add(tAddReason);
			tInputData.add(tLPPremSet);
			
		} // End of if
		else
		{
			Content = "��������ʧ��!";
			flag = false;
		}
//	}
	loggerDebug("EdorUWManuAddSave","-------------------------------------------" + flag);
try
{
  	if (flag == true)
  	{
		// ���ݴ���
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//EdorUWManuAddUI tEdorUWManuAddUI = new EdorUWManuAddUI();
		//if (!tEdorUWManuAddUI.submitData(tInputData,""))
	    if(!tBusinessDelegate.submitData(tInputData, "", "EdorUWManuAddUI"))
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
