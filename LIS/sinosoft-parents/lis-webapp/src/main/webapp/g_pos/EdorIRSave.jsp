<%
/***************************************************************
* <p>ProName��EdorIRSave.jsp</p>
 * <p>Title���滻��������</p>
 * <p>Description���滻��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-06-12
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
	String tInsuredid ="";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	if (tGI==null) {
		tFlagStr = "Fail";
		tContent = "ҳ��ʧЧ,�����µ�½";
	} else {
		
		try {
		
			// �����˻�����Ϣ
			String tOperate = request.getParameter("Operate");
			String tEdorType = request.getParameter("EdorType");
			String tEdorAppNo = request.getParameter("EdorAppNo");
			String tEdorNo = request.getParameter("EdorNo");
			String tActivityID = request.getParameter("ActivityID");
			String tMissionID = request.getParameter("MissionID");
			String tSubMissionID = request.getParameter("SubMissionID");
			String tOldInsuredName = request.getParameter("ChInsuredName");
			String tOldInsuredIDNo = request.getParameter("ChIdNo"); 
			String tSerialno =  request.getParameter("Serialno"); 
			String tInsuredID = request.getParameter("InsuredID");

			String tGrpContNo = request.getParameter("GrpPropNo"); // Ͷ�����
			String tRelationToMain = request.getParameter("relatomain"); // ���������˹�ϵ
			String tRelatomainName = request.getParameter("relatomainName");
			String tMainInsuredName = request.getParameter("mainCustName");
			String tMainInsuredIDNo = request.getParameter("mainIDNo");		
			String tEdorValDate = request.getParameter("edorValDate");// ��ȫ��Ч����
			String tPlanCode = request.getParameter("ContPlanCode"); // ��������
			String tPlanDesc = request.getParameter("ContPlanCodeName");
			String tInsuredName = request.getParameter("InsuredName");;		
			String tInsuredIDNo =request.getParameter("IDNo").toUpperCase(); // ֤�����루��¼���֤��������ĸת��Ϊ��д��
			String tInsuredGender =request.getParameter("InsuredGender");
			String tInsuredGenderName =request.getParameter("InsuredGenderName"); 
			String tInsuredBirthday =request.getParameter("InsuredBirthDay");
			String tInsuredIDType = request.getParameter("IDType");
			String tInsuredIDTypeName = request.getParameter("IDTypeName");		

			String tOccupCode = request.getParameter("OccupationCode");// ְҵ����
			String tOccupName  = request.getParameter("OccupationCodeName");
			
			// ������Ϣ
			String tHeadBankCode = request.getParameter("HeadBankCode"); //��������
			String tHeadBankName = request.getParameter("BankCodeName");
			String tAccName = request.getParameter("AccName"); //������
			String tBankAccNo = request.getParameter("BankAccNo"); //�˺�
			String tBankProvince = request.getParameter("Province"); //��������ʡ
			String tBankProvinceName = request.getParameter("ProvinceName1"); //��������ʡ
			String tBankCity = request.getParameter("City"); //������������
			String tBankCityName = request.getParameter("CityName1");
			
			//������Ϣ		
			String tServerArea = request.getParameter("ServerArea"); //��������
			String tServerAreaName = request.getParameter("ServiceArName");
			String tSubstandard = request.getParameter("Substandard"); //�Ƿ�α�׼��
			String tSubstandardName = request.getParameter("SubstandardName");
			String tSocialInsuFlag = request.getParameter("Social");  //�籣���
			String tSocialInsuFlagName = request.getParameter("SocialName");
			String tPosition = request.getParameter("Position");//ְ��
			String tPositionName = request.getParameter("PositionName");//ְ������
			String tJoinCompDate = request.getParameter("JoinCompDate"); //��˾ʱ��
			String tSeniority = request.getParameter("Seniority"); //����
			String tSalary = request.getParameter("Salary"); //��н
			
			// ������Ϣ
			String tWorkIDNo = request.getParameter("WorkIDNo"); //Ա����
			String tIsLongValid = request.getParameter("ISLongValid"); //֤���Ƿ���
			String tIsLongValidName = request.getParameter("ISLongValidName"); 
			String tIDEndDate = request.getParameter("LiscenceValidDate"); //֤����Ч��
			String tSubCompanyCode = request.getParameter("ComponyName"); //���ڷֹ�˾
			String tDeptCode = request.getParameter("DeptCode"); //���ڲ���
			String tInsureCode = request.getParameter("InsureCode"); //�������˱���
			//String tSubCustomerNo = request.getParameter("SubCustomerNo"); //�����ͻ�Ⱥ
			//String tSubCustomerName = request.getParameter("SubCustomerName"); //�����ͻ�Ⱥ����
			String tWorkAddress = request.getParameter("WorkAddress"); //������
			String tSocialInsuAddress = request.getParameter("SocialAddress"); //�籣��
			
			// ��������ϵ��Ϣ
			String tZipCode = request.getParameter("ZipCode");
			String tEMail = request.getParameter("EMail");
			String tWechat = request.getParameter("MicroNo"); // ΢�ź�
			String tPhone = request.getParameter("Phone");
			String tMobile = request.getParameter("Mobile"); 
			String tProvince = request.getParameter("ProvinceCode"); // ʡ����
			String tProvinceName = request.getParameter("ProvinceName");
			String tCity = request.getParameter("CityCode"); // �б���
			String tCityName = request.getParameter("CityName"); 
			String tCounty = request.getParameter("CountyCode"); // ������
			String tCountyName = request.getParameter("CountyName");
			String tAddress = request.getParameter("DetailAddress"); // ��ϸ��ַ
			
			LBPOEdorInsuredListSchema tLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
			LBPOEdorInsuredBnfListSet tLBPOEdorInsuredBnfListSet = new LBPOEdorInsuredBnfListSet();
			if("INSERT".equals(tOperate)||"UPDATE".equals(tOperate)){			
				tLBPOEdorInsuredListSchema.setSerialNo(tSerialno);
				tLBPOEdorInsuredListSchema.setInsuredID(tInsuredID);
				tLBPOEdorInsuredListSchema.setEdorAppNo(tEdorAppNo);
				tLBPOEdorInsuredListSchema.setEdorNo(tEdorNo);
				tLBPOEdorInsuredListSchema.setGrpContNo(tGrpContNo);
				tLBPOEdorInsuredListSchema.setActivityID(tActivityID);
				tLBPOEdorInsuredListSchema.setOldInsuredName(tOldInsuredName);
				tLBPOEdorInsuredListSchema.setOldInsuredIDNo(tOldInsuredIDNo);
				tLBPOEdorInsuredListSchema.setBatchNo(tEdorAppNo);
				tLBPOEdorInsuredListSchema.setEdorType(tEdorType);
				tLBPOEdorInsuredListSchema.setInsuredIDNo(tInsuredIDNo);
				tLBPOEdorInsuredListSchema.setRelationToMain(tRelationToMain);
				tLBPOEdorInsuredListSchema.setRelationToMainName(tRelatomainName);
				tLBPOEdorInsuredListSchema.setMainInsuredName(tMainInsuredName);
				tLBPOEdorInsuredListSchema.setMainInsuredIDNo(tMainInsuredIDNo);
				tLBPOEdorInsuredListSchema.setOldInsuredName(tOldInsuredName);
				tLBPOEdorInsuredListSchema.setOldInsuredIDNo(tOldInsuredIDNo);
				tLBPOEdorInsuredListSchema.setInsuredName(tInsuredName);
				tLBPOEdorInsuredListSchema.setInsuredIDType(tInsuredIDType);
				tLBPOEdorInsuredListSchema.setInsuredIDTypeName(tInsuredIDTypeName);
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
				
				// ��������Ϣ
				String tBnfNum[] = request.getParameterValues("BnfGridNo");
				String tBnfType[] = request.getParameterValues("BnfGrid2");//���������
				String tBnfTypeName[] = request.getParameterValues("BnfGrid1");// �������������
				String tBnfOrder[] = request.getParameterValues("BnfGrid4");//������˳��
				String tBnfOrderName[] = request.getParameterValues("BnfGrid3");// ������˳������
				String tBnfName[] = request.getParameterValues("BnfGrid5");//����
				String tGender[] = request.getParameterValues("BnfGrid7");//�Ա�
				String tGenderName[] = request.getParameterValues("BnfGrid6");
				String tBirthday[] = request.getParameterValues("BnfGrid8");//��������
				String tIDType[] = request.getParameterValues("BnfGrid10");//֤������
				String tIDTypeName[] = request.getParameterValues("BnfGrid9");
				String tIDNo[] = request.getParameterValues("BnfGrid11");//֤������
				String tRelationToInsured[] = request.getParameterValues("BnfGrid13");//�뱻���˹�ϵ
				String tRelationToInsuredName[] = request.getParameterValues("BnfGrid12");
				String tBnfLot[] = request.getParameterValues("BnfGrid14");//�������	
				
				LBPOEdorInsuredBnfListSchema tLBPOEdorInsuredBnfListSchema   = new LBPOEdorInsuredBnfListSchema();
				if(null != tBnfNum &&tBnfNum.length>=1 ){
				for (int i = 0; i < tBnfNum.length; i++){
					
					tLBPOEdorInsuredBnfListSchema = new LBPOEdorInsuredBnfListSchema();					
					tLBPOEdorInsuredBnfListSchema.setEdorAppNo(tEdorAppNo);
					tLBPOEdorInsuredBnfListSchema.setInsuredID(tInsuredID);
					tLBPOEdorInsuredBnfListSchema.setActivityID(tActivityID);
					tLBPOEdorInsuredBnfListSchema.setBatchNo(tEdorAppNo);
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
					//��������֤���룬�����������˵��Ա�ͳ�������
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
					
					tLBPOEdorInsuredBnfListSchema.setRelationToInsured(tRelationToInsured[i]);
					tLBPOEdorInsuredBnfListSchema.setRelationToInsuredName(tRelationToInsuredName[i]);
					tLBPOEdorInsuredBnfListSchema.setBnfLot(tBnfLot[i]);
					tLBPOEdorInsuredBnfListSet.add(tLBPOEdorInsuredBnfListSchema);
					}	
				}
			}
			
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("ActivityID",tActivityID);
			tTransferData.setNameAndValue("MissionID",tMissionID);
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
			tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
			tTransferData.setNameAndValue("EdorAppNo",tEdorAppNo);
			tTransferData.setNameAndValue("Serialno",tSerialno);
			tTransferData.setNameAndValue("InsuredID",tInsuredID);
			tTransferData.setNameAndValue("EdorType",tEdorType);
			
			tVData.add(tGI);
			tVData.add(tTransferData);
			tVData.add(tLBPOEdorInsuredListSchema);
			tVData.add(tLBPOEdorInsuredBnfListSet);
		
			BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			if (!tBusinessDelegate.submitData(tVData, tOperate, "EdorIRUI")) {
				tContent = tBusinessDelegate.getCErrors().getFirstError();
				tFlagStr = "Fail";
			} else {
				if ("INSERT".equals(tOperate)) {
					tInsuredid = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tContent = "��������Ϣ����ɹ���";
				} else if("UPDATE".equals(tOperate)) {
					tInsuredid = (String)tBusinessDelegate.getResult().getObjectByObjectName("String", 0, 1);
					tContent = "��������Ϣ�޸ĳɹ���";
				} else if("DELETE".equals(tOperate)){		
					tContent = "��������Ϣɾ���ɹ���";
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
	parent.fraInterface.afterSubmit("<%=tFlagStr%>", "<%=tContent%>","<%=tInsuredid%>");
</script>
</html>
