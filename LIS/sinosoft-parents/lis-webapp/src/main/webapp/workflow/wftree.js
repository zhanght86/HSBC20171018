"use strict";
$(document).ready(function() {

	$("body").height(getUrlVar('height'));
	reloadTree();
	document.oncontextmenu = function() {
		return false;
	}
});

function getUrlVar(name) {
	var hashes = window.location.href.slice(
			window.location.href.indexOf('?') + 1).split('&');
	for (var i = 0; i < hashes.length; i++) {
		var hash = hashes[i].split('=');
		if (hash[0] == name)
			return hash[1];
	}
	return null;
};

function reloadTree() {
	$("#browser").treeview();
}

function clk(e, type, target) {
	var id = null;
	var targetElm = target;
	if (!targetElm)
		targetElm = e.target;
	if (targetElm.id) {
		id = targetElm.id
	} else {
		targetElm = targetElm.parentNode;
		while (!targetElm.id) {
			targetElm = targetElm.parentNode;
		}
		id = targetElm.id;
	}
	setTarget(id);
	try {
		if (id == 'pid') {
			id = targetElm.getAttribute("pid");
			if (id == null || "" === id || "null" == id)
				return;
		}
		window.parent.treeClick(e, id);
	} catch (e) {
	}
	;

}

function setTarget(id) {
	$("span.file").css("color", "");
	$("span.file").css("font-weight", "");
	$("#pid").css("color", "");
	$("#pid").css("font-weight", "");

	if (id != "pid" && id != $("#pid").attr("pid")) {
		$("#" + id).find('a').find('span').css("color", "#0000FF");
		$("#" + id).find('a').find('span').css("font-weight", "bold");
	} else {
		$("#pid").css("color", "#0000FF");
		$("#pid").css("font-weight", "bold");
	}
}

function add(type, id, name1, name2) {
	if (type == 'node') {

		var li = document.createElement("li");
		li.id = id;
		li.onclick = clk;
		li.ondblclick = dblclk;

		var a = document.createElement("a");
		a.setAttribute("style", "cursor: hand");

		var span = document.createElement("span");
		span.setAttribute("class", "file");
		span.setAttribute("style", "font-size: 14px");

		var text = document.createTextNode(name1);

		span.appendChild(text);
		a.appendChild(span);
		li.appendChild(a);

		try {
			var last = document.getElementById("nodelist").lastChild;
			while (!last.tagName || last.tagName.toLowerCase() != "li") {
				last = last.previousSibling;
			}
			last.removeAttribute("class");
		} catch (e) {
		}
		document.getElementById("nodelist").appendChild(li);

		$("#" + id).smartMenu(bodyMenuData);

	} else if (type == 'link') {

		var li = document.createElement("li");
		li.id = id;
		li.onclick = clk;

		var a = document.createElement("a");
		a.setAttribute("style", "cursor: hand");

		var span = document.createElement("span");
		span.setAttribute("class", "file");
		span.setAttribute("style", "font-size: 14px");

		var text = document.createTextNode(name1 + " 到 " + name2);
		span.appendChild(text);
		//var textFrom = document.createTextNode("");
		//var spanStart = document.createElement("span");
		//spanStart.setAttribute("style", "color: red");
		//var textStart = document.createTextNode(name1);
		//var textTo = document.createTextNode("到");
		//var spanEnd = document.createElement("span");
		//spanEnd.setAttribute("style", "color: red");
		//var textEnd = document.createTextNode(name2);
		//spanStart.appendChild(textStart);
		//spanEnd.appendChild(textEnd);
		//span.appendChild(textFrom);
		//span.appendChild(spanStart);
		//span.appendChild(textTo);
		//span.appendChild(spanEnd);
		a.appendChild(span);
		li.appendChild(a);

		try {
			var last = document.getElementById("linklist").lastChild;
			while (!last.tagName || last.tagName.toLowerCase() != "li") {
				last = last.previousSibling;
			}
			last.removeAttribute("class");
		} catch (e) {
		}
		document.getElementById("linklist").appendChild(li);

		$("#" + id).smartMenu(bodyMenuData);
	}
	reloadTree();
}
function del(id) {
	var delnode = document.getElementById(id);
	delnode.parentNode.removeChild(delnode);
	reloadTree();
}

function setPName(id, name) {
	var pid = document.getElementById("pid");
	pid.setAttribute("pid", id);
	pid.innerHTML = name;
	$("#pid").smartMenu(bodyMenuData);
}

function clear() {
	var delnode = document.getElementById("nodelist");
	var lis = delnode.getElementsByTagName("li");
	while (lis.length != 0) {
		delnode.removeChild(lis[0]);
	}
	delnode = document.getElementById("linklist");
	lis = delnode.getElementsByTagName("li");
	while (lis.length != 0) {
		delnode.removeChild(lis[0]);
	}
	document.getElementById("pid").innerHTML = "流程";
	document.getElementById("pid").removeAttribute("pid");

	$("span.file").css("color", "");
	$("span.file").css("font-weight", "");
	$("#pid").css("color", "");
	$("#pid").css("font-weight", "");
	reloadTree();
}

var dblclk = function() {
	clk(null, null, this);
	try {
		window.parent.property(null);
	} catch (e) {
	}
}
var bodyMenuData = [ [ {
	text : "属性",
	func : dblclk
} ], [ {
	text : "删除",
	func : function() {
		clk(null, null, this);
		try {
			window.parent.del(null);
		} catch (e) {
		}
	}
} ] ];