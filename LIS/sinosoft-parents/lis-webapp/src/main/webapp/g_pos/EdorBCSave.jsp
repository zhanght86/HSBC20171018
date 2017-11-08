<%
/***************************************************************
* <p>ProName：EdorBCSave.jsp</p>
 * <p>Title：受益人维护</p>
 * <p>Description：受益人维护</p>
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
					
			String tOperate = request.getParameter("Operate");	
			String tGrpContNo = request.getParameter("GrpPropNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo =  request.getParameter("EdorNo");
			String tSerialNo = request.getParameter("SerialNo");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");			
			String tEdorValDate = request.getParameter("edorValDate");
			String tOldinsuredName = request.getParameter("InsuredOldName");
			String tOldinsuredidNo = request.getParameter("IdOldNo");
			
			LBPOEdorInsuredListSchema tLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
			tLBPOEdorInsuredListSchema.setSerialNo(tSerialNo);
			tLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
			tLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
			tLBPOEdorInsuredListSchema.setEdorType(tEdorType);
			tLBPOEdorInsuredListSchema.setActivityID(tActivityID);
			tLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
			tLBPOEdorInsuredListSchema.setEdorNo(tEdorNo);
			tLBPOEdorInsuredListSchema.setEdorValDate(tEdorValDate);
			tLBPOEdorInsuredListSchema.setOldInsuredName(tOldinsuredName);
			tLBPOEdorInsuredListSchema.setOldInsuredIDNo(tOldinsuredidNo);
	
			String tBnfNum[] = request.getParameterValues("BnfUpdateInfoGridNo");
			String tBnfTypeName[] = request.getParameterValues("BnfUpdateInfoGrid1");
			String tBnfType[] = request.getParameterValues("BnfUpdateInfoGrid2");//受益人类别编码
			String tBnfOrderName[] = request.getParameterValues("BnfUpdateInfoGrid3");
			String tBnfOrder[] =  request.getParameterValues("BnfUpdateInfoGrid4");
			String tBnfName[] = request.getParameterValues("BnfUpdateInfoGrid5");
			String tGenderName[] = request.getParameterValues("BnfUpdateInfoGrid6");
			String tGender[] = request.getParameterValues("BnfUpdateInfoGrid7");
			String tBirthday[] = request.getParameterValues("BnfUpdateInfoGrid8");
			String tIDTypeName[] = request.getParameterValues("BnfUpdateInfoGrid9");
			String tIDType[] = request.getParameterValues("BnfUpdateInfoGrid10");
			String tIDNo[] = request.getParameterValues("BnfUpdateInfoGrid11");
			String tRelationToInsuredName[] = request.getParameterValues("BnfUpdateInfoGrid12");
			String tRelationToInsured[] = request.getParameterValues("BnfUpdateInfoGrid13");
			String tBnfLot[] =  request.getParameterValues("BnfUpdateInfoGrid14");
			String tHeadBankCode[] =  request.getParameterValues("BnfUpdateInfoGrid16");
			String tHeadBankName[] =  request.getParameterValues("BnfUpdateInfoGrid15");
			String tAccName[] =  request.getParameterValues("BnfUpdateInfoGrid17");
			String tBankAccno[] =  request.getParameterValues("BnfUpdateInfoGrid18");
			String tBankProvinceName[] =  request.getParameterValues("BnfUpdateInfoGrid19");
			String tBankProvince[] =  request.getParameterValues("BnfUpdateInfoGrid20");
			String tBankCityName[] = request.getParameterValues("BnfUpdateInfoGrid21");
			String tBankCity[] = request.getParameterValues("BnfUpdateInfoGrid22");	
			String tMobile[] = request.getParameterValues("BnfUpdateInfoGrid23");
			
			LBPOEdorInsuredBnfListSet  tLBPOEdorInsuredBnfListSet = new LBPOEdorInsuredBnfListSet();
			LBPOEdorInsuredBnfListSchema  tLBPOEdorInsuredBnfListSchema = new LBPOEdorInsuredBnfListSchema();
			
			if(tBnfNum !=null || tBnfNum.length>=1){
				for(int i=0;i<tBnfNum.length;i++){
					tLBPOEdorInsuredBnfListSchema = new LBPOEdorInsuredBnfListSchema();
					tLBPOEdorInsuredBnfListSchema.setEdorAppNo(tEdorAppNo);
					tLBPOEdorInsuredBnfListSchema.setActivityID(tActivityID);
					tLBPOEdorInsuredBnfListSchema.setBatchNo(tEdorAppNo);
					tLBPOEdorInsuredBnfListSchema.setBnfType(tBnfType[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfTypeName(tBnfTypeName[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfOrder(tBnfOrder[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfOrderName(tBnfOrderName[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfName(tBnfName[i]);
					tLBPOEdorInsuredBnfListSchema.setGender(tGender[i]);
					tLBPOEdorInsuredBnfListSchema.setGenderName(tGenderName[i]);
					String xBirthday=tBirthday[i];
					tLBPOEdorInsuredBnfListSchema.setBirthday(tBirthday[i]);
					tLBPOEdorInsuredBnfListSchema.setIDType(tIDType[i]);
					if("11".equals(tIDType[i])){
						String xSex = PubFun.getSexFromId(tIDNo[i]);
						xBirthday = PubFun.getBirthdayFromId(tIDNo[i]);
						if(tGender[i]==null || "".equals(tGender[i])){
							tLBPOEdorInsuredBnfListSchema.setGender(xSex);
						}
						if(tBirthday[i]==null || "".equals(tBirthday[i])){
							tLBPOEdorInsuredBnfListSchema.setBirthday(xBirthday);
						}
					}
					
					tLBPOEdorInsuredBnfListSchema.setIDTypeName(tIDTypeName[i]);
					tLBPOEdorInsuredBnfListSchema.setIDNo(tIDNo[i]);
					tLBPOEdorInsuredBnfListSchema.setRelationToInsured(tRelationToInsured[i]);
					tLBPOEdorInsuredBnfListSchema.setRelationToInsuredName(tRelationToInsuredName[i]);
					if(tBnfLot[i]!=null&&!"".equals(tBnfLot[i])){
						tLBPOEdorInsuredBnfListSchema.setBnfLot(tBnfLot[i]);
					}
					tLBPOEdorInsuredBnfListSchema.setHeadBankCode(tHeadBankCode[i]);
					tLBPOEdorInsuredBnfListSchema.setHeadBankName(tHeadBankName[i]);
					tLBPOEdorInsuredBnfListSchema.setAccName(tAccName[i]);
					tLBPOEdorInsuredBnfListSchema.setBankAccNo(tBankAccno[i]);
					tLBPOEdorInsuredBnfListSchema.setBankProvinceName(tBankProvinceName[i]);
					tLBPOEdorInsuredBnfListSchema.setBankProvince(tBankProvince[i]);
					tLBPOEdorInsuredBnfListSchema.setBankCityName(tBankCityName[i]);
					tLBPOEdorInsuredBnfListSchema.setBankCity(tBankCity[i]);
					tLBPOEdorInsuredBnfListSchema.setMobile(tMobile[i]);
					tLBPOEdorInsuredBnfListSet.add(tLBPOEdorInsuredBnfListSchema);
				}
			}
		
			TransferData vTransferData = new TransferData();
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			VData tVData = new VData();		
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(tLBPOEdorInsuredBnfListSet);
			tVData.add(tLBPOEdorInsuredListSchema);

			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorBCUI")) {
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
