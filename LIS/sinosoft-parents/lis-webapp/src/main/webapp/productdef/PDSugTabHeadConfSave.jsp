<%@include file="../i18n/language.jsp"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDSugIncomeDataAlgSave.jsp
//程序功能：收益算法定义
//创建日期：2011-10-24
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.sinosoft.service.*" %>  
<%@page import="org.codehaus.xfire.addressing.RandomGUID" %>  
  
<%
 //接收信息，并作校验处理。
 //输入参数

 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 String tRiskCode=null;
 
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 tRiskCode=request.getParameter("RiskCode");
 
 VData tVData = new VData();
 PD_LMTabHeadRelaSet pD_LMTabHeadRelaSet = new PD_LMTabHeadRelaSet();
 String tTabHeadConfNum[] = request.getParameterValues("SaveTabHeadConfGridNo");
 String tTabHeadConfId[] = request.getParameterValues("SaveTabHeadConfGrid1");
 for(int i=0;i<tTabHeadConfNum.length;i++){
	 PD_LMTabHeadRelaSchema pD_LMTabHeadRelaSchema = new PD_LMTabHeadRelaSchema();
	 pD_LMTabHeadRelaSchema.setRiskCode(tRiskCode);
	 pD_LMTabHeadRelaSchema.setHeadID(tTabHeadConfId[i]);
	 pD_LMTabHeadRelaSchema.setType(request.getParameter("TYPE"));
	 pD_LMTabHeadRelaSet.add(pD_LMTabHeadRelaSchema);
 }
 try
 {

  tVData.add(tG);
  tVData.add(pD_LMTabHeadRelaSet);
  String busiName="PDSugTabHeadConfBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if (!tBusinessDelegate.submitData(tVData, "UPDATE",busiName)) {
	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"处理失败，原因是:"+"" + (String)rVData.get(0);
  	  FlagStr = "Fail";
  }
  else {
	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"处理成功!"+" ";
  	  FlagStr = "Succ";

  }
 
 }
 catch(Exception ex)
 {
  Content = ""+"保存失败，原因是:"+"" + ex.toString();
  FlagStr = "Fail";
 }
  

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
		parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


>","<%=Content%>");
</script>
</html>


