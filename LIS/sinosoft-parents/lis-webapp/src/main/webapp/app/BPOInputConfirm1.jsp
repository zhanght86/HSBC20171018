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
<%
    //输出参数
    CErrors tError = null;
    String tRela  = "";                
    String FlagStr="";
    String Content = "";
    String tAction = "";
    String tOperate = "";
    String wFlag = "";
    loggerDebug("BPOInputConfirm1","Start BPOInputConfirm...");
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
 
    VData tVData = new VData();

    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    wFlag = request.getParameter("WorkFlowFlag");
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("ContNo", request.getParameter("PrtNo"));
    mTransferData.setNameAndValue("PrtNo", request.getParameter("PrtNo"));
    mTransferData.setNameAndValue("AppntNo", request.getParameter("AppntNo"));
    mTransferData.setNameAndValue("AppntName",request.getParameter("AppntName"));
    mTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode"));
    mTransferData.setNameAndValue("ManageCom", request.getParameter("ManageCom"));
    mTransferData.setNameAndValue("Operator",tG.Operator);
    mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate());
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));   
    //mTransferData.setNameAndValue("DealType", request.getParameter("DealType"));
    mTransferData.setNameAndValue("BussNoType","TB");
    //mTransferData.setNameAndValue("State", tState);
    mTransferData.setNameAndValue("SpecType",StrTool.cTrim(request.getParameter("SpecType")));
    mTransferData.setNameAndValue("DefaultOperator",tG.Operator);
    //传入DealType，DealType不等于00不会进行客户号手工合并的校验
    mTransferData.setNameAndValue("DealType","99");
    mTransferData.setNameAndValue("SubType","TB1001");
    
    loggerDebug("BPOInputConfirm1","ContNo="+request.getParameter("ProposalContNo"));
    loggerDebug("BPOInputConfirm1","PrtNo="+request.getParameter("PrtNo"));
    loggerDebug("BPOInputConfirm1","AppntNo="+request.getParameter("AppntNo"));
    loggerDebug("BPOInputConfirm1","AppntName="+request.getParameter("AppntName"));
    loggerDebug("BPOInputConfirm1","AgentCode="+request.getParameter("AgentCode"));
    loggerDebug("BPOInputConfirm1","ManageCom="+request.getParameter("ManageCom"));
    loggerDebug("BPOInputConfirm1","Operator="+tG.Operator);
    loggerDebug("BPOInputConfirm1","MakeDate="+PubFun.getCurrentDate());
    loggerDebug("BPOInputConfirm1","MissionID="+request.getParameter("MissionID"));
    loggerDebug("BPOInputConfirm1","SubMissionID="+request.getParameter("SubMissionID"));
    loggerDebug("BPOInputConfirm1","DealType="+request.getParameter("DealType"));
    loggerDebug("BPOInputConfirm1","BussNoType=TB");
    //loggerDebug("BPOInputConfirm1","State="+tState);
    loggerDebug("BPOInputConfirm1","DefaultOperator="+tG.Operator);
    loggerDebug("BPOInputConfirm1","SpecType="+StrTool.cTrim(request.getParameter("SpecType")));
    
    tVData.add(mTransferData);
    tVData.add(tG);
    loggerDebug("BPOInputConfirm1","wFlag="+wFlag);
    loggerDebug("BPOInputConfirm1","-------------------start workflow---------------------");
    TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
    if( !tTbWorkFlowUI.submitData( tVData, wFlag ) ) {
        Content = " 处理完毕确认失败，原因是: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
        FlagStr = "Fail";
    }else {
        Content =" 处理成功！";
        FlagStr = "Succ";
    }
 
 loggerDebug("BPOInputConfirm1","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
 try 
 {
   window.returnValue = "<%=Content%>";
 }
 catch(e) {}
 window.close();
</script>
</html>
