<%--
    ��������ID���飬����������� 2011-11-28 HuangLiang
--%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<%
loggerDebug("TaskMonitorSave","##########TaskMonitor----START----#########");
%>
<script language="javascript">
var content = new Array();
<%
	String FlagStr="";      //�������
	String Content = "";    //����̨��Ϣ
	String tAction = "";    //�������ͣ�delete update insert
	String tOperate = "";   //��������
	String busiName = "";	//ҵ�����

	VData tData = new VData();
	GlobalInput tG = new GlobalInput();

	FlagStr = "Succ";
	busiName = "TaskMonitorUI";
	tAction = "action";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();

	tG=(GlobalInput)session.getValue("GI");
	String[] tArrayStr = request.getParameter("TaskMonitorCode").split(",");
	VData tVData = new VData();
	for(int i=0;i<tArrayStr.length;i++)
	{
		tVData.add(tArrayStr[i]);
	}
	// ׼���������� VData
	tData.add( tG );
	tData.add( tVData );

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
	}
	//��������������VData
	String tContent = "";
	tVData = tBusinessDelegate.getResult();

	tData = (VData) tVData.get(0);
	try{
	for (int i = 0; i < tData.size(); i++)
	{
		tContent = tData.get(i).toString();
%>
		content[<%=i%>]="<%=tContent%>";
<%
	}
	}catch(Exception ex){
		FlagStr = "Fail";
		ex.printStackTrace();
	}
	loggerDebug("TaskMonitorSave",tData.toString());
	loggerDebug("TaskMonitorSave","########TaskMonitor----END----#########");
%>
	parent.fraInterface.afterMonitor("<%=FlagStr%>",content);
</script>
</html>
