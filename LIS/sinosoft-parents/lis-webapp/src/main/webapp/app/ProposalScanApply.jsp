<%
//�������ƣ�ProposalScanApply.jsp
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
<%@page import="com.sinosoft.service.*" %>
<%
  String strResult = "";
  String strInfo = "";
  String prtNo = request.getParameter("prtNo");
  String operator = request.getParameter("operator");
  String state = request.getParameter("state");
  
  VData tVData = new VData();
  ES_DOC_MAINSchema  tES_DOC_MAINSchema = new ES_DOC_MAINSchema();

  tES_DOC_MAINSchema.setDocCode(prtNo);
  tES_DOC_MAINSchema.setOperator(operator);
  tES_DOC_MAINSchema.setInputState(state);
  tVData.add(tES_DOC_MAINSchema);
  
  String busiName="tbScanApplyUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //ScanApplyUI tScanApplyUI = new ScanApplyUI();
  String tOperate = "INSERT||MAIN";
  loggerDebug("ProposalScanApply","hh="+tOperate);
  if(!tBusinessDelegate.submitData(tVData, tOperate,busiName)) {
    strInfo = "��ӡˢ�Ÿոձ���������Ա���룬������ѡ���µ�ӡˢ�Ž������룡";
    strResult = "0";
  }
  else {
    if (state.equals("0")) {
      strInfo = "����ɹ���";
      strResult = "1";
    }
    else {
      strInfo = "��ӡˢ�Ŷ�Ӧ�ı���¼����ȫ����ɣ�\n�����ܹ���ѯ��������¼�룡";
      strResult = "1";
    }
  }
  
  loggerDebug("ProposalScanApply","strInfo:" + strInfo + "\nstrResult:" + strResult);
%>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<body onunload="closeClick()">
<center>
  <br><br><br><br>
  <%=strInfo%>
  <br><br>
  <input type=button class=cssButton value="ȷ ��" onclick="closeClick()">
</center>

<script>

function closeClick() {
  try {
    window.returnValue = "<%=strResult%>";
  }
  catch(e) {}
  
  window.close()
}

<%if (strResult.equals("1")) { %>
  closeClick();
<%}%>
</script>
