<%
//�������ƣ�LLParaPupilAmntSave.jsp
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
LLParaPupilAmntUI tLLParaPupilAmntUI = new LLParaPupilAmntUI(); 

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLParaPupilAmntSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LLParaPupilAmntSave","-----transact= "+transact);	   
    //##########��ȡҳ����Ϣ########### 
    LLParaPupilAmntSchema tLLParaPupilAmntSchema = new LLParaPupilAmntSchema();
	//׼����̨����

	tLLParaPupilAmntSchema.setComCode(request.getParameter("ComCode")); //�����������
	tLLParaPupilAmntSchema.setComCodeName(request.getParameter("ComCodeName")); //�����������
	//tLLParaPupilAmntSchema.setUpComCode(request.getParameter("UpComCode")); //�ϼ�����
	tLLParaPupilAmntSchema.setUpComCode("86"); //�ϼ�����
	tLLParaPupilAmntSchema.setBaseValue(request.getParameter("BaseValue")); //�����׼
	tLLParaPupilAmntSchema.setStartDate(request.getParameter("StartDate")); //��������
	tLLParaPupilAmntSchema.setEndDate(request.getParameter("EndDate")); //��������

    try
    {
        //׼���������� VData
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(transact);
        tVData.add(tLLParaPupilAmntSchema);    	
		if(!tLLParaPupilAmntUI.submitData(tVData,transact))
		{           
			Content = "�ύʧ�ܣ�ԭ����: " + tLLParaPupilAmntUI.mErrors.getError(0).errorMessage;
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
    loggerDebug("LLParaPupilAmntSave","------LLParaPupilAmntSave.jsp end------");
}
  
%>                     
<html>
<script language="javascript">
parent.fraInterface.afterHospitalSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
