<% 
//清空缓存
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html> 
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	String tCurrentDate = PubFun.getCurrentDate();
	String tPrtNo = request.getParameter("PrtNo");
%>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-07
//创建人  ：周平
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language = "javascript">
  var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
  var sysdate = "<%=tCurrentDate%>"; //记录登陆机构
  var tprtNo = "<%=tPrtNo%>"; 
  
  function initForm(){
     ScanActivity();
  }
  function ScanActivity(){
    //var tprtNo ="86160100596242";
    //alert(tprtNo);
    var tType = tprtNo.substring(2,4);
    //alert("tType:"+tType);
        if(tType=="11"||tType=="21"){
    	    try{goToPic(2); top.fraPic.scrollTo(40, 1250); showPosition(120+hx, 1555+hy, 1050, 60);}catch(e){}   
    	}else if (tType=="35"||tType=="25"||tType=="15"){
    	    try{goToPic(0); top.fraPic.scrollTo(40, 1250); showPosition(50+hx, 1410+hy, 750, 60);  } catch(e) {} 
   		}else if (tType=="13"){
   	 	}else if (tType=="61"){
   	 	try { goToPic(2); top.fraPic.scrollTo(300, 1200); showPosition(830+hx,1300+hy, 1000, 120); } catch(e) {} 
   	 	}else if (tType=="51"){
   	 	try { goToPic(2); top.fraPic.scrollTo(100, 1480); showPosition(300+hx,1590+hy, 1000, 60); } catch(e) {} 
   	 	}else if (tType=="16"){
   	 	    try{goToPic(0); top.fraPic.scrollTo(100, 1490); showPosition(100+hx, 1590+hy, 700, 60); } catch(e) {} 
   	 	}
    
}
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
     <script src="../common/javascript/EasyQuery.js"></script>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/EasyScanQuery/ShowPicControl.js"></script>
  <script src="ProposalAutoMove3.js"></script>
</head>
<body  onload="initForm()" >
  <form action="" method=post name=fm target="fraSubmit">
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
