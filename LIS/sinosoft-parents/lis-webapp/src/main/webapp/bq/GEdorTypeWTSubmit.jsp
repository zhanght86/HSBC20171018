<%
//程序名称：GEdorTypeWTSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
 <%@page import="com.sinosoft.service.*" %>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  
  String busiName="bqgrpGrpEdorWTDetailUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //接收信息，并作校验处理。
  //输入参数
  //个人批改信息
  LPGrpEdorMainSchema tLPGrpEdorMainSchema   = new LPGrpEdorMainSchema();
  LPPolSet tLPPolSet = new LPPolSet();
  LPPolSchema tLPPolSchema = new LPPolSchema();

 
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
 //loggerDebug("GEdorTypeWTSubmit","-----"+tG.Operator);
  //输出参数
  CErrors tError = null;
  //后面要执行的动作：添加，修改，删除
    
  String tRela  = "";                
  String FlagStr = "";
  String Content = "";
  String Result ="";
  String transact = "";
  String mContType = "";
   
  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput)session.getValue("GI");

	transact = request.getParameter("fmtransact");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String grpContNo = request.getParameter("GrpContNo");
	//alert(grpContNo);
	String getMoney = request.getParameter("GetMoney");
	if(getMoney==null||"".equals(getMoney)){
	    getMoney="0";
	}
	
	LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	tLPGrpEdorItemSchema.setEdorNo(edorNo);
	tLPGrpEdorItemSchema.setGrpContNo(grpContNo);
	tLPGrpEdorItemSchema.setEdorType(edorType);
	tLPGrpEdorItemSchema.setGetMoney(getMoney);
        // 准备传输数据 VData		
	VData tVData = new VData();
	tVData.add(tGlobalInput);
	tVData.add(tLPGrpEdorItemSchema);
 

if (!tBusinessDelegate.submitData(tVData, "",busiName))
	{
	 loggerDebug("EGEdorTypeWTSubmit.jsp","===========11111111111111111111111111=============");
		VData rVData = tBusinessDelegate.getResult();
		Content = transact + "失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
		FlagStr = "Fail";
	}
	else 
	{
		 loggerDebug("GEdorTypeWTSubmit.jsp","===========2342423423424333333333333333333333=============");
		Content = "保存成功";
		FlagStr = "Success";
	} 
  //添加各种预处理
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","");
</script>
</html>

