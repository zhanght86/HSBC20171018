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
    <%@page import="com.sinosoft.workflow.bq.*"%>
<%
//输出参数
CErrors tError = null;
String tRela  = "";                
String FlagStr="";
String Content = "";
String tAction = "";
String tOperate = "";
String wFlag = "";

String tCustomerIdea[] = request.getParameterValues("GrpGrid9");
String ttCustomerIdea = tCustomerIdea[0];
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
    VData tVData = new VData();
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    TransferData mTransferData = new TransferData();
	
    mTransferData.setNameAndValue("ContNo", request.getParameter("ContNo"));
    mTransferData.setNameAndValue("EdorNo", request.getParameter("EdorNo"));
    mTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
    mTransferData.setNameAndValue("ManageCom", request.getParameter("ManageCom"));
    mTransferData.setNameAndValue("ActivityID", request.getParameter("ActivityID"));
    mTransferData.setNameAndValue("BusiType", "1002");
    mTransferData.setNameAndValue("Operator",tG.Operator);
    mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate());
    mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
    mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
    mTransferData.setNameAndValue("CustomerIdea",ttCustomerIdea);
    
    loggerDebug("BQUWConfirmConfirm","InputTime="+request.getParameter("InputTime"));
    loggerDebug("BQUWConfirmConfirm","ContNo="+request.getParameter("ContNo"));
    loggerDebug("BQUWConfirmConfirm","EdorNo="+request.getParameter("EdorNo"));
    loggerDebug("BQUWConfirmConfirm","EdorType="+request.getParameter("EdorType"));
    loggerDebug("BQUWConfirmConfirm","CustomerIdea="+ttCustomerIdea);
    loggerDebug("BQUWConfirmConfirm","AgentCode="+request.getParameter("AgentCode"));
    //loggerDebug("BQUWConfirmConfirm","ManageCom="+tG.ManageCom);
    //loggerDebug("BQUWConfirmConfirm","Operator="+tG.Operator);
    loggerDebug("BQUWConfirmConfirm","MakeDate="+PubFun.getCurrentDate());
    loggerDebug("BQUWConfirmConfirm","MissionID="+request.getParameter("MissionID"));
    loggerDebug("BQUWConfirmConfirm","SubMissionID="+request.getParameter("SubMissionID"));
    
    //二核确认完毕之后聚合到人工核保结论节点	
	  EdorManuDataDeal tEdorManuDataDeal=new EdorManuDataDeal();
	  if(!tEdorManuDataDeal.submitData(mTransferData))
	  {
		  Content = "保存失败，原因是:" + tEdorManuDataDeal.getErrors().getFirstError();
	      FlagStr = "Fail";
	  }
	  mTransferData=tEdorManuDataDeal.getTransferData();
	  
    tVData.add(mTransferData);
    tVData.add(tG);
    loggerDebug("BQUWConfirmConfirm","-------------------start workflow---------------------");
		String ErrorMessage = "";
    String busiName="WorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(!tBusinessDelegate.submitData(tVData,"create",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
           ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
				   Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "保存失败";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content ="保存成功！";
		    	FlagStr = "Succ";	
	 }	 
 loggerDebug("BQUWConfirmConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
	var contract='<%=request.getParameter("ProposalContNo")%>';
	//alert(contract);
	
parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>",contract);
</script>
</html>
