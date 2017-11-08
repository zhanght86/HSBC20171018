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
		alert("对不起!您的浏览器不支持该功能,请使用Internet Explorer 6.0以上浏览器!");
	}
	return xmlhttp;
}