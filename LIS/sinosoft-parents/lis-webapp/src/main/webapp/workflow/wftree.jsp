<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="GBK">
<title>Tree</title>
<link rel="stylesheet" href="./tree/jquery.treeview.css" />
<link rel="stylesheet" href="./menu/smartMenu.css" />
<script src="./jquery-1.7.2.js"></script>
<script src="./tree/jquery.treeview.js"></script>
<script src="./menu/jquery-smartMenu.js"></script>
<script src="./wftree.js"></script>
</head>
<body onselectstart="return false" >
	<div id="pid" onclick="clk(event,'process')"  ondblclick="clk(event,'process');try{window.parent.property(null);}catch(e){}" style= "cursor:pointer ">����</div>
	<div>
		<ul id="browser" class="filetree">
			<li><span class="folder">�ڵ�</span>
				<ul id="nodelist">
					<!--li id="start" onclick="clk(event,'node')"><a
						style="cursor: hand"><span class="file">��ʼ</span></a></li>
					<li id="end" onclick="clk(event,'node')"><a
						style="cursor: hand"><span class="file">����</span></a></li-->
				</ul></li>
			<li><span class="folder">����</span>
				<ul id="linklist">
					<!--li id="start-end" onclick="clk(event,'link')"><a
						style="cursor: hand"><span class="file"><span
								style="font-size: 14px">��<span style="color: red">��ʼ</span>��<span
									style="color: red">����</span></span> </span></a></li-->
				</ul></li>
		</ul>
	</div>
</body>
</html>