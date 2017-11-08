<%
//**************************************************************************************************
//Name��LLClaimContDealSave.jsp
//Function�����۱��沢����
//Author������
//Date:   2005-07-14
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //׼��ͨ�ò���
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String tOperate = request.getParameter("hideOperate");    
    if(tG == null) 
    {
        loggerDebug("LLClaimContDealSave","��¼��Ϣû�л�ȡ!!!");
       return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimContDealSave","û�л�ȡ������!!!");
       return;   
    }
    
    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("CustNo"));
    tTransferData.setNameAndValue("ConType",request.getParameter("ConType"));  
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));    
    tTransferData.setNameAndValue("ContStateReason",request.getParameter("StateReason"));
    tTransferData.setNameAndValue("ContEndDate",request.getParameter("ContEndDate"));    
            
    LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();        

    //loggerDebug("LLClaimContDealSave",request.getParameter("CustNo"));
    
    
    //������ֹ���۲����м���
    if (tOperate.equals("CONT"))
    {
        //׼����̨����
        tLLBalanceSchema.setClmNo(request.getParameter("ClmNo"));
        tLLBalanceSchema.setContNo(request.getParameter("ContNo"));                                       
    }
          
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLBalanceSchema );

//        LLClaimContDealUI tLLClaimContDealUI = new LLClaimContDealUI();
//       if (tLLClaimContDealUI.submitData(tVData,tOperate) == false)
//        {
//    
//        } else {
//            Content = " ����ɹ�! ";
//            FlagStr = "SUCC";
//        }
//        int n = tLLClaimContDealUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//                Content = Content + "ԭ����: " + tLLClaimContDealUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//        }  
String busiName="grpLLClaimContDealUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLClaimContDealSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            Content = Content + "ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		  
		   FlagStr = "Fail";				
		} 
}
else {
           Content = " ����ɹ�! ";
           FlagStr = "SUCC";
}

    

    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }
    
//    loggerDebug("LLClaimContDealSave","LLMedicalFeeInpSave����--"+FlagStr);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
