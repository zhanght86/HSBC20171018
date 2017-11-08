$(document).ready(ScanViewCreate);
var positionArray;
var picArray;
var picIndex = 1;

//add by liuyuxiao 2011-05-27
var inTab = false;
function isInTab(){
	inTab = true;
}
//end add by liuyuxiao 2011-05-27

function ScanViewCreate(){
	lockPage("数据加载中...........");
	
	//modified by liuyuxiao 2011-05-27
	var vm;
	if(inTab == true){
		vm = parent.viewMode;
	}
	else{
		vm = top.viewMode;
	}
	if(inTab == true){
		picArray = parent.fraPic.arrPicName;
	}
	else{
		picArray = top.fraPic.arrPicName;
	}
	//end modified by liuyuxiao 2011-05-27
	$(document.body).append("<div id=\"scanViewWindow\" style=\"display:none\"><div id=\"imageView\" class=\"article\"><img id=\"scanPicture\" src=\"\"/></div></div>");
	$("#scanViewWindow").show();
	$("#scanViewWindow").window({
		title:'影像件',
		width:640,
		height:320,
		border: false,
		left: 320,
		top: 50,
		shadow: true,
		closed: false,
		closable:false,
		collapsible:false,
		minimizable:false,
		maximizable:false,
		resizeable:false,
		draggable:true,
		onResize:function(width,height){
						var picLeft = parseInt($('#scanPicture').css('left'));
						var picTop = parseInt($('#scanPicture').css('top'));
						var viewWidth = width-14;
						var viewHeight = height -36;
						//try{$("#scanPicture").attr("src",picArray[picIndex-1])}catch(ex){};
						$('#imageView').imageView({width: viewWidth, height:viewHeight});
						$('#scanPicture').css('left',picLeft);
 						$('#scanPicture').css('top',picTop);
						
		},onOpen:function(){
					var viewWidth = $("#scanViewWindow").window('options').width-14;
					var viewHeight = $("#scanViewWindow").window('options').height-36;
					$('#imageView').imageView({width: viewWidth, height:viewHeight});		
		}
	});
	try{$("#scanPicture").attr("src",picArray[0])}catch(ex){};
	if(vm%3 !=0){
		ScanViewHide();
	}
	initAllPosition();
}

function initAllPosition(){
	if(SubType==null ||SubType==""){
		//modified by liuyuxiao 2011-05-27
		if(inTab == true){
			try{SubType = parent.fraPic.subType;}catch(ex){}
		}
		else{
			try{SubType = top.fraPic.subType;}catch(ex){}
		}
		//end modified by liuyuxiao 2011-05-27
	}
		var params = {
			subType:SubType 
  	};
  jQuery.post(
  	"../common/jsp/ScanPicPosition.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
			positionArray = data;
			//modified by liuyuxiao 2011-05-27
			if(inTab == true){
				parent.fraPic.positionArray = data;
			}
			else{
				top.fraPic.positionArray = data;
			}
			//end modified by liuyuxiao 2011-05-27
			unLockPage();
		},"json" 
	);
}

function ScanViewHide(){
	$("#scanViewWindow").hide();
	$("#scanViewWindow").window('close');
}

function ScanViewShow(idx){
	$("#scanPicture").attr("src",picArray[idx-1]);
	$("#scanViewWindow").show();
	$("#scanViewWindow").window('open');
}

function ScanViewShowStation(objName,viewMode){
	$.each(positionArray, function(i, n) {
		 var tObjName = positionArray[i].objCode;
		 try{
		 	var objInput = $("[name='"+tObjName+"']").offset();
		 	var objWidth = objInput.left;
		 	var objHeight;
		 	//modified by liuyuxiao 2011-05-27
		 	if(inTab == true){
		 		objHeight = objInput.parent + $("[name='"+objName+"']").height()+5;
		 	}
		 	else{
		 		objHeight = objInput.top + $("[name='"+objName+"']").height()+5;
		 	}
		 	//end modified by liuyuxiao 2011-05-27
		 	
		 	if(tObjName == objName){
		 			var tPicIndex = parseInt(positionArray[i].picIndex);
		 			var x1 = parseInt(positionArray[i].x1);
		 			var y1 = parseInt(positionArray[i].y1);
		 			var x2 = parseInt(positionArray[i].x2);
		 			var y2 = parseInt(positionArray[i].y2);
		 			picIndex = tPicIndex-1;
		 			if(viewMode==0){
		 				var picSrc=picArray[picIndex];
		 				$("#scanPicture").attr("src",picSrc);
		 				lockPart("scanViewWindow","影像件定位中....");
		 				var sImg = new Image;
		 				var tWinWidth = x2-x1+14;
		 				var tWinHeight = y2-y1+36;
		 				sImg.onload = function(){
							$('#scanPicture').css('left',-x1);
 							$('#scanPicture').css('top',-y1);
 							$("#scanViewWindow").window('resize',{
 								width:tWinWidth,
 								height:tWinHeight
 							});
 							$("#scanViewWindow").window('move',{
 								left:objWidth,
								top:objHeight
							});	
							if(tWinWidth+objWidth-$(document.body).scrollLeft()>$(window).width()){
								var newST = tWinWidth+objWidth-$(window).width();
 								$(document.body).scrollLeft(newST);
 							}
 							unlockPart("scanViewWindow");	 			
		 				};
		 				sImg.src = picSrc;
					}
					return false;
		 	}
		 }catch(ex){}
	}); 	
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