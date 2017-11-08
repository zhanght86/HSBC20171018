<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%
//程序名称：MessagePage.jsp
//程序功能：信息显示页面
//创建日期：2002-05-10
//创建人  ：
//	更新记录：	更新人		更新日期		更新原因/内容
//				欧阳晟		2002-05-10	修改
//				HuangLiang	2012-01-11	增加后台任务进度条显示
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.StrTool"%>
<html>
<head>
<title>信息反馈</title>
<script src="../javascript/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="../css/Project.css">
<script src="../javascript/jquery.progressbar.js"></script>
<%
String SUCCESS = "S";	//成功
String FAILURE = "F";	//失败
String COMMON = "C";	//一般信息
String Picture = request.getParameter("picture");
String ctnt = request.getParameter("content");
String Content = ctnt; 
try{
    Content = StrTool.unicodeToGBK(ctnt);	
}catch(Exception e1){
    Content = ctnt;	
}

if (Content.toUpperCase().indexOf("HTTP://")>-1 || Content.toUpperCase().indexOf("SCRIPT")>-1 || Content.toUpperCase().indexOf("SRC")>-1)
{
  	session.putValue("GI",null);
%>
<script language=jscript.encode>
	try
	{
		top.window.location ="../../indexlis.jsp";
	}
	catch (exception)
	{
		top.window.location ="../indexlis.jsp";
	}
</script>
<%
}
boolean bIsOutTime = false;
if (session == null)
{
	bIsOutTime = true;
}
else
{
	GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	if (tG1 == null)
	{
		bIsOutTime = true;
	}
	else
	{
		String userCode = tG1.Operator;
		String comCode = tG1.ComCode;
		String manageCom = tG1.ManageCom;

		if ((userCode.length()==0) || (userCode.compareTo("")==0)||(comCode.length()==0) || (comCode.compareTo("")==0) ||(manageCom.length()==0) || (manageCom.compareTo("") == 0))
		{
			bIsOutTime = true;
		}
	}
}
if (bIsOutTime)
{
	Content = "页面超时，请重新登录.";
}
/*********************************************************************/

String strPicture ="";
%>
</head>
<body class="interface">
<h1><center>系统信息</center></h1>
<br>
<%
if(Picture==null)
{
	Picture = COMMON;
}
if(Picture.equalsIgnoreCase(SUCCESS))
{
	strPicture ="success.gif";
}
else if (Picture.equalsIgnoreCase(FAILURE))
{
	strPicture ="failure.gif";
}
else
{
	strPicture ="common.gif";
}
%>
	<table>
		<tr>
			<td>
				<img src='../images/<%=strPicture%>'>
			</td>
			<td class="common" id="contentTD">
				<%=Content%>
			</td>
		</tr>
	</table>
