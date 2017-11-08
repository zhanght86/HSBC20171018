<%
//*******************************************************
//* 程序名称：OperateButton.jsp
//* 程序功能：页面中一般的增加，修改，删除，查询 按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*
//******************************************************
%>
<span id="operateButton">
	<table class="common" align=center>
		<tr align=left >
			<!--<a href="javascript:void(0)" class="button" onclick="return addClick();">增  加</a>
			<a href="javascript:void(0)" class=button onclick="return updateClick();">修  改</a>
			<a href="javascript:void(0)" class=button onclick="return resetForm();">重  置</a>
			<a href="javascript:void(0)" class=button onclick="return queryClick();">查  询</a>-->
			 <INPUT class=cssButton VALUE="增  加"  TYPE=button onclick="return addClick();">
			<INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();">
			<INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">
			<INPUT class=cssButton VALUE="查  询"  TYPE=button onclick="return queryClick();">
		</tr>
	</table>
</span>
