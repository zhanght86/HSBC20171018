<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//EdorApproveSave.jsp
//�����ܣ���ȫ��������
//�������ڣ�2005-05-08 15:59:52
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
    <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //�������
  String FlagStr = "";
  String Content = ""; 
  String Flag = "0";
  
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  
  String tPrtSerNo	= request.getParameter("PrtSerNo");
 String tEdorAcceptNo	= request.getParameter("EdorAcceptNo1");
  String tLetterType	= request.getParameter("LetterType");
 String ActionFlag	= request.getParameter("ActionFlag");
 
      //����ԭ������
	String CancelReasonContent = request.getParameter("CancelReasonContent");
	//ԭ����
	String 	SCanclReason = request.getParameter("CancelReasonCode");
	tTransferData.setNameAndValue("CancelReasonContent", CancelReasonContent);
	tTransferData.setNameAndValue("SCanclReason", SCanclReason);
  
  //������ȫȷ�ϡ������޸Ľڵ������Ԫ��
  tTransferData.setNameAndValue("PrtSerNo", tPrtSerNo);
  tTransferData.setNameAndValue("BackReason", SCanclReason);
  tTransferData.setNameAndValue("OtherReason", CancelReasonContent);
  tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
    tTransferData.setNameAndValue("LetterType", tLetterType);
        
//  PEdorErrorForceBackBL tPEdorErrorForceBackBL = new PEdorErrorForceBackBL(); 
	 String busiName="tPEdorErrorForceBackBL";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
   tVData.add(tG);		
   tVData.add(tTransferData);
//   tPEdorErrorForceBackBL.submitData(tVData, ActionFlag);
   tBusinessDelegate.submitData(tVData, ActionFlag,busiName);
	
//	tError = tPEdorErrorForceBackBL.mErrors;
	tError=tBusinessDelegate.getCErrors();
	 if (!tError.needDealError())
	{                          
           		
        		 Content = "���ճɹ�!";
        		 FlagStr = "Succ";
	}
		 else                                                                           
	 {
		    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
		    	FlagStr = "Fail";
	 }
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Flag%>");
</script>
</html>
   
   
   
 