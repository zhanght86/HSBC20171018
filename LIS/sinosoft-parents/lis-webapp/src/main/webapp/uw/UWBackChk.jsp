<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%
    //�������ƣ�UWBackChk.jsp
//�����ܣ��˱�����
//�������ڣ�2003-03-27 15:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.tb.*" %>
<%@ page import="com.sinosoft.lis.cbcheck.*" %>
<%@ page import="com.sinosoft.workflow.tb.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.service.*" %>         
<%
    //�������
    CErrors tError = null;
    //CErrors tErrors = new CErrors();
    String FlagStr = "Fail";
    String Content = "��ѡ��һ������!";

    GlobalInput tG = new GlobalInput();

    tG = (GlobalInput) session.getValue("GI");

    if (tG == null) {
        out.println("session has expired");
        return;
    }

    //У�鴦��
    //���ݴ����

    //������Ϣ
    // Ͷ�����б�
    LCPolSet tLCPolSet = new LCPolSet();
    LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
    TransferData mTransferData = new TransferData();
    String tUWIdea = "";
    String tUWFlag = "z";        //�������
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
        //����������
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
            // ׼���������� VData
            VData tVData = new VData();
            tVData.add(mTransferData);
            tVData.add(tG);

            // ���ݴ���
            //TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
            String busiName="tWorkFlowUI";
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            if (tBusinessDelegate.submitData(tVData, "execute",busiName) == false) {
                int n = tBusinessDelegate.getCErrors().getErrorCount();
                for (int i = 0; i < n; i++)
                    Content = " �˱�����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
                FlagStr = "Fail";
            }
            //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
            if (FlagStr == "Fail") {

                tError = tBusinessDelegate.getCErrors();
                if (!tError.needDealError()) {
                    Content = " �˱����������ɹ�!";
                    FlagStr = "Succ";
                } else {
                    FlagStr = "Fail";
                }
            }
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        Content = Content.trim() + ".��ʾ���쳣��ֹ!";
    }
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
    //parent.close();
    this.close();
</script>
</html>
