<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>

<%
	loggerDebug("DSErrorReasonSave","开始执行Save页面");
	//定义全局变量：管理机构和操作员
	String tRela = "";
	String FlagStr = "";
	String Content = "";

	//差错记录表：BPOErrLog
	LBPODataDetailErrorSet mLBPODataDetailErrorSet = new LBPODataDetailErrorSet();

	//建立错误对象
	CErrors tError = null;
	loggerDebug("DSErrorReasonSave","开始进行获取数据的操作！！！");
	String mOperateType = "INSERT";
	String rgtNo = request.getParameter("prtNo");
	//操作类型是插入，查询，更新
	loggerDebug("DSErrorReasonSave","印刷号是" + rgtNo);

	//获取mulitiline中的值
	String tImpartNum[] = request.getParameterValues("PolGridNo");
	String terrorcontent[] = request.getParameterValues("PolGrid1"); //录入项目
	String terrorcount[] = request.getParameterValues("PolGrid2"); //录入内容
	String toperator[] = request.getParameterValues("PolGrid3"); //操作员工号
	String tErrorFlag[] = request.getParameterValues("PolGrid4"); //记录差错
	String tErrorCount[] = request.getParameterValues("PolGrid5"); //错误数
	String tSerialNo[] = request.getParameterValues("PolGrid6"); //错误流水号

	//给BPOErrLogSchema的实例赋值,已便在BL层中获得这些数据,根据这些变量查询数据库，进行一些校验判断：是否可以进行插入等操作
	int ImpartCount = 0;

	if (tImpartNum != null) {
		ImpartCount = tImpartNum.length;
		loggerDebug("DSErrorReasonSave","ImpartCount= " + ImpartCount);

		for (int i = 0; i < ImpartCount; i++) {
			LBPODataDetailErrorSchema mLBPODataDetailErrorSchema = new LBPODataDetailErrorSchema();
			loggerDebug("DSErrorReasonSave","i=" + i);
			mLBPODataDetailErrorSchema.setBussNo(rgtNo);
			loggerDebug("DSErrorReasonSave","印刷号是" + rgtNo);
			mLBPODataDetailErrorSchema.setBussNoType("TB");
			loggerDebug("DSErrorReasonSave","差错编码terrorCode[" + i + "]="
			+ tSerialNo[i]);
			mLBPODataDetailErrorSchema.setErrorCount(tErrorCount[i]);
			mLBPODataDetailErrorSchema.setErrorContent(terrorcontent[i]);
			mLBPODataDetailErrorSchema.setSerialNo(tSerialNo[i]);
			mLBPODataDetailErrorSchema.setOperator(toperator[i]);
			mLBPODataDetailErrorSchema.setErrorFlag(tErrorFlag[i]);
			mLBPODataDetailErrorSchema.setContent(tErrorFlag[i]);
			mLBPODataDetailErrorSet.add(mLBPODataDetailErrorSchema);
		}
	}

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//DSErrorReasonUI mDSErrorReasonUI = new DSErrorReasonUI();
	String busiName="tbDSErrorReasonUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	VData tVData = new VData();

	//将数据提交给后台UI,参数是VData和操作类型
	try {
		//将操作类型，管理机构，操作员添加到容器中
		tVData.addElement(mOperateType);
		tVData.addElement(mLBPODataDetailErrorSet);
		tVData.addElement(tG);

		tBusinessDelegate.submitData(tVData, mOperateType,busiName);
	} catch (Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = "保存成功";
			FlagStr = "Succ";
		} else {
			Content = "保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterfacereason.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//alert("<%=Content%>");
</script>
</html>
