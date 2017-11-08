<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //程序名称：AppntChkUnionQuestInputSave.jsp
  //程序功能：
  //创建日期：2005-04-20 16:49:52
  //创建人  ：zhangtao
  //更新记录：  更新人    更新日期     更新原因/内容
  //
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  loggerDebug("AppntChkUnionQuestInputSave","你好啊 ，呵呵");
  String FlagStr = "";
  String Content = "";
  CErrors tError = null;
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  LCGrpContSet tLCGrpContSet = new LCGrpContSet();
  LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();
  String tContNo = request.getParameter("ProposalNo");
  String tFlag = request.getParameter("Flag");
  String tBackObj = "2";
  String tQuest = "99";
  String tContent = request.getParameter("CUIContent");
  String tQuestionObj = request.getParameter("CustomerNo_OLD");
  String tQuestionObjName = request.getParameter("CustomerName");
  String tStandbyFlag1 = request.getParameter("CustomerNo_NEW");
  //loggerDebug("AppntChkUnionQuestInputSave","传入的数据===="+tContNo+"-"+tFlag+"-"+tContent+"-"+tQuestionObj);
  //loggerDebug("AppntChkUnionQuestInputSave","传入的数据===="+tQuestionObjName+"-"+tStandbyFlag1);
  String tQuestionObjValue = "0";
  String mFlag = "1";
  LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
  tLCGrpContSchema.setGrpContNo(tContNo);
  tLCGrpContSchema.setState(mFlag);
  tLCGrpContSet.add(tLCGrpContSchema);
  LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
  //tLCGrpIssuePolSchema.setIssueCont("本次投保申请中*******，但您在我公司投保的其他保单中********，请您提供您的身份证复印件，以便我公司进行核对。");
  tLCGrpIssuePolSchema.setIssueCont(tContent);
  tLCGrpIssuePolSchema.setOperatePos(tFlag);
  tLCGrpIssuePolSchema.setBackObjType(tBackObj);
  tLCGrpIssuePolSchema.setIssueType(tQuest);
  tLCGrpIssuePolSchema.setQuestionObjType(tQuestionObjValue);
  tLCGrpIssuePolSchema.setQuestionObj(tQuestionObj);
  tLCGrpIssuePolSchema.setQuestionObjName(tQuestionObjName);
  tLCGrpIssuePolSchema.setStandbyFlag1(tStandbyFlag1);
  tLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);
  GrpQuestInputChkUI tGrpQuestInputChkUI = new GrpQuestInputChkUI();
  tVData.add(tG);
  tVData.add(tLCGrpContSet);
  tVData.add(tLCGrpIssuePolSet);
  String busiName="cbcheckGrpQuestInputChkUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  		if(!tBusinessDelegate.submitData(tVData, "INSERT",busiName))
  	{
  		    	Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
  		    	FlagStr = "Fail";

  	}else{
  	 Content ="保存成功！";
  	FlagStr = "Succ";
  	}
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script></html>
