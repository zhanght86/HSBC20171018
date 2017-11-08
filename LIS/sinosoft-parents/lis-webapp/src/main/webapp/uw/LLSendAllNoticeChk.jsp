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
  <%@page import="com.sinosoft.workflow.claim.*"%>
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
    String tClmNo = request.getParameter("ClmNo");
    String tBatNo = request.getParameter("BatNo");
	//String tOtherNoType = "02";
	
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("LLSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
  loggerDebug("LLSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
try
{
  	if (flag == true)
  	{
  		/**********工作流升级改为循环生成5510不区分contno所以这里不需要contno作为条件*************/
  	  	String tSQL = "select activityid,missionid,SubMissionID from lwmission where 1=1"
  	  				+" and activityid in(select activityid from lwactivity where functionid='10030019') "
  	  				+" and missionprop1='"+tClmNo+"'";// and missionprop3='"+tBatNo+"'
  			SSRS tSSRS = new SSRS();
  			ExeSQL tExeSQL = new ExeSQL();
  			tSSRS = tExeSQL.execSQL(tSQL);
  			if(tSSRS.MaxRow==0){
  				FlagStr = "Fail";
  				Content = " 操作失败,原因是:不满足工作流节点！";
  			}else{
  				String tActivityID= tSSRS.GetText(1,1);
  				String tMissionId = tSSRS.GetText(1,2);
  				String tSubMissionId = tSSRS.GetText(1,3);
  				// 准备传输数据 VData
  				VData tVData = new VData();
  				//tVData.add( tLOPRTManagerSchema);
  				tVData.add( tG );
  				TransferData mTransferData = new TransferData();
  				mTransferData.setNameAndValue("ContNo",tProposalNo);
  				mTransferData.setNameAndValue("NoticeType",tCode);
  				mTransferData.setNameAndValue("ActivityID",tActivityID); 
  				mTransferData.setNameAndValue("MissionID",tMissionId);
  	   			mTransferData.setNameAndValue("SubMissionID",tSubMissionId); 
  	   			mTransferData.setNameAndValue("mSubMissionID",tSubMissionID); 
  	   			mTransferData.setNameAndValue("ClmNo",tClmNo); 
  	   			mTransferData.setNameAndValue("BatNo",tBatNo); 
  	    
  	    		tVData.add(mTransferData);
  	    		String busiName="tWorkFlowUI";
  	    		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  	    		  if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
  	    		  {    
  	    		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
  	    		       { 
  	    					Content = "提交工作流失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
  	    					FlagStr = "Fail";
  	    			   }
  	    			   else
  	    			   {
  	    					Content = "提交工作流失败";
  	    					FlagStr = "Fail";				
  	    			   }
  	    		  }
  	    		  else
  	    		  {
  	    		     	Content = "数据提交成功! ";
  	    		      	FlagStr = "Succ";  
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
