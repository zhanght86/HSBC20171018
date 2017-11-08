
<%
	//程序名称：CertifyDescSave.jsp
	//程序功能：
	//创建日期：2002-07-19 16:49:22
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
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
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	loggerDebug("CardRiskSave","开始执行Save页面");
	LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	String mOperateType = request.getParameter("OperateType");
	loggerDebug("CardRiskSave","操作的类型是 " + mOperateType);

	loggerDebug("CardRiskSave","开始进行获取数据的操作！！！");
	String[] strNumber = request.getParameterValues("CardRiskGridNo");
	String[] strCertifyCode = request.getParameterValues("CardRiskGrid1");
	String[] strRiskCode = request.getParameterValues("CardRiskGrid2");
	String[] strCardCode = request.getParameterValues("CardRiskGrid3"); //产品代码
	String[] strPrem = request.getParameterValues("CardRiskGrid4");
	String[] strPremProp = request.getParameterValues("CardRiskGrid5");
	String[] strPremLot = request.getParameterValues("CardRiskGrid6");
	if (strNumber != null) {
		for (int i = 0; i < strNumber.length; i++) {
			loggerDebug("CardRiskSave","险种编码 " + strRiskCode[i] +"产品代码"+strCardCode[i]);
			if((strRiskCode[i]==null || "".equals(strRiskCode[i]))&&(strCardCode[i]==null || "".equals(strCardCode[i])))
			{
				Content = "请录入险种编码或者产品代码" ;
				FlagStr = "Fail";
				break;
			}
			if((!("".equals(strRiskCode[i]) ))&& !("".equals(strCardCode[i])) )
			{
				Content = "险种编码与产品代码不能同时赋值，请录入其中一种" ;
				FlagStr = "Fail";
				break;
			}
			LMCardRiskSchema tLMCardRiskSchema = new LMCardRiskSchema();
			tLMCardRiskSchema.setCertifyCode(strCertifyCode[i]);
			if(!("".equals(strRiskCode[i])) )
			{
			 tLMCardRiskSchema.setRiskCode(strRiskCode[i]); //如果险种编码一列不为空，则赋值为险种代码，否则赋值为产品代码
			}
			else
			{
			 tLMCardRiskSchema.setRiskCode(strCardCode[i]);
			 tLMCardRiskSchema.setPortfolioFlag("1"); //存放的险种编码为产品组合代码
			}
			tLMCardRiskSchema.setPrem(strPrem[i]);
			tLMCardRiskSchema.setPremProp(strPremProp[i]);
			tLMCardRiskSchema.setPremLot(String.valueOf(strPremLot[i]));
			mLMCardRiskSet.add(tLMCardRiskSchema);
		}
	}
   if(FlagStr!="Fail")
   {
		VData tVData = new VData();
		CardRiskUI mCardRiskUI = new CardRiskUI();
		try {
			tVData.addElement(tG);
			tVData.addElement(mLMCardRiskSet);
	
			mCardRiskUI.submitData(tVData, mOperateType);
		} catch (Exception ex) {
			Content = "操作失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
	
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "") {
			tError = mCardRiskUI.mErrors;
			if (!tError.needDealError()) {
				Content = "操作成功!";
				FlagStr = "Succ";
			} else {
				Content = "操作失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
   }
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
