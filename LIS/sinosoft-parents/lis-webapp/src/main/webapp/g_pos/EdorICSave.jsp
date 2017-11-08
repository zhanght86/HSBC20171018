<%
/***************************************************************
* <p>ProName：EdorICSave.jsp</p>
 * <p>Title：被保险人重要资料变更</p>
 * <p>Description：被保险人重要资料变更</p>
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
		
			//	
			String tOperate = request.getParameter("Operate");	
			String tGrpContNo = request.getParameter("GrpPropNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo = request.getParameter("EdorNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");
						
			String tSerialNo = request.getParameter("SerialNo"); 
			String tEdorValDate = request.getParameter("edorValDate");
			String tOldInsuredName = request.getParameter("InsuredOldName");
			String tOldInsuredIDNo =  request.getParameter("IdOldNo");
			String tInsuredName = request.getParameter("InsuredName");
			String tInsuredIDType = request.getParameter("IDType");
			String tInsuredIDTypeName = request.getParameter("IDTypeName");
			String tInsuredIDNo = request.getParameter("IDNo");
			String tInsuredGender = request.getParameter("InsuredGender");
			String tInsuredGenderName = request.getParameter("InsuredGenderName");
			String tInsuredBirthday = request.getParameter("InsuredBirthDay");
			String tOccupCode = request.getParameter("OccupationCode");
			String tOccupName =request.getParameter("OccupationCodeName");
			String tServerArea =request.getParameter("ServerArea");
			String tServerAreaName = request.getParameter("ServiceArName");
			String tSubstandard =  request.getParameter("Substandard");
			String tSubstandardName = request.getParameter("SubstandardName");
			String tSocialInsuFlag = request.getParameter("Social");
			String tSocialInsuFlagName = request.getParameter("SocialName");
			String tSalary =  request.getParameter("Salary");

			LBPOEdorInsuredListSchema tLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();		
			tLBPOEdorInsuredListSchema.setSerialNo(tSerialNo);		
			tLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);	
			tLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
			tLBPOEdorInsuredListSchema.setActivityID(tActivityID);
			tLBPOEdorInsuredListSchema.setEdorType(tEdorType);
			tLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
			tLBPOEdorInsuredListSchema.setEdorNo(tEdorNo);
			tLBPOEdorInsuredListSchema.setEdorValDate(tEdorValDate);
			tLBPOEdorInsuredListSchema.setOldInsuredName(tOldInsuredName);
			tLBPOEdorInsuredListSchema.setOldInsuredIDNo(tOldInsuredIDNo);
			tLBPOEdorInsuredListSchema.setInsuredName(tInsuredName);
			tLBPOEdorInsuredListSchema.setInsuredIDType(tInsuredIDType);
			tLBPOEdorInsuredListSchema.setInsuredIDTypeName(tInsuredIDTypeName);
			tLBPOEdorInsuredListSchema.setInsuredIDNo(tInsuredIDNo);
			tLBPOEdorInsuredListSchema.setInsuredGender(tInsuredGender);
			tLBPOEdorInsuredListSchema.setInsuredGenderName(tInsuredGenderName);		
			tLBPOEdorInsuredListSchema.setInsuredBirthday(tInsuredBirthday);
			tLBPOEdorInsuredListSchema.setOccupCode(tOccupCode);
			tLBPOEdorInsuredListSchema.setOccupName(tOccupName);
			tLBPOEdorInsuredListSchema.setServerArea(tServerArea);
			tLBPOEdorInsuredListSchema.setServerAreaName(tServerAreaName);
			tLBPOEdorInsuredListSchema.setSubstandard(tSubstandard);
			tLBPOEdorInsuredListSchema.setSubstandardName(tSubstandardName);
			tLBPOEdorInsuredListSchema.setSalary(tSalary);
			tLBPOEdorInsuredListSchema.setSocialInsuFlag(tSocialInsuFlag);
			tLBPOEdorInsuredListSchema.setSocialInsuFlagName(tSocialInsuFlagName);
				
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
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorICUI")) {
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
