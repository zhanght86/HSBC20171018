<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupUWAutoChk.jsp
//程序功能：集体自动核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
loggerDebug("GroupUWSetSpecialFlag","Auto-begin:");
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
	loggerDebug("GroupUWSetSpecialFlag","session has expired");
	return;
   }
   
 	// 投保单列表
	LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	String tGrpProposalNo = request.getParameter("GrpProposalNo");
	String tGrpPrtNo = request.getParameter("GrpPrtNo");
	boolean flag = false;
	
	if (tGrpProposalNo != null && tGrpProposalNo != "")
	{
		loggerDebug("GroupUWSetSpecialFlag","GrpProposalNo:"+":"+tGrpProposalNo);	  	
   		tLCGrpPolSchema.setPrtNo( tGrpPrtNo );
		flag = true;
	}
		
	
try{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCGrpPolSchema );
		tVData.add( tG );
		
		// 数据传输
		//GrpUWSetSpecialFlagUI tGrpUWSetSpecialFlagUI   = new GrpUWSetSpecialFlagUI();
		
		String busiName="cbcheckgrpGrpUWSetSpecialFlagUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"UPDATE",busiName) == false)
		{
			  //loggerDebug("GroupUWSetSpecialFlag","Error: "+tGrpUWSetSpecialFlagUI.mErrors.getError(0).errorMessage);
			  Content = " 操作失败，原因是: " ; //+ tGrpUWSetSpecialFlagUI.mErrors.getError(0).errorMessage;
		
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    loggerDebug("GroupUWSetSpecialFlag","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " 操作成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 操作失败，原因是:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {
			        //tError = tErrors.getError(i);
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
			}
		    	FlagStr = "Fail";
		    }
		}
	}
	else
	{
		Content = "请选择保单！";
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
    top.close();
	alert("<%=Content%>");
	top.close();
</script>
</html>
