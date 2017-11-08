<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    //程序名称：AppntChkUnionQuestInputSave.jsp
//程序功能：
//创建日期：2005-04-20 16:49:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.db.*" %>
<%@ page import="com.sinosoft.lis.vdb.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.cbcheck.*" %>
<%@page import="com.sinosoft.service.*" %>
<%

    //输出参数
    String FlagStr = "";
    String Content = "";

    CErrors tError = null;
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    VData tVData = new VData();

    LCContSet tLCContSet = new LCContSet();
    LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();

    String tContNo = request.getParameter("ProposalNo");
    String tFlag = request.getParameter("Flag");
    String tBackObj = "2";
    String tQuest = "99";
    String tContent = request.getParameter("CUIContent");
    String tQuestionObj = request.getParameter("CustomerNo_OLD");
    String tQuestionObjName = request.getParameter("CustomerName");
    String tStandbyFlag1 = request.getParameter("CustomerNo_NEW");
    String tMissionID = request.getParameter("MissionID");
    String tSubMissionID = request.getParameter("SubMissionID");
    String tActivityID = request.getParameter("ActivityID");

    String tQuestionObjValue = "0";
    String mFlag = "1";

    LCContSchema tLCContSchema = new LCContSchema();
    tLCContSchema.setContNo(tContNo);
    tLCContSchema.setState(mFlag);
    tLCContSet.add(tLCContSchema);

    LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
    tLCIssuePolSchema.setIssueCont(tContent);
    loggerDebug("AppntChkUnionQuestInputSave",tContent);
    if (tContent.equals("")) {
        Content = "问题件内容为空,不允许录入!";
        FlagStr = "Fail";

        loggerDebug("AppntChkUnionQuestInputSave","失败");
    }
    tLCIssuePolSchema.setOperatePos(tFlag);
    tLCIssuePolSchema.setBackObjType(tBackObj);
    tLCIssuePolSchema.setIssueType(tQuest);
    tLCIssuePolSchema.setQuestionObjType(tQuestionObjValue);
    tLCIssuePolSchema.setQuestionObj(tQuestionObj);
    tLCIssuePolSchema.setQuestionObjName(tQuestionObjName);
    tLCIssuePolSchema.setStandbyFlag1(tStandbyFlag1);
    tLCIssuePolSet.add(tLCIssuePolSchema);

    //add by zhangxing
    boolean flag = true;
    if (tContent.equals("")) {
        Content = "问题件内容为空,不允许录入!";
        FlagStr = "Fail";
        flag = false;
        loggerDebug("AppntChkUnionQuestInputSave","失败");
    }
    if (flag == true) {
      //  QuestInputChkUI tQuestInputChkUI = new QuestInputChkUI();
       String busiName="cbcheckQuestInputChkUI";
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

        try {
            TransferData tTransferData = new TransferData();
            loggerDebug("AppntChkUnionQuestInputSave","contno=" + tContNo);

            tTransferData.setNameAndValue("MissionID", tMissionID);
            tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
            tTransferData.setNameAndValue("ActivityID", tActivityID);

            tVData.add(tG);
            tVData.add(tLCContSet);
            tVData.add(tLCIssuePolSet);
            tVData.add(tTransferData);

           
            
            tBusinessDelegate.submitData(tVData, "INSERT",busiName);
        }
        catch (Exception ex) {
            Content = "保存失败，原因是:" + ex.toString();
            loggerDebug("AppntChkUnionQuestInputSave","aaaa" + ex.toString());
            FlagStr = "Fail";
        }

        if ("".equals(FlagStr)) {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError()) {
                Content = "保存成功！";
                FlagStr = "Succ";
            } else {
                Content = "保存失败，原因是:" + tError.getFirstError();
                FlagStr = "Fail";
            }
        }
    }
%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
   
