<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //�������ƣ�AppntChkUnionQuestInputSave.jsp
  //�����ܣ�
  //�������ڣ�2005-04-20 16:49:52
  //������  ��zhangtao
  //���¼�¼��  ������    ��������     ����ԭ��/����
  //
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.workflow.ask.*"%>
<%@page import="com.sinosoft.workflowengine.*"%>
<%@page import="com.sinosoft.workflow.tbgrp.GrpTbWorkFlowUI" %>
<%
  //�������
  
  String FlagStr = "";
  String Content = "";
  CErrors tError = null;
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput) session.getValue("GI");
  //VData tVData = new VData();
  //LCGrpContSet tLCGrpContSet = new LCGrpContSet();
  //LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();
  //String tContNo = request.getParameter("ProposalNo");
  //String tFlag = request.getParameter("Flag");
  //String tBackObj = "2";
  //String tQuest = "99";
  //String tContent = request.getParameter("CUIContent");
  //String tQuestionObj = request.getParameter("CustomerNo_OLD");
  //String tQuestionObjName = request.getParameter("CustomerName");
  //String tStandbyFlag1 = request.getParameter("CustomerNo_NEW");
  ////loggerDebug("AppntChkUnionQuestInputSave2","���������===="+tContNo+"-"+tFlag+"-"+tContent+"-"+tQuestionObj);
  ////loggerDebug("AppntChkUnionQuestInputSave2","���������===="+tQuestionObjName+"-"+tStandbyFlag1);
  //String tQuestionObjValue = "0";
  //String mFlag = "1";
  //LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
  //tLCGrpContSchema.setGrpContNo(tContNo);
  //tLCGrpContSchema.setState(mFlag);
  //tLCGrpContSet.add(tLCGrpContSchema);
  //LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
  ////tLCGrpIssuePolSchema.setIssueCont("����Ͷ��������*******���������ҹ�˾Ͷ��������������********�������ṩ�������֤��ӡ�����Ա��ҹ�˾���к˶ԡ�");
  //tLCGrpIssuePolSchema.setIssueCont(tContent);
  //tLCGrpIssuePolSchema.setOperatePos(tFlag);
  //tLCGrpIssuePolSchema.setBackObjType(tBackObj);
  //tLCGrpIssuePolSchema.setIssueType(tQuest);
  //tLCGrpIssuePolSchema.setQuestionObjType(tQuestionObjValue);
  //tLCGrpIssuePolSchema.setQuestionObj(tQuestionObj);
  //tLCGrpIssuePolSchema.setQuestionObjName(tQuestionObjName);
  //tLCGrpIssuePolSchema.setStandbyFlag1(tStandbyFlag1);
  //tLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);
  //GrpQuestInputChkUI tGrpQuestInputChkUI = new GrpQuestInputChkUI();
  //tVData.add(tG);
  //tVData.add(tLCGrpContSet);
  //tVData.add(tLCGrpIssuePolSet);
   //��ѯMISSIONID
    ExeSQL exeSql = new ExeSQL();
    SSRS tSSRS = new SSRS();
    tSSRS = exeSql.execSQL("select * from lwmission where activityid='0000002001' and missionprop1='"+request.getParameter("ProposalNo")+"'");
    String mMissionID="";
    if(tSSRS.MaxRow==0)
    {
             
    }else{
       mMissionID=tSSRS.GetText(1,1);
    }
  
TransferData tTransferData = new TransferData();
tTransferData.setNameAndValue("ContNo",request.getParameter("ProposalNo")) ;
tTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo_OLD")) ;
tTransferData.setNameAndValue("MissionID",mMissionID) ;
tTransferData.setNameAndValue("SubMissionID","1") ;			
VData tVData = new VData();
tVData.add(tTransferData);
tVData.add(tGI);  			 
  String tOperate="0000002225";
  GrpTbWorkFlowUI tGrpTbWorkFlowUI=new GrpTbWorkFlowUI();
  		if (!tGrpTbWorkFlowUI.submitData(tVData,tOperate)) //ִ�����������
					{
						int n = tGrpTbWorkFlowUI.mErrors.getErrorCount();
						Content = " ���������ʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
						loggerDebug("AppntChkUnionQuestInputSave2",Content);
						FlagStr = "Fail";
					}
    			   else
    			   {
    			     FlagStr="Succ";
    			     Content="��������ͳɹ���";
    			   }
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script></html>
