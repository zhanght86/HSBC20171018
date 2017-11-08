<%
//程序名称：EasyScanQuery.jsp
//程序功能：扫描件显示
//创建日期：2002-09-28 17:06:57
//创建人  ：胡博
//更新：刘强 2005-03-27	修改查询接口
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html>
<head>

<SCRIPT src="../javascript/Common.js" ></SCRIPT>
<SCRIPT src="../easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<%@include file="./EasyScanQueryKernel.jsp"%>

<link rel="stylesheet" type="text/css" href="../themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../themes/icon.css">
<link rel="stylesheet" href="../css/jquery.Jcrop.css" type="text/css" />

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../jsp/Log4jUI.jsp"%>  

<script src="../javascript/jquery.js"></script>
<script src="../javascript/jquery.easyui.min.js"></script>
<script src="../javascript/jquery.Jcrop.min.js"></script>
<script src="../javascript/Signature.js"></script>
<script src="../javascript/jquery.rotate.js"></script>
<SCRIPT src="./ShowPicControl.js"></SCRIPT>
<SCRIPT>window.document.onkeydown = document_onkeydown;
	if(document.attachEvent){
     document.attachEvent('onmousewheel',scrollLoadImg);
	}
</SCRIPT>
<SCRIPT src="./DebugAutoMove.js"></SCRIPT>

</head>

<%
String queryType  = request.getParameter("QueryType");
String DocID      = request.getParameter("DocID");
String BussNo     = request.getParameter("prtNo");
String BussNoType = request.getParameter("BussNoType");
String BussType   = request.getParameter("BussType");
String SubType    = request.getParameter("SubType");
String BussNo2     = request.getParameter("BussNo2");
String BussNoType2 = request.getParameter("BussNoType2");
String BussType2   = request.getParameter("BussType2");
String SubType2    = request.getParameter("SubType2");

String clientUrl = (String)session.getValue("ClientURL");	//LQ 2004-04-20
String[] arrPic = null;
if (queryType == null)
{
	arrPic = easyScanQueryKernel1(BussNo,BussNoType,BussType,SubType,clientUrl);
}
else if (queryType.equals("0"))
{
	arrPic = easyScanQueryKernel0(DocID,clientUrl);
}else if (queryType.equals("1")) 
{
	arrPic = easyScanQueryKernel1(BussNo,BussNoType,BussType,SubType,clientUrl);
}else if (queryType.equals("2"))
{
	arrPic = easyScanQueryKernel2(BussType,BussNoType,BussType2,BussNoType2,BussType,SubType,clientUrl);
}else if (queryType.equals("3"))
{
	arrPic = easyScanQueryKernel3(BussNo,BussType, clientUrl);
}else if(queryType.equals("9"))
{
  arrPic = easyScanQueryKernel9(BussNo,BussNoType,BussType,SubType,clientUrl);
}
//Added by niuzj 20060926,历史单证扫描影像查询
else if (queryType.equals("9999"))
{
	arrPic = easyScanQueryKernel9999(DocID,clientUrl);
}
else{
	arrPic = easyScanQueryKernel1(BussNo,BussNoType,BussType,SubType,clientUrl);
}

if (arrPic != null) {
%>
<script>
	window.onunload = afterInput;
	function afterInput() {
		 	CollectGarbage();
	}

	function clearData(){
	
		var   n   =   window.event.screenX   -   window.screenLeft;   
    var   b   =   n   >   document.documentElement.scrollWidth-20;   
    if(b   &&   window.event.clientY   <   0   ||   window.event.altKey)   
    {   
          //alert("是关闭而非刷新");  
          //移出缓存
          var idx = 1;
					$.each(arrPicName,function(key,val){
						removeImgToCache(idx);
						idx++;
					}); 
          $('#imgArea').remove();
					$('#imgDiv').remove();
					$('#PicTab').remove();
					$('#PicView').remove();
					$("body").remove();
         // window.event.returnValue   =   "是否关闭？";
    }else{
             //alert("是刷新而非关闭");   
            // $('#imgArea').removeData('Jcrop');
            // $('#imgArea').remove();
            // $('#imgDiv').empty();
    }   
     
	}


	var bussNo = "<%=BussNo%>"; 
  var arrPicName = new Array();
  <%for (int i=0; i<arrPic.length; i++) {%>
    arrPicName[<%=i%>] = '<%=arrPic[i]%>';
  <%}%>
  window.top.fraInterface.pic_name = arrPicName;
  var prtNo = "<%=BussType%>"; 
  $(document).ready(initEasyScanPage);
</script>

<body  class="easyui-layout" onbeforeunload="clearData();">
<div id="PicView" region="center" title="" split="false" style="overflow:hidden;">
	<div id="PicTab" class="easyui-tabs" fit="true" border="false">
	</div>
</div>
<div id="preLoadImg" style="display:none">
</div>
	<form name=fm>
		<input type=hidden  id="x" name="x" />
		<input type=hidden  id="y" name="y" />
		<input type=hidden  id="x2" name="x2" />
		<input type=hidden  id="y2" name="y2" />
		<input type=hidden  id="w" name="w" value="0"/>
		<input type=hidden  id="h" name="h" value="0"/>
		<input type=hidden  id="DocCode" name="DocCode">
	</form>
</body>
</html>

<script>
	fm.DocCode.value = bussNo;
</script>

<%
}
else {  
%>
<body border="0">
	<center id="centerPic">该印刷号对应的扫描件图片不存在</center>
</body>
</html>
<%
}
%>
