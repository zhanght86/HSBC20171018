<%
//�������ƣ�LLColQueryHospitalInput.jsp
//�����ܣ�ҽԺ��Ϣά��
//�������ڣ�2005-9-6 9:48
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
    <SCRIPT src="LLColQueryHospital.js"></SCRIPT>
    <%@include file="LLColQueryHospitalInit.jsp"%>
</head>
<title>ҽԺ��Ϣ��ѯ</title>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryHospital);"></TD>
	        <TD class= titleImg>ҽԺ��Ϣ��ѯ</TD>
	     </TR>
	</Table>
	<Div  id= "QueryHospital" style= "display:''" class="maxbox1">
	    <Table  class= common>
            <TR class= common>
				<TD  class= title>ҽԺ����</TD>
				<TD  class= input> <input class="wid" class=common name=HospitalCodeQ id=HospitalCodeQ></TD>
                <TD  class= title>ҽԺ����</TD>
                <TD  class= input> <input class="wid" class=common name=HospitalNameQ id=HospitalNameQ></TD>
                <TD  class= title>ҽԺ�ȼ�</TD>
				<TD  class= input> <input class="wid" class=common name=HospitalGradeQ id=HospitalGradeQ></TD>
	       </TR>
            <TR class= common>
				<TD  class= title>�����������</TD><!--Modify by zhaorx 2006-05-23-->
				<TD  class= input> <input class="wid" class=common name=HospitalMngcomQ id=HospitalMngcomQ></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
				<TD  class= input></TD>
	       </TR>	       
	    </Table>
	</Div>
    <table style="display:none">
        <tr>
	        <td><input value="��  ѯ" class= cssButton type=button onclick="InitQueryClick();"></td>
	        <td><input value="��  ��" class= cssButton type=button onclick="returnParent();"></td>
        </tr>
    </table>
    <a href="javascript:void(0);" class="button" onClick="InitQueryClick();">��    ѯ</a>
   
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLColQueryHospitalGrid);"></TD>
	          <TD class= titleImg>�Ƽ�ҽԺ�б�</TD>
	     </TR>
	</Table>
    <Div id= "DivLLColQueryHospitalGrid" style= "display:''" align = center>
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanLLColQueryHospitalGrid"></span></TD>
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
 <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
    <!--���ر�����-->
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--��ѯ֪ͨ����-->

 	<input type=hidden id="tOperator" name=tOperator >
	<input type=hidden id="tComCode" name=tComCode >
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id="HosCode" name="HosCode"> <!--���ò�ѯ��Ӧ�Ĵ���input��-->
	<input type=hidden id="HosName" name="HosName"> <!--���ò�ѯ��Ӧ������input��-->
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
