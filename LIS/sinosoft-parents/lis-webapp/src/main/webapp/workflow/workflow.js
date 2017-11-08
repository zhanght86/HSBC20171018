"use strict";
var svgWin = null;
var treeWin = null;
var flowName = "";

function isIE() { //ie?
	if (!!window.ActiveXObject || "ActiveXObject" in window)
        return true;
    else
    	return false;
}

function BrowserInfo() {
	var ua = navigator.userAgent.toLowerCase();
	var Sys = {};
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
			.match(/Trident\/(\d+)/i)) ? Sys.ie11 = s[1] : (s = ua
			.match(/edge.([\d.]+)/)) ? Sys.edge = s[1] : (s = ua
			.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/opr.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
			.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
			.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
	var mBrowserInfo = {};
	if (Sys.ie) {
		mBrowserInfo.name = "IE";
		mBrowserInfo.version = "" + parseInt(Sys.ie);
	} else if (Sys.ie11) {
		mBrowserInfo.name = "IE";
		mBrowserInfo.version = 11;
	} else if (Sys.firefox) {
		mBrowserInfo.name = "Firefox";
		mBrowserInfo.version = Sys.firefox;
	} else if (Sys.chrome) {
		mBrowserInfo.name = "Chrome";
		mBrowserInfo.version = Sys.chrome;
	} else if (Sys.opera) {
		mBrowserInfo.name = "Opera";
		mBrowserInfo.version = Sys.opera;
	} else if (Sys.safari) {
		mBrowserInfo.name = "Safari";
		mBrowserInfo.version = Sys.safari;
	} else if (Sys.edge) {
		mBrowserInfo.name = "Edge";
		mBrowserInfo.version = Sys.edge;
	} else {
		//默认Chrome
		mBrowserInfo.name = "Chrome";
		mBrowserInfo.version = Sys.chrome;
	}
	return  mBrowserInfo;
}

function init(evt) {
	//status 是一种内嵌页面形式
	if("status" == action){
		document.getElementById("buttondiv").style.display="none";
		document.getElementById("treediv").style.display="none";
	}
	var availHeight =  window.screen.availHeight - 40 ;//div区域上方按钮高度40区域
	var availWidth = window.screen.availWidth - 20 - 200;//200为tree宽度，20为调整量
	
	//下面是浏览器高度调整量，主要考虑Windows下，任务栏和工具栏等高度影响。
	
	var browserInfo = BrowserInfo();
	if(browserInfo.name == 'IE'){
		availHeight = availHeight - 80;
	}
	else if(browserInfo.name == 'Edge'){
		availHeight = availHeight - 100;
	}
	else if(browserInfo.name == 'Chrome'){
		availHeight = availHeight - 45;
	}
	else if(browserInfo.name == 'Firefox'){
		availHeight = availHeight - 95;
	}
	else {
		availHeight = 600;
	}
	
	if("status" != action){
		document.getElementById("treediv").height = availHeight; 
		document.getElementById("tree").height = availHeight;
		document.getElementById("svgdiv").height = availHeight; 
		document.getElementById("svg").height = availHeight;
		document.getElementById("svgdiv").width = availWidth; 
		document.getElementById("svg").width = availWidth;
	}
	else {
		document.getElementById("svgdiv").height = availHeight + 40; //40为按钮区高度
		document.getElementById("svg").height = availHeight + 40;
		document.getElementById("svgdiv").width = availWidth + 200; //200为tree区宽度
		document.getElementById("svg").width = availWidth + 200;
	}
	
	document.getElementById("file").onchange = function (){
		var file = document.getElementById("file").files[0];  
		var reg = /\.(xml|XML)/i;
		if(reg.test(file.name)){
			if(window.FileReader) {
			    var reader = new FileReader();  
			    reader.readAsText(file);  
			    reader.onload=function(e){
			    	svgWin.readXML(e,this.result,null);  
			    	document.getElementById("file").value = "";
			    }  
			}
			else {
			    alert("FileReader not supported by your browser!");
			}
		}
	};
	
	//IE加载svg snap较慢，必须暂停一下. isIE判断无法识别 MS Edge浏览器，所有全部延时
	//if(isIE()) { 
		setTimeout(initd,1000);
	//} else  initd(evt);
}

