 <%
//程序名称：FinFeePaySave.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//         
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
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
// 输出参数
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("FinFeePaySave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
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
   
   LJFIGetSchema tLJFIGetSchema ; //财务给付表      
   LJAGetSchema  tLJAGetSchema  ; //实付总表
   LJAGetSet     tLJAGetSet     ;

// 准备传输数据 VData
   VData tVData = new VData();
   tLJAGetSchema = new LJAGetSchema();
   tLJAGetSchema.setActuGetNo(ActuGetNo);
   tVData.clear();
   tVData.add(tLJAGetSchema);
   tVData.add(tGI);
loggerDebug("FinFeePaySave","Start 查询实付总表");
   //LJAGetQueryUI tLJAGetQueryUI = new LJAGetQueryUI();
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   
   if(!tBusinessDelegate.submitData(tVData,"QUERY","LJAGetQueryUI"))
   {
       Content = " 查询实付总表失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
       FlagStr = "Fail";
   }  
   else
   { //第一个括号
    tVData.clear();    
    tLJAGetSet = new LJAGetSet();
    tLJAGetSchema = new LJAGetSchema();     
    tVData = tBusinessDelegate.getResult();
    tLJAGetSet.set((LJAGetSet)tVData.getObjectByObjectName("LJAGetSet",0));  
    tLJAGetSchema =(LJAGetSchema)tLJAGetSet.get(1);
loggerDebug("FinFeePaySave","更新实付总表Schema");
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

//2-构造财务给付表的新纪录
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

       
//入机时间等在BL层设置
//3-事务操作 插入纪录到财务给付表  更新实付总表
loggerDebug("FinFeePaySave","start 事务操作");
   tVData.clear();
   tVData.add(tLJFIGetSchema);
   tVData.add(tLJAGetSchema);   
   tVData.add(tGI);
   //OperFinFeeGetUI tOperFinFeeGetUI = new OperFinFeeGetUI();
   
   tBusinessDelegate.submitData(tVData,"VERIFY","OperFinFeeGetUI");
   tError = tBusinessDelegate.getCErrors();   
      if (tError.needDealError())
      {                          
     	Content = " 保存失败，原因:" + tError.getFirstError();
     	FlagStr = "Fail";
      }  
   else
    { // 第二个括号                      
      Content = " 操作成功";
      FlagStr = "Succ";   
%>
<script language="javascript">
	parent.fraInterface.initForm();
</script>
<%    
    } //对应第二个括号     
   } // 对应第一个括号
 } //页面有效区    
%> 
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML>    
