<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：UWCustomerQualitySave.jsp
//程序功能：
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：    更新人      更新日期      更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%
  loggerDebug("UWHospitalQualitySave","\n\n---UWCustomerQualitySave Start---");
  loggerDebug("UWHospitalQualitySave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("UWHospitalQualitySave","ContNo:" + request.getParameter("ContNo"));
//  loggerDebug("UWHospitalQualitySave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LCHospitalQualityRecordSchema tLCHospitalQualityRecordSchema = new LCHospitalQualityRecordSchema();
  

  String tContNo = request.getParameter("ContNo");
  String tHospitalCode =request.getParameter("HospitalCode");
  String tQualityFlag =request.getParameter("QualityFlag");
  String tRemark =request.getParameter("Remark");
  //体检医院评分
  String tCheckedItem1 = request.getParameter("CheckedItem1");
  //管理岗评分
  String tCheckedItem2 = request.getParameter("CheckedItem2");
  //内勤岗评分
  String tCheckedItem3 = request.getParameter("CheckedItem3");
  
  tLCHospitalQualityRecordSchema.setContNo(tContNo);
  tLCHospitalQualityRecordSchema.setHospitCode(tHospitalCode);
  tLCHospitalQualityRecordSchema.setQualityFlag(tQualityFlag);
  tLCHospitalQualityRecordSchema.setRemark(tRemark);
  tLCHospitalQualityRecordSchema.setHospitalQuality(tCheckedItem1);
  tLCHospitalQualityRecordSchema.setManagerQuality(tCheckedItem2);
  tLCHospitalQualityRecordSchema.setInsideQuality(tCheckedItem3);
 
  VData inVData = new VData();
  inVData.add(tLCHospitalQualityRecordSchema);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  /*UWHospitalQualityRecordUI tUWHospitalQualityRecordUI = new UWHospitalQualityRecordUI();

  if (!tUWHospitalQualityRecordUI.submitData(inVData, "INSERT")) {
    VData rVData = tUWHospitalQualityRecordUI.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }*/
  String busiName="UWHospitalQualityRecordUI";
  String mDescType="处理";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(inVData,"INSERT",busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"失败";
				FlagStr = "Fail";				
		   }
	  }
	  else
	  {
	     	Content = mDescType+"成功! ";
	      	FlagStr = "Succ";  
	  }

	loggerDebug("UWHospitalQualitySave",Content + "\n" + FlagStr + "\n---UWCustomerQualitySave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');

</script>
</html>

