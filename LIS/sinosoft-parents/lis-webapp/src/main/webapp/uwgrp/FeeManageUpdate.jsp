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
  
  
  LCFeeControlSet tFeeControlSet=new LCFeeControlSet();
  LCFeeControlSchema tFeeControlSchema=new LCFeeControlSchema();
  
  String tNum[]   = request.getParameterValues("PolGridNo");
  String tChk[] = request.getParameterValues("InpHavePolGridChk"); 
  String tGrppolno[]  = request.getParameterValues("HavePolGrid1");
  String tGetNoticeNo[]     = request.getParameterValues("HavePolGrid8");
  String tCountNO[]     = request.getParameterValues("HavePolGrid7");
  String tRate[]   = request.getParameterValues("HavePolGrid4");
  String tFee[]     = request.getParameterValues("HavePolGrid5");
  String tRemark[]     = request.getParameterValues("HavePolGrid10");
  
  int Count = 0;
  if (tNum != null) Count = tGrppolno.length;
  
  for (int i = 0; i < Count; i++)
          {
            loggerDebug("FeeManageUpdate",tChk[i]);
            if(tChk[i].equals("1"))
            {
            loggerDebug("FeeManageUpdate","存在要修改的数据！");
            tFeeControlSchema=new LCFeeControlSchema();
            tFeeControlSchema.setGrpPolno(tGrppolno[i]);                       
            tFeeControlSchema.setGetNoticeNo(tGetNoticeNo[i]);
            loggerDebug("FeeManageUpdate","^^^^^^^^^^^^^^..."+tGetNoticeNo[i]);
            tFeeControlSchema.setCountNo(tCountNO[i]);
            if(tRate[i].equals(""))
            {
            tFeeControlSchema.setStandbyFlag1("0");
            }else{
            tFeeControlSchema.setStandbyFlag1(tRate[i]);
            }
            if(tFee[i].equals(""))
            {
            tFeeControlSchema.setStandbyFlag2("0");   
            }else{
            tFeeControlSchema.setStandbyFlag2(tFee[i]);   
            }         
            tFeeControlSchema.setRemark(tRemark[i]);
            tFeeControlSet.add(tFeeControlSchema);
            }
            
          }
          
          TransferData tTransferData = new TransferData();
          tTransferData.setNameAndValue("Fee", tFeeControlSet);
          
          VData tVData = new VData();
          tVData.addElement(tTransferData);
          GlobalInput tG = new GlobalInput();
          tG = ( GlobalInput )session.getValue( "GI" );
          tVData.addElement(tG);
          FeeControlUpdate tFeeControlUpdate=new FeeControlUpdate();
          if( !tFeeControlUpdate.submitData( tVData, "UPDATE||MAIN" ) ) 
          {
          
            Content = " 修改失败，原因是: " + tFeeControlUpdate.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
          }else{
            Content = "修改成功!";
            FlagStr = "Succ";
          }

%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");	
</script>
</html>
