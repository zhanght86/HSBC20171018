<%
//页面名称: NBWMissionReassignSave.jsp
//页面功能：新契约任务重新分配
//建立人: chenrong    建立日期：2006-2-20   
//修改日期：  修改原因/内容：
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
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
    Content = "页面失效,请重新登陆";
}
else //页面有效
{
    String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    
    String tChk[] = request.getParameterValues("InpAllPolGridChk");
    boolean flag = false;
    
    String MissionID[] = request.getParameterValues("AllPolGrid8");
    String SubMissionID[] = request.getParameterValues("AllPolGrid9");
    String ActivityID[] = request.getParameterValues("AllPolGrid10");
    String DesignateOperator = request.getParameter("DesignateOperator");    
    
	  int tcontCount = tChk.length;	
    

    loggerDebug("NBWMissionReassignSave","-------------------------------- new contact mission designate-----开始--------------------------------");
        
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
		            Content = "提交失败，原因是: " +
		            tBusinessDelegate.getCErrors().getError(0).errorMessage;
		            FlagStr = "Fail";
		        }
		        else
		        {
		            Content = "数据提交成功";
		            FlagStr = "Succ";
		        }
		    }
		} //try end		
	  catch(Exception ex)
	  {
	    FlagStr = "Fail";
	    Content = "数据提交失败，原因是:" + ex.toString();
	  }
    loggerDebug("NBWMissionReassignSave","--------------------------------------------------------------------------");
	} //for end
    loggerDebug("NBWMissionReassignSave","----------------------------------------结束--------------------------------");
 } //else end 
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
