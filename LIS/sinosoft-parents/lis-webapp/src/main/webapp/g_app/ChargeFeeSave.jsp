<%
/***************************************************************
 * <p>ProName：ChargeFeeSave.jsp</p>
 * <p>Title：中介手续费率维护</p>
 * <p>Description：中介手续费率维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.LCPrintChangeFeeSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCPrintChangeFeeSet"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCPrintChangeFeeSet mLCPrintChangeFeeSet = null;
	LCPrintChangeFeeSchema mLCPrintChangeFeeSchema = null;
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			mLCPrintChangeFeeSet = new LCPrintChangeFeeSet();
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tRiskCode=request.getParameter("RiskCode");
			
			if("INSERT".equals(tOperate)){
				String tGridNo[] = request.getParameterValues("ZJGridNo");
				String tGrid1 [] = request.getParameterValues("ZJGrid1"); //得到第1列的所有值 
				String tGrid2 [] = request.getParameterValues("ZJGrid2");
				String tGrid4 [] = request.getParameterValues("ZJGrid4");    		
				int Count = tGridNo.length; //得到接受到的记录数
	
				for(int index=0;index<Count;index++){
			
					mLCPrintChangeFeeSchema = new LCPrintChangeFeeSchema();
					mLCPrintChangeFeeSchema.setGrpPropNo(tGrpPropNo);
					mLCPrintChangeFeeSchema.setGrpContNo(tGrpPropNo);
					mLCPrintChangeFeeSchema.setRiskCode(tRiskCode);
					mLCPrintChangeFeeSchema.setAgentCom(tGrid1[index]);
					mLCPrintChangeFeeSchema.setAgentComName(tGrid2[index]);
					mLCPrintChangeFeeSchema.setChangeFee(tGrid4[index]);
					mLCPrintChangeFeeSet.add(mLCPrintChangeFeeSchema);
			}
		}
		tVData.add(mLCPrintChangeFeeSet);
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "ChargeFeeUI")) {
			tContent = tBusinessDelegate.getCErrors().getFirstError();
			tFlagStr = "Fail";
		} else {
			tFlagStr = "Succ";
			tContent = "处理成功！";
		}
	} catch (Exception ex) {
		ex.printStackTrace();
		tContent = tFlagStr + "处理异常，请联系系统运维人员！";
		tFlagStr = "Fail";
	}
}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
