<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//�������ƣ�PDRiskDutyFactorSave.jsp
	//�����ܣ�����¼��Ҫ�ض���
	//�������ڣ�2009-3-13
	//������  ��
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//������Ϣ������У�鴦��
	//�������

	PDRiskDutyFactorBL pDRiskDutyFactorBL = new PDRiskDutyFactorBL();

	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String operator = "";
	String submitFlag = "";
	
	

	TransferData transferData = new TransferData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
	operator = request.getParameter("operator");
	submitFlag = request.getParameter("submitFlag");

	//tLDCodeSchema.setCodeType(request.getParameter("CodeType"));
	if ("PD_LMRiskDutyFactor".equals(submitFlag)) {
		String values[] = request.getParameterValues("Mulline9Grid4");
		java.util.ArrayList list = new java.util.ArrayList();
		for (int i = 0; i < values.length; i++) {
			if (i == 2) {
				list.add(values[i]);
				ExeSQL tExeSQL = new ExeSQL();
				String tSql = "select trim(CodeName) from ldcode where  codetype = 'pd_calfactor' and code='"
						+ values[i] + "'";
				SSRS tSSRS = tExeSQL.execSQL(tSql);
				if (tSSRS != null && tSSRS.getMaxRow() > 0) {
					list.add(tSSRS.GetText(1, 1));
				}
			}else if(i==4){
				  char[] c = values[i].toCharArray();   
		        for (int j = 0; j< c.length; j++) {   
			        if (c[j]==65342 || c[j]==65372) {   
			        	c[j] = (char)((int)c[j]-65248); 
			        }
			    }   
			      list.add(new String(c));
			} else {
				list.add(values[i]);
			}

		}
		transferData.setNameAndValue("list", list);
		transferData.setNameAndValue("tableName", request
				.getParameter("tableName"));
		transferData.setNameAndValue("RiskCode", request
				.getParameter("RiskCode"));
	} else {
		String codeType[] = request
				.getParameterValues("Mulline12Grid1");
		String code[] = request.getParameterValues("Mulline12Grid2");
		String entertainment[] = request
				.getParameterValues("Mulline12Grid3");
		String fourth[] = request.getParameterValues("Mulline12Grid4");
		String fifth[] = request.getParameterValues("Mulline12Grid5");
		LDCodeSet tLDCodeSet = new LDCodeSet();
		if (codeType != null) {
			for (int i = 0; i < entertainment.length; i++) {
				LDCodeSchema tLDCodeSchema = new LDCodeSchema();
				tLDCodeSchema.setCodeType(codeType[i].trim());
				tLDCodeSchema.setCode(code[i].trim());
				tLDCodeSchema.setCodeName(entertainment[i].trim());
				tLDCodeSchema.setCodeAlias(fourth[i].trim());
				tLDCodeSchema.setComCode(fifth[i].trim());
				tLDCodeSet.add(tLDCodeSchema);
			}
		}
		transferData.setNameAndValue("LDCodeSet", tLDCodeSet);
	}
	
	transferData.setNameAndValue("submitFlag", submitFlag);
	try {
		// ׼���������� VData
		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(transferData);
		pDRiskDutyFactorBL.submitData(tVData, operator);
		
		new RiskState().setState(request.getParameter("RiskCode"), ""+"��Լҵ�����"+"->"+"���ϼƻ�Լ��Ҫ��"+"", "1");
	} catch (Exception ex) {
		Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		tError = pDRiskDutyFactorBL.mErrors;
		if (!tError.needDealError()) {
			Content = " "+"����ɹ�!"+" ";
			FlagStr = "Success";
		} else {
			Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}

	//��Ӹ���Ԥ����
%>
<%=Content%>
<html>
	<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

