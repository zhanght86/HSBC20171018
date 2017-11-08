<%@include file="../i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK" %>

<html>

<%
 //程序名称：PD_RiskFaceDefInput.jsp
 //程序功能：责任给付赔付扩充计算公式
 //创建日期：2009-3-16
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
 
 String mRiskCode =  request.getParameter("RiskCode");
String mstandbyflag1 = request.getParameter("StandbyFlag1");
// mRiskCode = "121301";
	//mRiskCode = "\'" +mRiskCode+"\'";
%>

<head>
	<meta charset="utf-8">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <!--script src="../common/javascript/jquery-1.5.1.js"></script-->
  <script src="../common/javascript/jquery.js"></script>
	
 
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.form.js"></script>
<script src="../common/javascript/jquery.easyui.js"></script>
<script src="../common/javascript/jquery.autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../common/css/jquery.autocomplete.css">
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <link rel="stylesheet" href="../common/css/jquery.ui.all.css">
 <SCRIPT src="PD_RiskFaceDef.js"></SCRIPT>


 <%@include file="PD_RiskFaceDefInit.jsp"%>
	<style>
	.otherChoose ul { width=100%;list-style-type: none; margin: 0; padding: 0; margin-bottom: 10px; }
	//.demo li { margin: 5px; padding: 5px; width: 150px; }
	.otherChoose li { float:left; width:24%; margin-left:10px; display:inline; height:30px; background:#F7F7F7; margin-top:10px;}
</style>
 <Script>
 		var jRiskCode ='<%=mRiskCode%>';
 		var jStandbyFlag1 = '<%=mstandbyflag1%>';
		var contOpt = '<%=request.getParameter("contopt")%>';
		
	//jRiskCode= "121301";
 	$(document).ready(function(){
			initForm();
	 //$("[id='riskParamsDiv']").html("<p>Hello <b>world</b>!</p>");


});

 	</Script>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="">
<div id="TeacherDiv" class="easyui-tabs" height="100%"> 
<div title="险种界面要素选择" style="padding:5px;" height="100%" id="TaskPlanInfo">

		<form id="RiskFaceParamsSave"> 
				<div title="险种录入要素"  style="overflow:auto;">
					<div id='riskParamsDiv' >
						<table  id='riskGrid'>
						</table>
				  </div>
				</div>
	<hr>
	<%
	if(mstandbyflag1.equals("01")){
		%>
		<div class='otherChoose'  style="overflow:auto;">
				<ul>
      		<li><input type=checkbox name=DutyChoose id="DutyChoose" value="Duty" OnClick= "checkDuty()">是否需要显示责任列表</td></li>
    		</ul>	
    		</div>
	<%	
	}
	%>
			<!-- IE11-Style-Fit:去掉Fit属性 -->
    		<div id='DutyColSelDiv' class="easyui-accordion" border="false">
    			<Table>  
  					<TR class=common>  
  	 				  <TD class= titleImg>责任列表显示列选择</TD> 
  					</TR> 
				  </Table>
					<div class='DutyGridDiv'>
					</div>
						

			  </div>
		<hr>
		<%
			if(mstandbyflag1.equals("01")){
		%>
		<div class='otherChoose'  style="overflow:auto;">
				<ul>
      		<li><input type=checkbox name=PayChoose id="PayChoose" value="Pay" OnClick= "checkPay()">是否需要显示缴费列表</td></li>
    		</ul>	
    	</div>
	<%	
	}
	%>
		<!-- IE11-Style-Fit:去掉Fit属性 -->
    	<div id='PayColSelDiv' class="easyui-accordion" border="false" >
					<Table>  
  					<TR class=common>  
  	 				  <TD class= titleImg>缴费列表显示列选择</TD> 
  					</TR> 
				  </Table>
					<div class='PayGridDiv'>
					</div>

			  </div>
		<hr>
		<input type='button' class=common  value='保  存' onclick='save();' style="display: <%="query".equals(request.getParameter("contopt")) ? "none":""%>">
		
	  <Input type=hidden name=RiskParams id=RiskParams>
		</form>
</div>
		

<div title="险种界面定义" style="padding:5px;" id="TaskPlanMonitoring">
	<iframe src="showRiskFace.jsp?RiskCode=<%=mRiskCode%>&StandbyFlag1=<%=mstandbyflag1%>&contopt=<%=request.getParameter("contopt")%>"  frameborder="0"  scrolling="auto" style="width:100%;height:100%;"></iframe>

</div>	
<%
	if(mstandbyflag1.equals("02")){
%>
<div title="投保界面控件过滤" style="padding:5px;" id="ControlElement">
	<iframe src="PDSugInsureControlEleInput.jsp?RiskCode=<%=mRiskCode%>&StandbyFlag1=<%=mstandbyflag1%>&contopt=<%=request.getParameter("contopt")%>"  frameborder="0"  scrolling="auto" style="width:100%;height:100%;"></iframe>

</div>
<%	
	}
%>	
</div>
</body>
</html>

