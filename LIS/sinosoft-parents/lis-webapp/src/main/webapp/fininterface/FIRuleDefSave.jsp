<%
//�������� :FIRuleDefSave.jsp
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
  loggerDebug("FIRuleDefSave","1��ʼִ��Saveҳ��");
  FIRuleDefSchema mFIRuleDefSchema = new FIRuleDefSchema();
  //FIRuleDefUI mFIRuleDefUI = new FIRuleDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("FIRuleDefSave","2������������" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";
  String tRulesID = "";

  loggerDebug("FIRuleDefSave","3��ʼ���л�ȡ���ݵĲ���������");
  if(mOperateType.equals("INSERT||MAIN"))
  {
  	tRulesID = PubFun1.CreateMaxNo("RulesPlanID",8);
 	 	loggerDebug("FIRuleDefSave","��ˮ����ҵ��У�������" + tRulesID);
  	mFIRuleDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRuleDefSchema.setRuleID(tRulesID);
  	mFIRuleDefSchema.setRuleName(request.getParameter("RuleName"));
  	mFIRuleDefSchema.setRuleDealMode(request.getParameter("RuleDealMode"));
  	mFIRuleDefSchema.setRuleDealClass(request.getParameter("RuleDealClass"));
  	mFIRuleDefSchema.setRuleDealSQL(request.getParameter("RuleDealSQL"));
  	mFIRuleDefSchema.setRuleReturnMean(request.getParameter("RuleReturnMean")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRuleDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRuleDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
  	mFIRuleDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRuleDefSchema.setRuleID(request.getParameter("RuleID"));
  	mFIRuleDefSchema.setRuleName(request.getParameter("RuleName"));
  	mFIRuleDefSchema.setRuleDealMode(request.getParameter("RuleDealMode"));
  	mFIRuleDefSchema.setRuleDealClass(request.getParameter("RuleDealClass"));
  	mFIRuleDefSchema.setRuleDealSQL(request.getParameter("RuleDealSQL"));
  	mFIRuleDefSchema.setRuleReturnMean(request.getParameter("RuleReturnMean")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRuleDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRuleDefUI");
  	}
  	catch(Exception ex)
  	{
    	Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
  	}
  }
  
  if(mOperateType.equals("DELETE||MAIN"))
  {
  	mFIRuleDefSchema.setVersionNo(request.getParameter("VersionNo"));
  	mFIRuleDefSchema.setRuleID(request.getParameter("RuleID"));
  	mFIRuleDefSchema.setRuleName(request.getParameter("RuleName"));
  	mFIRuleDefSchema.setRuleDealMode(request.getParameter("RuleDealMode"));
  	mFIRuleDefSchema.setRuleDealClass(request.getParameter("RuleDealClass"));
  	mFIRuleDefSchema.setRuleDealSQL(request.getParameter("RuleDealSQL"));
  	mFIRuleDefSchema.setRuleReturnMean(request.getParameter("RuleReturnMean")); 
  	VData tVData = new VData();
  	try
  	{
  		tVData.add(tG);
  		tVData.addElement(mOperateType);
    	tVData.addElement(mFIRuleDefSchema);
    	uiBusinessDelegate.submitData(tVData,mOperateType,"FIRuleDefUI");
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
