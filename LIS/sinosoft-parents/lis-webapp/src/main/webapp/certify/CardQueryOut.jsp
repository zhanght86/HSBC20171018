<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�CertifyQueryOut.jsp
//�����ܣ�
//�������ڣ�2002-08-18
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  // ������Ϣ����
  LCPolSchema tLCPolSchema   = new LCPolSchema();

    tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
    tLCPolSchema.setRiskCode(request.getParameter("RiskCode"));
    tLCPolSchema.setRiskVersion(request.getParameter("RiskVersion"));
    tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
    tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
    tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
    tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
	// û��ǩ���Ĳ���
    tLCPolSchema.setAppFlag( "0" ); 
    
  // ׼���������� VData
  VData tVData = new VData();

	tVData.addElement(tLCPolSchema);

  // ���ݴ���
  CertifyQueryUI tCertifyQueryUI   = new CertifyQueryUI();
	if (!tCertifyQueryUI.submitData(tVData,"QUERY||MAIN"))
	{
      Content = " ��ѯʧ�ܣ�ԭ����: " + tCertifyQueryUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tCertifyQueryUI.getResult();
		
		// ��ʾ
		LCPolSet mLCPolSet = new LCPolSet(); 
		mLCPolSet.set((LCPolSet)tVData.getObjectByObjectName("LCPolSet",0));
		int n = mLCPolSet.size();
		for (int i = 1; i <= n; i++)
		{
		  	LCPolSchema mLCPolSchema = mLCPolSet.get(i);
		   	%>
		   	<script language="javascript">
		   	  parent.fraInterface.PolGrid.addOne("PolGrid")
		   		parent.fraInterface.fm.PolGrid1[<%=i-1%>].value="<%=mLCPolSchema.getProposalNo()%>";
		   		parent.fraInterface.fm.PolGrid2[<%=i-1%>].value="<%=mLCPolSchema.getPrtNo()%>";
		   		parent.fraInterface.fm.PolGrid3[<%=i-1%>].value="<%=mLCPolSchema.getRiskCode()%>";
		   		parent.fraInterface.fm.PolGrid4[<%=i-1%>].value="<%=mLCPolSchema.getAppntName()%>";
		   		parent.fraInterface.fm.PolGrid5[<%=i-1%>].value="<%=mLCPolSchema.getInsuredName()%>";
			</script>
			<%
		} // end of for
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail")
  {
    tError = tCertifyQueryUI.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " ��ѯ�ɹ�! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " ��ѯʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
loggerDebug("CardQueryOut","------end------");
loggerDebug("CardQueryOut",FlagStr);
loggerDebug("CardQueryOut",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

