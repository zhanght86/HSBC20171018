<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CertificateTypeDefSave.jsp
//�����ܣ�ƾ֤���Ͷ��屣��ҳ��
//�������ڣ�2008-08-12
//������  ��ZhongYan
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //������Ϣ������У�鴦��
  //�������
  FICertificateTypeDefSchema tFICertificateTypeDefSchema = new FICertificateTypeDefSchema();
  //FICertificateTypeDefUI tFICertificateTypeDef = new FICertificateTypeDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String mCertificateID = "";
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  tFICertificateTypeDefSchema.setVersionNo(request.getParameter("VersionNo"));
  tFICertificateTypeDefSchema.setCertificateID(request.getParameter("CertificateID"));
  tFICertificateTypeDefSchema.setCertificateName(request.getParameter("CertificateName"));
  tFICertificateTypeDefSchema.setDetailIndexID(request.getParameter("DetailIndexID"));
  tFICertificateTypeDefSchema.setDetailIndexName(request.getParameter("DetailIndexName"));
  tFICertificateTypeDefSchema.setAcquisitionType(request.getParameter("AcquisitionType"));
  tFICertificateTypeDefSchema.setRemark(request.getParameter("Remark"));

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFICertificateTypeDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("CertificateTypeDefSave","come========== in"+tFICertificateTypeDefSchema.getVersionNo());  
    loggerDebug("CertificateTypeDefSave","come========== in"+tFICertificateTypeDefSchema.getCertificateID());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FICertificateTypeDefUI");
    loggerDebug("CertificateTypeDefSave","come out" + tOperate);
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  if (!FlagStr.equals("Fail"))
  {
    tError = uiBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	TransferData sTransferData = (TransferData)uiBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
		mCertificateID = (String)sTransferData.getValueByName("String");
    	Content = " �����ѳɹ�! ";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }

  //��Ӹ���Ԥ����

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>

