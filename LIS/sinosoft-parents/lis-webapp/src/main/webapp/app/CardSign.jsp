<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.text.*"%>
<%
  String FlagStr = "Succ";
  String Content = "";
  loggerDebug("CardSign","ok");
  VData tVData = new VData();
  GlobalInput tG = new GlobalInput();
  LCContSet mLCContSet = new LCContSet();
  tG = (GlobalInput) session.getValue("GI");
  LCContSchema tLCContSchema = new LCContSchema();
  tLCContSchema.setContNo(request.getParameter("ProposalContNo"));
  mLCContSet.add(tLCContSchema);
  String mCurrentTime = PubFun.getCurrentTime();
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("CurrentDate", request.getParameter("PolAppntDate"));
  tTransferData.setNameAndValue("CurrentTime", request.getParameter("mCurrentTime"));
  tTransferData.setNameAndValue("mark", "card");
  tTransferData.setNameAndValue("CardContNo",request.getParameter("ContCardNo"));
  tVData.add(tG);
  tVData.add(mLCContSet);
  tVData.add(tTransferData);
  LCContSignBL tLCContSignBL = new LCContSignBL();
  String flag = "sign";
  if (tLCContSignBL.submitData(tVData, flag) == false) {
    Content = " 操作失败，原因是: " + tLCContSignBL.mErrors.getError(0).errorMessage;
    FlagStr = "Fail";
  }
  else {
    Content = " 操作成功! ";
    FlagStr = "Succ";
    loggerDebug("CardSign","操作成功。。。。。。。。。。。");
  }
%>
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit3("<%=FlagStr%>","<%=Content%>");
</script></html>
