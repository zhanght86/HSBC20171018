<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：GEdorTypeMultiDetailSubmit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
    //接收信息，并作校验处理
    loggerDebug("GEdorTypeMultiRiskSubmit","-----Detail submit---");
                 
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String Result="";
    TransferData tTransferData = new TransferData(); 
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    
    LCGrpPolSet tLCGrpPolSet   = new LCGrpPolSet();
    LPGrpEdorItemSchema tLPGrpEdorItemSchema   = new LPGrpEdorItemSchema();
    
    tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
    tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    loggerDebug("GEdorTypeMultiRiskSubmit","transact"+transact);
    transact=(String)request.getParameter("Transact");
    
    loggerDebug("GEdorTypeMultiRiskSubmit","transact"+transact);
    
    if (transact.equals("INSERT||EDORRISK"))
    {
        String tGrpPolNo[] = request.getParameterValues("RiskGrid1");    //合同号 
        String tRiskCode[] = request.getParameterValues("RiskGrid2"); //客户号    
        String tChk[] = request.getParameterValues("InpRiskGridChk");             

        loggerDebug("GEdorTypeMultiRiskSubmit","tChk.length"+tChk.length);
        for(int index=0;index<tChk.length;index++)
        {
            if(tChk[index].equals("1"))
            {
                LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
                //团单险种信息
                tLCGrpPolSchema.setGrpPolNo(tGrpPolNo[index]);
               
	            loggerDebug("GEdorTypeMultiRiskSubmit","tGrpPolNo"+tGrpPolNo[index]);
                tLCGrpPolSet.add(tLCGrpPolSchema);
            }           
        }
    }
    else
    {
        String tGrpPolNo[] = request.getParameterValues("Risk2Grid1");    //合同号 
        String tRiskCode[] = request.getParameterValues("Risk2Grid2"); //客户号    
        String tChk[] = request.getParameterValues("InpRisk2GridChk");             

        loggerDebug("GEdorTypeMultiRiskSubmit","tChk.length"+tChk.length);
        for(int index=0;index<tChk.length;index++)
        {
            if(tChk[index].equals("1"))
            {
                LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
                //团单险种信息
                tLCGrpPolSchema.setGrpPolNo(tGrpPolNo[index]);
               
	            loggerDebug("GEdorTypeMultiRiskSubmit","tGrpPolNo"+tGrpPolNo[index]);
                tLCGrpPolSet.add(tLCGrpPolSchema);
            }           
        }
    }   	
  
  
  GEdorRiskUI tGEdorRiskUI   = new GEdorRiskUI();
  try {
    // 准备传输数据 VData
  	VData tVData = new VData();  
	 	tVData.addElement(tG);
	 	tVData.addElement(tLPGrpEdorItemSchema);
	 	tVData.addElement(tLCGrpPolSet);
	 	
	 	//保存个人保单信息(保全)	
    tGEdorRiskUI.submitData(tVData, transact);
	}
	catch(Exception ex) {
		Content = transact+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
	}			
	
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="") 
  {
    CErrors tError = new CErrors(); 
    tError = tGEdorRiskUI.mErrors;
    
    if (!tError.needDealError()) 
    {                          
      Content = " 保存成功";
    	FlagStr = "Success";
    	
    	if (transact.equals("QUERY||MAIN")||transact.equals("QUERY||DETAIL"))	
    	{
      	    if (tGEdorRiskUI.getResult()!=null&&tGEdorRiskUI.getResult().size()>0) 
      	    {
      	    	Result = (String)tGEdorRiskUI.getResult().get(0);
      	    	
      	    	if (Result==null||Result.trim().equals(""))	
      	    	{
      	    		FlagStr = "Fail";
      	    		Content = "提交失败!!";
      	    	}
      	    }
    	}
    }
    else 
    {
    	Content = " 保存失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

