<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：PDRiskGraceSave.jsp
//程序功能：
//创建日期：2011-03-03
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
	<%@page import="com.sinosoft.service.*" %>
	<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
 //接收信息，并作校验处理。
 //输入参数
 //输出参数
  String FlagStr = "";
  String Content = "";
  //后面要执行的动作：添加，修改，删除
  String fmAction=request.getParameter("gOperator");
  System.out.println("gOperator:"+fmAction); 
  String mGraceCalCode = "";
  
  TransferData tTransferData = new TransferData(); 
  String busiName="PDRiskGraceUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  String tCalCodeType = request.getParameter("DutyGraceCalCodeSwitchFlag"); 
  tTransferData.setNameAndValue("CalCodeType",tCalCodeType);
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getAttribute("GI");  //参见loginSubmit.jsp
  System.out.println("tGI"+tGI);
  if(tGI==null)
  {
    System.out.println("页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = ""+"页面失效,请重新登陆"+"";  
  }
  else //页面有效
  {
    CErrors tError = null;
    
    String riskCode = request.getParameter("gRiskcode");
    
    PD_LMRiskPaySchema mPD_LMRiskPaySchema = new PD_LMRiskPaySchema();
    mPD_LMRiskPaySchema.setRiskCode(riskCode);
    mPD_LMRiskPaySchema.setGracePeriod(request.getParameter("GracePeriod2"));
    mPD_LMRiskPaySchema.setGracePeriodUnit(request.getParameter("GracePeriodUnit"));
    mPD_LMRiskPaySchema.setGraceDateCalMode(request.getParameter("GraceDateCalMode"));
    mPD_LMRiskPaySchema.setOverdueDeal(request.getParameter("OverdueDeal"));
    mPD_LMRiskPaySchema.setUrgePayFlag(request.getParameter("UrgePayFlag"));
    
     try{
     	 VData tVData = new VData();
	     tVData.add(mPD_LMRiskPaySchema);
	     
	     tVData.add(tTransferData);
	     tVData.add(tGI);
	     //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
	     if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
	     {
   			 System.out.println("submit success");
	     }
     }
		 catch(Exception ex)
		 {
		   Content = ""+"保存失败，原因是:"+"" + ex.toString();
		   FlagStr = "Fail";
		 }
		 //如果在Catch中发现异常，则不从错误类中提取错误信息
	   if ("".equals(FlagStr))
	   {
	     tError = tBusinessDelegate.getCErrors();
	     if (!tError.needDealError())
	     {
	       Content =""+"操作成功"+"";
	     	 FlagStr = "Succ";
	     	 if("save".equals(fmAction) || "update".equals(fmAction)){
	     	    VData tVData = new VData();
	      		tVData = tBusinessDelegate.getResult();
	      		PD_LMRiskPaySchema tPD_LMRiskPaySchema = new PD_LMRiskPaySchema();
	      		tPD_LMRiskPaySchema = (PD_LMRiskPaySchema)tVData.getObjectByObjectName("PD_LMRiskPaySchema",0);
	      		mGraceCalCode = tPD_LMRiskPaySchema.getGraceCalCode();
	      		if(mGraceCalCode==null){
	      		  mGraceCalCode = "";
	      		}
	      		System.out.println("mGraceCalCode:" + mGraceCalCode);
	      		RiskState.setState(riskCode, ""+"产品条款定义"+"->"+"宽限期信息"+"", "1");
	     	 }else{
	     	 	 RiskState.setState(riskCode, ""+"产品条款定义"+"->"+"宽限期信息"+"", "0");
	     	 }
	     }
	     else                                                                           
	     {
	     	 Content = ""+"操作失败，原因是:"+"" + tError.getFirstError();
	     	 FlagStr = "Fail";
	     }
	   }
   }
 
%>
<html>
<script type="text/javascript">
	parent.fraInterface.fmP.all("GraceCalCode").value="<%=mGraceCalCode%>";
	parent.fraInterface.afterSubmitRiskPay("<%=FlagStr%>","<%=Content%>","<%=fmAction%>");
</script>
</html>