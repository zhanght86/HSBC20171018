<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%
/***************************************************************
 * <p>ProName��LCInsuredAddSave.jsp</p>
 * <p>Title����ӱ������嵥</p>
 * <p>Description����ӱ������嵥</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
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
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
		
			//������Ϣ������У�鴦��
			LCContSchema tmLCContSchema = new LCContSchema();
			LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
			LCCustomerContactInfoSchema  tmLCCustomerContactInfoSchema = new LCCustomerContactInfoSchema(); // �������˿ͻ���ϵ��Ϣ
			LCCustomerAccountSchema tmLCCustomerAccountSchema = new LCCustomerAccountSchema();
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			LDPersonContactInfoSchema tLDPersonContactInfoSchema = new LDPersonContactInfoSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			ExeSQL mExeSQL = new ExeSQL();
			TransferData tTransferData = new TransferData();
			
			// �����˻�����Ϣ
			String tOperate = request.getParameter("Operate");
			String tInsuredSeqNo = request.getParameter("InsuredSeqNo");
			String tContNo= request.getParameter("mContNo");
			
			String tGrpPrtNo = request.getParameter("GrpPropNo"); // Ͷ�����
			String tInsuredType = request.getParameter("InsuredType"); // ������������
			String tContGradeCode = request.getParameter("ContPlanCode"); // ��������
			String tsysPlanCode = request.getParameter("sysPlanCode"); // ϵͳ��������
			String tRelatomain = request.getParameter("relatomain"); // ���������˹�ϵ
			String tmainCustNo = request.getParameter("mainCustNo"); // ���������˿ͻ���
			String tJGContPlanCode =  request.getParameter("JGContPlanCode"); // ���������˷�������
			String tJGsysContPlanCode = request.getParameter("JGsysContPlanCode"); // ����������ϵͳ��������
			
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
				tInsuredName = "������"; // ����������
				tIDType = "8"; // ֤������
				tIDNo = "1900-01-01"; // ֤�����루��¼���֤��������ĸת��Ϊ��д��
				tSex = "2";
				tBirthday = "1900-01-01";
				tInsuredPeoples = request.getParameter("InsuredPeoples");
			}else {
				tInsuredName = request.getParameter("InsuredName"); // ����������
				tIDType = request.getParameter("IDType"); // ֤������
				tIDNo = request.getParameter("IDNo").toUpperCase(); // ֤�����루��¼���֤��������ĸת��Ϊ��д��
				tSex = request.getParameter("InsuredGender");
				tBirthday = request.getParameter("InsuredBirthDay");
				tInsuredPeoples = "1";
			}
			
			String tInsuredAppAge = request.getParameter("InsuredAppAge");
			String tOccupationCode = request.getParameter("OccupationCode");// ְҵ����
			String tOccupationType = request.getParameter("OccupationType");//ְҵ���
			String tZipCode = request.getParameter("ZipCode");
			String tEMail = request.getParameter("EMail");
			String tMicroNo = request.getParameter("MicroNo"); // ΢�ź�
			String tPhone = request.getParameter("Phone");
			String tMobile = request.getParameter("Mobile"); 
			String tProvinceCode = request.getParameter("ProvinceCode"); // ʡ����
			String tCityCode = request.getParameter("CityCode"); // �б���
			String tCountyCode = request.getParameter("CountyCode"); // ������
			String tDetailAddress = request.getParameter("DetailAddress"); // ��ϸ��ַ
			
			//������Ϣ		
			String tServerArea = request.getParameter("ServerArea"); //��������
			String tSubstandard = request.getParameter("Substandard"); //�Ƿ�α�׼��
			String tSocial = request.getParameter("Social");  //�籣���
			String tPosition = request.getParameter("Position");//ְ��
			String tJoinCompDate = request.getParameter("JoinCompDate"); //��˾ʱ��
			String tSeniority = request.getParameter("Seniority"); //����
			String tSalary = request.getParameter("Salary"); //��н
			String tGetYear = request.getParameter("GetYear"); //��ȡ����
			String tGetStartType = request.getParameter("GetStartType"); //�������ڼ�������

			// ������Ϣ
			String tBankCode = request.getParameter("HeadBankCode"); //��������
			String tBankCodeName = request.getParameter("BankCodeName");
			String tAccName = request.getParameter("AccName"); //������
			String tBankAccNo = request.getParameter("BankAccNo"); //�˺�
			String tProvince = request.getParameter("BankProvince"); //��������ʡ
			String tCity = request.getParameter("BankCity"); //������������

			// ������Ϣ
			String tWorkIDNo = request.getParameter("WorkIDNo"); //Ա����
			String tISLongValid = request.getParameter("ISLongValid"); //֤���Ƿ���
			String tLiscenceValidDate = request.getParameter("LiscenceValidDate"); //֤����Ч��
			String tComponyName = request.getParameter("ComponyName"); //���ڷֹ�˾
			String tDeptCode = request.getParameter("DeptCode"); //���ڲ���
			String tInsureCode = request.getParameter("InsureCode"); //�������˱���
			//String tSubCustomerNo = request.getParameter("SubCustomerNo"); //�����ͻ�Ⱥ
			String tWorkAddress = request.getParameter("WorkAddress"); //������
			String tSocialAddress = request.getParameter("SocialAddress"); //�籣��
			
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
			tmLCInsuredSchema.setMainCustomerNo(tmainCustNo); // �������˿ͻ���
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
				String tRiskCode[] = request.getParameterValues("QueryScanGrid3");//���ֱ���
				String tDutyCode[] = request.getParameterValues("QueryScanGrid5");//���α���
				String tAmntType[] = request.getParameterValues("QueryScanGrid7");// ��������
				String tAmnt[] = request.getParameterValues("QueryScanGrid9"); // ����
				
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
					tContent = "��������Ϣ����ɹ���";
				} else if("UPDATE".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "��������Ϣ�޸ĳɹ���";
				} else if("DELETE".equals(tOperate)) {
					
					tContent = "��������Ϣɾ���ɹ���";
				}	else if("POLSave".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "���������ֱ����޸ĳɹ���";
				}	else if("PayPlanSave".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "�����˽ɷ�����Ϣ�޸ĳɹ���";
				}	else if("InvestSave".equals(tOperate)) {
					tContno = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tInsuredNo = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 2);
					tContent = "Ͷ���˻���Ϣ�޸ĳɹ���";
				}
				tFlagStr = "Succ";
			}
		} catch (Exception ex) {
			tContent = tFlagStr + "�����쳣������ϵϵͳ��ά��Ա��";
			tFlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tContno%>","<%=tInsuredNo%>");
</script>
</html>
