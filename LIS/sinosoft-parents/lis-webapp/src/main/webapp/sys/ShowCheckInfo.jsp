<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/*******************************************************************************
 * Name     ：ShowcheckInfo.jsp
 * Function ：显示综合查询之审核信息查询
 * Author   :LiuYansong
 * Date     :2004-2-19
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
	String RgtNo = request.getParameter("RgtNo");
	String Ope_Flag = "1";
  
  VData tVData = new VData();
  tVData.addElement(RgtNo);
  tVData.addElement(Ope_Flag); //初始化审核信息
//  ShowCaseInfoUI mShowCaseInfoUI = new ShowCaseInfoUI();
//  if (!mShowCaseInfoUI.submitData(tVData,"QUERY"))
//  {
//    Content = " 没有符合条件的信息，原因是: " + mShowCaseInfoUI.mErrors.getError(0).errorMessage;
//    FlagStr = "Fail";
//  }
String busiName="ShowCaseInfoUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"QUERY",busiName))
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
    LLClaimUWMDetailSet mLLClaimUWMDetailSet = new LLClaimUWMDetailSet();
    mLLClaimUWMDetailSet.set((LLClaimUWMDetailSet)tVData.getObjectByObjectName("LLClaimUWMDetailSet",0));
    int n = mLLClaimUWMDetailSet.size();
    if(n>0)
    {
      loggerDebug("ShowCheckInfo","查询的记录是 "+n);
      String Strtest = "0|" + n + "^" + mLLClaimUWMDetailSet.encode();
      loggerDebug("ShowCheckInfo","QueryResult: " + Strtest);
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

loggerDebug("ShowCheckInfo","------end------");
loggerDebug("ShowCheckInfo",FlagStr);
loggerDebug("ShowCheckInfo",Content);
%>
