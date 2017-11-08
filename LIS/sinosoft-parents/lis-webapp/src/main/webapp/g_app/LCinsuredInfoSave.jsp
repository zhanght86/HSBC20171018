<%
/***************************************************************
* <p>ProName：LCinsuredInfoSave.jsp</p>
 * <p>Title：被保人清单列表</p>
 * <p>Description：被保人清单列表</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-0-04-22
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
		
			// 被保人信息			
			String tOperate = request.getParameter("Operate");	
			String tGrpPropNo = request.getParameter("GrpPropNo");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tContPlanType = request.getParameter("ContPlanType");
			
			String tCustomerno = request.getParameter("Customerno");
			String tCustomName =  request.getParameter("CustomName");
			String tIDNO =  request.getParameter("IDNO");
			String tCustomerID = request.getParameter("CustomerID");
			String tContPlanCode = request.getParameter("ContPlanCode");
			String tSysPlanCode = request.getParameter("SysPlanCode");

			String tInsredNum[] = request.getParameterValues("InpQueryScanGridChk");
			String tInsredNo[] = request.getParameterValues("QueryScanGrid1");//被保人客户号
			String tContNo[] = request.getParameterValues("QueryScanGrid2");//被保人保单号
			String tRelationtomain[] = request.getParameterValues("QueryScanGrid3");//与主被保人关系

			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			
			if(null!=tInsredNum && tInsredNum.length>0){
				for (int i = 0; i < tInsredNum.length; i++){
					if (tInsredNum[i].equals("1")) {
						tLCInsuredSchema = new LCInsuredSchema();
						tLCInsuredSchema.setGrpContNo(tGrpPropNo);
						tLCInsuredSchema.setContNo(tContNo[i]);
						tLCInsuredSchema.setInsuredNo(tInsredNo[i]);
						tLCInsuredSchema.setRelationToMainInsured(tRelationtomain[i]);
						tLCInsuredSet.add(tLCInsuredSchema);
					}
				}
			}
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("Flag",request.getParameter("Flag"));
			tTransferData.setNameAndValue("GrpPropNo",tGrpPropNo);
			tTransferData.setNameAndValue("MissionID",tMissionID);
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			tTransferData.setNameAndValue("ActivityID",tActivityID);
			tTransferData.setNameAndValue("Customerno",tCustomerno);
			tTransferData.setNameAndValue("CustomName",tCustomName);
			tTransferData.setNameAndValue("IDNO",tIDNO);
			tTransferData.setNameAndValue("CustomerID",tCustomerID);
			tTransferData.setNameAndValue("ContPlanCode",tContPlanCode);
			tTransferData.setNameAndValue("SysPlanCode",tSysPlanCode);
		
			VData tVData = new VData();
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLCInsuredSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCInsuredDealUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "被保人信息操作成功！";
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
