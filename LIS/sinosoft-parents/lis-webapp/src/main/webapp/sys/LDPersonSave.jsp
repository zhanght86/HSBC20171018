<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
//�������ƣ�LDPersonSave.jsp
//�����ܣ��ͻ����������ͻ���
//�������ڣ�2005-06-20
//������  ��wangyan
//���¼�¼��������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
   <%@page import="com.sinosoft.service.*" %>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //������Ϣ������У�鴦��
  //�������
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
  LCAddressSchema tLCAddressSchema   = new LCAddressSchema();
  LCAccountSchema tLCAccountSchema   = new LCAccountSchema();
  
  LDPersonSchema mLDPersonSchema = new LDPersonSchema();   
  LCAddressSchema mLCAddressSchema = new LCAddressSchema();
  LCAccountSchema mLCAccountSchema = new LCAccountSchema();  
  String busiName="sysLDPersonUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //LDPersonUI tLDPersonUI   = new LDPersonUI();
  
  //�������
  CErrors tError = null;
  String tLimit="";
  String CustomerNo="";
  String FlagStr = "";
  String Content = "";
 
  GlobalInput tGI = new GlobalInput(); 
  
  tGI=(GlobalInput)session.getValue("GI");
  
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String transact=request.getParameter("Transact"); 

	//��װ�ͻ�������Ϣ
  tLDPersonSchema.setCustomerNo(request.getParameter("CustomerNo"));
  tLDPersonSchema.setName(request.getParameter("Name"));
  tLDPersonSchema.setSex(request.getParameter("AppntSex"));
  tLDPersonSchema.setBirthday(request.getParameter("Birthday"));
  tLDPersonSchema.setIDType(request.getParameter("AppntIDType"));
  tLDPersonSchema.setIDNo(request.getParameter("IDNo"));

  tLDPersonSchema.setNativePlace(request.getParameter("AppntNativePlace"));
  tLDPersonSchema.setNationality(request.getParameter("AppntNationality"));
  tLDPersonSchema.setRgtAddress(request.getParameter("RgtAddress"));
  tLDPersonSchema.setMarriage(request.getParameter("AppntMarriage"));
  
  tLDPersonSchema.setHealth(request.getParameter("HealthState"));
  tLDPersonSchema.setStature(request.getParameter("Stature"));
  tLDPersonSchema.setAvoirdupois(request.getParameter("Avoirdupois"));
  tLDPersonSchema.setDegree(request.getParameter("DegreeState"));
  tLDPersonSchema.setCreditGrade(request.getParameter("CreditGrade"));
  tLDPersonSchema.setOthIDType(request.getParameter("OthIDType"));
  tLDPersonSchema.setOthIDNo(request.getParameter("OthIDNo"));
  
 
 
 
  
 
  
  tLDPersonSchema.setPluralityType(request.getParameter("PluralityType"));
 
  tLDPersonSchema.setSmokeFlag(request.getParameter("SmokeFlag"));
  tLDPersonSchema.setBlacklistFlag(request.getParameter("listFlag"));
  tLDPersonSchema.setProterty(request.getParameter("ProtertyState"));
  tLDPersonSchema.setRemark(request.getParameter("Remark"));
  tLDPersonSchema.setState(request.getParameter("State"));
  tLDPersonSchema.setVIPValue(request.getParameter("VIPValueState"));
   
	//��װ�ͻ���ַ��Ϣ
  tLCAddressSchema.setAddressNo(request.getParameter("AddressNo"));
  tLCAddressSchema.setPostalAddress(request.getParameter("PostalAddress"));
  tLCAddressSchema.setZipCode(request.getParameter("ZipCode"));
  tLCAddressSchema.setPhone(request.getParameter("Phone"));
  tLCAddressSchema.setFax(request.getParameter("Fax"));
  tLCAddressSchema.setHomeAddress(request.getParameter("HomeAddress"));
  tLCAddressSchema.setHomeZipCode(request.getParameter("HomeZipCode"));
  tLCAddressSchema.setHomePhone(request.getParameter("HomePhone"));
  tLCAddressSchema.setHomeFax(request.getParameter("HomeFax"));
  tLCAddressSchema.setCompanyAddress(request.getParameter("CompanyAddress"));
  tLCAddressSchema.setCompanyZipCode(request.getParameter("CompanyZipCode"));
  tLCAddressSchema.setCompanyPhone(request.getParameter("CompanyPhone"));
  tLCAddressSchema.setCompanyFax(request.getParameter("CompanyFax"));
  tLCAddressSchema.setMobile(request.getParameter("Mobile"));
  tLCAddressSchema.setMobileChs(request.getParameter("MobileChs"));
  tLCAddressSchema.setEMail(request.getParameter("EMail"));
  tLCAddressSchema.setBP(request.getParameter("BP"));
  tLCAddressSchema.setMobile2(request.getParameter("Mobile2"));
  tLCAddressSchema.setMobileChs2(request.getParameter("MobileChs2"));
  tLCAddressSchema.setEMail2(request.getParameter("EMail2"));
  tLCAddressSchema.setBP2(request.getParameter("BP2"));

	//��װ�ͻ��ʺ���Ϣ
  tLCAccountSchema.setAccKind(request.getParameter("AccounteFlag"));
  tLCAccountSchema.setBankCode(request.getParameter("BankCodeState"));
  tLCAccountSchema.setBankAccNo(request.getParameter("GetBankAccNo"));
  tLCAccountSchema.setAccName(request.getParameter("AccName"));
  tLCAccountSchema.setOperator(tGI.Operator); 

	// ׼���������� VData
	VData tVData = new VData();
	tVData.addElement(tLDPersonSchema);
	tVData.addElement(tLCAddressSchema);
	tVData.addElement(tLCAccountSchema);
	tVData.addElement(tGI);
	

  try
  {
	  tBusinessDelegate.submitData(tVData,transact,busiName);
  
    /*
    if (transact.equals("INSERT"))
		{			        	
  		tVData.clear();
  		tVData = tBusinessDelegate.getResult();
  		
      mLDPersonSchema.setSchema(( LDPersonSchema )tVData.getObjectByObjectName( "LDPersonSchema", 0 ));
      mLCAddressSchema.setSchema(( LCAddressSchema )tVData.getObjectByObjectName( "LCAddressSchema", 0 ));
      mLCAccountSchema.setSchema(( LCAccountSchema )tVData.getObjectByObjectName( "LCAccountSchema", 0 ));
		%>
		<script language="javascript">
		parent.fraInterface.fm.all("CustomerNo").value = "<%=mLDPersonSchema.getCustomerNo()%>";
		parent.fraInterface.fm.all("AddressNo").value = "<%=mLCAddressSchema.getAddressNo()%>";		         	
		</script>  
		<%
		}
    if (transact.equals("UPDATE"))
		{			        	
  		tVData.clear();
  		tVData = tBusinessDelegate.getResult();     

      mLDPersonSchema.setSchema(( LDPersonSchema )tVData.getObjectByObjectName( "LDPersonSchema", 0 ));
      mLCAddressSchema.setSchema(( LCAddressSchema )tVData.getObjectByObjectName( "LCAddressSchema", 0 ));
      mLCAccountSchema.setSchema(( LCAccountSchema )tVData.getObjectByObjectName( "LCAccountSchema", 0 ));
		%>
		<script language="javascript">
		parent.fraInterface.fm.all("AddressNo").value = "<%=mLCAddressSchema.getAddressNo()%>";		         	
		</script>  
		<%		
		}*/
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
    	Content = " ����ɹ�! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " ����ʧ�ܣ�ʧ��ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%> 

                                     
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

