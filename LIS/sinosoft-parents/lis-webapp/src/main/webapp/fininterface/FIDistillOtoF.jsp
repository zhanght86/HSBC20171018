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
  <%@page import="com.sinosoft.lis.fininterface.FIDistillMain"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%
  VData tVData  = new VData();
  String Content = "";
  String FlagStr = "";
  String StartDate = "";
  String EndDate = "";
  String CertificateID = "";
  FIDistillMain tFIDistillMain = new FIDistillMain();  
  GlobalInput tG = new GlobalInput();  
  String szTemplatePath = application.getRealPath("") + "/fininterface/log/";	//ģ��·��  


  tG=(GlobalInput)session.getValue("GI");
  StartDate = request.getParameter("Bdate");
  EndDate = request.getParameter("Edate");
  CertificateID = request.getParameter("CertificateId");
  tVData.add(tG);
  tVData.add(StartDate);
  tVData.add(EndDate);
  tVData.add(CertificateID);
  tVData.add(szTemplatePath);
  String strBatchno = "";

  String remark = "";
  try
  {
     if(!tFIDistillMain.dealProcess(tVData))
     {
        Content = "ʧ�ܣ�ԭ����:" +tFIDistillMain.mErrors.getFirstError();
        FlagStr = "Fail";     
     }
  }
  catch(Exception ex)
  {
    Content = "ִ���쳣��ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
      Content = "���ݲɼ��ɹ�";
      ExeSQL oExeSQL = new ExeSQL();
      if(tFIDistillMain.BatchNo != null && !tFIDistillMain.BatchNo.equals("")){
         String strSQL  = " select count(1) from firuledealerrlog  where datasourcebatchno = '" + tFIDistillMain.BatchNo.trim() + "'";
        int countNum = Integer.parseInt(oExeSQL.getOneValue(strSQL));
		if (countNum > 0) {
			Content = Content + ",������һЩ���淶����,��Ҫ�˹�����";
		} 
      }
      FlagStr = "Succ";
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
