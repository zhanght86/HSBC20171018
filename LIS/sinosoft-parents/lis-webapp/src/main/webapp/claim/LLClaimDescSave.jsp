<%
//Name��LLClaimDescSave.jsp
//Function���¹������ύ
//Author��zhoulei
//Date:
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
//************************
//������Ϣ������У�鴦��
//************************

//�������
LLClaimDescSchema tLLClaimDescSchema = new LLClaimDescSchema(); //�¹�����

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//LLClaimDescUI tLLClaimDescUI = new LLClaimDescUI();
String busiName="LLClaimDescUI";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimDescSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LLClaimDescSave","-----transact= "+transact);
    
    //***************************************
    //��ȡҳ����Ϣ 
    //***************************************
    tLLClaimDescSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
//    tLLClaimDescSchema.setInqNo(request.getParameter("RptorName")); //�¹��������
    tLLClaimDescSchema.setCustomerNo(request.getParameter("WhoNo")); //�����˿ͻ���
    tLLClaimDescSchema.setStartPhase(request.getParameter("StartPhase")); //����׶�    
//    tLLClaimDescSchema.setDescType(request.getParameter("DescType")); //�¹���������
    tLLClaimDescSchema.setDescribe(request.getParameter("Describe")); //�¹���������
            
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(tLLClaimDescSchema);

        //���ݴ���
//         if (!tLLClaimDescUI.submitData(tVData,transact))
//	      {
//            Content = " �����ύLLClaimDescUIʧ�ܣ�ԭ����: " + tLLClaimDescUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//	      }
		 if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "�����ύLLClaimDescUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "�����ύLLClaimDescUIʧ��";
						   FlagStr = "Fail";				
				}
		   }


	      else
	      {
	    	tVData.clear();
	    	//tVData = tLLClaimDescUI.getResult();
	    	tVData = tBusinessDelegate.getResult();
//            LLClaimDescSet yLLClaimDescSet = new LLClaimDescSet();
//            yLLClaimDescSet.set((LLClaimDescSet)tVData.getObjectByObjectName("LLClaimDescSet",0));
//        int n = yLLClaimDescSet.size();
//        loggerDebug("LLClaimDescSave","get report "+n);
//    		String Strtest ="0|" + n + "^"+yLLClaimDescSet.encode();
//    		loggerDebug("LLClaimDescSave","Strtest==="+Strtest);
        }
    }
    catch(Exception ex)
    {
        Content = "�����ύLLClaimDescUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
        //tError = tLLClaimDescUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        if (!tError.needDealError())
        {
          	Content = " �����ύ�ɹ�! ";
    	      FlagStr = "Succ";
        }
        else
        {
      	    Content = " �����ύLLClaimDescUIʧ�ܣ�ԭ����:" + tError.getFirstError();
    	      FlagStr = "Fail";
        }
    }    
    loggerDebug("LLClaimDescSave","------LLClaimDescSave.jsp end------");
    loggerDebug("LLClaimDescSave",FlagStr);
    loggerDebug("LLClaimDescSave",Content);
    
} //end of else,ҳ����Ч��
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
