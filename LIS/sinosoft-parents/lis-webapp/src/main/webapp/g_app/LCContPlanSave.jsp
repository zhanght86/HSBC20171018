<%
/***************************************************************
 * <p>ProName：LCContPlanSave.jsp</p>
 * <p>Title：保险方案</p>
 * <p>Description：保险方案</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : dianjing
 * @version  : 8.0
 * @date     : 2014-04-28
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%> 
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
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			System.out.println("tOperate="+tOperate);
			
			String tMissionID = request.getParameter("MissionID");	//任务ID
			String tSubMissionID = request.getParameter("SubMissionID");	//子任务ID
			String tActivityID = request.getParameter("ActivityID");	//活动节点ID
			String tGrpPropNo = request.getParameter("GrpPropNo");	//投保单号
			String tContPlanType = request.getParameter("ContPlanType");	//保单类型
			
			VData tVData = new VData();
			tVData.add(tGI);
			TransferData vTransferData = new TransferData();
			 
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpPropNo",tGrpPropNo);
			vTransferData.setNameAndValue("ContPlanType",tContPlanType);

			tVData.add(vTransferData);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCContPlanDealUI")) {
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
