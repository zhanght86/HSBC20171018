//更新记录： 初始化词条添加页面
//更新人:  
//更新日期:  2008-8-15 
//更新原因/内容：

var showInfo;

function itemAdd() {	
	if(beforeSubmit()){
		document.all("Transact").value = "INSERT";
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

//点击取消时返回的页面
function returnParent(){
	document.location.href="bomMain.jsp"; 
}


//词条预校验
function afterCodeSelect(coname,Field){
	try{
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
		var bomname = document.all("bomName").value;
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ content;	
		var flag = confirm("操作成功,是否在当前BOM下继续增加词条?");
		if(flag){
			document.location.href="itemAddInput.jsp?bbName="+bomname; 
		}else{
			returnParent();	
		}
				
	}
}

function eNameCheck(Filed){	
	var patrn=/^[a-zA-Z0-9]{0,20}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("格式有误,只能为英文和数字");
		document.all("ItemEName").value = "";	
	}
}

function cNameCheck(Filed){	
	var patrn=/^[\u4e00-\u9fa5/]{0,30}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("格式有误,只能为中文和"/"");
		document.all("CNName").value = "";	
	}
}


function beforeSubmit() {
	// 添加前校验
	if (document.all('ItemEName').value.trim() == '') {
		alert("词条英文名不能为空!");
		return false;
	}
	if (document.all('CNName').value.trim() == '') {
		alert("词条中文名不能为空!");
		return false;
	}
	if (document.all('bomName').value.trim() == '') {
		alert("所属BOM名字不能为空!");
		return false;
	}
	if (document.all('IsHierarchical').value.trim() == '') {
		alert("层次型数据不能为空!");
		return false;
	}
	if (document.all('IsBase').value.trim() == '') {
		alert("基础词条不能为空!");
		return false;
	}	
	if (document.all('CommandType').value.trim() == '') {
		alert("词条数据类型不能为空!");
		return false;
	}
	if (document.all('Valid').value.trim() == '') {
		alert("词条有效性不能为空!");
		return false;
	}
	return true;
}