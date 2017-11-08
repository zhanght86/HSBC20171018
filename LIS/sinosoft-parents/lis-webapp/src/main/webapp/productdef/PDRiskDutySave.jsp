<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%
//程序名称：PDRiskDutyRelaSave.jsp
//程序功能：险种关联责任
//创建日期：2009-3-12
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

 //PDRiskDutyRelaBL tPDRiskDutyRelaBL = new PDRiskDutyRelaBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 
TransferData transferData = new TransferData();
GlobalInput tG = new GlobalInput(); 
tG=(GlobalInput)session.getAttribute("GI");
operator = request.getParameter("operatorS");
String tDutyCode  ="";
if("del".equals(operator) ){
	String tGetSel[] = request.getParameterValues("InpMulline14GridSel");
	String tDutyArr[] = request.getParameterValues("Mulline14Grid1");
	for(int i=0;i<tGetSel.length;i++){
		if(tGetSel[i].equals("1")){
			tDutyCode = tDutyArr[i];
		}
	}
}

if("update".equals(operator) ){
	String tGetSel[] = request.getParameterValues("InpMulline14GridSel");
	String tDutyArr[] = request.getParameterValues("Mulline14Grid1");
	for(int i=0;i<tGetSel.length;i++){
		if(tGetSel[i].equals("1")){
			tDutyCode = tDutyArr[i];
		}
	}
}
 String riskCode = request.getParameter("riskCodeS");
 transferData.setNameAndValue("riskCode",riskCode);
 transferData.setNameAndValue("DutyCode",tDutyCode);

 System.out.println("riskCode:" + riskCode);
 //获得责任代码
	//String maxdutysel = "select code from ldcode where codetype = 'dutysel'";
	//ExeSQL ttExeSQL = new ExeSQL();
	//SSRS ttSSRS = ttExeSQL.execSQL(maxdutysel);
	//
	//String maxSel = ttSSRS.GetText(1, 1);
	//int maxSelInt = Integer.parseInt(maxSel);
	//
	//int temp = maxSelInt + 1;
	//String sql = "update ldcode set code = '" + temp + "' where codetype = 'dutysel'";
	//ttExeSQL.execUpdateSQL(sql);
	//
	//String tempStr = temp + "";
	//if(tempStr.length() < 3){
	//	int tempStrL = tempStr.length();
	//	for(int i = tempStrL; i < 3; i ++){
	//		tempStr = "0" + tempStr;
	//	}
	//}
	//String tPayPlanCode = "";
	//if(riskCode.length() < 4){
	//	tPayPlanCode = tPayPlanCode + tempStr;
	//}else{
	//	tPayPlanCode = riskCode.substring(0, 3);
	//	tPayPlanCode = tPayPlanCode + tempStr;
	//}
	//
	//
	//System.out.println(" +++++++++++++++++++ " + tPayPlanCode);
 
	String tChoFlag = request.getParameter("choFlag");
	String tSpecFlag = "Y";
	String tRiskVer = "2012";
	
	System.out.println("lmriskduty的信息：  riskCode :" + riskCode + " RiskVer: " + tRiskVer + " ChoFlag:" + tChoFlag + " tSpecFlag:" + tSpecFlag);
	
	//获得lmduty的数据
	String tDutyName = request.getParameter("DutyName");
	String tInsuYear = request.getParameter("InsuYear");
	String tInsuYearFlag = request.getParameter("InsuYearFlag");
	String tInsuYearRela = request.getParameter("InsuYearRela");
	String tPayEndDateCalRef = request.getParameter("PayEndDateCalRef");
	String tPayEndDateCalMode = request.getParameter("PayEndDateCalMode");
	String tPayEndYearRela = request.getParameter("PayEndYearRela");
	String tGetYear = request.getParameter("GetYear");
	String tGetYearFlag = request.getParameter("GetYearFlag");
	String tGetYearRela = request.getParameter("GetYearRela");
	String tCalMode = request.getParameter("CalMode");
	//String tBasicCalCode = request.getParameter("BasicCalCode");
	String tBasicCalCode ="000003";
	String tAmntFlag = request.getParameter("AmntFlag");
	String tVPU = request.getParameter("VPU");
	//String AddAmntFlag = request.getParameter("AddAmntFlagDuty");
	//  modify AddAmntFlag
	String AddAmntFlag="1";
	//-------- add by jucy
	String tPCalMode = request.getParameter("PCalMode");
	//-------- end
	
	//-------- add by jucy
	//缴费终止期间 文本录入框；存储在LMDuty 表的PAYENDYEAR
	//缴费终止期间单位： 存储在LMDuty 表的PAYENDYEARFLAG 
	String tPayEndYear = request.getParameter("DutyPayEndYear");
	String tPayEndYearFlag = request.getParameter("DutyPayEndYearFlag");
	//-------- end
	//PDRiskDutyBL tPDRiskDutyBL = new PDRiskDutyBL();
	System.out.println("lmduty的信息： DutyName:" + tDutyName + " InsuYear:" + tInsuYear + " InsuYearFlag:" + tInsuYearFlag + " InsuYearRela:" + tInsuYearRela);
	try{
	 	// 准备传输数据 VData
	 	VData tVData = new VData(); 
	 	
	 	//lmriskduty的schema中赋值
		PD_LMRiskDutySchema pd_LMRiskDutySchema = new PD_LMRiskDutySchema();
		pd_LMRiskDutySchema.setRiskCode(riskCode);
		pd_LMRiskDutySchema.setRiskVer(tRiskVer);
		//pd_LMRiskDutySchema.setDutyCode(tPayPlanCode);
		pd_LMRiskDutySchema.setChoFlag(tChoFlag);
		pd_LMRiskDutySchema.setSpecFlag(tSpecFlag);
		tVData.add(pd_LMRiskDutySchema);
		
		//向lmduty的schema中赋值
		PD_LMDutySchema tPD_LMDutySchema = new PD_LMDutySchema();
		//tPD_LMDutySchema.setDutyCode(tPayPlanCode);
		tPD_LMDutySchema.setDutyName(tDutyName);
		tPD_LMDutySchema.setInsuYear(tInsuYear);
		tPD_LMDutySchema.setInsuYearFlag(tInsuYearFlag);
		tPD_LMDutySchema.setInsuYearRela(tInsuYearRela);
		tPD_LMDutySchema.setPayEndDateCalRef(tPayEndDateCalRef);
		tPD_LMDutySchema.setPayEndDateCalMode(tPayEndDateCalMode);
		tPD_LMDutySchema.setGetYearFlag(tGetYearFlag);
		tPD_LMDutySchema.setGetYear(tGetYear);
		tPD_LMDutySchema.setCalMode(tCalMode);
		tPD_LMDutySchema.setBasicCalCode(tBasicCalCode);
		tPD_LMDutySchema.setAmntFlag(tAmntFlag);
		tPD_LMDutySchema.setVPU(tVPU);
		tPD_LMDutySchema.setPayEndYearRela(tPayEndYearRela);
		tPD_LMDutySchema.setGetYearRela(tGetYearRela);
		
		/**------Add RiskCalCode 2016-11-10---------*/
		tPD_LMDutySchema.setRiskCalCode("000003");
		
		if(tInsuYearRela != null && !tInsuYearRela.trim().equals("")){
			tPD_LMDutySchema.setInsuYearRela(tInsuYearRela);
		}else{
			tPD_LMDutySchema.setInsuYearRela("0");
		}
		//-------- add by jucy
		//1)ADDAMNTFLAG，如果险种是非万能或非投连，则系统自动存储为1；如果险种是万能或投连，则系统自动存储为2
		tPD_LMDutySchema.setAddAmntFlag(AddAmntFlag);
		//2)建议书计算方法
		tPD_LMDutySchema.setPCalMode(tPCalMode);
		//-------- end
		//-------- add by jucy
		//新增输入框
		tPD_LMDutySchema.setPayEndYear(tPayEndYear);
		tPD_LMDutySchema.setPayEndYearFlag(tPayEndYearFlag);
		//-------- end
		tVData.add(tPD_LMDutySchema);
		
	 	tVData.add(tG);
	 	tVData.add(transferData);
	 	
	 	//tPDRiskDutyBL.submitData(tVData,operator);
	 	
		//	new RiskState().setState(riskCode, "产品条款定义->责任信息", "1");
		String busiName="PDRiskDutyBL";
	   
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//String tDiscountCode = "";
		if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
			VData rVData = tBusinessDelegate.getResult();
			 Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}
		else {
			Content = " "+"处理成功!"+" ";
			FlagStr = "Success";
		}
	}catch(Exception ex){
		System.out.println("========"+ ex.getMessage());
		Content = ""+"保存失败，原因是:"+"" + ex.getMessage();
		FlagStr = "Fail";
	}
/*
}catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}
	//如果在Catch中发现异常，则不从错误类中提取错误信息
if (FlagStr==""){
	tError = tPDRiskDutyBL.mErrors;
	if (!tError.needDealError()){                          
		Content = " 保存成功! ";
		FlagStr = "Success";
	}else{
	   Content = " 保存失败，原因是:" + tError.getFirstError();
	   FlagStr = "Fail";
	}
}*/

 //添加各种预处理
%>
<html>
	<script type="text/javascript">
 parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>