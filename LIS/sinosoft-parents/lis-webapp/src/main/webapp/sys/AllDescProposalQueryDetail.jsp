<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�AllDescProposalQueryDetail.jsp
//�����ܣ�
//�������ڣ�2003-10-15 11:10:36
//������  ��sxy
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bl.*"%>
  <%@page import="java.lang.reflect.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  String customerNo = "";  //��¼�����˿ͻ��ţ�������Ͷ���˿ͻ��Ž����ж�

  loggerDebug("AllDescProposalQueryDetail","\n\n------BProposalQueryDetail start------");
  // ������Ϣ����
  LBPolSchema tLBPolSchema   = new LBPolSchema();

  // ׼���������� VData
  tLBPolSchema.setPolNo(request.getParameter("PolNo"));
  VData tVData = new VData();
	tVData.addElement( tLBPolSchema );

  // ���ݴ���
  
 //   ProposalQuery_BUI tProposalQuery_BUI   = new ProposalQuery_BUI();
//	if (!tProposalQuery_BUI.submitData( tVData, "QUERY||DETAIL" )) {    
//      Content = "��ѯʧ��1��ԭ����: " + tProposalQuery_BUI.mErrors.getError(0).errorMessage;
//      FlagStr = "Fail";
//      loggerDebug("AllDescProposalQueryDetail","QueryDetail:"+Content);
//	}	
String busiName="ProposalQuery_BUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData, "QUERY||DETAIL" ,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "��ѯʧ��1��ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "����ʧ��";
		FlagStr = "Fail";				
	}
}

	else {
		tVData.clear();
		//tVData = tProposalQuery_BUI.getResult();
		tVData = tBusinessDelegate.getResult();
		// ��ʾ
		// ������Ϣ
		LBPolSchema mLBPolSchema = new LBPolSchema(); 
		mLBPolSchema.setSchema((LBPolSchema)tVData.getObjectByObjectName("LBPolSchema",0));
    String tGrpPolNo = mLBPolSchema.getGrpPolNo();
		String tLoadFlag = "3";
		if( !tGrpPolNo.equals( "00000000000000000000" ))	// �����µĸ���Ͷ����
			tLoadFlag = "4";
		%>
    <script language="javascript">
    try {
      if (parent.fraInterface.loadFlag == 5) 
      {
        parent.fraInterface.getRiskInput("<%=mLBPolSchema.getRiskCode()%>", "5");   
      	//parent.fraInterface.showCodeName();
      } 
      else 
      {
        parent.fraInterface.getRiskInput("<%=mLBPolSchema.getRiskCode()%>", "<%=tLoadFlag%>");   
      	//parent.fraInterface.showCodeName();
      }
    } catch(ex) {}
    <%
    //�����µĸ���Ͷ����
    int elementsNum;
    Class c = mLBPolSchema.getClass();
    Field f[] = c.getDeclaredFields();
    
    for(elementsNum=0; elementsNum<f.length; elementsNum++) 
    {
      if (!mLBPolSchema.getV(f[elementsNum].getName()).equals("null")) 
      {
        out.println("try{");
        out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() + "').value='" + mLBPolSchema.getV(f[elementsNum].getName()) + "';");
        out.println("} catch(ex) {};");
      }
    }
		loggerDebug("AllDescProposalQueryDetail","End Display ������Ϣ...");
    %>
    </script>

		<%
		// ������Ϣ��δ����
		LBDutySet mLBDutySet = new LBDutySet(); 
		mLBDutySet.set((LBDutySet)tVData.getObjectByObjectName("LBDutySet",0));
		LMDutySet mLMDutySet = new LMDutySet(); 
		mLMDutySet.set((LMDutySet)tVData.getObjectByObjectName("LMDutySet",0));
		int dutyCount = mLBDutySet.size();
		loggerDebug("AllDescProposalQueryDetail","dutyCount:"+dutyCount);
		loggerDebug("AllDescProposalQueryDetail","mLMDutySet:"+mLMDutySet.encode());
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
			LBDutySchema mLBDutySchema = mLBDutySet.get(i);
			LMDutySchema mLMDutySchema = mLMDutySet.get(i);
			%>	
	    	<script language="javascript">
	    	parent.fraInterface.DutyGrid.addOne();
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 1, "<%=mLBDutySchema.getDutyCode()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 2, "<%=mLMDutySchema.getDutyName()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 3, "<%=mLBDutySchema.getPrem()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 4, "<%=mLBDutySchema.getAmnt()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 5, "<%=mLBDutySchema.getPayYears()%>");
		   	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>, 6, "<%=mLBDutySchema.getGetYear()%>");
	    	</script>
	    <%
	  }
		loggerDebug("AllDescProposalQueryDetail","End Display ������Ϣ...");

		// ��������Ϣ
		LBInsuredSet mLBInsuredSet = new LBInsuredSet(); 
		mLBInsuredSet.set((LBInsuredSet)tVData.getObjectByObjectName("LBInsuredSet",0));
		int insuredCount = mLBInsuredSet.size();
		for (int i = 1; i <= insuredCount; i++)	{
			LBInsuredSchema mLBInsuredSchema = mLBInsuredSet.get(i);
			if (mLBInsuredSchema.getInsuredGrade() != null && mLBInsuredSchema.getInsuredGrade().equals("M"))	{
			%>	
	    	<script language="javascript">
	    	<%
    	  // ��������Ϣ 
    	  //����ͻ��ţ������ж��Ƿ���Ͷ������ͬһ����
    	  customerNo = mLBInsuredSchema.getCustomerNo();
    	  c = mLBInsuredSchema.getClass();
    	  f = c.getDeclaredFields();
    	  
    	  for (elementsNum=0; elementsNum<f.length; elementsNum++) {  
    	    if (!mLBInsuredSchema.getV(f[elementsNum].getName()).equals("null")) {
      	    out.println("try {");
      	    out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() +"').value = '" + mLBInsuredSchema.getV(f[elementsNum].getName()) + "';");
      	    out.println("} catch(ex) { };");
      	  }
    	  }    
    	  %>
		    
		    parent.fraInterface.document.all("Age").value="<%=PubFun.calInterval(mLBInsuredSchema.getBirthday(), PubFun.getCurrentDate(), "Y")%>";
	    	//parent.fraInterface.showCodeName();
	    	</script>
	    	<%
	    	break;
	    }
	  }
		loggerDebug("AllDescProposalQueryDetail","End Display ��������Ϣ...");
		
		// ������Ϣ�ĸ�������
		LBGetSet mLBGetSet = new LBGetSet(); 
		mLBGetSet.set((LBGetSet)tVData.getObjectByObjectName("LBGetSet",0));
		int lcgetCount = mLBGetSet.size();
				
		for (int i = 1; i <= lcgetCount; i++)	{
			LBGetSchema mLBGetSchema = mLBGetSet.get(i);
			loggerDebug("AllDescProposalQueryDetail","mLBGetSchema:" + mLBGetSchema.encode() + "\n" + mLBGetSchema.getGetDutyKind());
			if (mLBGetSchema.getGetDutyKind()!=null && !mLBGetSchema.getGetDutyKind().equals(""))	{
			%>	
	    	<script language="javascript">
		    try { parent.fraInterface.document.all("GetDutyKind").value="<%=mLBGetSchema.getGetDutyKind()%>"; } catch(ex) {}
	    	</script>
	    	<%    	
	    	break;
	    }
	  }
		loggerDebug("AllDescProposalQueryDetail","End Display ��������..."); 
		
		// ������������Ϣ��δ����
		LBInsuredSet mLBSubInsuredSet = new LBInsuredSet(); 
		mLBSubInsuredSet.set((LBInsuredSet)tVData.getObjectByObjectName("LBInsuredSet",0));
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
		  loggerDebug("AllDescProposalQueryDetail","insured set size:" + insuredCount);
		  loggerDebug("AllDescProposalQueryDetail","**********************");
		}
		for (int i = 1; i <= insuredCount; i++) {
			LBInsuredSchema mLBSubInsuredSchema = mLBInsuredSet.get(i);
			int j = 0;
			    loggerDebug("AllDescProposalQueryDetail","CustomerNo:" + mLBSubInsuredSchema.getCustomerNo());
				loggerDebug("AllDescProposalQueryDetail","Name:" + mLBSubInsuredSchema.getName());
			%>	
	    	<script language="javascript">
				parent.fraInterface.SubInsuredGrid.addOne("SubInsuredGrid");	    	
	    	    
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,1,"<%=mLBSubInsuredSchema.getCustomerNo()%>");
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,2,"<%=mLBSubInsuredSchema.getName()%>");
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,3,"<%=mLBSubInsuredSchema.getSex()%>");
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,4,"<%=mLBSubInsuredSchema.getBirthday()%>");
	    	    parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>,5,"<%=mLBSubInsuredSchema.getRelationToInsured()%>");	  
	    	    //parent.fraInterface.showCodeName();  	    	    	    	    	    	    	
	    	</script>
	    <%
			j++;
	  }
		loggerDebug("AllDescProposalQueryDetail","End Display ������������Ϣ...");

	
		// ����Ͷ������Ϣ
		LBAppntIndSchema mLBAppntIndSchema = new LBAppntIndSchema(); 
		mLBAppntIndSchema.setSchema((LBAppntIndSchema)tVData.getObjectByObjectName("LBAppntIndSchema",0));
		%>
    	<script language="javascript">
    	<%
    	  // ����Ͷ������Ϣ
    	  //�жϣ����Ͷ���˵��ڱ�����
    	  
    	    %>
    	    
    	    parent.fraInterface.isSamePersonQuery();
    	    parent.fraInterface.document.all("AppntCustomerNo").value="<%=customerNo%>";
    	    //parent.fraInterface.showCodeName();
   
    	    <%  
    	
      	  	c = mLBAppntIndSchema.getClass();
      	 	f = c.getDeclaredFields();
      	  
      	  	for (elementsNum=0; elementsNum<f.length; elementsNum++) {  
      	    	if (!mLBAppntIndSchema.getV(f[elementsNum].getName()).equals("null")) {
        	    out.println("try {");
        	    out.println("parent.fraInterface.document.all('Appnt" + f[elementsNum].getName() +"').value = '" + mLBAppntIndSchema.getV(f[elementsNum].getName()) + "';");
        	    out.println("} catch(ex) { };");
        	 }
      	   
    	%>
		    try { parent.fraInterface.document.all("AppntAge").value="<%=PubFun.calInterval(mLBAppntIndSchema.getBirthday(), PubFun.getCurrentDate(), "Y")%>";
		    			//parent.fraInterface.showCodeName();
		     } catch(ex) { }
    	</script>

  	<script language="javascript">
		<%
      	} 
		loggerDebug("AllDescProposalQueryDetail","End Display ����Ͷ������Ϣ...");
		// ����Ͷ������Ϣ
		LBAppntGrpSchema mLBAppntGrpSchema = new LBAppntGrpSchema(); 
		mLBAppntGrpSchema.setSchema((LBAppntGrpSchema)tVData.getObjectByObjectName("LBAppntGrpSchema",0));
		
		c = mLBAppntGrpSchema.getClass();
	  f = c.getDeclaredFields();
	  
	  for (elementsNum=0; elementsNum<f.length; elementsNum++) {  
	    if (!mLBAppntGrpSchema.getV(f[elementsNum].getName()).equals("null")) {
  	    out.println("try {");
  	    out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() +"').value = '" + mLBAppntGrpSchema.getV(f[elementsNum].getName()) + "';");
  	    out.println("} catch(ex) { };");
  	  }
	  }
	  
		loggerDebug("AllDescProposalQueryDetail","End Display ����Ͷ������Ϣ...");
		%>  	
		try { parent.fraInterface.document.all("AppGrpNo").value="<%=mLBAppntGrpSchema.getGrpNo()%>"; } catch(ex) { }
		try { parent.fraInterface.document.all("AppGrpName").value="<%=mLBAppntGrpSchema.getGrpName()%>"; } catch(ex) { }
		try { parent.fraInterface.document.all("AppGrpAddress").value="<%=mLBAppntGrpSchema.getGrpAddress()%>"; } catch(ex) { }
		try { parent.fraInterface.document.all("AppGrpZipCode").value="<%=mLBAppntGrpSchema.getGrpZipCode()%>"; } catch(ex) { }
					//parent.fraInterface.showCodeName();
  	</script>

    <%
		// ��������Ϣ
		LBBnfSet mLBBnfSet = new LBBnfSet(); 
		mLBBnfSet.set((LBBnfSet)tVData.getObjectByObjectName("LBBnfSet",0));
		int bnfCount = mLBBnfSet.size();
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
			LBBnfSchema mLBBnfSchema = mLBBnfSet.get(i);
		%>	
	    	<script language="javascript">
	    	parent.fraInterface.BnfGrid.addOne("BnfGrid");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 1, "<%=mLBBnfSchema.getBnfType()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 2, "<%=mLBBnfSchema.getName()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 3, "<%=mLBBnfSchema.getSex()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 4, "<%=mLBBnfSchema.getIDType()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 5, "<%=mLBBnfSchema.getIDNo()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 6, "<%=mLBBnfSchema.getBirthday()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 7, "<%=mLBBnfSchema.getRelationToInsured()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 8, "<%=mLBBnfSchema.getBnfLot()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 9, "<%=mLBBnfSchema.getBnfGrade()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 10, "<%=mLBBnfSchema.getAddress()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 11, "<%=mLBBnfSchema.getZipCode()%>");
		   	parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 12, "<%=mLBBnfSchema.getPhone()%>");
	    	//parent.fraInterface.showCodeName();
	    	</script>
	  <%
	  }
		loggerDebug("AllDescProposalQueryDetail","End Display ��������Ϣ...");

		// ��֪��Ϣ
		LBCustomerImpartSet mLBCustomerImpartSet = new LBCustomerImpartSet(); 
		mLBCustomerImpartSet.set((LBCustomerImpartSet)tVData.getObjectByObjectName("LBCustomerImpartSet",0));
		int impartCount = mLBCustomerImpartSet.size();
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
			LBCustomerImpartSchema mLBCustomerImpartSchema = mLBCustomerImpartSet.get(i);
		%>	
	    	<script language="javascript">
	    	parent.fraInterface.ImpartGrid.addOne("ImpartGrid");
		   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 1, "<%=mLBCustomerImpartSchema.getCustomerNo()%>");
		   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 2, "<%=mLBCustomerImpartSchema.getImpartCode()%>");
		   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 3, "<%=mLBCustomerImpartSchema.getImpartVer()%>");
		   	parent.fraInterface.ImpartGrid.setRowColData(<%=i-1%>, 5, "<%=mLBCustomerImpartSchema.getImpartContent()%>");
	    	//parent.fraInterface.showCodeName();
	    	</script>
	  <%
	  }
		loggerDebug("AllDescProposalQueryDetail","End Display ��֪��Ϣ...");

		// ��Լ��Ϣ
		LBSpecSet mLBSpecSet = new LBSpecSet(); 
		mLBSpecSet.set((LBSpecSet)tVData.getObjectByObjectName("LBSpecSet",0));
		int specCount = mLBSpecSet.size();
		%>
		<script>parent.fraInterface.SpecGrid.clearData("SpecGrid");</script>
		<%
		if (specCount == 0) { %>
		    <script>
		        parent.fraInterface.divLCSpec1.style.display = 'none';
		    </script>    
	<%	}
		for (int i = 1; i <= specCount; i++) {
			LBSpecSchema mLBSpecSchema = mLBSpecSet.get(i);
			%>	
	    	<script language="javascript"> 	
        	parent.fraInterface.SpecGrid.addOne("SpecGrid");
		   		parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 1, "<%=mLBSpecSchema.getSpecCode()%>");
					parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 2, "<%=mLBSpecSchema.getSpecType()%>");
		   		parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 3, "<%=mLBSpecSchema.getSpecContent()%>");
	    		//parent.fraInterface.showCodeName();
	    	</script>
	    <%
	  }
		loggerDebug("AllDescProposalQueryDetail","End Display ��Լ��Ϣ...");
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail") 
  {
    //tError = tProposalQuery_BUI.mErrors;
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
%>
	    	<script language="javascript"> 
	    	alert("<%=Content%>");
	    	</script>	    	
<%    	
    }
  }
  
loggerDebug("AllDescProposalQueryDetail",FlagStr);
loggerDebug("AllDescProposalQueryDetail",Content);
loggerDebug("AllDescProposalQueryDetail","------ProposalQueryDetail end------\n\n");

%> 

<script>
  parent.fraInterface.emptyUndefined();
  if (parent.fraInterface.loadFlag ==3|| parent.fraInterface.loadFlag ==5) {
		parent.fraInterface.showCodeName();	
  }
</script>
