<%@include file="../i18n/language.jsp"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDSugIncomeDataAlgSave.jsp
//程序功能：收益算法定义
//创建日期：2011-10-24
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.sinosoft.service.*" %>  
<%@page import="org.codehaus.xfire.addressing.RandomGUID" %>  
  
<%
 //接收信息，并作校验处理。
 //输入参数

 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 String tRiskCode=null;
 
 TransferData transferData = new TransferData();
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 tRiskCode=request.getParameter("RiskCode");
 
 
 String tTYPE =  request.getParameter("TYPE");
 String tSIGN =  request.getParameter("TYPEName");
 String tSTARTDATE =  request.getParameter("STARTDATE");
 String tENDDATE =  request.getParameter("ENDDATE");
 String tRATE =  request.getParameter("RATE");
 String tCURRENCY =  request.getParameter("CURRENCY");

 transferData.setNameAndValue("TYPE",tTYPE);
 transferData.setNameAndValue("SIGN",tSIGN);
 transferData.setNameAndValue("STARTDATE",tSTARTDATE);
 transferData.setNameAndValue("ENDDATE",tENDDATE);
 transferData.setNameAndValue("RATE",tRATE);
 transferData.setNameAndValue("CURRENCY",tCURRENCY);
 transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));


 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDSugRateBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    Content = " "+"处理失败，原因是:"+"" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"处理成功!"+" ";
  	  FlagStr = "Succ";

  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
	 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


