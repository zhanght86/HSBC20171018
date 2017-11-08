<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2005-12-05
 * @direction: 保单特殊复效打印
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="com.sinosoft.lis.f1print.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.service.*" %>



<%
    //System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : 保单特殊复效打印, 开始操作");

    //接受数据变量
    String sContNo = request.getParameter("ContNo");
    String sInvalidReason = request.getParameter("InvalidReason");
    String sOperator = request.getParameter("Operator");
    String sManageCom = request.getParameter("ManageCom");
    String sStartDate = request.getParameter("StartDate");
    String sEndDate = request.getParameter("EndDate");

    //收集整理数据
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

    //处理结果标记
    String FlagStr = "";
    String Content = "";
    CErrors tErrors = new CErrors();
    VData tResultData = new VData();
    //System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : 保单特殊复效打印, 准备提交");

    //获取 VTS 临时文件路径
    String sVtsFullPath = "";
    ExeSQL tExeSQL = new ExeSQL();
    String strSQL = "select SysVarValue from LDSysVar where SysVar = 'VTSFilePath'";
    String sVtsPathInDB = tExeSQL.getOneValue(strSQL);
    tExeSQL = null;
    if (sVtsPathInDB == null || sVtsPathInDB.trim().equals(""))
    {
        System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : LDSysVar : 无法获取临时文件路径");
        FlagStr = "Fail";
        Content = "打印失败, 原因是：无法获取临时文件路径！";
    }
    else
    {
        String sWebRootPath = application.getRealPath("/").replace('\\', '/');
        String sVtsFileName = tGlobalInput.Operator + "_" + FileQueue.getFileName() + ".vts";
        //拼凑完整路径
        sVtsFullPath = (sWebRootPath + sVtsPathInDB + sVtsFileName).replaceAll("//", "/");
        //System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : VTS 完整路径 : " + sVtsFullPath);

        //调用后台处理
        //LRNSpecialAvailablePrintUI tLRNSpecialAvailablePrintUI = new LRNSpecialAvailablePrintUI();
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        
        
        if(!tBusinessDelegate.submitData(tVData, "PRINT","LRNSpecialAvailablePrintUI"))
        {
            System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : 调用 LRNSpecialAvailablePrintUI.submitData() 提交数据失败");
            tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
            FlagStr = "Fail";
            Content = "打印失败, 原因是：" + tErrors.getFirstError();
        }
        else
        {
            //检查处理结果数据
            tResultData = tBusinessDelegate.getResult();
            if (tResultData.size() <= 0)
            {
                System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : 没有符合条件的纪录");
                FlagStr = "Fail";
                Content = "没有符合条件的纪录！";
            }
            else
            {
                XmlExport tXmlExport;
                tXmlExport = (XmlExport)tResultData.getObjectByObjectName("XmlExport", 0);
                if (tXmlExport == null)
                {
                    System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : 无法获取 XmlExport 数据");
                    FlagStr = "Fail";
                    Content = "打印失败, 原因是：无法获取要显示的数据！";
                }
                else
                {
                    //保存 VTS 文件到磁盘
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
                        System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : 保存 VTS 文件到 " + sVtsFullPath + " 失败");
                        FlagStr = "Fail";
                        Content = "保存打印摸板文件失败！";
                    }
                    //垃圾处理
                    baOutputStream = null;
                    tCombineVts = null;
                } //tXmlExport != null
                //垃圾处理
                tXmlExport = null;
            } //tResultData.size() > 0
        } //tLRNSpecialAvailablePrintUI.submitData
        //垃圾处理
        tGlobalInput = null;
        tTransferData = null;
        tVData = null;
        //tLRNSpecialAvailablePrintUI = null;
        tResultData = null;
    } //sVtsPathInDB != null

    //页面反馈信息
    if (FlagStr.equals("") && !tErrors.needDealError())
    {
        //System.out.println("\t@> LRNSpecialAvailableQueryPrint.jsp : 保单特殊复效打印, 打印成功");
        FlagStr = "Success";
        Content = "打印成功！";
        //调用 F1Print.jsp 展现结果
        session.putValue("RealPath", sVtsFullPath);
        //response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
        request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
    }
    else
    {
        //调用 afterSubmit
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

    //垃圾处理
    if (tErrors != null)    tErrors = null;
%>
