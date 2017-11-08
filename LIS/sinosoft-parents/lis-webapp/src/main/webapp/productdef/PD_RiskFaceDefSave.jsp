<%@include file="../i18n/language.jsp"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PD_LMDutyGetClmCalSave.jsp
//程序功能：责任给付赔付扩充计算公式
//创建日期：2009-3-16
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
 //接收信息，并作校验处理。
 //输入参数
 


 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 
 //tLDCodeSchema.setCodeType(request.getParameter("CodeType"));

 String[] values = request.getParameterValues("arr");
 System.out.println("values:"+values);
	RiskParamsBL mRiskParamsBL = new RiskParamsBL("");
	
	String RiskCode = request.getParameter("RiskCode");
	String StandbyFlag1 = request.getParameter("StandbyFlag1");
	
	String DutyChoose = request.getParameter("DutyChoose");
	String PayChoose = request.getParameter("PayChoose");
	
	String[] DutySel = request.getParameterValues("DutySel");
	String[] PaySel = request.getParameterValues("PaySel");
try{
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(0,values);
   tVData.add(1,RiskCode);
   tVData.add(2,DutyChoose);
   tVData.add(3,PayChoose);
   tVData.add(4,DutySel);
   tVData.add(5,PaySel);
   tVData.add(6,StandbyFlag1);
 
  String busiName="RiskFaceDefUI";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, "SaveRiskParams",busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
    //Content = " 处理失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
  	//FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	 // getItems = (JSONObject)rVData.get(0);
	
    //Content = " 处理成功! ";
  	//FlagStr = "Succ";
  	// new RiskState().setState(request.getParameter("RiskCode"), "契约业务控制->险种核保规则", "1");

  }
  }
 catch(Exception ex)
 {
 // Content = "保存失败，原因是:" + ex.toString();
 // FlagStr = "Fail";
 }
 
%>                      
<html>
<script type="text/javascript">

</script>
</html>

