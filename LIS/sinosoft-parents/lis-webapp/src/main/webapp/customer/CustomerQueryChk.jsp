<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：FinFeeSureChk.jsp
//程序功能：到帐确认
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
loggerDebug("CustomerQueryChk","Auto-begin:");

%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.customer.*"%>
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
	FICustomerAccTraceSet tFICustomerAccTraceSet = new FICustomerAccTraceSet();
	String tChk[] = request.getParameterValues("InpCustomerGridChk");
	String tSequence[] = request.getParameterValues("CustomerGrid11");
	String tOperationType[] = request.getParameterValues("CustomerGrid5");	
	String tOperationNo[] = request.getParameterValues("CustomerGrid4");
	
	boolean flag = false;
	int Count = tSequence.length;
	loggerDebug("CustomerQueryChk","cout:"+Count);

	for (int i = 0; i < Count; i++)
	{
		if (!tSequence.equals("") && tChk[i].equals("1"))
		{
	  			FICustomerAccTraceSchema tFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
	    		tFICustomerAccTraceSchema.setSequence( tSequence[i] );
	    		tFICustomerAccTraceSchema.setOperationType( tOperationType[i] );
			  	tFICustomerAccTraceSchema.setOperationNo( tOperationNo[i] );
	    		tFICustomerAccTraceSet.add( tFICustomerAccTraceSchema );
	    		flag = true;
		    
		}
	}
	loggerDebug("CustomerQueryChk","tFICustomerAccTraceSet:" + tFICustomerAccTraceSet.encode());

	try
	{
	  	if (flag == true)
	  	{
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add( tFICustomerAccTraceSet );
			tVData.add( tG );			
			// 数据传输
			
			 //添加客户账户处理
            //FICustomerMain tFICustomerMain = new FICustomerMain();
             BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            //调用客户账户收费接口，传入标志LQ
            //if(tFICustomerMain.submitData(tVData, "LQ"))
            //{
            //}
            //else
            //{FlagStr = "Fail";
            //	Content = " 余额领取失败，原因是: " + tFICustomerMain.mErrors.getError(0).errorMessage;
            //}
           if(tBusinessDelegate.submitData(tVData, "LQ","FICustomerMain"))
            {
            }
            else
            {FlagStr = "Fail";
            	Content = " 余额领取失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
            }
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr == "Fail")
			{
			    //tError = tFICustomerMain.mErrors;
			     tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {                          
			    	Content = " 余额领取成功! ";
			    	FlagStr = "Succ";
			    }
			    else                                                                           
			    {
			    	Content = " 余额领取失败，原因是：";
			    	int n = tError.getErrorCount();
	    			if (n > 0)
	    			{
						for(int i = 0;i < n;i++)
						{
						  //tError = tErrors.getError(i);
						  Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
						}
					}
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
