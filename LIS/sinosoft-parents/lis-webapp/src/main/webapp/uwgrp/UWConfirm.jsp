<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>  
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
<%
//�������
CErrors tError = null;
String tRela  = "";                
String FlagStr="Succ";
String Content = "";
String tAction = "";
String tOperate = "";
String wFlag = "";

  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  
  String tYesOrNo = request.getParameter("YesOrNo");
    VData tVData = new VData();
    VData mVData = new VData();
    //�����������ͱ𣬸��ݴ�ֵ�����ID��ȡ��������ִ�о���ҵ���߼�
    wFlag = request.getParameter("WorkFlowFlag");
   // TransferData mTransferData = new TransferData();
    LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
    LCGrpContSet tLCGrpContSet =new LCGrpContSet();

    tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
   // tLCGrpContSchema.setUWFlag(request.getParameter("GUWState"));
   //tLCGrpContSchema.setRemark(request.getParameter("GUWIdea"));
   tLCGrpContSchema.setUWFlag("9");
  
   tLCGrpContSchema.setRemark("����ͨ��");
   tLCGrpContSet.add(tLCGrpContSchema);
   
   //�ϱ�
   LCUWSendTraceSchema tLCUWSendTraceSchema = new LCUWSendTraceSchema();
   tLCUWSendTraceSchema.setOtherNo(request.getParameter("GrpContNo"));		
   tLCUWSendTraceSchema.setOtherNoType("2");                   //���� 
   tLCUWSendTraceSchema.setUWFlag("9");
   tLCUWSendTraceSchema.setUWIdea(request.getParameter("UWIdea"));
							TransferData nTransferData = new TransferData();
							
	    				nTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
	    			
   						nTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));	
   					
   					  nTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
   					
	           
              nTransferData.setNameAndValue("UWUpReport",request.getParameter("uwUpReport"));
              //�¼���������
              nTransferData.setNameAndValue("cooperate",request.getParameter("cooperate"));
              nTransferData.setNameAndValue("riskgrade",request.getParameter("riskgrade"));
              nTransferData.setNameAndValue("appcontract",request.getParameter("appcontract"));
             
              nTransferData.setNameAndValue("UWFlag","9");
              nTransferData.setNameAndValue("UWIdea",request.getParameter("UWIdea"));
              //nTransferData.setNameAndValue("LCUWSendTraceSchema",tLCUWSendTraceSchema);
   	   			 
    					mVData.add(tLCGrpContSet);
    					mVData.add(tLCUWSendTraceSchema);
    					mVData.add(nTransferData);
    					mVData.add(tG);
    				
    															
  						GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
  						if( !tGrpTbWorkFlowUI.submitData( mVData, wFlag ) ) {
  						    Content = " �˱�ȷ��ʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
  						    FlagStr = "Fail";
  						}else {
  						    Content = " �˱�ȷ�ϳɹ���";
  						    FlagStr = "Succ";
  						}
  				//}   //�ϱ�����ȥ�� hl 20050526
  		 //}      //�ϱ�����ȥ�� hl 20050526
  	//}         //�ϱ�����ȥ�� hl 20050526
 loggerDebug("UWConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
