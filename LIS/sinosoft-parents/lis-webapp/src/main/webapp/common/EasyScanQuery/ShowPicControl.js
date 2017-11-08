var rtArray = new Array();
var positionArray ;

//延时刷新时间
var refreshTime = 100;
var currentPage = 0;

var knockBottomTimes = 1; 
var maxKnockBottomTimes = 10;

var knockTopTimes = 1; 
var maxKnockTopTimes = 10;

var baseUrl = './LisImageShow.jsp?img_url=';
var imgAlt = '影像件第';
var refreshTime = 100;

//IMG缓存
var imgCacheCount =3;
var imgCacheArray = new Array(imgCacheCount);

//初始化影像缓存
function initImgCache(){
	for(var i=0;i<imgCacheArray.length;i++){
				imgCacheArray[i]=new Image();
				loadImage(i);
	}
}

function loadImage(i){
	
}


function document_onkeydown() {
	var keycode = event.keyCode
	if (keycode == "33") {
	  top.fraPic.previousPage();
	  top.fraPic.resetPanelScroll();
	  return ;
	}
	if (keycode == "34") {
		top.fraPic.nextPage();
		top.fraPic.resetPanelScroll();
		return ;
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


//更新当前页和总页数
function setPage(){
	var pageText = "";
	var maxPage = arrPicName.length;
	//var currentPage = $('#imgArea').attr('img_id').replace('TabPic','');	
	pageText = '当前显示第 '+ currentPage +" 页"+ "  " +" 共 " + maxPage + " 页 ";
	$('#pageSpan').text(pageText);
}

function gotoPage(){
	var destPage = $('#gotopage').val();
	var currPage = currentPage;
	var maxPage = arrPicName.length;
	if(!isNumeric(destPage)){
		alert('请输入数值');
		$('#gotopage').focus();
		return false;
	}
	else if(destPage == currPage){
		alert('当前已经是第'+destPage+'页');
		return false;
	}else if(destPage>maxPage){
		alert('影像件最大'+maxPage+'页');
		return false;
	}
	
	getImgToTab(destPage,'','');
	resetKonckTimes();
}

function initEasyScanPage(){
	if(arrPicName == null || arrPicName.length<1){
		alert("影像件加载失败！");
		return
	}
	$('#PicTab').tabs({
			tools:[
			{
				
				text:'<B><span id=\'pageSpan\'>1/1  </span></B> <input id=\'gotopage\' class=\"easyui-textbox\" style=\"width:50px;height:20px\" />',
			
				handler: function(){
					$('#gotopage').focus();
					return;
				}
			},{
				iconCls:'icon-search',
				text:'<B>跳转到</B>',
				handler: function(){
					gotoPage();
				}
			},{
				iconCls:"icon-undo",
				text:'<B>上一页(PageUp)</B>',
				handler: function(){
					previousPage();
					
					
				}
			},{
				iconCls:"icon-redo",
				text:'<B>下一页(PageDown)</B>',
				handler: function(){
					nextPage();
				}
			},{
				iconCls:"icon-reload",
				text:'<B>切换显示模式(Home键)</B>',
				handler: function(){
					changeViewMode();
				}
			}]
	});
	var baseName= '影像件第';
	var order=1;

	//测试css预加载
	//$("#preLoadImg"). 
//	document.getElementById("preLoadImg").style.background = "url(\""+baseUrl+"10012518"+"\") no-repeat -9999px -9999px";
//	document.getElementById("preLoadImg").style.background = "url(\""+baseUrl+"90012519"+"\") no-repeat -9999px -9999px";
//	document.getElementById("preLoadImg").style.background = "url(\""+baseUrl+"90012520"+"\") no-repeat -9999px -9999px";

	//优化
	$.each(arrPicName,function(key,val){
		rtArray[key]=0;
		if(order==1){
			//第一个创建tab和放入缓存
			addTab('影像件',order,val);
			pushImgToCache(baseName+order+'页',order,val);
			
		}else{
			//其他都直接放入缓存
			pushImgToCache(baseName+order+'页',order,val);
			}
		
		order++;
	});
	currentPage = 1;
	setPage();
	//$('#PicTab').tabs('getTab','影像件').hide();
	//绑定panel滚动事件
	 //执行滚动，触发事件
  // $('#PicTab').tabs('getSelected').bind("scroll", scrollLoadImg);
}





//鼠标滚轮事件

function scrollLoadImg(e){
     e=e || window.event;
     var panel = $('#PicTab').tabs('getSelected');

		 var panelheight = panel.height();//panel 高度
		 var imgHeight = $('#imgArea').height();
		 if(imgHeight==null){
					return ;
		 }
		 var scrolltop = panel.scrollTop(); //滚动条偏移高度
		 var oTop = $('#imgArea').offset().top; //图片相对高度
		  //alert(panelheight+":"+imgHeight+":"+scrolltop+":"+oTop);
     if(e.wheelDelta){//IE/Opera/Chrome
     		if(e.wheelDelta>0){
     			//向上滚动
     			//alert('向上:'+e.wheelDelta);  			
     			if(scrolltop==0){
						//到顶了
						//alert('到顶了'+knockTopTimes);
						if(knockTopTimes==maxKnockTopTimes){
								previousPage();
								panel.scrollTop(1);
								panel.scrollLeft(0);
						
						}
						else{
								knockTopTimes ++;
						}
					}
     		}else{
     			//向下滚动
					if(imgHeight+oTop - scrolltop<=panelheight){
						//到底了
					 //alert(panelheight+":"+imgHeight+":"+scrolltop+":"+oTop);
						//alert('到底了'+oTop+":"+scrolltop+":"+knockBottomTimes);
						if(knockBottomTimes==maxKnockBottomTimes){
									nextPage();
									if(imgHeight>panelheight){
										panel.scrollTop(2);
										panel.scrollLeft(0);
									}
						}else{
								knockBottomTimes ++;
						}
					}
     		}
         
     }
}

function resetPanelScroll(){
	var panel = $('#PicTab').tabs('getSelected');
	//panel.scrollTop(0);
	//			panel.scrollLeft(0);
	 setTimeout(function(){
	 			panel.scrollTop(0);
				panel.scrollLeft(0);},100);
	
}
function resetKonckTimes(){
	knockBottomTimes=1;
	knockTopTimes=1;
}

//数据放到缓存
function pushImgToCache(title,id,src){
	//alert('push:'+id);
	$('body').data('TabPic'+id,src);
}

//数据移出缓存
function removeImgToCache(id){
	//alert('push:'+id);
	$('body').removeData('TabPic'+id);
}


//创建img对象
function createImg(id,src){
	 	var img = $('<img/>');      //创建一个img
    img.attr("src",baseUrl+src);    //设置图片
    img.attr("img_id",'TabPic'+id);
    img.attr('id','imgArea');
    return img;
}

//创建span对象
function createSpan(title){
	 	var span = $('<span/>');      
    span.html(title);
    return span;
}

//将影像件从缓存中取出来,并且放到tab中
function getImgToTab(id,objName,viewMode){
	var currimgId = currentPage;
	if(currimgId!=id){
		
   
		var img_src = $('body').data('TabPic'+id);//+"?t="+new Date().getTime();;
		var newImg =  createImg(id,img_src);
		newImg.attr('alt',imgAlt+id+'页');
	
		try{
			var imgArea = $('#imgArea');
			imgArea.removeData('Jcrop');
			imgArea.attr('src',null);
			imgArea.remove();
		}catch(e){};
		$('#imgDiv').empty();
		$('#imgDiv').html(newImg[0].outerHTML);
		
		//设置延迟
		 var sImg = new Image();
		 sImg.onload = function(){
		 		$('#imgArea').width(sImg.width);
 				$('#imgArea').height(sImg.height);
 				currentPage = id;
 				setPage();
 				try{
						$('body').data('crop'+currimgId).destroy();
				}catch(e){}
				$('body').removeData('crop'+currimgId);
				$('body').data('crop'+id,$.Jcrop('#imgArea',{onChange: showCoords,onSelect: showCoords}));
				
				if(objName!=''){
						ViewShow(objName,viewMode);
				}
 				
		 }
		 sImg.onerror = function(){
		 		//alert('图片有问题');
		 		createSpan('扫描件对应的图片不存在').appendTo($('#imgDiv'));
		 		$('#imgArea').hide();
		 		currentPage = id;
 				setPage();
			}
		
		 sImg.src = baseUrl+img_src	
		
		
		/*
		sImg.onload = function(){
  							
 								$('#imgArea').width(sImg.width);
 								$('#imgArea').height(sImg.height);									
 								$('#imgArea').attr('src',img_src);
 								$('#imgArea').attr('img_id','TabPic'+id);							
								$('body').data('crop'+currimgId).destroy();
								$('body').removeData('crop'+currimgId);
								$('body').data('crop'+id,$.Jcrop('#imgArea',{onChange: showCoords,onSelect: showCoords}));
								setPage();
								if(objName!=''){
									ViewShow(objName,viewMode);
								}
  							 
     				}
      sImg.src = img_src				*/
     // setTimeout(function(){sImg.src = img_src;},refreshTime);
	}
}


function addTab(title,id,src){
	var content = "";
	
	
	content = "<div id='imgDiv' ><img id=\"imgArea\"  src=\""+baseUrl+src+"\" alt=\""+imgAlt+id+"页\" img_id=\"TabPic"+id+"\" ></div>  ";
	
	 var sImg = new Image();
	 sImg.onload = function(){
			$('#PicTab').tabs('add',{
				id:'oneImage',
				title:title,
				content:content,
				closable:false,
				cache:false
			});
			
			$('body').data('crop'+id,$.Jcrop('#imgArea',{onChange: showCoords,onSelect: showCoords}));
	}
	
	sImg.src = baseUrl+src

	/*
		var sImg = new Image;
					
		sImg.onload = function(){
			
				$('#PicTab').tabs('add',{
				id:'oneImage',
				title:title,
				content:content,
				closable:false,
				cache:false
				});
			
				$('#'+imgArea).width(sImg.width);
 				$('#'+imgArea).height(sImg.height);
			
				$('body').data('crop'+id,$.Jcrop('#imgArea',{onChange: showCoords,onSelect: showCoords}));
  							 
     }
      				
    //IE加载顺序有问题，延时设置src
    setTimeout(function(){sImg.src = baseUrl+src;//sImg.dispose();
    	},refreshTime);
        				 
	*/
	
}

function previousPage(){
		//var picId = $('#imgArea').attr('img_id').replace('TabPic','');
		var picId = currentPage;
		picId--;
		if(picId<1){
			picId = picId+arrPicName.length;
		}
		getImgToTab(picId,'','');
		resetKonckTimes();
}
function nextPage(){
		//var picId = $('#imgArea').attr('img_id').replace('TabPic','');
		var picId = currentPage;
		picId++;
		if(picId>arrPicName.length){
			picId = picId-arrPicName.length;
		}
		getImgToTab(picId,'','');
		resetKonckTimes();
}
function changeViewMode(){
		if(typeof(top.viewMode) == "undefined")top.viewMode = 1;
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
	    if(typeof(top.fraInterface.ScanViewShow)=="function"){
	    	top.fraInterface.ScanViewShow(idx);
	    }
	  }
}
function getSelectPicIndex(){
	//var picId = $('#PicTab').tabs('getSelected').panel('options').id;
	//var picId = $('#imgArea').img_id;
		var picId = currentPage;
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
		 		//var picId = $('#PicTab').tabs('getSelected').panel('options').id;
		 		//var picId = $('#imgArea').attr('img_id').replace('TabPic','');
		 		var picId = currentPage;
		 		if(picId!=tPicIndex){
		 			//alert('change page:'+picId+":"+tPicIndex);
		 			//$('#PicTab').tabs('select','影像件第'+tPicIndex+'页');
		 			getImgToTab(tPicIndex,objName,viewMode);
		 			return false;
		 		}
		 		var panelOpt = $('#PicTab').tabs('getSelected').panel('options');
		 		var st=0;
		 		var sl=0;
		 		var ph = parseInt(panelOpt.height)/2;

		 		if(y1 > ph){
		 			st = parseInt(y1)-(0.5*ph);
		 		}
		 		if(viewMode == 2){
		 			if(x2 > panelOpt.width){
		 				sl = parseInt(x1);
		 				if(x2 - x1 > panelOpt.width){
		 					sl += 0.13*(x2-x1);
		 				}
		 			}
		 		}
		 		
				var panel = $('#PicTab').tabs('getSelected');
		 		panel.scrollTop(st);
				panel.scrollLeft(sl);
				
				var crop = $('body').data('crop'+tPicIndex);
				crop.setSelect( [ x1, y1, x2, y2 ] );		 		
		 		
				try{
					if(top.fraInterface.LoadFlag=="5")//只在复核使用
						ViewShowHelper(objName,x2-x1,y2-y1);
				}catch(ex){
					
				}
				return false;
		 }
	}); 		
}
/**
 * 以下代码为实现功能:在高亮区间下方显示要核对的信息
 */
