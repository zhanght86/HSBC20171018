 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
 
//������  jw
 
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%> 
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String Content = "";
  String FlagStr = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  loggerDebug("FICertificateRBSave","��ʼִ��Saveҳ��");

  FIDistillRollBack tFIDistillRollBack = new FIDistillRollBack();
     
  String AppNo = request.getParameter("AppNo");


  
  try
  {
  
     VData tVData = new VData();
     tVData.add(tG);
     tVData.add(AppNo);
  
     if(!tFIDistillRollBack.dealProcess(tVData))
     {
        Content =  "ʧ�ܣ�ԭ����:" + tFIDistillRollBack.mErrors.getFirstError();
        FlagStr = "Fail";         
     }
     else
     {
         ExeSQL tExeSQL = new ExeSQL();
         String tSql = "update FIDataFeeBackApp set AppState = '05' where AppNo = '" + AppNo + "'";
         tExeSQL.execUpdateSQL(tSql);
         if(tExeSQL.mErrors.needDealError())
         {    
               Content =  "����״̬ʧ��";
               FlagStr = "Fail";                 
         }    
     }
    
  }
  catch(Exception ex)
  {
    Content =  "ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }


  if (FlagStr=="")
  {
      Content =  "����ȷ�ϳɹ�";
      FlagStr = "";
  }
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
