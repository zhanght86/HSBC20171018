<%
//�������ƣ�PEdorTypePLSubmit.jsp
//�����ܣ�
//�������ڣ�2005-5-8 10:47����
//������  ��Lihs
//���¼�¼��  ������    ��������     ����ԭ��/����
//             liurx    2005-06-28
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
 <%@page import="com.sinosoft.service.*" %>   

<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  System.out.println("-----PLsubmit---");
  
  String FlagStr = "";
  String Content = "";
  
  String lostDate = request.getParameter("ReportByLoseDate"); 
  String mCurrentDate = PubFun.getCurrentDate();
  String mCurrentTime = PubFun.getCurrentTime();
  String mSignDate = request.getParameter("SignDate");
  String tLostFlag = request.getParameter("LostFlag");
  String tContNo = request.getParameter("ContNo");
  String tEdorNo = request.getParameter("EdorNo");
  String tEdorType = request.getParameter("EdorType");
  String tEdorAcceptNo = request.getParameter("EdorAcceptNo"); 
  FDate mFDate = new FDate();
  if(lostDate!=null && !lostDate.trim().equals("") && !lostDate.trim().equals("0000-00-00")&&(mFDate.getDate(lostDate).after(mFDate.getDate(mCurrentDate)) || (mSignDate!=null&&!mSignDate.trim().equals("")&&mFDate.getDate(lostDate).before(mFDate.getDate(mSignDate)))) )
  {
      		Content = " ����ʧ��:��ʧ���ڲ������ڽ���,������ǩ�����ڣ�" ;
    		FlagStr = "Fail";
  }
  else
  {
     LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
     TransferData tTransferData = new TransferData();
//     EdorDetailUI tEdorDetailUI = new EdorDetailUI();
     String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	 
     CErrors tError = null;

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
    
    tLPEdorItemSchema.setEdorAcceptNo(tEdorAcceptNo);
	tLPEdorItemSchema.setContNo(tContNo);
    tLPEdorItemSchema.setEdorNo(tEdorNo);
	tLPEdorItemSchema.setEdorType(tEdorType);		
	//tLPEdorItemSchema.setInsuredNo("000000");
	//tLPEdorItemSchema.setPolNo("000000");
    tLPEdorItemSchema.setModifyDate(mCurrentDate);
    tLPEdorItemSchema.setModifyTime(mCurrentTime);  
    tLPEdorItemSchema.setOperator(tG.Operator);
    
    tTransferData.setNameAndValue("LostFlag",tLostFlag);
      
    //����ǹ�ʧ������Ҫ���͹�ʧ���͵�����
    if(tLostFlag.equals("1"))
    {
      System.out.println("report lost:");
      tLPEdorItemSchema.setEdorReasonCode(request.getParameter("ReportByLoseReason"));
      tLPEdorItemSchema.setEdorReason(request.getParameter("ReportByLoseReasonName"));
      String tRemark = "��ʧ����:"+lostDate;
      tTransferData.setNameAndValue("StateReason",request.getParameter("ReportByLoseReason"));
      tTransferData.setNameAndValue("Remark",tRemark);
      tTransferData.setNameAndValue("LostDate",lostDate);
    }
    else
    { 
      System.out.println("cancel lost:");
    }
 


  try {
     // ׼���������� VData
  
  	 VData tVData = new VData();  

  	
	 //������˱�����Ϣ(��ȫ)	
      tVData.addElement(tG);
      tVData.addElement(tLPEdorItemSchema);
      tVData.addElement(tTransferData);
//      boolean tag = tEdorDetailUI.submitData(tVData,""); 
      boolean tag = tBusinessDelegate.submitData(tVData,"",busiName);    
   } catch(Exception ex) {
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    	FlagStr = "Fail";
	 }			
    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr=="") {
  		System.out.println("------success");
//    	tError = tEdorDetailUI.mErrors;
    	tError = tBusinessDelegate.getCErrors(); 
    	if (!tError.needDealError()) {                          
        Content = " ����ɹ�";
    		FlagStr = "Success";  
    	 }else{
    		Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    		FlagStr = "Fail";
    	 }
  	}
 }
%>                      
<html>
<script language="javascript">
   
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

