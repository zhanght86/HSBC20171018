//             该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var arrDataSet;
var turnPage = new turnPageClass();



//返回按钮对应的操作
function returnParent()
{
  var arrReturn = new Array();
	var tSel = FinItemDefGrid.getSelNo();

	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "请您先选择一条记录，再点击返回按钮。" );
	else
	{

			try
			{
				arrReturn = getQueryResult();
				top.opener.afterQuery2( arrReturn );
			}
			catch(ex)
			{
				alert( "没有发现父窗口的afterQuery接口。" + ex );
			}
			top.close();

	}
}



function getQueryResult()
{
	var arrSelected = null;
	tRow = FinItemDefGrid.getSelNo();
	if( tRow == 0 || tRow == null )
	{
	  return arrSelected;
	}
	arrSelected = new Array();
	//tRow = 10 * turnPage.pageIndex + tRow; //10代表multiline的行数
	//设置需要返回的数组
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
		// 书写SQL语句
	var strSQL = "";
	/**
	strSQL = "select a.VersionNo,a.FinItemID,a.FinItemName,a.FinItemType,a.ItemMainCode,a.DealMode,a.ReMark";
	strSQL = strSQL + " from FIFinItemDef a  where a.VersionNo ='"+FinItemDefGrid.getRowColData(tRow-1,1)+"' and a.FinItemID ='"+FinItemDefGrid.getRowColData(tRow-1,2)+"'";
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FinItemDefQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId("FinItemDefQuerySql1");//指定使用的Sql的id
		mySql1.addSubPara(FinItemDefGrid.getRowColData(tRow-1,1));//指定传入的参数
		mySql1.addSubPara(FinItemDefGrid.getRowColData(tRow-1,2));//指定传入的参数
		strSQL= mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
    }
	//查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	return arrSelected;
}



// 查询按钮对应的操作
function easyQueryClick()
{
	// 初始化表格
	initFinItemDefGrid();
	//document.all('BranchType').value = top.opener.fm.BranchType.value;
	// 书写SQL语句
	var strSQL = "";
	/**
	//var tReturn = parseManageComLimitlike();
	strSQL = "select VersionNo,FinItemID,FinItemName,FinItemType,FinItemType,ItemMainCode,DealMode,ReMark from FIFinItemDef where 1=1 "
     + getWherePart('VersionNo')
     + getWherePart('FinItemID','FinItemID','like')
     + getWherePart('FinItemName','FinItemName','like')     
     + getWherePart('FinItemType')
     + getWherePart('ItemMainCode')
     + getWherePart('DealMode')
     +"order by VersionNo,FinItemID";
	*/
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FinItemDefQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId("FinItemDefQuerySql2");//指定使用的Sql的id
		mySql2.addSubPara(fm.VersionNo.value);//指定传入的参数
		mySql2.addSubPara(fm.FinItemID.value);//指定传入的参数
		mySql2.addSubPara(fm.FinItemName.value);//指定传入的参数
		mySql2.addSubPara(fm.FinItemType.value);//指定传入的参数
		mySql2.addSubPara(fm.ItemMainCode.value);//指定传入的参数
		mySql2.addSubPara(fm.DealMode.value);//指定传入的参数
		strSQL= mySql2.getString();
	turnPage.queryModal(strSQL,FinItemDefGrid);
}


//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}