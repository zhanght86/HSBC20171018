<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GetSendToBankSave.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="java.text.*"%>
  <%@page import="java.util.*"%>
  <%@page import="com.sinosoft.service.*"%>
  
<%!String checkDate(String start, String end){
	if(PubFun.calInterval(start, end, "D")<0){
		return "����ӦС��ֹ�ڣ�";
	}
	if(PubFun.calInterval(end,PubFun.calDate(PubFun.getCurrentDate(),7,"D",null), "D")<0){
		if(PubFun.calInterval("2010-02-01",PubFun.getCurrentDate(),"D")>=0 && PubFun.calInterval(PubFun.getCurrentDate(),"2010-02-12","D")>=0){
			if(PubFun.calInterval(end, "2010-02-19", "D")<0){
				return "2��1����2��12���ڼ��������ֹ���ڲ��ܲٹ�2��19��";
			}		
		}else{
			return "ֹ������ƺ����죡";
		}
	}
	return null;
}
%>
<% 
  String Content = "";
  String FlagStr = "";
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");


  //�Զ�����
  LCPolSchema  tLCPolSchema = new LCPolSchema();  // ���˱�����
  String startDate = request.getParameter("StartDate");
  String endDate = request.getParameter("EndDate");
  loggerDebug("GetSendToBankSave","StartDate:"+startDate);
  String  r=checkDate(startDate, endDate);
  if (r==null) {
      loggerDebug("GetSendToBankSave","StartDate:"+startDate);
  tLCPolSchema.setGetStartDate(PubFun.calDate(PubFun.getCurrentDate(),-60,"D",null)); //���ݾ�������Ҫ��
  tLCPolSchema.setPayEndDate(PubFun.getCurrentDate());

 
 //��TransferData�����̨��������  zhanghui 2005.2.18
 TransferData transferData2 = new TransferData();
 String bankCode=request.getParameter("BankCode");
 transferData2.setNameAndValue("bankCode", bankCode);
 
 
//tongmeng 2011-01-24 add
//����Լֱ�Ӵ����ջ�ȡ����,������Ӧ��
/*
  VData tVData2 = new VData();
  tVData2.add(tLCPolSchema);
  tVData2.add(tGlobalInput);
  tVData2.add(transferData2); //by zhanghui
  //NewIndiDueFeeMultiUI tNewIndiDueFeeMultiUI = new NewIndiDueFeeMultiUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	String key = "SendS" + bankCode;
	PubLock tPubLock = new PubLock();
	if (!tPubLock.lock(key, "׼��" + bankCode + "���շ�����������")) {
		 Content=tPubLock.mErrors.getErrContent();
	}else{
		try{
		 // tNewIndiDueFeeMultiUI.submitData(tVData2, "INSERT");
		  tBusinessDelegate.submitData(tVData2,"INSERT","NewIndiDueFeeMultiUI");
		  //if (tNewIndiDueFeeMultiUI.mErrors.needDealError()) {
		   if (tBusinessDelegate.getCErrors().needDealError()) {
		    Content="���մ���ʧ�ܣ������������з������ݣ�ԭ����:";
		    
		    //for(int n=0;n<tNewIndiDueFeeMultiUI.mErrors.getErrorCount();n++) {       
		    // Content=Content+tNewIndiDueFeeMultiUI.mErrors.getError(n).errorMessage;
		    for(int n=0;n<tBusinessDelegate.getCErrors().getErrorCount();n++) {       
		     Content=Content+tBusinessDelegate.getCErrors().getError(n).errorMessage;
		     Content=Content+"|";
		     loggerDebug("GetSendToBankSave",Content);
		    }
		    FlagStr="Fail";
		  }
		  else { 
		    loggerDebug("GetSendToBankSave","���մ���ɹ���");
		  }
		}finally{
			if(!tPubLock.unLock(key))
				loggerDebug("GetSendToBankSave",bankCode + "���շ����������ݽ���ʧ��");
		}
	}
  */
  //������������
  loggerDebug("GetSendToBankSave","\n\n---GetSendToBankSave Start---");
  //GetSendToBankUI getSendToBankUI1 = new GetSendToBankUI(); 
  BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();

  TransferData transferData1 = new TransferData();
  transferData1.setNameAndValue("startDate", request.getParameter("StartDate"));
  transferData1.setNameAndValue("endDate", request.getParameter("EndDate"));
  transferData1.setNameAndValue("bankCode", bankCode);
  transferData1.setNameAndValue("typeFlag", request.getParameter("typeFlag"));

  VData tVData = new VData();
  tVData.add(transferData1);
  tVData.add(tGlobalInput);

  //if (!getSendToBankUI1.submitData(tVData, "GETMONEY")) {
  if (!tBusinessDelegate2.submitData(tVData,"GETMONEY","GetSendToBankUI")) {
    //VData rVData = getSendToBankUI1.getResult();
    VData rVData = tBusinessDelegate2.getResult(); 
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = "���������ݴ���ɹ�! ";
  	FlagStr = "Succ";
  }  

	loggerDebug("GetSendToBankSave",Content + "\n" + FlagStr + "\n---GetSendToBankSave End---\n\n");
  }else{
	  FlagStr = "Fail";
	  Content = " ����ʧ�ܣ�ԭ����:" + r;
  }
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
