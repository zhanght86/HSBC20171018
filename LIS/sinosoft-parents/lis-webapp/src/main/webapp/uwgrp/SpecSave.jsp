<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������
  CErrors tError = null;
  String tFlag = "Fail";
  String Content = "";
	GlobalInput tG = new GlobalInput();  
	tG=(GlobalInput)session.getValue("GI");
  
	String tOperate = request.getParameter("Action");
  LCCGrpSpecSchema tLCCGrpSpecSchema=new LCCGrpSpecSchema();
  if("INSERT".equals(tOperate)){
  tLCCGrpSpecSchema.setGrpContNo(request.getParameter("PrtNo"));
  tLCCGrpSpecSchema.setProposalGrpContNo(request.getParameter("PrtNo"));
  tLCCGrpSpecSchema.setSpecContent(request.getParameter("Content"));
  
  loggerDebug("SpecSave","^^^^^^^^^^^^"+request.getParameter("PrtNo"));
  loggerDebug("SpecSave","^^^^^^^^^^^^"+request.getParameter("Content"));
  
  }else{
  String tRadio[] = request.getParameterValues("InpSpecInfoGridSel");
  String tSerialNo[] = request.getParameterValues("SpecInfoGrid5");
  String tProposalGrpContNo[] = request.getParameterValues("SpecInfoGrid6");
  if (tRadio!=null)
  {
    for (int index=0; index< tRadio.length;index++)
    {
      loggerDebug("SpecSave","**************3");
    
      if(tRadio[index].equals("1"))
      {
    	  tLCCGrpSpecSchema.setSerialNo(tSerialNo[index]);
    	  tLCCGrpSpecSchema.setProposalGrpContNo(tProposalGrpContNo[index]);
      }
    }
   }
  }
	  
  
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add( tLCCGrpSpecSchema);
		tVData.add( tG );
		
		// ���ݴ���
		GrpSpecInputBL tGrpSpecInputBL   = new GrpSpecInputBL();
		if("DELETE".equals(tOperate)){
			tFlag="ɾ��";
		}else{
			tFlag="����";
		}
		if (tGrpSpecInputBL.submitData(tVData,tOperate) == false){
			Content ="ʧ��";
		}else{
			Content ="�ɹ�";
		}
	
%>        
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=tFlag%>","<%=Content%>");
</script>

<html>

</html>
