<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�FinItemDefSave.jsp
//�����ܣ���Ŀ���Ͷ��屣��ҳ��
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
  FIFinItemDefSchema tFIFinItemDefSchema = new FIFinItemDefSchema();
  //FIFinItemDefUI tFIFinItemDef = new FIFinItemDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String mFinItemID = "";
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  tFIFinItemDefSchema.setVersionNo(request.getParameter("VersionNo"));
  tFIFinItemDefSchema.setFinItemID(request.getParameter("FinItemID"));
  tFIFinItemDefSchema.setFinItemName(request.getParameter("FinItemName"));
  tFIFinItemDefSchema.setFinItemType(request.getParameter("FinItemType"));
  tFIFinItemDefSchema.setItemMainCode(request.getParameter("ItemMainCode"));
  tFIFinItemDefSchema.setDealMode(request.getParameter("DealMode"));
  tFIFinItemDefSchema.setReMark(request.getParameter("ReMark"));

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFIFinItemDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("FinItemDefSave","come========== in"+tFIFinItemDefSchema.getFinItemID());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FIFinItemDefUI");
    loggerDebug("FinItemDefSave","come out" + tOperate);
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
		mFinItemID = (String)sTransferData.getValueByName("String");
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

