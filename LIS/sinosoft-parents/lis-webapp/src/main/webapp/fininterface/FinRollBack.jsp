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
  <%@page import="com.sinosoft.lis.fininterface.FIDistillRollBack"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%

  CErrors tError = null;
  String Content = "";
  String FlagStr = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");

  FIDistillRollBack tFIDistillRollBack = new FIDistillRollBack();
  int tIndex = 0;
  String tRBResultGrids[] = request.getParameterValues("InpRBResultGridSel");
  String tBatchNo[] = request.getParameterValues("RBResultGrid1");
  String BatchNo = "";
 
 

  for(tIndex = 0; tIndex < tRBResultGrids.length; tIndex++ )
  {
     if(tRBResultGrids[tIndex].equals("1"))
     {
          BatchNo = tBatchNo[tIndex];
          break;
     }
  }
  
  VData tVData = new VData();
  tVData.add(tG);
  tVData.add(BatchNo);
  

  try
  {
     if(!tFIDistillRollBack.dealProcess(tVData))
     {
        Content =  "ʧ�ܣ�ԭ����:" + tFIDistillRollBack.mErrors.getFirstError();
        FlagStr = "Fail";         
     }
  }
  catch(Exception ex)
  {
    Content =  "ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    Content = "���ݻ��˳ɹ�";
    FlagStr = "Succ";
  }
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
