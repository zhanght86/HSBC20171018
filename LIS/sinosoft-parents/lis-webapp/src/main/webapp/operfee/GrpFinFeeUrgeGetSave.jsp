 <%
//�������ƣ�IndiFinVerifyUrgeGetByPolNo.jsp
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
    
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
// �������
   CErrors tError = null;          
   String FlagStr = "";
   String Content = "";   
   int recordCount = 0;

	
   String tGrpContNo = request.getParameter("GrpContNo");
   loggerDebug("GrpFinFeeUrgeGetSave","========================="+tGrpContNo);
	 TransferData tTransferData = new TransferData();
	 tTransferData.setNameAndValue("GrpContNo",tGrpContNo);
	 
   VData tVData = new VData();     
   tVData.add(tTransferData);    
   tVData.add(tGI);

            RnGrpVerifyBLF tRnGrpVerifyBLF = new RnGrpVerifyBLF();
            if(!tRnGrpVerifyBLF.submitData(tVData,"VERIFY"))
            {
    	            Content = " ��������ʧ�ܣ�ԭ����:" + tRnGrpVerifyBLF.mErrors.getFirstError();
    	            FlagStr = "Fail";            
            }
  					else
            {                          
                    Content = " ��������ɹ�";
                    FlagStr = "Succ";
            }

  
Content.replace('\"','$'); 
loggerDebug("GrpFinFeeUrgeGetSave","Content:"+Content);   
%>
<HTML>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</HTML> 
