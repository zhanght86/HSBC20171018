
<%
//�������ƣ�LLReportInput.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("CertifyDescUpdateSave","��ʼִ��Saveҳ��");
  LMCertifyDesSchema mLMCertifyDesSchema = new LMCertifyDesSchema();
  LMCertifyDesSet mLMCertifyDesSet = new LMCertifyDesSet();
  LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();
  //CertifyDescUI mCertifyDescUI = new CertifyDescUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  loggerDebug("CertifyDescUpdateSave","������������"+mOperateType);
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  
  loggerDebug("CertifyDescUpdateSave","��ʼ���л�ȡ���ݵĲ���������");
  mLMCertifyDesSchema.setCertifyCode(request.getParameter("CertifyCode"));
  mLMCertifyDesSchema.setCertifyName(request.getParameter("CertifyName"));
  mLMCertifyDesSchema.setState(request.getParameter("State"));
  
  mLMCertifyDesSchema.setHaveLimit(request.getParameter("HaveLimit"));
  mLMCertifyDesSchema.setMaxUnit1(request.getParameter("MaxUnit1"));
  mLMCertifyDesSchema.setMaxUnit2(request.getParameter("MaxUnit2"));
  
  mLMCertifyDesSchema.setHaveValidate(request.getParameter("HaveValidate"));
  mLMCertifyDesSchema.setValidate1(request.getParameter("Validate1"));
  mLMCertifyDesSchema.setValidate2(request.getParameter("Validate2"));
  mLMCertifyDesSchema.setNote(request.getParameter("Note"));
  
  mLMCertifyDesSet.add(mLMCertifyDesSchema);

  if(mOperateType.equals("INSERT"))
  {
    mDescType = "����";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = "�޸�";
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = "ɾ��";
  }
  if(mOperateType.equals("QUERY"))
  {
    mDescType = "��ѯ";
  }

  String mCertifyClass="";
  VData tVData = new VData();

    tVData.addElement(mOperateType);
    tVData.addElement(mCertifyClass);
    tVData.addElement(tG);
    tVData.addElement(mLMCertifyDesSet);
    
    //mCertifyDescUI.submitData(tVData,mOperateType);

  String busiName="CertifyDescUI";
  if(!tBusinessDelegate.submitData(tVData,mOperateType,busiName))
  {    
       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
       { 
			Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
	   }
	   else
	   {
			Content = mDescType+"ʧ��";
			FlagStr = "Fail";				
	   }
  }
  else
  {
     	Content = mDescType+"�ɹ�! ";
      	FlagStr = "Succ";  
  }

%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
