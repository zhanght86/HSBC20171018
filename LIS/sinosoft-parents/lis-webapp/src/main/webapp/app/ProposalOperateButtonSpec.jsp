<%
//*******************************************************
//* 程序名称：inputButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="inputButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				&nbsp;<INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton name=save VALUE="保  存"  TYPE=button onclick="return submitForm();">
			</td>
		</tr>
	</table>
</div>

<div id="inputQuest" style="display:'none'">
	<input type="button" class=cssButton name="Input" value="问题件录入" onClick="QuestInput()" class=cssButton>
	<input class=cssButton VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
	<input class=cssButton id="Donextbutton4" VALUE=" 处理完毕 " TYPE=button onclick="inputConfirm();">
</div>



<%
//*******************************************************
//* 程序名称：deleteButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="deleteButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="删  除"  TYPE=button onclick="return deleteClick();">
			</td>
		</tr>
	</table>
</div>
