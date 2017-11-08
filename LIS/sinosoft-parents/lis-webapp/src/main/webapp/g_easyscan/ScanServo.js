/***************************************************************
 * <p>ProName：ScanServo.js</p>
 * <p>Title：影像随动</p>
 * <p>Description：影像随动</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 刘锦祥
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
 
 
$(document).ready(ScanViewCreate);//加载方法

var objName;//控件名称
var picArray = "";//图片信息
var positionArray ;//存储随动定制位置（一行存储一个随动定制位置）

/**
* 初始化图片信息,以及位置信息
*/
function ScanViewCreate(){
 
	if(scantype==2) {//判断是否做随动 1-随动，2-随动定制
		parent.fraPic.initPosArray();
	}else if(scantype==1) {
		ScanViewModeCreate();
		parent.fraPic.initPosArray();
	}
	
}

/**
* 为每个界面录入控件增加相应随动的事件，在业务界面中调用
*/
function setFocus() {

	var RowNo=0;
	var MultLineName = "";
	for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { //获取所有控件
	
		var tFunction = "goToArea";
		if(window.document.forms[0].elements[elementsNum].name.indexOf("Grid")==-1){//对muliline中的值
			eval(" window.document.forms[0].elements[elementsNum].onfocus = "+tFunction);  //焦点集中时触发方法
		} else {
		var tName = window.document.forms[0].elements[elementsNum].name.substring(0,window.document.forms[0].elements[elementsNum].name.indexOf('Grid'));
		
		if(MultLineName == null || MultLineName ==""){  
			MultLineName = tName;
		} else {
			
			if(MultLineName!=tName) {
				MultLineName = tName;
				RowNo = 0 ;
			}
			if(window.document.forms[0].elements[elementsNum].name.indexOf("No")!=-1){
				RowNo = RowNo + 1;
			}
		}
			eval("window.document.forms[0].elements[elementsNum].setAttribute('MultLineId','"+RowNo+"');");
			eval("window.document.forms[0].elements[elementsNum].onfocus = "+tFunction);   
		}  
	}
	 
	var mullist = $('.muline');
	$('.muline').show();
	$.each(mullist, function(i){
	try{
		mullist[i].click(function(){
		
			var objID = mullist[i].id;
			if(objID!=null && objID !=""){
				goToAreaById(objID);
			}	
		});
		} 
	catch(ex){}
	});
}

/**
* 对非mulline随动位置的展示
*/
function goToArea() {

  objName = this.name;
  try {
		 var vm = top.viewMode%3;
		 
		 if(vm!=0){
		 		try{parent.fraPic.ViewShow(objName,vm);}catch(e){};
		 }else{
		 		parent.fraInterface.ScanViewShowStation(objName,0);
		 }
	} catch(e) {}
			
}

/**
* 对muline随动的处理
*/
function goToAreaById(objID) {
	
	objName = objID;
	try {
		var vm = top.viewMode%3;
		if(vm!=0){
			try{parent.fraPic.ViewShow(objName,vm);}catch(e){};
		}else{
			parent.fraInterface.ScanViewShowStation(objName,0);
		}
	} catch(e) {}
}

/**
* 按键触发
*/
function document_onkeydown() {
	
	var keycode = event.keyCode
	if (keycode == "33") {
	  parent.fraPic.previousPage();
	}
	if (keycode == "34") {
		parent.fraPic.nextPage();
	}
	if (keycode == "35") {
		parent.fraPic.rotateBack();//旋转图片180
	}
	if (keycode == "36") {
		parent.fraPic.changeViewMode();
	}
	if (((keycode == "100")||(keycode == "37")) && (event.altKey == true)) {
	  parent.fraPic.rotateLeft();//向左旋转
	}
	if (((keycode == "102")||(keycode == "39")) && (event.altKey == true)) {
	  parent.fraPic.rotateRight();//向右旋转
	}
}

/**
 * -----------------------------------------------------图片迷你模式---------------------------------
 */
 
/**
* 随动创建迷你模式
*/
function ScanViewModeCreate(){
	
	parent.fraPic.lockPage("数据加载中...........");
	
	var vm = parent.fraPic.viewMode;
	picArray = parent.fraPic.arrPicName;
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
	if(vm%3!=0){
		ScanViewHide();
	}
	
}

