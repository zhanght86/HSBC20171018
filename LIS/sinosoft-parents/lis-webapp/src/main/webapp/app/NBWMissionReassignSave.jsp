<%
//ҳ������: NBWMissionReassignSave.jsp
//ҳ�湦�ܣ�����Լ�������·���
//������: chenrong    �������ڣ�2006-2-20   
//�޸����ڣ�  �޸�ԭ��/���ݣ�
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
CErrors tError = null;
String Content = "";
String FlagStr = "Fail";
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
//NBWMissionReassignUI tNBWMissionReassignUI = new NBWMissionReassignUI();     
String busiName="cbcheckNBWMissionReassignUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(tGI == null)
{
    FlagStr = "Fail";
    Content = "ҳ��ʧЧ,�����µ�½";
}
else //ҳ����Ч
{
    String Operator  = tGI.Operator ; //�����½����Ա�˺�
    String ManageCom = tGI.ManageCom  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����
    String transact = request.getParameter("fmtransact"); //��ȡ����insert||update
    
    String tChk[] = request.getParameterValues("InpAllPolGridChk");
    boolean flag = false;
    
    String MissionID[] = request.getParameterValues("AllPolGrid8");
    String SubMissionID[] = request.getParameterValues("AllPolGrid9");
    String ActivityID[] = request.getParameterValues("AllPolGrid10");
    String DesignateOperator = request.getParameter("DesignateOperator");    
    
	  int tcontCount = tChk.length;	
    

    loggerDebug("NBWMissionReassignSave","-------------------------------- new contact mission designate-----��ʼ--------------------------------");
        
	for (int i = 0; i < tcontCount; i++)
	{
        loggerDebug("NBWMissionReassignSave","--------------------------------------------------------------------------");
    
    LWMissionSchema tLWMissionSchema = new LWMissionSchema();
    if (tChk[i].equals("1"))
		{
    tLWMissionSchema.setMissionID(MissionID[i]);
    tLWMissionSchema.setSubMissionID(SubMissionID[i]);
    tLWMissionSchema.setActivityID(ActivityID[i]);
	  tLWMissionSchema.setDefaultOperator(DesignateOperator);
	
	   flag = true;
		}
        
	  try
	  {
	    loggerDebug("NBWMissionReassignSave","flag=="+flag);
		  	if (flag == true && tChk[i].equals("1") )
		  	{
	
			    VData tVdata = new VData();
			    tVdata.add(tGI);
			    tVdata.add(tLWMissionSchema);
			    if(!tBusinessDelegate.submitData(tVdata,transact,busiName))
		        {
		            Content = "�ύʧ�ܣ�ԭ����: " +
		            tBusinessDelegate.getCErrors().getError(0).errorMessage;
		            FlagStr = "Fail";
		        }
		        else
		        {
		            Content = "�����ύ�ɹ�";
		            FlagStr = "Succ";
		        }
		    }
		} //try end		
	  catch(Exception ex)
	  {
	    FlagStr = "Fail";
	    Content = "�����ύʧ�ܣ�ԭ����:" + ex.toString();
	  }
    loggerDebug("NBWMissionReassignSave","--------------------------------------------------------------------------");
	} //for end
    loggerDebug("NBWMissionReassignSave","----------------------------------------����--------------------------------");
 } //else end 
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
