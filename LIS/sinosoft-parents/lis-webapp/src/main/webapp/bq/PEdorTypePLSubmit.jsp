<%
//程序名称：PEdorTypePLSubmit.jsp
//程序功能：
//创建日期：2005-5-8 10:47上午
//创建人  ：Lihs
//更新记录：  更新人    更新日期     更新原因/内容
//             liurx    2005-06-28
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
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  System.out.println("-----PLsubmit---");
  
  String FlagStr = "";
  String Content = "";
  
  String lostDate = request.getParameter("ReportByLoseDate"); 
  String mCurrentDate = PubFun.getCurrentDate();
  String mCurrentTime = PubFun.getCurrentTime();
  String mSignDate = request.getParameter("SignDate");
  String tLostFlag = request.getParameter("LostFlag");
  String tContNo = request.getParameter("ContNo");
  String tEdorNo = request.getParameter("EdorNo");
  String tEdorType = request.getParameter("EdorType");
  String tEdorAcceptNo = request.getParameter("EdorAcceptNo"); 
  FDate mFDate = new FDate();
  if(lostDate!=null && !lostDate.trim().equals("") && !lostDate.trim().equals("0000-00-00")&&(mFDate.getDate(lostDate).after(mFDate.getDate(mCurrentDate)) || (mSignDate!=null&&!mSignDate.trim().equals("")&&mFDate.getDate(lostDate).before(mFDate.getDate(mSignDate)))) )
  {
      		Content = " 保存失败:丢失日期不能晚于今天,或早于签单日期！" ;
    		FlagStr = "Fail";
  }
  else
  {
     LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
     TransferData tTransferData = new TransferData();
//     EdorDetailUI tEdorDetailUI = new EdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
     CErrors tError = null;

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
    
    tLPEdorItemSchema.setEdorAcceptNo(tEdorAcceptNo);
	tLPEdorItemSchema.setContNo(tContNo);
    tLPEdorItemSchema.setEdorNo(tEdorNo);
	tLPEdorItemSchema.setEdorType(tEdorType);		
	//tLPEdorItemSchema.setInsuredNo("000000");
	//tLPEdorItemSchema.setPolNo("000000");
    tLPEdorItemSchema.setModifyDate(mCurrentDate);
    tLPEdorItemSchema.setModifyTime(mCurrentTime);  
    tLPEdorItemSchema.setOperator(tG.Operator);
    
    tTransferData.setNameAndValue("LostFlag",tLostFlag);
      
    //如果是挂失，则需要传送挂失类型等数据
    if(tLostFlag.equals("1"))
    {
      System.out.println("report lost:");
      tLPEdorItemSchema.setEdorReasonCode(request.getParameter("ReportByLoseReason"));
      tLPEdorItemSchema.setEdorReason(request.getParameter("ReportByLoseReasonName"));
      String tRemark = "丢失日期:"+lostDate;
      tTransferData.setNameAndValue("StateReason",request.getParameter("ReportByLoseReason"));
      tTransferData.setNameAndValue("Remark",tRemark);
      tTransferData.setNameAndValue("LostDate",lostDate);
    }
    else
    { 
      System.out.println("cancel lost:");
    }
 


  try {
     // 准备传输数据 VData
  
  	 VData tVData = new VData();  

  	
	 //保存个人保单信息(保全)	
      tVData.addElement(tG);
      tVData.addElement(tLPEdorItemSchema);
      tVData.addElement(tTransferData);
//      boolean tag = tEdorDetailUI.submitData(tVData,""); 
      boolean tag = tBusinessDelegate.submitData(tVData,"",busiName);    
   } catch(Exception ex) {
		Content = "操作失败，原因是:" + ex.toString();
    	FlagStr = "Fail";
	 }			
    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr=="") {
  		System.out.println("------success");
//    	tError = tEdorDetailUI.mErrors;
    	tError = tBusinessDelegate.getCErrors(); 
    	if (!tError.needDealError()) {                          
        Content = " 保存成功";
    		FlagStr = "Success";  
    	 }else{
    		Content = " 保存失败，原因是:" + tError.getFirstError();
    		FlagStr = "Fail";
    	 }
  	}
 }
%>                      
<html>
<script language="javascript">
   
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