function ViewShowHelper(objName,widthX,heightY){
				var $content = $(top.fraInterface.fm).find("[name='"+objName+"']");//内容所在对象
				var content = $content.val();//要显示的内容
				if($content.hasClass("codeno")||$content.hasClass("warnno")){//如果为codeno则有附加显示内容
					var $contentTemp = $content.parent().find("[class='codename']");
					if($contentTemp.size()>0){
						$content = $contentTemp;
						content += ":"+$content.val();
					}
				}
				var title = $content.parent().prev().html();//显示标题
				var $ContentWindow = $("#ContentWindow");//显示方框对象
				if($ContentWindow.size()==0){//只在第一次初始化
						var contentHtml = "<div id=\"ContentWindow\" style=\"text-align:center;font-size=20\"></div>";
						$(document.body).append(contentHtml);
						$ContentWindow = $("#ContentWindow");
						$(document.body).click(function(){$ContentWindow.offsetParent().hide().next().eq(0).hide();});//点击窗体隐藏
				}
				
				//以下是位置信息
				var offS = $('#PicTab').tabs('getSelected').parent().find(".jcrop-tracker").eq(0).offset();//
				var leftX = offS.left;//相对左坐标
				var topY = offS.top+heightY;//相对顶坐标，加上高亮高度
				$ContentWindow.window({
					title:title,
					width:widthX,//不加高度，则自动适应
					border: false,
					left: leftX,
					top: topY,
					shadow: false,
					closed: false,
					closable:false,
					collapsible:false,
					minimizable:false,
					maximizable:false,
					resizeable:false,
					draggable:true
				}).html(content);
}


