<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpQuestModifyMakeSure.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  
<%
	//�������
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr="";
	String GrpProposalNo = "";
	String Content="";

	//�������
	VData tVData = new VData();
	LCGrpContSchema tLCGrpContSchema   = new LCGrpContSchema();

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	GrpProposalNo = request.getParameter( "GrpProposalNo" );
   
	if( GrpProposalNo=="")
	{
	    
	}
	else
	{
        //������Ϣ
	    tLCGrpContSchema.setProposalGrpContNo(request.getParameter("GrpProposalNo"));
	    tLCGrpContSchema.setApproveFlag("0");
	 
	
    loggerDebug("GrpApproveModifyMakeSure","end setSchema:");
	// ׼���������� VData
	tVData.add( tLCGrpContSchema );
	tVData.add( tG );
	String tOperate="UpDate";
	GrpAppModifyMakeSureUI tGrpAppModifyMakeSureUI = new GrpAppModifyMakeSureUI();
	if( tGrpAppModifyMakeSureUI.submitData( tVData, tOperate ) == false )
	{
		Content = " ����ʧ�ܣ�ԭ����: " + tGrpAppModifyMakeSureUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
		
	}
	else
	{
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
		%>
    	<script language="javascript">
    	 	   	
    	</script>
		<%		
	}
      loggerDebug("GrpApproveModifyMakeSure","Content:"+Content);	
}
%>                      
<html>
<script language="javascript">
try {
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>")
    } 
catch(ex) {}
</script>
</html>

