<%
//�������ƣ�PEdorTypePCSubmit.jsp
//�����ܣ�
//�������ڣ�2005-5-8 10:47����
//������  ��Lihs
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %>

<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  System.out.println("-----PCsubmit---");
  
  LPContSchema tLPContSchema   = new LPContSchema(); 
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  LPAccountSchema tLPAccountSchema = new LPAccountSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  CErrors tError = null;
  //����Ҫִ�еĶ�������ӣ��޸�
 
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result="";
  
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
  System.out.println("------transact:"+transact);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  
  //���˱���������Ϣ
  
  if (transact.equals("UPDATE||MAIN")) {

  tLPEdorItemSchema.setPolNo("000000");
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));		
	tLPEdorItemSchema.setInsuredNo("000000");
	
	String theCurrentDate = PubFun.getCurrentDate();
  String theCurrentTime = PubFun.getCurrentTime();
  
  tLPEdorItemSchema.setMakeDate(theCurrentDate);
  tLPEdorItemSchema.setMakeTime(theCurrentTime);
  tLPEdorItemSchema.setModifyDate(theCurrentDate);
  tLPEdorItemSchema.setModifyTime(theCurrentTime);  
  tLPEdorItemSchema.setOperator(tG.Operator);
  
  tLPContSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPContSchema.setEdorType(request.getParameter("EdorType"));
  tLPContSchema.setContNo(request.getParameter("ContNo"));
  tLPContSchema.setPayLocation(request.getParameter("PayLocation"));
	tLPContSchema.setBankCode(request.getParameter("BankCode"));
	tLPContSchema.setBankAccNo(request.getParameter("BankAccNo"));
	tLPContSchema.setAccName(request.getParameter("AccName"));
	
	tLPAccountSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPAccountSchema.setEdorType(request.getParameter("EdorType"));
	tLPAccountSchema.setCustomerNo(request.getParameter("AppntNo"));
	tLPAccountSchema.setAccKind("Y");
	tLPAccountSchema.setBankCode(request.getParameter("BankCode"));
	tLPAccountSchema.setBankAccNo(request.getParameter("BankAccNo"));
	tLPAccountSchema.setAccName(request.getParameter("AccName"));
	tLPAccountSchema.setOperator(tG.Operator);	
	tLPAccountSchema.setMakeDate(theCurrentDate);
  tLPAccountSchema.setMakeTime(theCurrentTime);
  tLPAccountSchema.setModifyDate(theCurrentDate);
  tLPAccountSchema.setModifyTime(theCurrentTime);  
 
  }

  try {
     // ׼���������� VData
  
  	 VData tVData = new VData();  
  	
	 //������˱�����Ϣ(��ȫ)	
      tVData.addElement(tG);
      tVData.addElement(tLPEdorItemSchema);
      tVData.addElement(tLPContSchema);
      tVData.addElement(tLPAccountSchema);
//      boolean tag = tEdorDetailUI.submitData(tVData,transact); 
      boolean tag = tBusinessDelegate.submitData(tVData,transact,busiName);    
   } catch(Exception ex) {
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
	 }			
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr=="") {
  		System.out.println("------success");
//    	tError = tEdorDetailUI.mErrors;
    	tError = tBusinessDelegate.getCErrors(); 
    	if (!tError.needDealError()) {                          
        Content = " ����ɹ�";
    		FlagStr = "Success";  
    	 }else{
    		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    		FlagStr = "Fail";
    	 }
  	}
%>                      
<html>
<script language="javascript">
   
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

