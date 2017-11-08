<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：LMChargeRatesave.jsp
//程序功能：
//创建日期：2006-4-17
//创建人  ：lwj
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%> 
  <%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数

   LCGrpPolSchema tLCGrpPolSchema =new  LCGrpPolSchema();
   LMGrpChargeSchema tLMGrpChargeSchema =new LMGrpChargeSchema();
   CErrors tError = null;
   //LMGrpChargeBL tLMGrpChargeBL = new LMGrpChargeBL();
   String busiName="tbLMGrpChargeBL";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
  VData tVData = new VData();
  
  //输出参数
  String FlagStr = "";
  String Content = "";
    
  String strOperate = request.getParameter("mOperate");
  loggerDebug("LMChargeRateSave","==== strOperate == " + strOperate);
  LMGrpChargeSchema mLMGrpChargeSchema = new LMGrpChargeSchema();
  LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
  
  tLCGrpPolSchema.setGrpContNo(request.getParameter("GrpContNo"));     //集体合同号码
  tLCGrpPolSchema.setRiskCode(request.getParameter("RiskCode"));     //险种编码

  tLMGrpChargeSchema.setPayCountFrom(0);	//起始交费次数
  tLMGrpChargeSchema.setPayCountTo(0);          //终止交费次数
  tLMGrpChargeSchema.setChargeType("11");  	//手续费类型
  tLMGrpChargeSchema.setChargeRate(request.getParameter("ChargeRate"));		//手续费比率

    try
    {		   
       tVData.add(tLCGrpPolSchema);
       tVData.add(tLMGrpChargeSchema);
       tVData.add(tG);		   
       tBusinessDelegate.submitData(tVData,strOperate,busiName);
    }    
    catch(Exception ex)
    {
      Content = "保存失败，原因是:" + ex.toString();
      loggerDebug("LMChargeRateSave","aaaa"+ex.toString());
      FlagStr = "Fail";
    }  
 	if (FlagStr=="")
	{
	    tError =tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {                          
	      Content ="保存成功！";
	    	FlagStr = "Succ";
	    	tVData.clear();
			tVData =tBusinessDelegate.getResult();
	    }
	    else                                                                           
	    {
	    	Content = "保存失败，原因是:" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}      
  %>
  <%=Content%> 		

   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
