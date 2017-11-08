<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%
//程序名称：PD_RiskStaffRateSave.jsp
//程序功能：员工折扣比例定义
//创建日期：2012-12-19
//创建人  ：Jucy
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.agentcommision.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>


<%
  //接收信息，并作校验处理。
  //输入参数

  //输出参数
	System.out.println("start save...");
	request.setCharacterEncoding("UTF-8");
	CErrors tError = new CErrors();
	String tOperate=request.getParameter("hideOperate");
	String tOperator=request.getParameter("Operator");
	System.out.println("Operator:"+tOperator);
	tOperate=tOperate.trim();
	String tRela  = "";
	String FlagStr = "Fail";
	String Content = "";
	
	String tBranchType = request.getParameter("BranchType");
	String tFlag = request.getParameter("Flag");
	System.out.println("tOperate="+tOperate+" tBranchType="+tBranchType);
	String currentdate = PubFun.getCurrentDate();
	String currenttime = PubFun.getCurrentTime();

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getAttribute("GI");
	if(tG==null){
		System.out.println("页面失效,请重新登陆");
		FlagStr = "Fail";
		Content = ""+"页面失效,请重新登陆"+"";
	}else{
        String tManageCom = request.getParameter("ManageOrgan").trim();
        String tAgentCom = request.getParameter("AgentOrgan").trim();
        String tRiskCode = request.getParameter("Risk").trim();
        String tPayYears = request.getParameter("PayYears").trim();
        String tInsureAgeStart = request.getParameter("InsureAgeStart").trim();
        String tInsureAgeEnd = request.getParameter("InsureAgeEnd").trim();
        String tPayToAge = request.getParameter("PayToAge").trim();
        String tCurrency = request.getParameter("Currency").trim();
        String tInvestType = request.getParameter("PayType").trim();
        String tPayType = request.getParameter("PayMode").trim();    
        String tProtectionPlan = request.getParameter("ProtectionPlan").trim();   
        String tPayYear = request.getParameter("PremPayPeriod").trim();
        String tStaffRate= request.getParameter("STAFFRATE").trim();
        String tStartDate = request.getParameter("StartDate2").trim();
        String tAppState = request.getParameter("AppState").trim();
        String tIDNo = request.getParameter("IDNo").trim();
        String tSTAFFRATE=request.getParameter("STAFFRATE").trim();
   //     String tSRFlag=request.getParameter("SRFlag").trim();
        String tDefaultFlag=request.getParameter("DefaultFlag").trim();
        if(tSTAFFRATE==null||"".equals(tSTAFFRATE)){
        	tSTAFFRATE=tStaffRate;
		}
		PD_LDStaffRateSchema tPD_LDStaffRateSchema = new PD_LDStaffRateSchema();
		tPD_LDStaffRateSchema.setBranchtype(tBranchType);
		tPD_LDStaffRateSchema.setAgentCom(tAgentCom);
		tPD_LDStaffRateSchema.setManageCom(tManageCom);
		tPD_LDStaffRateSchema.setRiskCode(tRiskCode);
		tPD_LDStaffRateSchema.setPayYear(tPayYears);
		tPD_LDStaffRateSchema.setInsureAgeStart(tInsureAgeStart);
		tPD_LDStaffRateSchema.setInsureAgeEnd(tInsureAgeEnd);
		tPD_LDStaffRateSchema.setPayToAge(tPayToAge);
		tPD_LDStaffRateSchema.setCurrency(tCurrency);
		tPD_LDStaffRateSchema.setInvestType(tInvestType);
		
		tPD_LDStaffRateSchema.setPayType(tPayType);
		tPD_LDStaffRateSchema.setProtectionPlan(tProtectionPlan);
		tPD_LDStaffRateSchema.setPayYears(tPayYear);
		tPD_LDStaffRateSchema.setStartDate(tStartDate);
		tPD_LDStaffRateSchema.setAppDate(currentdate);
		tPD_LDStaffRateSchema.setAppTime(currenttime);
		tPD_LDStaffRateSchema.setOperator(tOperator);
		tPD_LDStaffRateSchema.setMakeDate(currentdate);
		tPD_LDStaffRateSchema.setMakeTime(currenttime);
		tPD_LDStaffRateSchema.setModifyDate(currentdate);
		tPD_LDStaffRateSchema.setModifyTime(currenttime);
		tPD_LDStaffRateSchema.setStaffRate(tSTAFFRATE);
	

		tPD_LDStaffRateSchema.setDefaultFlag("1");
		tPD_LDStaffRateSchema.setSRFlag("N");
		tPD_LDStaffRateSchema.setPlanCode("");
		tPD_LDStaffRateSchema.setInsuyear("");
		tPD_LDStaffRateSchema.setInsuyearFlag("");
		tPD_LDStaffRateSchema.setCommRate("0");
		tPD_LDStaffRateSchema.setValidFlag("01");
		tPD_LDStaffRateSchema.setAppState("02");
		tPD_LDStaffRateSchema.setActionType("");
		tPD_LDStaffRateSchema.setOrigIDNo("");
		
		tPD_LDStaffRateSchema.setBranchtype(tBranchType);
		tPD_LDStaffRateSchema.setIDNo(tIDNo);
		
		System.out.println("end for...");
		
		VData tVData = new VData();
        FlagStr="";
        tVData.add(tG);
        tVData.add(tPD_LDStaffRateSchema);
		
		
		PD_RiskStaffRateUI tPD_RiskStaffRateUI = new PD_RiskStaffRateUI();
		try{
			tPD_RiskStaffRateUI.submitData(tVData,tOperate);
			tError = tPD_RiskStaffRateUI.mErrors;
            if (tError.needDealError())
            {
				Content = ""+"失败，原因:"+"" + tError.getFirstError();
				FlagStr = "Fail";
            }
		}catch(Exception ex){
			System.out.println(ex);
			Content = ""+"保存失败，原因是:"+"" + ex.toString();
			FlagStr = "Fail";
		}

		if (!FlagStr.equals("Fail")){
			tError = tPD_RiskStaffRateUI.mErrors;
			if (!tError.needDealError()){
				Content = ""+"保存成功"+"";        
				FlagStr = "Succ";
          	}else{
            	Content = ""+"保存失败，原因是:"+"" + tError.getFirstError();
            	FlagStr = "Fail";
			}
		}
	}
	//添加各种预处理
%>
<html>
	<script type="text/javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
