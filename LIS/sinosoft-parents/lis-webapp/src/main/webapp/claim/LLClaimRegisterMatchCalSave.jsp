<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLMedicalFeeInpSave.jsp
//�����ܣ���������ƥ�䱣��
//�������ڣ�2005-05-19 11:10:36
//������  ������
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
    String tOperate = request.getParameter("hideOperate");    
    if(tG == null) 
    {
        loggerDebug("LLClaimRegisterMatchCalSave","��¼��Ϣû�л�ȡ!!!");
        return;
     } else if (tOperate == null || tOperate == "") {
        loggerDebug("LLClaimRegisterMatchCalSave","û�л�ȡ������!!!");
        return;   
    }
    
    
    
    //׼������������Ϣ
    String tAccNo = request.getParameter("AccNo");
    String tClmNo = request.getParameter("RptNo");
    String tAccDate = request.getParameter("AccidentDate");
    String tCusNo = request.getParameter("WhoNo");
    
    String tContType = request.getParameter("RgtClass");
    String tClmState = request.getParameter("clmState");
             
    TransferData tTransferData = new TransferData();    
    tTransferData.setNameAndValue("AccNo",tAccNo);
    tTransferData.setNameAndValue("ClmNo",tClmNo);
    tTransferData.setNameAndValue("AccDate",tAccDate);    
    tTransferData.setNameAndValue("ContType",tContType);   //�ܵ�����,1-������Ͷ����,2-�����ܵ�        
    tTransferData.setNameAndValue("ClmState",tClmState);   //�ⰸ״̬��20������30���
        
        
    loggerDebug("LLClaimRegisterMatchCalSave","LLClaimRegisterMatchCalSave.jsp����--"+tClmNo);
    
    LLCaseSchema tLLCaseSchema = new LLCaseSchema(); //��������
    
    tLLCaseSchema.setCustomerNo(request.getParameter("WhoNo")); //�����˱��� 
    tLLCaseSchema.setCustomerAge(request.getParameter("WhoAge")); //����������
    tLLCaseSchema.setCustomerSex(request.getParameter("WhoSex")); //�������Ա�
    tLLCaseSchema.setAccidentDate(request.getParameter("AccidentDate2")); //��������   
    tLLCaseSchema.setAccidentDetail(request.getParameter("WhoDesc")); //����ϸ��
    tLLCaseSchema.setDieFlag(request.getParameter("IsDead")); //������־  
    tLLCaseSchema.setCureDesc(request.getParameter("WhoTreatDesc")); //�������
    
    
    //loggerDebug("LLClaimRegisterMatchCalSave","LLMedicalFeeInpSave.jsp����--"+tOperate);
  
    
    try
    {    
        // ׼���������� VData
        VData tVData = new VData();
        
        tVData.add( tG );        
        tVData.add( tTransferData );        
        tVData.add( tLLCaseSchema );


       //LLClaimCalPortalBL tLLClaimCalPortalBL = new LLClaimCalPortalBL();
       //boolean tbl = tLLClaimCalPortalBL.submitData(tVData,tOperate);
	  
       //int n = tLLClaimCalPortalBL.mErrors.getErrorCount();
       
       //for (int i = 0; i < n; i++)
      //  {
      //      Content = Content + "��ʾ��Ϣ: " + tLLClaimCalPortalBL.mErrors.getError(i).errorMessage;
      //      FlagStr = "FAIL";

      //  }
                     
     //  if (n == 0 )
     //   {
     //       Content = " ����ɹ�! ";
     //       FlagStr = "SUCC";
     //   }
    
       String busiName="LLClaimCalPortalBL";
       BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
       if(!tBusinessDelegate.submitData(tVData,tOperate,busiName))
	   {    
	        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	          { 
	        	int n = tBusinessDelegate.getCErrors().getErrorCount();
		        for (int i = 0; i < n; i++)
		        {
		            //loggerDebug("LLClaimRegisterMatchCalSave","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
		            Content = Content + "��ʾ��Ϣ: " +tBusinessDelegate.getCErrors().getError(i).errorMessage;
		        }
	       		FlagStr = "Fail";				   
			}

			else
			{
			   Content = "����ʧ��";
			   FlagStr = "Fail";				
			}
	   }else
	   {
	   		Content = " ����ɹ�! ";
            FlagStr = "SUCC";
	   }
        //loggerDebug("LLClaimRegisterMatchCalSave","LLMedicalFeeInpSave����--"+FlagStr);
        
    }
    catch(Exception e)
    {
        e.printStackTrace();
        Content = Content.trim()+".��ʾ���쳣��ֹ!";
    }
    
    //loggerDebug("LLClaimRegisterMatchCalSave",FlagStr+"try����--"+Content);
  
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterMatchDutyPay("<%=FlagStr%>","<%=Content%>");
</script>
</html>
