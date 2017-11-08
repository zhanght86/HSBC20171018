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
	tLPEdorItemSchema.setPolNo(sPolNo);
	tLPEdorItemSchema.setInsuredNo(sInsuredNo);
	
	//保全变更信息
	String sPayIntv 	= request.getParameter("PayIntv_new");	
	
	LPPolSchema tLPPolSchema = new LPPolSchema();
	tLPPolSchema.setPayIntv(sPayIntv);

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

