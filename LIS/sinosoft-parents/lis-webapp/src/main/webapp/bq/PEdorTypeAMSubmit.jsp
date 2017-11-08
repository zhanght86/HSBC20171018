<%
//程序名称：PEdorTypeAMSubmit.jsp
//程序功能：
//创建日期：2008-10-9 
//创建人  ：pst
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
	  //保留原来的地址编号
	  tLPEdorItemSchema.setStandbyFlag2(request.getParameter("AddressNo"));
	  
  LPAddressSchema tLPAddressSchema   = new LPAddressSchema();	  

  tLPAddressSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPAddressSchema.setEdorType(request.getParameter("EdorType"));	
	tLPAddressSchema.setCustomerNo(request.getParameter("CustomerNo"));
	tLPAddressSchema.setAddressNo(request.getParameter("AddressNo"));
		
	tLPAddressSchema.setPostalAddress(request.getParameter("PostalAddress_New"));
	tLPAddressSchema.setZipCode(request.getParameter("ZipCode_New"));
	tLPAddressSchema.setHomeAddress(request.getParameter("HomeAddress_New"));
	tLPAddressSchema.setHomeZipCode(request.getParameter("HomeZipCode_New"));
	tLPAddressSchema.setMobile(request.getParameter("Mobile_New"));
  tLPAddressSchema.setPhone(request.getParameter("GrpPhone_New"));

	tLPAddressSchema.setEMail(request.getParameter("EMail_New"));
	tLPAddressSchema.setGrpName(request.getParameter("GrpName_New"));

  
try
  {
  // 准备传输数据 VData
  
  	VData tVData = new VData();  	
  	tVData.add(tLPEdorItemSchema);
    tVData.add(tLPAddressSchema);
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

