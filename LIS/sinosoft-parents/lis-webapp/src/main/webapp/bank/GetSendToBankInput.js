//GetSendToBankInput.js该文件中包含客户端需要处理的函数和事件

var showInfo;

//提交，保存按钮对应操作
function submitForm() {
  if(verifyInput() == false) return false;
  
  document.all("typeFlag").value = "";
  fm.signbutton1.disabled = true;
  document.getElementById("fm").submit();
}

//提交，保存按钮对应操作
function submitFormForXQ() {
  if(verifyInput() == false) return false;
  
  document.all("typeFlag").value = "ALLXQ";
  
  if (confirm("提取所有续期续保的催收数据，确定吗？")) {
    document.getElementById("fm").submit();
  }
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content ) {
	  fm.signbutton1.disabled = false;
  try { showInfo.close(); } catch(e) {}
  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
} 
