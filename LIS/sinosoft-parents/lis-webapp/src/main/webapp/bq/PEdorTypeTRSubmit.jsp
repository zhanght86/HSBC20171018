<%
//程序名称：PEdorTypeTRSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Minim
//修改人  ：Nicholas

//修改时间：2005-07
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
  //个人批改信息
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
  LCPolSchema tLCPolSchema = new LCPolSchema();
  LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
  LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
  //后面要执行的动作：添加，修改
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String Result = "";
  String loanResult = "";
  
  //transact = request.getParameter("fmtransact");
  transact = "INSERT||MAIN";
  //System.out.println("---transact: " + transact);  
  
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
  //个人保单批改信息
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	tLPEdorItemSchema.setStandbyFlag1(request.getParameter("SReLoan")); 
	tLPEdorItemSchema.setStandbyFlag2(request.getParameter("AReLoan")); 
	
  
  //获得用户选择的领取信息，程序将在表LJSGetDraw删除这些信息对应的记录
  String tLoanDate[] =request.getParameterValues("LoanGrid1"); //垫交日期
  String tPreSumMoney[] =request.getParameterValues("LoanGrid2"); //垫交保费
  String tPreInterest[] =request.getParameterValues("LoanGrid3"); //垫交利息
  String tSumMoney[] =request.getParameterValues("LoanGrid4"); //垫交保费
  String tInterest[] =request.getParameterValues("LoanGrid5"); //垫交利息
  String tLoanNo[] =request.getParameterValues("LoanGrid6"); //原批单号
  String tPolNo[] =request.getParameterValues("LoanGrid7"); //险种号
  String tCurrency[] =request.getParameterValues("LoanGrid8"); //险种号
  String tChk[] = request.getParameterValues("InpLoanGridChk");

	try{
	  for (int i=0;i<tChk.length;i++)
	  { 
	     if (tChk[i].equals("1"))
        {	
    	tLPReturnLoanSchema = new LPReturnLoanSchema();
    	tLPReturnLoanSchema.setContNo(request.getParameter("ContNo"));
    	tLPReturnLoanSchema.setPolNo(tPolNo[i]);
    	tLPReturnLoanSchema.setLoanDate(tLoanDate[i]);

    	//本次还款的本息和
    	tLPReturnLoanSchema.setLeaveMoney(Double.parseDouble(tInterest[i])+Double.parseDouble(tSumMoney[i]));
    	//还款本金
    	tLPReturnLoanSchema.setReturnMoney(Double.parseDouble(tSumMoney[i]));
    	//还款利息
    	tLPReturnLoanSchema.setReturnInterest(tInterest[i]);
    	tLPReturnLoanSchema.setLoanNo(tLoanNo[i]);
    	tLPReturnLoanSchema.setCurrency(tCurrency[i]);
    	
        tLPReturnLoanSet.add(tLPReturnLoanSchema);
        }
	  }
  }
  catch (Exception e)
	{
		System.out.println(e.toString());
	}

  //总还款金额
  //Double tempDouble = new Double(request.getParameter("AllShouldPay"));

  try 
  {
    // 准备传输数据 VData
    VData tVData = new VData();  
  	
    //保存个人保单信息(保全)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
	   tVData.add(tLPReturnLoanSet);    
//     tEdorDetailUI.submitData(tVData,transact);
     tBusinessDelegate.submitData(tVData, transact ,busiName);

  } 
  catch(Exception ex) 
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "") 
  {
//  	tError = tEdorDetailUI.mErrors;
  	tError = tBusinessDelegate.getCErrors(); 
  	if (!tError.needDealError()) 
  	{                          
      Content = "保存成功！";
  		FlagStr = "Success";


    } 
    else  
    {
  		Content = " 保存失败，原因是:" + tError.getFirstError();
  		FlagStr = "Fail";
  	}
	}
	
%>   
                   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=Result%>", "<%=loanResult%>");
</script>
</html>

