<%
//�������� :CostDataBaseDefInput.jsp
//������ :ƾ֤��������Դ����
//������ :���
//�������� :2008-08-18
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
  loggerDebug("CostDataBaseDefSave","1��ʼִ��Saveҳ��");
  FICostDataBaseDefSchema mFICostDataBaseDefSchema = new FICostDataBaseDefSchema();
  //FICostDataBaseDefUI mFICostDataBaseDefUI = new FICostDataBaseDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("CostDataBaseDefSave","2������������" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//��������־��Ӣ��ת���ɺ��ֵ���ʽ

  loggerDebug("CostDataBaseDefSave","3��ʼ���л�ȡ���ݵĲ���������");
  mFICostDataBaseDefSchema.setVersionNo(request.getParameter("VersionNo"));
  mFICostDataBaseDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  mFICostDataBaseDefSchema.setDataBaseID(request.getParameter("DataBaseID"));
  mFICostDataBaseDefSchema.setDataBaseName(request.getParameter("DataBaseName"));
  mFICostDataBaseDefSchema.setDataBaseOrder(request.getParameter("DataBaseOrder"));
  mFICostDataBaseDefSchema.setRemark(request.getParameter("Remark")); 
  loggerDebug("CostDataBaseDefSave","4");
  
  if(mOperateType.equals("INSERT||MAIN"))
  {
    mType = "����";
  }
  if(mOperateType.equals("DELETE||MAIN"))
  {
    mType = "ɾ��";
  }
  loggerDebug("CostDataBaseDefSave","5");
  VData tVData = new VData();
  try
  {
  	tVData.add(tG);
    tVData.addElement(mFICostDataBaseDefSchema);
    uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataBaseDefUI");
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
