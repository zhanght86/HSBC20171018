<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�ApplyIssueDocSave.jsp
	//�����ܣ�
	//�������ڣ�2006-04-07
	//������  ��wentao
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	loggerDebug("QueryOtherDocSave","-------------- start save  --------------");
	//�������
	String FlagStr = "Succ";
	String Content = "ɾ���ɹ�";
	String tContent = request.getParameter("Content");
	//  String tGridNo[] = request.getParameterValues("CodeGridNo");  	//�õ�����е�����ֵ
	String tChk[] = request.getParameterValues("InpCodeGridChk"); //������ʽ="MulLine������+Chk"
	String tBussNo[] = request.getParameterValues("CodeGrid1"); //�õ���2�е�����ֵ
	String tComCode[] = request.getParameterValues("CodeGrid2"); //�õ���6�е�����ֵ
	String tApplyOperator[] = request.getParameterValues("CodeGrid4"); //�õ���3�е�����ֵ
	String tReason[] = request.getParameterValues("CodeGrid8");

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	MMap map = new MMap();
	for (int index = 0; index < tChk.length; index++) {
		if (tChk[index].equals("1")) {
			//ѡ�е���
			LWMissionDB tLWMissionDB = new LWMissionDB();
			LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery("select * from LWMission where ProcessID='0000000010' and "+
					"ActivityID in('0000003001','0000003002') and MissionProp1='"+tBussNo[index]+
					"' and MissionProp2='"+tReason[index]+"' and MissionProp3='"+tApplyOperator[index]+"'");
			if (tLWMissionSet.size() == 0) {
				FlagStr = "Fail";
				Content = "��֤" + tBussNo[index] + "δ������";
				break;
			}

			if (tLWMissionSet.size() > 1) {
				FlagStr = "Fail";
				Content = "��֤" + tBussNo[index] + "�������˶��";
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
