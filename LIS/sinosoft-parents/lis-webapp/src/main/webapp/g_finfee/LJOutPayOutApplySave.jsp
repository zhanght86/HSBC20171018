<%
/***************************************************************
 * <p>ProName：LJOutPayOutApplySave.jsp</p>
 * <p>Title：溢缴退费申请</p>
 * <p>Description：溢缴退费申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJRefundConfirmSchema"%>
<%@page import="com.sinosoft.lis.schema.LJRefundConfirmSubSchema"%>
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
			
			VData tVData = new VData();
			tVData.add(tGI);
			
			if ("APPLYCLICK".equals(tOperate)) {
				
				LJRefundConfirmSchema tLJRefundConfirmSchema = new LJRefundConfirmSchema();
				LJRefundConfirmSubSchema tLJRefundConfirmSubSchema = new LJRefundConfirmSubSchema();
				
				String tOutPaySel[] = request.getParameterValues("InpOutPayInfoGridSel");//获取待匹配信息
				if (tOutPaySel!=null && tOutPaySel.length>0) {
					
					String tBussCom[] = request.getParameterValues("OutPayInfoGrid1");//BussCom 业务机构
					String tBussNo[] = request.getParameterValues("OutPayInfoGrid3");//BussNo 业务号(保单号)
					String tCustomerNo[] = request.getParameterValues("OutPayInfoGrid4");//团体客户号 GrpName
					String tGrpName[] = request.getParameterValues("OutPayInfoGrid5");//团体客户名 GrpName
					String tMoney[] = request.getParameterValues("OutPayInfoGrid6");//金额 RefundMoney
					String tHeadBankCode[] = request.getParameterValues("OutPayInfoGrid7");//客户开户行 总行
					String tProvince[] = request.getParameterValues("OutPayInfoGrid9");//客户开户行省
					String tCity[] = request.getParameterValues("OutPayInfoGrid11");//客户开户行市
					String tBankCode[] = request.getParameterValues("OutPayInfoGrid13");//银行编码 ldbank.bankcode
					String tBankAccNo[] = request.getParameterValues("OutPayInfoGrid14");//客户银行账号
					String tAccName[] = request.getParameterValues("OutPayInfoGrid15");//客户账户名
					
					for (int i=0; i<tOutPaySel.length; i++) {
						
						if ("1".equals(tOutPaySel[i])) {
						
							tLJRefundConfirmSchema.setBussNo(tBussNo[i]);
							tLJRefundConfirmSchema.setCustomerNo(tCustomerNo[i]);
							tLJRefundConfirmSchema.setGrpName(tGrpName[i]);
							tLJRefundConfirmSchema.setBussCom(tBussCom[i]);
							
							tLJRefundConfirmSubSchema.setRefundMoney(tMoney[i]);
							tLJRefundConfirmSubSchema.setCustBankCode(tHeadBankCode[i]);
							tLJRefundConfirmSubSchema.setBankCode(tBankAccNo[i]);
							tLJRefundConfirmSubSchema.setCustBankProvince(tProvince[i]);
							tLJRefundConfirmSubSchema.setCustBankCity(tCity[i]);
							tLJRefundConfirmSubSchema.setCustBankAccNo(tBankAccNo[i]);
							tLJRefundConfirmSubSchema.setCustAccName(tAccName[i]);
							
							tVData.add(tLJRefundConfirmSchema);
							tVData.add(tLJRefundConfirmSubSchema);
						}
					}
				}
			} else {
				
				String tApplyBatNo = request.getParameter("ApplyBatNo");
			
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("ApplyBatNo", tApplyBatNo);
				
				tVData.add(tTransferData);
			}

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJOutPayOutApplyUI")) {
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
