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
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
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
        loggerDebug("LLClaimContPolDealAdjustPaySave","��¼��Ϣû�л�ȡ!!!");
       return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimContPolDealAdjustPaySave","û�л�ȡ������!!!");
       return;   
    }
    
    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));  
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));    
    tTransferData.setNameAndValue("PolNo",request.getParameter("PolNo"));     
    tTransferData.setNameAndValue("FeeOperationType",request.getParameter("FeeOperationType"));
    tTransferData.setNameAndValue("SubFeeOperationType",request.getParameter("SubFeeOperationType"));    
    tTransferData.setNameAndValue("NewPay",request.getParameter("NewPay"));      
    String tAdjRemark = new String(request.getParameter("NewAdjRemark").getBytes("ISO-8859-1"),"GB2312");        
    tTransferData.setNameAndValue("AdjRemark",request.getParameter("NewAdjRemark"));

    tTransferData.setNameAndValue("ContStaDate",request.getParameter("ContStaDate"));
    
    //loggerDebug("LLClaimContPolDealAdjustPaySave",request.getParameter("CustNo"));
    loggerDebug("LLClaimContPolDealAdjustPaySave","--�������"+tOperate);
              
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        

        //LLClaimContPolDealAdjustPayUI tLLClaimContPolDealAdjustPayUI = new LLClaimContPolDealAdjustPayUI();
//       if (tLLClaimContPolDealAdjustPayUI.submitData(tVData,tOperate) == false)
//        {
//            Content = "��ʾ��Ϣ:";
//
//        } else {
//            Content = " ����ɹ�! ";
//            FlagStr = "SUCC";
//        }
//        int n = tLLClaimContPolDealAdjustPayUI.mErrors.getErrorCount();
//        String tMessage="��ʾ��Ϣ:";
//        for (int i = 0; i < n; i++)
//        {
//                Content = Content  + tLLClaimContPolDealAdjustPayUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//        }  
		String busiName="LLClaimContPolDealAdjustPayUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLClaimContPolDealAdjustPaySave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = " ��ʾ��Ϣ: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "��ʾ��Ϣ";
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
    
//    loggerDebug("LLClaimContPolDealAdjustPaySave","LLMedicalFeeInpSave����--"+FlagStr);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
