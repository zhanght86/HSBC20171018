<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * @direction: 团单保全人工核保整单层保存结果
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.workflow.bq.*" %>
<%@page import="com.sinosoft.service.*" %>

<%
    String busiName="bqGEdorGrpPolManuUWBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //后台处理标记
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //收集整理数据
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    //TransferData
    TransferData tTransferData = new TransferData();
    //VData
    VData tVData = new VData();
    tVData.addElement(tGlobalInput);

    //接收数据变量
    String sUWType = request.getParameter("UWType");
    if (sUWType == null || sUWType.trim().equals(""))
    {
        tErrors.addOneError("无法获取核保类型信息！");
    }
    else if (sUWType.trim().equalsIgnoreCase("GEdorApp"))
    {
        //保全申请结论
        String sAppUWState = request.getParameter("AppUWState");    //核保意见
        String sAppUWIdea = request.getParameter("AppUWIdea");      //核保结论
        tTransferData.setNameAndValue("UWFlag", sAppUWState);
        tTransferData.setNameAndValue("UWIdea", sAppUWIdea);
        //复核节点数据
        String sMissionID = request.getParameter("MissionID");
        String sSubMissionID = request.getParameter("SubMissionID");
        String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
        String sOtherNo = request.getParameter("OtherNo");
        String sOtherNoType = request.getParameter("OtherNoType");
        String sEdorAppName = request.getParameter("EdorAppName");
        String sAppType = request.getParameter("AppType");
        String sManageCom = request.getParameter("ManageCom");
        String sAppntName = request.getParameter("AppntName");
        String sPayToDate = request.getParameter("PayToDate");
        tTransferData.setNameAndValue("MissionID", sMissionID);
        tTransferData.setNameAndValue("SubMissionID", sSubMissionID);
        tTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);
        tTransferData.setNameAndValue("OtherNo", sOtherNo);
        tTransferData.setNameAndValue("OtherNoType", sOtherNoType);
        tTransferData.setNameAndValue("EdorAppName", sEdorAppName);
        tTransferData.setNameAndValue("Apptype", sAppType);
        tTransferData.setNameAndValue("ManageCom", sManageCom);
        tTransferData.setNameAndValue("AppntName", sAppntName);
        tTransferData.setNameAndValue("PaytoDate", sPayToDate);
        //LPEdorAppSchema
        LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
        tLPEdorAppSchema.setEdorAcceptNo(sEdorAcceptNo);
        //VData
        tVData.add(tTransferData);
        tVData.add(tLPEdorAppSchema);
        tLPEdorAppSchema = null;
        
        String busiNameWorkFlow="EdorWorkFlowUI";
   		BusinessDelegate tBusinessDelegateWorkFlow=BusinessDelegate.getBusinessDelegate();
        //调用后台处理
        //EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
        if (!tBusinessDelegateWorkFlow.submitData(tVData, "0000008006",busiNameWorkFlow))
        {
            tErrors.copyAllErrors(tBusinessDelegateWorkFlow.getCErrors());
        }
        //tEdorWorkFlowUI = null;
    }
    else if (sUWType.trim().equalsIgnoreCase("GrpCont"))
    {
        //保全申请结论
        String sGrpUWIdea = request.getParameter("GrpUWIdea");      //核保意见
        String sGrpUWState = request.getParameter("GrpUWState");    //核保结论
        tTransferData.setNameAndValue("UWFlag", sGrpUWState);
        tTransferData.setNameAndValue("UWIdea", sGrpUWIdea);
        
        //整单核保数据
        String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
        String sEdorNo = request.getParameter("EdorNo");
        String sEdorType = request.getParameter("EdorType");
        String sGrpContNo = request.getParameter("GrpContNo");
        String sGrpPolNo = request.getParameter("GrpPolNo");
        
        //LPGrpEdorMainSchema
        LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
        tLPGrpEdorMainSchema.setEdorAcceptNo(sEdorAcceptNo);
        tLPGrpEdorMainSchema.setGrpContNo(sGrpContNo);
        
        //LPGrpPolSchema
      	LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
      	mLPGrpPolSchema.setEdorNo(sEdorNo);
      	mLPGrpPolSchema.setEdorType(sEdorType);
      	mLPGrpPolSchema.setGrpContNo(sGrpContNo);
      	mLPGrpPolSchema.setGrpPolNo(sGrpPolNo);
loggerDebug("GEdorAppManuUWSave","___________sEdorNo__" + sEdorNo);
loggerDebug("GEdorAppManuUWSave","___________sEdorType__" + sEdorType);
loggerDebug("GEdorAppManuUWSave","___________sGrpContNo__" + sGrpContNo);
loggerDebug("GEdorAppManuUWSave","___________sGrpPolNo__" + sGrpPolNo);
        //VData
        tVData.addElement(tTransferData);
        tVData.addElement(tLPGrpEdorMainSchema);
        tVData.addElement(mLPGrpPolSchema);
        tLPGrpEdorMainSchema = null;
        //调用后台处理
        //GEdorGrpContManuUWBL tGEdorGrpContManuUWBL = new GEdorGrpContManuUWBL();
        
        if (!tBusinessDelegate.submitData(tVData, "",busiName))
        {
            tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
        }
       
    }
    else
    {
        tErrors.addOneError("未知的保全核保类型！");
    }

    //垃圾处理
    tGlobalInput = null;
    tTransferData = null;
    tVData = null;

    //页面反馈信息
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "操作成功！";
    }
    else
    {
        FlagStr = "Fail";
        Content = "操作失败，原因是：" + tErrors.getFirstError();
    }
    tErrors = null;
%>


<html>
<head>
    <script language="JavaScript">
        try
        {
            parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
        }
        catch (ex)
        {
            alert('<%=Content%>');
        }
    </script>
</html>
