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
	
	String tdutycode[] = request.getParameterValues("PolAddGrid1");
	String tGrpPolNo[] = request.getParameterValues("PolAddGrid2");
	String tpayplantype[] = request.getParameterValues("PolAddGrid7");
	
	
	
	int feeCount = tdutycode.length;
	if (feeCount == 0 )
	{
		Content = "��¼��ӷ���Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
	    	//׼����Լ�ӷ���Ϣ
	    	if (feeCount > 0)
	    	 {
	    	  for (int i = 0; i < feeCount; i++)
				{
					if (!tdutycode[i].equals(""))
					{
			  			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			  			tLCGrpPolSchema.setGrpPolNo(tGrpPolNo[i]);
			  			tLCGrpPolSchema.setDif( tpayplantype[i]);
			  		
	    			    tLCGrpPolSet.add( tLCGrpPolSchema );
	    			    flag = true;
	    			    
	    			 	loggerDebug("GrpUWAddFeeChk","i:"+i);
	    			    loggerDebug("GrpUWAddFeeChk","���α���"+i+":  "+tdutycode[i]);
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
			
					
	
		else
		{
			Content = "��������ʧ��!";
			flag = false;
		}
	}
	
	loggerDebug("GrpUWAddFeeChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tG );
		tVData.add( tLCGrpPolSet);				
		// ���ݴ���
		GrpUWAddFeeUI tGrpUWAddFeeUI = new GrpUWAddFeeUI();
		if (!tGrpUWAddFeeUI.submitData(tVData,""))
		{
			int n = tGrpUWAddFeeUI.mErrors.getErrorCount();
			Content = " �˹��˱��ӷ�ʧ�ܣ�ԭ����: " + tGrpUWAddFeeUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tGrpUWAddFeeUI.mErrors;
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
