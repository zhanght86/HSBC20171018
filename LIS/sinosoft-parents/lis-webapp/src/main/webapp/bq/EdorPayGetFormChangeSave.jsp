<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2006-03-16, 2006-06-28
 * @direction: ��ȫ�ո��ѷ�ʽ���������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@page import="com.sinosoft.service.*" %>

<%
    String busiName="bqEdorPayGetFormChangeDealBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //�������ݱ���
    String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
    String sGetNoticeNo = request.getParameter("GetNoticeNo");
    String sActuGetNo = request.getParameter("ActuGetNo");
    String sChangeType = request.getParameter("ChangeType");
    String sFormType = request.getParameter("FormType");
    String sBankCode = request.getParameter("BankCode");
    String sBankAccNo = request.getParameter("BankAccNo");
    String sAccName = request.getParameter("AccName");
    String sPayGetName = request.getParameter("PayGetName");
    String sPersonID = request.getParameter("PersonID");

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    //TransferData
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);
    tTransferData.setNameAndValue("GetNoticeNo", sGetNoticeNo);
    tTransferData.setNameAndValue("ChangeType", sChangeType);
    tTransferData.setNameAndValue("FormType", sFormType);
    tTransferData.setNameAndValue("BankCode", sBankCode);
    tTransferData.setNameAndValue("BankAccNo", sBankAccNo);
    tTransferData.setNameAndValue("AccName", sAccName);
    tTransferData.setNameAndValue("PayGetName", sPayGetName);
    tTransferData.setNameAndValue("PersonID", sPersonID);
    tTransferData.setNameAndValue("ActuGetNo", sActuGetNo);
    //VData
    VData tVData = new VData();
    tVData.addElement(tGlobalInput);
    tVData.addElement(tTransferData);
    //��������
    tGlobalInput = null;
    tTransferData = null;

    //��̨������
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //���ú�̨����
   
    if (!tBusinessDelegate.submitData(tVData, "OPERATE",busiName))
    {
        tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
    }
   
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
