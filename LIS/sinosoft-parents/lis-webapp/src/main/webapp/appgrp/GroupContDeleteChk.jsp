<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupContDeleteChk.jsp
//程序功能：团单整单删除
//创建日期：2004-12-06 11:10:36
//创建人  ：Zhangrong
//更新记录：  更新人    更新日期     更新原因/内容
loggerDebug("GroupContDeleteChk","Auto-begin:");
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
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
	loggerDebug("GroupContDeleteChk","session has expired");
	return;
   }
   
 	// 投保单列表
	String tGrpContNo = request.getParameter("GrpContNo");
//	String tPrtNo = request.getParameter("PrtNo");
	boolean flag = false;

	loggerDebug("GroupContDeleteChk","ready for UWCHECK GrpContNo: " + tGrpContNo);
	
	LCGrpContSet tLCGrpContSet = null;
	LCGrpContDB tLCGrpContDB = new LCGrpContDB();
	tLCGrpContDB.setGrpContNo(tGrpContNo);
	tLCGrpContSet = tLCGrpContDB.query();
	if (tLCGrpContSet == null)
	{
		loggerDebug("GroupContDeleteChk","集体（投）保单号为" + tGrpContNo + "的合同查找失败！");
		return;
	}
	LCGrpContSchema tLCGrpContSchema = tLCGrpContSet.get(1);

	
try{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCGrpContSchema );
		tVData.add( tG );
		
		// 数据传输
		//GroupContDeleteUI tGroupContDeleteUI = new GroupContDeleteUI();
		
		  String busiName="tbgrpGroupContDeleteUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"DELETE",busiName) == false)
		{
			FlagStr = "Fail";
		}
		else
			FlagStr = "Succ";
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    loggerDebug("GroupContDeleteChk","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " 整单删除成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 删除失败，原因是:";
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
