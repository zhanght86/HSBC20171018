<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AssociatedItemDefSave.jsp
//�����ܣ���Ŀר��屣��ҳ��
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
  FIAssociatedItemDefSchema tFIAssociatedItemDefSchema = new FIAssociatedItemDefSchema();
  //FIAssociatedItemDefUI tFIAssociatedItemDef = new FIAssociatedItemDefUI();
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
  tFIAssociatedItemDefSchema.setVersionNo(request.getParameter("VersionNo"));
  tFIAssociatedItemDefSchema.setAssociatedID(request.getParameter("AssociatedID"));
  tFIAssociatedItemDefSchema.setAssociatedName(request.getParameter("AssociatedName"));
  tFIAssociatedItemDefSchema.setColumnID(request.getParameter("ColumnID"));
  tFIAssociatedItemDefSchema.setSourceTableID(request.getParameter("SourceTableID"));
  tFIAssociatedItemDefSchema.setSourceColumnID(request.getParameter("SourceColumnID"));  
  tFIAssociatedItemDefSchema.setTransFlag(request.getParameter("TransFlag"));
  tFIAssociatedItemDefSchema.setTransSQL(request.getParameter("TransSQL"));
  tFIAssociatedItemDefSchema.setTransClass(request.getParameter("TransClass"));
  tFIAssociatedItemDefSchema.setReMark(request.getParameter("ReMark"));  

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFIAssociatedItemDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("AssociatedItemDefSave","come========== in"+tFIAssociatedItemDefSchema.getVersionNo());    
    loggerDebug("AssociatedItemDefSave","come========== in"+tFIAssociatedItemDefSchema.getAssociatedID());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FIAssociatedItemDefUI");
    loggerDebug("AssociatedItemDefSave","come out" + tOperate);
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

