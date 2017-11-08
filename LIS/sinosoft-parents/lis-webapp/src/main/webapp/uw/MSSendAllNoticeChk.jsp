<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSendPrintChk.jsp
//程序功能：送打印队列
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "部分通知书发送失败,原因是:";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  //tongmeng 2007-11-09 modify
  //修改为统一发放核保通知书,一期以新契约为主,二期再对其他复用此功能程序做修改.
  //校验处理
  //内容待填充	
			
  	//接收信息
  	// 投保单列表	
	String tProposalNo = request.getParameter("ContNo");
	String tOtherNoType = "02";
	
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("MSSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
  loggerDebug("MSSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
try
{
  	if (flag == true)
  	{
  		
		// 准备传输数据 VData
		VData tVData = new VData();
		//tVData.add( tLOPRTManagerSchema);
		tVData.add( tG );
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("ContNo",tProposalNo);
		mTransferData.setNameAndValue("NoticeType",tCode);
		//mTransferData.setNameAndValue("MissionID",tMissionID);
			//mTransferData.setNameAndValue("SubMissionID",tSubMissionID); 
			 


			tVData.add(mTransferData);
		 	String busiName="cbcheckUWSendNoticeBL";
	        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	        
	        if(tBusinessDelegate.submitData(tVData,"",busiName)==false)
	        {
	        	 int n = tBusinessDelegate.getCErrors().getErrorCount();
	             Content = "操作失败，原因是:";
	             Content = Content + tBusinessDelegate.getCErrors().getError(0).errorMessage;
	             FlagStr = "Fail";
	        }else{
	        	
	        }
	      //如果在Catch中发现异常，则不从错误类中提取错误信息
			 //如果在Catch中发现异常，则不从错误类中提取错误信息
        if (FlagStr == "Fail")
        {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
            	Content = "操作成功! ";
               
                FlagStr = "Succ";
            }
            else
            {
                Content = "操作失败，原因是:" + tError.getFirstError();
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
