<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWHealthInput.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
//�����¸���
String tContNo="";
String tFlag="";
GlobalInput tGI=new GlobalInput();
tGI=(GlobalInput)session.getValue("GI");
tFlag=request.getParameter("type");
%>
<script>
var contNo="<%=tContNo%>";          //���˵��Ĳ�ѯ����.
var operator="<%=tGI.Operator%>";   //��¼����Ա
var manageCom="<%=tGI.ManageCom%>"; //��¼��½����
var type="<%=tFlag%>";
var comcode="<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="RnewHealthInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="RnewHealthInit.jsp"%>
<title>ɨ��¼��</title>
</head>
<body  onload="initForm();initElementtype();" >
	<form action="./RnewHealthSave.jsp" method=post name=fm id=fm target="fraSubmit">
		<!-- ������Ϣ���� -->
		<table class=common border=0 width=100%>
			<tr>
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
				<td class=titleImg>�������ѯ������</td>
			</tr>
		</table>
        <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
		<table class=common>
			<TR class=common>
				<TD class=title5>��ͬ����</TD>
				<TD class=input5>		
        <Input class="wid" class= common name=QContNo id=QContNo > </TD>
				
				<TD class=title5>֪ͨ����ˮ��</TD>
				<TD class=input5>
				<Input class="wid" class= common name=QPrtSeq id=QPrtSeq > </TD>
							
				
			</tr>
			<tr id='SubTitle' style="display:none">
            <TD class=title5>��������</TD>
				<TD class=input5>
					
                    <Input class="coolDatePicker" onClick="laydate({elem: '#QHandleDate'});" verify="��������|date" dateFormat="short" name=QHandleDate id="QHandleDate"><span class="icon"><a onClick="laydate({elem: '#QHandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				<TD class=title5>������</TD>
				<TD class=input5>
					<Input class="wid" class= common name=QHandler id=QHandler > </TD>					
			</tr>
		</table></div>
		<INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick();">
		
		<table>
			<tr>
				<td class=common>
					<IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrp1);">
				</td>
				<td class=titleImg>������Ϣ</td>
			</tr>
			<INPUT type="hidden" class=Common name=MissionID  value="">
			<INPUT type="hidden" class=Common name=SubMissionID  value="">
		</table>
		<Div id="divLCGrp1" style="display: ''" align=center>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpGrid" ></span>
					</td>
				</tr>
			</table>
            
			
		</div>
		<p>
			<INPUT VALUE="��ʼ¼��" class=cssButton TYPE=button onclick="showHealthQ();">
		</p>
		<input class=common type=hidden name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden name=Operator >
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>







