<%
/***************************************************************
* <p>ProName：EdorBBSave.jsp</p>
 * <p>Title：被保险人基本资料变更</p>
 * <p>Description：被保险人基本资料变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : WEIGH
 * @version  : 8.0
 * @date     : 2014-06-13
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
		
			// 再保处理			
			String tOperate = request.getParameter("Operate");	
			String tSerialNo = request.getParameter("SerialNo");
			String tGrpContNo = request.getParameter("GrpPropNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo = request.getParameter("EdorNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");
			String tOldinsuredName = request.getParameter("InsuredOldName");
			String tOldinsuredidNo = request.getParameter("IdOldNo");
			
			String tEdorValDate = request.getParameter("edorValDate");
			String tRelationToMain = request.getParameter("relatomain");
			String tRelationToMainName = request.getParameter("relatomainName");
			String tZipCode = request.getParameter("ZipCode");
			String tEMail = request.getParameter("EMail");
			String tWechat = request.getParameter("MicroNo");
			String tPhone = request.getParameter("Phone");
			String tMobile = request.getParameter("Mobile");
			String tProvince = request.getParameter("ProvinceCode");
			String tProvinceName = request.getParameter("ProvinceName");
			String tCity = request.getParameter("CityCode");
			String tCityName = request.getParameter("CityName");
			String tCounty = request.getParameter("CountyCode");
			String tCountyName = request.getParameter("CountyName");
			String tAddress = request.getParameter("DetailAddress");
			String tJoinCompDate = request.getParameter("JoinCompDate");
			String tSeniority = request.getParameter("Seniority");
			String tWorkIDNo = request.getParameter("WorkIDNo");
			String tIsLongValid = request.getParameter("ISLongValid");
			String tIsLongValidName = request.getParameter("ISLongValidName");
			String tIDEndDate = request.getParameter("LiscenceValidDate");
			String tSubCompanyCode = request.getParameter("ComponyName");
			String tDeptCode = request.getParameter("DeptCode");
			String tInsureCode = request.getParameter("InsureCode");
			//String tSubCustomerNo = request.getParameter("SubCustomerNo");
			//String tSubCustomerName = request.getParameter("SubCustomerName");
			String tWorkAddress = request.getParameter("WorkAddress");
			String tSocialInsuAddress = request.getParameter("SocialAddress");
			
			LBPOEdorInsuredListSchema tLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();	
			tLBPOEdorInsuredListSchema.setSerialNo(tSerialNo);			
			tLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);	
			tLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
			tLBPOEdorInsuredListSchema.setActivityID(tActivityID);
			tLBPOEdorInsuredListSchema.setEdorNo(tEdorNo);
			tLBPOEdorInsuredListSchema.setEdorType(tEdorType);
			tLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
			tLBPOEdorInsuredListSchema.setOldInsuredName(tOldinsuredName);
			tLBPOEdorInsuredListSchema.setOldInsuredIDNo(tOldinsuredidNo);
			tLBPOEdorInsuredListSchema.setRelationToMain(tRelationToMain);
			tLBPOEdorInsuredListSchema.setRelationToMainName(tRelationToMainName);
			tLBPOEdorInsuredListSchema.setEdorValDate(tEdorValDate);
			tLBPOEdorInsuredListSchema.setZipCode(tZipCode);
			tLBPOEdorInsuredListSchema.setEMail(tEMail);
			tLBPOEdorInsuredListSchema.setWechat(tWechat);
			tLBPOEdorInsuredListSchema.setPhone(tPhone);
			tLBPOEdorInsuredListSchema.setMobile(tMobile);
			tLBPOEdorInsuredListSchema.setProvince(tProvince);
			tLBPOEdorInsuredListSchema.setProvinceName(tProvinceName);
			tLBPOEdorInsuredListSchema.setCity(tCity);
			tLBPOEdorInsuredListSchema.setCityName(tCityName);
			tLBPOEdorInsuredListSchema.setCounty(tCounty);
			tLBPOEdorInsuredListSchema.setCountyName(tCountyName);
			tLBPOEdorInsuredListSchema.setAddress(tAddress);
			tLBPOEdorInsuredListSchema.setJoinCompDate(tJoinCompDate);
			tLBPOEdorInsuredListSchema.setSeniority(tSeniority);
			tLBPOEdorInsuredListSchema.setWorkIDNo(tWorkIDNo);
			tLBPOEdorInsuredListSchema.setIsLongValid(tIsLongValid);
			tLBPOEdorInsuredListSchema.setIsLongValidName(tIsLongValidName);
			tLBPOEdorInsuredListSchema.setIDEndDate(tIDEndDate);
			tLBPOEdorInsuredListSchema.setSubCompanyCode(tSubCompanyCode);
			tLBPOEdorInsuredListSchema.setDeptCode(tDeptCode);
			tLBPOEdorInsuredListSchema.setInsureCode(tInsureCode);
			//tLBPOEdorInsuredListSchema.setSubCustomerNo(tSubCustomerNo);
			//tLBPOEdorInsuredListSchema.setSubCustomerName(tSubCustomerName);
			tLBPOEdorInsuredListSchema.setWorkAddress(tWorkAddress);
			tLBPOEdorInsuredListSchema.setSocialInsuAddress(tSocialInsuAddress);
			
			TransferData vTransferData = new TransferData();
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			VData tVData = new VData();		
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(tLBPOEdorInsuredListSchema);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorBBUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "被保人信息处理成功！";
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
