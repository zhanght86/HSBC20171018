<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//EdorApproveSave.jsp
	//�����ܣ���ȫ����
	//�������ڣ�2005-05-08 15:59:52
	//������  ��sinosoft
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
    <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	//�������
	String FlagStr = "";
	String Content = "";


	CErrors tError = new CErrors();

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();
	

	//�ʾ�����
	String tOperate = request.getParameter("AskOperate");
	if ("INSERT".equals(tOperate)) {
		//�������� 
		String tAskFlag = request.getParameter("AskFlag");
		//��������
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
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // ���ɴ�ӡ��ˮ��
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(tContNo);
			tLOPRTManagerSchema.setOtherNoType("02"); // ����������
			tLOPRTManagerSchema.setCode("BQ38"); // ��ӡ����
			tLOPRTManagerSchema.setManageCom(tG.ManageCom); // �������
			tLOPRTManagerSchema.setAgentCode(""); // �����˱���
			tLOPRTManagerSchema.setReqCom(tG.ComCode);
			tLOPRTManagerSchema.setReqOperator(tG.Operator);
			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // ǰ̨��ӡ
			tLOPRTManagerSchema.setStateFlag("A"); // ��ʼ���·�״̬ A-ask R-reply
			tLOPRTManagerSchema.setPatchFlag("0"); // �����־
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setStandbyFlag1(tEdorAcceptNo); //�����
			tLOPRTManagerSchema.setStandbyFlag2(tG.Operator); //����ʾ���
			tLOPRTManagerSchema.setStandbyFlag3(tAskFlag); //�ʾ����� 1 �˹��˱� 2 ��ȫ����
			tLOPRTManagerSchema.setStandbyFlag4(tLPEdorAppSchema.getOperator()); //����ڵ��˽��лظ�
			tLOPRTManagerSchema.setStandbyFlag5(tAskContent); //���������
			tLOPRTManagerSchema.setStandbyFlag6(""); //�ظ����
			tLOPRTManagerSchema.setStandbyFlag7(""); //�ظ�ʱ��
			tLOPRTManagerSchema.setDoneTime("");
			MMap tMap = new MMap();
			tMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
			tVData.add(tMap);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(tVData, "")) {
				tError.addOneError(tSubmit.mErrors.getFirstError());
				Content = " ����ʧ�ܣ�ԭ����:" + tSubmit.mErrors.getFirstError();
				FlagStr = "Fail";
				
			}

		} else {
			tError.addOneError(new CError("û�в�ѯ����ȫ�����¼"));
		}
	}else if("REPLY".equals(tOperate)){
		//�ظ��ر�
		String tPrtSeq = request.getParameter("PrtSeq");
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setPrtSeq(tPrtSeq);
		if (tLOPRTManagerDB.getInfo()) {
			LOPRTManagerSchema tLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			tLOPRTManagerSchema.setStateFlag("R"); // ����Ϊ�ظ� A-ask R-reply
			tLOPRTManagerSchema.setStandbyFlag6(request.getParameter("MyReply")); //�ظ����
			tLOPRTManagerSchema.setStandbyFlag7(PubFun.getCurrentDate()); //�ظ�ʱ��
			tLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());
			MMap tMap = new MMap();
			tMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
			tVData.add(tMap);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(tVData, "")) {
				tError.addOneError(tSubmit.mErrors.getFirstError());
				Content = " ����ʧ�ܣ�ԭ����:" + tSubmit.mErrors.getFirstError();
				FlagStr = "Fail";
				
			}
		}else{
			tError.addOneError(new CError("û�в�ѯ���������¼"));
		}
	}

	if (!tError.needDealError()) {

		Content = "����ɹ�!";
		FlagStr = "Succ";
	} else {
		Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		FlagStr = "Fail";
	}
%>
   
<%@page import="com.sinosoft.lis.f1print.PrintManagerBL"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
