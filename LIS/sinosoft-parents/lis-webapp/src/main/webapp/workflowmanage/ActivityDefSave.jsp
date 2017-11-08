<%@include file="../common/jsp/UsrCheck.jsp" %>
<%
    /**
     * Created by IntelliJ IDEA.
     * User: jinsh
     * Date: 2009-1-7
     * Time: 15:32:15
     * To change this template use File | Settings | File Templates.
     */
%><!--用户校验类-->
<%@page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
    BusinessDelegate tBusinessDelegate; 
    TransferData tTransferData = new TransferData();
    /**
     * 从前台获取一些业务字段
     */
    String OperFlag = request.getParameter("OperFlag");
    if ("INSERT||Activity".equalsIgnoreCase(OperFlag) || "MODIFY||Activity".equalsIgnoreCase(OperFlag) || "DELETE||Activity".equalsIgnoreCase(OperFlag))
    {
        String tActivityID = request.getParameter("ActivityID");
        String tActivityName = request.getParameter("ActivityName");
        String tActivityType = request.getParameter("ActivityType");
        String tActivityDesc = request.getParameter("ActivityDesc");
        String tBeforeInit = request.getParameter("BeforeInit");
        String tBeforeInitType = request.getParameter("BeforeInitType");
        String tAfterInit = request.getParameter("AfterInit");
        String tAfterInitType = request.getParameter("AfterInitType");
        String tBeforeEnd = request.getParameter("BeforeEnd");
        String tBeforeEndType = request.getParameter("BeforeEndType");
        String tAfterEnd = request.getParameter("AfterEnd");
        String tAfterEndType = request.getParameter("AfterEndType");
        String tWatingTime = request.getParameter("WatingTime");
        String tWorkingTime = request.getParameter("WorkingTime");
        String tTimeOut = request.getParameter("TimeOut");
        String tOperator = request.getParameter("Operator");
        String tMakeDate = request.getParameter("MakeDate");
        String tMakeTime = request.getParameter("MakeTime");
        String tModifyDate = request.getParameter("ModifyDate");
        String tModifyTime = request.getParameter("ModifyTime");
        String tBusiType = request.getParameter("BusiType");
        String tIsNeed = request.getParameter("IsNeed");
        String tActivityFlag = request.getParameter("ActivityFlag");
        String tImpDegree = request.getParameter("ImpDegree");
        // jiyongtian 增加聚合模式 begin
        String tTogether = request.getParameter("Together");
        String tCreateActionType = request.getParameter("CreateActionType");
        String tCreateAction = request.getParameter("CreateAction");
        String tApplyActionType = request.getParameter("ApplyActionType");
        String tApplyAction = request.getParameter("ApplyAction");
        String tDeleteActionType = request.getParameter("DeleteActionType");
        String tDeleteAction = request.getParameter("DeleteAction");    
        // end
        String tFunctionID = request.getParameter("FunctionID");
        tTransferData.setNameAndValue("ActivityID", tActivityID);
        tTransferData.setNameAndValue("ActivityName", tActivityName);
        tTransferData.setNameAndValue("ActivityType", tActivityType);
        tTransferData.setNameAndValue("ActivityDesc", tActivityDesc);
        tTransferData.setNameAndValue("BeforeInit", tBeforeInit);
        tTransferData.setNameAndValue("BeforeInitType", tBeforeInitType);
        tTransferData.setNameAndValue("AfterInit", tAfterInit);
        tTransferData.setNameAndValue("AfterInitType", tAfterInitType);
        tTransferData.setNameAndValue("BeforeEnd", tBeforeEnd);
        tTransferData.setNameAndValue("BeforeEndType", tBeforeEndType);
        tTransferData.setNameAndValue("AfterEnd", tAfterEnd);
        tTransferData.setNameAndValue("AfterEndType", tAfterEndType);
        tTransferData.setNameAndValue("WatingTime", tWatingTime);
        tTransferData.setNameAndValue("WorkingTime", tWorkingTime);
        tTransferData.setNameAndValue("TimeOut", tTimeOut);
        tTransferData.setNameAndValue("Operator", tOperator);
        tTransferData.setNameAndValue("MakeDate", tMakeDate);
        tTransferData.setNameAndValue("MakeTime", tMakeTime);
        tTransferData.setNameAndValue("ModifyDate", tModifyDate);
        tTransferData.setNameAndValue("ModifyTime", tModifyTime);
        tTransferData.setNameAndValue("BusiType", tBusiType);
        tTransferData.setNameAndValue("IsNeed", tIsNeed);
        tTransferData.setNameAndValue("ActivityFlag", tActivityFlag);
        tTransferData.setNameAndValue("ImpDegree", tImpDegree);
        tTransferData.setNameAndValue("FunctionID", tFunctionID);
        tTransferData.setNameAndValue("Together", tTogether);
        tTransferData.setNameAndValue("CreateActionType", tCreateActionType);
        tTransferData.setNameAndValue("CreateAction", tCreateAction);
        tTransferData.setNameAndValue("ApplyActionType", tApplyActionType);
        tTransferData.setNameAndValue("ApplyAction", tApplyAction);
        tTransferData.setNameAndValue("DeleteActionType", tDeleteActionType);
        tTransferData.setNameAndValue("DeleteAction", tDeleteAction);
        
        
    }
    if ("INSERT||Param".equalsIgnoreCase(OperFlag) || "MODIFY||Param".equalsIgnoreCase(OperFlag) || "DELETE||Param".equalsIgnoreCase(OperFlag))
    {
        String tActivityID = request.getParameter("ActivityID");
        String tActivityName = request.getParameter("ActivityName");
        String tFieldOrder = request.getParameter("hiddenFieldOrder");
        String tSourFieldName = request.getParameter("SourFieldName");
        String tSourFieldCName = request.getParameter("SourFieldCName");
        String tDestFieldName = request.getParameter("DestFieldName");
        String tDestFieldCName = request.getParameter("DestFieldCName");
        tTransferData.setNameAndValue("ActivityID", tActivityID);
        tTransferData.setNameAndValue("ActivityName", tActivityName);
        tTransferData.setNameAndValue("FieldOrder", tFieldOrder);
        tTransferData.setNameAndValue("SourFieldName", tSourFieldName);
        tTransferData.setNameAndValue("SourFieldCName", tSourFieldCName);
        tTransferData.setNameAndValue("DestFieldName", tDestFieldName);
        tTransferData.setNameAndValue("DestFieldCName", tDestFieldCName);
    }
    if ("INSERT||Condition".equalsIgnoreCase(OperFlag) || "MODIFY||Condition".equalsIgnoreCase(OperFlag) || "DELETE||Condition".equalsIgnoreCase(OperFlag))
    {
        String tBusiType = request.getParameter("BusiType");
        String tBusiTypeName = request.getParameter("BusiTypeName");
        String tStartActivityID = request.getParameter("StartActivityID");
        String tStartActivityName = request.getParameter("StartActivityName");
        String tEndActivityID = request.getParameter("EndActivityID");
        String tEndActivityName = request.getParameter("EndActivityName");
        String tTransitionCondT = request.getParameter("TransitionCondT");
        String tTransitionCondTName = request.getParameter("TransitionCondTName");
        String tTransitionCond = request.getParameter("TransitionCond");
        String tCondDesc = request.getParameter("CondDesc");
        tTransferData.setNameAndValue("BusiType", tBusiType);
        tTransferData.setNameAndValue("BusiTypeName", tBusiTypeName);
        tTransferData.setNameAndValue("StartActivityID", tStartActivityID);
        tTransferData.setNameAndValue("StartActivityName", tStartActivityName);
        tTransferData.setNameAndValue("EndActivityID", tEndActivityID);
        tTransferData.setNameAndValue("EndActivityName", tEndActivityName);
        tTransferData.setNameAndValue("TransitionCondT", tTransitionCondT);
        tTransferData.setNameAndValue("TransitionCondTName", tTransitionCondTName);
        tTransferData.setNameAndValue("TransitionCond", tTransitionCond);
        tTransferData.setNameAndValue("CondDesc", tCondDesc);
    }
    /**
     * 获取页面 session
     * **/
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    /**
     * 初始化后始实例,并进行数据的封装,完后进行调用
     * */
 
    VData mVData = new VData();
    mVData.add(tG);
    mVData.add(tTransferData);
    /**
     * 调用完成后和页面进行交互
     * */
    String FlagStr="";      //操作结果
    String Content = "";    //控制台信息
    tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(mVData, OperFlag, "ActivityDefUI"))
  	{
		  Content = " 保存失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
	   	FlagStr = "Fail";
	  }
	  else
	  {
	  	Content = " 保存成功! ";
		  FlagStr = "Succ";
    }

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
