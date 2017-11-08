<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  System.out.println("start");
  LJAGetSchema tLJAGetSchema = new LJAGetSchema();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
    //输出参数
    CError cError = new CError( );
    //后面要执行的动作：添加，修改，删除
		int index;
    String tRela  = "";
    String FlagStr = "";
    String Content = "";
    String strOperation = "PRINT";
  String tActuGetNo =request.getParameter("PrtData1");
  tLJAGetSchema.setActuGetNo(tActuGetNo);
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
             cError.moduleName = "TempFeeWithdrawPrintSave";
             cError.functionName = "submitData";
             cError.errorMessage = "GetCredenceF1PUI发生错误，但是没有提供详细的出错信息";
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
			    System.out.println("null");
			  }
			  session.putValue("PrintNo","00");
			  session.putValue("PrintQueue",tLOPRTManager2Schema);
			  session.putValue("PrintStream", txmlExport.getInputStream());
			  System.out.println("put session value");
			  response.sendRedirect("GetF1Print.jsp");
			}
			 	else
		 	{
		 		Content = "实付号码为" + ActuGetNo + "的收款收据已经打印过了！";
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
					Content = strOperation+"失败，原因是:" + mErrors.getFirstError();
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