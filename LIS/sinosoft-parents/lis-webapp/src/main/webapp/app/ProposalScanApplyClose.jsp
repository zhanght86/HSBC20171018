<%
//�������ƣ�ProposalScanApplyClose.jsp
//�����ܣ�Ͷ����ɨ�������
//�������ڣ�2002-11-23 17:06:57
//������  ������
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
  String strResult = "";
  String strInfo = "";
  String prtNo = request.getParameter("prtNo");
  String operator = request.getParameter("operator");
  String state = request.getParameter("state");
  
  VData tVData = new VData();
  ES_DOC_MAINSchema  tES_DOC_MAINSchema = new ES_DOC_MAINSchema();

  tES_DOC_MAINSchema.setDOC_CODE(prtNo);
  tES_DOC_MAINSchema.setOperator(operator);
  tES_DOC_MAINSchema.setInputState(state);
  tVData.add(tES_DOC_MAINSchema);
  
  ScanApplyUI tScanApplyUI = new ScanApplyUI();
  String tOperate = "QUERY||MAIN";
  
  if(tScanApplyUI.submitData(tVData, tOperate)) {
    VData outVData = tScanApplyUI.getResult();
    strResult = "0|" + outVData.size() + "^" + (String)outVData.get(0);
    loggerDebug("ProposalScanApplyClose","strResult:" + strResult);
  }
  
  //loggerDebug("ProposalScanApplyClose","strInfo:" + strInfo + "\nstrResult:" + strResult);
%>

<script>
  var strResult = "<%=strResult%>";
  
  window.returnValue = strResult;
  window.close();
  
</script>
