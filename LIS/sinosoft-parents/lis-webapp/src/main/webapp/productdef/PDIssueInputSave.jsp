<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDIssueInputSave.jsp
//程序功能：问题件录入
//创建日期：2009-4-2
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.workflow.proddef.ProdDefWorkFlowBL"%>
 <%@page import="com.sinosoft.service.*" %>    
<%
 //接收信息，并作校验处理。
 //输入参数
 

// PDIssueInputBL pDIssueInputBL = new PDIssueInputBL();
 
 CErrors tError = null;          
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 
 String tBackPost = request.getParameter("BackPost");

 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
 transferData.setNameAndValue("BackPost",request.getParameter("BackPost"));
 transferData.setNameAndValue("PostCode",request.getParameter("PostCode"));
 transferData.setNameAndValue("IssueType",request.getParameter("IssueType"));
 transferData.setNameAndValue("IssueContDesc",request.getParameter("IssueContDesc"));
 transferData.setNameAndValue("Filepath",request.getParameter("Filepath2"));
 transferData.setNameAndValue("Filename",request.getParameter("Filename2"));
 transferData.setNameAndValue("SerialNo",request.getParameter("SerialNo"));
 
 boolean needTransfer = false;
 try
 {
  	// 准备传输数据 VData
  	VData tVData = new VData();

  	tVData.add(tG);
  	tVData.add(transferData);
  	//pDIssueInputBL.submitData(tVData,operator);
  	String busiName="PDIssueInputBL";
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //String tDiscountCode = "";
    if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
  	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
    	FlagStr = "Fail";
    }
    else {
      Content = " "+"处理成功!"+" ";
    	FlagStr = "Success";
    	needTransfer = (Boolean)tBusinessDelegate.getResult().get(0);
    }
   
   }
   catch(Exception ex)
   {
    Content = ""+"保存失败，原因是:"+"" + ex.toString();
    FlagStr = "Fail";
   }

   
 System.out.println("------------in PDIssueInputSave.jsp，是否处理工作流条件---FlagStr:["+FlagStr+"];operator:["+ operator+"];IsNeadTransfer:"+needTransfer+";BackPost:"+request.getParameter("BackPost"));
 
  //开始处理工作流
  if( FlagStr == "Success" && operator.equals("deal") && needTransfer)
  {
	     transferData = new TransferData();
		 transferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
		 transferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
		 transferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
	     transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
		 transferData.setNameAndValue("RequDate",request.getParameter("RequDate"));
		 transferData.setNameAndValue("Operator",tG.Operator);

		 if( "00".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("IsBaseInfoDone","1");
			 operator = "workflow";
		 }else if ( "10".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
			 operator = "cont";
		 }else if ( "11".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
			 operator = "edor";
		 }else if ( "12".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
			 operator = "claim";
		 }else if ( "13".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
			 operator = "lfrisk";
		 }
		 
		// ProdDefWorkFlowBL tProdDefWorkFlowBL= new ProdDefWorkFlowBL();
 		 
		 VData data = new VData();
   	     data.add(tG);
		 data.add(transferData);
		 String busiName="ProdDefWorkFlowBL";
		    
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		    
		 boolean flag = tBusinessDelegate.submitData(data,operator,busiName);
		 System.out.println("工作流跳转处理结果："+flag);
		 if (flag)
		 {                     
		  	 Content = " "+"操作成功!"+" ";
		   	 FlagStr = "Success";
		 }
		 else                                                                           
		 {
		   	 Content = " "+"保存失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
		   	 FlagStr = "Fail";
		 }
  }
  
 //添加各种预处理
%>                      

<html>
<script type="text/javascript">	
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 
