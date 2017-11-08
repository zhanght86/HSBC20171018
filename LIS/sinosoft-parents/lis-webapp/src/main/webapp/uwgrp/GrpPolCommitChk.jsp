<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuRReportChk.jsp
//程序功能：新契约人工核保对集体险种下结论
//创建日期：2002-06-19 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人    更新日期     更新原因/内容
%>
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

  	//接收信息
  
  LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
  tLCGrpPolSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
  tLCGrpPolSchema.setUWFlag(request.getParameter("GUWState"));
  tLCGrpPolSchema.setRemark(request.getParameter("GUWIdea"));
  tLCGrpPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLCGrpPolSchema.setRiskCode(request.getParameter("temriskcode"));
 
	boolean flag = true;
	
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCGrpPolSchema);
		tVData.add( tG );
		
		GrpUWManuNormGChkUI tGrpUWManuNormGChkUI   = new GrpUWManuNormGChkUI();
		if (tGrpUWManuNormGChkUI.submitData(tVData,"") == false)
		{
			int n = tGrpUWManuNormGChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 下集体险种保单结论失败,原因是: " + tGrpUWManuNormGChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tGrpUWManuNormGChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 保存成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 下集体险种保单结论失败,原因是:" + tError.getFirstError();
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
