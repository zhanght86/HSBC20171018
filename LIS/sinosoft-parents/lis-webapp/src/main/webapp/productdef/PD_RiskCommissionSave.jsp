<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ�LABKRateToSave.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:12:33
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.agentcommision.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>  


<%
  //������Ϣ������У�鴦��
  //�������


  //�������
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
    System.out.println("ҳ��ʧЧ,�����µ�½");
    FlagStr = "Fail";
    Content = ""+"ҳ��ʧЧ,�����µ�½"+"";
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
              Content = ""+"ʧ�ܣ�ԭ��:"+"" + tError.getFirstError();
              FlagStr = "Fail";
            }

          }catch(Exception ex)
          {          System.out.println(ex);
            Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
            FlagStr = "Fail";
          }



      if (!FlagStr.equals("Fail"))
      {
          tError = tLABKCommRateMaintainUI.mErrors;
          if (!tError.needDealError())
          {
           
                Content = ""+"����ɹ�"+"";        
                FlagStr = "Succ";
          }
          else{
            	Content = ""+"����ʧ�ܣ�ԭ����:"+"" + tError.getFirstError();
            	FlagStr = "Fail";
          }
      }
}
  //��Ӹ���Ԥ����
%>
<html>
<script type="text/javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

