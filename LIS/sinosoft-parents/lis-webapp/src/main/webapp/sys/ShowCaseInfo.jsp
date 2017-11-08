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
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Succ";

  String PolNo = request.getParameter("PolNo");
  String Ope_Flag = "0"; //标记是初始化案件信息

  VData tVData = new VData();
  tVData.addElement(PolNo);
  tVData.addElement(Ope_Flag);
//  ShowCaseInfoUI mShowCaseInfoUI = new ShowCaseInfoUI();
//  if (!mShowCaseInfoUI.submitData(tVData,"INIT"))
//  {
//    Content = " 没有符合条件的信息，原因是: " + mShowCaseInfoUI.mErrors.getError(0).errorMessage;
//    FlagStr = "Fail";
//  }
String busiName="ShowCaseInfoUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"INIT",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "没有符合条件的信息，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "没有符合条件的信息";
		FlagStr = "Fail";				
	}
}

  else
  {
    tVData.clear();
    //tVData = mShowCaseInfoUI.getResult();
    tVData = tBusinessDelegate.getResult();
    LLCasePolicySet mLLCasePolicySet = new LLCasePolicySet();
    mLLCasePolicySet.set((LLCasePolicySet)tVData.getObjectByObjectName("LLCasePolicySet",0));
    int n = mLLCasePolicySet.size();
    if(n>0)
    {
      loggerDebug("ShowCaseInfo","查询的记录是 "+n);
      String Strtest = "0|" + n + "^" + mLLCasePolicySet.encode();
      loggerDebug("ShowCaseInfo","QueryResult: " + Strtest);
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

loggerDebug("ShowCaseInfo","------end------");
loggerDebug("ShowCaseInfo",FlagStr);
loggerDebug("ShowCaseInfo",Content);
%>
