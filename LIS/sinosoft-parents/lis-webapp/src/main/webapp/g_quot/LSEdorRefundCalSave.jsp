<%
/***************************************************************
 * <p>ProName：LSEdorRefundCalSave.jsp</p>
 * <p>Title：保全退费算法维护</p>
 * <p>Description：保全退费算法维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 张成
 * @version  : 8.0
 * @date     : 2014-04-23
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.lis.schema.LSQuotGetCalModeSchema"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tQuotNo = request.getParameter("QuotNo");//询价号
			String tQuotBatNo = request.getParameter("QuotBatNo");//批次号
			String tObjType = request.getParameter("ObjType");//
			
			String tSerialNo = request.getParameter("SerialNo");//流水号
			String tRiskCode = request.getParameter("RiskCode2");//险种
			String tGetType = request.getParameter("GetType2");//退费类型
			String tValPeriod = request.getParameter("ValPeriod");//单位
			String tValStartDate = request.getParameter("ValStartDate");//起始值
			String tValEndDate = request.getParameter("ValEndDate");//终止值
			String tFeeValue = request.getParameter("FeeValue");//费用比例

			LSQuotGetCalModeSchema tLSQuotGetCalModeSchema = new LSQuotGetCalModeSchema();
			if ("INSERT".equals(tOperate) || "UPDATE".equals(tOperate) ) {
				
				tLSQuotGetCalModeSchema.setSerialNo(tSerialNo);
				tLSQuotGetCalModeSchema.setQuotNo(tQuotNo);
				tLSQuotGetCalModeSchema.setQuotBatNo(tQuotBatNo);
				tLSQuotGetCalModeSchema.setRiskCode(tRiskCode);
				tLSQuotGetCalModeSchema.setGetType(tGetType);
				tLSQuotGetCalModeSchema.setValPeriod(tValPeriod);
				tLSQuotGetCalModeSchema.setValStartDate(tValStartDate);
				tLSQuotGetCalModeSchema.setValEndDate(tValEndDate);
				tLSQuotGetCalModeSchema.setFeeValue(tFeeValue);
				
			} else if ("DELETE".equals(tOperate)) {
				
				tLSQuotGetCalModeSchema.setSerialNo(tSerialNo);
			}
			
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ObjType", tObjType);
			tTransferData.setNameAndValue("QuotNo", tQuotNo);
			tTransferData.setNameAndValue("QuotBatNo", tQuotBatNo);
			tTransferData.setNameAndValue("MissionID", tMissionID);
			tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
			tTransferData.setNameAndValue("ActivityID", tActivityID);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLSQuotGetCalModeSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LSEdorRefundCalUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
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
