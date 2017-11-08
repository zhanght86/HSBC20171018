<%
//Name��LLInqFeeSave.jsp
//Function���������¼���ύ
//Author��yuejinwei
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
LLInqFeeSchema tLLInqFeeSchema = new LLInqFeeSchema(); //���������
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI");  
//LLInqFeeUI tLLInqFeeUI = new LLInqFeeUI();
String busiName="LLInqFeeUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLInqFeeSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    loggerDebug("LLInqFeeSave","----ComCode= "+tGI.ComCode);
    loggerDebug("LLInqFeeSave","-----ManageCom= "+ManageCom);

    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LLInqFeeSave","-----transact= "+transact);
    String isReportExist =request.getParameter("isReportExist"); //�Ƿ�Ϊ�����¼�����true����false
    //***************************************
    //��Ҫ����isReportExist�ж��Ƿ�Ϊ�����¼�\�޸��¼�
    //***************************************
    //��ȡ����¼��ҳ����Ϣ
        loggerDebug("LLInqFeeSave","------@@@@@@@@@@@@@@------"+request.getParameter("InqDept"));

    tLLInqFeeSchema.setClmNo(request.getParameter("ClmNo")); //�ⰸ��
    tLLInqFeeSchema.setInqNo(request.getParameter("InqNo")); //�������
    tLLInqFeeSchema.setInqDept(request.getParameter("InqDept")); //�������
    tLLInqFeeSchema.setFeeType(request.getParameter("FeeType")); //��������
    tLLInqFeeSchema.setFeeDate(request.getParameter("FeeDate")); //����ʱ��
    tLLInqFeeSchema.setFeeSum(request.getParameter("FeeSum")); //���
    tLLInqFeeSchema.setPayee(request.getParameter("Payee")); //�����
    tLLInqFeeSchema.setPayeeType(request.getParameter("PayeeType")); //��ʽ
    tLLInqFeeSchema.setRemark(request.getParameter("Remark")); //��ע
      
    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(isReportExist);
        tVData.add(tLLInqFeeSchema);
        //���ݴ���
//	      if (!tLLInqFeeUI.submitData(tVData,transact))
//	      {
//            Content = " �����ύLLInqFeeUIʧ�ܣ�ԭ����: " + tLLInqFeeUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//	      }
		if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				Content = "�����ύLLInqFeeUIʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				Content = "�����ύLLInqFeeUIʧ��";
				FlagStr = "Fail";				
			}
		}

	      else
	      {
	    	tVData.clear();
	    	//tVData = tLLInqFeeUI.getResult();
	    	tVData = tBusinessDelegate.getResult();
        }
    }
    catch(Exception ex)
    {
        Content = "�����ύLLInqFeeUIʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail")
    {
        //tError = tLLInqFeeUI.mErrors;
        tError = tBusinessDelegate.getCErrors();
        if (!tError.needDealError())
        {
          	Content = " �����ύ�ɹ�! ";
    	      FlagStr = "Succ";
        }
        else
        {
      	    Content = " �����ύLLInqFeeUIʧ�ܣ�ԭ����:" + tError.getFirstError();
    	      FlagStr = "Fail";
        }
    }    
    loggerDebug("LLInqFeeSave","------LLInqFeeSave.jsp end------");
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
