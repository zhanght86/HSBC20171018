<%
//�������ƣ�ProposalAutoApprove.jsp
//�����ܣ��쳣������������״̬�Զ���Ϊͨ��
//�������ڣ�2007-08-14 09:53
//���¼�¼��  ������    ��������     ����ԭ��/����
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
  loggerDebug("ProposalAutoApprove","***�Ѿ�������������� "+tExistedCount);
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
      strInfo = " ��������״̬�޸�ʧ�ܣ�ԭ����: " + tPubSubmit.mErrors.getError(0).errorMessage;
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
