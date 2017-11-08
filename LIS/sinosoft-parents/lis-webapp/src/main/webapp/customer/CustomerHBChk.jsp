<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：FinFeeSureChk.jsp
//程序功能：到帐确认
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
loggerDebug("CustomerHBChk","Auto-begin:");

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
	FICustomerAccSet tFICustomerAccSet = new FICustomerAccSet();
	
	//tongmeng 2012-02-15 modify
	FICustomerAccSchema tFICustomerAccSchema = new FICustomerAccSchema();
	
	FICustomerAccSchema tFICustomerAccSchema1 = new FICustomerAccSchema();
	    	
	    		
	String tRadio[] = request.getParameterValues("InpCustomerGridSel");  
	
	
	String tInsuAccNo[] = request.getParameterValues("CustomerGrid1");
	
	String tCustomerNo[] = request.getParameterValues("CustomerGrid2");
	
	
                         //参数格式=” Inp+MulLine对象名+Sel” 
      for (int index=0; index< tRadio.length;index++)
      {
        if(tRadio[index].equals("1"))
        {
        	tFICustomerAccSchema.setInsuAccNo( tInsuAccNo[index] );
    		tFICustomerAccSchema.setCustomerNo( tCustomerNo[index] );
    		break;
        }
      }

    String tRadio1[] = request.getParameterValues("InpCustomer1GridSel");  
    String tInsuAccNo1[] = request.getParameterValues("Customer1Grid1");
    String tCustomerNo1[] = request.getParameterValues("Customer1Grid2");	
    
    for (int index=0; index< tRadio1.length;index++)
    {
      if(tRadio1[index].equals("1"))
      {
    	   tFICustomerAccSchema1.setInsuAccNo( tInsuAccNo1[index] );
    	   tFICustomerAccSchema1.setCustomerNo( tCustomerNo1[index] );
    	   break;
      }
    }
	
	
	

	  		
	

	try
	{
			// 准备传输数据 VData
			VData tVData = new VData();
			tVData.add( tFICustomerAccSchema );
			tVData.add( tFICustomerAccSchema1 );
			tVData.add( tG );			
			// 数据传输
			
			 //添加客户账户处理
            //FICustomerMain tFICustomerMain = new FICustomerMain();
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            //调用客户账户收费接口，传入标志HB
            
            //if(tFICustomerMain.submitData(tVData, "HB"))
            //{
            //}
            //else
            //{FlagStr = "Fail";
            //	Content = " 客户账户合并失败，原因是: " + tFICustomerMain.mErrors.getError(0).errorMessage;
            //}
            if(tBusinessDelegate.submitData(tVData, "HB","FICustomerMain"))
            {
            }
            else
            {FlagStr = "Fail";
            	Content = " 客户账户合并失败，原因是: " +  tBusinessDelegate.getCErrors().getError(0).errorMessage;
            }
			//如果在Catch中发现异常，则不从错误类中提取错误信息
			if (FlagStr == "Fail")
			{
			    //tError = tFICustomerMain.mErrors;
			    tError = tBusinessDelegate.getCErrors();
			    if (!tError.needDealError())
			    {                          
			    	Content = " 客户账户合并成功! ";
			    	FlagStr = "Succ";
			    }
			    else                                                                           
			    {
			    	Content = " 客户账户合并失败，原因是：";
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
	catch(Exception e)
	{
		e.printStackTrace();
		Content = Content.trim() +" 提示:异常退出.";
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
