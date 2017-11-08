<%
/***************************************************************
 * <p>ProName：LJGetManualAppSubSave.jsp</p>
 * <p>Title: 手动付费申请明细</p>
 * <p>Description：手动付费申请明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJGetManualSubSchema"%>
<%@page import="com.sinosoft.lis.vschema.LJGetManualSubSet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String cTempFeeNo = "";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			tOperate = request.getParameter("Operate");
			String tApplyBatNo = request.getParameter("ApplyBatchNo");
			String tAppCom = request.getParameter("AppCom");
			LJGetManualSubSet tLJGetManualSubSet = new LJGetManualSubSet();
			
			VData tVData = new VData();
			tVData.add(tGI);
			if ("CHOOSECLICK".equals(tOperate)) {
			
				String tGetDealType = request.getParameter("GetDealType");
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("GetDealType", tGetDealType);
				tVData.add(tTransferData);
				
				String tChk[] = request.getParameterValues("InpGetInfoGridChk");//获取待匹配信息
				if (tChk!=null && tChk.length>0) {
					
					String tPayCode[] = request.getParameterValues("GetInfoGrid1");//付费号
					String tMainBussNo[] = request.getParameterValues("GetInfoGrid2");//团体保单号
					String tBussType[] = request.getParameterValues("GetInfoGrid5");//业务类型
					String tBussNo[] = request.getParameterValues("GetInfoGrid7");//业务号
					String tCustomerNo[] = request.getParameterValues("GetInfoGrid3");//投保人号码
					String tMoney[] = request.getParameterValues("GetInfoGrid8");//金额
					String tShouldDate[] = request.getParameterValues("GetInfoGrid9");//应付日期
					String tCustBankCode[] = request.getParameterValues("GetInfoGrid12");//客户开户行
					String tBankCode[] = request.getParameterValues("GetInfoGrid14");//银行编码
					String tCustBankProvince[] = request.getParameterValues("GetInfoGrid15");//开户省
					String tCustBankCity[] = request.getParameterValues("GetInfoGrid17");//客户市
					String tCustBankAccNo[] = request.getParameterValues("GetInfoGrid19");//账号
					String tCustAccName[] = request.getParameterValues("GetInfoGrid20");//账户
					String tInsuranceCom[] = request.getParameterValues("GetInfoGrid10");//共保公司
					String tGetObj[] = request.getParameterValues("GetInfoGrid21");//共保公司

					for (int i=0; i<tChk.length; i++) {
						
						if ("1".equals(tChk[i])) {
						
							LJGetManualSubSchema tLJGetManualSubSchema = new LJGetManualSubSchema();
							tLJGetManualSubSchema.setApplyBatNo(tApplyBatNo);
							tLJGetManualSubSchema.setPayCode(tPayCode[i]);
							tLJGetManualSubSchema.setMainBussNo(tMainBussNo[i]);
							tLJGetManualSubSchema.setBussType(tBussType[i]);
							tLJGetManualSubSchema.setBussNo(tBussNo[i]);
							tLJGetManualSubSchema.setCustomerNo(tCustomerNo[i]);
							tLJGetManualSubSchema.setMoney(tMoney[i]);
							tLJGetManualSubSchema.setShouldDate(tShouldDate[i]);
							tLJGetManualSubSchema.setCustBankCode(tCustBankCode[i]);
							tLJGetManualSubSchema.setBankCode(tBankCode[i]);
							tLJGetManualSubSchema.setCustBankProvince(tCustBankProvince[i]);
							tLJGetManualSubSchema.setCustBankCity(tCustBankCity[i]);
							tLJGetManualSubSchema.setCustBankAccNo(tCustBankAccNo[i]);
							tLJGetManualSubSchema.setCustAccName(tCustAccName[i]);
							tLJGetManualSubSchema.setInsuranceCom(tInsuranceCom[i]);
							tLJGetManualSubSchema.setBussCom(tAppCom);
							tLJGetManualSubSchema.setModifyTime(tGetObj[i]);//借用该字段传递对公/对私标识
							
							tLJGetManualSubSet.add(tLJGetManualSubSchema);
						}
					}
				}
				tVData.add(tLJGetManualSubSet);
			} else if ("CANCELCLICK".equals(tOperate) || "MODIFYBANK".equals(tOperate)) {
				
				
				String tChk[] = request.getParameterValues("InpApplyGetInfoGridChk");//获取待匹配信息
				if (tChk!=null && tChk.length>0) {
					
					String tTransNo[] = request.getParameterValues("ApplyGetInfoGrid1");//发盘号
					String tGetDealType[] = request.getParameterValues("ApplyGetInfoGrid3");//add by songsz 增加转溢缴数据处理
					
					for (int i=0; i<tChk.length; i++) {
						
						if ("1".equals(tChk[i])) {
						
							LJGetManualSubSchema tLJGetManualSubSchema = new LJGetManualSubSchema();
							tLJGetManualSubSchema.setTransNo(tTransNo[i]);
							tLJGetManualSubSchema.setGetDealType(tGetDealType[i]);
							
							tLJGetManualSubSet.add(tLJGetManualSubSchema);
						}
					}
				}
				
				tVData.add(tLJGetManualSubSet);
			} else if ("CONFIRM".equals(tOperate)){
			
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("ApplyBatNo", tApplyBatNo);
				
				tVData.add(tTransferData);
			}

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJGetManualAppSubUI")) {
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
