 <%
//程序名称：IndiFinVerifyUrgeGet.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>  
  
<%@page contentType="text/html;charset=gb2312" %>

<%

   GlobalInput tGI = new GlobalInput(); //repair:
   tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp

   String FlagStr = "";
   String Content = "";

   VData inputVData = new VData();     
   String StartDate = request.getParameter("StartDate");
   String EndDate   = request.getParameter("EndDate");
   
   TransferData tTransferData=new TransferData();
   tTransferData.setNameAndValue("StartDate",StartDate);
   tTransferData.setNameAndValue("EndDate",EndDate);
   
   inputVData.add(tTransferData);
   inputVData.add(tGI);
   
   PrepareAutoDueFeeMultiBL tPrepareAutoDueFeeMultiBL = new PrepareAutoDueFeeMultiBL();
   tPrepareAutoDueFeeMultiBL.submitData(inputVData,"");
   if(tPrepareAutoDueFeeMultiBL.mErrors.needDealError()==true)
   {
   		FlagStr="Fail";
   		Content="原因是："+tPrepareAutoDueFeeMultiBL.mErrors.getFirstError();
   }
   else
   {   
	   	VData tVData = tPrepareAutoDueFeeMultiBL.getResult();
	   	tTransferData=(TransferData)tVData.getObjectByObjectName("TransferData",0);
		String recordCount=(String)tTransferData.getValueByName("recordCount");
		String succNum=(String)tTransferData.getValueByName("succNum");
		String failNum=(String)tTransferData.getValueByName("failNum");
		String errContent=(String)tTransferData.getValueByName("errContent"); 
		FlagStr="Succ";     		
		Content="共查询到"+recordCount+"个纪录，成功处理"+succNum+"个纪录，失败"+failNum+"个纪录";
		Content=Content+"--错误原因:"+errContent;
   }
%>

<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML> 