<%
/***************************************************************
 * <p>ProName：LCContPolSave.jsp</p>
 * <p>Title：新单录入</p>
 * <p>Description：新单录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-28
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.LCGrpContSchema"%>
<%@page import="com.sinosoft.lis.schema.LCAgentToContSchema"%>
<%@page import="com.sinosoft.lis.schema.LCAchvComInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.LCAgentComInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.LCAgentSaleSchema"%>
<%@page import="com.sinosoft.lis.schema.LCGrpAppntSchema"%>
<%@page import="com.sinosoft.lis.schema.LCGrpContactInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.LCCustomerIDSchema"%>
<%@page import="com.sinosoft.lis.schema.LCGrpLinkSchema"%>
<%@page import="com.sinosoft.lis.schema.LCCustomerAccountSchema"%>
<%@page import="com.sinosoft.lis.schema.LCGrpEngineeringSchema"%>
<%@page import="com.sinosoft.lis.schema.LCGrpEnginDetailSchema"%>
<%@page import="com.sinosoft.lis.schema.LCGrpPolSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCGrpContSet"%>//团体保单投保信息
<%@page import="com.sinosoft.lis.vschema.LCAgentToContSet"%>//客户经理
<%@page import="com.sinosoft.lis.vschema.LCAchvComInfoSet"%>//业绩归属地
<%@page import="com.sinosoft.lis.vschema.LCAgentComInfoSet"%>//中介机构
<%@page import="com.sinosoft.lis.vschema.LCAgentSaleSet"%>//代理人
<%@page import="com.sinosoft.lis.vschema.LCGrpAppntSet"%>//投保人资料
<%@page import="com.sinosoft.lis.vschema.LCGrpContactInfoSet"%>//联系信息
<%@page import="com.sinosoft.lis.vschema.LCCustomerIDSet"%>//证件信息
<%@page import="com.sinosoft.lis.vschema.LCGrpLinkSet"%>//多联系人
<%@page import="com.sinosoft.lis.vschema.LCCustomerAccountSet"%>//开户银行
<%@page import="com.sinosoft.lis.vschema.LCGrpEngineeringSet"%>//建工险
<%@page import="com.sinosoft.lis.vschema.LCGrpEnginDetailSet"%>//建工险
<%@page import="com.sinosoft.lis.vschema.LCGrpPolSet"%>//建工险

<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCGrpContSet mLCGrpContSet = null;
	LCAgentToContSet mLCAgentToContSet = null;
	LCAchvComInfoSet mLCAchvComInfoSet = null;
	LCAgentComInfoSet mLCAgentComInfoSet= null;
	LCAgentSaleSet mLCAgentSaleSet= null;
	LCGrpAppntSet mLCGrpAppntSet= null;
	LCGrpContactInfoSet mLCGrpContactInfoSet= null;
	LCCustomerIDSet mLCCustomerIDSet= null;
	LCGrpLinkSet mLCGrpLinkSet= null;
	LCCustomerAccountSet mLCCustomerAccountSet= null;
	LCGrpEngineeringSet mLCGrpEngineeringSet= null;
	LCGrpEnginDetailSet mLCGrpEnginDetailSet= null;
	LCGrpPolSet mLCGrpPolSet = null;
	
	LCGrpContSchema mLCGrpContSchema = null;
	LCAgentToContSchema mLCAgentToContSchema = null;
	LCAchvComInfoSchema mLCAchvComInfoSchema = null;
	LCAgentComInfoSchema mLCAgentComInfoSchema = null;
	LCAgentSaleSchema mLCAgentSaleSchema = null;
	LCGrpAppntSchema mLCGrpAppntSchema = null;
	LCGrpContactInfoSchema mLCGrpContactInfoSchema = null;
	LCCustomerIDSchema mLCCustomerIDSchema = null;
	LCGrpLinkSchema mLCGrpLinkSchema = null;
	LCCustomerAccountSchema mLCCustomerAccountSchema = null;
	LCGrpEngineeringSchema mLCGrpEngineeringSchema = null;
	LCGrpEnginDetailSchema mLCGrpEnginDetailSchema = null;
	LCGrpPolSchema mLCGrpPolSchema = null;
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			
			String tMissionID = request.getParameter("MissionID");	//任务ID
			String tSubMissionID = request.getParameter("SubMissionID");	//子任务ID
			String tActivityID = request.getParameter("ActivityID");	//活动节点ID
			String tGrpPropNo = request.getParameter("GrpPropNo");	//投保单号
			String tContPlanType = request.getParameter("ContPlanType");	//保单类型
			
			VData tVData = new VData();
			tVData.add(tGI);
			TransferData vTransferData = new TransferData();
			mLCGrpContSet = new LCGrpContSet();
			mLCAgentToContSet = new LCAgentToContSet();
			mLCAchvComInfoSet = new LCAchvComInfoSet();
			mLCAgentComInfoSet = new LCAgentComInfoSet();
			mLCAgentSaleSet = new LCAgentSaleSet();
			mLCGrpAppntSet = new LCGrpAppntSet();
			mLCGrpContactInfoSet = new LCGrpContactInfoSet();
			mLCCustomerIDSet = new LCCustomerIDSet();
			mLCGrpLinkSet = new LCGrpLinkSet();
			mLCCustomerAccountSet = new LCCustomerAccountSet();
			mLCGrpEngineeringSet = new LCGrpEngineeringSet();
			mLCGrpEnginDetailSet = new LCGrpEnginDetailSet();
			
			if("INSERT".equals(tOperate)){
				//基本投保信息
				String mManageCom = request.getParameter("ManageCom");
				String mGrpPropNo = request.getParameter("GrpPropNo");
				String mSaleDepart = request.getParameter("SaleDepart");
				String mChnlType = request.getParameter("ChnlType");
				String mSaleChnl = request.getParameter("SaleChnl");
				//String mProjectType = request.getParameter("ProjectType");
				String mPolicyAppDate = request.getParameter("PolicyAppDate");
				String mValDateType = request.getParameter("ValDateType");
				String mValDate = request.getParameter("ValDate");
				String mRenewFlag = request.getParameter("RenewFlag");
				String mPayintvType = request.getParameter("PayintvType");
				String mPricingMode = request.getParameter("PricingMode");
				//保险期间
				String mInsuPeriod = request.getParameter("InsuPeriod");
				String mInsuPeriodFlag = request.getParameter("InsuPeriodFlag");
				String mGrpSpec = request.getParameter("GrpSpec");
				String mCoinsurance = request.getParameter("Coinsurance");
				//String mSegment1 = request.getParameter("Segment1");//乐容号
				
				//---------------------------------------------------------------------------------------------------------------------
				mLCGrpContSchema = new LCGrpContSchema();
				mLCGrpContSchema.setProposalGrpContNo(mGrpPropNo);
				mLCGrpContSchema.setGrpContNo(mGrpPropNo);
				mLCGrpContSchema.setManageCom(mManageCom);
				mLCGrpContSchema.setPrtNo(mGrpPropNo);
				mLCGrpContSchema.setSaleDepart(mSaleDepart);
				mLCGrpContSchema.setChnlType(mChnlType);
				mLCGrpContSchema.setSaleChnl(mSaleChnl);
				//mLCGrpContSchema.setProjectType(mProjectType);
				mLCGrpContSchema.setPolApplyDate(mPolicyAppDate);
				mLCGrpContSchema.setValDateType(mValDateType);
				mLCGrpContSchema.setCValiDate(mValDate);
				mLCGrpContSchema.setRenewFlag(mRenewFlag);
				mLCGrpContSchema.setPayIntv(mPayintvType);
				mLCGrpContSchema.setInsuYear(mInsuPeriod);
				mLCGrpContSchema.setInsuYearFlag(mInsuPeriodFlag);
				mLCGrpContSchema.setGrpSpec(mGrpSpec);
				mLCGrpContSchema.setCoinsurance(mCoinsurance);
				//mLCGrpContSchema.setSegment1(mSegment1);//乐容号借用
				//保存团体险种表信息
				mLCGrpPolSchema = new LCGrpPolSchema();
				mLCGrpPolSchema.setGrpProposalNo(mGrpPropNo);
				mLCGrpPolSchema.setGrpContNo(mGrpPropNo);
				mLCGrpPolSchema.setManageCom(mManageCom);
				mLCGrpPolSchema.setPricingMode(mPricingMode);
				mLCGrpPolSchema.setGrpPolNo(mGrpPropNo);
				mLCGrpPolSchema.setValDateType(mValDateType);
				mLCGrpPolSchema.setCValiDate(mValDate);
				mLCGrpPolSchema.setRenewFlag(mRenewFlag);
				mLCGrpPolSchema.setPayIntv(mPayintvType);
				 
				//-------------------------------客户经理信息--------------------------------------------------------------
			
				String tAgentDetailArr[] = request.getParameterValues("AgentDetailGridNo");
				if (tAgentDetailArr!=null && tAgentDetailArr.length>0) {
			
					String tAgentCode[] = request.getParameterValues("AgentDetailGrid1");
					String tAgentName[] = request.getParameterValues("AgentDetailGrid2");
					String tAgentManageCom[] = request.getParameterValues("AgentDetailGrid3");
					String tCommBusiRate[] = request.getParameterValues("AgentDetailGrid4");
					for (int i=0; i<tAgentDetailArr.length; i++) {
					
						if (tAgentCode[i]!=null && !"".equals(tAgentCode[i])) {
						
							mLCAgentToContSchema = new LCAgentToContSchema();
							mLCAgentToContSchema.setPolicyNo(mGrpPropNo);
							mLCAgentToContSchema.setAgentCode(tAgentCode[i]);
							mLCAgentToContSchema.setAgentName(tAgentName[i]);
							mLCAgentToContSchema.setAgentManageCom(tAgentManageCom[i]);
							mLCAgentToContSchema.setCommBusiRate(tCommBusiRate[i]);
							mLCAgentToContSet.add(mLCAgentToContSchema);
						}
					}
				}
				//-------------------------------业绩归属地信息--------------------------------------------------------------
		 		String tBusinessArea = request.getParameter("BusinessArea");
				if(tBusinessArea!=null&&"on".equals(tBusinessArea)){
				 	String tBusinessAreaArr[] = request.getParameterValues("BusinessAreaGridNo");
					if (tBusinessAreaArr!=null && tBusinessAreaArr.length>0) {
						String tAchvMngCom[] = request.getParameterValues("BusinessAreaGrid1");
						String tAchvMngComName[] = request.getParameterValues("BusinessAreaGrid2");
						String tAttributionRate[] = request.getParameterValues("BusinessAreaGrid3");
						String tAgentCode[] = request.getParameterValues("BusinessAreaGrid4");
						String tAgentName[] = request.getParameterValues("BusinessAreaGrid5");
						String tCommBusiRate[] = request.getParameterValues("BusinessAreaGrid6");
						for (int i=0; i<tBusinessAreaArr.length; i++) {
						
							if (tAchvMngCom[i]!=null && !"".equals(tAchvMngCom[i])) {
							
								mLCAchvComInfoSchema = new LCAchvComInfoSchema();
								mLCAchvComInfoSchema.setPolicyNo(mGrpPropNo);
								mLCAchvComInfoSchema.setAchvMngCom(tAchvMngCom[i]);
								mLCAchvComInfoSchema.setAchvMngComName(tAchvMngComName[i]);
								mLCAchvComInfoSchema.setAttributionRate(tAttributionRate[i]);
								mLCAchvComInfoSchema.setAgentCode(tAgentCode[i]);
								mLCAchvComInfoSchema.setAgentName(tAgentName[i]);
								mLCAchvComInfoSchema.setCommBusiRate(tCommBusiRate[i]);
								mLCAchvComInfoSet.add(mLCAchvComInfoSchema);
							}
						}
					}
				}
				//-------------------------------中介机构--------------------------------------------------------------
		 		String tAgentComa = request.getParameter("AgentCom");
				if(tAgentComa!=null&&"on".equals(tAgentComa)){
			 		String tAgentComArr[] = request.getParameterValues("AgentComGridNo");
					if (tAgentComArr!=null && tAgentComArr.length>0) {
				
						String tAgentCom[] = request.getParameterValues("AgentComGrid1");
						String tAgentComName[] = request.getParameterValues("AgentComGrid2");
						//String tAgentBranchesCode[] = request.getParameterValues("AgentComGrid3");
						//String tAgentBranchesName[] = request.getParameterValues("AgentComGrid4");
						String tAgentCode[] = request.getParameterValues("AgentComGrid3");
						String tAgentName[] = request.getParameterValues("AgentComGrid4");
						String tAgentGroup[] = request.getParameterValues("AgentComGrid5");
						//String tCommBusiRate[] = request.getParameterValues("AgentComGrid8");
						for (int i=0; i<tAgentComArr.length; i++) {
					
							if (tAgentCom[i]!=null && !"".equals(tAgentCom[i])) {
							
								mLCAgentComInfoSchema = new LCAgentComInfoSchema();
								mLCAgentComInfoSchema.setPolicyNo(mGrpPropNo);
								mLCAgentComInfoSchema.setAgentCom(tAgentCom[i]);
								mLCAgentComInfoSchema.setAgentComName(tAgentComName[i]);
								//mLCAgentComInfoSchema.setAgentBranchesCode(tAgentBranchesCode[i]);
								//mLCAgentComInfoSchema.setAgentBranchesName(tAgentBranchesName[i]);
								mLCAgentComInfoSchema.setAgentCode(tAgentCode[i]);
								mLCAgentComInfoSchema.setAgentName(tAgentName[i]);
								mLCAgentComInfoSchema.setAgentGroup(tAgentGroup[i]);
								//mLCAgentComInfoSchema.setCommBusiRate(tCommBusiRate[i]);
								mLCAgentComInfoSet.add(mLCAgentComInfoSchema);
							}
						}
					}
				}
				
				//投保人资料信息
				String mAppntNo = request.getParameter("AppntNo");
				String mGrpName = request.getParameter("GrpName");
				String mSumNumPeople = request.getParameter("SumNumPeople");
				String mMainBusiness = request.getParameter("MainBusiness");
				String mGrpNature = request.getParameter("GrpNature");
				String mBusiCategory = request.getParameter("BusiCategory");
				String mSocialInsuCode = request.getParameter("SocialInsuCode");
				String mPhone = request.getParameter("Phone1");
				String mFax = request.getParameter("Fax");
				String mCorporation = request.getParameter("Corporation");
				String mCorIDType = request.getParameter("CorIDType");
				String mCorID = request.getParameter("CorID");
				String mCorIDExpiryDate = request.getParameter("CorIDExpiryDate");
				//---------------------------------------------------------------------------------------------------------------------
				mLCGrpAppntSchema = new LCGrpAppntSchema();
				mLCGrpAppntSchema.setGrpContNo(mGrpPropNo);
				mLCGrpAppntSchema.setCustomerNo(mAppntNo);
				mLCGrpAppntSchema.setName(mGrpName);
				mLCGrpAppntSchema.setSumNumPeople(mSumNumPeople);
				mLCGrpAppntSchema.setMainBusiness(mMainBusiness);
				mLCGrpAppntSchema.setGrpNature(mGrpNature);
				mLCGrpAppntSchema.setBusiCategory(mBusiCategory);
				mLCGrpAppntSchema.setSocialInsuCode(mSocialInsuCode);
				mLCGrpAppntSchema.setPhone(mPhone);
				mLCGrpAppntSchema.setFax(mFax);
				mLCGrpAppntSchema.setState("");
				mLCGrpAppntSchema.setCorporation(mCorporation);
				mLCGrpAppntSchema.setCorIDType(mCorIDType);
				mLCGrpAppntSchema.setCorID(mCorID);
				mLCGrpAppntSchema.setCorIDExpiryDate(mCorIDExpiryDate);
				//省市县信息
				String mProvinceCode = request.getParameter("ProvinceCode");
				String mCityCode = request.getParameter("CityCode");
				String mCountyCode = request.getParameter("CountyCode");
				String mDetailAddress = request.getParameter("DetailAddress");
				String mZipCode = request.getParameter("ZipCode");
				//---------------------------------------------------------------------------------------------------------------------
				mLCGrpContactInfoSchema = new LCGrpContactInfoSchema();
				mLCGrpContactInfoSchema.setGrpContNo(mGrpPropNo);
				mLCGrpContactInfoSchema.setGrpPropNo(mGrpPropNo);
				mLCGrpContactInfoSchema.setCustomerNo(mAppntNo);
				mLCGrpContactInfoSchema.setProvince(mProvinceCode);
				mLCGrpContactInfoSchema.setCity(mCityCode);
				mLCGrpContactInfoSchema.setCounty(mCountyCode);
				mLCGrpContactInfoSchema.setAddress(mDetailAddress);
				mLCGrpContactInfoSchema.setZipCode(mZipCode);
				//-------------------------------单位证件信息--------------------------------------------------------------
				String mGrpIDType = request.getParameter("GrpIDType");
				String mGrpID = request.getParameter("GrpID");
				String mGrpIDExpiryDate = request.getParameter("GrpIDExpiryDate");
				vTransferData.setNameAndValue("GrpIDType",mGrpIDType);
				vTransferData.setNameAndValue("GrpID",mGrpID);
				vTransferData.setNameAndValue("GrpName",mGrpName);
				
				mLCCustomerIDSchema = new LCCustomerIDSchema();
				mLCCustomerIDSchema.setPolicyNo(mGrpPropNo);
				mLCCustomerIDSchema.setCustomerNo(mAppntNo);
				mLCCustomerIDSchema.setIDFlag("00");
				mLCCustomerIDSchema.setState("1");
				mLCCustomerIDSchema.setIDType(mGrpIDType);
				mLCCustomerIDSchema.setIDNo(mGrpID);
				mLCCustomerIDSchema.setStartDate("");
				mLCCustomerIDSchema.setEndDate(mGrpIDExpiryDate);
				mLCCustomerIDSet.add(mLCCustomerIDSchema);
				
				String tIDInfo = request.getParameter("IDInfo");
				if(tIDInfo!=null&&"on".equals(tIDInfo)){
					String tIDInfoArr[] = request.getParameterValues("IDInfoGridNo");
					if (tIDInfoArr!=null && tIDInfoArr.length>0) {
					
						String tIDType[] = request.getParameterValues("IDInfoGrid1");
						String tIDNo[] = request.getParameterValues("IDInfoGrid3");
						String tStartDate[] = request.getParameterValues("IDInfoGrid4");
						String tEndDate[] = request.getParameterValues("IDInfoGrid5");
						for (int i=0; i<tIDInfoArr.length; i++) {
						
							if (tIDType[i]!=null && !"".equals(tIDType[i])) {
							
								mLCCustomerIDSchema = new LCCustomerIDSchema();
								mLCCustomerIDSchema.setPolicyNo(mGrpPropNo);
								mLCCustomerIDSchema.setCustomerNo(mAppntNo);
								mLCCustomerIDSchema.setIDFlag("01");
								mLCCustomerIDSchema.setState("1");
								mLCCustomerIDSchema.setIDType(tIDType[i]);
								mLCCustomerIDSchema.setIDNo(tIDNo[i]);
								mLCCustomerIDSchema.setStartDate(tStartDate[i]);
								mLCCustomerIDSchema.setEndDate(tEndDate[i]);
								mLCCustomerIDSet.add(mLCCustomerIDSchema);
							}
						}
					}
				}
				//多联系人信息
				String nLinkMan = request.getParameter("LinkMan");
				String nPhone = request.getParameter("Phone2");
				String nEMail = request.getParameter("EMail");
				String nMobilePhone = request.getParameter("MobilePhone");
				String nDepartment = request.getParameter("Department");
				String nIDType = request.getParameter("IDType");
				String nIDNo = request.getParameter("IDNo");
				String nIDEndDate = request.getParameter("IDEndDate");
				//---------------------------------------mLCGrpLinkSet------------------------------------------------------------------------------
				mLCGrpLinkSchema = new LCGrpLinkSchema();
				mLCGrpLinkSchema.setGrpPropNo(mGrpPropNo);
				mLCGrpLinkSchema.setGrpContNo(mGrpPropNo);
				mLCGrpLinkSchema.setCustomerNo(mAppntNo);
				mLCGrpLinkSchema.setContactNo("0");
				mLCGrpLinkSchema.setLinkManFlag("00");
				mLCGrpLinkSchema.setLinkMan(nLinkMan);
				mLCGrpLinkSchema.setPhone(nPhone);
				mLCGrpLinkSchema.setEMail(nEMail);
				mLCGrpLinkSchema.setMobilePhone(nMobilePhone);
				mLCGrpLinkSchema.setDepartment(nDepartment);
				mLCGrpLinkSchema.setIDType(nIDType);
				mLCGrpLinkSchema.setIDNo(nIDNo);
				mLCGrpLinkSchema.setIDEndDate(nIDEndDate);
				mLCGrpLinkSet.add(mLCGrpLinkSchema);
				//勾选联系人信息
				String tTooContect = request.getParameter("TooContect");
				if(tTooContect!=null&&"on".equals(tTooContect)){
					String tTooContectArr[] = request.getParameterValues("TooContectGridNo");
					if (tTooContectArr!=null && tTooContectArr.length>0) {
				
						String tLinkMan[] = request.getParameterValues("TooContectGrid1");
						String tPhone[] = request.getParameterValues("TooContectGrid2");
						String tEMail[] = request.getParameterValues("TooContectGrid3");
						String tMobilePhone[] = request.getParameterValues("TooContectGrid4");
						String tDepartment[] = request.getParameterValues("TooContectGrid5");
						String tIDType[] = request.getParameterValues("TooContectGrid6");
						String tIDNo[] = request.getParameterValues("TooContectGrid7");
						String tIDEndDate[] = request.getParameterValues("TooContectGrid8");
						for (int i=0; i<tTooContectArr.length; i++) {
					
							if (tLinkMan[i]!=null && !"".equals(tLinkMan[i])) {
						
								mLCGrpLinkSchema = new LCGrpLinkSchema();
								mLCGrpLinkSchema.setGrpPropNo(mGrpPropNo);
								mLCGrpLinkSchema.setGrpContNo(mGrpPropNo);
								mLCGrpLinkSchema.setCustomerNo(mAppntNo);
								mLCGrpLinkSchema.setContactNo(i+1);
								mLCGrpLinkSchema.setLinkManFlag("01");
								mLCGrpLinkSchema.setLinkMan(tLinkMan[i]);
								mLCGrpLinkSchema.setPhone(tPhone[i]);
								mLCGrpLinkSchema.setEMail(tEMail[i]);
								mLCGrpLinkSchema.setMobilePhone(tMobilePhone[i]);
								mLCGrpLinkSchema.setDepartment(tDepartment[i]);
								mLCGrpLinkSchema.setIDType(tIDType[i]);
								mLCGrpLinkSchema.setIDNo(tIDNo[i]);
								mLCGrpLinkSchema.setIDEndDate(tIDEndDate[i]);
								mLCGrpLinkSet.add(mLCGrpLinkSchema);
							}
						}
					}
				}
				//银行信息
				String mBankCode = request.getParameter("BankCode");
				String mProvince = request.getParameter("BankProvince");
				String mCity = request.getParameter("BankCity");
				vTransferData.setNameAndValue("BankCode",mBankCode);
				vTransferData.setNameAndValue("Province",mProvince);
				vTransferData.setNameAndValue("City",mCity);
				String mAccName = request.getParameter("AccName");
				String mBankAccNo = request.getParameter("BankAccNo");
				String mPayType = request.getParameter("PayType");
				//-------------------------------mLCCustomerAccountSet--------------------------------------------------------------------------------------

				mLCCustomerAccountSchema = new LCCustomerAccountSchema();
				mLCCustomerAccountSchema.setPolicyNo(mGrpPropNo);
				mLCCustomerAccountSchema.setCustomerType("00");
				mLCCustomerAccountSchema.setAccKind("1");
				mLCCustomerAccountSchema.setState("1");
				mLCCustomerAccountSchema.setCustomerNo(mAppntNo);
				mLCCustomerAccountSchema.setAccName(mAccName);
				mLCCustomerAccountSchema.setBankAccNo(mBankAccNo);
				mLCCustomerAccountSchema.setPayType(mPayType);
				//工程信息
				String mEnginName = request.getParameter("EnginName");
				String mContractorName = request.getParameter("ContractorName");
				String mContractorType = request.getParameter("ContractorType");
				String mEnginPlace = request.getParameter("EnginPlace");
				String mDetailDes = request.getParameter("DetailDes");
				String mEnginStartDate = request.getParameter("EnginStartDate");
				String mEnginEndDate = request.getParameter("EnginEndDate");
				String mEnginCost = request.getParameter("EnginCost");
				String mEnginArea = request.getParameter("EnginArea");
				String mEnginType = request.getParameter("EnginType");
				
				String isChecked = request.getParameter("EnginCondition");
				String isChecked1 = request.getParameter("SafeProve");
				
				String mPremCalMode = request.getParameter("PremCalMode");
				String mPremFeeRate = request.getParameter("PremFeeRate");
				String mFirstPrem = request.getParameter("FirstPrem");
				String mContractorPeoples = request.getParameter("ContractorPeoples");
				
				//---------------------------------------------------------------------------------------------------------------------
				mLCGrpEngineeringSchema = new LCGrpEngineeringSchema();
				mLCGrpEngineeringSchema.setGrpPropNo(mGrpPropNo);
				mLCGrpEngineeringSchema.setGrpContNo(mGrpPropNo);
				mLCGrpEngineeringSchema.setEnginName(mEnginName);
				mLCGrpEngineeringSchema.setContractorName(mContractorName);
				mLCGrpEngineeringSchema.setContractorType(mContractorType);
				mLCGrpEngineeringSchema.setEnginPlace(mEnginPlace);
				mLCGrpEngineeringSchema.setDetailDes(mDetailDes);
				mLCGrpEngineeringSchema.setEnginStartDate(mEnginStartDate);
				mLCGrpEngineeringSchema.setEnginEndDate(mEnginEndDate);
				mLCGrpEngineeringSchema.setEnginCost(mEnginCost);
				mLCGrpEngineeringSchema.setEnginArea(mEnginArea);
				mLCGrpEngineeringSchema.setEnginType(mEnginType);
				if(isChecked!=null && "on".equals(isChecked)){
					mLCGrpEngineeringSchema.setEnginCondition("1");
				}
				if(isChecked1!=null && "on".equals(isChecked1)){
					mLCGrpEngineeringSchema.setSafeProve("1");
				}
				mLCGrpEngineeringSchema.setPremCalMode(mPremCalMode);
				mLCGrpEngineeringSchema.setPremFeeRate(mPremFeeRate);
				mLCGrpEngineeringSchema.setFirstPrem(mFirstPrem);
				mLCGrpEngineeringSchema.setContractorPeoples(mContractorPeoples);
				//工程明细

				//----------------------------工程明细-----------------------------------
				String tSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='engindetail'";
				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(tSql);
		
				if (tSSRS==null || tSSRS.getMaxRow()==0) {
					tContent = "获取基础信息失败！";
					tFlagStr = "Fail";
				} else {
			
					for (int i=1; i<=tSSRS.getMaxRow(); i++) {
				
						String tElementCode = tSSRS.GetText(i, 1)+tSSRS.GetText(i, 2);
						String tElementValue = request.getParameter(tElementCode +"Value");
						
						String isCheck = request.getParameter(tElementCode);
						
						if (isCheck!=null && "on".equals(isCheck)) {
							
							LCGrpEnginDetailSchema tLCGrpEnginDetailSchema = new LCGrpEnginDetailSchema();
							tLCGrpEnginDetailSchema.setGrpPropNo(mGrpPropNo);
							tLCGrpEnginDetailSchema.setGrpContNo(mGrpPropNo);
							tLCGrpEnginDetailSchema.setEnginCode(tSSRS.GetText(i, 2));
							tLCGrpEnginDetailSchema.setOtherDesc(tElementValue);	
							mLCGrpEnginDetailSet.add(tLCGrpEnginDetailSchema);
						}
					}
				}
			}
			
			//----------------------------合规信息-----------------------------------
			/*
			String rSql = "select code from ldcode where codetype='relabuss'";
			SSRS rSSRS = new SSRS();
			ExeSQL rExeSQL = new ExeSQL();
			rSSRS = rExeSQL.execSQL(rSql);
			LCRelaBussSchema mLCRelaBussSchema = new LCRelaBussSchema();
			mLCRelaBussSchema.setPara13(request.getParameter("Para13"));
			if (rSSRS!=null && rSSRS.getMaxRow()>0) {
		
				for (int i=1; i<=rSSRS.getMaxRow(); i++) {
			
					String tElementCode = rSSRS.GetText(i, 1);
					String tElementValue = request.getParameter("Check"+tElementCode);
						
					mLCRelaBussSchema.setV(tElementCode, tElementValue);
				}
			}
			*/
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpPropNo",tGrpPropNo);
			vTransferData.setNameAndValue("ContPlanType",tContPlanType);
			
			tVData.add(mLCGrpContSchema);
			tVData.add(mLCAgentToContSet);
			tVData.add(mLCAchvComInfoSet);
			tVData.add(mLCAgentComInfoSet);
			tVData.add(mLCAgentSaleSet);
			tVData.add(mLCGrpAppntSchema);
			tVData.add(mLCGrpContactInfoSchema);
			tVData.add(mLCCustomerIDSet);
			tVData.add(mLCGrpLinkSchema);
			tVData.add(mLCGrpLinkSet);
			tVData.add(mLCCustomerAccountSchema);
			tVData.add(vTransferData);
			tVData.add(mLCGrpEngineeringSchema);
			tVData.add(mLCGrpEnginDetailSet);
			tVData.add(mLCGrpPolSchema);
			//tVData.add(mLCRelaBussSchema);
			
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCContPolUI")) {
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
