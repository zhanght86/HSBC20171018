<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//程序名称：itemSave.jsp
	//程序功能：添加，修改词条
	//创建日期： 
	//创建人  ： 
	//更新记录：
	//
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.ibrms.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<SCRIPT src="./bomAddInput.js"></SCRIPT>
<%
	//输入参数
	LRBOMItemSchema tLRBOMItemSchema = new LRBOMItemSchema();
	//LDItemUI tLDItemUI = new LDItemUI();
	//输出参数
	CErrors tError = null;
	String tBmCert = "";
	//后面要执行的动作：添加，修改
	//后面要执行的动作：添加，修改
	String transact = request.getParameter("Transact");
	loggerDebug("itemSave","transact:" + transact);

	String tRela = "";
	String FlagStr = "";
	String Content = "";
	//    if(request.getParameter("ItemEName").length()>0)
	tLRBOMItemSchema.setName(request.getParameter("ItemEName").trim());
	//    if(request.getParameter("cName").length()>0)
	tLRBOMItemSchema.setCNName(request.getParameter("CNName").trim());
	//    if(request.getParameter("bomName").length()>0)
	tLRBOMItemSchema.setBOMName(request.getParameter("bomName").trim());
	//    if(request.getParameter("IsHierarchical").length()>0)
	tLRBOMItemSchema.setIsHierarchical(request.getParameter(
			"IsHierarchical").trim());
	//  if(request.getParameter("Connector").length()>0)
	tLRBOMItemSchema.setConnector(request.getParameter("Connector")
			.trim());
	//    if(request.getParameter("IsBase").length()>0)
	tLRBOMItemSchema.setIsBase(request.getParameter("IsBase").trim());
	//    if(request.getParameter("SourceType").length()>0)
	tLRBOMItemSchema.setSourceType(request.getParameter("SourceType")
			.trim());
	//    if(request.getParameter("Source").length()>0)
	tLRBOMItemSchema.setSource(request.getParameter("Source").trim());
	//    if(request.getParameter("CommandType").length()>0)
	tLRBOMItemSchema.setCommandType(request.getParameter("CommandType")
			.trim());
	//    if(request.getParameter("PreCheck").length()>0)
	tLRBOMItemSchema.setPreCheck(request.getParameter("PreCheck")
			.trim());
	//    if(request.getParameter("Description").length()>0)
	tLRBOMItemSchema.setDescription(request.getParameter("Description")
			.trim());
	//    if(request.getParameter("Valid").length()>0)
	tLRBOMItemSchema.setValid(request.getParameter("Valid").trim());

	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.addElement(tLRBOMItemSchema);

	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		String busiName="LDItemUI";
    	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData, transact,busiName)) {
		tError = tBusinessDelegate.getCErrors();
		if (tError.needDealError()) {
			loggerDebug("itemSave","失败");
			Content = transact + " 失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	} else {
		loggerDebug("itemSave","成功");
		Content = transact + " 成功";
		FlagStr = "Succ";
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

