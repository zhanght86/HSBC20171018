<%
//程序名称：PEdorTypeRFSubmit.jsp
//程序功能：
//创建日期：2005-07-09
//创建人  ：Nicholas
//修改时间：2005-07
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %>
<html>
<script language="javascript">
<%
  //个人批改信息
  TransferData tTransferData = new TransferData();
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
  //后面要执行的动作：
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  String transact = "";
  int nIndex;
  transact = "INSERT||MAIN";
 
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
  //个人保单批改信息
    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 

	tTransferData.setNameAndValue("PayOffFlag", request.getParameter("PayOffFlag"));
		
	LOLoanSet tLOLoanSet = new LOLoanSet();
	String tChk[] = request.getParameterValues("InpLoanGridChk"); 
	String tEdorNo[] = request.getParameterValues("LoanGrid1"); //贷款批单号
    String tLoanDate[] = request.getParameterValues("LoanGrid2"); //贷款日期
	String tPayOffDate[] = request.getParameterValues("LoanGrid3"); //还清日期
    String tCurSumLoan[] = request.getParameterValues("LoanGrid4"); //当前贷款
    String tCurSumLoanIntrest[] = request.getParameterValues("LoanGrid5"); //当前贷款利息
    String tShoudLoanMoeny[] = request.getParameterValues("LoanGrid7"); //本次还款
    String tPolNo[] = request.getParameterValues("LoanGrid8"); //主险保单号
    String tCurrency[] = request.getParameterValues("LoanGrid9"); //贷款次数
    System.out.println(""+tChk.length);
    for(nIndex = 0; nIndex < tChk.length; nIndex++ ) 
    {
      if( !tChk[nIndex].equals("1") ) 
      {
        continue;
      }   
      LOLoanSchema tLOLoanSchema = new LOLoanSchema();
      
      tLOLoanSchema.setContNo(request.getParameter("ContNo"));
      tLOLoanSchema.setPolNo(tPolNo[nIndex]);
      tLOLoanSchema.setCurrency(tCurrency[nIndex]);
      tLOLoanSchema.setEdorNo(tEdorNo[nIndex]);
      if (tShoudLoanMoeny[nIndex] != null)
      {

    	tLOLoanSchema.setSumMoney(tShoudLoanMoeny[nIndex]);   	
    	//存放截止本次借款的利息
    	tLOLoanSchema.setPreInterest(tCurSumLoanIntrest[nIndex]);
    	//存放借款余额
    	tLOLoanSchema.setLeaveMoney(Double.parseDouble(tCurSumLoanIntrest[nIndex])+Double.parseDouble(tCurSumLoan[nIndex])-Double.parseDouble(tShoudLoanMoeny[nIndex]));
        tLOLoanSet.add(tLOLoanSchema);
      }  

    }
    try 
     {
    // 准备传输数据 VData
    VData tVData = new VData();  
  	
    //保存个人保单信息(保全)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
		 tVData.add(tLOLoanSet);
		 tVData.add(tTransferData);
    
//     boolean tag = tEdorDetailUI.submitData(tVData,transact);
     boolean tag = tBusinessDelegate.submitData(tVData,transact,busiName);
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
  		FlagStr = "Success";                  
      Content = "保存成功！";
    } 
    else  
    {
  		Content = " 保存失败，原因是:" + tError.getFirstError();
  		FlagStr = "Fail";
  	}
}
%>                      
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
