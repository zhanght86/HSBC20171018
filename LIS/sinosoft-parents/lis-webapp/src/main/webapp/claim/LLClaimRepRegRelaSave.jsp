<%
//**************************************************************************************************
//Name��LLRegisterSave.jsp
//Function��������Ϣ�ύ
//Author��zhoulei
//Date: 2005-6-15 16:28
//�޸ģ�niuzj,2006-01-13,�����Ǽ�ʱ��Ҫ��LLRegister���е���ȡ��ʽGetMode�ֶ�Ĭ���ó�1
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
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
loggerDebug("LLClaimRepRegRelaSave","######################LLClaimRegisterSave.jsp start#############################");

//�������
LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //������

//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");


if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LLClaimRepRegRelaSave","ҳ��ʧЧ,�����µ�½");
}
else //ҳ����Ч
{

	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

    tLLRegisterSchema.setRgtNo(request.getParameter("ClmNo")); // ������
    tLLRegisterSchema.setRgtObjNo(request.getParameter("RptNo")); // ������
    tLLRegisterSchema.setRgtObj("1"); //����������

    
    //׼���������� VData
    VData tVData = new VData();
    tVData.add(tGI);
    tVData.add(tLLRegisterSchema);


    //��һ�α����ύʱ���ύ�����������棬������ֱ���ύ��ҵ����,���ύ������ʱwFlag=9899999999

        try
        {
        	String transact = "INSERT";
            //�����ύ
            loggerDebug("LLClaimRepRegRelaSave","---LLClaimRepRegRelaBL submitData and transact="+transact);
//            LLClaimRepRegRelaUI tLLClaimRepRegRelaUI = new LLClaimRepRegRelaUI();
//            
//            if (!tLLClaimRepRegRelaUI.submitData(tVData,transact))
//            {
//                Content = " ����ʧ�ܣ�ԭ����: " + tLLClaimRepRegRelaUI.mErrors.getError(0).errorMessage;
//                FlagStr = "Fail";
//            }
		String busiName="LLClaimRepRegRelaUI";
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		   if(!tBusinessDelegate.submitData(tVData,transact,busiName))
		   {    
		        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		        { 
						   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						   FlagStr = "Fail";
				}
				else
				{
						   Content = "����ʧ��";
						   FlagStr = "Fail";				
				}
		   }

            else
            {
                tVData.clear();
                Content = " �����ύ�ɹ���";
                FlagStr = "Succ";
            }
        }
        catch(Exception ex)
        {
            Content = "�����ύLLClaimRegisterUIʧ�ܣ�ԭ����:" + ex.toString();
            FlagStr = "Fail";
        }


}

%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit5("<%=FlagStr%>","<%=Content%>");
</script>
</html>
