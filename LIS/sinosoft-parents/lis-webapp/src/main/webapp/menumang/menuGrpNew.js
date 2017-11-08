//更新记录： showAllMenuInUnselect()，showMenuGrp()
//更新人:  周磊
//更新日期:  2005-5-4  
//更新原因/内容：增加页面权限菜单

var turnPage = new turnPageClass();
var mySql = new SqlClass();
function queryClick()
{
	var UserCode = document.all("UserCode").value;
	var ComCode = document.all("ComCode").value;
	if(UserCode=="") 
  {
  	alert("请先输入要查询的管理员代码，再进行查询！");
   	return ;
  }
	
	
	// 书写SQL语句
	/*var sqlStr="select Operator,MenuGrpCode,MenuGrpName,MenuGrpDescription " +
						 " from LDMenuGrp where Operator = '" + UserCode + "' and MenuGrpCode in " +
						 " (select MenuGrpCode from LDUserTOMenuGrp where usercode in " +
						 " (select usercode from lduser where comcode like '" + ComCode + "%%'))";*/
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuGrpNewSql");
	mySql.setSqlId("menuGrpNewSql1");
	mySql.addSubPara(UserCode); 
	mySql.addSubPara(ComCode); 
	//alert(sqlStr);
						 
	turnPage.queryModal(mySql.getString(), QueryGrpGrid);
}


//提交前进行必要的检查
function DataCopy()
{
	//得到被选中的数据
	getInput();
	//alert("1111111111111");
	if(document.all("MenuGrpCode").value == "" || document.all("MenuGrpCode").value == null)
	{
		alert("必须输入新的菜单分组编码！");
		return;
	}
	//向后台提交数据
  submitForm();
    
}


//提交，保存按钮对应操作
function submitForm()
{
	var i = 0;
	var showStr="正在复制数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //提交
}

function afterSubmit(FlagStr)
{
    showInfo.close();
    
    if (FlagStr == "success")
    	alert("复制菜单组成功！");
    else
    	alert("复制菜单组失败，可能的原因是此菜单组已存在！");  
    	
    clear();	
}

function getInput()
{
	var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<QueryGrpGrid.mulLineCount; i++) 
  {
   if (QueryGrpGrid.getSelNo(i)) 
   { 
      checkFlag = QueryGrpGrid.getSelNo();
      break;
   }
  }
  
  if (checkFlag != 0) 
  { 
    var	Action = QueryGrpGrid.getRowColData(checkFlag - 1, 2); 	
    //alert("选中的菜单分组编码：" + Action);
    document.all("Action").value = Action;
  }
  else 
  {
    alert("请先选择一条要复制的菜单组信息！"); 
  }
}
  
function clear()
{
	document.all("MenuGrpName").value = "";
	document.all("MenuGrpCode").value = "";
	document.all("MenuGrpDescription").value = "";
	document.all("MenuSign").value = "";
}










