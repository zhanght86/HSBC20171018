 <%
//�������ƣ�NormPayGrpChooseSaveAll.jsp
//�����ܣ�
//�������ڣ�2005-07-05 08:49:52
//������  ��ck
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="java.lang.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.bl.*"%>  
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>  
  
<%@page contentType="text/html;charset=gb2312" %> 
<%

// �������
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";
 
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  if(tGI==null)
  {
    System.out.println("ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
   String Operator  = tGI.Operator ;  //�����½����Ա�˺�
   String ManageCom = tGI.ComCode  ; //�����½��վ,ManageCom=�ڴ��е�½��վ����

  
  //���е������ֶ�
  String GrpPolNo=request.getParameter("GrpPolNo1"); //���屣������  

  SaveAllPersonInput tSaveAllPersonInput = new SaveAllPersonInput();
  try
  {
   VData tVData = new VData();
   tVData.add(tGI);
   tVData.addElement(GrpPolNo);

   
   tSaveAllPersonInput.submitData(tVData,"GrpChoose_INSERT");

   tError = tSaveAllPersonInput.mErrors;
   if (tError.needDealError())        
    {
      Content = " ʧ�ܣ�ԭ����:" + tError.getFirstError();
      FlagStr = "Fail"; 
    }
   else{
      Content = " ���ݱ������";
      FlagStr = "Succ";   	 
      } 
  }
  catch(Exception ex)
  {
    Content = "ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
} //ҳ����Ч��
%>                                              
<html>
<body>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</body>
</html>

