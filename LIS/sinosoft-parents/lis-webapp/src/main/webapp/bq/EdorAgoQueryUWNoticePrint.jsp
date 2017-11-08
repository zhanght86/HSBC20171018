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
 * @date     : 2005-12-15
 * @direction: ��ȫ�˹��˱�Ͷ(��)���˼�����ȫ��ѯ�˱�֪ͨ���ӡ
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="com.sinosoft.lis.f1print.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.* "%>
<%@ page import="com.sinosoft.utility.*" %>


<%
    //loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : ������ȫ��ѯ�˱�֪ͨ���ӡ, ��ʼ����");

    //�������ݱ���
    String sPrtSeq = request.getParameter("PrtSeq");
    String sManageCom = request.getParameter("ManageCom");
    String sOperator = request.getParameter("Operator");

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput.ManageCom = sManageCom;
    tGlobalInput.Operator = sOperator;
    //LOPRTManagerSchema
    LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
    tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
    //VData
    VData tVData = new VData();
    tVData.addElement(tGlobalInput);
    tVData.addElement(tLOPRTManagerSchema);

    //���������
    String FlagStr = "";
    String Content = "";
    CErrors tErrors = new CErrors();
    VData tResultData = new VData();
    //loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : ������ȫ��ѯ�˱�֪ͨ���ӡ, ׼���ύ");

    //��ȡ VTS ��ʱ�ļ�·��
    String sVtsFullPath = "";
    ExeSQL tExeSQL = new ExeSQL();
    String strSQL = "select SysVarValue from LDSysVar where SysVar = 'VTSFilePath'";
    String sVtsPathInDB = tExeSQL.getOneValue(strSQL);
    tExeSQL = null;
    if (sVtsPathInDB == null || sVtsPathInDB.trim().equals(""))
    {
        loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : LDSysVar : �޷���ȡ��ʱ�ļ�·��");
        FlagStr = "Fail";
        Content = "��ӡʧ��, ԭ���ǣ��޷���ȡ��ʱ�ļ�·����";
    }
    else
    {
        String sWebRootPath = application.getRealPath("/").replace('\\', '/');
        String sVtsFileName = tGlobalInput.Operator + "_" + FileQueue.getFileName() + ".vts";
        //ƴ������·��
        sVtsFullPath = (sWebRootPath + sVtsPathInDB + sVtsFileName).replaceAll("//", "/");
        //loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : VTS ����·�� : " + sVtsFullPath);

        //���ú�̨����
        EdorUWNoticeUI tEdorUWNoticeUI = new EdorUWNoticeUI();
        if(!tEdorUWNoticeUI.submitData(tVData, "PRINT"))
        {
            loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : ���� EdorUWNoticeUI.submitData() �ύ����ʧ��");
            tErrors.copyAllErrors(tEdorUWNoticeUI.getErrors());
            FlagStr = "Fail";
            Content = "��ӡʧ��, ԭ���ǣ�" + tErrors.getFirstError();
        }
        else
        {
            //��鴦��������
            tResultData = tEdorUWNoticeUI.getResult();
            if (tResultData.size() <= 0)
            {
                loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : û�з��������ļ�¼");
                FlagStr = "Fail";
                Content = "û�з��������ļ�¼��";
            }
            else
            {
                XmlExport tXmlExport;
                tXmlExport = (XmlExport)tResultData.getObjectByObjectName("XmlExport", 0);
                if (tXmlExport == null)
                {
                    loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : �޷���ȡ XmlExport ����");
                    FlagStr = "Fail";
                    Content = "��ӡʧ��, ԭ���ǣ��޷���ȡҪ��ʾ�����ݣ�";
                }
                else
                {
                    //���� VTS �ļ�������
                    String sVtsTempPath = application.getRealPath("f1print/NCLtemplate/") + "\\";
                    CombineVts tCombineVts;
                    ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
                    try
                    {
                        tCombineVts = new CombineVts(tXmlExport.getInputStream(), sVtsTempPath);
                        tCombineVts.output(baOutputStream);
                        AccessVtsFile.saveToFile(baOutputStream, sVtsFullPath);
                    }
                    catch (Exception ex)
                    {
                        loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : ���� VTS �ļ��� " + sVtsFullPath + " ʧ��");
                        FlagStr = "Fail";
                        Content = "�����ӡ�����ļ�ʧ�ܣ�";
                    }
                    //��������
                    baOutputStream = null;
                    tCombineVts = null;
                } //tXmlExport != null
                //��������
                tXmlExport = null;
            } //tResultData.size() > 0
        } //tEdorUWNoticeUI.submitData
        //��������
        tGlobalInput = null;
        tLOPRTManagerSchema = null;
        tVData = null;
        tEdorUWNoticeUI = null;
        tResultData = null;
    } //sVtsPathInDB != null

    //ҳ�淴����Ϣ
    if (FlagStr.equals("") && !tErrors.needDealError())
    {
        //loggerDebug("EdorAgoQueryUWNoticePrint","\t@> EdorAgoQueryUWNoticePrint.jsp : ������ȫ��ѯ�˱�֪ͨ���ӡ, ��ӡ�ɹ�");
        FlagStr = "Success";
        Content = "��ӡ�ɹ���";
        //���� F1Print.jsp չ�ֽ��
        session.putValue("RealPath", sVtsFullPath);
        //response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
        request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
    }
    else
    {
        //���� afterSubmit
        out.println("<html>");
        out.println("<head>");
        out.println("<script language=JavaScript>");
        out.println("    function ShowInfo()");
        out.println("    {");
        out.println("        try");
        out.println("        {");
        out.println("            top.opener.afterSubmit('" + FlagStr + "', '" + Content + "');");
        out.println("        }");
        out.println("        catch (ex)");
        out.println("        {");
        out.println("            alert('" + Content + "');");
        out.println("        }");
        out.println("    }");
        out.println("</script>");
        out.println("</head>");
        out.println("<body onunload='ShowInfo();'></body>");
        out.println("<script language=JavaScript>");
        out.println("    window.close();");
        out.println("</script>");
        out.println("</html>");
    }

    //��������
    if (tErrors != null)    tErrors = null;
%>
