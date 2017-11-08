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
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>

	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
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
	fm.ImportButton.disabled=true;
	var i=0;
	getImportPath();
	ImportFile=fm.all('FileName').value;
	//alert("==========");
	EdorValiDate=fm.all('EdorValiDate').value;
	EdorTypeCal=fm.all('EdorTypeCal').value;
	EdorType=fm.all('EdorType').value;
	var prtno=getPrtNo();
	var tprtno=ImportFile;

	if ( tprtno.indexOf("\\")>0 ) tprtno=tprtno.substring(tprtno.lastIndexOf("\\")+1);
	if ( tprtno.indexOf("/")>0 ) tprtno=tprtno.substring(tprtno.lastIndexOf("/")+1);
	if ( tprtno.indexOf("_")>0) tprtno=tprtno.substring( 0,tprtno.indexOf("_"));
	if ( tprtno.indexOf(".")>0) tprtno=tprtno.substring( 0,tprtno.indexOf("."));

	if ( prtno!=tprtno )
	{
		alert("文件名与印刷号不一致,请检查文件名：老系统的单子，请用团体保单号；新系统的单子，请用投保单号");
		return ;
	}
	else
	{
		document.all("info").innerText="上传文件导入中，请稍后..."
		fm.all('ImportPath').value = ImportPath;
		ImportState="Importing";
		fm.action="./DiskApplySave.jsp?ImportPath="+ImportPath+"&BQFlag=2&EdorType="+EdorType+"&EdorValiDate="+EdorValiDate+"&EdorTypeCal="+EdorTypeCal;
		//fm.action="./DiskApplySave.jsp?BQFlag=2&EdorType=NI";
		//alert("Come here--------!");
		fm.submit(); //提交
	}
}

function getImportPath ()
{
	// 书写SQL语句
	var strSQL="";
	strSQL="select SysvarValue from ldsysvar where sysvar='XmlPath'";
	var arrResult = easyExecSql(strSQL);
	//判断是否查询成功
	if (arrResult==null) {
		alert("未找到上传路径");
		return;
	}
 ImportPath=arrResult[0][0];
}

function getPrtNo ()
{
	// 书写SQL语句
	var strSQL="";
	strSQL="select PrtNo from lcgrpcont where grpcontno='"+ grpcontno +"'";
	var arrResult = easyExecSql(strSQL);
	//判断是否查询成功
	if (arrResult==null)
	{
		alert("未查找到保单:" + grpcontno);
		return;
	}
	return arrResult[0][0];
}

//提交后操作,服务器数据返回后执行的操作
function returnParent()
{
   try{
   top.opener.initForm();
   //top.opener.queryClick();
   //top.opener.queryPEdorList();
   top.close();
   }catch(re){
   }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ,Result )
{
        //alert("Come here!");
	document.all("info").innerText=content;
	ImportState=FlagStr ;
	fm.ImportButton.disabled=false;
}
</script>
</head>
<body>
	<form action="./DiskApplySave.jsp?BQFlag=2&EdorType=NI&EdorValiDate=<%=request.getParameter("EdorValiDate")%>&EdorTypeCal=<%=request.getParameter("EdorTypeCal")%>" method=post name=fm target="fraSubmit" ENCTYPE="multipart/form-data">
	
		<table class=common>
			<TR>
				<TD width='20%' style="font:9pt">文件名</TD>
				<TD width='80%'>
					<input type=hidden name="BQFlag" value="2">
					<input type=hidden name="EdorType" value=<%=request.getParameter("EdorType")%>>	
					<input type=hidden name="EdorValiDate" value=<%=request.getParameter("EdorValiDate")%>>
					<input type=hidden name="EdorTypeCal" value=<%=request.getParameter("EdorTypeCal")%>>
					<input type=hidden name="ImportPath">
					<Input type="file" width="100%" name=FileName>
					<INPUT VALUE="导入" class="cssButton" name="ImportButton" TYPE=button onclick="submitForm();">
					<Input type="hidden"　width="100%" name="insuredimport" value="1">
				</TD>
			</TR>
			<TR>
				<TD colspan=2>
				</TD>
			</TR>
			<input type=hidden name=ImportFile>
			
		</table>
		<table class=common>
			<TR>
				<TD id="info" width='100%' style="font:10pt"></TD>
			</TR>
			<TR>
				<TD colspan=2>
				<INPUT VALUE="返回" class="cssButton" TYPE=button onclick="returnParent();">
				</TD>
			</TR>
		</table>
	</form>
</body>
