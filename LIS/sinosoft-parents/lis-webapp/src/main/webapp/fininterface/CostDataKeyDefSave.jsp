<%
//�������� :CostDataKeyDefInput.jsp
//������ :ƾ֤����������������
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
  loggerDebug("CostDataKeyDefSave","1��ʼִ��Saveҳ��");
  FICostDataKeyDefSchema mFICostDataKeyDefSchema = new FICostDataKeyDefSchema();
  //FICostDataKeyDefUI mFICostDataKeyDefUI = new FICostDataKeyDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  mOperateType = mOperateType.trim();
  loggerDebug("CostDataKeyDefSave","2������������" +  mOperateType);
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mType = "";//��������־��Ӣ��ת���ɺ��ֵ���ʽ

  loggerDebug("CostDataKeyDefSave","3��ʼ���л�ȡ���ݵĲ���������");
  mFICostDataKeyDefSchema.setVersionNo(request.getParameter("VersionNo"));
  mFICostDataKeyDefSchema.setAcquisitionID(request.getParameter("AcquisitionID"));
  mFICostDataKeyDefSchema.setKeyID(request.getParameter("KeyID"));
  mFICostDataKeyDefSchema.setKeyName(request.getParameter("KeyName"));
  mFICostDataKeyDefSchema.setKeyOrder(request.getParameter("KeyOrder"));
  mFICostDataKeyDefSchema.setRemark(request.getParameter("Remark")); 
  loggerDebug("CostDataKeyDefSave","4");
  
  if(mOperateType.equals("INSERT||MAIN"))
  {
    mType = "����";
  }
  if(mOperateType.equals("DELETE||MAIN"))
  {
    mType = "ɾ��";
  }
  loggerDebug("CostDataKeyDefSave","5");
  VData tVData = new VData();
  try
  {
  	tVData.add(tG);
    tVData.addElement(mFICostDataKeyDefSchema);
    uiBusinessDelegate.submitData(tVData,mOperateType,"FICostDataKeyDefUI");
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
