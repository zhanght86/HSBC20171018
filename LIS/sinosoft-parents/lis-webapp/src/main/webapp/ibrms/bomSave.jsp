<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：bomSave.jsp
	//程序功能：添加，修改BOM对象
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
<%
	//接收信息，并作校验处理。
	//输入参数
	LRBOMSchema tLRBOMSchema = new LRBOMSchema();
	//LDBomUI tLDBomUI = new LDBomUI();
	//输出参数
	CErrors tError = null;
	String tBmCert = "";
	//后面要执行的动作：添加，修改
	String transact = request.getParameter("Transact");
	loggerDebug("bomSave","transact:"+transact);
	
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	//    if(request.getParameter("eName").length()>0)
	tLRBOMSchema.setName(request.getParameter("eName").trim());
	//    if(request.getParameter("cName").length()>0)
	tLRBOMSchema.setCNName(request.getParameter("cName").trim());
	tLRBOMSchema.setFBOM(request.getParameter("fBOM"));
	tLRBOMSchema.setLocalItem(request.getParameter("localItem").trim());
	tLRBOMSchema.setFatherItem(request.getParameter("fatherItem").trim());
	tLRBOMSchema.setBOMLevel(request.getParameter("bomLevel").trim());
	//    if(request.getParameter("Business").length()>0)
	tLRBOMSchema.setBusiness(request.getParameter("Business").trim());
	//    if(request.getParameter("Description").length()>0)
	tLRBOMSchema.setDiscription(request.getParameter("Description").trim());
	//    if(request.getParameter("Valid").length()>0)
	tLRBOMSchema.setSource("com.sinosoft.ibrms.bom."+request.getParameter("eName").trim());	
	tLRBOMSchema.setValid(request.getParameter("Valid").trim());
	
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.addElement(tLRBOMSchema);

		//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		String busiName="LDBomUI";
    	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		if(!tBusinessDelegate.submitData(tVData, transact,busiName))
		{
			if (FlagStr == "") {
				tError = tBusinessDelegate.getCErrors();
				if (!tError.needDealError()) {
					loggerDebug("bomSave","成功");
					Content = transact + " 成功";
					FlagStr = "Succ";
					
				} else {
					loggerDebug("bomSave","失败");
					Content = transact + " 失败，原因是:" + tError.getFirstError();
					FlagStr = "Fail";
				}
			}	
		}			
	loggerDebug("bomSave","FlagStr:"+FlagStr);
%>

<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
