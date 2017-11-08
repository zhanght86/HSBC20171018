<%
/***************************************************************
* <p>ProName：LCDutyAdjustPremSave.jsp</p>
 * <p>Title：个人险种责任调整保费</p>
 * <p>Description：个人险种责任调整保费</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-0-05-06
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			// 责任核保调整			
			String tOperate = request.getParameter("Operate");	
			String tGrpPropNo =  request.getParameter("GrpPropNo");	
			
			String tUWAdjustNum[] = request.getParameterValues("CpolErrGridNo");
			String tContNo[] = request.getParameterValues("CpolErrGrid1");//被保人客户号
			String tPlancode[] = request.getParameterValues("CpolErrGrid2");//方案编码
			String tPolno[] = request.getParameterValues("CpolErrGrid3");//被保人险种号
			String tRiskCode[] = request.getParameterValues("CpolErrGrid4");//险种编码
			String tDutyCode[] = request.getParameterValues("CpolErrGrid6");//责任编码
			String tPrem[] = request.getParameterValues("CpolErrGrid8");//保费
			String tAmnt[] = request.getParameterValues("CpolErrGrid9");//保额
			String tAdjustPrem[] = request.getParameterValues("CpolErrGrid10");//调整保费
			String tAdjustAmnt[] = request.getParameterValues("CpolErrGrid11");//调整保额
			String tInsuredNo[] = request.getParameterValues("CpolErrGrid12");//借用到被保险人客户号

			LCDutyUWAdjustSet tLCDutyUWAdjustSet = new LCDutyUWAdjustSet();
			LCDutyUWAdjustSchema tLCDutyUWAdjustSchema = new LCDutyUWAdjustSchema();
			if(null != tUWAdjustNum &&tUWAdjustNum.length>=1 ){
			for (int i = 0; i < tUWAdjustNum.length; i++){
				tLCDutyUWAdjustSchema   = new LCDutyUWAdjustSchema();
				tLCDutyUWAdjustSchema.setBussType("NB");
				tLCDutyUWAdjustSchema.setBussNo(tGrpPropNo);
				tLCDutyUWAdjustSchema.setContNo(tContNo[i]);
				tLCDutyUWAdjustSchema.setPolNo(tPolno[i]);
				tLCDutyUWAdjustSchema.setPlanCode(tPlancode[i]);
				tLCDutyUWAdjustSchema.setRiskCode(tRiskCode[i]);
				tLCDutyUWAdjustSchema.setDutyCode(tDutyCode[i]);
				tLCDutyUWAdjustSchema.setAmnt(tAmnt[i]);
				tLCDutyUWAdjustSchema.setUWAmnt(tAdjustAmnt[i]);
				tLCDutyUWAdjustSchema.setPrem(tPrem[i]);
				tLCDutyUWAdjustSchema.setUWPrem(tAdjustPrem[i]);
				tLCDutyUWAdjustSchema.setOtherNo(tInsuredNo[i]);
		
				tLCDutyUWAdjustSet.add(tLCDutyUWAdjustSchema);
			}			
			
		}
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Flag",request.getParameter("Flag"));

		VData tVData = new VData();
		
		tVData.add(tGI);
		tVData.add(tTransferData);
		tVData.add(tLCDutyUWAdjustSet);
	
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "LCDutyUWAdjustUI")) {
			tContent = tBusinessDelegate.getCErrors().getFirstError();
			tFlagStr = "Fail";
		} else {
			tContent = "个人险种保费调整操作成功！";
			tFlagStr = "Succ";
		}
	} catch (Exception ex) {
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
