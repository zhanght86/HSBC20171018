 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AssociatedDirectItemDefSave.jsp
//�����ܣ���Ŀר��屣��ҳ��
//�������ڣ�2008-09-16
//������  ��FanXin
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
  FIAssociatedDirectItemDefSchema tFIAssociatedDirectItemDefSchema = new FIAssociatedDirectItemDefSchema();
  //FIAssociatedDirectItemDefUI tFIAssociatedDirectItemDef = new FIAssociatedDirectItemDefUI();
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
  tFIAssociatedDirectItemDefSchema.setVersionNo(request.getParameter("VersionNo"));
  tFIAssociatedDirectItemDefSchema.setColumnID(request.getParameter("ColumnID"));
  tFIAssociatedDirectItemDefSchema.setSourceTableID(request.getParameter("SourceTableID"));
  tFIAssociatedDirectItemDefSchema.setSourceColumnID(request.getParameter("SourceColumnID"));  
  tFIAssociatedDirectItemDefSchema.setReMark(request.getParameter("ReMark"));  

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFIAssociatedDirectItemDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("AssociatedDirectItemDefSave","come========== in"+tFIAssociatedDirectItemDefSchema.getVersionNo());    
    loggerDebug("AssociatedDirectItemDefSave","come========== in"+tFIAssociatedDirectItemDefSchema.getColumnID());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FIAssociatedDirectItemDefUI");
    loggerDebug("AssociatedDirectItemDefSave","come out" + tOperate);
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

