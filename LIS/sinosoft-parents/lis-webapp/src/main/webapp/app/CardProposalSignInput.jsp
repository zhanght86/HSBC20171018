<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	//��½��Ϣ
	GlobalInput tGI = new GlobalInput();
	if (session.getValue("GI")==null)
	{
		loggerDebug("CardProposalSignInput","null");
	}
	else
	{
		tGI = (GlobalInput)session.getValue("GI");
	}
	
%>
<script>
	//var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var ComCode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="CardProposalSign.js"></SCRIPT>
	<%@include file="CardProposalSignInit.jsp"%>
	<title>ǩ������ </title>
</head>

<body  onload="initForm();" >
<form action="./CardProposalSignSave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<!--#################### ������Ϣ��ѯ�������� ##############################-->
	<Table class= common border=0 width=100%>
		<TR>
			<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
			<td class= titleImg align= center>�������ѯ������������������һ���</td>
		</TR>
	</Table>
	<div class="maxbox1">
	<Div  id= "divFCDay" style= "display: ''"> 
	<Table class= common align=center>
		<TR  class= common>
			<TD  class=title5>Ͷ������</TD>
			<TD  class=input5><Input class= "common wid" name=ContNo id="ContNo"></TD>
			<TD  class=title5>�������</TD>
			<TD  class= input5>
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ManageCom id="ManageCom" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>Ͷ����</TD>
			<TD  class= input5><input class= "common wid" name=AppntName id="AppntName"></TD>  
			<TD  class= title5>ҵ��Ա����</TD>
			<TD  class=input5>
				<input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME="AgentCode" id="AgentCode" CLASS="code" elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" onclick="return queryAgent();"  ondblclick="return queryAgent();" >
			</TD>
		</TR>
		<TR  class= common> 
			<TD  class= title5>¼������(��ʼ)</TD>
			<TD  class= input5>
				<Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
			<TD  class= title5>¼������(��ֹ)</TD>
			<TD  class= input5>
				<Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
		</TR>
		<TR class=common>
		  <TD CLASS=title5>ӡˢ����</TD>
			<TD CLASS=input5 COLSPAN=1>
				<Input NAME=PrtNo id="PrtNo" VALUE="" class= "common wid"  TABINDEX=-1 MAXLENGTH=14>
			</TD>
		</TR>
	</Table>
	</Div>
	</div>
	<a href="javascript:void(0)"  class=button onclick="easyQueryClick();">��  ѯ</a>
	<!-- <INPUT VALUE="��  ѯ" class=cssButton TYPE=button onclick="easyQueryClick()">  -->
	
	<Table>
		<TR>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCardPol);">
			</td>
			<td class= titleImg>��ǩ��������Ϣ </td>
		</TR>
	</Table>
	
	<Div  id= "divCardPol" style= "display: ''" align = center>
		<Table  class= common>
			<TR  class= common>
				<td text-align: left colSpan=1>
					<span id="spanCardPolGrid" ></span> 
				</td>
			</TR>
		</Table>
		<INPUT CLASS=cssButton90 VALUE="��  ҳ" class= cssButton TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT CLASS=cssButton91 VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT CLASS=cssButton92 VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT CLASS=cssButton93 VALUE="β  ҳ" class= cssButton TYPE=button onclick="turnPage.lastPage();">
	</Div>
	<br>
	<a href="javascript:void(0)"  class=button name="signbutton" id="signbutton" onclick="signCardPol();">ǩ������</a>
	<!-- <INPUT VALUE="ǩ������" class= cssButton name="signbutton" id="signbutton" TYPE=button onclick="signCardPol()">  -->
	
	<!-- <#################���ر�����#############################>
	 <div id="divHidden" style="display:none">
		<input class=common name=ProposalContNo> <Ͷ��������>
		<input class=common name=ContCardNo>     <��     ��>
	</div> -->
</form>
<br>
<br>
<br>
<br>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
