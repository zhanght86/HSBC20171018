<%@ page contentType='text/html;charset=GBK'%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<%
/**************************************************************
 *               Program NAME: ��ȡ��̨����
 *                 programmer: Ouyangsheng
 *                Create DATE: 2002.04.15
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *         ͨ�ô����ѯ����ҳ��,���������صĿ����,����
 *     ������Ҫ��ʾ�����嵥ʱ���ô�ҳ��
 *
 *****************************************************************/
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LDCodeSchema"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.Date"%>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
//log.debug("\n\n\n---Start InitIndex CExec.jsp---\n\n");
String codename             = "";	// ��������lx_code
String riskcode             = "";	// ���ִ���
String framename            = "";	// Frame������
String strOther             = "";	// ���Ŵ���
String strValue             = "";
String strError             = "";	// ������Ϣ
int    intSize              = -1;	// ��¼������Ϣ -1--null, 0--����0, 1--����0
String tStr                 = "";
String codeCondition        = "";	//�����ѯ����
String conditionField       = "";	//�����ѯ������Ӧ�����ݿ��ֶ�
String showWidth            = "";	//������Ŀ��
String changeEven			= "";	//����ѡ��ʱ�������¼�
String searchFlag  = "";//�Ƿ�����ƴ����ѯ���
//�õ�����ҳ��ķ�ʽ,��ȷ���ǳ�������ҳ��,����ͨ��ҳ���ѯ����
String strMethod = request.getMethod();
boolean error = false;
//�����POST��ʽ����ҳ��,˵����Ҫͨ��ҳ���ѯ����,ִ�����²�ѯ����,����ֱ�ӷ���Ĭ��ҳ��
/* ��POST��ʽ���� */
if(strMethod.equals("POST"))
{
	String hashSession = (String) session.getAttribute("HASH");
	String hash = request.getParameter("hash");
	GlobalInput gi = null;
	try {
		gi = (GlobalInput)session.getValue("GI");
	}
	catch(Exception ex) { }
	if(gi == null || gi.Operator == null || "".equals(gi.Operator)) {
		try {
			error = !hash.equals(hashSession);//����½������hashһ����
		}catch(Exception e)
		{
			error = true;
		}
		//�����¼�����ʹ��EASYQUERY����
		if(request.getParameter("mOperate").equals("EASYQUERY")){
			error = true;
		}
		else {
			//����½Ӧ��ֻ����ҳ�������Ϣ
			if(!"comcode".equals(request.getParameter("txtCodeName"))) {
				error = true;
			}
		}
	}
	
	if(error){
		out.println("<script language=javascript>"); 
		out.println("  session = null;");
		out.println("  try {");
		out.println("    CollectGarbage();");
		out.println("  } catch(ex) {}");
		out.println("  parent.window.location =\"../../indexlis.jsp\";");
		out.println("</script>");
		session.invalidate();
		return;
	} 
	
	if(!error && !request.getParameter( "mOperate" ).equals( "EASYQUERY" ))
	{
		//��ô����ѯ����,������������,������Ϣ��
		codename          = request.getParameter("txtCodeName");
		tStr=codename.toLowerCase();
		framename         = request.getParameter("txtFrameName");
		strOther          = request.getParameter("txtOther");
		codename          = codename.trim();
		framename         = framename.trim();
		strOther          = strOther.trim();
		codeCondition     = request.getParameter("txtCodeCondition");
		conditionField    = request.getParameter("txtConditionField");
		showWidth         = request.getParameter("txtShowWidth");
		changeEven		  = request.getParameter("changeEven");
		searchFlag  = request.getParameter("searchFlag");
		VData tData=new VData();
		LDCodeSchema tLDCodeSchema =new LDCodeSchema();
		tLDCodeSchema.setCodeType(tStr);

		GlobalInput tGI = new GlobalInput();
		try
		{
			tGI = (GlobalInput)session.getValue("GI");
		}
		catch(Exception ex)
		{
			tGI = new GlobalInput();
			loggerDebug("CExec","GlobalInput is null");
		}

		tData.add(tLDCodeSchema);
		tData.add(tGI);

		//���ݲ�ѯ����
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("codeCondition", codeCondition);
		tTransferData.setNameAndValue("conditionField", conditionField);
		tData.add(tTransferData);

   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   if(!tBusinessDelegate.submitData(tData,"QUERY||MAIN","CodeQueryUI"))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   String Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
			     strValue="Code Query Faile";
				}
   }
	else
	{
			tData.clear() ;
			tData=tBusinessDelegate.getResult() ;
			strValue=(String)tData.get(0);
	}
	
	
	}
}

