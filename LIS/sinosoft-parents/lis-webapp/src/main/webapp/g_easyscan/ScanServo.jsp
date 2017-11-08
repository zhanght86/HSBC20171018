<%
/***************************************************************
 * <p>ProName��ScanServo.jsp</p>
 * <p>Title��Ӱ���涯����</p>
 * <p>Description��Ӱ���涯����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String SubType    = request.getParameter("SubType");
	String BussNo     = request.getParameter("BussNo");
	String BussType   = request.getParameter("BussType");
	String PageCode   = request.getParameter("PageCode");
%>
<html>
<head>
	<title>�涯���ƽ���</title>
	<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
	<link rel="stylesheet" href="../common/css/jquery.Jcrop.css" type="text/css" />
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/javascript/jquery.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script><!-- jQuery EasyUI��һ�����JQuery��UI���ٴ��� �����EXTJS�е�����-->
	<script src="../common/javascript/jquery.Jcrop.min.js"></script><!-- Jcropһ��Queryʵ��ͼƬ�ü��Ĳ�� -->
	<script src="../common/javascript/jquery.rotate.js"></script><!-- ʵ��ͼƬ��ת���� -->
	<script src="../common/javascript/jquery.imageView.js"></script><!-- �����϶��鿴��ͼ��jQuery���-->
	<script src="EasyScanCommon.js"></script>
	<script>window.document.onkeydown = document_onkeydown;</script>
	
</head>
<!-- ����չʾͼƬ -->
<script>
	//���û��ر�һ��ҳ��ʱ���� onunload �¼���
  window.onunload = afterInput;
  function afterInput() {
    try {
    	$("body").remove();
    }
    catch(e) {}
  }
  
  	var viewMode = 1;//��ʾģʽ
  	//����ͼƬ
	var arrPicName = new Array();
	var subType =  "<%=SubType%>"; 
	var bussNo =  "<%=BussNo%>"; 
	var bussType =  "<%=BussType%>";
	var pageCode =  "<%=PageCode%>";
	
	//���ⷢƱͬʱҪչʾ������Ϣ
	if (subType=="23003") {
		subType = "23003,23004";
	}
	
	var params = {Operate:'1',SubType:subType,BussNo:bussNo,BussType:bussType,PageCode:pageCode,async : false}
  	$.post("EasyScanQuery.jsp?Sid="+getTimeForURL(),
  		params,function(data) {
			$.each(data, function(i, n) {
				 var picPath = data[i].picPath;
				 arrPicName.push(picPath);
		}); 
		initEasyScanServo();//�����涯��ť
  	},"json" 
	);
</script>

<body  class="easyui-layout">
<div id="PicView" region="center" title="Ӱ���" split="false">
	<div id="PicTab" class="easyui-tabs" fit="true" border="false"><!--ͼƬ���� -->
	</div>
</div>

	<form name=fm>
		<input type=hidden  id="x" name="x" />
		<input type=hidden  id="y" name="y" />
		<input type=hidden  id="x2" name="x2" />
		<input type=hidden  id="y2" name="y2" />
		<input type=hidden  id="w" name="w" value="0"/>
		<input type=hidden  id="h" name="h" value="0"/>
		<input type=hidden  id="DocCode" name="DocCode" value = "<%=BussNo%>"/>
	</form>
</body>
</html>
