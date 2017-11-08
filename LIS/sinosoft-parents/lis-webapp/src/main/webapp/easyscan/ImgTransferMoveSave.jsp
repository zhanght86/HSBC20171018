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
 * @date     : 2006-12-07, 2006-12-18
 * @direction: Ӱ��Ǩ�Ʊ���Զ�̵��ò�
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //�������ݱ���
    String sOldManageCom = request.getParameter("OldManageCom");
    String sNewManageCom = request.getParameter("NewManageCom");
    String sStartDate = request.getParameter("StartDate");
    String sEndDate = request.getParameter("EndDate");
    String sDocID = request.getParameter("DocID"); //�ش��Żᴫ���˲���

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    //��̨������
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //Զ�̵�ַ����
    String sRemoteAddr = new String("");
    String sRemoteHost = new String("");
    String sRemotePage = new String("");
    String sPageParams = new String("");

    //��ѯԶ�̵�ַ
    String QuerySQL = new String("");
    QuerySQL = "select SysVarValue from LDSysVar where SysVar = 'TransferPath'";
    ExeSQL tExeSQL = new ExeSQL();
    try
    {
        sRemoteHost = tExeSQL.getOneValue(QuerySQL);
    }
    catch (Exception ex)
    {
        tErrors.addOneError("��ѯ��ȡԶ��Ӧ�÷�������ַ�����쳣��");
    }
    if (sRemoteHost == null || sRemoteHost.trim().equals(""))
    {
        tErrors.addOneError("Ҫ���õ�Զ��Ӧ�÷�������ַ����Ϊ�գ�");
    }
    else if (!sRemoteHost.endsWith("/"))
    {
        sRemoteHost += "/";
    }
    tExeSQL = null;

    if (!tErrors.needDealError())
    {
        //ƴ��ҳ�����
        sRemotePage = "easyscan/ImgMoveSave.jsp";
        sPageParams = "?OldManageCom=" + sOldManageCom 
                    + "&NewManageCom=" + sNewManageCom 
                    + "&StartDate=" + sStartDate 
                    + "&EndDate=" + sEndDate 
                    + "&LoginManageCom=" + tGlobalInput.ManageCom 
                    + "&LoginOperator=" + tGlobalInput.Operator
                    + "&DocID=" + sDocID;

        //�ϳ�����·��
        sRemoteAddr = sRemoteHost + sRemotePage + sPageParams;
        loggerDebug("ImgTransferMoveSave","\t@> ImgTransferMoveSave.jsp : Զ�̵��õ�ַ������·���ǣ�");
        loggerDebug("ImgTransferMoveSave","\t@> " + sRemoteAddr);

        try
        {
            //��Զ�̵�ַ
            URL tURL = new URL(sRemoteAddr);
            URLConnection tURLConnection = tURL.openConnection();
            tURLConnection.connect();
            InputStream tInputStream = tURLConnection.getInputStream();
            ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
            int nReadChar = tInputStream.read();
            while (nReadChar != -1)
            {
                baOutputStream.write(nReadChar);
                nReadChar = tInputStream.read();
            }
            tInputStream.close();
            baOutputStream.close();

            //��ŷ�����Ϣ
            String sResponseText = new String("");
            sResponseText = baOutputStream.toString().trim();
            loggerDebug("ImgTransferMoveSave","\t@> ImgTransferMoveSave.jsp : Զ�̵�ַ�ķ��ؽ���ǣ�");
            loggerDebug("ImgTransferMoveSave","\t@> " + sResponseText);

            //�жϷ��ؽ��
            if (sResponseText != null && !sResponseText.trim().equals(""))
            {
                String[] arrResponseText = sResponseText.trim().split("~");
                //loggerDebug("ImgTransferMoveSave",arrResponseText.length);
                FlagStr = arrResponseText[1];
                Content = arrResponseText[2];
            }
        }
        catch (Exception ex)
        {
            tErrors.addOneError("��Զ��Ӧ�÷�����ִ�г���ʱ�����쳣��");
            ex.printStackTrace();
        }
    } //!tErrors.needDealError()

    //ҳ�淴����Ϣ
    if (tErrors.needDealError())
    {
        FlagStr = "Fail";
        Content = "Ǩ����֤ʧ�ܣ�ԭ���ǣ�" + tErrors.getFirstError();
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
