<%
//�������ƣ�AccountantPeriodSave.jsp
//�����ܣ�����ڼ����
//�������ڣ�2008-08-04	
//������  �����
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
  loggerDebug("AccountantPeriodSave","1��ʼִ��Saveҳ��");
  FIPeriodManagementSchema mFIPeriodManagementSchema = new FIPeriodManagementSchema();
  //FIPeriodManagementUI mFIPeriodManagementUI = new FIPeriodManagementUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
	String mmResult = "";
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("AccountantPeriodSave","2������������" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//��������־��Ӣ��ת���ɺ��ֵ���ʽ

  loggerDebug("AccountantPeriodSave","3��ʼ���л�ȡ���ݵĲ���������");
  mFIPeriodManagementSchema.setyear(request.getParameter("Year"));
  mFIPeriodManagementSchema.setmonth(request.getParameter("Month"));
  mFIPeriodManagementSchema.setstartdate(request.getParameter("StartDay"));
  mFIPeriodManagementSchema.setenddate(request.getParameter("EndDay"));
  mFIPeriodManagementSchema.setstate(request.getParameter("State"));  
  
  
  if(mOperateType.equals("INSERT||MAIN"))
  {
    mType = "����";
  }
  if(mOperateType.equals("UPDATE||MAIN"))
  {
    mType = "�޸�";
  }
  if(mOperateType.equals("DELETE||MAIN"))
  {
    mType = "ɾ��";
  }
  if(mOperateType.equals("QUERY||MAIN"))
  {
    mType = "��ѯ";
  }
  
  VData tVData = new VData();
  try
  {
  	tVData.add(tG);
    tVData.addElement(mFIPeriodManagementSchema);
    loggerDebug("AccountantPeriodSave","5tVData.addElement(mFIPeriodManagementSchema);");
    tBusinessDelegate.submitData(tVData,mOperateType,"FIPeriodManagementUI");
  }
  catch(Exception ex)
  {
    Content = mType+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
     tError = tBusinessDelegate.getCErrors();
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
