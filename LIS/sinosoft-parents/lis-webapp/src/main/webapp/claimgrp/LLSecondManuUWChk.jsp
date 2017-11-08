<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：InsuredUWInfoChk.jsp
//程序功能：人工核保体检资料查询
//创建日期：2005-01-19 11:10:36
//创建人  ：zhangxing
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
	String Flag = request.getParameter("flag");
 	String tCaseNo = request.getParameter("CaseNo"); 
  String tBatNo = request.getParameter("BatNo");
  
 	String tContNo=request.getParameter("ContNo");
 	String tUWIdea=request.getParameter("UWIdea"); 		
  String tUWFlag = request.getParameter("uwState");	
  System.out.println(tUWFlag);
  System.out.println("tCaseNo:"+tCaseNo);
  System.out.println("tBatNo:"+tBatNo);
	
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
	 
 
  	LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
  	
  	
   
    tLLCUWMasterSchema.setCaseNo(tCaseNo);
    tLLCUWMasterSchema.setBatNo(tBatNo);
 		tLLCUWMasterSchema.setContNo(tContNo);
 	
 		tLLCUWMasterSchema.setPassFlag(tUWFlag);
 		tLLCUWMasterSchema.setUWIdea(tUWIdea);
 		
 	
	
		// 准备传输数据 VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tLLCUWMasterSchema);
	
		
		SecondManuUWUI tSecondManuUWUI = new SecondManuUWUI();
		
		try{
			System.out.println("this will save the data!!!");
			tSecondManuUWUI.submitData(tVData,"");
		}
		catch(Exception ex){
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tSecondManuUWUI.mErrors;
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
