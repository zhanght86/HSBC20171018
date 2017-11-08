<%
//*******************************************************
//* 程序名称：ProposalButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             欧阳晟   2002-05-20    新建
//******************************************************
%>
<div id="inputButton" style="display: none">
	<table class="common" align=center>
		<tr align=left>
			<a href="javascript:void(0)" class=button onclick="return cancelForm();">取  消</a>
			<a href="javascript:void(0)" class=button onclick="return resetForm();">重  置</a>
			<a href="javascript:void(0)" class=button onclick="return submitForm();">保  存</a>
			<!-- <INPUT class=cssButton VALUE="取  消"  TYPE=button onclick="return cancelForm();">
			<INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">
			<INPUT class=cssButton VALUE="保  存"  TYPE=button onclick="return submitForm();"> -->
		</tr>
	</table>
</div>
