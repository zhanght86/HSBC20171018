<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�FineProposalQueryDetail.jsp
//�����ܣ�
//�������ڣ�2008-06-26
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="java.lang.reflect.*"%>
  <%@page import="java.util.Iterator"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String statusFlag = "";

  loggerDebug("FineWbProposalQueryDetail","\n\n------FineProposalQueryDetail start------");
  // ������Ϣ����
      String tSQL ="select to_char(makedate, 'yyyy-mm-dd') from bpopoldata where bussno='"+request.getParameter("TempFeeNo")+"'";
	  ExeSQL tExeSQL = new ExeSQL();
	  String payDate = tExeSQL.getOneValue(tSQL);   
  
    FineTBXMLTransfer TBXMLTransfer1 = new FineTBXMLTransfer();
    VData tVData = new VData();
    tVData=TBXMLTransfer1.getOnePolData(request.getParameter("TempFeeNo"));
    loggerDebug("FineWbProposalQueryDetail","tVData.size: "+tVData.size());

  // ���ݴ���

 if (tVData != null && tVData.size()>0) 
 {    
		// ��ʾ
		// ������Ϣ
		LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema(); 
		LJTempFeeClassSchema mLJTempFeeClassSchema = new LJTempFeeClassSchema(); 
		mLJTempFeeSchema.setSchema((LJTempFeeSchema)tVData.getObjectByObjectName("LJTempFeeSchema",0));
		mLJTempFeeClassSchema.setSchema((LJTempFeeClassSchema)tVData.getObjectByObjectName("LJTempFeeClassSchema",0));
 %>
<html>		
<head>
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
</head>
<body>
    <script language="javascript"> 
    <%
	    //������Ϣ
	    int elementsNum;
	    Class c = mLJTempFeeSchema.getClass();
	    Field f[] = c.getDeclaredFields();
	
	    for(elementsNum=0; elementsNum<f.length; elementsNum++) {
	    	      
	      if (!mLJTempFeeSchema.getV(f[elementsNum].getName()).equals("null")) {
	        out.println("try{");
	        out.println("parent.fraInterface.document.all('" + f[elementsNum].getName() + "').value='" + mLJTempFeeSchema.getV(f[elementsNum].getName()) + "';");
	        loggerDebug("FineWbProposalQueryDetail","parent.fraInterface.document.all('" + f[elementsNum].getName() + "').value='" + mLJTempFeeSchema.getV(f[elementsNum].getName()) + "';");
	        out.println("} catch(ex) {};");
	      }
	    }
	    
	    Class c1 = mLJTempFeeClassSchema.getClass();
	    Field f1[] = c1.getDeclaredFields();
 
		      if (!mLJTempFeeClassSchema.getV("IDNo").equals("null")) {
		        out.println("try{");
		        out.println("parent.fraInterface.document.all('IDNo').value='" + mLJTempFeeClassSchema.getV("IDNo") + "';");
		        loggerDebug("FineWbProposalQueryDetail","parent.fraInterface.document.all('IDNo').value='" + mLJTempFeeClassSchema.getV("IDNo") + "';");
		        out.println("} catch(ex) {};");
		      }

	     if(payDate.equals("null")||payDate==null||payDate.equals(""))
	     {
		        loggerDebug("FineWbProposalQueryDetail","***"+request.getParameter("TempFeeNo")+"bpopoldata��ѯ����!");
		 }  
		 else
		 {
			        out.println("try{");
			        out.println("parent.fraInterface.document.all('PayDate').value='" + payDate + "';");
			        loggerDebug("FineWbProposalQueryDetail","parent.fraInterface.document.all('PayDate').value='" + payDate + "';");
			        out.println("} catch(ex) {};");
	    }
	  	loggerDebug("FineWbProposalQueryDetail","End Display ������Ϣ...");
    %>
    </script> 
       <%
	    VData mtVData = new VData();
	    mtVData = (VData)tVData.getObjectByObjectName("VData",0);
	    Iterator tIIterator = mtVData.iterator();
	    int temp=0;
	    //  ���շѷ�����Ϣ
	    %>
	    <script language=javascript>
	    parent.fraInterface.TempGrid.clearData("TempGrid");
	    parent.fraInterface.TempClassGrid.clearData("TempClassGrid");
		  //prompt("aaa",parent.fraInterface.document.all("AutoPayFlag").value);
		  
		  parent.fraInterface.TempClassGrid.addOne("TempClassGrid");
		      parent.fraInterface.TempClassGrid.setRowColData(<%=temp%>,1,"<%=mLJTempFeeClassSchema.getPayMode()%>");
	          parent.fraInterface.TempClassGrid.setRowColData(<%=temp%>,2,"<%=mLJTempFeeClassSchema.getPayMoney()%>");
	          parent.fraInterface.TempClassGrid.setRowColData(<%=temp%>,3,"<%=mLJTempFeeClassSchema.getChequeNo()%>");
	          parent.fraInterface.TempClassGrid.setRowColData(<%=temp%>,4,"<%=payDate%>");
	          parent.fraInterface.TempClassGrid.setRowColData(<%=temp%>,5,"<%=mLJTempFeeClassSchema.getBankCode()%>");
	          parent.fraInterface.TempClassGrid.setRowColData(<%=temp%>,6,"<%=mLJTempFeeClassSchema.getBankAccNo()%>");
	          parent.fraInterface.TempClassGrid.setRowColData(<%=temp%>,7,"<%=mLJTempFeeClassSchema.getAccName()%>");
	          parent.fraInterface.TempClassGrid.setRowColData(<%=temp%>,8,"<%=mLJTempFeeClassSchema.getIDNo()%>");             
	    </script>
	    <%
	  	 //  ���շ���Ϣ
	    while(tIIterator.hasNext())
	    {
	          FineRiskBasicInfo tRiskBasicInfo = (FineRiskBasicInfo)tIIterator.next();
	          loggerDebug("FineWbProposalQueryDetail",tRiskBasicInfo.getRiskCode());
	          loggerDebug("FineWbProposalQueryDetail","temp"+temp);
	    %>      
	           <script language=javascript>
		  
		  parent.fraInterface.TempGrid.addOne("TempGrid");
		      parent.fraInterface.TempGrid.setRowColData(<%=temp%>,1,"<%=tRiskBasicInfo.getRiskCode()%>");
	          parent.fraInterface.TempGrid.setRowColData(<%=temp%>,2,"<%=tRiskBasicInfo.getMoney()%>");
	          parent.fraInterface.TempGrid.setRowColData(<%=temp%>,3,"<%=mLJTempFeeSchema.getOtherNo()%>");
	          parent.fraInterface.TempGrid.setRowColData(<%=temp%>,4,"<%=tRiskBasicInfo.getIntv()%>");
	          parent.fraInterface.TempGrid.setRowColData(<%=temp%>,5,"<%=tRiskBasicInfo.getYears()%>");          
	          </script>
	          <%
	          temp++;
	    }
	     
	} // end of if
  
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr == "Fail") {
    tError = TBXMLTransfer1.mErrors;
    if (!tError.needDealError()) {                          
    	Content = " ��ѯ�ɹ�! ";
    	FlagStr = "Succ";
    } else {
    	Content = " ��ѯʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
  

loggerDebug("FineWbProposalQueryDetail",FlagStr);
loggerDebug("FineWbProposalQueryDetail",Content);
loggerDebug("FineWbProposalQueryDetail","------FineProposalQueryDetail end------\n\n");

%> 


	  
<script>
  parent.fraInterface.emptyUndefined();
 
  if (parent.fraInterface.loadFlag == 3 || parent.fraInterface.loadFlag == 4) {
    parent.fraInterface.divButton.style.display = "none";
    //�����涯����
    try { parent.fraInterface.setFocus(parent.fraInterface.document.all("TempFeeNo").value); } catch(e) {}
  }
  
  //�����޸ġ������޸�
  if (parent.fraInterface.loadFlag == 3) {
    parent.fraInterface.inputButton.style.display = ""; 
    parent.fraInterface.inputQuest.style.display = "none";
    parent.fraInterface.divButton.style.display = "";    
    parent.fraInterface.deleteButton.style.display="";
    parent.fraInterface.inputQuestButton.style.display="none"; 
    
  }
  
  //���˳��
  if (parent.fraInterface.loadFlag == 4) {
    parent.fraInterface.inputButton.style.display = ""; 
    parent.fraInterface.inputQuest.style.display = "none";
    parent.fraInterface.divButton.style.display = "";    
    parent.fraInterface.deleteButton.style.display="none";
    parent.fraInterface.inputQuestButton.style.display="none"; 
    
  }  
</script>
</body>
</html>
