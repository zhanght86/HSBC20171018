<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//EdorApproveSave.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 15:59:52
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
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
  

  String tMissionID 	= request.getParameter("MissionID");
  String tSubMissionID 	= request.getParameter("SubMissionID"); 
  //�������� 
  String tApproveFlag 	= request.getParameter("ApproveFlag");
  //��������
  String tApproveContent= request.getParameter("ApproveContent");

  String tEdorAcceptNo	= request.getParameter("EdorAcceptNo");
  String tOtherNo		= request.getParameter("OtherNo");
  String tOtherNoType	= request.getParameter("OtherNoType");
  String tEdorAppName	= request.getParameter("EdorAppName");
  String tApptype		= request.getParameter("Apptype");
  String tManageCom		= request.getParameter("ManageCom");
  String tAppntName		= request.getParameter("AppntName");
  String tPaytoDate		= request.getParameter("PaytoDate");
  
  
  //������ȫȷ�ϡ������޸Ľڵ������Ԫ��
  tTransferData.setNameAndValue("MissionID", tMissionID);
  tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
  tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
  tTransferData.setNameAndValue("OtherNo", tOtherNo);
  tTransferData.setNameAndValue("OtherNoType", tOtherNoType);
  tTransferData.setNameAndValue("EdorAppName", tEdorAppName);

  tTransferData.setNameAndValue("ApproveFlag", tApproveFlag);
  tTransferData.setNameAndValue("ApproveContent", tApproveContent);    //������

  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();  
 
  String busiName="EdorNoticeBL";
    
   tVData.add(tG);		
   tVData.add(tTransferData);
 

   if(!tBusinessDelegate.submitData(tVData,"",busiName))
   {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = "����ʧ��";
				   FlagStr = "Fail";				
				}		
	 
	 }
	 else
	 {
				  Content ="����ɹ����뷵�أ�";
		    	FlagStr = "Succ";	
	 }	 
 
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Flag%>");
</script>
</html>
   
   
   
 
