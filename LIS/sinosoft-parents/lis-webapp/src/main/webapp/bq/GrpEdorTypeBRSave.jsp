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

<%
  
    TransferData tTransferData = new TransferData(); 
	  PGrpEdorBRDetailBL tPGrpEdorBRDetailBL  = new PGrpEdorBRDetailBL();
	  LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	  
	  //�������
	  String FlagStr = "";
	  String Content = "";
	  GlobalInput tGI = new GlobalInput(); //repair:
	  tGI = (GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp	  
	  if(tGI == null)
	  {
	    loggerDebug("GrpEdorTypeBRSave","ҳ��ʧЧ,�����µ�½");   
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
        //String tChk[]           = request.getParameterValues("InpLCGrpPolGridSel");
        //String tGrpContNo[]     = request.getParameterValues("LCGrpPolGrid1");    
        //String tGrpPolNo[]      = request.getParameterValues("LCGrpPolGrid2"); 
        //String tRiskCode[]      = request.getParameterValues("LCGrpPolGrid3");
        //String tCvalidate[]     = request.getParameterValues("LCGrpPolGrid5");
        //String tPayEndDate[]    = request.getParameterValues("LCGrpPolGrid6");
        //String tPeoples2[]      = request.getParameterValues("LCGrpPolGrid7");
        //String tGrpName[]       = request.getParameterValues("LCGrpPolGrid4");
        //String tGrpProposalNo[] = request.getParameterValues("LCGrpPolGrid8");
        //for(int index = 0; index < tChk.length; index++)
        //{
        //	if (tChk[index].equals("1")){
        //    
        //    LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
        //    tLCGrpPolSchema.setGrpPolNo(tGrpPolNo[index]); 
        //    tLCGrpPolSchema.setGrpContNo(tGrpContNo[index]);
        //    tLCGrpPolSchema.setRiskCode(tRiskCode[index]);
        //    tLCGrpPolSchema.setCValiDate(tCvalidate[index]);
        //    tLCGrpPolSchema.setPayEndDate(tPayEndDate[index]);
        //    tLCGrpPolSchema.setGrpName(tGrpName[index]);
        //    tLCGrpPolSchema.setPeoples2(tPeoples2[index]);
        //    tLCGrpPolSchema.setGrpProposalNo(tGrpProposalNo[index]);
        //    TransferData  mTransferData = new TransferData();
        //    mTransferData.setNameAndValue("Action","2");
        //    mTransferData.setNameAndValue("EdorNo",request.getParameter("EdorNo"));
        //    mTransferData.setNameAndValue("GrpPolNo",tGrpPolNo[index]);
        //    mTransferData.setNameAndValue("EdorType",request.getParameter("EdorType"));                       
        //               
        //    tLCGrpPolSet.add(tLCGrpPolSchema);
        //  }             
        //}
        //loggerDebug("GrpEdorTypeBRSave","tLCGrpPolSet="+tLCGrpPolSet.size());
      
        tTransferData.setNameAndValue("StatDate",   request.getParameter("StatDate"));
        tTransferData.setNameAndValue("EdorNo",    request.getParameter("EdorNo"));
        tTransferData.setNameAndValue("EdorType",  request.getParameter("EdorType"));
        tTransferData.setNameAndValue("GrpContNo", request.getParameter("GrpContNo"));
        VData tVData = new VData();
        tVData.add(tLCGrpPolSet);
        tVData.add(tTransferData);
        tVData.add(tGI);
        String tOpertor = request.getParameter("Operator"); 
        if ( tPGrpEdorBRDetailBL .submitData(tVData,tOpertor))
        {
            Content = "����ɹ�!";
			      FlagStr = "Succ";
        }
        else
        {
		        Content = "����ʧ�ܣ�ԭ����:"+ tPGrpEdorBRDetailBL.mErrors.getError(0).errorMessage;;
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

