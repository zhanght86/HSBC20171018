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
	<table  class=common >
		<tr align=left>
			<td class="common">
				<a href="javascript:void(0)" class=button name="addbutton" onclick="return submitForm();">��  ��</a>
				<a href="javascript:void(0)" class=button name="updatebutton" onclick="return updateClick();">��  ��</a>
				<a href="javascript:void(0)" class=button name="deletebutton" onclick="return deleteClick();">ɾ  ��</a>
				<!-- <INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">
				<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
				<INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();"> -->
			</td>
		</tr>
	</table>
</span>
