  <%
//�������ƣ�NormPayGrpChooseSubmitAll.jsp
//�����ܣ�
//�������ڣ�2002-10-11 08:49:52
//������  ��
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
  
<%@page contentType="text/html;charset=GBK" %> 
<%
  
//���屣����-���ü��屣���ţ��������ڣ�����Ա���������      
  LCGrpPolSet    tLCGrpPolSet  = new LCGrpPolSet();  ;
  LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
  
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
  String GrpPolNo=request.getParameter("SubmitGrpPolNo"); //���屣������   
  String GrpContNo=request.getParameter("SubmitGrpContNo");
  String PayDate=request.getParameter("SubmitPayDate"); //��������
  String ManageFeeRate=request.getParameter("SubmitManageFeeRate"); //����ѱ���
   
  tLCGrpPolSchema.setGrpPolNo(GrpPolNo);
  tLCGrpPolSchema.setGrpContNo(GrpContNo);
  tLCGrpPolSchema.setPaytoDate(PayDate); //���������ֶα��潻������
  tLCGrpPolSchema.setManageFeeRate(ManageFeeRate);
  tLCGrpPolSchema.setManageCom(ManageCom);
  tLCGrpPolSchema.setOperator(Operator);        
  
  NormPayGrpChooseOperUI tNormPayGrpChooseOperUI = new NormPayGrpChooseOperUI();   
  try
  {
   VData tVData = new VData();
   tVData.addElement(tGI);
   tVData.addElement(tLCGrpPolSchema);
   tNormPayGrpChooseOperUI.submitData(tVData,"VERIFY");
   tError = tNormPayGrpChooseOperUI.mErrors;
   
   if (tError.needDealError())        
    {
     Content = " ����ʧ�ܣ�ԭ����: " + tNormPayGrpChooseOperUI.mErrors.getError(0).errorMessage;
     FlagStr = "Fail";      	  
    }
    else
    {
     Content = " ���ݴ������";
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
    if("<%=FlagStr%>"=="Succ")
      {
       parent.fraInterface.  NormPayGrpChooseGrid.clearData("NormPayGrpChooseGrid");  
      }
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</body>
</html>

