
<%
	//��������:�������ⱨ���ӡ
	//������:�������ⱨ���ӡ
	//�������ڣ�2009-05-25
	//������  ��mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
	// * <p>claim1��1��������ʱЧ</p>
	// * <p>claim2��2��������ʱЧ</p>
	// * <p>claim3��3�����˲���ʱЧ</p>
	// * <p>claim4��4������</p>
	// * <p>claim5��5����ʱЧ</p>
	// * <p>claim6��6�鿱����ͳ��</p>
	// * <p>claim7��7���ͼ���</p>
	// * <p>claim8��8���ͼ�����������������</p>
	// * <p>claim9��9���ͼ����������ַ��ࣩ</p>
	// * <p>claim10��10�᰸��</p>
	// * <p>claim11��11���ڳ���ͳ��</p>
	// * <p>claim12��12�˻���</p>
	// * <p>claim13��13������</p>
	// * <p>claim14��14������</p>
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String ComCode = tG1.ComCode;
	String Operator = tG1.Operator; //����Ա

	String CurrentDate = PubFun.getCurrentDate();
	String mDay[] = PubFun.calFLDate(CurrentDate);

	String Code = request.getParameter("Code");
	loggerDebug("ClaimReport","url�Ĳ���Ϊ:" + Code);

	LDMenuDB tLDMenuDB = new LDMenuDB();
	String name = "../f1print/ClaimReport.jsp?Code=" + Code;
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
<script type="text/javascript">
var StrSql = "1 and char_length(comcode)<8 and comcode like #"+<%=ComCode%>+"%#";
function initForm(){
	//alert("����initform");
  var code="<%=Code%>";
  if(code=="claim12"){ //�˻��ʣ���Ҫѡ��ͳ�����ͣ�1-�ֹ�˾�˻��ʣ�2-�ܹ�˾�˻���
    divStatType.style.display='';
  }
  if(code=="claim3"){ //������
    divOperator.style.display='';
  }
  if(code == "claim9"){ //���ͼ����������ַ��ࣩ
    divRiskApp.style.display = '';
  }
  if(code == "EverySection"){
	  divCaseSection.style.display = '';
	  
  }
}

function submitForm(){	
	//alert("����submitform����");
	//У��ҳ����Ϣ
	if(verifyInput() == false)
	{
    	return false;
	}
	if(dateDiff(fm.StartDate.value,fm.EndDate.value,'M') > 6){
		alert("ʱ��������С��������");
		return false;
	}	
	
	
	
    var code="<%=Code%>";
	if(code=="claim12" && fm.StatType.value==""){
	  alert("�˻��ʱ�����Ҫ¼��ͳ������!");
	  return false;
	}
	if(code == "EverySection" && fm.CaseSection.value==""){
		alert("�����׶β���Ϊ��");
		return false;
	}
	//alert("submit֮ǰ");
	fm.submit();
	//alert("submit֮��");
   }  
</script>
</head>

<body onLoad="initForm()">
<form action="./ClaimReportF1Print.jsp" method=post name=fm id="fm"
	target="PRINT">
     <div class="maxbox1" >
<Table class=common>
	<TR class=common>
		<TD class=title5>��������</TD>
		<TD class=input5><input  readonly class="readonly wid" name=CodeName id="CodeName" value=<%=CodeName%>></TD>

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
	<TR class=common>
		<TD class=title5>��ʼ����</TD>
		<TD class=input5>
            <input class="coolDatePicker" dateFormat="short"value="<%= mDay[0]%>" name="StartDate"  id="StartDate" onClick="laydate({elem:'#StartDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
             <font color='#ff0000'><b>*</b></font></TD>
		<TD class=title5>��������</TD>
		<TD class=input5>
            <input class="coolDatePicker"dateFormat="short" value="<%= mDay[1]%>" name="EndDate"  id="EndDate" onClick="laydate({elem:'#EndDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <font
			color='#ff0000'><b>*</b></font></TD>
	</TR>
	<TR class=common>
		<TD class=input5 style="display: none"><Input class=common
			name=Code value=<%=Code%>></TD>
	</TR>
</Table>
</div>
<div id="divStatType" style="display:none">
<Table class=common>
	<TR class=common>
		<TD class=title5>ͳ������</TD>
		<TD class=input5><Input class=codeno name=StatType
			CodeData="0|^1|�ֹ�˾�˻���^2|�ܹ�˾�˻���"
			ondblClick="showCodeListEx('StatType',[this,StatTypeName],[0,1]);"
			onkeyup="showCodeListKeyEx('StatType',[this,StatTypeName],[0,1]);"><Input
			class=codename name=StatTypeName readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</Table>
</div>

<div id="divCaseSection" style="display:none">
<Table class=common>
	<TR class=common>
			<TD class=title>�����׶�</TD>
		<TD class=input><Input class=codeno name="CaseSection" 
			CodeData="0|^1|����^2|����^3|����^4|���^5|����^6|�ֹ�˾ǩ��^7|�ܹ�˾ǩ��"
			
			ondblclick="return showCodeListEx('CaseSection',[this,CaseSectionName],[0,1]);"
			onkeyup="return showCodeListKeyEx('CaseSection',[this,CaseSectionName],[0,1]);"><input
			class=codename name="CaseSectionName"  readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</Table>
</div>





<div id="divOperator" style="display: none">
<Table class=common>
	<TR class=common>
		<TD class=title5>������</TD>
		<TD class=input5><Input class=codeno name="defaultOperator" 
			ondblclick="return showCodeList('optname',[defaultOperatorName,this],[0,1],null,1,1,1);"
			onkeyup="return showCodeListKey('optname',[defaultOperatorName,this],[0,1],null,1,1,1);"><input
			class=codename name="defaultOperatorName"  readonly=true></TD>
		<TD class=title5></TD>
		<TD class=input5></TD>
	</TR>
</Table>
</div>

<div id="divRiskApp" style="display:none">
<Table class=common>
	<TR class=common>
			<TD class=title>���ִ���</TD>
		<TD class=input><Input class=codeno name="Risk" value="ȫ��"
			verify="���ִ���|NOTNULL"
			ondblclick="return showCodeList('selrisk',[this,RiskName],[0,1],null,null,1,1);"
			onkeyup="return showCodeListKey('selrisk',[this,RiskName],[0,1],null,null,1,1);"><input
			class=codename name="RiskName" value="ȫ��" readonly=true><font
			color='#ff0000'><b>*</b></font></TD>
		<TD class=title></TD>
		<TD class=input></TD>
	</TR>
</Table>
</div>
</div>
<!--<a href="javascript:void(0);" class="button" onclick="submitForm();">��ӡ����</a>-->

<INPUT class=cssButton VALUE="��ӡ����" TYPE=button onClick="submitForm()"></input>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray">
</span>
</body>
</html>

