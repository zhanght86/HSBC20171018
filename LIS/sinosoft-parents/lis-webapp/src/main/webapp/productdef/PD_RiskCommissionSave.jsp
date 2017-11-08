<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：LABKRateToSave.jsp
//程序功能：
//创建日期：2002-08-16 15:12:33
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.agentcommision.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>  


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
   if(tG==null)
  {
    System.out.println("页面失效,请重新登陆");
    FlagStr = "Fail";
    Content = ""+"页面失效,请重新登陆"+"";
  }else
  {
        String tManageCom = request.getParameter("ManageOrgan").trim();
        String tAgentCom = request.getParameter("AgentOrgan").trim();
        String tRiskCode = request.getParameter("Risk").trim();
        String tPremPayPeriod = request.getParameter("PayYears").trim();
        String tInsureAgeStart = request.getParameter("InsureAgeStart").trim();
        String tInsureAgeEnd = request.getParameter("InsureAgeEnd").trim();
        String tPayToAge = request.getParameter("PayToAge").trim();
        String tCurrency = request.getParameter("Currency").trim();
        String tInvestType = request.getParameter("PayType").trim();
        String tPayType = request.getParameter("PayMode").trim();
        String tProtectionPlan = request.getParameter("ProtectionPlan").trim();
        String tPayYear = request.getParameter("PremPayPeriod").trim();
        String tCommissionRate = request.getParameter("CommissionRate").trim();
        String tStartDate = request.getParameter("StartDate2").trim();
        String tAppState = request.getParameter("AppState").trim();
        String tIDNo = request.getParameter("IDNo").trim();
        String tSTAFFRATE=request.getParameter("STAFFRATE").trim();
        String tSRFlag=request.getParameter("SRFlag").trim();
        String tDefaultFlag=request.getParameter("DefaultFlag").trim();
        if(tSTAFFRATE==null||"".equals(tSTAFFRATE)){
        tSTAFFRATE=tCommissionRate;
        }

           	 PD_LACommissionRateSchema tPD_LACommissionRateSchema = new PD_LACommissionRateSchema();
        	 tPD_LACommissionRateSchema.setBranchtype(tBranchType);
        	 tPD_LACommissionRateSchema.setManageCom(tManageCom);
        	 tPD_LACommissionRateSchema.setAgentCom(tAgentCom);
        	 tPD_LACommissionRateSchema.setRiskCode(tRiskCode);
        	 tPD_LACommissionRateSchema.setPayYears(tPayYear);
        	 tPD_LACommissionRateSchema.setInsureAgeStart(tInsureAgeStart);
        	 tPD_LACommissionRateSchema.setInsureAgeEnd(tInsureAgeEnd);
        	 tPD_LACommissionRateSchema.setPayToAge(tPayToAge);
        	 tPD_LACommissionRateSchema.setCurrency(tCurrency);
        	 tPD_LACommissionRateSchema.setInvestType(tInvestType);
        	 tPD_LACommissionRateSchema.setPayType(tPayType);
        	 tPD_LACommissionRateSchema.setProtectionPlan(tProtectionPlan);
        	 tPD_LACommissionRateSchema.setPayYear(tPremPayPeriod);
        	 tPD_LACommissionRateSchema.setCommRate(tCommissionRate);
        	 tPD_LACommissionRateSchema.setStartDate(tStartDate);
        	 tPD_LACommissionRateSchema.setAppState(tAppState);
        	 tPD_LACommissionRateSchema.setIDNo(tIDNo);
        	 tPD_LACommissionRateSchema.setOperator(tOperator);
        	 tPD_LACommissionRateSchema.setMakeDate(currentdate);
        	 tPD_LACommissionRateSchema.setMakeTime(currenttime);
        	 tPD_LACommissionRateSchema.setModifyDate(currentdate);
        	 tPD_LACommissionRateSchema.setModifyTime(currenttime);
        	 tPD_LACommissionRateSchema.setStaffRate(tSTAFFRATE);
        	 tPD_LACommissionRateSchema.setDefaultFlag(tDefaultFlag);
        	 tPD_LACommissionRateSchema.setSRFlag(tSRFlag);
        	 
        	 
        System.out.println("end for...");
         VData tVData = new VData();
        FlagStr="";
        tVData.add(tG);
        tVData.add(tPD_LACommissionRateSchema);
       PD_RiskCommissionUI tLABKCommRateMaintainUI = new PD_RiskCommissionUI();
          try
          {
        	  
        	 tLABKCommRateMaintainUI.submitData(tVData,tOperate);
             tError = tLABKCommRateMaintainUI.mErrors;
            if (tError.needDealError())
            {
              Content = ""+"失败，原因:"+"" + tError.getFirstError();
              FlagStr = "Fail";
            }

          }catch(Exception ex)
          {          System.out.println(ex);
            Content = ""+"保存失败，原因是:"+"" + ex.toString();
            FlagStr = "Fail";
          }



      if (!FlagStr.equals("Fail"))
      {
          tError = tLABKCommRateMaintainUI.mErrors;
          if (!tError.needDealError())
          {
           
                Content = ""+"保存成功"+"";        
                FlagStr = "Succ";
          }
          else{
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

