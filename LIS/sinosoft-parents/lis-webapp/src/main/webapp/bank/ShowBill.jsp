<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ShowBill.jsp
//�����ܣ�
//������  ��������
//�������ڣ�
//���¼�¼��  
//������
//��������
//����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>

<%
  //�������
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  
	//����Ҫ��ѯ�ı�Ľṹ
	String strStartDate = request.getParameter("StartDate"); 
  String strEndDate = request.getParameter("EndDate");
  String strBankCode = request.getParameter("BankCode");
  String strFlag = request.getParameter("Flag");
	String strTFFlag = request.getParameter("TFFlag");
	String strXQFlag = request.getParameter("SXFlag");
  GlobalInput tG = new GlobalInput();	
	tG=(GlobalInput)session.getValue("GI");
 	loggerDebug("ShowBill","������Ŀ�ʼ������"+strStartDate);
 	loggerDebug("ShowBill","������Ľ���������"+strEndDate);
 	loggerDebug("ShowBill","���д�����"+strBankCode);
 	loggerDebug("ShowBill","���մ�����־�ǲ�����־��"+strFlag);
 	loggerDebug("ShowBill","��ȷ����ı�־��"+strTFFlag);
 	loggerDebug("ShowBill","�����ڵı�־��"+strXQFlag);
 	loggerDebug("ShowBill","SHOW");
 	
 	
 	
  VData tVData = new VData();
  tVData.addElement(strStartDate);
  tVData.addElement(strEndDate);
  tVData.addElement(strBankCode);
  tVData.addElement(strFlag);
  tVData.addElement(strTFFlag);
  tVData.addElement(strXQFlag);
	tVData.addElement(tG);
	NewShowBillUI mNewShowBillUI = new NewShowBillUI();

	if (!mNewShowBillUI.submitData(tVData,"SHOW"))
	{
      Content = " ��ѯʧ�ܣ�ԭ����: " + mNewShowBillUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = mNewShowBillUI.getResult();
		
		// ��ʾ
		LYBankLogSet tLYBankLogSet = new LYBankLogSet();
		tLYBankLogSet.set((LYBankLogSet)tVData.getObjectByObjectName("LYBankLogSet",0));
		int n = tLYBankLogSet.size();
		loggerDebug("ShowBill","get report "+n);
		if(n==0)
		{
			Content = " ��ѯʧ�ܣ�ԭ����:û�з�������������" ;
      FlagStr = "Fail";
		}
		else
		{
		String Strtest = "0|" + n + "^" + tLYBankLogSet.encode();
		loggerDebug("ShowBill","QueryResult: " + Strtest);
		%>
				<script language="javascript">
		   	 try 
		   	 {
		   	   parent.fraInterface.displayQueryResult('<%=Strtest%>');
		   	 }
		   	 catch(ex) {}		   	
		   	</script>
		<%
		for (int i = 1; i <= n; i++)
		{
		  	LYBankLogSchema tLYBankLogSchema = tLYBankLogSet.get(i);
		  	
		} 
	} 
  }
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail")
  {
    tError = mNewShowBillUI.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " ��ѯ�ɹ�! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " ��ѯʧ�ܣ�ԭ����: û�з���������������Ϣ";
    	FlagStr = "Fail";
    }
  }
loggerDebug("ShowBill","------end------");
loggerDebug("ShowBill",FlagStr);
loggerDebug("ShowBill",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
