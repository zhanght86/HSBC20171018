<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/*******************************************************************************
 * Name     ��ShowCaseInfo.jsp
 * Function ����ʾ�ۺϲ�ѯ���������ϸ��Ϣ
 * Author   :LiuYansong
 * Date     :2004-2-17
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

  String PolNo = request.getParameter("PolNo");
  String Ope_Flag = "0"; //����ǳ�ʼ��������Ϣ

  VData tVData = new VData();
  tVData.addElement(PolNo);
  tVData.addElement(Ope_Flag);
//  ShowCaseInfoUI mShowCaseInfoUI = new ShowCaseInfoUI();
//  if (!mShowCaseInfoUI.submitData(tVData,"INIT"))
//  {
//    Content = " û�з�����������Ϣ��ԭ����: " + mShowCaseInfoUI.mErrors.getError(0).errorMessage;
//    FlagStr = "Fail";
//  }
String busiName="ShowCaseInfoUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"INIT",busiName))
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
    LLCasePolicySet mLLCasePolicySet = new LLCasePolicySet();
    mLLCasePolicySet.set((LLCasePolicySet)tVData.getObjectByObjectName("LLCasePolicySet",0));
    int n = mLLCasePolicySet.size();
    if(n>0)
    {
      loggerDebug("ShowCaseInfo","��ѯ�ļ�¼�� "+n);
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