function initd(evt) {
	var svgFrame = document.getElementById("svg");
	svgWin = svgFrame.contentWindow;
	
	var treeFrame = document.getElementById("tree");
	treeWin = treeFrame.contentWindow;
	if("status" == action) {
		showTip("正在读取工作流信息.......");
		document.getElementById("create").disabled = true;//不能新建
		document.getElementById("save").disabled = true;//不能保存
		document.getElementById("move").disabled = true;//不能移动
		document.getElementById("start").disabled = true;//不能创建开始节点
		document.getElementById("node").disabled = true;//不能创建过程节点
		document.getElementById("end").disabled = true;//不能创建过程节点
		document.getElementById("link").disabled = true;//不能创建联线
		document.getElementById("prop").disabled = true;//禁止修改属性
		document.getElementById("del").disabled = true;//不能删除
		document.getElementById("read").disabled = true;//不能导入数据
		document.getElementById("file").disabled = true;//不能导入数据
		document.getElementById("write").disabled = true;//不能导出
		document.getElementById("empty").disabled = true;//不能清空
		svgWin.readWF(null,flowId,flowVersion,mainMissionId);
		hideTip();
	}else if("query" == action){
		showTip("正在读取工作流信息.......");
		document.getElementById("create").disabled = true;//不能新建
		document.getElementById("save").disabled = true;//不能保存
		document.getElementById("move").disabled = false;
		document.getElementById("start").disabled = true;//不能创建开始节点
		document.getElementById("node").disabled = true;//不能创建过程节点
		document.getElementById("end").disabled = true;//不能创建过程节点
		document.getElementById("link").disabled = true;//不能创建联线
		document.getElementById("prop").disabled = false;//禁止修改属性
		document.getElementById("del").disabled = true;//不能删除
		document.getElementById("read").disabled = true;//不能导入数据
		document.getElementById("file").disabled = true;//不能导入数据
		document.getElementById("write").disabled = false;
		document.getElementById("empty").disabled = true;//不能清空
		svgWin.readWF(null,flowId,flowVersion,null);
		hideTip();
	} else if("new" == action){
		// 没有限制
	} else if("update" == action){
		showTip("正在读取工作流信息.......");
		document.getElementById("create").disabled = true;//不能新建
		document.getElementById("read").disabled = true;//不能导入数据
		document.getElementById("file").disabled = true;//不能导入数据
		document.getElementById("empty").disabled = true;//不能清空
		svgWin.readWF(null,flowId,flowVersion,null);
		hideTip();
	} else if("copy" == action){
		showTip("正在读取工作流信息.......");
		document.getElementById("create").disabled = true;//不能新建
		svgWin.readWF(null,flowId,flowVersion,null);
		//自动调用新建
		svgWin.createNew(null);
		hideTip();
	} else if("rebuild" == action){
		document.getElementById("create").disabled = true;//不能新建
		document.getElementById("save").disabled = false;
		document.getElementById("move").disabled = true; //不能移动
		document.getElementById("start").disabled = true;//不能创建开始节点
		document.getElementById("node").disabled = true;//不能创建过程节点
		document.getElementById("end").disabled = true;//不能创建过程节点
		document.getElementById("link").disabled = true;//不能创建联线
		document.getElementById("prop").disabled = false;//(禁止修改)
		document.getElementById("del").disabled = true;//不能删除
		document.getElementById("read").disabled = true;//不能导入数据
		document.getElementById("file").disabled = true;//不能导入数据
		document.getElementById("write").disabled = false;
		document.getElementById("empty").disabled = true;//不能清空
		svgWin.readWF(null,flowId,flowVersion,null);
	}
}

function showTip(tip) {
	document.getElementById('Tip').innerHTML= tip;  
	document.getElementById('divTip').style.display="block";          
}
function hideTip() {
	document.getElementById('Tip').innerHTML= ""; 
	document.getElementById('divTip').style.display="none";
}

