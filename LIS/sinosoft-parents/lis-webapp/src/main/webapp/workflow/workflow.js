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
		//Ĭ��Chrome
		mBrowserInfo.name = "Chrome";
		mBrowserInfo.version = Sys.chrome;
	}
	return  mBrowserInfo;
}

function init(evt) {
	//status ��һ����Ƕҳ����ʽ
	if("status" == action){
		document.getElementById("buttondiv").style.display="none";
		document.getElementById("treediv").style.display="none";
	}
	var availHeight =  window.screen.availHeight - 40 ;//div�����Ϸ���ť�߶�40����
	var availWidth = window.screen.availWidth - 20 - 200;//200Ϊtree��ȣ�20Ϊ������
	
	//������������߶ȵ���������Ҫ����Windows�£��������͹������ȸ߶�Ӱ�졣
	
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
		document.getElementById("svgdiv").height = availHeight + 40; //40Ϊ��ť���߶�
		document.getElementById("svg").height = availHeight + 40;
		document.getElementById("svgdiv").width = availWidth + 200; //200Ϊtree�����
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
	
	//IE����svg snap������������ͣһ��. isIE�ж��޷�ʶ�� MS Edge�����������ȫ����ʱ
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
		showTip("���ڶ�ȡ��������Ϣ.......");
		document.getElementById("create").disabled = true;//�����½�
		document.getElementById("save").disabled = true;//���ܱ���
		document.getElementById("move").disabled = true;//�����ƶ�
		document.getElementById("start").disabled = true;//���ܴ�����ʼ�ڵ�
		document.getElementById("node").disabled = true;//���ܴ������̽ڵ�
		document.getElementById("end").disabled = true;//���ܴ������̽ڵ�
		document.getElementById("link").disabled = true;//���ܴ�������
		document.getElementById("prop").disabled = true;//��ֹ�޸�����
		document.getElementById("del").disabled = true;//����ɾ��
		document.getElementById("read").disabled = true;//���ܵ�������
		document.getElementById("file").disabled = true;//���ܵ�������
		document.getElementById("write").disabled = true;//���ܵ���
		document.getElementById("empty").disabled = true;//�������
		svgWin.readWF(null,flowId,flowVersion,mainMissionId);
		hideTip();
	}else if("query" == action){
		showTip("���ڶ�ȡ��������Ϣ.......");
		document.getElementById("create").disabled = true;//�����½�
		document.getElementById("save").disabled = true;//���ܱ���
		document.getElementById("move").disabled = false;
		document.getElementById("start").disabled = true;//���ܴ�����ʼ�ڵ�
		document.getElementById("node").disabled = true;//���ܴ������̽ڵ�
		document.getElementById("end").disabled = true;//���ܴ������̽ڵ�
		document.getElementById("link").disabled = true;//���ܴ�������
		document.getElementById("prop").disabled = false;//��ֹ�޸�����
		document.getElementById("del").disabled = true;//����ɾ��
		document.getElementById("read").disabled = true;//���ܵ�������
		document.getElementById("file").disabled = true;//���ܵ�������
		document.getElementById("write").disabled = false;
		document.getElementById("empty").disabled = true;//�������
		svgWin.readWF(null,flowId,flowVersion,null);
		hideTip();
	} else if("new" == action){
		// û������
	} else if("update" == action){
		showTip("���ڶ�ȡ��������Ϣ.......");
		document.getElementById("create").disabled = true;//�����½�
		document.getElementById("read").disabled = true;//���ܵ�������
		document.getElementById("file").disabled = true;//���ܵ�������
		document.getElementById("empty").disabled = true;//�������
		svgWin.readWF(null,flowId,flowVersion,null);
		hideTip();
	} else if("copy" == action){
		showTip("���ڶ�ȡ��������Ϣ.......");
		document.getElementById("create").disabled = true;//�����½�
		svgWin.readWF(null,flowId,flowVersion,null);
		//�Զ������½�
		svgWin.createNew(null);
		hideTip();
	} else if("rebuild" == action){
		document.getElementById("create").disabled = true;//�����½�
		document.getElementById("save").disabled = false;
		document.getElementById("move").disabled = true; //�����ƶ�
		document.getElementById("start").disabled = true;//���ܴ�����ʼ�ڵ�
		document.getElementById("node").disabled = true;//���ܴ������̽ڵ�
		document.getElementById("end").disabled = true;//���ܴ������̽ڵ�
		document.getElementById("link").disabled = true;//���ܴ�������
		document.getElementById("prop").disabled = false;//(��ֹ�޸�)
		document.getElementById("del").disabled = true;//����ɾ��
		document.getElementById("read").disabled = true;//���ܵ�������
		document.getElementById("file").disabled = true;//���ܵ�������
		document.getElementById("write").disabled = false;
		document.getElementById("empty").disabled = true;//�������
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
	//���µ���ƫ��
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
	//350Ϊ�����
	if(x1+ 350 > maxWidth){
		x1 = maxWidth - 350;
	}
	//50Ϊdivͷ�ͱ��ͷ�ϼƸ߶ȣ�22Ϊÿ�����ݸ߶�
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
	              +      "<td class= centertitle>��ʼ����</td><td class= centertitle>��ʼʱ��</td><td class= centertitle>��������</td><td class= centertitle>����ʱ��</td><td class= centertitle>ִ����</td>"
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
	//��֧��chrome
	if(BrowserInfo().name != 'Chrome'){
		alert("��ǰ��֧��Chrome��");
		return;
	}
	var svgXml = svgWin.exp(e);
	var image = new Image();
	image.src = 'data:image/svg+xml;base64,' + window.btoa(unescape(encodeURIComponent(svgXml))); // ��ͼƬ����д��base64�����svg��

	var canvas = document.createElement('canvas');  // ׼���ջ���
	//var svg = document.getElementById("svg");
	canvas.width = 960;
	canvas.height = 600;

	var context = canvas.getContext('2d');  // ȡ�û�����2d��ͼ������
	context.drawImage(image, 0, 0);

//	Canvas2Image.saveAsPNG(canvas,null,960,600);
	var a = document.createElement('a');
	a.href = canvas.toDataURL('image/png');  // �������ڵ���Ϣ����ΪpngͼƬ����
	a.download = "������-"+flowName;  // �趨��������
	a.click(); // �����������
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