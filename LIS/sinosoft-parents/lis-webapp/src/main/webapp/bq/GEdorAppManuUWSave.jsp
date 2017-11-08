<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * @direction: �ŵ���ȫ�˹��˱������㱣����
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
    //��̨������
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    //TransferData
    TransferData tTransferData = new TransferData();
    //VData
    VData tVData = new VData();
    tVData.addElement(tGlobalInput);

    //�������ݱ���
    String sUWType = request.getParameter("UWType");
    if (sUWType == null || sUWType.trim().equals(""))
    {
        tErrors.addOneError("�޷���ȡ�˱�������Ϣ��");
    }
    else if (sUWType.trim().equalsIgnoreCase("GEdorApp"))
    {
        //��ȫ�������
        String sAppUWState = request.getParameter("AppUWState");    //�˱����
        String sAppUWIdea = request.getParameter("AppUWIdea");      //�˱�����
        tTransferData.setNameAndValue("UWFlag", sAppUWState);
        tTransferData.setNameAndValue("UWIdea", sAppUWIdea);
        //���˽ڵ�����
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
        //���ú�̨����
        //EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
        if (!tBusinessDelegateWorkFlow.submitData(tVData, "0000008006",busiNameWorkFlow))
        {
            tErrors.copyAllErrors(tBusinessDelegateWorkFlow.getCErrors());
        }
        //tEdorWorkFlowUI = null;
    }
    else if (sUWType.trim().equalsIgnoreCase("GrpCont"))
    {
        //��ȫ�������
        String sGrpUWIdea = request.getParameter("GrpUWIdea");      //�˱����
        String sGrpUWState = request.getParameter("GrpUWState");    //�˱�����
        tTransferData.setNameAndValue("UWFlag", sGrpUWState);
        tTransferData.setNameAndValue("UWIdea", sGrpUWIdea);
        
        //�����˱�����
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
        //���ú�̨����
        //GEdorGrpContManuUWBL tGEdorGrpContManuUWBL = new GEdorGrpContManuUWBL();
        
        if (!tBusinessDelegate.submitData(tVData, "",busiName))
        {
            tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
        }
       
    }
    else
    {
        tErrors.addOneError("δ֪�ı�ȫ�˱����ͣ�");
    }

    //��������
    tGlobalInput = null;
    tTransferData = null;
    tVData = null;

    //ҳ�淴����Ϣ
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "�����ɹ���";
    }
    else
    {
        FlagStr = "Fail";
        Content = "����ʧ�ܣ�ԭ���ǣ�" + tErrors.getFirstError();
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
