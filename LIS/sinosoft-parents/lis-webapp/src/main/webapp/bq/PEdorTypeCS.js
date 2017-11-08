$(document).ready(initCS);
var csState = 0;
var cs_jcrop_api = null;
function initCS(){
	if(EdorAcceptNo==null ||EdorAcceptNo==""){
		alert("接收数据失败！");
		return;
	}
	$('#CSDeal').css('height',$(window).height()*0.4);
	$(document.body).layout();
	initScanPic();
	initPage();
	initSignWindow();
	initCutWindow();
}

function initScanPic(){
	lockPart('CSPicView','影像件加载中....');
	if(picArray == null || picArray.length<1){
		alert("影像件加载失败！");
		return
	}
	var baseName= '影像件第';
	var order=1;
	$.each(picArray,function(key,val){
		addTab(baseName+order+'页',order,val);
		order++;
	});
	$('#CSPicView').tabs({  
     onSelect: function(title){
     		if(csState == 1){
     			var picId = $('#CSPicView').tabs('getSelected').panel('options').id;
     			if(cs_jcrop_api != null){
     				cs_jcrop_api.destroy();
     				cs_jcrop_api == null;
     			}
     			cs_jcrop_api = $.Jcrop(
						'#CSPic'+picId,{
						onChange: showCoords,
						onSelect: showCoords
					});
				}

     }
  });
	unlockPart('CSPicView');
}

function initSignWindow(){
	var params = {
		oper:'sq',
		docCode:ContNo
  };
	var sdl = $('#signDiv').offset();
  $.post(
  	"../common/jsp/SignatureCut.jsp?sid="+getTimeForURL(),
  	params,
  	function(OData) {
			var size = OData.Size;
			var signShowHtml = "<div id=\"OldSignWindow\">"
											 + "<div id=\"OldSignDiv\" ><img id=\"OldSignPic\" src=\"\"/></div></div>";
			$('#signDiv').append(signShowHtml);
			$('#OldSignWindow').window({
				title:'原签名影像',
				width:320,
				height:120,
				border: false,
				left: sdl.left+240,
				top: sdl.top+36,
				shadow: true,
				closable:false,
				collapsible:true,
				minimizable:false,
				maximizable:false,
				draggable:true
			});
			if(size<1){
				$("#OldSignDiv").hide();
				$("#OldSignWindow").html("<br><center>签名不存在，请补签名!</center>");
			}else{
				$('#OldSignPic').attr("src",OData.PicPath);
				var sImg = new Image;
				sImg.onload = function(){
  				var pw = sImg.width +14;
  				if(pw<140){
  					pw = 140;	
  				}
  				var ph = sImg.height +46;
  				$('#OldSignWindow').window('resize',{
					 			width:pw,
 								height:ph
					});
					$('#OldSignDiv').show();
				};
				sImg.src = OData.PicPath;
			}
			params = {
				oper:'2',
				EdorAcceptNo:EdorAcceptNo
			}
			$.post(
			  "PEdorTypeCSInit.jsp?sid="+getTimeForURL(),
  			params,
  			function(NData) {
					var nSize = NData.Size;
					var OssHtml = "<div id=\"NewSignWindow\">"
											 		 + "<div id=\"NewSignDiv\" ><img id=\"NewSignPic\" src=\"\"/></div>"
											 		 + "<br>&nbsp;&nbsp;&nbsp;<div align='center'><a id= \"SignPicChange\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\" onclick=\"StartSignCut()\" >更换/补签名</a>"
											 		 + " <a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-undo\" onclick=\"returnParent();\"> 返回 </a></div>"
											 		 + "</div>";
					$('#signDiv').append(OssHtml);
					$('#NewSignWindow').window({
							title:'新签名影像',
							width:320,
							height:120,
							border: false,
							left: $("#OldSignWindow").window('options').left+$("#OldSignWindow").window('options').width+50,
							top: sdl.top+36,
							shadow: true,
							closable:false,
							collapsible:true,
							minimizable:false,
							maximizable:false,
							draggable:true
					});
					$('.easyui-linkbutton').linkbutton();	
					if(nSize<1){
						$("#NewSignDiv").hide();
					}else{
						$('#NewSignPic').attr("src",NData.PicPath);
						var sImg = new Image;
						sImg.onload = function(){
  						var pw = sImg.width +14;
  						if(pw<260){
  							pw = 260;	
  						}
  						var ph = sImg.height +96;
  						$('#NewSignWindow').window('resize',{
							 			width:pw,
 										height:ph
							});
							$('#NewSignDiv').show();
						};
						sImg.src = NData.PicPath;
					}			 		 
				},"json" 
			)
		},"json" 
	);
}

