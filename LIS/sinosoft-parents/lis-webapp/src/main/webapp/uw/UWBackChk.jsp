<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%
    //程序名称：UWBackChk.jsp
//程序功能：核保订正
//创建日期：2003-03-27 15:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.tb.*" %>
<%@ page import="com.sinosoft.lis.cbcheck.*" %>
<%@ page import="com.sinosoft.workflow.tb.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>         
<%
    //输出参数
    CErrors tError = null;
    //CErrors tErrors = new CErrors();
    String FlagStr = "Fail";
    String Content = "请选择一个任务!";

    GlobalInput tG = new GlobalInput();

    tG = (GlobalInput) session.getValue("GI");

    if (tG == null) {
        out.println("session has expired");
        return;
    }

    //校验处理
    //内容待填充

    //接收信息
    // 投保单列表
    LCPolSet tLCPolSet = new LCPolSet();
    LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
    TransferData mTransferData = new TransferData();
    String tUWIdea = "";
    String tUWFlag = "z";        //订正标记
    String tProposalNo = request.getParameter("ProposalNoHide");
    String tMissionId = request.getParameter("MissionId");
    String tSubMissionId = request.getParameter("SubMissionId");
    String tPrtNo = request.getParameter("PrtNo");
    String tAgentCode = request.getParameter("AgentCode");
    String tAgentGroup = request.getParameter("AgentGroup");
    String tAppntName = request.getParameter("AppntName");
    String tInsuredName = request.getParameter("InsuredName");
    tUWIdea = request.getParameter("UWIdea");
    String tActivityID = request.getParameter("ActivityID");
    loggerDebug("UWBackChk","-----------------------MissionId:" + tMissionId);

    boolean flag = false;
    if (!tProposalNo.equals("")) {

        mTransferData.setNameAndValue("UWFlag", tUWFlag);
        mTransferData.setNameAndValue("ContNo", tProposalNo);
        loggerDebug("UWBackChk","-----------------ContNo-" + tProposalNo);
        mTransferData.setNameAndValue("AppntName", tAppntName);
        mTransferData.setNameAndValue("InsuredName", tInsuredName);
        mTransferData.setNameAndValue("AgentGroup", tAgentGroup);
        mTransferData.setNameAndValue("MissionID", tMissionId);
        mTransferData.setNameAndValue("SubMissionID", tSubMissionId);
        mTransferData.setNameAndValue("UWIdea", tUWIdea);
        mTransferData.setNameAndValue("Uw_State", "5");
        //升级工作流
        mTransferData.setNameAndValue("ActivityID", tActivityID);
        mTransferData.setNameAndValue("BusiType", "1001");
		
        //SYY BEGIN
        String tResult = "";
		String tSQL_a = " select A.y from ( "
				+ " select '9999999999' x,(case when defaultoperator is not null then defaultoperator else MissionProp20 end) y from lwmission where  activityid='0000001100' "
				+ " and missionid = '"
				+ tMissionId
				+ "' "
				+ " union "
				+ " select serialno x,(case when defaultoperator is not null then defaultoperator else MissionProp20 end) y from lbmission where  activityid='0000001100' "
				+ " and missionid = '" + tMissionId + "' "
				+ " ) A order by A.x desc ";
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(tSQL_a);
		if (tResult == null || tResult.equals("")) {
			tResult = null;
		}
		mTransferData.setNameAndValue("LastUserCode",tResult);
        //SYY END
        flag = true;
    }


    try {
        if (flag == true) {
            // 准备传输数据 VData
            VData tVData = new VData();
            tVData.add(mTransferData);
            tVData.add(tG);

            // 数据传输
            //TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
            String busiName="tWorkFlowUI";
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if (tBusinessDelegate.submitData(tVData, "execute",busiName) == false) {
                int n = tBusinessDelegate.getCErrors().getErrorCount();
                for (int i = 0; i < n; i++)
                    Content = " 核保订正失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
                FlagStr = "Fail";
            }
            //如果在Catch中发现异常，则不从错误类中提取错误信息
            if (FlagStr == "Fail") {

                tError = tBusinessDelegate.getCErrors();
                if (!tError.needDealError()) {
                    Content = " 核保订正操作成功!";
                    FlagStr = "Succ";
                } else {
                    FlagStr = "Fail";
                }
            }
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        Content = Content.trim() + ".提示：异常终止!";
    }
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
    //parent.close();
    this.close();
</script>
</html>
