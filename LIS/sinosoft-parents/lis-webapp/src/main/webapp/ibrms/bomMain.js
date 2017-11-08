//更新记录： 
//更新人:  
//更新日期:  2008-8-15 
//更新原因/内容：

var showInfo;
var turnPage = new turnPageClass();
var turnPageitem = new turnPageClass();

var tResourceName = 'ibrms.bomMainSql';

// 查询并列出所有BOM记录
function queryClick() {
	//BOM高级查询
	/*
	var sqlStr = "select name,cnname,fbom,localitem,fatheritem,bomlevel,business,discription,source,valid from LRBOM where 1=1";
	if (document.all("eName").value.trim().length > 0) {
		sqlStr += " and name like  '%" + document.all("eName").value + "%'";
	}	
	if (document.all("cName").value.trim().length > 0) {
		sqlStr += " and cnname like  '%" + document.all("cName").value + "%'";
	}
	if (document.all("Business").value.length > 0) {
		sqlStr += " and business like  '%" + document.all("Business").value + "%'";
	}
	if (document.all("Valid").value.length > 0) {
		sqlStr += " and valid like  '%" + document.all("Valid").value + "%'";
	}
	sqlStr += " order by name";
	//prompt("",sqlStr);
	*/
	
		var sqlid1="bomMainSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all("eName").value);//指定传入的参数
		mySql1.addSubPara(document.all("cName").value);//指定传入的参数
		mySql1.addSubPara(document.all("Business").value);//指定传入的参数
		mySql1.addSubPara(document.all("Valid").value);
	  var strSql=mySql1.getString();	
	turnPage.queryModal(strSql, QueryGrpGrid);
}

//添加BOM对象，跳转到bomAddInput.jsp,交由bomAddInput.jsp处理
function insertClick() {
	document.all("eName").value = "";
	document.all("cName").value = "";	
	document.location.href = "bomAddInput.jsp";
}

function updateClick(){
	var count = QueryGrpGrid.getSelNo();// 获取选中的行
	if(count>0){
	var bomName = QueryGrpGrid.getRowColData(count-1,1);// 获取选中行的数据
	}else{
		if (count == 0) {
			alert("您还没有选择需要修改的BOM");
			return;
		}
	}		
	document.location.href = "bomUpdate.jsp?bbName="+bomName;
}

//提交
function submitForm() {	
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

}

function hiddenButton(){
	var BranchTyp = document.all('BranchTyp').value;
	if(BranchTyp=='1'){
		document.all('ic').style.display='none';
		document.all('uc').style.display='none';
		document.all('dc').style.display='none';
		document.all('ii').style.display='none';
		document.all('iu').style.display='none';
		document.all('id').style.display='none';
		}
}

//删除BOM对象
function deleteClick() {
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if (selMenuGrpNo == 0) {
		alert("您还没有选择需要删除的BOM");
		return;
	}
	if (!confirm("删除BOM将删除BOM下的所有词条，您确实要删除这个BOM吗？"))
		return;
	document.all("Action").value = "DELETE";
	submitForm();	
	queryClick();
	itemQuery();
}

//----------- 词条操作-----------------
//词条查询，可以根据BOM表中选中的记录查询词条
function itemQuery() {	
	
	var bomName;
	var count = QueryGrpGrid.getSelNo();// 获取选中的行
	if(count>0){
	  bomName = QueryGrpGrid.getRowColData(count-1,1);// 获取选中行的数据
	}
	
	/*else{
		 bomName="";
	}
	var sqlStr = "select name,bomname,cnname,connector,ishierarchical,isbase,sourcetype,source,commandtype,precheck,valid,description from lrbomitem where 1=1";
	
	if(bomName.trim()!=""){
		sqlStr+=" and bomname like '%" + bomName + "%'";
	}
	sqlStr+=" order by cnname,name";
	*/
		var sqlid1="bomMainSql2";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(bomName);//指定传入的参数
	  var strSql=mySql1.getString();	
	
	turnPageitem.queryModal(strSql, ItemGrid);
//	for ( var i = 0; i < ItemGrid.mulLineCount; i++) {
//		if (ItemGrid.getRowColData(i, 10) == "null") {		
//			ItemGrid.setRowColData(i, 10, "");
//		}
//	}
}

//词条插入
function itemInsert(){
	var count = QueryGrpGrid.getSelNo();// 获取选中的行
	if(count>0){
	var bomName = QueryGrpGrid.getRowColData(count-1,1);// 获取选中行的数据
	}else{
		if (count == 0) {
			alert("您还没有选择需要增加词条的所属BOM");
			return;
		}
	}
	document.location.href = "itemAddInput.jsp?bbName="+bomName;
}

function itemUpdate(){
	var count = ItemGrid.getSelNo();// 获取选中的行	
	if(count>0){
	var itemName = ItemGrid.getRowColData(count-1,1);// 获取选中行的数据	
	var bomName = ItemGrid.getRowColData(count-1,2);// 获取选中行的数据	
	document.location.href = "itemUpdate.jsp?itemName="+itemName+"&bomName="+bomName;
	}else{
		if (count == 0) {
			alert("您还没有选择需要修改的词条");
			return;
		}
	}			
}

function itemdelete(){
	var selMenuGrpNo = ItemGrid.getSelNo();
	if (selMenuGrpNo == 0) {
		alert("您还没有选择需要删除的词条");
		return;
	}
	if (!confirm("您确实要删除这个词条吗？"))
		return;
	
	document.all("itemAction").value = "DELETE";
	submitForm();
	showInfo.close(); 
	itemQuery();
}

function afterSubmit(FlagStr)
{
    showInfo.close();  
    if (document.all('Action').value == "DELETE") {
        if (FlagStr == "success")
            alert("删除BOM成功！");
        else
            alert("删除BOM失败!");
    }		
    if (document.all('itemAction').value == "DELETE") {
        if (FlagStr == "success")
            alert("删除词条成功！");
        else
            alert("删除词条失败!");
    }	
}