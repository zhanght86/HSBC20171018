<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDIssueQuerySave.jsp
//程序功能：问题件录入
//创建日期：2009-4-3
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>    
<%
 //接收信息，并作校验处理。
 //输入参数
 

 //PDIssueQueryBL pDIssueQueryBL = new PDIssueQueryBL();
 
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

 transferData.setNameAndValue("SerialNo",request.getParameter("SerialNo"));

 transferData.setNameAndValue("ReplyCont",request.getParameter("ReplyContDesc"));

 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();


  tVData.add(tG);

  tVData.add(transferData);

String busiName="PDIssueQueryBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
	  VData rVData = tBusinessDelegate.getResult();

    Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
  	FlagStr = "Fail";
  }
  else {
    Content = " "+"处理成功!"+" ";
  	FlagStr = "Succ";
  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();

  FlagStr = "Fail";
 }
  //pDIssueQueryBL.submitData(tVData,operator);
 

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

