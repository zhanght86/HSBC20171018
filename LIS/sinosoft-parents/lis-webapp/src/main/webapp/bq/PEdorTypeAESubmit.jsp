<%
//�������ƣ�PEdorTypeAESubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.lang.*"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>


<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  LPAppntSchema tLPAppntSchema   = new LPAppntSchema();
  LPAddressSchema tLPAddressSchema   = new LPAddressSchema();
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  LPInsuredSet mLPInsuredSet = new LPInsuredSet();
  LPPersonSchema tLPPersonSchema = new LPPersonSchema();
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();

  
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸�
 
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result="";
  String appnt = "";
  String app = "";
  String tLastName = request.getParameter("LastName");
  if(tLastName == null){
	  tLastName = "";
  }
  String tFirstName  = request.getParameter("FirstName");
  if(tFirstName == null){
	  tFirstName = "";
  }
  String tLastNameEn  = request.getParameter("LastNameEn");
  if(tLastNameEn == null){
	  tLastNameEn = "";
  }
  String tFirstNameEn  = request.getParameter("FirstNameEn");
  if(tFirstNameEn == null){
	  tFirstNameEn = "";
  }
  String tLastNamePY  = request.getParameter("LastNamePY");
  if(tLastNamePY == null){
	  tLastNamePY = "";
  }
  String tFirstNamePY  = request.getParameter("FirstNamePY");
  if(tFirstNamePY == null){
	  tFirstNamePY = "";
  }
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  
  //���˱���������Ϣ
  tLPEdorItemSchema.setPolNo("000000");
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setEdorReasonCode(request.getParameter("EdorReason"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate"));
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
	tLPEdorItemSchema.setInsuredNo("000000");
	String theCurrentDate = PubFun.getCurrentDate();
  String theCurrentTime = PubFun.getCurrentTime();
  
  tLPEdorItemSchema.setMakeDate(theCurrentDate);
  tLPEdorItemSchema.setMakeTime(theCurrentTime);
  tLPEdorItemSchema.setModifyDate(theCurrentDate);
  tLPEdorItemSchema.setModifyTime(theCurrentTime);  
  tLPEdorItemSchema.setOperator("001");
  
  //ԭͶ������Ϣ����ԭͶ��������ʱʹ�ã�
  tLPPersonSchema.setCustomerNo(request.getParameter("PCustomerNo"));
  tLPPersonSchema.setDeathDate(request.getParameter("deathdate"));
  tLPPersonSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPPersonSchema.setEdorType(request.getParameter("EdorType"));
	
	
	tLPAppntSchema.setContNo(request.getParameter("ContNo"));
  tLPAppntSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPAppntSchema.setEdorType(request.getParameter("EdorType"));		
	tLPAppntSchema.setAppntNo(request.getParameter("AppntNo"));
	
	//tLPAppntSchema.setAppntName(app);
	tLPAppntSchema.setAppntName(tLastName+tFirstName);
	
	//System.out.println("ok------"+app+"------");
	tLPAppntSchema.setAppntSex(request.getParameter("AppntSex"));
	tLPAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));
	tLPAppntSchema.setIDType(request.getParameter("AppntIDType"));
	tLPAppntSchema.setIDNo(request.getParameter("AppntIDNo"));
	tLPAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));
	tLPAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));
	tLPAppntSchema.setMarriage(request.getParameter("AppntMarriage"));
	tLPAppntSchema.setNativePlace(request.getParameter("AppntNativePlace"));
	//tLPAppntSchema.setNationality(request.getParameter("AppntNationality"));
	tLPAppntSchema.setBankCode(request.getParameter("GetBankCode"));
	tLPAppntSchema.setBankAccNo(request.getParameter("GetBankAccNo"));
	tLPAppntSchema.setAccName(request.getParameter("GetAccName"));
	tLPAppntSchema.setWorkType(request.getParameter("AppntWorkType"));
	tLPAppntSchema.setPluralityType(request.getParameter("PluralityType"));	
	tLPAppntSchema.setRelationToInsured(request.getParameter("RelationToInsured"));
	tLPAppntSchema.setLastName(tLastName);
	tLPAppntSchema.setFirstName(tFirstName);
	tLPAppntSchema.setLastNameEn(tLastNameEn);
	tLPAppntSchema.setFirstNameEn(tFirstNameEn);
	tLPAppntSchema.setNameEn(tLastNameEn+tFirstNameEn);
	tLPAppntSchema.setLastNamePY(tLastNamePY);
	tLPAppntSchema.setFirstNamePY(tFirstNamePY);
	tLPAppntSchema.setLanguage(request.getParameter("AppntLanguage"));

	//String smokeflag  = request.getParameter("AppntSmokeFlag");
	String rgtaddress = request.getParameter("AppntRgtAddress");
	//System.out.println("smokeflag"+smokeflag);
	System.out.println("rgtaddress"+rgtaddress);
	tLPAppntSchema.setRgtAddress(rgtaddress);
	//tLPAppntSchema.setSmokeFlag(smokeflag);
	System.out.println("RgtAddress"+tLPAppntSchema.getRgtAddress());
	//System.out.println("smokeflag"+tLPAppntSchema.getSmokeFlag());
	System.out.println("AccName"+tLPAppntSchema.getAccName());	
	

	tLPAddressSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPAddressSchema.setEdorType(request.getParameter("EdorType"));	
	tLPAddressSchema.setCustomerNo(request.getParameter("AppntNo"));
	tLPAddressSchema.setAddressNo(request.getParameter("AddressNo"));
	
	//tLPAddressSchema.setProvince(request.getParameter("AppntProvince"));
	//tLPAddressSchema.setCounty(request.getParameter("AppntDistrict"));
	//tLPAddressSchema.setCity(request.getParameter("AppntCity"));
	
	tLPAddressSchema.setPostalAddress(request.getParameter("PostalAddress"));
	tLPAddressSchema.setZipCode(request.getParameter("ZipCode"));
	tLPAddressSchema.setMobile(request.getParameter("Mobile"));
  tLPAddressSchema.setPhone(request.getParameter("GrpPhone"));
	//tLPAddressSchema.setPhone(request.getParameter("HomePhone"));
	//tLPAddressSchema.setFax(request.getParameter("Fax"));
	tLPAddressSchema.setEMail(request.getParameter("EMail"));
	tLPAddressSchema.setGrpName(request.getParameter("GrpName"));
	tLPAddressSchema.setHomeAddress(request.getParameter("HomeAddress"));
	tLPAddressSchema.setHomeZipCode(request.getParameter("HomeZipCode"));
  //tLPAddressSchema.setHomePhone(request.getParameter("HomePhone"));
  //tLPAddressSchema.setHomeFax(request.getParameter("AppntHomeFax"));
  //tLPAddressSchema.setCompanyAddress(request.getParameter("CompanyAddress"));
	//tLPAddressSchema.setCompanyZipCode(request.getParameter("AppntGrpZipCode"));
  //tLPAddressSchema.setCompanyFax(request.getParameter("AppntGrpFax"));
  
  
  LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
  String tInsuredNum[] = request.getParameterValues("InsuredGridNo");
  String tInsuredNo[] = request.getParameterValues("InsuredGrid1");
  String tRelationToMainInsured[] = request.getParameterValues("InsuredGrid7");
  String tRelationToAppnt[] = request.getParameterValues("InsuredGrid9");
  int InsuredCount = 0;
  if(tInsuredNum != null){
    InsuredCount = tInsuredNum.length;
  }
  System.out.println(InsuredCount);     
  if(InsuredCount != 0){
  	for(int i = 0; i < InsuredCount; i++)	{
  	  tLPInsuredSchema = new LPInsuredSchema();
  	  System.out.println(tInsuredNo);

  	  tLPInsuredSchema.setRelationToMainInsured(tRelationToMainInsured[i]);
  	  tLPInsuredSchema.setRelationToAppnt(tRelationToAppnt[i]);
  	  tLPInsuredSchema.setInsuredNo(tInsuredNo[i]);
  	  System.out.println(tRelationToAppnt[i]);
  	  tLPInsuredSchema.setContNo(request.getParameter("ContNo"));
  	  mLPInsuredSet.add(tLPInsuredSchema);
  	}
  }   
    
  
  String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
	String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
	String tImpartContent[] = request.getParameterValues("ImpartGrid3");        //��֪����
	String tImpartParamModle[] = request.getParameterValues("ImpartGrid4");        //��д����
		//String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //��֪�ͻ�����
		//String tImpartCustomerNo[] = request.getParameterValues("ImpartGrid6");  
  int ImpartCount = 0;
  


  LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
	if (tImpartNum != null) ImpartCount = tImpartNum.length;
	System.out.println(ImpartCount);
	if(ImpartCount != 0){	  	    
	   for (int i = 0; i < ImpartCount; i++)	{
	      if(tImpartCode[i] == null || tImpartCode[i].equals("") || tImpartContent[i] == null || tImpartContent[i].equals("")
	      || tImpartParamModle[i] == null ||tImpartParamModle[i].equals("")
	      || tImpartVer[i] == null || tImpartVer[i].equals("")){
	      }
	      else{
	         tLCCustomerImpartSchema = new LCCustomerImpartSchema();		
           //tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
           //tLCCustomerImpartSchema.setProposalContNo(tLCContDB.getPrtNo());
           tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
				   tLCCustomerImpartSchema.setCustomerNo(request.getParameter("AppntNo"));
				   tLCCustomerImpartSchema.setCustomerNoType("0");
				   tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
				   tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
				   tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
				   tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
				   tLCCustomerImpartSchema.setMakeDate(theCurrentDate);
				   tLCCustomerImpartSchema.setMakeTime(theCurrentTime);
				   tLCCustomerImpartSchema.setModifyDate(theCurrentDate);
				   tLCCustomerImpartSchema.setModifyTime(theCurrentTime);
				   tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
				}
			}			
      System.out.println("end set schema ��֪��Ϣ..."+ImpartCount);
  }

  // ׼���������� VData

  VData tVData = new VData();  
	 //������˱�����Ϣ(��ȫ)	
	 tVData.addElement(tG);
	 tVData.addElement(tLPEdorItemSchema);
   tVData.addElement(tLPAppntSchema);
   tVData.addElement(tLPAddressSchema);
   tVData.addElement(mLPInsuredSet);
   tVData.addElement(tLCCustomerImpartSet);

	 
	 if(tLPEdorItemSchema.getEdorReasonCode().equals("01")){
	    tVData.addElement(tLPPersonSchema);
	 }	 
	  String ErrorMessage = "";
    String busiName="EdorDetailUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	 

    if(!tBusinessDelegate.submitData(tVData,transact,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
           ErrorMessage=tBusinessDelegate.getCErrors().getError(0).moduleName;
				   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "����ʧ��";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content =" ����ɹ�!�ñ���"+request.getParameter("ContNo")+"Ͷ�����ѱ������ע����пͻ���ϵ��ʽ������Զ�ת���ʺű����ǩ���������Ŀ�Ĳ���";
		    	FlagStr = "Succ";	
	 }	 	


%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

