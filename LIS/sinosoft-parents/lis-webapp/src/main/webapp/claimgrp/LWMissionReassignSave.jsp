<%
//**************************************************************************************************
//ҳ������: LWMissionReassignSave.jsp
//ҳ�湦�ܣ��������·���
//������: yuejw    �������ڣ�2005-7-14   
//�޸����ڣ�  �޸�ԭ��/���ݣ�
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
LWMissionReassignUI tLWMissionReassignUI=new LWMissionReassignUI();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    System.out.println("ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    System.out.println("-----transact= "+transact);
    //***************************************
    //��ȡҳ����Ϣ 
    //***************************************  
	LWMissionSchema tLWMissionSchema = new LWMissionSchema();
	tLWMissionSchema.setMissionID(request.getParameter("MissionID"));
	tLWMissionSchema.setSubMissionID(request.getParameter("SubMissionID"));
	tLWMissionSchema.setActivityID(request.getParameter("ActivityID"));
	tLWMissionSchema.setDefaultOperator(request.getParameter("DesignateOperator"));
	try
	{
		VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(tLWMissionSchema);
       if(!tLWMissionReassignUI.submitData(tVData,transact))
        {
            Content = "�ύʧ�ܣ�ԭ����: " +
                      tLWMissionReassignUI.mErrors.getError(0).errorMessage;
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
}	
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>