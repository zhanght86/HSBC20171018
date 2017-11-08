<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GroupUWModifyChk.jsp
//程序功能：集体核保定证
//创建日期：2004-12-8 11:10:36
//创建人  ：heyq
//更新记录：  更新人    更新日期     更新原因/内容

%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
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
	loggerDebug("GroupUWModifyChk","session has expired");
	return;
   }
   
  TransferData mTransferData = new TransferData();
  
  mTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));  
  mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
  mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));  
 	// 投保单列表
	String tProposalGrpContNo = request.getParameter("GrpContNo");
//	String tPrtNo = request.getParameter("PrtNo");
	boolean flag = false;

	loggerDebug("GroupUWModifyChk","ready for UWCHECK ProposalGrpContNo: " + tProposalGrpContNo);
	//传入prtno控制并发 add by liuqh 2008-09-26
    String tRadio[] = request.getParameterValues("InpGrpGridSel");
	String tPrtNo[] = request.getParameterValues("GrpGrid1");
	for ( int i=0;i<tRadio.length;i++)
	{
		if(tRadio[i].equals("1"))
		mTransferData.setNameAndValue("PrtNo",tPrtNo[i]);
	}

	if (tProposalGrpContNo != null && tProposalGrpContNo != "")
		flag = true;
	
try{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( mTransferData );
		tVData.add( tG );
		
		// 数据传输
		//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
		String busiName="tbgrpGrpTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"0000002005",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			{
			  loggerDebug("GroupUWModifyChk","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			  Content = "核保订正失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    loggerDebug("GroupUWModifyChk","tError.getErrorCount:"+tError.getErrorCount());
		    if (!tError.needDealError())
		    {                          
		    	Content = " 核保订正成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 核保订正失败，原因是:";
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
    //top.close();
	//alert("<%=Content%>");
	//top.close();
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
