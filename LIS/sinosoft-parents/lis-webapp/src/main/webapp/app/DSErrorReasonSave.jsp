<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.*" %>

<%
	loggerDebug("DSErrorReasonSave","��ʼִ��Saveҳ��");
	//����ȫ�ֱ�������������Ͳ���Ա
	String tRela = "";
	String FlagStr = "";
	String Content = "";

	//����¼��BPOErrLog
	LBPODataDetailErrorSet mLBPODataDetailErrorSet = new LBPODataDetailErrorSet();

	//�����������
	CErrors tError = null;
	loggerDebug("DSErrorReasonSave","��ʼ���л�ȡ���ݵĲ���������");
	String mOperateType = "INSERT";
	String rgtNo = request.getParameter("prtNo");
	//���������ǲ��룬��ѯ������
	loggerDebug("DSErrorReasonSave","ӡˢ����" + rgtNo);

	//��ȡmulitiline�е�ֵ
	String tImpartNum[] = request.getParameterValues("PolGridNo");
	String terrorcontent[] = request.getParameterValues("PolGrid1"); //¼����Ŀ
	String terrorcount[] = request.getParameterValues("PolGrid2"); //¼������
	String toperator[] = request.getParameterValues("PolGrid3"); //����Ա����
	String tErrorFlag[] = request.getParameterValues("PolGrid4"); //��¼���
	String tErrorCount[] = request.getParameterValues("PolGrid5"); //������
	String tSerialNo[] = request.getParameterValues("PolGrid6"); //������ˮ��

	//��BPOErrLogSchema��ʵ����ֵ,�ѱ���BL���л����Щ����,������Щ������ѯ���ݿ⣬����һЩУ���жϣ��Ƿ���Խ��в���Ȳ���
	int ImpartCount = 0;

	if (tImpartNum != null) {
		ImpartCount = tImpartNum.length;
		loggerDebug("DSErrorReasonSave","ImpartCount= " + ImpartCount);

		for (int i = 0; i < ImpartCount; i++) {
			LBPODataDetailErrorSchema mLBPODataDetailErrorSchema = new LBPODataDetailErrorSchema();
			loggerDebug("DSErrorReasonSave","i=" + i);
			mLBPODataDetailErrorSchema.setBussNo(rgtNo);
			loggerDebug("DSErrorReasonSave","ӡˢ����" + rgtNo);
			mLBPODataDetailErrorSchema.setBussNoType("TB");
			loggerDebug("DSErrorReasonSave","������terrorCode[" + i + "]="
			+ tSerialNo[i]);
			mLBPODataDetailErrorSchema.setErrorCount(tErrorCount[i]);
			mLBPODataDetailErrorSchema.setErrorContent(terrorcontent[i]);
			mLBPODataDetailErrorSchema.setSerialNo(tSerialNo[i]);
			mLBPODataDetailErrorSchema.setOperator(toperator[i]);
			mLBPODataDetailErrorSchema.setErrorFlag(tErrorFlag[i]);
			mLBPODataDetailErrorSchema.setContent(tErrorFlag[i]);
			mLBPODataDetailErrorSet.add(mLBPODataDetailErrorSchema);
		}
	}

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//DSErrorReasonUI mDSErrorReasonUI = new DSErrorReasonUI();
	String busiName="tbDSErrorReasonUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	VData tVData = new VData();

	//�������ύ����̨UI,������VData�Ͳ�������
	try {
		//���������ͣ��������������Ա��ӵ�������
		tVData.addElement(mOperateType);
		tVData.addElement(mLBPODataDetailErrorSet);
		tVData.addElement(tG);

		tBusinessDelegate.submitData(tVData, mOperateType,busiName);
	} catch (Exception ex) {
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		tError = tBusinessDelegate.getCErrors();
		if (!tError.needDealError()) {
			Content = "����ɹ�";
			FlagStr = "Succ";
		} else {
			Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterfacereason.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//alert("<%=Content%>");
</script>
</html>
