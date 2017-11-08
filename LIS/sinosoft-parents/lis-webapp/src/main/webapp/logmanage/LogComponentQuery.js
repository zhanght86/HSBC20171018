//               该文件中包含客户端需要处理的函数和事件
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var sqlresourcename = "logmanage.LogComponentSql";
//提交，保存按钮对应操作

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function DateReset()
{
  try
  {
	  initInpBox();
  }
  catch(re)
  {
  	alert("LogComponent.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

function ClickQuery()
{
	// 初始化表格
	initLogStateGrid();
    initLogTrackGrid();

	// 书写SQL语句
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("LogComponentSql2");
	    mySql.addSubPara(fm.SubjectID.value);
	    mySql.addSubPara(fm.ItemID.value);
	    mySql.addSubPara(fm.KeyNO.value);

	turnPage.queryModal(mySql.getString(), LogStateGrid);
	
	var mySql2=new SqlClass();
	    mySql2.setResourceName(sqlresourcename);
	    mySql2.setSqlId("LogComponentSql3");
	    mySql2.addSubPara(fm.SubjectID.value);
	    mySql2.addSubPara(fm.ItemID.value);
	    mySql2.addSubPara(fm.KeyNO.value);

	turnPage1.queryModal(mySql2.getString(), LogTrackGrid);
	if(LogStateGrid.mulLineCount <= 0 && LogTrackGrid.mulLineCount <= 0){
		alert("没有符合条件的日志数据！");
		return false;
	}

}