<%
if (Content.indexOf("正在") == -1)
{
%>
	<center>
		<input type=button class=common id=butSubmit value="确 定" onclick="window.close()" tabIndex=0>
	</center>
<%
}
String AjaxFlag = request.getParameter("ajaxflag");
if("true".equalsIgnoreCase(AjaxFlag)){
	String AjaxUrl = "../../" + request.getParameter("ajaxurl");//调整url位置
	String Params =  request.getParameter("params");
	String StrTime =  request.getParameter("time");
	int intervalTime =1000;
	if(StrTime!=null&&StrTime!=""){
		intervalTime = Integer.parseInt(StrTime);
		if(intervalTime<1000)intervalTime=1000;
	}
%>
<style type="text/css">
	input.mulinetitle{
		background-image:url(../css/MulLine.gif); 
		BORDER-BOTTOM: medium none;text-align:center;border-left:medium none;
		border-right:medium none;border-top:medium none;text-align:center;
		height:21px;line-height:21px;
	}
	td.mulinetitle{
		border-top:#999999 1pt solid;border-right:#999999 1pt solid;overflow:hidden;
	}
	td.muline{
		color:#000000;text-align:center;height:21px;line-height:21px;background-color:#f7f7f7;
	}
</style>
<script type="text/javascript">
	var params = {
		content:"",
		parameters:"<%=Params%>",
		steps:"init"
	};
	var windowWidth;
	var aContent = "<%=Content%>";
	var AjaxUrl = "<%=AjaxUrl%>";
	var intervalTime = "<%=intervalTime%>";
	var $content = $("#contentTD");
	$(document).ready(function(){
		setTimeout(DoAjax,intervalTime);
		windowWidth = document.body.clientWidth - 110;
	});
	function DoAjax(){
		$.ajax({
			type:'post',
			async:true,
			data:params,
			url:AjaxUrl,
			cache:false,
			dataType:'json',
			success:function(rtData){
				var parameters = rtData.parameters;
				var receive = rtData.content;
				if(rtData.display==true||rtData.display=="true"){
					$content.html(receive);//使用自定义
				}else{
					$content.showMessage(receive);//使用默认
				}
				params = $.extend(params,rtData||{});
				if("completed"!=rtData.steps){
					setTimeout(DoAjax,intervalTime);//递归调用，使请求成功后才继续请求，减少服务器负担
				}
			},
			error:function(aObj,aError){
				alert(aError);
			}
		});
	}
	(function($){
		$.fn.showMessage=function(content){
			var $this = $(this);
			var $progressTable = $this.find("#progressTable");
			if($progressTable.length==0){//初始化
				var s = "<table id='progressTable' style='width:"+windowWidth+"px' class='muline' border=1 CELLSPACING=0 CELLPADDING=0  style='font-size:9pt'>";
				var i = 0,j=0;
				s+="<tr >";
				var colSize = content.colSize;
				var rowSize = content.rowSize;
				var tdWidth = windowWidth/colSize;
				for(i=0;i<colSize;i++){
					k =i+1;
					s+="<td id='title" + k +"' class='mulinetitle' style='background:#f7f7f7'><input type='text' readonly='readonly' class='mulinetitle' value='"
						+ content.title["col"+k] + "' style='width:" + tdWidth + "px' /></td>";
				}
				s+="</tr>";
				for(i=1;i<=rowSize;i++){
					s+="<tr>";
					for(j=1;j<=colSize;j++){
						s+="<td id='row"+i+"col"+j+"' class='muline'>"+content["row"+i]["col"+j]+"</td>";
					}
					s+="</tr>";
				}
				s+="</table>";
				$this.hide();
				$this.append(s);
			}
			var pCol = content.progressCol;
			var rowSize = content.rowSize;
			var i = 0;
			for(i=1;i<=rowSize;i++){
				var progressRate=0;
				var progressContent = content["row"+i]["col"+pCol];
				if(progressContent=="completed"){
					progressRate = 100;
				}else if(progressContent==""){
					progressRate = 0;
				}else{
					progressRate = eval(progressContent)*100;
				}
				$progressTable.find("#row"+i+"col"+pCol).progressBar(progressRate,
					{imageUrl:'../images/progressBar/',otherText:'('+progressContent+')'});
			}
			$this.show();
		}
	})(jQuery);
</script>
<%
}
%>

<script language=JavaScript>
ini = new Date().getTime();
var pc = 0;

function load()
{
	pc += 1;
	lpc.style.width = pc + "%";
	time = setTimeout("load()",30);
	if (pc > 100)
	{
		pc=0;
	}
}

function loaded()
{
	fim = new Date().getTime();
	dif = fim - ini;
	ld.style.display = 'none';
	body.style.backgroundColor = 'silver';
	q.innerHTML = dif/1000;
	page.style.display = '';
}

function Show()
{
	if (txt.style.display == "none")
	{
		txt.style.display = "";
	}
	else
	{
		txt.style.display = "none";
	}
}

try
{
	window.butSubmit.focus();
}
catch(e)
{}
</script>
</body>
</html>
