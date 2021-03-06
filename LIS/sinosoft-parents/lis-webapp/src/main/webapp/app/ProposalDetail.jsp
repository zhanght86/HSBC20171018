<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ProposalDetail.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>

<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  String tPolNo = "";
  tPolNo = (String)session.getValue("PolNo");
loggerDebug("ProposalDetail","--PolNo:---"+tPolNo);

  // 保单信息部分
  LCPolSchema tLCPolSchema   = new LCPolSchema();

    tLCPolSchema.setPolNo( tPolNo );

  // 准备传输数据 VData
  VData tVData = new VData();

	tVData.addElement(tLCPolSchema);

  // 数据传输
  ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
	if (!tProposalQueryUI.submitData(tVData,"QUERY||DETAIL"))
	{
      Content = " 查询失败，原因是: " + tProposalQueryUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tProposalQueryUI.getResult();

		// 显示
		// 保单信息
		LCPolSchema mLCPolSchema = new LCPolSchema(); 
		mLCPolSchema.setSchema((LCPolSchema)tVData.getObjectByObjectName("LCPolSchema",0));
		%>
    	<script language="javascript">
    	 	parent.fraInterface.document.all("PrtNo").value = "<%=mLCPolSchema.getPrtNo()%>";
    	 	parent.fraInterface.document.all("ProposalNo").value = "<%=mLCPolSchema.getProposalNo()%>";
    	 	parent.fraInterface.document.all("ManageCom").value = "<%=mLCPolSchema.getManageCom()%>";
    	 	parent.fraInterface.document.all("SaleChnl").value = "<%=mLCPolSchema.getSaleChnl()%>";
    	 	parent.fraInterface.document.all("AgentCom").value = "<%=mLCPolSchema.getAgentCom()%>";
    	 	parent.fraInterface.document.all("AgentCode").value = "<%=mLCPolSchema.getAgentCode()%>";
    	 	parent.fraInterface.document.all("AgentGroup").value = "<%=mLCPolSchema.getAgentGroup()%>";
    	 	parent.fraInterface.document.all("Handler").value = "<%=mLCPolSchema.getHandler()%>";
    	 	parent.fraInterface.document.all("AgentCode1").value = "<%=mLCPolSchema.getAgentCode1()%>";
    	 	parent.fraInterface.document.all("EndDate").value = "<%=mLCPolSchema.getEndDate()%>";
    	 	parent.fraInterface.document.all("PayEndDate").value = "<%=mLCPolSchema.getPayEndDate()%>";
    	 	parent.fraInterface.document.all("RiskCode").value = "<%=mLCPolSchema.getRiskCode()%>";
    	 	parent.fraInterface.document.all("RiskVersion").value = "<%=mLCPolSchema.getRiskVersion()%>";
    	 	parent.fraInterface.document.all("CValiDate").value = "<%=mLCPolSchema.getCValiDate()%>";
    	 	parent.fraInterface.document.all("Mult").value = "<%=mLCPolSchema.getMult()%>";
    	 	parent.fraInterface.document.all("StandPrem").value = "<%=mLCPolSchema.getStandPrem()%>";
    	 	parent.fraInterface.document.all("Amnt").value = "<%=mLCPolSchema.getAmnt()%>";
    	</script>
		<%
		// 投保人信息
		LCAppntIndSchema mLCAppntIndSchema = new LCAppntIndSchema(); 
		mLCAppntIndSchema.setSchema((LCAppntIndSchema)tVData.getObjectByObjectName("LCAppntIndSchema",0));
		%>
    	<script language="javascript">
    	 	parent.fraInterface.document.all("AppntCustomerNo").value = "<%=mLCAppntIndSchema.getCustomerNo()%>";
    	 	parent.fraInterface.document.all("AppntName").value = "<%=mLCAppntIndSchema.getName()%>";
    	 	parent.fraInterface.document.all("AppntSex").value = "<%=mLCAppntIndSchema.getSex()%>";
    	 	parent.fraInterface.document.all("AppntBirthday").value = "<%=mLCAppntIndSchema.getBirthday()%>";
    	 	parent.fraInterface.document.all("AppntIDType").value = "<%=mLCAppntIndSchema.getIDType()%>";
    	 	parent.fraInterface.document.all("AppntIDNo").value = "<%=mLCAppntIndSchema.getIDNo()%>";
    	 	parent.fraInterface.document.all("RelationToInsured").value = "<%=mLCAppntIndSchema.getRelationToInsured()%>";
    	 	parent.fraInterface.document.all("AppntPhone").value = "<%=mLCAppntIndSchema.getPhone()%>";
    	 	parent.fraInterface.document.all("AppntMobile").value = "<%=mLCAppntIndSchema.getMobile()%>";
    	 	parent.fraInterface.document.all("AppntPostalAddress").value = "<%=mLCAppntIndSchema.getPostalAddress()%>";
    	 	parent.fraInterface.document.all("AppntZipCode").value = "<%=mLCAppntIndSchema.getZipCode()%>";
    	 	parent.fraInterface.document.all("AppntEMail").value = "<%=mLCAppntIndSchema.getEMail()%>";
    	</script>
		<%
		// 被保人信息
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
	    	 	parent.fraInterface.document.all("CustomerNo").value = "<%=mLCInsuredSchema.getCustomerNo()%>";
	    	 	parent.fraInterface.document.all("Name").value = "<%=mLCInsuredSchema.getName()%>";
	    	 	parent.fraInterface.document.all("Sex").value = "<%=mLCInsuredSchema.getSex()%>";
	    	 	parent.fraInterface.document.all("Birthday").value = "<%=mLCInsuredSchema.getBirthday()%>";
	    	 	parent.fraInterface.document.all("IDType").value = "<%=mLCInsuredSchema.getIDType()%>";
	    	 	parent.fraInterface.document.all("IDNo").value = "<%=mLCInsuredSchema.getIDNo()%>";
	    	 	parent.fraInterface.document.all("Health").value = "<%=mLCInsuredSchema.getHealth()%>";
	    	 	parent.fraInterface.document.all("OccupationType").value = "<%=mLCInsuredSchema.getOccupationType()%>";
	    	 	parent.fraInterface.document.all("Marriage").value = "<%=mLCInsuredSchema.getMarriage()%>";
	    	</script>
	    	<%
	    		break;
	    	}
	    }
		
		// 连带被保人信息
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
		
		// 受益人信息
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

		// 告知信息
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

		// 告知信息
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
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail")
  {
    tError = tProposalQueryUI.mErrors;
    if (!tError.needDealError())
    {                          
    	Content = " 查询成功! ";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " 查询失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
loggerDebug("ProposalDetail","------end------");
loggerDebug("ProposalDetail",FlagStr);
loggerDebug("ProposalDetail",Content);
  out.println("<script language=javascript>");
  out.println("</script>");
%>
