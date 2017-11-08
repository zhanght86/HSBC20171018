<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%
/***************************************************************
 * <p>ProName：LCInsuredAddSave.jsp</p>
 * <p>Title：添加被保人清单</p>
 * <p>Description：添加被保人清单</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-23
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LCContSchema"%>
<%@page import="com.sinosoft.lis.schema.LCInsuredSchema"%>
<%@page import="com.sinosoft.lis.schema.LCCustomerContactInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.LCCustomerAccountSchema"%>
<%@page import="com.sinosoft.lis.schema.LDPersonSchema"%>
<%@page import="com.sinosoft.lis.schema.LDPersonContactInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.LCPolSchema"%>
<%@page import="com.sinosoft.lis.schema.LCDutySchema"%>
<%@page import="com.sinosoft.lis.schema.LCPremSchema"%>
<%@page import="com.sinosoft.lis.schema.LCPerInvestPlanSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCDutySet"%>
<%@page import="com.sinosoft.lis.vschema.LCPremSet"%>
<%@page import="com.sinosoft.lis.vschema.LCPerInvestPlanSet"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	String tContno ="";
	String tInsuredNo ="";
	StringBuffer mStrBuff = new StringBuffer();
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			//接收信息，并作校验处理。
			LCContSchema tmLCContSchema = new LCContSchema();
			LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
			LCCustomerContactInfoSchema  tmLCCustomerContactInfoSchema = new LCCustomerContactInfoSchema(); // 保单个人客户联系信息
			LCCustomerAccountSchema tmLCCustomerAccountSchema = new LCCustomerAccountSchema();
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			LDPersonContactInfoSchema tLDPersonContactInfoSchema = new LDPersonContactInfoSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			ExeSQL mExeSQL = new ExeSQL();
			TransferData tTransferData = new TransferData();
			
			// 被保人基本信息
			String tOperate = request.getParameter("Operate");
			String tInsuredSeqNo = request.getParameter("InsuredSeqNo");
			String tContNo= request.getParameter("mContNo");
			
			String tGrpPrtNo = request.getParameter("GrpPropNo"); // 投保书号
			String tInsuredType = request.getParameter("InsuredType"); // 被保险人类型
			String tContGradeCode = request.getParameter("ContPlanCode"); // 方案编码
			String tsysPlanCode = request.getParameter("sysPlanCode"); // 系统方案编码
			String tRelatomain = request.getParameter("relatomain"); // 主附被保人关系
			String tmainCustNo = request.getParameter("mainCustNo"); // 主被保险人客户号
			String tJGContPlanCode =  request.getParameter("JGContPlanCode"); // 主被保险人方案编码
			String tJGsysContPlanCode = request.getParameter("JGsysContPlanCode"); // 主被保险人系统方案编码
			
			String tPlanFlag = request.getParameter("PlanFlag");
			String tempSubPropNo= "";
			if("00".equals(tPlanFlag)){
				mStrBuff = new StringBuffer();
				mStrBuff.append("select subpropno from lcproposaltocont where grppropno='"+"?tGrpPrtNo?"+"' and policyflag in('0','C')");
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(mStrBuff.toString());
				sqlbv1.put("tGrpPrtNo",tGrpPrtNo);
				tempSubPropNo = mExeSQL.getOneValue(sqlbv1);
			}else if("01".equals(tPlanFlag)){
				mStrBuff = new StringBuffer();
				mStrBuff.append("select subpropno from lcproposaltocont where grppropno='"+"?tGrpPrtNo?"+"' and policyflag ='S' ");
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(mStrBuff.toString());
				sqlbv1.put("tGrpPrtNo",tGrpPrtNo);
				tempSubPropNo = mExeSQL.getOneValue(sqlbv1);
			}
			if(!"".equals(tempSubPropNo)){
				tGrpPrtNo = tempSubPropNo;
			}
			String tInsuredName ="";
			String tIDType ="";			
			String tIDNo ="";
			String tSex ="";
			String tBirthday ="";
			String tInsuredPeoples ="";
			
			if("1".equals(tInsuredType)){		
				tInsuredName = "无名单"; // 被保人姓名
				tIDType = "8"; // 证件类型
				tIDNo = "1900-01-01"; // 证件号码（降录入的证件号码字母转化为大写）
				tSex = "2";
				tBirthday = "1900-01-01";
				tInsuredPeoples = request.getParameter("InsuredPeoples");
			}else {
				tInsuredName = request.getParameter("InsuredName"); // 被保人姓名
				tIDType = request.getParameter("IDType"); // 证件类型
				tIDNo = request.getParameter("IDNo").toUpperCase(); // 证件号码（降录入的证件号码字母转化为大写）
				tSex = request.getParameter("InsuredGender");
				tBirthday = request.getParameter("InsuredBirthDay");
				tInsuredPeoples = "1";
			}
			
			String tInsuredAppAge = request.getParameter("InsuredAppAge");
			String tOccupationCode = request.getParameter("OccupationCode");// 职业代码
			String tOccupationType = request.getParameter("OccupationType");//职业类别
			String tZipCode = request.getParameter("ZipCode");
			String tEMail = request.getParameter("EMail");
			String tMicroNo = request.getParameter("MicroNo"); // 微信号
			String tPhone = request.getParameter("Phone");
			String tMobile = request.getParameter("Mobile"); 
			String tProvinceCode = request.getParameter("ProvinceCode"); // 省编码
			String tCityCode = request.getParameter("CityCode"); // 市编码
			String tCountyCode = request.getParameter("CountyCode"); // 区编码
			String tDetailAddress = request.getParameter("DetailAddress"); // 详细地址
			
			//特殊信息		
			String tServerArea = request.getParameter("ServerArea"); //服务区域
			String tSubstandard = request.getParameter("Substandard"); //是否次标准体
			String tSocial = request.getParameter("Social");  //社保标记
			String tPosition = request.getParameter("Position");//职级
			String tJoinCompDate = request.getParameter("JoinCompDate"); //入司时间
			String tSeniority = request.getParameter("Seniority"); //工龄
			String tSalary = request.getParameter("Salary"); //月薪
			String tGetYear = request.getParameter("GetYear"); //领取年龄
			String tGetStartType = request.getParameter("GetStartType"); //起领日期计算类型

			// 银行信息
			String tBankCode = request.getParameter("HeadBankCode"); //开户银行
			String tBankCodeName = request.getParameter("BankCodeName");
			String tAccName = request.getParameter("AccName"); //开户名
			String tBankAccNo = request.getParameter("BankAccNo"); //账号
			String tProvince = request.getParameter("BankProvince"); //开户银行省
			String tCity = request.getParameter("BankCity"); //开户行所在市

			// 其他信息
			String tWorkIDNo = request.getParameter("WorkIDNo"); //员工号
			String tISLongValid = request.getParameter("ISLongValid"); //证件是否长期
			String tLiscenceValidDate = request.getParameter("LiscenceValidDate"); //证件有效期
			String tComponyName = request.getParameter("ComponyName"); //所在分公司
			String tDeptCode = request.getParameter("DeptCode"); //所在部门
			String tInsureCode = request.getParameter("InsureCode"); //被保险人编码
			//String tSubCustomerNo = request.getParameter("SubCustomerNo"); //所属客户群
			String tWorkAddress = request.getParameter("WorkAddress"); //工作地
			String tSocialAddress = request.getParameter("SocialAddress"); //社保地
			
			tLDPersonSchema.setName(tInsuredName); 
			tLDPersonSchema.setSex(tSex);
			tLDPersonSchema.setBirthday(tBirthday);
			tLDPersonSchema.setIDType(tIDType);
			tLDPersonSchema.setIDNo(tIDNo);
			tLDPersonSchema.setIDValFlag(tISLongValid);
			tLDPersonSchema.setIDExpDate(tLiscenceValidDate);
			tLDPersonSchema.setJoinCompanyDate(tJoinCompDate);
			tLDPersonSchema.setSalary(tSalary);
			tLDPersonSchema.setOccupationType(tOccupationType);
			tLDPersonSchema.setOccupationCode(tOccupationCode);
			tLDPersonSchema.setSocialInsuFlag(tSocial);
			tLDPersonSchema.setWorkAddress(tWorkAddress);
			tLDPersonSchema.setSocialInsuAddress(tSocialAddress);
			
			tLDPersonContactInfoSchema.setProvince(tProvinceCode);
			tLDPersonContactInfoSchema.setCity(tCityCode);
			tLDPersonContactInfoSchema.setCounty(tCountyCode);
			tLDPersonContactInfoSchema.setPostalAddress(tDetailAddress);
			tLDPersonContactInfoSchema.setZipCode(tZipCode);
			tLDPersonContactInfoSchema.setPhone(tPhone);
			tLDPersonContactInfoSchema.setMobile1(tMobile);
			tLDPersonContactInfoSchema.setEMail1(tEMail);
			tLDPersonContactInfoSchema.setWechat1(tMicroNo);

			tmLCContSchema.setGrpContNo(tGrpPrtNo);
			tmLCContSchema.setProposalContNo(tGrpPrtNo);
			tmLCContSchema.setPrtNo(tGrpPrtNo);
			tmLCContSchema.setContFlag(tInsuredType);
			tmLCContSchema.setContNo(tContNo);
			tmLCContSchema.setSumNumPeople(tInsuredPeoples);
			
			tmLCInsuredSchema.setGrpContNo(tGrpPrtNo);
			tmLCInsuredSchema.setContNo(tContNo);
			tmLCInsuredSchema.setInsuredNo(tInsuredSeqNo);
			tmLCInsuredSchema.setRelationToMainInsured(tRelatomain);
			tmLCInsuredSchema.setMainCustomerNo(tmainCustNo); // 主被保人客户号
			tmLCInsuredSchema.setInsuredType(tInsuredType);
			tmLCInsuredSchema.setName(tInsuredName);
			tmLCInsuredSchema.setSex(tSex);
			tmLCInsuredSchema.setBirthday(tBirthday);
			tmLCInsuredSchema.setIDType(tIDType);
			tmLCInsuredSchema.setIDNo(tIDNo);
			tmLCInsuredSchema.setIsLongValid(tISLongValid);
			tmLCInsuredSchema.setIDExpDate(tLiscenceValidDate);
			tmLCInsuredSchema.setInsuredAppAge(tInsuredAppAge);
			tmLCInsuredSchema.setSeniority("".equals(tSeniority)?"-1":tSeniority);
			tmLCInsuredSchema.setWorkNo(tWorkIDNo);
			tmLCInsuredSchema.setJoinCompanyDate(tJoinCompDate);
			tmLCInsuredSchema.setSalary("".equals(tSalary)?"-1":tSalary);
			tmLCInsuredSchema.setOccupationType(tOccupationType);
			tmLCInsuredSchema.setOccupationCode(tOccupationCode);
			tmLCInsuredSchema.setSocialInsuFlag(tSocial);
			if("0".equals(tInsuredType)){
				tmLCInsuredSchema.setPlanCode(tContGradeCode);
				tmLCInsuredSchema.setContPlanCode(tsysPlanCode);	
			}else{
				tmLCInsuredSchema.setPlanCode(tJGContPlanCode);
				tmLCInsuredSchema.setContPlanCode(tJGsysContPlanCode);
			}
			tmLCInsuredSchema.setServerArea(tServerArea);
			//tmLCInsuredSchema.setSubCustomerNo(tSubCustomerNo);
			tmLCInsuredSchema.setSubstandard(tSubstandard);
			tmLCInsuredSchema.setDeptCode(tDeptCode);
			tmLCInsuredSchema.setSubCompanyCode(tComponyName);
			tmLCInsuredSchema.setInsureCode(tInsureCode);
			tmLCInsuredSchema.setWorkAddress(tWorkAddress);
			tmLCInsuredSchema.setSocialInsuAddress(tSocialAddress);
			tmLCInsuredSchema.setInsuredPeoples(tInsuredPeoples);
			tmLCInsuredSchema.setPosition(tPosition);
			
			tmLCCustomerContactInfoSchema.setPolicyNo(tGrpPrtNo);
			tmLCCustomerContactInfoSchema.setContNo(tContNo);
			tmLCCustomerContactInfoSchema.setCustomerNo(tInsuredSeqNo);
			tmLCCustomerContactInfoSchema.setPropNo(tGrpPrtNo);
			tmLCCustomerContactInfoSchema.setProvince(tProvinceCode);
			tmLCCustomerContactInfoSchema.setCity(tCityCode);
			tmLCCustomerContactInfoSchema.setCounty(tCountyCode);
			tmLCCustomerContactInfoSchema.setPostalAddress(tDetailAddress);
			tmLCCustomerContactInfoSchema.setZipCode(tZipCode);
			tmLCCustomerContactInfoSchema.setPhone(tPhone);
			tmLCCustomerContactInfoSchema.setMobile1(tMobile);
			tmLCCustomerContactInfoSchema.setEMail1(tEMail);
			tmLCCustomerContactInfoSchema.setWechat1(tMicroNo);
			
			tmLCCustomerAccountSchema.setPolicyNo(tGrpPrtNo);
			tmLCCustomerAccountSchema.setCustomerNo(tInsuredSeqNo);
			tmLCCustomerAccountSchema.setCustomerType("01");
			tmLCCustomerAccountSchema.setBankAccNo(tBankAccNo);
			tmLCCustomerAccountSchema.setAccName(tAccName);
			
			tLCPolSchema.setGetYear("".equals(tGetYear)?"-1":tGetYear);
			tLCPolSchema.setGetStartType(tGetStartType);
			
			LCDutySet tLCDutySet = new LCDutySet();
			LCDutySchema  tLCDutySchema = new LCDutySchema();	
			if("POLSave".equals(tOperate)){
				String tPolNum[] = request.getParameterValues("QueryScanGridNo");
				String tContNon[] = request.getParameterValues("QueryScanGrid1");
				String tPolNon[] = request.getParameterValues("QueryScanGrid2");
				String tRiskCode[] = request.getParameterValues("QueryScanGrid3");//险种编码
				String tDutyCode[] = request.getParameterValues("QueryScanGrid5");//责任编码
				String tAmntType[] = request.getParameterValues("QueryScanGrid7");// 保单类型
				String tAmnt[] = request.getParameterValues("QueryScanGrid9"); // 保额
				
				if(null !=tPolNum && tPolNum.length>=1){	
					for(int i=0; i<tPolNum.length;i++){
						double iAmnt = Double.parseDouble(tAmnt[i]);
						tLCDutySchema = new LCDutySchema();
						tLCDutySchema.setStandbyFlag2(tGrpPrtNo);
						tLCDutySchema.setContNo(tContNon[i]);
						tLCDutySchema.setPolNo(tPolNon[i]);
						tLCDutySchema.setRiskCode(tRiskCode[i]);
						tLCDutySchema.setDutyCode(tDutyCode[i]);
						tLCDutySchema.setStandbyFlag3(tAmntType[i]);
						tLCDutySchema.setAmnt(iAmnt);
						tLCDutySet.add(tLCDutySchema);
					}
				}
			}
			
			LCPremSet tLCPremSet = new LCPremSet();
			
			if ("PayPlanSave".equals(tOperate)) {
				
				String tGridNo[] = request.getParameterValues("InpPayPlanGridSel");
				String tPolNo[] = request.getParameterValues("PayPlanGrid1");
				String tDutyCode[] = request.getParameterValues("PayPlanGrid2");
				String tPayPlanCode[] = request.getParameterValues("PayPlanGrid4");
				String tPayPlanPrem[] = request.getParameterValues("PayPlanGrid6");
				
				for (int i=0; i<tGridNo.length; i++) {
					if (tGridNo[i].equals("1")) {
						
						LCPremSchema tLCPremSchema = new LCPremSchema();
						
						tLCPremSchema.setPolNo(tPolNo[i]);
						tLCPremSchema.setDutyCode(tDutyCode[i]);
						tLCPremSchema.setPayPlanCode(tPayPlanCode[i]);
						tLCPremSchema.setPrem(tPayPlanPrem[i]);
						tLCPremSet.add(tLCPremSchema);
					}
				}
			}
			
			LCPerInvestPlanSet tLCPerInvestPlanSet = new LCPerInvestPlanSet();
			
			if ("InvestSave".equals(tOperate)) {
				
				String tGridNo[] = request.getParameterValues("InvestGridNo");
				String tPolNo[] = request.getParameterValues("InvestGrid1");
				String tDutyCode[] = request.getParameterValues("InvestGrid2");
				String tPayPlanCode[] = request.getParameterValues("InvestGrid3");
				String tInsuAccNo[] = request.getParameterValues("InvestGrid4");
				String tInvestMoney[] = request.getParameterValues("InvestGrid6");
				String tInvestRate[] = request.getParameterValues("InvestGrid7");
				
				for (int i=0; i<tGridNo.length; i++) {
					
					LCPerInvestPlanSchema tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
					
					tLCPerInvestPlanSchema.setPolNo(tPolNo[i]);
					tLCPerInvestPlanSchema.setDutyCode(tDutyCode[i]);
					tLCPerInvestPlanSchema.setPayPlanCode(tPayPlanCode[i]);
					tLCPerInvestPlanSchema.setInsuAccNo(tInsuAccNo[i]);
					tLCPerInvestPlanSchema.setInvestMoney("".equals(tInvestMoney[i])?"-1":tInvestMoney[i]);
					tLCPerInvestPlanSchema.setInvestRate("".equals(tInvestRate[i])?"-1":tInvestRate[i]);
					tLCPerInvestPlanSet.add(tLCPerInvestPlanSchema);
				}
			}
			
			tTransferData.setNameAndValue("BankCode",tBankCode);
			tTransferData.setNameAndValue("Province",tProvince);
			tTransferData.setNameAndValue("City",tCity);
			tTransferData.setNameAndValue("InsuredSeqNo",tInsuredSeqNo);
			tTransferData.setNameAndValue("Relatomain",tRelatomain);	
			
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tmLCContSchema);
			tVData.add(tmLCInsuredSchema);
			tVData.add(tmLCCustomerContactInfoSchema);
			tVData.add(tmLCCustomerAccountSchema);
			tVData.add(tLDPersonSchema);
			tVData.add(tLDPersonContactInfoSchema);
			tVData.add(tLCPolSchema);
			tVData.add(tLCDutySet);
			tVData.add(tLCPremSet);
			tVData.add(tLCPerInvestPlanSet);
			tVData.add(tTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCContInsuredAddUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				if ("INSERT".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "被保人信息保存成功！";
				} else if("UPDATE".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "被保人信息修改成功！";
				} else if("DELETE".equals(tOperate)) {
					
					tContent = "被保人信息删除成功！";
				}	else if("POLSave".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "被保人险种保额修改成功！";
				}	else if("PayPlanSave".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "被保人缴费项信息修改成功！";
				}	else if("InvestSave".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "投资账户信息修改成功！";
				}
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tContno%>","<%=tInsuredNo%>");
</script>
</html>
