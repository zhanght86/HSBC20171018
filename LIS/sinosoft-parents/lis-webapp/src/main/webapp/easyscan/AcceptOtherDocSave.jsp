<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：AcceptIssueDocSave.jsp
	//程序功能：
	//创建日期：2006-04-07
	//创建人  ：wentao
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.workflowengine.ActivityOperator"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.service.*" %>
<%
	loggerDebug("AcceptOtherDocSave","-------------- start save  --------------");
	//输出参数
	String FlagStr = "Succ";
	String Content = "签批成功";
	String tContent = request.getParameter("Content");
	//  String tGridNo[] = request.getParameterValues("CodeGridNo");  	//得到序号列的所有值
	String tChk[] = request.getParameterValues("InpCodeGridChk"); //参数格式="MulLine对象名+Chk"
	String tBussNo[] = request.getParameterValues("CodeGrid1"); //得到第2列的所有值
	String tSubType[] = request.getParameterValues("CodeGrid2"); //得到第1列的所有值
	String tApplyOperator[] = request.getParameterValues("CodeGrid6"); //得到第2列的所有值

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	if(tChk==null ||tChk.length==0){
		FlagStr = "Fail";
		Content = "无待签批项";
	}else{
	boolean have=false;
	//ActivityOperator tActivityOperator = new ActivityOperator();
	//AcceptOtherDocUI tAcceptOtherDocUI = new AcceptOtherDocUI();
	String busiName="easyscanAcceptOtherDocUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	for (int index = 0; index < tChk.length; index++) {
		if (tChk[index].equals("1")) {
			have=true;
			//选中的行
			try {
				String activityid="0000003001";
				LWMissionDB tLWMissionDB = new LWMissionDB();
				tLWMissionDB.setProcessID("0000000010");
				tLWMissionDB.setActivityID(activityid);
				tLWMissionDB.setMissionProp1(tBussNo[index]);
				tLWMissionDB.setMissionProp2(tSubType[index]);

				LWMissionSet tLWMissionSet = tLWMissionDB.query();
				if (tLWMissionSet.size() == 0) {
					FlagStr = "Fail";
					Content = "此单"+tBussNo[index]+"未申请";
					break;
				}

				LWMissionSchema tLWMissionSchema = tLWMissionSet.get(1);

				VData mInputData = new VData();
				mInputData.add(tG);
				String tMissionID = tLWMissionSchema.getMissionID();
				String tSubMissionID = tLWMissionSchema.getSubMissionID();
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("BussNo", tBussNo[index]);
				tTransferData.setNameAndValue("BussNoType", tSubType[index]);
				tTransferData.setNameAndValue("ApplyOperator", tApplyOperator[index]);
				tTransferData.setNameAndValue("AcceptOperator", tG.Operator);
				tTransferData.setNameAndValue("ApplyReason", tLWMissionSchema.getMissionProp4());
				tTransferData.setNameAndValue("ManageCom", tG.ManageCom);
				tTransferData.setNameAndValue("MissionID",tMissionID);
				tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
				tTransferData.setNameAndValue("activityid",activityid);
				mInputData.add(tTransferData);

				
				VData tVData = new VData();
				MMap mMMap=new MMap();
				//if (!tActivityOperator.ExecuteMission(tMissionID,
				
				if (!tBusinessDelegate.submitData(mInputData,"ExecuteMission",busiName)){ //ExecuteMission(tMissionID,tSubMissionID, activityid, mInputData)) {
					// @@错误处理
					FlagStr = "Fail";
					Content = tBusinessDelegate.getCErrors().getErrContent();
					break;
				}
				// 获得执行承保工作流待人工核保活动表任务的结果
				tVData = tBusinessDelegate.getResult();
				if (tVData != null) {
					for (int i = 0; i < tVData.size(); i++) {
						VData tempVData = (VData) tVData.get(i);
						if ((tempVData != null) && (tempVData.size() > 0)) {
							mMMap.add((MMap)tempVData.getObjectByObjectName("MMap", 0));
						}
					}
				}

				// 产生执行完承保工作流待人工核保活动表任务后的任务节点
				//if (!tActivityOperator.CreateNextMission(tMissionID,tSubMissionID, activityid, mInputData)) 
				if (!tBusinessDelegate.submitData(mInputData,"CreateNextMission",busiName)) //.CreateNextMission(tMissionID,tSubMissionID, activityid, mInputData))
				{
					FlagStr = "Fail";
					Content = tBusinessDelegate.getCErrors().getErrContent();
					break;
				} else {
					VData tempVData = tBusinessDelegate.getResult();
					if ((tempVData != null) && (tempVData.size() > 0)) {
						mMMap.add((MMap)tempVData.getObjectByObjectName("MMap", 0));
					}
				}

				//tActivityOperator = new ActivityOperator();
				//if (!tActivityOperator.DeleteMission(tMissionID,tSubMissionID, activityid, mInputData)) {
				if (!tBusinessDelegate.submitData(mInputData,"DeleteMission",busiName)){ //.DeleteMission(tMissionID,tSubMissionID, activityid, mInputData)) {
					FlagStr = "Fail";
					Content = tBusinessDelegate.getCErrors().getErrContent();
					break;
				} else {
					VData tempVData = new VData();
					tempVData = tBusinessDelegate.getResult();
					if ((tempVData != null) && (tempVData.size() > 0)) {
						mMMap.add((MMap)tempVData.getObjectByObjectName("MMap", 0));
					}
				}
				//PubSubmit ps = new PubSubmit();
				tVData = new VData();
				tVData.add(mMMap);
				//if (!ps.submitData(tVData)) {
				if (!tBusinessDelegate.submitData(tVData,"submitData",busiName)) {
					FlagStr = "Fail";
					Content = tBusinessDelegate.getCErrors().getErrContent();
					break;
				}
			} catch (Exception ex) {
				// @@错误处理
				FlagStr = "Fail";
				Content = "工作流引擎执行新契约活动表任务出错:" + ex.getMessage();
				break;
			}
		}
	}
	if(!have){
		FlagStr = "Fail";
		Content = "无待签批项";
	}
	}
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=PubFun.changForJavaScript(Content)%>");
</script>
</html>
