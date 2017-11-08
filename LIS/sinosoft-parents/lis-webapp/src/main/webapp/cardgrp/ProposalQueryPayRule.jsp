<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>
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
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("JoinCompanyDate", request.getParameter("JoinCompanyDate"));
  tTransferData.setNameAndValue("AppFlag", request.getParameter("AppFlag"));
  tTransferData.setNameAndValue("InsuredNo", request.getParameter("CustomerNo"));
  loggerDebug("ProposalQueryPayRule","InsuredNo"+request.getParameter("CustomerNo"));
  loggerDebug("ProposalQueryPayRule","JoinCompanyDate"+request.getParameter("JoinCompanyDate"));
  loggerDebug("ProposalQueryPayRule","AppFlag"+request.getParameter("AppFlag"));
  
  VData tVData = new VData();
  tVData.addElement(tLCPayRuleFactorySchema);
  tVData.addElement(tTransferData);
  
  QueryPayRuleUI tQueryPayRuleUI = new QueryPayRuleUI();
  loggerDebug("ProposalQueryPayRule","adfsfds");
  if(!tQueryPayRuleUI.submitData(tVData)){
    
  }
  else{
    Vector tVector = new Vector();
    tVector = tQueryPayRuleUI.getResult();
    loggerDebug("ProposalQueryPayRule","size  :  "+tVector.size());
    for(int i=0;i<tVector.size();i++){
      loggerDebug("ProposalQueryPayRule","vector:"+tVector.get(i).toString());
    }
    if(tVector.size()>0){
      for(int i=0;i<tVector.size()/2;i++){
        loggerDebug("ProposalQueryPayRule","paypalncode:"+tVector.get(i*2).toString()+"  rate:"+tVector.get(i*2+1).toString());
        %>
        <script>
        for (var j=0;j<parent.fraInterface.PremGrid.mulLineCount;j++){
          if(parent.fraInterface.PremGrid.getRowColData(j,2)=="<%=tVector.get(i*2).toString()%>"){
            parent.fraInterface.PremGrid.setRowColData(j,9,"<%=tVector.get(i*2+1).toString()%>");
          }
        }
        </script>
        <%
      }
      %>
      parent.fraInterface.fm.action="./ProposalSave.jsp";
      <%
    }
  }
  
%>
  
