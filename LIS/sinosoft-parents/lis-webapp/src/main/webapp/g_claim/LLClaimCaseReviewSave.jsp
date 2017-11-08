<%
/***************************************************************
 * <p>ProName：LLClimCaseSave.jsp</p>
 * <p>Title：理赔普通审核界面</p>
 * <p>Description：理赔普通审核管理</p>
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
<%@page import="com.sinosoft.lis.schema.LLClaimDetailSchema"%>
<%@page import="com.sinosoft.lis.vschema.LLAppClaimReasonSet"%>
<%@page import="com.sinosoft.lis.schema.LLCaseDutySchema"%>
<%@page import="com.sinosoft.lis.vschema.LLCaseDutySet"%>
<%@page import="com.sinosoft.lis.schema.LDBankSchema"%>
<%@page import="com.sinosoft.lis.schema.LLClaimUWMainSchema"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tGrpRegisterNo = "";
	String tRegisterNo = "";
	String tCaseNo = "";
	String tMissionID = "";
	String tSubMissionID = "";
	String tActivityID = "";
	String tGiveType = "";
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
							
			LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
			tLLRegisterSchema.setGrpRgtNo(request.getParameter("GrpRgtNo"));
			tLLRegisterSchema.setRgtNo(request.getParameter("RegisterNo"));
			tLLRegisterSchema.setCustomerNo(request.getParameter("CustomerNo"));
			
			LLCaseSchema tLLCaseSchema = new LLCaseSchema();
			tLLCaseSchema.setRgtNo(request.getParameter("RegisterNo"));
			tLLCaseSchema.setCaseNo(request.getParameter("CaseNo"));
			tLLCaseSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLCaseSchema.setCustomerName(request.getParameter("CustomerName"));
			tLLCaseSchema.setAccidentType(request.getParameter("AccReason"));
			tLLCaseSchema.setAccDate(request.getParameter("AccDate"));
			tLLCaseSchema.setDeathDate(request.getParameter("DeathDate"));
			tLLCaseSchema.setDianoseDate(request.getParameter("DeformityDate"));
			tLLCaseSchema.setStandpay(request.getParameter("ClaimPay"));
			tLLCaseSchema.setCureDesc(request.getParameter("TreatResult"));
			tLLCaseSchema.setAccResult1(request.getParameter("AccResult1"));
			tLLCaseSchema.setAccResult2(request.getParameter("AccResult2"));
			tLLCaseSchema.setHospitalCode(request.getParameter("HospitalCode"));
			tLLCaseSchema.setHospitalName(request.getParameter("HospitalName"));
			tLLCaseSchema.setAccProvince(request.getParameter("Province"));
			tLLCaseSchema.setAccCity(request.getParameter("City"));
			tLLCaseSchema.setAccCounty(request.getParameter("County"));
			tLLCaseSchema.setAccSite(request.getParameter("AccidentPlace"));
			tLLCaseSchema.setCaseSource(request.getParameter("CaseSource"));
			tLLCaseSchema.setCloseCaseReason(request.getParameter("CloseReasonDesc"));
			tLLCaseSchema.setCloseCaseRemark(request.getParameter("CloseRemarkDesc"));
			tLLCaseSchema.setLRCaseNo(request.getParameter("LRCaseNo"));
			tLLCaseSchema.setAccidentDetail(request.getParameter("AccidentRemarks"));
			tLLCaseSchema.setRemark(request.getParameter("Remarks"));
					
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
			
			LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
			tLLClaimDetailSchema.setRgtNo(request.getParameter("RegisterNo"));
			tLLClaimDetailSchema.setClmNo(request.getParameter("RegisterNo"));
			tLLClaimDetailSchema.setCaseNo(request.getParameter("CaseNo"));
			tLLClaimDetailSchema.setCustomerNo(request.getParameter("CustomerNo"));
			tLLClaimDetailSchema.setPolNo(request.getParameter("PolNo"));
			tLLClaimDetailSchema.setDutyCode(request.getParameter("DutyCode"));
			tLLClaimDetailSchema.setGetDutyCode(request.getParameter("GetDutyCode"));
			tLLClaimDetailSchema.setGetDutyKind(request.getParameter("GetDutyKind"));
			
			tGiveType = request.getParameter("GiveType");
			tLLClaimDetailSchema.setGiveType(tGiveType);
			if ("0".equals(tGiveType)) {
				tLLClaimDetailSchema.setAdjReason(request.getParameter("AdjReason"));
				tLLClaimDetailSchema.setRealPay(request.getParameter("RealPay"));
				tLLClaimDetailSchema.setAdjRemark(request.getParameter("AdjRemark"));				
			} else if ("1".equals(tGiveType)) {
				tLLClaimDetailSchema.setGiveReason(request.getParameter("NoGiveReason"));//拒付原因
				tLLClaimDetailSchema.setSpecialRemark(request.getParameter("SpecialRemark"));
				tLLClaimDetailSchema.setGiveReasonDesc(request.getParameter("AdjRemark"));				
			}
			
			//记录审核信息--LLClaimUWMain
			LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
			tLLClaimUWMainSchema.setClmNo(request.getParameter("RegisterNo"));
			tLLClaimUWMainSchema.setRgtNo(request.getParameter("RegisterNo"));
			tLLClaimUWMainSchema.setCheckType("0");
			tLLClaimUWMainSchema.setExamConclusion(request.getParameter("Conclusion"));
									
			if ("Review".equals(request.getParameter("UWFlag"))) {
				tLLClaimUWMainSchema.setRemark(request.getParameter("ReviewAdvice"));			
			} else if ("AgainReview".equals(request.getParameter("UWFlag"))) {
				tLLClaimUWMainSchema.setRemark(request.getParameter("AgainReviewAdvice"));
			}else{
				tLLClaimUWMainSchema.setRemark(request.getParameter("ReviewAdvice"));
			}
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tLLGrpRegisterSchema);
			tVData.add(tLLRegisterSchema);
			tVData.add(tLLCaseSchema);
			tVData.add(tLLAppClaimReasonSet);
			tVData.add(tLLCaseDutySet);			
			tVData.add(tLLClaimDetailSchema);
			tVData.add(tLLClaimUWMainSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLClaimCaseReviewUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				
				TransferData tTransferData = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
				if (tOperate.contains("CASE")) {
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tCaseNo%>");
</script>
</html>
