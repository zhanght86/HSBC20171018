<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
//输出参数
CErrors tError = null;
String tRela  = "";                
String FlagStr="";
String Content = "";
String tAction = "";
String tOperate = "";
String wFlag = "";
String mSubType = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
    VData tVData = new VData();
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    wFlag = request.getParameter("WorkFlowFlag");
    TransferData mTransferData = new TransferData();
    String tInputTime="";
    tInputTime=request.getParameter("InputTime");
    if("0".equals(tInputTime)||tInputTime==null){
    	tInputTime="1";
    }else if(tInputTime.equals("1")){
    	tInputTime="2";
    }else{
    	tInputTime="3";
    }
    loggerDebug("DSInputConfirm","本次为"+tInputTime+"岗录入");
    String mPrtNo = request.getParameter("PrtNo");
    if("0000001094".equals(wFlag)){
	    String tSubTypeSql = "select MissionProp17 from lwmission "+
	    		"where 1=1 and missionprop1='"+mPrtNo+"'";
		ExeSQL tAgentExeSQL = new ExeSQL();
		mSubType = tAgentExeSQL.getOneValue(tSubTypeSql);
    }
    else if("0000001401".equals(wFlag)){
	    String tSubTypeSql = "select missionprop5 from lwmission "+
	    		"where 1=1 and missionprop1='"+mPrtNo+"'";
		ExeSQL tAgentExeSQL = new ExeSQL();
		mSubType = tAgentExeSQL.getOneValue(tSubTypeSql);
    }
	
    mTransferData.setNameAndValue("ContNo", request.getParameter("ProposalContNo"));
    mTransferData.setNameAndValue("PrtNo", request.getParameter("PrtNo"));
    mTransferData.setNameAndValue("AppntNo", request.getParameter("AppntNo"));
    mTransferData.setNameAndValue("AppntName",request.getParameter("AppntName"));
    mTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode"));
    mTransferData.setNameAndValue("ManageCom", tG.ManageCom);
    mTransferData.setNameAndValue("Operator",tG.Operator);
    mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate());
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("InputTime",tInputTime);
    mTransferData.setNameAndValue("SubType",mSubType);
    
    loggerDebug("DSInputConfirm","InputTime="+request.getParameter("InputTime"));
    loggerDebug("DSInputConfirm","ContNo="+request.getParameter("ProposalContNo"));
    loggerDebug("DSInputConfirm","PrtNo="+request.getParameter("PrtNo"));
    loggerDebug("DSInputConfirm","AppntNo="+request.getParameter("AppntNo"));
    loggerDebug("DSInputConfirm","AppntName="+request.getParameter("AppntName"));
    loggerDebug("DSInputConfirm","AgentCode="+request.getParameter("AgentCode"));
    loggerDebug("DSInputConfirm","ManageCom="+request.getParameter("ManageCom"));
    loggerDebug("DSInputConfirm","Operator="+tG.Operator);
    loggerDebug("DSInputConfirm","MakeDate="+PubFun.getCurrentDate());
    loggerDebug("DSInputConfirm","MissionID="+request.getParameter("MissionID"));
    loggerDebug("DSInputConfirm","SubMissionID="+request.getParameter("SubMissionID"));
    
    loggerDebug("DSInputConfirm","prtNo=="+request.getParameter("PrtNo"));
    tVData.add(mTransferData);
    tVData.add(tG);
    loggerDebug("DSInputConfirm","wFlag="+wFlag);
    loggerDebug("DSInputConfirm","-------------------start workflow---------------------");
   // TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
   String busiName="tbTbWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   
  if( !tBusinessDelegate.submitData( tVData, wFlag,busiName ) ) {
      Content = " 录入确认失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
      Content =" 保存成功！";
      FlagStr = "Succ";
  }
 loggerDebug("DSInputConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
	var contract='<%=request.getParameter("ProposalContNo")%>';
	//alert(contract);
	
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>",contract);
</script>
</html>
