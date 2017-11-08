"use strict";
var showInfo = null;
var submitForm = function(){
    
}

var showMessagePage = function(title, content, messagePicture, width, height) {
    var urlStr = "../common/jsp/MessagePage.jsp?picture=" + messagePicture + "&content=" + content ;
    var name = title;   //网页名称，可为空; 
    var iWidth = width;      //弹出窗口的宽度; 
    var iHeight = height;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    var tmpShowInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    tmpShowInfo.focus();
    return tmpShowInfo;
}

var afterSubmit = function(flagStr, content, onsucc_name){
    if(showInfo !== null && showInfo !== undefined)
        showInfo.close();
	
    if (flagStr == "Fail" )
    {             
        showInfo = showMessagePage("操作失败", content, "F", 550, 350);
    }
    else if(flagStr == "Succ")
    { 
        showInfo = showMessagePage("操作成功", content, "S", 550, 350);
        if(onsucc_name !== null && onsucc_name !== undefined)
            if(window[onsucc_name]){
                window[onsucc_name]();
            }
    }
}