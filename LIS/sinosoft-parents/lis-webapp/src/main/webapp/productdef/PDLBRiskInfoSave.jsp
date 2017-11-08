<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：
//程序功能：
//创建日期：2009-09-10
//创建人  ：sunyu
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*" %>

<%
  String FlagStr = "";
  String Content = "";
      	 
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("RiskCode", request.getParameter("RiskCode"));
  tTransferData.setNameAndValue("StartDate", request.getParameter("StartDate"));
  tTransferData.setNameAndValue("EndDate", request.getParameter("EndDate"));
  tTransferData.setNameAndValue("batch", request.getParameter("batch"));
  
  System.out.println(request.getParameter("batch"));
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
   
  //调用PDLBRiskInfoBL方法 ，返回一个ListTable ListTable中方的就是需要展现在页面上的信息
  PDLBRiskInfoBL tPDLBRiskInfoBL = new PDLBRiskInfoBL();
  
  ListTable tTableList =  tPDLBRiskInfoBL.submitData(tVData, "ModifyQuery");
  
  //赋值
  int tTableListSize = tTableList.size();
  
  //删除原来数据
  %>
  <script type="text/javascript">
 	parent.fraInterface.Mulline9Grid.clearData();
   </script>
 <%
  for(int i = 0; i < tTableListSize; i ++){
	  String[] stringArray = tTableList.get(i);
	  
	  //添加一行
	  %>
	  <script type="text/javascript">
	 	parent.fraInterface.Mulline9Grid.addOne();
	   </script>
	 <%
	 
	  int stringArrayLength = stringArray.length;
	  
	  for(int j = 0; j < stringArrayLength; j ++){
		  //添加一个数据
		  %>
		  <script type="text/javascript">
		 	parent.fraInterface.Mulline9Grid.setRowColData("<%=i%>", "<%=j + 1%>", "<%=stringArray[j]%>");
		   </script>
		 <%
	  }
  }
 
 FlagStr = "True";
 Content = ""+"查询修改信息成功！"+"";
 %>
 
 
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 