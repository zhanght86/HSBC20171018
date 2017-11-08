<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：FineProposalQueryDetail.jsp
//程序功能：
//创建日期：2008-06-26
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="java.lang.reflect.*"%>
  <%@page import="java.util.Iterator"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String statusFlag = "";

  loggerDebug("FineWbProposalQueryDetail","\n\n------FineProposalQueryDetail start------");
  // 财务单信息部分
      String tSQL ="select to_char(makedate, 'yyyy-mm-dd') from bpopoldata where bussno='"+request.getParameter("TempFeeNo")+"'";
	  ExeSQL tExeSQL = new ExeSQL();
	  String payDate = tExeSQL.getOneValue(tSQL);   
  
    FineTBXMLTransfer TBXMLTransfer1 = new FineTBXMLTransfer();
    VData tVData = new VData();
    tVData=TBXMLTransfer1.getOnePolData(request.getParameter("TempFeeNo"));
    loggerDebug("FineWbProposalQueryDetail","tVData.size: "+tVData.size());

  // 数据传输

 if (tVData != null && tVData.size()>0) 
 {    
		// 显示
		// 财务单信息
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
	    //财务单信息
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
		        loggerDebug("FineWbProposalQueryDetail","***"+request.getParameter("TempFeeNo")+"bpopoldata查询错误!");
		 }  
		 else
		 {
			        out.println("try{");
			        out.println("parent.fraInterface.document.all('PayDate').value='" + payDate + "';");
			        loggerDebug("FineWbProposalQueryDetail","parent.fraInterface.document.all('PayDate').value='" + payDate + "';");
			        out.println("} catch(ex) {};");
	    }
	  	loggerDebug("FineWbProposalQueryDetail","End Display 财务单信息...");
    %>
    </script> 
       <%
	    VData mtVData = new VData();
	    mtVData = (VData)tVData.getObjectByObjectName("VData",0);
	    Iterator tIIterator = mtVData.iterator();
	    int temp=0;
	    //  暂收费分类信息
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
	  	 //  暂收费信息
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
  
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr == "Fail") {
    tError = TBXMLTransfer1.mErrors;
    if (!tError.needDealError()) {                          
    	Content = " 查询成功! ";
    	FlagStr = "Succ";
    } else {
    	Content = " 查询失败，原因是:" + tError.getFirstError();
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
    //增加随动处理
    try { parent.fraInterface.setFocus(parent.fraInterface.document.all("TempFeeNo").value); } catch(e) {}
  }
  
  //复核修改、问题修改
  if (parent.fraInterface.loadFlag == 3) {
    parent.fraInterface.inputButton.style.display = ""; 
    parent.fraInterface.inputQuest.style.display = "none";
    parent.fraInterface.divButton.style.display = "";    
    parent.fraInterface.deleteButton.style.display="";
    parent.fraInterface.inputQuestButton.style.display="none"; 
    
  }
  
  //复核抽查
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
