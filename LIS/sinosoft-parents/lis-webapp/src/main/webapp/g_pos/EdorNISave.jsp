<%
/***************************************************************
* <p>ProName：EdorNISave.jsp</p>
 * <p>Title：新增被保险人</p>
 * <p>Description：新增被保险人</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInsuredBnfListSet"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInsuredListSet"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorUnFixedAmntListSet"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorPayPlanListSet"%>
<%@page import="com.sinosoft.lis.vschema.LBPOEdorInvestListSet"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInsuredListSchema"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInsuredBnfListSchema"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorUnFixedAmntListSchema"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorPayPlanListSchema"%>
<%@page import="com.sinosoft.lis.schema.LBPOEdorInvestListSchema"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	String tInsuredid ="";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
		
			// 被保人基本信息
			String tOperate = request.getParameter("Operate");
			String tEdorType = request.getParameter("EdorType");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tActivityID = request.getParameter("ActivityID");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tSerialno = request.getParameter("Serialno");
			String tInsuredID = request.getParameter("InsuredID");
			String tShowAmnt =  request.getParameter("AmntFlag");

			String tGrpContNo = request.getParameter("GrpPropNo"); // 投保书号
			String tInsuredType= request.getParameter("InsuredType");
			String tInsuredTypeName = request.getParameter("InsuredTypeName");
			String tRelationToMain = request.getParameter("relatomain"); // 主附被保人关系
			String tRelatomainName = request.getParameter("relatomainName");
			String tMainInsuredName = request.getParameter("mainCustName");
			String tMainInsuredIDNo = request.getParameter("mainIDNo");		
		
			String tEdorValDate = request.getParameter("edorValDate");// 保全生效日期
			
			String tPlanCode ="";
			String tPlanDesc ="";
			String tNumPeople = "";
			
			String tInsuredName ="";
			String tInsuredIDNo ="";
			String tInsuredGender ="";
			String tInsuredGenderName ="";
			String tInsuredBirthday ="";
			String tInsuredIDType ="";			
			String tInsuredIDTypeName ="";
						
			if("0".equals(tInsuredType)){			
				 tPlanCode = request.getParameter("ContPlanCode"); // 方案编码
				 tPlanDesc = request.getParameter("ContPlanCodeName");
				 tNumPeople = "1";		
				 tInsuredName = request.getParameter("InsuredName");;		
				 tInsuredIDNo =request.getParameter("IDNo").toUpperCase(); // 证件号码（降录入的证件号码字母转化为大写）
				 tInsuredGender =request.getParameter("InsuredGender");
				 tInsuredGenderName =request.getParameter("InsuredGenderName"); 
				 tInsuredBirthday =request.getParameter("InsuredBirthDay");
				 tInsuredIDType = request.getParameter("IDType");
				 tInsuredIDTypeName = request.getParameter("IDTypeName");
			}else if("1".equals(tInsuredType)){				
				 tPlanCode = request.getParameter("JGContPlanCode"); // 方案编码
				 tPlanDesc = request.getParameter("JGContPlanCodeName");		
				 tNumPeople = request.getParameter("InsuredPeoples");		
				 tInsuredName = "无名单"; // 被保人姓名
				 tInsuredIDNo = "1990-01-01"; 
				 tInsuredGender = "9";
				 tInsuredGenderName ="未说明的性别";
				 tInsuredBirthday ="1990-01-01";
				 tInsuredIDType = "28"; // 证件类型
				 tInsuredIDTypeName="其他证件";				 
				}
				
			String tOccupCode = request.getParameter("OccupationCode");// 职业代码
			String tOccupName  = request.getParameter("OccupationCodeName");
			
			// 银行信息
			String tHeadBankCode = request.getParameter("HeadBankCode"); //开户银行
			String tHeadBankName = request.getParameter("BankCodeName");
			String tAccName = request.getParameter("AccName"); //开户名
			String tBankAccNo = request.getParameter("BankAccNo"); //账号
			String tBankProvince = request.getParameter("BankProvince"); //开户银行省
			String tBankProvinceName = request.getParameter("BankProvinceName"); //开户银行省
			String tBankCity = request.getParameter("BankCity"); //开户行所在市
			String tBankCityName = request.getParameter("BankCityName");
			
			//特殊信息		
			String tServerArea = request.getParameter("ServerArea"); //服务区域
			String tServerAreaName = request.getParameter("ServiceArName");
			String tSubstandard = request.getParameter("Substandard"); //是否次标准体
			String tSubstandardName = request.getParameter("SubstandardName");
			String tSocialInsuFlag = request.getParameter("Social");  //社保标记
			String tSocialInsuFlagName = request.getParameter("SocialName");
			String tPosition = request.getParameter("Position");//职级
			String tPositionName = request.getParameter("PositionName");//职级名称
			String tJoinCompDate = request.getParameter("JoinCompDate"); //入司时间
			String tSeniority = request.getParameter("Seniority"); //工龄
			String tSalary = request.getParameter("Salary"); //月薪
			String tGetYear = request.getParameter("GetYear"); //领取年龄
			String tGetStartType = request.getParameter("GetStartType"); //起领日期计算类型
			String tGetStartTypeName = request.getParameter("GetStartTypeName"); //起领日期计算类型名称
			
			// 其他信息
			String tWorkIDNo = request.getParameter("WorkIDNo"); //员工号
			String tIsLongValid = request.getParameter("ISLongValid"); //证件是否长期
			String tIsLongValidName = request.getParameter("ISLongValidName"); 
			String tIDEndDate = request.getParameter("LiscenceValidDate"); //证件有效期
			String tSubCompanyCode = request.getParameter("ComponyName"); //所在分公司
			String tDeptCode = request.getParameter("DeptCode"); //所在部门
			String tInsureCode = request.getParameter("InsureCode"); //被保险人编码
			//String tSubCustomerNo = request.getParameter("SubCustomerNo"); //所属客户群
			//String tSubCustomerName = request.getParameter("SubCustomerName"); //所属客户群名称
			String tWorkAddress = request.getParameter("WorkAddress"); //工作地
			String tSocialInsuAddress = request.getParameter("SocialAddress"); //社保地
			
			// 被保人联系信息
			String tZipCode = request.getParameter("ZipCode");
			String tEMail = request.getParameter("EMail");
			String tWechat = request.getParameter("MicroNo"); // 微信号
			String tPhone = request.getParameter("Phone");
			String tMobile = request.getParameter("Mobile"); 
			String tProvince = request.getParameter("ProvinceCode"); // 省编码
			String tProvinceName = request.getParameter("ProvinceName");
			String tCity = request.getParameter("CityCode"); // 市编码
			String tCityName = request.getParameter("CityName"); 
			String tCounty = request.getParameter("CountyCode"); // 区编码
			String tCountyName = request.getParameter("CountyName");
			String tAddress = request.getParameter("DetailAddress"); // 详细地址
			
			LBPOEdorInsuredListSchema tLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
			LBPOEdorInsuredBnfListSet tLBPOEdorInsuredBnfListSet = new LBPOEdorInsuredBnfListSet();
			if("INSERT".equals(tOperate)||"UPDATE".equals(tOperate)){
				tLBPOEdorInsuredListSchema.setSerialNo(tSerialno);		
				tLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
				tLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
				tLBPOEdorInsuredListSchema.setActivityID(tActivityID);
				tLBPOEdorInsuredListSchema.setInsuredID(tInsuredID);
				tLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
				tLBPOEdorInsuredListSchema.setEdorType(tEdorType);
				tLBPOEdorInsuredListSchema.setInsuredType(tInsuredType);
				tLBPOEdorInsuredListSchema.setInsuredTypeName(tInsuredTypeName);
				tLBPOEdorInsuredListSchema.setNumPeople(tNumPeople);
				tLBPOEdorInsuredListSchema.setRelationToMain(tRelationToMain);
				tLBPOEdorInsuredListSchema.setRelationToMainName(tRelatomainName);
				tLBPOEdorInsuredListSchema.setMainInsuredName(tMainInsuredName);
				tLBPOEdorInsuredListSchema.setMainInsuredIDNo(tMainInsuredIDNo);
				tLBPOEdorInsuredListSchema.setOldInsuredIDNo(tInsuredIDNo);
				tLBPOEdorInsuredListSchema.setOldInsuredName(tInsuredName);
				tLBPOEdorInsuredListSchema.setInsuredName(tInsuredName);
				tLBPOEdorInsuredListSchema.setInsuredIDType(tInsuredIDType);
				tLBPOEdorInsuredListSchema.setInsuredIDTypeName(tInsuredIDTypeName);
				tLBPOEdorInsuredListSchema.setInsuredIDNo(tInsuredIDNo);
				tLBPOEdorInsuredListSchema.setInsuredGender(tInsuredGender);
				tLBPOEdorInsuredListSchema.setInsuredGenderName(tInsuredGenderName);
				tLBPOEdorInsuredListSchema.setInsuredBirthday(tInsuredBirthday);
				tLBPOEdorInsuredListSchema.setEdorValDate(tEdorValDate);
				tLBPOEdorInsuredListSchema.setPlanCode(tPlanCode);
				tLBPOEdorInsuredListSchema.setPlanDesc(tPlanDesc);
				tLBPOEdorInsuredListSchema.setOccupCode(tOccupCode);
				tLBPOEdorInsuredListSchema.setOccupName(tOccupName);
				tLBPOEdorInsuredListSchema.setHeadBankCode(tHeadBankCode);
				tLBPOEdorInsuredListSchema.setHeadBankName(tHeadBankName);
				tLBPOEdorInsuredListSchema.setAccName(tAccName);
				tLBPOEdorInsuredListSchema.setBankAccNo(tBankAccNo);
				tLBPOEdorInsuredListSchema.setBankProvince(tBankProvince);
				tLBPOEdorInsuredListSchema.setBankProvinceName(tBankProvinceName);
				tLBPOEdorInsuredListSchema.setBankCity(tBankCity);
				tLBPOEdorInsuredListSchema.setBankCityName(tBankCityName);
				tLBPOEdorInsuredListSchema.setServerArea(tServerArea);
				tLBPOEdorInsuredListSchema.setServerAreaName(tServerAreaName);
				tLBPOEdorInsuredListSchema.setSubstandard(tSubstandard);
				tLBPOEdorInsuredListSchema.setSubstandardName(tSubstandardName);
				tLBPOEdorInsuredListSchema.setSocialInsuFlag(tSocialInsuFlag);
				tLBPOEdorInsuredListSchema.setSocialInsuFlagName(tSocialInsuFlagName);
				tLBPOEdorInsuredListSchema.setPosition(tPosition);
				tLBPOEdorInsuredListSchema.setPositionName(tPositionName);
				tLBPOEdorInsuredListSchema.setJoinCompDate(tJoinCompDate);
				tLBPOEdorInsuredListSchema.setSeniority(tSeniority);
				tLBPOEdorInsuredListSchema.setSalary(tSalary);
				tLBPOEdorInsuredListSchema.setGetYear(tGetYear);
				tLBPOEdorInsuredListSchema.setGetStartType(tGetStartType);
				tLBPOEdorInsuredListSchema.setGetStartTypeName(tGetStartTypeName);
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
				
				// 受益人信息
				String tBnfNum[] = request.getParameterValues("BnfGridNo");
				String tBnfType[] = request.getParameterValues("BnfGrid2");//受益人类别
				String tBnfTypeName[] = request.getParameterValues("BnfGrid1");// 受益人类别名称
				String tBnfOrder[] = request.getParameterValues("BnfGrid4");//受益人顺序
				String tBnfOrderName[] = request.getParameterValues("BnfGrid3");// 受益人顺序名称
				String tBnfName[] = request.getParameterValues("BnfGrid5");//姓名
				String tGender[] = request.getParameterValues("BnfGrid7");//性别
				String tGenderName[] = request.getParameterValues("BnfGrid6");
				String tBirthday[] = request.getParameterValues("BnfGrid8");//出生日期
				String tIDType[] = request.getParameterValues("BnfGrid10");//证件类型
				String tIDTypeName[] = request.getParameterValues("BnfGrid9");
				String tIDNo[] = request.getParameterValues("BnfGrid11");//证件号码
				String tRelationToInsured[] = request.getParameterValues("BnfGrid13");//与被保人关系
				String tRelationToInsuredName[] = request.getParameterValues("BnfGrid12");
				String tBnfLot[] = request.getParameterValues("BnfGrid14");//受益比例	
				
				LBPOEdorInsuredBnfListSchema tLBPOEdorInsuredBnfListSchema   = new LBPOEdorInsuredBnfListSchema();
				if(null != tBnfNum &&tBnfNum.length>=1 ){
				for (int i = 0; i < tBnfNum.length; i++){
					
					tLBPOEdorInsuredBnfListSchema = new LBPOEdorInsuredBnfListSchema();					
					tLBPOEdorInsuredBnfListSchema.setEdorAppNo(tEdorAppNo);
					tLBPOEdorInsuredBnfListSchema.setActivityID(tActivityID);
					tLBPOEdorInsuredBnfListSchema.setBatchNo(tEdorAppNo);
					tLBPOEdorInsuredBnfListSchema.setInsuredID(tInsuredID);
					tLBPOEdorInsuredBnfListSchema.setBnfType(tBnfType[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfTypeName(tBnfTypeName[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfOrder(tBnfOrder[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfOrderName(tBnfOrderName[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfName(tBnfName[i]);
					tLBPOEdorInsuredBnfListSchema.setIDType(tIDType[i]);
					tLBPOEdorInsuredBnfListSchema.setIDTypeName(tIDTypeName[i]);
					tLBPOEdorInsuredBnfListSchema.setIDNo(tIDNo[i]);
					tLBPOEdorInsuredBnfListSchema.setGender(tGender[i]);
					tLBPOEdorInsuredBnfListSchema.setBirthday(tBirthday[i]);	
					tLBPOEdorInsuredBnfListSchema.setGenderName(tGenderName[i]);
					String xBirthday = tBirthday[i];					
					//如果是身份证号码，则生成受益人的性别和出生日期
					if("0".equals(tIDType[i])){
						String xSex = PubFun.getSexFromId(tIDNo[i]);
						xBirthday = PubFun.getBirthdayFromId(tIDNo[i]);
						if(tGender[i]==null || "".equals(tGender[i])){
							tLBPOEdorInsuredBnfListSchema.setGender(xSex);
						}
						if(tBirthday[i]==null || "".equals(tBirthday[i])){
							tLBPOEdorInsuredBnfListSchema.setBirthday(xBirthday);
						}
					}
					
					tLBPOEdorInsuredBnfListSchema.setRelationToInsured(tRelationToInsured[i]);
					tLBPOEdorInsuredBnfListSchema.setRelationToInsuredName(tRelationToInsuredName[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfLot(tBnfLot[i]);
					tLBPOEdorInsuredBnfListSet.add(tLBPOEdorInsuredBnfListSchema);
					}	
				}
			}

			LBPOEdorInsuredListSet tLBPOEdorInsuredListSet = new LBPOEdorInsuredListSet();
			if("CHOSEDEL".equals(tOperate)){
			
				String tInsredNum[] = request.getParameterValues("InpEdorDetailGridSel");
				String tSerialnoNO[] = request.getParameterValues("EdorDetailGrid16");//流水号
				
				LBPOEdorInsuredListSchema aLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
				if(null!=tInsredNum && tInsredNum.length>0){
				for (int i = 0; i < tInsredNum.length; i++){
					if (tInsredNum[i].equals("1")) {
						aLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
						aLBPOEdorInsuredListSchema.setSerialNo(tSerialnoNO[i]);
						tLBPOEdorInsuredListSet.add(aLBPOEdorInsuredListSchema);
						}
					}
				}
			}
			
			LBPOEdorUnFixedAmntListSet tLBPOEdorUnFixedAmntListSet = new LBPOEdorUnFixedAmntListSet();
			
			if("ShowAmnt".equals(tShowAmnt)){
			LBPOEdorUnFixedAmntListSchema tLBPOEdorUnFixedAmntListSchema = new LBPOEdorUnFixedAmntListSchema();	
			String tUnFixAmnt[] = request.getParameterValues("AmntGridNo");
			String tRiskCode[] = request.getParameterValues("AmntGrid1");
			String tDutyCode[] = request.getParameterValues("AmntGrid3");
			String tAmnt[] = request.getParameterValues("AmntGrid7");
			
			if(null !=tUnFixAmnt && tUnFixAmnt.length>=1 ){
				for (int j=0;j<tUnFixAmnt.length;j++){
					tLBPOEdorUnFixedAmntListSchema = new LBPOEdorUnFixedAmntListSchema();
					tLBPOEdorUnFixedAmntListSchema.setEdorAppNo(tEdorAppNo);
					tLBPOEdorUnFixedAmntListSchema.setGrpContNo(tGrpContNo);
					tLBPOEdorUnFixedAmntListSchema.setActivityID(tActivityID);
					tLBPOEdorUnFixedAmntListSchema.setBatchNo(tEdorAppNo);
					tLBPOEdorUnFixedAmntListSchema.setEdorType(tEdorType);
					tLBPOEdorUnFixedAmntListSchema.setInsuredID(tInsuredID);
					tLBPOEdorUnFixedAmntListSchema.setInsuredType(tInsuredType);
					tLBPOEdorUnFixedAmntListSchema.setInsuredTypeName(tInsuredTypeName);
					tLBPOEdorUnFixedAmntListSchema.setNumPeople(tNumPeople);
					tLBPOEdorUnFixedAmntListSchema.setRelationToMain(tRelationToMain);
					tLBPOEdorUnFixedAmntListSchema.setRelationToMainName(tRelatomainName);
					tLBPOEdorUnFixedAmntListSchema.setMainInsuredName(tMainInsuredName);
					tLBPOEdorUnFixedAmntListSchema.setMainInsuredIDNo(tMainInsuredIDNo);
					tLBPOEdorUnFixedAmntListSchema.setOldInsuredName(tInsuredName);
					tLBPOEdorUnFixedAmntListSchema.setOldInsuredIDNo(tInsuredIDNo);
					tLBPOEdorUnFixedAmntListSchema.setInsuredName(tInsuredName);
					tLBPOEdorUnFixedAmntListSchema.setInsuredIDType(tInsuredIDType);
					tLBPOEdorUnFixedAmntListSchema.setInsuredIDTypeName(tInsuredIDTypeName);
					tLBPOEdorUnFixedAmntListSchema.setInsuredIDNo(tInsuredIDNo);
					tLBPOEdorUnFixedAmntListSchema.setInsuredGender(tInsuredGender);
					tLBPOEdorUnFixedAmntListSchema.setInsuredGenderName(tInsuredGenderName);
					tLBPOEdorUnFixedAmntListSchema.setInsuredBirthday(tInsuredBirthday);
					tLBPOEdorUnFixedAmntListSchema.setEdorValDate(tEdorValDate);
					tLBPOEdorUnFixedAmntListSchema.setPlanCode(tPlanCode);
					tLBPOEdorUnFixedAmntListSchema.setPlanDesc(tPlanDesc);
					tLBPOEdorUnFixedAmntListSchema.setRiskCode(tRiskCode[j]);
					tLBPOEdorUnFixedAmntListSchema.setDutyCode(tDutyCode[j]);
					tLBPOEdorUnFixedAmntListSchema.setAmnt(tAmnt[j]);
					tLBPOEdorUnFixedAmntListSchema.setOccupCode(tOccupCode);
					tLBPOEdorUnFixedAmntListSchema.setOccupName(tOccupName);
					tLBPOEdorUnFixedAmntListSchema.setHeadBankCode(tHeadBankCode);
					tLBPOEdorUnFixedAmntListSchema.setHeadBankName(tHeadBankName);
					tLBPOEdorUnFixedAmntListSchema.setAccName(tAccName);
					tLBPOEdorUnFixedAmntListSchema.setBankAccNo(tBankAccNo);
					tLBPOEdorUnFixedAmntListSchema.setBankProvince(tBankProvince);
					tLBPOEdorUnFixedAmntListSchema.setBankProvinceName(tBankProvinceName);
					tLBPOEdorUnFixedAmntListSchema.setBankCity(tBankCity);
					tLBPOEdorUnFixedAmntListSchema.setBankCityName(tBankCityName);
					tLBPOEdorUnFixedAmntListSchema.setServerArea(tServerArea);
					tLBPOEdorUnFixedAmntListSchema.setServerAreaName(tServerAreaName);
					tLBPOEdorUnFixedAmntListSchema.setSubstandard(tSubstandard);
					tLBPOEdorUnFixedAmntListSchema.setSubstandardName(tSubstandardName);
					tLBPOEdorUnFixedAmntListSchema.setSocialInsuFlag(tSocialInsuFlag);
					tLBPOEdorUnFixedAmntListSchema.setSocialInsuFlagName(tSocialInsuFlagName);
					tLBPOEdorUnFixedAmntListSchema.setPosition(tPosition);
					tLBPOEdorUnFixedAmntListSchema.setPositionName(tPositionName);
					tLBPOEdorUnFixedAmntListSchema.setJoinCompDate(tJoinCompDate);
					tLBPOEdorUnFixedAmntListSchema.setSeniority(tSeniority);
					tLBPOEdorUnFixedAmntListSchema.setSalary(tSalary);
					tLBPOEdorUnFixedAmntListSchema.setWorkIDNo(tWorkIDNo);
					tLBPOEdorUnFixedAmntListSchema.setIsLongValid(tIsLongValid);
					tLBPOEdorUnFixedAmntListSchema.setIsLongValidName(tIsLongValidName);
					tLBPOEdorUnFixedAmntListSchema.setIDEndDate(tIDEndDate);
					tLBPOEdorUnFixedAmntListSchema.setSubCompanyCode(tSubCompanyCode);
					tLBPOEdorUnFixedAmntListSchema.setDeptCode(tDeptCode);
					tLBPOEdorUnFixedAmntListSchema.setInsureCode(tInsureCode);
					//tLBPOEdorUnFixedAmntListSchema.setSubCustomerNo(tSubCustomerNo);
					//tLBPOEdorUnFixedAmntListSchema.setSubCustomerName(tSubCustomerName);
					tLBPOEdorUnFixedAmntListSchema.setWorkAddress(tWorkAddress);
					tLBPOEdorUnFixedAmntListSchema.setSocialInsuAddress(tSocialInsuAddress);
					tLBPOEdorUnFixedAmntListSchema.setZipCode(tZipCode);
					tLBPOEdorUnFixedAmntListSchema.setEMail(tEMail);
					tLBPOEdorUnFixedAmntListSchema.setWechat(tWechat);
					tLBPOEdorUnFixedAmntListSchema.setPhone(tPhone);
					tLBPOEdorUnFixedAmntListSchema.setMobile(tMobile);
					tLBPOEdorUnFixedAmntListSchema.setProvince(tProvince);
					tLBPOEdorUnFixedAmntListSchema.setProvinceName(tProvinceName);
					tLBPOEdorUnFixedAmntListSchema.setCity(tCity);
					tLBPOEdorUnFixedAmntListSchema.setCityName(tCityName);
					tLBPOEdorUnFixedAmntListSchema.setCounty(tCounty);
					tLBPOEdorUnFixedAmntListSchema.setCountyName(tCountyName);
					tLBPOEdorUnFixedAmntListSchema.setAddress(tAddress);
					tLBPOEdorUnFixedAmntListSet.add(tLBPOEdorUnFixedAmntListSchema);
			 	}
			}
		}
		
			LBPOEdorPayPlanListSet tLBPOEdorPayPlanListSet = new LBPOEdorPayPlanListSet();
			
			if ("PayPlanSave".equals(tOperate)) {
				
				String tGridNo[] = request.getParameterValues("InpPayPlanGridSel");
				String tBatchNo[] = request.getParameterValues("PayPlanGrid1");
				String tPayInsuredID[] = request.getParameterValues("PayPlanGrid2");
				String tPayPlanID[] = request.getParameterValues("PayPlanGrid3");
				String tPayPlanPrem[] = request.getParameterValues("PayPlanGrid8");
				
				for (int i=0; i<tGridNo.length; i++) {
					if (tGridNo[i].equals("1")) {
						
						LBPOEdorPayPlanListSchema tLBPOEdorPayPlanListSchema = new LBPOEdorPayPlanListSchema();
						
						tLBPOEdorPayPlanListSchema.setEdorAppNo(tEdorAppNo);
						tLBPOEdorPayPlanListSchema.setBatchNo(tBatchNo[i]);
						tLBPOEdorPayPlanListSchema.setInsuredID(tPayInsuredID[i]);
						tLBPOEdorPayPlanListSchema.setPayPlanID(tPayPlanID[i]);
						tLBPOEdorPayPlanListSchema.setPayPlanPrem(tPayPlanPrem[i]);
						tLBPOEdorPayPlanListSet.add(tLBPOEdorPayPlanListSchema);
					}
				}
			}
			
			LBPOEdorInvestListSet tLBPOEdorInvestListSet = new LBPOEdorInvestListSet();
			
			if ("InvestSave".equals(tOperate)) {
				
				String tGridNo[] = request.getParameterValues("InvestGridNo");
				String tBatchNo[] = request.getParameterValues("InvestGrid1");
				String tPayPlanID[] = request.getParameterValues("InvestGrid3");
				String tInsuAccNo[] = request.getParameterValues("InvestGrid4");
				String tInvestMoney[] = request.getParameterValues("InvestGrid6");
				String tInvestRate[] = request.getParameterValues("InvestGrid7");
				
				for (int i=0; i<tGridNo.length; i++) {
					
					LBPOEdorInvestListSchema tLBPOEdorInvestListSchema = new LBPOEdorInvestListSchema();
					
					tLBPOEdorInvestListSchema.setEdorAppNo(tEdorAppNo);
					tLBPOEdorInvestListSchema.setBatchNo(tBatchNo[i]);
					tLBPOEdorInvestListSchema.setPayPlanID(tPayPlanID[i]);
					tLBPOEdorInvestListSchema.setInsuAccNo(tInsuAccNo[i]);
					tLBPOEdorInvestListSchema.setInvestMoney(tInvestMoney[i]);
					tLBPOEdorInvestListSchema.setInvestRate(tInvestRate[i]);
					tLBPOEdorInvestListSet.add(tLBPOEdorInvestListSchema);
				}
			}
		
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ActivityID",tActivityID);
			tTransferData.setNameAndValue("MissionID",tMissionID);
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			tTransferData.setNameAndValue("EdorType",tEdorType);
			tTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			tTransferData.setNameAndValue("ShowAmnt",tShowAmnt);
			tTransferData.setNameAndValue("InsuredNameQ",request.getParameter("InsuredNameQ"));
			tTransferData.setNameAndValue("IdNoQ",request.getParameter("IdNoQ"));
			tTransferData.setNameAndValue("InsuredNoQ",request.getParameter("InsuredNoQ"));
			tTransferData.setNameAndValue("PlanCodeQ",request.getParameter("PlanCodeQ"));
			tTransferData.setNameAndValue("SysPlanCodeQ",request.getParameter("sysPlanCodeQ"));
			tTransferData.setNameAndValue("ImpBatch",request.getParameter("ImpBatch"));
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLBPOEdorInsuredListSchema);
			tVData.add(tLBPOEdorInsuredListSet);
			tVData.add(tLBPOEdorInsuredBnfListSet);
			tVData.add(tLBPOEdorUnFixedAmntListSet);
			tVData.add(tLBPOEdorPayPlanListSet);
			tVData.add(tLBPOEdorInvestListSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorNIUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				if ("INSERT".equals(tOperate)) {
					tInsuredid = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tContent = "被保人信息保存成功！";
				} else if("UPDATE".equals(tOperate)) {
					tInsuredid = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tContent = "被保人信息修改成功！";
				} else if("CHOSEDEL".equals(tOperate)||"CONDDEL".equals(tOperate)|| "ALLDEL".equals(tOperate) ){		
					tContent = "被保人信息操作成功！";
				} else if("PayPlanSave".equals(tOperate)){
					tContent = "缴费项信息修改成功！";
				} else if("InvestSave".equals(tOperate)){
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tInsuredid%>");
</script>
</html>
