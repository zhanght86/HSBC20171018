<%
//��������
//�����ܣ�
//�������ڣ�
//������  ��jw
//���¼�¼��
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.fininterface.FICertificateRBProduceMain"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%

  String Content = "";
  String FlagStr = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  loggerDebug("FICertificateConfirmSave","��ʼִ��Saveҳ��");

     

  String AppNo = request.getParameter("AppNo");
  String CertificateID = request.getParameter("CertificateId");	
  
   
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(CertificateID);
  tVData.add(AppNo);


  
  try
  {
      ExeSQL tExeSQL = new ExeSQL();
      String tSql = "update FIDataFeeBackApp set AppState = '04' where AppNo = '" + AppNo + "'";
      tExeSQL.execUpdateSQL(tSql);
      if(tExeSQL.mErrors.needDealError())
      {
            Content =  "����״̬ʧ��";
            FlagStr = "Fail";                 
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
