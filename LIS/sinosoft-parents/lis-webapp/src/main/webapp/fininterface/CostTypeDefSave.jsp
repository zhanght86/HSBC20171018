<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CostTypeDefSave.jsp
//�����ܣ�ƾ֤�������Ͷ��屣��ҳ��
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
  FICostTypeDefSchema tFICostTypeDefSchema = new FICostTypeDefSchema();
  //FICostTypeDefUI tFICostTypeDef = new FICostTypeDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String mCostID = "";
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  tFICostTypeDefSchema.setVersionNo(request.getParameter("VersionNo"));
	tFICostTypeDefSchema.setCertificateID(request.getParameter("CertificateID"));  
  tFICostTypeDefSchema.setCostID(request.getParameter("CostID"));
  tFICostTypeDefSchema.setCostName(request.getParameter("CostName"));
  tFICostTypeDefSchema.setFinItemType(request.getParameter("FinItemType"));
  tFICostTypeDefSchema.setFinItemID(request.getParameter("FinItemID"));  
  tFICostTypeDefSchema.setFinItemName(request.getParameter("FinItemName"));
  tFICostTypeDefSchema.setRemark(request.getParameter("Remark"));
  tFICostTypeDefSchema.setState(request.getParameter("State"));

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFICostTypeDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("CostTypeDefSave","come========== in"+tFICostTypeDefSchema.getVersionNo());
    loggerDebug("CostTypeDefSave","come========== in"+tFICostTypeDefSchema.getCertificateID());  
    loggerDebug("CostTypeDefSave","come========== in"+tFICostTypeDefSchema.getCostID());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FICostTypeDefUI");
    loggerDebug("CostTypeDefSave","come out" + tOperate);
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
		mCostID = (String)sTransferData.getValueByName("String");
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

