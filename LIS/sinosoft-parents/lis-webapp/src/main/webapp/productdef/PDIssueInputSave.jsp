<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDIssueInputSave.jsp
//�����ܣ������¼��
//�������ڣ�2009-4-2
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.workflow.proddef.ProdDefWorkFlowBL"%>
 <%@page import="com.sinosoft.service.*" %>    
<%
 //������Ϣ������У�鴦��
 //�������
 

// PDIssueInputBL pDIssueInputBL = new PDIssueInputBL();
 
 CErrors tError = null;          
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 
 String tBackPost = request.getParameter("BackPost");

 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
 transferData.setNameAndValue("BackPost",request.getParameter("BackPost"));
 transferData.setNameAndValue("PostCode",request.getParameter("PostCode"));
 transferData.setNameAndValue("IssueType",request.getParameter("IssueType"));
 transferData.setNameAndValue("IssueContDesc",request.getParameter("IssueContDesc"));
 transferData.setNameAndValue("Filepath",request.getParameter("Filepath2"));
 transferData.setNameAndValue("Filename",request.getParameter("Filename2"));
 transferData.setNameAndValue("SerialNo",request.getParameter("SerialNo"));
 
 boolean needTransfer = false;
 try
 {
  	// ׼���������� VData
  	VData tVData = new VData();

  	tVData.add(tG);
  	tVData.add(transferData);
  	//pDIssueInputBL.submitData(tVData,operator);
  	String busiName="PDIssueInputBL";
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //String tDiscountCode = "";
    if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
  	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
    	FlagStr = "Fail";
    }
    else {
      Content = " "+"����ɹ�!"+" ";
    	FlagStr = "Success";
    	needTransfer = (Boolean)tBusinessDelegate.getResult().get(0);
    }
   
   }
   catch(Exception ex)
   {
    Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
    FlagStr = "Fail";
   }

   
 System.out.println("------------in PDIssueInputSave.jsp���Ƿ�����������---FlagStr:["+FlagStr+"];operator:["+ operator+"];IsNeadTransfer:"+needTransfer+";BackPost:"+request.getParameter("BackPost"));
 
  //��ʼ��������
  if( FlagStr == "Success" && operator.equals("deal") && needTransfer)
  {
	     transferData = new TransferData();
		 transferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
		 transferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
		 transferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
	     transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
		 transferData.setNameAndValue("RequDate",request.getParameter("RequDate"));
		 transferData.setNameAndValue("Operator",tG.Operator);

		 if( "00".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("IsBaseInfoDone","1");
			 operator = "workflow";
		 }else if ( "10".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
			 operator = "cont";
		 }else if ( "11".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
			 operator = "edor";
		 }else if ( "12".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
			 operator = "claim";
		 }else if ( "13".equals(tBackPost) )
		 {
			 transferData.setNameAndValue("NeedTransferFlag",request.getParameter("NeedTransferFlag"));
			 operator = "lfrisk";
		 }
		 
		// ProdDefWorkFlowBL tProdDefWorkFlowBL= new ProdDefWorkFlowBL();
 		 
		 VData data = new VData();
   	     data.add(tG);
		 data.add(transferData);
		 String busiName="ProdDefWorkFlowBL";
		    
		 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		    
		 boolean flag = tBusinessDelegate.submitData(data,operator,busiName);
		 System.out.println("��������ת��������"+flag);
		 if (flag)
		 {                     
		  	 Content = " "+"�����ɹ�!"+" ";
		   	 FlagStr = "Success";
		 }
		 else                                                                           
		 {
		   	 Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
		   	 FlagStr = "Fail";
		 }
  }
  
 //��Ӹ���Ԥ����
%>                      

<html>
<script type="text/javascript">	
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
 
