<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpEdorTypeBSSave.jsp
//�����ܣ�
//�������ڣ�2006-04-10 08:49:52
//������  �������
//      
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.bqgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
  

<%
  	loggerDebug("GrpEdorTypeBSSave","=====This is GrpEdorTypeBSSave.jsp=====\n");
    TransferData tTransferData = new TransferData(); 
	  //PGrpEdorBSDetailBL tPGrpEdorBSDetailUI  = new PGrpEdorBSDetailBL();
	   String busiName="bqgrpPGrpEdorBSDetailBL";
       BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
       
	  LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	  
	  //�������
	  String FlagStr = "";
	  String Content = "";
	  GlobalInput tGI = new GlobalInput(); //repair:
	  tGI = (GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
	  loggerDebug("GrpEdorTypeBSSave","tGI" + tGI);
	  if(tGI == null)
	  {
	    loggerDebug("GrpEdorTypeBSSave","ҳ��ʧЧ,�����µ�½");   
	    FlagStr = "Fail";        
	    Content = "ҳ��ʧЧ,�����µ�½";  
	  }
	  else //ҳ����Ч
	  {
	    CErrors tError = null;
	    String tBmCert = "";
	              
	  }    
    try
    {
       
             
        loggerDebug("GrpEdorTypeBSSave","tLCGrpPolSet = " + tLCGrpPolSet.size()); 
        tTransferData.setNameAndValue("EndDate",   request.getParameter("EndDate"));
        tTransferData.setNameAndValue("EdorNo",    request.getParameter("EdorNo"));
        tTransferData.setNameAndValue("EdorType",  request.getParameter("EdorType"));
        tTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo"));
        VData tVData = new VData();
        tVData.add(tLCGrpPolSet);
        tVData.add(tTransferData);
        tVData.add(tGI);
        String tOpertor = request.getParameter("Operator"); 
        if ( tBusinessDelegate .submitData(tVData,tOpertor,busiName))
        {
            Content = "����ɹ�!";
			      FlagStr = "Succ";
        }
        else
        {
		        Content = "����ʧ�ܣ�ԭ����:"+ tBusinessDelegate.getCErrors().getError(0).errorMessage;;
			      FlagStr = "Fail";
        }
     
		}
		catch(Exception ex)
    {
	      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      FlagStr = "Fail";
    }
		  
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

