/***************************************************************
 * <p>ProName��ScanServo.js</p>
 * <p>Title��Ӱ���涯</p>
 * <p>Description��Ӱ���涯</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2013-01-01
 ****************************************************************/
 
 
$(document).ready(ScanViewCreate);//���ط���

var objName;//�ؼ�����
var picArray = "";//ͼƬ��Ϣ
var positionArray ;//�洢�涯����λ�ã�һ�д洢һ���涯����λ�ã�

/**
* ��ʼ��ͼƬ��Ϣ,�Լ�λ����Ϣ
*/
function ScanViewCreate(){
 
	if(scantype==2) {//�ж��Ƿ����涯 1-�涯��2-�涯����
		parent.fraPic.initPosArray();
	}else if(scantype==1) {
		ScanViewModeCreate();
		parent.fraPic.initPosArray();
	}
	
}

/**
* Ϊÿ������¼��ؼ�������Ӧ�涯���¼�����ҵ������е���
*/
function setFocus() {

	var RowNo=0;
	var MultLineName = "";
	for (var elementsNum=0; elementsNum<window.document.forms[0].elements.length; elementsNum++) { //��ȡ���пؼ�
	
		var tFunction = "goToArea";
		if(window.document.forms[0].elements[elementsNum].name.indexOf("Grid")==-1){//��muliline�е�ֵ
			eval(" window.document.forms[0].elements[elementsNum].onfocus = "+tFunction);  //���㼯��ʱ��������
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
* �Է�mulline�涯λ�õ�չʾ
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
* ��muline�涯�Ĵ���
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
* ��������
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
		parent.fraPic.rotateBack();//��תͼƬ180
	}
	if (keycode == "36") {
		parent.fraPic.changeViewMode();
	}
	if (((keycode == "100")||(keycode == "37")) && (event.altKey == true)) {
	  parent.fraPic.rotateLeft();//������ת
	}
	if (((keycode == "102")||(keycode == "39")) && (event.altKey == true)) {
	  parent.fraPic.rotateRight();//������ת
	}
}

/**
 * -----------------------------------------------------ͼƬ����ģʽ---------------------------------
 */
 
/**
* �涯��������ģʽ
*/
function ScanViewModeCreate(){
	
	parent.fraPic.lockPage("���ݼ�����...........");
	
	var vm = parent.fraPic.viewMode;
	picArray = parent.fraPic.arrPicName;
	$(document.body).append("<div id=\"scanViewWindow\" style=\"display:none\"><div id=\"imageView\" class=\"article\"><img id=\"scanPicture\" src=\"\"/></div></div>");
	$("#scanViewWindow").show();
	$("#scanViewWindow").window({
		title:'Ӱ���',
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
* �ر�����ģʽ
*/
function ScanViewHide(){
	$("#scanViewWindow").hide();
	$("#scanViewWindow").window("close");
}

/**
* ������ģʽ
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
* չʾӰ�������ģʽ��ģʽΪ0ʱ����
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
		 				lockPart("scanViewWindow","Ӱ�����λ��....");
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

//����ҵ��ţ�ҵ�����ͣ���֤ϸ���ѯӰ���
function showscan(subType,bussNo,bussType) {

	parent.fraPic.showeasyscan(subType,bussNo,bussType);
}

//���ͼƬ
function showclear() {
	parent.fraPic.showeasyclear();
}

/**
 * -----------------------------------------------------ǩ����ѯ---------------------------------
 */

/**
 * ת���ȡͼƬ�Ĳ���
 */
function intoSignatureCut(BtnId){
	
	parent.fraPic.lockPage("����Ͷ��ɨ�����ʾ�������ǩ����ȡ!");
	parent.fraPic.rotateBack();
	var vm = top.viewMode;
	if(vm%3!=1){//��ԭ��ģʽ1ȥ��ȡͼƬ
			top.fraSet.rows = "0,50%,*,0";
			top.fraSet.cols = "*";
			parent.fraInterface.ScanViewHide();
			top.viewMode = 1;
	}
	$("#"+BtnId+"Window").window('close');//�ر�ͼ�񴰿�
	parent.fraPic.signatureCut(BtnId);//��ȡͼƬ
}

/**
 * ��ͼ��ѯ
 */
function signatureQuery(Field){
	
	var BtnId = Field.id;
	var sqBtnId = '#'+BtnId;
	var sqWinId = sqBtnId+'Window';
	var sqPicId = sqBtnId+'Pic';
	var sqBtn = $(sqBtnId).offset();//��ȡƥ��Ԫ���ڵ�ǰ�ӿڵ����ƫ�ơ�
	var ww = sqBtn.left + $(sqBtnId).width();//��ȡ�ؼ��Ŀ��
	var wh = sqBtn.top + $(sqBtnId).height();//��ȡ�����ĸ߶�
	var cancut = $(sqBtnId).attr("cancut");//0��ʾ���ܽ�ͼ,1Ϊ���Խ�ȡ
	if(cancut == null || cancut == ""){
		cancut = "0";
	}

	if($(sqWinId).length>0){//�ж��Ƿ��Ѿ���ѯ����ֱ�ӽ�ͼƬչʾ����

		$(sqWinId).window('move',{left:ww,top:wh});
		$(sqWinId).window('open');
		if(($(sqWinId).window('options').height+wh-$(window).scrollTop())> $(window).height()){
			var newST = $(sqWinId).window('options').height+wh-$(window).height();
			$(document.body).scrollTop(newST);//������ͼ�ν���
		}
		var pSrc = $(sqPicId).attr("src");
		if(pSrc == null || pSrc== ""){
			$(sqWinId).window('close');
			if(parent.fraPic.arrPicName == null){				
					alert('ǩ��������!');
					return;
			}
			if(cancut=="1"){
				if(confirm('ǩ�������ڣ��Ƿ���н�ȡǩ��������')){
					intoSignatureCut(BtnId);
				}
			}else{
				alert('ǩ��������!');
				return;
			}

		}
	}else{		
		   
				var codeType = $(sqBtnId).attr("codetype");//����ÿһ���涯�����ǩ�������ҵ���
				if(codeType == null || codeType == ""){
					codeType = "BussNo";//Ĭ��ΪBussNo,�涯����ҵ��ţ��洢ÿһ���涯�����
				}
				if($("[name='"+codeType+"']").length==0){
					alert('codeType�����ҳ��Ԫ��'+codeType+'������!');
					return;
				}
				var docCode = $("[name='"+codeType+"']").val();//��Ϊҵ��Ŵ洢��es����
				var params = {
						Operate:'sq',
						DocCode:docCode
  			};
  			//��ѯǩ����ͼƬ
  			jQuery.post(
  				"../easyscan/SignatureCut.jsp?Sid="+getTimeForURL(),
  				params,
  				function(data) {
						var size = data.Size;
						var cutHeight = 0;
						var cutHtml = "";
						if(cancut == "1"){//�ж��Ƿ���Խ�ȡǩ��ͼƬ
							cutHtml = "<br>&nbsp;&nbsp;&nbsp;<a id= \""+BtnId+"Change\" href=\"javascript:void(0)\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\" onclick=\"intoSignatureCut('"+BtnId+"')\" >����ǩ��</a>";
							cutHeight = 30;
							$('#'+BtnId+'Change').linkbutton();
						}
						var signShowHtml = "<div id=\""+BtnId+"Window\">"
														 + "<div id=\""+BtnId+"Div\" ><img id=\""+BtnId+"Pic\" src=\"\"/></div>"+ cutHtml + "</div>";
						$(document.body).append(signShowHtml);//���ؽ���չʾǩ��ͼƬ
						$(sqWinId).window({
							title:'ǩ��Ӱ��',
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
						if(parent.fraPic.arrPicName == null){//�ж�Ӱ����Ƿ����
							$("#"+BtnId+"Window").window('close');
							alert('ǩ��������!');
							return;
						}
						if(size<1){//��ѯ��ͼƬΪ��
							$("#"+BtnId+"Window").window('close');
							if(cancut == "1"){
								if(confirm('ǩ�������ڣ��Ƿ���н�ȡǩ��������')){
									intoSignatureCut();
								}
							}else{
								alert('ǩ��������!');
								return;
							}
						}else{
							//��ѯ����ͼƬ·��
							$('#'+BtnId+'Pic').attr("src",data.PicPath);
							var sImg = new Image;
							sImg.onload = function(){//չʾͼƬ
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
 * �����ͼ�Ժ��ѯ
 */
function afterSignCut(BtnId){

		var codeType = $('#'+BtnId).attr("codetype");
		if(codeType == null || codeType == ""){
			codeType = "BussNO";//Ĭ��ΪPrtNo
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
 					alert('û��ǩ��ͼƬ��Ϣ!');
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
 * ��ȡ��ǰʱ�䴫��ϵͳ��
 */
function getTimeForURL() {
	
	//���jsʱ��
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
