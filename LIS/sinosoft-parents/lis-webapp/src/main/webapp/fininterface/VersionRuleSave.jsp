<%
//�������� :VersionRuleSave.jsp
//������ :�������汾����
//������ :���
//�������� :2008-08-21
//
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("VersionRuleSave","��ʼִ��Saveҳ��");
  FIRulesVersionSchema mFIRulesVersionSchema = new FIRulesVersionSchema();
  FIRulesVersionTraceSchema mFIRulesVersionTraceSchema = new FIRulesVersionTraceSchema();
  //FIVersionRuleUI mFIVersionRuleUI = new FIVersionRuleUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("VersionRuleSave","������������" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//��������־��Ӣ��ת���ɺ��ֵ���ʽ
  loggerDebug("VersionRuleSave",mOperateType);
  String tVersionNo = "";
  
  loggerDebug("VersionRuleSave","3��ʼ���л�ȡ���ݵĲ���������");
  if (mOperateType.equals("addVersion"))
  {
  	tVersionNo = PubFun1.CreateMaxNo("VersionNo",20);
  	loggerDebug("VersionRuleSave","��ˮ����ҵ���汾���" + tVersionNo);
		mFIRulesVersionSchema.setVersionNo(tVersionNo);
		mFIRulesVersionSchema.setVersionState(request.getParameter("VersionState"));
  	mFIRulesVersionSchema.setStartDate(request.getParameter("StartDate"));
  	mFIRulesVersionSchema.setEndDate(request.getParameter("EndDate"));
  	mFIRulesVersionSchema.setVersionReMark(request.getParameter("VersionReMark"));
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulesVersionSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  
	else if (mOperateType.equals("deleteVersion"))
	{
		mFIRulesVersionSchema.setVersionNo(request.getParameter("VersionNo"));
		mFIRulesVersionSchema.setVersionState(request.getParameter("VersionState"));
  	mFIRulesVersionSchema.setStartDate(request.getParameter("StartDate"));
  	mFIRulesVersionSchema.setEndDate(request.getParameter("EndDate"));
  	mFIRulesVersionSchema.setVersionReMark(request.getParameter("VersionReMark"));
  	loggerDebug("VersionRuleSave",mFIRulesVersionSchema.getVersionNo());
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulesVersionSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
  	}  	
	}
  else if (mOperateType.equals("applyAmend"))
  	{
			mFIRulesVersionTraceSchema.setMaintenanceno(request.getParameter("Maintenanceno"));
			mFIRulesVersionTraceSchema.setVersionNo(request.getParameter("VersionNo"));
			mFIRulesVersionTraceSchema.setMaintenanceState(request.getParameter("MaintenanceState"));
			mFIRulesVersionTraceSchema.setMaintenanceReMark(request.getParameter("MaintenanceReMark"));
			mFIRulesVersionSchema.setVersionNo(request.getParameter("VersionNo"));
			mFIRulesVersionSchema.setVersionState(request.getParameter("VersionState"));
  		mFIRulesVersionSchema.setStartDate(request.getParameter("StartDate"));
  		mFIRulesVersionSchema.setEndDate(request.getParameter("EndDate"));
  		mFIRulesVersionSchema.setVersionReMark(request.getParameter("VersionReMark"));
			VData tVData = new VData();
  		try
  		{
  			tVData.add(tG);
  			tVData.addElement(mOperateType);
  			tVData.addElement(mFIRulesVersionSchema);
    		tVData.addElement(mFIRulesVersionTraceSchema);
    		uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  		}
  		catch(Exception ex)
  		{
    		Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    		FlagStr = "Fail";
  		}
  	}
	else if (mOperateType.equals("CompleteAmend"))
		{
			mFIRulesVersionTraceSchema.setMaintenanceno(request.getParameter("Maintenanceno"));
			mFIRulesVersionTraceSchema.setVersionNo(request.getParameter("TraceVersionNo"));
			mFIRulesVersionTraceSchema.setMaintenanceState(request.getParameter("MaintenanceState"));
			mFIRulesVersionTraceSchema.setMaintenanceReMark(request.getParameter("MaintenanceReMark"));
  		loggerDebug("VersionRuleSave",mFIRulesVersionTraceSchema.getMaintenanceno());
  		VData tVData = new VData();
  		try
  		{
  			tVData.add(tG);
  			tVData.addElement(mOperateType);
    		tVData.addElement(mFIRulesVersionTraceSchema);
    		uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  		}
  		catch(Exception ex)
  		{
    		Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    		FlagStr = "Fail";
  		}  	
		}
	else if (mOperateType.equals("cancelAmend"))
		{
			mFIRulesVersionTraceSchema.setMaintenanceno(request.getParameter("Maintenanceno"));
			mFIRulesVersionTraceSchema.setVersionNo(request.getParameter("TraceVersionNo"));
			mFIRulesVersionTraceSchema.setMaintenanceState(request.getParameter("MaintenanceState"));
			mFIRulesVersionTraceSchema.setMaintenanceReMark(request.getParameter("MaintenanceReMark"));
  		loggerDebug("VersionRuleSave",mFIRulesVersionTraceSchema.getMaintenanceno());
  		VData tVData = new VData();
  		try
  		{
  			tVData.add(tG);
  			tVData.addElement(mOperateType);
    		tVData.addElement(mFIRulesVersionTraceSchema);
    		uiBusinessDelegate.submitData(tVData,mOperateType,"FIVersionRuleUI");
  		}
  		catch(Exception ex)
  		{
    		Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    		FlagStr = "Fail";
  		}  	
		}
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
     tError = uiBusinessDelegate.getCErrors();
    	   if (!tError.needDealError())
    		{     
    				Content = "�����ѳɹ�!";
    				FlagStr = "Succ";
    		}
   	  else
    		{
    			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    			FlagStr = "Fail";
    		}
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.fm.TraceVersionNo.value = "<%=tVersionNo%>";
</script>
</html>
