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
  loggerDebug("BQManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//������Ϣ
  	// �����б�
	LPPremSet tLPPremSet = new LPPremSet();
	LPPolSet tLPPolSet = new LPPolSet();
    TransferData tTransferData = new TransferData();
    VData tInputData = new VData();
	Hashtable PolHashtable = new Hashtable();
	String tContNo = request.getParameter("ContNo");
	String tAddReason = request.getParameter("AddReason");
	String tUpReporFlag = request.getParameter("UpReporFlag");
	
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	//
	String tRadio[] = request.getParameterValues("InpPolAddGridSel");  
	
	String[] tPolNo = request.getParameterValues("PolAddGrid12");
	 loggerDebug("BQManuChangeRiskAddChk","@@@@@@@@@@@@@@@@@@@@@@@@@@@111111111");
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
	//�ɷѼƻ�����
	String tpayPlanCode[] = request.getParameterValues("PolAddGrid14");
	//�ӷѿ�ʼʱ������ 0-׷��  1-����  2-����
	String taddForm[] = request.getParameterValues("PolAddGrid15");
	//
	//String tSecondScore[] = request.getParameterValues("SpecGrid6");
	//String AddObj[] = request.getParameterValues("SpecGrid7");
	//�ӷѽ��
	String trate[] = request.getParameterValues("PolAddGrid7");
  
	int feeCount = tRadio.length;
	if (feeCount == 0 )
	{
		Content = "��¼��ӷ���Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		//ѭ������ÿ�����ֵļӷ�����
		for(int i=0;i<feeCount;i++)
		{ 
			loggerDebug("BQManuChangeRiskAddChk","�Ƿ�ѡ��"+i+":  "+tRadio[i]);
			if(tRadio[i].equals("1")) 
			{
				String tCurrPolNo = tPolNo[i];
				
				//String tCurrDuty = tdutycode[i];
				if (!tdutycode[i].equals("")&&!tpayplantype[i].equals("")&& !tstartdate[i].equals("")&&!tenddate[i].equals("")&&!trate[i].equals("")&&!taddForm[i].equals(""))
				{
					LPPolSchema tLPPolSchema = new LPPolSchema();
					tLPPolSchema.setPolNo(tCurrPolNo);
					tLPPolSchema.setContNo(tContNo);
					tLPPolSchema.setEdorNo(tEdorNo);
					tLPPolSchema.setEdorType(tEdorType);
					if(i==0)
					{
						PolHashtable.put(tCurrPolNo,tCurrPolNo);
						tLPPolSet.add(tLPPolSchema);
					}
					else if(!PolHashtable.containsKey(tCurrPolNo))
					{
						tLPPolSet.add(tLPPolSchema);
					}			
					
					LPPremSchema tLPPremSchema = new LPPremSchema();
			  	  	tLPPremSchema.setPolNo(tPolNo[i]);
			  	  	tLPPremSchema.setDutyCode( tdutycode[i]);
			  	  	tLPPremSchema.setPayStartDate( tstartdate[i]);
					tLPPremSchema.setPayPlanType( tpayplantype[i]);
					tLPPremSchema.setPayEndDate( tenddate[i]);
					tLPPremSchema.setAddFeeDirect(tAddFeeDirect[i]); //�ӷѷ�ʽ
					tLPPremSchema.setAddForm(taddForm[i]); //�ӷѿ�ʼʱ������
				    tLPPremSchema.setPrem(trate[i]);
				    tLPPremSchema.setSuppRiskScore( tsuppriskscore[i]);
				    tLPPremSchema.setPayPlanCode( tpayPlanCode[i]);
				    tLPPremSchema.setEdorNo(tEdorNo);
				    tLPPremSchema.setEdorType(tEdorType);
				    
				    //tLPPremSchema.setAddFeeDirect(taddForm[i]); //��¼�ӷ���ʽ
	    		  	tLPPremSet.add(tLPPremSchema);
	    		  	flag = true;
	    		  	    
	    		  	loggerDebug("BQManuChangeRiskAddChk","i:"+i);
	    		  	loggerDebug("BQManuChangeRiskAddChk","���α���"+i+":  "+tdutycode[i]);
	    	  } // End of if
	    	  else
	    	  {
	    		  	Content = "�ӷ���Ϣδ��д����,��ȷ��!";
	    		  	flag = false;	
	    		  	FlagStr = "Fail";   
	    		  	break; 			 
	    	  }
					//if(taddForm[i].equals("0")){
					//}
			  break;
			}
			
		}
		
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("EdorType",tEdorType);
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tInputData.add(tG);
		tInputData.add(tAddReason);
		tInputData.add(tUpReporFlag); //�����ж��Ƿ���Ҫ�ٱ���־
		tInputData.add(tLPPolSet);
		tInputData.add(tLPPremSet);
		tInputData.add(tTransferData);
	}
	
	loggerDebug("BQManuChangeRiskAddChk","flag:"+flag);
try
{
  	if (flag == true)
  	{		
  		 String busiName="BQManuAddCHKUI";//TODO hq
  		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		// ���ݴ���
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
