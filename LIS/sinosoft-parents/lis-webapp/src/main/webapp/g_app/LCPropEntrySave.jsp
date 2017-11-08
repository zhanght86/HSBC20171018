<%
/***************************************************************
 * <p>ProName：LCPropEntrySave.jsp</p>
 * <p>Title：投保书信息录入</p>
 * <p>Description：投保书信息录入</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.utility.VData"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%@page import="com.sinosoft.utility.TransferData"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.LCPrintAppntSchema"%>
<%@page import="com.sinosoft.lis.schema.LCPrtCustomerIDSchema"%>
<%@page import="com.sinosoft.lis.schema.LCPrtContactInfoSchema"%>
<%@page import="com.sinosoft.lis.schema.LCPrintDivSchema"%>
<%@page import="com.sinosoft.lis.schema.LCPrintAttaSchema"%>
<%@page import="com.sinosoft.lis.schema.LCPrintAgentSchema"%>
<%@page import="com.sinosoft.lis.vschema.LCPrtCustomerIDSet"%>
<%@page import="com.sinosoft.lis.vschema.LCPrintDivSet"%>
<%@page import="com.sinosoft.lis.vschema.LCPrintAgentSet"%>
<%@page import="com.sinosoft.lis.vschema.LCPrintAttaSet"%>
<%
	
	String tFlagStr = "Fail";
	String tContent = "";
	String tOperate = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	LCPrintAppntSchema mLCPrintAppntSchema = null;
	LCPrtCustomerIDSet mLCPrtCustomerIDSet = null;
	LCPrtCustomerIDSchema mLCPrtCustomerIDSchema = null;
	LCPrtContactInfoSchema mLCPrtContactInfoSchema = null;
	LCPrintDivSet mLCPrintDivSet = null;
	LCPrintDivSchema mLCPrintDivSchema = null;
	LCPrintAgentSet mLCPrintAgentSet = null;
	LCPrintAgentSchema mLCPrintAgentSchema = null;
	LCPrintAttaSet mLCPrintAttaSet = null;
	LCPrintAttaSchema mLCPrintAttaSchema=null;
	TransferData mTransferData = new TransferData();
	
	String tFileName="";
	String tFilePath1="";
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "页面失效,请重新登陆";
	} else {
		
		try {
			//获取操作类型的前台参数
			tOperate = request.getParameter("Operate");
			VData tVData = new VData();
			tVData.add(tGI);
			mLCPrtCustomerIDSet = new LCPrtCustomerIDSet();
			mLCPrintAgentSet = new LCPrintAgentSet();
			mLCPrintDivSet = new LCPrintDivSet();
			String mGrpPropNo = request.getParameter("GrpPropNo");
 
			String mGrpName = request.getParameter("GrpName");
			String mMainBusiness = request.getParameter("MainBusiness");
			String mCorporation = request.getParameter("Corporation");
			String mCorIDType = request.getParameter("CorIDType");
			String mCorID = request.getParameter("CorID");
			String mCorIDExpiryDate = request.getParameter("CorIDExpiryDate");
			String mBusiCategoryCode = request.getParameter("BusiCategoryCode");
			String mFoundDate = request.getParameter("FoundDate");
			String mSocialInsuCode = request.getParameter("SocialInsuCode");
			String mGrpNature = request.getParameter("GrpNature");
			String mGrpIDType = request.getParameter("GrpIDType");//单位证件类型
			String mGrpIDNo = request.getParameter("GrpIDNo");
			String mGrpIDExpiryDate = request.getParameter("GrpIDExpiryDate");
			String mProvince = request.getParameter("Province");
			String mCity = request.getParameter("City");
			String mCounty = request.getParameter("County");
			String mAddress = request.getParameter("Address");
			String mZipCode = request.getParameter("ZipCode");
			String mPhone = request.getParameter("Phone");
			String mFax = request.getParameter("Fax");
			String mEMail = request.getParameter("EMail");
			String mLinkMan = request.getParameter("LinkMan");
			String mLinkPhone = request.getParameter("LinkPhone");
			String mLinkIDType = request.getParameter("LinkIDType");
			String mLinkID = request.getParameter("LinkID");
			String mLinkIDExpiryDate = request.getParameter("LinkIDExpiryDate");
			String mSumNumPeople = request.getParameter("SumNumPeople");
			String mMainContNumber = request.getParameter("MainContNumber");
			String mRelatedContNumber = request.getParameter("RelatedContNumber");
			String mAppManageCom = request.getParameter("AppManageCom");
			String mGrpSpec = request.getParameter("GrpSpec");

			mLCPrintAppntSchema = new LCPrintAppntSchema();
			
			mLCPrintAppntSchema.setGrpPropNo(mGrpPropNo);
			mLCPrintAppntSchema.setGrpContNo(mGrpPropNo);
			mLCPrintAppntSchema.setGrpName(mGrpName);
			mLCPrintAppntSchema.setMainBusiness(mMainBusiness);
			mLCPrintAppntSchema.setCorporation(mCorporation);
			mLCPrintAppntSchema.setCorIDType(mCorIDType);
			mLCPrintAppntSchema.setCorID(mCorID);
			mLCPrintAppntSchema.setCorIDExpiryDate(mCorIDExpiryDate);
			mLCPrintAppntSchema.setBusiCategory(mBusiCategoryCode);
			mLCPrintAppntSchema.setFoundDate(mFoundDate);
			mLCPrintAppntSchema.setSocialInsuCode(mSocialInsuCode);
			mLCPrintAppntSchema.setGrpNature(mGrpNature);
			mLCPrintAppntSchema.setPhone(mPhone);
			mLCPrintAppntSchema.setFax(mFax);
			mLCPrintAppntSchema.setEMail(mEMail);
			mLCPrintAppntSchema.setLinkMan(mLinkMan);
			mLCPrintAppntSchema.setLinkPhone(mLinkPhone);
			mLCPrintAppntSchema.setLinkIDType(mLinkIDType);
			mLCPrintAppntSchema.setLinkID(mLinkID);
			mLCPrintAppntSchema.setLinkIDExpiryDate(mLinkIDExpiryDate);
			mLCPrintAppntSchema.setSumNumPeople(mSumNumPeople);
			mLCPrintAppntSchema.setMainContNumber(mMainContNumber);
			mLCPrintAppntSchema.setRelatedContNumber(mRelatedContNumber);
			mLCPrintAppntSchema.setAppManageCom(mAppManageCom);
			
			//主要单位证件类型
			mLCPrtCustomerIDSchema = new LCPrtCustomerIDSchema();
			mLCPrtCustomerIDSchema.setGrpIDType(mGrpIDType);
			mLCPrtCustomerIDSchema.setGrpIDNo(mGrpIDNo);
			mLCPrtCustomerIDSchema.setEndDate(mGrpIDExpiryDate);
			mLCPrtCustomerIDSchema.setGrpIDFlag("00");
			mLCPrtCustomerIDSchema.setSerialNo("1");
			mLCPrtCustomerIDSchema.setGrpPropNo(mGrpPropNo);
			mLCPrtCustomerIDSchema.setGrpContNo(mGrpPropNo);
			mLCPrtCustomerIDSet.add(mLCPrtCustomerIDSchema);
			
			mTransferData.setNameAndValue("GrpPropNo",mGrpPropNo);
			mTransferData.setNameAndValue("GrpIDType",mGrpIDType);
			mTransferData.setNameAndValue("GrpID",mGrpIDNo);
			mTransferData.setNameAndValue("GrpName",mGrpName);
			mTransferData.setNameAndValue("GrpSpec",mGrpSpec);
			
			//附属单位证件类型		 	
			String tIDInfoCheck = request.getParameter("IDInfoCheck");
			if(tIDInfoCheck!=null&&"on".equals(tIDInfoCheck)){
				String tGridNo[] = request.getParameterValues("IDInfoGridNo");
				String tGrid1 [] = request.getParameterValues("IDInfoGrid1"); //得到第1列的所有值
				String tGrid3 [] = request.getParameterValues("IDInfoGrid3");
				String tGrid4 [] = request.getParameterValues("IDInfoGrid4");
				String tGrid5 [] = request.getParameterValues("IDInfoGrid5");
				if(null!=tGridNo){
					int Count = tGridNo.length; //得到接受到的记录数
					for(int index=0;index<Count;index++){
						mLCPrtCustomerIDSchema = new LCPrtCustomerIDSchema();
						mLCPrtCustomerIDSchema.setGrpPropNo(mGrpPropNo);
						mLCPrtCustomerIDSchema.setGrpContNo(mGrpPropNo);	
						mLCPrtCustomerIDSchema.setSerialNo(index+2+"");
						mLCPrtCustomerIDSchema.setGrpIDFlag("01");
						mLCPrtCustomerIDSchema.setGrpIDType(tGrid1[index]);
						mLCPrtCustomerIDSchema.setGrpIDNo(tGrid3[index]);
						mLCPrtCustomerIDSchema.setStartDate(tGrid4[index]);
						mLCPrtCustomerIDSchema.setEndDate(tGrid5[index]);
						mLCPrtCustomerIDSet.add(mLCPrtCustomerIDSchema);
					}
				}
			}
				//存储地址信息
			mLCPrtContactInfoSchema = new LCPrtContactInfoSchema();
			mLCPrtContactInfoSchema.setGrpPropNo(mGrpPropNo);
			mLCPrtContactInfoSchema.setGrpContNo(mGrpPropNo);
			mLCPrtContactInfoSchema.setContactNo("1");
			mLCPrtContactInfoSchema.setProvince(mProvince);
			mLCPrtContactInfoSchema.setCity(mCity);
			mLCPrtContactInfoSchema.setCounty(mCounty);
			mLCPrtContactInfoSchema.setAddress(mAddress);
			mLCPrtContactInfoSchema.setZipCode(mZipCode);
			
			String tValDateType = request.getParameter("ValDateType");
			String tValDate = request.getParameter("ValDate");
			String tInsuPeriod = request.getParameter("InsuPeriod");
			String tInsuPeriodFlag = request.getParameter("InsuPeriodFlag");
			String tPayIntv = request.getParameter("PayIntv");
			String tPremMode = request.getParameter("PremMode");
			String tPayType = request.getParameter("PayType");
			String tSaleChnl = request.getParameter("SaleChnl");
			String tChnlType = request.getParameter("ChnlType");
			
			mLCPrintAppntSchema.setGrpPropNo(mGrpPropNo);
			mLCPrintAppntSchema.setValDateType(tValDateType);
			mLCPrintAppntSchema.setValDate(tValDate);
			mLCPrintAppntSchema.setInsuPeriod(tInsuPeriod);
			mLCPrintAppntSchema.setInsuPeriodFlag(tInsuPeriodFlag);
			mLCPrintAppntSchema.setPayIntv(tPayIntv);
			mLCPrintAppntSchema.setPremMode(tPremMode);
			mLCPrintAppntSchema.setPayMode(tPayType);
			mLCPrintAppntSchema.setSaleChnl(tSaleChnl);
			mLCPrintAppntSchema.setChnlType(tChnlType);
			
			String tSql = "select a.codetype,a.code from ldcode a where 1=1 and a.codetype='quotplandiv'";
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
					
					String isChecked = request.getParameter(tElementCode);
					
					if (isChecked!=null && "on".equals(isChecked)) {
						
						LCPrintDivSchema tLCPrintDivSchema = new LCPrintDivSchema();
						tLCPrintDivSchema.setGrpPropNo(mGrpPropNo);	
						tLCPrintDivSchema.setGrpContNo(mGrpPropNo);
						tLCPrintDivSchema.setDivType(tSSRS.GetText(i, 2));
						tLCPrintDivSchema.setOtherDesc(tElementValue);

						mLCPrintDivSet.add(tLCPrintDivSchema);
					}
				}
			}

				mLCPrintAgentSchema = new LCPrintAgentSchema();
				String mGridNo[] = request.getParameterValues("ZJGridNo");
				String mGrid1[] = request.getParameterValues("ZJGrid1");
				String mGrid2[] = request.getParameterValues("ZJGrid2");
				if(mGridNo!=null ){
					int  Count = mGridNo.length; //得到接受到的记录数
					for(int index=0;index<Count;index++) {
						if (mGrid1[index]!=null && !"".equals(mGrid1[index])) {
							mLCPrintAgentSchema = new LCPrintAgentSchema();
							mLCPrintAgentSchema.setGrpPropNo(mGrpPropNo);
							mLCPrintAgentSchema.setAgentCom(mGrid1[index]);
							mLCPrintAgentSchema.setAgentComName(mGrid2[index]);
							mLCPrintAgentSet.add(mLCPrintAgentSchema); 
						}
					}
				}
			//打印投保单
			String mAddService = request.getParameter("AddService");
			String mPersonProp = request.getParameter("PersonProp");
			
			mLCPrintAttaSet = new LCPrintAttaSet();
			if(mAddService!=null&&"on".equals(mAddService)){
				mLCPrintAttaSchema = new LCPrintAttaSchema();
				mLCPrintAttaSchema.setGrpPropNo(mGrpPropNo);
				mLCPrintAttaSchema.setAttaType("1");
				mLCPrintAttaSet.add(mLCPrintAttaSchema);
			}
			if(mPersonProp!=null&&"on".equals(mPersonProp)){
				mLCPrintAttaSchema = new LCPrintAttaSchema();
				mLCPrintAttaSchema.setGrpPropNo(mGrpPropNo);
				mLCPrintAttaSchema.setAttaType("2");
				mLCPrintAttaSet.add(mLCPrintAttaSchema);
			}
			
			String tempFilePath = request.getRealPath("");
			String tempImagePath = request.getRealPath("");
			tempFilePath=tempFilePath.replaceAll("\\\\","/");
			tempImagePath=tempImagePath.replaceAll("\\\\","/");
		
			tempImagePath+="/common/images/image004.png";
			mTransferData.setNameAndValue("tempFilePath",tempFilePath);
			mTransferData.setNameAndValue("tempImagePath",tempImagePath);
		
			tVData.add(mLCPrtCustomerIDSet);
			tVData.add(mLCPrintAppntSchema);
			tVData.add(mLCPrtContactInfoSchema);
			tVData.add(mLCPrintAgentSet);
			tVData.add(mLCPrintDivSet);
			tVData.add(mLCPrintAttaSet);
			tVData.add(mGrpPropNo);
			tVData.add(mTransferData);
			
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "LCPrintAppntUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				tFlagStr = "Succ";
				tContent = "处理成功！";
				if("PRINT".equals(tOperate) || "PRINTPREM".equals(tOperate)){
					
					TransferData trd = (TransferData)tBusinessDelegate.getResult().getObjectByObjectName("TransferData", 0);
					tFilePath1 = (String) trd.getValueByName("FilePath");
					tFileName = (String) trd.getValueByName("FileName");
					
				}
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tFilePath1%>","<%=tFileName%>");
</script>
</html>
