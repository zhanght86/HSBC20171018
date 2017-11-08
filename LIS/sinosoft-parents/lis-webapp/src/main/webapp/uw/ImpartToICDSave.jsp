<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuHealthQChk.jsp
//程序功能：人工核保体检资料查询
//创建日期：2005-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
 
 	String tContNo = request.getParameter("ContNo");
 	String tProposalContNo =request.getParameter("ContNo");
 	String tCustomerNo=request.getParameter("InsureNo");
 	String tPrtSeq=request.getParameter("PrtSeq");
 	loggerDebug("ImpartToICDSave","tContNo="+tContNo);
 	loggerDebug("ImpartToICDSave","tCustomerNo="+tCustomerNo);
 	loggerDebug("ImpartToICDSave","tPrtSeq="+tPrtSeq);
 	
 	LCDiseaseResultSchema tLCDiseaseResultSchema ;
 	LCDiseaseResultSet tLCDiseaseResultSet = new LCDiseaseResultSet();
 	
	int lineCount = 0;
	String arrCount[] = request.getParameterValues("DisDesbGridNo");

	if(arrCount!=null)
	{
		String tDisDesb[]=request.getParameterValues("DisDesbGrid1");
		String tDisResult[]=request.getParameterValues("DisDesbGrid2");
		String tICDCode[]=request.getParameterValues("DisDesbGrid3");
		
		lineCount = arrCount.length;
		loggerDebug("ImpartToICDSave","lineCount="+lineCount);
		for(int i = 0;i<lineCount;i++)
		{
			tLCDiseaseResultSchema = new LCDiseaseResultSchema();
			tLCDiseaseResultSchema.setContNo(tContNo);
			tLCDiseaseResultSchema.setProposalContNo(tProposalContNo);
			tLCDiseaseResultSchema.setDiseaseSource("1");          //告知整理信息
			tLCDiseaseResultSchema.setCustomerNo(tCustomerNo);
			tLCDiseaseResultSchema.setDisDesb(tDisDesb[i]);
			loggerDebug("ImpartToICDSave","tDisDesb="+tDisDesb[i]);
			loggerDebug("ImpartToICDSave","tDisResult="+tDisResult[i]);
			loggerDebug("ImpartToICDSave","tICDCode="+tICDCode[i]);
			tLCDiseaseResultSchema.setDisResult(tDisResult[i]);
			tLCDiseaseResultSchema.setICDCode(tICDCode[i]);
			tLCDiseaseResultSet.add(tLCDiseaseResultSchema);
		}
	}
	else
	{
	}
	
	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr="";

	tVData.add(tG);
	tVData.add(tLCDiseaseResultSet);
	
	ImpartToICDUI tImpartToICDUI = new ImpartToICDUI();
	
	try{
		loggerDebug("ImpartToICDSave","this will save the data!!!");
		tImpartToICDUI.submitData(tVData,"");
	}
	catch(Exception ex){
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
	
	if (!FlagStr.equals("Fail")){
		tError = tImpartToICDUI.mErrors;
		if (!tError.needDealError()){
			Content = " 保存成功! ";
			FlagStr = "Succ";
		}
		else{
			Content = " 保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
