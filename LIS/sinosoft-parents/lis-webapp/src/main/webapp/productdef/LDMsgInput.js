//更新记录： 
//更新人:  
//更新日期:  2008-8-15 
//更新原因/内容：

var showInfo;
var turnPage = new turnPageClass();
var turnPageitem = new turnPageClass();

var tResourceName = 'productdef.LDMsgInputSql';

// 查询并列出所有BOM记录
function queryClick() { 
	
	
		var sqlid1="LDMsgInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(jKeyID);//指定传入的参数
		mySql1.addSubPara(jMsgType);//指定传入的参数
		 var strSql=mySql1.getString();	
		// alert(strSql);
	turnPage.queryModal(strSql, QueryGrpGrid);
}


function deleteForm() {	
	var count = QueryGrpGrid.getSelNo();// 获取选中的行
	if(count<=0){
		myAlert(''+"请选择需要删除的数据!"+'');
		return false;
	}
		
	//var i = 0;
	//var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	fm.all('hiddenAction').value='DELETE';
	lockPage(""+"正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面!"+"");
	fm.submit(); // 提交

}

//提交
function submitForm() {	
	if(!verifyForm('fm')){
			return false;
	}
	//var i = 0;
	//var showStr = "正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	lockPage(""+"正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面!"+"");
	fm.all('hiddenAction').value='SAVE';
	fm.submit(); // 提交
}
//删除BOM对象
function deleteClick() {
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if (selMenuGrpNo == 0) {
		myAlert(""+"您还没有选择需要删除的BOM"+"");
		return;
	}
	if (!confirm(""+"删除BOM将删除BOM下的所有词条，您确实要删除这个BOM吗？"+""))
		return;
	fm.all("Action").value = "DELETE";
	submitForm();	
	queryClick();
	itemQuery();
}

//----------- 词条操作-----------------
//词条查询，可以根据BOM表中选中的记录查询词条
function itemQuery() {	
	
		var Lan;
	var count = QueryGrpGrid.getSelNo();// 获取选中的行
	if(count>0){
	  Lan = QueryGrpGrid.getRowColData(count-1,1);// 获取选中行的数据
	  Msg = QueryGrpGrid.getRowColData(count-1,3);
	  MsgType = QueryGrpGrid.getRowColData(count-1,2);
	  document.getElementById("MsgLan").value=Lan;
	  document.getElementById("MsgDetail").value=Msg;
	   document.getElementById("MsgType").value=MsgType;
	  showOneCodeName('language','MsgLan','MsgLanName');
	   showOneCodeName('msgtype','MsgType','MsgTypeName');
	}
}

function afterSubmit(FlagStr,content)
{
  	unLockPage();
  if (FlagStr == "Fail")
    {
var urlStr="../common/jsp/MessagePage.jsp?picture=F&content="+content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    }
    else
    {   
        content = ""+"保存成功！"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		}
		
		initForm();
}

//-------- add by jucy
//增加返回按钮
function returnForm(){
	top.opener.focus();
	top.close();
}
//-------- end
