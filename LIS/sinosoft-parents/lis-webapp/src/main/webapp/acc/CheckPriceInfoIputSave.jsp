<%
//程序名称：LOAccUnitPriceSave.jsp
//程序功能：
//创建日期：2002-08-19
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
   <%@page import="com.sinosoft.lis.acc.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%

  //接收信息，并作校验处理。
  //输入参数
  String DoBatch="";

  String tBmCert = "";
  //后面要执行的动作：添加，修改，删除
  String transact = "";

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  LOAccUnitPriceSchema tLOAccUnitPriceSchema   = new LOAccUnitPriceSchema();
  //LOAccUnitPriceUI tLOAccUnitPriceUI   = new LOAccUnitPriceUI();
  //  LOAccUnitPriceSet tLOAccUnitPriceSet=new LOAccUnitPriceSet();
     GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getAttribute("GI");
  //输出参数
  CErrors tError =new CErrors();

  DoBatch= request.getParameter("DoBatch");
  if( DoBatch.equals("OK"))
  {
     //DealInsuAcc tDealInsuAcc=new DealInsuAcc(tGI);
     String busiName="DealInsuAcc";
	 BusinessDelegate iBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
	 VData tVData=new VData();
	 TransferData tTransferData = new TransferData();
	 tTransferData.setNameAndValue("DealDate",request.getParameter("DealDate"));
	 tTransferData.setNameAndValue("NextPriceDate",request.getParameter("NextPriceDate"));
     tVData.add(tTransferData);
     tVData.add(tGI);
     try
     {
    	 if(!iBusinessDelegate.submitData(tVData,"DoBatch",busiName))
	   //if (!tDealInsuAcc.dealInsuAcc(request.getParameter("DealDate"),request.getParameter("NextPriceDate")))
	   {
       	 FlagStr="fail";
	     Content ="批处理失败";
	     //tError = tDealInsuAcc.mErrors;
	     tError = iBusinessDelegate.getCErrors();
    	 if (tError.needDealError())
    	 {
    		 Content = Content+"，原因是："+ tError.getFirstError();
    	 }
	   }else{
	     FlagStr="Succ";
	     Content ="批处理完毕";
	   }
     }
     catch(Exception ex)
     {
       Content = Content+"原因是:" + ex.toString();
       FlagStr = "Fail";
     }
  }else{

	if(FlagStr=="")
	{
	    //tLOAccUnitPriceSchema.setRiskCode(request.getParameter("RiskCode"));
	
	    tLOAccUnitPriceSchema.setInsuAccNo(request.getParameter("InsuAccNo"));
	
	    tLOAccUnitPriceSchema.setStartDate(request.getParameter("StartDate"));
	
	    tLOAccUnitPriceSchema.setEndDate(request.getParameter("EndDate"));
	
	    tLOAccUnitPriceSchema.setInvestFlag(request.getParameter("InvestFlag"));
	
	    tLOAccUnitPriceSchema.setSRateDate(request.getParameter("SRateDate"));
	
	    tLOAccUnitPriceSchema.setARateDate(request.getParameter("ARateDate"));
	
	    tLOAccUnitPriceSchema.setUnitPriceBuy(request.getParameter("UnitPriceBuy"));
	
	    tLOAccUnitPriceSchema.setUnitPriceSell(request.getParameter("UnitPriceSell"));
	
	    tLOAccUnitPriceSchema.setRedeemRate(request.getParameter("RedeemRate"));
	
	    tLOAccUnitPriceSchema.setState(request.getParameter("State"));
	
	    tLOAccUnitPriceSchema.setRedeemMoney(request.getParameter("RedeemMoney"));
	
	    tLOAccUnitPriceSchema.setOperator(tGI.Operator);
	
	
	    transact=request.getParameter("Transact");
	
	    String busiName="LOAccUnitPriceUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  try
		  {
		    // 准备传输数据 VData
		
		    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		    VData tVData=new VData();
		    tVData.add(tLOAccUnitPriceSchema);
		    tVData.add(tGI);
		    loggerDebug("CheckPriceInfoIputSave","+_+......"+transact);
		    //tLOAccUnitPriceUI.submitData(tVData,transact);
		    tBusinessDelegate.submitData(tVData, transact,busiName);
		  }
		  catch(Exception ex)
		  {
		    Content = transact+"失败，原因是:" + ex.toString();
		    FlagStr = "Fail";
		  }
		  //如果在Catch中发现异常，则不从错误类中提取错误信息
		  if (FlagStr=="")
		  {
		    //tError = tLOAccUnitPriceUI.mErrors;
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {
		      if(transact.equals("INSERT"))
		      {
		        transact="录入";
		      }
		      if(transact.equals("CONFIRM"))
		      {
		        transact="生效";
		      }
		      if(transact.equals("DELETE"))
		      {
		        transact="删除";
		      }
		      Content = transact+"成功";
		      FlagStr = "Succ";
		    }
		    else
		    {
		      Content = transact+" 失败，原因是:" + tError.getFirstError();
		      FlagStr = "Fail";
		    }
		  }
	}else{
	}

}
  //添加各种预处理
%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<script language="javascript">
	
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//alert("Flag-01");
	//
	//alert("Flag-02");
	
</script>
</html>

