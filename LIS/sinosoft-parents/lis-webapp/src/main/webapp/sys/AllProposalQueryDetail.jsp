<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：AllProposalQueryDetail.jsp
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
  <%@page import="com.sinosoft.lis.bl.*"%>
  <%@page import="java.lang.reflect.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  String customerNo = "";  //记录被保人客户号，用来与投保人客户号进行判断

  loggerDebug("AllProposalQueryDetail","\n\n------ProposalQueryDetail start------");
  // 保单信息部分
  LCPolSchema tLCPolSchema   = new LCPolSchema();

  // 准备传输数据 VData
  tLCPolSchema.setPolNo(request.getParameter("PolNo"));
  VData tVData = new VData();
	tVData.addElement( tLCPolSchema );

  // 数据传输
  
//  ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
//	if (!tProposalQueryUI.submitData( tVData, "QUERY||DETAIL" )) {    
//      Content = "查询失败1，原因是: " + tProposalQueryUI.mErrors.getError(0).errorMessage;
//      FlagStr = "Fail";
//      loggerDebug("AllProposalQueryDetail","QueryDetail:"+Content);
//	}	
String busiName="tbProposalQueryUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData, "QUERY||DETAIL",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "查询失败1，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "保存失败";
		FlagStr = "Fail";				
	}
}

	else {
		tVData.clear();
		//tVData = tProposalQueryUI.getResult();
		tVData = tBusinessDelegate.getResult();
		// 显示
		// 保单信息
		LCPolSchema mLCPolSchema = new LCPolSchema(); 
		mLCPolSchema.setSchema((LCPolSchema)tVData.getObjectByObjectName("LCPolSchema",0));
    String tGrpPolNo = mLCPolSchema.getGrpPolNo();
		String tLoadFlag = "3";
		if( !tGrpPolNo.equals( "00000000000000000000" ))	// 集体下的个人投保单
			tLoadFlag = "4";
		%>
    <script language="javascript">
    try {
      if (parent.fraInterface.loadFlag == 5) 
      {
        parent.fraInterface.getRiskInput("<%=mLCPolSchema.getRiskCode()%>", "5");   
				parent.fraInterface.document.all("Remark").value="<%=mLCPolSchema.getRemark()%>";      	//parent.fraInterface.showCodeName();
      } 
      else 
      {
        parent.fraInterface.getRiskInput("<%=mLCPolSchema.getRiskCode()%>", "<%=tLoadFlag%>");   
      	parent.fraInterface.document.all("Remark").value="<%=mLCPolSchema.getRemark()%>";
      }
    } catch(ex) {}
    <%
    //集体下的个人投保单
    int elementsNum;
    Class c = mLCPolSchema.getClass();
    Field f[] = c.getDeclaredFields();
    
    for(elementsNum=0; elementsNum<f.length; elementsNum++) 
    {
      if (!mLCPolSchema.getV(f[elementsNum].getName()).equals("null")) 
      {
        out.println("try{");
        out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() + "').value='" + mLCPolSchema.getV(f[elementsNum].getName()) + "';");
        out.println("} catch(ex) {};");
      }
    }
		loggerDebug("AllProposalQueryDetail","End Display 保单信息...");
    %>
    </script>

		<%
		// 责任信息（未整理）
		LCDutySet mLCDutySet = new LCDutySet(); 
		mLCDutySet.set((LCDutySet)tVData.getObjectByObjectName("LCDutySet",0));
		LMDutySet mLMDutySet = new LMDutySet(); 
		mLMDutySet.set((LMDutySet)tVData.getObjectByObjectName("LMDutySet",0));
		int dutyCount = mLCDutySet.size();
		loggerDebug("AllProposalQueryDetail","dutyCount:"+dutyCount);
		loggerDebug("AllProposalQueryDetail","mLMDutySet:"+mLMDutySet.encode());
		%>
		<script>
		parent.fraInterface.initDutyGrid();
		parent.fraInterface.DutyGrid.clearData();
		</script>
		<%
		if (dutyCount == 0){ %>
	    	<script>
	    	    parent.fraInterface.divDutyGrid.display = 'none';
	    	</script>    	
	<% }
		for (int i = 1; i <= dutyCount; i++) {
			LCDutySchema mLCDutySchema = mLCDutySet.get(i);
			LMDutySchema mLMDutySchema = mLMDutySet.get(i);
			%>	
	    	<script language="javascript">
	    	parent.fraInterface.DutyGrid.addOne();
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 1, "<%=mLCDutySchema.getDutyCode()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 2, "<%=mLMDutySchema.getDutyName()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 3, "<%=mLCDutySchema.getPrem()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 4, "<%=mLCDutySchema.getAmnt()%>");
			parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 5, "<%=mLCDutySchema.getPayEndYear()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 6, "<%=mLCDutySchema.getPayEndYearFlag()%>");
	    	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 7, "<%=mLCDutySchema.getGetYear()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 8, "<%=mLCDutySchema.getGetYearFlag()%>");
	    	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 9, "<%=mLCDutySchema.getInsuYear()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 10, "<%=mLCDutySchema.getInsuYearFlag()%>");
	    	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 11, "<%=mLCDutySchema.getPayIntv()%>");
	    	
	    	</script>
	    <%
	  }
		loggerDebug("AllProposalQueryDetail","End Display 责任信息...");

		// 被保人信息
		LCInsuredSet mLCInsuredSet = new LCInsuredSet(); 
		mLCInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
		int insuredCount = mLCInsuredSet.size();
		for (int i = 1; i <= insuredCount; i++)	{
			LCInsuredSchema mLCInsuredSchema = mLCInsuredSet.get(i);
			if (mLCInsuredSchema.getInsuredGrade() != null && mLCInsuredSchema.getInsuredGrade().equals("M"))	{
			%>	
	    	<script language="javascript">
	    	<%
    	  // 被保人信息 
    	  //保存客户号，用于判断是否与投保人是同一个人
    	  customerNo = mLCInsuredSchema.getCustomerNo();
    	  c = mLCInsuredSchema.getClass();
    	  f = c.getDeclaredFields();
    	  
    	  for (elementsNum=0; elementsNum<f.length; elementsNum++) {  
    	    if (!mLCInsuredSchema.getV(f[elementsNum].getName()).equals("null")) {
      	    out.println("try {");
      	    out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() +"').value = '" + mLCInsuredSchema.getV(f[elementsNum].getName()) + "';");
      	    out.println("} catch(ex) { };");
      	  }
    	  }    
    	  %>
		    
		    parent.fraInterface.document.all("Age").value="<%=PubFun.calInterval(mLCInsuredSchema.getBirthday(), PubFun.getCurrentDate(), "Y")%>";
	    	//parent.fraInterface.showCodeName();
	    	</script>
	    	<%
	    	break;
	    }
	  }
		loggerDebug("AllProposalQueryDetail","End Display 被保人信息...");
		
		// 险种信息的给付方法
		LCGetSet mLCGetSet = new LCGetSet(); 
		mLCGetSet.set((LCGetSet)tVData.getObjectByObjectName("LCGetSet",0));
		int lcgetCount = mLCGetSet.size();
				
		for (int i = 1; i <= lcgetCount; i++)	{
			LCGetSchema mLCGetSchema = mLCGetSet.get(i);
			loggerDebug("AllProposalQueryDetail","mLCGetSchema:" + mLCGetSchema.encode() + "\n" + mLCGetSchema.getGetDutyKind());
			if (mLCGetSchema.getGetDutyKind()!=null && !mLCGetSchema.getGetDutyKind().equals(""))	{
			%>	
	    	<script language="javascript">
		    try { parent.fraInterface.document.all("GetDutyKind").value="<%=mLCGetSchema.getGetDutyKind()%>"; } catch(ex) {}
	    	</script>
	    	<%    	
	    	break;
	    }
	  }
		loggerDebug("AllProposalQueryDetail","End Display 给付方法..."); 
		
		// 连带被保人信息（未整理）
		LCInsuredSet mLCSubInsuredSet = new LCInsuredSet(); 
		mLCSubInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
		%>
		<script>parent.fraInterface.SubInsuredGrid.clearData("SubInsuredGrid");
						//parent.fraInterface.showCodeName();
		
		</script>
		<%
		if (insuredCount == 0) { %>
		    <script>
		        parent.fraInterface.divLCInsured0.style.display = 'none';
		    </script>
		<%        
		} else {
		  loggerDebug("AllProposalQueryDetail","insured set size:" + insuredCount);
		  loggerDebug("AllProposalQueryDetail","**********************");
		}
				int j = 0;
		for (int i = 1; i <= insuredCount; i++) {
			LCInsuredSchema mLCSubInsuredSchema = mLCInsuredSet.get(i);
			    loggerDebug("AllProposalQueryDetail","CustomerNo:" + mLCSubInsuredSchema.getCustomerNo());
				loggerDebug("AllProposalQueryDetail","Name:" + mLCSubInsuredSchema.getName());
			if (mLCSubInsuredSchema.getInsuredGrade()!=null && mLCSubInsuredSchema.getInsuredGrade().equals("S")) {
			loggerDebug("AllProposalQueryDetail","j:"+j+mLCSubInsuredSchema.getRelationToInsured());
			%>	
	    	<script language="javascript">
				parent.fraInterface.SubInsuredGrid.addOne("SubInsuredGrid");	    	
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,1,"<%=mLCSubInsuredSchema.getCustomerNo()%>");
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,2,"<%=mLCSubInsuredSchema.getName()%>");
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,3,"<%=mLCSubInsuredSchema.getSex()%>");
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,4,"<%=mLCSubInsuredSchema.getBirthday()%>");
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,5,"<%=mLCSubInsuredSchema.getRelationToInsured()%>");	  
	    	    //parent.fraInterface.showCodeName();  	    	    	    	    	    	    	
	    	</script>
	    <%
			j++;
	   }
	  }
		loggerDebug("AllProposalQueryDetail","End Display 连带被保人信息...");

	
		// 个人投保人信息
		LCAppntIndSchema mLCAppntIndSchema = new LCAppntIndSchema(); 
		mLCAppntIndSchema.setSchema((LCAppntIndSchema)tVData.getObjectByObjectName("LCAppntIndSchema",0));
		%>
    	<script language="javascript">
    	<%
    	  // 个人投保人信息
    	  //判断，如果投保人等于被保人
    	  
    	    %>
    	    
    	    parent.fraInterface.isSamePersonQuery();
    	    parent.fraInterface.document.all("AppntCustomerNo").value="<%=customerNo%>";
    	    //parent.fraInterface.showCodeName();
   
    	    <%  
    	
      	  	c = mLCAppntIndSchema.getClass();
      	 	f = c.getDeclaredFields();
      	  
      	  	for (elementsNum=0; elementsNum<f.length; elementsNum++) {  
      	    	if (!mLCAppntIndSchema.getV(f[elementsNum].getName()).equals("null")) {
        	    out.println("try {");
        	    out.println("parent.fraInterface.document.all('Appnt" + f[elementsNum].getName() +"').value = '" + mLCAppntIndSchema.getV(f[elementsNum].getName()) + "';");
        	    out.println("} catch(ex) { };");
        	 }
      	   
    	%>
		    try { parent.fraInterface.document.all("AppntAge").value="<%=PubFun.calInterval(mLCAppntIndSchema.getBirthday(), PubFun.getCurrentDate(), "Y")%>";
		    			//parent.fraInterface.showCodeName();
		     } catch(ex) { }
    	</script>

  	<script language="javascript">
		<%
      	} 
		loggerDebug("AllProposalQueryDetail","End Display 个人投保人信息...");
		// 集体投保人信息
		LCAppntGrpSchema mLCAppntGrpSchema = new LCAppntGrpSchema(); 
		mLCAppntGrpSchema.setSchema((LCAppntGrpSchema)tVData.getObjectByObjectName("LCAppntGrpSchema",0));
		
		c = mLCAppntGrpSchema.getClass();
	  f = c.getDeclaredFields();
	  
	  for (elementsNum=0; elementsNum<f.length; elementsNum++) {  
	    if (!mLCAppntGrpSchema.getV(f[elementsNum].getName()).equals("null")) {
  	    out.println("try {");
  	    out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() +"').value = '" + mLCAppntGrpSchema.getV(f[elementsNum].getName()) + "';");
  	    out.println("} catch(ex) { };");
  	  }
	  }
	  
		loggerDebug("AllProposalQueryDetail","End Display 集体投保人信息...");
		%>  	
		try { parent.fraInterface.document.all("AppGrpNo").value="<%=mLCAppntGrpSchema.getGrpNo()%>"; } catch(ex) { }
		try { parent.fraInterface.document.all("AppGrpName").value="<%=mLCAppntGrpSchema.getGrpName()%>"; } catch(ex) { }
		try { parent.fraInterface.document.all("AppGrpAddress").value="<%=mLCAppntGrpSchema.getGrpAddress()%>"; } catch(ex) { }
		try { parent.fraInterface.document.all("AppGrpZipCode").value="<%=mLCAppntGrpSchema.getGrpZipCode()%>"; } catch(ex) { }
					//parent.fraInterface.showCodeName();
					
		if("<%=mLCPolSchema.getRiskCode()%>"=='311603')	
		{
			
			try { parent.fraInterface.document.all("GrpBankCode").value="<%=mLCPolSchema.getBankCode()%>"; } catch(ex) { }
			try { parent.fraInterface.document.all("GrpBankAccNo").value="<%=mLCPolSchema.getBankAccNo()%>"; } catch(ex) { }

		}				
  	</script>

    <%
		// 受益人信息
		LCBnfSet mLCBnfSet = new LCBnfSet(); 
		mLCBnfSet.set((LCBnfSet)tVData.getObjectByObjectName("LCBnfSet",0));
		int bnfCount = mLCBnfSet.size();
		%>
		<script>
		parent.fraInterface.BnfGrid.clearData("BnfGrid");
		//parent.fraInterface.showCodeName();				
		</script>
		<%
		if (bnfCount == 0) { %>
           		
		<script>
		   parent.fraInterface.divLCBnf1.style.display = 'none';
		</script>           
	<%	}
		for (int i = 1; i <= bnfCount; i++)	{
			LCBnfSchema mLCBnfSchema = mLCBnfSet.get(i);
		%>	
	    	<script language="javascript">
	    	parent.fraInterface.BnfGrid.addOne("BnfGrid");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 1, "<%=mLCBnfSchema.getBnfType()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 2, "<%=mLCBnfSchema.getName()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 3, "<%=mLCBnfSchema.getSex()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 4, "<%=mLCBnfSchema.getIDType()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 5, "<%=mLCBnfSchema.getIDNo()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 6, "<%=mLCBnfSchema.getBirthday()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 7, "<%=mLCBnfSchema.getRelationToInsured()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 8, "<%=mLCBnfSchema.getBnfLot()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 9, "<%=mLCBnfSchema.getBnfGrade()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 10, "<%=mLCBnfSchema.getAddress()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 11, "<%=mLCBnfSchema.getZipCode()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 12, "<%=mLCBnfSchema.getPhone()%>");
	    	//parent.fraInterface.showCodeName();
	    	</script>
	  <%
	  }
		loggerDebug("AllProposalQueryDetail","End Display 受益人信息...");

		// 告知信息
		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet(); 
		mLCCustomerImpartSet.set((LCCustomerImpartSet)tVData.getObjectByObjectName("LCCustomerImpartSet",0));
		int impartCount = mLCCustomerImpartSet.size();
		%>
		<script>
		parent.fraInterface.ImpartGrid.clearData("ImpartGrid");
		</script>
		<%
		if (impartCount == 0) { %>
		    <script>
		        parent.fraInterface.divLCImpart1.style.display = 'none';
		    </script>        
		    
	<%	}    
		for (int i = 1; i <= impartCount; i++) {
			LCCustomerImpartSchema mLCCustomerImpartSchema = mLCCustomerImpartSet.get(i);
		%>	
	    	<script language="javascript">
	    	parent.fraInterface.ImpartGrid.addOne("ImpartGrid");
		   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 1, "<%=mLCCustomerImpartSchema.getCustomerNo()%>");
		   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 2, "<%=mLCCustomerImpartSchema.getImpartCode()%>");
		   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 3, "<%=mLCCustomerImpartSchema.getImpartVer()%>");
		   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 5, "<%=mLCCustomerImpartSchema.getImpartContent()%>");
	    	//parent.fraInterface.showCodeName();
	    	</script>
	  <%
	  }
		loggerDebug("AllProposalQueryDetail","End Display 告知信息...");

		// 特约信息
		LCSpecSet mLCSpecSet = new LCSpecSet(); 
		mLCSpecSet.set((LCSpecSet)tVData.getObjectByObjectName("LCSpecSet",0));
		int specCount = mLCSpecSet.size();
		%>
		<script>parent.fraInterface.SpecGrid.clearData("SpecGrid");</script>
		<%
		if (specCount == 0) { %>
		    <script>
		        parent.fraInterface.divLCSpec1.style.display = 'none';
		    </script>    
	<%	}
		for (int i = 1; i <= specCount; i++) {
			LCSpecSchema mLCSpecSchema = mLCSpecSet.get(i);
			%>	
	    	<script language="javascript"> 	
        	parent.fraInterface.SpecGrid.addOne("SpecGrid");
		   		parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 1, "<%=mLCSpecSchema.getSpecCode()%>");
					parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 2, "<%=mLCSpecSchema.getSpecType()%>");
		   		parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 3, "<%=mLCSpecSchema.getSpecContent()%>");
	    		//parent.fraInterface.showCodeName();
	    	</script>
	    <%
	  }
		loggerDebug("AllProposalQueryDetail","End Display 特约信息...");
	} // end of if
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail") 
  {
    //tError = tProposalQueryUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
    	Content = " 查询成功! ";
    	FlagStr = "Succ";
    } 
    else
    {
    	Content = " 查询失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
%>
	    	<script language="javascript"> 
	    	alert("<%=Content%>");
	    	</script>	    	
<%    	
    }
  }
  
loggerDebug("AllProposalQueryDetail",FlagStr);
loggerDebug("AllProposalQueryDetail",Content);
loggerDebug("AllProposalQueryDetail","------ProposalQueryDetail end------\n\n");

%> 

<script>
  parent.fraInterface.emptyUndefined();
  if (parent.fraInterface.loadFlag ==3|| parent.fraInterface.loadFlag ==5) {
		parent.fraInterface.showCodeName();	
  }
</script>