function showHis(x1,y1,hisArr,maxWidth,maxHeight) {
	//重新调整偏移
	maxHeight = window.screen.availHeight - 50;
	maxWidth = window.screen.availWidth -50;
	x1 += 10;
	y1 += 10;
	
	document.getElementById('divHis').style.display="none";
	if(hisArr==null){
		return null ;
	}
	if(!hisArr.length){
		return null ;
	}
	if(hisArr.length == 0){
		return null ;
	}
	//350为表格宽度
	if(x1+ 350 > maxWidth){
		x1 = maxWidth - 350;
	}
	//50为div头和表格头合计高度，22为每行数据高度
	if(y1+ 50 + 22 +hisArr.length*22  > maxHeight){
		y1 = maxHeight -(50 + 22 + hisArr.length*22);
	}
	
	var hisHtml = editNodeHis(hisArr);
	if(hisHtml != null) {
		document.getElementById('His').innerHTML= hisHtml;  
		document.getElementById('divHis').style.left=x1+"px";
		document.getElementById('divHis').style.top=y1+"px";
		document.getElementById('divHis').style.display="block";
	}
}

function editNodeHis(arr) {
	
	var hisHtml="<table class= common>"
	              +   "<tr>"
	              +      "<td class= centertitle>开始日期</td><td class= centertitle>开始时间</td><td class= centertitle>结束日期</td><td class= centertitle>结束时间</td><td class= centertitle>执行人</td>"
	              +   "</tr>";
	for(var i=0;i<arr.length;i++){
	      hisHtml=hisHtml
	              +   "<tr>"
	              +      "<td class= common>"+arr[i][0]+"</td><td class= common>"+arr[i][1]+"</td><td class= common>"+arr[i][2]+"</td><td class= common>"+arr[i][3]+"</td><td class= common>"+arr[i][4]+"</td>"
	              +   "</tr>";       
	 }
	 hisHtml=hisHtml+"</table>";
	 return hisHtml;
}

function hideHis() {
	document.getElementById('divHis').style.display="none";
}

function create(e) {
	svgWin.createNew(e);
}

function save(e) {
	svgWin.saveToDB(e);
}

function move(e) {
	svgWin._setMoveState(e);
}

function add(e, type) {
	svgWin._createNode(e, type);
}

function link(e) {
	svgWin._setLinkState(e);
}

function property(e) {
	svgWin.setProperty(e);
}

function del(e) {
	svgWin._del(e);
}

function exp(e) {
	//仅支持chrome
	if(BrowserInfo().name != 'Chrome'){
		alert("当前仅支持Chrome！");
		return;
	}
	var svgXml = svgWin.exp(e);
	var image = new Image();
	image.src = 'data:image/svg+xml;base64,' + window.btoa(unescape(encodeURIComponent(svgXml))); // 给图片对象写入base64编码的svg流

	var canvas = document.createElement('canvas');  // 准备空画布
	//var svg = document.getElementById("svg");
	canvas.width = 960;
	canvas.height = 600;

	var context = canvas.getContext('2d');  // 取得画布的2d绘图上下文
	context.drawImage(image, 0, 0);

//	Canvas2Image.saveAsPNG(canvas,null,960,600);
	var a = document.createElement('a');
	a.href = canvas.toDataURL('image/png');  // 将画布内的信息导出为png图片数据
	a.download = "工作流-"+flowName;  // 设定下载名称
	a.click(); // 点击触发下载
}

function empty(e) {
	action = "new";
	svgWin._empty(e);
}

function imp(evt) {
	 return document.getElementById("file").click();
}

function treeClick(e,id) {
	svgWin._setCurrTarget(e,id);
}

function treeAdd(type, id, name1, name2) {
	treeWin.add(type, id, name1, name2);
}

function treeDel(id) {
	treeWin.del(id);
}

function treePName(id,name){
	flowName = name;
	treeWin.setPName(id,name);
}

function treeSetTarget(id){
	treeWin.setTarget(id);
}

function treeClear() {
	treeWin.clear();
}