 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：TempFeeSave.jsp
//程序功能：
//创建日期：2002-07-22 
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>  
<%@page import="com.sinosoft.service.*" %>
<%
// 输出参数
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";
   String CurrentDate = PubFun.getCurrentDate();
    String ShowFinance="N"; //显示缴纳金额信息的标记（如果财务收费完全正确，则显示金额的详细信息） 
	 String Enteraccdate = CurrentDate;
	double CashValue=0;
	double ChequeValue=0;
	loggerDebug("TempFeeSave","111111111111111111111");
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  if(tGI==null)
  {
    loggerDebug("TempFeeSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
	   String Operator  = tGI.Operator ;  //保存登陆管理员账号
	   //String ManageCom = tGI.ComCode  ; //保存登陆区站,ManageCom=内存中登陆区站代码
	   String CERTIFY_XQTempFee=request.getParameter("CERTIFY_XQTempFee");
	   
	   LJTempFeeSchema tLJTempFeeSchema;
		 LJTempFeeClassSchema tLJTempFeeClassSchema;
	   LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet(); 
	       
		 String tTempClassNum[]   = request.getParameterValues("TempClassToGridNo");
	   String tCTempFeeNo[]     = request.getParameterValues("TempClassToGrid1");
		 String tCPayMode[]       = request.getParameterValues("TempClassToGrid2");
		 String tCPayDate[]       = request.getParameterValues("TempClassToGrid3");
		 String tCCurrency[]      = request.getParameterValues("TempClassToGrid4");
		 String tCPayMoney[]      = request.getParameterValues("TempClassToGrid5");
		 String tCEnterAccDate[]  = request.getParameterValues("TempClassToGrid6");
		 String tCManageCom[]     = request.getParameterValues("TempClassToGrid7");
		
		 String tCChequeNo[]      = request.getParameterValues("TempClassToGrid8");
		 String tChequeDate[]     = request.getParameterValues("TempClassToGrid9");
		 String tBankcode[]       = request.getParameterValues("TempClassToGrid10");
		 String tBankaccno[]      = request.getParameterValues("TempClassToGrid11");
		 String tAccname[]        = request.getParameterValues("TempClassToGrid12");
			//MS不存在银行的划分问题，所以只需要记录收费银行即可	 		
		 //只在暂收费表记录交费期间和交费期限，不记录交费单位信息	
		 String tIDType[]         = request.getParameterValues("TempClassToGrid19");
		 String tIDNo[]           = request.getParameterValues("TempClassToGrid20");
		 String tTempfeeNoType[]           = request.getParameterValues("TempClassToGrid21");
					
		 int TempClassCount = tTempClassNum.length;	
		
		 for (int i = 0; i < TempClassCount; i++)
	   {
	      tLJTempFeeClassSchema   = new LJTempFeeClassSchema();
		    tLJTempFeeClassSchema.setTempFeeNo(tCTempFeeNo[i]);
		    tLJTempFeeClassSchema.setPayMode(tCPayMode[i]);	    	    
		    tLJTempFeeClassSchema.setPayDate(tCPayDate[i]);	 
		    tLJTempFeeClassSchema.setCurrency(tCCurrency[i]);
		    tLJTempFeeClassSchema.setPayMoney(tCPayMoney[i]);
		    tLJTempFeeClassSchema.setChequeNo(tCChequeNo[i]);
		    tLJTempFeeClassSchema.setChequeDate(tChequeDate[i]);
		    tLJTempFeeClassSchema.setEnterAccDate(tCEnterAccDate[i]);   
		    tLJTempFeeClassSchema.setManageCom(tGI.ManageCom);
		    tLJTempFeeClassSchema.setPolicyCom(tCManageCom[i]);
		    tLJTempFeeClassSchema.setBankCode(tBankcode[i]);
		    tLJTempFeeClassSchema.setBankAccNo(tBankaccno[i]);
		    tLJTempFeeClassSchema.setAccName(tAccname[i]);	
		    tLJTempFeeClassSchema.setIDType(tIDType[i]);
		    tLJTempFeeClassSchema.setIDNo(tIDNo[i]);	            	    
		    tLJTempFeeClassSchema.setOperator(Operator);	
		    tLJTempFeeClassSchema.setOperState("0");	
		    tLJTempFeeClassSchema.setTempFeeNoType(tTempfeeNoType[i]);	    	    
		    tLJTempFeeClassSet.add(tLJTempFeeClassSchema);		          		      
		 } 
	
	 loggerDebug("TempFeeSave","tLJTempFeeSchema:");
	
	  // 暂收表信息记录集
	   LJTempFeeSet tLJTempFeeSet   = new LJTempFeeSet();
		 String tTempNum[]      = request.getParameterValues("TempToGridNo");
		 String tTempFeeNo[]    = request.getParameterValues("TempToGrid1");
		 String tTempFeeType[]  = request.getParameterValues("TempToGrid2");
		 String tPayDate[]      = request.getParameterValues("TempToGrid3");
		 String tCurrency[]     = request.getParameterValues("TempToGrid4");
		 String tPayMoney[]     = request.getParameterValues("TempToGrid5");
		 String tEnterAccDate[] = request.getParameterValues("TempToGrid6");
		 String tManageCom[]    = request.getParameterValues("TempToGrid7"); 	
		 String tRiskCode[]     = request.getParameterValues("TempToGrid8");
	 	 String tAgentGroup[]   = request.getParameterValues("TempToGrid9");					
	   String tAgentCode[]    = request.getParameterValues("TempToGrid10");
		 String tOtherNo[]      = request.getParameterValues("TempToGrid11");
	   String tPayIntv[]      = request.getParameterValues("TempToGrid13");
		 String tPayYears[]     = request.getParameterValues("TempToGrid14");	
	   String tComment[]      = request.getParameterValues("TempToGrid17");	
	   String tCertifyFlag  = request.getParameter("CertifyFlagHidden");
	   String tClaimFeeType  = request.getParameter("ClaimFeeTypeHidden");
	   String tCardNo[]  = request.getParameterValues("TempToGrid15");

		 int TempCount = tTempNum.length; 		
		 loggerDebug("TempFeeSave","TempCount:"+TempCount);
		for (int i = 0; i < TempCount; i++)
		{   
	      tLJTempFeeSchema   = new LJTempFeeSchema();
		    tLJTempFeeSchema.setTempFeeNo(tTempFeeNo[i]);
		    tLJTempFeeSchema.setTempFeeType(tTempFeeType[i]);
		    tLJTempFeeSchema.setRiskCode(tRiskCode[i]);	    	    	    	    	    	    	    	    		    	    
		    tLJTempFeeSchema.setAgentGroup(tAgentGroup[i]);
		    tLJTempFeeSchema.setAgentCode(tAgentCode[i]);
		    tLJTempFeeSchema.setPayDate(tPayDate[i]);
		    tLJTempFeeSchema.setEnterAccDate(tEnterAccDate[i]); //到帐日期 	
		    tLJTempFeeSchema.setCurrency(tCurrency[i]);
		    tLJTempFeeSchema.setPayMoney(tPayMoney[i]);
		    tLJTempFeeSchema.setManageCom(tGI.ManageCom);	    	    	    
		    tLJTempFeeSchema.setPolicyCom(tManageCom[i]);
		    tLJTempFeeSchema.setOtherNo(tOtherNo[i]);
		    tLJTempFeeSchema.setOperator(Operator);	
			  tLJTempFeeSchema.setPayIntv(tPayIntv[i]);
			  tLJTempFeeSchema.setPayYears(tPayYears[i]);    	 
			  tLJTempFeeSchema.setOperState("0"); 	//配合团单签单，正常数据为0 
			  tLJTempFeeSchema.setSerialNo(tCardNo[i]);  //续期收据号	    
			  tLJTempFeeSchema.setState(request.getParameter("CardCode"));
			  tLJTempFeeSchema.setRemark(tCardNo[i]); //续期收据号	 zy 2009-06-29 18:06 由于续期收据号被覆盖，所以存放在新的字段上

		    if(tTempFeeType[i].equals("5")||tTempFeeNo[i]=="5")
		    {    
		     tLJTempFeeSchema.setEnterAccDate(Enteraccdate);
		     tLJTempFeeSchema.setRemark(tComment[i]);
		    }
		    if("1".equals(tTempFeeType[i]))
		    {
		    	 tLJTempFeeSchema.setTempFeeNoType(tCertifyFlag);  
		    }
		    if("6".equals(tTempFeeType[i]))
		    {
		    	 tLJTempFeeSchema.setTempFeeNoType(tClaimFeeType);  
		    }
		    tLJTempFeeSet.add(tLJTempFeeSchema);
		} 
	

	  
	  
	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("Type","0"); //为了判断是界面录入还是外包录入，0代表手工录入
	   TransferData tTransferData1 = new TransferData();
	   tTransferData1.setNameAndValue("CERTIFY_XQTempFee",CERTIFY_XQTempFee);   
	   // 准备传输数据 VData
	   VData tVData = new VData();
		 tVData.addElement(tLJTempFeeSet);
		 tVData.addElement(tLJTempFeeClassSet);
	     tVData.add(tTransferData1);
		 tVData.addElement(tGI); 	    
	      // 数据传输
	   //TempFeeUI tTempFeeUI   = new TempFeeUI();
	   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	   
	   loggerDebug("TempFeeSave","go to bl !!!!!!!!!");
	    
	  //如果在Catch中发现异常，则不从错误类中提取错误信息
	   try
	    {
		   if(!tBusinessDelegate.submitData(tVData,"INSERT","TempFeeUI"))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = "失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content ="失败";
						FlagStr = "Fail";				
				   }
			  }
		   else
			  {
			     	Content = "操作成功! ";
			      	FlagStr = "Succ";
			      	
			      	String []errTempFeeNo=(String[])tBusinessDelegate.getResult().getObjectByObjectName("String",0);
			        if(errTempFeeNo!=null)
			        {
			          for(int n=0;n<errTempFeeNo.length;n++)
			          {
			            if(errTempFeeNo[n]==null) 
			            {
			              if(n==0) //如果没有纪录直接退出
			              {
			                ShowFinance="Y"; //显示缴纳金额信息的标记为真
			                break; 
			              }
			              Content=Content+" )  原因可能是：1-该纪录您已经录入；2-暂交费号对应的险种编码选择有误";
			              Content=Content+". 请您确认或查询后重新操作这些纪录!";
			              break;          //退出
			                             
			            }
			            if(n==0)
			            {
			             Content="操作完成,但是以下暂交费纪录已经存在,不能保存 ( ";
			            }
			            Content=Content+(n+1)+": "+errTempFeeNo[n]+" | ";
			            
			            if(n==errTempFeeNo.length-1) //如果是正常结束
			            {
			              Content=Content+" ) 请您确认或查询后重新操作这些纪录! ";
			            }
			            
			          } //end for
			          
			     	 } //end if
				     else
				     	{
				     	  ShowFinance="Y"; //显示缴纳金额信息的标记为真
				     	}
			  }	      
	    } 
	   catch(Exception ex)
	    {
	       Content = " 失败，原因:" + ex.toString();
	       FlagStr = "Fail";    
	    }    
} //页面有效区
%>                      
<html>
<script language="javascript">
       if("<%=FlagStr%>"=="Succ") //操作成功
       {
           parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");          

       }
       else//操作失败
       {
        parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
       }
</script>
</html>

