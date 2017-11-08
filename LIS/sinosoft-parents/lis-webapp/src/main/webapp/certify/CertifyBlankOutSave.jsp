
<%
	//程序名称：CertifyBlankOutSave.jsp
	//程序功能：单证作废
	//创建日期：2002-10-08
	//创建人  ：kevin
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
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	boolean bContinue = true;
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	try {
		// 单证信息部分
		String szHandler = request.getParameter("Handler");
		String szHandleDate = request.getParameter("HandleDate");

		String szNo[] = request.getParameterValues("CertifyListNo");
		String szCertifyCode[] = request.getParameterValues("CertifyList1");
		String szStartNo[] = request.getParameterValues("CertifyList3");
		String szEndNo[] = request.getParameterValues("CertifyList4");
		String szStateFlag[] = request.getParameterValues("CertifyList5");

		int nIndex;
		LZCardSet setLZCard = new LZCardSet();

		for (nIndex = 0; nIndex < szNo.length; nIndex++) {
			LZCardSchema schemaLZCard = new LZCardSchema();

			schemaLZCard.setCertifyCode(szCertifyCode[nIndex]);

			schemaLZCard.setStartNo(szStartNo[nIndex]);
			schemaLZCard.setEndNo(szEndNo[nIndex]);

			schemaLZCard.setStateFlag(szStateFlag[nIndex]); //6、使用作废，7、停用作废

			schemaLZCard.setHandler(szHandler);
			schemaLZCard.setHandleDate(szHandleDate);

			setLZCard.add(schemaLZCard);
		}

		// 准备传输数据 VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);

		// 数据传输
		/*CertBlankOutUI tCertBlankOutUI = new CertBlankOutUI();
		if (!tCertBlankOutUI.submitData(vData, "INSERT")) {
			Content = " 保存失败，原因是: " + tCertBlankOutUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " 保存成功 ";
			FlagStr = "Succ";
		}*/
		
		String busiName="CertBlankOutUI";
		String mDescType="保存";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,"INSERT",busiName))
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
		Content = " 保存失败，原因是:" + ex.getMessage();
		FlagStr = "Fail";
	}
%>

<html>
<script language="javascript">
  	<%= szFailSet %>
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	</script>
<body>
</body>
</html>

