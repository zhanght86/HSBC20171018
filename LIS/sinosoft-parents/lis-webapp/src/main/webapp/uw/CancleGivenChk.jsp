<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：CancleGivenChk.jsp
//程序功能：人工核保体检资料查询
//创建日期：2009-1-7 17:10:36
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
	String Flag = request.getParameter("flag");
	loggerDebug("CancleGivenChk","@@@Flag:"+Flag);
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
		
  	LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
  	
 		String tContNo=request.getParameter("ContNo");
 		String tInsuredNo = request.getParameter("InsuredNo");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("InsuredNo",tInsuredNo);	 
		// 准备传输数据 VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tTransferData);
		
		/*CancleGivenUI tCancleGivenUI = new CancleGivenUI();
		
		try{
			loggerDebug("CancleGivenChk","this will save the data!!!");
			tCancleGivenUI.submitData(tVData,"submit");
		}
		catch(Exception ex){
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tCancleGivenUI.mErrors;
			if (!tError.needDealError()){
				Content = " 保存成功! ";
				FlagStr = "Succ";
			}
			else{
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}*/
		String busiName="CancleGivenUI";
		  String mDescType="保存";
		  loggerDebug("CancleGivenChk","this will save the data!!!");
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData,"submit",busiName))
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
%>                      
<html>
<script language="javascript">	
			parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
