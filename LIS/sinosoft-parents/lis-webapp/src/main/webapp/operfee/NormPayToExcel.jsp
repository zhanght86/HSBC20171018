<%
//程序名称：NormPayToExcel.jsp
//程序功能：保监会报表精算数据导入
//创建日期：
//创建人 : ck
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>

<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.agentprint.*"%>   
  <%@page import="com.sinosoft.service.*" %> 
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="java.io.*"%>
	
<%
  //接收信息，并作校验处理。
	String GrpPolNo=request.getParameter("GrpPolNo1");
	String operator="download";
	String file="";
	
	//输出参数
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr = "Fail";
	String Content = "";
	String Result="";	
	boolean flag=true;
	GlobalInput tG = new GlobalInput();  
    tG=(GlobalInput)session.getValue("GI");  
    if(tG == null) {
		out.println("session has expired");
		return;
    } 
    System.out.println("团单号："+GrpPolNo);
  
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("GrpPolNo",GrpPolNo);	
    
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tTransferData );
		tVData.add( tG );
		
		// 数据传输
  	//ExcelChange tExcelChange   = new ExcelChange();
  	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  			System.out.println("before ExcelChange!!!!");			
		 if (!tBusinessDelegate.submitData(tVData,"CHANGE","ExcelChange"))
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			System.out.println("Error: "+ tBusinessDelegate.getCErrors().getError(i).errorMessage);
			Content = " 数据导入失败，原因是: " +  tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError =  tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = "成功生成模板文件! ";
		    	FlagStr = "Succ";
		    	//file=tExcelChange.getFile();    
		    	file=tBusinessDelegate.getResult().getObjectByObjectName("String",0).toString();
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+"提示：异常终止!";
}
 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=operator%>","<%=file%>");
</script>
</html>
