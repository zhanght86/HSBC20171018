<%
//*******************************************************
//* �������ƣ�inputButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="inputButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				&nbsp;<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return resetForm();">
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton name=save VALUE="��  ��"  TYPE=button onclick="return submitForm();">
			</td>
		</tr>
	</table>
</div>

<div id="inputQuest" style="display:'none'">
	<input type="button" class=cssButton name="Input" value="�����¼��" onClick="QuestInput()" class=cssButton>
	<input class=cssButton VALUE=�������ѯ TYPE=button onclick="QuestQuery();">
	<input class=cssButton id="Donextbutton4" VALUE=" ������� " TYPE=button onclick="inputConfirm();">
</div>



<%
//*******************************************************
//* �������ƣ�deleteButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="deleteButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
			</td>
		</tr>
	</table>
</div>
