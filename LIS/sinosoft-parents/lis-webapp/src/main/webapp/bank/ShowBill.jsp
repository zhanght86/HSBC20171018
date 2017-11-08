<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ShowBill.jsp
//程序功能：
//创建人  ：刘岩松
//创建日期：
//更新记录：  
//更新人
//更新日期
//更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>

<%
  //输出参数
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  
	//定义要查询的表的结构
	String strStartDate = request.getParameter("StartDate"); 
  String strEndDate = request.getParameter("EndDate");
  String strBankCode = request.getParameter("BankCode");
  String strFlag = request.getParameter("Flag");
	String strTFFlag = request.getParameter("TFFlag");
	String strXQFlag = request.getParameter("SXFlag");
  GlobalInput tG = new GlobalInput();	
	tG=(GlobalInput)session.getValue("GI");
 	loggerDebug("ShowBill","您输入的开始日期是"+strStartDate);
 	loggerDebug("ShowBill","您输入的结束日期是"+strEndDate);
 	loggerDebug("ShowBill","银行代码是"+strBankCode);
 	loggerDebug("ShowBill","代收代付标志是操作标志是"+strFlag);
 	loggerDebug("ShowBill","正确错误的标志是"+strTFFlag);
 	loggerDebug("ShowBill","首续期的标志是"+strXQFlag);
 	loggerDebug("ShowBill","SHOW");
 	
 	
 	
  VData tVData = new VData();
  tVData.addElement(strStartDate);
  tVData.addElement(strEndDate);
  tVData.addElement(strBankCode);
  tVData.addElement(strFlag);
  tVData.addElement(strTFFlag);
  tVData.addElement(strXQFlag);
	tVData.addElement(tG);
	NewShowBillUI mNewShowBillUI = new NewShowBillUI();

	if (!mNewShowBillUI.submitData(tVData,"SHOW"))
	{
      Content = " 查询失败，原因是: " + mNewShowBillUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = mNewShowBillUI.getResult();
		
		// 显示
		LYBankLogSet tLYBankLogSet = new LYBankLogSet();
		tLYBankLogSet.set((LYBankLogSet)tVData.getObjectByObjectName("LYBankLogSet",0));
		int n = tLYBankLogSet.size();
		loggerDebug("ShowBill","get report "+n);
		if(n==0)
		{
			Content = " 查询失败，原因是:没有符合条件的数据" ;
      FlagStr = "Fail";
		}
		else
		{
		String Strtest = "0|" + n + "^" + tLYBankLogSet.encode();
		loggerDebug("ShowBill","QueryResult: " + Strtest);
		%>
				<script language="javascript">
		   	 try 
		   	 {
		   	   parent.fraInterface.displayQueryResult('<%=Strtest%>');
		   	 }
		   	 catch(ex) {}		   	
		   	</script>
		<%
		for (int i = 1; i <= n; i++)
		{
		  	LYBankLogSchema tLYBankLogSchema = tLYBankLogSet.get(i);
		  	
		} 
	} 
  }
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail")
  {
    tError = mNewShowBillUI.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " 查询成功! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " 查询失败，原因是: 没有符合条件的数据信息";
    	FlagStr = "Fail";
    }
  }
loggerDebug("ShowBill","------end------");
loggerDebug("ShowBill",FlagStr);
loggerDebug("ShowBill",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
