<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UnderwriteApply.jsp
//�����ܣ��˹��˱�Ͷ��������У��
//�������ڣ�2003-04-09 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//          zhangtao    2005-05-05
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.workflow.bq.*"%>
<%@page import="com.sinosoft.workflowengine.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //�������
    CErrors tError = null;
    String FlagStr = "";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG =(GlobalInput)session.getValue("GI");

    if (tG == null)
    {
        out.println("session has expired");
        return;
    }

    //У�鴦��
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
    String tMissionID    = request.getParameter("MissionID");
    String tSubMissionID = request.getParameter("SubMissionID");
    String tOtherNo      = request.getParameter("OtherNo");
    String tOtherNoType  = request.getParameter("OtherNoType");
    String tEdorAppName  = request.getParameter("EdorAppName");
    String tApptype      = request.getParameter("Apptype");
    String tManageCom    = request.getParameter("ManageCom");
    String tAppntName    = request.getParameter("AppntNamew");
    String tPaytoDate    = request.getParameter("PaytoDate");

    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("MissionID",tMissionID);
    tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
    tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
    tTransferData.setNameAndValue("OtherNo", tOtherNo);
    tTransferData.setNameAndValue("OtherNoType", tOtherNoType);
    tTransferData.setNameAndValue("EdorAppName", tEdorAppName);
    tTransferData.setNameAndValue("ActivityID", request.getParameter("ActivityID"));
    tTransferData.setNameAndValue("Apptype", tApptype);
    tTransferData.setNameAndValue("ManageCom", tManageCom);
    tTransferData.setNameAndValue("AppntName", tAppntName);
    tTransferData.setNameAndValue("PaytoDate", tPaytoDate);
    tTransferData.setNameAndValue("BackDate", "");
    tTransferData.setNameAndValue("BackTime", "");
    tTransferData.setNameAndValue("UWActivityStatus", "1");
    tTransferData.setNameAndValue("DeleteActivity005","1"); 
    tTransferData.setNameAndValue("DefaultOperator", tG.Operator);
    //System.out.println("---------------------tAppntName--------" + tAppntName);
    //System.out.println("---------------------tPaytoDate--------" + tPaytoDate);
    VData tVData = new VData();
    //EdorWorkFlowUI tEdorWorkFlowUI   = new EdorWorkFlowUI();
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//String busiName="tWorkFlowUI";
		String busiName="WorkFlowUI";
    try
    {
        tVData.addElement(tG);
        tVData.add(tTransferData);
        //System.out.println("==========start===submit====================");
        //tEdorWorkFlowUI.submitData(tVData, "0000000005");
        
				tBusinessDelegate.submitData(tVData, "execute",busiName);
				if(tBusinessDelegate.getCErrors().needDealError())
				{
					  		Content = tBusinessDelegate.getCErrors().getFirstError();
					    	FlagStr = "Fail";
				 }
    }
    catch(Exception ex)
    {
        Content = "�˹��˱�����ʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
        //System.out.println("==========Content==" + Content);
    }

    if ("".equals(FlagStr))
    {
        tError = tBusinessDelegate.getCErrors();
        if (!tError.needDealError())
        {
            Content ="�˹��˱�����ɹ���";
            FlagStr = "Succ";
        }
        else
        {
            //System.out.println("==========Content==" + Content);
            Content = "�˹��˱�����ʧ�ܣ�ԭ����:" + tError.getFirstError();
            FlagStr = "Fail";
        }
    }

%>

<html>
<script language="javascript">
    try
    {
        //parent.fraInterface.afterSubmit1("<%=FlagStr%>", "<%=Content%>");
    }
    catch (ex) {}
</script>
</html>
