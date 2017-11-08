<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>  
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
//输出参数
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
    //工作流操作型别，根据此值检索活动ID，取出服务类执行具体业务逻辑
    wFlag = request.getParameter("WorkFlowFlag");
   // TransferData mTransferData = new TransferData();
    LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
    LCGrpContSet tLCGrpContSet =new LCGrpContSet();

    tLCGrpContSchema.setGrpContNo(request.getParameter("GrpContNo"));
   // tLCGrpContSchema.setUWFlag(request.getParameter("GUWState"));
   //tLCGrpContSchema.setRemark(request.getParameter("GUWIdea"));
   tLCGrpContSchema.setUWFlag("9");
  
   tLCGrpContSchema.setRemark("正常通过");
   tLCGrpContSet.add(tLCGrpContSchema);
   
   //上报
   LCUWSendTraceSchema tLCUWSendTraceSchema = new LCUWSendTraceSchema();
   tLCUWSendTraceSchema.setOtherNo(request.getParameter("GrpContNo"));		
   tLCUWSendTraceSchema.setOtherNoType("2");                   //团险 
  // tLCUWSendTraceSchema.setUWFlag("9");
   //tLCUWSendTraceSchema.setUWIdea(request.getParameter("UWIdea"));
							TransferData nTransferData = new TransferData();
							
	    				nTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));  
	    			
   						nTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));	
   					
   					  nTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
   					
	           
              nTransferData.setNameAndValue("UWUpReport",request.getParameter("uwUpReport"));
             
              nTransferData.setNameAndValue("UWIdea",request.getParameter("UWIdea"));
   	   			 
   	   			 
    					mVData.add(tLCGrpContSet);
    					mVData.add(nTransferData);
    					mVData.add(tLCUWSendTraceSchema);
    					mVData.add(tG);
    				
    															
  						//GrpTbWorkFlowUI tGrpTbWorkFlowUI = new GrpTbWorkFlowUI();
  						String busiName="tbgrpGrpTbWorkFlowUI";
                        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  						
                        if( !tBusinessDelegate.submitData( mVData, wFlag,busiName ) ) {
  						    Content = " 核保确认失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
  						    FlagStr = "Fail";
  						}else {
  						    Content = " 核保确认成功！";
  						    FlagStr = "Succ";
  						}
  				//}   //上报错误去掉 hl 20050526
  		 //}      //上报错误去掉 hl 20050526
  	//}         //上报错误去掉 hl 20050526
 loggerDebug("UWConfirm","-------------------end workflow---------------------");
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
