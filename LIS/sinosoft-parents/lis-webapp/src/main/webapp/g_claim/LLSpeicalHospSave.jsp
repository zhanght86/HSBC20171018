
<%
/***************************************************************
 * <p>ProName：LLSpeicalHospSave.jsp</p>
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
		String tOldBillName=request.getParameter("SpecialHospBillNo1");
		String tCaseSource =request.getParameter("CaseSource");
		String tSerialNo1 =request.getParameter("tSerialNo4");
		String tHospCode= request.getParameter("tempHosp");
		String tHospStart =request.getParameter("tempHospStart");
		String tHospEnd =request.getParameter("tempHospEnd");
		String tResult1 =request.getParameter("tempResult1");
		//准备数据容器信息
		LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();    
    	//准备后台数据
		tLLFeeMainSchema.setClmNo(request.getParameter("tRgtNo"));
		tLLFeeMainSchema.setCaseNo(request.getParameter("tCaseNo4"));
		tLLFeeMainSchema.setMainFeeNo(request.getParameter("SpecialHospBillNo").trim());
		tLLFeeMainSchema.setCustomerNo(request.getParameter("tCustomerNo"));
		tLLFeeMainSchema.setHospitalCode(request.getParameter("SpecialHospID"));
		tLLFeeMainSchema.setFeeType("PB");
		tLLFeeMainSchema.setReceiptNo(tSerialNo1);
		tLLFeeMainSchema.setRealHospDate(request.getParameter("SpecialHospNum"));
		tLLFeeMainSchema.setHospitalName(request.getParameter("SpecialHospIDName"));
		tLLFeeMainSchema.setHospStartDate(request.getParameter("SpecialHospStart"));
		tLLFeeMainSchema.setHospEndDate(request.getParameter("SpecialHospEnd"));
		tLLFeeMainSchema.setBillMoney(request.getParameter("SpecialHospBillMoney"));
		tLLFeeMainSchema.setOtherExplain(request.getParameter("SpecialHospRemark"));
		tLLFeeMainSchema.setECMNO(request.getParameter("SpecialHospScanNum"));
		tLLFeeMainSchema.setAccResult1(request.getParameter("SpecialHospICD"));
   	tLLFeeMainSchema.setAccResult2(request.getParameter("SpecialHospICDDetail"));

  	String arrCount[] = request.getParameterValues("SpecialHospItemGridNo");
  	LLCaseReceiptSet tLLCaseReceiptSet =new LLCaseReceiptSet();
  	if (arrCount != null){
		String tFeeItemCode[] =request.getParameterValues("SpecialHospItemGrid1"); //费用类型代码
		String tFeeItemName[] = request.getParameterValues("SpecialHospItemGrid2");	//费用类型
		String tFee[] = request.getParameterValues("SpecialHospItemGrid3");	//费用金额
		String tSelfAmnt[] = request.getParameterValues("SpecialHospItemGrid4");	//自费
		String tPartlySelfAmnt[] = request.getParameterValues("SpecialHospItemGrid5");	//部分自费
		String tUnReasonAmnt[] = request.getParameterValues("SpecialHospItemGrid6");	//不合理自费
		String tRefuseAmnt[] = request.getParameterValues("SpecialHospItemGrid7");	//扣除金额
  	String tAdjRemark[] = request.getParameterValues("SpecialHospItemGrid8");	//扣除说明
  	String tRemark[] = request.getParameterValues("SpecialHospItemGrid9");	//备注
  		int	lineCount = arrCount.length; //行数
	
		for(int i=0;i<lineCount;i++){
		
		LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
		tLLCaseReceiptSchema.setCustomerNo(request.getParameter("tCustomerNo"));
	    tLLCaseReceiptSchema.setCaseNo(request.getParameter("tCaseNo4"));
	    tLLCaseReceiptSchema.setClmNo(request.getParameter("tRgtNo"));
	    tLLCaseReceiptSchema.setRgtNo(request.getParameter("tRgtNo"));
	    tLLCaseReceiptSchema.setFeeItemType("PB");    //门诊
	    tLLCaseReceiptSchema.setFeeItemCode(tFeeItemCode[i]);   //费用类型
	    tLLCaseReceiptSchema.setFeeItemName(tFeeItemName[i]);  //费用类型name
	    tLLCaseReceiptSchema.setFee(tFee[i]);  //费用金额
	    tLLCaseReceiptSchema.setStartDate(request.getParameter("SpecialHospStart"));   //开始日期
	    tLLCaseReceiptSchema.setEndDate(request.getParameter("SpecialHospEnd"));   //结束日期
	    tLLCaseReceiptSchema.setAdjRemark(tAdjRemark[i]);
	    tLLCaseReceiptSchema.setRefuseAmnt(tRefuseAmnt[i]);
	    tLLCaseReceiptSchema.setRemark(tRemark[i]);
	    tLLCaseReceiptSchema.setSelfAmnt(tSelfAmnt[i]);
	    tLLCaseReceiptSchema.setPartlyselfAmnt(tPartlySelfAmnt[i]);
	    tLLCaseReceiptSchema.setUnreasonAmnt(tUnReasonAmnt[i]);
	    tLLCaseReceiptSchema.setDayCount(request.getParameter("SpecialHospNum"));
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
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LLSpecialBillUI")) {
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
