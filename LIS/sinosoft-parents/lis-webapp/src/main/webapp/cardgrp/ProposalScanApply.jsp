<%
//程序名称：ProposalScanApply.jsp
//程序功能：投保单扫描件申请
//创建日期：2002-11-23 17:06:57
//创建人  ：胡博
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  String strResult = "";
  String strInfo = "";
  String prtNo = request.getParameter("prtNo");
  String operator = request.getParameter("operator");
  String state = request.getParameter("state");
  
  VData tVData = new VData();
  ES_DOC_MAINSchema  tES_DOC_MAINSchema = new ES_DOC_MAINSchema();

  tES_DOC_MAINSchema.setDocCode(prtNo);
  tES_DOC_MAINSchema.setScanOperator(operator);
  tES_DOC_MAINSchema.setDocFlag(state);
  tVData.add(tES_DOC_MAINSchema);
  
  ScanApplyUI tScanApplyUI = new ScanApplyUI();
  String tOperate = "INSERT||MAIN";
  loggerDebug("ProposalScanApply","hh="+tOperate);
  if(!tScanApplyUI.submitData(tVData, tOperate)) {
    strInfo = "该印刷号刚刚被其他操作员申请，请重新选择新的印刷号进行申请！";
    strResult = "0";
  }
  else {
    if (state.equals("0")) {
      strInfo = "申请成功！";
      strResult = "1";
    }
    else {
      strInfo = "该印刷号对应的保单录入已全部完成！\n不再能够查询出来进行录入！";
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
  <input type=button class=common value="确 定" onclick="closeClick()">
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
