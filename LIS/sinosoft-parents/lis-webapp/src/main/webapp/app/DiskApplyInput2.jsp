<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-21 09:25:18
//������ ��HST
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<head>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<script language='javascript'>
var showInfo;
var turnPage=new turnPageClass();
var arrDataSet;
var ImportPath;
var grpcontno="<%=request.getParameter("grpcontno")%>";
var cImportFlag="<%=request.getParameter("ImportFlag")%>";
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
		fm.submit(); //�ύ
	}
}

function getImportPath ()
{
	// ��дSQL���
	var strSQL="";

	    var sqlid28="ContQuerySQL28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
		mySql28.setSqlId(sqlid28); //ָ��ʹ�õ�Sql��id
		strSQL=mySql28.getString();
	
	//strSQL="select SysvarValue from ldsysvar where sysvar='ImportPath'";
	
	arrResult = easyExecSql(strSQL, 1, 1);
    //alert(Result);
    if (arrResult != null && arrResult.length > 0)
    {
        ImportPath = arrResult[0][0];       
    }
    else
    {
        alert("δ�ҵ��ϴ�·��");
        return;
    }
}

function getPrtNo ()
{
	// ��дSQL���
	var strSQL="";

    var sqlid29="ContQuerySQL29";
	var mySql29=new SqlClass();
	mySql29.setResourceName("app.ContQuerySQL"); //ָ��ʹ�õ�properties�ļ���
	mySql29.setSqlId(sqlid29); //ָ��ʹ�õ�Sql��id
	mySql29.addSubPara(grpcontno); //ָ������Ĳ���
	strSQL=mySql29.getString();
	
	//strSQL="select PrtNo from lcgrpcont where grpcontno='"+ grpcontno +"'";
	turnPage.strQueryResult =easyQueryVer3(strSQL, 1, 1, 1);
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult)
	{
		alert("δ���ҵ�����:" + grpcontno);
		return;
	}
	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage.arrDataCacheSet=clearArrayElements(turnPage.arrDataCacheSet);
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet=decodeEasyQueryResult(turnPage.strQueryResult);
	return turnPage.arrDataCacheSet[0][0];
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ,Result,BatchNo )
{
	ImportState=FlagStr;	
	if (FlagStr == "Fail")
	{
	    parent.close();
	    showError(content,BatchNo);
	    return;
	}
	document.all("info").innerText=content;
}

function showError(cContent,cBatchNo){
     window.open("DiskApplyErrorInput.jsp?GrpContNo=" + grpcontno + "&ImportFlag=" + cImportFlag + "&ErrorInfo=" +cContent + "&BatchNo=" +cBatchNo,"", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
}
</script>
</head>
<body>
	<form action="./DiskApplySave.jsp" method=post name=fm id=fm target="fraSubmit" ENCTYPE="multipart/form-data">
		<table class=common>
			<TR>
				<TD width='20%' style="font:9pt">�ļ���</TD>
				<TD width='80%'>
					<input type=hidden name="ImportPath">
					<input type=hidden name="ImportFlag" value="<%=request.getParameter("ImportFlag")%>">
					<Input type="file" width="100%" name=FileName id=FileName>
					<INPUT VALUE="����" class="cssButton" TYPE=button onclick="submitForm();">
					<Input type="hidden"��width="100%" name="insuredimport" id=insuredimport value="1">
				</TD>
			</TR>
			<TR>
				<TD colspan=2>
				</TD>
			</TR>
			<input type=hidden name=ImportFile id=ImportFile>
		</table>
		<table class=common>
			<TR>
				<TD id="info" width='100%' style="font:10pt"></TD>
			</TR>
		</table>
		<div id="divError" style="display: none" align=left>
		    <input VALUE="ʧ����Ϣ" class=cssButton TYPE=button onclick="showError();">
		</div>
	</form>
</body>
