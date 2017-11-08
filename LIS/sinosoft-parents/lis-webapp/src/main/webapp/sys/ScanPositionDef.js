$(document).ready(initScanDef);
var oper = '1';
var positionArray;
function initScanDef(){
	if(subType==null ||subType==""){
		alert("影像件类型未定义");
		return;
	}
	lockPage("数据加载中，请稍候...");
	$('#ScanTree').css('width',$(window).width()*0.2);
	$(document.body).layout();
	initScanTree();
	initScanPic();
	initWindow();
	initUpLoad();
	initPosArray();
}


function initScanPic(){
	$('#PicTabs').tabs({
			tools:[{
				iconCls:"icon-add",
				text:'<div id="newPage"><B>添加新影像件</B></div>',
				handler: function(){
				}
			},{
				iconCls:"icon-remove",
				text:'<B>删除影像件</B>',
				handler: function(){
					delPages();
				}
			},{
				iconCls:"icon-save",
				text:'<B>随动定制</B>',
				handler: function(){
					positionSave();
				}
			},{
				iconCls:"icon-search",
				text:'<B>签名影像位置查看</B>',
				handler: function(){
					signDefQuery();
				}
			},{
				iconCls:"icon-cut",
				text:'<B>签名影像定制</B>',
				handler: function(){
					signDefSave();
				}
			}]
	});
	queryScanPages();
}



function initScanTree(){
	$('#st').tree({
    url:'ScanPositionDefInit.jsp?oper=1&subType='+subType+'&sid='+getTimeForURL(),
    onContextMenu: function(e, node){
			e.preventDefault();
			$('#st').tree('select', node.target);
			$('#objCode').val(node.id);
			$('#objName').val(node.text);
			$('#mb').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		}
	});
}

function initUpLoad(){
	new Ajax_upload('newPage', {
		action: 'ScanPageUpload.jsp?subType='+subType+'&sid='+getTimeForURL(),
		name:'image',
		onSubmit : function(file , ext){
  			if (ext && /^(jpg|png|jpeg|gif)$/.test(ext)){
					lockPage('图片上传中....');
			} else {
				$.messager.alert('错误','不支持的图片格式！','error');
				return false;				
			}
		},
		onComplete : function(file){
			queryScanPages()
			unLockPage();
		}		
	});		
}

function initWindow(){
		var owHtml = "<div id=\"ObjWindow\"><br>"
								+ "	<table><tr><td>元素代码:</td><td><input id=\"objCode\" name=\"objCode\" class=\"easyui-validatebox\" required=\"true\" missingMessage=\"元素代码不能为空\"></td></tr>"
								+ "<tr><td>元素名称:</td><td><input id=\"objName\" name=\"objName\" class=\"easyui-validatebox\" required=\"true\" missingMessage=\"元素名称不能为空\"></td></tr></table>&nbsp;&nbsp;<a id= \"PicSave\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-save\" onclick=\"addObj()\">保存</a>"
								+ "<br></div>";
		$(document.body).append(owHtml);
		
		$("#ObjWindow").window({
			title:'影像件元素',
			width:270,
			height:144,
			border: false,
			left: 50,
			top: 50,
			shadow: true,
			closed: true,
			closable:true,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			resizeable:false,
			draggable:true
		});
		$('#objCode').validatebox();
		$('#objName').validatebox();
		$('#PicSave').linkbutton();
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
	if(!$('#PicTabs').tabs('exists',title)){
		var sImg = new Image;
		sImg.onload = function(){
			var content = "<img id=\"Pic"+id+"\" src=\""+src+"\"/>";
			$('#PicTabs').tabs('add',{
				id:id,
				title:title,
				content:content,
				closable:false
			});
			$('body').data('panel'+id,$('#PicTabs').tabs('getTab',title));
			$('body').data('crop'+id,$.Jcrop('#Pic'+id,{onChange: showCoords,onSelect: showCoords}));
		};
		sImg.src = src;
	}
} 

function intoAddObj() {
	oper='1';
	$('#objCode').val("");
	$('#objName').val("");
	var l = $('#mb').offset().left;
	var t = $('#mb').offset().top;
	$('#ObjWindow').window('move',{left:l,top:t});	
	$('#ObjWindow').window('open');
}

function intoEditObj() {
	oper='7';
	$('#objCode').attr("readonly",true);
	var l = $('#mb').offset().left;
	var t = $('#mb').offset().top;
	$('#ObjWindow').window('move',{left:l,top:t});	
	$('#ObjWindow').window('open');
}

