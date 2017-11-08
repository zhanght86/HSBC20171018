<%
//程序名称：GEdorTypePRSubmit.jsp
//程序功能：提交个单迁移更改数据
//创建日期：2005-4-6 16:55 
//创建人  ：lanjun
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
 	<%@page import="com.sinosoft.lis.bq.*"%>
 	<%@page import="com.sinosoft.lis.pubfun.*"%>
 	<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %> 

<%
  LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();  
  LPAppntSchema tLPAppntSchema = new LPAppntSchema();
  LPAddressSchema tLPAddressSchema = new LPAddressSchema();
  
//  EdorDetailUI  tEdorDetailUI = new EdorDetailUI();
  	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  GlobalInput tG = new GlobalInput();  
  tG = (GlobalInput)session.getValue("GI"); 
  
  CErrors tError = null;
  
  //后面要执行的动作：添加，修改 
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";
  String manageCom = "";
  transact = request.getParameter("fmtransact");
  
  //个人保全项目表信息
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
  tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
  tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
  tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
  /*
  String theCurrentDate = PubFun.getCurrentDate();
  String theCurrentTime = PubFun.getCurrentTime();
  
  tLPEdorItemSchema.setMakeDate(theCurrentDate);
  tLPEdorItemSchema.setMakeTime(theCurrentTime);
  tLPEdorItemSchema.setModifyDate(theCurrentDate);
  tLPEdorItemSchema.setModifyTime(theCurrentTime);  
  tLPEdorItemSchema.setOperator(tG.Operator); */

  //投保人表
  tLPAppntSchema.setContNo(request.getParameter("ContNo")); 
  tLPAppntSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPAppntSchema.setEdorType(request.getParameter("EdorType"));
	//tLPAppntSchema.setAddressNo(request.getParameter("AddressNo")); 
	
  //地址信息表
	//tLPAddressSchema.setEdorNo(request.getParameter("EdorNo"));
	//tLPAddressSchema.setEdorType(request.getParameter("EdorType"));
  //tLPAddressSchema.setPostalAddress(request.getParameter("PostalAddress"));
  //tLPAddressSchema.setZipCode(request.getParameter("ZipCode"));        
  //tLPAddressSchema.setPhone(request.getParameter("Phone"));            
  //tLPAddressSchema.setFax(request.getParameter("Fax"));                
 // tLPAddressSchema.setMobile(request.getParameter("Mobile"));
  //tLPAddressSchema.setAddressNo(request.getParameter("AddressNo")); 
  
  
  manageCom = request.getParameter("ManageCom");
  
  //add by jiaqiangli 2009-02-11 迁移增加lpmove表以方便外围接口取数
  LPMoveSchema tLPMoveSchema = new LPMoveSchema();
  tLPMoveSchema.setEdorNo(request.getParameter("EdorNo"));
  tLPMoveSchema.setEdorType(request.getParameter("EdorType"));
  tLPMoveSchema.setContNoOld(request.getParameter("ContNo"));
  tLPMoveSchema.setManageComOld(request.getParameter("OldManageCom"));
  tLPMoveSchema.setManageComNew(request.getParameter("ManageCom"));
  tLPMoveSchema.setOldCoName(request.getParameter("OldManageComName"));
  tLPMoveSchema.setNewCoName(request.getParameter("ManageComName"));
  //add by jiaqiangli 2009-02-11 迁移增加lpmove表以方便外围接口取数
  		  
  try
  { 
  	VData tVData = new VData();  
  	tVData.addElement(tLPEdorItemSchema);
    //add by jiaqiangli 2009-02-11 迁移增加lpmove表以方便外围接口取数
  	tVData.addElement(tLPMoveSchema);
    //add by jiaqiangli 2009-02-11 迁移增加lpmove表以方便外围接口取数
  	//tVData.addElement(tLPAppntSchema);
	  //tVData.addElement(tLPAddressSchema);
  	tVData.addElement(tG);
  	tVData.addElement(manageCom);
  	System.out.println("LPEdorItemSchema ======================>"+ tLPEdorItemSchema.encode());
//    tEdorDetailUI.submitData(tVData,transact);
    tBusinessDelegate.submitData(tVData, transact ,busiName);		
	}
	catch(Exception ex)
	{
		Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
	}			
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
//    tError = tEdorDetailUI.mErrors;
    tError = tBusinessDelegate.getCErrors(); 
    if (!tError.needDealError())
    {                          
      Content = " 保存成功,请变更续期交费信息和联系方式信息!";
    	FlagStr = "Success";
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
   //alert("<%=Content%>");
   //top.close();
	 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

