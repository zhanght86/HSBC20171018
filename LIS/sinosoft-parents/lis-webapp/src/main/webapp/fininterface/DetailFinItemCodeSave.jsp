<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�DetailFinItemCodeSave.jsp
//�����ܣ���ϸ��Ŀ��֧Ӱ�䶨�屣��ҳ��
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
  FIDetailFinItemCodeSchema tFIDetailFinItemCodeSchema = new FIDetailFinItemCodeSchema();
  //FIDetailFinItemCodeUI tFIDetailFinItemCode = new FIDetailFinItemCodeUI();
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
  tFIDetailFinItemCodeSchema.setVersionNo(request.getParameter("VersionNo"));
	tFIDetailFinItemCodeSchema.setFinItemID(request.getParameter("FinItemID"));  
  tFIDetailFinItemCodeSchema.setJudgementNo(request.getParameter("JudgementNo"));
  tFIDetailFinItemCodeSchema.setLevelConditionValue(request.getParameter("LevelConditionValue"));
  tFIDetailFinItemCodeSchema.setLevelCode(request.getParameter("LevelCode"));
  tFIDetailFinItemCodeSchema.setNextJudgementNo(request.getParameter("NextJudgementNo"));
  tFIDetailFinItemCodeSchema.setReMark(request.getParameter("ReMark"));

  // ׼���������� VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tFIDetailFinItemCodeSchema);
	tVData.add(tG);
  try
  {
    loggerDebug("DetailFinItemCodeSave","come========== in"+tFIDetailFinItemCodeSchema.getVersionNo());
    loggerDebug("DetailFinItemCodeSave","come========== in"+tFIDetailFinItemCodeSchema.getFinItemID());  
    loggerDebug("DetailFinItemCodeSave","come========== in"+tFIDetailFinItemCodeSchema.getJudgementNo()); 
    loggerDebug("DetailFinItemCodeSave","come========== in"+tFIDetailFinItemCodeSchema.getLevelConditionValue());        
    uiBusinessDelegate.submitData(tVData,tOperate,"FIDetailFinItemCodeUI");
    loggerDebug("DetailFinItemCodeSave","come out" + tOperate);
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

