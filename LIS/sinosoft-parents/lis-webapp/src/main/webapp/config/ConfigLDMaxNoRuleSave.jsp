<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

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
 
request.setCharacterEncoding("UTF-8"); 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getValue("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 String busiName = "LDMaxNoConfigUI";
 BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
 
 String jsonStr= request.getParameter("arr");  
 String tNoCode = request.getParameter("NoCode");  
 String tShowRule = request.getParameter("ShowRule");  
 String tLimitType = request.getParameter("LimitType");  
// JSONArray tJSONArray = JSONArray.fromObject(jsonStr);
try{
  // 准备传输数据 VData
  VData tVData = new VData();

   tVData.add(tG);
   tVData.add(1,jsonStr);
   tVData.add(2,tNoCode);
   tVData.add(3,tShowRule);
   tVData.add(4,tLimitType);
  
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, "SaveLDMaxNoRule",busiName)) {
	  out.print("[{\"success\":false,\"msg\":\""+tBusinessDelegate.getCErrors().getError(0).errorMessage+"\"}]");
	  
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
	  out.print("[{\"success\":true,\"msg\":\"success\"}]");
  }
  }
 catch(Exception ex)
 {
 }
 
%>                      


