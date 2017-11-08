<%
/***************************************************************
 * <p>ProName：LAscriptionRuleSave.jsp</p>
 * <p>Title：归属规则维护</p>
 * <p>Description：归属规则维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-05-09
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.schema.LCAccAscriptionSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCAccAscriptionSet"%>
<%@page import="com.sinosoft.lis.schema.LCPositionSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCPositionSet"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCPositionSchema mLCPositionSchema = null;
	LCAccAscriptionSchema mLCAccAscriptionSchema = null;
	TransferData tTransferData=new TransferData();

	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");			
			VData tVData = new VData();
			tVData.add(tGI);
			//参数获取
			String mGrpContNo = request.getParameter("GrpContNo");
			String mRiskCode = request.getParameter("RiskCode");
			String mPositionCode=request.getParameter("PositionCode");
			String mPositionName=request.getParameter("PositionCodeName");
			String mCountType = request.getParameter("CountType");
			
			String mAscriptionCode=request.getParameter("AscriptionCode");
			String myearStarte=request.getParameter("StartYears");
			String myearEnd=request.getParameter("EndYears");
			String mRate=request.getParameter("Rate");
			
			String mSepicalType=request.getParameter("SepicalType");
			String mRate1=request.getParameter("Rate1");
			String mSerialNo=request.getParameter("SerialNo");
			tTransferData.setNameAndValue("SerialNo",mSerialNo);
			//---------------------保存职级信息----------------------------------
			if("INSERT".equals(tOperate)){

				mLCPositionSchema = new LCPositionSchema();
				mLCPositionSchema.setGrpContNo(mGrpContNo);
				mLCPositionSchema.setRiskCode(mRiskCode);
				mLCPositionSchema.setPositionCode(mPositionCode);
				mLCPositionSchema.setPositionCodeName(mPositionName);
				mLCPositionSchema.setCountType(mCountType);
				
			}else if("UPDATE".equals(tOperate)||"DELETE".equals(tOperate)){
				mLCPositionSchema = new LCPositionSchema();
				mLCPositionSchema.setPositionCode(mPositionCode);
				mLCPositionSchema.setPositionCodeName(mPositionName);
				mLCPositionSchema.setCountType(mCountType);
			}
			if("INSERT1".equals(tOperate)){
				
				mLCAccAscriptionSchema = new LCAccAscriptionSchema();
				mLCAccAscriptionSchema.setGrpContNo(mGrpContNo);
				mLCAccAscriptionSchema.setRiskCode(mRiskCode);
				mLCAccAscriptionSchema.setRuleType("0");
				mLCAccAscriptionSchema.setPositionCode(mAscriptionCode);
				mLCAccAscriptionSchema.setStartYears(myearStarte);
				mLCAccAscriptionSchema.setEndYears(myearEnd);
				mLCAccAscriptionSchema.setRate(mRate);
			}else if("UPDATE1".equals(tOperate)||"DELETE1".equals(tOperate)){
				mLCAccAscriptionSchema = new LCAccAscriptionSchema();
				mLCAccAscriptionSchema.setPositionCode(mAscriptionCode);
				mLCAccAscriptionSchema.setRuleType("0");
				mLCAccAscriptionSchema.setStartYears(myearStarte);
				mLCAccAscriptionSchema.setEndYears(myearEnd);
				mLCAccAscriptionSchema.setRate(mRate);
			}
			if("INSERT2".equals(tOperate)){
				mLCAccAscriptionSchema = new LCAccAscriptionSchema();
				mLCAccAscriptionSchema.setRiskCode(mRiskCode);
				mLCAccAscriptionSchema.setGrpContNo(mGrpContNo);
				mLCAccAscriptionSchema.setRuleType("1");
				mLCAccAscriptionSchema.setPositionCode(mSepicalType);
				mLCAccAscriptionSchema.setStartYears("0");
				mLCAccAscriptionSchema.setEndYears("9999");
				mLCAccAscriptionSchema.setRate(mRate1);
				
			}else if("UPDATE2".equals(tOperate)||"DELETE2".equals(tOperate)){
				mLCAccAscriptionSchema = new LCAccAscriptionSchema();
				mLCAccAscriptionSchema.setPositionCode(mSepicalType);
				mLCAccAscriptionSchema.setStartYears("0");
				mLCAccAscriptionSchema.setEndYears("9999");
				mLCAccAscriptionSchema.setRate(mRate1);
			}

		tVData.add(tGI);
		tVData.add(tTransferData);
		tVData.addElement(mLCPositionSchema);
		tVData.addElement(mLCAccAscriptionSchema);
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "LAscriptionRuleUI")) {
			tContent = tBusinessDelegate.getCErrors().getFirstError();
			tFlagStr = "Fail";
		} else {
			tFlagStr = "Succ";
			tContent = "处理成功！";
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>");
</script>
</html>
