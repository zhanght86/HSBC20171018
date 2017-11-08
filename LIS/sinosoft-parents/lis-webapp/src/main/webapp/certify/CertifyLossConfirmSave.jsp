
<%
	//程序名称：CertifyLossConfirmSave.jsp
	//程序功能：
	//创建日期：2002-09-23
	//创建人  ：mw
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
		String tChk[] = request.getParameterValues("InpCertifyListChk");
		String tApplyNo[] = request.getParameterValues("CertifyList1");
		
		String tOperator = request.getParameter("Operator");
		String tOperateDate = request.getParameter("OperateDate");
		String tPublishDate = request.getParameter("PublishDate");
		String tNewspaper = request.getParameter("Newspaper");
		String tStartNo[] = request.getParameterValues("CertifyList6");
		String tEndNo[] = request.getParameterValues("CertifyList7");
		
		loggerDebug("CertifyLossConfirmSave","tChk.length=" + tChk.length);

		LZCardAppSet tLZCardAppSet = new LZCardAppSet();
		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		LZCardAppSchema tLZCardAppSchema = new LZCardAppSchema();
		tLZCardAppSchema.setApplyNo(tApplyNo[i]);
		tLZCardAppSchema.setReplyPerson(tOperator);
		tLZCardAppSchema.setReplyDate(tOperateDate);
		tLZCardAppSchema.setnote(tPublishDate+"@"+tNewspaper);
		tLZCardAppSchema.setStartNo(tStartNo[i]);
		tLZCardAppSchema.setEndNo(tEndNo[i]);
		
		tLZCardAppSet.add(tLZCardAppSchema);
		
		loggerDebug("CertifyLossConfirmSave","CertifyLossConfirmSave.jsp传入数据：");
		loggerDebug("CertifyLossConfirmSave","挂失确认申请号码=" + tApplyNo[i]);
			}
		}

		// 准备传输数据 VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(tLZCardAppSet);

		// 设置操作字符串
		String szOperator = request.getParameter("operateFlag");

		// 数据传输
		/*CertLossConfirmUI tCertLossConfirmUI = new CertLossConfirmUI();
		if (!tCertLossConfirmUI.submitData(vData, szOperator)) {
			Content = " 保存失败，原因是: "
			+ tCertLossConfirmUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " 保存成功 ";
			FlagStr = "Succ";
		}*/
		String busiName="CertLossConfirmUI";
		  String mDescType="保存";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(vData,szOperator,busiName))
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
