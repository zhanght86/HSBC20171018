<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
GlobalInput tG1 =new GlobalInput();
tG1=(GlobalInput)session.getValue("GI");
String Operator =tG1.Operator;
String ComCode = tG1.ComCode;
%>
<%
//�������ƣ�menuGrpNew.jsp
//�����ܣ��˵��������
//�������ڣ�2002-10-10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
 <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="menuGrpNew.js"></SCRIPT>
<script src="treeMenu.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="menuGrpInitNew.jsp"%>
</head>
<body onload="initForm();">
	<form action="./menuGrpManNew.jsp" method=post name=fm target="fraSubmit">
		<table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			<td class= titleImg>�������ѯ������</td>
		</tr>
	</table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
		<Table class="common">
			<TR class=common style="display:none">
				<TD class=title5>����Ա</TD>
				<TD class=input5>
					<Input class="wid" class=common name=Operator value="<%=Operator%>">
				</TD>
				<TD class=title5>��½����</TD>
				<TD class=input5>
					<Input class="wid" class=common name=ComCode value="<%=ComCode%>">
			</TR>
			<TR  class=common>
				<TD class=title5>����Ա����</TD>
				<TD class=input5>
					<Input class="wid" class=common name=UserCode id=UserCode >
				</TD>
                <TD class=title5></TD>
                <TD class=input5></TD>
				 <!--td  class=title></td>
          <td  class=common ></td>
				</TD-->
			</TR>
		</Table></div>
		<!--<INPUT VALUE="��ѯ�˵���" TYPE=button onclick="queryClick()" class="cssButton">-->
        <a href="javascript:void(0);" class="button" onClick="queryClick();">��ѯ�˵���</a>
		<!--<Div id=divCmdButton style="display: ''">-->
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divQueryGrp);">
				</td>
				<td class=titleImg>�˵�����Ϣ</td>
			</tr>
		</table>			
		
		<Div  id="divQueryGrp" style="display: ''">
			<table class=common>
				<tr>
					<td text-align: left colSpan=1>
						<span id="spanQueryGrpGrid"></span>
					</td>
				</tr>
			</table>
		
    </div>
    
    <div class="maxbox1">
	<Table class="common">
	<TR class=common>
		
			<TD class=title5>�˵������</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpCode id=MenuGrpCode>
			</TD>
		
			<TD class=title5>�˵�������</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpName id=MenuGrpName>
				</TD>
			</TR>
			<TR class =common>
				<TD class=title5>�˵�������</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuGrpDescription id=MenuGrpDescription>
				</TD>
				<TD class=title5>�˵���־</TD>
				<TD class=input5>
					<Input class="wid" class=common name=MenuSign id=MenuSign>
				</TD>
			</TR>
			<tr>
					<TD class=input style="display: none">
						<Input class="code" name=Action>
					</TD>
			</tr>
		</Table>		
	</div>
		<!--<input type=button value="����"  onclick="DataCopy()" class="cssButton" >-->
        <a href="javascript:void(0);" class="button" onClick="DataCopy();">��    ��</a>
        <br><br><br><br>
	</form>
</body>
</html>
