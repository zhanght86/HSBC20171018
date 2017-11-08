$(document).ready(initScanDef);
var oper = '1';
var positionArray;
function initScanDef(){
	if(subType==null ||subType==""){
		alert("Ӱ�������δ����");
		return;
	}
	lockPage("���ݼ����У����Ժ�...");
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
				text:'<div id="newPage"><B>�����Ӱ���</B></div>',
				handler: function(){
				}
			},{
				iconCls:"icon-remove",
				text:'<B>ɾ��Ӱ���</B>',
				handler: function(){
					delPages();
				}
			},{
				iconCls:"icon-save",
				text:'<B>�涯����</B>',
				handler: function(){
					positionSave();
				}
			},{
				iconCls:"icon-search",
				text:'<B>ǩ��Ӱ��λ�ò鿴</B>',
				handler: function(){
					signDefQuery();
				}
			},{
				iconCls:"icon-cut",
				text:'<B>ǩ��Ӱ����</B>',
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
					lockPage('ͼƬ�ϴ���....');
			} else {
				$.messager.alert('����','��֧�ֵ�ͼƬ��ʽ��','error');
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
								+ "	<table><tr><td>Ԫ�ش���:</td><td><input id=\"objCode\" name=\"objCode\" class=\"easyui-validatebox\" required=\"true\" missingMessage=\"Ԫ�ش��벻��Ϊ��\"></td></tr>"
								+ "<tr><td>Ԫ������:</td><td><input id=\"objName\" name=\"objName\" class=\"easyui-validatebox\" required=\"true\" missingMessage=\"Ԫ�����Ʋ���Ϊ��\"></td></tr></table>&nbsp;&nbsp;<a id= \"PicSave\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-save\" onclick=\"addObj()\">����</a>"
								+ "<br></div>";
		$(document.body).append(owHtml);
		
		$("#ObjWindow").window({
			title:'Ӱ���Ԫ��',
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
		alert("Ԫ�ش��벻��Ϊ��!");
		return;
	}
	var objName = $.trim($('#objName').val());
	if(objName==null||objName==""){
		alert("Ԫ�����Ʋ���Ϊ��!");
		return;
	}
	var req = /^([A-Z]|[a-z]|[\d])*$/;
	if(!req.test(objCode)){
		alert("Ԫ�ش���ֻ��Ϊ���ֺ���ĸ�����!");
		return;
	}
	lockPage('Ԫ�������....');
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
  		 		$.messager.alert('����',data.Msg,'error');
  		 }else{
  		 		$.messager.alert('��Ϣ',"��ӳɹ�",'info');	
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
		alert("Ҫɾ����Ԫ�ز���Ϊ��!");
		return;
	}
	if(objCode==subType){
		alert("��Ԫ�ز���ɾ��!");
		return;
	}
	
	$.messager.confirm('ȷ��', 'ȷ��ɾ��'+objCode+'Ԫ��?', function(r){
		 if (r){
				lockPage('����ɾ��....');
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
  					 		$.messager.alert('����',data.Msg,'error');
  					 }else{
  					 		$.messager.alert('��Ϣ',"ɾ���ɹ�",'info');	
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
	$.messager.confirm('ȷ��', 'ȷ��Ҫɾ��'+pageTitle+'?', function(r){
		 if (r){	 	
		 		var count =	$('#PicTabs').tabs('tabs').length;
		 		if(pageCode!=count){
		 			$.messager.alert('����','�����Ӱ��������һҳ��ʼɾ��!','error');
		 			return;
		 		}
				lockPage('����ɾ��....');
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
  					 		$.messager.alert('����',data.Msg,'error');
  					 }else{
  					 		$.messager.alert('��Ϣ',"ɾ���ɹ�",'info');	
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
		alert("����ѡ��һ��Ӱ���Ԫ��!");
		return;
	}
	var objCode = node.id;

	if(objCode==subType){
		alert("��Ԫ�ز��ܽ����涯����!");
		return;
	}
	var x1= $('#x').val();
	var y1= $('#y').val();
	var x2= $('#x2').val();
	var y2= $('#y2').val();
	var w= $('#w').val();
	var h= $('#h').val();
	if(w<5||h<5){
		$.messager.alert('����','��ͼ���ȹ�С','error');
		return;
	}
	lockPage('�����У����Ժ�....');
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
  		 		$.messager.alert('����',data.Msg,'error');
  		 		unLockPage();	 
  		 }else{
  		 		$.messager.alert('��Ϣ',"���Ƴɹ�",'info');	
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
  			var baseName= 'Ӱ�����';
  			var order=1;
				$.each(data, function(i, n) {
					 var picPath = data[i].picPath;
					 addTab(baseName+order+'ҳ',order,picPath);
					 order++;
				}); 
  			if(order>1){
  					$('#PicTabs').tabs('select','Ӱ�����1ҳ');
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
					$('#PicTabs').tabs('select','Ӱ�����'+tPicIndex+'ҳ');
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
			$.messager.alert('����','��ͼ���ȹ�С','error');
			return;
		}
		lockPage('�����У����Ժ�....');

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
  			 		$.messager.alert('����',data.Msg,'error');
  			 }else{
  			 		$.messager.alert('��Ϣ',"���Ƴɹ�",'info');	
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
			 		$('#PicTabs').tabs('select','Ӱ�����'+pageCode+'ҳ');
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
					$.messager.alert('��Ϣ',"ǩ��Ӱ����δ����",'info');	
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



