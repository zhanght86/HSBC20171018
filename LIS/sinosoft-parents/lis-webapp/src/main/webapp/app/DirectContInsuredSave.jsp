<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�DirectContInsuredSave.jsp
//�����ܣ�ֱ������¼�뱻������Ϣ����
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//������Ϣ������У�鴦��
	//�������
	LCContSchema tLCContSchema = new LCContSchema();
	LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
	LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
	LCAddressSchema tLCAddressSchema = new LCAddressSchema();
	LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
	LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
	LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
	TransferData tTransferData = new TransferData(); 
	//ContInsuredUI tContInsuredUI   = new ContInsuredUI();
	String busiName="tbContInsuredUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//�������
	String FlagStr = "";
	String Content = "";
   
	GlobalInput tGI = new GlobalInput();  
	tGI=(GlobalInput)session.getValue("GI");   
	loggerDebug("DirectContInsuredSave","tGI"+tGI);
	if(tGI==null)
	{
		loggerDebug("DirectContInsuredSave","ҳ��ʧЧ,�����µ�½");   
		FlagStr = "Fail";        
		Content = "ҳ��ʧЧ,�����µ�½";  
	}
	else //ҳ����Ч
	{
		CErrors tError = null;
		String tBmCert = "";
		//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
		String fmAction=request.getParameter("fmAction");
		loggerDebug("DirectContInsuredSave","fmAction:"+fmAction); 
		tLCContSchema.setGrpContNo(request.getParameter("GrpContNo"));  
		tLCContSchema.setContNo(request.getParameter("ContNo")); 
		tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
		tLCContSchema.setManageCom(request.getParameter("ManageCom"));
		
		tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
		tmLCInsuredSchema.setRelationToMainInsured(request.getParameter("RelationToMainInsured"));
		tmLCInsuredSchema.setRelationToAppnt(request.getParameter("RelationToAppnt"));
		tmLCInsuredSchema.setContNo(request.getParameter("ContNo"));
		tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
		tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
		tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
		tmLCInsuredSchema.setJoinCompanyDate(request.getParameter("JoinCompanyDate"));
		tmLCInsuredSchema.setInsuredPeoples(request.getParameter("InsuredPeoples"));
		tmLCInsuredSchema.setSalary(request.getParameter("Salary"));
		tmLCInsuredSchema.setBankCode(request.getParameter("BankCode"));
		tmLCInsuredSchema.setBankAccNo(request.getParameter("BankAccNo"));                
		tmLCInsuredSchema.setAccName(request.getParameter("AccName"));
		tmLCInsuredSchema.setLicenseType(request.getParameter("LicenseType"));
		
		tLDPersonSchema.setCustomerNo(request.getParameter("InsuredNo"));
		tLDPersonSchema.setName(request.getParameter("InsuredName"));
		tLDPersonSchema.setSex(request.getParameter("InsuredSex"));      
		tLDPersonSchema.setBirthday(request.getParameter("InsuredBirthday"));
		tLDPersonSchema.setIDType(request.getParameter("InsuredIDType"));
		tLDPersonSchema.setIDNo(request.getParameter("InsuredIDNo"));
		tLDPersonSchema.setNativePlace(request.getParameter("InsuredNativePlace"));
		tLDPersonSchema.setNationality(request.getParameter("InsuredNationality"));
		tLDPersonSchema.setMarriage(request.getParameter("InsuredMarriage"));
		tLDPersonSchema.setMarriageDate(request.getParameter("InsuredMarriageDate"));
		tLDPersonSchema.setOccupationType(request.getParameter("InsuredOccupationType"));
		tLDPersonSchema.setOccupationCode(request.getParameter("InsuredOccupationCode"));
		tLDPersonSchema.setLicenseType(request.getParameter("InsuredLicenseType"));

		tLCAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
		if(request.getParameter("InsuredAddressNo")!=null && !request.getParameter("InsuredAddressNo").equals(""))
		{
			tLCAddressSchema.setAddressNo(request.getParameter("InsuredAddressNo"));  
		}
		tLCAddressSchema.setPostalAddress(request.getParameter("InsuredPostalAddress"));
		tLCAddressSchema.setZipCode(request.getParameter("InsuredZipCode"));
		tLCAddressSchema.setPhone(request.getParameter("InsuredPhone"));
		tLCAddressSchema.setFax(request.getParameter("InsuredFax"));          
		tLCAddressSchema.setMobile(request.getParameter("InsuredMobile"));        
		tLCAddressSchema.setEMail(request.getParameter("InsuredEMail"));
		tLCAddressSchema.setHomeAddress(request.getParameter("InsuredHomeAddress"));        
		tLCAddressSchema.setHomeZipCode(request.getParameter("InsuredHomeZipCode"));
		tLCAddressSchema.setHomePhone(request.getParameter("InsuredHomePhone"));
		tLCAddressSchema.setHomeFax(request.getParameter("InsuredHomeFax"));        
		tLCAddressSchema.setCompanyPhone(request.getParameter("InsuredGrpPhone"));
		tLCAddressSchema.setCompanyAddress(request.getParameter("InsuredGrpAddress"));
		tLCAddressSchema.setCompanyZipCode(request.getParameter("InsuredGrpZipCode"));
		tLCAddressSchema.setCompanyFax(request.getParameter("InsuredGrpFax"));        
		tLCAddressSchema.setGrpName(request.getParameter("InsuredGrpName"));        
		tLCAddressSchema.setProvince(request.getParameter("InsuredProvince"));
		tLCAddressSchema.setCity(request.getParameter("InsuredCity"));
		tLCAddressSchema.setCounty(request.getParameter("InsuredDistrict"));

		String SavePolType="";
		String BQFlag=request.getParameter("BQFlag");
		if(BQFlag==null) SavePolType="0";
		else if(BQFlag.equals("")) SavePolType="0";
		else  SavePolType=BQFlag;   

		tTransferData.setNameAndValue("SavePolType",SavePolType); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
		tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
		tTransferData.setNameAndValue("FamilyType",request.getParameter("FamilyType")); //��ͥ����� 
		tTransferData.setNameAndValue("ContType",request.getParameter("ContType")); //�ŵ������˵����
		tTransferData.setNameAndValue("PolTypeFlag",request.getParameter("PolTypeFlag")); //���������
		tTransferData.setNameAndValue("InsuredPeoples",request.getParameter("InsuredPeoples")); //������������
		tTransferData.setNameAndValue("InsuredAppAge",request.getParameter("InsuredAppAge")); //������������
		tTransferData.setNameAndValue("SequenceNo",request.getParameter("SequenceNo")); //�ڲ��ͻ���
		loggerDebug("DirectContInsuredSave","fmAction=="+fmAction);

		if (fmAction.equals("UPDATE||CONTINSURED")||fmAction.equals("DELETE||CONTINSURED"))
		{
			String tRadio[] = request.getParameterValues("InpInsuredGridSel");
    		String tInsuredNo[] = request.getParameterValues("InsuredGrid1");
			if (tRadio!=null)
			{
				for (int index=0; index< tRadio.length;index++)
				{
					if(tRadio[index].equals("1"))
					{
						tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
						tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
						tOLDLCInsuredDB.setInsuredNo(tInsuredNo[index]);						
					}
				}
				if (tOLDLCInsuredDB.getInsuredNo()==null)
				{
					tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
					tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
					tOLDLCInsuredDB.setInsuredNo(tInsuredNo[0]);
				}
			}
			else
			{
				tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
				tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
				tOLDLCInsuredDB.setInsuredNo(tInsuredNo[0]);
			}
			loggerDebug("DirectContInsuredSave","ԭ���ı����˺���==="+tOLDLCInsuredDB.getInsuredNo());
		}
		String tImpartNum[] = request.getParameterValues("ImpartInsuredGridNo");
		String tImpartVer[] = request.getParameterValues("ImpartInsuredGrid1");            //��֪���
		String tImpartCode[] = request.getParameterValues("ImpartInsuredGrid2");           //��֪����
		String tImpartContent[] = request.getParameterValues("ImpartInsuredGrid3");        //��֪����
		String tImpartParamModle[] = request.getParameterValues("ImpartInsuredGrid4");        //��д����
		int ImpartCount = 0;
		if (tImpartNum != null) ImpartCount = tImpartNum.length;
		for (int i = 0; i < ImpartCount; i++)	
		{
			LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
			tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ProposalContNo"));
			tLCCustomerImpartSchema.setCustomerNoType("1");
			tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
			tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
			tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
			tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]);
			tLCCustomerImpartSchema.setPatchNo("0");
			tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		}
          
		try
		{
			// ׼���������� VData
			VData tVData = new VData();
			tVData.add(tLCContSchema);
			tVData.add(tLDPersonSchema);
			tVData.add(tLCCustomerImpartSet);
//			tVData.add(tLCCustomerImpartDetailSet);             
			tVData.add(tmLCInsuredSchema);
			tVData.add(tLCAddressSchema);
			tVData.add(tOLDLCInsuredDB);   
			tVData.add(tTransferData);
			tVData.add(tGI);

			//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
			if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
			{
			Content ="�����ɹ���";
			FlagStr = "Succ";
			}
		}
		catch(Exception ex)
		{
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr=="")
		{
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError())
			{                          
			Content ="�����ɹ���";
			FlagStr = "Succ";
			}
			else                                                                           
			{
			Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
			}
		}
		loggerDebug("DirectContInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
	}

%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>


