<%
//�������� :FIRulePlanDefSave.jsp
//������ :У��ƻ�����
//������ :���
//�������� :2008-09-17
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
  <%@page import="com.sinosoft.lis.fininterface.checkdata.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("FIRulePlanDefSave","1��ʼִ��Saveҳ��");
  FIRulePlanDefSchema mFIRulePlanDefSchema = new FIRulePlanDefSchema();
  //FIRulePlanDefUI mFIRulePlanDefUI = new FIRulePlanDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("FIRulePlanDefSave","2������������" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";
  String tRulesPlanID = "";

  loggerDebug("FIRulePlanDefSave","3��ʼ���л�ȡ���ݵĲ���������");
  if(mOperateType.equals("INSERT||MAIN"))
  {
  	tRulesPlanID = PubFun1.CreateMaxNo("RulesPlanID",8);
 	 	loggerDebug("FIRulePlanDefSave","��ˮ����ҵ��У��ƻ����" + tRulesPlanID);
  	mFIRulePlanDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRulePlanDefSchema.setRulesPlanID(tRulesPlanID);
  	mFIRulePlanDefSchema.setRulesPlanName(request.getParameter("RulesPlanName"));
  	mFIRulePlanDefSchema.setRulePlanState(request.getParameter("RulePlanState"));
  	mFIRulePlanDefSchema.setMarkInfo(request.getParameter("MarkInfo"));
  	mFIRulePlanDefSchema.setSequence(request.getParameter("Sequence"));
  	mFIRulePlanDefSchema.setCallPointID(request.getParameter("CallPointID")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulePlanDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRulePlanDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
  	mFIRulePlanDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRulePlanDefSchema.setRulesPlanID(request.getParameter("RulesPlanID"));
  	mFIRulePlanDefSchema.setRulesPlanName(request.getParameter("RulesPlanName"));
  	mFIRulePlanDefSchema.setRulePlanState(request.getParameter("RulePlanState"));
  	mFIRulePlanDefSchema.setMarkInfo(request.getParameter("MarkInfo"));
  	mFIRulePlanDefSchema.setSequence(request.getParameter("Sequence"));
  	mFIRulePlanDefSchema.setCallPointID(request.getParameter("CallPointID")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulePlanDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRulePlanDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  
  if(mOperateType.equals("DELETE||MAIN"))
  {
  	mFIRulePlanDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRulePlanDefSchema.setRulesPlanID(request.getParameter("RulesPlanID"));
  	mFIRulePlanDefSchema.setRulesPlanName(request.getParameter("RulesPlanName"));
  	mFIRulePlanDefSchema.setRulePlanState(request.getParameter("RulePlanState"));
  	mFIRulePlanDefSchema.setMarkInfo(request.getParameter("MarkInfo"));
  	mFIRulePlanDefSchema.setSequence(request.getParameter("Sequence"));
  	mFIRulePlanDefSchema.setCallPointID(request.getParameter("CallPointID")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRulePlanDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRulePlanDefUI");
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
</script>
</html>
