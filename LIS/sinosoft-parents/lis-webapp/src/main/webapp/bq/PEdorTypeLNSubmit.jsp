<%
//程序名称：PEdorTypeLNSubmit.jsp
//程序功能：
//创建日期：2005-07-07
//创建人  ：Nicholas
//修改时间：2005-07
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
  //个人批改信息
  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
  //后面要执行的动作：
  CErrors tError = null;                 
  String FlagStr = "";
  String Content = "";
  String transact = "";
  Double tInterest = new Double(0.0);
  
  //transact = request.getParameter("fmtransact");
  transact = "INSERT||MAIN";
  //System.out.println("---transact: " + transact);  
  
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	
  //个人保单批改信息
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
	tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
	tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 

  //总还款金额
  Double tempDouble = new Double(request.getParameter("MaxLoan"));
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("Currency",request.getParameter("Currency"));
  try 
  {
    // 准备传输数据 VData
    VData tVData = new VData();  
  	
    //保存个人保单信息(保全)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
		 tVData.add(tempDouble);
		 tVData.add(tTransferData);
    
//     boolean tag = tEdorDetailUI.submitData(tVData,transact);
     boolean tag = tBusinessDelegate.submitData(tVData,transact,busiName);
  } 
  catch(Exception ex) 
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "") 
  {
//  	tError = tEdorDetailUI.mErrors;
  	tError = tBusinessDelegate.getCErrors(); 
  	if (!tError.needDealError()) 
  	{        
  		FlagStr = "Success";                  
      Content = "保存成功！";
//  		if (tEdorDetailUI.getResult()!=null && tEdorDetailUI.getResult().size()>0)
  		if (tBusinessDelegate.getResult()!=null && tBusinessDelegate.getResult().size()>0)
  	  {
  	  	VData tResult = new VData();
//  	  	tResult = tEdorDetailUI.getResult();
  	  	tResult = tBusinessDelegate.getResult();
  		  if (tResult==null || tResult.size()<=0) 
  		  {
  				FlagStr = "Fail"; 
  				Content = "操作已完成，但未获得返回信息，请重新保存！";
  		  }
  		  else
  		  {
  		  	tInterest = (Double)tResult.getObjectByObjectName("Double", 0);
  		  	//tInterest = (Double)tResult.get(0);
  		  }
  	  }
    } 
    else  
    {
  		Content = " 保存失败，原因是:" + tError.getFirstError();
  		FlagStr = "Fail";
  	}
	}

%>   
                   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%=String.valueOf(tInterest.doubleValue())%>");
</script>
</html>

