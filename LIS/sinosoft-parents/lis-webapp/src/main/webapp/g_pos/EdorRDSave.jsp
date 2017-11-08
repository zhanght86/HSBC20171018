<%
/***************************************************************
 * <p>ProName:EdorRDSave.jsp</p>
 * <p>Title:  ������ȡ</p>
 * <p>Description:������ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInsuredListSchema"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorPayPlanListSchema"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInvestListSchema"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInsuredListSet"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorPayPlanListSet"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInvestListSet"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	LBPOEdorInsuredListSet mLBPOEdorInsuredListSet = new LBPOEdorInsuredListSet();
	LBPOEdorPayPlanListSet tLBPOEdorPayPlanListSet = new LBPOEdorPayPlanListSet();
	LBPOEdorInvestListSet tLBPOEdorInvestListSet = new LBPOEdorInvestListSet();
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
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
				String tOldInsuredName[] = request.getParameterValues("OldInsuredInfoGrid2");
				String tOldInsuredIDNo[] = request.getParameterValues("OldInsuredInfoGrid8");
				
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
						mLBPOEdorInsuredListSet.add(mLBPOEdorInsuredListSchema);
					}
				}
			} else if ("DELETE".equals(tOperate)) {
				
				String tChk[]=request.getParameterValues("InpUpdateInsuredInfoGridSel");
				String tSerialNo[] = request.getParameterValues("UpdateInsuredInfoGrid1");
				
				for (int index=0;index<tChk.length;index++) {
				
					if (tChk[index].equals("1")) {
					
						LBPOEdorInsuredListSchema mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
						mLBPOEdorInsuredListSchema.setSerialNo(tSerialNo[index]);
						mLBPOEdorInsuredListSet.add(mLBPOEdorInsuredListSchema);
					}
				}
			} else if ("PayPlanSave".equals(tOperate)) {
				
				String tInsuredID = request.getParameter("InsuredID");
				
				String tGridNo[] = request.getParameterValues("InpPayPlanGridSel");
				String tDutyCode[] = request.getParameterValues("PayPlanGrid2");
				String tPayPlanCode[] = request.getParameterValues("PayPlanGrid4");
				String tGetAmount[] = request.getParameterValues("PayPlanGrid7");
				
				for (int i=0; i<tGridNo.length; i++) {
					if (tGridNo[i].equals("1")) {
						
						LBPOEdorPayPlanListSchema tLBPOEdorPayPlanListSchema = new LBPOEdorPayPlanListSchema();
						
						tLBPOEdorPayPlanListSchema.setEdorAppNo(tEdorAppNo);
						tLBPOEdorPayPlanListSchema.setActivityID(tActivityID);
						tLBPOEdorPayPlanListSchema.setBatchNo(tEdorAppNo);
						tLBPOEdorPayPlanListSchema.setInsuredID(tInsuredID);
						tLBPOEdorPayPlanListSchema.setDutyCode(tDutyCode[i]);
						tLBPOEdorPayPlanListSchema.setPayPlanCode(tPayPlanCode[i]);
						tLBPOEdorPayPlanListSchema.setGetAmount(tGetAmount[i]);
						tLBPOEdorPayPlanListSet.add(tLBPOEdorPayPlanListSchema);
						
						String tInvestGridNo[] = request.getParameterValues("InvestGridNo");
						String tInsuAccNo[] = request.getParameterValues("InvestGrid4");
						String tInvestGetAmount[] = request.getParameterValues("InvestGrid7");
						
						for (int j=0; j<tInvestGridNo.length; j++) {
							
							LBPOEdorInvestListSchema tLBPOEdorInvestListSchema = new LBPOEdorInvestListSchema();
							
							tLBPOEdorInvestListSchema.setEdorAppNo(tEdorAppNo);
							tLBPOEdorInvestListSchema.setActivityID(tActivityID);
							tLBPOEdorInvestListSchema.setBatchNo(tEdorAppNo);
							tLBPOEdorInvestListSchema.setInsuAccNo(tInsuAccNo[j]);
							if (tInvestGridNo.length==1) {
								tLBPOEdorInvestListSchema.setGetAmount(tGetAmount[i]);
							} else {
								tLBPOEdorInvestListSchema.setGetAmount(tInvestGetAmount[j]);
							}
							tLBPOEdorInvestListSet.add(tLBPOEdorInvestListSchema);
						}
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
			tVData.add(tLBPOEdorPayPlanListSet);
			tVData.add(tLBPOEdorInvestListSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorRDUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "����ɹ�!";
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
