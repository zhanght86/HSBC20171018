<%
//程序名称：GEdorAppSave.jsp
//程序功能：团单保全申请
//创建日期：2005-08-16 16:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//			zhangtao	2005-04-28
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	String busiName="EdorWorkFlowUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。     
     
    LPEdorAppSchema tLPEdorAppSchema=new LPEdorAppSchema(); 
    tLPEdorAppSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo")); //保全受理号
    tLPEdorAppSchema.setOtherNo(request.getParameter("ContNoApp")); //申请号码 团体保单
    tLPEdorAppSchema.setPostalAddress(request.getParameter("OtherNo")); //借用该字段保存兼业保单号
    tLPEdorAppSchema.setOtherNoType(request.getParameter("OtherNoType")); //申请号码类型
    tLPEdorAppSchema.setEdorAppName(request.getParameter("EdorAppName")); //申请人名称
    tLPEdorAppSchema.setAppType(request.getParameter("AppType")); //申请方式    
	tLPEdorAppSchema.setEdorAppDate(request.getParameter("EdorAppDate")); //批改申请日期
	tLPEdorAppSchema.setBankCode(request.getParameter("BankCode"));
	tLPEdorAppSchema.setBankAccNo(request.getParameter("BankAccNo"));
	tLPEdorAppSchema.setAccName(request.getParameter("AccName"));
	
	tLPEdorAppSchema.setPayGetName(request.getParameter("PayGetName"));  //补退费领取人
	tLPEdorAppSchema.setPersonID(request.getParameter("PersonID"));  //补退费领取人身份证号
	
	tLPEdorAppSchema.setManageCom(tG.ManageCom); //管理机构	
	tLPEdorAppSchema.setEdorState("3");

		
  /** 输出参数 */
	CErrors tError = new CErrors();   
	String tRela  = "";                
	String FlagStr = "";
	String Content = "";
	String transact = request.getParameter("LoadFlag");
    String Result = "";
    String sActivityID = "";
    if (transact.equals("edorApp"))
    {
    	sActivityID = "0000008002";
    }
    else if (transact.equals("scanApp"))
    {
    	sActivityID = "0000008001";
    }
    else if (transact.equals("edorTest"))
    {
    	sActivityID = "0000008092";
    }
  	/**  为工作流[保全申请确认]、[保全撤销]节点准备数据  */
	TransferData mTransferData = new TransferData();
	String sOtherNoType = request.getParameter("OtherNoType");
	mTransferData.setNameAndValue("EdorAcceptNo", request.getParameter("EdorAcceptNo"));   			
	mTransferData.setNameAndValue("MissionID", request.getParameter("MissionID"));
	mTransferData.setNameAndValue("SubMissionID", request.getParameter("SubMissionID"));	
	mTransferData.setNameAndValue("OtherNo", request.getParameter("ContNoApp"));
	mTransferData.setNameAndValue("OtherNoType", request.getParameter("OtherNoType"));
	mTransferData.setNameAndValue("EdorAppName", request.getParameter("EdorAppName"));
	mTransferData.setNameAndValue("Apptype", request.getParameter("AppType"));
	mTransferData.setNameAndValue("EdorAppDate", request.getParameter("EdorAppDate"));	
	mTransferData.setNameAndValue("ManageCom",tG.ManageCom );          
	mTransferData.setNameAndValue("EdorState","3");	
	mTransferData.setNameAndValue("NodeID",sActivityID);		
	mTransferData.setNameAndValue("Transact","INSERT||EDORAPP");
	if (sOtherNoType != null && sOtherNoType.trim().equals("4"))
	{
		mTransferData.setNameAndValue("AppntName", request.getParameter("GrpName"));
		mTransferData.setNameAndValue("PaytoDate", "");
	}
	else
	{
		mTransferData.setNameAndValue("AppntName", request.getParameter("GrpName2"));
		mTransferData.setNameAndValue("PaytoDate", "");
	}
	VData tVData = new VData();       

	
	
	try
	{		   
		tVData.addElement(tG);
		tVData.addElement(tLPEdorAppSchema);
		tVData.add(mTransferData);
		tBusinessDelegate.submitData(tVData, sActivityID,busiName);
	}
	catch(Exception ex)
	{
	      Content = "保存失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
				Content ="保存成功！";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "保存失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

