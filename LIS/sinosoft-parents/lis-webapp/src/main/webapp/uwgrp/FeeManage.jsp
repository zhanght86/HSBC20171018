<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%--用户校验类--%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>  
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  loggerDebug("FeeManage","&&&&&&&&&&&&&&&&&&&&&begin!");
  LCFeeControlSet tFeeControlSet=new LCFeeControlSet();
  LCFeeControlSchema tFeeControlSchema=new LCFeeControlSchema();
  
  String tNum[]   = request.getParameterValues("PolGridNo");
  String tGrppolno[]  = request.getParameterValues("PolGrid1");
  String tGrpcontno[]     = request.getParameterValues("PolGrid2");
  String tRiskcode[]     = request.getParameterValues("PolGrid3");
  String tRate[]   = request.getParameterValues("PolGrid5");
  String tFee[]     = request.getParameterValues("PolGrid6");
  String tRemark[]     = request.getParameterValues("PolGrid7");
  
  int Count = 0;
  if (tNum != null) Count = tNum.length;
  
  for (int i = 0; i < Count; i++)
          {
            
            
            tFeeControlSchema=new LCFeeControlSchema();
            tFeeControlSchema.setGrpPolno(tGrppolno[i]);
            tFeeControlSchema.setGrpContno(tGrpcontno[i]);
            tFeeControlSchema.setRiskCode(tRiskcode[i]);
            tFeeControlSchema.setStandbyFlag1(tRate[i]);
            tFeeControlSchema.setStandbyFlag2(tFee[i]);            
            tFeeControlSchema.setRemark(tRemark[i]);
            tFeeControlSet.add(tFeeControlSchema);
            
          }
          
          TransferData tTransferData = new TransferData();
          tTransferData.setNameAndValue("Fee", tFeeControlSet);
          tTransferData.setNameAndValue("Drawer", request.getParameter( "Drawer" ));
          tTransferData.setNameAndValue("DrawerID", request.getParameter( "DrawerID" ));
          VData tVData = new VData();
          tVData.addElement(tTransferData);
          GlobalInput tG = new GlobalInput();
          tG = ( GlobalInput )session.getValue( "GI" );
          tVData.addElement(tG);
          FeeControl tFeeControl=new FeeControl();
          if( !tFeeControl.submitData( tVData, "INSERT" ) ) 
          {
          
            Content = " 保存失败，原因是: " + tFeeControl.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
          }else{
            Content = "保存成功!";
            FlagStr = "Succ";
          }

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
