<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	LCGCUWMasterSet tLCGCUWMasterSet = new LCGCUWMasterSet();

	String tUWFlag = "9";		
	String tUWIdea = "批量通过";
	String tGrpContNo = request.getParameter("GrpContNo");

	loggerDebug("UWBatchManuNormGChk","UWState:"+tUWFlag);
	loggerDebug("UWBatchManuNormGChk","UWIDEA:"+tUWIdea);
	loggerDebug("UWBatchManuNormGChk","GrpContNo:"+tGrpContNo);
	
	boolean flag = false;
	
	if (!tGrpContNo.equals(""))
	{
	
 	    LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
 	    LCGCUWMasterSchema tLCGCUWMasterSchema = new LCGCUWMasterSchema();
 		
	    tLCGrpContSchema.setGrpContNo( tGrpContNo);
	    tLCGrpContSchema.setUWFlag(tUWFlag);
	    tLCGrpContSchema.setRemark(tUWIdea);
	    tLCGrpContSchema.setSpecFlag("5"); //说明是批量通过
	    tLCGCUWMasterSchema.setUWIdea(tUWIdea);
	    
	    tLCGrpContSet.add( tLCGrpContSchema );
	    tLCGCUWMasterSet.add( tLCGCUWMasterSchema );
	    
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
		tVData.add( tLCGrpContSet );
		tVData.add( tLCGCUWMasterSet);
		tVData.add( tG );
		
		// 数据传输

		UWBatchManuNormGChkUI tUWBatchManuNormGChkUI = new UWBatchManuNormGChkUI();
		if (tUWBatchManuNormGChkUI.submitData(tVData,"") == false)
		{
			int n = tUWBatchManuNormGChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 人工核保失败，原因是: " + tUWBatchManuNormGChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    
		    tError = tUWBatchManuNormGChkUI.mErrors;
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
