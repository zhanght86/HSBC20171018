<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//EdorApproveSave.jsp
	//程序功能：保全审批
	//创建日期：2005-05-08 15:59:52
	//创建人  ：sinosoft
	//更新记录：  更新人    更新日期     更新原因/内容
	//
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
    <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	//输出参数
	String FlagStr = "";
	String Content = "";


	CErrors tError = new CErrors();

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	

	//问卷类型
	String tOperate = request.getParameter("AskOperate");
	if ("INSERT".equals(tOperate)) {
		//函件类型 
		String tAskFlag = request.getParameter("AskFlag");
		//函件内容
		String tAskContent = request.getParameter("AskContent");

		String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
		String tContNo = request.getParameter("ContNo");
		//String tOtherNoType = request.getParameter("OtherNoType");
		//String tEdorAppName = request.getParameter("EdorAppName");

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(tEdorAcceptNo);
		if (tLPEdorAppDB.getInfo()) {
			LPEdorAppSchema tLPEdorAppSchema = tLPEdorAppDB.getSchema();
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

			String strNoLimit = PubFun.getNoLimit(tG.ManageCom);
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(tContNo);
			tLOPRTManagerSchema.setOtherNoType("02"); // 个单保单号
			tLOPRTManagerSchema.setCode("BQ38"); // 打印类型
			tLOPRTManagerSchema.setManageCom(tG.ManageCom); // 管理机构
			tLOPRTManagerSchema.setAgentCode(""); // 代理人编码
			tLOPRTManagerSchema.setReqCom(tG.ComCode);
			tLOPRTManagerSchema.setReqOperator(tG.Operator);
			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			tLOPRTManagerSchema.setStateFlag("A"); // 初始化下发状态 A-ask R-reply
			tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setStandbyFlag1(tEdorAcceptNo); //受理号
			tLOPRTManagerSchema.setStandbyFlag2(tG.Operator); //提出问卷人
			tLOPRTManagerSchema.setStandbyFlag3(tAskFlag); //问卷类型 1 人工核保 2 保全审批
			tLOPRTManagerSchema.setStandbyFlag4(tLPEdorAppSchema.getOperator()); //申请岗的人进行回复
			tLOPRTManagerSchema.setStandbyFlag5(tAskContent); //问题卷内容
			tLOPRTManagerSchema.setStandbyFlag6(""); //回复意见
			tLOPRTManagerSchema.setStandbyFlag7(""); //回复时间
			tLOPRTManagerSchema.setDoneTime("");
			MMap tMap = new MMap();
			tMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
			tVData.add(tMap);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(tVData, "")) {
				tError.addOneError(tSubmit.mErrors.getFirstError());
				Content = " 保存失败，原因是:" + tSubmit.mErrors.getFirstError();
				FlagStr = "Fail";
				
			}

		} else {
			tError.addOneError(new CError("没有查询到保全申请记录"));
		}
	}else if("REPLY".equals(tOperate)){
		//回复关闭
		String tPrtSeq = request.getParameter("PrtSeq");
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(tPrtSeq);
		if (tLOPRTManagerDB.getInfo()) {
			LOPRTManagerSchema tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			tLOPRTManagerSchema.setStateFlag("R"); // 设置为回复 A-ask R-reply
			tLOPRTManagerSchema.setStandbyFlag6(request.getParameter("MyReply")); //回复意见
			tLOPRTManagerSchema.setStandbyFlag7(PubFun.getCurrentDate()); //回复时间
			tLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());
			MMap tMap = new MMap();
			tMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
			tVData.add(tMap);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(tVData, "")) {
				tError.addOneError(tSubmit.mErrors.getFirstError());
				Content = " 保存失败，原因是:" + tSubmit.mErrors.getFirstError();
				FlagStr = "Fail";
				
			}
		}else{
			tError.addOneError(new CError("没有查询到问题件记录"));
		}
	}

	if (!tError.needDealError()) {

		Content = "保存成功!";
		FlagStr = "Succ";
	} else {
		Content = "处理失败，原因是:" + tError.getFirstError();
		FlagStr = "Fail";
	}
%>
   
<%@page import="com.sinosoft.lis.f1print.PrintManagerBL"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
