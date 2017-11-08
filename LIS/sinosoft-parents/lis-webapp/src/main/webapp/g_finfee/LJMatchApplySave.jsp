<%
/***************************************************************
 * <p>ProName：LJMatchApplySave.jsp</p>
 * <p>Title：保费申请</p>
 * <p>Description：保费申请</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 宋慎哲
 * @version  : 8.0
 * @date     : 2014-03-14
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LJPremMatchFeeSchema"%>
<%@page import="com.sinosoft.lis.schema.LJPremMatchPaySchema"%>
<%@page import="com.sinosoft.lis.vschema.LJPremMatchFeeSet"%>
<%@page import="com.sinosoft.lis.vschema.LJPremMatchPaySet"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	String tQuotNo = "";
	String tQuotBatNo = "";
	String tOperate = "";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			
			VData tVData = new VData();
			tVData.add(tGI);
			
			tOperate = request.getParameter("Operate");
			if ("PREMMATCH".equals(tOperate)) {
				
				LJPremMatchFeeSet tLJPremMatchFeeSet = new LJPremMatchFeeSet();
				
				String tMatchFeeChk[] = request.getParameterValues("InpChoosedDataGridChk");//获取待匹配信息
				if (tMatchFeeChk!=null && tMatchFeeChk.length>0) {
					
					String tPremSource[] = request.getParameterValues("ChoosedDataGrid1");//保费来源
					String tSourceNo[] = request.getParameterValues("ChoosedDataGrid3");//来源编码
					String tGrpContNo[] = request.getParameterValues("ChoosedDataGrid4");//团体保单号
					String tBussType[] = request.getParameterValues("ChoosedDataGrid5");//业务类型编码
					String tBussNo[] = request.getParameterValues("ChoosedDataGrid7");//业务号码
					String tCustBankCode[] = request.getParameterValues("ChoosedDataGrid8");//客户开户行编码
					String tCustBankAccNo[] = request.getParameterValues("ChoosedDataGrid10");//客户账号
					String tCustAccName[] = request.getParameterValues("ChoosedDataGrid11");//客户账户名
					String tAppntNo[] = request.getParameterValues("ChoosedDataGrid12");//投保单位编码
					String tGrpName[] = request.getParameterValues("ChoosedDataGrid13");//投保单位名称
					String tInBankCode[] = request.getParameterValues("ChoosedDataGrid14");//收费银行编码
					String tInBankAccNo[] = request.getParameterValues("ChoosedDataGrid16");//收费账号
					String tEnterAccDate[] = request.getParameterValues("ChoosedDataGrid17");//到账日期
					String tMoney[] = request.getParameterValues("ChoosedDataGrid18");//到账金额
					String tCurOutPayMoney[] = request.getParameterValues("ChoosedDataGrid19");//溢缴金额
					
					for (int i=0; i<tMatchFeeChk.length; i++) {
						
						if ("1".equals(tMatchFeeChk[i])) {
						
							LJPremMatchFeeSchema tLJPremMatchFeeSchema = new LJPremMatchFeeSchema();
							tLJPremMatchFeeSchema.setDataSource(tPremSource[i]);
							tLJPremMatchFeeSchema.setSourceNo(tSourceNo[i]);
							tLJPremMatchFeeSchema.setGrpContNo(tGrpContNo[i]);
							tLJPremMatchFeeSchema.setBussType(tBussType[i]);
							tLJPremMatchFeeSchema.setBussNo(tBussNo[i]);
							tLJPremMatchFeeSchema.setCustBankCode(tCustBankCode[i]);
							tLJPremMatchFeeSchema.setCustBankAccNo(tCustBankAccNo[i]);
							tLJPremMatchFeeSchema.setCustAccName(tCustAccName[i]);
							tLJPremMatchFeeSchema.setAppntNo(tAppntNo[i]);
							tLJPremMatchFeeSchema.setGrpName(tGrpName[i]);
							tLJPremMatchFeeSchema.setInBankCode(tInBankCode[i]);
							tLJPremMatchFeeSchema.setInBankAccNo(tInBankAccNo[i]);
							tLJPremMatchFeeSchema.setEnterAccDate(tEnterAccDate[i]);
							tLJPremMatchFeeSchema.setMoney(tMoney[i]);
							tLJPremMatchFeeSchema.setCurOutPayMoney(tCurOutPayMoney[i]);
							
							tLJPremMatchFeeSet.add(tLJPremMatchFeeSchema);
						}
					}
					
					tVData.add(tLJPremMatchFeeSet);
				}
				
				LJPremMatchPaySet tLJPremMatchPaySet = new LJPremMatchPaySet();
				String tBussChk[] = request.getParameterValues("InpBusinessDataGridChk");//获取待匹配信息
				if (tBussChk!=null && tBussChk.length>0) {
				
					String tMainBussNo[] = request.getParameterValues("BusinessDataGrid1");//保单号(主业务号码)
					String tBussType[] = request.getParameterValues("BusinessDataGrid2");//业务类型编码
					String tSubBussNo[] = request.getParameterValues("BusinessDataGrid4");//业务号码(子业务号码)
					String tAppntNo[] = request.getParameterValues("BusinessDataGrid5");//投保单位编码
					String tGrpName[] = request.getParameterValues("BusinessDataGrid6");//投保人名称
					String tAgentName[] = request.getParameterValues("BusinessDataGrid7");//投保人名称
					String tBussDate[] = request.getParameterValues("BusinessDataGrid8");//业务日期
					String tDuePayMoney[] = request.getParameterValues("BusinessDataGrid9");//应收金额
					String tCurOutPayMoney[] = request.getParameterValues("BusinessDataGrid10");//溢缴金额
					String tInsurancecom[] = request.getParameterValues("BusinessDataGrid11");//共保公司编码
					String tInsurancecomName[] = request.getParameterValues("BusinessDataGrid12");//共保公司
				
					for (int i=0; i<tBussChk.length; i++) {
					
						if ("1".equals(tBussChk[i])) {
							
							LJPremMatchPaySchema tLJPremMatchPaySchema = new LJPremMatchPaySchema();
							tLJPremMatchPaySchema.setMainBussNo(tMainBussNo[i]);
							tLJPremMatchPaySchema.setBussType(tBussType[i]);
							tLJPremMatchPaySchema.setSubBussNo(tSubBussNo[i]);
							tLJPremMatchPaySchema.setAppntNo(tAppntNo[i]);
							tLJPremMatchPaySchema.setGrpName(tGrpName[i]);
							tLJPremMatchPaySchema.setBussDate(tBussDate[i]);
							tLJPremMatchPaySchema.setDuePayMoney(tDuePayMoney[i]);
							tLJPremMatchPaySchema.setCurOutPayMoney(tCurOutPayMoney[i]);
							tLJPremMatchPaySchema.setInsurancecom(tInsurancecom[i]);
							tLJPremMatchPaySchema.setInsurancecom(tInsurancecom[i]);
							tLJPremMatchPaySchema.setInsurancecomName(tInsurancecomName[i]);
							tLJPremMatchPaySchema.setModifyTime(tAgentName[i]);//借用该字段存储中介机构名称
							
							tLJPremMatchPaySet.add(tLJPremMatchPaySchema);
						}
					}
				}
				
				tVData.add(tLJPremMatchPaySet);
			} else if ("APPLYCONFIRM".equals(tOperate)) {
				
				TransferData tTransferData = new TransferData();
				String tMatchSerialNo = request.getParameter("MatchSerialNo");
				tTransferData.setNameAndValue("MatchSerialNo", tMatchSerialNo);
				
				tVData.add(tTransferData);
			} else if ("CANCELCLICK".equals(tOperate)) {
				
				TransferData tTransferData = new TransferData();
				String tMatchSerialNo = request.getParameter("MatchSerialNo");
				tTransferData.setNameAndValue("MatchSerialNo", tMatchSerialNo);
				
				tVData.add(tTransferData);
			}
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LJMatchApplyUI")) {
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
