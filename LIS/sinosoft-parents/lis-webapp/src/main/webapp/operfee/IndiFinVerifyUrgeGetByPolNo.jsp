
<%
	//程序名称：IndiFinVerifyUrgeGetByPolNo.jsp
	//程序功能：
	//创建日期：
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>

<%
	GlobalInput tGI = new GlobalInput(); //repair:
	tGI = (GlobalInput) session.getValue("GI"); //参见loginSubmit.jsp
	// 输出参数
	CErrors tError = null;
	String FlagStr = "";
	String Content = "";
	int recordCount = 0;

	String ContNo = request.getParameter("ContNo");
	
	LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	tLJTempFeeSchema.setOtherNo(ContNo);
	
	VData tVData = new VData();
	tVData.add(tLJTempFeeSchema);
	tVData.add(tGI);

	/*IndiFinUrgeVerifyUI tIndiFinUrgeVerifyUI = new IndiFinUrgeVerifyUI();
	tIndiFinUrgeVerifyUI.submitData(tVData, "VERIFY");
	tError = tIndiFinUrgeVerifyUI.mErrors;
	if (!tError.needDealError()) {
		Content = " 核销事务成功";
		FlagStr = "Succ";
	} else {
		Content = " 核销事务失败，原因是:" + tError.getFirstError();
		FlagStr = "Fail";
	}*/
	String busiName="IndiFinUrgeVerifyUI";
	  String mDescType="核销事务";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(tVData,"VERIFY",busiName))
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

	Content.replace('\"', '$');
	System.out.println("Content:" + Content);
%>
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML>