function initPage(){
	$('[name=EdorAcceptNo]').val(EdorAcceptNo);
	$('[name=ContNo]').val(ContNo);
	$('[name=EdorType]').val(EdorType);
	
	var params = {
			oper:'1',
			ContNo:ContNo	
  };
  jQuery.post(
  	"PEdorTypeCSInit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
			$('[name=AppntName]').val(data.Name);
			$('[name=IDType]').val(data.IDType);
			$('[name=IDTypeName]').val(data.IDTypeName);
			$('[name=IDNo]').val(data.IDNo);
		},"json" 
	);
}

function initCutWindow(){
		var cutHtml = "<div id=\"PicCutWindow\"><br><br>"
								+ "&nbsp;&nbsp;&nbsp;&nbsp;<a id= \"PicCut\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-cut\" onclick=\"SignCutSave()\" >截取</a>"
								+ "&nbsp;&nbsp;<a id= \"PicSave\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-cancel\" onclick=\"SignCutCancel()\">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "<br><br></div>";
		$(document.body).append(cutHtml);
		
		var wh = $('#CSPic').height();
		var ws = $('#CSPic').scrollTop();
		var pch = ws+(wh-60)/2;
		$("#PicCutWindow").window({
			title:'工具栏',
			width:240,
			height:120,
			border: false,
			left: 480,
			top: pch,
			shadow: true,
			closed: false,
			closable:false,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			resizeable:false,
			draggable:true
		});
		$('#PicCut').linkbutton();
		$(window).scroll(function(){ 
				if(csState == 1){
					 var winHeight = $('#CSPic').height();
					 var winScrTop = $('#CSPic').scrollTop();
					 var picHeight = $("#PicCutWindow").window('options').height;
					 var picTop = winScrTop + (winHeight - picHeight)/2;
					 var picLeft = $("#PicCutWindow").window('options').left;
					 $("#PicCutWindow").window('move',{
 					 	left:picLeft,
					 	top:picTop
					 });
				}
		});
		$("#PicCutWindow").window('close');
}

function lockPart(objID,msg){
	var loadMsg = "Loading........";
	if(msg != null && msg != ""){
		loadMsg = msg;
	}
	var obj = $('#'+objID);
	var h = obj.height();
	var w = obj.width();

		$("<div class=\"datagrid-mask\" fit=\"true\"></div>").css(
			{display:"block",
			 width:w,
			 height:h
			}).appendTo(obj);
	$("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(obj).css(
			{display:"block",
			 left:(w-$("div.datagrid-mask-msg",obj).outerWidth())/2,
			 top:(h-$("div.datagrid-mask-msg",obj).outerHeight())/2
			});  
}

function unlockPart(objID){
		$('#'+objID).find("div.datagrid-mask-msg").remove();
		$('#'+objID).find("div.datagrid-mask").remove();
}

function unLockPage()
{ 
		$(document.body).find("div.datagrid-mask-msg").remove();
		$(document.body).find("div.datagrid-mask").remove();
}


function lockPage(msg){ 
		var loadMsg = "Loading........";
		if(msg != null && msg != ""){
				loadMsg = msg;
		}
		var dheight = 0;
		var dWidth = 0;
	 	var sh=document.body.scrollHeight;
 		var ch =document.body.clientHeight;	
 		if(sh > ch){
 			dheight=sh;
 		}else{
 			dheight=ch;
 		}
 		var sw = document.body.scrollWidth;
 		var cw =document.body.clientWidth;
		if(sw >cw ){
			dWidth=sw; 
		}else{
			dWidth=cw;
		}
	var body = $(document.body);
	$("<div class=\"datagrid-mask\"></div>").css(
			{display:"block",
			 width:dWidth,
			 height:dheight
			}).appendTo(body);
	$("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(body).css(
			{display:"block",
			 left:(dWidth-$("div.datagrid-mask-msg",body).outerWidth())/2,
			 top:(dheight-$("div.datagrid-mask-msg",body).outerHeight())/2
			 });                    
}

function addTab(title,id,src){

	var content = "<img id=\"CSPic"+id+"\" src=\""+src+"\"/>";
	$('#CSPicView').tabs('add',{
		id:id,
		title:title,
		content:content,
		closable:false
	});
} 

function returnParent() {
	top.opener.getEdorItemGrid();
	window.close();
}

