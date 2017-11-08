<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%> 
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%
  loggerDebug("ComGroupConfigSave","start ComGroupConfigUI.jsp ");
  //需要传入后台参数（最大集合）

  String Content = "";    //处理结果
  String Flag="";  
  String tAction = request.getParameter("hideaction");
 
  String tOtherSQL = "";
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
  //需要保存的信息
  LDComGroupSet tLDComGroupSet = new LDComGroupSet();
  LDComToComGroupSet tLDComToComGroupSet = new LDComToComGroupSet();
  //保存操作
  if(tAction.equals("INSERT"))
  {
	//机构信息
	String tComGroupCode = request.getParameter("ComGroupCode");
 	String tComGroupName = request.getParameter("ComGroupName");
  	String tGroupInfo = request.getParameter("GroupInfo");
  	String tBatchNo = request.getParameter("BatchNo");
   	loggerDebug("ComGroupConfigSave","begin deal Insert..");
   	LDComGroupSchema tLDComGroupSchema = new LDComGroupSchema();
   	tLDComGroupSchema.setComGroup(tComGroupCode);
   	tLDComGroupSchema.setComGroupName(tComGroupName);
   	tLDComGroupSchema.setRemark(tGroupInfo);
   	tLDComGroupSchema.setBatchNo(tBatchNo);
   	tLDComGroupSet.add(tLDComGroupSchema);
  }
  else if(tAction.equals("INSERTSUB"))
  {
	  //正常保存
	  String tComGroupCode = request.getParameter("ComGroupCode");
	  String tBatchNo = request.getParameter("BatchNo");
	  
	  //循环结果集
	  String tGridNo[] = request.getParameterValues("ComGroupMapGridNo");  //得到序号列的所有值
      String tGrid1 [] = request.getParameterValues("ComGroupMapGrid1"); //得到第1列的所有值
	  for(int i=0;i<tGridNo.length;i++)
	  {
		  String tComCode = tGrid1[i];
		  if(tComCode==null||tComCode.equals(""))
		  {
			  
		  }
		  LDComToComGroupSchema tLDComToComGroupSchema = new LDComToComGroupSchema();
		  tLDComToComGroupSchema.setComGroup(tComGroupCode);
		  tLDComToComGroupSchema.setComCode(tComCode);
		  tLDComToComGroupSchema.setBatchNo(tBatchNo);
		  tLDComToComGroupSet.add(tLDComToComGroupSchema);
	  }
  }
  else if(tAction.equals("OTHERSAVE"))
  {
	  //扩充算法存储
	  String tSQL = "";
	  String tSQL1 =  request.getParameter("WorkDetail");
	  String tSQL2 =  request.getParameter("OtherCondition");
	  tSQL = tSQL1 + tSQL2;
	  String tComGroupCode = request.getParameter("ComGroupCode");
	  String tComGroupName = request.getParameter("ComGroupName");
	  String tGroupInfo = request.getParameter("GroupInfo");
	  String tBatchNo = request.getParameter("BatchNo");
	  loggerDebug("ComGroupConfigSave","begin deal Insert..");
	  LDComGroupSchema tLDComGroupSchema = new LDComGroupSchema();
	  tLDComGroupSchema.setComGroup(tComGroupCode);
	  tLDComGroupSchema.setComGroupName(tComGroupName);
	  tLDComGroupSchema.setRemark(tGroupInfo);
	  tLDComGroupSchema.setBatchNo(tBatchNo);
	  tLDComGroupSchema.setCalSQL(tSQL);
	  tLDComGroupSet.add(tLDComGroupSchema);
  }
  VData tVData = new VData();
  VData mResult = new VData();
  tVData.add(tGI);
  tVData.add(tLDComGroupSet);
  tVData.add(tLDComToComGroupSet);
  ComGroupConfigUI tComGroupConfigUI = new ComGroupConfigUI();
  Flag="Succ";
  Content = "保存成功";
   loggerDebug("ComGroupConfigSave","33333333333333");
  if(!tComGroupConfigUI.submitData(tVData,tAction))
  {
    loggerDebug("ComGroupConfigSave","数据处理错误");
    Flag="Fail";
    Content = " 处理失败，原因是:" +tComGroupConfigUI.mErrors.getFirstError();
  }
   loggerDebug("ComGroupConfigSave",Content + "\n" + Flag + "\n---数据处理完毕 End---\n\n");
%>
 
<html>
<script language="javascript"> 
   parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
</script>
</html> 
  
 