function addObj() {
	
	var objCode = $.trim($('#objCode').val());
	if(objCode==null||objCode==""){
		alert("元素代码不能为空!");
		return;
	}
	var objName = $.trim($('#objName').val());
	if(objName==null||objName==""){
		alert("元素名称不能为空!");
		return;
	}
	var req = /^([A-Z]|[a-z]|[\d])*$/;
	if(!req.test(objCode)){
		alert("元素代码只能为数字和字母的组合!");
		return;
	}
	lockPage('元素添加中....');
	var params = {
		oper:oper,
		subType:subType,
		objCode:objCode,
		objName:encodeURI(objName)
	}
  $.post(
  	"ScanPositionDefSubmit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
  		 var f = data.Flag;
  		 
  		 if(f=="F"){
  		 		$.messager.alert('错误',data.Msg,'error');
  		 }else{
  		 		$.messager.alert('消息',"添加成功",'info');	
  		 		$('#objCode').attr("readonly",false);
  		 		$('#ObjWindow').window('close');
  		 		$('#st').tree('reload');
  		 }
  		 unLockPage();	 
  	},"json" 
	);
}

function delObj() {
	var objCode = $('#st').tree('getSelected').id;
	if(objCode==null||objCode==""){
		alert("要删除的元素不能为空!");
		return;
	}
	if(objCode==subType){
		alert("该元素不能删除!");
		return;
	}
	
	$.messager.confirm('确认', '确认删除'+objCode+'元素?', function(r){
		 if (r){
				lockPage('正在删除....');
				var params = {
					oper:'2',
					subType:subType,
					objCode:objCode
				}
  			$.post(
  				"ScanPositionDefSubmit.jsp?sid="+getTimeForURL(),
  				params,
  				function(data) {
  					 var f = data.Flag;
  					 if(f=="F"){
  					 		$.messager.alert('错误',data.Msg,'error');
  					 }else{
  					 		$.messager.alert('消息',"删除成功",'info');	
  					 		$('#objCode').attr("readonly",false);
  					 		$('#st').tree('reload');
  					 }
  					 unLockPage();	 
  				},"json" 
				);
		 }
	});


}

function delPages() {
	var pageTitle = $('#PicTabs').tabs('getSelected').panel('options').title;
	var pageCode = $('#PicTabs').tabs('getSelected').panel('options').id;
	$.messager.confirm('确认', '确认要删除'+pageTitle+'?', function(r){
		 if (r){	 	
		 		var count =	$('#PicTabs').tabs('tabs').length;
		 		if(pageCode!=count){
		 			$.messager.alert('错误','必须从影像件的最后一页开始删除!','error');
		 			return;
		 		}
				lockPage('正在删除....');
				var params = {
					oper:'5',
					subType:subType,
					pageCode:pageCode
				}
  			$.post(
  				"ScanPositionDefSubmit.jsp?sid="+getTimeForURL(),
  				params,
  				function(data) {
  					 var f = data.Flag;
  					 if(f=="F"){
  					 		$.messager.alert('错误',data.Msg,'error');
  					 }else{
  					 		$.messager.alert('消息',"删除成功",'info');	
  					 		$('#PicTabs').tabs('close',pageTitle);
  					 }
  					 unLockPage();	 
  				},"json" 
				);
		 }
	});


}

function positionSave(){
	var node = $('#st').tree('getSelected');
	if(node==null){
		alert("请先选择一个影像件元素!");
		return;
	}
	var objCode = node.id;

	if(objCode==subType){
		alert("该元素不能进行随动定制!");
		return;
	}
	var x1= $('#x').val();
	var y1= $('#y').val();
	var x2= $('#x2').val();
	var y2= $('#y2').val();
	var w= $('#w').val();
	var h= $('#h').val();
	if(w<5||h<5){
		$.messager.alert('错误','截图长度过小','error');
		return;
	}
	lockPage('保存中，请稍候....');
	var picIndex = $('#PicTabs').tabs('getSelected').panel('options').id;
	var params = {
		oper:'3',
		subType:subType,
		objCode:objCode,
		picIndex:picIndex,
		x1:x1,
		y1:y1,
		x2:x2,
		y2:y2		
	}
  $.post(
  	"ScanPositionDefSubmit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
  		 var f = data.Flag;
  		 if(f=="F"){
  		 		$.messager.alert('错误',data.Msg,'error');
  		 		unLockPage();	 
  		 }else{
  		 		$.messager.alert('消息',"定制成功",'info');	
  		 		initPosArray();
  		 }
  		 
  	},"json" 
	);	
}

function showCoords(c){
	$('#x').val(c.x);
	$('#y').val(c.y);
	$('#x2').val(c.x2);
	$('#y2').val(c.y2);
	$('#w').val(c.w);
	$('#h').val(c.h);
};



