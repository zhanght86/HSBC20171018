<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�DetailFinItemDefSave.jsp
//�����ܣ���ϸ��Ŀ�ж��������屣��ҳ��
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
  FIDetailFinItemDefSchema tFIDetailFinItemDefSchema = new FIDetailFinItemDefSchema();
  //FIDetailFinItemDefUI tFIDetailFinItemDef = new FIDetailFinItemDefUI();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String mJudgementNo = "";
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  tFIDetailFinItemDefSchema.setVersionNo(request.getParameter("VersionNo"));
	tFIDetailFinItemDefSchema.setFinItemID(request.getParameter("FinItemID"));  
  tFIDetailFinItemDefSchema.setJudgementNo(request.getParameter("JudgementNo"));
  tFIDetailFinItemDefSchema.setLevelCondition(request.getParameter("LevelCondition"));
  tFIDetailFinItemDefSchema.setFirstMark(request.getParameter("FirstMark"));
  tFIDetailFinItemDefSchema.setReMark(request.getParameter("ReMark"));

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFIDetailFinItemDefSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("DetailFinItemDefSave","come========== in"+tFIDetailFinItemDefSchema.getVersionNo());
    loggerDebug("DetailFinItemDefSave","come========== in"+tFIDetailFinItemDefSchema.getFinItemID());  
    loggerDebug("DetailFinItemDefSave","come========== in"+tFIDetailFinItemDefSchema.getJudgementNo());    
    uiBusinessDelegate.submitData(tVData,tOperate,"FIDetailFinItemDefUI");
    loggerDebug("DetailFinItemDefSave","come out" + tOperate);
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
		mJudgementNo = (String)sTransferData.getValueByName("String");
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

