<%@page import="java.io.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLClaimExemptSave.jsp
//�����ܣ�������Ϣ����
//�������ڣ�2005-7-20
//������  ��yuejw
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
    //׼��ͨ�ò���
    CErrors tError = null;
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    String transact = request.getParameter("fmtransact");    
   
    if(tG == null) 
    {
        loggerDebug("LLClaimExemptSave","��¼��Ϣû�л�ȡ!!!");
        return;
     }
      else if (transact == null || transact == "") 
     {
        loggerDebug("LLClaimExemptSave","û�л�ȡ������!!!");
        return;   
    }
    
	//׼������������Ϣ
	LLClaimDetailSchema tLLClaimDetailSchema=new LLClaimDetailSchema();
	LCPremSchema tLCPremSchema=new LCPremSchema();
	LLExemptSchema tLLExemptSchema=new LLExemptSchema();
	
	//if(transact.equals("LLExempt||Get"))  //[��ȡ������Ϣ]
	//{
	    tLLClaimDetailSchema.setClmNo(request.getParameter("ClmNo")); //�����ⰸ��
	//}
	
	if(transact.equals("LLExempt||Save"))  //[�޸ı��������Ϣ]
	{
	    tLLExemptSchema.setClmNo(request.getParameter("ClmNo")); //�����ⰸ��<����>
	    tLLExemptSchema.setPolNo(request.getParameter("PolNo")); //�������ֺ���<����>
	    tLLExemptSchema.setDutyCode(request.getParameter("DutyCode")); //���α���<����>
	    tLLExemptSchema.setPayPlanCode(request.getParameter("PayPlanCode")); //���Ѽƻ�����<����>
		tLLExemptSchema.setFreeFlag(request.getParameter("FreeFlag")); //�⽻��־
		tLLExemptSchema.setFreeRate(request.getParameter("FreeRate")); //�⽻����
		tLLExemptSchema.setFreeStartDate(request.getParameter("FreeStartDate")); //�⽻����
		tLLExemptSchema.setFreeEndDate(request.getParameter("FreeEndDate")); //�⽻ֹ��
		tLLExemptSchema.setExemptReason(request.getParameter("ExemptReason")); //����ԭ��
        tLLExemptSchema.setExemptDesc(request.getParameter("ExemptDesc")); //��������
		
	}
  
    
    try
    {    
        // ׼���������� VData
		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(transact);
		tVData.add(transact);
		 TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ClmNo",request.getParameter("ClmNo"));
    tVData.add(tTransferData);
		tVData.add(tLLExemptSchema);
		tVData.add(tLLClaimDetailSchema);
//    LLClaimExemptUI tLLClaimExemptUI = new LLClaimExemptUI();        
//    tLLClaimExemptUI.submitData(tVData,transact);
//
//    
//    int n = tLLClaimExemptUI.mErrors.getErrorCount();
//    for (int i = 0; i < n; i++)
//    {
//       Content = Content + "��ʾ��Ϣ: " + tLLClaimExemptUI.mErrors.getError(i).errorMessage;
//    }
//    loggerDebug("LLClaimExemptSave","���ⷵ�ر�־--"+n);
//    
//    if (n == 0 )
//    {
//            Content = " ����ɹ�! ";
//            FlagStr = "SUCC";
//    }
		String busiName="LLClaimExemptUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		    {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
		        	int n = tBusinessDelegate.getCErrors().getErrorCount();
			        for (int i = 0; i < n; i++)
			        {
			            //loggerDebug("LLClaimExemptSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			            Content =Content + "��ʾ��Ϣ: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
			        }
		       		FlagStr = "Fail";				   
				}
				else
				{
				   Content = "����ʧ��";
				   FlagStr = "Fail";				
				} 
		}
		else{
		           Content = " ����ɹ�! ";
		           FlagStr = "SUCC";
		}

  
     
    }catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }
    
  
%>                     
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
