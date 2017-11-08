
var winCutExists = 0;
var FPFlag = true;
var mBtnId;
var mSBArr = new Array();
function getURLParameter(uri, name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '='
		+ '([^&;]+?)(&|#|;|$)').exec(uri) || [, ""])[1]
		.replace(/\+/g, '%20'))
		|| null
}
function signatureCut(BtnId){
		var cutHtml = "<div id=\"PicCutWindow\"><br><br>"
								+ "&nbsp;&nbsp;&nbsp;&nbsp;<a id= \"PicCut\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-cut\" onclick=\"SignCutSave()\" >截取</a>"
								+ "&nbsp;&nbsp;<a id= \"PicSave\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-cancel\" onclick=\"SignCutCancel()\">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;"
								+ "<br><br></div>";
		$(document.body).append(cutHtml);
		
		var wh = $(window).height();
		var ws = $(window).scrollTop();
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
		winCutExists = 1;   
		$('a.easyui-linkbutton').linkbutton();
		if(FPFlag){
				$(window).scroll(function(){ 
						if(winCutExists == 1){
								var winHeight = $(window).height();
								var winScrTop = $(window).scrollTop();
								var picHeight = $("#PicCutWindow").window('options').height;
								var picTop = winScrTop + (winHeight - picHeight)/2;
								var picLeft = $("#PicCutWindow").window('options').left;
								$("#PicCutWindow").window('move',{
 									left:picLeft,
									top:picTop
								});
						}
				});
				FPFlag = false;
		}
		mBtnId = BtnId;

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
	var picId = $('#PicTab').tabs('getSelected').panel('options').id;
	//修改
	//alert(picId);
	var picSrc = "";
	if(picId == 'oneImage'){
		//优化后的单tab显示影像随动
		picId = $('#imgArea').attr('img_id').replace('TabPic','');	
		picSrc  = $('#imgArea').attr('src');
	}
	else{
		picSrc = $('#TabPic'+picId).attr("src");
	}
	var picName = picSrc.substring((picSrc.lastIndexOf("/")+1),picSrc.lastIndexOf("."));
    if (picName.toUpperCase() == ("LisImageShow").toUpperCase()) {
        picName = "F" + getURLParameter(picSrc, "img_url");
    }
	var docCode = $('#DocCode').val();
	var params = {
			oper:'cut',
			pageName:picName,
			docCode:docCode,
			x1:x1,
			y1:y1,
			w:w,
			h:h			
  	};
  jQuery.post(
  	"../jsp/SignatureCut.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
			var flag = data.Flag;
			if(flag == "Succ"){
				$.messager.alert('消息','截图成功','info',function(){
						SignCutCancel();
						top.fraInterface.afterSignCut(mBtnId);
				});
			}else{
				$.messager.alert('错误',data.Info,'error');
			}
			unlockPart('PicCutWindow');
		},"json" 
	);
	
}


function SignCutCancel(){
	winCutExists = 0;
	$('#x').val(0);
	$('#y').val(0);
	$('#x2').val(0);
	$('#y2').val(0);
	$('#w').val(0);
	$('#h').val(0);
	$("#PicCutWindow").window('destroy');
	$("#PicCutWindow").remove();
	try{top.fraInterface.unLockPage();}catch(ex){}
}

function showCoords(c){
	$('#x').val(c.x);
	$('#y').val(c.y);
	$('#x2').val(c.x2);
	$('#y2').val(c.y2);
	$('#w').val(c.w);
	$('#h').val(c.h);
};

function intoSignatureCut(BtnId){
	lockPage("请在投保扫描件显示区域进行签名截取!");
	//try{top.fraPic.rotateBack();}catch(ex){};
	var vm = top.viewMode;
	if(vm%3!=1){
			top.fraSet.rows = "0,50%,*,0";
			top.fraSet.cols = "*";
			top.fraInterface.ScanViewHide();
			top.viewMode = 1;
	}
	$.each(mSBArr,function(key,val){
		$("#"+val+"Window").window('close');
	});
	try{top.fraPic.signatureCut(BtnId);}catch(ex){};	
}

