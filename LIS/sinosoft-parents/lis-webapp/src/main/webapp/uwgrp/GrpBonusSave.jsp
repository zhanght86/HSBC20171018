<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//������Ϣ������У�鴦��
//�������
LCGrpBonusSchema tLCGrpBonusSchema=new LCGrpBonusSchema();
LCGrpBonusSchema ttLCGrpBonusSchema=new LCGrpBonusSchema();
LCGrpBonusSubSchema tLCGrpBonusSubSchema=new LCGrpBonusSubSchema();
//�������
CErrors tError = null;
String tRearStr = "";
String Content = "";
String tRela = "";
String FlagStr="";
String operator=request.getParameter("frmAction");
loggerDebug("GrpBonusSave","operator========"+operator);
//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

String GrpContNo = request.getParameter("GrpContNo");	
String AppntNo = request.getParameter("AppntNo");	
//String Degree = request.getParameter("Degree");	
String RiskCode=request.getParameter("RiskCode");	
String GrpPolNo = request.getParameter("GrpPolNo");
String InsuYear = request.getParameter("InsuYear");	
String InsuYearFlag=request.getParameter("InsuYearFlag");	
String RewardType = request.getParameter("RewardType");	
String RewardValue = request.getParameter("RewardValue");
//suoxin add
RewardValue = String.valueOf(Double.parseDouble(RewardValue) + Double.parseDouble("0.025"));
			
String StartDate=request.getParameter("StartDate"); 
String EndDate = request.getParameter("EndDate");
String PayMoney=request.getParameter("PayMoney"); 
String Note=request.getParameter("Note");
//���ڱ�ʾ�޸� 
String GrpPolNo1 = request.getParameter("GrpPolNo1");

//ȡ�ø���ֺ��˱���Ϣ
String GrpContNo2 = request.getParameter("GrpContNo2");	
String AppntNo2 = request.getParameter("AppntNo2");	
String RiskCode2=request.getParameter("RiskCode2");	
String GrpPolNo2 = request.getParameter("GrpPolNo2");
loggerDebug("GrpBonusSave","GrpPolNo2========"+GrpPolNo2);
String AccountPassYear = request.getParameter("AccountPassYear");	
String AccountValueRate = request.getParameter("AccountValueRate");	
String Note2=request.getParameter("Note2");
	
//������Ϣ��ֵ
        tLCGrpBonusSchema.setGrpContNo(GrpContNo);
        tLCGrpBonusSchema.setAppntNo(AppntNo);
//      tLCGrpBonusSchema.setDegree(Degree);
        tLCGrpBonusSchema.setDegree("");
        tLCGrpBonusSchema.setRiskCode(RiskCode);
        tLCGrpBonusSchema.setGrpPolNo(GrpPolNo);
        tLCGrpBonusSchema.setInsuYear(InsuYear);
        tLCGrpBonusSchema.setInsuYearFlag(InsuYearFlag);
        tLCGrpBonusSchema.setRewardType(RewardType);
        tLCGrpBonusSchema.setRewardValue(RewardValue);
        tLCGrpBonusSchema.setStartDate(StartDate);
        tLCGrpBonusSchema.setEndDate(EndDate);
        tLCGrpBonusSchema.setPayMoney(PayMoney); 
        tLCGrpBonusSchema.setNote(Note); 
        
        //����ֺ��˱���Ϣ����
         tLCGrpBonusSubSchema.setGrpContNo(GrpContNo2);
         tLCGrpBonusSubSchema.setAppntNo(AppntNo2);
         tLCGrpBonusSubSchema.setRiskCode(RiskCode2);
         tLCGrpBonusSubSchema.setGrpPolNo(GrpPolNo2);
         tLCGrpBonusSubSchema.setAccountPassYears(AccountPassYear);
         tLCGrpBonusSubSchema.setAccountValueRate(AccountValueRate);
    //     tLCGrpBonusSubSchema.setGrpPolNo(Note2);
         
       if(operator.equals("UPDATE"))
       {
        ttLCGrpBonusSchema.setGrpPolNo(GrpPolNo1);
        }
        // ׼���������� VData
          VData tVData = new VData();
          tVData.add(tG);
          tVData.add(tLCGrpBonusSchema);
          tVData.add(tLCGrpBonusSubSchema);
          
         if(operator.equals("UPDATE"))
         {
           tVData.add(ttLCGrpBonusSchema);
          }
          LCGrpBonusBL tLCGrpBonusBL=new LCGrpBonusBL();
          
       
        if(!tLCGrpBonusBL.submitData(tVData,operator))
        { 
          loggerDebug("GrpBonusSave","fail");
          Content = tLCGrpBonusBL.mErrors.getError(0).errorMessage;
          FlagStr = "Fail";
        }else{
          loggerDebug("GrpBonusSave","ok");
          Content = "����ɹ�!";
          FlagStr = "Succ";
         }
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
