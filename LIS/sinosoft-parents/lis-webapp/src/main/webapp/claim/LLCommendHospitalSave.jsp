<%
//�������ƣ�LLCommendHospitalSave.jsp
//�����ܣ�ҽԺ��Ϣά��
//�������ڣ�2005-7-13
//������  ��yuejw
//���¼�¼��  ������ yuejw    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>

<%
    //׼��ͨ�ò���
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
LLCommendHospitalUI tLLCommendHospitalUI = new LLCommendHospitalUI(); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLCommendHospitalSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LLCommendHospitalSave","-----transact= "+transact);	   
    //##########��ȡҳ����Ϣ########### 
    LLCommendHospitalSchema tLLCommendHospitalSchema = new LLCommendHospitalSchema();
	//׼����̨����
	tLLCommendHospitalSchema.setConsultNo(request.getParameter("ConsultNo")); //��ѯ֪ͨ����==ҽԺ����	
	tLLCommendHospitalSchema.setHospitalCode(request.getParameter("HospitalCode")); //ҽԺ����
	tLLCommendHospitalSchema.setHospitalName(request.getParameter("HospitalName")); //ҽԺ����
	tLLCommendHospitalSchema.setHosAtti(request.getParameter("HosAtti")); //ҽԺ�ȼ�
	tLLCommendHospitalSchema.setConFlag(request.getParameter("ConFlag")); //�����־
	tLLCommendHospitalSchema.setAppFlag(request.getParameter("AppFlag")); //�м��������ʱ�־
	tLLCommendHospitalSchema.setHosState(request.getParameter("HosState")); //ҽԺ״̬

    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(tLLCommendHospitalSchema);    	
		if(!tLLCommendHospitalUI.submitData(tVData,transact))
		{           
			Content = "�ύʧ�ܣ�ԭ����: " + tLLCommendHospitalUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";    
		}
		else
		{	    
			Content = "�����ύ�ɹ�";
			FlagStr = "Succ";            
		}
    }
    catch(Exception ex)
    {
        Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    loggerDebug("LLCommendHospitalSave","------LLCommendHospitalSave.jsp end------");
}
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterHospitalSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
