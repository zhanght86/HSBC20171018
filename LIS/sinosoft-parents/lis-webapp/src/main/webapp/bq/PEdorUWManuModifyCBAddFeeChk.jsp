<%@page contentType="text/html;charset=gb2312" %>
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
 <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
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
	LPPremSet tLPPremSet = new LPPremSet();
    TransferData tTransferData = new TransferData();
	String tPolNo = request.getParameter("PolNo"); //��ȫ��Ŀ����Եı���
	String tPolNo2 = request.getParameter("PolNo2"); //��ȫ�ӷ�����Եı���
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tAddReason = request.getParameter("AddReason");
	
	
	String tdutycode[] = request.getParameterValues("Spec2Grid1");
	String tpayplantype[] = request.getParameterValues("Spec2Grid2");
	String tstartdate[] = request.getParameterValues("Spec2Grid3");
	String tenddate[] = request.getParameterValues("Spec2Grid4");
	String trate[] = request.getParameterValues("Spec2Grid5");
	String tpayplancode[] = request.getParameterValues("Spec2Grid6");
	
	System.out.println("polno:"+tPolNo);
	System.out.println("AddReason:"+tAddReason);
	System.out.println("EdorNo:"+tEdorNo);
	
	
	int feeCount = tdutycode.length;
	if (feeCount == 0 )
	{
		Content = "��¼��ӷ���Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		if (!tPolNo.equals("")&& !tEdorNo.equals("")&& !tEdorType.equals("")&& !tMissionID.equals("")&& !tSubMissionID.equals(""))
		{
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
			  			tLPPremSchema.setEdorType(tEdorType); //��ȫ�����Ŀ�ӷ�
			  			tLPPremSchema.setDutyCode( tdutycode[i]);
			  			tLPPremSchema.setPayStartDate( tstartdate[i]);
						tLPPremSchema.setPayPlanType( tpayplantype[i]);
						tLPPremSchema.setPayEndDate( tenddate[i]);
				    	tLPPremSchema.setPrem( trate[i]);
				    	tLPPremSchema.setPayPlanCode(tpayplancode[i]);
	    			    tLPPremSet.add( tLPPremSchema );
	    			    flag = true;
	    			    
	    			 	System.out.println("i:"+i);
	    			    System.out.println("���α���"+i+":  "+tdutycode[i]);
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
			tTransferData.setNameAndValue("PolNo",tPolNo);
			tTransferData.setNameAndValue("PolNo2",tPolNo2);
			tTransferData.setNameAndValue("EdorNo",tEdorNo) ;
			tTransferData.setNameAndValue("EdorType",tEdorType) ;
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("AddReason",tAddReason);
			tTransferData.setNameAndValue("LPPremSet",tLPPremSet);	
			
		} // End of if
		else
		{
			Content = "��������ʧ��!";
			flag = false;
		}
	}
	
	System.out.println("flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tG );
		tVData.add( tTransferData);				
		// ���ݴ���
		PEdorUWModifyCBAddFeeBL tPEdorUWModifyCBAddFeeBL   = new PEdorUWModifyCBAddFeeBL();
		if (!tPEdorUWModifyCBAddFeeBL.submitData(tVData,"UPDATE")) //ִ�б�ȫ�˱���Լ�������ڵ�0000000003
		{
			int n = tPEdorUWModifyCBAddFeeBL.mErrors.getErrorCount();
			Content = " �����˱�ʧ�ܣ�ԭ����: " + tPEdorUWModifyCBAddFeeBL.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tPEdorUWModifyCBAddFeeBL.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����˱��ɹ�! ";
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
