"use strict";
function InitRequest() {
	var xmlhttp = null;
	try {
		if (window.XMLHttpRequest) {// for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	} catch (e) {
		console.log(e);
		alert("�Բ���!�����������֧�ָù���,��ʹ��Internet Explorer 6.0���������!");
	}
	return xmlhttp;
}