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
	loggerDebug("ApplyOtherDocSave","-------------- start save  --------------");
	//�������
	String FlagStr = "Succ";
	String Content = "����ɹ�";
	String tContent = request.getParameter("Content");
	//  String tGridNo[] = request.getParameterValues("CodeGridNo");  	//�õ�����е�����ֵ
	String tChk[] = request.getParameterValues("InpCodeGridChk"); //������ʽ="MulLine������+Chk"
	String tSubType[] = request.getParameterValues("CodeGrid1"); //�õ���1�е�����ֵ
	String tBussNo[] = request.getParameterValues("CodeGrid2"); //�õ���2�е�����ֵ
	String tBussNoType[] = request.getParameterValues("CodeGrid3"); //�õ���3�е�����ֵ
	String tDocID[] = request.getParameterValues("CodeGrid5"); //�õ���5�е�����ֵ
	String tComCode[] = request.getParameterValues("CodeGrid6"); //�õ���6�е�����ֵ
	String Reason = request.getParameter("Reason");

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	for (int index = 0; index < tChk.length; index++) {
		if (tChk[index].equals("1")) {
		MMap map = new MMap();
			if (Reason.equals("UR005") || Reason.equals("UR004")) { //�б�ҪԼ����
/* ��ʱ��У����, �ŵ����ڿ���������������ʵ��
				String sql = "select count(1) from LCApplyRecallPol where prtno='"
						+ tBussNo[index] + "' and applytype='1'";
				String r = new ExeSQL().getOneValue(sql);
				if (r.equals("0")) {
					FlagStr = "Fail";
					Content = "��֤" + tBussNo[index] + "�޸�������";
					break;
				}
*/
			} else if (Reason.equals("UR003")) { //�б�ҪԼ����
				String sql = "select count(1) from LCApplyRecallPol where prtno='"
						+ tBussNo[index] + "' and applytype='0'";
				String r = new ExeSQL().getOneValue(sql);
				if (r.equals("0")) {
					FlagStr = "Fail";
					Content = "��֤" + tBussNo[index] + "�޳�������";
					break;
				}
			}

			//ѡ�е���
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tLWMissionDB.setProcessID("0000000010");
			tLWMissionDB.setActivityID("0000003002");
			tLWMissionDB.setMissionProp1(tBussNo[index]);
			tLWMissionDB.setMissionProp2(Reason);
			tLWMissionDB.setMissionProp3(tG.Operator);
			if (tLWMissionDB.getCount() > 0) {
				FlagStr = "Fail";
				Content = "��֤" + tBussNo[index] + "�ѱ�ǩ��";
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
				Content = "��֤" + tBussNo[index] + "�ѱ�����";
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
