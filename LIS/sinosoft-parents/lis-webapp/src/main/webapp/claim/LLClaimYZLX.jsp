
<%@page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<html>
<script language="javascript">
<%

  	    CErrors tError = null;
	  	String FlagStr = "";
	  	String Content = "";
	  	GlobalInput tGI = new GlobalInput(); 
	  	tGI=(GlobalInput)session.getValue("GI");  
	
	  	//ҳ����Ч
	  	if(tGI == null)
	  	{
	  	    FlagStr = "Fail";
	  	    Content = "ҳ��ʧЧ,�����µ�½";
	  	    loggerDebug("LLClaimYZLX","ҳ��ʧЧ,�����µ�½");    
	  	}
	  	else
	  	{
	  		
	  		VData tVData=new VData();
			TransferData tTransferData =new TransferData();
			tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
			tVData.add(tTransferData);
		    tVData.add(tGI);
			
			//LLClaimYZLXBL tClaimYZLXBL=new LLClaimYZLXBL();
			String busiName="LLClaimYZLXBL";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			
			String transact = request.getParameter("fmtransact"); //��ȡ����insert||update

			try
		  	{
		
			    //if (!tClaimYZLXBL.submitData(tVData,transact))
//			    {
//			        Content = " �����ύLLEndCaseUIʧ�ܣ�ԭ����: " + tClaimYZLXBL.mErrors.getError(0).errorMessage;
//			        FlagStr = "Fail";
//			    }
				if(!tBusinessDelegate.submitData(tVData,transact,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "�����ύLLEndCaseUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "�����ύLLEndCaseUIʧ��";
						FlagStr = "Fail";				
					}
				}

			    else
			    {
			    	Content = " �����ύ�ɹ�! ";
				    FlagStr = "Succ";
			    }

			}
			catch(Exception ex){
				Content = "ʧ�ܣ�ԭ����:" + ex.toString();
		    	FlagStr = "Fail";
			}
	  	}
		
%>
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
