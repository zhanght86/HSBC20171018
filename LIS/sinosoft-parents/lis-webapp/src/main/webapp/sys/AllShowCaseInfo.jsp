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
<%
  //�������
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Succ";

  String InsuredNo = request.getParameter("InsuredNo");
  String Ope_Flag = "3"; //����ǳ�ʼ��������Ϣ(��Ͷ���˱����ѯ)

  VData tVData = new VData();
  tVData.addElement(InsuredNo);
  tVData.addElement(Ope_Flag);
  ShowCaseInfoUI mShowCaseInfoUI = new ShowCaseInfoUI();
  if (!mShowCaseInfoUI.submitData(tVData,"INIT"))
  {
    Content = " û�з�����������Ϣ��ԭ����: " + mShowCaseInfoUI.mErrors.getError(0).errorMessage;
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
      loggerDebug("AllShowCaseInfo","��ѯ�ļ�¼�� "+n);
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
