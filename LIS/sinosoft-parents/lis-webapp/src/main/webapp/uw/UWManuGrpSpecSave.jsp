
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuGrpSpecSave.jsp
//�����ܣ��˹��˱���Լ�б�
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//modify by zhangxing
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  boolean flag = true;
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null)
   {
	 	out.println("session has expired");
		 return;
   } 
    //������Ϣ
  
  String tOperate = request.getParameter("Operate");
  String tGrpcontNo = request.getParameter("GrpContNo");
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	TransferData tTransferData = new TransferData();

  tTransferData.setNameAndValue("GrpContNo",tGrpcontNo);
	tTransferData.setNameAndValue("Remark",tRemark);
	tTransferData.setNameAndValue("SpecReason",tSpecReason);
	
try{
	  
	   VData tVData = new VData();
	   tVData.add(tTransferData);
	   tVData.add(tG);
		
		 // ���ݴ���
	   GrpUWSpecUI tGrpUWSpecUI   = new GrpUWSpecUI();
	   if (!tGrpUWSpecUI.submitData(tVData,tOperate))
	   {             
			 int n = tGrpUWSpecUI.mErrors.getErrorCount();
			 Content = " ����ʧ�ܣ�ԭ����: " + tGrpUWSpecUI.mErrors.getError(0).errorMessage;
			 FlagStr = "Fail";
	   }
	 
	   if (FlagStr == "Fail")
	   {
		   tError = tGrpUWSpecUI.mErrors;
		   if(!tError.needDealError())
		   {                          
		    	Content = "�˱���Լ�ɹ�! ";
		    	FlagStr = "Succ";
		   }
		   else                                                                           
		   {
		    FlagStr = "Fail";
		   }
	   }
	 }
	
  catch(Exception e)
  {
  	e.printStackTrace();
  	Content = Content.trim()+"��ʾ���쳣��ֹ!";
  }
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

