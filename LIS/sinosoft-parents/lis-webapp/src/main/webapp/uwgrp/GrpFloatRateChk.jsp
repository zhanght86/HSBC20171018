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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
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
	

    VData tInputData = new VData();
    LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	
	
	String tGrpPolNo[] = request.getParameterValues("ContGrid1");
	String tFloatRate[] = request.getParameterValues("ContGrid6");
	
	
	
	int feeCount = tGrpPolNo.length;

	
	    	//׼����Լ�ӷ���Ϣ
	    	if (feeCount > 0)
	    	 {
	    	  for (int i = 0; i < feeCount; i++)
				{
					if (!tGrpPolNo[i].equals("")&& !(tFloatRate[i].equals("")))
					{
			  			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			  			tLCGrpPolSchema.setGrpPolNo(tGrpPolNo[i]);
			  			loggerDebug("GrpFloatRateChk","tFloatRate[i]:"+tFloatRate[i]);
			  			tLCGrpPolSchema.setStandbyFlag1(tFloatRate[i]);
			  		
	    			    tLCGrpPolSet.add( tLCGrpPolSchema );
	    			    flag = true;
	    			    
	    			 	loggerDebug("GrpFloatRateChk","i:"+i);
	    			   
	    			} // End of if
	    			else
	    			{
	    			 Content = "��Ϣδ��д����,��ȷ��!";
	    			 flag = false;	
	    			 FlagStr = "Fail";   
	    			 break; 			 
	    			}
				} // End of for				    
			} // End of if
			
					
	
		else
		{
			Content = "��������ʧ��!";
			flag = false;
		}
	
	
	loggerDebug("GrpFloatRateChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tG );
		tVData.add( tLCGrpPolSet);				
		// ���ݴ���
		GrpFloatRateUI tGrpFloatRateUI = new GrpFloatRateUI();
		if (!tGrpFloatRateUI.submitData(tVData,""))
		{
			int n = tGrpFloatRateUI.mErrors.getErrorCount();
			Content = " �޸ķ���ʧ�ܣ�ԭ����: " + tGrpFloatRateUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tGrpFloatRateUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �޸ķ��ʳɹ�! ";
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
