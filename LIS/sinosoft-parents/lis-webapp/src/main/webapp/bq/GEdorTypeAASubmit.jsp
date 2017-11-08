<%
//程序名称：GEdorTypeAASubmit.jsp
//程序功能：
//创建日期：2005-10-27 16:49:22
//创建人  ：wenhuan
//更新记录：  更新人    更新日期     更新原因/内容
%>

 <%@page import="com.sinosoft.utility.*"%>
 <%@page import="com.sinosoft.lis.schema.*"%>
 <%@page import="com.sinosoft.lis.vschema.*"%>

 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.*" %>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 

<%
    String busiUIName="bqgrpPGrpEdorAADetailUI";
    String busiBLName="bqgrpPGrpEdorAADetailBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
    ////loggerDebug("GEdorTypeAASubmit","-----AA submit---");
    GlobalInput tG = new GlobalInput();
    LPPolSchema tLPPolSchema = new LPPolSchema();
    LPDutySchema tLPDutySchema = new LPDutySchema();
    LPDutySet tLPDutySet = new LPDutySet();
    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
   
    CErrors tError = new CErrors();
 
    String tRela  = "";                
    String FlagStr = "";
    String Content = "";
    String transact = "";
    String[] Result = new String[13];
    String[] tResult=new String[3];
    transact = request.getParameter("fmtransact");	  
	  tG = (GlobalInput)session.getValue("GI");
	  String theCurrentDate = PubFun.getCurrentDate();
    String theCurrentTime = PubFun.getCurrentTime();	  
	  if(transact.equals("INSERT||MAIN"))
	  {
    
    String tChk[] = request.getParameterValues("InpPolGridSel"); 
    String tRiskCode[] = request.getParameterValues("PolGrid1");
    String RiskCode;   
    String tPolNo[] = request.getParameterValues("PolGrid9");
    String PolNo;
    String tcalmode[] = request.getParameterValues("DutyGrid7");
    String tDutyCode[] = request.getParameterValues("DutyGrid1");
    String tAmnt[] = request.getParameterValues("DutyGrid5");
    String tPrem[] = request.getParameterValues("DutyGrid6");     
    ////loggerDebug("GEdorTypeAASubmit","tChk.length =========>"+tChk.length);
    ////loggerDebug("GEdorTypeAASubmit","tChk[0] =========>"+tChk[0]);
  
    for(int index=0;index<tChk.length;index++)
    {
       if(tChk[index].equals("1"))
       {       
    	   ////loggerDebug("GEdorTypeAASubmit","PolNo ========>" + tPolNo[index]);
           ////loggerDebug("GEdorTypeAASubmit","tcalmode ========>" + tcalmode[index]);
           tLPPolSchema.setPolNo(tPolNo[index]);
           PolNo = tPolNo[index];
           tLPPolSchema.setProposalNo(PolNo);
            tLPPolSchema.setInsuredNo(request.getParameter("InsuredNo"));            
            tLPPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
            tLPPolSchema.setContNo(request.getParameter("ContNo"));
            tLPPolSchema.setPrtNo(request.getParameter("ContNo"));   
            tLPPolSchema.setEdorNo(request.getParameter("EdorNo"));
	          tLPPolSchema.setEdorType(request.getParameter("EdorType"));
	          tLPPolSchema.setRiskCode(tRiskCode[index]);
	          tLPPolSchema.setMakeDate(theCurrentDate);
	          tLPPolSchema.setMakeTime(theCurrentTime);
	          tLPPolSchema.setModifyDate(theCurrentDate);
	          tLPPolSchema.setModifyTime(theCurrentTime);  
	          tLPPolSchema.setOperator(tG.Operator);
	          tLPPolSchema.setManageCom(tG.ManageCom);	
            String tDutyChk[] = request.getParameterValues("InpDutyGridChk"); 
            for(int i=0;i<tDutyChk.length;i++)
            {
              if(tDutyChk[i].equals("1")){
              tLPDutySchema = new LPDutySchema(); 
              tLPDutySchema.setPolNo(PolNo);
              tLPDutySchema.setDutyCode(tDutyCode[i]);
              tLPDutySchema.setEdorNo(request.getParameter("EdorNo"));
	            tLPDutySchema.setEdorType(request.getParameter("EdorType"));
	            // //loggerDebug("GEdorTypeAASubmit","tcalmode ========>" + tcalmode[i]);
	          //try{
	          //if(tcalmode[i].equals("G") && Float.parseFloat(tAmnt[i])>0){
            //tLPDutySchema.setAmnt(tAmnt[i]);
            //}
            //else if(tcalmode[i].equals("P") && Float.parseFloat(tPrem[i])>0){                 
            //tLPDutySchema.setPrem(tPrem[i]);              
            //}
            //else if(Float.parseFloat(tAmnt[i])>0 && Float.parseFloat(tPrem[i])>0){
              tLPDutySchema.setAmnt(tAmnt[i]);
              tLPDutySchema.setPrem(tPrem[i]);
            //}else{
            //Content = "保存失败，原因是:保额保费输入错误!" ;
            //FlagStr = "Fail";
            //} 
            //}
            // catch(Exception ex){
		        // Content = "保存失败，原因是:保额保费输入错误!" ;
            // FlagStr = "Fail";
	          // } 
                TransferData  mTransferData =new TransferData();
                mTransferData.setNameAndValue("Action","2");
                mTransferData.setNameAndValue("EdorNo",request.getParameter("EdorNo"));
                mTransferData.setNameAndValue("PolNo",PolNo);
                mTransferData.setNameAndValue("EdorValiDate",request.getParameter("EdorValiDate"));                
                mTransferData.setNameAndValue("EdorType",request.getParameter("EdorType"));
                // //loggerDebug("GEdorTypeAASubmit",mTransferData.getValueByName("EdorType"));
                
	                   
            tLPDutySet.add(tLPDutySchema);
            }
            
            }            
            break;
       }           
    }
    
  
  
  if(!FlagStr.equals("Fail")){    
  tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPGrpEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
	tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));	
	tLPGrpEdorItemSchema.setEdorTypeCal(request.getParameter("EdorTypeCal"));
	tLPGrpEdorItemSchema.setMakeDate(theCurrentDate);
  tLPGrpEdorItemSchema.setMakeTime(theCurrentTime);
  tLPGrpEdorItemSchema.setModifyDate(theCurrentDate);
  tLPGrpEdorItemSchema.setModifyTime(theCurrentTime);  
  tLPGrpEdorItemSchema.setOperator(tG.Operator);
  tLPGrpEdorItemSchema.setManageCom(tG.ManageCom);
  
  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));    
	  tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
	  tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
	  tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	  tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	  tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
	  tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	  tLPEdorItemSchema.setEdorTypeCal(request.getParameter("EdorTypeCal"));
	  tLPEdorItemSchema.setMakeDate(theCurrentDate);
    tLPEdorItemSchema.setMakeTime(theCurrentTime);
    tLPEdorItemSchema.setModifyDate(theCurrentDate);
    tLPEdorItemSchema.setModifyTime(theCurrentTime);  
    tLPEdorItemSchema.setOperator(tG.Operator);
    tLPEdorItemSchema.setManageCom(tG.ManageCom);

    try
    {          
  	    VData tVData = new VData(); 	      
	 	    tVData.addElement(tG);
	 	    tVData.addElement(tLPEdorItemSchema);
	 	    tVData.addElement(tLPGrpEdorItemSchema);
	 	    tVData.addElement(tLPPolSchema);
	 	    tVData.addElement(tLPDutySet);
	 	   ////loggerDebug("GEdorTypeAASubmit","tLPGrpEdorItemSchema ========================>"+tLPGrpEdorItemSchema.encode());
	 	   // //loggerDebug("GEdorTypeAASubmit","tLPEdorItemSchema ========================>"+tLPEdorItemSchema.encode());
	 	   // //loggerDebug("GEdorTypeAASubmit","tLPPolSchema ========================>"+tLPPolSchema.encode());	
	 	   // //loggerDebug("GEdorTypeAASubmit","tLPDutySet ========================>"+tLPDutySet.encode()); 	    
    	  tBusinessDelegate.submitData(tVData,transact,busiUIName);	
	   }
	   catch(Exception ex)
	   {
		    Content = "保存失败，原因是:" + ex.toString();
        FlagStr = "Fail";
	   }
	}
	   
     if (FlagStr=="")
     {
        tError = tBusinessDelegate.getCErrors();
        if (!tError.needDealError())
        {     
           Content = " 保存成功";
    	     FlagStr = "Success";              
         }else{
    	     Content = " 保存失败，原因是:" + tError.getFirstError();
    	     FlagStr = "Fail";
    	     // //loggerDebug("GEdorTypeAASubmit","==============================  Fail ====================");
        }
      }		
 }
 else
 {
	//团体项目批改信息
  tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	tLPGrpEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
	tLPGrpEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));
	tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));	
	tLPGrpEdorItemSchema.setEdorTypeCal(request.getParameter("EdorTypeCal"));
	tLPGrpEdorItemSchema.setMakeDate(theCurrentDate);
  tLPGrpEdorItemSchema.setMakeTime(theCurrentTime);
  tLPGrpEdorItemSchema.setModifyDate(theCurrentDate);
  tLPGrpEdorItemSchema.setModifyTime(theCurrentTime);  
  tLPGrpEdorItemSchema.setOperator(tG.Operator);
  tLPGrpEdorItemSchema.setManageCom(tG.ManageCom);
    try
    {          
  	    VData tVData = new VData(); 	      
	 	    tVData.addElement(tG);
	 	    tVData.addElement(tLPGrpEdorItemSchema);	    
    	  tBusinessDelegate.submitData(tVData,transact,busiBLName);	
	   }
	   catch(Exception ex)
	   {
		    Content = "保存失败，原因是:" + ex.toString();
        FlagStr = "Fail";
	   } 
     if (FlagStr=="")
     {
        tError = tBusinessDelegate.getCErrors();
        if (!tError.needDealError())
        {     
           Content = " 保存成功";
    	     FlagStr = "Success";              
         }else{
    	     Content = " 保存失败，原因是:" + tError.getFirstError();
    	     FlagStr = "Fail";
    	     //   //loggerDebug("GEdorTypeAASubmit","==============================  Fail ====================");
        }
      }	   
	    
 }
	  // //loggerDebug("GEdorTypeAASubmit","+++++++++++++++++++=  Over  =+++++++++++++++++++"); 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
</script>
</html>

