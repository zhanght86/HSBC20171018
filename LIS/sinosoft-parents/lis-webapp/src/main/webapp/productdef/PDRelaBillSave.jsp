<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//�������ƣ�PDRelaBillSave.jsp
	//�����ܣ����θ����˵�����
	//�������ڣ�2009-3-16
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>  
<%
	//������Ϣ������У�鴦��
	//�������

	//PDRelaBillBL pDRelaBillBL = new PDRelaBillBL();

	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String operator = "";
	TransferData transferData = new TransferData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	operator = request.getParameter("operator");
	String bttnflag = request.getParameter("bttnflag");
	//String tGetDutyCode = request.getParameter("GetDutyCode");
	//String tGetDutyKind = request.getParameter("GetDutyKind");
	PD_LMDutyGetFeeRelaSet tPD_LMDutyGetFeeRelaSet = new PD_LMDutyGetFeeRelaSet();
	
	String GETDUTYCODE = request.getParameter("GETDUTYCODE");
	String GETDUTYNAME = request.getParameter("GETDUTYNAME");
	String GETDUTYKIND = request.getParameter("GETDUTYKIND");
	String CLMFEECODE = request.getParameter("CLMFEECODE");
	String CLMFEENAME = request.getParameter("CLMFEENAME");
	String CLMFEECALTYPE = request.getParameter("CLMFEECALTYPE");
	String CLMFEECALCODE = request.getParameter("CLMFEECALCODE");
	

	PD_LMDutyGetFeeRelaSchema tPD_LMDutyGetFeeRelaSchema = new PD_LMDutyGetFeeRelaSchema();
	tPD_LMDutyGetFeeRelaSchema.setGetDutyCode(GETDUTYCODE);
	tPD_LMDutyGetFeeRelaSchema.setGetDutyName(GETDUTYNAME);
	tPD_LMDutyGetFeeRelaSchema.setGetDutyKind(GETDUTYKIND);
	tPD_LMDutyGetFeeRelaSchema.setClmFeeCode(CLMFEECODE);
	tPD_LMDutyGetFeeRelaSchema.setClmFeeName(CLMFEENAME);
	tPD_LMDutyGetFeeRelaSchema.setClmFeeCalType(CLMFEECALTYPE);
	tPD_LMDutyGetFeeRelaSchema.setClmFeeCalCode(CLMFEECALCODE);
	
	tPD_LMDutyGetFeeRelaSet.add(tPD_LMDutyGetFeeRelaSchema);
	
	transferData.setNameAndValue("RiskCode", request.getParameter("RiskCode"));
	transferData.setNameAndValue("tableName",request.getParameter("tableName"));
	transferData.setNameAndValue("GetDutyCode",GETDUTYCODE);
	 String tCalCodeType = request.getParameter("CalCodeSwitchFlag");
	 transferData.setNameAndValue("CalCodeType",tCalCodeType);
	transferData.setNameAndValue("PD_LMDutyGetFeeRelaSet", tPD_LMDutyGetFeeRelaSet);
	try {
		// ׼���������� VData
		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(transferData);
		//pDRelaBillBL.submitData(tVData, operator);
		String busiName="PDRelaBillBL";
		  
		  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  //String tDiscountCode = "";
		  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
		    Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
		  	FlagStr = "Fail";
		  }
		  else {
		    Content = " "+"����ɹ�!"+" ";
		  	FlagStr = "Success";
		  	CLMFEECALCODE = (String)tBusinessDelegate.getResult().get(0);
		  	// new RiskState().setState(request.getParameter("RiskCode"), "��Լҵ�����->���ֺ˱�����", "1");

		  }
		 
		 }
		 catch(Exception ex)
		 {
		  Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
		  FlagStr = "Fail";
		 }
		/*
	} catch (Exception ex) {
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		tError = pDRelaBillBL.mErrors;
		if (!tError.needDealError()) {
			Content = " ����ɹ�! ";
			FlagStr = "Success";
		} else {
			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}*/

	//��Ӹ���Ԥ����
%>
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.setCalCode("<%=CLMFEECALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

