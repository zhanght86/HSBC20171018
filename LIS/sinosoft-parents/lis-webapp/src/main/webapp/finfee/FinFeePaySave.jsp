 <%
//�������ƣ�FinFeePaySave.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//         
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>    
  <%@page import="com.sinosoft.lis.pubfun.*"%> 
  <%@page import="com.sinosoft.service.*" %>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
// �������
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("FinFeePaySave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {   
   String ActuGetNo = request.getParameter("ActuGetNo");
   String PolNo = request.getParameter("PolNo");
   String PayMode = request.getParameter("PayMode");
String Currency = request.getParameter("Currency");
   String GetMoney = request.getParameter("GetMoney");
  // String AgentCode = request.getParameter("AgentCode");
   String Drawer = request.getParameter("SDrawer");
  // String AgentGroup = request.getParameter("AgentGroup");   
   String DrawerID = request.getParameter("SDrawerID");   
   String EnterAccDate = request.getParameter("EnterAccDate");   
   //String ConfDate = request.getParameter("ConfDate"); 
   //String Operator = request.getParameter("Operator");   
   //String ModifyDate = request.getParameter("ModifyDate");   
   //String DFBankCode = request.getParameter("BankCode4");
   //String DFBankAccNo = request.getParameter("AccNo4");
   //String DFAccName = request.getParameter("AccName4");   
  // String InBankCode = request.getParameter("InSureBankCode"); 
    String BankCode="";
    String AccName="";
    String BankAccNo="";
    String ChequeNo=""; 
//  if("4".equals(PayMode))     
//  { 
//     BankCode = request.getParameter("BankCode");  
//     AccName = request.getParameter("BankAccName");      
//     BankAccNo = request.getParameter("BankAccNo");
//   }
 if("9".equals(PayMode))
	{
		 BankCode = request.getParameter("BankCode9");  
     AccName = request.getParameter("BankAccName9");      
     BankAccNo = request.getParameter("BankAccNo9");
	}
  else if("2".equals(PayMode) || "3".equals(PayMode))
  {
  	  BankCode = request.getParameter("BankCode2");  
  	  ChequeNo = request.getParameter("ChequeNo"); 
  	  BankAccNo =ChequeNo;
  }
  else
  {
     BankCode="";
     AccName="";
     BankAccNo="";
     ChequeNo="";
    
	}
  // String InBankAccNo = request.getParameter("InSureAccNo");

   //String InAccName = request.getParameter("InSureAccName");        
   //String AccName = request.getParameter("InSureAccName");
   String ActualDrawer = request.getParameter("Drawer");
   String ActualDrawerID = request.getParameter("DrawerID");
   String DrawerIDType =  request.getParameter("DrawerIDType");
   String CurrentDate = PubFun.getCurrentDate();
   
   LJFIGetSchema tLJFIGetSchema ; //���������      
   LJAGetSchema  tLJAGetSchema  ; //ʵ���ܱ�
   LJAGetSet     tLJAGetSet     ;

// ׼���������� VData
   VData tVData = new VData();
   tLJAGetSchema = new LJAGetSchema();
   tLJAGetSchema.setActuGetNo(ActuGetNo);
   tVData.clear();
   tVData.add(tLJAGetSchema);
   tVData.add(tGI);
loggerDebug("FinFeePaySave","Start ��ѯʵ���ܱ�");
   //LJAGetQueryUI tLJAGetQueryUI = new LJAGetQueryUI();
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   
   if(!tBusinessDelegate.submitData(tVData,"QUERY","LJAGetQueryUI"))
   {
       Content = " ��ѯʵ���ܱ�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
       FlagStr = "Fail";
   }  
   else
   { //��һ������
    tVData.clear();    
    tLJAGetSet = new LJAGetSet();
    tLJAGetSchema = new LJAGetSchema();     
    tVData = tBusinessDelegate.getResult();
    tLJAGetSet.set((LJAGetSet)tVData.getObjectByObjectName("LJAGetSet",0));  
    tLJAGetSchema =(LJAGetSchema)tLJAGetSet.get(1);
loggerDebug("FinFeePaySave","����ʵ���ܱ�Schema");
    //tLJAGetSchema.setSumGetMoney(Double.parseDouble(GetMoney)+tLJAGetSchema.getSumGetMoney());
    tLJAGetSchema.setEnterAccDate(EnterAccDate);
    tLJAGetSchema.setConfDate(CurrentDate);
    tLJAGetSchema.setDrawer(Drawer);
    tLJAGetSchema.setDrawerID(DrawerID);
    tLJAGetSchema.setDrawerType(DrawerIDType);
    //tLJAGetSchema.setOperator(Operator);
    tLJAGetSchema.setActualDrawer(ActualDrawer);
    tLJAGetSchema.setActualDrawerID(ActualDrawerID);
    //if(PayMode=="4")
    //{
    //   tLJAGetSchema.setEnterAccDate("");
   //    tLJAGetSchema.setConfDate("");
  //     tLJAGetSchema.setBankCode(DFBankCode);
  //     tLJAGetSchema.setBankAccNo(DFBankAccNo);
  //     tLJAGetSchema.setAccName(DFAccName);
  //  }
   // else
  //  {
      tLJAGetSchema.setPayMode(PayMode);
      tLJAGetSchema.setBankCode(BankCode);
      tLJAGetSchema.setBankAccNo(BankAccNo);
      tLJAGetSchema.setAccName(AccName);        
   // }

//2-��������������¼�¼
    tLJFIGetSchema = new LJFIGetSchema();

    tLJFIGetSchema.setEnterAccDate(EnterAccDate);
    //tLJFIGetSchema.setConfDate(CurrentDate);    
    tLJFIGetSchema.setActuGetNo(ActuGetNo);
    tLJFIGetSchema.setPayMode(PayMode);
    tLJFIGetSchema.setOtherNo(tLJAGetSchema.getOtherNo());
    tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
tLJFIGetSchema.setCurrency(Currency);
    tLJFIGetSchema.setGetMoney(GetMoney);
    tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
    tLJFIGetSchema.setBankCode(BankCode);       
    tLJFIGetSchema.setBankAccNo(BankAccNo);
    tLJFIGetSchema.setAccName(AccName);
    tLJFIGetSchema.setInBankCode(BankCode);
    tLJFIGetSchema.setInBankAccNo(BankAccNo);
    tLJFIGetSchema.setInAccName(AccName);     
    tLJFIGetSchema.setChequeNo(ChequeNo); 
    tLJFIGetSchema.setSaleChnl(tLJAGetSchema.getSaleChnl());
  //  tLJFIGetSchema.setAgentGroup(AgentGroup);
   // tLJFIGetSchema.setAgentCode(AgentCode);
    tLJFIGetSchema.setSerialNo(tLJAGetSchema.getSerialNo());
   // tLJFIGetSchema.setOperator(Operator);

       
//���ʱ�����BL������
//3-������� �����¼�����������  ����ʵ���ܱ�
loggerDebug("FinFeePaySave","start �������");
   tVData.clear();
   tVData.add(tLJFIGetSchema);
   tVData.add(tLJAGetSchema);   
   tVData.add(tGI);
   //OperFinFeeGetUI tOperFinFeeGetUI = new OperFinFeeGetUI();
   
   tBusinessDelegate.submitData(tVData,"VERIFY","OperFinFeeGetUI");
   tError = tBusinessDelegate.getCErrors();   
      if (tError.needDealError())
      {                          
     	Content = " ����ʧ�ܣ�ԭ��:" + tError.getFirstError();
     	FlagStr = "Fail";
      }  
   else
    { // �ڶ�������                      
      Content = " �����ɹ�";
      FlagStr = "Succ";   
%>
<script language="javascript">
	parent.fraInterface.initForm();
</script>
<%    
    } //��Ӧ�ڶ�������     
   } // ��Ӧ��һ������
 } //ҳ����Ч��    
%> 
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML>    
