 <%
//�������� :FIRulePlanDefDetailSave.jsp
//������ :ƾ֤��������Դ����
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
  loggerDebug("FIRulePlanDefDetailSave","1��ʼִ��Saveҳ��");
  FIRulePlanDefDetailSchema mFIRulePlanDefDetailSchema = new FIRulePlanDefDetailSchema();
  //FIRulePlanDefDetailUI mFIRulePlanDefDetailUI = new FIRulePlanDefDetailUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("FIRulePlanDefDetailSave","2������������" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//��������־��Ӣ��ת���ɺ��ֵ���ʽ

  loggerDebug("FIRulePlanDefDetailSave","3��ʼ���л�ȡ���ݵĲ���������");
  mFIRulePlanDefDetailSchema.setVersionNo(request.getParameter("VersionNo"));
  mFIRulePlanDefDetailSchema.setRulePlanID(request.getParameter("RulePlanID"));
  mFIRulePlanDefDetailSchema.setRuleID(request.getParameter("RuleID"));
  mFIRulePlanDefDetailSchema.setSequence(request.getParameter("Sequence"));
  mFIRulePlanDefDetailSchema.setRuleState(request.getParameter("RuleState"));
  loggerDebug("FIRulePlanDefDetailSave","4");
  
  if(mOperateType.equals("INSERT||MAIN"))
  {
    mType = "����";
  }
  if(mOperateType.equals("DELETE||MAIN"))
  {
    mType = "ɾ��";
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
    mType = "�޸�";
  }
  loggerDebug("FIRulePlanDefDetailSave","5");
  VData tVData = new VData();
  try
  {
  	tVData.add(tG);
    tVData.addElement(mFIRulePlanDefDetailSchema);
    uiBusinessDelegate.submitData(tVData,mOperateType,"FIRulePlanDefDetailUI");
  }
  catch(Exception ex)
  {
    Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
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
