 <%
//�������ƣ�NormPayGrpChooseSave.jsp
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
  <%@page import="com.sinosoft.lis.pubfun.*"%>  
  <%@page import="com.sinosoft.service.*" %> 
  
<%@page contentType="text/html;charset=GBK" %> 
<%

//Ӧ�ո��˽��ѱ�      
  LJSPayPersonSet    tLJSPayPersonSet    ;
  LJSPayPersonSchema tLJSPayPersonSchema ;
  
  String tContno = "00000000000000000000";
  
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
  String GrpPolNo=request.getParameter("PolNo"); //���屣������   
  String PayDate=request.getParameter("PayDate"); //��������

//Ӧ�ո��˽��ѱ��еĶ�������                        
    String tTempClassNum[] = request.getParameterValues("NormPayGrpChooseGridNo"); //���
    String tChk[] = request.getParameterValues("InpNormPayGrpChooseGridChk");    //ѡ�б��   
    String tPolNo[] = request.getParameterValues("NormPayGrpChooseGrid1"); //���˱�����
    String tDutyCode[] = request.getParameterValues("NormPayGrpChooseGrid2"); //���α���
    String tPayPlanCode[] = request.getParameterValues("NormPayGrpChooseGrid3"); //���Ѽƻ�����
    String tSumDuePayMoney[] = request.getParameterValues("NormPayGrpChooseGrid6"); //Ӧ�����    
    String tSumActuPayMoney[] = request.getParameterValues("NormPayGrpChooseGrid7"); //ʵ�����
	
    int TempCount = tTempClassNum.length; //��¼��      
  
    tLJSPayPersonSet    = new LJSPayPersonSet();
   
   for(int i=0;i<TempCount;i++)
   {
     tLJSPayPersonSchema = new LJSPayPersonSchema();
     tLJSPayPersonSchema.setPolNo(tPolNo[i]);
     tLJSPayPersonSchema.setDutyCode(tDutyCode[i]);
     tLJSPayPersonSchema.setPayPlanCode(tPayPlanCode[i]);
     tLJSPayPersonSchema.setSumDuePayMoney(tSumDuePayMoney[i]);
     tLJSPayPersonSchema.setSumActuPayMoney(tSumActuPayMoney[i]);
     tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
     tLJSPayPersonSchema.setContNo(tContno);
     tLJSPayPersonSchema.setPayAimClass("2"); //�����µĸ��˽���     
     tLJSPayPersonSchema.setOperator(Operator);
     tLJSPayPersonSchema.setPayType("ZC");           //���ѷ�ʽ����������    
     if(tChk[i].equals("1")) //�����ѡ�У���ô¼������Ϊ1
    	 tLJSPayPersonSchema.setInputFlag("1");
     else
         tLJSPayPersonSchema.setInputFlag("0"); 	 
     tLJSPayPersonSet.add(tLJSPayPersonSchema); 
   }

  //PreParePayPersonUI tPreParePayPersonUI = new PreParePayPersonUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
   VData tVData = new VData();
   tVData.addElement(tLJSPayPersonSet);
   
   tBusinessDelegate.submitData(tVData,"INSERT","PreParePayPersonUI");

   tError =  tBusinessDelegate.getCErrors();
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

