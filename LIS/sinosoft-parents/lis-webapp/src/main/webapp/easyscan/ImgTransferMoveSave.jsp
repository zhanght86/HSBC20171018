<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2006-12-07, 2006-12-18
 * @direction: 影像迁移保存远程调用层
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //接收数据变量
    String sOldManageCom = request.getParameter("OldManageCom");
    String sNewManageCom = request.getParameter("NewManageCom");
    String sStartDate = request.getParameter("StartDate");
    String sEndDate = request.getParameter("EndDate");
    String sDocID = request.getParameter("DocID"); //重传才会传过此参数

    //收集整理数据
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    //后台处理标记
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //远程地址变量
    String sRemoteAddr = new String("");
    String sRemoteHost = new String("");
    String sRemotePage = new String("");
    String sPageParams = new String("");

    //查询远程地址
    String QuerySQL = new String("");
    QuerySQL = "select SysVarValue from LDSysVar where SysVar = 'TransferPath'";
    ExeSQL tExeSQL = new ExeSQL();
    try
    {
        sRemoteHost = tExeSQL.getOneValue(QuerySQL);
    }
    catch (Exception ex)
    {
        tErrors.addOneError("查询获取远程应用服务器地址发生异常！");
    }
    if (sRemoteHost == null || sRemoteHost.trim().equals(""))
    {
        tErrors.addOneError("要调用的远程应用服务器地址不能为空！");
    }
    else if (!sRemoteHost.endsWith("/"))
    {
        sRemoteHost += "/";
    }
    tExeSQL = null;

    if (!tErrors.needDealError())
    {
        //拼凑页面参数
        sRemotePage = "easyscan/ImgMoveSave.jsp";
        sPageParams = "?OldManageCom=" + sOldManageCom 
                    + "&NewManageCom=" + sNewManageCom 
                    + "&StartDate=" + sStartDate 
                    + "&EndDate=" + sEndDate 
                    + "&LoginManageCom=" + tGlobalInput.ManageCom 
                    + "&LoginOperator=" + tGlobalInput.Operator
                    + "&DocID=" + sDocID;

        //合成完整路径
        sRemoteAddr = sRemoteHost + sRemotePage + sPageParams;
        loggerDebug("ImgTransferMoveSave","\t@> ImgTransferMoveSave.jsp : 远程调用地址的完整路径是：");
        loggerDebug("ImgTransferMoveSave","\t@> " + sRemoteAddr);

        try
        {
            //打开远程地址
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

            //存放返回信息
            String sResponseText = new String("");
            sResponseText = baOutputStream.toString().trim();
            loggerDebug("ImgTransferMoveSave","\t@> ImgTransferMoveSave.jsp : 远程地址的返回结果是：");
            loggerDebug("ImgTransferMoveSave","\t@> " + sResponseText);

            //判断返回结果
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
            tErrors.addOneError("打开远程应用服务器执行程序时发生异常！");
            ex.printStackTrace();
        }
    } //!tErrors.needDealError()

    //页面反馈信息
    if (tErrors.needDealError())
    {
        FlagStr = "Fail";
        Content = "迁移验证失败，原因是：" + tErrors.getFirstError();
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
