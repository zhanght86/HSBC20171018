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
        loggerDebug("LLClaimContPolDealSave","��¼��Ϣû�л�ȡ!!!");
       return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimContPolDealSave","û�л�ȡ������!!!");
       return;   
    }
    
    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tTransferData.setNameAndValue("CustNo",request.getParameter("CustNo"));
    tTransferData.setNameAndValue("ConType",request.getParameter("ConType"));  
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));    
    tTransferData.setNameAndValue("PolNo",request.getParameter("PolNo"));        
    tTransferData.setNameAndValue("ContStateReason",request.getParameter("PolStateReason"));
    tTransferData.setNameAndValue("ContEndDate",request.getParameter("ContEndDate"));    
            


    //loggerDebug("LLClaimContPolDealSave",request.getParameter("CustNo"));
              
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        

        //LLClaimContPolDealUI tLLClaimContPolDealUI = new LLClaimContPolDealUI();
//       if (tLLClaimContPolDealUI.submitData(tVData,tOperate) == false)
//        {
//    
//        } else {
//            Content = " ����ɹ�! ";
//            FlagStr = "SUCC";
//        }
//        int n = tLLClaimContPolDealUI.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//                Content = Content + "ԭ����: " + tLLClaimContPolDealUI.mErrors.getError(i).errorMessage;
//                FlagStr = "FAIL";
//        }  
		String busiName="LLClaimContPolDealUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLClaimContPolDealSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content = Content + "ԭ����: "  + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "����ʧ��";
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
    
//    loggerDebug("LLClaimContPolDealSave","LLMedicalFeeInpSave����--"+FlagStr);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
