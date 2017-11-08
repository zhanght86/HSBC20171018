<%
//程序名称：PEdorTypeFMSubmit.jsp
//程序功能：
//创建日期：2005-05-16 09:49:22
//创建人:  zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  
  <%@page import="java.util.regex.Matcher"%>
  <%@page import="java.util.regex.Pattern"%>
  
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
  <%@page import="com.sinosoft.service.*" %>

<%
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。     

		
  /** 输出参数 */
	CErrors tError = new CErrors();   
	String tRela  = "";                
	String FlagStr = "";
	String Content = "";
	String transact = "";
    String Result = "";
    

	//保全批改项目信息
	String sEdorAcceptNo= request.getParameter("EdorAcceptNo");
	String sEdorType 	= request.getParameter("EdorType");	
	String sEdorNo 		= request.getParameter("EdorNo");
	String sContNo 		= request.getParameter("ContNo");
	String sPolNo 		= request.getParameter("PolNo");
	String sInsuredNo 	= request.getParameter("InsuredNo");
	
	LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
	tLPEdorItemSchema.setEdorAcceptNo(sEdorAcceptNo);
	tLPEdorItemSchema.setEdorType(sEdorType);
	tLPEdorItemSchema.setEdorNo(sEdorNo);
	tLPEdorItemSchema.setContNo(sContNo);
	tLPEdorItemSchema.setInsuredNo(sInsuredNo);
	
	//保全变更信息
	String sPayYears 	= request.getParameter("PayYears_new");	
	
	LPPolSchema tLPPolSchema = new LPPolSchema();
	tLPPolSchema.setPayYears(sPayYears);
	tLPPolSchema.setPayEndYear(sPayYears);
	String tPayEndYearFlagName = request.getParameter("PayYears_newName");
	System.out.println("tPayEndYearFlagName"+tPayEndYearFlagName);
	
	//add by jiaqiangli 2008-12-23 由于LMRiskParamsDef.ParamsName字段描述不统一，改成正则判断，不再判断最后一位的中文字符
	String regex = "年";
	Matcher m = Pattern.compile(regex).matcher(tPayEndYearFlagName);
	System.out.println("ddddddddddddddddddtPayEndYearFlagName"+tPayEndYearFlagName);
	String tPayEndYearFlag = "";
	if (m.find())
		tPayEndYearFlag = "Y";
	regex = "月";
	m = Pattern.compile(regex).matcher(tPayEndYearFlagName);
	if (m.find())
		tPayEndYearFlag = "M";
	regex = "日";
	m = Pattern.compile(regex).matcher(tPayEndYearFlagName);
	if (m.find())
		tPayEndYearFlag = "D";
	regex = "岁";
	m = Pattern.compile(regex).matcher(tPayEndYearFlagName);
	if (m.find())
		tPayEndYearFlag = "A";
	//add by jiaqiangli 2008-12-23 由于LMRiskParamsDef.ParamsName字段描述不统一，改成正则判断，不再判断最后一位的中文字符
	tLPPolSchema.setPayEndYearFlag(tPayEndYearFlag);
	System.out.println("tLPPolSchema.getPayEndYearFlag()" + tLPPolSchema.getPayEndYearFlag());

	VData tVData = new VData();       

//	EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	try
	{		   
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);
		tVData.add(tLPPolSchema);
//		tEdorDetailUI.submitData(tVData, "EDORITEM|INPUT");
		tBusinessDelegate.submitData(tVData, "EDORITEM|INPUT" ,busiName);
	}
	catch(Exception ex)
	{
	      Content = "保存失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
//		    tError = tEdorDetailUI.mErrors;
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

