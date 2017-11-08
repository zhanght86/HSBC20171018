 
  <%
//程序名称：TempFeeQureyResult.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  String mAction = request.getParameter("fmAction");
  loggerDebug("TempFeeQueryResult","1111111");
  CErrors tError = null;  
  String Content = "";
  String FlagStr = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
 
  String tManageCom  = request.getParameter("ManageCom");  
  String StartDate  = request.getParameter("StartDate"); 
  String EndDate   = request.getParameter("EndDate"); 
  String Operator   = request.getParameter("Operator"); 
  String AgentCode = request.getParameter("AgentCode"); 
  String TempFeeNo = request.getParameter("TempFeeNo");
  String PrtNo = request.getParameter("PrtNo");
  String tMaxMoney = request.getParameter("MaxMoney");
  String tMinMoney = request.getParameter("MinMoney");  
  String tEnterAccStartDate = request.getParameter("EnterAccStartDate");
  String tEnterAccEndDate = request.getParameter("EnterAccEndDate"); 
  
  //loggerDebug("TempFeeQueryResult","chequeno:"+ChequeNo);
  loggerDebug("TempFeeQueryResult","----------mAction:"+mAction);
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("MaxMoney",tMaxMoney);
  tTransferData.setNameAndValue("MinMoney",tMinMoney);
  tTransferData.setNameAndValue("ManageCom",tManageCom);
  tTransferData.setNameAndValue("EnterAccStartDate",tEnterAccStartDate);
  tTransferData.setNameAndValue("EnterAccEndDate",tEnterAccEndDate);
  tTransferData.setNameAndValue("StartDate",StartDate);
  tTransferData.setNameAndValue("EndDate",EndDate);
  

  
  if(mAction.equals("QUERY")){
    String TempFeeStatus   = request.getParameter("TempFeeStatus");
    String RiskCode = request.getParameter("RiskCode");
    tTransferData.setNameAndValue("TempFeeStatus",TempFeeStatus);
       
    int recordCount = 0;
    double  PayMoney = 0;;   //交费金额
    
    
    //暂交费表
    LJTempFeeSet     mLJTempFeeSet     ; 
    LJTempFeeSchema  tLJTempFeeSchema  ;  
    VData tVData = new VData();
    
    tVData.addElement(tG);
    tLJTempFeeSchema = new LJTempFeeSchema(); 
    tLJTempFeeSchema.setRiskCode(RiskCode); 
    tLJTempFeeSchema.setOperator(Operator); 
    tLJTempFeeSchema.setAgentCode(AgentCode); 
    tLJTempFeeSchema.setTempFeeNo(TempFeeNo); 
    if(PrtNo!=null&&!PrtNo.trim().equals("")){
      tLJTempFeeSchema.setOtherNo(PrtNo);
      //tLJTempFeeSchema.setOtherNoType("4");
    }
    
    tVData.add(tLJTempFeeSchema);
    //tVData.addElement(StartDate);
   // tVData.addElement(EndDate);
   // tVData.addElement(TempFeeStatus);
  	tVData.addElement(tTransferData);
  	
    //TempFeeQueryUI tTempFeeQueryUI = new TempFeeQueryUI();
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();


    if (!tBusinessDelegate.submitData(tVData,mAction,"TempFeeQueryUI"))
    {
      loggerDebug("TempFeeQueryResult","查询暂交费表时出错！没有相关数据存在");
      
      if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
      { 
			Content = "查询失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
	   }
	   else
	   {
			Content = "查询失败";
			FlagStr = "Fail";				
	   }
    }   
    else
    {   
    	Content = " 查询成功";
        FlagStr = "Succ";
        
  	    tVData.clear();
        mLJTempFeeSet = new LJTempFeeSet();
	      tVData = tBusinessDelegate.getResult();
        mLJTempFeeSet.set((LJTempFeeSet)tVData.getObjectByObjectName("LJTempFeeSet",0));             
       
       //暂交费表得到纪录数据：
       //得到符合查询条件的纪录的数目，后面循环时用到
       recordCount=mLJTempFeeSet.size();
       if (recordCount>0)
       {
       String strRecord="0|"+recordCount+"^";
       strRecord=strRecord+mLJTempFeeSet.encode(); 
       loggerDebug("TempFeeQueryResult","strRecord:"+strRecord);   
%>
        <script language="javascript">
        //调用js文件中显示数据的函数
        parent.fraInterface.showRecord("<%=strRecord%>"); 
        </script>                
<%

    	}
    }
   }
  
%>                                        
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>