/**
* 关闭迷你模式
*/
function ScanViewHide(){
	$("#scanViewWindow").hide();
	$("#scanViewWindow").window("close");
}

/**
* 打开迷你模式
*/
function ScanViewModeShow(idx){
	
	$("#scanPicture").attr("src",picArray[idx-1]);
	$("#scanViewWindow").show();
	$("#scanViewWindow").window("open");
	ScanViewHide();
	$("#scanPicture").attr("src",picArray[idx-1]);
	$("#scanViewWindow").show();
	$("#scanViewWindow").window("open");
}

/**
* 展示影像件迷你模式，模式为0时调用
*/
function ScanViewShowStation(objName,viewMode){

	$.each(positionArray, function(i, n) {
		 var tObjName = positionArray[i].objCode;
		 try{
		 	var objInput = $("[name='"+tObjName+"']").offset();
		 	var objWidth = objInput.left;
		 	var objHeight;
		 	objHeight = objInput.top + $("[name='"+objName+"']").height()+5;
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

//根据业务号，业务类型，单证细类查询影像件
function showscan(subType,bussNo,bussType) {

	parent.fraPic.showeasyscan(subType,bussNo,bussType);
}

//清空图片
function showclear() {
	parent.fraPic.showeasyclear();
}

/**
 * -----------------------------------------------------签名查询---------------------------------
 */

/**
 * 转向截取图片的操作
 */
function intoSignatureCut(BtnId){
	
	parent.fraPic.lockPage("请在投保扫描件显示区域进行签名截取!");
	parent.fraPic.rotateBack();
	var vm = top.viewMode;
	if(vm%3!=1){//还原到模式1去截取图片
			top.fraSet.rows = "0,50%,*,0";
			top.fraSet.cols = "*";
			parent.fraInterface.ScanViewHide();
			top.viewMode = 1;
	}
	$("#"+BtnId+"Window").window('close');//关闭图像窗口
	parent.fraPic.signatureCut(BtnId);//截取图片
}

/**
 * 截图查询
 */
function signatureQuery(Field){
	
	var BtnId = Field.id;
	var sqBtnId = '#'+BtnId;
	var sqWinId = sqBtnId+'Window';
	var sqPicId = sqBtnId+'Pic';
	var sqBtn = $(sqBtnId).offset();//获取匹配元素在当前视口的相对偏移。
	var ww = sqBtn.left + $(sqBtnId).width();//获取控件的宽度
	var wh = sqBtn.top + $(sqBtnId).height();//获取输入框的高度
	var cancut = $(sqBtnId).attr("cancut");//0表示不能截图,1为可以截取
	if(cancut == null || cancut == ""){
		cancut = "0";
	}

	if($(sqWinId).length>0){//判断是否已经查询过，直接将图片展示出来

		$(sqWinId).window('move',{left:ww,top:wh});
		$(sqWinId).window('open');
		if(($(sqWinId).window('options').height+wh-$(window).scrollTop())> $(window).height()){
			var newST = $(sqWinId).window('options').height+wh-$(window).height();
			$(document.body).scrollTop(newST);//弹出新图形界面
		}
		var pSrc = $(sqPicId).attr("src");
		if(pSrc == null || pSrc== ""){
			$(sqWinId).window('close');
			if(parent.fraPic.arrPicName == null){				
					alert('签名不存在!');
					return;
			}
			if(cancut=="1"){
				if(confirm('签名不存在，是否进行截取签名操作？')){
					intoSignatureCut(BtnId);
				}
			}else{
				alert('签名不存在!');
				return;
			}

		}
	}else{		
		   
				var codeType = $(sqBtnId).attr("codetype");//定义每一个随动界面的签名传入的业务号
				if(codeType == null || codeType == ""){
					codeType = "BussNo";//默认为BussNo,随动界面业务号，存储每一个随动界面的
				}
				if($("[name='"+codeType+"']").length==0){
					alert('codeType定义的页面元素'+codeType+'不存在!');
					return;
				}
				var docCode = $("[name='"+codeType+"']").val();//做为业务号存储到es表中
				var params = {
						Operate:'sq',
						DocCode:docCode
  			};
  			//查询签名的图片
  			jQuery.post(
  				"../easyscan/SignatureCut.jsp?Sid="+getTimeForURL(),
  				params,
  				function(data) {
						var size = data.Size;
						var cutHeight = 0;
						var cutHtml = "";
						if(cancut == "1"){//判断是否可以截取签名图片
							cutHtml = "<br>&nbsp;&nbsp;&nbsp;<a id= \""+BtnId+"Change\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\" onclick=\"intoSignatureCut('"+BtnId+"')\" >更换签名</a>";
							cutHeight = 30;
							$('#'+BtnId+'Change').linkbutton();
						}
						var signShowHtml = "<div id=\""+BtnId+"Window\">"
														 + "<div id=\""+BtnId+"Div\" ><img id=\""+BtnId+"Pic\" src=\"\"/></div>"+ cutHtml + "</div>";
						$(document.body).append(signShowHtml);//加载界面展示签名图片
						$(sqWinId).window({
							title:'签名影像',
							width:320,
							height:240,
							border: false,
							left: ww,
							top: wh,
							shadow: true,
							closable:true,
							collapsible:false,
							minimizable:false,
							maximizable:false,
							draggable:true
						});
						if(parent.fraPic.arrPicName == null){//判断影响件是否存在
							$("#"+BtnId+"Window").window('close');
							alert('签名不存在!');
							return;
						}
						if(size<1){//查询出图片为空
							$("#"+BtnId+"Window").window('close');
							if(cancut == "1"){
								if(confirm('签名不存在，是否进行截取签名操作？')){
									intoSignatureCut();
								}
							}else{
								alert('签名不存在!');
								return;
							}
						}else{
							//查询出了图片路径
							$('#'+BtnId+'Pic').attr("src",data.PicPath);
							var sImg = new Image;
							sImg.onload = function(){//展示图片
								var pw = sImg.width +14;
								if(pw<140){
									pw = 140;	
								}
								var ph = sImg.height +46 + cutHeight;
								$(sqWinId).window('resize',{width:pw,height:ph});
								$('#'+BtnId+'Div').show();
								if((ph+wh-$(window).scrollTop())> $(window).height()){
									var newST = ph+wh-$(window).height();
									$(document.body).scrollTop(newST);
								}
							};
							sImg.src = data.PicPath;
						}
						
					},"json" 
				);
	}
}

/**
 * 保存截图以后查询
 */
function afterSignCut(BtnId){

		var codeType = $('#'+BtnId).attr("codetype");
		if(codeType == null || codeType == ""){
			codeType = "BussNO";//默认为PrtNo
		}
		var docCode = $("[name='"+codeType+"']").val();
		var params = {
			Operate:'sq',
			DocCode:docCode		
  	};
  	jQuery.post(
  		"../easyscan/SignatureCut.jsp?Sid="+getTimeForURL(),
  		params,
  		function(data) {
  			var size = data.Size;
  			if(size<1){
 					alert('没有签名图片信息!');
  			}else{
						var cancut = $('#'+BtnId).attr("cancut");
						if(cancut == null || cancut == ""){
							cancut = "0";
						}
						var sqBtn = $('#'+BtnId).offset();
						var ww = sqBtn.left + $('#'+BtnId).width();
						var wh = sqBtn.top + $('#'+BtnId).height();
						var cutHeight = 0;
						if(cancut == "1"){
							cutHeight = 30;
						}
						$('#'+BtnId+'Pic').attr("src",data.PicPath);
						var sImg = new Image;
						sImg.onload = function(){
							var pw = sImg.width +14;
							if(pw<140){
  							pw = 140;	
  						}
  						var ph = sImg.height +46+cutHeight;
  						$("#"+BtnId+"Window").window('resize',{
							 	width:pw,
								height:ph,
								left: ww,
								top: wh
							});
							$("#"+BtnId+"Window").window('open');
							$('#'+BtnId+'Div').show();
							if((ph+wh-$(window).scrollTop())> $(window).height()){
								var newST = ph+wh-$(window).height();
								$(document.body).scrollTop(newST);
							}

						};
						sImg.src = data.PicPath;
  			}
  		},"json" 
		);
}

/**
 * 获取当前时间传入系统中
 */
function getTimeForURL() {
	
	//获得js时间
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
