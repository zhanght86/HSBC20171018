var rtArray = new Array();
var positionArray ;

//
var currentLoadPage = 0;

//最大初始化扫描页面数
var maxLoadPage = 10;
//延时刷新时间
var refreshTime = 100;



function nextPage(){
		var picId = $('#PicTab').tabs('getSelected').panel('options').id;
		picId++;
		if(picId>arrPicName.length){
			picId = picId-arrPicName.length;
		}
		$('#PicTab').tabs('select','影像件第'+picId+'页');	
}



function document_onkeydown() {
	var keycode = event.keyCode
	if (keycode == "33") {
	  top.fraPic.previousPage();
	}
	if (keycode == "34") {
		top.fraPic.nextPage();
	}
	if (keycode == "35") {
		top.fraPic.rotateBack();
	}
	if (keycode == "36") {
		top.fraPic.changeViewMode();
	}
	if (((keycode == "100")||(keycode == "37")) && (event.altKey == true)) {
	  top.fraPic.rotateLeft();
	}
	if (((keycode == "102")||(keycode == "39")) && (event.altKey == true)) {
	   top.fraPic.rotateRight();
	}
}
function rotateRight(){
	var picId = $('#PicTab').tabs('getSelected').panel('options').id;
	$('#TabPic'+picId).rotateRight(90);
	rtArray[picId-1]++;
}
function rotateLeft(){
	var picId = $('#PicTab').tabs('getSelected').panel('options').id;
	$('#TabPic'+picId).rotateLeft(90);
	rtArray[picId-1]--;

}
function goToPicForView(picSrc){
	try { $("#scanPicture").attr('src',picSrc);} catch(e) {}
}
function rotateBack(){
		if(top.s_img != 0 ||top.b_img!=0){
			top.fraPic.service.width = w
		}
		top.s_img = 0;
		top.b_img = 0;
		var ys = rtState%4;
		if(ys >0 ){
			$('#service').rotateLeft(ys*90);
		}else if(ys <0){
			$('#service').rotateLeft((4+ys)*90);
		}
		rtState = 0;
}
function initEasyScanPage(){
	if(arrPicName == null || arrPicName.length<1){
		alert("影像件加载失败！");
		return
	}
	
	$('#PicTab').tabs({
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
	var baseName= '影像件第';
	var order=1;
	currentLoadPage = 0;
	$.each(arrPicName,function(key,val){
		rtArray[key]=0;
		addTab(baseName+order+'页',order,val);
		order++;
	});
	
	
	//tab被选中时刷新
	$('#PicTab').tabs({ 
						onSelect:function(title){ 
				 			refresh_img(title);
				 			
				} 
				});
				
	initUpLoad();	
}

//刷新影像件
function refresh_img(title){
	var refresh_tab = $('#PicTab').tabs('getSelected');
	var imgId =  refresh_tab.find('img')[0].getAttribute('id');

			if(refresh_tab){
				var refreshFlag = refresh_tab.find('img')[0].loadflag;
				//alert(refreshFlag);
				if(refreshFlag=='0'){
					
					var img_src = $('#'+imgId).attr('img_src')+"?t="+new Date().getTime();
					var sImg = new Image;
					var trueID = imgId.replace('TabPic','');
						sImg.onload = function(){
  							//alert("W:H-"+sImg.width+sImg.height);
 									$('#'+imgId).width(sImg.width);
 									$('#'+imgId).height(sImg.height);
  									
 									$('#'+imgId).attr('src',img_src);
									$('body').data('panel'+trueID,$('#PicTab').tabs('getTab',title))
									$('body').data('crop'+trueID,$.Jcrop('#'+imgId,{onChange: showCoords,onSelect: showCoords}));
									$('body').data('crop'+trueID+'Init',1);
									$('#'+imgId).attr('loadflag','1');
  							 
     				}
      				
        				//IE加载顺序有问题，延时设置src
        				 setTimeout(function(){sImg.src = img_src;},refreshTime);
        	
				}
			}
}

function addTab(title,id,src){
	
	var content = "";
	var loadFlag = true;
	if(currentLoadPage<maxLoadPage){
		//直接加载
		content = "<img id=\"TabPic"+id+"\" loadflag=\"1\"  src=\""+src+"\"/ img_src=\"../common/images/loading.gif\">";
	}else{
		//按需加载
		content = "<img id=\"TabPic"+id+"\" loadflag=\"0\"  img_src=\""+src+"\"/ src=\"../common/images/loading.gif\">";
		loadFlag = false;
	}
	
	if(!$('#PicTab').tabs('exists',title)){
			$('#PicTab').tabs('add',{
				id:id,
				title:title,
				content:content,
				closable:false
			});
			
			if(loadFlag){
					$('body').data('panel'+id,$('#PicTab').tabs('getTab',title));
					$('body').data('crop'+id,$.Jcrop('#TabPic'+id,{onChange: showCoords,onSelect: showCoords}));
					$('body').data('crop'+id+'Init',1);
			}
			
		
			currentLoadPage = currentLoadPage + 1;
	}	
}
function previousPage(){
		var picId = $('#PicTab').tabs('getSelected').panel('options').id;
		picId--;
		if(picId<1){
			picId = picId+arrPicName.length;
		}
		$('#PicTab').tabs('select','影像件第'+picId+'页');
}
function nextPage(){
		var picId = $('#PicTab').tabs('getSelected').panel('options').id;
		picId++;
		if(picId>arrPicName.length){
			picId = picId-arrPicName.length;
		}
		$('#PicTab').tabs('select','影像件第'+picId+'页');	
}
function changeViewMode(){
		top.viewMode++;
	  var vm = top.viewMode;
	  if(vm%3 == 1){
	  	top.fraSet.rows = "0,50%,*,0";
	    top.fraSet.cols = "*";
			top.fraInterface.ScanViewHide();
	  }else if (vm%3 == 2){
	  	top.fraSet.rows = "*";
	    top.fraSet.cols = "0%,48%,*,0%";
	  }else {
	  	var idx = top.fraPic.getSelectPicIndex();
	  	top.fraSet.rows = "0,0,*,0";
	    top.fraSet.cols = "*";
	    top.fraInterface.ScanViewShow(idx);
	  }
}
function getSelectPicIndex(){
	var picId = $('#PicTab').tabs('getSelected').panel('options').id;
	return picId;
}

function ViewShow(objName,viewMode){
	
	
	if(positionArray == null){
		positionArray = top.fraInterface.positionArray;
	}
	$.each(positionArray, function(i, n) {
		 var tObjName = positionArray[i].objCode;
		 if(tObjName == objName){
		 		var tPicIndex = parseInt(positionArray[i].picIndex);
		 		var x1 = parseInt(positionArray[i].x1);
		 		var y1 = parseInt(positionArray[i].y1);
		 		var x2 = parseInt(positionArray[i].x2);
		 		var y2 = parseInt(positionArray[i].y2);
		 		var picId = $('#PicTab').tabs('getSelected').panel('options').id;
		 		
		 		if(picId!=tPicIndex){
		 			$('#PicTab').tabs('select','影像件第'+tPicIndex+'页');
		 		}
		 		var panelOpt = $('#PicTab').tabs('getSelected').panel('options');
		 		var st=0;
		 		var sl=0;
		 		var ph = parseInt(panelOpt.height);

		 		if(y1 > ph){
		 			st = parseInt(y1)-(0.5*ph);
		 		}
		 		if(viewMode == 2){
		 			if(x1 > $(window).width()/2){
		 				sl = parseInt(x1)-(0.5*$(window).width());
		 			}
		 		}
		 		
		 		var cropFlag = 0;
				try{
					cropFlag = $('body').data('crop'+tPicIndex+'Init');
					}catch(e){
						cropFlag= 0;
					}
					if(cropFlag==undefined||cropFlag!='1'){
						//未初始化，延时调用
						setTimeout(function(){ViewShow(objName,viewMode);},refreshTime);
					}
					else{
					
						var panel = $('body').data('panel'+tPicIndex);
		 				panel.scrollTop(st);
						panel.scrollLeft(sl);
				
						var crop = $('body').data('crop'+tPicIndex);
						crop.setSelect( [ x1, y1, x2, y2 ] );
					}
				return false;
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

function delPages() {
	var pageTitle = $('#PicTab').tabs('getSelected').panel('options').title;
	var pageCode = $('#PicTab').tabs('getSelected').panel('options').id;
	$.messager.confirm('确认', '确认要删除'+pageTitle+'?', function(r){
		 if (r){	 	
		 		var count =	$('#PicTab').tabs('tabs').length;
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
  					 		$('#PicTab').tabs('close',pageTitle);
  					 }
  					 unLockPage();	 
  				},"json" 
				);
		 }
	});


}

function positionSave(){

	var objCode = top.fraInterface.objName;
	if(objCode==null || objCode == ""){
		alert("请先选择一个录入域!");
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
	var picIndex = $('#PicTab').tabs('getSelected').panel('options').id;
	var params = {
		oper:'6',
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
  					$('#PicTab').tabs('select','影像件第1页');
  			}
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
			 		$('#PicTab').tabs('select','影像件第'+pageCode+'页');
		 			var panelOpt = $('#PicTab').tabs('getSelected').panel('options');
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
					crop.setSelect( [ x1, y1, x2, y2 ] );
			}else{
					$.messager.alert('消息',"签名影像尚未定制",'info');	
			}
		},"json" 
	);
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

		var pageCode = $('#PicTab').tabs('getSelected').panel('options').id;
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
			top.fraInterface.positionArray = data;
			unLockPage();
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
