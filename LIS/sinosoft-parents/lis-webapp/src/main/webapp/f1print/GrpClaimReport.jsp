
<%
	//��������:�������ⱨ���ӡ
	//������:�������ⱨ���ӡ
	//�������ڣ�2009-06-01
	//������  ��mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
	// * <p>GrpClaim1�����������±���</p>
	// * <p>GrpClaim2���������������⸶ͳ�Ʊ���</p>
	// * <p>GrpClaim3����������δ�᰸���嵥����</p>
	// * <p>GrpClaim4���������������嵥</p>
	// * <p>GrpClaim5������������˰����嵥����</p>
	// * <p>GrpClaim6���������ⰸ��ʱЧ����</p>
	// * <p>GrpClaim7���鿱����ͳ��</p>
	// * <p>GrpClaim8��������ͳ���嵥</p>
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String ComCode = tG1.ComCode;
	String Operator = tG1.Operator; //����Ա

	String CurrentDate = PubFun.getCurrentDate();
	String mDay[] = PubFun.calFLDate(CurrentDate);

	String Code = request.getParameter("Code");
	loggerDebug("GrpClaimReport","url�Ĳ���Ϊ:" + Code);

	LDMenuDB tLDMenuDB = new LDMenuDB();
	String name = "../f1print/GrpClaimReport.jsp?Code=" + Code;
	tLDMenuDB.setRunScript(name);
	LDMenuSet mLDMenuSet = tLDMenuDB.query();
	String CodeName = mLDMenuSet.get(1).getNodeName();
%>

<html>
<head>
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
<LINK href="Project.css" rel=stylesheet type=text/css>
<script language="javascript">
function initForm(){
  var code="<%=Code%>";
  if(code=="GrpClaim3"){ //�˻��ʣ���Ҫѡ��ͳ�����ͣ�1-�ֹ�˾�˻��ʣ�2-�ܹ�˾�˻���
    divStatType.style.display='';
  }else{
    divStatType.style.display='none';
  }
}

function submitForm(){	
	//У��ҳ����Ϣ
	if(verifyInput() == false)
	{
    	return false;
	}	
	
    var code="<%=Code%>";
	if(code=="GrpClaim3" && fm.StatType.value==""){
	  alert("��������δ�᰸���嵥������Ҫ¼��ͳ������!");
	  return false;
	}
	
	fm.submit();
   }  
</script>
</head>

<body onLoad="initForm();initElementtype();">
<form action="./GrpClaimReportF1Print.jsp" method=post name=fm id=fm
	target="PRINT">
    <div class="maxbox1" >
<Table class=common>
	<TR class=common>
		<TD class=title5>��������</TD>
		<TD class=input5><input  class="readonly wid" name=CodeName value=<%=CodeName%>></TD>
		<TD class=title5>ͳ�ƻ���</TD>
		<TD class=input5><Input class=codeno name="MngCom"
			verify="ͳ�ƻ���|NOTNULL"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,MngComName],[0,1],null,StrSql,1,1);"

			ondblclick="return showCodeList('station',[this,MngComName],[0,1],null,StrSql,1,1);"
			onkeyup="return showCodeListKey('station',[this,MngComName],[0,1],null,StrSql,1,1);"><input
			class=codename name="MngComName" readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
	</TR>
	<%
if(Code.equals("GrpClaim2")){
%>
	<TR class=common>
		<TD class=title5>��������</TD>
		<TD class=input5> <Input class="codeno"  name=CaseType verify="��������|NOTNULL" readonly 
             CodeData="0|^01|����|^03|����|^11|��ͨ"
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('CaseType',[this,CaseTypeName],[0,1]);"
            ondblClick="showCodeListEx('CaseType',[this,CaseTypeName],[0,1]);"
            onkeyup="showCodeListKeyEx('CaseType',[this,CaseTypeName],[0,1]);"><input class=codename name=CaseTypeName elementtype=nacessary readonly></TD>
		<TD class=title5>��������</TD>
		<TD class=input5><Input class="codeno"  name=CaseResult verify="��������|NOTNULL" readonly 
             CodeData="0|^0|���������ָ���|^1|ȫ������" 
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('CaseResult',[this,CaseResultName],[0,1]);"
            ondblClick="showCodeListEx('CaseResult',[this,CaseResultName],[0,1]);"
            onkeyup="showCodeListKeyEx('CaseResult',[this,CaseResultName],[0,1]);"><input class=codename name=CaseResultName elementtype=nacessary readonly></TD></TD>
	</TR>
	<%
	}
%>
<%
if(!Code.equals("GrpClaim8")){
%>
	<TR class=common>
		<TD class="title5">��ʼ����</TD>
		<TD class="input5">
            <Input class="coolDatePicker"   onClick="laydate({elem: '#StartDate'});"dateFormat="short"value="<%=mDay[0]%>" name="StartDate" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span><font color='#ff0000'><b>*</b></font></TD>
            </TD>
		<TD class=title5>��������</TD>
		<TD class=input5>
            <Input class="coolDatePicker"   onClick="laydate({elem: '#EndDate'});"dateFormat="short"value="<%= mDay[1]%>" name="EndDate" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span><font color='#ff0000'><b>*</b></font></TD>
	</TR>
	<%
	}
%>
	<TR class=common>
		<TD class=input style="display: none"><Input class=common
			name=Code value=<%=Code%>></TD>
	</TR>
</Table>
</div>

<div id="divStatType" style="display: 'none'">
<Table class=common>
	<TR class=common>
		<TD class=title5>ͳ������</TD>
		<TD class=input5><Input class=codeno name=StatType
			CodeData="0|^0|ȫ��^5|5��δ�᰸��^10|10��δ�᰸��^30|30��δ�᰸��"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('StatType',[this,StatTypeName],[0,1],null,null,null,1);"
			ondblClick="showCodeListEx('StatType',[this,StatTypeName],[0,1],null,null,null,1);"
			onkeyup="showCodeListKeyEx('StatType',[this,StatTypeName],[0,1],null,null,null,1);"><Input
			class=codename name=StatTypeName readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</Table>
</div>


<INPUT class=cssButton VALUE="��ӡ����" TYPE=button onClick="submitForm()">
<!--<a href="javascript:void(0);" class="button"onclick="easyQuery();">��ӡ����</a>-->
</form>
<span id="spanCode" style="display: none; position:absolute; slategray">
</span>
</body>
</html>
<script>
var StrSql = "1 and length(comcode)<8 and comcode like #"+<%=ComCode%>+"%#";
</script>
