<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：CardQueryDetail.jsp
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

  // 保单信息部分
  LCPolSchema tLCPolSchema   = new LCPolSchema();

    tLCPolSchema.setPolNo(request.getParameter("PolNo"));

  // 准备传输数据 VData
  VData tVData = new VData();

	tVData.addElement(tLCPolSchema);

  // 数据传输
  CardQueryUI tCardQueryUI   = new CardQueryUI();
	if (!tCardQueryUI.submitData(tVData,"QUERY||DETAIL"))
	{
      Content = " 查询失败，原因是: " + tCardQueryUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
	}
	else
	{
		tVData.clear();
		tVData = tCardQueryUI.getResult();

		// 显示
		// 保单信息
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
		// 投保人信息
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
		
		// 受益人信息
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

		// 告知信息
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

		// 告知信息
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
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail")
  {
    tError = tCardQueryUI.mErrors;
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
loggerDebug("CardQueryDetail","------end------");
loggerDebug("CardQueryDetail",FlagStr);
loggerDebug("CardQueryDetail",Content);
  out.println("<script language=javascript>");
  //out.println("showInfo.close();");
  out.println("top.close();");
  out.println("</script>");
%>
