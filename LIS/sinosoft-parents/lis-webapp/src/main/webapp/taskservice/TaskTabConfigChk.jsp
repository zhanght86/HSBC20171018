<%--
    ���漯�屣����Ϣ 2004-11-16 wzw
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.taskservice.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.net.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%	         
	
	String FlagStr="";      //�������
	String Content = "";    //����̨��Ϣ
	String tAction = "";    //�������ͣ�delete update insert
	String tOperate = "";   //��������

	VData tData = new VData();

	GlobalInput tGI = new GlobalInput();
	tAction = request.getParameter("fmAction");
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

	String tUserCode = request.getParameter("UserCode");
	String tUserName =  request.getParameter("UserName");
	
	
	String busiName = "TaskServiceUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	
	if(tAction.equals("INSERTUSERTAB"))
	{
		LDUSERToTaskTabSchema tLDUSERToTaskTabSchema = new LDUSERToTaskTabSchema();
		LDUSERToTaskTabSet tLDUSERToTaskTabSet = new LDUSERToTaskTabSet();
		
		String tParamValue[] = request.getParameterValues("UserTabDetailGrid1");
		
		int n = tParamValue.length;
		for (int i = 0; i < n; i++)
		{ 
			//�жϲ�����Ϣ�Ƿ�Ϊ�գ�ʡȥɾ�����п��еı�Ҫ
			if (tParamValue[i] != null && !tParamValue[i].equals(""))
			{   
				tLDUSERToTaskTabSchema = new LDUSERToTaskTabSchema();
				tLDUSERToTaskTabSchema.setTaskTabID(tParamValue[i]);
				tLDUSERToTaskTabSchema.setUserID(tUserCode);
				
				
				tLDUSERToTaskTabSet.add(tLDUSERToTaskTabSchema);
			}
		}
		
		tData.add( tG );
		tData.add(tUserCode);
		tData.add(tLDUSERToTaskTabSet);
	}
	if(tAction.equals("DELETEUSERTAB"))
	{
		
		 // ׼���������� VData
		tData.add( tG );
		tData.add(tUserCode);
	}
	
	//TaskService tTaskService = new TaskService();
	//if( tTaskService.submitData( tData, tAction ) < 0 )
	if (!tBusinessDelegate.submitData(tData, tAction,busiName))
	{
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getFirstError();
		FlagStr = "Fail";
	}
	else
	{
		Content = " �����ɹ�! ";
		FlagStr = "Succ";
		tData.clear();

%>
<%		
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
