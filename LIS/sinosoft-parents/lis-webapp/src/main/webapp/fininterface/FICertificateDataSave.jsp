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
  loggerDebug("FICertificateDataSave","��ʼִ��Saveҳ��");
  FICertificateRBProduceMain tFICertificateRBProduceMain = new FICertificateRBProduceMain();
     

  String AppNo = request.getParameter("AppNo");
  String CertificateID = request.getParameter("CertificateId");	

  //�����п������ϵͳ��ʱ���룬������Ŀʵʩ����ȥ��
  String tSql = "update FIAboriginalData  set TypeFlag06 = (select b.codealias from Ficodetrans b where b.codetype = 'RiskTrans' and b.code =  StringInfo05 ) where StringInfo05 is not null and BatchNo = '" + AppNo + "'";
  ExeSQL tExeSQL = new ExeSQL();
  tExeSQL.execUpdateSQL(tSql); 
  //�п������ϵͳ��ʱ���룬������Ŀʵʩ����ȥ��    
   
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(CertificateID);
  tVData.add(AppNo);


  
  try
  {
    if(!tFICertificateRBProduceMain.dealProcess(tVData))
    {
        Content =  "ʧ�ܣ�ԭ����:" + tFICertificateRBProduceMain.mErrors.getFirstError();
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
      Content =  "���ƾ֤��ȡ�ɹ�";
      FlagStr = "";
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
