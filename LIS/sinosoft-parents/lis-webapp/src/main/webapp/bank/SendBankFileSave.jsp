<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：SendBankFileSave.jsp
//程序功能：邮保通批量代付代扣发盘文件发送页面
//创建日期：2010-2-2 9:48
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.bank.*"%>
<%
  loggerDebug("SendBankFileSave","---SendBankFileSave Start---"); 

  String Content = "";
  String FlagStr = "";
  TransferData tTransferData = new TransferData();
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
	VData inVData = new VData();
  String tSerialNo = request.getParameter("serialNo");
	String action = request.getParameter("fmtransact");
	String tYBTBankCode = request.getParameter("YBTBankCode");
	loggerDebug("SendBankFileSave","SerialNo: " + tSerialNo);
  loggerDebug("SendBankFileSave","action: " + action);
  loggerDebug("SendBankFileSave","YBTBankCode: " + tYBTBankCode);
	if (action.equals("send"))
	{
	  tTransferData.setNameAndValue("SerialNo",tSerialNo);
	  inVData.add(tTransferData);
	  inVData.add(tGlobalInput);
	  
	  ExeSQL tExeSQL = new ExeSQL();
	  String tYBTSendBankClassDefSQL = "select CodeAlias from LDCode where CodeType='YBTSendBankClassDef' and code in ('0000','"+tYBTBankCode+"')";
	  loggerDebug("SendBankFileSave","YBTSendBankClassDefSQL: " + tYBTSendBankClassDefSQL);
	  
	  String tYBTSendBankClassName = StrTool.cTrim(tExeSQL.getOneValue(tYBTSendBankClassDefSQL));
	  loggerDebug("SendBankFileSave","YBTSendBankClassName: " + tYBTSendBankClassName);
	  
	  if(tYBTSendBankClassName != null && !"".equals(tYBTSendBankClassName))
	  {
	    Class v_class = Class.forName(tYBTSendBankClassName);
	    YBTSendBank tYBTSendBank = (YBTSendBank) v_class.newInstance();
	    
	    if (!tYBTSendBank.submitData(inVData,"Send")) 
	    {
	      Content = " 发送失败，原因是:" + tYBTSendBank.getResult().get(0);
	     	FlagStr = "Fail";
	    }
	    else 
	    {
	  	  Content = " 发送成功! ";
	  	  FlagStr = "Succ";
	  	  
	  	  String tSQL = "select (case SendBankFileState when '1' then 'Succ' else 'Fail' end) FlagStr, (case SendBankFileState when '1' then '发送成功' when '2' then '发送失败' when '3' then '银行接受成功但反馈异常'end) from LYBankLog where SerialNo='"+tSerialNo+"'";
	      tExeSQL = new ExeSQL();
	      SSRS tSSRS = tExeSQL.execSQL(tSQL);
	      
	      if( tSSRS != null && tSSRS.getMaxRow()>0)
	      {
	        FlagStr = tSSRS.GetText(1,1);
	        Content = tSSRS.GetText(1,2);
	      }
	    }
	   }
	   else
		 {
		    Content = " 发送失败，原因是银行对应的处理类未定义" ;
	      FlagStr = "Fail";
		 }
		loggerDebug("SendBankFileSave",Content + "\n" + FlagStr + "\n---SendBankFileSave End---");
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit2('<%=FlagStr%>', '<%=PubFun.changForJavaScript(Content)%>');
</script>
</html>
