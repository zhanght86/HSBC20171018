
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
	
	  	//页面有效
	  	if(tGI == null)
	  	{
	  	    FlagStr = "Fail";
	  	    Content = "页面失效,请重新登陆";
	  	    loggerDebug("LLClaimYZLX","页面失效,请重新登陆");    
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
			
			String transact = request.getParameter("fmtransact"); //获取操作insert||update

			try
		  	{
		
			    //if (!tClaimYZLXBL.submitData(tVData,transact))
//			    {
//			        Content = " 数据提交LLEndCaseUI失败，原因是: " + tClaimYZLXBL.mErrors.getError(0).errorMessage;
//			        FlagStr = "Fail";
//			    }
				if(!tBusinessDelegate.submitData(tVData,transact,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "数据提交LLEndCaseUI失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "数据提交LLEndCaseUI失败";
						FlagStr = "Fail";				
					}
				}

			    else
			    {
			    	Content = " 数据提交成功! ";
				    FlagStr = "Succ";
			    }

			}
			catch(Exception ex){
				Content = "失败，原因是:" + ex.toString();
		    	FlagStr = "Fail";
			}
	  	}
		
%>
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
