<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：FinFeeSureChk.jsp
//程序功能：到帐确认
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
loggerDebug("FinFeeSureChk","Auto-begin:");

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
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
 
  	if(tG == null) {
		out.println("session has expired");
		return;
	}        
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
	LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();

	String tTempFeeNo[] = request.getParameterValues("PolGrid1");
	String tPayintv[] = request.getParameterValues("PolGrid3");
	String tDate[] = request.getParameterValues("PolGrid7");
	String tChk[] = request.getParameterValues("InpPolGridChk");
	String tChequeNo[] = request.getParameterValues("PolGrid2");
	boolean flag = false;
	int feeCount = tTempFeeNo.length;
	loggerDebug("FinFeeSureChk","feecout:"+feeCount);
	

	for (int i = 0; i < feeCount; i++)
	{
		if (!tTempFeeNo.equals("") && tChk[i].equals("1"))
		{
			if (tDate[i].equals(""))
			{
				Content = " 请录入到帐日期! ";
			}
		else if(tDate[i].length()!=10 )
			{
		  	Content = " 到帐日期格式错误，请采用YYYY-MM-DD格式! ";
			}
			else
			{
				loggerDebug("FinFeeSureChk","TempFeeNo:"+i+":"+tTempFeeNo[i]);
	  			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		    		tLJTempFeeClassSchema.setTempFeeNo( tTempFeeNo[i] );
		    		tLJTempFeeClassSchema.setPayMode( tPayintv[i] );
		    		tLJTempFeeClassSchema.setEnterAccDate( tDate[i] );
	          tLJTempFeeClassSchema.setChequeNo( tChequeNo[i] ); 
		    		tLJTempFeeClassSet.add( tLJTempFeeClassSchema );
		    		flag = true;
		    	}
		}
	}
	loggerDebug("FinFeeSureChk","tLJTempFeeClassSet:" + tLJTempFeeClassSet.encode());

try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLJTempFeeClassSet );
		tVData.add( tG );
		
		// 数据传输
		//FinFeeSureUI tFinFeeSureUI   = new FinFeeSureUI();
	
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	
		if (tBusinessDelegate.submitData(tVData,"INSERT","FinFeeSureUI") == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			loggerDebug("FinFeeSureChk","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			Content = " 到帐确认失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " 到帐确认成功! ";
		    	FlagStr = "Succ";
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
	Content = Content.trim() +" 提示:异常退出.";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.initPolGrid();
</script>
</html>
