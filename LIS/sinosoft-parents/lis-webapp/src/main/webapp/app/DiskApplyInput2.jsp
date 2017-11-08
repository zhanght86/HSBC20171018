<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-21 09:25:18
//创建人 ：HST
//更新记录： 更新人  更新日期   更新原因/内容
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
		alert("磁盘投保尚未完成，请不要离开!");
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
		if ( !confirm("确定您要再次磁盘投保吗?") )
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
		alert("文件名与印刷号不一致,请检查文件名!");
		return ;
	}
	else
	{
		document.all("info").innerText="上传文件导入中，请稍后..."
		document.all('ImportPath').value = ImportPath;
		ImportState="Importing";
//		fm.action="./DiskApplySave.jsp?ImportPath="+ImportPath;
		fm.submit(); //提交
	}
}

function getImportPath ()
{
	// 书写SQL语句
	var strSQL="";

	    var sqlid28="ContQuerySQL28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql28.setSqlId(sqlid28); //指定使用的Sql的id
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
        alert("未找到上传路径");
        return;
    }
}

function getPrtNo ()
{
	// 书写SQL语句
	var strSQL="";

    var sqlid29="ContQuerySQL29";
	var mySql29=new SqlClass();
	mySql29.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
	mySql29.setSqlId(sqlid29); //指定使用的Sql的id
	mySql29.addSubPara(grpcontno); //指定传入的参数
	strSQL=mySql29.getString();
	
	//strSQL="select PrtNo from lcgrpcont where grpcontno='"+ grpcontno +"'";
	turnPage.strQueryResult =easyQueryVer3(strSQL, 1, 1, 1);
	//判断是否查询成功
	if (!turnPage.strQueryResult)
	{
		alert("未查找到保单:" + grpcontno);
		return;
	}
	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	turnPage.arrDataCacheSet=clearArrayElements(turnPage.arrDataCacheSet);
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet=decodeEasyQueryResult(turnPage.strQueryResult);
	return turnPage.arrDataCacheSet[0][0];
}

//提交后操作,服务器数据返回后执行的操作
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
				<TD width='20%' style="font:9pt">文件名</TD>
				<TD width='80%'>
					<input type=hidden name="ImportPath">
					<input type=hidden name="ImportFlag" value="<%=request.getParameter("ImportFlag")%>">
					<Input type="file" width="100%" name=FileName id=FileName>
					<INPUT VALUE="导入" class="cssButton" TYPE=button onclick="submitForm();">
					<Input type="hidden"　width="100%" name="insuredimport" id=insuredimport value="1">
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
		    <input VALUE="失败信息" class=cssButton TYPE=button onclick="showError();">
		</div>
	</form>
</body>
