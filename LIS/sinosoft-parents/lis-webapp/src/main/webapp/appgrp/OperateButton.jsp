<%
//*******************************************************
//* 程序名称：OperateButton.jsp
//* 程序功能：页面中一般的增加，修改，删除，查询 按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*
//******************************************************
%>
<span id="operateButton" >
	<table  class=common >
		<tr align=left>
			<td class="common">
				<a href="javascript:void(0)" class=button name="addbutton" onclick="return submitForm();">增  加</a>
				<a href="javascript:void(0)" class=button name="updatebutton" onclick="return updateClick();">修  改</a>
				<a href="javascript:void(0)" class=button name="deletebutton" onclick="return deleteClick();">删  除</a>
				<!-- <INPUT class=cssButton name="addbutton" VALUE="增  加"  TYPE=button onclick="return submitForm();">
				<INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
				<INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();"> -->
			</td>
		</tr>
	</table>
</span>
