<%
//程序名称：GEdorTypeBBSubmit.jsp
//程序功能：
//创建日期：2002-10-9 
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.*"%>
 	<%@page import="com.sinosoft.lis.bq.*"%>
 	<%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page contentType="text/html;charset=GBK" %>
	<%@page import="com.sinosoft.service.*" %>

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  
  LPInsuredSchema tLPInsuredSchema   = new LPInsuredSchema();
  LPAppntSchema  tLPAppntSchema = new LPAppntSchema();
  
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
  
  //PEdorBBDetailUI  tPEdorBBDetailUI = new PEdorBBDetailUI();
//  EdorDetailUI tEdorDetailUI = new EdorDetailUI();
  String busiName="EdorDetailUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
  GEdorDetailUI tGEdorDetailUI = new GEdorDetailUI();
  GlobalInput tG = new GlobalInput();  
  tG = (GlobalInput)session.getValue("GI"); 
  //输出参数
  
  CErrors tError = null;
  //后面要执行的动作：添加，修改
 
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String IsAppntFlag="";
  /**投保人和被保人是否是同一人*/
  String AppntIsInsuredFlag=""; 
  String AppObj = "";
  
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
 
  AppntIsInsuredFlag=request.getParameter("AppntIsInsuredFlag");
  
 
   //个人保全项目表信息
    System.out.println(request.getParameter("CustomerNo"));
  	tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  	tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  	tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  	//tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
  	tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
    tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
	  tLPEdorItemSchema.setPolNo("000000");
	  tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
	  //暂时存放客户身份
	  tLPEdorItemSchema.setStandbyFlag1(AppntIsInsuredFlag);

  //只是投保人
  if("0".equals(AppntIsInsuredFlag))
  {
		
		tLPAppntSchema.setContNo(request.getParameter("ContNo"));
	    tLPAppntSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPAppntSchema.setEdorType(request.getParameter("EdorType"));
		tLPAppntSchema.setAppntNo(request.getParameter("CustomerNo"));
		tLPAppntSchema.setAppntName(request.getParameter("Name_New"));
		tLPAppntSchema.setMarriage(request.getParameter("AppntMarriage_New"));
		tLPAppntSchema.setNativePlace(request.getParameter("NativePlace_New"));
		tLPAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress_New"));
		tLPAppntSchema.setLanguage(request.getParameter("NewLanguage"));
		
  }
  //只是被保人
  if("1".equals(AppntIsInsuredFlag))
  {
		
		tLPInsuredSchema.setContNo(request.getParameter("ContNo"));
	    tLPInsuredSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPInsuredSchema.setEdorType(request.getParameter("EdorType"));
		tLPInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));
		tLPInsuredSchema.setName(request.getParameter("Name_New"));
		tLPInsuredSchema.setMarriage(request.getParameter("AppntMarriage_New"));
		tLPInsuredSchema.setNativePlace(request.getParameter("NativePlace_New"));
		tLPInsuredSchema.setRgtAddress(request.getParameter("AppntRgtAddress_New"));
		tLPInsuredSchema.setSocialInsuFlag(request.getParameter("NewSFlag"));
		tLPInsuredSchema.setLanguage(request.getParameter("NewLanguage"));
		
  }

	  
  //如果投被保人是同一个人的话则，需要处理LCAppnt,和LCInSured
  if("2".equals(AppntIsInsuredFlag))
  {
		tLPInsuredSchema.setContNo(request.getParameter("ContNo"));
	    tLPInsuredSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPInsuredSchema.setEdorType(request.getParameter("EdorType"));
		tLPInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));
		tLPInsuredSchema.setName(request.getParameter("Name_New"));
		tLPInsuredSchema.setMarriage(request.getParameter("AppntMarriage_New"));
		tLPInsuredSchema.setNativePlace(request.getParameter("NativePlace_New"));
		tLPInsuredSchema.setRgtAddress(request.getParameter("AppntRgtAddress_New"));
		tLPInsuredSchema.setSocialInsuFlag(request.getParameter("NewSFlag"));
		tLPInsuredSchema.setLanguage(request.getParameter("NewLanguage"));
		
		tLPAppntSchema.setContNo(request.getParameter("ContNo"));
	    tLPAppntSchema.setEdorNo(request.getParameter("EdorNo"));
		tLPAppntSchema.setEdorType(request.getParameter("EdorType"));
		tLPAppntSchema.setAppntNo(request.getParameter("CustomerNo"));
		tLPAppntSchema.setAppntName(request.getParameter("Name_New"));
		tLPAppntSchema.setMarriage(request.getParameter("AppntMarriage_New"));
		tLPAppntSchema.setNativePlace(request.getParameter("NativePlace_New"));
		tLPAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress_New"));
		tLPAppntSchema.setLanguage(request.getParameter("NewLanguage"));
		tLPAppntSchema.setSocialInsuFlag(request.getParameter("NewSFlag"));
		
  }
try
  {
  // 准备传输数据 VData
  
  	VData tVData = new VData();  	
  	tVData.add(tLPEdorItemSchema);
    tVData.add(tLPInsuredSchema);
    tVData.add(tLPAppntSchema);
  	tVData.add(tG);
//  	if(!tEdorDetailUI.submitData(tVData,transact))
  	if(!tBusinessDelegate.submitData(tVData,transact,busiName))
  	{
//  	  tError = tEdorDetailUI.mErrors;
  	  tError = tBusinessDelegate.getCErrors();
  	  Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
  	}else
  	{
  	
  	  Content = " 保存成功！";
    	FlagStr = "Success";
  	
  	}
       	  
	}
	catch(Exception ex)
	{
		Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
	}			
  //添加各种预处理

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

