<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.reinsure.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
  //接收信息，并作校验处理。
  //输入参数
  RIItemCalSchema tRIItemCalSchema = new RIItemCalSchema();
  //RIItemCalAdd tRIItemCalAdd = new RIItemCalAdd();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String tOperate = "";

	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getAttribute("GI");
  tRIItemCalSchema.setArithmeticDefID(request.getParameter("KArithmeticDefID"));
	tRIItemCalSchema.setArithmeticID(request.getParameter("ArithmeticID"));  
  tRIItemCalSchema.setArithmeticName(request.getParameter("ArithmeticName"));
  tRIItemCalSchema.setArithmeticType(request.getParameter("ArithmeticType"));
  tRIItemCalSchema.setCalItemID(request.getParameter("CalItemID"));
  tRIItemCalSchema.setCalItemName(request.getParameter("CalItemName"));
  
  tRIItemCalSchema.setCalItemOrder(request.getParameter("CalItemOrder"));
  tRIItemCalSchema.setCalItemType(request.getParameter("CalItemType"));
  tRIItemCalSchema.setItemCalType(request.getParameter("ItemCalType"));
  tRIItemCalSchema.setDoubleValue(request.getParameter("DoubleValue"));
  tRIItemCalSchema.setCalClass(request.getParameter("CalClass"));
  tRIItemCalSchema.setCalSQL(request.getParameter("CalSQL"));
  
  tRIItemCalSchema.setTarGetClumn(request.getParameter("TarGetClumn"));
  tRIItemCalSchema.setReMark(request.getParameter("ReMark"));
  tRIItemCalSchema.setStandbyString1(request.getParameter("StandbyString1"));
  tRIItemCalSchema.setStandbyString2(request.getParameter("StandbyString2"));
  tRIItemCalSchema.setStandbyString3(request.getParameter("StandbyString3"));

  // 准备传输数据 VData
  VData tVData = new VData();
  FlagStr="";
	tVData.addElement(tRIItemCalSchema);
	tVData.add(tG);
  try
  {
    System.out.println("come========== in"+tRIItemCalSchema.getArithmeticDefID());   
    //tRIItemCalAdd.submitData(tVData,tOperate);
    tBusinessDelegate.submitData(tVData,tOperate,"RIItemCalAdd");
    System.out.println("come out" + tOperate);
  }
  catch(Exception ex)
  {
    Content = ""+"操作失败，原因是:"+"" + ex.toString();
    FlagStr = "Fail";
  }

  if (!FlagStr.equals("Fail"))
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
    	Content = " "+"操作已成功!"+" ";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = " "+"操作失败，原因是:"+"" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }

  //添加各种预处理

%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>

