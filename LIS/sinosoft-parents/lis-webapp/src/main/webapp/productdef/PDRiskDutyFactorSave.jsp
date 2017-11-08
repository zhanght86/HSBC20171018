<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//程序名称：PDRiskDutyFactorSave.jsp
	//程序功能：责任录入要素定义
	//创建日期：2009-3-13
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//接收信息，并作校验处理。
	//输入参数

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

	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
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
		// 准备传输数据 VData
		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(transferData);
		pDRiskDutyFactorBL.submitData(tVData, operator);
		
		new RiskState().setState(request.getParameter("RiskCode"), ""+"契约业务控制"+"->"+"保障计划约定要素"+"", "1");
	} catch (Exception ex) {
		Content = ""+"保存失败，原因是:"+"" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = pDRiskDutyFactorBL.mErrors;
		if (!tError.needDealError()) {
			Content = " "+"保存成功!"+" ";
			FlagStr = "Success";
		} else {
			Content = " "+"保存失败，原因是:"+"" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}

	//添加各种预处理
%>
<%=Content%>
<html>
	<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

