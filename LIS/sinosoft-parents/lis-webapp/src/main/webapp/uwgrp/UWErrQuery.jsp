<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWErrQuery.jsp
//�����ܣ��˹��˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String Flag = "";
  
  String tPolNo = "";
  //tPolNo = (String)session.getValue("PolNo");
  loggerDebug("UWErrQuery","----");
  tPolNo = request.getParameter("ProposalNoHide");
  loggerDebug("UWErrQuery","--PolNo:---"+tPolNo);

  //�˱���־
  //Flag = request.getParameter("Flag");
  //Flag = parent.fm.all("Flag").value;
  //loggerDebug("UWErrQuery","flag:"+Flag);
  
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
  Flag = "QUERY||LCUWERROR";
  //UWQueryUI tUWQueryUI   = new UWQueryUI();
  String busiName="cbcheckgrpUWQueryUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if (!tBusinessDelegate.submitData(tVData,Flag,busiName))
	{
      		Content = " ��ѯʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      		FlagStr = "Fail";
	}
	else
	{
		
		tVData.clear();
		tVData = tBusinessDelegate.getResult();
		
		// ��ʾ
		LCUWSubSet mLCUWSubSet = new LCUWSubSet();
		LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();		
		
		
			mLCUWErrorSet.set((LCUWErrorSet)tVData.getObjectByObjectName("LCUWErrorSet",0));
			int n = mLCUWErrorSet.size();
			loggerDebug("UWErrQuery","n:"+n);
			if (n > 1)
			{
				for (int i = 1; i <= n; i++)
				{
					LCUWErrorSchema mLCUWErrorSchema = mLCUWErrorSet.get(i);
					if (mLCUWErrorSchema.getUWNo() != n)
					{
		  			
%>
		   			<script language="javascript">
		   				parent.fraInterface.fm.UWErrGrid1[<%=i-1%>].value="<%=mLCUWErrorSchema.getProposalNo()%>";
				   		parent.fraInterface.fm.UWErrGrid2[<%=i-1%>].value="<%=mLCUWErrorSchema.getUWNo()%>";
				   		parent.fraInterface.fm.UWErrGrid3[<%=i-1%>].value="<%=mLCUWErrorSchema.getUWError()%>";
				   		parent.fraInterface.fm.UWErrGrid4[<%=i-1%>].value="<%=mLCUWErrorSchema.getModifyDate()%>";
				 
					</script>
<%
					}
				} // end of for
			}
		
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail")
  {
    tError = tBusinessDelegate.getCErrors();
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
loggerDebug("UWErrQuery","------end------");
loggerDebug("UWErrQuery",FlagStr);
loggerDebug("UWErrQuery",Content);
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

