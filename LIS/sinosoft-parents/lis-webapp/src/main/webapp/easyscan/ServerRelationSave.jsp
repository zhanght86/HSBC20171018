<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.easyscan.*"%>
  <%@page import="java.sql.*"%>
  
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//������Ϣ������У�鴦��
  loggerDebug("ServerRelationSave","--mulLineSave.jsp-- begin save");
  ServerRelationUI tServerRelationUI   = new ServerRelationUI();

  //�������
  ES_COM_SERVERSchema tES_Com_ServerSchema   = new ES_COM_SERVERSchema();
  ES_COM_SERVERSet tES_Com_ServerSet   = new ES_COM_SERVERSet();

  //�������
  CErrors tError = null;
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";
  int checked = 0;
  String pkWherePart = "";

  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  transact = request.getParameter("fmtransact");
  loggerDebug("ServerRelationSave","--mulLineSave.jsp--transact:"+transact);

  tES_Com_ServerSchema.setManageCom(request.getParameter("ManageCom"));
  tES_Com_ServerSchema.setHost_Name(request.getParameter("Host_Name"));

  loggerDebug("ServerRelationSave","--mulLineSave.jsp--10");
  
  String tGridNo[] = request.getParameterValues("CodeGridNo");  //�õ�����е�����ֵ
  String tChk[] = request.getParameterValues("InpCodeGridChk");    //������ʽ="MulLine������+Chk"
  String tGrid1  [] = request.getParameterValues("CodeGrid1");  //�õ���1�е�����ֵ
  String tGrid2  [] = request.getParameterValues("CodeGrid2");  //�õ���2�е�����ֵ
  
  int count = tChk.length; //�õ����ܵ��ļ�¼��
  
  loggerDebug("ServerRelationSave","--mulLineSave.jsp--20:" + count + ":" + tGridNo.length);
  
  
  for(int index=0; index< count; index++)
  {
	loggerDebug("ServerRelationSave","--mulLineSave.jsp--30  "+ count);
	if(tChk[index].equals("1"))
	{
		checked = index;
		loggerDebug("ServerRelationSave","--mulLineSave.jsp--31  " + tGrid1[index] + checked);
		//ѡ�е���
		ES_COM_SERVERSchema tServerSchema = new ES_COM_SERVERSchema();
		tServerSchema.setManageCom(tGrid1[index]);
		tServerSchema.setHost_Name(tGrid2[index]);
		loggerDebug("ServerRelationSave","--mulLineSave.jsp--32  " + tGrid1[index]);
		tES_Com_ServerSet.add(tServerSchema);

	}
  }


	loggerDebug("ServerRelationSave","--mulLineSave.jsp--40");
  try
  {
    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(tES_Com_ServerSchema);
    tVData.add(tES_Com_ServerSet);

    loggerDebug("ServerRelationSave","--ManageSave.jsp--before submitData");
    tServerRelationUI.submitData(tVData,transact);
    loggerDebug("ServerRelationSave","--ManageSave.jsp--after  submitData");
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tServerRelationUI.mErrors;
    if (!tError.needDealError())
    {
      Content = " ����ɹ�! ";
      FlagStr = "Success";
    }
    else
    {
      Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }

  //��Ӹ���Ԥ����

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
