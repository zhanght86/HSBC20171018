<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ProposalDetail.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  String tPolNo = "";
  tPolNo = (String)session.getValue("PolNo");
loggerDebug("ProposalDetail","--PolNo:---"+tPolNo);

  // ������Ϣ����
  LCPolSchema tLCPolSchema   = new LCPolSchema();

    tLCPolSchema.setPolNo( tPolNo );

  // ׼���������� VData
  VData tVData = new VData();

	tVData.addElement(tLCPolSchema);

  // ���ݴ���
     String busiName="tbgrpProposalQueryUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
	if (!tBusinessDelegate.submitData(tVData,"QUERY||DETAIL",busiName))
	{
      Content = " ��ѯʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tBusinessDelegate.getResult();

		// ��ʾ
		// ������Ϣ
		LCPolSchema mLCPolSchema = new LCPolSchema(); 
		mLCPolSchema.setSchema((LCPolSchema)tVData.getObjectByObjectName("LCPolSchema",0));
		%>
    	<script language="javascript">
    	 	parent.fraInterface.fm.all("PrtNo").value = "<%=mLCPolSchema.getPrtNo()%>";
    	 	parent.fraInterface.fm.all("ProposalNo").value = "<%=mLCPolSchema.getProposalNo()%>";
    	 	parent.fraInterface.fm.all("ManageCom").value = "<%=mLCPolSchema.getManageCom()%>";
    	 	parent.fraInterface.fm.all("SaleChnl").value = "<%=mLCPolSchema.getSaleChnl()%>";
    	 	parent.fraInterface.fm.all("AgentCom").value = "<%=mLCPolSchema.getAgentCom()%>";
    	 	parent.fraInterface.fm.all("AgentCode").value = "<%=mLCPolSchema.getAgentCode()%>";
    	 	parent.fraInterface.fm.all("AgentGroup").value = "<%=mLCPolSchema.getAgentGroup()%>";
    	 	parent.fraInterface.fm.all("Handler").value = "<%=mLCPolSchema.getHandler()%>";
    	 	parent.fraInterface.fm.all("AgentCode1").value = "<%=mLCPolSchema.getAgentCode1()%>";
    	 	parent.fraInterface.fm.all("EndDate").value = "<%=mLCPolSchema.getEndDate()%>";
    	 	parent.fraInterface.fm.all("PayEndDate").value = "<%=mLCPolSchema.getPayEndDate()%>";
    	 	parent.fraInterface.fm.all("RiskCode").value = "<%=mLCPolSchema.getRiskCode()%>";
    	 	parent.fraInterface.fm.all("RiskVersion").value = "<%=mLCPolSchema.getRiskVersion()%>";
    	 	parent.fraInterface.fm.all("CValiDate").value = "<%=mLCPolSchema.getCValiDate()%>";
    	 	parent.fraInterface.fm.all("Mult").value = "<%=mLCPolSchema.getMult()%>";
    	 	parent.fraInterface.fm.all("StandPrem").value = "<%=mLCPolSchema.getStandPrem()%>";
    	 	parent.fraInterface.fm.all("Amnt").value = "<%=mLCPolSchema.getAmnt()%>";
    	</script>
		<%
		// Ͷ������Ϣ
		LCAppntIndSchema mLCAppntIndSchema = new LCAppntIndSchema(); 
		mLCAppntIndSchema.setSchema((LCAppntIndSchema)tVData.getObjectByObjectName("LCAppntIndSchema",0));
		%>
    	<script language="javascript">
    	 	parent.fraInterface.fm.all("AppntCustomerNo").value = "<%=mLCAppntIndSchema.getCustomerNo()%>";
    	 	parent.fraInterface.fm.all("AppntName").value = "<%=mLCAppntIndSchema.getName()%>";
    	 	parent.fraInterface.fm.all("AppntSex").value = "<%=mLCAppntIndSchema.getSex()%>";
    	 	parent.fraInterface.fm.all("AppntBirthday").value = "<%=mLCAppntIndSchema.getBirthday()%>";
    	 	parent.fraInterface.fm.all("AppntIDType").value = "<%=mLCAppntIndSchema.getIDType()%>";
    	 	parent.fraInterface.fm.all("AppntIDNo").value = "<%=mLCAppntIndSchema.getIDNo()%>";
    	 	parent.fraInterface.fm.all("RelationToInsured").value = "<%=mLCAppntIndSchema.getRelationToInsured()%>";
    	 	parent.fraInterface.fm.all("AppntPhone").value = "<%=mLCAppntIndSchema.getPhone()%>";
    	 	parent.fraInterface.fm.all("AppntMobile").value = "<%=mLCAppntIndSchema.getMobile()%>";
    	 	parent.fraInterface.fm.all("AppntPostalAddress").value = "<%=mLCAppntIndSchema.getPostalAddress()%>";
    	 	parent.fraInterface.fm.all("AppntZipCode").value = "<%=mLCAppntIndSchema.getZipCode()%>";
    	 	parent.fraInterface.fm.all("AppntEMail").value = "<%=mLCAppntIndSchema.getEMail()%>";
    	</script>
		<%
		// ��������Ϣ
		LCInsuredSet mLCInsuredSet = new LCInsuredSet(); 
		mLCInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
		int insuredCount = mLCInsuredSet.size();
		for (int i = 1; i <= insuredCount; i++)
		{
			LCInsuredSchema mLCInsuredSchema = mLCInsuredSet.get(i);
			if (mLCInsuredSchema.getInsuredGrade().equals("M"))
			{
			%>	
	    	<script language="javascript">
	    	 	parent.fraInterface.fm.all("CustomerNo").value = "<%=mLCInsuredSchema.getCustomerNo()%>";
	    	 	parent.fraInterface.fm.all("Name").value = "<%=mLCInsuredSchema.getName()%>";
	    	 	parent.fraInterface.fm.all("Sex").value = "<%=mLCInsuredSchema.getSex()%>";
	    	 	parent.fraInterface.fm.all("Birthday").value = "<%=mLCInsuredSchema.getBirthday()%>";
	    	 	parent.fraInterface.fm.all("IDType").value = "<%=mLCInsuredSchema.getIDType()%>";
	    	 	parent.fraInterface.fm.all("IDNo").value = "<%=mLCInsuredSchema.getIDNo()%>";
	    	 	parent.fraInterface.fm.all("Health").value = "<%=mLCInsuredSchema.getHealth()%>";
	    	 	parent.fraInterface.fm.all("OccupationType").value = "<%=mLCInsuredSchema.getOccupationType()%>";
	    	 	parent.fraInterface.fm.all("Marriage").value = "<%=mLCInsuredSchema.getMarriage()%>";
	    	</script>
	    	<%
	    		break;
	    	}
	    }
		
		// ������������Ϣ
		LCInsuredSet mLCSubInsuredSet = new LCInsuredSet(); 
		mLCSubInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
		for (int i = 1; i <= insuredCount; i++)
		{
			LCInsuredSchema mLCSubInsuredSchema = mLCInsuredSet.get(i);
			int j = 0;
			if (mLCSubInsuredSchema.getInsuredGrade().equals("S"))
			{
			%>	
	    	<script language="javascript">
		   		parent.fraInterface.fm.SubInsuredGrid1[<%=j%>].value="<%=mLCSubInsuredSchema.getCustomerNo()%>";
		   		parent.fraInterface.fm.SubInsuredGrid2[<%=j%>].value="<%=mLCSubInsuredSchema.getName()%>";
		   		parent.fraInterface.fm.SubInsuredGrid3[<%=j%>].value="<%=mLCSubInsuredSchema.getSex()%>";
		   		parent.fraInterface.fm.SubInsuredGrid4[<%=j%>].value="<%=mLCSubInsuredSchema.getBirthday()%>";
		   		parent.fraInterface.fm.SubInsuredGrid5[<%=j%>].value="<%=mLCSubInsuredSchema.getRelationToInsured()%>";
	    	</script>
	    	<%
				j++;
	    	}
	    }
		
		// ��������Ϣ
		LCBnfSet mLCBnfSet = new LCBnfSet(); 
		mLCBnfSet.set((LCBnfSet)tVData.getObjectByObjectName("LCBnfSet",0));
		int bnfCount = mLCBnfSet.size();
		for (int i = 1; i <= bnfCount; i++)
		{
			LCBnfSchema mLCBnfSchema = mLCBnfSet.get(i);
			%>	
	    	<script language="javascript">
		   		parent.fraInterface.fm.BnfGrid1[<%=i-1%>].value="<%=mLCBnfSchema.getName()%>";
		   		parent.fraInterface.fm.BnfGrid2[<%=i-1%>].value="<%=mLCBnfSchema.getRelationToInsured()%>";
		   		parent.fraInterface.fm.BnfGrid3[<%=i-1%>].value="<%=mLCBnfSchema.getBnfType()%>";
		   		parent.fraInterface.fm.BnfGrid4[<%=i-1%>].value="<%=mLCBnfSchema.getBnfGrade()%>";
		   		parent.fraInterface.fm.BnfGrid5[<%=i-1%>].value="<%=mLCBnfSchema.getBnfLot()%>";
	    	</script>
	    	<%
	    }

		// ��֪��Ϣ
		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet(); 
		mLCCustomerImpartSet.set((LCCustomerImpartSet)tVData.getObjectByObjectName("LCCustomerImpartSet",0));
		int impartCount = mLCCustomerImpartSet.size();
		for (int i = 1; i <= impartCount; i++)
		{
			LCCustomerImpartSchema mLCCustomerImpartSchema = mLCCustomerImpartSet.get(i);
			%>	
	    	<script language="javascript">
		   		parent.fraInterface.fm.ImpartGrid1[<%=i-1%>].value="<%=mLCCustomerImpartSchema.getCustomerNo()%>";
		   		parent.fraInterface.fm.ImpartGrid3[<%=i-1%>].value="<%=mLCCustomerImpartSchema.getImpartCode()%>";
		   		parent.fraInterface.fm.ImpartGrid4[<%=i-1%>].value="<%=mLCCustomerImpartSchema.getImpartVer()%>";
		   		parent.fraInterface.fm.ImpartGrid5[<%=i-1%>].value="<%=mLCCustomerImpartSchema.getImpartContent()%>";
	    	</script>
	    	<%
	    }

		// ��֪��Ϣ
		LCSpecSet mLCSpecSet = new LCSpecSet(); 
		mLCSpecSet.set((LCSpecSet)tVData.getObjectByObjectName("LCSpecSet",0));
		int specCount = mLCSpecSet.size();
		for (int i = 1; i <= specCount; i++)
		{
			LCSpecSchema mLCSpecSchema = mLCSpecSet.get(i);
			%>	
	    	<script language="javascript">
		   		parent.fraInterface.fm.SpecGrid1[<%=i-1%>].value="<%=mLCSpecSchema.getSpecCode()%>";
				parent.fraInterface.fm.SpecGrid2[<%=i-1%>].value="<%=mLCSpecSchema.getSpecType()%>";
		   		parent.fraInterface.fm.SpecGrid3[<%=i-1%>].value="<%=mLCSpecSchema.getSpecContent()%>";
	    	</script>
	    	<%
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
loggerDebug("ProposalDetail","------end------");
loggerDebug("ProposalDetail",FlagStr);
loggerDebug("ProposalDetail",Content);
  out.println("<script language=javascript>");
  out.println("</script>");
%>