String a = ("HS"+String.valueOf(System.currentTimeMillis()));
session.setAttribute("HASH", a);

//������ҳ��ķ�ʽΪPOSTʱ�Ĵ����ѯ����ִ�����
//strValue="0|^001|�ܹ�˾|�ܹ�˾������Ϣ^002|�����ֹ�˾|�����ֹ�˾������Ϣ";
//��ѯ�����󣬽���ѯ�������input�򴫻ص��ͻ��ˣ������ػ�ҳ�棬ʹ��ָ�����ʼ״̬
%>
<body>
<form name='fm' action='CExec.jsp?random=<%=new Date().getTime()%>' method='POST'>
	<input type='hidden' name='txtVarData' value='<%=strValue%>'>
	<input type='hidden' name='txtCodeName' value=''>
	<input type='hidden' name='txtOther'>
	<input type='hidden' name='txtFrameName'>
	<input type='hidden' name='txtSQL'>
	<input type='hidden' name='startIndex'>
	<input type='hidden' name='txtQueryResult'>
	<input type='hidden' name='mOperate'>
	<input type='hidden' name='txtCodeCondition'>
	<input type='hidden' name='txtConditionField'>
	<input type='hidden' name='txtShowWidth'>
	<input type='hidden' name='changeEven'>
	<input type='hidden' name='searchFlag'>
	<input type='hidden' name='hash' value='<%=a%>'>
</form>
</body>
<%
//ִ�д����嵥��ʾ��ѡ��Ŀͻ���JavaScript����
if(strMethod.equals("POST") && !error)
{
	if( !request.getParameter( "mOperate" ).equals( "EASYQUERY" ))
	{
		out.println("<script language=javascript>");
		if(!strError.equals(""))
		{
			out.println("alert('"+strError+"');");
			out.println("</script>");
			return;
		}
		out.println("try{");
		out.println("  parent."+framename+".initializeCode('"+codename+"', '" + codeCondition + "' , '" + conditionField + "','"+ searchFlag +"');");
		out.println("}catch(exception){}");
		String tempStr="";
		out.println("try{");
		out.println("  parent."+framename+".showCodeList('"+codename+"',null,null,null, '" + codeCondition + "' , '" + conditionField + "' , null, '" + showWidth + "',"+changeEven+","+searchFlag+");");
		out.println("}catch(exception){}");
		out.println("</script>");
	}
	else
	{
		// easyQuery����
		String tSQL = request.getParameter( "txtSQL" );
		String tStart = request.getParameter( "startIndex" );
		Integer tIntStart = new Integer( String.valueOf( tStart ));
		//String tFrameName = request.getParameter( "txtFrameName" );
		String tResult = "";
		String tError = "";
		loggerDebug("CExec",tSQL);
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();		
		VData tVData = new VData();
		tVData.add( tSQL );
		tVData.add( tIntStart );

		tBusinessDelegate.submitData(tVData,"QUERY||MAIN","EasyQueryUI");
		if( tBusinessDelegate.getCErrors().needDealError())
		{

			tError = tBusinessDelegate.getCErrors().getFirstError();
		}
		else
		{

			tVData.clear() ;
			tVData = tBusinessDelegate.getResult() ;
			tResult = ( String )tVData.getObject( 0 );
%>
<script language="javascript">
fm.all('txtQueryResult').value = '<%= tResult %>';
</script>
<%
		}

		out.println("<script language=javascript>");
		out.println("try{");
		out.println("  top.fraInterface.afterEasyQuery('" + tError + "');");
		out.println("}catch(exception){}");
		out.println("</script>");
	}
}
%>
<SCRIPT language="JavaScript1.2">
window.status="finished";
try
{
	top.achieveEX = true;	//�����ж�ҳ���ʼ�����
}
catch(ex)
{}
</SCRIPT>
</html>
