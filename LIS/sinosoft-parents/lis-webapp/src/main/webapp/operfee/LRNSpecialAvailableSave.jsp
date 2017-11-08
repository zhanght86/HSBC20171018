<%@ page contentType="text/html; charset=gb2312" language="java"
	errorPage=""%>

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
	 * @date     : 2005-11-21, 2006-02-15
	 * @direction: 保单特殊复效录入提交保存
	 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp"%>

<%@ page import="com.sinosoft.lis.bq.*"%>
<%@ page import="com.sinosoft.lis.operfee.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%@ page import="com.sinosoft.lis.schema.*"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>  

<%
	//接受数据变量
	String sContNo = request.getParameter("ContNo");
    String sPayToDate = request.getParameter("LastPayToDate");
	String sInvalidReason = request.getParameter("InvalidReason");
	String sRemark = request.getParameter("Remark");

	//必录字段的校验
	if (sContNo == null || sContNo.equals("") || sInvalidReason == null
			|| sInvalidReason.equals("")) {
		out.println("<script language=JavaScript>");
		out.println("    alert('提示：请依次检查合同号码、失效原因是否为空！ ');");
		out.println("    window.close();");
		out.println("</script>");
	} else {
		sContNo = sContNo.trim();
		sInvalidReason = sInvalidReason.trim();
	}
	//可选字段的校验
	if (sRemark != null)
		sRemark = sRemark.trim();

	//System.out.println("\t@> LRNSpecialAvailableSave.jsp : 保单特殊复效录入, 准备提交");
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI");
	
	//TransferData
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ContNo", sContNo);
	tTransferData.setNameAndValue("PayToDate", sPayToDate);
	tTransferData.setNameAndValue("InvalidReason", sInvalidReason);
	if (sRemark != null && !sRemark.equals(""))
		tTransferData.setNameAndValue("Remark", sRemark);
	
	//VData
	VData tVData = new VData();
	tVData.addElement(tGlobalInput);
	tVData.addElement(tTransferData);

	//后台处理标记
	String FlagStr = "";
	String Content = "";
	CErrors tErrors = new CErrors();

	//调用后台处理
	//RnSpecialAvailiable tLRNSpecialAvailableBL = new RnSpecialAvailiable();
	
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	
	if (!tBusinessDelegate.submitData(tVData, "OPERATE","RnSpecialAvailiable")) {
		//System.out.println("\t@> LRNSpecialAvailableSave.jsp : 调用 LRNSpecialAvailableBL.submitData() 提交数据失败");
		tErrors.copyAllErrors(tBusinessDelegate.getCErrors());
		FlagStr = "Fail";
		Content = "操作失败, 原因是：" + tErrors.getFirstError();
	}
	//页面反馈信息
	if (!tErrors.needDealError()) {
		//System.out.println("\t@> LRNSpecialAvailableSave.jsp : 保单特殊复效录入, 保存成功");
		FlagStr = "Success";
		Content = "复效操作成功！建议马上进行过期保单催收！";
	}
%>

<html>
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
