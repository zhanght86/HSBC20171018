
<%
	//�������ƣ�CertifyDescSave.jsp
	//�����ܣ�
	//�������ڣ�2002-07-19 16:49:22
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
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
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	loggerDebug("CardRiskSave","��ʼִ��Saveҳ��");
	LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	CErrors tError = null;
	String FlagStr = "";
	String Content = "";

	String mOperateType = request.getParameter("OperateType");
	loggerDebug("CardRiskSave","������������ " + mOperateType);

	loggerDebug("CardRiskSave","��ʼ���л�ȡ���ݵĲ���������");
	String[] strNumber = request.getParameterValues("CardRiskGridNo");
	String[] strCertifyCode = request.getParameterValues("CardRiskGrid1");
	String[] strRiskCode = request.getParameterValues("CardRiskGrid2");
	String[] strCardCode = request.getParameterValues("CardRiskGrid3"); //��Ʒ����
	String[] strPrem = request.getParameterValues("CardRiskGrid4");
	String[] strPremProp = request.getParameterValues("CardRiskGrid5");
	String[] strPremLot = request.getParameterValues("CardRiskGrid6");
	if (strNumber != null) {
		for (int i = 0; i < strNumber.length; i++) {
			loggerDebug("CardRiskSave","���ֱ��� " + strRiskCode[i] +"��Ʒ����"+strCardCode[i]);
			if((strRiskCode[i]==null || "".equals(strRiskCode[i]))&&(strCardCode[i]==null || "".equals(strCardCode[i])))
			{
				Content = "��¼�����ֱ�����߲�Ʒ����" ;
				FlagStr = "Fail";
				break;
			}
			if((!("".equals(strRiskCode[i]) ))&& !("".equals(strCardCode[i])) )
			{
				Content = "���ֱ������Ʒ���벻��ͬʱ��ֵ����¼������һ��" ;
				FlagStr = "Fail";
				break;
			}
			LMCardRiskSchema tLMCardRiskSchema = new LMCardRiskSchema();
			tLMCardRiskSchema.setCertifyCode(strCertifyCode[i]);
			if(!("".equals(strRiskCode[i])) )
			{
			 tLMCardRiskSchema.setRiskCode(strRiskCode[i]); //������ֱ���һ�в�Ϊ�գ���ֵΪ���ִ��룬����ֵΪ��Ʒ����
			}
			else
			{
			 tLMCardRiskSchema.setRiskCode(strCardCode[i]);
			 tLMCardRiskSchema.setPortfolioFlag("1"); //��ŵ����ֱ���Ϊ��Ʒ��ϴ���
			}
			tLMCardRiskSchema.setPrem(strPrem[i]);
			tLMCardRiskSchema.setPremProp(strPremProp[i]);
			tLMCardRiskSchema.setPremLot(String.valueOf(strPremLot[i]));
			mLMCardRiskSet.add(tLMCardRiskSchema);
		}
	}
   if(FlagStr!="Fail")
   {
		VData tVData = new VData();
		CardRiskUI mCardRiskUI = new CardRiskUI();
		try {
			tVData.addElement(tG);
			tVData.addElement(mLMCardRiskSet);
	
			mCardRiskUI.submitData(tVData, mOperateType);
		} catch (Exception ex) {
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
	
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "") {
			tError = mCardRiskUI.mErrors;
			if (!tError.needDealError()) {
				Content = "�����ɹ�!";
				FlagStr = "Succ";
			} else {
				Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
   }
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
