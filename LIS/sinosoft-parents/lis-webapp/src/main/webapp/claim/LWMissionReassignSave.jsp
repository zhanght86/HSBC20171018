<%
//**************************************************************************************************
//ҳ������: LWMissionReassignSave.jsp
//ҳ�湦�ܣ��������·���
//������: yuejw    �������ڣ�2005-7-14   
//�޸����ڣ�  �޸�ԭ��/���ݣ�
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//�������
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
//LWMissionReassignUI tLWMissionReassignUI=new LWMissionReassignUI();
String busiName="LWMissionReassignUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
    loggerDebug("LWMissionReassignSave","ҳ��ʧЧ,�����µ�½");    
}
else //ҳ����Ч
{
	String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    loggerDebug("LWMissionReassignSave","-----transact= "+transact);
    String tChk[] = request.getParameterValues("InpLWMissionGridChk");
    //***************************************
    //��ȡҳ����Ϣ 
    //***************************************  
	String tMissionID[] = request.getParameterValues("LWMissionGrid9");
	String tSubMissionID[] = request.getParameterValues("LWMissionGrid10");
    String tActivityID[] = request.getParameterValues("LWMissionGrid11");
    int tcontCount = tChk.length;
    for(int i = 0; i < tcontCount; i++){
    	if(tChk[i].equals("1")){
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			tLWMissionSchema.setMissionID(tMissionID[i]);
			tLWMissionSchema.setSubMissionID(tSubMissionID[i]);
			tLWMissionSchema.setActivityID(tActivityID[i]);
			tLWMissionSchema.setDefaultOperator(request.getParameter("DesignateOperator"));
			try
			{
				VData tVData = new VData();
		        tVData.add(tGI);
		        tVData.add(tLWMissionSchema);
//		       if(!tLWMissionReassignUI.submitData(tVData,transact))
//		        {
//		            Content = "�ύʧ�ܣ�ԭ����: " +
//		                      tLWMissionReassignUI.mErrors.getError(0).errorMessage;
//		            FlagStr = "Fail";
//		        }
				if(!tBusinessDelegate.submitData(tVData,transact,busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "�ύʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "�ύʧ��";
						FlagStr = "Fail";				
					}
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
	}
}
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
