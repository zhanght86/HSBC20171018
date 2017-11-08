
<%
	//程序名称：CertifySendOutSave.jsp
	//程序功能：
	//创建日期：2002-09-23
	//创建人  ：周平
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<SCRIPT src="./CQueryValueOperate.js"></SCRIPT>
<SCRIPT src="IndiDunFeeInput.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//输出参数
	String strTakeBackNo = "";
	String FlagStr = "Fail";
	String Content = "";
	boolean bContinue = true;
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	try {
		// 单证信息部分
		String tNote = request.getParameter("note");
		String tReplyPerson = request.getParameter("ReplyPerson");
		String tReplyDate = request.getParameter("ReplyDate");

		// 设置操作字符串，INSERT为确认入库，CANCEL为拒绝入库
		String szOperator = request.getParameter("operateFlag");

		String tChk[] = request.getParameterValues("InpCertifyListChk");
		String tApplyNo[] = request.getParameterValues("CertifyList1");

		loggerDebug("CertifySendOutReplySave","tChk.length=" + tChk.length);

		LZCardAppSet tLZCardAppSet = new LZCardAppSet();
		for (int i = 0; i < tChk.length; i++) {
			loggerDebug("CertifySendOutReplySave",i);
			if ("1".equals(tChk[i])) {
		LZCardAppSchema tLZCardAppschema = new LZCardAppSchema();
		tLZCardAppschema.setApplyNo(tApplyNo[i]);
		tLZCardAppschema.setReplyPerson(tReplyPerson);
		tLZCardAppschema.setReplyDate(tReplyDate);
		tLZCardAppschema.setnote(tNote);
		if (szOperator.equals("true")) {
			tLZCardAppschema.setStateFlag("2"); //2、批复确认未发放
		} else {
			tLZCardAppschema.setStateFlag("4"); //4、批复拒绝
		}

		tLZCardAppSet.add(tLZCardAppschema);
			}
		}

		// 准备传输数据 VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(tLZCardAppSet);

		// 数据传输
		/*CertSendOutApplyUI tCertSendOutApplyUI = new CertSendOutApplyUI();

		if (!tCertSendOutApplyUI.submitData(vData, "REPLY")) { //REPLY:批复
			Content = " 保存失败，原因是: "
			+ tCertSendOutApplyUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " 保存成功 ";
			FlagStr = "Succ";
		}*/
		String busiName="CertSendOutApplyUI";
		String mDescType="保存";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,"REPLY",busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
					Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
					FlagStr = "Fail";
			   }
			   else
			   {
					Content = mDescType+"失败";
					FlagStr = "Fail";				
			   }
		  }
		  else
		  {
		     	Content = mDescType+"成功! ";
		      	FlagStr = "Succ";  
		  }
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = FlagStr + " 保存失败，原因是:" + ex.getMessage();
		FlagStr = "Fail";
	}
%>

<html>
<script language="javascript">
  	<%= szFailSet %>
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
	</script>
<body>
</body>
</html>
