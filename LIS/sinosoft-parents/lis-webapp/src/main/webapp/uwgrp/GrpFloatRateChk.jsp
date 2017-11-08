<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpecChk.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
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
  
	//接收信息
  	// 保单列表
	

    VData tInputData = new VData();
    LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	
	
	String tGrpPolNo[] = request.getParameterValues("ContGrid1");
	String tFloatRate[] = request.getParameterValues("ContGrid6");
	
	
	
	int feeCount = tGrpPolNo.length;

	
	    	//准备特约加费信息
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
	    			 Content = "信息未填写完整,请确认!";
	    			 flag = false;	
	    			 FlagStr = "Fail";   
	    			 break; 			 
	    			}
				} // End of for				    
			} // End of if
			
					
	
		else
		{
			Content = "传输数据失败!";
			flag = false;
		}
	
	
	loggerDebug("GrpFloatRateChk","flag:"+flag);
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tG );
		tVData.add( tLCGrpPolSet);				
		// 数据传输
		GrpFloatRateUI tGrpFloatRateUI = new GrpFloatRateUI();
		if (!tGrpFloatRateUI.submitData(tVData,""))
		{
			int n = tGrpFloatRateUI.mErrors.getErrorCount();
			Content = " 修改费率失败，原因是: " + tGrpFloatRateUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tGrpFloatRateUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 修改费率成功! ";
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
	Content = Content.trim()+".提示：异常终止!";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
