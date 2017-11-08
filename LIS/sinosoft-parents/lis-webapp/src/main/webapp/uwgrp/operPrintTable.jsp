<%@page import="com.sinosoft.workflow.tb.TbWorkFlowUI"%>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.f1printgrp.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.certify.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
    <%@page import="com.sinosoft.workflow.ask.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>

<%
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI");
// 输出参数
   String FlagStr = "";
   String Content = "";
   String PrintNo=(String)session.getValue("PrintNo");
   String tCode = (String)session.getValue("Code");
   String tPrtNo = (String)session.getValue("PrtNo");
   String tContNo = (String)session.getValue("ContNo");
   String tMissionID = (String)session.getValue("MissionID");
   String tSubMissionID = (String)session.getValue("SubMissionID");
   String tGrpContNo = (String)session.getValue("GrpContNo");
   loggerDebug("operPrintTable","PrintNo:"+PrintNo);
   loggerDebug("operPrintTable","put session value,ContNo2:"+tContNo);
   loggerDebug("operPrintTable","tMissionID:"+tMissionID);
   loggerDebug("operPrintTable","tCode:"+tCode);

   if(PrintNo==null || tCode == null)
   {
          FlagStr="Succ";
         Content="打印数据完毕！";
   }
  else
   {
   	 if(tCode.equals("03")|| tCode.equals("BQ90") || tCode.equals("04")||tCode.equals("05")||tCode.equals("14")||tCode.equals("17") || tCode.equals("85")||tCode.equals("00")||tCode.equals("06")||tCode.equals("81")||tCode.equals("82")||tCode.equals("83")||tCode.equals("84")||tCode.equals("86")||tCode.equals("87")||tCode.equals("88")||tCode.equals("89")||tCode.equals("BQ80")||tCode.equals("BQ81")||tCode.equals("BQ82")||tCode.equals("BQ85")||tCode.equals("BQ84")||tCode.equals("BQ86")||tCode.equals("BQ87")||tCode.equals("BQ89")) //edity by yaory
	   {
	   			//准备数据
	   			loggerDebug("operPrintTable","开始打印后台处理");
					//准备公共传输信息
					String tOperate = new String();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("PrtSeq",PrintNo);
					tTransferData.setNameAndValue("Code",tCode) ;
					tTransferData.setNameAndValue("ContNo",tContNo) ;
					tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
					tTransferData.setNameAndValue("MissionID",tMissionID) ;
					tTransferData.setNameAndValue("SubMissionID",tSubMissionID) ;
	  			 if(tCode == "03")
	  			    tOperate = "0000001106"; //打印体检通知书任务节点编码
	  			 if(tCode == "04")
	  			    tOperate = "0000001108";  //打印面见通知书任务节点编码
	  			 if(tCode == "05")
	  			    tOperate = "0000001107"; //打印核保通知书任务节点编码
	  			 if(tCode == "14")
	  			    tOperate = "0000001220";  //打印契约内容变更通知书任务节点编码
	  			 
	  		//	if(tCode == "85")
	  			//    tOperate = "0000001023";  //打印业务员通知书任务节点编码
	  			if(tCode == "17")
	  			    tOperate = "0000001230";  //打印客户合并通知书任务节点编码

	  			if(tCode.equals("00")||tCode.equals("06")||tCode.equals("81")||tCode.equals("82")||tCode.equals("83")||tCode.equals("84")||tCode.equals("00")||tCode.equals("86")||tCode.equals("85")||tCode.equals("87")||tCode.equals("88")||tCode.equals("89")||tCode.equals("BQ80")||tCode.equals("BQ81")||tCode.equals("BQ82")||tCode.equals("BQ85")||tCode.equals("BQ84")||tCode.equals("BQ86")||tCode.equals("BQ87")||tCode.equals("BQ89"))
	  			  tOperate = "0000001280"; //核保通知书任务节点编码
	  			 //if(tCode.equals("87")||tCode.equals("88"))
	  			  // tOperate = "0000001108";   //面见通知书任务节点编码

	  			 VData tVData = new VData();
	  			 tVData.add(tTransferData);
	  			 tVData.add(tGI);

    			   // 数据传输
					TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
					if (!tTbWorkFlowUI.submitData(tVData,tOperate)) //执行保全核保生调工作流节点0000000004
					{
						int n = tTbWorkFlowUI.mErrors.getErrorCount();
						Content = " 更新打印数据失败，原因是: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
						loggerDebug("operPrintTable",Content);
						FlagStr = "Fail";
					}
    			   else
    			   {
    			     FlagStr="Succ";
    			     Content="更新打印数据成功！";
    			   }
    			   loggerDebug("operPrintTable","Print:"+FlagStr);
	 }
	 else if(tCode == "61" || tCode == "64"||tCode == "65"||tCode.equals("54")||tCode.equals("99")) //add by yaory
	 {
	   			//准备数据
	   			loggerDebug("operPrintTable","开始打印后台处理");
					//准备公共传输信息
					String tOperate = new String();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("PrtSeq",PrintNo);
					tTransferData.setNameAndValue("Code",tCode) ;
					tTransferData.setNameAndValue("GrpContNo",tGrpContNo) ;
					tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
					tTransferData.setNameAndValue("MissionID",tMissionID) ;
					tTransferData.setNameAndValue("SubMissionID",tSubMissionID) ;
	  			 if(tCode == "61")
	  			    tOperate = "0000006015"; //打印询价确定通知书任务节点编码
	  			 if(tCode == "64")
	  			    tOperate = "0000006008";  //团体询价补充材料通知书
	  			 if(tCode == "65")
	  			    tOperate = "0000006021"; //团单询价跟踪通知书
	  			 if(tCode == "54") //add by yaory
	  			    tOperate = "0000002220";  //打印团单问题件契约内容变更通知书任务节点编码
	  			 if(tCode == "99") //add by yaory
	  			    tOperate = "0000002230";  //打印团单客户合并任务节点编码
	  			 VData tVData = new VData();
	  			 tVData.add(tTransferData);
	  			 tVData.add(tGI);

    			   // 数据传输
					AskWorkFlowUI tAskWorkFlowUI   = new AskWorkFlowUI();
					if (!tAskWorkFlowUI.submitData(tVData,tOperate)) //执行保全核保生调工作流节点0000000004
					{
						int n = tAskWorkFlowUI.mErrors.getErrorCount();
						Content = " 更新打印数据失败，原因是: " + tAskWorkFlowUI.mErrors.getError(0).errorMessage;
						loggerDebug("operPrintTable",Content);
						FlagStr = "Fail";
					}
    			   else
    			   {
    			     FlagStr="Succ";
    			     Content="更新打印数据成功！";
    			   }
    			   loggerDebug("operPrintTable","Print:"+FlagStr);
	 }
	  else if(tCode == "76" || tCode == "75" ) //add by zhangxing
	 {
	   			//准备数据
	   			loggerDebug("operPrintTable","开始打印后台处理");
					//准备公共传输信息
					String tOperate = new String();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("PrtSeq",PrintNo);
					tTransferData.setNameAndValue("Code",tCode) ;
					tTransferData.setNameAndValue("GrpContNo",tGrpContNo) ;
					tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
					tTransferData.setNameAndValue("MissionID",tMissionID) ;
					tTransferData.setNameAndValue("SubMissionID",tSubMissionID) ;
	  			 if(tCode == "76")
	  			 {
	  			    tOperate = "0000002106"; //打印询价确定通知书任务节点编码
	  			 }
	  			 else if(tCode == "75")
	  			 {
	  			    tOperate = "0000002306";
	  			 }
	  			         
	  			
	  			 VData tVData = new VData();
	  			 tVData.add(tTransferData);
	  			 tVData.add(tGI);

    			   // 数据传输
					GrpTbWorkFlowUI tGrpTbWorkFlowUI   = new GrpTbWorkFlowUI();
					if (!tGrpTbWorkFlowUI.submitData(tVData,tOperate)) //执行保全核保生调工作流节点0000000004
					{
						int n = tGrpTbWorkFlowUI.mErrors.getErrorCount();
						Content = " 更新打印数据失败，原因是: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
						loggerDebug("operPrintTable",Content);
						FlagStr = "Fail";
					}
    			   else
    			   {
    			     FlagStr="Succ";
    			     Content="更新打印数据成功！";
    			   }
    			   loggerDebug("operPrintTable","Print:"+FlagStr);
	 }
	 else
     {
	   LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	   loggerDebug("operPrintTable","进入更新的PrintNo:"+PrintNo);
	   tLOPRTManagerSchema.setPrtSeq(PrintNo);
	   
	   VData tVData = new VData();
	   tVData.add(tLOPRTManagerSchema);
	   tVData.add(tGI);

       PrintManagerBL tPrintManagerBL = new PrintManagerBL();
       if(!tPrintManagerBL.submitData(tVData,"CONFIRM"))
       {
         FlagStr="Fail:"+tPrintManagerBL.mErrors.getFirstError().toString();
       }
       else
       {
         FlagStr = "Nothing";
         Content="更新打印数据成功！";
// 	     session.putValue("PrintNo",null );

       }
       loggerDebug("operPrintTable","Print:"+FlagStr);

   }
  }


%>
<html>
<script language="javascript">
if("<%=FlagStr%>"=='Fail')
{
alert("此单证不能发送！");
}

window.returnValue="<%=FlagStr%>";

window.close();
</script>
</html>


