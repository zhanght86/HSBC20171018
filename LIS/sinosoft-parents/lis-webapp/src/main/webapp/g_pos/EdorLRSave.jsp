<%
/***************************************************************
* <p>ProName：EdorLRSave.jsp</p>
 * <p>Title：保单补发</p>
 * <p>Description：保单补发</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LPPolicyReissueSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPPolicyReissueSet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	LPPolicyReissueSet tLPPolicyReissueSet = new LPPolicyReissueSet();
	 
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			String tOperate = request.getParameter("Operate");	
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo = request.getParameter("EdorNo");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");
			String tReason = request.getParameter("Reason");
			String tGrpLRFlag = request.getParameter("GrpLRFlag");
			String tFeeLRFlag = request.getParameter("FeeLRFlag");
			String tPreLRFlag = request.getParameter("PreLRFlag");
			String tMoney = request.getParameter("Money");
			
			if("1".equals(tPreLRFlag)){
				if("DELETE".equals(tOperate)){
	 				String tChk[]=request.getParameterValues("InpUpdateInsuredInfoGridChk");
					String tContNo[] = request.getParameterValues("UpdateInsuredInfoGrid1");
					String tCustomerNo[] = request.getParameterValues("UpdateInsuredInfoGrid2");
				
					for(int index=0;index<tChk.length;index++){
						if(tChk[index].equals("1")){
							LPPolicyReissueSchema tLPPolicyReissueSchema = new LPPolicyReissueSchema();
							tLPPolicyReissueSchema.setPolicyNo(tContNo[index]);
							tLPPolicyReissueSchema.setCustomerNo(tCustomerNo[index]);
							tLPPolicyReissueSet.add(tLPPolicyReissueSchema);
						}
					}
				
				}else if ("PERCLICK".equals(tOperate)){
				
					String tChk[]=request.getParameterValues("InpOldInsuredInfoGridChk");
					String tContNo[] = request.getParameterValues("OldInsuredInfoGrid1");
					String tCustomerNo[] = request.getParameterValues("OldInsuredInfoGrid2");					
					for(int index=0;index<tChk.length;index++){
						if(tChk[index].equals("1")){
							LPPolicyReissueSchema tLPPolicyReissueSchema = new LPPolicyReissueSchema();
							tLPPolicyReissueSchema.setEdorNo(tEdorNo);
							tLPPolicyReissueSchema.setEdorType(tEdorType);
							tLPPolicyReissueSchema.setCustomerType("01");
							tLPPolicyReissueSchema.setPolicyNo(tContNo[index]);
							tLPPolicyReissueSchema.setCustomerNo(tCustomerNo[index]);
							tLPPolicyReissueSchema.setFeeFlag("0");
							tLPPolicyReissueSet.add(tLPPolicyReissueSchema);
						}
					}
				}
			}				
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			vTransferData.setNameAndValue("EdorNo",tEdorNo);
			vTransferData.setNameAndValue("EdorType",tEdorType);
			vTransferData.setNameAndValue("GrpLRFlag",tGrpLRFlag);
			vTransferData.setNameAndValue("FeeLRFlag",tFeeLRFlag);
			vTransferData.setNameAndValue("PreLRFlag",tPreLRFlag);
			vTransferData.setNameAndValue("Reason",tReason);
			vTransferData.setNameAndValue("Money",tMoney);
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(tLPPolicyReissueSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorLRUI")) {
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
