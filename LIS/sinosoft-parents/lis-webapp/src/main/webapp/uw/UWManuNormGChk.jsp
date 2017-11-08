<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuNormChk.jsp
//程序功能：人工核保最终结论录入保存
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
	LCPolSet tLCPolSet = new LCPolSet();
	LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();

	String tUWFlag = request.getParameter("UWState");		
	String tUWIdea = request.getParameter("UWIdea");
	String tvalidate = request.getParameter("UWDelay");
	String tPolNo = "";

	tPolNo = request.getParameter("PolNo");

	loggerDebug("UWManuNormGChk","UWIDEA:"+tUWIdea);
	loggerDebug("UWManuNormGChk","PolNo:"+tPolNo);
	
	boolean flag = false;
	
	if (!tPolNo.equals(""))
	{
	
 	    LCPolSchema tLCPolSchema = new LCPolSchema();
 	    LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
 		
	    tLCPolSchema.setPolNo( tPolNo);
	    tLCPolSchema.setUWFlag(tUWFlag);
	    tLCPolSchema.setRemark(tUWIdea);
	    tLCUWMasterSchema.setPostponeDay(tvalidate);
	    tLCUWMasterSchema.setUWIdea(tUWIdea);
	    
	    tLCPolSet.add( tLCPolSchema );
	    tLCUWMasterSet.add( tLCUWMasterSchema );
	    
	    flag = true;
	}
	else
	{
	    FlagStr = "Fail";
	    Content = "号码传输失败!";
	}
	
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCPolSet );
		tVData.add( tLCUWMasterSet);
		tVData.add( tG );
		
		// 数据传输

		ManuUWNormGChkUI tManuUWNormGChkUI   = new ManuUWNormGChkUI();
		if (tManuUWNormGChkUI.submitData(tVData,"INSERT") == false)
		{
			int n = tManuUWNormGChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 人工核保失败，原因是: " + tManuUWNormGChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    
		    tError = tManuUWNormGChkUI.mErrors;
		    if (!tError.needDealError())
		    {                     
		    	Content = " 人工核保操作成功!";
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
