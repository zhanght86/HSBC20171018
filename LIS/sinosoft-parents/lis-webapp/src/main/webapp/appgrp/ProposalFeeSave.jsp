<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ProposalFeeSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
  
	String tPolNo = "";
	tPolNo = request.getParameter("PolNo");
  String polType = request.getParameter("polType");
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// �ݽ�����Ϣ
	LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();

	String tTempFeeNo[] = request.getParameterValues("FeeGrid1");

	boolean flag = false;
	int feeCount = tTempFeeNo.length;
	for (int i = 0; i < feeCount; i++)
	{
		if (tTempFeeNo[i] != null && tTempFeeNo[i].trim().length() > 0)
		{
	  		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	
		    tLJTempFeeSchema.setTempFeeNo( tTempFeeNo[i]);
		    tLJTempFeeSchema.setOperState("0");    
		    tLJTempFeeSchema.setOtherNo( tPolNo );  
		    tLJTempFeeSet.add( tLJTempFeeSchema );
		    flag = true;
		}
	}
	
	if (flag == false)
	{
  		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	    tLJTempFeeSchema.setTempFeeNo( "NULL" );
	    tLJTempFeeSchema.setOperState("0");    
	    tLJTempFeeSchema.setOtherNo( tPolNo );
	    tLJTempFeeSet.add( tLJTempFeeSchema );
	}	
	
  	// ׼���������� VData
	VData tVData = new VData();
	tVData.add( tLJTempFeeSet );

  	// ���ݴ���
  	   String busiName="tbgrpProposalFeeUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//ProposalFeeUI tProposalFeeUI   = new ProposalFeeUI();
	if (tBusinessDelegate.submitData(tVData,"INSERT||" + polType,busiName) == false)
	{
int n = tBusinessDelegate.getCErrors().getErrorCount();
for (int i = 0; i < n; i++)
loggerDebug("ProposalFeeSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
      Content = " ��ѯʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tBusinessDelegate.getResult();
		
		// ��ʾ
		%>
		<script language='javascript'>
			parent.fraInterface.initFeeGrid();
		</script>
		<%
loggerDebug("ProposalFeeSave","---NOW YOUR CAN DISPLAY IT---");
		LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet(); 
		mLJTempFeeSet.set((LJTempFeeSet)tVData.getObjectByObjectName("LJTempFeeSet",0));
		int n = mLJTempFeeSet.size();
loggerDebug("ProposalFeeSave","n:"+n);
		for (int i = 1; i <= n; i++)
		{
		  	LJTempFeeSchema mLJTempFeeSchema = mLJTempFeeSet.get(i);
		   	%>
			<script language='javascript'>
		   		parent.fraInterface.fm.FeeGrid1[<%=i-1%>].value="<%=mLJTempFeeSchema.getTempFeeNo()%>";
		   		parent.fraInterface.fm.FeeGrid2[<%=i-1%>].value="<%=mLJTempFeeSchema.getPayMoney()%>";
		   		parent.fraInterface.fm.FeeGrid3[<%=i-1%>].value="<%=mLJTempFeeSchema.getPayDate()%>";
		   		parent.fraInterface.fm.FeeGrid4[<%=i-1%>].value="<%=mLJTempFeeSchema.getEnterAccDate()%>";
			</script>
			<%
		} // end of for
	}	
  
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
	
	loggerDebug("ProposalFeeSave","ProposalFeeSave End:" + Content + "\n" + FlagStr);
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
