<%
/***************************************************************
 * <p>ProName：EdorVCSave.jsp</p>
 * <p>Title：归属规则维护</p>
 * <p>Description：归属规则维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.schema.LPAccAscriptionSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPAccAscriptionSet"%>
<%@page import="com.sinosoft.lis.schema.LPPositionSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPPositionSet"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LPPositionSchema mLPPositionSchema = null;
	LPAccAscriptionSchema mLPAccAscriptionSchema = null;
	TransferData tTransferData=new TransferData();

	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		try {
			//获取操作类型的前台参数
			
			VData tVData = new VData();
			tVData.add(tGI);
			//参数获取
			tOperate = request.getParameter("Operate");	
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorNo = request.getParameter("EdorNo");
			
			String tEdorType = request.getParameter("EdorType");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");
			
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

			//---------------------保存职级信息----------------------------------
			if("INSERT".equals(tOperate)){

				mLPPositionSchema = new LPPositionSchema();
				mLPPositionSchema.setGrpContNo(tGrpContNo);
				mLPPositionSchema.setEdorNo(tEdorNo);
				mLPPositionSchema.setEdorType(tEdorType);
				mLPPositionSchema.setRiskCode(mRiskCode);
				mLPPositionSchema.setPositionCode(mPositionCode);
				mLPPositionSchema.setPositionCodeName(mPositionName);
				mLPPositionSchema.setCountType(mCountType);
			}else if("UPDATE".equals(tOperate)||"DELETE".equals(tOperate)){
				mLPPositionSchema = new LPPositionSchema();
				String tRadio[]=request.getParameterValues("InpPositionGridSel");
				for(int index=0;index<tRadio.length;index++){
					if(tRadio[index].equals("1")){
						String tGrid5[]=request.getParameterValues("PositionGrid5");
						tTransferData.setNameAndValue("SerialNo",tGrid5[index]);
						mLPPositionSchema.setSerialNo(tGrid5[index]);
						mLPPositionSchema.setEdorNo(tEdorNo);
						mLPPositionSchema.setEdorType(tEdorType);
						mLPPositionSchema.setPositionCode(mPositionCode);
						mLPPositionSchema.setPositionCodeName(mPositionName);
						mLPPositionSchema.setCountType(mCountType);
					}
				}
			}
			if("INSERT1".equals(tOperate)){
				
				mLPAccAscriptionSchema = new LPAccAscriptionSchema();
				mLPAccAscriptionSchema.setGrpContNo(tGrpContNo);
				mLPAccAscriptionSchema.setEdorNo(tEdorNo);
				mLPAccAscriptionSchema.setEdorType(tEdorType);
				mLPAccAscriptionSchema.setRiskCode(mRiskCode);
				mLPAccAscriptionSchema.setRuleType("0");
				mLPAccAscriptionSchema.setPositionCode(mAscriptionCode);
				mLPAccAscriptionSchema.setStartYears(myearStarte);
				mLPAccAscriptionSchema.setEndYears(myearEnd);
				mLPAccAscriptionSchema.setRate(mRate);
			}else if("UPDATE1".equals(tOperate)||"DELETE1".equals(tOperate)){
				mLPAccAscriptionSchema = new LPAccAscriptionSchema();
				String tRadio[]=request.getParameterValues("InpAscriptionGridSel");
				for(int index=0;index<tRadio.length;index++){
					if(tRadio[index].equals("1")){
						String tGrid6[]=request.getParameterValues("AscriptionGrid6");
						tTransferData.setNameAndValue("SerialNo",tGrid6[index]);
						mLPAccAscriptionSchema.setSerialNo(tGrid6[index]);
						mLPAccAscriptionSchema.setEdorNo(tEdorNo);
						mLPAccAscriptionSchema.setEdorType(tEdorType);
						mLPAccAscriptionSchema.setPositionCode(mAscriptionCode);
						mLPAccAscriptionSchema.setRuleType("0");
						mLPAccAscriptionSchema.setStartYears(myearStarte);
						mLPAccAscriptionSchema.setEndYears(myearEnd);
						mLPAccAscriptionSchema.setRate(mRate);		
					}
				}
			}
			if("INSERT2".equals(tOperate)){

				mLPAccAscriptionSchema = new LPAccAscriptionSchema();
				mLPAccAscriptionSchema.setRiskCode(mRiskCode);
				mLPAccAscriptionSchema.setGrpContNo(tGrpContNo);
				mLPAccAscriptionSchema.setEdorNo(tEdorNo);
				mLPAccAscriptionSchema.setEdorType(tEdorType);
				mLPAccAscriptionSchema.setRuleType("1");
				mLPAccAscriptionSchema.setPositionCode(mSepicalType);
				mLPAccAscriptionSchema.setStartYears("0");
				mLPAccAscriptionSchema.setEndYears("9999");
				mLPAccAscriptionSchema.setRate(mRate1);
			}else if("UPDATE2".equals(tOperate)||"DELETE2".equals(tOperate)){
				mLPAccAscriptionSchema = new LPAccAscriptionSchema();
				String tRadio[]=request.getParameterValues("InpSepicalAscriptionGridSel");
				for(int index=0;index<tRadio.length;index++){
					if(tRadio[index].equals("1")){
						String tGrid4[]=request.getParameterValues("SepicalAscriptionGrid4");
						tTransferData.setNameAndValue("SerialNo",tGrid4[index]);
						mLPAccAscriptionSchema.setSerialNo(tGrid4[index]);
						mLPAccAscriptionSchema.setEdorNo(tEdorNo);
						mLPAccAscriptionSchema.setEdorType(tEdorType);
						mLPAccAscriptionSchema.setPositionCode(mSepicalType);
						mLPAccAscriptionSchema.setStartYears("0");
						mLPAccAscriptionSchema.setEndYears("9999");
						mLPAccAscriptionSchema.setRate(mRate1);
					}
				}
			}
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		tTransferData.setNameAndValue("ActivityID",tActivityID);
		tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
		tTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tTransferData.setNameAndValue("EdorType",tEdorType);
		tVData.add(tGI);
		tVData.add(tTransferData);
		tVData.addElement(mLPPositionSchema);
		tVData.addElement(mLPAccAscriptionSchema);
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
		if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorVCUI")) {
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
