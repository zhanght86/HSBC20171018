<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpContCarAllDelSave.jsp
//程序功能：
//创建日期：2006-10-23 15:12:33
//创建人 ：chenrong
//更新记录： 更新人  更新日期   更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LCGrpContSchema"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//接收信息，并作校验处理。
//输入参数
LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
//GrpContCarAllDelUI tGrpContCarAllDelUI = new GrpContCarAllDelUI();

//输出参数
CErrors tError = null;

String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("GrpContCarAllDelSave","begin ...");

String tGrpContNo = request.getParameter("ProposalGrpContNo");	//集体合同号码

//保险计划基础信息
tLCGrpContSchema.setGrpContNo(tGrpContNo);

// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.add(tLCGrpContSchema);
String busiName="tbGrpContCarAllDelUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
try{
	loggerDebug("GrpContCarAllDelSave","this will save the data!!!");
	tBusinessDelegate.submitData(tVData,"",busiName);
}
catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tBusinessDelegate.getCErrors();
	if (!tError.needDealError()){
		Content = " 保存成功! ";
		FlagStr = "Succ";
	}
	else{
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
