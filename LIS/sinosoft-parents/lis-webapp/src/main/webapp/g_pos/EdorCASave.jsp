<%
/***************************************************************
 * <p>ProName:EdorCASave.jsp</p>
 * <p>Title:  账户金额转移</p>
 * <p>Description:账户金额转移</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-25
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInsuredListSchema"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInsuredListSet"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	LBPOEdorInsuredListSet mLBPOEdorInsuredListSet = new LBPOEdorInsuredListSet();
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			String tOperate = request.getParameter("Operate");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorType = request.getParameter("EdorType");
			
			if ("UPDATE".equals(tOperate)) {
			
				String tChk[]=request.getParameterValues("InpOldInsuredInfoGridChk");
				String tOldInsuredName[] = request.getParameterValues("OldInsuredInfoGrid3");
				String tOldInsuredIDNo[] = request.getParameterValues("OldInsuredInfoGrid7");
				String tInAmount[] = request.getParameterValues("OldInsuredInfoGrid12");
				String tOutAmount[] = request.getParameterValues("OldInsuredInfoGrid13");
				
				for (int index=0;index<tChk.length;index++) {
					
					if(tChk[index].equals("1")){
			
						LBPOEdorInsuredListSchema mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
						
						mLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
						mLBPOEdorInsuredListSchema.setActivityID(tActivityID);
						mLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setEdorType(tEdorType);
						mLBPOEdorInsuredListSchema.setOldInsuredName(tOldInsuredName[index]);
						mLBPOEdorInsuredListSchema.setOldInsuredIDNo(tOldInsuredIDNo[index]);
						mLBPOEdorInsuredListSchema.setInAmount(tInAmount[index]);
						mLBPOEdorInsuredListSchema.setOutAmount(tOutAmount[index]);
						mLBPOEdorInsuredListSet.add(mLBPOEdorInsuredListSchema);
					}
				}
			} else if ("DELETE".equals(tOperate)) {
				
				String tChk[]=request.getParameterValues("InpUpdateInsuredInfoGridChk");
				String tSerialNo[] = request.getParameterValues("UpdateInsuredInfoGrid1");
				
				for (int index=0;index<tChk.length;index++) {
				
					if (tChk[index].equals("1")) {
					
						LBPOEdorInsuredListSchema mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
						mLBPOEdorInsuredListSchema.setSerialNo(tSerialNo[index]);
						mLBPOEdorInsuredListSet.add(mLBPOEdorInsuredListSchema);
					}
				}
			}
			
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			vTransferData.setNameAndValue("EdorType",tEdorType);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(mLBPOEdorInsuredListSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorCAUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功!";
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
