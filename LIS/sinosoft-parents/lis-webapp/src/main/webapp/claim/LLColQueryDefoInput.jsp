<%
//�������ƣ�LLColQueryDefoInput.jsp
//�����ܣ��˲д����ѯ
//�������ڣ�2005-9-6 17:46
//������  ��zhoulei
//���¼�¼��
%>
<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--�û�У����-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>

<head >
	<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String tCode = request.getParameter("codeno");
    String tName = request.getParameter("codename");
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLColQueryDefo.js"></SCRIPT>
    <%@include file="LLColQueryDefoInit.jsp"%>
</head>
<title>�˲д����ѯ</title>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <br>
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryDefo);"></TD>
	        <TD class= titleImg>�˲д����ѯ</TD>
	     </TR>
	</Table>
	<Div  id= "QueryDefo" class=maxbox1 style= "display:''">
	    <Table  class= common>
            <TR class= common>
				<TD  class= title> �м�����</TD>
				<TD  class= input> <Input class=codeno name=DefoType id=DefoType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onkeyup="showCodeListKey('lldefotype',[this,DefoTypeName],[0,1]);"><input class=codename name=DefoTypeName id=DefoTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title> �˲м���</TD>
                <TD  class= input> <Input class=codeno name=DefoGrade id=DefoGrade style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lldefograde',[this,DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');" onkeyup="return showCodeListKey('lldefograde',[this,DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');"><input class=codename name=DefoGradeName id=DefoGradeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title> �˲�����</TD>
                <TD  class= input> <input class="common wid" name=DefoCodeName id=DefoCodeName ></TD>
	       </TR>
	    </Table>
	</Div>
    <table>
        <tr>
	        <td><input value="��  ѯ" class= cssButton type=button onclick="InitQueryClick();"></td>
	        <td><input value="��  ��" class= cssButton type=button onclick="returnParent();"></td>
        </tr>
    </table>
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLColQueryDefoGrid);"></TD>
	          <TD class= titleImg>�˲д����б�</TD>
	     </TR>
	</Table>
    <Div id= "DivLLColQueryDefoGrid" style= "display:''" align = center>
		<Table  class= common>
		    <TR>
		    	<TD style="text-align: left" colSpan=1><span id="spanLLColQueryDefoGrid"></span></TD>
		    </TR>
		</Table>
        <table>
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>
	</Div>

    <!--���ر�����-->
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--��ѯ֪ͨ����-->

 	<input type=hidden id="tOperator" name=tOperator >
	<input type=hidden id="tComCode" name=tComCode >
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id="HosCode" name="HosCode"> <!--���ò�ѯ��Ӧ�Ĵ���input��-->
	<input type=hidden id="HosName" name="HosName"> <!--���ò�ѯ��Ӧ������input��-->
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
