<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PEdorReprintSava.jsp
//程序功能：保全人工核保补打通知书
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
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
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
	TransferData tTransferData = new TransferData();
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	String tPrtSeq = request.getParameter("PrtSeq");
	String tCode = request.getParameter("Code");
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");	
	String tActivityID = request.getParameter("ActivityID");
	
	loggerDebug("RePrintSave","ContNo:"+tContNo);
	loggerDebug("RePrintSave","tMissionID:"+tMissionID);
	loggerDebug("RePrintSave","tSubMissionID:"+tSubMissionID);
	loggerDebug("RePrintSave","tCode:"+tCode);
	boolean flag = true;
	if (!tContNo.equals("")&& !tMissionID.equals("") && !tSubMissionID.equals(""))
	{
		//准备公共传输信息
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("GrpContNo",tContNo);
		tTransferData.setNameAndValue("Code",tCode);
		tTransferData.setNameAndValue("PrtNo",tPrtNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);	
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("LOPRTManagerSchema",tLOPRTManagerSchema);	
	}
	else
	{
		flag = false;
		Content = "数据不完整!";
	}	
	loggerDebug("RePrintSave","flag:"+flag);
	loggerDebug("RePrintSave","tCode:"+tCode);
try
{
  
  	if(tCode.equals("TB89")||tCode.equals("TB90")||tCode.equals("03") || tCode.equals("04")||tCode.equals("05")||tCode.equals("14")||tCode.equals("17") || tCode.equals("85")||tCode.equals("81")||tCode.equals("82")||tCode.equals("83")||tCode.equals("84")||tCode.equals("86")||tCode.equals("87")||tCode.equals("88")||tCode.trim().equals("89")) //edity by yaory
	   {
		// 准备传输数据 VData
		String tOperate = new String();
		if(tCode.trim().equals("03"))	
	      tOperate = "0000001114"; //打印体检通知书任务节点编码
	//   if(tCode.trim().equals("04"))	
	//      tOperate = "0000001116";  //打印面见通知书任务节点编码
	   if(tCode.trim().equals("05"))	
	      tOperate = "0000001115"; //打印核保通知书任务节点编码 
	   if(tCode.trim().equals("54")) //add by yaory	
	      tOperate = "0000002210"; //打印团单问题件任务节点编码  
	   if(tCode.trim().equals("14"))	
	      tOperate = "0000001018"; //打印新契约问题件通知书任务节点编码 
	   if(tCode.trim().equals("17"))	
	      tOperate = "0000001240"; //打印新契约客户合并通知书任务节点编码
	      //打印业务员通知书
	  			  if(tCode.equals("14"))
	  			  	tOperate = "0000001018";
	  			  //打印核保通知书(打印类)
	  			  if(tCode.equals("TB89"))
	  			  	//tOperate = "0000001115";
	  				tOperate=tActivityID;
	  			  //核保通知书(非打印类)
	  			  if(tCode.equals("TB90"))
	  			  	//tOperate = "0000001301";
	  				tOperate=tActivityID;
	  			  	//生调通知书
	  			  if(tCode.equals("04"))
	  			  	tOperate = "0000001116";
	   if(tCode.trim().equals("00")||tCode.trim().equals("06")||tCode.trim().equals("81")||tCode.trim().equals("82")||tCode.trim().equals("83")||tCode.trim().equals("84")||tCode.trim().equals("85")||tCode.trim().equals("86")||tCode.trim().equals("89")||tCode.trim().equals("87")||tCode.trim().equals("88")) 
	      tOperate = "0000001290";	    
	    loggerDebug("RePrintSave","tOperate"+tOperate);
	    //查询busitype
	    ExeSQL tExeSQL=new ExeSQL();
	    String tBusiType=tExeSQL.getOneValue("select busitype from lwactivity where activityid='"+tOperate+"'");
	    tTransferData.setNameAndValue("BusiType", tBusiType);
	    tTransferData.setNameAndValue("ActivityID",tOperate);
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		String busiName="tWorkFlowUI";
		

		// 数据传输
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if (tBusinessDelegate.submitData(tVData,"execute",busiName)==false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			Content = " 补打通知书数据提交失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = "补打通知书数据提交成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 补打通知书数据提交失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		     }
		}
		}
  	/**
		else if(tCode.equals("76") || tCode.equals("75"))
		{
		loggerDebug("RePrintSave","kaishi");
			String tOperate = new String();
		if(tCode.trim().equals("76"))	
	      tOperate = "0000002114"; //打印团单核保结论通知书任务节点编码
	  if (tCode.trim().equals("75"))
	     tOperate = "0000002314"; //打印团单核保要求通知书任务节点编码    
	 
	    loggerDebug("RePrintSave","tOperate"+tOperate);
	      
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tG );
		
		// 数据传输
		GrpTbWorkFlowUI tGrpTbWorkFlowUI   = new GrpTbWorkFlowUI();
		if (!tGrpTbWorkFlowUI.submitData(tVData,tOperate))
		{
			int n = tGrpTbWorkFlowUI.mErrors.getErrorCount();
			Content = " 补打通知书数据提交失败，原因是: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tGrpTbWorkFlowUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = "补打通知书数据提交成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 补打通知书数据提交失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		     }
		}
		}
  	*/
		//00 06 07 09 
		//add 21 ,hanbin 2010-05-11
	  else if(tCode.equals("08")||tCode.equals("00") || tCode.equals("06")||tCode.equals("07") || tCode.equals("09") ||tCode.equals("21"))
	  {
	  	VData tVData = new VData();
			tVData.add( tTransferData);
			tVData.add( tG );
			tVData.add(tLOPRTManagerSchema);
	      RePrintUI tRePrintUI = new RePrintUI();
     try
  	{
    	tRePrintUI.submitData(tVData,"CONFIRM");
    	 tError = tRePrintUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = "补打通知书数据提交成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 补打通知书数据提交失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		     }
  	}
  	catch(Exception ex)
  	{
 	   	Content = "数据提交失败，原因是:" + ex.toString();
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
