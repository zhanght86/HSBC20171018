<%
//程序名称：PEdorTypePCSubmit.jsp
//程序功能：
//创建日期：2005-5-8 10:47上午
//创建人  ：Lihs
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %>

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  System.out.println("-----PCsubmit---");
  
  LPContSchema tLPContSchema   = new LPContSchema(); 
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  LPAccountSchema tLPAccountSchema = new LPAccountSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  CErrors tError = null;
  //后面要执行的动作：添加，修改
 
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result="";
  
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
  System.out.println("------transact:"+transact);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
  
  //个人保单批改信息
  
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
     // 准备传输数据 VData
  
  	 VData tVData = new VData();  
  	
	 //保存个人保单信息(保全)	
      tVData.addElement(tG);
      tVData.addElement(tLPEdorItemSchema);
      tVData.addElement(tLPContSchema);
      tVData.addElement(tLPAccountSchema);
//      boolean tag = tEdorDetailUI.submitData(tVData,transact); 
      boolean tag = tBusinessDelegate.submitData(tVData,transact,busiName);    
   } catch(Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
	 }			
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr=="") {
  		System.out.println("------success");
//    	tError = tEdorDetailUI.mErrors;
    	tError = tBusinessDelegate.getCErrors(); 
    	if (!tError.needDealError()) {                          
        Content = " 保存成功";
    		FlagStr = "Success";  
    	 }else{
    		Content = " 保存失败，原因是:" + tError.getFirstError();
    		FlagStr = "Fail";
    	 }
  	}
%>                      
<html>
<script language="javascript">
   
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

