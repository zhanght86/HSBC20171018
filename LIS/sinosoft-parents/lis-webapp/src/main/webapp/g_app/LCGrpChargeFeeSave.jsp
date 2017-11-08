<%
/***************************************************************
 * <p>ProName：LCGrpChargeFeeSave.jsp</p>
 * <p>Title：中介手续费率维护</p>
 * <p>Description：中介手续费率维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-05
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.LCGrpChangeFeeSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCGrpChangeFeeSet"%>
<%@page import="com.sinosoft.lis.schema.LCGrpPolSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCGrpChangeFeeSet mLCGrpChangeFeeSet = new LCGrpChangeFeeSet();
	LCGrpChangeFeeSchema mLCGrpChangeFeeSchema = null;
	LCGrpPolSchema mLCGrpPolSchema = null;
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);

			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tRiskCode=request.getParameter("RiskCode");
			
			if("INSERT".equals(tOperate)){
				String tGridNo[] = request.getParameterValues("ZJGridNo");
				String tGrid1 [] = request.getParameterValues("ZJGrid1"); 
				String tGrid2 [] = request.getParameterValues("ZJGrid2");
				String tGrid3 [] = request.getParameterValues("ZJGrid3");
				int Count = tGridNo.length;
				
				for(int index=0;index<Count;index++){
			
				mLCGrpChangeFeeSchema = new LCGrpChangeFeeSchema();
				mLCGrpChangeFeeSchema.setPolicyNo(tGrpPropNo);
				mLCGrpChangeFeeSchema.setRiskCode(tRiskCode);
				mLCGrpChangeFeeSchema.setAgentCom(tGrid1[index]);
				mLCGrpChangeFeeSchema.setAgentComName(tGrid2[index]);
				mLCGrpChangeFeeSchema.setChangeFee(tGrid3[index]);
		
				mLCGrpChangeFeeSet.add(mLCGrpChangeFeeSchema);
			}
		}
		tVData.add(mLCGrpChangeFeeSet);
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "LCGrpChangeFeeUI")) {
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
