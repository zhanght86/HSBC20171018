<%
//**************************************************************************************************
//页面名称: LWMissionReassignSave.jsp
//页面功能：任务重新分配
//建立人: yuejw    建立日期：2005-7-14   
//修改日期：  修改原因/内容：
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.db.LWMissionDB"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%
//输出参数
CErrors tError = null;
String FlagStr = "Fail";
String Content = "";
GlobalInput tGI = new GlobalInput(); 
tGI=(GlobalInput)session.getValue("GI"); 
LWMissionReassignUI tLWMissionReassignUI=new LWMissionReassignUI();

if(tGI == null)
{
    FlagStr = "Fail";
    Content = "页面失效,请重新登陆";
    System.out.println("页面失效,请重新登陆");    
}
else //页面有效
{
	String Operator  = tGI.Operator ; //保存登陆管理员账号
    String ManageCom = tGI.ManageCom  ; //保存登陆区站,ManageCom=内存中登陆区站代码
    String transact = request.getParameter("fmtransact"); //获取操作insert||update
    System.out.println("-----transact= "+transact);
    //***************************************
    //获取页面信息 
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
            Content = "提交失败，原因是: " +
                      tLWMissionReassignUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }
        else
        {
            Content = "数据提交成功";
            FlagStr = "Succ";
        }
	}
	catch(Exception ex)
    {
        Content = "数据提交失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
}	
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>