
<%
//程序名称：GEdorTypeCTSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	String busiName="bqgrpPGrpEdorCTDetailUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//接收信息，并作校验处理。
	//输入参数
	//个人批改信息
	//loggerDebug("GEdorTypeCTSubmit","-----CTsubmit---");
	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	

	CErrors tError = null;
	//后面要执行的动作：添加，修改

	String FlagStr = "";
	String Content = "";
	String fmAction = "";
	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	fmAction = request.getParameter("fmAction");
	GlobalInput tG = new GlobalInput();
	//loggerDebug("GEdorTypeCTSubmit","------------------begin ui");
	tG = (GlobalInput) session.getValue("GI");

	String edorAcceptNo = request.getParameter("EdorAcceptNo");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String grpcontNo = request.getParameter("GrpContNo");
	
	tLPGrpEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
	tLPGrpEdorItemSchema.setEdorNo(edorNo);
	tLPGrpEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
	tLPGrpEdorItemSchema.setEdorType(edorType);
	tLPGrpEdorItemSchema.setGrpContNo(grpcontNo);


	try {
		// 准备传输数据 VData

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tLPGrpEdorItemSchema);
		tBusinessDelegate.submitData(tVData, fmAction,busiName);
	} catch (Exception ex) {
		Content = fmAction + "失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = " 保存成功";
			FlagStr = "Success";
		} else {
			Content = " 保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

