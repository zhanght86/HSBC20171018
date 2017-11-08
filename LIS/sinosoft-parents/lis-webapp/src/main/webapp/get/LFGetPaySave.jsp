<%
//程序名称：LJSGetDrawInput.jsp
//程序功能：
//创建日期：2002-07-19 11:48:25
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.get.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //接收信息，并作校验处理。



  CErrors tError = new CErrors();
  String Content="";
  String FlagStr="";
  String tPrtSeq = "";
  String transact = request.getParameter("fmtransact");
  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
 
if("GETMONEY".equals(transact)){
	
  String tGrpContNo = request.getParameter("GrpContNoBak");
  LFGetPayNewBL tLFGetPayNewBL = new LFGetPayNewBL();
  try
  {
	  tLFGetPayNewBL.setGlobalInput(tGlobalInput);
	  tLFGetPayNewBL.setAppName(request.getParameter("LFAppName"));
	  tLFGetPayNewBL.dealLFGet(tGrpContNo);
	  tPrtSeq = tLFGetPayNewBL.getPrtSeq();
      tError = tLFGetPayNewBL.mErrors;
      }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
}else if("UPDATEPAYMODE".equals(transact)){
	String tContNo = request.getParameter("ContNo");
	
	String tPayMode = request.getParameter("PayMode"); //给付方式 对应BNF的Remark
	String tDrawer = request.getParameter("Drawer"); //领取人姓名,对应BNF的Name
	String tIDNo =  request.getParameter("DrawerIDNo"); //领取人的身份证号,对应BNF的IDNo
	String tBankCode = request.getParameter("BankCode"); //领取人的开户银行编码,对应BNF的BankCode
	String tBankAccNo = request.getParameter("BankAccNo"); //领取人的开户银行账户,对应BNF的BankAccNo
	String tAccName = request.getParameter("AccName"); //领取人的开户银行户名,对应BNF的AccName
	if(tBankCode==null){
		tBankCode = "";
	}
	if(tBankAccNo==null){
		tBankAccNo = "";
	}
	if(tAccName==null){
		tAccName = "";
	}
	String tSql = "Update LjsGet set "
				+ "PayMode = '"+tPayMode+"', Drawer = '"+tDrawer+"', "
				+ "BankAccNo = '"+tBankAccNo+"', AccName = '"+tAccName+"' ,"
				+ "DrawerID = '"+tIDNo+"', BankCode = '"+tBankCode+"' Where OtherNo = '"+tContNo+"' and OtherNoType = '2' and GetDate <= now()";
	 MMap tMap = new MMap();
     tMap.put(tSql, "UPDATE");
     VData tVData = new VData();
     tVData.add(tMap);
     //数据提交
     PubSubmit tSubmit = new PubSubmit();
     if (!tSubmit.submitData(tVData, "")) {
     	Content = " 保存失败，原因是:" + tSubmit.mErrors.getFirstError();
     	FlagStr = "Fail";   	
     }
}
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
 {
 	
   if (!tError.needDealError())
   {
     Content = " 操作成功";
   	 FlagStr = "Succ";
    }
   else
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  //添加各种预处理
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tPrtSeq%>");
</script>
</html>

