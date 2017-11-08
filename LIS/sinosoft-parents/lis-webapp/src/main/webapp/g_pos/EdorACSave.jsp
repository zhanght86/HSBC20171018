<%
/***************************************************************
	* <p>ProName：EdorACSave.jsp</p>
	* <p>Title:  投保人资料变更</p>
	* <p>Description:投保人资料变更</p>
	* <p>Copyright：Copyright (c) 2012</p>
	* <p>Company：Sinosoft</p>
	* @author   : caiyc
	* @version  : 8.0
	* @date     : 2014-06-13
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LPGrpAppntSchema"%>
<%@page import="com.sinosoft.lis.schema.LPGrpLinkSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPGrpLinkSet"%>
<%@page import="com.sinosoft.lis.schema.LPCustomerIDSchema"%>
<%@page import="com.sinosoft.lis.vschema.LPCustomerIDSet"%>
<%@page import="com.sinosoft.lis.schema.LPCustomerAccountSchema"%>
<%@page import="com.sinosoft.lis.schema.LPGrpContactInfoSchema"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LPAppntSchema"%>
<%

	String tFlagStr = "Fail";
	String tContent = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	TransferData vTransferData = new TransferData();
	
	LPGrpAppntSchema mLPGrpAppntSchema=null;
	LPGrpLinkSet tLPGrpLinkSet = new LPGrpLinkSet();
	LPGrpLinkSchema mLPGrpLinkSchema = null;
	LPCustomerAccountSchema mLPCustomerAccountSchema = null;
	LPCustomerIDSet tLPCustomerIDSet = new LPCustomerIDSet();
	LPGrpContactInfoSchema mLPGrpContactInfoSchema = null;
	LPCustomerIDSchema tLPCustomerIDSchema = new LPCustomerIDSchema();
	LPAppntSchema tLPAppntSchema = new LPAppntSchema();
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			String tOperate = request.getParameter("Operate");
			String tMissionID=  request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID"); 
			String tActivityID = request.getParameter("ActivityID");
			String tGrpContNo = request.getParameter("GrpContNo");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorType = request.getParameter("EdorType");
			String tEdorNo = request.getParameter("EdorNo");

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
			
			//省市县信息
			String mProvinceCode = request.getParameter("ProvinceCode");
			String mCityCode = request.getParameter("CityCode");
			String mCountyCode = request.getParameter("CountyCode");
			String mDetailAddress = request.getParameter("DetailAddress");
			String mZipCode = request.getParameter("ZipCode");
			
			//多联系人信息
			String nLinkMan = request.getParameter("LinkMan");
			String nPhone = request.getParameter("Phone2");
			String nEMail = request.getParameter("EMail");
			String nMobilePhone = request.getParameter("MobilePhone");
			String nDepartment = request.getParameter("Department");
			String nIDType = request.getParameter("IDType");
			String nIDNo = request.getParameter("IDNo");
			String nIDEndDate = request.getParameter("IDEndDate");
			
			//银行信息参数
			String mBankCode = request.getParameter("HeadBankCode");
			String mProvince = request.getParameter("BankProvince");
			String mCity = request.getParameter("BankCity");
			String mAccName = request.getParameter("AccName");
			String mBankAccNo = request.getParameter("BankAccNo");
			String mPayType = request.getParameter("PayType");

			if("SAVE".equals(tOperate)){
				//投保人基本资料
				mLPGrpAppntSchema = new LPGrpAppntSchema();
				mLPGrpAppntSchema.setGrpContNo(tGrpContNo);
				mLPGrpAppntSchema.setEdorType(tEdorType);
				mLPGrpAppntSchema.setEdorNo(tEdorNo);
				mLPGrpAppntSchema.setCustomerNo(mAppntNo);
				mLPGrpAppntSchema.setName(mGrpName);
				mLPGrpAppntSchema.setSumNumPeople(mSumNumPeople);
				mLPGrpAppntSchema.setMainBusiness(mMainBusiness);
				mLPGrpAppntSchema.setGrpNature(mGrpNature);
				mLPGrpAppntSchema.setBusiCategory(mBusiCategory);
				mLPGrpAppntSchema.setSocialInsuCode(mSocialInsuCode);
				mLPGrpAppntSchema.setPhone(mPhone);
				mLPGrpAppntSchema.setFax(mFax);
				mLPGrpAppntSchema.setState("0");
				mLPGrpAppntSchema.setCorporation(mCorporation);
				mLPGrpAppntSchema.setCorIDType(mCorIDType);
				mLPGrpAppntSchema.setCorID(mCorID);
				mLPGrpAppntSchema.setCorIDExpiryDate(mCorIDExpiryDate);
				
				//-----------------------mLCGrpLinkSet------------------------
				mLPGrpLinkSchema = new LPGrpLinkSchema();
				mLPGrpLinkSchema.setGrpPropNo(tGrpContNo);
				mLPGrpLinkSchema.setGrpContNo(tGrpContNo);
				mLPGrpLinkSchema.setEdorType(tEdorType);
				mLPGrpLinkSchema.setEdorNo(tEdorNo);
				mLPGrpLinkSchema.setCustomerNo(mAppntNo);
				mLPGrpLinkSchema.setContactNo("0");
				mLPGrpLinkSchema.setLinkManFlag("00");
				mLPGrpLinkSchema.setLinkMan(nLinkMan);
				mLPGrpLinkSchema.setPhone(nPhone);
				mLPGrpLinkSchema.setEMail(nEMail);
				mLPGrpLinkSchema.setMobilePhone(nMobilePhone);
				mLPGrpLinkSchema.setDepartment(nDepartment);
				mLPGrpLinkSchema.setIDType(nIDType);
				mLPGrpLinkSchema.setIDNo(nIDNo);
				mLPGrpLinkSchema.setIDEndDate(nIDEndDate);
				tLPGrpLinkSet.add(mLPGrpLinkSchema);
				
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
								mLPGrpLinkSchema = new LPGrpLinkSchema();
								mLPGrpLinkSchema.setGrpPropNo(tGrpContNo);
								mLPGrpLinkSchema.setGrpContNo(tGrpContNo);
								mLPGrpLinkSchema.setEdorNo(tEdorNo);
								mLPGrpLinkSchema.setEdorType(tEdorType);
								mLPGrpLinkSchema.setCustomerNo(mAppntNo);
								mLPGrpLinkSchema.setContactNo(i+1);
								mLPGrpLinkSchema.setLinkManFlag("01");
								mLPGrpLinkSchema.setLinkMan(tLinkMan[i]);
								mLPGrpLinkSchema.setPhone(tPhone[i]);
								mLPGrpLinkSchema.setEMail(tEMail[i]);
								mLPGrpLinkSchema.setMobilePhone(tMobilePhone[i]);
								mLPGrpLinkSchema.setDepartment(tDepartment[i]);
								mLPGrpLinkSchema.setIDType(tIDType[i]);
								mLPGrpLinkSchema.setIDNo(tIDNo[i]);
								mLPGrpLinkSchema.setIDEndDate(tIDEndDate[i]);
								tLPGrpLinkSet.add(mLPGrpLinkSchema);
							}
						}
					}
				}
				
				//银行信息
				mLPCustomerAccountSchema = new LPCustomerAccountSchema();
				mLPCustomerAccountSchema.setPolicyNo(tGrpContNo);
				mLPCustomerAccountSchema.setEdorType(tEdorType);
				mLPCustomerAccountSchema.setEdorNo(tEdorNo);
				mLPCustomerAccountSchema.setCustomerType("00");
				mLPCustomerAccountSchema.setAccKind("1");
				mLPCustomerAccountSchema.setState("1");
				mLPCustomerAccountSchema.setCustomerNo(mAppntNo);
				mLPCustomerAccountSchema.setAccName(mAccName);
				mLPCustomerAccountSchema.setBankAccNo(mBankAccNo);
				mLPCustomerAccountSchema.setPayType(mPayType);
				
				//-------------------------------单位证件信息----------------------
				//单位主证件类型
				String mGrpIDType = request.getParameter("GrpIDType");
				String mGrpID = request.getParameter("GrpID");
				String mGrpIDExpiryDate = request.getParameter("GrpIDExpiryDate");
				vTransferData.setNameAndValue("GrpIDType",mGrpIDType);
				vTransferData.setNameAndValue("GrpID",mGrpID);
				vTransferData.setNameAndValue("GrpName",mGrpName);
				
				tLPCustomerIDSchema = new LPCustomerIDSchema();
				tLPCustomerIDSchema.setPolicyNo(tGrpContNo);
				tLPCustomerIDSchema.setEdorType(tEdorType);
				tLPCustomerIDSchema.setEdorNo(tEdorNo);
				tLPCustomerIDSchema.setCustomerNo(mAppntNo);
				tLPCustomerIDSchema.setIDFlag("00");
				tLPCustomerIDSchema.setState("1");
				tLPCustomerIDSchema.setIDType(mGrpIDType);
				tLPCustomerIDSchema.setIDNo(mGrpID);
				tLPCustomerIDSchema.setStartDate("");
				tLPCustomerIDSchema.setEndDate(mGrpIDExpiryDate);
				tLPCustomerIDSet.add(tLPCustomerIDSchema);
				
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
								LPCustomerIDSchema mLPCustomerIDSchema = new LPCustomerIDSchema();
								mLPCustomerIDSchema.setPolicyNo(tGrpContNo);
								mLPCustomerIDSchema.setEdorType(tEdorType);
								mLPCustomerIDSchema.setEdorNo(tEdorNo);
								mLPCustomerIDSchema.setCustomerNo(mAppntNo);
								mLPCustomerIDSchema.setIDFlag("01");
								mLPCustomerIDSchema.setState("1");
								mLPCustomerIDSchema.setIDType(tIDType[i]);
								mLPCustomerIDSchema.setIDNo(tIDNo[i]);
								mLPCustomerIDSchema.setStartDate(tStartDate[i]);
								mLPCustomerIDSchema.setEndDate(tEndDate[i]);
								tLPCustomerIDSet.add(mLPCustomerIDSchema);
							}
						}
					}
				}
				//省市县信息保存
				mLPGrpContactInfoSchema = new LPGrpContactInfoSchema();
				mLPGrpContactInfoSchema.setGrpContNo(tGrpContNo);
				mLPGrpContactInfoSchema.setGrpPropNo(tGrpContNo);
				mLPGrpContactInfoSchema.setEdorType(tEdorType);
				mLPGrpContactInfoSchema.setEdorNo(tEdorNo);
				mLPGrpContactInfoSchema.setCustomerNo(mAppntNo);
				mLPGrpContactInfoSchema.setProvince(mProvinceCode);
				mLPGrpContactInfoSchema.setCity(mCityCode);
				mLPGrpContactInfoSchema.setCounty(mCountyCode);
				mLPGrpContactInfoSchema.setAddress(mDetailAddress);
				mLPGrpContactInfoSchema.setZipCode(mZipCode);
			} else if("SAVEAPP".equals(tOperate)){
				//投保人资料信息
				
				String mIAppntNo = request.getParameter("IAppntNo");
				String mAppntName = request.getParameter("AppntName");
				String mIDType = request.getParameter("IDType");
				String mIDNo = request.getParameter("IDNo");
				String mInsuredGender = request.getParameter("InsuredGender");
				String mInsuredBirthDay = request.getParameter("InsuredBirthDay");
				String mEndDate = request.getParameter("CorIDExpiryDate");
				
				tLPAppntSchema.setContNo(tGrpContNo);
				tLPAppntSchema.setAppntNo(mIAppntNo);
				tLPAppntSchema.setAppntName(mAppntName);
				tLPAppntSchema.setIDType(mIDType);
				tLPAppntSchema.setIDNo(mIDNo);
				tLPAppntSchema.setAppntSex(mInsuredGender);
				tLPAppntSchema.setAppntBirthday(mInsuredBirthDay);
				tLPAppntSchema.setIDEndDate(mEndDate);
			}
			
			vTransferData.setNameAndValue("MissionID",tMissionID);
			vTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			vTransferData.setNameAndValue("ActivityID",tActivityID);
			vTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			vTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			vTransferData.setNameAndValue("EdorNo",tEdorNo);
			vTransferData.setNameAndValue("EdorType",tEdorType);
			vTransferData.setNameAndValue("HeadBankCode",mBankCode);
			vTransferData.setNameAndValue("Province",mProvince);
			vTransferData.setNameAndValue("City",mCity);
			
			VData tVData = new VData();
			
			tVData.add(tGI);
			tVData.add(vTransferData);
			tVData.add(mLPGrpAppntSchema);
			tVData.add(tLPGrpLinkSet);
			tVData.add(tLPCustomerIDSchema);
			tVData.add(tLPCustomerIDSet);
			tVData.add(mLPGrpContactInfoSchema);
			tVData.add(mLPCustomerAccountSchema);
			tVData.add(tLPAppntSchema);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorACUI")) {
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
