<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSubGQuery.jsp
//�����ܣ��˱��켣��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Flag = "";
  
  String tPolNo = "";
  //tPolNo = (String)session.getValue("PolNo");
  loggerDebug("UWSubGQuery","----");
  tPolNo = request.getParameter("ProposalNoHide");
  loggerDebug("UWSubGQuery","--PolNo:---"+tPolNo);

  //�˱���־
  //Flag = request.getParameter("Flag");
  //Flag = parent.fm.all("Flag").value;
  //loggerDebug("UWSubGQuery","flag:"+Flag);
  
  //�˱��켣��
  //LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
  //tLCUWSubSchema.setProposal(request.getParameter("ProposalNo"));  
  //VData tVData = new VData();  
  //tVData.addElement(tLCUWSubSchema);
  
  
  // ������Ϣ����
  LCPolSchema tLCPolSchema   = new LCPolSchema();

    //tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
    tLCPolSchema.setProposalNo(tPolNo);

  // ׼���������� VData
  VData tVData = new VData();

	tVData.addElement(tLCPolSchema);

  // ���ݴ���
  Flag = "QUERY||LCUWSUB";
  UWQueryUI tUWQueryUI   = new UWQueryUI();
	if (!tUWQueryUI.submitData(tVData,Flag))
	{
      		Content = " ��ѯʧ�ܣ�ԭ����: " + tUWQueryUI.mErrors.getError(0).errorMessage;
      		FlagStr = "Fail";
	}
	else
	{
		
		tVData.clear();
		tVData = tUWQueryUI.getResult();
		
		// ��ʾ
		LCUWSubSet mLCUWSubSet = new LCUWSubSet();
		LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();		
		
		
			mLCUWSubSet.set((LCUWSubSet)tVData.getObjectByObjectName("LCUWSubSet",0));
			int n = mLCUWSubSet.size();
			loggerDebug("UWSubGQuery","n:"+n);
			if (n > 1)
			{
				for (int i = 1; i <= n; i++)
				{
					LCUWSubSchema mLCUWSubSchema = mLCUWSubSet.get(i);
					if (mLCUWSubSchema.getUWNo() != n)
					{
		  			
%>
		   			<script language="javascript">
		   				<!--parent.fraInterface.fm.PolGrid1[<%=i-1%>].value="<%=mLCUWSubSchema.getProposalNo()%>";-->
		   				parent.fraInterface.fm.UWSubGrid1[<%=i-1%>].value="<%=mLCUWSubSchema.getProposalNo()%>"
				   		parent.fraInterface.fm.UWSubGrid2[<%=i-1%>].value="<%=mLCUWSubSchema.getUWNo()%>";
				   		parent.fraInterface.fm.UWSubGrid3[<%=i-1%>].value="<%=mLCUWSubSchema.getState()%>";
				   		parent.fraInterface.fm.UWSubGrid4[<%=i-1%>].value="<%=mLCUWSubSchema.getUWIdea()%>";
				   		parent.fraInterface.fm.UWSubGrid5[<%=i-1%>].value="<%=mLCUWSubSchema.getOperator()%>";
				   		parent.fraInterface.fm.UWSubGrid6[<%=i-1%>].value="<%=mLCUWSubSchema.getModifyDate()%>";
					</script>
<%
					}
				} // end of for
			}
		
		/**
		else
		{
			mLCUWErrorSet.set((LCUWErrorSet)tVData.getObjectByObjectName("LCUWErrorSet",0));
			int n = mLCPolSet.size();
			if (n>0)
			{
				for (int i = 1;i <= n;i++)
				{
				
					LCUWErrorSchema mLCUWErrorSchema = mLCUWErrorSet.get(i);
					
					//���
				}
			}
		}
		**/
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail")
  {
    tError = tUWQueryUI.mErrors;
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
loggerDebug("UWSubGQuery","------end------");
loggerDebug("UWSubGQuery",FlagStr);
loggerDebug("UWSubGQuery",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

