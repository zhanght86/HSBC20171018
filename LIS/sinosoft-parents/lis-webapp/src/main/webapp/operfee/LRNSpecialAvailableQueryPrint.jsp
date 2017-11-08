<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

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
 * @date     : 2005-12-05
 * @direction: �������⸴Ч��ӡ
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="com.sinosoft.lis.f1print.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.service.*" %>



<%
    //System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : �������⸴Ч��ӡ, ��ʼ����");

    //�������ݱ���
    String sContNo = request.getParameter("ContNo");
    String sInvalidReason = request.getParameter("InvalidReason");
    String sOperator = request.getParameter("Operator");
    String sManageCom = request.getParameter("ManageCom");
    String sStartDate = request.getParameter("StartDate");
    String sEndDate = request.getParameter("EndDate");

    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    //TransferData
    TransferData tTransferData = new TransferData();
    if (sContNo != null && !sContNo.trim().equals(""))                  tTransferData.setNameAndValue("ContNo", sContNo);
    if (sInvalidReason != null && !sInvalidReason.trim().equals(""))    tTransferData.setNameAndValue("InvalidReason", sInvalidReason);
    if (sOperator != null && !sOperator.trim().equals(""))              tTransferData.setNameAndValue("Operator", sOperator);
    if (sManageCom != null && !sManageCom.trim().equals(""))            tTransferData.setNameAndValue("ManageCom", sManageCom);
    if (sStartDate != null && !sStartDate.trim().equals(""))            tTransferData.setNameAndValue("StartDate", sStartDate);
    if (sEndDate != null && !sEndDate.trim().equals(""))                tTransferData.setNameAndValue("EndDate", sEndDate);
    //VData
    VData tVData = new VData();
    tVData.addElement(tGlobalInput);
    tVData.addElement(tTransferData);

    //���������
    String FlagStr = "";
    String Content = "";
    CErrors tErrors = new CErrors();
    VData tResultData = new VData();
    //System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : �������⸴Ч��ӡ, ׼���ύ");

    //��ȡ VTS ��ʱ�ļ�·��
    String sVtsFullPath = "";
    ExeSQL tExeSQL = new ExeSQL();
    String strSQL = "select SysVarValue from LDSysVar where SysVar = 'VTSFilePath'";
    String sVtsPathInDB = tExeSQL.getOneValue(strSQL);
    tExeSQL = null;
    if (sVtsPathInDB == null || sVtsPathInDB.trim().equals(""))
    {
        System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : LDSysVar : �޷���ȡ��ʱ�ļ�·��");
        FlagStr = "Fail";
        Content = "��ӡʧ��, ԭ���ǣ��޷���ȡ��ʱ�ļ�·����";
    }
    else
    {
        String sWebRootPath = application.getRealPath("/").replace('\\', '/');
        String sVtsFileName = tGlobalInput.Operator + "_" + FileQueue.getFileName() + ".vts";
        //ƴ������·��
        sVtsFullPath = (sWebRootPath + sVtsPathInDB + sVtsFileName).replaceAll("//", "/");
        //System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : VTS ����·�� : " + sVtsFullPath);

        //���ú�̨����
        //LRNSpecialAvailablePrintUI tLRNSpecialAvailablePrintUI = new LRNSpecialAvailablePrintUI();
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        
        
        if(!tBusinessDelegate.submitData(tVData, "PRINT","LRNSpecialAvailablePrintUI"))
        {
            System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : ���� LRNSpecialAvailablePrintUI.submitData() �ύ����ʧ��");
            tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
            FlagStr = "Fail";
            Content = "��ӡʧ��, ԭ���ǣ�" + tErrors.getFirstError();
        }
        else
        {
            //��鴦��������
            tResultData = tBusinessDelegate.getResult();
            if (tResultData.size() <= 0)
            {
                System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : û�з��������ļ�¼");
                FlagStr = "Fail";
                Content = "û�з��������ļ�¼��";
            }
            else
            {
                XmlExport tXmlExport;
                tXmlExport = (XmlExport)tResultData.getObjectByObjectName("XmlExport", 0);
                if (tXmlExport == null)
                {
                    System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : �޷���ȡ XmlExport ����");
                    FlagStr = "Fail";
                    Content = "��ӡʧ��, ԭ���ǣ��޷���ȡҪ��ʾ�����ݣ�";
                }
                else
                {
                    //���� VTS �ļ�������
                    String sVtsTempPath = application.getRealPath("f1print/MStemplate/") + "/";
                    System.out.println("========"+sVtsTempPath);
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
                        System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : ���� VTS �ļ��� " + sVtsFullPath + " ʧ��");
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
        } //tLRNSpecialAvailablePrintUI.submitData
        //��������
        tGlobalInput = null;
        tTransferData = null;
        tVData = null;
        //tLRNSpecialAvailablePrintUI = null;
        tResultData = null;
    } //sVtsPathInDB != null

    //ҳ�淴����Ϣ
    if (FlagStr.equals("") && !tErrors.needDealError())
    {
        //System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : �������⸴Ч��ӡ, ��ӡ�ɹ�");
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
