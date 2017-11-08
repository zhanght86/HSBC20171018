<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2005-01-26 13:18:16
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/javascript/json2.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
 <SCRIPT src="../common/javascript/jquery-1.7.2.js"></SCRIPT> 

  <SCRIPT src="../common/javascript/jquery.easyui.min.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>

  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <%@include file="ConfigLDMaxNoRuleInit.jsp"%>
  <SCRIPT src="ConfigLDMaxNoRuleInput.js"></SCRIPT>
</head>
<%
	String mNoCode = request.getParameter("NoCode");
	String mLimitType = request.getParameter("LimitType");
%>

<script>
	var jNoCode = '<%=mNoCode%>'
	var jLimitType = '<%=mLimitType%>'
	$(document).load(function(){
		initLDMaxNoElement();
});
	</script>
<style type="text/css">
		.drag{
		z-index: 9;
			width:80px;
			height:15px;
			padding:10px;
			margin:5px;
			text-align: center;
			border:1px solid #ccc;
			background:#AACCFF;
			float:left;
			
		}
		.dp{
			opacity:0.5;
			filter:alpha(opacity=50);
		}
		.over{
			z-index: 4;
			background:#D7E1F6;
		}
		.finally{
			text-align: center;
			float:left;
		}
	.draggable-model-proxy{
			width:80px;
			height:15px;
			padding:10px;
			margin:5px;
			text-align: center;
			border:1px solid #ccc;
			background:#AACCFF; 
      position="absolute"; //    
			opacity:0.7;
			z-index:20;
			filter:alpha(opacity=70);
}
	</style>
	<script>
		
		
	</script>
</head>
<body  style="width:850px;height:450px;padding:5px;">
	<table  class=common align=center>
		<tr align=right>
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="addbutton" VALUE="保    存"  TYPE=button onclick="return saveRule();">
				<INPUT class=cssButton id="previewButton" name="addbutton" VALUE="预览号段"  TYPE=button onclick="return previewRule();">
				<INPUT class=cssButton name="addbutton" VALUE="返    回"  TYPE=button onclick="return window.close();">
			</td>
		</tr>
	</table>
<!--div style="height:60px">
<table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand; ">
    		</td>
    		 <td class= titleImg>
        		 号段生成规则配置
       		 </td>   		 
    	</tr>
</table>
</div-->

<hr class=line>
	<form  method=post name=fm id=fm target="fraSubmit">

<div id='div_layout' class="easyui-layout" style="width:700px;height:400px;">
<div region="north" id='northDiv' title="号段规则预览"  style="overflow:hidden;height:60px;padding:10px">	
		<div id='ShowDetail' style="height:20px"></div>
</div>

	<div region="west" id="source"  title="号段元素" split="true" style="width:100px;">
	<!--div id="source"  class="palette-menu" style="border:1px solid #ccc;width:80px;height:400px;float:left;margin:5px;">
	</div-->
</div>
<div region="center" id="target"  title="号段规则" split="true" >
	<!--div  id="target" style="border:1px solid #ccc;width:500px;height:200px;float:left;margin:5px;">
		<p align=center class="pClassCenter" >号段规则</p>
	</div-->
</div>

</div>	
	<!--菜单-->
	<div id="NoMenu" class="easyui-menu" style="width:120px;">
		<div onclick="showProperties();">属性</div>
		<div onclick="deleteElement();">删除</div>
	</div>
	

	<!--属性-->
	<div id="dProperties" closed="true"  class="easyui-dialog" title="属性配置"  style="padding:5px;width:620px;height:250px;">
      <div id="divProps" >
   		</div>
	</div>
	
	<!--预览-->
	<div id="dPreview" closed="true"  class="easyui-dialog" title="号段预览"  style="padding:5px;width:200px;height:100px;">
      <div id="divPreview" >
   		</div>
	</div>
	
	<div id='hiddenElement' value=''></div>
</form>
</body>
</html>