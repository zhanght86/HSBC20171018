<%
//�������ƣ�PEdorTypeAMSubmit.jsp
//�����ܣ�
//�������ڣ�2008-10-9 
//������  ��pst
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.*"%>
 	<%@page import="com.sinosoft.lis.bq.*"%>
 	<%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page contentType="text/html;charset=GBK" %>
	<%@page import="com.sinosoft.service.*" %>

<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  
  LPInsuredSchema tLPInsuredSchema   = new LPInsuredSchema();
  LPAppntSchema  tLPAppntSchema = new LPAppntSchema();
  
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  
  //PEdorBBDetailUI  tPEdorBBDetailUI = new PEdorBBDetailUI();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
  GlobalInput tG = new GlobalInput();  
  tG = (GlobalInput)session.getValue("GI"); 
  //�������
  
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸�
 
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String IsAppntFlag="";
  /**Ͷ���˺ͱ������Ƿ���ͬһ��*/
  String AppntIsInsuredFlag=""; 
  String AppObj = "";
  
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
 
  AppntIsInsuredFlag=request.getParameter("AppntIsInsuredFlag");
  
 
   //���˱�ȫ��Ŀ����Ϣ
    System.out.println(request.getParameter("CustomerNo"));
  	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  	//tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
  	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
    tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	  tLPEdorItemSchema.setPolNo("000000");
	  tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
	  //��ʱ��ſͻ����
	  
	  tLPEdorItemSchema.setStandbyFlag1(AppntIsInsuredFlag);
	  //����ԭ���ĵ�ַ���
	  tLPEdorItemSchema.setStandbyFlag2(request.getParameter("AddressNo"));
	  
  LPAddressSchema tLPAddressSchema   = new LPAddressSchema();	  

  tLPAddressSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPAddressSchema.setEdorType(request.getParameter("EdorType"));	
	tLPAddressSchema.setCustomerNo(request.getParameter("CustomerNo"));
	tLPAddressSchema.setAddressNo(request.getParameter("AddressNo"));
		
	tLPAddressSchema.setPostalAddress(request.getParameter("PostalAddress_New"));
	tLPAddressSchema.setZipCode(request.getParameter("ZipCode_New"));
	tLPAddressSchema.setHomeAddress(request.getParameter("HomeAddress_New"));
	tLPAddressSchema.setHomeZipCode(request.getParameter("HomeZipCode_New"));
	tLPAddressSchema.setMobile(request.getParameter("Mobile_New"));
  tLPAddressSchema.setPhone(request.getParameter("GrpPhone_New"));

	tLPAddressSchema.setEMail(request.getParameter("EMail_New"));
	tLPAddressSchema.setGrpName(request.getParameter("GrpName_New"));

  
try
  {
  // ׼���������� VData
  
  	VData tVData = new VData();  	
  	tVData.add(tLPEdorItemSchema);
    tVData.add(tLPAddressSchema);
  	tVData.add(tG);
//  	if(!tEdorDetailUI.submitData(tVData,transact))
  	if(!tBusinessDelegate.submitData(tVData,transact,busiName))
  	{
//  	  tError = tEdorDetailUI.mErrors;
  	  tError = tBusinessDelegate.getCErrors();
  	  Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
  	}else
  	{
  	
  	  Content = " ����ɹ���";
    	FlagStr = "Success";
  	
  	}
       	  
	}
	catch(Exception ex)
	{
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
	}			
  //��Ӹ���Ԥ����

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

