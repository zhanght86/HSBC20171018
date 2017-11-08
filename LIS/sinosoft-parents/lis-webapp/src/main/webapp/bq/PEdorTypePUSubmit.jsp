<%
//程序名称：PEdorTypePUSubmit.jsp
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
  TransferData tTransferData = new TransferData();
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
	
	//个别险种处理
	tTransferData.setNameAndValue("InsuYear",request.getParameter("InsuYear"));
	tTransferData.setNameAndValue("GetDutyKind",request.getParameter("GetDutyKind"));

  try 
  {
    // 准备传输数据 VData
    VData tVData = new VData();  
  	
    //保存个人保单信息(保全)	
		 tVData.add(tG);
		 tVData.add(tLPEdorItemSchema);
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
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

