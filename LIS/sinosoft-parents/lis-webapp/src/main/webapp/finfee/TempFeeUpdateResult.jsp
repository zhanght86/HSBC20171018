<%
//程序名称：TempFeeQureyResult.jsp
//程序功能：
//创建日期：
//创建人  ：yaory
//更新记录：  更新人    更新日期     更新原因/内容
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <%@page import="com.sinosoft.service.*" %>
	
<%
  
  CErrors tError = null;  
  String Content = "";
  String FlagStr = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
 
  String TempFeeNo   = request.getParameter("TempFeeNo");
  String TOtherNo     = request.getParameter("OtherNo");
  
  
  TransferData mTransferData = new TransferData();

  
  int recordCount = 0;
  String tOpt=request.getParameter("Opt");

  LJTempFeeSet  mLJTempFeeSet=new LJTempFeeSet(); 
  LJTempFeeClassSet mLJTempFeeClassSet=new LJTempFeeClassSet(); 
  LJTempFeeSchema tLJTempFeeSchema=new LJTempFeeSchema();
  LJTempFeeClassSchema tLJTempFeeClassSchema=new LJTempFeeClassSchema();
  VData tVData = new VData();

  if (tOpt.equals("QUERY")) 
  { 
  //暂交费表  
	  tVData.addElement(tG);
	  tLJTempFeeSchema = new LJTempFeeSchema(); 
	  tLJTempFeeSchema.setTempFeeNo(TempFeeNo); 
	  tLJTempFeeSchema.setOtherNo(TOtherNo);
	  tLJTempFeeSchema.setOtherNoType("4");
	  tVData.add(tLJTempFeeSchema);
	  tVData.addElement(mTransferData);
  
  	//TempFeeQueryUI tTempFeeQueryUI = new TempFeeQueryUI();

		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

   if (!tBusinessDelegate.submitData(tVData,"QUERYUpd","TempFeeQueryUI"))
    {
	    Content = " 查询失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
	    FlagStr = "Fail";
    }   
   else
    {   
		tVData.clear();
		mLJTempFeeSet = new LJTempFeeSet();
		mLJTempFeeClassSet = new LJTempFeeClassSet();
		tVData = tBusinessDelegate.getResult();
	    mLJTempFeeSet.set((LJTempFeeSet)tVData.getObjectByObjectName("LJTempFeeSet",0)); //两个结果集合    
		mLJTempFeeClassSet.set((LJTempFeeClassSet)tVData.getObjectByObjectName("LJTempFeeClassSet",0));                     
       
       //暂交费表得到纪录数据：
      //得到符合查询条件的纪录的数目，后面循环时用到
       recordCount=mLJTempFeeSet.size();
       if (recordCount>0)
       {
      %>
      <script language="javascript">
   		  parent.fraInterface.document.all("Frame1").style.display= "";
		  parent.fraInterface.document.all("Frame2").style.display= "";
		  parent.fraInterface.document.all("ManageCom").value="<%=mLJTempFeeSet.get(1).getManageCom()%>"; 
		  parent.fraInterface.document.all("PayDate").value="<%=mLJTempFeeSet.get(1).getPayDate()%>";
		  parent.fraInterface.document.all("AgentCode").value="<%=mLJTempFeeSet.get(1).getAgentCode()%>";
		  parent.fraInterface.document.all("AgentGroup").value="<%=mLJTempFeeSet.get(1).getAgentGroup()%>";
		  parent.fraInterface.document.all("TempFeeType").value="<%=mLJTempFeeSet.get(1).getTempFeeType()%>";	
		  parent.fraInterface.document.all("TempFeeType").value="<%=mLJTempFeeSet.get(1).getTempFeeType()%>";	
      </script>
 <%      
         String strRecord="0|"+recordCount+"^";
         strRecord=strRecord+mLJTempFeeSet.encode();       
%>
      <script language="javascript">
        //调用js文件中显示数据的函数
          parent.fraInterface.showRecord("<%=strRecord%>"); 
      </script>                
<%
		recordCount=mLJTempFeeClassSet.size();
		strRecord="0|"+recordCount+"^";
        strRecord=strRecord+mLJTempFeeClassSet.encode();       
%>
      <script language="javascript">
        //调用js文件中显示数据的函数
        parent.fraInterface.showRecord1("<%=strRecord%>"); 
      </script>                
<%
    	}
    }   

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content = " 查询成功";
      FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = " 查询失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
   }
 }

  if (tOpt.equals("SAVE")) 
  { 
		        
	        //还是把所有的数据都显示出来(后台处理），然后直接DELETE||INSERT
	      String TempGridNo[] = request.getParameterValues("TempGridNo");	        
        String TempFeeNo2[]  = request.getParameterValues("TempGrid1");
        String RiskCode[]     = request.getParameterValues("TempGrid2");
        String PayMoney[]   = request.getParameterValues("TempGrid3");
       // String PayDate[]     = request.getParameterValues("TempGrid4");
        String OtherNo[] = request.getParameterValues("TempGrid5");
        String PayIntv[] = request.getParameterValues("TempGrid6");
        String PayEndYear[] = request.getParameterValues("TempGrid7");
        String tManageCom = request.getParameter("ManageCom");
				String tPayDate = request.getParameter("PayDate");
				String tAgentCode=request.getParameter("AgentCode");
				String tAgentGroup=request.getParameter("AgentGroup");
				String tTempFeeType=request.getParameter("TempFeeType");

        
        loggerDebug("TempFeeUpdateResult","tempfee no ======="+TempGridNo.length);
        mLJTempFeeSet = new LJTempFeeSet();

        for (int i = 0; i < TempGridNo.length; i++)
        {
        
	        tLJTempFeeSchema=new LJTempFeeSchema();
	        tLJTempFeeSchema.setTempFeeNo(TempFeeNo2[i]);
	        tLJTempFeeSchema.setTempFeeType(tTempFeeType);
	        tLJTempFeeSchema.setRiskCode(RiskCode[i]);
	        tLJTempFeeSchema.setPayMoney(PayMoney[i]);
	        tLJTempFeeSchema.setPayDate(tPayDate);
	        tLJTempFeeSchema.setOtherNo(OtherNo[i]);
	        tLJTempFeeSchema.setPayIntv(PayIntv[i]);
	        tLJTempFeeSchema.setPayYears(PayEndYear[i]);
	        tLJTempFeeSchema.setManageCom(tManageCom);
					tLJTempFeeSchema.setAgentCode(tAgentCode);
					tLJTempFeeSchema.setAgentGroup(tAgentGroup);
          mLJTempFeeSet.add(tLJTempFeeSchema);       
        }
        
        String LJTempFeeClassNum[] = request.getParameterValues("TempClassToGridNo");
        String TempFeeNo1[] = request.getParameterValues("TempClassToGrid1");
        String PayMode[] = request.getParameterValues("TempClassToGrid2");
        String PayMoney1[] = request.getParameterValues("TempClassToGrid3");
        String ChequeNo[] = request.getParameterValues("TempClassToGrid5");
        String EnterAccDate[] = request.getParameterValues("TempClassToGrid6");
        String InBankCode[] = request.getParameterValues("TempClassToGrid7");
        String InBankAccNo[] = request.getParameterValues("TempClassToGrid8");
        String InAccName[] = request.getParameterValues("TempClassToGrid9");	
        String IDType[] = request.getParameterValues("TempClassToGrid12");
        String IDNo[] = request.getParameterValues("TempClassToGrid13");	
        
        mLJTempFeeClassSet = new LJTempFeeClassSet();

        for (int j = 0; j < LJTempFeeClassNum.length; j++)
        {
        
          tLJTempFeeClassSchema=new LJTempFeeClassSchema();
          tLJTempFeeClassSchema.setTempFeeNo(TempFeeNo1[j]);
          tLJTempFeeClassSchema.setPayMode(PayMode[j]);
          tLJTempFeeClassSchema.setPayMoney(PayMoney1[j]);
          tLJTempFeeClassSchema.setPayDate(tPayDate);
          tLJTempFeeClassSchema.setChequeNo(ChequeNo[j]);
          tLJTempFeeClassSchema.setEnterAccDate(EnterAccDate[j]);
          tLJTempFeeClassSchema.setBankCode(InBankCode[j]);
          tLJTempFeeClassSchema.setBankAccNo(InBankAccNo[j]);
          tLJTempFeeClassSchema.setAccName(InAccName[j]);
          //tLJTempFeeClassSchema.setOtherNo(OtherNoClass[j]);
          //tLJTempFeeClassSchema.setOtherNoType(OtherNoTypeClass[j]);
          tLJTempFeeClassSchema.setIDType(IDType[j]);
          tLJTempFeeClassSchema.setIDNo(IDNo[j]);
					tLJTempFeeClassSchema.setManageCom(tManageCom);
					
          mLJTempFeeClassSet.add(tLJTempFeeClassSchema);
        
        }
        
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("orign", mLJTempFeeSet);  
        loggerDebug("TempFeeUpdateResult","kkkkkkkkkkkkkkkkk");
        tVData.clear();
        tVData.addElement(tTransferData); 
        tVData.addElement(tG);
         
        tVData.add(mLJTempFeeSet);  //需要修改的数据
        tVData.add(mLJTempFeeClassSet); //需要修改的数据 
           
  
		  //TempFeeUpdateUI tTempFeeUpdateUI = new TempFeeUpdateUI();
		  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		 
		  if (!tBusinessDelegate.submitData(tVData,"UPDATE","TempFeeUpdateUI"))
		  {
		     loggerDebug("TempFeeUpdateResult","修改失败!");
		     Content = " 修改失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		     FlagStr = "Fail";
		   }   
		   else
		    {  
				  Content="操作成功!"; 
				  FlagStr = "Succ";
					loggerDebug("TempFeeUpdateResult","操作成功!");
		}
 }
%>                                        
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
