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
  <%@page import="com.sinosoft.lis.pubfun.*"%>  
  
<%@page contentType="text/html;charset=gb2312" %>

<%

   GlobalInput tGI = new GlobalInput(); //repair:
   tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp

   String FlagStr = "";
   String Content = "";

   VData inputVData = new VData();     
   String StartDate = request.getParameter("StartDate");
   String EndDate   = request.getParameter("EndDate");
   
   TransferData tTransferData=new TransferData();
   tTransferData.setNameAndValue("StartDate",StartDate);
   tTransferData.setNameAndValue("EndDate",EndDate);
   
   inputVData.add(tTransferData);
   inputVData.add(tGI);
   
   PrepareAutoDueFeeMultiBL tPrepareAutoDueFeeMultiBL = new PrepareAutoDueFeeMultiBL();
   tPrepareAutoDueFeeMultiBL.submitData(inputVData,"");
   if(tPrepareAutoDueFeeMultiBL.mErrors.needDealError()==true)
   {
   		FlagStr="Fail";
   		Content="ԭ���ǣ�"+tPrepareAutoDueFeeMultiBL.mErrors.getFirstError();
   }
   else
   {   
	   	VData tVData = tPrepareAutoDueFeeMultiBL.getResult();
	   	tTransferData=(TransferData)tVData.getObjectByObjectName("TransferData",0);
		String recordCount=(String)tTransferData.getValueByName("recordCount");
		String succNum=(String)tTransferData.getValueByName("succNum");
		String failNum=(String)tTransferData.getValueByName("failNum");
		String errContent=(String)tTransferData.getValueByName("errContent"); 
		FlagStr="Succ";     		
		Content="����ѯ��"+recordCount+"����¼���ɹ�����"+succNum+"����¼��ʧ��"+failNum+"����¼";
		Content=Content+"--����ԭ��:"+errContent;
   }
%>

<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML> 