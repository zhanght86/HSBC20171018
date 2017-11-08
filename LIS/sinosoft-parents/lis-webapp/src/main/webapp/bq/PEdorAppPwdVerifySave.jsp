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
 * @date     : 2006-11-06
 * @direction: 保全申请添加批改项目校验保单密码保存
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.lis.bq.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //接收数据变量
    String sOtherNo = request.getParameter("OtherNo");
    String sOtherNoType = request.getParameter("OtherNoType");

    //保单密码信息
    String arrGridNo[] = request.getParameterValues("ContPwdGridNo");
    String arrContNo[] = request.getParameterValues("ContPwdGrid1");
    String arrPassword[] = request.getParameterValues("ContPwdGrid7");

    //后台处理标记
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //收集整理数据
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    //LCContSet
    LCContSet tLCContSet = new LCContSet();
    if (arrGridNo != null)
    {
        for (int i = 0; i < arrGridNo.length; i++)
        {
            LCContSchema tLCContSchema = new LCContSchema();
            tLCContSchema.setContNo(arrContNo[i]);
            tLCContSchema.setPassword(arrPassword[i]);
            tLCContSet.add(tLCContSchema);
            tLCContSchema = null;
        }
    }

    //VData
    VData tVData = new VData();
    tVData.add(tGlobalInput);
    tVData.add(tLCContSet);
    //垃圾处理
    tGlobalInput = null;
    tLCContSet = null;

    //调用后台处理
    //PEdorContPwdBL
    PEdorContPwdBL tPEdorContPwdBL = new PEdorContPwdBL();
    if (!tPEdorContPwdBL.submitData(tVData, "verify"))
    {
        tErrors.copyAllErrors(tPEdorContPwdBL.getErrors());
    }
    tPEdorContPwdBL = null;
    tVData = null;

    //页面反馈信息
    if (!tErrors.needDealError())
    {
        FlagStr = "Success";
        Content = "操作成功！";
    }
    else
    {
        FlagStr = "Fail";
        Content = "操作失败，原因是：" + tErrors.getFirstError();
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
