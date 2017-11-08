<%
/***************************************************************
 * <p>ProName：LLClinicBillSave.jsp</p>
 * <p>Title：简易门诊账单</p>
 * <p>Description：简易门诊账单</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
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
		
		String tOperate = request.getParameter("Operate");//获得操作状态
		String tOldBillName=request.getParameter("HospBillNo1");
		String tCaseSource =request.getParameter("CaseSource");
		String tSerialNo1 =request.getParameter("tSerialNo2");
		String tHospCode= request.getParameter("tempHosp");
		String tHospStart =request.getParameter("tempHospStart");
		String tHospEnd =request.getParameter("tempHospEnd");
		String tResult1 =request.getParameter("tempResult1");
		
		//准备数据容器信息
		LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();    
		LLEasyBillDeductItemSet tLLEasyBillDeductItemSet=new LLEasyBillDeductItemSet();
    //准备后台数据
		tLLFeeMainSchema.setClmNo(request.getParameter("tRgtNo"));
		tLLFeeMainSchema.setCaseNo(request.getParameter("tCaseNo2"));
		tLLFeeMainSchema.setMainFeeNo(request.getParameter("HospBillNo").trim());
		tLLFeeMainSchema.setRgtNo(request.getParameter("tRgtNo"));
		tLLFeeMainSchema.setCustomerNo(request.getParameter("tCustomerNo")); 
		tLLFeeMainSchema.setHospitalCode(request.getParameter("HospID"));
		tLLFeeMainSchema.setFeeType("OB");
		tLLFeeMainSchema.setReceiptNo(tSerialNo1);
		tLLFeeMainSchema.setHospitalName(request.getParameter("HospIDName"));
		tLLFeeMainSchema.setHospStartDate(request.getParameter("HospStartdate"));
		tLLFeeMainSchema.setHospEndDate(request.getParameter("HospEnddate"));
		tLLFeeMainSchema.setBillMoney(request.getParameter("HospBillMoney"));
		tLLFeeMainSchema.setOtherExplain(request.getParameter("HospRemark"));
		tLLFeeMainSchema.setECMNO(request.getParameter("HospScanNum"));
		tLLFeeMainSchema.setAccResult1(request.getParameter("HospICDNo"));
		tLLFeeMainSchema.setAccResult2(request.getParameter("HospICDDetail"));
		tLLFeeMainSchema.setRealHospDate(request.getParameter("HospDays"));
    
		LLCaseReceiptSet tLLCaseReceiptSet =new LLCaseReceiptSet(); 
 		String arrCount[] = request.getParameterValues("HospBillItemGridNo");
 	if (arrCount != null){
 	
				String tDeducttypecode[] =request.getParameterValues("HospBillItemGrid1"); //费用类型代码
				String tDeducttypename[] = request.getParameterValues("HospBillItemGrid2");	//费用类型
				String tDeductmoney[] = request.getParameterValues("HospBillItemGrid3");	//费用金额
				String tDeductdesc[] = request.getParameterValues("HospBillItemGrid4");	//自费
				String tRemark[] = request.getParameterValues("HospBillItemGrid5");	//部分自费
 			  int	lineCount = arrCount.length; //行数
			  for(int i=0;i<lineCount;i++){
			  
					LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
					tLLCaseReceiptSchema.setCustomerNo(request.getParameter("tCustomerNo"));
		    		tLLCaseReceiptSchema.setClmNo(request.getParameter("tRgtNo"));
		    		tLLCaseReceiptSchema.setRgtNo(request.getParameter("tRgtNo"));
					tLLCaseReceiptSchema.setCaseNo(request.getParameter("tCaseNo2"));
					tLLCaseReceiptSchema.setFeeItemType("OB");    //简易理赔
					tLLCaseReceiptSchema.setFeeItemCode(tDeducttypecode[i]);
					tLLCaseReceiptSchema.setFeeItemName(tDeducttypename[i]);
					tLLCaseReceiptSchema.setRefuseAmnt(tDeductmoney[i]);
					tLLCaseReceiptSchema.setAdjRemark(tDeductdesc[i]);
					tLLCaseReceiptSchema.setRemark(tRemark[i]);
					tLLCaseReceiptSchema.setStartDate(request.getParameter("HospStartdate"));   //开始日期
					tLLCaseReceiptSchema.setEndDate(request.getParameter("HospEnddate"));   //结束日期
					tLLCaseReceiptSchema.setDayCount(request.getParameter("HospDays"));
					tLLCaseReceiptSet.add(tLLCaseReceiptSchema);
 	      }
 	}
 		TransferData mTransferData = new TransferData();
 		mTransferData.setNameAndValue("OldBillName", tOldBillName);
		mTransferData.setNameAndValue("CaseSource", tCaseSource);
 		mTransferData.setNameAndValue("SerialNo", tSerialNo1);
 		mTransferData.setNameAndValue("HospCode", tHospCode);
 		mTransferData.setNameAndValue("HospStart", tHospStart);
 		mTransferData.setNameAndValue("HospEnd", tHospEnd);
 		mTransferData.setNameAndValue("Result1", tResult1);
	// 准备传输数据 VData
		VData tVData = new VData();	
		tVData.add(tGI);
		tVData.add(tLLFeeMainSchema);
		tVData.add(tLLCaseReceiptSet);
		tVData.add(mTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLSimpleBillUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功！";
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
