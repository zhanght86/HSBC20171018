//更新记录： 
//更新人:  
//更新日期:  2008-8-15 
//更新原因/内容：

var showInfo;
var turnPage = new turnPageClass();
var turnPageitem = new turnPageClass();

var tResourceName = 'ibrms.LDMsgResultInputSql';

// 查询并列出所有BOM记录
function queryClick() {
	
	
		var sqlid1="LDMsgResultInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(jKeyID);//指定传入的参数
		 var strSql=mySql1.getString();	
	turnPage.queryModal(strSql, QueryGrpGrid);
}


//提交
function submitForm() {	
	//var i = 0;
	//var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	document.all('hiddenAction').value='SAVE';
	lockPage("正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面!");
	fm.submit(); // 提交

}


function deleteForm() {	
	var count = QueryGrpGrid.getSelNo();// 获取选中的行
	if(count<=0){
		alert('请选择需要删除的数据!');
		return false;
	}
		
	//var i = 0;
	//var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	document.all('hiddenAction').value='DELETE';
	lockPage("正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面!");
	fm.submit(); // 提交

}



function itemQuery() {	
	
	var Lan;
	var count = QueryGrpGrid.getSelNo();// 获取选中的行
	if(count>0){
	  Lan = QueryGrpGrid.getRowColData(count-1,1);// 获取选中行的数据
	  Msg = QueryGrpGrid.getRowColData(count-1,2);
	  document.getElementById("MsgLan").value=Lan;
	  document.getElementById("MsgDetail").value=Msg;
	  showOneCodeName('language','MsgLan','MsgLanName');;
	}
}

function afterSubmit(FlagStr,content)
{
	unLockPage();
  if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		}
    else
    {   
        content = "保存成功！";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		}
		
		initForm();
}