�?<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>基本任务管理</title>
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.form.js"></script>
<script src="../common/javascript/jquery.easyui.js"></script>
<script src="../common/javascript/common.min.js"></script>
<script src="../common/javascript/locale/easyui-lang-zh_CN.js"></script>
<script src="../common/javascript/jquery.autocomplete.js"></script>
<script src="../common/javascript/CodeList.js"></script>
<script src="../common/javascript/jquery.watermark.min.js"></script>

<script src="TaskBase.js"></script>
<sx:head parseContent="true"/>
	<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../common/css/jquery.autocomplete.css">
		<link rel="stylesheet" type="text/css" href="../common/css/watermark.css">
</head>

<body onload="initForm();" >

	<div id="TeacherDiv" class="easyui-tabs">

		<div title="基本任务信息" style="padding:5px;" id="TaskPlanInfo">
			<div id="buttons" align="right">
		<div id="divAddButton" style="display:''">
  		<a href="#" class="easyui-linkbutton" icon="icon-add" onclick="appendTask();">增加任务</a>
			<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="deleteTask();">删除任务</a>
		</div>
		</div>
	<form id="TaskPlan" method="POST" >
		<table id="TaskPlanTable">
	<tbody class="datagrid-body">
        <TR  class= common>
          <TD  class= title>
            基本任务编码
          </TD>
          <TD  class= input>
            <Input class=common  name=TaskCode id=TaskCode readonly="true">
          </TD>
          <TD  class= title>
            任务描述
          </TD>
          <TD  class= common>
          	<input type="text" name="TaskDescribe" id="TaskDescribe" >
          </TD>
          <TD  class= title>
            任务处理�?
          </TD>
          <TD  class= common>
							<input type="text" name="TaskClass" id="TaskClass" >
          </TD>
        </TR>
		</tbody>
   </table>
    <table id="TaskSet">     
		</table> 
    </form>
 </div>

 		
  </div>


		

</body>

</html>
