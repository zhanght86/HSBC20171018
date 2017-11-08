//更新记录： 初始化运算符添加页面
//更新人:  
//更新日期:  2008-9-17 
//更新原因/内容：

var showInfo;

function returnParent(){
	document.location.href="CommandMain.jsp"; 
}

function submitForm() {
	// 提交前的检验
	if(beforeSubmit()){
		var i = 0;
		var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
		//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		document.getElementById("fm").submit(); // 提交	
		//showInfo.close();	
		
	}else{	
	
	}
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	//alert(FlagStr);
	showInfo.close();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ content;	
		if(confirm("添加运算符成功,是否继续添加?")){
			document.location.href="CommandAddInput.jsp";		
		}else{
			returnParent();			
		}	
	}
	
}

// 重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		alert("在CommandAddInput.js-->resetForm函数中发生异常:初始化界面错误!");
	}
}

// 取消按钮对应操作
function cancelForm() {
	// window.location="../common/html/Blank.html";
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加前校验		
	if (document.all('Name').value.trim() == '') {
		alert("运算符名称不能为空!");
		return false;
	}
	if (document.all('display').value.trim() == '') {
		alert("界面展示不能为空!");
		return false;
	}
	if (document.all('implenmation').value.trim() == '') {
		alert("技术实现不能为空!");
		return false;
	}
	if (document.all('Valid').value == '') {
		alert("有效性不能为空!");
		return false;
	}
	if (document.all('commandType').value == '') {
		alert("运算数据类型不能为空!");
		return false;
	}
	if (document.all('resulttype').value == '') {
		alert("运算结果类型不能为空!");
		return false;
	}
	document.all("Transact").value = "INSERT";
	return true;
}

function afterCodeSelect( cCodeName, Field ) {
  
    
    if(cCodeName=="IbrmsCommType")
    {
    		if(Field.value=='0')
    		{
    			CommDetailDiv.style.display = "none";
    			document.all('CommDetail').value = "";
    		}
    		else
    		{
    			CommDetailDiv.style.display = "";
    		}
    }
}