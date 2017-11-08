<%@include file="../i18n/language.jsp"%>

<%@page import="com.sinosoft.productdef.*"%>
<html >
<head>
<%
String tRiskCode = request.getParameter("RiskCode");
String tStandbyFlag1 = request.getParameter("StandbyFlag1");
tRiskCode = "" +tRiskCode+"";
System.out.println(tRiskCode);
/*
RiskFaceDefService tTest = new RiskFaceDefService(tRiskCode);
String tRiskString = tTest.initRiskScript();
tRiskString = "";
String tPayString = tTest.initPayScript();
String tFuncString = tTest.initFuncScript();
*/
%>
	<meta charset="UTF-8">
	<title>险种界面RiskFaceDefService</title>
 	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 	<!--LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css-->
 	<!--link rel="stylesheet" href="../common/css/jquery.ui.all.css"-->
 	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
 	<script src="../common/javascript/jquery.js"></script>
	<script src="../common/javascript/jquery.ui.core.js"></script>
	<script src="../common/javascript/jquery.ui.widget.js"></script>
	<script src="../common/javascript/jquery.ui.mouse.js"></script>
	<script src="../common/javascript/jquery.ui.draggable.js"></script>
	<script src="../common/javascript/jquery.ui.sortable.js"></script>
	<script src="../common/javascript/jquery.easyui.js"></script>
	<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" href="../demos.css">
	 <SCRIPT src="showRiskFace.js"></SCRIPT>
	<style>
	.divRisk ul { width=100%;list-style-type: none; margin: 0; padding: 0; margin-bottom: 10px; }
	//.demo li { margin: 5px; padding: 5px; width: 150px; }
	.divRisk li { float:left; width:30%; margin-left:10px; display:inline; height:30px; background:#F7F7F7; margin-top:10px;}
</style>

<script type="text/javascript">
	var jRiskCode = '<%=tRiskCode%>';
	var jStandbyFlag1 = '<%=tStandbyFlag1%>';
	var contOpt = '<%=request.getParameter("contopt")%>';
	//alert(jRiskCode);
</script>

<script>
	$(document).ready(function(){
		initAllInput();
//initPayForm();
});
	</script>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body>
	
<DIV id=DivLCRiskButton STYLE="display:''"> 
  <Table>  
  	<TR>  
  		<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></TD> 
  	  <TD class= titleImg>险种信息</TD> 
  	</TR> 
 </Table>
</DIV>
<!--险种-->
<div class='divRisk'>
</div>
<hr>
<!--责任-->
<DIV id='DivLCRiskDuty' > 
	<Table> 
		<TR>  
			<TD class=common><IMG  src= '../common/images/butExpand.gif' style= 'cursor:hand;' ></TD> 
			<TD class= titleImg>可选责任信息</TD> 
		</TR> 
	</Table>
</DIV>
<div id='divDuty'  style='height:auto'>
		<!--table id='dutyGrid'>
		</table-->
</div>
<hr>
<!--缴费-->
<DIV id='DivLCRiskPay' > 
	<Table> 
		<TR>  
			<TD class=common><IMG  src= '../common/images/butExpand.gif' style= 'cursor:hand;' ></TD> 
			<TD class= titleImg>可选缴费信息</TD> 
		</TR> 
	</Table>
</DIV>
<div id='divPay'>
		<!--table id='payGrid'  ></table-->
</div>





<!--div id="inputdetail" class="demo-description">
	<input type='button' class=common  value='保  存' onclick='saveRiskFace();'>
</div--><!-- End demo-description -->


</body>
</html>
