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
	loggerDebug("ApplyOtherDocSave","-------------- start save  --------------");
	//输出参数
	String FlagStr = "Succ";
	String Content = "申请成功";
	String tContent = request.getParameter("Content");
	//  String tGridNo[] = request.getParameterValues("CodeGridNo");  	//得到序号列的所有值
	String tChk[] = request.getParameterValues("InpCodeGridChk"); //参数格式="MulLine对象名+Chk"
	String tSubType[] = request.getParameterValues("CodeGrid1"); //得到第1列的所有值
	String tBussNo[] = request.getParameterValues("CodeGrid2"); //得到第2列的所有值
	String tBussNoType[] = request.getParameterValues("CodeGrid3"); //得到第3列的所有值
	String tDocID[] = request.getParameterValues("CodeGrid5"); //得到第5列的所有值
	String tComCode[] = request.getParameterValues("CodeGrid6"); //得到第6列的所有值
	String Reason = request.getParameter("Reason");

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	for (int index = 0; index < tChk.length; index++) {
		if (tChk[index].equals("1")) {
		MMap map = new MMap();
			if (Reason.equals("UR005") || Reason.equals("UR004")) { //承保要约更正
/* 暂时不校验了, 放到二期开发出变更申请后再实现
				String sql = "select count(1) from LCApplyRecallPol where prtno='"
						+ tBussNo[index] + "' and applytype='1'";
				String r = new ExeSQL().getOneValue(sql);
				if (r.equals("0")) {
					FlagStr = "Fail";
					Content = "单证" + tBussNo[index] + "无更正申请";
					break;
				}
*/
			} else if (Reason.equals("UR003")) { //承保要约撤单
				String sql = "select count(1) from LCApplyRecallPol where prtno='"
						+ tBussNo[index] + "' and applytype='0'";
				String r = new ExeSQL().getOneValue(sql);
				if (r.equals("0")) {
					FlagStr = "Fail";
					Content = "单证" + tBussNo[index] + "无撤单申请";
					break;
				}
			}

			//选中的行
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setProcessID("0000000010");
			tLWMissionDB.setActivityID("0000003002");
			tLWMissionDB.setMissionProp1(tBussNo[index]);
			tLWMissionDB.setMissionProp2(Reason);
			tLWMissionDB.setMissionProp3(tG.Operator);
			if (tLWMissionDB.getCount() > 0) {
				FlagStr = "Fail";
				Content = "单证" + tBussNo[index] + "已被签批";
				break;
			}

			tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setProcessID("0000000010");
			tLWMissionDB.setActivityID("0000003001");
			tLWMissionDB.setMissionProp1(tBussNo[index]);
			tLWMissionDB.setMissionProp2(Reason);
			tLWMissionDB.setMissionProp3(tG.Operator);
			if (tLWMissionDB.getCount() > 0) {
				FlagStr = "Fail";
				Content = "单证" + tBussNo[index] + "已被申请";
				break;
			}

			VData tVData = new VData();
			tVData.add(tG);
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("BussNo", tBussNo[index]);
			tTransferData.setNameAndValue("BussNoType", Reason);
			tTransferData.setNameAndValue("ApplyOperator", tG.Operator);
			tTransferData.setNameAndValue("ApplyReason", tContent);
			tTransferData.setNameAndValue("ManageCom", tG.ManageCom);
			tVData.add(tTransferData);

			String busiName="easyscanApplyOtherDocUI";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			//ApplyOtherDocUI tApplyOtherDocUI = new ApplyOtherDocUI();
			if(tBusinessDelegate.submitData(tVData,"CreateStartMission",busiName))
			{
				VData r = tBusinessDelegate.getResult();
				MMap m = (MMap) r.getObjectByObjectName("MMap", 0);
				map.add(m);
			}
			else
			{
				FlagStr = "Fail";
				Content = PubFun.changForJavaScript(tBussNo[index]
						+ ":"
						+ tBusinessDelegate.getCErrors().getErrContent());
				break;
			}
			//ActivityOperator tActivityOpertor = new ActivityOperator();
			/*if (tActivityOpertor.CreateStartMission("0000000010",
					"0000003001", tVData)) {
				VData r = tActivityOpertor.getResult();
				MMap m = (MMap) r.getObjectByObjectName("MMap", 0);
				map.add(m);
			} else {
				FlagStr = "Fail";
				Content = PubFun.changForJavaScript(tBussNo[index]
						+ ":"
						+ tActivityOpertor.mErrors.getErrContent());
				break;
			}*/
			if (FlagStr.equals("Succ")) {
				//PubSubmit ps = new PubSubmit();
				tVData = new VData();
				tVData.add(map);
				if(!tBusinessDelegate.submitData(tVData,"submitData",busiName))
				{
					FlagStr = "Fail";
					Content = PubFun.changForJavaScript(tBusinessDelegate.getCErrors()	.getErrContent());
					break;
				}
				/*
				if (!ps.submitData(tVData)) {
					FlagStr = "Fail";
					Content = PubFun.changForJavaScript(ps.mErrors
							.getErrContent());
					break;
				}
				*/
			}
		}
	}
%>
<%@page import="com.sinosoft.workflowengine.ActivityOperator"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
