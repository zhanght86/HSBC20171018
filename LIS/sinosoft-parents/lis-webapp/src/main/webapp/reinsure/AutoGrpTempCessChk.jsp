<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ApplyRecallChk.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.reinsure.*"%>
  <%@page import="com.sinosoft.lis.reinsure.calRule.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	  //输出参数
	  CErrors tError = null;
	  String FlagStr = "Fail";
		String Content="";
		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getAttribute("GI");
		
		String tOpeFlag 	= request.getParameter("OpeFlag"); 
		String tGrpContNo	= request.getParameter("OpeData"); 
		
		System.out.println("OpeFlag: "	+tOpeFlag);
		System.out.println("GrpContNo: "+tGrpContNo);
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OpeFlag",tOpeFlag);
		tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
		
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG);
		System.out.println(" 开始团单临分核保。。。。。。。。。。。。"); 
		// 数据传输 
		String result= "" ;
		GrpTempAutoUW tGrpTempAutoUW = new GrpTempAutoUW(); 
		if (tGrpTempAutoUW.submitData(tVData,"") == false){ 
			Content = " "+"自核失败，原因是:"+" " + tGrpTempAutoUW.mErrors.getError(0).errorMessage; 
			FlagStr = "Fail";
		}
	  else{
	  	result=tGrpTempAutoUW.getResult();
		  Content = " "+"自核成功!"+" ";
		  FlagStr = "Succ";
		}
%> 
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>");
</script>
</html>

