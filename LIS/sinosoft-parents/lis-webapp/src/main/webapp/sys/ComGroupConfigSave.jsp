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
  //��Ҫ�����̨��������󼯺ϣ�

  String Content = "";    //������
  String Flag="";  
  String tAction = request.getParameter("hideaction");
 
  String tOtherSQL = "";
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
  //��Ҫ�������Ϣ
  LDComGroupSet tLDComGroupSet = new LDComGroupSet();
  LDComToComGroupSet tLDComToComGroupSet = new LDComToComGroupSet();
  //�������
  if(tAction.equals("INSERT"))
  {
	//������Ϣ
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
	  //��������
	  String tComGroupCode = request.getParameter("ComGroupCode");
	  String tBatchNo = request.getParameter("BatchNo");
	  
	  //ѭ�������
	  String tGridNo[] = request.getParameterValues("ComGroupMapGridNo");  //�õ�����е�����ֵ
      String tGrid1 [] = request.getParameterValues("ComGroupMapGrid1"); //�õ���1�е�����ֵ
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
	  //�����㷨�洢
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
  Content = "����ɹ�";
   loggerDebug("ComGroupConfigSave","33333333333333");
  if(!tComGroupConfigUI.submitData(tVData,tAction))
  {
    loggerDebug("ComGroupConfigSave","���ݴ������");
    Flag="Fail";
    Content = " ����ʧ�ܣ�ԭ����:" +tComGroupConfigUI.mErrors.getFirstError();
  }
   loggerDebug("ComGroupConfigSave",Content + "\n" + Flag + "\n---���ݴ������ End---\n\n");
%>
 
<html>
<script language="javascript"> 
   parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
</script>
</html> 
  
 
