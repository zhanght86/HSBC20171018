<%
//*******************************************************
//* �������ƣ�OperateButton.jsp
//* �����ܣ�ҳ����һ������ӣ��޸ģ�ɾ������ѯ ��ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*
//******************************************************
%>
<span id="operateButton" >
	<table  class=common align=center>
		<tr align=right>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">
			</td>
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
			</td>			
<!--			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="querybutton" VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">
			</td>		-->
			<td class=button width="10%" align=right>
				<INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
			</td>
		</tr>
	</table>
</span>
