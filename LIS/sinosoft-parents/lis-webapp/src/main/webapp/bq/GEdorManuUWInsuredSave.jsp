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
 * @version  : 1.00
 * @date     : 2006-07-05
 * @direction: 团单保全人工核保分单层结果
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.lis.bq.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.utility.*" %>

<%
    //接收数据变量
    String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
    String sEdorNo = request.getParameter("EdorNo");
    String sEdorType = request.getParameter("EdorType");
    String sContNo = request.getParameter("ContNo");
    String sPolNo = request.getParameter("PolNo");
    String sUWState = request.getParameter("UWState");
    String sUWIdea = request.getParameter("UWIdea");

    //收集整理数据
    
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
    
    //垃圾处理
    tGlobalInput = null;
    tTransferData = null;

    //后台处理标记
    String FlagStr = new String("");
    String Content = new String("");
    CErrors tErrors = new CErrors();

    //调用后台处理
    //GEdorContManuUWBL tGEdorContManuUWBL = new GEdorContManuUWBL();
    PEdorPolManuUWBL tGEdorContManuUWBL = new PEdorPolManuUWBL();
    if (!tGEdorContManuUWBL.submitData(tVData, ""))
    {
        tErrors.copyAllErrors(tGEdorContManuUWBL.mErrors);
    }
    tGEdorContManuUWBL = null;
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
