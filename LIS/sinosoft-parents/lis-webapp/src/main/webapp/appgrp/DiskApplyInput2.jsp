<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<head>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>

	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<script language='javascript'>
var showInfo;
var turnPage=new turnPageClass();
var arrDataSet;
var ImportPath;
var grpcontno="<%=request.getParameter("grpcontno")%>";
var ImportState="no";
window.onbeforeunload=beforeAfterInput;
window.onunload=AfterInput;
function beforeAfterInput()
{
	if ( ImportState=="Importing" )
	{
		alert("����Ͷ����δ��ɣ��벻Ҫ�뿪!");
		return false;
	}
}

function AfterInput()
{
	if ( ImportState=="Importing" )
	{
		return false;
	}
}

function submitForm()
{
	if ( ImportState=="Succ")
	{
		if ( !confirm("ȷ����Ҫ�ٴδ���Ͷ����?") )
		{
			return false ;
		}
	}
	fm.ImportButton.disabled=true;
	var i=0;
	getImportPath();
	ImportFile=document.all('FileName').value;
	var prtno=getPrtNo();
	var tprtno=ImportFile;

	if ( tprtno.indexOf("\\")>0 ) tprtno=tprtno.substring(tprtno.lastIndexOf("\\")+1);
	if ( tprtno.indexOf("/")>0 ) tprtno=tprtno.substring(tprtno.lastIndexOf("/")+1);
	if ( tprtno.indexOf("_")>0) tprtno=tprtno.substring( 0,tprtno.indexOf("_"));
	if ( tprtno.indexOf(".")>0) tprtno=tprtno.substring( 0,tprtno.indexOf("."));

	if ( prtno!=tprtno )
	{
		alert("�ļ�����ӡˢ�Ų�һ��,�����ļ���!");
		return ;
	}
	else
	{
		document.all("info").innerText="�ϴ��ļ������У����Ժ�..."
		document.all('ImportPath').value = ImportPath;
		ImportState="Importing";
//		fm.action="./DiskApplySave.jsp?ImportPath="+ImportPath;
lockScreen('lkscreen');  
		document.getElementById("fm").submit(); //�ύ
	}
}

function getImportPath ()
{
	// ��дSQL���
	var strSQL="";
	strSQL="select SysvarValue from ldsysvar where sysvar='XmlPath'";
	var arrResult = easyExecSql(strSQL);
	//alert(arrResult);
	ImportPath=arrResult[0][0];
}

function getPrtNo ()
{
	// ��дSQL���
	var strSQL="";
	strSQL="select PrtNo from lcgrpcont where grpcontno='"+ grpcontno +"'";
	var arrResult = easyExecSql(strSQL);
	//�ж��Ƿ��ѯ�ɹ�
	if (arrResult==null)
	{
		alert("δ���ҵ�����:" + grpcontno);
		return;
	}
	
	return arrResult[0][0];
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ,Result )
{
	unlockScreen('lkscreen');  
	document.all("info").innerText=content;
	ImportState=FlagStr ;
	//alert("�����ļ���������������ӿ����жϣ������˳�ϵͳ���µ�¼��");
	fm.ImportButton.disabled=false;
	
}
</script>
</head>
<body>
	<form action="./DiskApplySave.jsp" method=post name=fm id="fm" target="fraSubmit" ENCTYPE="multipart/form-data">
		<table class=common>
			<TR>
				<TD width='20%' style="font:9pt">�ļ���</TD>
				<TD width='80%'>
					<input type=hidden name="ImportPath" id="ImportPath">
					<Input type="file" width="100%" name=FileName id="FileName">
					<a href="javascript:void(0)" class=button name="ImportButton" id="ImportButton" onclick="submitForm();">��  ��</a>
					<!-- <INPUT VALUE="����" class="cssButton" name="ImportButton" TYPE=button onclick="submitForm();"> -->
					<Input type="hidden"��width="100%" name="insuredimport" id="insuredimport" value="1">
				</TD>
			</TR>
			<TR>
				<TD colspan=2>
				</TD>
			</TR>
			<input type=hidden name=ImportFile id="ImportFile">
		</table>
		<table class=common>
			<TR>
				<TD id="info" width='100%' style="font:10pt"></TD>
			</TR>
		</table>
	</form>
</body>
