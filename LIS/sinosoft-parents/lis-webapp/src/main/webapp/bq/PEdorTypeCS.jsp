<html>
<%@ page language="java" contentType="text/html; charset=gbk" %>
<head>
	<title>Ͷ����ǩ�����/��ǩ��</title>
	
  <script src="../common/javascript/jquery.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script>
	<script src="../common/javascript/jquery.Jcrop.min.js"></script>
	<script src="PEdorTypeCS.js"></script>
	
	<script language="javascript">
			var picArray = top.opener.top.fraPic.arrPicName;
			var EdorAcceptNo=top.opener.document.all('EdorAcceptNo').value;
			var ContNo = top.opener.document.all('ContNo').value;
			var EdorType = top.opener.document.all('EdorType').value;
			window.document.onkeydown = cskeydown;
	</script>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link rel="stylesheet" href="../common/css/jquery.Jcrop.css" type="text/css" />
</head>
<body>
	
		<div id="CSPic" region="center" title="��ȫӰ���(���ҷ�����л�ͼƬ)" split="false" style="overflow:hidden;">
			<div id="CSPicView" class="easyui-tabs" fit="true" border="false">
			</div>
		</div>
		<div id="CSDeal" class=maxbox1 title="ǩ��Ӱ�񼰱�ȫ����" region="south" split="true">
			<table class=common>
    		<tr  class= common>
    	 		<td  class= title > ��ȫ����� </td>
    	  	<td  class= input >
    	    	<input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
    	  	</td>
    	  	<td class = title > �������� </td>
    	  	<td class = input >
    	    	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true value="Ͷ���˲�ǩ��/ǩ�����">
    	  	</td>
    	  	<td class = title > ������ </td>
    	  	<td class = input >
    	    	<input class="readonly wid" readonly name=ContNo id=ContNo>
    	  	</td>
    		</tr>
    		<tr  class= common>
    	 		<td  class= title > Ͷ�������� </td>
    	  	<td  class= input >
    	    	<input class="readonly wid" readonly name=AppntName id=AppntName >
    	  	</td>
    	  	<td class = title > Ͷ����֤������ </td>
    	  	<td class = input >
    	    	<Input class=codeno  readonly name=IDType id=IDType><input class=codename name=IDTypeName id=IDTypeName readonly=true>
    	  	</td>
    	  	<td class = title > Ͷ����֤������ </td>
    	  	<td class = input >
    	    	<input class="readonly wid" readonly name=IDNo id=IDNo>
    	  	</td>
    		</tr>
    	</table>
    	<div id="signDiv" style="display:''">

			</div>
			
		</div>

<input type=hidden  id="x" name="x" />
<input type=hidden  id="y" name="y" />
<input type=hidden  id="x2" name="x2" />
<input type=hidden  id="y2" name="y2" />
<input type=hidden  id="w" name="w" value="0"/>
<input type=hidden  id="h" name="h" value="0"/>
</html>