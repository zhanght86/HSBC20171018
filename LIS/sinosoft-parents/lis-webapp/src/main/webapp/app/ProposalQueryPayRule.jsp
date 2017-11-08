<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>     
<%@page import="com.sinosoft.utility.*"%>

<%
  //输出参数
  loggerDebug("ProposalQueryPayRule","here");
  CErrors tError = null;
  LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
  tLCPayRuleFactorySchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLCPayRuleFactorySchema.setRiskCode(request.getParameter("RiskCode"));
  tLCPayRuleFactorySchema.setPayRuleCode(request.getParameter("PayRuleCode"));
  
  LCPolSchema tLCPolSchema = new LCPolSchema();
  tLCPolSchema.setInterestDifFlag(request.getParameter("AppFlag"));
  tLCPolSchema.setInsuredNo(request.getParameter("CustomerNo"));
  
  LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
  //loggerDebug("ProposalQueryPayRule","JoinCompanyDate->"+request.getParameter("JoinCompanyDate"));
  tLCInsuredSchema.setJoinCompanyDate(request.getParameter("JoinCompanyDate"));
  tLCInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));
  tLCInsuredSchema.setSalary(request.getParameter("Salary"));
  //loggerDebug("ProposalQueryPayRule","Salary=" + request.getParameter("Salary"));
  
//  TransferData tTransferData = new TransferData();
//  tTransferData.setNameAndValue("JoinCompanyDate", request.getParameter("JoinCompanyDate"));
//  tTransferData.setNameAndValue("AppFlag", request.getParameter("AppFlag"));
//  tTransferData.setNameAndValue("InsuredNo", request.getParameter("CustomerNo"));
//  loggerDebug("ProposalQueryPayRule","InsuredNo"+request.getParameter("CustomerNo"));
//  loggerDebug("ProposalQueryPayRule","JoinCompanyDate"+request.getParameter("JoinCompanyDate"));
//  loggerDebug("ProposalQueryPayRule","AppFlag"+request.getParameter("AppFlag"));
  
  VData tVData = new VData();
  tVData.addElement(tLCPayRuleFactorySchema);
//  tVData.addElement(tTransferData);
  tVData.addElement(tLCPolSchema);
  tVData.addElement(tLCInsuredSchema);
  
  QueryPayRuleUI tQueryPayRuleUI = new QueryPayRuleUI();
  loggerDebug("ProposalQueryPayRule","adfsfds");
  if(!tQueryPayRuleUI.submitData(tVData)){
    
  }
  else{
    VData tResult = new VData();
    tResult = tQueryPayRuleUI.getResult();
    LCPremSet tLCPremSet = new LCPremSet(); 
    tLCPremSet = (LCPremSet)tResult.getObjectByObjectName("LCPremSet",-1);
   // loggerDebug("ProposalQueryPayRule","size  :  "+tVector.size());
    if(tLCPremSet.size()>0){
      for(int i=0;i<tLCPremSet.size();i++){
         String tPayPlanCode = "";
      	 String tPrem ="";
      	 tPayPlanCode = tLCPremSet.get(i+1).getPayPlanCode().toString();
      	 tPrem = String.valueOf(tLCPremSet.get(i+1).getPrem());
        loggerDebug("ProposalQueryPayRule","paypalncode:"+tPayPlanCode+";Prem:"+tPrem);
        %>
        <script>
       for (var j=0;j<parent.fraInterface.PremGrid.mulLineCount;j++){
          if(parent.fraInterface.PremGrid.getRowColData(j,2)=="<%=tPayPlanCode%>"){
            parent.fraInterface.PremGrid.setRowColData(j,4,"<%=tPrem%>");
            parent.fraInterface.PremGrid.checkBoxSel(j+1);
          }
        }
        </script>
        <%
      }
      
    }
    else{
      %>
        <script>
       for (var j=0;j<parent.fraInterface.PremGrid.mulLineCount;j++){
          
            parent.fraInterface.PremGrid.setRowColData(j,4,"0");
          
        }
        </script>
        <% 
    }
  }
  
%>
  <script>
      parent.fraInterface.fm.action="./ProposalSave.jsp";
  </script>
<%

%>
  
