// 更新记录：BOM更新的流程处理
// 更新人:
// 更新日期: 2008-8-15
// 更新原因/内容：

var showInfo;
// 提交，保存按钮对应操作
function defineMultLanguage()
{
	var tBOMName = document.all('eName').value;
	addMsgResult(tBOMName,1,'BOM');
}
function addMsgResult(tKeyID,tEditFlag,tMsgType)
{
	dialogURL="../ibrms/LDMsgBOMMain.jsp?KeyID="+tKeyID+"&EditFlag="+tEditFlag+"&MsgType="+tMsgType;  
	//showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:500px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=1000;      //弹出窗口的宽度; 
	var iHeight=500;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (dialogURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

function updateBom() {
	document.all("Transact").value = "UPDATE";
	if (verifyInput()) {
		document.getElementById("fm").submit();
		var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ showStr;
		//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	// showInfo.close();
}

function afterCodeSelect(coname, Field) {
	try {
		if (coname == 'ibrmsfBOM') {
			var bomtem = document.all('fBOM').value;
//			var fitemsql = "select name,cnname from lrbomitem where bomname='"
//					+ bomtem + "'";
			var fitemsql = "";
		    var sqlid9="bomUpdateSql9";
			var mySql9=new SqlClass();
			mySql9.setResourceName("ibrms.bomUpdateSql"); 
			mySql9.setSqlId(sqlid1);
			mySql9.addSubPara(bomtem);
			fitemsql=mySql9.getString();
			var tem = easyQueryVer3(fitemsql, 1, 1, 1);
			if (tem == false) {
				document.all('fatherItem').CodeData = ''
			} else {
				document.all('fatherItem').CodeData = tem;
			}
		}
	} catch (ex) {

	}
}

// 点击取消时返回的页面
function returnParent() {
	document.location.href = "bomMain.jsp";
}

// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	// alert(FlagStr);
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
		alert("修改BOM信息成功");
		returnParent();
	}
}
