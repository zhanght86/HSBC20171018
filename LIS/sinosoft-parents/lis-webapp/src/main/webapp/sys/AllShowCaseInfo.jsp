<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/*******************************************************************************
 * Name     ：ShowCaseInfo.jsp
 * Function ：显示综合查询中理赔的详细信息
 * Author   :LiuYansong
 * Date     :2004-2-17
 */
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
<%
  //输出参数
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Succ";

  String InsuredNo = request.getParameter("InsuredNo");
  String Ope_Flag = "3"; //标记是初始化案件信息(按投保人编码查询)

  VData tVData = new VData();
  tVData.addElement(InsuredNo);
  tVData.addElement(Ope_Flag);
  ShowCaseInfoUI mShowCaseInfoUI = new ShowCaseInfoUI();
  if (!mShowCaseInfoUI.submitData(tVData,"INIT"))
  {
    Content = " 没有符合条件的信息，原因是: " + mShowCaseInfoUI.mErrors.getError(0).errorMessage;
    FlagStr = "Fail";
  }
  else
  {
    tVData.clear();
    tVData = mShowCaseInfoUI.getResult();
    LLCasePolicySet mLLCasePolicySet = new LLCasePolicySet();
    mLLCasePolicySet.set((LLCasePolicySet)tVData.getObjectByObjectName("LLCasePolicySet",0));
    int n = mLLCasePolicySet.size();
    if(n>0)
    {
      loggerDebug("AllShowCaseInfo","查询的记录是 "+n);
      String Strtest = "0|" + n + "^" + mLLCasePolicySet.encode();
      loggerDebug("AllShowCaseInfo","QueryResult: " + Strtest);
      %>
      <script language="javascript">
      try
      {
        parent.fraInterface.displayQueryResult1('<%=Strtest%>');
      }
      catch(ex) {}
      </script>
      <%
    }
  }

loggerDebug("AllShowCaseInfo","------end------");
loggerDebug("AllShowCaseInfo",FlagStr);
loggerDebug("AllShowCaseInfo",Content);
%>
