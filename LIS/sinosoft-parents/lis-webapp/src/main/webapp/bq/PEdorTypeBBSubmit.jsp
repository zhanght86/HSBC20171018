<%
//�������ƣ�GEdorTypeBBSubmit.jsp
//�����ܣ�
//�������ڣ�2002-10-9 
//������  ��lh
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

  //ֻ��Ͷ����
  if("0".equals(AppntIsInsuredFlag))
  {
		
		tLPAppntSchema.setContNo(request.getParameter("ContNo"));
	    tLPAppntSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPAppntSchema.setEdorType(request.getParameter("EdorType"));
		tLPAppntSchema.setAppntNo(request.getParameter("CustomerNo"));
		tLPAppntSchema.setAppntName(request.getParameter("Name_New"));
		tLPAppntSchema.setMarriage(request.getParameter("AppntMarriage_New"));
		tLPAppntSchema.setNativePlace(request.getParameter("NativePlace_New"));
		tLPAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress_New"));
		tLPAppntSchema.setLanguage(request.getParameter("NewLanguage"));
		
  }
  //ֻ�Ǳ�����
  if("1".equals(AppntIsInsuredFlag))
  {
		
		tLPInsuredSchema.setContNo(request.getParameter("ContNo"));
	    tLPInsuredSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPInsuredSchema.setEdorType(request.getParameter("EdorType"));
		tLPInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));
		tLPInsuredSchema.setName(request.getParameter("Name_New"));
		tLPInsuredSchema.setMarriage(request.getParameter("AppntMarriage_New"));
		tLPInsuredSchema.setNativePlace(request.getParameter("NativePlace_New"));
		tLPInsuredSchema.setRgtAddress(request.getParameter("AppntRgtAddress_New"));
		tLPInsuredSchema.setSocialInsuFlag(request.getParameter("NewSFlag"));
		tLPInsuredSchema.setLanguage(request.getParameter("NewLanguage"));
		
  }

	  
  //���Ͷ��������ͬһ���˵Ļ�����Ҫ����LCAppnt,��LCInSured
  if("2".equals(AppntIsInsuredFlag))
  {
		tLPInsuredSchema.setContNo(request.getParameter("ContNo"));
	    tLPInsuredSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPInsuredSchema.setEdorType(request.getParameter("EdorType"));
		tLPInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));
		tLPInsuredSchema.setName(request.getParameter("Name_New"));
		tLPInsuredSchema.setMarriage(request.getParameter("AppntMarriage_New"));
		tLPInsuredSchema.setNativePlace(request.getParameter("NativePlace_New"));
		tLPInsuredSchema.setRgtAddress(request.getParameter("AppntRgtAddress_New"));
		tLPInsuredSchema.setSocialInsuFlag(request.getParameter("NewSFlag"));
		tLPInsuredSchema.setLanguage(request.getParameter("NewLanguage"));
		
		tLPAppntSchema.setContNo(request.getParameter("ContNo"));
	    tLPAppntSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPAppntSchema.setEdorType(request.getParameter("EdorType"));
		tLPAppntSchema.setAppntNo(request.getParameter("CustomerNo"));
		tLPAppntSchema.setAppntName(request.getParameter("Name_New"));
		tLPAppntSchema.setMarriage(request.getParameter("AppntMarriage_New"));
		tLPAppntSchema.setNativePlace(request.getParameter("NativePlace_New"));
		tLPAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress_New"));
		tLPAppntSchema.setLanguage(request.getParameter("NewLanguage"));
		tLPAppntSchema.setSocialInsuFlag(request.getParameter("NewSFlag"));
		
  }
try
  {
  // ׼���������� VData
  
  	VData tVData = new VData();  	
  	tVData.add(tLPEdorItemSchema);
    tVData.add(tLPInsuredSchema);
    tVData.add(tLPAppntSchema);
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

