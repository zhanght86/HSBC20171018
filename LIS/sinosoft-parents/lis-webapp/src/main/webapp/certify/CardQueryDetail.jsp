<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�CardQueryDetail.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
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

    tLCPolSchema.setPolNo(request.getParameter("PolNo"));

  // ׼���������� VData
  VData tVData = new VData();

	tVData.addElement(tLCPolSchema);

  // ���ݴ���
  CardQueryUI tCardQueryUI   = new CardQueryUI();
	if (!tCardQueryUI.submitData(tVData,"QUERY||DETAIL"))
	{
      Content = " ��ѯʧ�ܣ�ԭ����: " + tCardQueryUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tCardQueryUI.getResult();

		// ��ʾ
		// ������Ϣ
		LCPolSchema mLCPolSchema = new LCPolSchema(); 
		mLCPolSchema.setSchema((LCPolSchema)tVData.getObjectByObjectName("LCPolSchema",0));
		%>
    	<script language="javascript">
    	 	top.opener.document.all("PrtNo").value = "<%=mLCPolSchema.getPrtNo()%>";
    	 	top.opener.document.all("ProposalNo").value = "<%=mLCPolSchema.getProposalNo()%>";
    	 	top.opener.document.all("ManageCom").value = "<%=mLCPolSchema.getManageCom()%>";
    	 	top.opener.document.all("SaleChnl").value = "<%=mLCPolSchema.getSaleChnl()%>";
    	 	top.opener.document.all("AgentCom").value = "<%=mLCPolSchema.getAgentCom()%>";
    	 	top.opener.document.all("AgentCode").value = "<%=mLCPolSchema.getAgentCode()%>";
    	 	top.opener.document.all("AgentGroup").value = "<%=mLCPolSchema.getAgentGroup()%>";
    	 	top.opener.document.all("Handler").value = "<%=mLCPolSchema.getHandler()%>";
    	 	top.opener.document.all("AgentCode1").value = "<%=mLCPolSchema.getAgentCode1()%>";
    	 	top.opener.document.all("EndDate").value = "<%=mLCPolSchema.getEndDate()%>";
    	 	top.opener.document.all("PayEndDate").value = "<%=mLCPolSchema.getPayEndDate()%>";
    	 	top.opener.document.all("RiskCode").value = "<%=mLCPolSchema.getRiskCode()%>";
    	 	top.opener.document.all("RiskVersion").value = "<%=mLCPolSchema.getRiskVersion()%>";
    	 	top.opener.document.all("ValiDate").value = "<%=mLCPolSchema.getValiDate()%>";
    	 	top.opener.document.all("Mult").value = "<%=mLCPolSchema.getMult()%>";
    	 	top.opener.document.all("Prem").value = "<%=mLCPolSchema.getPrem()%>";
    	 	top.opener.document.all("Amnt").value = "<%=mLCPolSchema.getAmnt()%>";
    	</script>
		<%
		// Ͷ������Ϣ
		LCAppntIndSchema mLCAppntIndSchema = new LCAppntIndSchema(); 
		mLCAppntIndSchema.setSchema((LCAppntIndSchema)tVData.getObjectByObjectName("LCAppntIndSchema",0));
		%>
    	<script language="javascript">
    	 	top.opener.document.all("AppntCustomerNo").value = "<%=mLCAppntIndSchema.getCustomerNo()%>";
    	 	top.opener.document.all("AppntName").value = "<%=mLCAppntIndSchema.getName()%>";
    	 	top.opener.document.all("AppntSex").value = "<%=mLCAppntIndSchema.getSex()%>";
    	 	top.opener.document.all("AppntBirthday").value = "<%=mLCAppntIndSchema.getBirthday()%>";
    	 	top.opener.document.all("AppntIDType").value = "<%=mLCAppntIndSchema.getIDType()%>";
    	 	top.opener.document.all("AppntIDNo").value = "<%=mLCAppntIndSchema.getIDNo()%>";
    	 	top.opener.document.all("RelationToInsured").value = "<%=mLCAppntIndSchema.getRelationToInsured()%>";
    	 	top.opener.document.all("AppntPhone").value = "<%=mLCAppntIndSchema.getPhone()%>";
    	 	top.opener.document.all("AppntMobile").value = "<%=mLCAppntIndSchema.getMobile()%>";
    	 	top.opener.document.all("AppntPostalAddress").value = "<%=mLCAppntIndSchema.getPostalAddress()%>";
    	 	top.opener.document.all("AppntZipCode").value = "<%=mLCAppntIndSchema.getZipCode()%>";
    	 	top.opener.document.all("AppntEMail").value = "<%=mLCAppntIndSchema.getEMail()%>";
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
	    	 	top.opener.document.all("CustomerNo").value = "<%=mLCInsuredSchema.getCustomerNo()%>";
	    	 	top.opener.document.all("Name").value = "<%=mLCInsuredSchema.getName()%>";
	    	 	top.opener.document.all("Sex").value = "<%=mLCInsuredSchema.getSex()%>";
	    	 	top.opener.document.all("Birthday").value = "<%=mLCInsuredSchema.getBirthday()%>";
	    	 	top.opener.document.all("IDType").value = "<%=mLCInsuredSchema.getIDType()%>";
	    	 	top.opener.document.all("IDNo").value = "<%=mLCInsuredSchema.getIDNo()%>";
	    	 	top.opener.document.all("Health").value = "<%=mLCInsuredSchema.getHealth()%>";
	    	 	top.opener.document.all("OccupationType").value = "<%=mLCInsuredSchema.getOccupationType()%>";
	    	 	top.opener.document.all("Marriage").value = "<%=mLCInsuredSchema.getMarriage()%>";
	    	</script>
	    	<%
	    		break;
	    	}
	    	break;
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
		   		top.opener.fm.SubInsuredGrid1[<%=j%>].value="<%=mLCSubInsuredSchema.getCustomerNo()%>";
		   		top.opener.fm.SubInsuredGrid2[<%=j%>].value="<%=mLCSubInsuredSchema.getName()%>";
		   		top.opener.fm.SubInsuredGrid3[<%=j%>].value="<%=mLCSubInsuredSchema.getSex()%>";
		   		top.opener.fm.SubInsuredGrid4[<%=j%>].value="<%=mLCSubInsuredSchema.getBirthday()%>";
		   		top.opener.fm.SubInsuredGrid5[<%=j%>].value="<%=mLCSubInsuredSchema.getRelationToInsured()%>";
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
		   		top.opener.fm.BnfGrid1[<%=i-1%>].value="<%=mLCBnfSchema.getName()%>";
		   		top.opener.fm.BnfGrid2[<%=i-1%>].value="<%=mLCBnfSchema.getRelationToInsured()%>";
		   		top.opener.fm.BnfGrid3[<%=i-1%>].value="<%=mLCBnfSchema.getBnfType()%>";
		   		top.opener.fm.BnfGrid4[<%=i-1%>].value="<%=mLCBnfSchema.getBnfGrade()%>";
		   		top.opener.fm.BnfGrid5[<%=i-1%>].value="<%=mLCBnfSchema.getBnfLot()%>";
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
		   		top.opener.fm.ImpartGrid1[<%=i-1%>].value="<%=mLCCustomerImpartSchema.getCustomerNo()%>";
		   		top.opener.fm.ImpartGrid3[<%=i-1%>].value="<%=mLCCustomerImpartSchema.getImpartCode()%>";
		   		top.opener.fm.ImpartGrid4[<%=i-1%>].value="<%=mLCCustomerImpartSchema.getImpartVer()%>";
		   		top.opener.fm.ImpartGrid5[<%=i-1%>].value="<%=mLCCustomerImpartSchema.getImpartContent()%>";
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
		   		top.opener.fm.SpecGrid1[<%=i-1%>].value="<%=mLCSpecSchema.getSpecCode()%>";
				top.opener.fm.SpecGrid2[<%=i-1%>].value="<%=mLCSpecSchema.getSpecType()%>";
		   		top.opener.fm.SpecGrid3[<%=i-1%>].value="<%=mLCSpecSchema.getSpecContent()%>";
	    	</script>
	    	<%
	    }
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail")
  {
    tError = tCardQueryUI.mErrors;
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
loggerDebug("CardQueryDetail","------end------");
loggerDebug("CardQueryDetail",FlagStr);
loggerDebug("CardQueryDetail",Content);
  out.println("<script language=javascript>");
  //out.println("showInfo.close();");
  out.println("top.close();");
  out.println("</script>");
%>
