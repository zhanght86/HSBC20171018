<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  loggerDebug("GetCredenceF1PSave","start");
  LJAGetSchema tLJAGetSchema = new LJAGetSchema();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
    //�������
    CError cError = new CError( );
    //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
		int index;
    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String strOperation = "";
  String tGrid1[] = request.getParameterValues("PolGrid1");
  String tRadio[] = request.getParameterValues("InpPolGridSel");  
    for(index=0;index<tRadio.length;index++)
    {
      if(tRadio[index].equals("1"))
				break;
    }

  strOperation = request.getParameter("fmtransact");
  tLJAGetSchema.setActuGetNo(tGrid1[index]);
  VData tVData = new VData();
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
      tVData.addElement(tLJAGetSchema);
      tVData.addElement(tG);
      GetCredenceF1PUI tGetCredenceF1PUI = new GetCredenceF1PUI();
  try
  {    
      
      if(!tGetCredenceF1PUI.submitData(tVData,strOperation))
      {
             mErrors.copyAllErrors(tGetCredenceF1PUI.mErrors);
             cError.moduleName = "GetCredenceF1PSave";
             cError.functionName = "submitData";
             cError.errorMessage = "GetCredenceF1PUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
             mErrors.addOneError(cError);
      }
		  mResult = tGetCredenceF1PUI.getResult();
		  String ActuGetNo = (String)mResult.getObjectByObjectName("String",0);
		  if (ActuGetNo==null)
		  {
			  LOPRTManager2Schema tLOPRTManager2Schema = (LOPRTManager2Schema)mResult.getObjectByObjectName("LOPRTManager2Schema",0);
			  XmlExport txmlExport = new XmlExport();
			  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
			  if (txmlExport==null)
			  {
			    loggerDebug("GetCredenceF1PSave","null");
			  }
			  session.putValue("PrintNo","00");
			  session.putValue("PrintQueue",tLOPRTManager2Schema);
			  session.putValue("PrintStream", txmlExport.getInputStream());
			  loggerDebug("GetCredenceF1PSave","put session value");
			  response.sendRedirect("GetF1Print.jsp");
			}
			 	else
		 	{
		 		Content = "ʵ������Ϊ" + ActuGetNo + "���տ��վ��Ѿ���ӡ���ˣ�";
%>
			<html>			
			<script language="javascript">	
				//top.opener.showInfo.close();
				alert("<%=Content%>");
				top.opener.focus();
				top.close();				
			</script>
			</html>
<% 		
		 	}
		}
		catch(Exception ex)
		{
					mErrors=tGetCredenceF1PUI.mErrors;
					Content = strOperation+"ʧ�ܣ�ԭ����:" + mErrors.getFirstError();
%>
			<html>
			<script language="javascript">	
				//top.opener.showInfo.close();
				alert("<%=Content%>");
				top.opener.focus();
				top.close();				
			</script>
			</html>
<%  	
 	  }
%>
