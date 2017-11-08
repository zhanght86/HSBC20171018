<%
/***************************************************************
* <p>ProName：EdorZTSave.jsp</p>
 * <p>Title：减少被保险人</p>
 * <p>Description：减少被保险人</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInsuredListSchema"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInsuredListSet"%>
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
			String tEdorType = request.getParameter("EdorType");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			String tEdorValDate = request.getParameter("EdorValDate");
			String tEdorNo = request.getParameter("EdorNo"); 

			if("UPDATE".equals(tOperate)){
			
				String tOldInsuredName [] = request.getParameterValues("OldInsuredInfoGrid2");
				String tOldInsuredIDNo [] = request.getParameterValues("OldInsuredInfoGrid8");
				String tMainInsuredName [] = request.getParameterValues("OldInsuredInfoGrid15");
				String tMainIdNo[] =request.getParameterValues("OldInsuredInfoGrid16"); 
				String tChk[]=request.getParameterValues("InpOldInsuredInfoGridChk");
				for(int index=0;index<tChk.length;index++){
					if(tChk[index].equals("1")){
						LBPOEdorInsuredListSchema mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
						mLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
						mLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setEdorType(tEdorType);
						mLBPOEdorInsuredListSchema.setActivityID(tActivityID);
						mLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setEdorNo(tEdorNo);
						mLBPOEdorInsuredListSchema.setOldInsuredName(tOldInsuredName [index]);
						mLBPOEdorInsuredListSchema.setOldInsuredIDNo(tOldInsuredIDNo [index]);
						mLBPOEdorInsuredListSchema.setMainInsuredName(tMainInsuredName [index]);
						mLBPOEdorInsuredListSchema.setMainInsuredIDNo(tMainIdNo[index]);
						mLBPOEdorInsuredListSchema.setEdorValDate(tEdorValDate);
						mLBPOEdorInsuredListSet.add(mLBPOEdorInsuredListSchema);
					}
				}

			}else if("DELETE".equals(tOperate)){
				String tGrid1 [] = request.getParameterValues("UpdateInsuredInfoGrid1");
				String tChk[]=request.getParameterValues("InpUpdateInsuredInfoGridChk");
				for(int index=0;index<tChk.length;index++){
					if(tChk[index].equals("1")){
			
						LBPOEdorInsuredListSchema mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
						mLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
						mLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
						mLBPOEdorInsuredListSchema.setEdorType(tEdorType);
						mLBPOEdorInsuredListSchema.setActivityID(tActivityID);
						mLBPOEdorInsuredListSchema.setSerialNo(tGrid1 [index]);
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
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorZTUI")) {
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
