<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�InfoFinItemAssociatedSave.jsp
//�����ܣ���Ŀ����ר��屣��ҳ��
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
  FIInfoFinItemAssociatedSchema tFIInfoFinItemAssociatedSchema = new FIInfoFinItemAssociatedSchema();
  //FIInfoFinItemAssociatedUI tFIInfoFinItemAssociated = new FIInfoFinItemAssociatedUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String mAssociatedID = "";
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  tFIInfoFinItemAssociatedSchema.setVersionNo(request.getParameter("VersionNo"));
	tFIInfoFinItemAssociatedSchema.setFinItemID(request.getParameter("FinItemID"));  
  tFIInfoFinItemAssociatedSchema.setAssociatedID(request.getParameter("AssociatedID"));
  tFIInfoFinItemAssociatedSchema.setAssociatedName(request.getParameter("AssociatedName"));
  tFIInfoFinItemAssociatedSchema.setReMark(request.getParameter("ReMark"));

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFIInfoFinItemAssociatedSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("InfoFinItemAssociatedSave","come========== in"+tFIInfoFinItemAssociatedSchema.getVersionNo());
    loggerDebug("InfoFinItemAssociatedSave","come========== in"+tFIInfoFinItemAssociatedSchema.getFinItemID());  
    loggerDebug("InfoFinItemAssociatedSave","come========== in"+tFIInfoFinItemAssociatedSchema.getAssociatedID());
    loggerDebug("InfoFinItemAssociatedSave","come========== in"+tFIInfoFinItemAssociatedSchema.getAssociatedName());
    uiBusinessDelegate.submitData(tVData,tOperate,"FIInfoFinItemAssociatedUI");
    loggerDebug("InfoFinItemAssociatedSave","come out" + tOperate);
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
		mAssociatedID = (String)sTransferData.getValueByName("String");
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

