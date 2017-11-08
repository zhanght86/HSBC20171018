//更新记录： 初始化BOM添加页面
//更新人:  
//更新日期:  2008-8-15 
//更新原因/内容：

var showInfo;

function returnParent(){
	document.location.href="bomMain.jsp"; 
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

function afterCodeSelect(coname,Field){
	try{		
		if(coname=='ibrmsfBOM'){
			var bomtem = document.all('fBOM').value;
			//var fitemsql = "select name,cnname from lrbomitem where bomname='"+bomtem+"'";
			//prompt("",fitemsql);
			//var tem = easyQueryVer3(fitemsql,1,1,1);
		var sqlid1="bomAddInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName('ibrms.bomAddInputSql'); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		
		mySql1.addSubPara(fBOM);
	  var strSql=mySql1.getString();	
		var tem = easyQueryVer3(strSql,1,1,1);
			document.all('fatherItem').CodeData = tem;
		}
	}catch(ex){
		
	}
}
// 对父BOM字段进行初始化
function fbomonload(){
	document.all('fatherItem').value = "";	
	var fBOM = document.all("fBOM").value;

	
	
	var fbomsql;
	if(fBOM==''){
		//fbomsql = "select name,cnname from (select * from lrbomitem where 1=2)";
		document.all('fatherItem').CodeData='';
	}else{
		//fbomsql = "select name,cnname from (select * from lrbomitem where bomname='"+fBOM+"')";
	
		//prompt("",fbomsql);
			var sqlid1="bomAddInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName('ibrms.bomAddInputSql'); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		
		mySql1.addSubPara(fBOM);
	  var strSql=mySql1.getString();	
		var tem = easyQueryVer3(strSql,1,1,1);
		if(tem==false){
			document.all('fatherItem').CodeData=''
		}else{
			document.all('fatherItem').CodeData = tem;
		}
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
		if(confirm("添加BOM对象成功,是否继续添加?")){
			document.location.href="bomAddInput.jsp";		
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
		alert("在bomAddInput.js-->resetForm函数中发生异常:初始化界面错误!");
	}
}

// 取消按钮对应操作
function cancelForm() {
	// window.location="../common/html/Blank.html";
}

// 提交前的校验、计算
function beforeSubmit() {
	// 添加前校验	
	document.all("Transact").value = "INSERT";
	if (document.all('eName').value.trim() == '') {
		alert("BOM英文名不能为空!");
		return false;
	}
	if (document.all('cName').value.trim() == '') {
		alert("BOM中文名不能为空!");
		return false;
	}		
	if(document.all("bomLevel").value.trim()==''){
		alert("层次不能为空！");
	    return false;
	}
	if (document.all('Business').value.trim()== ''){
		alert("业务模块不能为空！");
		return false;
	}
	return true;
}

function eNameCheck(Filed){	
	var patrn=/^[a-zA-Z0-9]{0,20}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("格式有误,只能为英文和数字");
	document.all("eName").value = "";	
	}
}

function cNameCheck(Filed){	
	var patrn=/^[\u4e00-\u9fa5]{0,30}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("格式有误,只能为中文");
	document.all("cName").value = "";	
	}
}

function bomLevelCheck(Field){
	var patrn=/^[0-9]\d*$/;
	if (!patrn.test(Field.value.trim())) {
		alert("格式有误,BOM层次只能为整数");
	    document.all("bomLevel").value = "";
	    return "0";
	}
	
}