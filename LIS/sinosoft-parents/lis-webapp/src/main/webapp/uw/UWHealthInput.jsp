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
<SCRIPT src="UWHealthInput.js"></SCRIPT>
 <script src="../common/javascript/jquery.workpool.js"></script>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="UWHealthInit.jsp"%>
<script src="../common/javascript/MultiCom.js"></script>
<title>ɨ��¼��</title>
</head>
<body  onload="initForm();initElementtype();" >
	<form action="./UWHealthSave.jsp" method=post name=fm id=fm target="fraSubmit">
	    <div id="UWHealthPool"></div>
	    <Input type=hidden id="Operator" name="Operator" value="">
	    <Input type=hidden id="ComCode" name="ComCode" value="">
		<Input type=hidden id="MissionID" name="MissionID" value="">
		<Input type=hidden id="SubMissionID" name="SubMissionID" value="">
		<Input type=hidden id="ActivityID" name="ActivityID" value=""> 
		<Input type=hidden id="QContNo" name="QContNo" value="">
	    <Input type=hidden id="QPrtSeq" name="QPrtSeq" value="">
		<Input type=hidden id="QHandler" name="QHandler" value="">
		<p>
			<!--<INPUT VALUE="��ʼ¼��" class=cssButton TYPE=button onclick="showHealthQ();">-->
            <a href="javascript:void(0);" class="button" onClick="showHealthQ();">��ʼ¼��</a>
		</p>
		
		<!-- ������Ϣ���� -->
		<!-- <table class=common border=0 width=100%>
			<tr>
				<td class=titleImg align=center>�������ѯ������</td>
			</tr>
		</table>
		<table class=common>
			<TR class=common>
				<TD class=title>ӡˢ��</TD>
				<TD class=input>		
        <Input class= common name=QContNo > </TD>
				</TD>
				<TD class=title>֪ͨ����ˮ��</TD>
				<TD class=input>
				<Input class= common name=QPrtSeq > </TD>
				</TD>				
				<TD class=title>��������</TD>
				<TD class=input>
					<Input class="multiDatePicker"  dateFormat="short" name=QHandleDate verify="��������|date" >
				</TD>
			</tr>
			<tr id='SubTitle' style="display:"none"">
				<TD class=title>������</TD>
				<TD class=input>
					<Input class= common name=QHandler > </TD>					
			</tr>
		</table>
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
			<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="getFirstPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="getNextPage();">
			<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="getLastPage();">
		</div>
		<p>
			<INPUT VALUE="��ʼ¼��" class=cssButton TYPE=button onclick="showHealthQ();">
		</p>
		<input class=common type=hidden name=tFlag value="<%=tFlag%>">
		<Input class=common type=hidden name=Operator >
		 -->
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>







