 <%
//�������ƣ�IndiFinVerifyUrgeGet.jsp
//�����ܣ�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//         
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>    
  
<%@page contentType="text/html;charset=GBK" %>

<%
// �������
   CErrors tErrors = null;          
   String FlagStr = "";
   String Content = "";

   // ׼���������� VData 
   VData tVData = new VData();
   
   GlobalInput tGI = (GlobalInput)session.getValue("GI");
   tVData.add(tGI);
   
   //String StartDate = request.getParameter("StartDate");
   //String EndDate   = request.getParameter("EndDate");
//System.out.println("EndDate="+EndDate);
   
   //LJTempFeeSchema qLJTempFeeSchema = new LJTempFeeSchema();
   //qLJTempFeeSchema.setEnterAccDate(StartDate); //�������ڴ����ʼ����
   //qLJTempFeeSchema.setConfDate(EndDate);      //ȷ�����ڴ����ֹ����
   
  // tVData.add(qLJTempFeeSchema);
   MultFinVerifyUrgeGetBL tMultFinVerifyUrgeGetBL = new MultFinVerifyUrgeGetBL();
   tMultFinVerifyUrgeGetBL.submitData(tVData,"Verify");
   int failNum = tMultFinVerifyUrgeGetBL.failNum;
   int Count = tMultFinVerifyUrgeGetBL.Count;
   
   if(failNum != 0){
     int succNum = Count - failNum;
     FlagStr="Fail";
     Content="����ѯ��"+Count+"����¼���ɹ�����"+succNum+"����¼��ʧ��"+failNum+"����¼";
     tErrors = tMultFinVerifyUrgeGetBL.mErrors;
     if (tErrors.needDealError()){
      Content = Content+"\n ʧ��ԭ����:" + tErrors.getFirstError();
      }
   }
   else{
      FlagStr="���������";
      Content="����ѯ��"+Count+"����¼,ȫ������ɹ�!";
   }
%>
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML>  