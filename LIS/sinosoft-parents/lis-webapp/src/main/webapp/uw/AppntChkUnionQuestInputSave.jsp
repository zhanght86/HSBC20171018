<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    //�������ƣ�AppntChkUnionQuestInputSave.jsp
//�����ܣ�
//�������ڣ�2005-04-20 16:49:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.db.*" %>
<%@ page import="com.sinosoft.lis.vdb.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.cbcheck.*" %>
<%@page import="com.sinosoft.service.*" %>
<%

    //�������
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
        Content = "���������Ϊ��,������¼��!";
        FlagStr = "Fail";

        loggerDebug("AppntChkUnionQuestInputSave","ʧ��");
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
        Content = "���������Ϊ��,������¼��!";
        FlagStr = "Fail";
        flag = false;
        loggerDebug("AppntChkUnionQuestInputSave","ʧ��");
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
            Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
            loggerDebug("AppntChkUnionQuestInputSave","aaaa" + ex.toString());
            FlagStr = "Fail";
        }

        if ("".equals(FlagStr)) {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError()) {
                Content = "����ɹ���";
                FlagStr = "Succ";
            } else {
                Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
   
