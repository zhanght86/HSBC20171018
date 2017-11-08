//更新记录：词条更新的流程处理
//更新人:  
//更新日期:  2008-8-15 
//更新原因/内容：

var showInfo;
//var turnPagebom = new turnPageClass();

// 提交，保存按钮对应操作
function defineMultLanguage()
{
	var tBOMName = document.all('bomName').value;
	var tItemName = document.all('ItemEName').value;
	addMsgResult(tBOMName+"_"+tItemName,1,'BOMItem');
}
function addMsgResult(tKeyID,tEditFlag,tMsgType)
{
	dialogURL="../ibrms/LDMsgBOMMain.jsp?KeyID="+tKeyID+"&EditFlag="+tEditFlag+"&MsgType="+tMsgType;  
	//showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:500px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (dialogURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

function itemUpdate() {
		document.all("Transact").value = "UPDATE";
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
}

//点击取消时返回的页面
function returnParent(){
	document.location.href="bomMain.jsp"; 
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
		alert("修改词条信息成功");
		returnParent();			
	}
}


//词条预校验
function afterCodeSelect(coname,Field){
	try{
		/*if(coname=='IbrmsCommandType'){
			var itemsql;
			var SourceType = document.all('CommandType').value;			
			if(SourceType==''){
				itemsql= "select code1,codename from ldcode1 where 1=2";
				}else{
				itemsql = "select code1,codename from ldcode1 where code='"+SourceType+"'";
			}
			var itemTem = easyQueryVer3(itemsql,1,1,1);		
			document.all('PreCheck').CodeData = itemTem;
		}*/
		if(coname=='IbrmsCommandType'){
			var itemsql;
			var SourceType = document.all('CommandType').value;			
			if(SourceType==''){
				//itemsql= "select code1,codename from ldcode1 where 1=2";
				document.all('PreCheck').CodeData='';
				}else{
				//itemsql = "select code1,codename from ldcode1 where code='"+SourceType+"'";
			
			//alert('SourceType'+SourceType);
			var sqlid1="itemAddInputSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName('ibrms.itemAddInputSql'); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		
			mySql1.addSubPara(SourceType);
		  var strSql=mySql1.getString();	
		 // alert('1'+strSql);
				var itemTem = easyQueryVer3(strSql,1,1,1);		
				if(itemTem==false){
					document.all('PreCheck').CodeData=''
				}else{
					document.all('PreCheck').CodeData = itemTem;
				}
			}
		}
		
	}catch(ex){
		
	}
}

function eNameCheck(Filed){	
	var patrn=/^[a-zA-Z0-9]{0,20}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("格式有误,只能为英文和数字");
	document.all('ItemEName').value = "";	
	}
}

function cNameCheck(Filed){	
	var patrn=/^[\u4e00-\u9fa5/]{0,30}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("格式有误,只能为中文和"/"");
	document.all('CNName').value = "";	
	}
}
/*function lengthCheck(Filed){	
	var patrn=/^[0-9]{0,10}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("格式有误,只能为数字");
	document.all("Length").value = "";	
	}
}
*/