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
 * @date     : 2005-11-14
 * @direction: 保费自动垫交实际业务处理
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.bq.*" %>
<%@page import="com.sinosoft.service.*" %>

<%
    //文本框
    String sEdorAcceptNo = request.getParameter("EdorAcceptNo");
    String sEdorType = request.getParameter("EdorType");
    String sContNo = request.getParameter("ContNo");
    //隐藏域
    String sEdorNo = request.getParameter("EdorNo");
    String sPolNo = request.getParameter("PolNo");
    String sInsuredNo = request.getParameter("InsuredNo");
    //垫交
    String sAutoPayFlag = request.getParameter("AutoPayFlag");
    

    ////参数校验
    //if (sEdorAcceptNo == null || sEdorAcceptNo.equals("") || sEdorType == null || sEdorType.equals("") || sContNo == null || sContNo.equals(""))
    //{
    //    out.println("<script language='JavaScript'>");
    //    out.println("  alert('错误：请检查保全受理号、批改类型、保单号是否为空！ ');");
    //    out.println("  window.close();");
    //    out.println("</script>");
    //}
    //if (sEdorNo == null || sEdorNo.equals("") || sPolNo == null || sPolNo.equals("") || sInsuredNo == null || sInsuredNo.equals(""))
    //{
    //    out.println("<script language='JavaScript'>");
    //    out.println("  alert('错误：无法获取保全批单号、险种号、被保人客户号！ ');");
    //    out.println("  window.close();");
    //    out.println("</script>");
    //}
    //if (sAutoPayFlag == null || sAutoPayFlag == "")
    //{
    //    out.println("<script language='JavaScript'>");
    //    out.println("  alert('错误：请输入是否自动垫交标记！ ');");
    //    out.println("  window.close();");
    //    out.println("</script>");
    //}

    //反馈标记
    String FlagStr = "";
    String Content = "";
    CErrors tErrors = new CErrors();

    //业务处理
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setOperator(tGlobalInput.Operator);
    tLPEdorItemSchema.setEdorAcceptNo(sEdorAcceptNo);
    tLPEdorItemSchema.setEdorType(sEdorType);
    tLPEdorItemSchema.setContNo(sContNo);
    tLPEdorItemSchema.setEdorNo(sEdorNo);
	tLPEdorItemSchema.setPolNo(sPolNo);
	tLPEdorItemSchema.setInsuredNo(sInsuredNo);
    //tLPEdorItemSchema.setEdorReason("");

    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("AutoPayFlag", sAutoPayFlag);

    //传输数据
    VData tVData = new VData();
    tVData.addElement(tGlobalInput);
    tVData.addElement(tLPEdorItemSchema);
    tVData.addElement(tTransferData);

    //提交数据
//    EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//    if (!tEdorDetailUI.submitData(tVData, "OPERATE"))
    if (!tBusinessDelegate.submitData(tVData, "OPERATE",busiName))
    {
        System.out.println("\t@> PEdorTypeAPSubmit.jsp : 调用 EdorDetailUI.submitData() 提交数据失败");
//        tErrors.copyAllErrors(tEdorDetailUI.mErrors);
        tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
        FlagStr = "Fail";
        Content = "保存失败, 原因是：" + tErrors.getFirstError();
    }
    if (FlagStr.equals("") && !tErrors.needDealError())
    {
        System.out.println("\t@> PEdorTypeAPSubmit.jsp : 保费自动垫交变更成功");
        FlagStr = "Success";
        Content = "操作成功！";
    }
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
