<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>
<%@page import="com.sinosoft.lis.schema.LWConditionSchema"%>
<%@page import="com.sinosoft.lis.vschema.LWConditionSet"%>
<%@page import="java.sql.*"%>
<%@page import="java.net.URLDecoder"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	TransferData tTransferData = new TransferData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	String operator = tG.Operator;
	/**
	 * ��ǰ̨��ȡһЩҵ���ֶ�
	 */
	String OperFlag = request.getParameter("OperFlag");
	if ("INSERT||Condition".equalsIgnoreCase(OperFlag) || "MODIFY||Condition".equalsIgnoreCase(OperFlag)
			|| "DELETE||Condition".equalsIgnoreCase(OperFlag)) {

		String ProcessId = request.getParameter("ProcessId");
		String Version = request.getParameter("Version");
		String tStartActivityID = request.getParameter("StartActivityID");
		String tEndActivityID = request.getParameter("EndActivityID");
		String tTransitionCondT = request.getParameter("TransitionCondT");//ת����������
		String tTransitionCond1 = request.getParameter("TransitionCond");//ת������
		String tTransitionCondDesc = request.getParameter("CondDesc");//ת������
		String tTransitionCond = URLDecoder.decode(tTransitionCond1, "UTF-8");
		String transitionid = PubFun1.CreateMaxNo("TRANSITIONID", 10);
		//����busytype
		String BusiType = request.getParameter("BusiType");

		LWConditionSchema tLWConditionSchema = new LWConditionSchema();
		tLWConditionSchema.setBusiType(BusiType);
		tLWConditionSchema.setProcessID(ProcessId);
		tLWConditionSchema.setVersion(Version);
		tLWConditionSchema.setTransitionStart(tStartActivityID);
		tLWConditionSchema.setTransitionEnd(tEndActivityID);
		tLWConditionSchema.setTransitionCond(tTransitionCond);
		tLWConditionSchema.setTransitionCondT(tTransitionCondT);
		tLWConditionSchema.setCondDesc(tTransitionCondDesc);

		LWConditionSet tLWConditionSet = new LWConditionSet();
		tLWConditionSet.add(tLWConditionSchema);
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLWConditionSet);
		String busiName = "LWConditionUI";
		BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();

		String FlagStr = ""; //�������
		String Content = ""; //����̨��Ϣ
		if (!tBusinessDelegate.submitData(tVData, "save", busiName)) { //����ʧ��ʱ
			Content = "����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";

		} else {
			Content = " ���óɹ�! ";
			FlagStr = "Succ";
		}

		response.getWriter().println(Content);

	}
	if ("CREATE||Condition".equalsIgnoreCase(OperFlag)) {
		String mCalCodeType = request.getParameter("mCalCodeType");
		String calcode = PubFun1.CreateRuleCalCode("WF", mCalCodeType);
		response.getWriter().println(calcode);
	}
%>