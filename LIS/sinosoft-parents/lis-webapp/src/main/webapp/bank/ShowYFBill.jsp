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
  GlobalInput tG = new GlobalInput();	
	tG=(GlobalInput)session.getValue("GI");
 	loggerDebug("ShowYFBill","ShowYFBill"+strStartDate);
 	loggerDebug("ShowYFBill","������Ľ���������"+strEndDate);
 	loggerDebug("ShowYFBill","���д�����"+strBankCode);
 	loggerDebug("ShowYFBill","���մ�����־�ǲ�����־��"+strFlag);
 	loggerDebug("ShowYFBill","��ȷ����ı�־��"+strTFFlag);
 	
  VData tVData = new VData();
  tVData.addElement(strStartDate);
  tVData.addElement(strEndDate);
  tVData.addElement(strBankCode);
  tVData.addElement(strFlag);
  tVData.addElement(strTFFlag);
	tVData.addElement(tG);
	ShowYFBillUI mShowYFBillUI = new ShowYFBillUI();

	if (!mShowYFBillUI.submitData(tVData,"QUERY||MAIN"))
	{
      Content = " ��ѯʧ�ܣ�ԭ����: " + mShowYFBillUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = mShowYFBillUI.getResult();
		
		// ��ʾ
		LYBankLogSet tLYBankLogSet = new LYBankLogSet();
		tLYBankLogSet.set((LYBankLogSet)tVData.getObjectByObjectName("LYBankLogSet",0));
		int n = tLYBankLogSet.size();
		loggerDebug("ShowYFBill","get report "+n);
		if(n==0)
		{
			Content = " ��ѯʧ�ܣ�ԭ����:û�з�������������" ;
      FlagStr = "Fail";
		}
		else
		{
		String Strtest = "0|" + n + "^" + tLYBankLogSet.encode();
		loggerDebug("ShowYFBill","QueryResult: " + Strtest);
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
    tError = mShowYFBillUI.mErrors;
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
loggerDebug("ShowYFBill","------end------");
loggerDebug("ShowYFBill",FlagStr);
loggerDebug("ShowYFBill",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
