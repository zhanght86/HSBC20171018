<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：ApplyIssueDocSave.jsp
	//程序功能：
	//创建日期：2006-04-07
	//创建人  ：wentao
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	loggerDebug("QueryOtherDocSave","-------------- start save  --------------");
	//输出参数
	String FlagStr = "Succ";
	String Content = "删除成功";
	String tContent = request.getParameter("Content");
	//  String tGridNo[] = request.getParameterValues("CodeGridNo");  	//得到序号列的所有值
	String tChk[] = request.getParameterValues("InpCodeGridChk"); //参数格式="MulLine对象名+Chk"
	String tBussNo[] = request.getParameterValues("CodeGrid1"); //得到第2列的所有值
	String tComCode[] = request.getParameterValues("CodeGrid2"); //得到第6列的所有值
	String tApplyOperator[] = request.getParameterValues("CodeGrid4"); //得到第3列的所有值
	String tReason[] = request.getParameterValues("CodeGrid8");

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	MMap map = new MMap();
	for (int index = 0; index < tChk.length; index++) {
		if (tChk[index].equals("1")) {
			//选中的行
			LWMissionDB tLWMissionDB = new LWMissionDB();
			LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery("select * from LWMission where ProcessID='0000000010' and "+
					"ActivityID in('0000003001','0000003002') and MissionProp1='"+tBussNo[index]+
					"' and MissionProp2='"+tReason[index]+"' and MissionProp3='"+tApplyOperator[index]+"'");
			if (tLWMissionSet.size() == 0) {
				FlagStr = "Fail";
				Content = "单证" + tBussNo[index] + "未被申请";
				break;
			}

			if (tLWMissionSet.size() > 1) {
				FlagStr = "Fail";
				Content = "单证" + tBussNo[index] + "被申请了多次";
				break;
			}
			map.put(tLWMissionSet, "DELETE");
		}
	}
	if (FlagStr.equals("Succ")) {
		String busiName="easyscanQueryOtherDocSaveUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//QueryOtherDocSaveUI tQueryOtherDocSaveUI = new QueryOtherDocSaveUI();
		//PubSubmit ps = new PubSubmit();
		VData tVData = new VData();
		tVData.add(map);
		if (!tBusinessDelegate.submitData(tVData,"",busiName)) {
			FlagStr = "Fail";
			Content = PubFun.changForJavaScript(tBusinessDelegate.getCErrors().getErrContent());
		}
	}
%>
<%@page import="com.sinosoft.workflowengine.ActivityOperator"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.vdb.LWMissionDBSet"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
