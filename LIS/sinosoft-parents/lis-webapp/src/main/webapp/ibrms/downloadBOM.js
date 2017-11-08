//更新记录： 初始化BOM添加页面
//更新人:  
//更新日期:  2008-8-15 
//更新原因/内容：

var showInfo;
var turnPage = new turnPageClass();
var tResourceName = 'ibrms.downloadBOMSql';
function submitForm() {
	// 提交前的检验
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
}

function queryClick() {
	//BOM高级查询
//	var sqlStr = "select name,cnname,fbom,localitem,fatheritem,bomlevel,business,discription,source,valid from LRBOM where 1=1";
	
	var sqlid1="downloadBOMSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(tResourceName); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(document.all("eName").value);//指定传入的参数
	mySql1.addSubPara(document.all("cName").value);//指定传入的参数
	mySql1.addSubPara(document.all("Business").value);//指定传入的参数
	mySql1.addSubPara(document.all("Valid").value);
	var strSql=mySql1.getString();	
	
	/*if (document.all('eName').value.trim().length > 0) {
		sqlStr += " and name like  '%" + document.all("eName").value + "%'";
	}
	if (document.all('cName').value.trim().length > 0) {
		sqlStr += " and cnname like  '%" + document.all("cName").value + "%'";
	}
	if (document.all('Business').value.trim().length > 0) {
		sqlStr += " and business like  '%" + document.all("Business").value + "%'";
	}
	if (document.all('Valid').value.trim().length > 0) {
		sqlStr += " and valid like  '%" + document.all("Valid").value + "%'";
	}
	sqlStr += " order by name";*/
	//prompt("",sqlStr);
	turnPage.queryModal(strSql, QueryGrpGrid);
	
}

function closeShowInfo(){
	try{
		showInfo.close();
	}catch(ex){
		
	}
}
// 提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content) {
	//alert(FlagStr);	
	if (FlagStr == "Fail") {	
		closeShowInfo();
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
		closeShowInfo();
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ content;	
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");		
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		}
}

function creatPartClick(){
	var i = 0;
	var flag = 0;
	flag = 0;
	//判定是否有选择打印数据
	for( i = 0; i < QueryGrpGrid.mulLineCount; i++ )
	{
		if( QueryGrpGrid.getChkNo(i) == true )
		{
			flag = 1;
			break;
		}
	}
	//如果没有打印数据，提示用户选择
	if( flag == 0 )
	{
		alert("您还没有选择需要生成JAVA文件的BOM信息");
		return false;
	}
	document.all('creatFlag').value = "CREATEPAET";	
	submitForm();	
	queryClick();
}

function creatAllClick(){
	document.all("creatFlag").value = "CREATEALL";
	submitForm();	
}

function downClick(){
	document.all("creatFlag").value = "DOWNCLASS";
	submitForm();
}