function initPosArray(){
	var params = {
			oper:'3',
			subType:subType 
  };
  $.post(
  	"ScanPositionDefInit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
			positionArray = data;
			unLockPage();
		},"json" 
	);
}

function queryScanPages(){
	var params = {
		oper:'2',
		subType:subType
	}
  $.post(
  	"ScanPositionDefInit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
  			var baseName= '影像件第';
  			var order=1;
				$.each(data, function(i, n) {
					 var picPath = data[i].picPath;
					 addTab(baseName+order+'页',order,picPath);
					 order++;
				}); 
  			if(order>1){
  					$('#PicTabs').tabs('select','影像件第1页');
  			}
  	},"json" 
	);
}

function searchPst(){    
		var node = $('#st').tree('getSelected');
  	var objCode = node.id;
		$.each(positionArray, function(i, n) {
			 
			 var tObjCode = positionArray[i].objCode;
			 if(tObjCode == objCode){
			 		var tPicIndex = positionArray[i].picIndex;
			 		var x1 = positionArray[i].x1;
			 		var y1 = positionArray[i].y1;
			 		var x2 = positionArray[i].x2;
			 		var y2 = positionArray[i].y2;
			 		if(tPicIndex ==0||tPicIndex =="0"){
			 			return false;
			 		}
			 		var count =	$('#PicTabs').tabs('tabs').length;
		 			if(parseInt(tPicIndex)>parseInt(count)){
		 				return false;
		 			}
					$('#PicTabs').tabs('select','影像件第'+tPicIndex+'页');
		 			var panelOpt = $('#PicTabs').tabs('getSelected').panel('options');
		 			var st=0;
		 			var sl=0;
		 			var ph = parseInt(panelOpt.height);
        	
		 			if(y1 > ph){
		 				st = parseInt(y1)-(0.5*ph);
		 			}

		 			if(x1 > $(window).width()/2){
		 				sl = parseInt(x1)-(0.5*$(window).width());
		 			}

					var panel = $('body').data('panel'+tPicIndex);
		 			panel.scrollTop(st);
					panel.scrollLeft(sl);
					var crop = $('body').data('crop'+tPicIndex);
					crop.animateTo( [ x1, y1, x2, y2 ] );
					return false;
 				}
	});
}

function signDefSave(){
		var x1= $('#x').val();
		var y1= $('#y').val();
		var w= $('#w').val();
		var h= $('#h').val();
		if(w<5||h<5){
			$.messager.alert('错误','截图长度过小','error');
			return;
		}
		lockPage('保存中，请稍候....');

		var pageCode = $('#PicTabs').tabs('getSelected').panel('options').id;
		var params = {
			oper:'4',
			subType:subType,
			cropType:'7',
			pageCode:pageCode,
			x1:x1,
			y1:y1,
			w:w,
			h:h		
		}
  	$.post(
  		"ScanPositionDefSubmit.jsp?sid="+getTimeForURL(),
  		params,
  		function(data) {
  			 var f = data.Flag;
  			 if(f=="F"){
  			 		$.messager.alert('错误',data.Msg,'error');
  			 }else{
  			 		$.messager.alert('消息',"定制成功",'info');	
  			 }
  			 unLockPage();	  
  		},"json" 
		);	
	
}

function signDefQuery(){
	var params = {
			oper:'4',
			subType:subType,
			cropType:'7'
  };
  $.post(
  	"ScanPositionDefInit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
			var size = data.size;
			if(size>0){
					var pageCode = data.pageCode;
			 		var x1 = data.x1;
			 		var y1 = data.y1;
			 		var width = data.width;
			 		var height = data.height;
			 		$('#PicTabs').tabs('select','影像件第'+pageCode+'页');
		 			var panelOpt = $('#PicTabs').tabs('getSelected').panel('options');
		 			var st=0;
		 			var sl=0;
		 			var ph = parseInt(panelOpt.height);
        	
		 			if(y1 > ph){
		 				st = parseInt(y1)-(0.5*ph);
		 			}
		 			
		 			if(x1 > $(window).width()/2){
		 				sl = parseInt(x1)-(0.5*$(window).width());
		 			}
		 			var x2 = parseInt(x1)+parseInt(width);
 					var y2 = parseInt(y1)+parseInt(height);
					var panel = $('body').data('panel'+pageCode);
		 			panel.scrollTop(st);
					panel.scrollLeft(sl);
					var crop = $('body').data('crop'+pageCode);
					crop.animateTo( [ x1, y1, x2, y2 ] );
			}else{
					$.messager.alert('消息',"签名影像尚未定制",'info');	
			}
		},"json" 
	);
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



