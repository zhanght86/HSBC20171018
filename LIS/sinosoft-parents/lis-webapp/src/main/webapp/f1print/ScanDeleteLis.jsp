
<%
//�������ƣ�ScanDeleteLis.jsp
//�����ܣ��ۺϴ�ӡ���б������嵥--��֤ɾ���嵥
//�������ڣ�2010-06-02
//������  ��hanabin
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String Branch = tG1.ComCode;
%>

<script language="javascript">
  var comCode ="<%=Branch%>";
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>

<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>

<SCRIPT src="./ScanDeleteLis.js"></SCRIPT>
<%@include file="./ScanDeleteLisInit.jsp"%>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<title>��֤ɾ���嵥</title>
</head>

<body onLoad="initForm();">
<form action="ScanDeleteLisSave.jsp" method=post name=fm  id=fm target="fraSubmit">
<table class= common border=0 width=100%>
    	<tr>   
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>  		
    		 <td class= titleImg>
        		�����ѯ����
       		 </td>   		      
    	</tr>
    </table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
<table class=common>
	<TR class=common>
		<TD class=title5>�������</TD>
		<TD class=input5><Input class=codeno name=ManageCom  id=ManageCom
        style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);"
			ondblclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);"
			onkeyup="return showCodeListKey('ComCode',[this,ManageComName],[0,1]);"><input
			class="codename" name="ManageComName" readonly></TD>
		<TD class=title5>ӡˢ��</TD>
				<TD class=input5>
					<Input class="common wid" name=PrtNo elementtype=nacessary MAXLENGTH="14" verify="Ͷ������|num&len=14">
				</TD>
	</TR>
	<TR class=common>
		<TD class=title5>ɾ����ʼ����</TD>
		<TD class=input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		<TD class=title5>ɾ����������</TD>
		<TD class=input5> <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
	</TR>
</table>
</div></div>
<p><!--������--> <INPUT VALUE="��   ѯ" class=cssButton TYPE=button onclick="easyQuery()"> 
<INPUT VALUE="��   ӡ" class=cssButton TYPE=button onClick="easyPrint();">

<br><br>
<input type=hidden id="fmtransact" name="fmtransact">
	
<Div id="divCodeGrid" style="display: ''" align=center>
<table class=common>
	<tr class=common>
		<td text-align: left colSpan=1><span id="spanCodeGrid"> </span>
		</td>
	</tr>
</table>
<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button
	onclick="getFirstPage();"> <INPUT VALUE="��һҳ" class=cssButton91
	TYPE=button onClick="getPreviousPage();"> <INPUT VALUE="��һҳ"
	class=cssButton92 TYPE=button onClick="getNextPage();"> <INPUT
	VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="getLastPage();">
</div>	
</form>
<span id="spanCode" style="display: none; position: absolute;"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>