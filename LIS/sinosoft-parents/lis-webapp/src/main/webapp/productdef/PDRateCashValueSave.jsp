<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDRateCashValueSave.jsp
//程序功能：数据表和现金价值定制
//创建日期：2009-3-17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
 //接收信息，并作校验处理。
 //输入参数
 
 
 System.out.println("test:" + request.getParameter("testfm2"));

 PDRateCashValueBL pDRateCashValueBL = new PDRateCashValueBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 
 String MullineName = "";
 if(operator.equals("createRateTable"))
 {
	 MullineName = "Mulline10Grid3";
 }
 else if(operator.equals("deleteRateTable"))
 {
	 
 }
 else if(operator.equals("createCashValue"))
 {
	 MullineName = "Mulline13Grid3";
 }

 if(!MullineName.equals(""))
 {
 String values[] = request.getParameterValues(MullineName);
 
 java.util.ArrayList list = new java.util.ArrayList();
 for(int i = 0; i < values.length; i++)
 {
  list.add(values[i]);
 }
 transferData.setNameAndValue("list",list);
 }
  
transferData.setNameAndValue("tableName",request.getParameter("tableName"));
transferData.setNameAndValue("newTableName",request.getParameter("newTableName"));
transferData.setNameAndValue("PremDataTypeName",request.getParameter("PremDataTypeName"));
transferData.setNameAndValue("CashValueDataTypeName",request.getParameter("CashValueDataTypeName"));
transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
transferData.setNameAndValue("PayCode",request.getParameter("PayCode"));
transferData.setNameAndValue("DataTBLName",request.getParameter("DataTBLName"));
//transferData.setNameAndValue("CoreTableName",request.getParameter("CoreTableName"));

 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  pDRateCashValueBL.submitData(tVData,operator);
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = pDRateCashValueBL.mErrors;
  if (!tError.needDealError())
  {                          
   if(operator.equals("deleteRateTable"))
   {
	   MullineName = "Mulline10Grid3";
	   String tvle[] = request.getParameterValues(MullineName);
	   if(tvle.length ==0)
	   {
		   RiskState.setState(request.getParameter("RiskCode"),""+"产品条款定义"+"->"+"数据表定义"+"","0");
	   }
   }
   else
   {
	   RiskState.setState(request.getParameter("RiskCode"),""+"产品条款定义"+"->"+"数据表定义"+"","1");
   }
   Content = " "+"保存成功!"+" ";
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

