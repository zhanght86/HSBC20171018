<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	String flag=request.getParameter("flag");

	//接收信息，并作校验处理。
	//输入参数
	//输出参数
	CErrors tError = new CErrors();
	//后面要执行的动作：添加，修改，删除
	String transact = "";
	String Content = "";
	String FlagStr = "";

	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getAttribute("GI");
	LMRiskSaleNameSchema lMRiskSaleNameSchema=null;
	LMRiskSaleNameSet lMRiskSaleNameSet=null;
	PD_LMRiskSaleNameSchema pd_LMRiskSaleNameSchema=null;
	PD_LMRiskSaleNameSet pd_LMRiskSaleNameSet=null;
	if("1".equals(flag)){
		lMRiskSaleNameSchema = new LMRiskSaleNameSchema();
		lMRiskSaleNameSet = new LMRiskSaleNameSet();
		lMRiskSaleNameSchema.setRiskCode(request.getParameter("RiskCode"));
		lMRiskSaleNameSchema.setSaleChnl(request.getParameter("SaleChnl"));
		lMRiskSaleNameSchema.setManageCom(request.getParameter("ManageCom"));
		lMRiskSaleNameSchema.setRiskNameShort(request.getParameter("RiskNameShort"));
		lMRiskSaleNameSchema.setRiskNameEn(request.getParameter("RiskNameEn"));
		lMRiskSaleNameSchema.setRiskNameCn(request.getParameter("RiskNameCn"));
		lMRiskSaleNameSchema.setRiskNameTr(request.getParameter("RiskNameTr"));
		lMRiskSaleNameSet.add(lMRiskSaleNameSchema);
		lMRiskSaleNameSchema.setOperator(tGI.Operator);
	}else if("0".equals(flag)){
		pd_LMRiskSaleNameSchema = new PD_LMRiskSaleNameSchema();
		pd_LMRiskSaleNameSet = new PD_LMRiskSaleNameSet();
		pd_LMRiskSaleNameSchema.setRiskCode(request.getParameter("RiskCode"));
		pd_LMRiskSaleNameSchema.setSaleChnl(request.getParameter("SaleChnl"));
		pd_LMRiskSaleNameSchema.setManageCom(request.getParameter("ManageCom"));
		pd_LMRiskSaleNameSchema.setRiskNameShort(request.getParameter("RiskNameShort"));
		pd_LMRiskSaleNameSchema.setRiskNameEn(request.getParameter("RiskNameEn"));
		pd_LMRiskSaleNameSchema.setRiskNameCn(request.getParameter("RiskNameCn"));
		pd_LMRiskSaleNameSchema.setRiskNameTr(request.getParameter("RiskNameTr"));
		pd_LMRiskSaleNameSet.add(pd_LMRiskSaleNameSchema);
		pd_LMRiskSaleNameSchema.setOperator(tGI.Operator);
	}
	transact = request.getParameter("Transact");
	String busiName = "tbRiskSaleNameUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	try {
		VData tVData = new VData();
		if("1".equals(flag)){
			tVData.add(lMRiskSaleNameSchema);
			tVData.add(lMRiskSaleNameSet);
			
			
		}else if("0".equals(flag)){
			tVData.add(pd_LMRiskSaleNameSchema);
			tVData.add(pd_LMRiskSaleNameSet);
		}
		
		tVData.add(tGI);
		tVData.add(flag);
		tBusinessDelegate.submitData(tVData, transact, "tbRiskSaleNameUI");
	} catch (Exception ex) {
		Content = transact + ""+"失败，原因是:"+"" + ex.toString();
		FlagStr = "Fail";
	}

	if ("".equals(FlagStr)) {
		try {
			tError = tBusinessDelegate.getCErrors();

			if (!tError.needDealError()) {
		if (transact.equals("INSERT")) {
			transact = ""+"录入"+"";
		}
		if (transact.equals("UPDATE")) {
			transact = ""+"修改"+"";
		}
		if (transact.equals("DELETE")) {
			transact = ""+"删除"+"";
		}
		Content = transact + ""+"成功"+"";
		FlagStr = "Succ";
			} else {
		Content = transact + " "+"失败，原因是:"+"" + tError.getFirstError();
		FlagStr = "Fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
