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
<%
	//接收信息，并作校验处理。
	//输入参数
	LDMsgInfo_BOMSchema tLDMsgInfo_BOMSchema = new LDMsgInfo_BOMSchema();
	LDMsgInfoUI tLDMsgInfoUI = new LDMsgInfoUI();
	//输出参数
	CErrors tError = null;
	String tBmCert = "";
	//后面要执行的动作：添加，修改
	String transact = request.getParameter("hiddenAction");
	loggerDebug("LDMsgInfoInputSave","transact:"+transact);
	
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	//    if(request.getParameter("eName").length()>0)
	tLDMsgInfo_BOMSchema.setLanguage(request.getParameter("MsgLan").trim());
	//    if(request.getParameter("cName").length()>0)
	tLDMsgInfo_BOMSchema.setKeyID(request.getParameter("hiddenKeyID").trim());
	tLDMsgInfo_BOMSchema.setMsg(request.getParameter("MsgDetail"));
	tLDMsgInfo_BOMSchema.setMsgType(request.getParameter("MsgType"));
	
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.addElement(tLDMsgInfo_BOMSchema);
		tVData.addElement(transact);
		//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		
		if(!tLDMsgInfoUI.submitData(tVData, "MSGBOM"))
		{
			if (FlagStr == "") {
				tError = tLDMsgInfoUI.mErrors;
				if (!tError.needDealError()) {
					loggerDebug("LDMsgInfoInputSave","成功");
					Content = "保存成功";
					FlagStr = "Succ";
					
				} else {
					loggerDebug("LDMsgInfoInputSave","失败");
					Content = transact + "保存失败，原因是:" + tError.getFirstError();
					FlagStr = "Fail";
				}
			}	
		}			
	loggerDebug("LDMsgInfoInputSave","FlagStr:"+FlagStr);
%>

<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