function signatureQuery(Field){
	var BtnId = Field.id;
	var sqBtnId = '#'+BtnId;
	var sqWinId = sqBtnId+'Window';
	var sqPicId = sqBtnId+'Pic';
	var sqBtn = $(sqBtnId).offset();
	var ww = sqBtn.left + $(sqBtnId).width();
	var wh = sqBtn.top + $(sqBtnId).height();
	var cancut = $(sqBtnId).attr("cancut");//0表示不能截图,1为可以截取
	if(cancut == null || cancut == ""){
		cancut = "0";
	}

	if($(sqWinId).length>0){

		$(sqWinId).window('move',{
 									left:ww,
									top:wh
								});
		$(sqWinId).window('open');
		
		if(($(sqWinId).window('options').height+wh-$(window).scrollTop())> $(window).height()){
			var newST = $(sqWinId).window('options').height+wh-$(window).height();
			$(document.body).scrollTop(newST);
		}
		
		var pSrc = $(sqPicId).attr("src");
		if(pSrc == null || pSrc== ""){
			$(sqWinId).window('close');
			if(top.fraPic.arrPicName == null){				
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
				var codeType = $(sqBtnId).attr("codetype");
				if(codeType == null || codeType == ""){
					codeType = "PrtNo";//默认为PrtNo
				}
				if($("[name='"+codeType+"']").length==0){
					alert('codeType定义的页面元素'+codeType+'不存在!');
					return;
				}
				var docCode = $("[name='"+codeType+"']").val();
				var params = {
						oper:'sq',
						docCode:docCode
  			};
  			jQuery.post(
  				"../common/jsp/SignatureCut.jsp?sid="+getTimeForURL(),
  				params,
  				function(data) {
						var size = data.Size;
						var cutHeight = 0;
						var cutHtml = "";
						if(cancut == "1"){
							cutHtml = "<br>&nbsp;&nbsp;&nbsp;<a id= \""+BtnId+"Change\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\" onclick=\"intoSignatureCut('"+BtnId+"')\" >更换签名</a>";
							cutHeight = 30;
						}
						var signShowHtml = "<div id=\""+BtnId+"Window\">"
														 + "<div id=\""+BtnId+"Div\" ><img id=\""+BtnId+"Pic\" src=\"\"/></div>"+ cutHtml + "</div>";
						$(document.body).append(signShowHtml);
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
						$('#'+BtnId+'Change').linkbutton();
						mSBArr.push(BtnId);
						if(top.fraPic.arrPicName == null){
							$("#"+BtnId+"Window").window('close');
							alert('签名不存在!');
							return;
						}
						if(size<1){
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
							$('#'+BtnId+'Pic').attr("src",data.PicPath);
							var sImg = new Image;
							sImg.onload = function(){
    						var pw = sImg.width +14;
    					if(pw<140){
    						pw = 140;	
    					}
    						var ph = sImg.height +46 + cutHeight;
    						$(sqWinId).window('resize',{
								 			width:pw,
 											height:ph
								});
								
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

function afterSignCut(BtnId){

		var codeType = $('#'+BtnId).attr("codetype");//0为印刷号,1为保单号
		if(codeType == null || codeType == ""){
			codeType = "PrtNo";//默认为PrtNo
		}
		var docCode = $("[name='"+codeType+"']").val();
		var params = {
			oper:'sq',
			docCode:docCode		
  	};
  	jQuery.post(
  		"../common/jsp/SignatureCut.jsp?sid="+getTimeForURL(),
  		params,
  		function(data) {
  			var size = data.Size;
  			if(size<1){
 					alert('没有签名图片信息!');
  			}else{
  				$.each(mSBArr,function(key,val){
  						var cancut = $('#'+val).attr("cancut");
  						if(cancut == null || cancut == ""){
								cancut = "0";
							}
							var sqBtn = $('#'+val).offset();
							var ww = sqBtn.left + $('#'+val).width();
							var wh = sqBtn.top + $('#'+val).height();
							var cutHeight = 0;
							if(cancut == "1"){
								cutHeight = 30;
							}
							$('#'+val+'Pic').attr("src",data.PicPath);
							var sImg = new Image;
							sImg.onload = function(){
								var pw = sImg.width +14;
								if(pw<140){
    							pw = 140;	
    						}
    						var ph = sImg.height +46+cutHeight;
    						$("#"+val+"Window").window('resize',{
								 	width:pw,
 									height:ph,
 									left: ww,
									top: wh
								});
								if(val==BtnId){
									$("#"+BtnId+"Window").window('open');
									$('#'+BtnId+'Div').show();
									if((ph+wh-$(window).scrollTop())> $(window).height()){
										var newST = ph+wh-$(window).height();
										$(document.body).scrollTop(newST);
									}
								}
							};
							sImg.src = data.PicPath;
  				});
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
