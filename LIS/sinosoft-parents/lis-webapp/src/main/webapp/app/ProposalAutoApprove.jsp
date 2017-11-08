<%
//程序名称：ProposalAutoApprove.jsp
//程序功能：异常件、抽检件复核状态自动置为通过
//创建日期：2007-08-14 09:53
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("ProposalAutoApprove","***Strart ProposalAutoApprove.... ");
  String strResult = "1";
  String strInfo = "";
  String tPrtNo = request.getParameter("prtNo");
  String tDealType = request.getParameter("DealType");
  loggerDebug("ProposalAutoApprove","***tPrtNo: "+tPrtNo);
  loggerDebug("ProposalAutoApprove","***tDealType: "+tDealType);

  ExeSQL tExeSQL = new ExeSQL();
  String tSQL = "select Count(*) from BPOMissionState where bussno='"+tPrtNo+"' and bussnotype='TB' and DealType='"+tDealType+"' and State='1'";
  int tExistedCount = Integer.parseInt(tExeSQL.getOneValue(tSQL));
  loggerDebug("ProposalAutoApprove","***已经处理完成条数： "+tExistedCount);
  if(tExistedCount>0)
  {
    VData tResult = new VData();
    PubSubmit tPubSubmit = new PubSubmit();
    MMap map = new MMap();
    String str1="update LCPol set ApproveCode=Operator,ApproveFlag='9',ApproveDate='"+PubFun.getCurrentDate()+"',ModifyDate='"+PubFun.getCurrentDate()+"',ModifyTime='"+PubFun.getCurrentTime()+"' where PrtNo='"+tPrtNo+"'";
    String str2="update lccont set ApproveCode=Operator,ApproveFlag='9',ApproveDate='"+PubFun.getCurrentDate()+"',ModifyDate='"+PubFun.getCurrentDate()+"',ModifyTime='"+PubFun.getCurrentTime()+"' where PrtNo='"+tPrtNo+"'";

    map.put(str1,"UPDATE");
    map.put(str2,"UPDATE");
    tResult.add(map);
    if(!tPubSubmit.submitData(tResult,""))
    {
      strInfo = " 保单复核状态修改失败，原因是: " + tPubSubmit.mErrors.getError(0).errorMessage;
      strResult = "0";
    }      
  }
  
 loggerDebug("ProposalAutoApprove","strInfo:" + strInfo + "\nstrResult:" + strResult);
%>

<script>
  var strResult = "<%=strResult%>";
  
  window.returnValue = strResult;
  window.close();
  
</script>
