<%
/***************************************************************
 * <p>ProName：LLClimCaseSave.jsp</p>
 * <p>Title：理赔普通立案界面</p>
 * <p>Description：理赔普通立案管理</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-05-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LLGrpRegisterSchema"%>
<%@page import="com.sinosoft.lis.schema.LLRegisterSchema"%>
<%@page import="com.sinosoft.lis.schema.LLCaseSchema"%>
<%@page import="com.sinosoft.lis.schema.LLAppClaimReasonSchema"%>
<%@page import="com.sinosoft.lis.vschema.LLAppClaimReasonSet"%>
<%@page import="com.sinosoft.lis.schema.LLCaseDutySchema"%>
<%@page import="com.sinosoft.lis.vschema.LLCaseDutySet"%>
<%@page import="com.sinosoft.lis.schema.LDBankSchema"%>
<%@page import="com.sinosoft.lis.schema.LWMissionSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tGrpRegisterNo = "";
	String tRegisterNo = "";
	String tCaseNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			String tOperate = request.getParameter("Operate");
			String tRgtClass = request.getParameter("RgtClass");
						
			LLGrpRegisterSchema tLLGrpRegisterSchema = new LLGrpRegisterSchema();
			tLLGrpRegisterSchema.setRgtNo(request.getParameter("GrpRgtNo"));
			tLLGrpRegisterSchema.setRgtState("01");
			tLLGrpRegisterSchema.setRgtClass(request.getParameter("RgtClass"));
			tLLGrpRegisterSchema.setCustomerNo(request.getParameter("AppntNo"));
			tLLGrpRegisterSchema.setGrpName(request.getParameter("AppntName"));
			tLLGrpRegisterSchema.setRgtantName(request.getParameter("AppntName"));
			tLLGrpRegisterSchema.setApplyerType(request.getParameter("AppntType"));			
			tLLGrpRegisterSchema.setAppDate(request.getParameter("AppDate"));
			tLLGrpRegisterSchema.setRgtDate(request.getParameter("AcceptDate"));
			tLLGrpRegisterSchema.setRgtObjNo(request.getParameter("PageNo"));
			tLLGrpRegisterSchema.setAcceptOperator(request.getParameter("AcceptOperator"));
			tLLGrpRegisterSchema.setMngCom(request.getParameter("AcceptCom"));
							
			LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
			tLLRegisterSchema.setGrpRgtNo(request.getParameter("GrpRgtNo"));
			tLLRegisterSchema.setRgtNo(request.getParameter("RegisterNo"));
			tLLRegisterSchema.setRgtState("01");
			tLLRegisterSchema.setRgtObj(request.getParameter("RgtClass"));
			tLLRegisterSchema.setRgtObjNo(request.getParameter("RegisterNo"));
			tLLRegisterSchema.setRgtClass(request.getParameter("RgtClass"));
			tLLRegisterSchema.setAppntNo(request.getParameter("SelfAppntNo"));
			tLLRegisterSchema.setGrpName(request.getParameter("SelfAppntName"));
			tLLRegisterSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLRegisterSchema.setRgtantName(request.getParameter("CustomerName"));
			tLLRegisterSchema.setRgtantSex(request.getParameter("Gender"));
			tLLRegisterSchema.setBirthday(request.getParameter("Birthday"));
			tLLRegisterSchema.setEmployeNo(request.getParameter("EmployeNo"));
			tLLRegisterSchema.setIDType(request.getParameter("IDType"));
			tLLRegisterSchema.setIDNo(request.getParameter("IDNo"));
			tLLRegisterSchema.setAppAmnt(request.getParameter("AppAmnt"));
			tLLRegisterSchema.setBillCount(request.getParameter("BillCount"));
			tLLRegisterSchema.setScanCount(request.getParameter("ScanCount"));
			tLLRegisterSchema.setIsUrgent(request.getParameter("IsUrgent"));
			tLLRegisterSchema.setIsOpenBillFlag(request.getParameter("IsOpenBillFlag"));
			tLLRegisterSchema.setIsBackBill(request.getParameter("IsBackBill"));
			tLLRegisterSchema.setBankCode(request.getParameter("BankCode"));
			tLLRegisterSchema.setBankAccNo(request.getParameter("BankAccNo"));
			tLLRegisterSchema.setAccName(request.getParameter("AccName"));
			tLLRegisterSchema.setApplyDate(request.getParameter("CustomerAppDate"));
			tLLRegisterSchema.setRemark(request.getParameter("Remark"));
			tLLRegisterSchema.setRgtantPhone(request.getParameter("TelPhone"));
			
			LDBankSchema tLDBankSchema = new LDBankSchema();
			tLDBankSchema.setHeadBankCode(request.getParameter("BankCode"));
			tLDBankSchema.setProvince(request.getParameter("Province"));
			tLDBankSchema.setCity(request.getParameter("City"));
			
			LLCaseSchema tLLCaseSchema = new LLCaseSchema();
			tLLCaseSchema.setRgtNo(request.getParameter("RegisterNo"));
			tLLCaseSchema.setCaseNo(request.getParameter("CaseNo"));
			tLLCaseSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLCaseSchema.setCustomerName(request.getParameter("CustomerName"));
			tLLCaseSchema.setAccidentType(request.getParameter("AccReason"));
			tLLCaseSchema.setAccDate(request.getParameter("AccDate"));
			tLLCaseSchema.setDeathDate(request.getParameter("DeathDate"));
			System.out.println(request.getParameter("DeathDate")+"===============");
			tLLCaseSchema.setDianoseDate(request.getParameter("DeformityDate"));
			tLLCaseSchema.setStandpay(request.getParameter("ClaimPay"));
			tLLCaseSchema.setCureDesc(request.getParameter("TreatResult"));
			tLLCaseSchema.setAccResult1(request.getParameter("AccResult1"));
			tLLCaseSchema.setAccResult2(request.getParameter("AccResult2"));
			tLLCaseSchema.setHospitalCode(request.getParameter("HospitalCode"));
			tLLCaseSchema.setHospitalName(request.getParameter("HospitalName"));
			/* tLLCaseSchema.setAccProvince(request.getParameter("AccProvince"));
			tLLCaseSchema.setAccCity(request.getParameter("AccCity"));
			tLLCaseSchema.setAccCounty(request.getParameter("AccCounty")); */
			tLLCaseSchema.setAccSite(request.getParameter("AccSite"));
			tLLCaseSchema.setCaseSource(request.getParameter("CaseSource"));
			tLLCaseSchema.setCloseCaseReason(request.getParameter("CloseCaseReason"));
			tLLCaseSchema.setLRCaseNo(request.getParameter("LRCaseNo"));
			tLLCaseSchema.setAccidentDetail(request.getParameter("AccDesc"));
			tLLCaseSchema.setRemark(request.getParameter("CaseRemark"));
			
			
			String tClaimType[] = request.getParameterValues("ClaimType");
			String tClaimReason = request.getParameter("AccReason");
			LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet();
			if (tClaimType!=null && tClaimType.length>=1) {
				
			  	for (int i = 0; i < tClaimType.length; i++){
			  	  	
					tClaimType[i] = tClaimReason + tClaimType[i];
					LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
					
					tLLAppClaimReasonSchema.setRgtNo(request.getParameter("RegisterNo"));
					tLLAppClaimReasonSchema.setCaseNo(request.getParameter("CaseNo"));
					tLLAppClaimReasonSchema.setCustomerNo(request.getParameter("CustomerNo"));
					tLLAppClaimReasonSchema.setReasonCode(tClaimType[i]);
					tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
			  	}
			}
			
			LLCaseDutySet tLLCaseDutySet = new LLCaseDutySet();
			
			//接收MulLine表格中数据集合			
			String tGridNo[] = null;
			String tPolNo[] = null;
			String tDutyCode[] = null;
			String tGetDutyCode[] = null;
			String tSel[] = null;			
			
			if ("DUTYOFF".equals(tOperate)) {
				tGridNo = request.getParameterValues("OnEventDutyListGridNo");  //得到序号列的所有值
				tSel = request.getParameterValues("InpOnEventDutyListGridChk");
				tPolNo = request.getParameterValues("OnEventDutyListGrid2");
				tDutyCode = request.getParameterValues("OnEventDutyListGrid5");
				tGetDutyCode = request.getParameterValues("OnEventDutyListGrid7");
				
			} else if ("DUTYON".equals(tOperate)) {
				tGridNo = request.getParameterValues("OffEventDutyListGridNo");  //得到序号列的所有值
				tSel = request.getParameterValues("InpOffEventDutyListGridChk");
				tPolNo = request.getParameterValues("OffEventDutyListGrid2");
				tDutyCode = request.getParameterValues("OffEventDutyListGrid5");
				tGetDutyCode = request.getParameterValues("OffEventDutyListGrid7");				
			}
			if (tSel!=null && tSel.length>=1) {

			  for(int index=0;index<tSel.length;index++) {
				  
					if(tSel[index].equals("1")) {
					
						LLCaseDutySchema tLLCaseDutySchema = new LLCaseDutySchema();
						tLLCaseDutySchema.setRgtNo(request.getParameter("RegisterNo"));
						tLLCaseDutySchema.setCaseNo(request.getParameter("CaseNo"));
						tLLCaseDutySchema.setCustomerNo(request.getParameter("CustomerNo"));
						
						tLLCaseDutySchema.setPolNo(tPolNo[index]);
						tLLCaseDutySchema.setDutyCode(tDutyCode[index]);
						tLLCaseDutySchema.setGetDutyCode(tGetDutyCode[index]);
						tLLCaseDutySet.add(tLLCaseDutySchema);
					}
			  }
			}					
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLGrpRegisterSchema);
			tVData.add(tLLRegisterSchema);
			tVData.add(tLDBankSchema);
			tVData.add(tLLCaseSchema);
			tVData.add(tLLAppClaimReasonSet);
			tVData.add(tLLCaseDutySet);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimCaseUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
				if (tOperate.contains("ACCEPT")) {
					tGrpRegisterNo = (String)tTransferData.getValueByName("GrpRegisterNo");

				} else if (tOperate.contains("CUSTOMER")) {
					tRegisterNo = (String)tTransferData.getValueByName("RegisterNo");
				} else if (tOperate.contains("CASE")) {
					tCaseNo = (String)tTransferData.getValueByName("CaseNo");
				}
				tContent = "处理成功！";
				tFlagStr = "Succ";
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tGrpRegisterNo%>","<%=tRegisterNo%>","<%=tCaseNo%>");
</script>
</html>
