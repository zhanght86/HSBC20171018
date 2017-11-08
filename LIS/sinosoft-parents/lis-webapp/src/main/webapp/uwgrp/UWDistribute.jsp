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
  
  //LCGrpPolSet tLCGrpPolSet=new LCGrpPolSet();
  DistriControlSet tDistriControlSet=new DistriControlSet();
  //LCGrpPolSchema tLCGrpPolSchema=new LCGrpPolSchema();
  DistriControlSchema tDistriControlSchema=new DistriControlSchema();
  
  String tNum[]   = request.getParameterValues("PolGridNo");
  String tGrppolno[]  = request.getParameterValues("PolGrid1");
  String tGrpcontno[]     = request.getParameterValues("PolGrid2");
  String tRiskcode[]     = request.getParameterValues("PolGrid3");
  String tRate[]   = request.getParameterValues("PolGrid4");
  String tFee[]     = request.getParameterValues("PolGrid4");
  String tManagecom[]     = request.getParameterValues("PolGrid6");
  String tRefee[]     = request.getParameterValues("PolGrid7");
  int Count = 0;
  if (tNum != null) Count = tNum.length;
  
  for (int i = 0; i < Count; i++)
          {
            

            //tLCGrpPolSchema = new LCGrpPolSchema();
            //
            //tLCGrpPolSchema.setGrpPolNo(tGrppolno[i]);
            //tLCGrpPolSchema.setGrpContNo(tGrpcontno[i]);
            //tLCGrpPolSchema.setStandbyFlag2(tRate[i]);
            //tLCGrpPolSchema.setStandbyFlag3(tFee[i]);
            //tLCGrpPolSchema.setmanagecom(tManagecom[i]);
            //tLCGrpPolSchema.setfee(tRefee[i]);
            //tLCGrpPolSet.add(tLCGrpPolSchema);
            
            tDistriControlSchema=new DistriControlSchema();
            tDistriControlSchema.setGrpPolno(tGrppolno[i]);
            tDistriControlSchema.setGrpContno(tGrpcontno[i]);
            tDistriControlSchema.setRiskcode(tRiskcode[i]);
            tDistriControlSchema.setStandbyFlag1(tRate[i]);
            tDistriControlSchema.setStandbyFlag2(tFee[i]);
            tDistriControlSchema.setManagecom(tManagecom[i]);
            tDistriControlSchema.setStandbyFlag3(tRefee[i]);
            tDistriControlSet.add(tDistriControlSchema);
            
          }
          
          TransferData tTransferData = new TransferData();
          tTransferData.setNameAndValue("DISTRI", tDistriControlSet);
          
          VData tVData = new VData();
          tVData.addElement(tTransferData);
          DistributeBL tDistributeBL=new DistributeBL();
          if( !tDistributeBL.submitData( tVData, "UPDATE" ) ) 
          {
          
            Content = " 保存失败，原因是: " + tDistributeBL.mErrors.getError(0).errorMessage;
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
