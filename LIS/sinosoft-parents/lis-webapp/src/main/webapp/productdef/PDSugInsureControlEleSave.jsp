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
 String tRiskCode = "";
 
 TransferData transferData = new TransferData();
 Hashtable tHashtable = new Hashtable();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 tRiskCode=request.getParameter("RiskCode");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 
 
 String tCONTROLCODE =  request.getParameter("CONTROLCODE");
 String tCONTROLVALUE =  request.getParameter("CONTROLVALUE");
 String tFUNCTIONTYPE =  request.getParameter("FUNCTIONTYPE");
 String tRELACODE =  request.getParameter("RELACODE");
 String tRELASHOWFLAG =  request.getParameter("RELASHOWFLAG");
 String tRELAVALUESQL =  request.getParameter("RELAVALUESQL");
 String tRELASHOWVALUE =  request.getParameter("RELASHOWVALUE");

 transferData.setNameAndValue("CONTROLCODE",tCONTROLCODE);
 transferData.setNameAndValue("CONTROLVALUE",tCONTROLVALUE);
 transferData.setNameAndValue("FUNCTIONTYPE",tFUNCTIONTYPE);
 transferData.setNameAndValue("RELACODE",tRELACODE);
 transferData.setNameAndValue("RELASHOWFLAG",tRELASHOWFLAG);
 transferData.setNameAndValue("RELAVALUESQL",tRELAVALUESQL);
 transferData.setNameAndValue("RELASHOWVALUE",tRELASHOWVALUE);
 transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));

 try
 {
  // 准备传输数据 VData
  VData tVData = new VData();

  tVData.add(tG);
  tVData.add(transferData);
  String busiName="PDSugInsureControlEleBL";
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //String tDiscountCode = "";
  if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
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
	
	if('<%=FlagStr%>'=='Fail'){
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + encodeURI(encodeURI('<%=Content%>')) ;  
    	top.fraInterface.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    	parent.fraInterface.unLockPage();
	}else{
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + encodeURI(encodeURI('<%=Content%>')) ;  
    	top.fraInterface.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");  
		top.fraInterface.refresh_tab();
	}
	
</script>
</html>


