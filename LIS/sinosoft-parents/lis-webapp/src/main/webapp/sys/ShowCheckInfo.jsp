<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/*******************************************************************************
 * Name     ��ShowcheckInfo.jsp
 * Function ����ʾ�ۺϲ�ѯ֮�����Ϣ��ѯ
 * Author   :LiuYansong
 * Date     :2004-2-19
 */
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Succ";
	String RgtNo = request.getParameter("RgtNo");
	String Ope_Flag = "1";
  
  VData tVData = new VData();
  tVData.addElement(RgtNo);
  tVData.addElement(Ope_Flag); //��ʼ�������Ϣ
//  ShowCaseInfoUI mShowCaseInfoUI = new ShowCaseInfoUI();
//  if (!mShowCaseInfoUI.submitData(tVData,"QUERY"))
//  {
//    Content = " û�з�����������Ϣ��ԭ����: " + mShowCaseInfoUI.mErrors.getError(0).errorMessage;
//    FlagStr = "Fail";
//  }
String busiName="ShowCaseInfoUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"QUERY",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "û�з�����������Ϣ��ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "û�з�����������Ϣ";
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
      loggerDebug("ShowCheckInfo","��ѯ�ļ�¼�� "+n);
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
