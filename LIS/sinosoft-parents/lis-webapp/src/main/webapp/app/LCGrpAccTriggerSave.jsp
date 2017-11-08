<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
 CErrors tError=null;
String FlagStr = "";
String Content = "";
 
 LCGrpAccTriggerSet tLCGrpAccTriggerSet=new LCGrpAccTriggerSet();
 
 GlobalInput tGlobalInput=(GlobalInput)session.getValue("GI");
 String tOperate=request.getParameter("mOperate");	//操作模式
 
 loggerDebug("LCGrpAccTriggerSave","begin submit.jsp...........");
 
 String GrpPolNo=request.getParameter("GrpPolNo");
 String GrpContNo=request.getParameter("GrpContNo");
 String RiskCode=request.getParameter("RiskCode");
 
 String arrCount[]=request.getParameterValues("AccTriggerGridNo");
 int lineCount = arrCount.length; //行数
 //loggerDebug("LCGrpAccTriggerSave","the length is "+lineCount);    
 
 if (arrCount != null)
 {
 	String InsuAccNo[]= request.getParameterValues("AccTriggerGrid1");
 	String PayPlanCode[]= request.getParameterValues("AccTriggerGrid2");
    String RiskAccPayName[] = request.getParameterValues("AccTriggerGrid3"); //险种帐户名称
    String ToObjectType[] = request.getParameterValues("AccTriggerGrid4"); //归属对象类型

 	for(int i=0;i<lineCount;i++)
 	{
 		if(ToObjectType[i]!=null&&ToObjectType[i].length()>0)
 		{
 			LCGrpAccTriggerSchema tLCGrpAccTriggerSchema=new LCGrpAccTriggerSchema();
 			tLCGrpAccTriggerSchema.setGrpPolNo(GrpPolNo);
  			tLCGrpAccTriggerSchema.setGrpContNo(GrpContNo);
  			tLCGrpAccTriggerSchema.setRiskCode(RiskCode);
  			tLCGrpAccTriggerSchema.setInsuAccNo(InsuAccNo[i]);
  			tLCGrpAccTriggerSchema.setPayPlanCode(PayPlanCode[i]);
  			tLCGrpAccTriggerSchema.setRiskAccPayName(RiskAccPayName[i]);
  			tLCGrpAccTriggerSchema.setToObjectType(ToObjectType[i]);
  		
  			tLCGrpAccTriggerSet.add(tLCGrpAccTriggerSchema);
  		}
 	}
 	
 }
  
 
  
  VData vData=new VData();
  vData.add(tGlobalInput);
  vData.add(tLCGrpAccTriggerSet);
  //LCGrpAccTriggerUI tLCGrpAccTriggerUI=new LCGrpAccTriggerUI();
  String busiName="tbLCGrpAccTriggerUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
   {
	  tBusinessDelegate.submitData(vData,tOperate,busiName);
   }
   catch(Exception e)
   {
     	Content = "保存失败，原因是:" + e.toString();
	FlagStr = "Fail";
   }
   
  if(!FlagStr.equals("Fail"))
  {
     	tError=tBusinessDelegate.getCErrors();
     	if(!tError.needDealError())
      	{
      		loggerDebug("LCGrpAccTriggerSave","no errors");
        	Content="操作成功";
        	FlagStr="Succ";
      	}
     	else
     	{
      		Content="操作失败,失败原因是：" + tError.getFirstError();
      		FlagStr="Fail";
     	}
  }
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
