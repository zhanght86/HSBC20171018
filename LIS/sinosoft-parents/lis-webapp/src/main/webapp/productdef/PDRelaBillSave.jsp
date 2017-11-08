<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
	//程序名称：PDRelaBillSave.jsp
	//程序功能：责任给付账单关联
	//创建日期：2009-3-16
	//创建人  ：
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>  
<%
	//接收信息，并作校验处理。
	//输入参数

	//PDRelaBillBL pDRelaBillBL = new PDRelaBillBL();

	CErrors tError = null;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String operator = "";
	TransferData transferData = new TransferData();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");

	//执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
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
		// 准备传输数据 VData
		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(transferData);
		//pDRelaBillBL.submitData(tVData, operator);
		String busiName="PDRelaBillBL";
		  
		  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  //String tDiscountCode = "";
		  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
		    Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
		  	FlagStr = "Fail";
		  }
		  else {
		    Content = " "+"处理成功!"+" ";
		  	FlagStr = "Success";
		  	CLMFEECALCODE = (String)tBusinessDelegate.getResult().get(0);
		  	// new RiskState().setState(request.getParameter("RiskCode"), "契约业务控制->险种核保规则", "1");

		  }
		 
		 }
		 catch(Exception ex)
		 {
		  Content = ""+"保存失败，原因是:"+"" + ex.toString();
		  FlagStr = "Fail";
		 }
		/*
	} catch (Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = pDRelaBillBL.mErrors;
		if (!tError.needDealError()) {
			Content = " 保存成功! ";
			FlagStr = "Success";
		} else {
			Content = " 保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}*/

	//添加各种预处理
%>
<%=Content%>
<html>
<script type="text/javascript">
parent.fraInterface.setCalCode("<%=CLMFEECALCODE%>");
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

