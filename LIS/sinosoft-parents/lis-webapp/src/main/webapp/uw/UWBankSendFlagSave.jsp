<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//WorkFlowNotePadSave.jsp
//�����ܣ�
//�������ڣ�2005-04-22 14:49:52
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
 loggerDebug("UWBankSendFlagSave","Start UWBankSendFlagSave...");
  //�������
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  ExeSQL tExeSQL = new ExeSQL();
  
 try
	{		
   String tPrtNo = request.getParameter("PrtNo");
   String tNewAutoSendBankFlag = request.getParameter("NewAutoSendBankFlag");
   loggerDebug("UWBankSendFlagSave","tPrtNo: "+tPrtNo);
   loggerDebug("UWBankSendFlagSave","tNewAutoSendBankFlag: "+tNewAutoSendBankFlag);
   
   if("1".equals(tNewAutoSendBankFlag))
   {
     tNewAutoSendBankFlag = "";
   }
    String tSQL = "update LCCont set NewAutoSendBankFlag='"+tNewAutoSendBankFlag+"',modifydate=to_date('"+PubFun.getCurrentDate()+"','yyyy-mm-dd'),modifytime='"+PubFun.getCurrentTime()+"' where ContNo='"+tPrtNo+"'"; 
	  if(!tExeSQL.execUpdateSQL(tSQL))
	  {
	    loggerDebug("UWBankSendFlagSave","����ʧ��...");            
	  }
	  loggerDebug("UWBankSendFlagSave","1");          
	}
	catch(Exception ex)
	{
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      loggerDebug("UWBankSendFlagSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	} 
	loggerDebug("UWBankSendFlagSave","1.1");  
	if ("".equals(FlagStr))
	{
	 loggerDebug("UWBankSendFlagSave","1.2");  
	 tError = tExeSQL.mErrors;
	 if (!tError.needDealError())
		{ 
		  loggerDebug("UWBankSendFlagSave","1.3");                           
		  Content ="����ɹ���";
		  FlagStr = "Succ";
		}
		else                                                                           
		{
		   loggerDebug("UWBankSendFlagSave","1.4"); 
		   Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		   FlagStr = "Fail";
		}
	}
	loggerDebug("UWBankSendFlagSave","Content"+Content);      
	loggerDebug("UWBankSendFlagSave","End UWBankSendFlagSave...");                 
  %>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
