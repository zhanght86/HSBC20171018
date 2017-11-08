<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：LABranchGroupSave.jsp
//程序功能：
//创建日期：2002-08-16 15:12:33
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.wagecal.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //接收信息，并作校验处理。
  //输入参数
  LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();

  //ALABranchGroupUI tLABranchGroup = new ALABranchGroupUI();

  //输出参数
  CErrors tError = null;
  String tOperate=request.getParameter("hideOperate");
  tOperate=tOperate.trim();
  String tRela  = "";                
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();

	//tG.Operator = "Admin";
	//tG.ComCode  = "001";
  //session.putValue("GI",tG);

  tG=(GlobalInput)session.getValue("GI");


    tLABranchGroupSchema.setAgentGroup(request.getParameter("AgentGroup"));
    tLABranchGroupSchema.setName(request.getParameter("Name"));
    tLABranchGroupSchema.setManageCom(request.getParameter("ManageCom"));
    tLABranchGroupSchema.setUpBranch(request.getParameter("UpBranch"));
    tLABranchGroupSchema.setBranchAttr(request.getParameter("BranchAttr"));
    tLABranchGroupSchema.setBranchType(request.getParameter("BranchType"));
    loggerDebug("LABranchGroupSave","BranchType:"+request.getParameter("BranchType"));
    tLABranchGroupSchema.setBranchLevel(request.getParameter("BranchLevel"));
  //  tLABranchGroupSchema.setBranchManager(request.getParameter("BranchManager"));
 //   tLABranchGroupSchema.setBranchManagerName(request.getParameter("BranchManagerName"));
    tLABranchGroupSchema.setBranchAddressCode(request.getParameter("BranchAddressCode"));
    tLABranchGroupSchema.setBranchAddress(request.getParameter("BranchAddress"));
    tLABranchGroupSchema.setBranchPhone(request.getParameter("BranchPhone"));
    tLABranchGroupSchema.setBranchFax(request.getParameter("BranchFax"));
    tLABranchGroupSchema.setBranchZipcode(request.getParameter("BranchZipcode"));
    tLABranchGroupSchema.setFoundDate(request.getParameter("FoundDate"));
    tLABranchGroupSchema.setEndDate(request.getParameter("EndDate"));
    tLABranchGroupSchema.setEndFlag(request.getParameter("EndFlag"));
    tLABranchGroupSchema.setCertifyFlag(request.getParameter("CertifyFlag"));
    tLABranchGroupSchema.setFieldFlag(request.getParameter("FieldFlag"));
    tLABranchGroupSchema.setState(request.getParameter("State"));
    tLABranchGroupSchema.setOperator(request.getParameter("Operator"));
    tLABranchGroupSchema.setUpBranchAttr(request.getParameter("UpBranchAttr"));

  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tLABranchGroupSchema);
	tVData.add(tG);
	/*
  try
  {
    loggerDebug("LABranchGroupSave","come in");
    tLABranchGroup.submitData(tVData,tOperate);
    loggerDebug("LABranchGroupSave","come out");
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  
  if (!FlagStr.equals("Fail"))
  {
    tError = tLABranchGroup.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " 保存成功! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }*/
  String busiName="ALABranchGroupUI";
  String mDescType="保存";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"失败";
				FlagStr = "Fail";				
		   }
	  }
	  else
	  {
	     	Content = mDescType+"成功! ";
	      	FlagStr = "Succ";  
	  }
  
  //添加各种预处理

%>                      
<html>
<script language="javascript">
        parent.fraInterface.document.all('Operator').value = "<%=tG.Operator%>";
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

