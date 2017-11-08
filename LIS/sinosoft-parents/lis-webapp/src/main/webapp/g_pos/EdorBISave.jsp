<%
/***************************************************************
* <p>ProName：EdorBISave.jsp</p>
 * <p>Title：被保险人银行信息变更</p>
 * <p>Description：被保险人银行信息变更</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-06-20
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInsuredListSchema"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInsuredListSet"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	LBPOEdorInsuredListSet mLBPOEdorInsuredListSet = new LBPOEdorInsuredListSet();
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			String tFlag1 = "Flag1";
			String tFlag2 = "Flag2";
			String tOperate = request.getParameter("Operate");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tActivityID = request.getParameter("ActivityID");
			
			String tEdorType = request.getParameter("EdorType");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tMaininsuredname = request.getParameter("MainInsuredNames");
			String tMaininsuredidno = request.getParameter("MainInsuredIdNos");
			
			String tEdorValDate = request.getParameter("EdorValDate");
			String tHeadBankCode = request.getParameter("HeadBankCode");
			String tHeadBankName = request.getParameter("HeadBankName");
			String tAccName = request.getParameter("AccName");
			String tBankAccNo = request.getParameter("BankAccNo");
			String tBankProvince = request.getParameter("BankProvince");
			String tBankProvinceName = request.getParameter("BankProvinceName");
			String tBankCity = request.getParameter("BankCity");
			String tBankCityName = request.getParameter("BankCityName");
			String tMobile = request.getParameter("Mobile");
			String tFS2 = request.getParameter("FS2");
			String tFS1 = request.getParameter("FS1");
			String tSerialNo = request.getParameter("SerialNo");
			String tOldInsuredName =request.getParameter("InsuredNames"); 
			String tOldInsuredIDNo = request.getParameter("InsuredIdNos");

			LBPOEdorInsuredListSchema mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
			if("INSERT".equals(tOperate)){
				mLBPOEdorInsuredListSchema.setSerialNo(tSerialNo);
				mLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
				mLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
				mLBPOEdorInsuredListSchema.setEdorType(tEdorType);
				mLBPOEdorInsuredListSchema.setActivityID(tActivityID);
				mLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
				mLBPOEdorInsuredListSchema.setOldInsuredIDNo(tOldInsuredIDNo);
				mLBPOEdorInsuredListSchema.setOldInsuredName(tOldInsuredName);
				mLBPOEdorInsuredListSchema.setEdorValDate(tEdorValDate);
				mLBPOEdorInsuredListSchema.setHeadBankCode(tHeadBankCode);
				mLBPOEdorInsuredListSchema.setHeadBankName(tHeadBankName);
				mLBPOEdorInsuredListSchema.setBankAccNo(tBankAccNo);
				mLBPOEdorInsuredListSchema.setAccName(tAccName);
				mLBPOEdorInsuredListSchema.setBankProvince(tBankProvince);
				mLBPOEdorInsuredListSchema.setBankProvinceName(tBankProvinceName);
				mLBPOEdorInsuredListSchema.setBankCity(tBankCity);
				mLBPOEdorInsuredListSchema.setBankCityName(tBankCityName);
				mLBPOEdorInsuredListSchema.setMobile(tMobile);
							
				if("on".equals(tFS2)&&tFS2!=null){
					String tGrid3 [] = request.getParameterValues("FSGrid3");
					String tGrid9 [] = request.getParameterValues("FSGrid9");
					String tChk[]=request.getParameterValues("InpFSGridChk");
					for(int index=0;index<tChk.length;index++){
						if(tChk[index].equals("1")){
							LBPOEdorInsuredListSchema nLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
							nLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
							nLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
							nLBPOEdorInsuredListSchema.setEdorType(tEdorType);
							nLBPOEdorInsuredListSchema.setActivityID(tActivityID);
							nLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);	
							nLBPOEdorInsuredListSchema.setMainInsuredName(tMaininsuredname);
							nLBPOEdorInsuredListSchema.setMainInsuredIDNo(tMaininsuredidno);
							nLBPOEdorInsuredListSchema.setOldInsuredIDNo(tGrid9 [index]);
							nLBPOEdorInsuredListSchema.setOldInsuredName(tGrid3 [index]);
							nLBPOEdorInsuredListSchema.setEdorValDate(tEdorValDate);
							nLBPOEdorInsuredListSchema.setHeadBankCode(tHeadBankCode);
							nLBPOEdorInsuredListSchema.setHeadBankName(tHeadBankName);
							nLBPOEdorInsuredListSchema.setBankAccNo(tBankAccNo);
							nLBPOEdorInsuredListSchema.setAccName(tAccName);
							nLBPOEdorInsuredListSchema.setBankProvince(tBankProvince);
							nLBPOEdorInsuredListSchema.setBankProvinceName(tBankProvinceName);
							nLBPOEdorInsuredListSchema.setBankCity(tBankCity);
							nLBPOEdorInsuredListSchema.setBankCityName(tBankCityName);
							nLBPOEdorInsuredListSchema.setMobile(tMobile);
							mLBPOEdorInsuredListSet.add(nLBPOEdorInsuredListSchema);
						}
						vTransferData.setNameAndValue("Flag",tFlag2);
					}
				}else if("on".equals(tFS1)&&tFS1!=null){
					vTransferData.setNameAndValue("Flag",tFlag1);
				}
							
			}else if("DELETE".equals(tOperate)){
					mLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
					mLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
					mLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
					mLBPOEdorInsuredListSchema.setEdorType(tEdorType);
					mLBPOEdorInsuredListSchema.setSerialNo(tSerialNo);
					mLBPOEdorInsuredListSet.add(mLBPOEdorInsuredListSchema);
			}
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			vTransferData.setNameAndValue("EdorType",tEdorType);

			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(mLBPOEdorInsuredListSet);
			tVData.add(mLBPOEdorInsuredListSchema);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorBIUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tContent = "处理成功!";
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