function StartSignCut(){
	lockPart('CSDeal','请完成截图！');
	$(document.body).layout('collapse','south');
	$('#NewSignWindow').window('close');
	$('#OldSignWindow').window('close');
	var picId = $('#CSPicView').tabs('getSelected').panel('options').id;
	if(cs_jcrop_api != null){
     				cs_jcrop_api.destroy();
     				cs_jcrop_api == null;
  }
  cs_jcrop_api = $.Jcrop(
		'#CSPic'+picId,{
		onchange: showCoords,
		onSelect: showCoords
	});
	$("#PicCutWindow").window('open');
	csState = 1;
}

function showCoords(c){
	$('#x').val(c.x);
	$('#y').val(c.y);
	$('#x2').val(c.x2);
	$('#y2').val(c.y2);
	$('#w').val(c.w);
	$('#h').val(c.h);
};

function SignCutCancel(){
	$('#x').val(0);
	$('#y').val(0);
	$('#x2').val(0);
	$('#y2').val(0);
	$('#w').val(0);
	$('#h').val(0);
	cs_jcrop_api.destroy();
	cs_jcrop_api = null;
	$("#PicCutWindow").window('close');
	$('#NewSignWindow').window('open');
	$('#OldSignWindow').window('open');
	csState = 0;
	$(document.body).layout('expand','south');
	unlockPart('CSDeal');
} 

function SignCutSave(){

	var x1= $('#x').val();
	var y1= $('#y').val();
	var x2= $('#x2').val();
	var y2= $('#y2').val();
	var w= $('#w').val();
	var h= $('#h').val();
	
	if(w<2||h<2 ){
		$.messager.alert('错误','截取的图片大小不符合要求!','error');
		return;
	}
	lockPart('PicCutWindow','截图中...');
	var picId = $('#CSPicView').tabs('getSelected').panel('options').id;
	var picSrc = $('#CSPic'+picId).attr("src");
	var picName = picSrc.substring((picSrc.lastIndexOf("/")+1),picSrc.lastIndexOf("."));
	var params = {
			pageName:picName,
			EdorAcceptNo:EdorAcceptNo,
			x1:x1,
			y1:y1,
			w:w,
			h:h			
  	};
  jQuery.post(
  	"PEdorTypeCSSubmit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
			var flag = data.Flag;
			if(flag == "Succ"){
				$.messager.alert('消息','截图成功','info',function(){
						SignCutCancel();
						afterSignCut();
				});
			}else{
				$.messager.alert('错误',data.Info,'error');
			}
			unlockPart('PicCutWindow');
		},"json" 
	);
	
}
function afterSignCut(){
	lockPart('NewSignWindow','签名影像查询中...');
	params = {
		oper:'2',
		EdorAcceptNo:EdorAcceptNo
	}
  jQuery.post(
  	"PEdorTypeCSInit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
  		var size = data.Size;
  		if(size<1){
 				alert('没有签名图片信息!');
  		}else{
				$('#NewSignPic').attr("src",data.PicPath);
				var sImg = new Image;
				sImg.onload = function(){
					var pw = sImg.width +14;
					if(pw<260){
  					pw = 260;	
  				}
  				var ph = sImg.height +96;
  				$('#NewSignWindow').window('resize',{
					 	width:pw,
 						height:ph
					});
					$('#NewSignDiv').show();
						unlockPart('NewSignWindow');
				};
				sImg.src = data.PicPath;
  		}
  	},"json" 
	);
} 

function cskeydown(){
	var keycode = event.keyCode;
	var picId = $('#CSPicView').tabs('getSelected').panel('options').id;
	if (keycode == "37") {
		picId--;
		if(picId<1){
			picId = picId+picArray.length;
		}
		$('#CSPicView').tabs('select','影像件第'+picId+'页');
	}else if (keycode == "39") {
		picId++;
		if(picId>7){
			picId = picId-picArray.length;
		}
		$('#CSPicView').tabs('select','影像件第'+picId+'页');
	}
}

function getTimeForURL()
{
  var now = new Date();
  var year = now.getYear();
  var month = now.getMonth() + 1;
  var date = now.getDate();
  var hours = now.getHours();
  var minutes = now.getMinutes();
  var seconds = now.getSeconds();
  
  
  var timeValue = "";
  timeValue += year ;
  timeValue += ((month < 10) ? "0" : "") + month ;
  timeValue += ((date < 10) ? "0" : "") +date ;

  timeValue += hours;
  timeValue += ((minutes < 10) ? "0" : "") + minutes;
  timeValue += ((seconds < 10) ? "0" : "") + seconds;
  return timeValue;
}
