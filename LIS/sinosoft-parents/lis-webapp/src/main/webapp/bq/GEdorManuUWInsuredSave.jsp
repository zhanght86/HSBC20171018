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
 * @version  : 1.00
 * @date     : 2006-07-05
 * @direction: �ŵ���ȫ�˹��˱��ֵ�����
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.lis.bq.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //�������ݱ���
    String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
    String sEdorNo = request.getParameter("EdorNo");
    String sEdorType = request.getParameter("EdorType");
    String sContNo = request.getParameter("ContNo");
    String sPolNo = request.getParameter("PolNo");
    String sUWState = request.getParameter("UWState");
    String sUWIdea = request.getParameter("UWIdea");

    //�ռ���������
    
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    
    //TransferData
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorAcceptNo", sEdorAcceptNo);
    tTransferData.setNameAndValue("EdorNo", sEdorNo);
    tTransferData.setNameAndValue("ContNo", sContNo);
    tTransferData.setNameAndValue("UWFlag", sUWState);
    tTransferData.setNameAndValue("UWIdea", sUWIdea);

		//LPPolSchema
    LPPolSchema mLPPolSchema = new LPPolSchema();
    mLPPolSchema.setEdorNo(sEdorNo);
    mLPPolSchema.setEdorType(sEdorType);
    mLPPolSchema.setContNo(sContNo);
    mLPPolSchema.setPolNo(sPolNo);
    //mLPPolSchema.setInsuredNo("000000");		

    //VData
    VData tVData = new VData();
    tVData.addElement(tGlobalInput);
    tVData.addElement(tTransferData);
    tVData.addElement(mLPPolSchema);
    
    //��������
    tGlobalInput = null;
    tTransferData = null;

    //��̨������
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //���ú�̨����
    //GEdorContManuUWBL tGEdorContManuUWBL = new GEdorContManuUWBL();
    PEdorPolManuUWBL tGEdorContManuUWBL = new PEdorPolManuUWBL();
    if (!tGEdorContManuUWBL.submitData(tVData, ""))
    {
        tErrors.copyAllErrors(tGEdorContManuUWBL.mErrors);
    }
    tGEdorContManuUWBL = null;
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
